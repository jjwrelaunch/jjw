/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoSimplePoolMapper.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:41
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

package de.jjw.service.mapper.duo;

import java.sql.Timestamp;

import de.jjw.model.duo.DuoSimplePoolClass;
import de.jjw.model.duo.DuoSimplePoolItem;
import de.jjw.service.mapper.admin.AgeMapper;


public class DuoSimplePoolMapper
{


    public static DuoSimplePoolClass mapDuoSimplePoolClassFromDB( DuoSimplePoolClass duoclass )
    {
        DuoSimplePoolClass ret = new DuoSimplePoolClass();

        if ( duoclass.getAge() == null )
        {
            ret.setAge( AgeMapper.mapAgeFromDB( duoclass.getDuoTeamList().get( 0 ).getDuoclass().getAge() ) );
        }

        DuoclassMapper.mapDuoclassToDB( duoclass.getDuoTeamList().get( 0 ).getDuoclass(), ret );
        for ( DuoSimplePoolItem item : duoclass.getDuoTeamList() )
        {
            ret.addDuoTeamList( mapDuoSimplePoolItemFromDB( item ) );
        }

        return ret;
    }

    public static DuoSimplePoolItem mapDuoSimplePoolItemFromDB( DuoSimplePoolItem item )
    {
        if ( item == null )
        {
            return null;
        }

        DuoSimplePoolItem ret = new DuoSimplePoolItem();

        ret.setNumberInPool( item.getNumberInPool() );
        ret.setDuoTeam( DuoTeamMapper.mapDuoTeamFromDB( item.getDuoTeam() ) );
        ret.setFight1( DuoFightMapper.mapFightFromDB( item.getFight1() ) );
        ret.setFight2( DuoFightMapper.mapFightFromDB( item.getFight2() ) );
        ret.setFight3( DuoFightMapper.mapFightFromDB( item.getFight3() ) );
        ret.setFight4( DuoFightMapper.mapFightFromDB( item.getFight4() ) );

        ret.setCreateDate( new Timestamp( item.getCreateDate().getTime() ) );
        ret.setCreateId( Long.valueOf( item.getCreateId() ) );
        ret.setId( Long.valueOf( item.getId() ) );
        ret.setModificationDate( new Timestamp( item.getModificationDate().getTime() ) );
        ret.setModificationId( Long.valueOf( item.getModificationId() ) );
        return ret;
    }
}