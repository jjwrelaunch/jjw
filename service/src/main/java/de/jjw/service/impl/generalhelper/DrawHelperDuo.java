/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2013.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DrawHelperFighting.java
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
/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2013.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DrawHelperDuo.java
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

import de.jjw.model.duo.DuoTeam;


public class DrawHelperDuo
    extends DrawHelperBase
{
    public List<DuoTeam> drawDuo( final List<DuoTeam> duoteams, int poolsize, boolean onlyhalfList )
    {
        List<DuoTeam> ret = null;

        for ( int i = 0; i < 5; i++ )
        {
            ret = organizeDuoDraw( duoteams, onlyhalfList );
            if ( checkDoubleFreeRound( ret, poolsize, createCheckPoolHelperDTO( duoteams, poolsize ) ) )
                break;
            log.error( "redraw " + duoteams.get( 0 ).getDuoclass().getAge().getDescription() + " "
                + duoteams.get( 0 ).getDuoclass().getDuoclassName() );
            if ( i == 4 )
                log.error( "redraw 4 rounds " + duoteams.get( 0 ).getDuoclass().getAge().getDescription() + " "
                    + duoteams.get( 0 ).getDuoclass().getDuoclassName() );
        }
        return ret;
    }

    private List<DuoTeam> organizeDuoDraw( final List<DuoTeam> duoteams, boolean onlyhalfList )
    {
        List<DrawHelperDTO> drawList =
            new ArrayList<DrawHelperDTO>( ( onlyhalfList ) ? duoteams.size() / 2 + 1 : duoteams.size() );
        DrawHelperDTO dto = null;
        List<DuoTeam> retList = new ArrayList<DuoTeam>( duoteams.size() );

        for ( int i = 0; i < duoteams.size(); i++ )
        {
            if ( !onlyhalfList || i % 2 == 1 )
            {
                dto = new DrawHelperDTO();
                dto.country = duoteams.get( i ).getTeam().getCountryId();
                dto.region = duoteams.get( i ).getTeam().getRegionId();
                dto.team = duoteams.get( i ).getTeam().getId();
                dto.fighter = duoteams.get( i ).getId();
                drawList.add( dto );
            }
        }
        doDraw( drawList );
        Map<Long, DuoTeam> hlp = new HashMap<Long, DuoTeam>();
        for ( DuoTeam item : duoteams )
        {
            hlp.put( item.getId(), item );
        }
        if ( onlyhalfList )
        {
            int j = 0;
            for ( int i = 0; i < duoteams.size(); i++ )
            {
                if ( i % 2 == 1 )
                {
                    retList.add( hlp.get( drawList.get( j ).fighter ) );
                    j++;
                }
                else
                {
                    retList.add( duoteams.get( i ) );
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

    private CheckPoolHelperDTO createCheckPoolHelperDTO( List<DuoTeam> duoteams, int poolsize )
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
            teammap.put( duoteams.get( i ).getTeam().getId(), duoteams.get( i ).getTeam().getId() );

                if ( isPoolA )
            {
                if ( duoteams.size() > i + poolsize / 2 )
                {
                    if ( poolANotFreeRound.get( duoteams.get( i ).getTeam().getId() ) == null )
                        poolANotFreeRound.put( duoteams.get( i ).getTeam().getId(), 1l );
                    else
                        poolANotFreeRound.put( duoteams.get( i ).getTeam().getId(),
                                               poolANotFreeRound.get( duoteams.get( i ).getTeam().getId() + 1l ) );

                    if ( poolANotFreeRound.get( duoteams.get( i + poolsize / 2 ).getTeam().getId() ) == null )
                        poolANotFreeRound.put( duoteams.get( i + poolsize / 2 ).getTeam().getId(), 1l );
                    else
                        poolANotFreeRound.put( duoteams.get( i + poolsize / 2 ).getTeam().getId(),
                                               poolANotFreeRound.get( duoteams.get( i + poolsize / 2 ).getTeam().getId() + 1l ) );

                }
                else
                {
                    freeRounds++;
                    if ( poolAFreeRound.get( duoteams.get( i ).getTeam().getId() ) == null )
                        poolAFreeRound.put( duoteams.get( i ).getTeam().getId(), 1l );
                    else
                        poolAFreeRound.put( duoteams.get( i ).getTeam().getId(),
                                            poolAFreeRound.get( duoteams.get( i ).getTeam().getId() + 1l ) );
                }
                }
                else
            {// pool B
                if ( duoteams.size() > i + poolsize / 2 )
                {
                    if ( poolBNotFreeRound.get( duoteams.get( i ).getTeam().getId() ) == null )
                        poolBNotFreeRound.put( duoteams.get( i ).getTeam().getId(), 1l );
                    else
                        poolBNotFreeRound.put( duoteams.get( i ).getTeam().getId(),
                                               poolBNotFreeRound.get( duoteams.get( i ).getTeam().getId() + 1l ) );

                    if ( poolBNotFreeRound.get( duoteams.get( i + poolsize / 2 ).getTeam().getId() ) == null )
                        poolBNotFreeRound.put( duoteams.get( i + poolsize / 2 ).getTeam().getId(), 1l );
                    else
                        poolBNotFreeRound.put( duoteams.get( i + poolsize / 2 ).getTeam().getId(),
                                               poolBNotFreeRound.get( duoteams.get( i + poolsize / 2 ).getTeam().getId() + 1l ) );
                }
                else
                {
                    freeRounds++;
                    if ( poolBFreeRound.get( duoteams.get( i ).getTeam().getId() ) == null )
                        poolBFreeRound.put( duoteams.get( i ).getTeam().getId(), 1l );
                    else
                        poolBFreeRound.put( duoteams.get( i ).getTeam().getId(),
                                            poolBFreeRound.get( duoteams.get( i ).getTeam().getId() + 1l ) );
                }
            }
            isPoolA = !isPoolA;
        }
        return new CheckPoolHelperDTO( poolAFreeRound, poolBFreeRound, poolANotFreeRound, poolBNotFreeRound, teammap,
                                       freeRounds );
    }
}

