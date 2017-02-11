/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightsystemDaoHibernate.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:46
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

import de.jjw.dao.admin.FightsystemDao;
import de.jjw.dao.hibernate.BaseDaoHibernate;
import de.jjw.model.admin.Fightsystem;

import java.util.List;

public class FightsystemDaoHibernate
    extends BaseDaoHibernate
    implements FightsystemDao
{

    private static String ALL_FIGHTSYSTEMS = "from Fightsystem";

    public Fightsystem getFightsystem( Long fightsystemId )
    {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Fightsystem> getAllFightsystems()
    {
        try
        {
            return (List<Fightsystem>) getHibernateTemplate().find( ALL_FIGHTSYSTEMS );
        }
        catch ( Exception e )
        {

        }
        return null;
    }

    public void saveFightsystem( Fightsystem fightsystem )
    {
        // TODO Auto-generated method stub

    }

    public void removeFightsystem( Fightsystem fightsystem )
    {
        // TODO Auto-generated method stub

    }

    public void removeFightsystem( Long fightsystemId )
    {
        // TODO Auto-generated method stub

    }

    public void removeAllFightsystems()
    {
        List<Fightsystem> list = getAllFightsystems();
        if ( list != null && list.size() > 0 )
        {
            getHibernateTemplate().deleteAll( list );
        }

    }

    public void saveFightsystems( List<Fightsystem> fightsystemList )
    {
        try
        {
            for ( Fightsystem fightsystem : fightsystemList )
            {
                getHibernateTemplate().saveOrUpdate( fightsystem );
            }
        }
        catch ( Exception e )
        {
            e.getMessage();
        }

    }

}
