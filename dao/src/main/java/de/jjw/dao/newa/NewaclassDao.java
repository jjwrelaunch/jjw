/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaclassDao.java
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

package de.jjw.dao.newa;

import java.util.List;
import java.util.Map;

import de.jjw.dao.Dao;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.admin.Age;
import de.jjw.model.newa.NewaCertification;
import de.jjw.model.newa.NewaDoublePoolClass;
import de.jjw.model.newa.NewaKoClass;
import de.jjw.model.newa.NewaSimplePoolClass;
import de.jjw.model.newa.Newaclass;
import de.jjw.util.dto.SortDTO;

public interface NewaclassDao
    extends Dao
{


    public void removeNewaclasses();

    public void removeUnusedNewaclasses()
        throws Exception;

    public void removeUnusedNewaclass( long newaclass_id )
        throws JJWDataLayerException;

    public void removeNewaclass( Newaclass newaclass );

    public void saveNewaclass( Newaclass newaclass )
        throws JJWDataLayerException;

    public void saveNewaclassAfterCreate( Newaclass newaclass )
        throws JJWDataLayerException;

    public boolean existFightingClass( Newaclass newaclass );

    public List<Newaclass> getDeleteStoppedNewaclasses();

    /**
     * get all newaclasses, that are in Use all parameter in the list, which correspond to a column will be use for
     * order by. Beginning with die first element (0) by Integer and than the second ....
     * <p/>
     * e.g. (0,1, ..
     * 
     * @return Newaclasses
     */
    public List<Newaclass> getInUseNewaclasses( Map<Integer, SortDTO> sortParameter )
        throws JJWDataLayerException;

    public Newaclass getNewaclass( Newaclass newaclass );

    public Newaclass getNewaclass( Long newaclassId )
        throws JJWDataLayerException;

    public NewaSimplePoolClass getNewaclassSimplePool( Long newaclassId )
        throws JJWDataLayerException;

    public NewaDoublePoolClass getNewaclassDoublePool( Long newaclassId )
        throws JJWDataLayerException;

    public int getFightsystemOfNewaclass( long newaclassId )
        throws JJWDataLayerException;

    public NewaKoClass getNewaclassKo( Long newaclassId )
        throws JJWDataLayerException;

    public void setNewaclassResults( Map<Long, Integer> resultMap, Long newaclassId )
        throws JJWDataLayerException;

    public void resetNewaclassResults( Long newaclassId )
        throws JJWDataLayerException;

    public NewaCertification getNewaCertification( Long newaclassId )
        throws JJWDataLayerException;

    public void changeFighterInPool( int fighter1, int fighter2, Newaclass newaclass )
        throws JJWDataLayerException;

    public void changeFighterInDPool( int fighter1, int fighter2, Newaclass newaclass )
        throws JJWDataLayerException;

    public void changeFighterInKo( int fighter1, int fighter2, Newaclass newaclass )
        throws JJWDataLayerException;

    public int getNumberOfFighterInNewaclass( long newaclassId )
        throws JJWDataLayerException;

    public List<Newaclass> getInUseNewaclassesForTatami( Map<Integer, SortDTO> sortParameter, Long accountId )
        throws JJWDataLayerException;

    public void createAutomaticNewaclasses( Long ageId, Long userId )
        throws JJWDataLayerException;

    public void sortFighterIntoClasses( Long userId )
        throws JJWDataLayerException;

    public List<Age> getAgesForAutomaticNewaclassCreation()
        throws JJWDataLayerException;
    
    public List<Newaclass> getChildNewaclasses( Long newaclassId )
    	throws JJWDataLayerException;
    
    public List<Newaclass> getCombinableNewaclasses( Long newaclassId )
    	throws JJWDataLayerException;
    
    public void addNewaclassToClass( long parentClass, long childClass )
		throws JJWDataLayerException;

    public void removeNewaclassFromParent( long parentClass, long childClass )
		throws JJWDataLayerException;

    public Map<Long, Long> getTimesOfCurrentTatamiUse()
        throws JJWDataLayerException;
}
