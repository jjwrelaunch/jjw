/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FighterDao.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:49
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
import de.jjw.model.fighting.Fighter;
import de.jjw.model.fighting.Fightingclass;

public interface FighterDao
    extends Dao
{

    public Fighter getFighter( Long fighterId )
        throws JJWDataLayerException;

    public List<Fighter> getAllFighters()
        throws JJWDataLayerException;

    public List<Fighter> getAllFightersInOrder( int order, boolean isAscending )
        throws JJWDataLayerException;

    public List<Fighter> getFilteredFightersInSpezialOrder();

    public List<Fighter> getNotRegistratedFighters();

    public List<Fighter> getRegistratedFighters();

    public List<Fighter> getFilteredNotRegistratedFightersInSpezialOrder();

    public List<Fighter> getFighterByAge( long ageId, boolean notReg )
        throws JJWDataLayerException;

    public List<Fighter> getFighterByRegion( long regionId, boolean notReg )
        throws JJWDataLayerException;

    public List<Fighter> getFighterByCountry( long countryId, boolean notReg )
        throws JJWDataLayerException;

    public List<Fighter> getFighterByTeam( long teamId, boolean notReg )
        throws JJWDataLayerException;

    public List<Fighter> getFighterByFightingclassForDraw( Fightingclass fightingclass );

    public List<Fighter> getFighterByFightingclass( long fightingclassId, boolean notReg )
        throws JJWDataLayerException;

    public void saveFighter( Fighter fighter )
        throws JJWDataLayerException;

    public void removeFighter( Long fighterId )
        throws JJWDataLayerException;

    public void removeFighter( Fighter fighter )
        throws JJWDataLayerException;

    public boolean isFighterInUse( Long fighterId )
        throws JJWDataLayerException;

    /**
     * gets all Fightingclass in which is at least one
     * Fighter registrated
     */
    public List<Fightingclass> getUsedWeightclasses()
        throws JJWDataLayerException;

    /*
    * test  Name, firstname and Team of the given fighter
    *
    */

    public boolean existFighter( Fighter fighter );

    /*
    * test Name and firstname of the given fighter
    */

    public List<Fighter> getPossibleDublicateFighters( Fighter fighter );

    public List<Fighter> getFighterResultList()
        throws JJWDataLayerException;
    
    public List<Fighter> getFightersForJSONExport()
                    throws JJWDataLayerException;
}
