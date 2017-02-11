/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : TeamManager.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:50
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

package de.jjw.service;

import java.util.List;
import java.util.Locale;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.Team;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.exception.TeamInUseException;
import de.jjw.util.dto.TeamParticipantDto;

public interface TeamManager
{

    final int DELETE_LOGO = 1;
    final int KEEP_LOGO = 2;
    final int CHANGE_LOGO = 3;

    public Team getTeam( long id );

    public List<Team> getAllTeams();

    public List<Team> getTeamsForEvent( long eventId );

    public List<Team> getTeamsForEvents( List eventIdList );

    public List<Team> getTeamsByTeamName( String teamName );

    public void saveTeam( Team team, int logoStatus )
        throws OptimisticLockingException;

    public List<Team> getTeamsByCountry( long countryId );

    public List<Team> getTeamsByRegion( long regionId );

    public List<Team> getTeamsByCountryAndRegion( long countryId, long regionId );

    public void removeTeam( Team team )
        throws TeamInUseException, JJWManagerException;

    public void removeTeam( Long teamId )
        throws TeamInUseException, JJWManagerException;

    public List<TeamParticipantDto> getNumberOfTeamMember()
        throws JJWManagerException;

    public TeamResultDTO getTeamResults( long teamId, Locale local )
        throws JJWManagerException;

    public boolean isATeamMemberAlreadyInTheSystem( long teamId )
        throws JJWManagerException;
}
