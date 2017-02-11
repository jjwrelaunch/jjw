/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : CountryManagerImpl.java
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

package de.jjw.service.impl.admin;

import java.util.List;

import de.jjw.dao.admin.CountryDao;
import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.admin.Country;
import de.jjw.service.admin.CountryManager;
import de.jjw.service.exception.CountryInUseException;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.impl.BaseManager;
import de.jjw.service.mapper.admin.CountryMapper;

public class CountryManagerImpl
    extends BaseManager
    implements CountryManager
{

    private CountryDao countryDao;

    public void setCountryDao( CountryDao countryDao )
    {
        this.countryDao = countryDao;
    }

    public Country getCountry( Long countryId )
    {
        return CountryMapper.mapCountryFromDB( countryDao.getCountry( countryId ) );
    }

    public List<Country> getAllCountries()
    {
        return CountryMapper.mapCountryListFromDB( countryDao.getAllCountries() );
    }

    public void saveCountry( Country country )
        throws OptimisticLockingException
    {
        if ( null == country.getId() )
        {
            countryDao.saveCountry( country );
        }
        else
        {
            optimisticDao.testLocking( TABLE_COUNTRY, country.getId(), country.getModificationDate() );
            CountryMapper.mapCountryToDB( country, countryDao.getCountry( country.getId() ) );
        }
    }

    public void removeCountry( Country country )
        throws CountryInUseException, JJWManagerException
    {
        if ( countryDao.isCountryInUse( country ) )
        {
            throw new CountryInUseException();
        }
        try
        {
            countryDao.removeCountry( country );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }

    }

    public void removeCountry( Long countryId )
        throws CountryInUseException, JJWManagerException
    {
        if ( countryDao.isCountryInUse( countryId ) )
        {
            throw new CountryInUseException();
        }
        try
        {
            countryDao.removeCountry( countryId );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }

    }

}
