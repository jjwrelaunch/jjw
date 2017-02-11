/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2013.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DrawHelperBase.java
 * Created : 01 May 2013
 * Last Modified: Wed, 01 May 2013 11:50:35
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

package de.jjw.service.impl.generalhelper;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import de.jjw.util.TypeUtil;

public class DrawHelperBase
{
    protected final Logger log = Logger.getRootLogger();

    protected static int KO8 = 8;

    protected static int KO16 = 16;

    protected static int KO32 = 32;

    protected static int KO64 = 64;

    protected static int KO128 = 128;

    protected boolean checkDoubleFreeRound( final List fighters, int poolsize, CheckPoolHelperDTO dto )
    {

        if ( fighters.size() == KO8 || fighters.size() == KO16 || fighters.size() == KO32 || fighters.size() == KO64
            || fighters.size() == KO128 )
            return true; // no free rounds
        if ( fighters.size() == ( KO8 + 1 ) || fighters.size() == ( KO16 + 1 ) || fighters.size() == ( KO32 + 1 )
            || fighters.size() == ( KO64 + 1 ) )
            return true;

        if ( poolsize / 2 - dto.freeRounds == 2 )
        {
            // wenn 2 Kämpfe in der Vorrunde existieren und 2mal die selben Teamsbeteiligt sind dann false
            if ( dto.poolANotFreeRound.size() == 2 )
            {
                if ( dto.poolBNotFreeRound.size() == 2 )
                {
                    if ( dto.poolBNotFreeRound.containsKey( dto.poolANotFreeRound.keySet().toArray()[0] )
                        && dto.poolBNotFreeRound.containsKey( dto.poolANotFreeRound.keySet().toArray()[1] ) )
                        return false;
                }
            }
        }

        if ( poolsize / 2 - dto.freeRounds <= poolsize / 4 && poolsize / 2 - dto.freeRounds > 1 )
        {
            int foundFreeRounds = 0;
            for ( int i = 0; i < dto.poolAFreeRound.size(); i++ )
            {
                if ( dto.poolBFreeRound.get( dto.poolAFreeRound.keySet().toArray()[i] ) != null )
                {
                    foundFreeRounds++;
                }
            }
            if ( foundFreeRounds >= 2 )
                return false;
        }
        return true;
    }

    protected List<DrawHelperDTO> doDraw( List<DrawHelperDTO> drawList )
    {

        Map<Long, Double> countryMap = new HashMap<Long, Double>();
        Map<Long, Double> regionMap = new HashMap<Long, Double>();
        Map<Long, Double> teamMap = new HashMap<Long, Double>();
        Map<Long, Double> fighterMap = new HashMap<Long, Double>();
        Random rand = new Random( System.currentTimeMillis() );

        for ( DrawHelperDTO item : drawList )
        {
            countryMap.put( item.country, rand.nextDouble() );
            regionMap.put( item.region, rand.nextDouble() );
            teamMap.put( item.team, rand.nextDouble() );
            fighterMap.put( item.fighter, rand.nextDouble() );
        }

        for ( DrawHelperDTO item : drawList )
        {
            item.countryValue = countryMap.get( item.country );
            item.regionValue = regionMap.get( item.region );
            item.teamValue = teamMap.get( item.team );
            item.fighterValue = fighterMap.get( item.fighter );

        }

        Collections.sort( drawList );
        return drawList;
    }

    protected class CheckPoolHelperDTO
    {
        public Map<Long, Long> poolAFreeRound = new HashMap<Long, Long>();

        public Map<Long, Long> poolBFreeRound = new HashMap<Long, Long>();

        public Map<Long, Long> poolANotFreeRound = new HashMap<Long, Long>();

        public Map<Long, Long> poolBNotFreeRound = new HashMap<Long, Long>();

        public Map<Long, Long> teammap = new HashMap<Long, Long>();

        public int freeRounds = 0;

        public CheckPoolHelperDTO( Map<Long, Long> poolAFreeRound, Map<Long, Long> poolBFreeRound,
                                   Map<Long, Long> poolANotFreeRound, Map<Long, Long> poolBNotFreeRound,
                                   Map<Long, Long> teammap, int freeRounds )
        {
            super();
            this.poolAFreeRound = poolAFreeRound;
            this.poolBFreeRound = poolBFreeRound;
            this.poolANotFreeRound = poolANotFreeRound;
            this.poolBNotFreeRound = poolBNotFreeRound;
            this.teammap = teammap;
            this.freeRounds = freeRounds;
        }
    }

    protected class DrawHelperDTO
        implements Comparable<DrawHelperDTO>
    {
        public long country = TypeUtil.LONG_EMPTY;

        public long team = TypeUtil.LONG_EMPTY;

        public long region = TypeUtil.LONG_EMPTY;

        public long fighter = TypeUtil.LONG_EMPTY;

        public double countryValue = TypeUtil.DOUBLE_EMPTY;

        public double regionValue = TypeUtil.DOUBLE_EMPTY;

        public double teamValue = TypeUtil.DOUBLE_EMPTY;

        public double fighterValue = TypeUtil.DOUBLE_EMPTY;

        @Override
        public int compareTo( DrawHelperDTO o )
        {
            if ( this.countryValue > o.countryValue )
                return 1;
            if ( this.countryValue < o.countryValue )
                return -1;

            if ( this.countryValue == o.countryValue )
            {
                if ( this.regionValue > o.regionValue )
                    return 1;
                if ( this.regionValue < o.regionValue )
                    return -1;
                if ( this.regionValue == o.regionValue )
                {
                    if ( this.teamValue > o.teamValue )
                        return 1;
                    if ( this.teamValue < o.teamValue )
                        return -1;
                    if ( this.teamValue == o.teamValue )
                    {
                        if ( this.fighterValue > o.fighterValue )
                            return 1;
                        if ( this.fighterValue < o.fighterValue )
                            return -1;
                    }
                }
            }
            return 0;
        }
    }

}
