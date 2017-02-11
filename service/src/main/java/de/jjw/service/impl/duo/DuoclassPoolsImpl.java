/*
 * Th file  part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoclassPoolsImpl.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:44
 *
 * Ju Jutsu Web  free software: you can redtribute it and/or modify
 * it under the terms of the GNU General Public License as publhed by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Ju Jutsu Web  dtributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Ju Jutsu Web.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.jjw.service.impl.duo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.jjw.dao.duo.DuoFightDao;
import de.jjw.dao.duo.DuoclassDao;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.duo.DuoDoublePoolClass;
import de.jjw.model.duo.DuoDoublePoolItem;
import de.jjw.model.duo.DuoFight;
import de.jjw.model.duo.DuoSimplePoolClass;
import de.jjw.model.duo.DuoSimplePoolItem;
import de.jjw.model.duo.DuoTeam;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.mapper.duo.DuoDoublePoolMapper;
import de.jjw.service.mapper.duo.DuoSimplePoolMapper;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;

public class DuoclassPoolsImpl
{
    private static int FIGHT_WIN = 1;

    private static int FIGHT_LOST = 0;

    // TODO: handle triple places; also for duo

    public DuoSimplePoolClass getDuoclassSimplePool( Long duoclassId, DuoclassDao duoclassDao, int revenge )
        throws JJWManagerException
    {
        try
        {
            DuoSimplePoolClass pool =
                DuoSimplePoolMapper.mapDuoSimplePoolClassFromDB( duoclassDao.getDuoclassSimplePool( duoclassId ) );
            pool.setId( duoclassId );
            // compute and fullfill the pool
            DuoclassPoolRankingDTO ranking;
            List<DuoclassPoolRankingDTO> rankingList = new ArrayList<DuoclassPoolRankingDTO>( 5 );
            int i = 0;
            for ( DuoSimplePoolItem item : pool.getDuoTeamList() )
            {
                i++;
                if ( item.getFight1() != null )
                {
                    if ( item.getFight1().getWinnerId() != null
                        && !TypeUtil.isEmpty( item.getFight1().getWinnerId().longValue() ) )
                    {
                        if ( item.getFight1().getWinnerId().equals( item.getDuoTeam().getId() ) )
                        {
                            // the fight was won
                            item.getResult().setResultFight1( FIGHT_WIN );
                            item.getResult().setUbFight1( ( item.getDuoTeam().getId().equals( item.getFight1().getTeamIdRed() ) ) ? item.getFight1().getPointsRed()
                                                                          : item.getFight1().getPointsBlue() );
                            if ( item.getResult().getUbFight1() == TypeUtil.DOUBLE_EMPTY )
                            {
                                item.getResult().setUbFight1( 0 );
                            }
                        }
                        else
                        {
                            item.getResult().setResultFight1( FIGHT_LOST );
                            item.getResult().setUbFight1( ( item.getDuoTeam().getId().equals( item.getFight1().getTeamIdRed() ) ) ? item.getFight1().getPointsRed()
                                                                          : item.getFight1().getPointsBlue() );
                            if ( item.getResult().getUbFight1() == TypeUtil.DOUBLE_EMPTY )
                            {
                                item.getResult().setUbFight1( 0 );
                            }
                        }
                    }
                    // set in FightListMap
                    if ( i == 1 )
                    {
                        item.getFight1().setDuoTeamRed( item.getDuoTeam() );
                        item.getFight1().setDuoTeamBlue( pool.getDuoTeamList().get( 1 ).getDuoTeam() );
                        pool.putFightListMap( TypeUtil.INTEGER_1, item.getFight1() );
                    }
                }

                if ( item.getFight2() != null )
                {
                    if ( item.getFight2().getWinnerId() != null
                        && !TypeUtil.isEmpty( item.getFight2().getWinnerId().longValue() ) )
                    {
                        if ( item.getFight2().getWinnerId().equals( item.getDuoTeam().getId() ) )
                        {
                            // the fight was won
                            item.getResult().setResultFight2( FIGHT_WIN );
                            item.getResult().setUbFight2( ( item.getDuoTeam().getId().equals( item.getFight2().getTeamIdRed() ) ) ? item.getFight2().getPointsRed()
                                                                          : item.getFight2().getPointsBlue() );
                            if ( item.getResult().getUbFight2() == TypeUtil.DOUBLE_EMPTY )
                            {
                                item.getResult().setUbFight2( 0 );
                            }
                        }
                        else
                        {
                            item.getResult().setResultFight2( FIGHT_LOST );
                            item.getResult().setUbFight2( ( item.getDuoTeam().getId().equals( item.getFight2().getTeamIdRed() ) ) ? item.getFight2().getPointsRed()
                                                                          : item.getFight2().getPointsBlue() );
                            if ( item.getResult().getUbFight2() == TypeUtil.DOUBLE_EMPTY )
                            {
                                item.getResult().setUbFight2( 0 );
                            }
                        }
                    }
                    // set in FightListMap
                    if ( i == 1 )
                    {
                        if ( pool.getDuoTeamList().size() == 2 && ( revenge == 1 || revenge == 2 ) )
                        {
                            item.getFight2().setDuoTeamRed( pool.getDuoTeamList().get( 1 ).getDuoTeam() );
                            item.getFight2().setDuoTeamBlue( item.getDuoTeam() );
                            pool.putFightListMap( TypeUtil.INTEGER_4, item.getFight2() );
                        }
                        else
                        {
                            item.getFight2().setDuoTeamRed( item.getDuoTeam() );
                            item.getFight2().setDuoTeamBlue( pool.getDuoTeamList().get( 2 ).getDuoTeam() );
                            pool.putFightListMap( TypeUtil.INTEGER_6, item.getFight2() );
                        }
                    }
                    if ( i == 2 )
                    {
                        if ( pool.getDuoTeamList().size() != 2 )
                        {
                            item.getFight2().setDuoTeamRed( item.getDuoTeam() );
                            item.getFight2().setDuoTeamBlue( pool.getDuoTeamList().get( 2 ).getDuoTeam() );
                            pool.putFightListMap( TypeUtil.INTEGER_4, item.getFight2() );
                        }
                    }
                }

                if ( item.getFight3() != null )
                {
                    if ( item.getFight3().getWinnerId() != null
                        && !TypeUtil.isEmpty( item.getFight3().getWinnerId().longValue() ) )
                    {
                        if ( item.getFight3().getWinnerId().equals( item.getDuoTeam().getId() ) )
                        {
                            // the fight was won
                            item.getResult().setResultFight3( FIGHT_WIN );
                            item.getResult().setUbFight3( ( item.getDuoTeam().getId().equals( item.getFight3().getTeamIdRed() ) ) ? item.getFight3().getPointsRed()
                                                                          : item.getFight3().getPointsBlue() );
                            if ( item.getResult().getUbFight3() == TypeUtil.DOUBLE_EMPTY )
                            {
                                item.getResult().setUbFight3( 0 );
                            }
                        }
                        else
                        {
                            item.getResult().setResultFight3( FIGHT_LOST );
                            item.getResult().setUbFight3( ( item.getDuoTeam().getId().equals( item.getFight3().getTeamIdRed() ) ) ? item.getFight3().getPointsRed()
                                                                          : item.getFight3().getPointsBlue() );
                            if ( item.getResult().getUbFight3() == TypeUtil.DOUBLE_EMPTY )
                            {
                                item.getResult().setUbFight3( 0 );
                            }
                        }
                    }
                    // set in FightListMap

                    if ( i == 1 )
                    {
                        if ( pool.getDuoTeamList().size() == 2 && revenge == 2 )
                        {
                            item.getFight3().setDuoTeamRed( item.getDuoTeam() );
                            item.getFight3().setDuoTeamBlue( pool.getDuoTeamList().get( 1 ).getDuoTeam() );
                            pool.putFightListMap( TypeUtil.INTEGER_6, item.getFight3() );
                        }
                        else
                        {
                            item.getFight3().setDuoTeamRed( item.getDuoTeam() );
                            item.getFight3().setDuoTeamBlue( pool.getDuoTeamList().get( 3 ).getDuoTeam() );
                            pool.putFightListMap( TypeUtil.INTEGER_9, item.getFight3() );
                        }
                    }
                    if ( i == 2 && pool.getDuoTeamList().size() != 2 )
                    {
                        item.getFight3().setDuoTeamRed( item.getDuoTeam() );
                        item.getFight3().setDuoTeamBlue( pool.getDuoTeamList().get( 3 ).getDuoTeam() );
                        pool.putFightListMap( TypeUtil.INTEGER_7, item.getFight3() );
                    }
                    if ( i == 3 && pool.getDuoTeamList().size() != 2 )
                    {
                        item.getFight3().setDuoTeamRed( item.getDuoTeam() );
                        item.getFight3().setDuoTeamBlue( pool.getDuoTeamList().get( 3 ).getDuoTeam() );
                        pool.putFightListMap( TypeUtil.INTEGER_2, item.getFight3() );
                    }
                }

                if ( item.getFight4() != null )
                {
                    if ( item.getFight4().getWinnerId() != null
                        && !TypeUtil.isEmpty( item.getFight4().getWinnerId().longValue() ) )
                    {
                        if ( item.getFight4().getWinnerId().equals( item.getDuoTeam().getId() ) )
                        {
                            // the fight was won
                            item.getResult().setResultFight4( FIGHT_WIN );
                            item.getResult().setUbFight4( ( item.getDuoTeam().getId().equals( item.getFight4().getTeamIdRed() ) ) ? item.getFight4().getPointsRed()
                                                                          : item.getFight4().getPointsBlue() );
                            if ( item.getResult().getUbFight4() == TypeUtil.DOUBLE_EMPTY )
                            {
                                item.getResult().setUbFight4( 0 );
                            }
                        }
                        else
                        {
                            item.getResult().setResultFight4( FIGHT_LOST );
                            item.getResult().setUbFight4( ( item.getDuoTeam().getId().equals( item.getFight4().getTeamIdRed() ) ) ? item.getFight4().getPointsRed()
                                                                          : item.getFight4().getPointsBlue() );
                            if ( item.getResult().getUbFight4() == TypeUtil.DOUBLE_EMPTY )
                            {
                                item.getResult().setUbFight4( 0 );
                            }
                        }
                    }
                    // set in FightListMap
                    if ( i == 1 )
                    {
                        item.getFight4().setDuoTeamRed( item.getDuoTeam() );
                        item.getFight4().setDuoTeamBlue( pool.getDuoTeamList().get( 4 ).getDuoTeam() );
                        pool.putFightListMap( TypeUtil.INTEGER_3, item.getFight4() );
                    }
                    if ( i == 2 )
                    {
                        item.getFight4().setDuoTeamRed( item.getDuoTeam() );
                        item.getFight4().setDuoTeamBlue( pool.getDuoTeamList().get( 4 ).getDuoTeam() );
                        pool.putFightListMap( TypeUtil.INTEGER_10, item.getFight4() );
                    }
                    if ( i == 3 )
                    {
                        item.getFight4().setDuoTeamRed( item.getDuoTeam() );
                        item.getFight4().setDuoTeamBlue( pool.getDuoTeamList().get( 4 ).getDuoTeam() );
                        pool.putFightListMap( TypeUtil.INTEGER_8, item.getFight4() );
                    }
                    if ( i == 4 )
                    {
                        item.getFight4().setDuoTeamRed( item.getDuoTeam() );
                        item.getFight4().setDuoTeamBlue( pool.getDuoTeamList().get( 4 ).getDuoTeam() );
                        pool.putFightListMap( TypeUtil.INTEGER_5, item.getFight4() );
                    }
                }
                ranking = new DuoclassPoolRankingDTO();
                ranking.setId( item.getDuoTeam().getId() );
                ranking.setWins( item.getResult().getWinCount() );
                ranking.setUbs( item.getResult().getUBCount() );
                rankingList.add( ranking );
            }
            if ( pool.getDuoTeamList().size() > 1 )
            {
                // triple place ranking
                rankingList = doRanking( pool.getDuoFightListMap().values(), rankingList );

                for ( DuoSimplePoolItem item : pool.getDuoTeamList() )
                {
                    for ( DuoclassPoolRankingDTO dto : rankingList )
                    {
                        if ( item.getDuoTeam().getId().equals( dto.getId() ) )
                        {
                            item.setPlace( dto.getPlace() );
                            break;
                        }
                    }
                }
            }
            return pool;
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public DuoDoublePoolClass getDuoclassDoublePool( Long duoclassId, DuoclassDao duoclassDao, DuoFightDao fightDao,
                                                     boolean isResetFinalFight )
        throws JJWManagerException
    {
        try
        {
            DuoDoublePoolClass pool =
                DuoDoublePoolMapper.mapDuoDoubePoolClassFromDB( duoclassDao.getDuoclassDoublePool( duoclassId ) );
            pool.setId( duoclassId );
            // compute and fullfill the pool
            DuoclassPoolRankingDTO ranking;
            List<DuoclassPoolRankingDTO> rankingList = new ArrayList<DuoclassPoolRankingDTO>( 5 );
            int i = 0;
            for ( DuoDoublePoolItem item : pool.getDuoListPoolA() )
            {
                i++;
                if ( item.getFight1() != null )
                {
                    if ( item.getFight1().getWinnerId() != null
                        && !TypeUtil.isEmpty( item.getFight1().getWinnerId().longValue() ) )
                    {
                        if ( item.getFight1().getWinnerId().equals( item.getDuoTeam().getId() ) )
                        {
                            // the fight was won
                            item.getResult().setResultFight1( FIGHT_WIN );
                            item.getResult().setUbFight1( ( item.getDuoTeam().getId().equals( item.getFight1().getTeamIdRed() ) ) ? item.getFight1().getPointsRed()
                                                                          : item.getFight1().getPointsBlue() );
                            if ( item.getResult().getUbFight1() == TypeUtil.DOUBLE_EMPTY )
                            {
                                item.getResult().setUbFight1( 0 );
                            }
                        }
                        else
                        {
                            item.getResult().setResultFight1( FIGHT_LOST );
                            item.getResult().setUbFight1( ( item.getDuoTeam().getId().equals( item.getFight1().getTeamIdRed() ) ) ? item.getFight1().getPointsRed()
                                                                          : item.getFight1().getPointsBlue() );
                            if ( item.getResult().getUbFight1() == TypeUtil.DOUBLE_EMPTY )
                            {
                                item.getResult().setUbFight1( 0 );
                            }
                        }
                    }
                    // set in FightListMap
                    if ( i == 1 )
                    {
                        item.getFight1().setDuoTeamRed( item.getDuoTeam() );
                        item.getFight1().setDuoTeamBlue( pool.getDuoListPoolA().get( 1 ).getDuoTeam() );
                        pool.putFightListMapPoolA( TypeUtil.INTEGER_1, item.getFight1() );
                    }
                }

                if ( item.getFight2() != null )
                {
                    if ( item.getFight2().getWinnerId() != null
                        && !TypeUtil.isEmpty( item.getFight2().getWinnerId().longValue() ) )
                    {
                        if ( item.getFight2().getWinnerId().equals( item.getDuoTeam().getId() ) )
                        {
                            // the fight was won
                            item.getResult().setResultFight2( FIGHT_WIN );
                            item.getResult().setUbFight2( ( item.getDuoTeam().getId().equals( item.getFight2().getTeamIdRed() ) ) ? item.getFight2().getPointsRed()
                                                                          : item.getFight2().getPointsBlue() );
                            if ( item.getResult().getUbFight2() == TypeUtil.DOUBLE_EMPTY )
                            {
                                item.getResult().setUbFight2( 0 );
                            }
                        }
                        else
                        {
                            item.getResult().setResultFight2( FIGHT_LOST );
                            item.getResult().setUbFight2( ( item.getDuoTeam().getId().equals( item.getFight2().getTeamIdRed() ) ) ? item.getFight2().getPointsRed()
                                                                          : item.getFight2().getPointsBlue() );
                            if ( item.getResult().getUbFight2() == TypeUtil.DOUBLE_EMPTY )
                            {
                                item.getResult().setUbFight2( 0 );
                            }
                        }
                    }
                    // set in FightListMap
                    if ( i == 1 )
                    {
                        item.getFight2().setDuoTeamRed( item.getDuoTeam() );
                        item.getFight2().setDuoTeamBlue( pool.getDuoListPoolA().get( 2 ).getDuoTeam() );
                        pool.putFightListMapPoolA( TypeUtil.INTEGER_6, item.getFight2() );
                    }
                    if ( i == 2 )
                    {
                        item.getFight2().setDuoTeamRed( item.getDuoTeam() );
                        item.getFight2().setDuoTeamBlue( pool.getDuoListPoolA().get( 2 ).getDuoTeam() );
                        pool.putFightListMapPoolA( TypeUtil.INTEGER_4, item.getFight2() );
                    }
                }

                if ( item.getFight3() != null )
                {
                    if ( item.getFight3().getWinnerId() != null
                        && !TypeUtil.isEmpty( item.getFight3().getWinnerId().longValue() ) )
                    {
                        if ( item.getFight3().getWinnerId().equals( item.getDuoTeam().getId() ) )
                        {
                            // the fight was won
                            item.getResult().setResultFight3( FIGHT_WIN );
                            item.getResult().setUbFight3( ( item.getDuoTeam().getId().equals( item.getFight3().getTeamIdRed() ) ) ? item.getFight3().getPointsRed()
                                                                          : item.getFight3().getPointsBlue() );
                            if ( item.getResult().getUbFight3() == TypeUtil.DOUBLE_EMPTY )
                            {
                                item.getResult().setUbFight3( 0 );
                            }
                        }
                        else
                        {
                            item.getResult().setResultFight3( FIGHT_LOST );
                            item.getResult().setUbFight3( ( item.getDuoTeam().getId().equals( item.getFight3().getTeamIdRed() ) ) ? item.getFight3().getPointsRed()
                                                                          : item.getFight3().getPointsBlue() );
                            if ( item.getResult().getUbFight3() == TypeUtil.DOUBLE_EMPTY )
                            {
                                item.getResult().setUbFight3( 0 );
                            }
                        }
                    }
                    // set in FightListMap

                    if ( i == 1 )
                    {
                        item.getFight3().setDuoTeamRed( item.getDuoTeam() );
                        item.getFight3().setDuoTeamBlue( pool.getDuoListPoolA().get( 3 ).getDuoTeam() );
                        pool.putFightListMapPoolA( TypeUtil.INTEGER_9, item.getFight3() );
                    }
                    if ( i == 2 )
                    {
                        item.getFight3().setDuoTeamRed( item.getDuoTeam() );
                        item.getFight3().setDuoTeamBlue( pool.getDuoListPoolA().get( 3 ).getDuoTeam() );
                        pool.putFightListMapPoolA( TypeUtil.INTEGER_7, item.getFight3() );
                    }
                    if ( i == 3 )
                    {
                        item.getFight3().setDuoTeamRed( item.getDuoTeam() );
                        item.getFight3().setDuoTeamBlue( pool.getDuoListPoolA().get( 3 ).getDuoTeam() );
                        pool.putFightListMapPoolA( TypeUtil.INTEGER_2, item.getFight3() );
                    }
                }

                if ( item.getFight4() != null )
                {
                    if ( item.getFight4().getWinnerId() != null
                        && !TypeUtil.isEmpty( item.getFight4().getWinnerId().longValue() ) )
                    {
                        if ( item.getFight4().getWinnerId().equals( item.getDuoTeam().getId() ) )
                        {
                            // the fight was won
                            item.getResult().setResultFight4( FIGHT_WIN );
                            item.getResult().setUbFight4( ( item.getDuoTeam().getId().equals( item.getFight4().getTeamIdRed() ) ) ? item.getFight4().getPointsRed()
                                                                          : item.getFight4().getPointsBlue() );
                            if ( item.getResult().getUbFight4() == TypeUtil.DOUBLE_EMPTY )
                            {
                                item.getResult().setUbFight4( 0 );
                            }
                        }
                        else
                        {
                            item.getResult().setResultFight4( FIGHT_LOST );
                            item.getResult().setUbFight4( ( item.getDuoTeam().getId().equals( item.getFight4().getTeamIdRed() ) ) ? item.getFight4().getPointsRed()
                                                                          : item.getFight4().getPointsBlue() );
                            if ( item.getResult().getUbFight4() == TypeUtil.DOUBLE_EMPTY )
                            {
                                item.getResult().setUbFight4( 0 );
                            }
                        }
                    }
                    // set in FightListMap
                    if ( i == 1 )
                    {
                        item.getFight4().setDuoTeamRed( item.getDuoTeam() );
                        item.getFight4().setDuoTeamBlue( pool.getDuoListPoolA().get( 4 ).getDuoTeam() );
                        pool.putFightListMapPoolA( TypeUtil.INTEGER_3, item.getFight4() );
                    }
                    if ( i == 2 )
                    {
                        item.getFight4().setDuoTeamRed( item.getDuoTeam() );
                        item.getFight4().setDuoTeamBlue( pool.getDuoListPoolA().get( 4 ).getDuoTeam() );
                        pool.putFightListMapPoolA( TypeUtil.INTEGER_10, item.getFight4() );
                    }
                    if ( i == 3 )
                    {
                        item.getFight4().setDuoTeamRed( item.getDuoTeam() );
                        item.getFight4().setDuoTeamBlue( pool.getDuoListPoolA().get( 4 ).getDuoTeam() );
                        pool.putFightListMapPoolA( TypeUtil.INTEGER_8, item.getFight4() );
                    }
                    if ( i == 4 )
                    {
                        item.getFight4().setDuoTeamRed( item.getDuoTeam() );
                        item.getFight4().setDuoTeamBlue( pool.getDuoListPoolA().get( 4 ).getDuoTeam() );
                        pool.putFightListMapPoolA( TypeUtil.INTEGER_5, item.getFight4() );
                    }
                }
                ranking = new DuoclassPoolRankingDTO();
                ranking.setId( item.getDuoTeam().getId() );
                ranking.setWins( item.getResult().getWinCount() );
                ranking.setUbs( item.getResult().getUBCount() );
                rankingList.add( ranking );
            }
            // triple place ranking
            rankingList = doRanking( pool.getFightListMapPoolA().values(), rankingList );

            for ( DuoDoublePoolItem item : pool.getDuoListPoolA() )
            {
                for ( DuoclassPoolRankingDTO dto : rankingList )
                {
                    if ( item.getDuoTeam().getId().equals( dto.getId() ) )
                    {
                        item.setPoolPlace( dto.getPlace() );
                        break;
                    }
                }
            }

            // ---------------------------------------------------------------------------------------------
            i = 0;
            rankingList = new ArrayList<DuoclassPoolRankingDTO>( 5 );
            for ( DuoDoublePoolItem item : pool.getDuoListPoolB() )
            {
                i++;
                if ( item.getFight1() != null )
                {
                    if ( item.getFight1().getWinnerId() != null
                        && !TypeUtil.isEmpty( item.getFight1().getWinnerId().longValue() ) )
                    {
                        if ( item.getFight1().getWinnerId().equals( item.getDuoTeam().getId() ) )
                        {
                            // the fight was won
                            item.getResult().setResultFight1( FIGHT_WIN );
                            item.getResult().setUbFight1( ( item.getDuoTeam().getId().equals( item.getFight1().getTeamIdRed() ) ) ? item.getFight1().getPointsRed()
                                                                          : item.getFight1().getPointsBlue() );
                            if ( item.getResult().getUbFight1() == TypeUtil.DOUBLE_EMPTY )
                            {
                                item.getResult().setUbFight1( 0 );
                            }
                        }
                        else
                        {
                            item.getResult().setResultFight1( FIGHT_LOST );
                            item.getResult().setUbFight1( ( item.getDuoTeam().getId().equals( item.getFight1().getTeamIdRed() ) ) ? item.getFight1().getPointsRed()
                                                                          : item.getFight1().getPointsBlue() );
                            if ( item.getResult().getUbFight1() == TypeUtil.DOUBLE_EMPTY )
                            {
                                item.getResult().setUbFight1( 0 );
                            }
                        }
                    }
                    // set in FightListMap
                    if ( i == 1 )
                    {
                        item.getFight1().setDuoTeamRed( item.getDuoTeam() );
                        item.getFight1().setDuoTeamBlue( pool.getDuoListPoolB().get( 1 ).getDuoTeam() );
                        pool.putFightListMapPoolB( TypeUtil.INTEGER_1, item.getFight1() );
                    }
                }

                if ( item.getFight2() != null )
                {
                    if ( item.getFight2().getWinnerId() != null
                        && !TypeUtil.isEmpty( item.getFight2().getWinnerId().longValue() ) )
                    {
                        if ( item.getFight2().getWinnerId().equals( item.getDuoTeam().getId() ) )
                        {
                            // the fight was won
                            item.getResult().setResultFight2( FIGHT_WIN );
                            item.getResult().setUbFight2( ( item.getDuoTeam().getId().equals( item.getFight2().getTeamIdRed() ) ) ? item.getFight2().getPointsRed()
                                                                          : item.getFight2().getPointsBlue() );
                            if ( item.getResult().getUbFight2() == TypeUtil.DOUBLE_EMPTY )
                            {
                                item.getResult().setUbFight2( 0 );
                            }
                        }
                        else
                        {
                            item.getResult().setResultFight2( FIGHT_LOST );
                            item.getResult().setUbFight2( ( item.getDuoTeam().getId().equals( item.getFight2().getTeamIdRed() ) ) ? item.getFight2().getPointsRed()
                                                                          : item.getFight2().getPointsBlue() );
                            if ( item.getResult().getUbFight2() == TypeUtil.DOUBLE_EMPTY )
                            {
                                item.getResult().setUbFight2( 0 );
                            }
                        }
                    }
                    // set in FightListMap
                    if ( i == 1 )
                    {
                        item.getFight2().setDuoTeamRed( item.getDuoTeam() );
                        item.getFight2().setDuoTeamBlue( pool.getDuoListPoolB().get( 2 ).getDuoTeam() );
                        pool.putFightListMapPoolB( TypeUtil.INTEGER_6, item.getFight2() );
                    }
                    if ( i == 2 )
                    {
                        item.getFight2().setDuoTeamRed( item.getDuoTeam() );
                        item.getFight2().setDuoTeamBlue( pool.getDuoListPoolB().get( 2 ).getDuoTeam() );
                        pool.putFightListMapPoolB( TypeUtil.INTEGER_4, item.getFight2() );
                    }
                }

                if ( item.getFight3() != null )
                {
                    if ( item.getFight3().getWinnerId() != null
                        && !TypeUtil.isEmpty( item.getFight3().getWinnerId().longValue() ) )
                    {
                        if ( item.getFight3().getWinnerId().equals( item.getDuoTeam().getId() ) )
                        {
                            // the fight was won
                            item.getResult().setResultFight3( FIGHT_WIN );
                            item.getResult().setUbFight3( ( item.getDuoTeam().getId().equals( item.getFight3().getTeamIdRed() ) ) ? item.getFight3().getPointsRed()
                                                                          : item.getFight3().getPointsBlue() );
                            if ( item.getResult().getUbFight3() == TypeUtil.DOUBLE_EMPTY )
                            {
                                item.getResult().setUbFight3( 0 );
                            }
                        }
                        else
                        {
                            item.getResult().setResultFight3( FIGHT_LOST );
                            item.getResult().setUbFight3( ( item.getDuoTeam().getId().equals( item.getFight3().getTeamIdRed() ) ) ? item.getFight3().getPointsRed()
                                                                          : item.getFight3().getPointsBlue() );
                            if ( item.getResult().getUbFight3() == TypeUtil.DOUBLE_EMPTY )
                            {
                                item.getResult().setUbFight3( 0 );
                            }
                        }
                    }
                    // set in FightListMap

                    if ( i == 1 )
                    {
                        item.getFight3().setDuoTeamRed( item.getDuoTeam() );
                        item.getFight3().setDuoTeamBlue( pool.getDuoListPoolB().get( 3 ).getDuoTeam() );
                        pool.putFightListMapPoolB( TypeUtil.INTEGER_9, item.getFight3() );
                    }
                    if ( i == 2 )
                    {
                        item.getFight3().setDuoTeamRed( item.getDuoTeam() );
                        item.getFight3().setDuoTeamBlue( pool.getDuoListPoolB().get( 3 ).getDuoTeam() );
                        pool.putFightListMapPoolB( TypeUtil.INTEGER_7, item.getFight3() );
                    }
                    if ( i == 3 )
                    {
                        item.getFight3().setDuoTeamRed( item.getDuoTeam() );
                        item.getFight3().setDuoTeamBlue( pool.getDuoListPoolB().get( 3 ).getDuoTeam() );
                        pool.putFightListMapPoolB( TypeUtil.INTEGER_2, item.getFight3() );
                    }
                }

                if ( item.getFight4() != null )
                {
                    if ( item.getFight4().getWinnerId() != null
                        && !TypeUtil.isEmpty( item.getFight4().getWinnerId().longValue() ) )
                    {
                        if ( item.getFight4().getWinnerId().equals( item.getDuoTeam().getId() ) )
                        {
                            // the fight was won
                            item.getResult().setResultFight4( FIGHT_WIN );
                            item.getResult().setUbFight4( ( item.getDuoTeam().getId().equals( item.getFight4().getTeamIdRed() ) ) ? item.getFight4().getPointsRed()
                                                                          : item.getFight4().getPointsBlue() );
                            if ( item.getResult().getUbFight4() == TypeUtil.DOUBLE_EMPTY )
                            {
                                item.getResult().setUbFight4( 0 );
                            }
                        }
                        else
                        {
                            item.getResult().setResultFight4( FIGHT_LOST );
                            item.getResult().setUbFight4( ( item.getDuoTeam().getId().equals( item.getFight4().getTeamIdRed() ) ) ? item.getFight4().getPointsRed()
                                                                          : item.getFight4().getPointsBlue() );
                            if ( item.getResult().getUbFight4() == TypeUtil.DOUBLE_EMPTY )
                            {
                                item.getResult().setUbFight4( 0 );
                            }
                        }
                    }
                    // set in FightListMap
                    if ( i == 1 )
                    {
                        item.getFight4().setDuoTeamRed( item.getDuoTeam() );
                        item.getFight4().setDuoTeamBlue( pool.getDuoListPoolB().get( 4 ).getDuoTeam() );
                        pool.putFightListMapPoolB( TypeUtil.INTEGER_3, item.getFight4() );
                    }
                    if ( i == 2 )
                    {
                        item.getFight4().setDuoTeamRed( item.getDuoTeam() );
                        item.getFight4().setDuoTeamBlue( pool.getDuoListPoolB().get( 4 ).getDuoTeam() );
                        pool.putFightListMapPoolB( TypeUtil.INTEGER_10, item.getFight4() );
                    }
                    if ( i == 3 )
                    {
                        item.getFight4().setDuoTeamRed( item.getDuoTeam() );
                        item.getFight4().setDuoTeamBlue( pool.getDuoListPoolB().get( 4 ).getDuoTeam() );
                        pool.putFightListMapPoolB( TypeUtil.INTEGER_8, item.getFight4() );
                    }
                    if ( i == 4 )
                    {
                        item.getFight4().setDuoTeamRed( item.getDuoTeam() );
                        item.getFight4().setDuoTeamBlue( pool.getDuoListPoolB().get( 4 ).getDuoTeam() );
                        pool.putFightListMapPoolB( TypeUtil.INTEGER_5, item.getFight4() );
                    }
                }
                ranking = new DuoclassPoolRankingDTO();
                ranking.setId( item.getDuoTeam().getId() );
                ranking.setWins( item.getResult().getWinCount() );
                ranking.setUbs( item.getResult().getUBCount() );
                rankingList.add( ranking );
            }
            // triple place ranking
            rankingList = doRanking( pool.getFightListMapPoolB().values(), rankingList );

            for ( DuoDoublePoolItem itemB : pool.getDuoListPoolB() )
            {
                for ( DuoclassPoolRankingDTO dto : rankingList )
                {
                    if ( itemB.getDuoTeam().getId().equals( dto.getId() ) )
                    {
                        itemB.setPoolPlace( dto.getPlace() );
                        break;
                    }
                }
            }

            if ( isAllFightsAreOver( pool.getFightListMapPoolA().values() )
                && isAllFightsAreOver( pool.getFightListMapPoolB().values() ) )
            {
                DuoTeam fistplacePoolA = getFighterOfRank( 1, pool.getDuoListPoolA() );
                DuoTeam fistplacePoolB = getFighterOfRank( 1, pool.getDuoListPoolB() );

                DuoTeam secondplacePoolA = getFighterOfRank( 2, pool.getDuoListPoolA() );
                DuoTeam secondplacePoolB = getFighterOfRank( 2, pool.getDuoListPoolB() );

                // halfFinal 1
                if ( null == fistplacePoolA && null != secondplacePoolB )
                {
                    DuoFight halfFinalFight = fightDao.getDuoFight( pool.getHalfFinalFight1().getId() );
                    if ( isResetFinalFight )
                    {
                        halfFinalFight.resetForKoList();
                    }
                    halfFinalFight.setTeamIdRed( IValueConstants.LONG_MIN );
                    halfFinalFight.setTeamIdBlue( secondplacePoolB.getId() );
                    halfFinalFight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                    halfFinalFight.setModificationId( IValueConstants.SYSTEM_USER );
                    halfFinalFight.setWinnerId( secondplacePoolB.getId() );

                    pool.getHalfFinalFight1().resetForKoList();
                    pool.getHalfFinalFight1().setDuoTeamRed( new DuoTeam() );
                    pool.getHalfFinalFight1().setDuoTeamBlue( secondplacePoolB );
                    pool.getHalfFinalFight1().setTeamIdRed( TypeUtil.LONG_MIN );
                    pool.getHalfFinalFight1().setTeamIdBlue( secondplacePoolB.getId() );
                    pool.getHalfFinalFight1().setWinnerId( secondplacePoolB.getId() );
                }

                if ( null != fistplacePoolA && null == secondplacePoolB )
                {
                    DuoFight halfFinalFight = fightDao.getDuoFight( pool.getHalfFinalFight1().getId() );
                    if ( isResetFinalFight )
                    {
                        halfFinalFight.resetForKoList();
                    }
                    halfFinalFight.setTeamIdRed( fistplacePoolA.getId() );
                    halfFinalFight.setTeamIdBlue( IValueConstants.LONG_MIN );
                    halfFinalFight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                    halfFinalFight.setModificationId( IValueConstants.SYSTEM_USER );
                    halfFinalFight.setWinnerId( fistplacePoolA.getId() );

                    pool.getHalfFinalFight1().resetForKoList();
                    pool.getHalfFinalFight1().setDuoTeamRed( fistplacePoolA );
                    pool.getHalfFinalFight1().setDuoTeamBlue( new DuoTeam() );
                    pool.getHalfFinalFight1().setTeamIdRed( fistplacePoolA.getId() );
                    pool.getHalfFinalFight1().setTeamIdBlue( TypeUtil.LONG_MIN );
                    pool.getHalfFinalFight1().setWinnerId( fistplacePoolA.getId() );
                }

                if ( null != fistplacePoolA && null != secondplacePoolB )
                {

                    DuoFight halfFinalFight = fightDao.getDuoFight( pool.getHalfFinalFight1().getId() );
                    if ( halfFinalFight.getTeamIdRed() != fistplacePoolA.getId()
                        || halfFinalFight.getTeamIdBlue() != secondplacePoolB.getId() )
                    {

                        if ( isResetFinalFight )
                        {
                            halfFinalFight.resetForKoList();
                        }
                        halfFinalFight.setTeamIdRed( fistplacePoolA.getId() );
                        halfFinalFight.setTeamIdBlue( secondplacePoolB.getId() );
                        halfFinalFight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                        halfFinalFight.setModificationId( IValueConstants.SYSTEM_USER );

                        pool.getHalfFinalFight1().resetForKoList();
                        pool.getHalfFinalFight1().setDuoTeamRed( fistplacePoolA );
                        pool.getHalfFinalFight1().setDuoTeamBlue( secondplacePoolB );
                        pool.getHalfFinalFight1().setTeamIdRed( fistplacePoolA.getId() );
                        pool.getHalfFinalFight1().setTeamIdBlue( secondplacePoolB.getId() );
                        pool.getHalfFinalFight1().setWinnerId( TypeUtil.LONG_MIN );
                    }

                }
                // halfFinal
                if ( null == fistplacePoolB && null != secondplacePoolA )
                {
                    DuoFight halfFinalFight = fightDao.getDuoFight( pool.getHalfFinalFight2().getId() );
                    if ( isResetFinalFight )
                    {
                        halfFinalFight.resetForKoList();
                    }
                    halfFinalFight.setTeamIdRed( IValueConstants.LONG_MIN );
                    halfFinalFight.setTeamIdBlue( secondplacePoolA.getId() );
                    halfFinalFight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                    halfFinalFight.setModificationId( IValueConstants.SYSTEM_USER );
                    halfFinalFight.setWinnerId( secondplacePoolA.getId() );

                    pool.getHalfFinalFight2().resetForKoList();
                    pool.getHalfFinalFight2().setDuoTeamRed( new DuoTeam() );
                    pool.getHalfFinalFight2().setDuoTeamBlue( secondplacePoolA );
                    pool.getHalfFinalFight2().setTeamIdRed( TypeUtil.LONG_MIN );
                    pool.getHalfFinalFight2().setTeamIdBlue( secondplacePoolA.getId() );
                    pool.getHalfFinalFight2().setWinnerId( secondplacePoolA.getId() );
                }

                if ( null != fistplacePoolB && null == secondplacePoolA )
                {
                    DuoFight halfFinalFight = fightDao.getDuoFight( pool.getHalfFinalFight2().getId() );
                    if ( isResetFinalFight )
                    {
                        halfFinalFight.resetForKoList();
                    }
                    halfFinalFight.setTeamIdRed( fistplacePoolB.getId() );
                    halfFinalFight.setTeamIdBlue( IValueConstants.LONG_MIN );
                    halfFinalFight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                    halfFinalFight.setModificationId( IValueConstants.SYSTEM_USER );
                    halfFinalFight.setWinnerId( fistplacePoolB.getId() );

                    pool.getHalfFinalFight2().resetForKoList();
                    pool.getHalfFinalFight2().setDuoTeamRed( fistplacePoolB );
                    pool.getHalfFinalFight2().setDuoTeamBlue( new DuoTeam() );
                    pool.getHalfFinalFight2().setTeamIdRed( fistplacePoolB.getId() );
                    pool.getHalfFinalFight2().setTeamIdBlue( TypeUtil.LONG_MIN );
                    pool.getHalfFinalFight2().setWinnerId( fistplacePoolB.getId() );
                }

                if ( null != fistplacePoolB && null != secondplacePoolA )
                {

                    DuoFight halfFinalFight = fightDao.getDuoFight( pool.getHalfFinalFight2().getId() );
                    if ( halfFinalFight.getTeamIdRed() != fistplacePoolB.getId()
                        || halfFinalFight.getTeamIdBlue() != secondplacePoolA.getId() )
                    {

                        if ( isResetFinalFight )
                        {
                            halfFinalFight.resetForKoList();
                        }
                        halfFinalFight.setTeamIdRed( fistplacePoolB.getId() );
                        halfFinalFight.setTeamIdBlue( secondplacePoolA.getId() );
                        halfFinalFight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                        halfFinalFight.setModificationId( IValueConstants.SYSTEM_USER );

                        pool.getHalfFinalFight2().resetForKoList();
                        pool.getHalfFinalFight2().setDuoTeamRed( fistplacePoolB );
                        pool.getHalfFinalFight2().setDuoTeamBlue( secondplacePoolA );
                        pool.getHalfFinalFight2().setTeamIdRed( fistplacePoolB.getId() );
                        pool.getHalfFinalFight2().setTeamIdBlue( secondplacePoolA.getId() );
                        pool.getHalfFinalFight2().setWinnerId( TypeUtil.LONG_MIN );
                    }

                }

                // FinalFight

                if ( TypeUtil.LONG_EMPTY == pool.getHalfFinalFight2().getWinnerId()
                                || pool.getHalfFinalFight1().getWinnerId() == TypeUtil.LONG_EMPTY ){
                    // reset final fight on DB, when half finals are not finished
                    DuoFight finalFight = fightDao.getDuoFight( pool.getFinalFight().getId() );
                    finalFight.resetForKoList();
                }
                
                if ( TypeUtil.LONG_DEFAULT == pool.getHalfFinalFight2().getWinnerId()
                    && pool.getHalfFinalFight1().getWinnerId() > TypeUtil.LONG_DEFAULT )
                {

                    DuoFight finalFight = fightDao.getDuoFight( pool.getFinalFight().getId() );
                    if ( isResetFinalFight )
                    {
                        finalFight.resetForKoList();
                    }
                    finalFight.setTeamIdRed( pool.getHalfFinalFight1().getWinnerId() );
                    finalFight.setTeamIdBlue( IValueConstants.LONG_MIN );
                    finalFight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                    finalFight.setModificationId( IValueConstants.SYSTEM_USER );
                    finalFight.setWinnerId( pool.getHalfFinalFight1().getWinnerId() );

                    pool.getFinalFight().resetForKoList();
                    pool.getFinalFight().setDuoTeamRed( ( pool.getHalfFinalFight1().getWinnerId() == pool.getHalfFinalFight1().getTeamIdRed() ) ? pool.getHalfFinalFight1().getDuoTeamRed()
                                                                        : pool.getHalfFinalFight1().getDuoTeamBlue() );

                    pool.getFinalFight().setDuoTeamBlue( new DuoTeam() );
                    pool.getFinalFight().setTeamIdRed( pool.getHalfFinalFight1().getWinnerId() );
                    pool.getFinalFight().setTeamIdBlue( TypeUtil.LONG_MIN );
                    pool.getFinalFight().setWinnerId( pool.getHalfFinalFight1().getWinnerId() );
                }
                
                if ( TypeUtil.LONG_DEFAULT < pool.getHalfFinalFight2().getWinnerId()
                    && pool.getHalfFinalFight1().getWinnerId() == TypeUtil.LONG_DEFAULT )
                {
                    DuoFight finalFight = fightDao.getDuoFight( pool.getFinalFight().getId() );
                    if ( isResetFinalFight )
                    {
                        finalFight.resetForKoList();
                    }
                    finalFight.setTeamIdRed( IValueConstants.LONG_MIN );
                    finalFight.setTeamIdBlue( pool.getHalfFinalFight2().getWinnerId() );
                    finalFight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                    finalFight.setModificationId( IValueConstants.SYSTEM_USER );
                    finalFight.setWinnerId( pool.getHalfFinalFight2().getWinnerId() );

                    pool.getFinalFight().resetForKoList();
                    pool.getFinalFight().setDuoTeamRed( new DuoTeam() );
                    pool.getFinalFight().setDuoTeamBlue( ( pool.getHalfFinalFight2().getWinnerId() == pool.getHalfFinalFight2().getTeamIdRed() ) ? pool.getHalfFinalFight2().getDuoTeamRed()
                                                                         : pool.getHalfFinalFight2().getDuoTeamBlue() );

                    pool.getFinalFight().setTeamIdRed( TypeUtil.LONG_MIN );
                    pool.getFinalFight().setTeamIdBlue( pool.getHalfFinalFight2().getWinnerId() );
                    pool.getFinalFight().setWinnerId( pool.getHalfFinalFight2().getWinnerId() );
                }

                if ( TypeUtil.LONG_DEFAULT < pool.getHalfFinalFight2().getWinnerId()
                    && pool.getHalfFinalFight1().getWinnerId() > TypeUtil.LONG_DEFAULT )
                {
                    DuoFight finalFight = fightDao.getDuoFight( pool.getFinalFight().getId() );
                    if ( finalFight.getTeamIdRed() != pool.getHalfFinalFight1().getWinnerId()
                        || finalFight.getTeamIdBlue() != pool.getHalfFinalFight2().getWinnerId() )
                    {

                        if ( isResetFinalFight )
                        {
                            finalFight.resetForKoList();
                        }
                        finalFight.setTeamIdRed( pool.getHalfFinalFight1().getWinnerId() );
                        finalFight.setTeamIdBlue( pool.getHalfFinalFight2().getWinnerId() );
                        finalFight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                        finalFight.setModificationId( IValueConstants.SYSTEM_USER );

                        pool.getFinalFight().resetForKoList();
                        pool.getFinalFight().setDuoTeamRed( ( pool.getHalfFinalFight1().getWinnerId() == pool.getHalfFinalFight1().getTeamIdRed() ) ? pool.getHalfFinalFight1().getDuoTeamRed()
                                        : pool.getHalfFinalFight1().getDuoTeamBlue() );
                        pool.getFinalFight().setDuoTeamBlue( ( pool.getHalfFinalFight2().getWinnerId() == pool.getHalfFinalFight2().getTeamIdRed() ) ? pool.getHalfFinalFight2().getDuoTeamRed()
                                        : pool.getHalfFinalFight2().getDuoTeamBlue() );
                        pool.getFinalFight().setTeamIdRed( pool.getHalfFinalFight1().getWinnerId() );
                        pool.getFinalFight().setTeamIdBlue( pool.getHalfFinalFight2().getWinnerId() );
                        pool.getFinalFight().setWinnerId( TypeUtil.LONG_MIN );
                    }
                }
                
                
                doDoublePoolResults( pool );

            }

            return pool;
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    
    
    private void doDoublePoolResults( DuoDoublePoolClass pool )
    {
        // TODO: write Results
        if ( pool.getFinalFight().getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            // when final fight is over
            if ( pool.getFinalFight().getWinnerId().longValue() == TypeUtil.LONG_DEFAULT )
            {
                // double DQ both are 2nd place
                pool.getFinalFight().getDuoTeamBlue().setPlace( IValueConstants._2 );
                pool.getFinalFight().getDuoTeamRed().setPlace( IValueConstants._2 );
                                
                pool.getResultMap().put( Long.valueOf( 2 ), getAsList(pool.getFinalFight().getDuoTeamRed(),pool.getFinalFight().getDuoTeamBlue()));
            }

            if ( pool.getFinalFight().getWinnerId().longValue() == pool.getFinalFight().getTeamIdBlue() )
            {

                pool.getFinalFight().getDuoTeamBlue().setPlace( IValueConstants._1 );
                pool.getResultMap().put( Long.valueOf( 1 ), getAsList(pool.getFinalFight().getDuoTeamBlue(),null));              
                pool.getFinalFight().getDuoTeamRed().setPlace( IValueConstants._2 );
                pool.getResultMap().put( Long.valueOf( 2 ), getAsList(pool.getFinalFight().getDuoTeamRed(),null));    
            }

            if ( pool.getFinalFight().getWinnerId().longValue() == pool.getFinalFight().getTeamIdRed() )
            {

                pool.getFinalFight().getDuoTeamBlue().setPlace( IValueConstants._2 );
                pool.getResultMap().put( Long.valueOf( 2 ),
                                         getAsList( pool.getFinalFight().getDuoTeamBlue(), null ) );
                pool.getFinalFight().getDuoTeamRed().setPlace( IValueConstants._1 );
                pool.getResultMap().put( Long.valueOf( 1 ),
                                         getAsList( pool.getFinalFight().getDuoTeamRed(), null ) );
            }

            ArrayList<DuoTeam> rdList = new ArrayList<DuoTeam>();
            if ( pool.getHalfFinalFight1().getWinnerId().longValue() != pool.getHalfFinalFight1().getTeamIdRed() )
            {
                pool.getHalfFinalFight1().getDuoTeamRed().setPlace( IValueConstants._3 );
                rdList.add( pool.getHalfFinalFight1().getDuoTeamRed() );
            }

            if ( pool.getHalfFinalFight1().getWinnerId().longValue() != pool.getHalfFinalFight1().getTeamIdBlue() )
            {
                pool.getHalfFinalFight1().getDuoTeamBlue().setPlace( IValueConstants._3 );
                rdList.add( pool.getHalfFinalFight1().getDuoTeamBlue() );
            }
            
            if ( pool.getHalfFinalFight2().getWinnerId().longValue() != pool.getHalfFinalFight2().getTeamIdRed() )
            {
                pool.getHalfFinalFight2().getDuoTeamRed().setPlace( IValueConstants._3 );
                rdList.add( pool.getHalfFinalFight2().getDuoTeamRed() );
            }

            if ( pool.getHalfFinalFight2().getWinnerId().longValue() != pool.getHalfFinalFight2().getTeamIdBlue() )
            {
                pool.getHalfFinalFight2().getDuoTeamBlue().setPlace( IValueConstants._3 );
                rdList.add( pool.getHalfFinalFight2().getDuoTeamBlue());
            }
            pool.getResultMap().put( Long.valueOf( 3 ),rdList);

            // ranking of the pool fighter
            for ( DuoDoublePoolItem item : pool.getDuoListPoolA() )
            {

                if ( item.getDuoTeam().getId().longValue() == pool.getHalfFinalFight1().getTeamIdRed()||
                                item.getDuoTeam().getId().longValue() ==pool.getHalfFinalFight2().getTeamIdBlue())
                {
                    continue;
                }
                // 2 3 4 5
                // 3 5 7 9
                if ( item.getPoolPlace() == IValueConstants._1 )
                {
                    item.setPlace( IValueConstants._3 );
                }
                else
                {
                    item.setPlace( item.getPoolPlace() * 2 - 1 );
                }
            }

            for ( DuoDoublePoolItem item : pool.getDuoListPoolB() )
            {

                if ( item.getDuoTeam().getId().longValue() == pool.getHalfFinalFight2().getTeamIdBlue() ||
                                item.getDuoTeam().getId().longValue() ==pool.getHalfFinalFight1().getTeamIdBlue())
                {
                    continue;
                }
                // 2 3 4 5
                // 3 5 7 9
                if ( item.getPoolPlace() == IValueConstants._1 )
                {
                    item.setPlace( IValueConstants._3 );
                }
                else
                {
                    item.setPlace( item.getPoolPlace() * 2 - 1 );
                }
            }

        }
        else
        {
            // no final fight
            if ( isAllFightsAreOver( pool.getFightListMapPoolA().values() )
                && isAllFightsAreOver( pool.getFightListMapPoolB().values() ) )
            {
                // ranking of the pool fighter
                for ( DuoDoublePoolItem item : pool.getDuoListPoolA() )
                {

                    if ( item.getDuoTeam().getId().longValue() == pool.getFinalFight().getTeamIdRed() )
                    {
                        continue;
                    }
                    // 2 3 4 5
                    // 3 5 7 9
                    if ( item.getPoolPlace() == IValueConstants._1 )
                    {
                        item.setPlace( IValueConstants._3 );
                    }
                    else
                    {
                        item.setPlace( item.getPoolPlace() * 2 - 1 );
                    }
                }

                for ( DuoDoublePoolItem item : pool.getDuoListPoolB() )
                {

                    if ( item.getDuoTeam().getId().longValue() == pool.getFinalFight().getTeamIdBlue() )
                    {
                        continue;
                    }
                    // 2 3 4 5
                    // 3 5 7 9
                    if ( item.getPoolPlace() == IValueConstants._1 )
                    {
                        item.setPlace( IValueConstants._3 );
                    }
                    else
                    {
                        item.setPlace( item.getPoolPlace() * 2 - 1 );
                    }
                }
            }
        }
    }

    private ArrayList<DuoTeam> getAsList(DuoTeam team1,DuoTeam team2){
        
        ArrayList<DuoTeam> list =new ArrayList<DuoTeam>();
        
        if(team1 !=null)
            list.add( team1 );
        if(team2 !=null)
            list.add( team2 );
        
        
        return list;
    }
    

    
    /**
     * iterates the whole fightlt,  at least one fight not over --> false
     * 
     * @param fightList
     * @return
     */
    private static boolean isAllFightsAreOver( Collection<DuoFight> fightList )
    {
        for ( DuoFight fight : fightList )
        {
            if ( TypeUtil.LONG_MIN == fight.getWinnerId() )
            {
                return false;
            }
        }
        return true;
    }

    /**
     * get the id of the fighter with the given rank. if there are more than one fighter the method returns null
     * 
     * @param rank
     * @param itemList
     * @return
     */
    private DuoTeam getFighterOfRank( int rank, List<DuoDoublePoolItem> itemList )
    {
        DuoTeam duoTeam = null;
        for ( DuoDoublePoolItem item : itemList )
        {
            if ( item.getPoolPlace() == rank )
            {
                if ( duoTeam != null )
                {
                    return null;
                }
                else
                {
                    duoTeam = item.getDuoTeam();
                }
            }
        }
        return duoTeam;
    }

    /**
     * th method  for performing the ranking in a pool when there were no fight performed then no ranking
     * 
     * @param fightCollection
     * @param rankingL
     */
    private List<DuoclassPoolRankingDTO> doRanking( Collection<DuoFight> fightCollection,
                                                    List<DuoclassPoolRankingDTO> rankingList )
    {

        Collections.sort( rankingList );
        // return when no fight performed
        if ( rankingList.get( 0 ).getWins() < TypeUtil.INT_DEFAULT )
        {
            return rankingList;
        }

        boolean isdoublePlaces = false;
        int doublePlaces = 0;
        int place = 0;
        int wins = 0;
        double ubs = 0;

        List<Long> firstPlaceList = new ArrayList<Long>( 5 );
        List<Long> secondPlaceList = new ArrayList<Long>( 5 );
        List<Long> thirdPlaceList = new ArrayList<Long>( 5 );
        List<Long> forthPlaceList = new ArrayList<Long>( 5 );

        for ( int count = 0; count < rankingList.size(); count++ )
        {
            if ( rankingList.get( count ).getWins() == wins && rankingList.get( count ).getUbs() == ubs
                && rankingList.get( count ).getWins() != TypeUtil.INT_EMPTY )
            {
                // handle double places
                rankingList.get( count ).setPlace( place );
                doublePlaces++;
                isdoublePlaces = true;
                if ( place == 1 )
                {
                    firstPlaceList.add( Long.valueOf( rankingList.get( count ).getId() ) );
                }
                if ( place == 2 )
                {
                    secondPlaceList.add( Long.valueOf( rankingList.get( count ).getId() ) );
                }
                if ( place == 3 )
                {
                    thirdPlaceList.add( Long.valueOf( rankingList.get( count ).getId() ) );
                }
                if ( place == 4 )
                {
                    forthPlaceList.add( Long.valueOf( rankingList.get( count ).getId() ) );
                }
            }
            else
            {
                if ( rankingList.get( count ).getWins() != TypeUtil.INT_EMPTY )
                {
                    ++place;
                    place += doublePlaces;
                    rankingList.get( count ).setPlace( place );
                    doublePlaces = 0;
                    wins = rankingList.get( count ).getWins();
                    ubs = rankingList.get( count ).getUbs();

                    if ( place == 1 )
                    {
                        firstPlaceList.add( Long.valueOf( rankingList.get( count ).getId() ) );
                    }
                    if ( place == 2 )
                    {
                        secondPlaceList.add( Long.valueOf( rankingList.get( count ).getId() ) );
                    }
                    if ( place == 3 )
                    {
                        thirdPlaceList.add( Long.valueOf( rankingList.get( count ).getId() ) );
                    }
                    if ( place == 4 )
                    {
                        forthPlaceList.add( Long.valueOf( rankingList.get( count ).getId() ) );
                    }
                }
            }
        }

        if ( isdoublePlaces && rankingList.size() > 2 )
        {
            if ( firstPlaceList.size() == 2 )
            {
                rankingList = handleDoublePlaces( rankingList, fightCollection, firstPlaceList, 2 );
            }

            if ( secondPlaceList.size() == 2 )
            {
                rankingList = handleDoublePlaces( rankingList, fightCollection, secondPlaceList, 3 );
            }

            if ( thirdPlaceList.size() == 2 )
            {
                rankingList = handleDoublePlaces( rankingList, fightCollection, thirdPlaceList, 4 );
            }

            if ( forthPlaceList.size() == 2 )
            {
                rankingList = handleDoublePlaces( rankingList, fightCollection, forthPlaceList, 5 );
            }

            if ( firstPlaceList.size() == 3 )
            {
                rankingList = handle3SamePlaces( rankingList, fightCollection, firstPlaceList, 2 );
            }

            if ( secondPlaceList.size() == 3 )
            {
                rankingList = handle3SamePlaces( rankingList, fightCollection, secondPlaceList, 3 );
            }

            if ( thirdPlaceList.size() == 3 )
            {
                rankingList = handle3SamePlaces( rankingList, fightCollection, thirdPlaceList, 4 );
            }

            if ( forthPlaceList.size() == 3 )
            {
                rankingList = handle3SamePlaces( rankingList, fightCollection, forthPlaceList, 5 );
            }
        }
        return rankingList;
    }

    /**
     * handles the situation, when 2 fighter have the some wins and points
     * 
     * @param fightCollection
     * @param firstPlaceList
     */
    private static List<DuoclassPoolRankingDTO> handleDoublePlaces( List<DuoclassPoolRankingDTO> rankingList,
                                                                    Collection<DuoFight> fightCollection,
                                                                    List<Long> placeList, int place )
    {
        for ( DuoFight fight : fightCollection )
        {
            if ( BothFighterInList( placeList, fight ) )
            {
                // if the fighters are both in the lt, get the loser an set him a new place
                if ( fight.getWinnerId() == fight.getTeamIdRed() )
                {
                    for ( DuoclassPoolRankingDTO dto : rankingList )
                    {
                        if ( fight.getTeamIdBlue() == dto.getId() )
                        {
                            dto.setPlace( place );
                            break;
                        }
                    }
                    break;
                }
                if ( fight.getWinnerId() == fight.getTeamIdBlue() )
                {
                    for ( DuoclassPoolRankingDTO dto : rankingList )
                    {
                        if ( fight.getTeamIdRed() == dto.getId() )
                        {
                            dto.setPlace( place );
                            break;
                        }
                    }
                    break;
                }
            }
        }
        return rankingList;
    }

    /**
     * th function handles the situation, when 3 have the same place. We reduce the attention only to these 3 and cout
     * their wins in the 3 corresponding fights. If everyone has one win so we have three times th place. Else we have
     * to check the direct competition
     * 
     * @param rankingList
     * @param fightCollection
     * @param placeList
     * @param place
     * @return
     */
    private static List<DuoclassPoolRankingDTO> handle3SamePlaces( List<DuoclassPoolRankingDTO> rankingList,
                                                                   Collection<DuoFight> fightCollection,
                                                                   List<Long> placeList, int place )
    {
        List<DuoFight> fightList = new ArrayList<DuoFight>();
        Map<Long, Integer> fighterWinningMap = new HashMap<Long, Integer>( 3 );
        for ( DuoFight fight : fightCollection )
        {

            if ( BothFighterInList( placeList, fight ) )
            {
                fightList.add( fight );

                if ( !TypeUtil.isEmptyOrDefault( fight.getWinnerId() ) )
                {
                    if ( fighterWinningMap.containsKey( fight.getWinnerId() ) )
                    {
                        fighterWinningMap.put( fight.getWinnerId(), fighterWinningMap.get( fight.getWinnerId() + 1 ) );
                    }
                    else
                    {
                        fighterWinningMap.put( fight.getWinnerId(), 1 );
                    }
                }
            }
        }

        if ( fighterWinningMap.size() != 3 && fighterWinningMap.size() != 0 )
        {
            if ( fighterWinningMap.size() == 2 )
            {
                rankingList = handleWhen2InWinningMap( rankingList, place, fightList, fighterWinningMap, placeList );
            }

            if ( fighterWinningMap.size() == 1 )
            {
                rankingList = handleWhen1InWinningMap( rankingList, place, fightList, fighterWinningMap, placeList );
            }
        }
        return rankingList;
    }

    private static List<DuoclassPoolRankingDTO> handleWhen1InWinningMap( List<DuoclassPoolRankingDTO> rankingList,
                                                                         int place, List<DuoFight> fightList,
                                                                         Map<Long, Integer> fighterWinningMap,
                                                                         List<Long> placeList )
    {
        // TODO: get right infos to handle th
        if ( 1 != 2 )
            return rankingList;

        // get participant, which have no win in the winningMap
        Map<Long, Integer> fighterLoosingMap = new HashMap<Long, Integer>( 2 );
        for ( long item : placeList )
        {
            if ( fighterWinningMap.get( item ) == null )
            {
                fighterLoosingMap.put( item, TypeUtil.INT_DEFAULT );
            }
        }

        // get the fight of these participants
        for ( DuoFight item : fightList )
        {
            if ( fighterLoosingMap.get( item.getTeamIdRed() ) != null
                && fighterLoosingMap.get( item.getTeamIdBlue() ) != null )
            {
                // check the result of th fight to set the places
                if ( item.getWinnerId() == null || item.getWinnerId() <= TypeUtil.LONG_DEFAULT )
                {
                    fighterLoosingMap.clear();
                    fighterLoosingMap.put( item.getTeamIdRed(), TypeUtil.INTEGER_1 );
                    fighterLoosingMap.put( item.getTeamIdRed(), TypeUtil.INTEGER_1 );
                }

                if ( item.getWinnerId().equals( item.getTeamIdRed() ) )
                {
                    fighterLoosingMap.clear();
                    fighterLoosingMap.put( item.getTeamIdRed(), TypeUtil.INTEGER_1 );
                    fighterLoosingMap.put( item.getTeamIdBlue(), TypeUtil.INTEGER_2 );
                }

                if ( item.getWinnerId().equals( item.getTeamIdRed() ) )
                {
                    fighterLoosingMap.clear();
                    fighterLoosingMap.put( item.getTeamIdBlue(), TypeUtil.INTEGER_1 );
                    fighterLoosingMap.put( item.getTeamIdRed(), TypeUtil.INTEGER_2 );
                }
            }
        }

        // set the ranking
        for ( DuoclassPoolRankingDTO dto : rankingList )
        {
            if ( fighterLoosingMap.get( dto.getId() ) != null )
            {
                dto.setPlace( place + fighterLoosingMap.get( dto.getId() ) );
            }
        }

        return rankingList;
    }

    /**
     * method handles the situation, when 2 are in the winningmap
     * 
     * @param rankingList
     * @param place
     * @param fightList
     * @param fighterWinningMap
     * @return
     */
    private static List<DuoclassPoolRankingDTO> handleWhen2InWinningMap( List<DuoclassPoolRankingDTO> rankingList,
                                                                         int place, List<DuoFight> fightList,
                                                                         Map<Long, Integer> fighterWinningMap,
                                                                         List<Long> placeList )
    {
        // TODO: get right infos to handle th
        if ( 1 != 2 )
            return rankingList;

        Map<Long, Integer> placeMap = new HashMap<Long, Integer>( 3 );
        // when size == 3 or 0 do nothing,because three time the same place

        if ( fighterWinningMap.get( (Long) fighterWinningMap.keySet().toArray()[0] ) > fighterWinningMap.get( (Long) fighterWinningMap.keySet().toArray()[1] ) )
        {
            // id of fighterWinningMap.get( 0 ) leads, than id of fighterWinningMap.get( 1 ) than
            // id of not in lt
            placeMap =
                fillPlaceMap( placeMap, (Long) fighterWinningMap.keySet().toArray()[0],
                              (Long) fighterWinningMap.keySet().toArray()[1] );
        }

        if ( fighterWinningMap.get( (Long) fighterWinningMap.keySet().toArray()[0] ) < fighterWinningMap.get( (Long) fighterWinningMap.keySet().toArray()[1] ) )
        {
            // id of fighterWinningMap.get( 1 ) leads, than id of fighterWinningMap.get( 0 ) than
            // id of not in lt
            placeMap =
                fillPlaceMap( placeMap, (Long) fighterWinningMap.keySet().toArray()[1],
                              (Long) fighterWinningMap.keySet().toArray()[0] );
        }

        if ( fighterWinningMap.get( ( (Long) fighterWinningMap.keySet().toArray()[0] ) ).equals( fighterWinningMap.get( (Long) fighterWinningMap.keySet().toArray()[1] ) ) )
        {
            // id of fighterWinningMap.get( 0 ) == fighterWinningMap.get( 1 ) than
            // id of not in lt
            for ( DuoFight fight : fightList )
            {
                if ( ( fight.getTeamIdRed() == (Long) fighterWinningMap.keySet().toArray()[0] || fight.getTeamIdBlue() == (Long) fighterWinningMap.keySet().toArray()[0] )
                    && ( fight.getTeamIdRed() == (Long) fighterWinningMap.keySet().toArray()[1] || fight.getTeamIdBlue() == (Long) fighterWinningMap.keySet().toArray()[1] ) )
                {
                    if ( fight.getWinnerId().equals( fight.getTeamIdRed() ) )
                    {
                        placeMap = fillPlaceMap( placeMap, fight.getTeamIdRed(), fight.getTeamIdBlue() );
                        break;
                    }

                    if ( fight.getWinnerId().equals( fight.getTeamIdBlue() ) )
                    {
                        placeMap = fillPlaceMap( placeMap, fight.getTeamIdBlue(), fight.getTeamIdRed() );
                        break;
                    }
                }
            }
        }
        // set new places
        for ( DuoclassPoolRankingDTO dto : rankingList )
        {
            if ( placeMap.get( dto.getId() ) != null )
            {
                if ( TypeUtil.INTEGER_1.equals( placeMap.get( dto.getId() ) ) )
                    dto.setPlace( place + 1 );
            }
            else if ( placeList.contains( dto.getId() ) && fighterWinningMap.get( dto.getId() ) == null )
                dto.setPlace( place + 2 );
        }
        return rankingList;
    }

    /**
     * sets ids in placeMap. 1st id has value 0 and 2nd has value 1
     * 
     * @param placeMap
     * @param fight
     */
    private static Map<Long, Integer> fillPlaceMap( Map<Long, Integer> placeMap, long firstId, long secondId )
    {
        Map<Long, Integer> ret = placeMap;
        ret.put( firstId, 0 );
        ret.put( secondId, 1 );

        return ret;
    }

    /**
     * Check if the fighters blue and red are in the List th  import for handling double places
     * 
     * @param firstPlaceList
     * @param fight
     */
    private static boolean BothFighterInList( List<Long> firstPlaceList, DuoFight fight )
    {
        if ( firstPlaceList.contains( fight.getTeamIdRed() ) && firstPlaceList.contains( fight.getTeamIdBlue() ) )
        {
            return true;
        }
        return false;
    }
}