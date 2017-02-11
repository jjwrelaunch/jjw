/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : LookupManagerImpl.java
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

package de.jjw.service.impl;

import java.util.ArrayList;
import java.util.List;

import de.jjw.dao.LookupDao;
import de.jjw.model.LabelValue;
import de.jjw.model.Role;
import de.jjw.service.LookupManager;

/**
 * Implementation of LookupManager interface to talk to the persistence layer.
 * <p/>
 * <p>
 * <a href="LookupManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 */
public class LookupManagerImpl
    extends BaseManager
    implements LookupManager
{
    // ~ Instance fields ========================================================

    private LookupDao dao;

    // ~ Methods ================================================================

    public void setLookupDao( LookupDao dao )
    {
        super.dao = dao;
        this.dao = dao;
    }

    /**
     * @see de.jjw.service.LookupManager#getAllRoles()
     */
    public List getAllRoles()
    {
        List roles = dao.getRoles();
        List list = new ArrayList();
        Role role = null;

        for ( int i = 0; i < roles.size(); i++ )
        {
            role = (Role) roles.get( i );
            list.add( new LabelValue( role.getName(), role.getName() ) );
        }

        return list;
    }
}
