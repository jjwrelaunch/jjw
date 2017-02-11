/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoclassPoolCreateImpl.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:48
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


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import de.jjw.model.ITechnicalDBFields;
import de.jjw.model.duo.DuoDoublePoolClass;
import de.jjw.model.duo.DuoDoublePoolItem;
import de.jjw.model.duo.DuoFight;
import de.jjw.model.duo.DuoSimplePoolClass;
import de.jjw.model.duo.DuoSimplePoolItem;
import de.jjw.model.duo.DuoTeam;
import de.jjw.model.duo.Duoclass;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.util.IValueConstants;

public class DuoclassPoolCreateImpl
{

    public Duoclass doSimplePool( List<DuoTeam> duoTeams, Duoclass duoclass, ServiceExchangeContext ctx, int revenge )
    {
        DuoSimplePoolClass simplePool = new DuoSimplePoolClass();
        setTechnicalDBFieldsForUpdate( simplePool, ctx );
        duoclass.setInuse( true );

        DuoSimplePoolItem item = null;
        List<DuoSimplePoolItem> itemList = new ArrayList<DuoSimplePoolItem>( duoTeams.size() );
        int i = 0;
        for ( DuoTeam duoTeam : duoTeams )
        {
            item = new DuoSimplePoolItem();

            setTechnicalDBFieldsForCreate( item, ctx );

            item.setDuoclassId( duoclass.getId() );
            item.setDuoclass( duoclass );
            item.setDuoTeam( duoTeam );
            item.setDuoTeamId( duoTeam.getId() );
            item.setNumberInPool( i );
            i++;
            itemList.add( item );
        }
        itemList = doFightsForPool( duoclass, ctx, itemList );

        if ( ( revenge == 1 || revenge == 2 ) && itemList.size() == 2 )
        {
            DuoFight fight = new DuoFight();
            setTechnicalDBFieldsForCreate( fight, ctx );
            fight.setDuoclassId( duoclass.getId() );
            fight.setTeamIdRed( itemList.get( 1 ).getDuoTeam().getId() );
            fight.setTeamIdBlue( itemList.get( 0 ).getDuoTeam().getId() );
            // first Fight for both
            itemList.get( 0 ).setFight2( fight );
            itemList.get( 1 ).setFight2( fight );
        }

        if ( revenge == 2 && itemList.size() == 2 )
        {
            DuoFight fight = new DuoFight();
            setTechnicalDBFieldsForCreate( fight, ctx );
            fight.setDuoclassId( duoclass.getId() );
            fight.setTeamIdRed( itemList.get( 0 ).getDuoTeam().getId() );
            fight.setTeamIdBlue( itemList.get( 1 ).getDuoTeam().getId() );
            // first Fight for both
            itemList.get( 0 ).setFight3( fight );
            itemList.get( 1 ).setFight3( fight );
        }

        simplePool.setDuoTeamList( itemList );
        return simplePool;
    }

    /**
     * creates all nessesary fights
     *
     * @param duoclass
     * @param ctx
     * @param itemList
     * @return
     */
    private List<DuoSimplePoolItem> doFightsForPool( Duoclass duoclass, ServiceExchangeContext ctx,
                                                     List<DuoSimplePoolItem> itemList )
    {
        DuoFight fight = null;
        switch ( itemList.size() )
        {

            case 5:
                fight = new DuoFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setDuoclassId( duoclass.getId() );
                fight.setTeamIdRed( itemList.get( 0 ).getDuoTeam().getId() );
                fight.setTeamIdBlue( itemList.get( 4 ).getDuoTeam().getId() );
                // 1st fight for fighter 5 and 4th fight for fighter 1
                itemList.get( 0 ).setFight4( fight );
                itemList.get( 4 ).setFight1( fight );

                fight = new DuoFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setDuoclassId( duoclass.getId() );
                fight.setTeamIdRed( itemList.get( 1 ).getDuoTeam().getId() );
                fight.setTeamIdBlue( itemList.get( 4 ).getDuoTeam().getId() );
                // 2nd fight for fighter 5 and 4th fight for fighter 2
                itemList.get( 1 ).setFight4( fight );
                itemList.get( 4 ).setFight2( fight );

                fight = new DuoFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setDuoclassId( duoclass.getId() );
                fight.setTeamIdRed( itemList.get( 2 ).getDuoTeam().getId() );
                fight.setTeamIdBlue( itemList.get( 4 ).getDuoTeam().getId() );
                // 3rd fight for fighter 5 and 4th fight for fighter 3
                itemList.get( 2 ).setFight4( fight );
                itemList.get( 4 ).setFight3( fight );

                fight = new DuoFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setDuoclassId( duoclass.getId() );
                fight.setTeamIdRed( itemList.get( 3 ).getDuoTeam().getId() );
                fight.setTeamIdBlue( itemList.get( 4 ).getDuoTeam().getId() );
                // 4th fight for fighter 5 and 4th fight for fighter 4
                itemList.get( 3 ).setFight4( fight );
                itemList.get( 4 ).setFight4( fight );

            case 4:
                fight = new DuoFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setDuoclassId( duoclass.getId() );
                fight.setTeamIdRed( itemList.get( 2 ).getDuoTeam().getId() );
                fight.setTeamIdBlue( itemList.get( 3 ).getDuoTeam().getId() );
                // 3rd fight for fighter 4 and 3rd fight for fighter 3
                itemList.get( 2 ).setFight3( fight );
                itemList.get( 3 ).setFight3( fight );

                fight = new DuoFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setDuoclassId( duoclass.getId() );
                fight.setTeamIdRed( itemList.get( 1 ).getDuoTeam().getId() );
                fight.setTeamIdBlue( itemList.get( 3 ).getDuoTeam().getId() );
                // 2nd fight for fighter 4 and 3rd fight for fighter 2
                itemList.get( 1 ).setFight3( fight );
                itemList.get( 3 ).setFight2( fight );

                fight = new DuoFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setDuoclassId( duoclass.getId() );
                fight.setTeamIdRed( itemList.get( 0 ).getDuoTeam().getId() );
                fight.setTeamIdBlue( itemList.get( 3 ).getDuoTeam().getId() );
                // first fight for fighter 4 and 3rd fight for fighter 1
                itemList.get( 0 ).setFight3( fight );
                itemList.get( 3 ).setFight1( fight );

            case 3:
                fight = new DuoFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setDuoclassId( duoclass.getId() );
                fight.setTeamIdRed( itemList.get( 0 ).getDuoTeam().getId() );
                fight.setTeamIdBlue( itemList.get( 2 ).getDuoTeam().getId() );
                // first fight for fighter 3 and 2nd fight for fighter 1
                itemList.get( 0 ).setFight2( fight );
                itemList.get( 2 ).setFight1( fight );

                fight = new DuoFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setDuoclassId( duoclass.getId() );
                fight.setTeamIdRed( itemList.get( 1 ).getDuoTeam().getId() );
                fight.setTeamIdBlue( itemList.get( 2 ).getDuoTeam().getId() );
                // second DuoFight for both
                itemList.get( 1 ).setFight2( fight );
                itemList.get( 2 ).setFight2( fight );

            case 2:
                fight = new DuoFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setDuoclassId( duoclass.getId() );
                fight.setTeamIdRed( itemList.get( 0 ).getDuoTeam().getId() );
                fight.setTeamIdBlue( itemList.get( 1 ).getDuoTeam().getId() );
                // first DuoFight for both
                itemList.get( 0 ).setFight1( fight );
                itemList.get( 1 ).setFight1( fight );
                break;
        }
        return itemList;
    }

    /**
     * set technical DB Fields
     *
     * @param obj
     */
    protected void setTechnicalDBFieldsForCreate( ITechnicalDBFields obj, ServiceExchangeContext ctx )
    {
        obj.setCreateDate( new Timestamp( System.currentTimeMillis() ) );
        obj.setCreateId( ctx.getUserId() );
        obj.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
        obj.setModificationId( ctx.getUserId() );
    }

    /**
     * set technical DB Fields
     *
     * @param obj
     */
    protected void setTechnicalDBFieldsForUpdate( ITechnicalDBFields obj, ServiceExchangeContext ctx )
    {
        obj.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
        obj.setModificationId( ctx.getUserId() );
    }

    public Duoclass doDoublePool( List<DuoTeam> duoTeams, Duoclass duoclass, ServiceExchangeContext ctx )
    {
        DuoDoublePoolClass doublePool = new DuoDoublePoolClass();
        setTechnicalDBFieldsForUpdate( doublePool, ctx );
        duoclass.setInuse( true );

        DuoDoublePoolItem item = null;

        List<DuoDoublePoolItem> itemListPoolA = new ArrayList<DuoDoublePoolItem>( (int) duoTeams.size() / 2 );
        List<DuoDoublePoolItem> itemListPoolB =
            new ArrayList<DuoDoublePoolItem>( (int) ( duoTeams.size() / 2 - 0.5f ) );
        String pool = IValueConstants.POOL_A;
        int i = 0, j = 0;
        for ( DuoTeam duoTeam : duoTeams )
        {

            item = new DuoDoublePoolItem();
            setTechnicalDBFieldsForCreate( item, ctx );
            item.setDuoclass( duoclass );
            item.setDuoTeam( duoTeam );
            item.setDuoTeamId( duoTeam.getId() );
            if ( pool.equalsIgnoreCase( IValueConstants.POOL_A ) )
            {
                // negative logic, here is Pool B
                pool = IValueConstants.POOL_B;
                item.setPoolPart( IValueConstants.POOL_A );
                item.setDuoclassId( duoclass.getId() );
                item.setNumberInPool( j );
                j++;
                itemListPoolB.add( item );
            }
            else
            {
                // negative logic, here is Pool A
                pool = IValueConstants.POOL_A;
                item.setPoolPart( IValueConstants.POOL_B );
                item.setDuoclassId( duoclass.getId() );
                item.setNumberInPool( i );
                i++;
                itemListPoolA.add( item );
            }
            itemListPoolA = doFightsForDPool( duoclass, ctx, itemListPoolA );
            itemListPoolB = doFightsForDPool( duoclass, ctx, itemListPoolB );
        }
        doublePool.setDuoListPoolA( itemListPoolA );
        doublePool.setDuoListPoolB( itemListPoolB );
        
        //halfFinal 1
        DuoFight fight = new DuoFight();
        setTechnicalDBFieldsForCreate( fight, ctx );
        fight.setDuoclassId( duoclass.getId() );
        fight.setFlag( IValueConstants.HALF_FINAL_FIGHT_1 );
        doublePool.setHalfFinalFight1(  fight );
      //halfFinal 2
        fight = new DuoFight();
        setTechnicalDBFieldsForCreate( fight, ctx );
        fight.setDuoclassId( duoclass.getId() );
        fight.setFlag( IValueConstants.HALF_FINAL_FIGHT_2 );
        doublePool.setHalfFinalFight2( fight );

        
        // finalFight
        fight = new DuoFight();
        setTechnicalDBFieldsForCreate( fight, ctx );
        fight.setDuoclassId( duoclass.getId() );
        fight.setFlag( IValueConstants.FINAL_FIGHT );
        doublePool.setFinalFight( fight );
        return doublePool;
    }


    private List<DuoDoublePoolItem> doFightsForDPool( Duoclass duoclass, ServiceExchangeContext ctx,
                                                      List<DuoDoublePoolItem> itemList )
    {
        DuoFight fight = null;
        switch ( itemList.size() )
        {

            case 5:
                fight = new DuoFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setDuoclassId( duoclass.getId() );
                fight.setTeamIdRed( itemList.get( 0 ).getDuoTeam().getId() );
                fight.setTeamIdBlue( itemList.get( 4 ).getDuoTeam().getId() );
                // 1st fight for fighter 5 and 4th fight for fighter 1
                itemList.get( 0 ).setFight4( fight );
                itemList.get( 4 ).setFight1( fight );

                fight = new DuoFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setDuoclassId( duoclass.getId() );
                fight.setTeamIdRed( itemList.get( 1 ).getDuoTeam().getId() );
                fight.setTeamIdBlue( itemList.get( 4 ).getDuoTeam().getId() );
                // 2nd fight for fighter 5 and 4th fight for fighter 2
                itemList.get( 1 ).setFight4( fight );
                itemList.get( 4 ).setFight2( fight );

                fight = new DuoFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setDuoclassId( duoclass.getId() );
                fight.setTeamIdRed( itemList.get( 2 ).getDuoTeam().getId() );
                fight.setTeamIdBlue( itemList.get( 4 ).getDuoTeam().getId() );
                // 3rd fight for fighter 5 and 4th fight for fighter 3
                itemList.get( 2 ).setFight4( fight );
                itemList.get( 4 ).setFight3( fight );

                fight = new DuoFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setDuoclassId( duoclass.getId() );
                fight.setTeamIdRed( itemList.get( 3 ).getDuoTeam().getId() );
                fight.setTeamIdBlue( itemList.get( 4 ).getDuoTeam().getId() );
                // 4th fight for fighter 5 and 4th fight for fighter 4
                itemList.get( 3 ).setFight4( fight );
                itemList.get( 4 ).setFight4( fight );

            case 4:
                fight = new DuoFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setDuoclassId( duoclass.getId() );
                fight.setTeamIdRed( itemList.get( 2 ).getDuoTeam().getId() );
                fight.setTeamIdBlue( itemList.get( 3 ).getDuoTeam().getId() );
                // 3rd fight for fighter 4 and 3rd fight for fighter 3
                itemList.get( 2 ).setFight3( fight );
                itemList.get( 3 ).setFight3( fight );

                fight = new DuoFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setDuoclassId( duoclass.getId() );
                fight.setTeamIdRed( itemList.get( 1 ).getDuoTeam().getId() );
                fight.setTeamIdBlue( itemList.get( 3 ).getDuoTeam().getId() );
                // 2nd fight for fighter 4 and 3rd fight for fighter 2
                itemList.get( 1 ).setFight3( fight );
                itemList.get( 3 ).setFight2( fight );

                fight = new DuoFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setDuoclassId( duoclass.getId() );
                fight.setTeamIdRed( itemList.get( 0 ).getDuoTeam().getId() );
                fight.setTeamIdBlue( itemList.get( 3 ).getDuoTeam().getId() );
                // first fight for fighter 4 and 3rd fight for fighter 1
                itemList.get( 0 ).setFight3( fight );
                itemList.get( 3 ).setFight1( fight );

            case 3:
                fight = new DuoFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setDuoclassId( duoclass.getId() );
                fight.setTeamIdRed( itemList.get( 0 ).getDuoTeam().getId() );
                fight.setTeamIdBlue( itemList.get( 2 ).getDuoTeam().getId() );
                // first fight for fighter 3 and 2nd fight for fighter 1
                itemList.get( 0 ).setFight2( fight );
                itemList.get( 2 ).setFight1( fight );

                fight = new DuoFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setDuoclassId( duoclass.getId() );
                fight.setTeamIdRed( itemList.get( 1 ).getDuoTeam().getId() );
                fight.setTeamIdBlue( itemList.get( 2 ).getDuoTeam().getId() );
                // second DuoFight for both
                itemList.get( 1 ).setFight2( fight );
                itemList.get( 2 ).setFight2( fight );

            case 2:
                fight = new DuoFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setDuoclassId( duoclass.getId() );
                fight.setTeamIdRed( itemList.get( 0 ).getDuoTeam().getId() );
                fight.setTeamIdBlue( itemList.get( 1 ).getDuoTeam().getId() );
                // first DuoFight for both
                itemList.get( 0 ).setFight1( fight );
                itemList.get( 1 ).setFight1( fight );
                break;
        }
        return itemList;

    }
}
