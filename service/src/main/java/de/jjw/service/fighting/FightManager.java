/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightManager.java
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
import java.util.Map;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.FastFight;
import de.jjw.model.admin.FightTimes;
import de.jjw.model.fighting.Fight;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.modelWeb.FightWeb;

public interface FightManager
{

    public Fight getFight( Long fightId )
        throws JJWManagerException;

    public FightWeb getFightForClock( Long fightId, Locale local )
        throws JJWManagerException;

    public Fight getFight( Fight fight )
        throws JJWManagerException;

    public void saveFight( ServiceExchangeContext ctx, Fight fight )
        throws OptimisticLockingException, JJWManagerException;

    public List<FightTimes> getFightTimes()
        throws JJWManagerException;

    public Map<Integer, List<FastFight>> getFastestFights()
        throws JJWManagerException;
}
