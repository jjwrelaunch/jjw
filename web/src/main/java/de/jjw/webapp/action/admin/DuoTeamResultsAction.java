/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoTeamResultsAction.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:47
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

package de.jjw.webapp.action.admin;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import de.jjw.service.duo.DuoTeamManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.modelWeb.DuoTeamWeb;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.pdf.duo.DuoTeamResultsPDF;

public class DuoTeamResultsAction
    extends BasePage
    implements IGlobalWebConstants
{

    DuoTeamManager duoTeamManager = null;

    List<DuoTeamWeb> duoteams = null;

    public List<DuoTeamWeb> getDuoTeams()
    {
        if ( duoTeamManager == null )
        {
            log.warn( "keine duoTeamManager Injection" );
        }
        if ( null == duoteams )
        {
            try
            {
                duoteams = duoTeamManager.getDuoTeamResultList( getLocale() );
            }
            catch ( JJWManagerException e )
            {
                addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
            }
        }
        return duoteams;
    }

    public String showDuoResultAsPdf()
    {

        HttpServletResponse response = (HttpServletResponse) getFacesContext().getExternalContext().getResponse();

        response.setHeader( "Expires", "0" );
        response.setHeader( "Cache-Control", "must-revalidate, post-check=0, pre-check=0" );
        response.setHeader( "Pragma", "public" );
        response.setContentType( "application/pdf" );
        // response.addHeader("Content-disposition", "attachment; filename=\"" + "fileName" + ".pdf\"");

        try
        {
            new DuoTeamResultsPDF( "de.jjw.webapp.messages.duoTeam", response, getDuoTeams(), getLocale() ).showResults();

            getFacesContext().responseComplete();
        }
        catch ( Exception e )
        {
            log.error( "FightingclassOverviewAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;

    }

    public DuoTeamManager getDuoTeamManager()
    {
        return duoTeamManager;
    }

    public void setDuoTeamManager( DuoTeamManager duoTeamManager )
    {
        this.duoTeamManager = duoTeamManager;
    }

}
