/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FastFightMapper.java
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

package de.jjw.service.mapper.fighting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.jjw.model.FastFight;

public class FastFightMapper
{
    public static Map<Integer, List<FastFight>> mapFighterListFastFightFromDB( Map<Integer, List<FastFight>> fastFightMap )
    {
        Map<Integer, List<FastFight>> ret = new HashMap<Integer, List<FastFight>>();

        List<FastFight> newFightList = null;
        for ( List<FastFight> fightList : fastFightMap.values() )
        {
            newFightList = new ArrayList<FastFight>( 21 );
            for ( FastFight item : fightList )
            {
                newFightList.add( mapFastFightFromDB( item ) );
            }
            ret.put( ret.size() + 1, newFightList );
        }
        return ret;
    }

    /**
     * maps a FastFight from DB and get a new FastFight object
     * 
     * @param fastFight
     * @return
     */
    public static FastFight mapFastFightFromDB( FastFight fastFight )
    {
        if ( fastFight == null )
        {
            return null;
        }
        FastFight ret = new FastFight();

        ret.setFirstname( new String( fastFight.getFirstname() ) );
        ret.setName( new String( fastFight.getName() ) );
        ret.setFightTime( fastFight.getFightTime() );
        ret.setSex( fastFight.getSex() );
        ret.setTeam( new String( fastFight.getTeam() ) );
        ret.setRegion( new String( fastFight.getRegion() ) );
        ret.setCountry( new String( fastFight.getCountry() ) );
        ret.setAge( new String( fastFight.getAge() ) );

        return ret;
    }

}
