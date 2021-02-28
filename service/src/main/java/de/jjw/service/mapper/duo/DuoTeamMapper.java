/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoTeamMapper.java
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

package de.jjw.service.mapper.duo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.jjw.model.Team;
import de.jjw.model.admin.Age;
import de.jjw.model.duo.DuoTeam;
import de.jjw.model.duo.Duoclass;
import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.service.mapper.admin.AgeMapper;
import de.jjw.service.mapper.admin.TeamMapper;
import de.jjw.service.modelWeb.DuoTeamWeb;
import de.jjw.service.util.IGlobalProjectConstants;
import de.jjw.util.ICodestableTypeConstants;
import de.jjw.util.IValueConstants;

public class DuoTeamMapper
    implements ICodestableTypeConstants, IValueConstants, IGlobalProjectConstants
{

    public static List<DuoTeamWeb> mapDuoTeamListFromDB( List<DuoTeam> duoteams, Locale local )
    {
        List<DuoTeamWeb> ret = new ArrayList<DuoTeamWeb>( duoteams.size() + 1 );
        DuoTeamWeb fighterWeb = null;
        for ( DuoTeam fighter : duoteams )
        {
            fighterWeb = mapDuoTeamFromDB( fighter );
            fighterWeb.setSexWeb( CodestableMain.getInstance().getCodestableCodeValueByType( TYPE_SEX,
                                                                                             fighter.getSex(), local ) );
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
     * @param duoTeam
     * @return
     */
    public static DuoTeamWeb mapDuoTeamFromDB( DuoTeam duoTeam )
    {
        if ( duoTeam == null )
        {
            return null;
        }
        DuoTeamWeb ret = new DuoTeamWeb();

        ret.setAge( AgeMapper.mapAgeFromDB( duoTeam.getAge() ) );
        ret.setAgeId( duoTeam.getAgeId() );
        ret.setFirstname( new String( duoTeam.getFirstname() ) );
        ret.setName( new String( duoTeam.getName() ) );
        ret.setFirstname2( new String( duoTeam.getFirstname2() ) );
        ret.setName2( new String( duoTeam.getName2() ) );
        ret.setPlace( duoTeam.getPlace() );
        ret.setReady( duoTeam.getReady() );
        ret.setSex( new String( duoTeam.getSex() ) );
        ret.setTeam( TeamMapper.mapTeamFromDB( duoTeam.getTeam() ) );
        ret.setTeamId( duoTeam.getTeamId() );
        ret.setDuoclassId( duoTeam.getDuoclassId() );
        ret.setYearOfBirth( duoTeam.getYearOfBirth() );
        ret.setMonthOfBirth( duoTeam.getMonthOfBirth() );
        ret.setDayOfBirth( duoTeam.getDayOfBirth() );
        ret.setYearOfBirth2( duoTeam.getYearOfBirth2() );
        ret.setMonthOfBirth2( duoTeam.getMonthOfBirth2() );
        ret.setDayOfBirth2( duoTeam.getDayOfBirth2() );
        ret.setMarkForExport( duoTeam.isMarkForExport() );
        try
        {
            ret.setDuoclass( DuoclassMapper.mapDuoclassFromDB( duoTeam.getDuoclass() ) );
        }
        catch ( Exception e )
        {
            // ignore, not corresponding weightclass
            ret.setDuoclass( null );
        }
        ret.setCreateDate( new Timestamp( duoTeam.getCreateDate().getTime() ) );
        ret.setCreateId( Long.valueOf( duoTeam.getCreateId() ) );
        ret.setId( Long.valueOf( duoTeam.getId() ) );
        ret.setModificationDate( new Timestamp( duoTeam.getModificationDate().getTime() ) );
        ret.setModificationId( Long.valueOf( duoTeam.getModificationId() ) );
        return ret;
    }

    public static void mapDuoTeamToDB( DuoTeam duoTeam, DuoTeam duoTeamDB, boolean deepMapping )
    {

        if ( deepMapping )
        {
            Age ageDB = new Age();
            AgeMapper.mapAgeToDB( duoTeam.getAge(), ageDB );
            duoTeamDB.setAge( ageDB );
            Team teamDB = new Team();
            TeamMapper.mapTeamToDB( duoTeam.getTeam(), teamDB, false );
            duoTeamDB.setTeam( teamDB );
            if ( duoTeam.getDuoclass() != null )
            {
                Duoclass fc = new Duoclass();
                DuoclassMapper.mapDuoclassToDB( duoTeam.getDuoclass(), fc );
                duoTeamDB.setDuoclass( fc );
            }
        }
        duoTeamDB.setAgeId( duoTeam.getAgeId() );
        duoTeamDB.setTeamId( duoTeam.getTeamId() );
        duoTeamDB.setDuoclassId( duoTeam.getDuoclassId() );
        duoTeamDB.setFirstname( new String( duoTeam.getFirstname() ) );
        duoTeamDB.setName( new String( duoTeam.getName() ) );
        duoTeamDB.setFirstname2( new String( duoTeam.getFirstname2() ) );
        duoTeamDB.setName2( new String( duoTeam.getName2() ) );
        duoTeamDB.setPlace( duoTeam.getPlace() );
        duoTeamDB.setReady( duoTeam.getReady() );
        duoTeamDB.setSex( new String( duoTeam.getSex() ) );

        duoTeamDB.setYearOfBirth( duoTeam.getYearOfBirth() );
        duoTeamDB.setYearOfBirth2( duoTeam.getYearOfBirth2() );
        duoTeamDB.setMonthOfBirth( duoTeam.getMonthOfBirth() );
        duoTeamDB.setDayOfBirth( duoTeam.getDayOfBirth() );
        duoTeamDB.setMonthOfBirth2( duoTeam.getMonthOfBirth2() );
        duoTeamDB.setDayOfBirth2( duoTeam.getDayOfBirth2() );
        duoTeamDB.setMarkForExport( duoTeam.isMarkForExport() );
        duoTeamDB.setModificationDate( new Timestamp( duoTeam.getModificationDate().getTime() ) );
        duoTeamDB.setModificationId( Long.valueOf( duoTeam.getModificationId() ) );
    }

}
