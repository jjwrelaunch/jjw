/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : LookupDaoHibernate.java
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

package de.jjw.dao.hibernate;

import de.jjw.dao.LookupDao;

import java.util.List;


/**
 * Hibernate implementation of LookupDao.
 * <p/>
 * <p><a href="LookupDaoHibernate.java.html"><i>View Source</i></a></p>
 *
 * @author
 */
public class LookupDaoHibernate
    extends BaseDaoHibernate
    implements LookupDao
{

    /**
     * @see de.jjw.dao.LookupDao#getRoles()
     */
    public List getRoles()
    {
        if ( log.isDebugEnabled() )
        {
            log.debug( "retrieving all role names..." );
        }

        return getHibernateTemplate().find( "from Role order by name" );
    }
}
