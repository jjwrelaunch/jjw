/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : HighestDuoPointsMapper.java
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

package de.jjw.service.mapper.duo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.jjw.model.duo.HighestDuoPoints;
import de.jjw.service.util.IGlobalProjectConstants;
import de.jjw.util.ICodestableTypeConstants;
import de.jjw.util.IValueConstants;

public class HighestDuoPointsMapper
    implements ICodestableTypeConstants, IValueConstants, IGlobalProjectConstants
{

    public static Map<Integer, List<HighestDuoPoints>> mapHighestDuoPointsFromDB( Map<Integer, List<HighestDuoPoints>> duoPointMap )
    {
        Map<Integer, List<HighestDuoPoints>> ret = new HashMap<Integer, List<HighestDuoPoints>>();

        List<HighestDuoPoints> newFightList = null;
        for ( List<HighestDuoPoints> list : duoPointMap.values() )
        {
            newFightList = new ArrayList<HighestDuoPoints>( 21 );
            for ( HighestDuoPoints item : list )
            {
                newFightList.add( mapHighestDuoPointsFromDB( item ) );
            }
            ret.put( ret.size() + 1, newFightList );
        }
        return ret;
    }


    /**
     * maps a Fighter from DB and get a new Fighter object
     * 
     * @param highestDuoPoints
     * @return
     */
    public static HighestDuoPoints mapHighestDuoPointsFromDB( HighestDuoPoints highestDuoPoints )
    {
        if ( highestDuoPoints == null )
        {
            return null;
        }
        HighestDuoPoints ret = new HighestDuoPoints();

        ret.setName( highestDuoPoints.getName() );
        ret.setFirstname( highestDuoPoints.getFirstname() );
        ret.setName2( highestDuoPoints.getName2() );
        ret.setFirstname2( highestDuoPoints.getFirstname2() );
        ret.setAge( highestDuoPoints.getAge() );
        ret.setCountry( highestDuoPoints.getCountry() );
        ret.setRegion( highestDuoPoints.getRegion() );
        ret.setTeam( highestDuoPoints.getTeam() );
        ret.setSex( highestDuoPoints.getSex() );

        ret.setPoints( highestDuoPoints.getPoints() );
        ret.setPointsMax( highestDuoPoints.getPointsMax() );
        return ret;
    }



}
