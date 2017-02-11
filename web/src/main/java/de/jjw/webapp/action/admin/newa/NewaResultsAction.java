/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaResultsAction.java
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

package de.jjw.webapp.action.admin.newa;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.modelWeb.NewaFighterWeb;
import de.jjw.service.newa.NewaFighterManager;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.pdf.newa.NewaResultsPDF;

public class NewaResultsAction
    extends BasePage
    implements IGlobalWebConstants
{

    NewaFighterManager newaFighterManager = null;

    List<NewaFighterWeb> fighters = null;

    public List<NewaFighterWeb> getFighters()
    {
        if ( newaFighterManager == null )
        {
            log.warn( "keine newafighterManager Injection" );
        }
        if ( null == fighters )
        {
            try
            {
                fighters = newaFighterManager.getFighterResultList( getLocale() );
            }
            catch ( JJWManagerException e )
            {
                addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
            }

        }
        return fighters;
    }

    public String showFightingclassAsPdf()
    {

        HttpServletResponse response = (HttpServletResponse) getFacesContext().getExternalContext().getResponse();

        response.setHeader( "Expires", "0" );
        response.setHeader( "Cache-Control", "must-revalidate, post-check=0, pre-check=0" );
        response.setHeader( "Pragma", "public" );
        response.setContentType( "application/pdf" );
        //	response.addHeader("Content-disposition", "attachment; filename=\"" + "fileName" + ".pdf\"");

        try
        {
            new NewaResultsPDF( "de.jjw.webapp.messages.fighter", response, getFighters(), getLocale() ).showResults();

            getFacesContext().responseComplete();
        }
        catch ( Exception e )
        {
            log.error( "FightingclassOverviewAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;

    }


    public NewaFighterManager getNewaFighterManager()
    {
        return newaFighterManager;
    }

    public void setNewaFighterManager( NewaFighterManager fighterManager )
    {
        this.newaFighterManager = fighterManager;
    }


}
