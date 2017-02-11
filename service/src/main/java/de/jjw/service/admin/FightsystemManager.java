/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightsystemManager.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:45
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

package de.jjw.service.admin;

import java.util.List;

import de.jjw.model.admin.Fightsystem;
import de.jjw.service.ServiceExchangeContext;

public interface FightsystemManager
{

    public Fightsystem getFightsystem( Integer numberOfParticiapantsInClass );

    public Fightsystem getFightsystem( Long fightsystemId );

    public List<Fightsystem> getAllFightsystems();

    public void saveFightsystem( Fightsystem fightsystem );

    public void removeFightsystem( Fightsystem fightsystem );

    public void removeFightsystem( Long fightsystemId );

    public void removeAllFightsystems();

    public void saveFightsystems( ServiceExchangeContext ctx, List<Fightsystem> fightsystemList );

}
