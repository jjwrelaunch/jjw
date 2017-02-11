/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FriendlyDuoFightDao.java
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

import de.jjw.dao.Dao;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.duo.DuoTeam;
import de.jjw.model.duo.FriendlyDuoFight;


public interface FriendlyDuoFightDao
    extends Dao
{

    public List<FriendlyDuoFight> getAllFriendlyDuoFights()
        throws JJWDataLayerException;

    public List<FriendlyDuoFight> getFightsFromDuoTeam( DuoTeam duoteam );

    public FriendlyDuoFight getDuoFight( Long fightId )
        throws JJWDataLayerException;

    public void removeDuoFriendlyFight( FriendlyDuoFight fight )
        throws JJWDataLayerException;

    public void removeDuoFriendlyFight( Long fightId )
        throws JJWDataLayerException;

    public void saveFriendlyDuoFight( FriendlyDuoFight fight )
        throws JJWDataLayerException;

    public boolean existDuoFight( Long fightId );

    public boolean existDuoFight( Long fightIdRed, Long fightIdBlue )
        throws JJWDataLayerException;

}
