/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaSimplePoolMapper.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:43
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

import de.jjw.model.newa.NewaSimplePoolClass;
import de.jjw.model.newa.NewaSimplePoolItem;
import de.jjw.service.mapper.admin.AgeMapper;

public class NewaSimplePoolMapper
{


    public static NewaSimplePoolClass mapFightingSimplePoolClassFromDB( NewaSimplePoolClass fightingclass )
    {
        NewaSimplePoolClass ret = new NewaSimplePoolClass();
        if ( fightingclass.getAge() == null )
        {
            ret.setAge( AgeMapper.mapAgeFromDB( fightingclass.getFighterList().get( 0 ).getNewaclass().getAge() ) );
        }

        NewaWeightclassMapper.mapWeightclassToDB( fightingclass.getFighterList().get( 0 ).getNewaclass(), ret );
        for ( NewaSimplePoolItem item : fightingclass.getFighterList() )
        {
            ret.addFighterList( mapFightingSimplePoolItemFromDB( item ) );
        }

        return ret;
    }

    public static NewaSimplePoolItem mapFightingSimplePoolItemFromDB( NewaSimplePoolItem item )
    {
        if ( item == null )
        {
            return null;
        }

        NewaSimplePoolItem ret = new NewaSimplePoolItem();

        ret.setNumberInPool( item.getNumberInPool() );
        ret.setFighter( NewaFighterMapper.mapFighterFromDB( item.getFighter() ) );
        ret.setFight1( NewaFightMapper.mapFightFromDB( item.getFight1() ) );
        ret.setFight2( NewaFightMapper.mapFightFromDB( item.getFight2() ) );
        ret.setFight3( NewaFightMapper.mapFightFromDB( item.getFight3() ) );
        ret.setFight4( NewaFightMapper.mapFightFromDB( item.getFight4() ) );

        ret.setCreateDate( new Timestamp( item.getCreateDate().getTime() ) );
        ret.setCreateId( Long.valueOf( item.getCreateId() ) );
        ret.setId( Long.valueOf( item.getId() ) );
        ret.setModificationDate( new Timestamp( item.getModificationDate().getTime() ) );
        ret.setModificationId( Long.valueOf( item.getModificationId() ) );
        return ret;
    }
}