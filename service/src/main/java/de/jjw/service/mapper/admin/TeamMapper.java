/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : TeamMapper.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:46
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

package de.jjw.service.mapper.admin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import de.jjw.model.Team;
import de.jjw.model.admin.Age;

public class TeamMapper
{

    public static List<Team> mapTeamListFromDB( List<Team> teams )
    {
        if ( teams == null )
        {
            return null;
        }

        List<Team> ret = new ArrayList<Team>( teams.size() + 1 );

        for ( Team team : teams )
        {
            ret.add( mapTeamFromDB( team ) );
        }

        return ret;
    }

    /**
     * maps a Team from DB and get a new Team object
     *
     * @param team
     * @return
     */
    public static Team mapTeamFromDB( Team team )
    {
        Team ret = new Team();

        ret.setLogo( ( team.getLogo() ) );

        ret.setTeamName( new String( team.getTeamName() ) );
        ret.setTeamtype( team.getTeamtype() );

        ret.setCountry( CountryMapper.mapCountryFromDB( team.getCountry() ) );
        ret.setRegion( RegionMapper.mapRegionFromDB( team.getRegion() ) );

        ret.setCreateDate( new Timestamp( team.getCreateDate().getTime() ) );
        ret.setCreateId( new Long( team.getCreateId() ) );
        ret.setId( new Long( team.getId() ) );
        ret.setModificationDate( new Timestamp( team.getModificationDate().getTime() ) );
        ret.setModificationId( new Long( team.getModificationId() ) );
        if ( team.getLogo() != null )
        {
            ret.setLogo( team.getLogo().clone() );
        }
        return ret;
    }

    /*
    * Function maps and set a new ModificationDate
    *
    */

    public static void mapTeamToDB( Team team, Team teamDB, boolean deepMapping )
    {

        if ( teamDB.getId() == null )
            teamDB.setId( team.getId() );

        if ( team != null && teamDB == null )
        {
            teamDB = new Team();
        }

        teamDB.setLogo( ( team.getLogo() ) );

        teamDB.setTeamName( new String( team.getTeamName() ) );
        teamDB.setTeamtype( team.getTeamtype() );
        if ( team.getLogo() != null )
        {
            teamDB.setLogo( team.getLogo().clone() );
        }

        if ( deepMapping )
        {
            RegionMapper.mapRegionToDB( team.getRegion(), teamDB.getRegion() );
            CountryMapper.mapCountryToDB( team.getCountry(), teamDB.getCountry() );
        }
        teamDB.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
        teamDB.setModificationId( Long.valueOf( team.getModificationId() ) );
        teamDB.setCountryId( Long.valueOf( team.getCountry().getId() ) );
        teamDB.setRegionId( Long.valueOf( team.getRegion().getId() ) );
    }


}
