/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : WeightclassValidator.java
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

package de.jjw.webapp.action.validation.fighting;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import de.jjw.model.fighting.Fightingclass;
import de.jjw.service.modelWeb.FightingclassWeb;
import de.jjw.util.ICodestableConstants;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.constants.admin.IWeightclassConstants;

public class WeightclassValidator
    implements IWeightclassConstants, IValueConstants, ICodestableConstants
{

    public static boolean isRequiredFieldsValid( Fightingclass fightingclass, Vector<ErrorElement> errors )
    {
        boolean isValid = true;

        if ( TypeUtil.isEmptyOrDefault( fightingclass.getWeightLimit() ) )
        {
            errors.add( new ErrorElement( FIELD_WEIGHT_LIMIT, GEN_NO_VALUE ) );
        }
        if ( TypeUtil.isEmptyOrDefault( fightingclass.getStartWeight() ) )
        {
            errors.add( new ErrorElement( FIELD_START_WEIGHT, GEN_NO_VALUE ) );
        }

        if ( TypeUtil.isEmptyOrDefault( fightingclass.getWeightclass() ) )
        {
            errors.add( new ErrorElement( FIELD_WEIGHTCLASS, GEN_NO_VALUE ) );
        }
        if ( TypeUtil.isEmptyOrDefault( fightingclass.getSex() ) )
        {
            errors.add( new ErrorElement( FIELD_SEX, GEN_NO_VALUE ) );
        }
        if ( TypeUtil.isEmptyOrDefault( fightingclass.getAge().getId() ) )
        {
            errors.add( new ErrorElement( FIELD_AGE, GEN_NO_VALUE ) );
        }

        if ( fightingclass.getStartWeight() < DOUBLE_DEFAULT )
        {
            errors.add( new ErrorElement( FIELD_START_WEIGHT, GEN_VALUE_NOT_NEGATIV ) );
        }
        if ( fightingclass.getWeightLimit() < DOUBLE_DEFAULT )
        {
            errors.add( new ErrorElement( FIELD_WEIGHT_LIMIT, GEN_VALUE_NOT_NEGATIV ) );
        }

        if ( errors.size() > 0 )
        {
            return false;
        }

        if ( fightingclass.getStartWeight() > MAX_WEIGHTCLASS )
        {
            List<String> list = new ArrayList<String>();
            list.add( TypeUtil.toString( (int) MAX_WEIGHTCLASS ) );

            errors.add( new ErrorElement( FIELD_START_WEIGHT, FIGHTING_WEIGHTCLASS_MORE_THAN_MAX_WEIGHT, list ) );
        }

        if ( fightingclass.getWeightLimit() > MAX_WEIGHTCLASS )
        {
            List<String> list = new ArrayList<String>();
            list.add( TypeUtil.toString( (int) MAX_WEIGHTCLASS ) );

            errors.add( new ErrorElement( FIELD_START_WEIGHT, FIGHTING_WEIGHTCLASS_MORE_THAN_MAX_WEIGHT, list ) );
        }

        if ( fightingclass.getStartWeight() >= fightingclass.getWeightLimit() )
        {
            List<String> list = new ArrayList<String>();
            list.add( FIELD_START_WEIGHT );
            list.add( FIELD_WEIGHT_LIMIT );

            errors.add( new ErrorElement( list, FIGHTING_WEIGHTCLASS_LIMIT_IS_UNDER_OR_STARTWEIGHT ) );
        }

        if ( fightingclass.getWeightclass().length() > MAX_LENGHT_WEIGHTCLASS_DESCRIPTION )
        {
            List<String> list = new ArrayList<String>();
            list.add( TypeUtil.toString( MAX_LENGHT_WEIGHTCLASS_DESCRIPTION ) );

            errors.add( new ErrorElement( FIELD_WEIGHTCLASS, FIGHTING_WEIGHTCLASS_DESCRIPTION_TO_LONG, list ) );
        }

        if ( errors.size() > 0 )
        {
            return false;
        }

        return isValid;
    }

    public static boolean isBusinessLogicValid( List<FightingclassWeb> fightingclasses, Fightingclass fightingclass,
                                                List<ErrorElement> errors )
    {
        boolean isValid = true;
        for ( Fightingclass oneclass : fightingclasses )
        {
            if ( fightingclass.getAge().getAgeShort().equals( oneclass.getAge().getAgeShort() ) &&
                fightingclass.getSex().equals( oneclass.getSex() ) )
            {

                if (  // 1. liegt die untere Grenze in einer anderen Def,
                    ( oneclass.getStartWeight() < fightingclass.getStartWeight() &&
                        oneclass.getWeightLimit() > fightingclass.getStartWeight() ) ||
                        // 2. liegt die obere Grenze in einer anderen Def
                        ( oneclass.getStartWeight() < fightingclass.getWeightLimit() &&
                            oneclass.getWeightLimit() > fightingclass.getWeightLimit() ) ||
                        //3. liegt zwischen den beiden Grenzen eine oder mehere Defs.
                        ( fightingclass.getStartWeight() > oneclass.getStartWeight() &&
                            fightingclass.getWeightLimit() < oneclass.getWeightLimit() ) ||
                        // 4. die neu darf nicht identisch mit einer vorhandenen sein
                        ( fightingclass.getStartWeight() == oneclass.getStartWeight() ||
                            fightingclass.getWeightLimit() == oneclass.getWeightLimit() ) )
                {
                    isValid = false;
                    List<String> list = new ArrayList<String>();
                    list.add( FIELD_START_WEIGHT );
                    list.add( FIELD_WEIGHT_LIMIT );

                    errors.add( new ErrorElement( list, FIGHTING_WEIGHTCLASS_COLLISION_WITH_OTHER_WEIGHTCLASS ) );
                    break;
                }

                if ( oneclass.getWeightclass().equals( fightingclass.getWeightclass() ) )
                {
                    errors.add( new ErrorElement( FIELD_WEIGHTCLASS, FIGHTING_WEIGHTCLASS_RENAME_WEIGHTCLASS ) );
                    isValid = false;
                    break;
                }
            }
        }

        return isValid;
    }
}
