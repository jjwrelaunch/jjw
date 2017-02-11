/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaclassPoolCreateImpl.java
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

package de.jjw.service.impl.newa;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import de.jjw.model.ITechnicalDBFields;
import de.jjw.model.newa.NewaDoublePoolClass;
import de.jjw.model.newa.NewaDoublePoolItem;
import de.jjw.model.newa.NewaFight;
import de.jjw.model.newa.NewaFighter;
import de.jjw.model.newa.NewaSimplePoolClass;
import de.jjw.model.newa.NewaSimplePoolItem;
import de.jjw.model.newa.Newaclass;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.util.IValueConstants;

public class NewaclassPoolCreateImpl
{

    public Newaclass doSimplePool( List<NewaFighter> fighters, Newaclass newaclass, ServiceExchangeContext ctx,
                                       int revenge )
    {
        NewaSimplePoolClass simplePool = new NewaSimplePoolClass();
        setTechnicalDBFieldsForUpdate( simplePool, ctx );
        newaclass.setInuse( true );

        NewaSimplePoolItem item = null;
        List<NewaSimplePoolItem> itemList = new ArrayList<NewaSimplePoolItem>( fighters.size() );
        int i = 0;
        for ( NewaFighter fighter : fighters )
        {
            item = new NewaSimplePoolItem();

            setTechnicalDBFieldsForCreate( item, ctx );

            item.setNewaclassId( newaclass.getId() );
            item.setNewaclass( newaclass );
            item.setFighter( fighter );
            item.setFighterId( fighter.getId() );
            item.setNumberInPool( i );
            i++;
            itemList.add( item );
        }
        itemList = doFightsForPool( newaclass, ctx, itemList );

        if ( ( revenge == 1 || revenge == 2 ) && itemList.size() == 2 )
        {
            NewaFight fight = new NewaFight();
            setTechnicalDBFieldsForCreate( fight, ctx );
            fight.setNewaclassId( newaclass.getId() );
            fight.setFighterIdRed( itemList.get( 1 ).getFighter().getId() );
            fight.setFighterIdBlue( itemList.get( 0 ).getFighter().getId() );
            // first Fight for both
            itemList.get( 0 ).setFight2( fight );
            itemList.get( 1 ).setFight2( fight );
        }

        if ( revenge == 2 && itemList.size() == 2 )
        {
            NewaFight fight = new NewaFight();
            setTechnicalDBFieldsForCreate( fight, ctx );
            fight.setNewaclassId( newaclass.getId() );
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
     * @param newaclass
     * @param ctx
     * @param itemList
     * @return
     */
    private List<NewaSimplePoolItem> doFightsForPool( Newaclass newaclass, ServiceExchangeContext ctx,
                                                      List<NewaSimplePoolItem> itemList )
    {
        NewaFight fight = null;
        switch ( itemList.size() )
        {

            case 5:
                fight = new NewaFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setNewaclassId( newaclass.getId() );
                fight.setFighterIdRed( itemList.get( 0 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 4 ).getFighter().getId() );
                //1st fight for fighter 5 and 4th fight for fighter 1
                itemList.get( 0 ).setFight4( fight );
                itemList.get( 4 ).setFight1( fight );

                fight = new NewaFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setNewaclassId( newaclass.getId() );
                fight.setFighterIdRed( itemList.get( 1 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 4 ).getFighter().getId() );
                //2nd fight for fighter 5 and 4th fight for fighter 2
                itemList.get( 1 ).setFight4( fight );
                itemList.get( 4 ).setFight2( fight );

                fight = new NewaFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setNewaclassId( newaclass.getId() );
                fight.setFighterIdRed( itemList.get( 2 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 4 ).getFighter().getId() );
                //3rd fight for fighter 5 and 4th fight for fighter 3
                itemList.get( 2 ).setFight4( fight );
                itemList.get( 4 ).setFight3( fight );

                fight = new NewaFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setNewaclassId( newaclass.getId() );
                fight.setFighterIdRed( itemList.get( 3 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 4 ).getFighter().getId() );
                //4th fight for fighter 5 and 4th fight for fighter 4
                itemList.get( 3 ).setFight4( fight );
                itemList.get( 4 ).setFight4( fight );

            case 4:
                fight = new NewaFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setNewaclassId( newaclass.getId() );
                fight.setFighterIdRed( itemList.get( 2 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 3 ).getFighter().getId() );
                //3rd fight for fighter 4 and 3rd fight for fighter 3
                itemList.get( 2 ).setFight3( fight );
                itemList.get( 3 ).setFight3( fight );

                fight = new NewaFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setNewaclassId( newaclass.getId() );
                fight.setFighterIdRed( itemList.get( 1 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 3 ).getFighter().getId() );
                //2nd fight for fighter 4 and 3rd fight for fighter 2
                itemList.get( 1 ).setFight3( fight );
                itemList.get( 3 ).setFight2( fight );

                fight = new NewaFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setNewaclassId( newaclass.getId() );
                fight.setFighterIdRed( itemList.get( 0 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 3 ).getFighter().getId() );
                //first fight for fighter 4 and 3rd fight for fighter 1
                itemList.get( 0 ).setFight3( fight );
                itemList.get( 3 ).setFight1( fight );

            case 3:
                fight = new NewaFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setNewaclassId( newaclass.getId() );
                fight.setFighterIdRed( itemList.get( 0 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 2 ).getFighter().getId() );
                //first fight for fighter 3 and 2nd fight for fighter 1
                itemList.get( 0 ).setFight2( fight );
                itemList.get( 2 ).setFight1( fight );

                fight = new NewaFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setNewaclassId( newaclass.getId() );
                fight.setFighterIdRed( itemList.get( 1 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 2 ).getFighter().getId() );
                // second Fight for both
                itemList.get( 1 ).setFight2( fight );
                itemList.get( 2 ).setFight2( fight );

            case 2:
                fight = new NewaFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setNewaclassId( newaclass.getId() );
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

    public Newaclass doDoublePool( List<NewaFighter> fighters, Newaclass newaclass, ServiceExchangeContext ctx )
    {
        NewaDoublePoolClass doublePool = new NewaDoublePoolClass();
        setTechnicalDBFieldsForUpdate( doublePool, ctx );
        newaclass.setInuse( true );

        NewaDoublePoolItem item = null;

        List<NewaDoublePoolItem> itemListPoolA = new ArrayList<NewaDoublePoolItem>( (int) fighters.size() / 2 );
        List<NewaDoublePoolItem> itemListPoolB =
            new ArrayList<NewaDoublePoolItem>( (int) ( fighters.size() / 2 - 0.5f ) );
        String pool = IValueConstants.POOL_A;
        int i = 0, j = 0;
        for ( NewaFighter fighter : fighters )
        {

            item = new NewaDoublePoolItem();
            setTechnicalDBFieldsForCreate( item, ctx );
            item.setNewaclass( newaclass );
            item.setFighter( fighter );
            item.setFighterId( fighter.getId() );
            if ( pool.equalsIgnoreCase( IValueConstants.POOL_A ) )
            {
                //negative logic, here is Pool B
                pool = IValueConstants.POOL_B;
                item.setPoolPart( IValueConstants.POOL_A );
                item.setNewaclassId( newaclass.getId() );
                item.setNumberInPool( j );
                j++;
                itemListPoolB.add( item );
            }
            else
            {
                //negative logic, here is Pool A
                pool = IValueConstants.POOL_A;
                item.setPoolPart( IValueConstants.POOL_B );
                item.setNewaclassId( newaclass.getId() );
                item.setNumberInPool( i );
                i++;
                itemListPoolA.add( item );
            }
            itemListPoolA = doFightsForDPool( newaclass, ctx, itemListPoolA );
            itemListPoolB = doFightsForDPool( newaclass, ctx, itemListPoolB );
        }
        doublePool.setFighterListPoolA( itemListPoolA );
        doublePool.setFighterListPoolB( itemListPoolB );
        //halfFinal 1
        NewaFight fight = new NewaFight();
        setTechnicalDBFieldsForCreate( fight, ctx );
        fight.setNewaclassId( newaclass.getId() );
        fight.setFlag( IValueConstants.HALF_FINAL_FIGHT_1 );
        doublePool.setHalfFinalFight1(  fight );
      //halfFinal 2
        fight = new NewaFight();
        setTechnicalDBFieldsForCreate( fight, ctx );
        fight.setNewaclassId( newaclass.getId() );
        fight.setFlag( IValueConstants.HALF_FINAL_FIGHT_2 );
        doublePool.setHalfFinalFight2( fight );

        //finalFight
        fight = new NewaFight();
        setTechnicalDBFieldsForCreate( fight, ctx );
        fight.setNewaclassId( newaclass.getId() );
        fight.setFlag( IValueConstants.FINAL_FIGHT );
        doublePool.setFinalFight( fight );
        return doublePool;
    }


    private List<NewaDoublePoolItem> doFightsForDPool( Newaclass newaclass, ServiceExchangeContext ctx,
                                                       List<NewaDoublePoolItem> itemList )
    {
        NewaFight fight = null;
        switch ( itemList.size() )
        {

            case 5:
                fight = new NewaFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setNewaclassId( newaclass.getId() );
                fight.setFighterIdRed( itemList.get( 0 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 4 ).getFighter().getId() );
                //1st fight for fighter 5 and 4th fight for fighter 1
                itemList.get( 0 ).setFight4( fight );
                itemList.get( 4 ).setFight1( fight );

                fight = new NewaFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setNewaclassId( newaclass.getId() );
                fight.setFighterIdRed( itemList.get( 1 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 4 ).getFighter().getId() );
                //2nd fight for fighter 5 and 4th fight for fighter 2
                itemList.get( 1 ).setFight4( fight );
                itemList.get( 4 ).setFight2( fight );

                fight = new NewaFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setNewaclassId( newaclass.getId() );
                fight.setFighterIdRed( itemList.get( 2 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 4 ).getFighter().getId() );
                //3rd fight for fighter 5 and 4th fight for fighter 3
                itemList.get( 2 ).setFight4( fight );
                itemList.get( 4 ).setFight3( fight );

                fight = new NewaFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setNewaclassId( newaclass.getId() );
                fight.setFighterIdRed( itemList.get( 3 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 4 ).getFighter().getId() );
                //4th fight for fighter 5 and 4th fight for fighter 4
                itemList.get( 3 ).setFight4( fight );
                itemList.get( 4 ).setFight4( fight );

            case 4:
                fight = new NewaFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setNewaclassId( newaclass.getId() );
                fight.setFighterIdRed( itemList.get( 2 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 3 ).getFighter().getId() );
                //3rd fight for fighter 4 and 3rd fight for fighter 3
                itemList.get( 2 ).setFight3( fight );
                itemList.get( 3 ).setFight3( fight );

                fight = new NewaFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setNewaclassId( newaclass.getId() );
                fight.setFighterIdRed( itemList.get( 1 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 3 ).getFighter().getId() );
                //2nd fight for fighter 4 and 3rd fight for fighter 2
                itemList.get( 1 ).setFight3( fight );
                itemList.get( 3 ).setFight2( fight );

                fight = new NewaFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setNewaclassId( newaclass.getId() );
                fight.setFighterIdRed( itemList.get( 0 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 3 ).getFighter().getId() );
                //first fight for fighter 4 and 3rd fight for fighter 1
                itemList.get( 0 ).setFight3( fight );
                itemList.get( 3 ).setFight1( fight );

            case 3:
                fight = new NewaFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setNewaclassId( newaclass.getId() );
                fight.setFighterIdRed( itemList.get( 0 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 2 ).getFighter().getId() );
                //first fight for fighter 3 and 2nd fight for fighter 1
                itemList.get( 0 ).setFight2( fight );
                itemList.get( 2 ).setFight1( fight );

                fight = new NewaFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setNewaclassId( newaclass.getId() );
                fight.setFighterIdRed( itemList.get( 1 ).getFighter().getId() );
                fight.setFighterIdBlue( itemList.get( 2 ).getFighter().getId() );
                // second Fight for both
                itemList.get( 1 ).setFight2( fight );
                itemList.get( 2 ).setFight2( fight );

            case 2:
                fight = new NewaFight();
                setTechnicalDBFieldsForCreate( fight, ctx );
                fight.setNewaclassId( newaclass.getId() );
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
