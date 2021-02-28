/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaFighterMapper.java
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.jjw.model.Team;
import de.jjw.model.admin.Age;
import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.model.newa.NewaFighter;
import de.jjw.model.newa.Newaclass;
import de.jjw.service.mapper.admin.AgeMapper;
import de.jjw.service.mapper.admin.TeamMapper;
import de.jjw.service.modelWeb.NewaFighterWeb;
import de.jjw.service.util.IGlobalProjectConstants;
import de.jjw.util.ICodestableTypeConstants;
import de.jjw.util.IValueConstants;

public class NewaFighterMapper
    implements ICodestableTypeConstants, IValueConstants, IGlobalProjectConstants
{

    public static List<NewaFighterWeb> mapFighterListFromDB( List<NewaFighter> fighters, Locale local )
    {
        List<NewaFighterWeb> ret = new ArrayList<NewaFighterWeb>( fighters.size() + 1 );
        NewaFighterWeb fighterWeb = null;
        for ( NewaFighter fighter : fighters )
        {
            fighterWeb = mapFighterFromDB( fighter );
            fighterWeb.setSexWeb(
                CodestableMain.getInstance().getCodestableCodeValueByType( TYPE_SEX, fighter.getSex(), local ) );
            fighterWeb.setPlaceWeb( String.valueOf( fighter.getPlace() ) );
            if ( STRING_1.equals( fighter.getReady() ) )
            {
                fighterWeb.setReadyWeb( IMAGE_JJW_DIR + OK_GIF );
            }
            else
            {
                fighterWeb.setReadyWeb( IMAGE_JJW_DIR + X_GIF );
            }
            ret.add( fighterWeb );
        }

        return ret;
    }

    /**
     * maps a Fighter from DB and get a new Fighter object
     *
     * @param fighter
     * @return
     */
    public static NewaFighterWeb mapFighterFromDB( NewaFighter fighter )
    {
        if ( fighter == null )
        {
            return null;
        }
        NewaFighterWeb ret = new NewaFighterWeb();

        ret.setAge( AgeMapper.mapAgeFromDB( fighter.getAge() ) );
        ret.setAgeId( fighter.getAgeId() );
        ret.setFirstname( new String( fighter.getFirstname() ) );
        ret.setName( new String( fighter.getName() ) );
        ret.setPlace( fighter.getPlace() );
        ret.setReady( fighter.getReady() );
        ret.setSex( new String( fighter.getSex() ) );
        ret.setTeam( TeamMapper.mapTeamFromDB( fighter.getTeam() ) );
        ret.setTeamId( fighter.getTeamId() );
        ret.setWeight( fighter.getWeight() );
        ret.setNewaclassId( fighter.getNewaclassId() );
        ret.setYearOfBirth( fighter.getYearOfBirth() );
        ret.setMonthOfBirth( fighter.getMonthOfBirth() );
        ret.setDayOfBirth( fighter.getDayOfBirth() );
        ret.setMarkForExport( fighter.isMarkForExport() );
        try
        {
            ret.setNewaclass( NewaWeightclassMapper.mapWeightclassFromDB( fighter.getNewaclass() ) );
        }
        catch ( Exception e )
        {
            // ignore, not corresponding weightclass
            ret.setNewaclass( null );
        }
        ret.setCreateDate( new Timestamp( fighter.getCreateDate().getTime() ) );
        ret.setCreateId( Long.valueOf( fighter.getCreateId() ) );
        ret.setId( Long.valueOf( fighter.getId() ) );
        ret.setModificationDate( new Timestamp( fighter.getModificationDate().getTime() ) );
        ret.setModificationId( Long.valueOf( fighter.getModificationId() ) );
        return ret;
    }

    public static void mapFighterToDB( NewaFighter fighter, NewaFighter fighterDB, boolean deepMapping )
    {

        if ( deepMapping )
        {
            Age ageDB = new Age();
            AgeMapper.mapAgeToDB( fighter.getAge(), ageDB );
            fighterDB.setAge( ageDB );
            Team teamDB = new Team();
            TeamMapper.mapTeamToDB( fighter.getTeam(), teamDB, false );
            fighterDB.setTeam( teamDB );
            if ( fighter.getNewaclass() != null )
            {
                Newaclass fc = new Newaclass();
                NewaWeightclassMapper.mapWeightclassToDB( fighter.getNewaclass(), fc );
                fighterDB.setNewaclass( fc );
            }
        }
        fighterDB.setAgeId( fighter.getAgeId() );
        fighterDB.setTeamId( fighter.getTeamId() );
        fighterDB.setNewaclassId( fighter.getNewaclassId() );
        fighterDB.setFirstname( new String( fighter.getFirstname() ) );
        fighterDB.setName( new String( fighter.getName() ) );
        fighterDB.setPlace( fighter.getPlace() );
        fighterDB.setReady( fighter.getReady() );
        fighterDB.setSex( new String( fighter.getSex() ) );

        fighterDB.setWeight( fighter.getWeight() );
        fighterDB.setYearOfBirth( fighter.getYearOfBirth() );
        fighterDB.setMonthOfBirth( fighter.getMonthOfBirth() );
        fighterDB.setDayOfBirth( fighter.getDayOfBirth() );
        fighterDB.setMarkForExport( fighter.isMarkForExport() );

//        if (fighter.getFightingclass() != null && fighterDB.getFightingclass() == null) {
//        	fighterDB.setFightingclass( new Fightingclass());        
//		}
//        
//        if (fighter.getFightingclass() != null && fighterDB.getFightingclass() != null) {        	
//        	fighterDB.setId(fighter.getFightingclass().getId());
//		}
//        
//		if (fighter.getFightingclass() == null && fighterDB.getFightingclass() != null) {
//			fighterDB.setFightingclass( null);			
//		}

        fighterDB.setModificationDate( new Timestamp( fighter.getModificationDate().getTime() ) );
        fighterDB.setModificationId( Long.valueOf( fighter.getModificationId() ) );

//        fighterDB.setTeamId(fighter.getTeamId());
//        fighterDB.setAgeId(fighter.getAgeId());
//        fighterDB.setFightingclassId(fighter.getFightingclassId());
    }


}
