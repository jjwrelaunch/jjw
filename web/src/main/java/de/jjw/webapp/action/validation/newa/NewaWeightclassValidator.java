/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaWeightclassValidator.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:45
 *
 * Ju Jutsu Web is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Ju Jutsu Web is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Ju Jutsu Web.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.jjw.webapp.action.validation.newa;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import de.jjw.model.newa.Newaclass;
import de.jjw.service.modelWeb.NewaclassWeb;
import de.jjw.util.ICodestableConstants;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.constants.admin.INewaWeightclassConstants;

public class NewaWeightclassValidator
    implements INewaWeightclassConstants, IValueConstants, ICodestableConstants
{

    public static boolean isRequiredFieldsValid( Newaclass newaclass, Vector<ErrorElement> errors )
    {
        boolean isValid = true;

        if ( TypeUtil.isEmptyOrDefault( newaclass.getWeightLimit() ) )
        {
            errors.add( new ErrorElement( FIELD_WEIGHT_LIMIT, GEN_NO_VALUE ) );
        }
        if ( TypeUtil.isEmptyOrDefault( newaclass.getStartWeight() ) )
        {
            errors.add( new ErrorElement( FIELD_START_WEIGHT, GEN_NO_VALUE ) );
        }

        if ( TypeUtil.isEmptyOrDefault( newaclass.getWeightclass() ) )
        {
            errors.add( new ErrorElement( FIELD_WEIGHTCLASS, GEN_NO_VALUE ) );
        }
        if ( TypeUtil.isEmptyOrDefault( newaclass.getSex() ) )
        {
            errors.add( new ErrorElement( FIELD_SEX, GEN_NO_VALUE ) );
        }
        if ( TypeUtil.isEmptyOrDefault( newaclass.getAge().getId() ) )
        {
            errors.add( new ErrorElement( FIELD_AGE, GEN_NO_VALUE ) );
        }

        if ( newaclass.getStartWeight() < DOUBLE_DEFAULT )
        {
            errors.add( new ErrorElement( FIELD_START_WEIGHT, GEN_VALUE_NOT_NEGATIV ) );
        }
        if ( newaclass.getWeightLimit() < DOUBLE_DEFAULT )
        {
            errors.add( new ErrorElement( FIELD_WEIGHT_LIMIT, GEN_VALUE_NOT_NEGATIV ) );
        }

        if ( errors.size() > 0 )
        {
            return false;
        }

        if ( newaclass.getStartWeight() > MAX_WEIGHTCLASS )
        {
            List<String> list = new ArrayList<String>();
            list.add( TypeUtil.toString( (int) MAX_WEIGHTCLASS ) );

            errors.add( new ErrorElement( FIELD_START_WEIGHT, NEWA_WEIGHTCLASS_MORE_THAN_MAX_WEIGHT, list ) );
        }

        if ( newaclass.getWeightLimit() > MAX_WEIGHTCLASS )
        {
            List<String> list = new ArrayList<String>();
            list.add( TypeUtil.toString( (int) MAX_WEIGHTCLASS ) );

            errors.add( new ErrorElement( FIELD_START_WEIGHT, NEWA_WEIGHTCLASS_MORE_THAN_MAX_WEIGHT, list ) );
        }

        if ( newaclass.getStartWeight() >= newaclass.getWeightLimit() )
        {
            List<String> list = new ArrayList<String>();
            list.add( FIELD_START_WEIGHT );
            list.add( FIELD_WEIGHT_LIMIT );

            errors.add( new ErrorElement( list, NEWA_WEIGHTCLASS_LIMIT_IS_UNDER_OR_STARTWEIGHT ) );
        }

        if ( newaclass.getWeightclass().length() > MAX_LENGHT_WEIGHTCLASS_DESCRIPTION )
        {
            List<String> list = new ArrayList<String>();
            list.add( TypeUtil.toString( MAX_LENGHT_WEIGHTCLASS_DESCRIPTION ) );

            errors.add( new ErrorElement( FIELD_WEIGHTCLASS, NEWA_WEIGHTCLASS_DESCRIPTION_TO_LONG, list ) );
        }

        if ( errors.size() > 0 )
        {
            return false;
        }

        return isValid;
    }

    public static boolean isBusinessLogicValid( List<NewaclassWeb> newaclasses, Newaclass newaclass,
                                                List<ErrorElement> errors )
    {
        boolean isValid = true;
        for ( Newaclass oneclass : newaclasses )
        {
            if ( newaclass.getAge().getAgeShort().equals( oneclass.getAge().getAgeShort() )
                && newaclass.getSex().equals( oneclass.getSex() ) )
            {

                if (  // 1. liegt die untere Grenze in einer anderen Def,
                ( oneclass.getStartWeight() < newaclass.getStartWeight() && oneclass.getWeightLimit() > newaclass.getStartWeight() )
                    ||
                        // 2. liegt die obere Grenze in einer anderen Def
                    ( oneclass.getStartWeight() < newaclass.getWeightLimit() && oneclass.getWeightLimit() > newaclass.getWeightLimit() )
                    ||
                        //3. liegt zwischen den beiden Grenzen eine oder mehere Defs.
                    ( newaclass.getStartWeight() > oneclass.getStartWeight() && newaclass.getWeightLimit() < oneclass.getWeightLimit() )
                    ||
                        // 4. die neu darf nicht identisch mit einer vorhandenen sein
                    ( newaclass.getStartWeight() == oneclass.getStartWeight() || newaclass.getWeightLimit() == oneclass.getWeightLimit() ) )
                {
                    isValid = false;
                    List<String> list = new ArrayList<String>();
                    list.add( FIELD_START_WEIGHT );
                    list.add( FIELD_WEIGHT_LIMIT );

                    errors.add( new ErrorElement( list, NEWA_WEIGHTCLASS_COLLISION_WITH_OTHER_WEIGHTCLASS ) );
                    break;
                }

                if ( oneclass.getWeightclass().equals( newaclass.getWeightclass() ) )
                {
                    errors.add( new ErrorElement( FIELD_WEIGHTCLASS, NEWA_WEIGHTCLASS_RENAME_WEIGHTCLASS ) );
                    isValid = false;
                    break;
                }
            }
        }

        return isValid;
    }
}
