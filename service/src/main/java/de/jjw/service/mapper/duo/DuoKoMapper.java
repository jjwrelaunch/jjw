/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoKoMapper.java
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

package de.jjw.service.mapper.duo;

import de.jjw.model.duo.DuoKoClass;
import de.jjw.model.duo.DuoKoItem;
import de.jjw.service.mapper.admin.AgeMapper;
import de.jjw.util.Utility;

public class DuoKoMapper
{

    public static DuoKoClass mapDuoKoClassFromDB( DuoKoClass duoclass )
    {
        DuoKoClass ret = new DuoKoClass();

        if ( duoclass.getAge() == null )
        {
            ret.setAge( AgeMapper.mapAgeFromDB( duoclass.getTeamListPoolA().get( 0 ).getDuoclass().getAge() ) );
        }

        DuoclassMapper.mapDuoclassToDB( duoclass.getTeamListPoolA().get( 0 ).getDuoclass(), ret );

        ret.setId( new Long( duoclass.getId() ) );

        for ( DuoKoItem item : duoclass.getTeamListPoolA() )
        {
            ret.addTeamListPoolA( mapDuoKoItemFromDB( item ) );
        }

        for ( DuoKoItem item : duoclass.getTeamListPoolB() )
        {
            ret.addTeamListPoolB( mapDuoKoItemFromDB( item ) );
        }

        for ( Integer number : duoclass.getFightListMapPoolA().keySet() )
        {
            ret.putFightListMapPoolA( number,
                                      DuoFightMapper.mapFightFromDB( duoclass.getFightListMapPoolA().get( number ) ) );
        }

        for ( Integer number : duoclass.getFightListMapPoolB().keySet() )
        {
            ret.putFightListMapPoolB( number,
                                      DuoFightMapper.mapFightFromDB( duoclass.getFightListMapPoolB().get( number ) ) );
        }

        for ( Integer number : duoclass.getFightListLooserMapPoolA().keySet() )
        {
            ret.putFightListLoserMapPoolA(
                                           number,
                                           DuoFightMapper.mapFightFromDB( duoclass.getFightListLooserMapPoolA().get(
                                                                                                                     number ) ) );
        }

        for ( Integer number : duoclass.getFightListLooserMapPoolB().keySet() )
        {
            ret.putFightListLoserMapPoolB(
                                           number,
                                           DuoFightMapper.mapFightFromDB( duoclass.getFightListLooserMapPoolB().get(
                                                                                                                     number ) ) );
        }

        if ( duoclass.getFinalFight() != null )
        {
            ret.setFinalFight( DuoFightMapper.mapFightFromDB( duoclass.getFinalFight() ) );
        }
        
        ret.setThirdPlaceFightsChanged( duoclass.isThirdPlaceFightsChanged() );
        return ret;
    }

    public static DuoKoItem mapDuoKoItemFromDB( DuoKoItem item )
    {
        if ( item == null )
        {
            return null;
        }

        DuoKoItem ret = (DuoKoItem) Utility.getInstance().deepCopy( item );

        return ret;
    }

}
