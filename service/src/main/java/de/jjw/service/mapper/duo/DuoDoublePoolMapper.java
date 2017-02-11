/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoDoublePoolMapper.java
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

import de.jjw.model.duo.DuoDoublePoolClass;
import de.jjw.model.duo.DuoDoublePoolItem;
import de.jjw.service.mapper.admin.AgeMapper;


public class DuoDoublePoolMapper
{

    public static DuoDoublePoolClass mapDuoDoubePoolClassFromDB( DuoDoublePoolClass duoclass )
    {
        DuoDoublePoolClass ret = new DuoDoublePoolClass();

        if ( duoclass.getAge() == null )
        {
            ret.setAge( AgeMapper.mapAgeFromDB( duoclass.getDuoListPoolA().get( 0 ).getDuoclass().getAge() ) );
        }

        DuoclassMapper.mapDuoclassToDB( duoclass.getDuoListPoolA().get( 0 ).getDuoclass(), ret );

        for ( DuoDoublePoolItem item : duoclass.getDuoListPoolA() )
        {
            ret.addDuoListPoolA( mapDuoDoublePoolItemFromDB( item ) );
        }

        for ( DuoDoublePoolItem item : duoclass.getDuoListPoolB() )
        {
            ret.addDuoListPoolB( mapDuoDoublePoolItemFromDB( item ) );
        }
        
        if ( duoclass.getHalfFinalFight1() != null )
        {
            ret.setHalfFinalFight1( DuoFightMapper.mapFightFromDB( duoclass.getHalfFinalFight1() ) );
        }
        if ( duoclass.getHalfFinalFight2() != null )
        {
            ret.setHalfFinalFight2( DuoFightMapper.mapFightFromDB( duoclass.getHalfFinalFight2() ) );
        }

        if ( duoclass.getFinalFight() != null )
        {
            ret.setFinalFight( DuoFightMapper.mapFightFromDB( duoclass.getFinalFight() ) );
        }
        return ret;
    }

    public static DuoDoublePoolItem mapDuoDoublePoolItemFromDB( DuoDoublePoolItem item )
    {
        if ( item == null )
        {
            return null;
        }

        DuoDoublePoolItem ret = new DuoDoublePoolItem();

        ret.setNumberInPool( item.getNumberInPool() );
        ret.setDuoTeam( DuoTeamMapper.mapDuoTeamFromDB( item.getDuoTeam() ) );
        ret.setFight1( DuoFightMapper.mapFightFromDB( item.getFight1() ) );
        ret.setFight2( DuoFightMapper.mapFightFromDB( item.getFight2() ) );
        ret.setFight3( DuoFightMapper.mapFightFromDB( item.getFight3() ) );
        ret.setFight4( DuoFightMapper.mapFightFromDB( item.getFight4() ) );

        if ( item.getFinalFight() != null )
        {
            ret.setFinalFight( DuoFightMapper.mapFightFromDB( item.getFinalFight() ) );
        }

        ret.setCreateDate( new Timestamp( item.getCreateDate().getTime() ) );
        ret.setCreateId( Long.valueOf( item.getCreateId() ) );
        ret.setId( Long.valueOf( item.getId() ) );
        ret.setModificationDate( new Timestamp( item.getModificationDate().getTime() ) );
        ret.setModificationId( Long.valueOf( item.getModificationId() ) );
        return ret;
    }

}
