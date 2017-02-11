/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : TeamDao.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:48
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

package de.jjw.dao;

import java.util.List;

import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.Team;
import de.jjw.model.duo.DuoTeam;
import de.jjw.model.fighting.Fighter;
import de.jjw.util.dto.TeamParticipantDto;

public interface TeamDao
    extends Dao
{

    public Team getTeam( Long id );

    public List<Team> getAllTeams();

    public List<Team> getTeamsByTeamName( String teamName );

    public void saveTeam( Team team );

    public List<Team> getTeamsByCountry( Long countryId );

    public List<Team> getTeamsByCountryAndRegion( Long countryId, Long regionId );

    public List<Team> getTeamsByRegion( long regionId );

    public List<TeamParticipantDto> getNumberOfTeamMember()
        throws JJWDataLayerException;

    public void removeTeam( Long teamId );

    public void removeTeam( Team team );

    public boolean isTeamInUse( Team team )
        throws JJWDataLayerException;

    public boolean isTeamInUse( Long teamId )
        throws JJWDataLayerException;

    public List<Fighter> getTeamResultsFighting( long teamId )
        throws JJWDataLayerException;

    public List<DuoTeam> getTeamResultsDuo( long teamId )
        throws JJWDataLayerException;
}
