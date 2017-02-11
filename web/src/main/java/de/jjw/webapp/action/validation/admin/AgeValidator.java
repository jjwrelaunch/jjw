/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : AgeValidator.java
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

package de.jjw.webapp.action.validation.admin;

import java.util.List;
import java.util.Vector;

import de.jjw.model.admin.Age;
import de.jjw.util.ICodestableConstants;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.constants.admin.IAgeConstants;


public class AgeValidator
    implements ICodestableConstants, IAgeConstants
{
    public static boolean isNewAgeValid( List<Age> ageList, Vector<ErrorElement> errors, Age newAge )
    {
        boolean ret = true;

        if ( TypeUtil.isEmptyOrDefault( newAge.getAgeShort() ) )
        {
            errors.add( new ErrorElement( FIELD_AGE_SHORT, GEN_NO_VALUE ) );
        }
        if ( TypeUtil.isEmptyOrDefault( newAge.getDescription() ) )
        {
            errors.add( new ErrorElement( FIELD_DESCRIPTION, GEN_NO_VALUE ) );
        }
        if ( TypeUtil.isEmptyOrDefault( newAge.getFightingTime() ) )
        {
            errors.add( new ErrorElement( FIELD_FIGHTING_TIME, GEN_NO_VALUE ) );
        }
        if ( TypeUtil.INT_DEFAULT > newAge.getFightingTime() )
        {
            errors.add( new ErrorElement( FIELD_FIGHTING_TIME, GEN_VALUE_NOT_NEGATIV ) );
        }

        if ( TypeUtil.INT_DEFAULT > newAge.getFightingTimeNewa() )
        {
            errors.add( new ErrorElement( FIELD_FIGHTING_TIME_NEWA, GEN_VALUE_NOT_NEGATIV ) );
        }
        if ( TypeUtil.isEmptyOrDefault( newAge.getFightingRounds() ) )
        {
            errors.add( new ErrorElement( FIELD_FIGHTING_ROUNDS, GEN_NO_VALUE ) );
        }
        if ( TypeUtil.INT_DEFAULT > newAge.getFightingRounds() )
        {
            errors.add( new ErrorElement( FIELD_FIGHTING_ROUNDS, GEN_VALUE_NOT_NEGATIV ) );
        }
        if ( TypeUtil.isEmptyOrDefault( newAge.getOvertime() ) )
        {
            errors.add( new ErrorElement( FIELD_OVERTIME, GEN_NO_VALUE ) );
        }
        if ( TypeUtil.INT_DEFAULT > newAge.getOvertime() )
        {
            errors.add( new ErrorElement( FIELD_OVERTIME, GEN_VALUE_NOT_NEGATIV ) );
        }

        if ( errors.size() > 0 )
        {
            return false;
        }
        // TODO: testen, damit nur Buchstaben A-Z erlaubt sind (3 Zeichen)

        if ( isAgeExists( ageList, errors, newAge ) )
        {
            return false;
        }

        ret = isAgeDescriptionValid( errors, newAge );

        return ret;
    }

    public static boolean isEditAgeValid( List<Age> ageList, Vector<ErrorElement> errors, Age newAge )
    {
        boolean ret = true;
        // test, damit nur Buchstaben A-Z erlaubt sind (3 Zeichen)

        if ( isAgeExists( ageList, errors, newAge ) )
        {
            return false;
        }

        ret = isAgeDescriptionValid( errors, newAge );
        return ret;
    }

    /**
     * chek, if there is a description for the Age und the description lenght is
     * valid
     *
     * @param errors
     * @param newAge
     * @return
     */
    private static boolean isAgeDescriptionValid( Vector<ErrorElement> errors, Age newAge )
    {
        boolean ret = true;
        if ( TypeUtil.isEmptyOrDefault( newAge.getDescription().trim() ) )
        {
            errors.add( new ErrorElement( FIELD_DESCRIPTION, ADMIN_AGE_NO_DESCRIPTION ) );
            ret = false;
        }
        else
        {
            if ( newAge.getDescription().length() > MAX_LENGTH_DESCRIPTION )
            {
                errors.add( new ErrorElement( FIELD_DESCRIPTION, ADMIN_AGE_DESCRIPTION_TO_LONG ) );
                ret = false;
            }
        }
        return ret;
    }

    /**
     * Check, if there is a age with this id
     *
     * @param ageList
     * @param errors
     * @param newAge
     * @return
     */
    private static boolean isAgeExists( List<Age> ageList, Vector<ErrorElement> errors, Age newAge )
    {
        for ( Age age : ageList )
        {
            if ( age.getAgeShort().equals( newAge.getAgeShort() ) &&
                age.getId().longValue() != newAge.getId().longValue() )
            {
                errors.add( new ErrorElement( FIELD_AGE_SHORT, ADMIN_AGE_EXISTS ) );
                return true;
            }
        }
        return false;
    }

    /**
     * check all the other ages, so that ageStart und AgeLimit is correkt
     *
     * @param ageList
     * @param errors
     * @param newAge
     * @return
     */
    public static boolean isAgeWithoutConflict( List<Age> ageList, Vector<ErrorElement> errors, Age newAge )
    {
        boolean ret = false;
//		for (Age age : ageList) {
//			if (age.getStartAge() <= newAge.getStartAge() && age.getAgeLimit() >= newAge.getStartAge()) {
//				errors.add(new ErrorElement(ADMIN_AGE_CONFICT_WITH_ANOTHER_AGE));
//				return false;
//			}
//			if (age.getStartAge() <= newAge.getAgeLimit() && age.getAgeLimit() >= newAge.getAgeLimit()) {
//				errors.add(new ErrorElement(ADMIN_AGE_CONFICT_WITH_ANOTHER_AGE));
//				return false;
//			}
//		}
        return ret;
    }

}
