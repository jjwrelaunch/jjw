/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : TeamParticipantsAction.java
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

package de.jjw.webapp.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import de.jjw.service.TeamManager;
import de.jjw.service.TeamResultDTO;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.util.dto.TeamParticipantDto;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.pdf.TeamResultsPDF;

public class TeamParticipantsAction
    extends BasePage
    implements IGlobalWebConstants
{

    private TeamManager teamManager;

    private List<TeamParticipantDto> teamList;

    private static final String ALL_TEAMS = "allTeams";

    public TeamManager getTeamManager()
    {
        return teamManager;
    }

    public void setTeamManager( TeamManager teamManager )
    {
        this.teamManager = teamManager;
    }


    public List<TeamParticipantDto> getTeamList()
    {
        if ( teamManager == null )
        {
            log.warn( "keine teamManager Injection" );
        }
        if ( getRequest().getAttribute( ALL_TEAMS ) == null )
        {
            try
            {
                teamList = teamManager.getNumberOfTeamMember();
            }
            catch ( JJWManagerException e )
            {
                addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
            }
            getRequest().setAttribute( ALL_TEAMS, ALL_TEAMS );
        }

        return teamList;
    }

    public String teamResultsPDF()
    {
        HttpServletResponse response = (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
        response.setHeader( "Expires", "0" );
        response.setHeader( "Cache-Control", "must-revalidate, post-check=0, pre-check=0" );
        response.setHeader( "Pragma", "public" );
        response.setContentType( "application/pdf" );
        try
        {
            TeamResultDTO result =
                teamManager.getTeamResults( ( (TeamParticipantDto) dataTable.getRowData() ).getTeamId(), getLocale() );
            new TeamResultsPDF( "de.jjw.webapp.messages.extern.Team", response, getLocale() ).showResults( result );
            getFacesContext().responseComplete();
        }
        catch ( Exception e )
        {
            log.error( "StatisticAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;
    }

    public void setTeamList( List<TeamParticipantDto> teamList )
    {
        this.teamList = teamList;
    }


}
