/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2013.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DrawHelperNewa.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.jjw.model.newa.NewaFighter;


public class DrawHelperNewa
    extends DrawHelperBase
{
    public List<NewaFighter> drawFighter( final List<NewaFighter> fighters, int poolsize, boolean onlyhalfList )
    {
        List<NewaFighter> ret = null;

        for ( int i = 0; i < 5; i++ )
        {
            ret = organizeFighterDraw( fighters, onlyhalfList );
            if ( checkDoubleFreeRound( ret, poolsize, createCheckPoolHelperDTO( fighters, poolsize ) ) )
                break;
            log.error( "redraw " + fighters.get( 0 ).getNewaclass().getAge().getDescription() + " "
                + fighters.get( 0 ).getNewaclass().getWeightclass() );
            if ( i == 4 )
                log.error( "redraw 4 rounds " + fighters.get( 0 ).getNewaclass().getAge().getDescription() + " "
                    + fighters.get( 0 ).getNewaclass().getWeightclass() );
        }
        return ret;
    }

    private List<NewaFighter> organizeFighterDraw( final List<NewaFighter> fighters, boolean onlyhalfList )
    {
        List<DrawHelperDTO> drawList =
            new ArrayList<DrawHelperDTO>( ( onlyhalfList ) ? fighters.size() / 2 + 1 : fighters.size() );
        DrawHelperDTO dto = null;
        List<NewaFighter> retList = new ArrayList<NewaFighter>( fighters.size() );

        for ( int i = 0; i < fighters.size(); i++ )
        {
            if ( !onlyhalfList || i % 2 == 1 )
            {
                dto = new DrawHelperDTO();
                dto.country = fighters.get( i ).getTeam().getCountryId();
                dto.region = fighters.get( i ).getTeam().getRegionId();
                dto.team = fighters.get( i ).getTeam().getId();
                dto.fighter = fighters.get( i ).getId();
                drawList.add( dto );
            }
        }
        doDraw( drawList );
        Map<Long, NewaFighter> hlp = new HashMap<Long, NewaFighter>();
        for ( NewaFighter item : fighters )
        {
            hlp.put( item.getId(), item );
        }
        if ( onlyhalfList )
        {
            int j = 0;
            for ( int i = 0; i < fighters.size(); i++ )
            {
                if ( i % 2 == 1 )
                {
                    retList.add( hlp.get( drawList.get( j ).fighter ) );
                    j++;
                }
                else
                {
                    retList.add( fighters.get( i ) );
                }
            }
        }
        else
        {
            for ( DrawHelperDTO item : drawList )
            {
                retList.add( hlp.get( item.fighter ) );
            }
        }
        return retList;
    }

    private CheckPoolHelperDTO createCheckPoolHelperDTO( List<NewaFighter> fighters, int poolsize )
    {
        boolean isPoolA = true;
        int freeRounds = 0;
        Map<Long, Long> poolAFreeRound = new HashMap<Long, Long>();
        Map<Long, Long> poolBFreeRound = new HashMap<Long, Long>();
        Map<Long, Long> poolANotFreeRound = new HashMap<Long, Long>();
        Map<Long, Long> poolBNotFreeRound = new HashMap<Long, Long>();
        Map<Long, Long> teammap = new HashMap<Long, Long>();

        for ( int i = 0; i < poolsize / 2; i++ )
        {
            teammap.put( fighters.get( i ).getTeam().getId(), fighters.get( i ).getTeam().getId() );

                if ( isPoolA )
            {
                if ( fighters.size() > i + poolsize / 2 )
                {
                    if ( poolANotFreeRound.get( fighters.get( i ).getTeam().getId() ) == null )
                        poolANotFreeRound.put( fighters.get( i ).getTeam().getId(), 1l );
                    else
                        poolANotFreeRound.put( fighters.get( i ).getTeam().getId(),
                                               poolANotFreeRound.get( fighters.get( i ).getTeam().getId() + 1l ) );

                    if ( poolANotFreeRound.get( fighters.get( i + poolsize / 2 ).getTeam().getId() ) == null )
                        poolANotFreeRound.put( fighters.get( i + poolsize / 2 ).getTeam().getId(), 1l );
                    else
                        poolANotFreeRound.put( fighters.get( i + poolsize / 2 ).getTeam().getId(),
                                               poolANotFreeRound.get( fighters.get( i + poolsize / 2 ).getTeam().getId() + 1l ) );

                }
                else
                {
                    freeRounds++;
                    if ( poolAFreeRound.get( fighters.get( i ).getTeam().getId() ) == null )
                        poolAFreeRound.put( fighters.get( i ).getTeam().getId(), 1l );
                    else
                        poolAFreeRound.put( fighters.get( i ).getTeam().getId(),
                                            poolAFreeRound.get( fighters.get( i ).getTeam().getId() + 1l ) );
                }
                }
                else
            {// pool B
                if ( fighters.size() > i + poolsize / 2 )
                {
                    if ( poolBNotFreeRound.get( fighters.get( i ).getTeam().getId() ) == null )
                        poolBNotFreeRound.put( fighters.get( i ).getTeam().getId(), 1l );
                    else
                        poolBNotFreeRound.put( fighters.get( i ).getTeam().getId(),
                                               poolBNotFreeRound.get( fighters.get( i ).getTeam().getId() + 1l ) );

                    if ( poolBNotFreeRound.get( fighters.get( i + poolsize / 2 ).getTeam().getId() ) == null )
                        poolBNotFreeRound.put( fighters.get( i + poolsize / 2 ).getTeam().getId(), 1l );
                    else
                        poolBNotFreeRound.put( fighters.get( i + poolsize / 2 ).getTeam().getId(),
                                               poolBNotFreeRound.get( fighters.get( i + poolsize / 2 ).getTeam().getId() + 1l ) );
                }
                else
                {
                    freeRounds++;
                    if ( poolBFreeRound.get( fighters.get( i ).getTeam().getId() ) == null )
                        poolBFreeRound.put( fighters.get( i ).getTeam().getId(), 1l );
                    else
                        poolBFreeRound.put( fighters.get( i ).getTeam().getId(),
                                            poolBFreeRound.get( fighters.get( i ).getTeam().getId() + 1l ) );
                }
            }
            isPoolA = !isPoolA;
        }
        return new CheckPoolHelperDTO( poolAFreeRound, poolBFreeRound, poolANotFreeRound, poolBNotFreeRound, teammap,
                                       freeRounds );
    }
}

