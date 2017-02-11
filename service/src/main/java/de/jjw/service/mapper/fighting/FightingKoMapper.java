/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightingKoMapper.java
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

package de.jjw.service.mapper.fighting;


import de.jjw.model.fighting.FightingKoClass;
import de.jjw.model.fighting.FightingKoItem;
import de.jjw.service.mapper.admin.AgeMapper;
import de.jjw.util.Utility;

public class FightingKoMapper
{

    public static FightingKoClass mapFightingKoClassFromDB( FightingKoClass fightingclass )
    {
        FightingKoClass ret = new FightingKoClass();

        if ( fightingclass.getAge() == null )
        {
            ret.setAge( AgeMapper.mapAgeFromDB( fightingclass.getFighterListPoolA().get( 0 ).getFightingclass().getAge() ) );
        }

        WeightclassMapper.mapWeightclassToDB( fightingclass.getFighterListPoolA().get( 0 ).getFightingclass(), ret );

        ret.setId( new Long( fightingclass.getId() ) );

        for ( FightingKoItem item : fightingclass.getFighterListPoolA() )
        {
            ret.addFighterListPoolA( mapFightingKoItemFromDB( item ) );
        }

        for ( FightingKoItem item : fightingclass.getFighterListPoolB() )
        {
            ret.addFighterListPoolB( mapFightingKoItemFromDB( item ) );
        }

        for ( Integer number : fightingclass.getFightListMapPoolA().keySet() )
        {
            ret.putFightListMapPoolA( number,
                                      FightMapper.mapFightFromDB( fightingclass.getFightListMapPoolA().get( number ) ) );
        }

        for ( Integer number : fightingclass.getFightListMapPoolB().keySet() )
        {
            ret.putFightListMapPoolB( number,
                                      FightMapper.mapFightFromDB( fightingclass.getFightListMapPoolB().get( number ) ) );
        }

        for ( Integer number : fightingclass.getFightListLooserMapPoolA().keySet() )
        {
            ret.putFightListLoserMapPoolA(
                                           number,
                                           FightMapper.mapFightFromDB( fightingclass.getFightListLooserMapPoolA().get(
                                                                                                                       number ) ) );
        }

        for ( Integer number : fightingclass.getFightListLooserMapPoolB().keySet() )
        {
            ret.putFightListLoserMapPoolB(
                                           number,
                                           FightMapper.mapFightFromDB( fightingclass.getFightListLooserMapPoolB().get(
                                                                                                                       number ) ) );
        }

        if ( fightingclass.getFinalFight() != null )
        {
            ret.setFinalFight( FightMapper.mapFightFromDB( fightingclass.getFinalFight() ) );
        }
        
        ret.setThirdPlaceFightsChanged( fightingclass.isThirdPlaceFightsChanged() );
        return ret;
    }

    public static FightingKoItem mapFightingKoItemFromDB( FightingKoItem item )
    {
        if ( item == null )
        {
            return null;
        }

        FightingKoItem ret = (FightingKoItem) Utility.getInstance().deepCopy( item );


        
        //		 
        // ret.setCreateDate(new Timestamp(item.getCreateDate().getTime()));
        // ret.setCreateId(new Long(item.getCreateId()));
        // ret.setId(new Long(item.getId()));
        // ret.setModificationDate(new Timestamp(item.getModificationDate().getTime()));
        // ret.setModificationId(new Long(item.getModificationId()));
        return ret;
    }

}
