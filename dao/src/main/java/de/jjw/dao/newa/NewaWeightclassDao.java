/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaWeightclassDao.java
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

package de.jjw.dao.newa;

import java.util.List;

import de.jjw.dao.Dao;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.admin.Age;
import de.jjw.model.newa.Newaclass;

public interface NewaWeightclassDao
    extends Dao
{

    public Newaclass getWeightclass( Long id );

    public List<Newaclass> getAllWeightclasses();

    public List<Newaclass> getWeightclassByAgeNameShort( String ageNameShort );

    public List<Newaclass> getWeightclassByAge( Age age );

    public void saveWeightclass( Newaclass newaclass );

    public void removeWeightclass( Long newaclassId );

    public void removeWeightclass( Newaclass newaclass );

    public Newaclass getWeightclassByAgeSexWeight( Long ageId, String sex, Double weight )
        throws JJWDataLayerException;

    public boolean isWeightclassInUse( Long newaclassId );

    public boolean isWeightclassInUse( Newaclass newaclassId );

    public boolean isWeightclassChildOrParent( Long newaclassId )
        throws JJWDataLayerException;
}
