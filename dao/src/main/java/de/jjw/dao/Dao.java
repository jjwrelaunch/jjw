/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : Dao.java
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

import java.io.Serializable;
import java.util.List;

/**
 * Data Access Object (Dao) interface. This is an interface used to tag our Dao classes and to provide common methods to
 * all Daos.
 * <p/>
 * <p>
 * <a href="Dao.java.html"><i>View Source</i></a>
 * </p>
 */
public interface Dao
{

    /**
     * Generic method used to get all objects of a particular type. This is the same as lookup up all rows in a table.
     * 
     * @param clazz the type of objects (a.k.a. while table) to get data from
     * @return List of populated objects
     */
    public List getObjects( Class clazz );

    /**
     * Generic method to get an object based on class and identifier. An ObjectRetrievalFailureException Runtime
     * Exception is thrown if nothing is found.
     * 
     * @param clazz model class to lookup
     * @param id the identifier (primary key) of the class
     * @return a populated object
     * @see org.springframework.orm.ObjectRetrievalFailureException
     */
    public Object getObject( Class clazz, Serializable id );

    /**
     * Generic method to save an object - handles both update and insert.
     * 
     * @param o the object to save
     */
    public void saveObject( Object o );

    /**
     * Generic method to delete an object based on class and id
     * 
     * @param clazz model class to lookup
     * @param id the identifier (primary key) of the class
     */
    public void removeObject( Class clazz, Serializable id );
}
