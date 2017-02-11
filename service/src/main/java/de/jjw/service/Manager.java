/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : Manager.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:45
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

package de.jjw.service;

import de.jjw.dao.Dao;

import java.io.Serializable;
import java.util.List;

public interface Manager
{

    /**
     * Expose the setDao method for testing purposes
     *
     * @param dao
     */
    public void setDao( Dao dao );

    /**
     * Generic method used to get a all objects of a particular type.
     *
     * @param clazz the type of objects
     * @return List of populated objects
     */
    public List getObjects( Class clazz );

    /**
     * Generic method to get an object based on class and identifier.
     *
     * @param clazz model class to lookup
     * @param id    the identifier (primary key) of the class
     * @return a populated object
     * @see org.springframework.orm.ObjectRetrievalFailureException
     */
    public Object getObject( Class clazz, Serializable id );

    /**
     * Generic method to save an object.
     *
     * @param o the object to save
     */
    public void saveObject( Object o );

    /**
     * Generic method to delete an object based on class and id
     *
     * @param clazz model class to lookup
     * @param id    the identifier of the class
     */
    public void removeObject( Class clazz, Serializable id );
}
