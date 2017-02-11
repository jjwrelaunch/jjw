/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : CountryMapper.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:43
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

package de.jjw.service.mapper.admin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import de.jjw.model.admin.Country;

public class CountryMapper
{

    public static List<Country> mapCountryListFromDB( List<Country> countries )
    {

        if ( countries == null )
        {
            return null;
        }
        List<Country> ret = new ArrayList<Country>( countries.size() + 1 );

        for ( Country country : countries )
        {
            ret.add( mapCountryFromDB( country ) );
        }

        return ret;
    }

    /**
     * maps a Country from DB and get a new Country object
     *
     * @param country
     * @return
     */
    public static Country mapCountryFromDB( Country country )
    {
        if ( country == null )
        {
            return null;
        }
        Country newCountry = new Country();

        newCountry.setCountryShort( new String( country.getCountryShort() ) );
        newCountry.setCreateDate( new Timestamp( country.getCreateDate().getTime() ) );
        newCountry.setCreateId( Long.valueOf( country.getCreateId() ) );
        newCountry.setDescription( new String( country.getDescription() ) );
        newCountry.setId( Long.valueOf( country.getId() ) );
        newCountry.setModificationDate( new Timestamp( country.getModificationDate().getTime() ) );
        newCountry.setModificationId( Long.valueOf( country.getModificationId() ) );
        return newCountry;
    }

    public static void mapCountryToDB( Country country, Country countryDB )
    {
        countryDB.setCountryShort( new String( country.getCountryShort() ) );
        countryDB.setDescription( new String( country.getDescription() ) );
        countryDB.setModificationDate( new Timestamp( country.getModificationDate().getTime() ) );
        countryDB.setModificationId( Long.valueOf( country.getModificationId() ) );
    }

}
