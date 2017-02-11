/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoclassValidator.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:48
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

package de.jjw.webapp.action.validation.duo;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import de.jjw.model.duo.Duoclass;
import de.jjw.service.modelWeb.DuoclassWeb;
import de.jjw.util.ICodestableConstants;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.constants.admin.IDuoclassConstants;

public class DuoclassValidator
    implements IDuoclassConstants, IValueConstants, ICodestableConstants
{

    public static boolean isRequiredFieldsValid( Duoclass duoclass, Vector<ErrorElement> errors )
    {
        boolean isValid = true;

        if ( TypeUtil.isEmptyOrDefault( duoclass.getSex() ) )
        {
            errors.add( new ErrorElement( FIELD_SEX, GEN_NO_VALUE ) );
        }
        if ( TypeUtil.isEmptyOrDefault( duoclass.getAge().getId() ) )
        {
            errors.add( new ErrorElement( FIELD_AGE, GEN_NO_VALUE ) );
        }
        if ( TypeUtil.isEmptyOrDefault( duoclass.getDuoclassName() ) )
        {
            errors.add( new ErrorElement( FIELD_DUOCLASS_NAME, GEN_NO_VALUE ) );
        }

        if ( duoclass.getDuoclassName().length() > MAX_LENGHT_DUOCLASS_DESCRIPTION )
        {
            List<String> list = new ArrayList<String>();
            list.add( TypeUtil.toString( MAX_LENGHT_DUOCLASS_DESCRIPTION ) );

            errors.add( new ErrorElement( FIELD_DUOCLASS_NAME, DUO_DUOCLASS_DESCRIPTION_TO_LONG, list ) );
        }

        if ( errors.size() > 0 )
        {
            return false;
        }

        return isValid;
    }

    public static boolean isBusinessLogicValid( List<DuoclassWeb> duoclasses, Duoclass duoclass,
                                                List<ErrorElement> errors )
    {
        boolean isValid = true;

        for ( Duoclass oneclass : duoclasses )
        {
            if ( duoclass.getId().longValue() != oneclass.getId().longValue() &&
                duoclass.getAge().getAgeShort().equals( oneclass.getAge().getAgeShort() ) &&
                duoclass.getSex().equals( oneclass.getSex() ) )
            {
                errors.add( new ErrorElement( DUO_DUOCLASS_COLLISION_WITH_OTHER_WEIGHTCLASS ) );
                isValid = false;
                break;
            }
            if ( duoclass.getId().longValue() != oneclass.getId().longValue() &&
                duoclass.getDuoclassName().equals( oneclass.getDuoclassName() ) )
            {
                errors.add( new ErrorElement( DUO_DUOCLASS_COLLISION_WITH_OTHER_WEIGHTCLASS ) );
                isValid = false;
                break;
            }
        }

        return isValid;
    }
}
