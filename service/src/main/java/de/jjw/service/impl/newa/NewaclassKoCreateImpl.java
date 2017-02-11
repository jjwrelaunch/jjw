/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaclassKoCreateImpl.java
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

package de.jjw.service.impl.newa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.jjw.model.newa.NewaFight;
import de.jjw.model.newa.NewaFighter;
import de.jjw.model.newa.NewaKoClass;
import de.jjw.model.newa.NewaKoItem;
import de.jjw.model.newa.Newaclass;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.impl.KoCreateImplAbstract;
import de.jjw.service.impl.generalhelper.DrawHelperNewa;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;

public class NewaclassKoCreateImpl
    extends KoCreateImplAbstract
{

    /**
     * @param oriFighters
     * @param weightclass
     * @return
     */
    public Newaclass doKo( List<NewaFighter> oriFighters, Newaclass fightingclass, ServiceExchangeContext ctx )
    {

        List<NewaFighter> fighters = handleRedraw( oriFighters );

        NewaKoClass koClass = new NewaKoClass();
        setTechnicalDBFieldsForUpdate( koClass, ctx );
        fightingclass.setInuse( true );

        NewaKoItem item = null;
        List<NewaKoItem> itemListPoolA = new ArrayList<NewaKoItem>( fighters.size() / 2 );
        List<NewaKoItem> itemListPoolB = new ArrayList<NewaKoItem>( (int) ( fighters.size() / 2 - 0.5f ) );
        Map<Integer, NewaFight> fightListPoolA = new HashMap<Integer, NewaFight>();
        Map<Integer, NewaFight> fightListPoolB = new HashMap<Integer, NewaFight>();
        // int[] startPositionsInPool = getStartPositions(fightListPoolA.size()*2);

        String pool = IValueConstants.POOL_A;
        int i = 0, j = 0;
        for ( NewaFighter fighter : fighters )
        {

            item = new NewaKoItem();
            setTechnicalDBFieldsForCreate( item, ctx );
            item.setNewaclass( fightingclass );
            item.setFighter( fighter );
            item.setFighterId( fighter.getId() );
            if ( pool.equalsIgnoreCase( IValueConstants.POOL_A ) )
            {
                // negative logic, here is Pool A
                pool = IValueConstants.POOL_B;
                item.setPoolPart( IValueConstants.POOL_A );
                item.setNewaclassId( fightingclass.getId() );
                item.setNumberInPool( j );
                j++;
                itemListPoolA.add( item );
            }
            else
            {
                // negative logic, here is Pool B
                pool = IValueConstants.POOL_A;
                item.setPoolPart( IValueConstants.POOL_B );
                item.setNewaclassId( fightingclass.getId() );
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
        NewaFight fight = new NewaFight();
        setTechnicalDBFieldsForCreate( fight, ctx );
        fight.setNewaclassId( fightingclass.getId() );
        fight.setFlag( IValueConstants.FINAL_FIGHT );
        koClass.setFinalFight( fight );

        return koClass;
    }

    /**
     * delegates the redraw and give back the new order of fighterList
     * 
     * @param fighters
     */
    private List<NewaFighter> handleRedraw( final List<NewaFighter> oriFighters )
    {
        DrawHelperNewa dh = new DrawHelperNewa();
        List<NewaFighter> drawFighters = dh.drawFighter( oriFighters, getPoolsize( oriFighters.size() ), true );
        Map<Long, NewaFighter> oriMap = new HashMap<Long, NewaFighter>();

        for ( NewaFighter fighter : oriFighters )
        {
            oriMap.put( fighter.getId(), fighter );
        }

        List<NewaFighter> fighters = new ArrayList<NewaFighter>( oriMap.size() );

        for ( NewaFighter fighter : drawFighters )
        {
            fighters.add( oriMap.get( fighter.getId() ) );
        }
        return fighters;
    }

    private void handleFreeRound( Map<Integer, NewaFight> fightListMap, List<NewaFighter> fighters )
    {
        int fightIndex = fightListMap.size() - 1; // set to last
        String mainRoundFlag = getMainRoundFlag( fightIndex );
        NewaFight fight = null;
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
    private Map<Integer, NewaFight> doMainFightsForKo( List<NewaFighter> fighters, Map<Integer, NewaFight> fightList,
                                                       List<NewaKoItem> itemListPool, ServiceExchangeContext ctx,
                                                   String poolPart )
    {

        NewaFighter fighterRed = null;
        NewaFighter fighterBlue = null;
        NewaFight fight = null;

        int fightListSizeForPoolPart = getNumberOfFightsInMainRound( fighters.size() ) / 2;

        for ( int j = 0; j < fightListSizeForPoolPart; j++ )
        {
            fight = new NewaFight();
            fight.setFlag( getMainRoundFlag( j ) );
            fight.setMainRound( true );
            fight.setPoolPart( poolPart );
            fight.setFightNumberInPart( j );
            setTechnicalDBFieldsForCreate( fight, ctx );
            fight.setNewaclassId( itemListPool.get( 0 ).getNewaclassId() );
            fight.setWriteFlag( TypeUtil.BOOLEAN_TRUE );
            fightList.put( Integer.valueOf( j ), fight );
        }

        int poolsize = getPoolsize( fighters.size() );
        for ( int i = 0; i < poolsize / 4; i++ )
        {
            fighterRed = itemListPool.get( i ).getFighter();
            fightList.get( getMainRoundPosition( i, poolsize ) ).setFighterIdRed( fighterRed.getId() );
            // setFirstRoundFightsInKoItem(itemListPool.get(getNumberInListOfNewaKoItem(itemListPool, fighterRed)),
            // poolsize, fight.getId());
            if ( i + poolsize / 4 < itemListPool.size() )
            {
                fighterBlue = itemListPool.get( i + poolsize / 4 ).getFighter();
                fightList.get( getMainRoundPosition( i, poolsize ) ).setFighterIdBlue( fighterBlue.getId() );
                // setFirstRoundFightsInKoItem(itemListPool.get(getNumberInListOfNewaKoItem(itemListPool,
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

    private Map<Integer, NewaFight> doLoserFightsForKo( List<NewaFighter> fighters, List<NewaKoItem> itemListPool,
                                                    ServiceExchangeContext ctx, String poolPart )
    {

        int numberOfFights = getNumberOfFightsInALoserRound( fighters.size() ) / 2 + 1;
        Map<Integer, NewaFight> fightMap = new HashMap<Integer, NewaFight>( numberOfFights );

        NewaFight fight = null;
        for ( int j = 0; j < numberOfFights; j++ )
        {
            if ( isCreateLoserRoundFight( j, itemListPool, fighters.size() ) )
            {
                fight = new NewaFight();
                fight.setMainRound( false );
                fight.setPoolPart( poolPart );
                fight.setFightNumberInPart( j );
                fight.setFlag( getLoserRoundFlag( j ) );
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setNewaclassId( itemListPool.get( 0 ).getNewaclassId() );
                fightMap.put( Integer.valueOf( j ), fight );
            }
        }
        return fightMap;
    }

    private static boolean isCreateLoserRoundFight( int fightNumberInLoserRound, List<NewaKoItem> itemListPool,
                                                    int fighterCount )
    {

        if ( fightNumberInLoserRound < LOSER_ROUND_FIGHT_CREATE_MAP.get( itemListPool.size() ) )
        {
            return true;
        }
        return false;
    }

    public static Map<Integer, NewaFight> handleLoserEmptyFights( Map<Integer, NewaFight> poolFights,
                                                                  Map<Integer, NewaFight> loserFights,
                                                                  int numberOfFighterInPool )
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
