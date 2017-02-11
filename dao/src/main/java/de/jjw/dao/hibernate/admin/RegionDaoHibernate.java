/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : RegionDaoHibernate.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:41
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

import de.jjw.dao.admin.RegionDao;
import de.jjw.dao.hibernate.BaseDaoHibernate;
import de.jjw.model.admin.Region;

public class RegionDaoHibernate
    extends BaseDaoHibernate
    implements RegionDao
{

    private static String REGION_ALL = "from Region r left join fetch r.country c order by c.id, r.description";

    private static String REGIONS_BY_COUNTRY = "from Region r where r.country = ? order by r.description ";

    private static String REGION_IN_TEAM_USE = "from Team f where f.region = ?";

    public Region getRegion( Long regionId )
    {
        try
        {
            return (Region) getHibernateTemplate().get( Region.class, regionId );
        }
        catch ( Exception e )
        {

        }
        return null;
    }

    public List getAllRegions()
    {
        try
        {
            return getHibernateTemplate().find( REGION_ALL );
        }
        catch ( Exception e )
        {

        }
        return null;
    }

    public void saveRegion( Region region )
    {
        getHibernateTemplate().saveOrUpdate( region );
    }

    public void removeRegion( Region region )
    {
        getHibernateTemplate().delete( getRegion( region.getId() ) );
    }

    public void removeRegion( Long regionId )
    {
        getHibernateTemplate().delete( getRegion( regionId ) );

    }

    public boolean isRegionInUse( Long regionId )
    {
        Query q = getSession().createQuery( REGION_IN_TEAM_USE );
        q.setLong( 0, regionId );

        return !q.list().isEmpty();

    }

    public boolean isRegionInUse( Region region )
    {
        return isRegionInUse( region.getId() );
    }

    public List<Region> getRegionsByCountry( Long countryId )
    {
        Query q = getSession().createQuery( REGIONS_BY_COUNTRY );
        q.setLong( 0, countryId );
        return q.list();
    }

}
