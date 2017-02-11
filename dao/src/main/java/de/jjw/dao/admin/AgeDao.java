/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : AgeDao.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:47
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

package de.jjw.dao.admin;

import de.jjw.dao.Dao;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.admin.Age;

import java.util.List;


public interface AgeDao
    extends Dao
{

    public Age getAge( Long ageId );

    public List getAllAges();

    public void saveAge( Age age );

    public void removeAge( Age age );

    public void removeAge( Long ageId );

    /**
     * check if there is a fighter or a duo team
     * in the age
     *
     * @param age
     * @return
     */
    public boolean isAgeInUse( Age age );

    /**
     * check if there is a fighter or a duo team
     * in the age
     *
     * @param age
     * @return
     */
    public boolean isAgeInUse( Long ageId );

    public int getMaxOrderNumber();

    public Age getPreviousAge( long ageId )
        throws JJWDataLayerException;
}
