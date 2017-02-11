/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : WeightclassDao.java
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

package de.jjw.dao.fighting;

import java.util.List;

import de.jjw.dao.Dao;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.admin.Age;
import de.jjw.model.fighting.Fightingclass;

public interface WeightclassDao
    extends Dao
{

    public Fightingclass getWeightclass( Long id );

    public List<Fightingclass> getAllWeightclasses();

    public List<Fightingclass> getWeightclassByAgeNameShort( String ageNameShort );

    public List<Fightingclass> getWeightclassByAge( Age age );

    public void saveWeightclass( Fightingclass fightingclass );

    public void removeWeightclass( Long fightingclassId );

    public void removeWeightclass( Fightingclass fightingclass );

    public Fightingclass getWeightclassByAgeSexWeight( Long ageId, String sex, Double weight )
        throws JJWDataLayerException;

    public boolean isWeightclassInUse( Long fightingclassId );

    public boolean isWeightclassInUse( Fightingclass fightingclassId );

    public boolean isWeightclassChildOrParent( Long fightingclassId )
        throws JJWDataLayerException;
}
