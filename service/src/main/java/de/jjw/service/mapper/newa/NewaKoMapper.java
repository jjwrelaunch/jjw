/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaFightingKoMapper.java
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

package de.jjw.service.mapper.newa;


import de.jjw.model.newa.NewaKoClass;
import de.jjw.model.newa.NewaKoItem;
import de.jjw.service.mapper.admin.AgeMapper;
import de.jjw.util.Utility;

public class NewaKoMapper
{

    public static NewaKoClass mapFightingKoClassFromDB( NewaKoClass fightingclass )
    {
        NewaKoClass ret = new NewaKoClass();

        if ( fightingclass.getAge() == null )
        {
            ret.setAge( AgeMapper.mapAgeFromDB( fightingclass.getFighterListPoolA().get( 0 ).getNewaclass().getAge() ) );
        }

        NewaWeightclassMapper.mapWeightclassToDB( fightingclass.getFighterListPoolA().get( 0 ).getNewaclass(), ret );

        ret.setId( new Long( fightingclass.getId() ) );

        for ( NewaKoItem item : fightingclass.getFighterListPoolA() )
        {
            ret.addFighterListPoolA( mapFightingKoItemFromDB( item ) );
        }

        for ( NewaKoItem item : fightingclass.getFighterListPoolB() )
        {
            ret.addFighterListPoolB( mapFightingKoItemFromDB( item ) );
        }

        for ( Integer number : fightingclass.getFightListMapPoolA().keySet() )
        {
            ret.putFightListMapPoolA( number,
                                      NewaFightMapper.mapFightFromDB( fightingclass.getFightListMapPoolA().get( number ) ) );
        }

        for ( Integer number : fightingclass.getFightListMapPoolB().keySet() )
        {
            ret.putFightListMapPoolB( number,
                                      NewaFightMapper.mapFightFromDB( fightingclass.getFightListMapPoolB().get( number ) ) );
        }

        for ( Integer number : fightingclass.getFightListLooserMapPoolA().keySet() )
        {
            ret.putFightListLoserMapPoolA(
                                           number,
                                           NewaFightMapper.mapFightFromDB( fightingclass.getFightListLooserMapPoolA().get(
                                                                                                                       number ) ) );
        }

        for ( Integer number : fightingclass.getFightListLooserMapPoolB().keySet() )
        {
            ret.putFightListLoserMapPoolB(
                                           number,
                                           NewaFightMapper.mapFightFromDB( fightingclass.getFightListLooserMapPoolB().get(
                                                                                                                       number ) ) );
        }

        if ( fightingclass.getFinalFight() != null )
        {
            ret.setFinalFight( NewaFightMapper.mapFightFromDB( fightingclass.getFinalFight() ) );
        }
        
        ret.setThirdPlaceFightsChanged( fightingclass.isThirdPlaceFightsChanged() );
        return ret;
    }

    public static NewaKoItem mapFightingKoItemFromDB( NewaKoItem item )
    {
        if ( item == null )
        {
            return null;
        }

        NewaKoItem ret = (NewaKoItem) Utility.getInstance().deepCopy( item );

     		 
        // ret.setCreateDate(new Timestamp(item.getCreateDate().getTime()));
        // ret.setCreateId(new Long(item.getCreateId()));
        // ret.setId(new Long(item.getId()));
        // ret.setModificationDate(new Timestamp(item.getModificationDate().getTime()));
        // ret.setModificationId(new Long(item.getModificationId()));
        return ret;
    }

}
