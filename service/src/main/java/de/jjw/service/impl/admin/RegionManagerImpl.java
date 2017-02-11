/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : RegionManagerImpl.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:48
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

package de.jjw.service.impl.admin;

import java.util.List;

import de.jjw.dao.IDatabaseTableConstants;
import de.jjw.dao.admin.RegionDao;
import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.admin.Region;
import de.jjw.service.admin.RegionManager;
import de.jjw.service.exception.RegionInUseException;
import de.jjw.service.impl.BaseManager;
import de.jjw.service.mapper.admin.RegionMapper;


public class RegionManagerImpl
    extends BaseManager
    implements RegionManager, IDatabaseTableConstants
{

    private RegionDao regionDao;

    public void setRegionDao( RegionDao regionDao )
    {
        this.regionDao = regionDao;
    }

    public Region getRegion( Long regionId )
    {
        return RegionMapper.mapRegionFromDB( regionDao.getRegion( regionId ) );
    }

    public List getAllRegions()
    {
        return RegionMapper.mapRegionListFromDB( regionDao.getAllRegions() );
    }

    public void saveRegion( Region region )
        throws OptimisticLockingException
    {
        if ( null == region.getId() )
        {
            regionDao.saveRegion( region );
        }
        else
        {
            optimisticDao.testLocking( IDatabaseTableConstants.TABLE_REGION, region.getId(),
                                       region.getModificationDate() );
            RegionMapper.mapRegionToDB( region, regionDao.getRegion( region.getId() ) );
        }
    }

    public void removeRegion( Region region )
        throws RegionInUseException
    {
        if ( regionDao.isRegionInUse( region ) )
        {
            throw new RegionInUseException();
        }
        regionDao.removeRegion( region );
    }

    public void removeRegion( Long regionId )
        throws RegionInUseException
    {
        if ( regionDao.isRegionInUse( regionId ) )
        {
            throw new RegionInUseException();
        }
        regionDao.removeRegion( regionId );
    }

    public List<Region> getRegionsByCountry( Long countryId )
    {
        return RegionMapper.mapRegionListFromDB( regionDao.getRegionsByCountry( countryId ) );
    }

}
