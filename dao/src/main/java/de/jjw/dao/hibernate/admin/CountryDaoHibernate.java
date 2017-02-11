/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : CountryDaoHibernate.java
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

package de.jjw.dao.hibernate.admin;

import java.util.List;

import org.hibernate.Query;

import de.jjw.dao.admin.CountryDao;
import de.jjw.dao.hibernate.BaseDaoHibernate;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.dao.hibernate.fighting.IFighterDatabaseConstants;
import de.jjw.model.admin.Country;

public class CountryDaoHibernate
    extends BaseDaoHibernate
    implements CountryDao, ICountryDatabaseConstants, IFighterDatabaseConstants
{

    private static String COUNTRY_ALL = "from " + TABLE_COUNTRY + " c order by c." + DESCRIPTION;

    private static String COUNTRY_IN_FIGHTER_USE =
        "select f." + ID + " from " + TABLE_FIGHTER + " f where f." + COUNTRY + "= ?";

    private static String COUNTRY_IN_USE_BY_TEAM = "from Team t where t.country = ?";

    private static String COUNTRY_IN_USE_BY_REGION = "from Region r where r.country = ?";

    public Country getCountry( Long countryId )
    {
        try
        {
            return (Country) getHibernateTemplate().get( Country.class, countryId );
        }
        catch ( Exception e )
        {

        }
        return null;
    }

    public List<Country> getAllCountries()
    {
        try
        {
            return (List<Country>) getHibernateTemplate().find( COUNTRY_ALL );
        }
        catch ( Exception e )
        {

        }
        return null;
    }

    public void saveCountry( Country country )
    {
        getHibernateTemplate().saveOrUpdate( country );

    }

    private static String DELETE_COUNTRY_SQL = "delete from " + SQL_TABLE_COUNTRY + "  where id=?";

    public void removeCountry( Country country )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createSQLQuery( DELETE_COUNTRY_SQL );
            q.setLong( 0, country.getId() );
            q.executeUpdate();
        }
        catch ( Exception e )
        {
            log.error( "can not removeFighter", e );
            throw new JJWDataLayerException( e );
        }
    }

    public void removeCountry( Long countryId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createSQLQuery( DELETE_COUNTRY_SQL );
            q.setLong( 0, countryId );
            q.executeUpdate();
        }
        catch ( Exception e )
        {
            log.error( "can not removeFighter", e );
            throw new JJWDataLayerException( e );
        }
    }

    public boolean isCountryInUse( Long countryId )
    {
        Query q = getSession().createQuery( COUNTRY_IN_USE_BY_REGION );
        q.setLong( 0, countryId );
        if ( q.list().isEmpty() )
        {
            q = getSession().createQuery( COUNTRY_IN_USE_BY_TEAM );
            q.setLong( 0, countryId );
            return !q.list().isEmpty();
        }
        else
        {
            return true;
        }
    }

    public boolean isCountryInUse( Country country )
    {
        return isCountryInUse( country.getId() );

    }

}
