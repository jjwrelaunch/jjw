/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : RoleDao.java
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

package de.jjw.dao;

import java.util.List;

import de.jjw.model.Role;

/**
 * Role Data Access Object (DAO) interface.
 * <p/>
 * <p>
 * <a href="RoleDao.java.html"><i>View Source</i></a>
 * </p>
 */
public interface RoleDao
    extends Dao
{
    /**
     * Gets role information based on rolename
     * 
     * @param rolename the rolename
     * @return role populated role object
     */
    public Role getRoleByName( String rolename );

    /**
     * Gets a list of roles based on parameters passed in.
     * 
     * @return List populated list of roles
     */
    public List getRoles( Role role );

    /**
     * Saves a role's information
     * 
     * @param role the object to be saved
     */
    public void saveRole( Role role );

    /**
     * Removes a role from the database by name
     * 
     * @param rolename the role's rolename
     */
    public void removeRole( String rolename );
}
