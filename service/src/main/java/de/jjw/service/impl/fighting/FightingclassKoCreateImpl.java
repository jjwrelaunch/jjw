/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightingclassKoCreateImpl.java
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

package de.jjw.service.impl.fighting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.jjw.model.fighting.Fight;
import de.jjw.model.fighting.Fighter;
import de.jjw.model.fighting.FightingKoClass;
import de.jjw.model.fighting.FightingKoItem;
import de.jjw.model.fighting.Fightingclass;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.impl.KoCreateImplAbstract;
import de.jjw.service.impl.generalhelper.DrawHelperFighting;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;

public class FightingclassKoCreateImpl
    extends KoCreateImplAbstract
{

    /**
     * @param oriFighters
     * @param weightclass
     * @return
     */
    public Fightingclass doKo( List<Fighter> oriFighters, Fightingclass fightingclass, ServiceExchangeContext ctx )
    {

        List<Fighter> fighters = handleRedraw( oriFighters );

        FightingKoClass koClass = new FightingKoClass();
        setTechnicalDBFieldsForUpdate( koClass, ctx );
        fightingclass.setInuse( true );

        FightingKoItem item = null;
        List<FightingKoItem> itemListPoolA = new ArrayList<FightingKoItem>( fighters.size() / 2 );
        List<FightingKoItem> itemListPoolB = new ArrayList<FightingKoItem>( (int) ( fighters.size() / 2 - 0.5f ) );
        Map<Integer, Fight> fightListPoolA = new HashMap<Integer, Fight>();
        Map<Integer, Fight> fightListPoolB = new HashMap<Integer, Fight>();
        // int[] startPositionsInPool = getStartPositions(fightListPoolA.size()*2);

        String pool = IValueConstants.POOL_A;
        int i = 0, j = 0;
        for ( Fighter fighter : fighters )
        {

            item = new FightingKoItem();
            setTechnicalDBFieldsForCreate( item, ctx );
            item.setFightingclass( fightingclass );
            item.setFighter( fighter );
            item.setFighterId( fighter.getId() );
            if ( pool.equalsIgnoreCase( IValueConstants.POOL_A ) )
            {
                // negative logic, here is Pool A
                pool = IValueConstants.POOL_B;
                item.setPoolPart( IValueConstants.POOL_A );
                item.setFightingclassId( fightingclass.getId() );
                item.setNumberInPool( j );
                j++;
                itemListPoolA.add( item );
            }
            else
            {
                // negative logic, here is Pool B
                pool = IValueConstants.POOL_A;
                item.setPoolPart( IValueConstants.POOL_B );
                item.setFightingclassId( fightingclass.getId() );
                item.setNumberInPool( i );
                i++;
                itemListPoolB.add( item );
            }
        }
        koClass.setFightListMapPoolA( doMainFightsForKo( fighters, fightListPoolA, itemListPoolA, ctx,
                                                         IValueConstants.POOL_A ) );
        koClass.setFightListMapPoolB( doMainFightsForKo( fighters, fightListPoolB, itemListPoolB, ctx,
                                                         IValueConstants.POOL_B ) );
        koClass.setFighterListPoolA( itemListPoolA );
        koClass.setFighterListPoolB( itemListPoolB );
        koClass.setFightListLoserMapPoolA( doLoserFightsForKo( fighters, itemListPoolA, ctx, IValueConstants.POOL_A ) );
        koClass.setFightListLoserMapPoolB( doLoserFightsForKo( fighters, itemListPoolB, ctx, IValueConstants.POOL_B ) );

        handleFreeRound( koClass.getFightListMapPoolA(), fighters );
        handleFreeRound( koClass.getFightListMapPoolB(), fighters );

        // handleLoserEmptyFights(koClass.getFightListMapPoolA(), koClass.getFightListLooserMapPoolA(),
        // fighters.size());
        // handleLoserEmptyFights(koClass.getFightListMapPoolB(), koClass.getFightListLooserMapPoolB(),
        // fighters.size());
        // finalFight
        Fight fight = new Fight();
        setTechnicalDBFieldsForCreate( fight, ctx );
        fight.setFightingclassId( fightingclass.getId() );
        fight.setFlag( IValueConstants.FINAL_FIGHT );
        koClass.setFinalFight( fight );

        return koClass;
    }

    /**
     * delegates the redraw and give back the new order of fighterList
     * 
     * @param fighters
     */
    private List<Fighter> handleRedraw( final List<Fighter> oriFighters )
    {
        DrawHelperFighting dh = new DrawHelperFighting();
        List<Fighter> drawFighters = dh.drawFighter( oriFighters, getPoolsize( oriFighters.size() ), true );
        Map<Long, Fighter> oriMap = new HashMap<Long, Fighter>();

        for ( Fighter fighter : oriFighters )
        {
            oriMap.put( fighter.getId(), fighter );
        }

        List<Fighter> fighters = new ArrayList<Fighter>( oriMap.size() );

        for ( Fighter fighter : drawFighters )
        {
            fighters.add( oriMap.get( fighter.getId() ) );
        }
        return fighters;
    }

    private void handleFreeRound( Map<Integer, Fight> fightListMap, List<Fighter> fighters )
    {
        int fightIndex = fightListMap.size() - 1; // set to last
        String mainRoundFlag = getMainRoundFlag( fightIndex );
        Fight fight = null;
        while ( mainRoundFlag.equals( getMainRoundFlag( fightIndex ) ) )
        {
            fight = fightListMap.get( Integer.valueOf( fightIndex ) );
            if ( fight != null && fight.getFighterIdBlue() == TypeUtil.LONG_EMPTY )
            {
                if ( fightIndex % 2 == 0 )
                {
                    // blue
                    fightListMap.get( Integer.valueOf( ( fightIndex - 1 ) / 2 ) ).setFighterIdBlue(
                                                                                                    fight.getFighterIdRed() );
                }
                else
                {
                    // red
                    fightListMap.get( Integer.valueOf( ( fightIndex - 1 ) / 2 ) ).setFighterIdRed(
                                                                                                   fight.getFighterIdRed() );
                }
            }
            fightIndex--;
        }
    }

    /**
     * create all fights and set firstRoundfights into itemPool
     * 
     * @param fighters
     * @param fightList
     * @param poolPart
     * @param fightListSize
     * @return
     */
    private Map<Integer, Fight> doMainFightsForKo( List<Fighter> fighters, Map<Integer, Fight> fightList,
                                                   List<FightingKoItem> itemListPool, ServiceExchangeContext ctx,
                                                   String poolPart )
    {

        Fighter fighterRed = null;
        Fighter fighterBlue = null;
        Fight fight = null;

        int fightListSizeForPoolPart = getNumberOfFightsInMainRound( fighters.size() ) / 2;

        for ( int j = 0; j < fightListSizeForPoolPart; j++ )
        {
            fight = new Fight();
            fight.setFlag( getMainRoundFlag( j ) );
            fight.setMainRound( true );
            fight.setPoolPart( poolPart );
            fight.setFightNumberInPart( j );
            setTechnicalDBFieldsForCreate( fight, ctx );
            fight.setFightingclassId( itemListPool.get( 0 ).getFightingclassId() );
            fight.setWriteFlag( TypeUtil.BOOLEAN_TRUE );
            fightList.put( Integer.valueOf( j ), fight );
        }

        int poolsize = getPoolsize( fighters.size() );
        for ( int i = 0; i < poolsize / 4; i++ )
        {
            fighterRed = itemListPool.get( i ).getFighter();
            fightList.get( getMainRoundPosition( i, poolsize ) ).setFighterIdRed( fighterRed.getId() );
            // setFirstRoundFightsInKoItem(itemListPool.get(getNumberInListOfFightingKoItem(itemListPool, fighterRed)),
            // poolsize, fight.getId());
            if ( i + poolsize / 4 < itemListPool.size() )
            {
                fighterBlue = itemListPool.get( i + poolsize / 4 ).getFighter();
                fightList.get( getMainRoundPosition( i, poolsize ) ).setFighterIdBlue( fighterBlue.getId() );
                // setFirstRoundFightsInKoItem(itemListPool.get(getNumberInListOfFightingKoItem(itemListPool,
                // fighterBlue)), poolsize, fight.getId());
            }
            else
            {
                fightList.get( getMainRoundPosition( i, poolsize ) ).setFighterIdBlue( TypeUtil.LONG_EMPTY );
                fightList.get( getMainRoundPosition( i, poolsize ) ).setWriteFlag( TypeUtil.BOOLEAN_FALSE );
            }
        }
        return fightList;
    }

    private Map<Integer, Fight> doLoserFightsForKo( List<Fighter> fighters, List<FightingKoItem> itemListPool,
                                                    ServiceExchangeContext ctx, String poolPart )
    {

        int numberOfFights = getNumberOfFightsInALoserRound( fighters.size() ) / 2 + 1;
        Map<Integer, Fight> fightMap = new HashMap<Integer, Fight>( numberOfFights );

        Fight fight = null;
        for ( int j = 0; j < numberOfFights; j++ )
        {
            if ( isCreateLoserRoundFight( j, itemListPool, fighters.size() ) )
            {
                fight = new Fight();
                fight.setMainRound( false );
                fight.setPoolPart( poolPart );
                fight.setFightNumberInPart( j );
                fight.setFlag( getLoserRoundFlag( j ) );
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setFightingclassId( itemListPool.get( 0 ).getFightingclassId() );
                fightMap.put( Integer.valueOf( j ), fight );
            }
        }
        return fightMap;
    }

    private static boolean isCreateLoserRoundFight( int fightNumberInLoserRound, List<FightingKoItem> itemListPool,
                                                    int fighterCount )
    {

        if ( fightNumberInLoserRound < LOSER_ROUND_FIGHT_CREATE_MAP.get( itemListPool.size() ) )
        {
            return true;
        }
        return false;
    }

    public static Map<Integer, Fight> handleLoserEmptyFights( Map<Integer, Fight> poolFights,
                                                              Map<Integer, Fight> loserFights, int numberOfFighterInPool )
    {

        int poolsize = getPoolsize( numberOfFighterInPool );
        for ( int j = poolsize / 4 - 1; j < poolsize / 2 - 1; j += 2 )
        {
            if ( TypeUtil.LONG_EMPTY == poolFights.get( Integer.valueOf( j ) ).getFighterIdBlue() )
            {
                loserFights.get( MAIN_LOSER_FIGHT_MAP.get( Integer.valueOf( j ) ) ).setFighterIdRed(
                                                                                                     TypeUtil.LONG_EMPTY );
            }
            if ( TypeUtil.LONG_EMPTY == poolFights.get( Integer.valueOf( j + 1 ) ).getFighterIdBlue() )
            {
                loserFights.get( MAIN_LOSER_FIGHT_MAP.get( Integer.valueOf( j + 1 ) ) ).setFighterIdBlue(
                                                                                                          TypeUtil.LONG_EMPTY );
            }
        }
        return loserFights;
    }
}
