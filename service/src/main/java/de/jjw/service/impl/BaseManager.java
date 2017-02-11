/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : BaseManager.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:43
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

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;

import de.jjw.dao.Dao;
import de.jjw.dao.IDatabaseTableConstants;
import de.jjw.dao.generalhelper.OptimisticLocking;
import de.jjw.service.Manager;

/**
 * Base class for Business Services - use this class for utility methods and
 * generic CRUD methods.
 * <p/>
 * <p><a href="BaseManager.java.html"><i>View Source</i></a></p>
 */
public class BaseManager
    implements Manager, IDatabaseTableConstants
{
    protected final Logger log = Logger.getRootLogger();

    protected Dao dao = null;

    protected OptimisticLocking optimisticDao = null;

    /**
     * @see de.jjw.service.Manager#setDao(de.jjw.dao.Dao)
     */
    public void setDao( Dao dao )
    {
        this.dao = dao;
    }

    /**
     * @see de.jjw.service.Manager#getObject(java.lang.Class, java.io.Serializable)
     */
    public Object getObject( Class clazz, Serializable id )
    {
        return dao.getObject( clazz, id );
    }

    /**
     * @see de.jjw.service.Manager#getObjects(java.lang.Class)
     */
    public List getObjects( Class clazz )
    {
        return dao.getObjects( clazz );
    }

    /**
     * @see de.jjw.service.Manager#removeObject(java.lang.Class, java.io.Serializable)
     */
    public void removeObject( Class clazz, Serializable id )
    {
        dao.removeObject( clazz, id );
    }

    /**
     * @see de.jjw.service.Manager#saveObject(java.lang.Object)
     */
    public void saveObject( Object o )
    {
        dao.saveObject( o );
    }

    /**
     * @return Returns the optimisticDao.
     */
    public OptimisticLocking getOptimisticDao()
    {
        return optimisticDao;
    }

    /**
     * @param optimisticDao The optimisticDao to set.
     */
    public void setOptimisticDao( OptimisticLocking optimiscticDao )
    {
        this.optimisticDao = optimiscticDao;
    }


}
