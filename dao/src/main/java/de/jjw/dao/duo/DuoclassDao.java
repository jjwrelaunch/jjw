/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoclassDao.java
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

package de.jjw.dao.duo;

import java.util.List;
import java.util.Map;

import de.jjw.dao.Dao;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.admin.Age;
import de.jjw.model.duo.DuoCertification;
import de.jjw.model.duo.DuoDoublePoolClass;
import de.jjw.model.duo.DuoKoClass;
import de.jjw.model.duo.DuoSimplePoolClass;
import de.jjw.model.duo.Duoclass;
import de.jjw.util.dto.SortDTO;

public interface DuoclassDao
    extends Dao
{

    public Duoclass getDuoclass( Long id )
        throws JJWDataLayerException;

    public List<Duoclass> getAllDuoclasses()
        throws JJWDataLayerException;

    public List<Duoclass> getDuoclassByAge( Age age );

    public void saveDuoclass( Duoclass duoclass )
        throws JJWDataLayerException;

    public Duoclass getDuoclassByAgeSex( Long ageId, String sex )
        throws JJWDataLayerException;

    public DuoSimplePoolClass getDuoclassSimplePool( Long duoclassId )
        throws JJWDataLayerException;

    public DuoDoublePoolClass getDuoclassDoublePool( Long duoclassId )
        throws JJWDataLayerException;

    public DuoKoClass getDuoclassKo( Long duoclassId )
        throws JJWDataLayerException;

    public void removeUnusedDuoclasses()
        throws JJWDataLayerException;

    public void removeUnusedDuoclass( long duoclass_id )
        throws JJWDataLayerException;

    public List<Duoclass> getDeleteStoppedDuoclasses();

    public void saveDuoclassAfterCreate( Duoclass duoclass )
        throws JJWDataLayerException;

    public List<Duoclass> getInUseDuoclasses( Map<Integer, SortDTO> sortParameter )
        throws JJWDataLayerException;

    public void setDuoclassResults( Map<Long, Integer> resultMap, Long duoclassId )
        throws JJWDataLayerException;

    public void resetDuoclassResults( Long duoclassId )
        throws JJWDataLayerException;

    public int getFightsystemOfDuoclass( long duoclassId )
        throws JJWDataLayerException;

    public DuoCertification getDuoCertification( Long duoclassId )
        throws JJWDataLayerException;

    public void changeDuoTeamsInPool( int duoTeam1, int duoteam2, Duoclass duoclass )
        throws JJWDataLayerException;

    public void changeDuoTeamsInDPool( int duoTeam1, int duoTeam2, Duoclass duoclass )
        throws JJWDataLayerException;

    public void changeDuoTeamsInKo( int duoTeam1, int duoTeam2, Duoclass duoclass )
        throws JJWDataLayerException;

    public int getNumberOfDuoTeamsInDuoclass( long duoclassId )
        throws JJWDataLayerException;

    public void removeDuoclass( Duoclass duoclass )
        throws JJWDataLayerException;

    public boolean isDuoclassInUse( Long duoclassId );

    public boolean isDuoclassInUse( Duoclass duoclassId );

    public List<Duoclass> getInUseDuoclassesForTatami( Map<Integer, SortDTO> sortParameter, Long accountId )
        throws JJWDataLayerException;

    public List<Duoclass> getChildDuoclasses( Long duoclassId )
        throws JJWDataLayerException;

    public List<Duoclass> getCombinableDuoclasses( Long duoclassId )
        throws JJWDataLayerException;

    public void addDuoclassToClass( long parentClass, long childClass )
        throws JJWDataLayerException;

    public void removeDuoclassFromParent( long parentClass, long childClass )
        throws JJWDataLayerException;

    public boolean isDuoclassChildOrParent( Long duoclassId )
        throws JJWDataLayerException;

    public Map<Long, Long> getTimesOfCurrentTatamiUse()
        throws JJWDataLayerException;
}
