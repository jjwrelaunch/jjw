/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightingclassPoolCreateImpl.java
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
import java.util.List;

import de.jjw.model.ITechnicalDBFields;
import de.jjw.model.fighting.Fight;
import de.jjw.model.fighting.Fighter;
import de.jjw.model.fighting.FightingDoublePoolClass;
import de.jjw.model.fighting.FightingDoublePoolItem;
import de.jjw.model.fighting.FightingSimplePoolClass;
import de.jjw.model.fighting.FightingSimplePoolItem;
import de.jjw.model.fighting.Fightingclass;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.util.IValueConstants;

public class FightingclassPoolCreateImpl
{

    public Fightingclass doSimplePool( List<Fighter> fighters, Fightingclass fightingclass, ServiceExchangeContext ctx,
                                       int revenge )
    {
        FightingSimplePoolClass simplePool = new FightingSimplePoolClass();
        setTechnicalDBFieldsForUpdate( simplePool, ctx );
        fightingclass.setInuse( true );

        FightingSimplePoolItem item = null;
        List<FightingSimplePoolItem> itemList = new ArrayList<FightingSimplePoolItem>( fighters.size() );
        int i = 0;
        for ( Fighter fighter : fighters )
        {
            item = new FightingSimplePoolItem();

            setTechnicalDBFieldsForCreate( item, ctx );

            item.setFightingclassId( fightingclass.getId() );
            item.setFightingclass( fightingclass );
            item.setFighter( fighter );
            item.setFighterId( fighter.getId() );
            item.setNumberInPool( i );
            i++;
            itemList.add( item );
        }
        itemList = doFightsForPool( fightingclass, ctx, itemList );

        if ( ( revenge == 1 || revenge == 2 ) && itemList.size() == 2 )
        {
            Fight fight = new Fight();
            setTechnicalDBFieldsForCreate( fight, ctx );
            fight.setFightingclassId( fightingclass.getId() );
            fight.setFighterIdRed( itemList.get( 1 ).getFighter().getId() );
            fight.setFighterIdBlue( itemList.get( 0 ).getFighter().getId() );
            // first Fight for both
            itemList.get( 0 ).setFight2( fight );
            itemList.get( 1 ).setFight2( fight );
        }

        if ( revenge == 2 && itemList.size() == 2 )
        {
            Fight fight = new Fight();
            setTechnicalDBFieldsForCreate( fight, ctx );
            fight.setFightingclassId( fightingclass.getId() );
            fight.setFighterIdRed( itemList.get( 0 ).getFighter().getId() );
            fight.setFighterIdBlue( itemList.get( 1 ).getFighter().getId() );
            // first Fight for both
            itemList.get( 0 ).setFight3( fight );
            itemList.get( 1 ).setFight3( fight );
        }

        simplePool.setFighterList( itemList );
        return simplePool;
    }

    /**
     * creates all nessesary fights
     *
     * @param fightingclass
     * @param ctx
     * @param itemList
     * @return
     */
    private List<FightingSimplePoolItem> doFightsForPool( Fightingclass fightingclass, ServiceExchangeContext ctx,
                                                          List<FightingSimplePoolItem> itemList )
    {
        Fight fight = null;
        switch ( itemList.size() )
        {

            case 5:
                fight = new Fight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setFightingclassId( fightingclass.getId() );
                fight.setFighterIdRed( itemList.get( 0 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 4 ).getFighter().getId() );
                //1st fight for fighter 5 and 4th fight for fighter 1
                itemList.get( 0 ).setFight4( fight );
                itemList.get( 4 ).setFight1( fight );

                fight = new Fight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setFightingclassId( fightingclass.getId() );
                fight.setFighterIdRed( itemList.get( 1 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 4 ).getFighter().getId() );
                //2nd fight for fighter 5 and 4th fight for fighter 2
                itemList.get( 1 ).setFight4( fight );
                itemList.get( 4 ).setFight2( fight );

                fight = new Fight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setFightingclassId( fightingclass.getId() );
                fight.setFighterIdRed( itemList.get( 2 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 4 ).getFighter().getId() );
                //3rd fight for fighter 5 and 4th fight for fighter 3
                itemList.get( 2 ).setFight4( fight );
                itemList.get( 4 ).setFight3( fight );

                fight = new Fight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setFightingclassId( fightingclass.getId() );
                fight.setFighterIdRed( itemList.get( 3 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 4 ).getFighter().getId() );
                //4th fight for fighter 5 and 4th fight for fighter 4
                itemList.get( 3 ).setFight4( fight );
                itemList.get( 4 ).setFight4( fight );

            case 4:
                fight = new Fight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setFightingclassId( fightingclass.getId() );
                fight.setFighterIdRed( itemList.get( 2 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 3 ).getFighter().getId() );
                //3rd fight for fighter 4 and 3rd fight for fighter 3
                itemList.get( 2 ).setFight3( fight );
                itemList.get( 3 ).setFight3( fight );

                fight = new Fight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setFightingclassId( fightingclass.getId() );
                fight.setFighterIdRed( itemList.get( 1 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 3 ).getFighter().getId() );
                //2nd fight for fighter 4 and 3rd fight for fighter 2
                itemList.get( 1 ).setFight3( fight );
                itemList.get( 3 ).setFight2( fight );

                fight = new Fight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setFightingclassId( fightingclass.getId() );
                fight.setFighterIdRed( itemList.get( 0 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 3 ).getFighter().getId() );
                //first fight for fighter 4 and 3rd fight for fighter 1
                itemList.get( 0 ).setFight3( fight );
                itemList.get( 3 ).setFight1( fight );

            case 3:
                fight = new Fight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setFightingclassId( fightingclass.getId() );
                fight.setFighterIdRed( itemList.get( 0 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 2 ).getFighter().getId() );
                //first fight for fighter 3 and 2nd fight for fighter 1
                itemList.get( 0 ).setFight2( fight );
                itemList.get( 2 ).setFight1( fight );

                fight = new Fight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setFightingclassId( fightingclass.getId() );
                fight.setFighterIdRed( itemList.get( 1 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 2 ).getFighter().getId() );
                // second Fight for both
                itemList.get( 1 ).setFight2( fight );
                itemList.get( 2 ).setFight2( fight );

            case 2:
                fight = new Fight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setFightingclassId( fightingclass.getId() );
                fight.setFighterIdRed( itemList.get( 0 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 1 ).getFighter().getId() );
                // first Fight for both
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

    public Fightingclass doDoublePool( List<Fighter> fighters, Fightingclass fightingclass, ServiceExchangeContext ctx )
    {
        FightingDoublePoolClass doublePool = new FightingDoublePoolClass();
        setTechnicalDBFieldsForUpdate( doublePool, ctx );
        fightingclass.setInuse( true );

        FightingDoublePoolItem item = null;

        List<FightingDoublePoolItem> itemListPoolA = new ArrayList<FightingDoublePoolItem>( (int) fighters.size() / 2 );
        List<FightingDoublePoolItem> itemListPoolB =
            new ArrayList<FightingDoublePoolItem>( (int) ( fighters.size() / 2 - 0.5f ) );
        String pool = IValueConstants.POOL_A;
        int i = 0, j = 0;
        for ( Fighter fighter : fighters )
        {

            item = new FightingDoublePoolItem();
            setTechnicalDBFieldsForCreate( item, ctx );
            item.setFightingclass( fightingclass );
            item.setFighter( fighter );
            item.setFighterId( fighter.getId() );
            if ( pool.equalsIgnoreCase( IValueConstants.POOL_A ) )
            {
                //negative logic, here is Pool B
                pool = IValueConstants.POOL_B;
                item.setPoolPart( IValueConstants.POOL_A );
                item.setFightingclassId( fightingclass.getId() );
                item.setNumberInPool( j );
                j++;
                itemListPoolB.add( item );
            }
            else
            {
                //negative logic, here is Pool A
                pool = IValueConstants.POOL_A;
                item.setPoolPart( IValueConstants.POOL_B );
                item.setFightingclassId( fightingclass.getId() );
                item.setNumberInPool( i );
                i++;
                itemListPoolA.add( item );
            }
            itemListPoolA = doFightsForDPool( fightingclass, ctx, itemListPoolA );
            itemListPoolB = doFightsForDPool( fightingclass, ctx, itemListPoolB );
        }
        doublePool.setFighterListPoolA( itemListPoolA );
        doublePool.setFighterListPoolB( itemListPoolB );
        //halfFinal 1
        Fight fight = new Fight();
        setTechnicalDBFieldsForCreate( fight, ctx );
        fight.setFightingclassId( fightingclass.getId() );
        fight.setFlag( IValueConstants.HALF_FINAL_FIGHT_1 );
        doublePool.setHalfFinalFight1(  fight );
      //halfFinal 2
        fight = new Fight();
        setTechnicalDBFieldsForCreate( fight, ctx );
        fight.setFightingclassId( fightingclass.getId() );
        fight.setFlag( IValueConstants.HALF_FINAL_FIGHT_2 );
        doublePool.setHalfFinalFight2( fight );
        //finalFight
        fight = new Fight();
        setTechnicalDBFieldsForCreate( fight, ctx );
        fight.setFightingclassId( fightingclass.getId() );
        fight.setFlag( IValueConstants.FINAL_FIGHT );
        doublePool.setFinalFight( fight );
        return doublePool;
    }


    private List<FightingDoublePoolItem> doFightsForDPool( Fightingclass fightingclass, ServiceExchangeContext ctx,
                                                           List<FightingDoublePoolItem> itemList )
    {
        Fight fight = null;
        switch ( itemList.size() )
        {

            case 5:
                fight = new Fight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setFightingclassId( fightingclass.getId() );
                fight.setFighterIdRed( itemList.get( 0 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 4 ).getFighter().getId() );
                //1st fight for fighter 5 and 4th fight for fighter 1
                itemList.get( 0 ).setFight4( fight );
                itemList.get( 4 ).setFight1( fight );

                fight = new Fight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setFightingclassId( fightingclass.getId() );
                fight.setFighterIdRed( itemList.get( 1 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 4 ).getFighter().getId() );
                //2nd fight for fighter 5 and 4th fight for fighter 2
                itemList.get( 1 ).setFight4( fight );
                itemList.get( 4 ).setFight2( fight );

                fight = new Fight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setFightingclassId( fightingclass.getId() );
                fight.setFighterIdRed( itemList.get( 2 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 4 ).getFighter().getId() );
                //3rd fight for fighter 5 and 4th fight for fighter 3
                itemList.get( 2 ).setFight4( fight );
                itemList.get( 4 ).setFight3( fight );

                fight = new Fight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setFightingclassId( fightingclass.getId() );
                fight.setFighterIdRed( itemList.get( 3 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 4 ).getFighter().getId() );
                //4th fight for fighter 5 and 4th fight for fighter 4
                itemList.get( 3 ).setFight4( fight );
                itemList.get( 4 ).setFight4( fight );

            case 4:
                fight = new Fight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setFightingclassId( fightingclass.getId() );
                fight.setFighterIdRed( itemList.get( 2 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 3 ).getFighter().getId() );
                //3rd fight for fighter 4 and 3rd fight for fighter 3
                itemList.get( 2 ).setFight3( fight );
                itemList.get( 3 ).setFight3( fight );

                fight = new Fight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setFightingclassId( fightingclass.getId() );
                fight.setFighterIdRed( itemList.get( 1 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 3 ).getFighter().getId() );
                //2nd fight for fighter 4 and 3rd fight for fighter 2
                itemList.get( 1 ).setFight3( fight );
                itemList.get( 3 ).setFight2( fight );

                fight = new Fight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setFightingclassId( fightingclass.getId() );
                fight.setFighterIdRed( itemList.get( 0 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 3 ).getFighter().getId() );
                //first fight for fighter 4 and 3rd fight for fighter 1
                itemList.get( 0 ).setFight3( fight );
                itemList.get( 3 ).setFight1( fight );

            case 3:
                fight = new Fight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setFightingclassId( fightingclass.getId() );
                fight.setFighterIdRed( itemList.get( 0 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 2 ).getFighter().getId() );
                //first fight for fighter 3 and 2nd fight for fighter 1
                itemList.get( 0 ).setFight2( fight );
                itemList.get( 2 ).setFight1( fight );

                fight = new Fight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setFightingclassId( fightingclass.getId() );
                fight.setFighterIdRed( itemList.get( 1 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 2 ).getFighter().getId() );
                // second Fight for both
                itemList.get( 1 ).setFight2( fight );
                itemList.get( 2 ).setFight2( fight );

            case 2:
                fight = new Fight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setFightingclassId( fightingclass.getId() );
                fight.setFighterIdRed( itemList.get( 0 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 1 ).getFighter().getId() );
                // first Fight for both
                itemList.get( 0 ).setFight1( fight );
                itemList.get( 1 ).setFight1( fight );
                break;
        }
        return itemList;

    }
}
