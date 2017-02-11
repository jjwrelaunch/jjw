/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : RegionManager.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:42
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

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.admin.Region;
import de.jjw.service.exception.RegionInUseException;

public interface RegionManager
{

    public Region getRegion( Long regionId );

    public List<Region> getAllRegions();

    public void saveRegion( Region region )
        throws OptimisticLockingException;

    public void removeRegion( Region region )
        throws RegionInUseException;

    public void removeRegion( Long regionId )
        throws RegionInUseException;

    public List<Region> getRegionsByCountry( Long countryId );

}
