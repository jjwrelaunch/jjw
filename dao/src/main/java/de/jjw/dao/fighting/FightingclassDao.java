/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightingclassDao.java
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

package de.jjw.dao.fighting;

import java.util.List;
import java.util.Map;

import de.jjw.dao.Dao;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.EstimatedTimes;
import de.jjw.model.admin.Age;
import de.jjw.model.fighting.FightingCertification;
import de.jjw.model.fighting.FightingDoublePoolClass;
import de.jjw.model.fighting.FightingKoClass;
import de.jjw.model.fighting.FightingSimplePoolClass;
import de.jjw.model.fighting.Fightingclass;
import de.jjw.util.dto.SortDTO;

public interface FightingclassDao
    extends Dao
{


    public void removeFightingclasses();

    public void removeUnusedFightingclasses()
        throws Exception;

    public void removeUnusedFightingclass( long fightingclass_id )
        throws JJWDataLayerException;

    public void removeFightingclass( Fightingclass fightingclass );

    public void saveFightingclass( Fightingclass fightingclass )
        throws JJWDataLayerException;

    public void saveFightingclassAfterCreate( Fightingclass fightingclass )
        throws JJWDataLayerException;

    public boolean existFightingClass( Fightingclass fightingclass );

    public List<Fightingclass> getDeleteStoppedFightingclasses();

    /**
     * get all fightingclasses, that are in Use
     * all parameter in the list, which correspond to a column
     * will be use for order by.
     * Beginning with die first element (0) by Integer and than the second ....
     * <p/>
     * e.g. (0,1, ..
     *
     * @return Fightingclasses
     */
    public List<Fightingclass> getInUseFightingclasses( Map<Integer, SortDTO> sortParameter )
        throws JJWDataLayerException;

    public Fightingclass getFightingclass( Fightingclass fightingclass );

    public Fightingclass getFightingclass( Long fightingclassId )
        throws JJWDataLayerException;

    public FightingSimplePoolClass getFightingclassSimplePool( Long fightingclassId )
        throws JJWDataLayerException;

    public FightingDoublePoolClass getFightingclassDoublePool( Long fightingclassId )
        throws JJWDataLayerException;

    public int getFightsystemOfFightingclass( long fightingclassId )
        throws JJWDataLayerException;

    public FightingKoClass getFightingclassKo( Long fightingclassId )
        throws JJWDataLayerException;

    public void setFightingclassResults( Map<Long, Integer> resultMap, Long fightingclassId )
        throws JJWDataLayerException;

    public void resetFightingclassResults( Long fightingclassId )
        throws JJWDataLayerException;

    public FightingCertification getFightingCertification( Long fightingclassId )
        throws JJWDataLayerException;

    public void changeFighterInPool( int fighter1, int fighter2, Fightingclass fightingclass )
        throws JJWDataLayerException;

    public void changeFighterInDPool( int fighter1, int fighter2, Fightingclass fightingclass )
        throws JJWDataLayerException;

    public void changeFighterInKo( int fighter1, int fighter2, Fightingclass fightingclass )
        throws JJWDataLayerException;

    public int getNumberOfFighterInFightingclass( long fightingclassId )
        throws JJWDataLayerException;

    public List<Fightingclass> getInUseFightingclassesForTatami( Map<Integer, SortDTO> sortParameter, Long accountId )
        throws JJWDataLayerException;

    public void createAutomaticFightingclasses( Long ageId, Long userId )
        throws JJWDataLayerException;

    public void sortFighterIntoClasses( Long userId )
        throws JJWDataLayerException;

    public List<Age> getAgesForAutomaticFightingclassCreation()
        throws JJWDataLayerException;
    
    public List<Fightingclass> getChildFightingclasses( Long fightingclassId )
    	throws JJWDataLayerException;
    
    public List<Fightingclass> getCombinableFightingclasses( Long fightingclassId)
    	throws JJWDataLayerException;
    
    public void addFightingclassToClass(long parentClass, long childClass)
		throws JJWDataLayerException;

    public void removeFightingclassFromParent(long parentClass, long childClass)
		throws JJWDataLayerException;

    public List<EstimatedTimes> getTimesOfCurrentTatamiUse()
        throws JJWDataLayerException;
}
