/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : BaseDaoHibernate.java
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

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import de.jjw.dao.Dao;

/**
 * This class serves as the Base class for all other Daos - namely to hold common methods that they might all use. Can
 * be used for standard CRUD operations.</p>
 * <p/>
 * <p>
 * <a href="BaseDaoHibernate.java.html"><i>View Source</i></a>
 * </p>
 */
public class BaseDaoHibernate
    extends HibernateDaoSupport
    implements Dao, ISqlDatabaseConstants
{
    protected final Logger log = Logger.getRootLogger();

    /**
     * @see de.jjw.dao.Dao#saveObject(java.lang.Object)
     */
    public void saveObject( Object o )
    {
        getHibernateTemplate().saveOrUpdate( o );
    }

    /**
     * @see de.jjw.dao.Dao#getObject(java.lang.Class, java.io.Serializable)
     */
    public Object getObject( Class clazz, Serializable id )
    {
        Object o = getHibernateTemplate().get( clazz, id );

        if ( o == null )
        {
            throw new ObjectRetrievalFailureException( clazz, id );
        }

        return o;
    }

    /**
     * @see de.jjw.dao.Dao#getObjects(java.lang.Class)
     */
    public List getObjects( Class clazz )
    {
        return getHibernateTemplate().loadAll( clazz );
    }

    /**
     * @see de.jjw.dao.Dao#removeObject(java.lang.Class, java.io.Serializable)
     */
    public void removeObject( Class clazz, Serializable id )
    {
        getHibernateTemplate().delete( getObject( clazz, id ) );
    }
}
