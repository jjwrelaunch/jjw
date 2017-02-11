/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FriendlyFightDao.java
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

import de.jjw.dao.Dao;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.fighting.Fighter;
import de.jjw.model.fighting.FriendlyFight;


public interface FriendlyFightDao
    extends Dao
{

    public List<FriendlyFight> getAllFriendlyFights()
        throws JJWDataLayerException;

    public List<FriendlyFight> getFightsFromFighter( Fighter fighter );

    public FriendlyFight getFight( Long fightId )
        throws JJWDataLayerException;

    public void removeFriendlyFight( FriendlyFight fight )
        throws JJWDataLayerException;

    public void removeFriendlyFight( Long fightId )
        throws JJWDataLayerException;

    public void saveFriendlyFight( FriendlyFight fight )
        throws JJWDataLayerException;

    public boolean existFight( Long fightId );

    public boolean existFight( Long fightIdRed, Long fightIdBlue )
        throws JJWDataLayerException;
}
