/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightingclassPoolsImpl.java
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

package de.jjw.service.impl.fighting;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.jjw.dao.fighting.FightDao;
import de.jjw.dao.fighting.FightingclassDao;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.fighting.Fight;
import de.jjw.model.fighting.Fighter;
import de.jjw.model.fighting.FightingDoublePoolClass;
import de.jjw.model.fighting.FightingDoublePoolItem;
import de.jjw.model.fighting.FightingSimplePoolClass;
import de.jjw.model.fighting.FightingSimplePoolItem;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.mapper.fighting.FightingDoublePoolMapper;
import de.jjw.service.mapper.fighting.FightingSimplePoolMapper;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;

public class FightingclassPoolsImpl
{
    private static int FIGHT_WIN = 1;

    private static int FIGHT_LOST = 0;

    private static int FIGHT_DIFFERENT_BORDER = 14;

    public FightingSimplePoolClass getFightingclassSimplePool( Long fightingclassId, FightingclassDao fightingclassDao,
                                                               int revenge )
        throws JJWManagerException
    {
        try
        {
            FightingSimplePoolClass pool =
                FightingSimplePoolMapper.mapFightingSimplePoolClassFromDB( fightingclassDao.getFightingclassSimplePool( fightingclassId ) );
            pool.setId( fightingclassId );
            // compute and fullfill the pool
            FightingclassPoolRankingDTO ranking;
            List<FightingclassPoolRankingDTO> rankingList = new ArrayList<FightingclassPoolRankingDTO>( 5 );
            int i = 0;
            for ( FightingSimplePoolItem item : pool.getFighterList() )
            {
                i++;
                if ( item.getFight1() != null )
                {
                    if ( item.getFight1().getWinnerId() != null
                        && !TypeUtil.isEmpty( item.getFight1().getWinnerId().longValue() ) )
                    {
                        if ( item.getFight1().getWinnerId().equals( item.getFighter().getId() ) )
                        {
                            // the fight was won
                            item.getResult().setResultFight1( FIGHT_WIN );
                            int diff = Math.abs( item.getFight1().getPointsBlue() - item.getFight1().getPointsRed() );
                            if ( diff > FIGHT_DIFFERENT_BORDER )
                            {
                                item.getResult().setUbFight1( FIGHT_DIFFERENT_BORDER );
                            }
                            else
                            {
                                item.getResult().setUbFight1( diff );
                            }
                        }
                        else
                        {
                            item.getResult().setResultFight1( FIGHT_LOST );
                            item.getResult().setUbFight1( FIGHT_LOST );
                        }
                    }
                    // set in FightListMap
                    if ( i == 1 )
                    {
                        item.getFight1().setFighterRed( item.getFighter() );
                        item.getFight1().setFighterBlue( pool.getFighterList().get( 1 ).getFighter() );
                        pool.putFightListMap( TypeUtil.INTEGER_1, item.getFight1() );
                    }
                }

                if ( item.getFight2() != null )
                {
                    if ( item.getFight2().getWinnerId() != null
                        && !TypeUtil.isEmpty( item.getFight2().getWinnerId().longValue() ) )
                    {
                        if ( item.getFight2().getWinnerId().equals( item.getFighter().getId() ) )
                        {
                            // the fight was won
                            item.getResult().setResultFight2( FIGHT_WIN );
                            int diff = Math.abs( item.getFight2().getPointsBlue() - item.getFight2().getPointsRed() );
                            if ( diff > FIGHT_DIFFERENT_BORDER )
                            {
                                item.getResult().setUbFight2( FIGHT_DIFFERENT_BORDER );
                            }
                            else
                            {
                                item.getResult().setUbFight2( diff );
                            }
                        }
                        else
                        {
                            item.getResult().setResultFight2( FIGHT_LOST );
                            item.getResult().setUbFight2( FIGHT_LOST );
                        }
                    }
                    // set in FightListMap
                    if ( i == 1 )
                    {
                        if ( pool.getFighterList().size() == 2 && ( revenge == 1 || revenge == 2 ) )
                        {
                            item.getFight2().setFighterRed( pool.getFighterList().get( 1 ).getFighter() );
                            item.getFight2().setFighterBlue( item.getFighter() );
                            pool.putFightListMap( TypeUtil.INTEGER_4, item.getFight2() );
                        }
                        else
                        {
                            item.getFight2().setFighterRed( item.getFighter() );
                            item.getFight2().setFighterBlue( pool.getFighterList().get( 2 ).getFighter() );
                            pool.putFightListMap( TypeUtil.INTEGER_6, item.getFight2() );
                        }
                    }
                    if ( i == 2 )
                    {
                        if ( pool.getFighterList().size() != 2 )
                        {
                            item.getFight2().setFighterRed( item.getFighter() );
                            item.getFight2().setFighterBlue( pool.getFighterList().get( 2 ).getFighter() );
                            pool.putFightListMap( TypeUtil.INTEGER_4, item.getFight2() );
                        }
                    }
                }

                if ( item.getFight3() != null )
                {
                    if ( item.getFight3().getWinnerId() != null
                        && !TypeUtil.isEmpty( item.getFight3().getWinnerId().longValue() ) )
                    {
                        if ( item.getFight3().getWinnerId().equals( item.getFighter().getId() ) )
                        {
                            // the fight was won
                            item.getResult().setResultFight3( FIGHT_WIN );
                            int diff = Math.abs( item.getFight3().getPointsBlue() - item.getFight3().getPointsRed() );
                            if ( diff > FIGHT_DIFFERENT_BORDER )
                            {
                                item.getResult().setUbFight3( FIGHT_DIFFERENT_BORDER );
                            }
                            else
                            {
                                item.getResult().setUbFight3( diff );
                            }
                        }
                        else
                        {
                            item.getResult().setResultFight3( FIGHT_LOST );
                            item.getResult().setUbFight3( FIGHT_LOST );
                        }
                    }
                    // set in FightListMap

                    if ( i == 1 )
                    {
                        if ( pool.getFighterList().size() == 2 && revenge == 2 )
                        {
                            item.getFight3().setFighterRed( item.getFighter() );
                            item.getFight3().setFighterBlue( pool.getFighterList().get( 1 ).getFighter() );
                            pool.putFightListMap( TypeUtil.INTEGER_6, item.getFight3() );
                        }
                        else
                        {
                            item.getFight3().setFighterRed( item.getFighter() );
                            item.getFight3().setFighterBlue( pool.getFighterList().get( 3 ).getFighter() );
                            pool.putFightListMap( TypeUtil.INTEGER_9, item.getFight3() );
                        }
                    }
                    if ( i == 2 && pool.getFighterList().size() != 2 )
                    {
                        item.getFight3().setFighterRed( item.getFighter() );
                        item.getFight3().setFighterBlue( pool.getFighterList().get( 3 ).getFighter() );
                        pool.putFightListMap( TypeUtil.INTEGER_7, item.getFight3() );
                    }
                    if ( i == 3 && pool.getFighterList().size() != 2 )
                    {
                        item.getFight3().setFighterRed( item.getFighter() );
                        item.getFight3().setFighterBlue( pool.getFighterList().get( 3 ).getFighter() );
                        pool.putFightListMap( TypeUtil.INTEGER_2, item.getFight3() );
                    }
                }

                if ( item.getFight4() != null )
                {
                    if ( item.getFight4().getWinnerId() != null
                        && !TypeUtil.isEmpty( item.getFight4().getWinnerId().longValue() ) )
                    {
                        if ( item.getFight4().getWinnerId().equals( item.getFighter().getId() ) )
                        {
                            // the fight was won
                            item.getResult().setResultFight4( FIGHT_WIN );
                            int diff = Math.abs( item.getFight4().getPointsBlue() - item.getFight4().getPointsRed() );
                            if ( diff > FIGHT_DIFFERENT_BORDER )
                            {
                                item.getResult().setUbFight4( FIGHT_DIFFERENT_BORDER );
                            }
                            else
                            {
                                item.getResult().setUbFight4( diff );
                            }
                        }
                        else
                        {
                            item.getResult().setResultFight4( FIGHT_LOST );
                            item.getResult().setUbFight4( FIGHT_LOST );
                        }
                    }
                    // set in FightListMap
                    if ( i == 1 )
                    {
                        item.getFight4().setFighterRed( item.getFighter() );
                        item.getFight4().setFighterBlue( pool.getFighterList().get( 4 ).getFighter() );
                        pool.putFightListMap( TypeUtil.INTEGER_3, item.getFight4() );
                    }
                    if ( i == 2 )
                    {
                        item.getFight4().setFighterRed( item.getFighter() );
                        item.getFight4().setFighterBlue( pool.getFighterList().get( 4 ).getFighter() );
                        pool.putFightListMap( TypeUtil.INTEGER_10, item.getFight4() );
                    }
                    if ( i == 3 )
                    {
                        item.getFight4().setFighterRed( item.getFighter() );
                        item.getFight4().setFighterBlue( pool.getFighterList().get( 4 ).getFighter() );
                        pool.putFightListMap( TypeUtil.INTEGER_8, item.getFight4() );
                    }
                    if ( i == 4 )
                    {
                        item.getFight4().setFighterRed( item.getFighter() );
                        item.getFight4().setFighterBlue( pool.getFighterList().get( 4 ).getFighter() );
                        pool.putFightListMap( TypeUtil.INTEGER_5, item.getFight4() );
                    }
                }
                ranking = new FightingclassPoolRankingDTO();
                ranking.setId( item.getFighter().getId() );
                ranking.setWins( item.getResult().getWinCount() );
                ranking.setUbs( item.getResult().getUBCount() );
                rankingList.add( ranking );
            }
            if ( pool.getFighterList().size() > 1 )
            {
                // triple place ranking
                rankingList = doRanking( pool.getFightListMap().values(), rankingList );

                for ( FightingSimplePoolItem item : pool.getFighterList() )
                {
                    for ( FightingclassPoolRankingDTO dto : rankingList )
                    {
                        if ( item.getFighter().getId().equals( dto.getId() ) )
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

    /**
     * @param fightingclassId
     * @param fightingclassDao
     * @param fightDao
     * @param isResetFinalFight if false, we don't Rest the Data of the existing finalFight, this sould only be if this
     *            class is called after saving the finalFight results
     * @return
     * @throws JJWManagerException
     */
    public FightingDoublePoolClass getFightingclassDoublePool( Long fightingclassId, FightingclassDao fightingclassDao,
                                                               FightDao fightDao, boolean isResetFinalFight )
        throws JJWManagerException
    {
        try
        {
            FightingDoublePoolClass pool =
                FightingDoublePoolMapper.mapFightingDoubePoolClassFromDB( fightingclassDao.getFightingclassDoublePool( fightingclassId ) );
            pool.setId( fightingclassId );
            // compute and fullfill the pool
            FightingclassPoolRankingDTO ranking;
            List<FightingclassPoolRankingDTO> rankingList = new ArrayList<FightingclassPoolRankingDTO>( 5 );
            int i = 0;
            for ( FightingDoublePoolItem item : pool.getFighterListPoolA() )
            {
                i++;
                if ( item.getFight1() != null )
                {
                    if ( item.getFight1().getWinnerId() != null
                        && !TypeUtil.isEmpty( item.getFight1().getWinnerId().longValue() ) )
                    {
                        if ( item.getFight1().getWinnerId().equals( item.getFighter().getId() ) )
                        {
                            // the fight was won
                            item.getResult().setResultFight1( FIGHT_WIN );
                            int diff = Math.abs( item.getFight1().getPointsBlue() - item.getFight1().getPointsRed() );
                            if ( diff > FIGHT_DIFFERENT_BORDER )
                            {
                                item.getResult().setUbFight1( FIGHT_DIFFERENT_BORDER );
                            }
                            else
                            {
                                item.getResult().setUbFight1( diff );
                            }
                        }
                        else
                        {
                            item.getResult().setResultFight1( FIGHT_LOST );
                            item.getResult().setUbFight1( FIGHT_LOST );
                        }
                    }
                    // set in FightListMap
                    if ( i == 1 )
                    {
                        item.getFight1().setFighterRed( item.getFighter() );
                        item.getFight1().setFighterBlue( pool.getFighterListPoolA().get( 1 ).getFighter() );
                        pool.putFightListMapPoolA( TypeUtil.INTEGER_1, item.getFight1() );
                    }
                }

                if ( item.getFight2() != null )
                {
                    if ( item.getFight2().getWinnerId() != null
                        && !TypeUtil.isEmpty( item.getFight2().getWinnerId().longValue() ) )
                    {
                        if ( item.getFight2().getWinnerId().equals( item.getFighter().getId() ) )
                        {
                            // the fight was won
                            item.getResult().setResultFight2( FIGHT_WIN );
                            int diff = Math.abs( item.getFight2().getPointsBlue() - item.getFight2().getPointsRed() );
                            if ( diff > FIGHT_DIFFERENT_BORDER )
                            {
                                item.getResult().setUbFight2( FIGHT_DIFFERENT_BORDER );
                            }
                            else
                            {
                                item.getResult().setUbFight2( diff );
                            }
                        }
                        else
                        {
                            item.getResult().setResultFight2( FIGHT_LOST );
                            item.getResult().setUbFight2( FIGHT_LOST );
                        }
                    }
                    // set in FightListMap
                    if ( i == 1 )
                    {
                        item.getFight2().setFighterRed( item.getFighter() );
                        item.getFight2().setFighterBlue( pool.getFighterListPoolA().get( 2 ).getFighter() );
                        pool.putFightListMapPoolA( TypeUtil.INTEGER_6, item.getFight2() );
                    }
                    if ( i == 2 )
                    {
                        item.getFight2().setFighterRed( item.getFighter() );
                        item.getFight2().setFighterBlue( pool.getFighterListPoolA().get( 2 ).getFighter() );
                        pool.putFightListMapPoolA( TypeUtil.INTEGER_4, item.getFight2() );
                    }
                }

                if ( item.getFight3() != null )
                {
                    if ( item.getFight3().getWinnerId() != null
                        && !TypeUtil.isEmpty( item.getFight3().getWinnerId().longValue() ) )
                    {
                        if ( item.getFight3().getWinnerId().equals( item.getFighter().getId() ) )
                        {
                            // the fight was won
                            item.getResult().setResultFight3( FIGHT_WIN );
                            int diff = Math.abs( item.getFight3().getPointsBlue() - item.getFight3().getPointsRed() );
                            if ( diff > FIGHT_DIFFERENT_BORDER )
                            {
                                item.getResult().setUbFight3( FIGHT_DIFFERENT_BORDER );
                            }
                            else
                            {
                                item.getResult().setUbFight3( diff );
                            }
                        }
                        else
                        {
                            item.getResult().setResultFight3( FIGHT_LOST );
                            item.getResult().setUbFight3( FIGHT_LOST );
                        }
                    }
                    // set in FightListMap

                    if ( i == 1 )
                    {
                        item.getFight3().setFighterRed( item.getFighter() );
                        item.getFight3().setFighterBlue( pool.getFighterListPoolA().get( 3 ).getFighter() );
                        pool.putFightListMapPoolA( TypeUtil.INTEGER_9, item.getFight3() );
                    }
                    if ( i == 2 )
                    {
                        item.getFight3().setFighterRed( item.getFighter() );
                        item.getFight3().setFighterBlue( pool.getFighterListPoolA().get( 3 ).getFighter() );
                        pool.putFightListMapPoolA( TypeUtil.INTEGER_7, item.getFight3() );
                    }
                    if ( i == 3 )
                    {
                        item.getFight3().setFighterRed( item.getFighter() );
                        item.getFight3().setFighterBlue( pool.getFighterListPoolA().get( 3 ).getFighter() );
                        pool.putFightListMapPoolA( TypeUtil.INTEGER_2, item.getFight3() );
                    }
                }

                if ( item.getFight4() != null )
                {
                    if ( item.getFight4().getWinnerId() != null
                        && !TypeUtil.isEmpty( item.getFight4().getWinnerId().longValue() ) )
                    {
                        if ( item.getFight4().getWinnerId().equals( item.getFighter().getId() ) )
                        {
                            // the fight was won
                            item.getResult().setResultFight4( FIGHT_WIN );
                            int diff = Math.abs( item.getFight4().getPointsBlue() - item.getFight4().getPointsRed() );
                            if ( diff > FIGHT_DIFFERENT_BORDER )
                            {
                                item.getResult().setUbFight4( FIGHT_DIFFERENT_BORDER );
                            }
                            else
                            {
                                item.getResult().setUbFight4( diff );
                            }
                        }
                        else
                        {
                            item.getResult().setResultFight4( FIGHT_LOST );
                            item.getResult().setUbFight4( FIGHT_LOST );
                        }
                    }
                    // set in FightListMap
                    if ( i == 1 )
                    {
                        item.getFight4().setFighterRed( item.getFighter() );
                        item.getFight4().setFighterBlue( pool.getFighterListPoolA().get( 4 ).getFighter() );
                        pool.putFightListMapPoolA( TypeUtil.INTEGER_3, item.getFight4() );
                    }
                    if ( i == 2 )
                    {
                        item.getFight4().setFighterRed( item.getFighter() );
                        item.getFight4().setFighterBlue( pool.getFighterListPoolA().get( 4 ).getFighter() );
                        pool.putFightListMapPoolA( TypeUtil.INTEGER_10, item.getFight4() );
                    }
                    if ( i == 3 )
                    {
                        item.getFight4().setFighterRed( item.getFighter() );
                        item.getFight4().setFighterBlue( pool.getFighterListPoolA().get( 4 ).getFighter() );
                        pool.putFightListMapPoolA( TypeUtil.INTEGER_8, item.getFight4() );
                    }
                    if ( i == 4 )
                    {
                        item.getFight4().setFighterRed( item.getFighter() );
                        item.getFight4().setFighterBlue( pool.getFighterListPoolA().get( 4 ).getFighter() );
                        pool.putFightListMapPoolA( TypeUtil.INTEGER_5, item.getFight4() );
                    }
                }
                ranking = new FightingclassPoolRankingDTO();
                ranking.setId( item.getFighter().getId() );
                ranking.setWins( item.getResult().getWinCount() );
                ranking.setUbs( item.getResult().getUBCount() );
                rankingList.add( ranking );
            }
            // triple place ranking
            rankingList = doRanking( pool.getFightListMapPoolA().values(), rankingList );

            for ( FightingDoublePoolItem item : pool.getFighterListPoolA() )
            {
                for ( FightingclassPoolRankingDTO dto : rankingList )
                {
                    if ( item.getFighter().getId().equals( dto.getId() ) )
                    {
                        item.setPoolPlace( dto.getPlace() );
                        break;
                    }
                }
            }

            // ---------------------------------------------------------------------------------------------
            i = 0;
            rankingList = new ArrayList<FightingclassPoolRankingDTO>( 5 );
            for ( FightingDoublePoolItem item : pool.getFighterListPoolB() )
            {
                i++;
                if ( item.getFight1() != null )
                {
                    if ( item.getFight1().getWinnerId() != null
                        && !TypeUtil.isEmpty( item.getFight1().getWinnerId().longValue() ) )
                    {
                        if ( item.getFight1().getWinnerId().equals( item.getFighter().getId() ) )
                        {
                            // the fight was won
                            item.getResult().setResultFight1( FIGHT_WIN );
                            int diff = Math.abs( item.getFight1().getPointsBlue() - item.getFight1().getPointsRed() );
                            if ( diff > FIGHT_DIFFERENT_BORDER )
                            {
                                item.getResult().setUbFight1( FIGHT_DIFFERENT_BORDER );
                            }
                            else
                            {
                                item.getResult().setUbFight1( diff );
                            }
                        }
                        else
                        {
                            item.getResult().setResultFight1( FIGHT_LOST );
                            item.getResult().setUbFight1( FIGHT_LOST );
                        }
                    }
                    // set in FightListMap
                    if ( i == 1 )
                    {
                        item.getFight1().setFighterRed( item.getFighter() );
                        item.getFight1().setFighterBlue( pool.getFighterListPoolB().get( 1 ).getFighter() );
                        pool.putFightListMapPoolB( TypeUtil.INTEGER_1, item.getFight1() );
                    }
                }

                if ( item.getFight2() != null )
                {
                    if ( item.getFight2().getWinnerId() != null
                        && !TypeUtil.isEmpty( item.getFight2().getWinnerId().longValue() ) )
                    {
                        if ( item.getFight2().getWinnerId().equals( item.getFighter().getId() ) )
                        {
                            // the fight was won
                            item.getResult().setResultFight2( FIGHT_WIN );
                            int diff = Math.abs( item.getFight2().getPointsBlue() - item.getFight2().getPointsRed() );
                            if ( diff > FIGHT_DIFFERENT_BORDER )
                            {
                                item.getResult().setUbFight2( FIGHT_DIFFERENT_BORDER );
                            }
                            else
                            {
                                item.getResult().setUbFight2( diff );
                            }
                        }
                        else
                        {
                            item.getResult().setResultFight2( FIGHT_LOST );
                            item.getResult().setUbFight2( FIGHT_LOST );
                        }
                    }
                    // set in FightListMap
                    if ( i == 1 )
                    {
                        item.getFight2().setFighterRed( item.getFighter() );
                        item.getFight2().setFighterBlue( pool.getFighterListPoolB().get( 2 ).getFighter() );
                        pool.putFightListMapPoolB( TypeUtil.INTEGER_6, item.getFight2() );
                    }
                    if ( i == 2 )
                    {
                        item.getFight2().setFighterRed( item.getFighter() );
                        item.getFight2().setFighterBlue( pool.getFighterListPoolB().get( 2 ).getFighter() );
                        pool.putFightListMapPoolB( TypeUtil.INTEGER_4, item.getFight2() );
                    }
                }

                if ( item.getFight3() != null )
                {
                    if ( item.getFight3().getWinnerId() != null
                        && !TypeUtil.isEmpty( item.getFight3().getWinnerId().longValue() ) )
                    {
                        if ( item.getFight3().getWinnerId().equals( item.getFighter().getId() ) )
                        {
                            // the fight was won
                            item.getResult().setResultFight3( FIGHT_WIN );
                            int diff = Math.abs( item.getFight3().getPointsBlue() - item.getFight3().getPointsRed() );
                            if ( diff > FIGHT_DIFFERENT_BORDER )
                            {
                                item.getResult().setUbFight3( FIGHT_DIFFERENT_BORDER );
                            }
                            else
                            {
                                item.getResult().setUbFight3( diff );
                            }
                        }
                        else
                        {
                            item.getResult().setResultFight3( FIGHT_LOST );
                            item.getResult().setUbFight3( FIGHT_LOST );
                        }
                    }
                    // set in FightListMap

                    if ( i == 1 )
                    {
                        item.getFight3().setFighterRed( item.getFighter() );
                        item.getFight3().setFighterBlue( pool.getFighterListPoolB().get( 3 ).getFighter() );
                        pool.putFightListMapPoolB( TypeUtil.INTEGER_9, item.getFight3() );
                    }
                    if ( i == 2 )
                    {
                        item.getFight3().setFighterRed( item.getFighter() );
                        item.getFight3().setFighterBlue( pool.getFighterListPoolB().get( 3 ).getFighter() );
                        pool.putFightListMapPoolB( TypeUtil.INTEGER_7, item.getFight3() );
                    }
                    if ( i == 3 )
                    {
                        item.getFight3().setFighterRed( item.getFighter() );
                        item.getFight3().setFighterBlue( pool.getFighterListPoolB().get( 3 ).getFighter() );
                        pool.putFightListMapPoolB( TypeUtil.INTEGER_2, item.getFight3() );
                    }
                }

                if ( item.getFight4() != null )
                {
                    if ( item.getFight4().getWinnerId() != null
                        && !TypeUtil.isEmpty( item.getFight4().getWinnerId().longValue() ) )
                    {
                        if ( item.getFight4().getWinnerId().equals( item.getFighter().getId() ) )
                        {
                            // the fight was won
                            item.getResult().setResultFight4( FIGHT_WIN );
                            int diff = Math.abs( item.getFight4().getPointsBlue() - item.getFight4().getPointsRed() );
                            if ( diff > FIGHT_DIFFERENT_BORDER )
                            {
                                item.getResult().setUbFight4( FIGHT_DIFFERENT_BORDER );
                            }
                            else
                            {
                                item.getResult().setUbFight4( diff );
                            }
                        }
                        else
                        {
                            item.getResult().setResultFight4( FIGHT_LOST );
                            item.getResult().setUbFight4( FIGHT_LOST );
                        }
                    }
                    // set in FightListMap
                    if ( i == 1 )
                    {
                        item.getFight4().setFighterRed( item.getFighter() );
                        item.getFight4().setFighterBlue( pool.getFighterListPoolB().get( 4 ).getFighter() );
                        pool.putFightListMapPoolB( TypeUtil.INTEGER_3, item.getFight4() );
                    }
                    if ( i == 2 )
                    {
                        item.getFight4().setFighterRed( item.getFighter() );
                        item.getFight4().setFighterBlue( pool.getFighterListPoolB().get( 4 ).getFighter() );
                        pool.putFightListMapPoolB( TypeUtil.INTEGER_10, item.getFight4() );
                    }
                    if ( i == 3 )
                    {
                        item.getFight4().setFighterRed( item.getFighter() );
                        item.getFight4().setFighterBlue( pool.getFighterListPoolB().get( 4 ).getFighter() );
                        pool.putFightListMapPoolB( TypeUtil.INTEGER_8, item.getFight4() );
                    }
                    if ( i == 4 )
                    {
                        item.getFight4().setFighterRed( item.getFighter() );
                        item.getFight4().setFighterBlue( pool.getFighterListPoolB().get( 4 ).getFighter() );
                        pool.putFightListMapPoolB( TypeUtil.INTEGER_5, item.getFight4() );
                    }
                }
                ranking = new FightingclassPoolRankingDTO();
                ranking.setId( item.getFighter().getId() );
                ranking.setWins( item.getResult().getWinCount() );
                ranking.setUbs( item.getResult().getUBCount() );
                rankingList.add( ranking );
            }
            // triple place ranking
            rankingList = doRanking( pool.getFightListMapPoolB().values(), rankingList );

            for ( FightingDoublePoolItem itemB : pool.getFighterListPoolB() )
            {
                for ( FightingclassPoolRankingDTO dto : rankingList )
                {
                    if ( itemB.getFighter().getId().equals( dto.getId() ) )
                    {
                        itemB.setPoolPlace( dto.getPlace() );
                        break;
                    }
                }
            }

            if ( isAllFightsAreOver( pool.getFightListMapPoolA().values() )
                && isAllFightsAreOver( pool.getFightListMapPoolB().values() ) )
            {
                Fighter fistplacePoolA = getFighterOfRank( 1, pool.getFighterListPoolA() );
                Fighter fistplacePoolB = getFighterOfRank( 1, pool.getFighterListPoolB() );
                Fighter secondplacePoolA = getFighterOfRank( 2, pool.getFighterListPoolA() );
                Fighter secondplacePoolB = getFighterOfRank( 2, pool.getFighterListPoolB() );

                // halfFinal 1
                if ( null == fistplacePoolA && null != secondplacePoolB )
                {
                    Fight halfFinalFight = fightDao.getFight( pool.getHalfFinalFight1().getId() );
                    if ( isResetFinalFight )
                    {
                        halfFinalFight.resetForKoList();
                    }
                    halfFinalFight.setFighterIdRed( IValueConstants.LONG_MIN );
                    halfFinalFight.setFighterIdBlue( secondplacePoolB.getId() );
                    halfFinalFight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                    halfFinalFight.setModificationId( IValueConstants.SYSTEM_USER );
                    halfFinalFight.setWinnerId( secondplacePoolB.getId() );

                    pool.getHalfFinalFight1().resetForKoList();
                    pool.getHalfFinalFight1().setFighterRed( new Fighter() );
                    pool.getHalfFinalFight1().setFighterBlue( secondplacePoolB );
                    pool.getHalfFinalFight1().setFighterIdRed( TypeUtil.LONG_MIN );
                    pool.getHalfFinalFight1().setFighterIdBlue( secondplacePoolB.getId() );
                    pool.getHalfFinalFight1().setWinnerId( secondplacePoolB.getId() );
                }

                if ( null != fistplacePoolA && null == secondplacePoolB )
                {
                    Fight halfFinalFight = fightDao.getFight( pool.getHalfFinalFight1().getId() );
                    if ( isResetFinalFight )
                    {
                        halfFinalFight.resetForKoList();
                    }
                    halfFinalFight.setFighterIdRed( fistplacePoolA.getId() );
                    halfFinalFight.setFighterIdBlue( IValueConstants.LONG_MIN );
                    halfFinalFight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                    halfFinalFight.setModificationId( IValueConstants.SYSTEM_USER );
                    halfFinalFight.setWinnerId( fistplacePoolA.getId() );

                    pool.getHalfFinalFight1().resetForKoList();
                    pool.getHalfFinalFight1().setFighterRed( fistplacePoolA );
                    pool.getHalfFinalFight1().setFighterBlue( new Fighter() );
                    pool.getHalfFinalFight1().setFighterIdRed( fistplacePoolA.getId() );
                    pool.getHalfFinalFight1().setFighterIdBlue( TypeUtil.LONG_MIN );
                    pool.getHalfFinalFight1().setWinnerId( fistplacePoolA.getId() );
                }

                if ( null != fistplacePoolA && null != secondplacePoolB )
                {

                    Fight halfFinalFight = fightDao.getFight( pool.getHalfFinalFight1().getId() );
                    if ( halfFinalFight.getFighterIdRed() != fistplacePoolA.getId()
                        || halfFinalFight.getFighterIdBlue() != secondplacePoolB.getId() )
                    {

                        if ( isResetFinalFight )
                        {
                            halfFinalFight.resetForKoList();
                        }
                        halfFinalFight.setFighterIdRed( fistplacePoolA.getId() );
                        halfFinalFight.setFighterIdBlue( secondplacePoolB.getId() );
                        halfFinalFight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                        halfFinalFight.setModificationId( IValueConstants.SYSTEM_USER );

                        pool.getHalfFinalFight1().resetForKoList();
                        pool.getHalfFinalFight1().setFighterRed( fistplacePoolA );
                        pool.getHalfFinalFight1().setFighterBlue( secondplacePoolB );
                        pool.getHalfFinalFight1().setFighterIdRed( fistplacePoolA.getId() );
                        pool.getHalfFinalFight1().setFighterIdBlue( secondplacePoolB.getId() );
                        pool.getHalfFinalFight1().setWinnerId( TypeUtil.LONG_MIN );
                    }

                }
                // halfFinal
                if ( null == fistplacePoolB && null != secondplacePoolA )
                {
                    Fight halfFinalFight = fightDao.getFight( pool.getHalfFinalFight2().getId() );
                    if ( isResetFinalFight )
                    {
                        halfFinalFight.resetForKoList();
                    }
                    halfFinalFight.setFighterIdRed( IValueConstants.LONG_MIN );
                    halfFinalFight.setFighterIdBlue( secondplacePoolA.getId() );
                    halfFinalFight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                    halfFinalFight.setModificationId( IValueConstants.SYSTEM_USER );
                    halfFinalFight.setWinnerId( secondplacePoolA.getId() );

                    pool.getHalfFinalFight2().resetForKoList();
                    pool.getHalfFinalFight2().setFighterRed( new Fighter() );
                    pool.getHalfFinalFight2().setFighterBlue( secondplacePoolA );
                    pool.getHalfFinalFight2().setFighterIdRed( TypeUtil.LONG_MIN );
                    pool.getHalfFinalFight2().setFighterIdBlue( secondplacePoolA.getId() );
                    pool.getHalfFinalFight2().setWinnerId( secondplacePoolA.getId() );
                }

                if ( null != fistplacePoolB && null == secondplacePoolA )
                {
                    Fight halfFinalFight = fightDao.getFight( pool.getHalfFinalFight2().getId() );
                    if ( isResetFinalFight )
                    {
                        halfFinalFight.resetForKoList();
                    }
                    halfFinalFight.setFighterIdRed( fistplacePoolB.getId() );
                    halfFinalFight.setFighterIdBlue( IValueConstants.LONG_MIN );
                    halfFinalFight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                    halfFinalFight.setModificationId( IValueConstants.SYSTEM_USER );
                    halfFinalFight.setWinnerId( fistplacePoolB.getId() );

                    pool.getHalfFinalFight2().resetForKoList();
                    pool.getHalfFinalFight2().setFighterRed( fistplacePoolB );
                    pool.getHalfFinalFight2().setFighterBlue( new Fighter() );
                    pool.getHalfFinalFight2().setFighterIdRed( fistplacePoolB.getId() );
                    pool.getHalfFinalFight2().setFighterIdBlue( TypeUtil.LONG_MIN );
                    pool.getHalfFinalFight2().setWinnerId( fistplacePoolB.getId() );
                }

                if ( null != fistplacePoolB && null != secondplacePoolA )
                {

                    Fight halfFinalFight = fightDao.getFight( pool.getHalfFinalFight2().getId() );
                    if ( halfFinalFight.getFighterIdRed() != fistplacePoolB.getId()
                        || halfFinalFight.getFighterIdBlue() != secondplacePoolA.getId() )
                    {

                        if ( isResetFinalFight )
                        {
                            halfFinalFight.resetForKoList();
                        }
                        halfFinalFight.setFighterIdRed( fistplacePoolB.getId() );
                        halfFinalFight.setFighterIdBlue( secondplacePoolA.getId() );
                        halfFinalFight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                        halfFinalFight.setModificationId( IValueConstants.SYSTEM_USER );

                        pool.getHalfFinalFight2().resetForKoList();
                        pool.getHalfFinalFight2().setFighterRed( fistplacePoolB );
                        pool.getHalfFinalFight2().setFighterBlue( secondplacePoolA );
                        pool.getHalfFinalFight2().setFighterIdRed( fistplacePoolB.getId() );
                        pool.getHalfFinalFight2().setFighterIdBlue( secondplacePoolA.getId() );
                        pool.getHalfFinalFight2().setWinnerId( TypeUtil.LONG_MIN );
                    }

                }

                // FinalFight

                if ( TypeUtil.LONG_EMPTY == pool.getHalfFinalFight2().getWinnerId()
                                || pool.getHalfFinalFight1().getWinnerId() == TypeUtil.LONG_EMPTY ){
                    // reset final fight on DB, when half finals are not finished
                    Fight finalFight = fightDao.getFight( pool.getFinalFight().getId() );
                    finalFight.resetForKoList();
                }
                
                if ( TypeUtil.LONG_DEFAULT == pool.getHalfFinalFight2().getWinnerId()
                    && pool.getHalfFinalFight1().getWinnerId() > TypeUtil.LONG_DEFAULT )
                {

                    Fight finalFight = fightDao.getFight( pool.getFinalFight().getId() );
                    if ( isResetFinalFight )
                    {
                        finalFight.resetForKoList();
                    }
                    finalFight.setFighterIdRed( pool.getHalfFinalFight1().getWinnerId() );
                    finalFight.setFighterIdBlue( IValueConstants.LONG_MIN );
                    finalFight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                    finalFight.setModificationId( IValueConstants.SYSTEM_USER );
                    finalFight.setWinnerId( pool.getHalfFinalFight1().getWinnerId() );

                    pool.getFinalFight().resetForKoList();
                    pool.getFinalFight().setFighterRed( ( pool.getHalfFinalFight1().getWinnerId() == pool.getHalfFinalFight1().getFighterIdRed() ) ? pool.getHalfFinalFight1().getFighterRed()
                                                                        : pool.getHalfFinalFight1().getFighterBlue() );

                    pool.getFinalFight().setFighterBlue( new Fighter() );
                    pool.getFinalFight().setFighterIdRed( pool.getHalfFinalFight1().getWinnerId() );
                    pool.getFinalFight().setFighterIdBlue( TypeUtil.LONG_MIN );
                    pool.getFinalFight().setWinnerId( pool.getHalfFinalFight1().getWinnerId() );
                }
                
                if ( TypeUtil.LONG_DEFAULT < pool.getHalfFinalFight2().getWinnerId()
                    && pool.getHalfFinalFight1().getWinnerId() == TypeUtil.LONG_DEFAULT )
                {
                    Fight finalFight = fightDao.getFight( pool.getFinalFight().getId() );
                    if ( isResetFinalFight )
                    {
                        finalFight.resetForKoList();
                    }
                    finalFight.setFighterIdRed( IValueConstants.LONG_MIN );
                    finalFight.setFighterIdBlue( pool.getHalfFinalFight2().getWinnerId() );
                    finalFight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                    finalFight.setModificationId( IValueConstants.SYSTEM_USER );
                    finalFight.setWinnerId( pool.getHalfFinalFight2().getWinnerId() );

                    pool.getFinalFight().resetForKoList();
                    pool.getFinalFight().setFighterRed( new Fighter() );
                    pool.getFinalFight().setFighterBlue( ( pool.getHalfFinalFight2().getWinnerId() == pool.getHalfFinalFight2().getFighterIdRed() ) ? pool.getHalfFinalFight2().getFighterRed()
                                                                         : pool.getHalfFinalFight2().getFighterBlue() );

                    pool.getFinalFight().setFighterIdRed( TypeUtil.LONG_MIN );
                    pool.getFinalFight().setFighterIdBlue( pool.getHalfFinalFight2().getWinnerId() );
                    pool.getFinalFight().setWinnerId( pool.getHalfFinalFight2().getWinnerId() );
                }

                if ( TypeUtil.LONG_DEFAULT < pool.getHalfFinalFight2().getWinnerId()
                    && pool.getHalfFinalFight1().getWinnerId() > TypeUtil.LONG_DEFAULT )
                {
                    Fight finalFight = fightDao.getFight( pool.getFinalFight().getId() );
                    if ( finalFight.getFighterIdRed() != pool.getHalfFinalFight1().getWinnerId()
                        || finalFight.getFighterIdBlue() != pool.getHalfFinalFight2().getWinnerId() )
                    {

                        if ( isResetFinalFight )
                        {
                            finalFight.resetForKoList();
                        }
                        finalFight.setFighterIdRed( pool.getHalfFinalFight1().getWinnerId() );
                        finalFight.setFighterIdBlue( pool.getHalfFinalFight2().getWinnerId() );
                        finalFight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                        finalFight.setModificationId( IValueConstants.SYSTEM_USER );

                        pool.getFinalFight().resetForKoList();
                        pool.getFinalFight().setFighterRed( ( pool.getHalfFinalFight1().getWinnerId() == pool.getHalfFinalFight1().getFighterIdRed() ) ? pool.getHalfFinalFight1().getFighterRed()
                                        : pool.getHalfFinalFight1().getFighterBlue() );
                        pool.getFinalFight().setFighterBlue( ( pool.getHalfFinalFight2().getWinnerId() == pool.getHalfFinalFight2().getFighterIdRed() ) ? pool.getHalfFinalFight2().getFighterRed()
                                        : pool.getHalfFinalFight2().getFighterBlue() );
                        pool.getFinalFight().setFighterIdRed( pool.getHalfFinalFight1().getWinnerId() );
                        pool.getFinalFight().setFighterIdBlue( pool.getHalfFinalFight2().getWinnerId() );
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

    private void doDoublePoolResults( FightingDoublePoolClass pool )
    {
        // TODO: write Results
        if ( pool.getFinalFight().getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            // when final fight is over
            if ( pool.getFinalFight().getWinnerId().longValue() == TypeUtil.LONG_DEFAULT )
            {
                // double DQ both are 2nd place
                pool.getFinalFight().getFighterBlue().setPlace( IValueConstants._2 );
                pool.getFinalFight().getFighterRed().setPlace( IValueConstants._2 );
                                
                pool.getResultMap().put( Long.valueOf( 2 ), getAsList(pool.getFinalFight().getFighterRed(),pool.getFinalFight().getFighterBlue()));
            }

            if ( pool.getFinalFight().getWinnerId().longValue() == pool.getFinalFight().getFighterIdBlue() )
            {

                pool.getFinalFight().getFighterBlue().setPlace( IValueConstants._1 );
                pool.getResultMap().put( Long.valueOf( 1 ), getAsList(pool.getFinalFight().getFighterBlue(),null));              
                pool.getFinalFight().getFighterRed().setPlace( IValueConstants._2 );
                pool.getResultMap().put( Long.valueOf( 2 ), getAsList(pool.getFinalFight().getFighterRed(),null));    
            }

            if ( pool.getFinalFight().getWinnerId().longValue() == pool.getFinalFight().getFighterIdRed() )
            {

                pool.getFinalFight().getFighterBlue().setPlace( IValueConstants._2 );
                pool.getResultMap().put( Long.valueOf( 2 ),
                                         getAsList( pool.getFinalFight().getFighterBlue(), null ) );
                pool.getFinalFight().getFighterRed().setPlace( IValueConstants._1 );
                pool.getResultMap().put( Long.valueOf( 1 ),
                                         getAsList( pool.getFinalFight().getFighterRed(), null ) );
            }

            ArrayList<Fighter> rdList = new ArrayList<Fighter>();
            if ( pool.getHalfFinalFight1().getWinnerId().longValue() != pool.getHalfFinalFight1().getFighterIdRed() )
            {
                pool.getHalfFinalFight1().getFighterRed().setPlace( IValueConstants._3 );
                rdList.add( pool.getHalfFinalFight1().getFighterRed() );
            }

            if ( pool.getHalfFinalFight1().getWinnerId().longValue() != pool.getHalfFinalFight1().getFighterIdBlue() )
            {
                pool.getHalfFinalFight1().getFighterBlue().setPlace( IValueConstants._3 );
                rdList.add( pool.getHalfFinalFight1().getFighterBlue() );
            }
            
            if ( pool.getHalfFinalFight2().getWinnerId().longValue() != pool.getHalfFinalFight2().getFighterIdRed() )
            {
                pool.getHalfFinalFight2().getFighterRed().setPlace( IValueConstants._3 );
                rdList.add( pool.getHalfFinalFight2().getFighterRed() );
            }

            if ( pool.getHalfFinalFight2().getWinnerId().longValue() != pool.getHalfFinalFight2().getFighterIdBlue() )
            {
                pool.getHalfFinalFight2().getFighterBlue().setPlace( IValueConstants._3 );
                rdList.add( pool.getHalfFinalFight2().getFighterBlue());
            }
            pool.getResultMap().put( Long.valueOf( 3 ),rdList);

            // ranking of the pool fighter
            for ( FightingDoublePoolItem item : pool.getFighterListPoolA() )
            {

                if ( item.getFighter().getId().longValue() == pool.getHalfFinalFight1().getFighterIdRed()||
                                item.getFighter().getId().longValue() ==pool.getHalfFinalFight2().getFighterIdBlue())
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

            for ( FightingDoublePoolItem item : pool.getFighterListPoolB() )
            {

                if ( item.getFighter().getId().longValue() == pool.getHalfFinalFight2().getFighterIdBlue() ||
                                item.getFighter().getId().longValue() ==pool.getHalfFinalFight1().getFighterIdBlue())
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
                for ( FightingDoublePoolItem item : pool.getFighterListPoolA() )
                {

                    if ( item.getFighter().getId().longValue() == pool.getFinalFight().getFighterIdRed() )
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

                for ( FightingDoublePoolItem item : pool.getFighterListPoolB() )
                {

                    if ( item.getFighter().getId().longValue() == pool.getFinalFight().getFighterIdBlue() )
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

    private ArrayList<Fighter> getAsList(Fighter fighter1,Fighter fighter2){
        
        ArrayList<Fighter> list =new ArrayList<Fighter>();
        
        if(fighter1 !=null)
            list.add( fighter1 );
        if(fighter2 !=null)
            list.add( fighter2 );
        
        
        return list;
    }
    
    /**
     * iterates the whole fightlist, is at least one fight not over --> false
     * 
     * @param fightList
     * @return
     */
    private static boolean isAllFightsAreOver( Collection<Fight> fightList )
    {
        for ( Fight fight : fightList )
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
    private Fighter getFighterOfRank( int rank, List<FightingDoublePoolItem> itemList )
    {
        Fighter fighter = null;
        for ( FightingDoublePoolItem item : itemList )
        {
            if ( item.getPoolPlace() == rank )
            {
                if ( fighter != null )
                {
                    return null;
                }
                else
                {
                    fighter = item.getFighter();
                }
            }
        }
        return fighter;
    }

    /**
     * this method is for performing the ranking in a pool when there were no fight performed then no ranking
     * 
     * @param fightCollection
     * @param rankingLis
     */
    private List<FightingclassPoolRankingDTO> doRanking( Collection<Fight> fightCollection,
                                                         List<FightingclassPoolRankingDTO> rankingList )
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
        int ubs = 0;

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
    private static List<FightingclassPoolRankingDTO> handleDoublePlaces( List<FightingclassPoolRankingDTO> rankingList,
                                                                         Collection<Fight> fightCollection,
                                                                         List<Long> placeList, int place )
    {
        for ( Fight fight : fightCollection )
        {
            if ( isBothFighterInList( placeList, fight ) )
            {
                // if the fighters are both in the list, get the loser an set him a new place
                if ( fight.getWinnerId() == fight.getFighterIdRed() )
                {
                    for ( FightingclassPoolRankingDTO dto : rankingList )
                    {
                        if ( fight.getFighterIdBlue() == dto.getId() )
                        {
                            dto.setPlace( place );
                            break;
                        }
                    }
                    break;
                }
                if ( fight.getWinnerId() == fight.getFighterIdBlue() )
                {
                    for ( FightingclassPoolRankingDTO dto : rankingList )
                    {
                        if ( fight.getFighterIdRed() == dto.getId() )
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
     * this function handles the situation, when 3 have the same place. We reduce the attention only to these 3 and cout
     * their wins in the 3 corresponding fights. If everyone has one win so we have three times this place. Else we have
     * to check the direct competition
     * 
     * @param rankingList
     * @param fightCollection
     * @param placeList
     * @param place
     * @return
     */
    private static List<FightingclassPoolRankingDTO> handle3SamePlaces( List<FightingclassPoolRankingDTO> rankingList,
                                                                        Collection<Fight> fightCollection,
                                                                        List<Long> placeList, int place )
    {
        List<Fight> fightList = new ArrayList<Fight>();
        Map<Long, Integer> fighterWinningMap = new HashMap<Long, Integer>( 3 );
        for ( Fight fight : fightCollection )
        {

            if ( isBothFighterInList( placeList, fight ) )
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

    private static List<FightingclassPoolRankingDTO> handleWhen1InWinningMap( List<FightingclassPoolRankingDTO> rankingList,
                                                                              int place, List<Fight> fightList,
                                                                              Map<Long, Integer> fighterWinningMap,
                                                                              List<Long> placeList )
    {
        // TODO: get right infos to handle this
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
        for ( Fight item : fightList )
        {
            if ( fighterLoosingMap.get( item.getFighterIdRed() ) != null
                && fighterLoosingMap.get( item.getFighterIdBlue() ) != null )
            {
                // check the result of this fight to set the places
                if ( item.getWinnerId() == null || item.getWinnerId() <= TypeUtil.LONG_DEFAULT )
                {
                    fighterLoosingMap.clear();
                    fighterLoosingMap.put( item.getFighterIdRed(), TypeUtil.INTEGER_1 );
                    fighterLoosingMap.put( item.getFighterIdRed(), TypeUtil.INTEGER_1 );
                }

                if ( item.getWinnerId().equals( item.getFighterIdRed() ) )
                {
                    fighterLoosingMap.clear();
                    fighterLoosingMap.put( item.getFighterIdRed(), TypeUtil.INTEGER_1 );
                    fighterLoosingMap.put( item.getFighterIdBlue(), TypeUtil.INTEGER_2 );
                }

                if ( item.getWinnerId().equals( item.getFighterIdRed() ) )
                {
                    fighterLoosingMap.clear();
                    fighterLoosingMap.put( item.getFighterIdBlue(), TypeUtil.INTEGER_1 );
                    fighterLoosingMap.put( item.getFighterIdRed(), TypeUtil.INTEGER_2 );
                }
            }
        }

        // set the ranking
        for ( FightingclassPoolRankingDTO dto : rankingList )
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
    private static List<FightingclassPoolRankingDTO> handleWhen2InWinningMap( List<FightingclassPoolRankingDTO> rankingList,
                                                                              int place, List<Fight> fightList,
                                                                              Map<Long, Integer> fighterWinningMap,
                                                                              List<Long> placeList )
    {
        // TODO: get right infos to handle this
        if ( 1 != 2 )
            return rankingList;

        Map<Long, Integer> placeMap = new HashMap<Long, Integer>( 3 );
        // when size == 3 or 0 do nothing,because three time the same place

        if ( fighterWinningMap.get( (Long) fighterWinningMap.keySet().toArray()[0] ) > fighterWinningMap.get( (Long) fighterWinningMap.keySet().toArray()[1] ) )
        {
            // id of fighterWinningMap.get( 0 ) leads, than id of fighterWinningMap.get( 1 ) than
            // id of not in list
            placeMap =
                fillPlaceMap( placeMap, (Long) fighterWinningMap.keySet().toArray()[0],
                              (Long) fighterWinningMap.keySet().toArray()[1] );
        }

        if ( fighterWinningMap.get( (Long) fighterWinningMap.keySet().toArray()[0] ) < fighterWinningMap.get( (Long) fighterWinningMap.keySet().toArray()[1] ) )
        {
            // id of fighterWinningMap.get( 1 ) leads, than id of fighterWinningMap.get( 0 ) than
            // id of not in list
            placeMap =
                fillPlaceMap( placeMap, (Long) fighterWinningMap.keySet().toArray()[1],
                              (Long) fighterWinningMap.keySet().toArray()[0] );
        }

        if ( fighterWinningMap.get( ( (Long) fighterWinningMap.keySet().toArray()[0] ) ).equals( fighterWinningMap.get( (Long) fighterWinningMap.keySet().toArray()[1] ) ) )
        {
            // id of fighterWinningMap.get( 0 ) == fighterWinningMap.get( 1 ) than
            // id of not in list
            for ( Fight fight : fightList )
            {
                if ( ( fight.getFighterIdRed() == (Long) fighterWinningMap.keySet().toArray()[0] || fight.getFighterIdBlue() == (Long) fighterWinningMap.keySet().toArray()[0] )
                    && ( fight.getFighterIdRed() == (Long) fighterWinningMap.keySet().toArray()[1] || fight.getFighterIdBlue() == (Long) fighterWinningMap.keySet().toArray()[1] ) )
                {
                    if ( fight.getWinnerId().equals( fight.getFighterIdRed() ) )
                    {
                        placeMap = fillPlaceMap( placeMap, fight.getFighterIdRed(), fight.getFighterIdBlue() );
                        break;
                    }

                    if ( fight.getWinnerId().equals( fight.getFighterIdBlue() ) )
                    {
                        placeMap = fillPlaceMap( placeMap, fight.getFighterIdBlue(), fight.getFighterIdRed() );
                        break;
                    }
                }
            }
        }
        // set new places
        for ( FightingclassPoolRankingDTO dto : rankingList )
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
     * Check if the fighters blue and red are in the List this is import for handling double places
     * 
     * @param firstPlaceList
     * @param fight
     */
    private static boolean isBothFighterInList( List<Long> firstPlaceList, Fight fight )
    {
        if ( firstPlaceList.contains( fight.getFighterIdRed() ) && firstPlaceList.contains( fight.getFighterIdBlue() ) )
        {
            return true;
        }
        return false;
    }
}