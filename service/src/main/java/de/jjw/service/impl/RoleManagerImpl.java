/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : RoleManagerImpl.java
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

package de.jjw.service.impl;

import de.jjw.dao.RoleDao;
import de.jjw.model.Role;
import de.jjw.service.RoleManager;

import java.util.List;

/**
 * Implementation of RoleManager interface.</p>
 * <p/>
 * <p><a href="RoleManagerImpl.java.html"><i>View Source</i></a></p>
 */
public class RoleManagerImpl
    extends BaseManager
    implements RoleManager
{
    private RoleDao dao;

    public void setRoleDao( RoleDao dao )
    {
        this.dao = dao;
    }

    public List getRoles( Role role )
    {
        return dao.getRoles( role );
    }

    public Role getRole( String rolename )
    {
        return dao.getRoleByName( rolename );
    }

    public void saveRole( Role role )
    {
        dao.saveRole( role );
    }

    public void removeRole( String rolename )
    {
        dao.removeRole( rolename );
    }
}
