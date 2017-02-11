/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaFightDao.java
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
import de.jjw.model.FastFight;
import de.jjw.model.admin.FightTimes;
import de.jjw.model.newa.NewaFight;
import de.jjw.model.newa.NewaFighter;
import de.jjw.model.newa.Newaclass;

public interface NewaFightDao
    extends Dao
{

    public List<NewaFight> getFightsFromFighter( NewaFighter fighter );

    public List<NewaFight> getAllFightsForTheClass( Newaclass newaclass );

    public NewaFight getFight( Long fightId )
        throws JJWDataLayerException;
    
    
    public NewaFight getFightByQuery( Long fightId )
                    throws JJWDataLayerException;

    public void removeFight( NewaFight fight );

    public void removeFight( Long fightId );

    public void removeAllFightsForTheClass( Newaclass newaclass );

    public void saveFight( NewaFight fight );

    public boolean existFight( Long fightId );

    public List<FightTimes> getFightTimes()
        throws JJWDataLayerException;

    public Map<Integer, List<FastFight>> getFastestFights()
        throws JJWDataLayerException;
    
    public boolean isDoneFightRegardlessRedBlue( Long newaclassId, Long fighterIdRed, Long fighterIdBlue )
                    throws JJWDataLayerException;
}
