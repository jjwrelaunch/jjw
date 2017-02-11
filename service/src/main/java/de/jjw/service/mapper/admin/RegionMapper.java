/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : RegionMapper.java
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

package de.jjw.service.mapper.admin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import de.jjw.model.admin.Region;

public class RegionMapper
{
    public static List<Region> mapRegionListFromDB( List<Region> regions )
    {

        if ( regions == null )
        {
            return null;
        }
        List<Region> ret = new ArrayList<Region>( regions.size() + 1 );

        for ( Region region : regions )
        {
            ret.add( mapRegionFromDB( region ) );
        }

        return ret;
    }

    /**
     * maps a Region from DB and get a new Region object
     *
     * @param region
     * @return
     */
    public static Region mapRegionFromDB( Region region )
    {
        if ( region == null )
        {
            return null;
        }
        Region ret = new Region();

        ret.setRegionShort( new String( region.getRegionShort() ) );
        ret.setCreateDate( new Timestamp( region.getCreateDate().getTime() ) );
        ret.setCreateId( Long.valueOf( region.getCreateId() ) );
        ret.setDescription( new String( region.getDescription() ) );
        ret.setId( Long.valueOf( region.getId() ) );
        ret.setModificationDate( new Timestamp( region.getModificationDate().getTime() ) );
        ret.setModificationId( Long.valueOf( region.getModificationId() ) );

        ret.setCountry( CountryMapper.mapCountryFromDB( region.getCountry() ) );
        return ret;
    }

    public static void mapRegionToDB( Region region, Region regionDB )
    {
        regionDB.setRegionShort( new String( region.getRegionShort() ) );
        regionDB.setDescription( new String( region.getDescription() ) );
        regionDB.setModificationDate( new Timestamp( region.getModificationDate().getTime() ) );
        regionDB.setModificationId( Long.valueOf( region.getModificationId() ) );
        CountryMapper.mapCountryToDB( region.getCountry(), regionDB.getCountry() );
    }

}
