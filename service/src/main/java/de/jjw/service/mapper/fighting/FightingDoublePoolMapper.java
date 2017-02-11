/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightingDoublePoolMapper.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:44
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

package de.jjw.service.mapper.fighting;

import java.sql.Timestamp;

import de.jjw.model.fighting.FightingDoublePoolClass;
import de.jjw.model.fighting.FightingDoublePoolItem;
import de.jjw.service.mapper.admin.AgeMapper;

public class FightingDoublePoolMapper
{

    public static FightingDoublePoolClass mapFightingDoubePoolClassFromDB( FightingDoublePoolClass fightingclass )
    {
        FightingDoublePoolClass ret = new FightingDoublePoolClass();
        if ( fightingclass.getAge() == null )
        {
            ret.setAge(
                AgeMapper.mapAgeFromDB( fightingclass.getFighterListPoolA().get( 0 ).getFightingclass().getAge() ) );
        }

        WeightclassMapper.mapWeightclassToDB( fightingclass.getFighterListPoolA().get( 0 ).getFightingclass(), ret );

        for ( FightingDoublePoolItem item : fightingclass.getFighterListPoolA() )
        {
            ret.addFighterListPoolA( mapFightingDoublePoolItemFromDB( item ) );
        }

        for ( FightingDoublePoolItem item : fightingclass.getFighterListPoolB() )
        {
            ret.addFighterListPoolB( mapFightingDoublePoolItemFromDB( item ) );
        }

        
        if ( fightingclass.getHalfFinalFight1() != null )
        {
            ret.setHalfFinalFight1( FightMapper.mapFightFromDB( fightingclass.getHalfFinalFight1() ) );
        }
        if ( fightingclass.getHalfFinalFight2() != null )
        {
            ret.setHalfFinalFight2( FightMapper.mapFightFromDB( fightingclass.getHalfFinalFight2() ) );
        }
        if ( fightingclass.getFinalFight() != null )
        {
            ret.setFinalFight( FightMapper.mapFightFromDB( fightingclass.getFinalFight() ) );
        }
        return ret;
    }

    public static FightingDoublePoolItem mapFightingDoublePoolItemFromDB( FightingDoublePoolItem item )
    {
        if ( item == null )
        {
            return null;
        }

        FightingDoublePoolItem ret = new FightingDoublePoolItem();

        ret.setNumberInPool( item.getNumberInPool() );
        ret.setFighter( FighterMapper.mapFighterFromDB( item.getFighter() ) );
        ret.setFight1( FightMapper.mapFightFromDB( item.getFight1() ) );
        ret.setFight2( FightMapper.mapFightFromDB( item.getFight2() ) );
        ret.setFight3( FightMapper.mapFightFromDB( item.getFight3() ) );
        ret.setFight4( FightMapper.mapFightFromDB( item.getFight4() ) );

        if ( item.getFinalFight() != null )
        {
            ret.setFinalFight( FightMapper.mapFightFromDB( item.getFinalFight() ) );
        }

        ret.setCreateDate( new Timestamp( item.getCreateDate().getTime() ) );
        ret.setCreateId( Long.valueOf( item.getCreateId() ) );
        ret.setId( Long.valueOf( item.getId() ) );
        ret.setModificationDate( new Timestamp( item.getModificationDate().getTime() ) );
        ret.setModificationId( Long.valueOf( item.getModificationId() ) );
        return ret;
    }

}
