/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FriendlyFightManager.java
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

package de.jjw.service.fighting;

import java.util.List;
import java.util.Locale;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.fighting.FriendlyFight;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.modelWeb.FightWeb;

public interface FriendlyFightManager
{

    public List<FightWeb> getAllFriendlyFights()
        throws JJWManagerException;

    public FriendlyFight getFight( Long fightId )
        throws JJWManagerException;

    public FightWeb getFightForClock( Long fightId, Locale local )
        throws JJWManagerException;

    public FriendlyFight getFight( FriendlyFight fight )
        throws JJWManagerException;

    public void saveFight( ServiceExchangeContext ctx, FriendlyFight fight )
        throws OptimisticLockingException, JJWManagerException;

    public void createFriendlyFight( ServiceExchangeContext ctx, long fighterIdRed, long fighterIdBlue )
        throws JJWManagerException;
    
    public void removeFriendlyFight( long fightId )
        throws JJWManagerException;

}
