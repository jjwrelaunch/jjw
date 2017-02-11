/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : CountryValidator.java
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

package de.jjw.webapp.action.validation.admin;

import de.jjw.model.admin.Country;
import de.jjw.util.ICodestableConstants;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.constants.admin.ICountryConstants;

import java.util.List;
import java.util.Vector;

public class CountryValidator
    implements ICodestableConstants, ICountryConstants
{

    public static boolean isNewCountryValid( List<Country> countryList, Vector<ErrorElement> errors,
                                             Country newCountry )
    {
        boolean ret = true;
        if ( TypeUtil.isEmptyOrDefault( newCountry.getCountryShort().trim() ) )
        {
            errors.add( new ErrorElement( FIELD_COUNTRY_SHORT, GEN_NO_VALUE ) );
            ret = false;
        }
       

        // testen, damit nur Buchstaben A-Z erlaubt sind (3 Zeichen)

        if ( isCountryExists( countryList, errors, newCountry ) )
        {
            return false;
        }

        ret = isCountryDescriptionValid( errors, newCountry );

        return ret;
    }


    public static boolean isEditCountryValid( List<Country> countryList, Vector<ErrorElement> errors,
                                              Country newCountry )
    {
        boolean ret = true;
        
        if ( TypeUtil.isEmptyOrDefault( newCountry.getCountryShort().trim() ) )
        {
            errors.add( new ErrorElement( FIELD_COUNTRY_SHORT, GEN_NO_VALUE ) );
            ret = false;
        }
        // test, damit nur Buchstaben A-Z erlaubt sind (3 Zeichen)

        if ( isCountryExists( countryList, errors, newCountry ) )
        {
            return false;
        }

        ret = isCountryDescriptionValid( errors, newCountry );
        return ret;
    }


    /**
     * chek, if there is a description for the Country und the description
     * lenght is valid
     *
     * @param errors
     * @param newCountry
     * @return
     */
    private static boolean isCountryDescriptionValid( Vector<ErrorElement> errors, Country newCountry )
    {
        boolean ret=true;
        if ( TypeUtil.isEmptyOrDefault( newCountry.getDescription().trim() ) )
        {
            errors.add( new ErrorElement( FIELD_DESCRIPTION, ADMIN_COUNTRY_NO_DESCRIPTION ) );
            ret = false;
        }
        else
        {
            if ( newCountry.getDescription().length() > MAX_LENGTH_DESCRIPTION )
            {
                errors.add( new ErrorElement( FIELD_DESCRIPTION, ADMIN_COUNTRY_DESCRIPTION_TO_LONG ) );
                ret = false;
            }
           
        }
        return ret;
    }

    /**
     * Check, if there is a country with this id
     *
     * @param countryList
     * @param errors
     * @param newCountry
     * @return
     */
    private static boolean isCountryExists( List<Country> countryList, Vector<ErrorElement> errors, Country newCountry )
    {
        for ( Country country : countryList )
        {
            if ( country.getCountryShort().equals( newCountry.getCountryShort() ) )
            {
                errors.add( new ErrorElement( FIELD_COUNTRY_SHORT, ADMIN_COUNTRY_EXISTS ) );
                return true;
            }
        }
        return false;
    }

}
