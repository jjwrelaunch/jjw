/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : RoleDaoHibernate.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:44
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

package de.jjw.dao.hibernate;

import java.util.List;

import de.jjw.dao.RoleDao;
import de.jjw.model.Role;

/**
 * This class interacts with Spring's HibernateTemplate to save/delete and retrieve Role objects.
 * <p/>
 * <p>
 * <a href="RoleDaoHibernate.java.html"><i>View Source</i></a>
 * </p>
 */
public class RoleDaoHibernate
    extends BaseDaoHibernate
    implements RoleDao
{

    public List getRoles( Role role )
    {
        return getHibernateTemplate().find( "from Role" );
    }

    public Role getRole( Long roleId )
    {
        return (Role) getHibernateTemplate().get( Role.class, roleId );
    }

    public Role getRoleByName( String rolename )
    {
        List roles = getHibernateTemplate().find( "from Role where name=?", rolename );
        if ( roles.isEmpty() )
        {
            return null;
        }
        else
        {
            return (Role) roles.get( 0 );
        }
    }

    public void saveRole( Role role )
    {
        getHibernateTemplate().saveOrUpdate( role );
    }

    public void removeRole( String rolename )
    {
        Object role = getRoleByName( rolename );
        getHibernateTemplate().delete( role );
    }

}
