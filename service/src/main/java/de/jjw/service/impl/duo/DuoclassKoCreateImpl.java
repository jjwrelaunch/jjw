/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoclassKoCreateImpl.java
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

package de.jjw.service.impl.duo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.jjw.model.duo.DuoFight;
import de.jjw.model.duo.DuoKoClass;
import de.jjw.model.duo.DuoKoItem;
import de.jjw.model.duo.DuoTeam;
import de.jjw.model.duo.Duoclass;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.impl.KoCreateImplAbstract;
import de.jjw.service.impl.generalhelper.DrawHelperDuo;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;

public class DuoclassKoCreateImpl
    extends KoCreateImplAbstract
{

    /**
     * @param duoTeams
     * @param weightclass
     * @return
     */
    public Duoclass doKo( List<DuoTeam> oriDuoTeams, Duoclass duoclass, ServiceExchangeContext ctx )
    {
        List<DuoTeam> duoTeams = handleRedraw( oriDuoTeams );

        DuoKoClass koClass = new DuoKoClass();
        setTechnicalDBFieldsForUpdate( koClass, ctx );
        duoclass.setInuse( true );

        DuoKoItem item = null;
        List<DuoKoItem> itemListPoolA = new ArrayList<DuoKoItem>( duoTeams.size() / 2 );
        List<DuoKoItem> itemListPoolB = new ArrayList<DuoKoItem>( (int) ( duoTeams.size() / 2 - 0.5f ) );
        Map<Integer, DuoFight> fightListPoolA = new HashMap<Integer, DuoFight>();
        Map<Integer, DuoFight> fightListPoolB = new HashMap<Integer, DuoFight>();
        // int[] startPositionsInPool = getStartPositions(fightListPoolA.size()*2);

        String pool = IValueConstants.POOL_A;
        int i = 0, j = 0;
        for ( DuoTeam duoTeam : duoTeams )
        {

            item = new DuoKoItem();
            setTechnicalDBFieldsForCreate( item, ctx );
            item.setDuoclassId( duoclass.getId() );
            item.setDuoclass( duoclass );
            item.setDuoTeam( duoTeam );
            item.setDuoTeamId( duoTeam.getId() );
            if ( pool.equalsIgnoreCase( IValueConstants.POOL_A ) )
            {
                // negative logic, here is Pool A
                pool = IValueConstants.POOL_B;
                item.setPoolPart( IValueConstants.POOL_A );
                item.setDuoclassId( duoclass.getId() );
                item.setNumberInPool( j );
                j++;
                itemListPoolA.add( item );
            }
            else
            {
                // negative logic, here is Pool B
                pool = IValueConstants.POOL_A;
                item.setPoolPart( IValueConstants.POOL_B );
                item.setDuoclassId( duoclass.getId() );
                item.setNumberInPool( i );
                i++;
                itemListPoolB.add( item );
            }
        }
        koClass.setFightListMapPoolA( doMainFightsForKo( duoTeams, fightListPoolA, itemListPoolA, ctx,
                                                         IValueConstants.POOL_A ) );
        koClass.setFightListMapPoolB( doMainFightsForKo( duoTeams, fightListPoolB, itemListPoolB, ctx,
                                                         IValueConstants.POOL_B ) );
        koClass.setTeamListPoolA( itemListPoolA );
        koClass.setTeamListPoolB( itemListPoolB );
        koClass.setFightListLoserMapPoolA( doLoserFightsForKo( duoTeams, itemListPoolA, ctx, IValueConstants.POOL_A ) );
        koClass.setFightListLoserMapPoolB( doLoserFightsForKo( duoTeams, itemListPoolB, ctx, IValueConstants.POOL_B ) );

        handleFreeRound( koClass.getFightListMapPoolA(), duoTeams );
        handleFreeRound( koClass.getFightListMapPoolB(), duoTeams );

        // handleLoserEmptyFights(koClass.getFightListMapPoolA(), koClass.getFightListLooserMapPoolA(),
        // fighters.size());
        // handleLoserEmptyFights(koClass.getFightListMapPoolB(), koClass.getFightListLooserMapPoolB(),
        // fighters.size());
        // finalFight
        DuoFight fight = new DuoFight();
        setTechnicalDBFieldsForCreate( fight, ctx );
        fight.setDuoclassId( duoclass.getId() );
        fight.setFlag( IValueConstants.FINAL_FIGHT );
        koClass.setFinalFight( fight );

        return koClass;
    }

    /**
     * delegates the redraw and give back the new order of fighterList
     * 
     * @param fighters
     */
    private List<DuoTeam> handleRedraw( final List<DuoTeam> oriDuoTeams )
    {
        DrawHelperDuo dh = new DrawHelperDuo();
        List<DuoTeam> drawFighters = dh.drawDuo( oriDuoTeams, getPoolsize( oriDuoTeams.size() ), true );
        Map<Long, DuoTeam> oriMap = new HashMap<Long, DuoTeam>();

        for ( DuoTeam team : oriDuoTeams )
        {
            oriMap.put( team.getId(), team );
        }

        List<DuoTeam> duoTeams = new ArrayList<DuoTeam>( oriMap.size() );
        for ( DuoTeam fighter : drawFighters )
        {
            duoTeams.add( oriMap.get( fighter.getId() ) );
        }
        return duoTeams;
    }

    private void handleFreeRound( Map<Integer, DuoFight> fightListMap, List<DuoTeam> duoTeams )
    {
        int fightIndex = fightListMap.size() - 1; // set to last
        String mainRoundFlag = getMainRoundFlag( fightIndex );
        DuoFight fight = null;
        while ( mainRoundFlag.equals( getMainRoundFlag( fightIndex ) ) )
        {
            fight = fightListMap.get( Integer.valueOf( fightIndex ) );
            if ( fight != null && fight.getTeamIdBlue() == TypeUtil.LONG_EMPTY )
            {
                if ( fightIndex % 2 == 0 )
                {
                    // blue
                    fightListMap.get( Integer.valueOf( ( fightIndex - 1 ) / 2 ) ).setTeamIdBlue( fight.getTeamIdRed() );
                }
                else
                {
                    // red
                    fightListMap.get( Integer.valueOf( ( fightIndex - 1 ) / 2 ) ).setTeamIdRed( fight.getTeamIdRed() );
                }
            }
            fightIndex--;
        }
    }

    /**
     * create all fights and set firstRoundfights into itemPool
     * 
     * @param duoTeams
     * @param fightList
     * @param poolPart
     * @param fightListSize
     * @return
     */
    private Map<Integer, DuoFight> doMainFightsForKo( List<DuoTeam> duoTeams, Map<Integer, DuoFight> fightList,
                                                      List<DuoKoItem> itemListPool, ServiceExchangeContext ctx,
                                                      String poolPart )
    {

        DuoTeam duoTeamRed = null;
        DuoTeam duoTeamBlue = null;
        DuoFight fight = null;

        int fightListSizeForPoolPart = getNumberOfFightsInMainRound( duoTeams.size() ) / 2;

        for ( int j = 0; j < fightListSizeForPoolPart; j++ )
        {
            fight = new DuoFight();
            fight.setFlag( getMainRoundFlag( j ) );
            fight.setMainRound( true );
            fight.setPoolPart( poolPart );
            fight.setFightNumberInPart( j );
            setTechnicalDBFieldsForCreate( fight, ctx );
            fight.setDuoclassId( itemListPool.get( 0 ).getDuoclassId() );
            fight.setWriteFlag( TypeUtil.BOOLEAN_TRUE );
            fightList.put( Integer.valueOf( j ), fight );
        }

        int poolsize = getPoolsize( duoTeams.size() );
        for ( int i = 0; i < poolsize / 4; i++ )
        {
            duoTeamRed = itemListPool.get( i ).getDuoTeam();
            fightList.get( getMainRoundPosition( i, poolsize ) ).setTeamIdRed( duoTeamRed.getId() );
            // setFirstRoundFightsInKoItem(itemListPool.get(getNumberInListOfFightingKoItem(itemListPool, fighterRed)),
            // poolsize, fight.getId());
            if ( i + poolsize / 4 < itemListPool.size() )
            {
                duoTeamBlue = itemListPool.get( i + poolsize / 4 ).getDuoTeam();
                fightList.get( getMainRoundPosition( i, poolsize ) ).setTeamIdBlue( duoTeamBlue.getId() );
                // setFirstRoundFightsInKoItem(itemListPool.get(getNumberInListOfFightingKoItem(itemListPool,
                // fighterBlue)), poolsize, fight.getId());
            }
            else
            {
                fightList.get( getMainRoundPosition( i, poolsize ) ).setTeamIdBlue( TypeUtil.LONG_EMPTY );
                fightList.get( getMainRoundPosition( i, poolsize ) ).setWriteFlag( TypeUtil.BOOLEAN_FALSE );
            }
        }
        return fightList;
    }

    private Map<Integer, DuoFight> doLoserFightsForKo( List<DuoTeam> duoTeams, List<DuoKoItem> itemListPool,
                                                       ServiceExchangeContext ctx, String poolPart )
    {

        int numberOfFights = getNumberOfFightsInALoserRound( duoTeams.size() ) / 2 + 1;
        Map<Integer, DuoFight> fightMap = new HashMap<Integer, DuoFight>( numberOfFights );

        DuoFight fight = null;
        for ( int j = 0; j < numberOfFights; j++ )
        {
            if ( isCreateLoserRoundFight( j, itemListPool, duoTeams.size() ) )
            {
                fight = new DuoFight();
                fight.setMainRound( false );
                fight.setPoolPart( poolPart );
                fight.setFightNumberInPart( j );
                fight.setFlag( getLoserRoundFlag( j ) );
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setDuoclassId( itemListPool.get( 0 ).getDuoclassId() );
                fightMap.put( Integer.valueOf( j ), fight );
            }
        }
        return fightMap;
    }

    private static boolean isCreateLoserRoundFight( int fightNumberInLoserRound, List<DuoKoItem> itemListPool,
                                                    int fighterCount )
    {
        if ( fightNumberInLoserRound < LOSER_ROUND_FIGHT_CREATE_MAP.get( itemListPool.size() ) )
        {
            return true;
        }
        return false;
    }

    public static Map<Integer, DuoFight> handleLoserEmptyFights( Map<Integer, DuoFight> poolFights,
                                                                 Map<Integer, DuoFight> loserFights,
                                                                 int numberOfFighterInPool )
    {

        int poolsize = getPoolsize( numberOfFighterInPool );
        for ( int j = poolsize / 4 - 1; j < poolsize / 2 - 1; j += 2 )
        {
            if ( TypeUtil.LONG_EMPTY == poolFights.get( Integer.valueOf( j ) ).getTeamIdBlue() )
            {
                loserFights.get( MAIN_LOSER_FIGHT_MAP.get( Integer.valueOf( j ) ) ).setTeamIdRed( TypeUtil.LONG_EMPTY );
            }
            if ( TypeUtil.LONG_EMPTY == poolFights.get( Integer.valueOf( j + 1 ) ).getTeamIdBlue() )
            {
                loserFights.get( MAIN_LOSER_FIGHT_MAP.get( Integer.valueOf( j + 1 ) ) ).setTeamIdBlue(
                                                                                                       TypeUtil.LONG_EMPTY );
            }
        }
        return loserFights;
    }

}
