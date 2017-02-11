/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : ValidationUtil.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:49
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

package de.jjw.webapp.action.validation;

import de.jjw.util.ICodestableConstants;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;

import java.util.Vector;

public class ValidationUtil
    implements IValueConstants, ICodestableConstants
{

    public static boolean isFieldValid( String field, String fieldName, int minLength, int maxLength, String pattern,
                                        Vector<ErrorElement> errors )
    {
        if ( field == null )
        {
            errors.add( new ErrorElement( fieldName, GEN_NO_VALUE ) );
            return false;
        }

        if ( minLength != INT_EMPTY && field.length() < minLength )
        {
            errors.add( new ErrorElement( fieldName, GEN_VALUE_TO_LESS_CHARACTER ) );
            return false;
        }

        if ( maxLength != INT_EMPTY && field.length() > maxLength )
        {
            errors.add( new ErrorElement( fieldName, GEN_VALUE_TO_MANY_CHARACTER ) );
            return false;
        }
        if ( null != pattern )
        {
            //todo: pattern
        }
        return true;
    }

    public static boolean isMinFieldLenghtValid( String field, int minLength, String pattern )
    {
        return isMinMaxFieldLengthValid( field, minLength, INT_MAX, pattern );
    }

    public static boolean isMaxFieldLengthValid( String field, int maxLength, String pattern )
    {
        return isMinMaxFieldLengthValid( field, LEANGTH_0, maxLength, pattern );
    }


    public static boolean isMinMaxFieldLengthValid( String field, int minLength, int maxLength, String pattern )
    {
        if ( TypeUtil.isEmptyOrDefault( field ) )
        {
            return false;
        }

        if ( minLength != INT_EMPTY && field.length() < minLength )
        {
            return false;
        }

        if ( maxLength != INT_EMPTY && field.length() > maxLength )
        {
            return false;
        }
        //todo: pattern
        return true;
    }

    public static boolean isValidPattern( String field, String pattern )
    {
        return false;
    }
}
