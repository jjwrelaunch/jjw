/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaDoublePoolMapper.java
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

package de.jjw.service.mapper.newa;

import java.sql.Timestamp;

import de.jjw.model.newa.NewaDoublePoolClass;
import de.jjw.model.newa.NewaDoublePoolItem;
import de.jjw.service.mapper.admin.AgeMapper;

public class NewaDoublePoolMapper
{

    public static NewaDoublePoolClass mapFightingDoubePoolClassFromDB( NewaDoublePoolClass fightingclass )
    {
        NewaDoublePoolClass ret = new NewaDoublePoolClass();
        if ( fightingclass.getAge() == null )
        {
            ret.setAge(
 AgeMapper.mapAgeFromDB( fightingclass.getFighterListPoolA().get( 0 ).getNewaclass().getAge() ) );
        }

        NewaWeightclassMapper.mapWeightclassToDB( fightingclass.getFighterListPoolA().get( 0 ).getNewaclass(), ret );

        for ( NewaDoublePoolItem item : fightingclass.getFighterListPoolA() )
        {
            ret.addFighterListPoolA( mapNewaDoublePoolItemFromDB( item ) );
        }

        for ( NewaDoublePoolItem item : fightingclass.getFighterListPoolB() )
        {
            ret.addFighterListPoolB( mapNewaDoublePoolItemFromDB( item ) );
        }
        
        
        if ( fightingclass.getHalfFinalFight1() != null )
        {
            ret.setHalfFinalFight1( NewaFightMapper.mapFightFromDB( fightingclass.getHalfFinalFight1() ) );
        }
        if ( fightingclass.getHalfFinalFight2() != null )
        {
            ret.setHalfFinalFight2( NewaFightMapper.mapFightFromDB( fightingclass.getHalfFinalFight2() ) );
        }


        if ( fightingclass.getFinalFight() != null )
        {
            ret.setFinalFight( NewaFightMapper.mapFightFromDB( fightingclass.getFinalFight() ) );
        }
        return ret;
    }

    public static NewaDoublePoolItem mapNewaDoublePoolItemFromDB( NewaDoublePoolItem item )
    {
        if ( item == null )
        {
            return null;
        }

        NewaDoublePoolItem ret = new NewaDoublePoolItem();

        ret.setNumberInPool( item.getNumberInPool() );
        ret.setFighter( NewaFighterMapper.mapFighterFromDB( item.getFighter() ) );
        ret.setFight1( NewaFightMapper.mapFightFromDB( item.getFight1() ) );
        ret.setFight2( NewaFightMapper.mapFightFromDB( item.getFight2() ) );
        ret.setFight3( NewaFightMapper.mapFightFromDB( item.getFight3() ) );
        ret.setFight4( NewaFightMapper.mapFightFromDB( item.getFight4() ) );

        if ( item.getFinalFight() != null )
        {
            ret.setFinalFight( NewaFightMapper.mapFightFromDB( item.getFinalFight() ) );
        }

        ret.setCreateDate( new Timestamp( item.getCreateDate().getTime() ) );
        ret.setCreateId( Long.valueOf( item.getCreateId() ) );
        ret.setId( Long.valueOf( item.getId() ) );
        ret.setModificationDate( new Timestamp( item.getModificationDate().getTime() ) );
        ret.setModificationId( Long.valueOf( item.getModificationId() ) );
        return ret;
    }

}
