/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaclassOverviewAction.java
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

package de.jjw.webapp.action.off.newa;

import java.io.IOException;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletResponse;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.admin.Fightsystem;
import de.jjw.model.newa.NewaKoClass;
import de.jjw.model.newa.Newaclass;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.modelWeb.NewaclassWeb;
import de.jjw.service.newa.NewaclassManager;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.pdf.newa.NewaDPoolPDF;
import de.jjw.webapp.pdf.newa.NewaKo16ComplexListPDF;
import de.jjw.webapp.pdf.newa.NewaKo32ComplexListPDF;
import de.jjw.webapp.pdf.newa.NewaKo64ComplexListPDF;
import de.jjw.webapp.pdf.newa.NewaPoolPDF;
import de.jjw.webapp.util.ConfigMain;

public class NewaclassOverviewAction
    extends BasePage
    implements IGlobalWebConstants
{

    private static String RELOAD = "newaclassOverview";

    private NewaclassManager newaclassManager;

    private String ALL_NEWACASSES = "all_newaclasses";

    List<NewaclassWeb> newaclasses = null;

    public void setNewaclassManager( NewaclassManager newaclassManager )
    {
        this.newaclassManager = newaclassManager;
    }

    public List<NewaclassWeb> getFighters()
    {
        if ( newaclassManager == null )
        {
            log.warn( "keine fighterManager Injection" );
        }
        if ( getRequest().getAttribute( ALL_NEWACASSES ) == null )
        {
            try
            {
                newaclasses =
                    newaclassManager.getInUseNewaclassesForTatami(
                                                                           null,
                                                                           getLocale(),
                                                                           WebExchangeContextHelper.createServiceExchangeContext( getSession() ) );
            }
            catch ( JJWManagerException e )
            {
                log.error( e.getStackTrace() );
                addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
            }
            getRequest().setAttribute( ALL_NEWACASSES, ALL_NEWACASSES );
        }
        return newaclasses;
    }

    public String showNewaclassAsWeb()
    {

        String contextPath = getFacesContext().getExternalContext().getRequestContextPath();

        StringBuffer sb = new StringBuffer( 120 );
        sb.append( contextPath );
        if ( getFacesContext().getExternalContext().getUserPrincipal() != null )
        {
            sb.append( SHOW_NEWACLASS_OFFICIAL );
        }
        else
        {
            sb.append( SHOW_NEWACLASS_EXTERN );
        }
        sb.append( HTML_QUESTION_MARK ).append( HTML_PARAM_NEWACLASS_ID ).append( HTML_EQUAL ).append(
 String.valueOf( ( (Newaclass) dataTable.getRowData() ).getId() ) );
        try
        {

            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     sb.toString() ) );
            return null;
        }
        catch ( IOException e1 )
        {
            log.error( "NewaclassOverviewAction", e1 );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }

        return RELOAD;
    }

    public String showNewaclassAsPdf()
    {

        HttpServletResponse response = (HttpServletResponse) getFacesContext().getExternalContext().getResponse();

        response.setHeader( "Expires", "0" );
        response.setHeader( "Cache-Control", "must-revalidate, post-check=0, pre-check=0" );
        response.setHeader( "Pragma", "public" );
        response.setContentType( "application/pdf" );
        // response.addHeader("Content-disposition", "attachment; filename=\"" + "fileName" + ".pdf\"");

        Newaclass fightingclass = (Newaclass) dataTable.getRowData();
        try
        {
            if ( fightingclass.getFightsystem() == Fightsystem.SIMPLE_POOL )

            {
                new NewaPoolPDF( "de.jjw.webapp.messages.admin.fightingclass",
                                 newaclassManager.getNewaclassSimplePool( fightingclass.getId() ),
                                     response, ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(),
                                     getLocale() );
            }

            if ( fightingclass.getFightsystem() == Fightsystem.DOUBLE_POOL )

            {
                new NewaDPoolPDF( "de.jjw.webapp.messages.admin.fightingclass",
                                  newaclassManager.getNewaclassDoublePool( fightingclass.getId() ),
                                      response, ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(),
                                      getLocale() );
            }

            if ( fightingclass.getFightsystem() == Fightsystem.DOUBLE_KO )
            {

                NewaKoClass ko = newaclassManager.getNewaclassKo( fightingclass.getId() );
                if ( ko.getFighterListPoolA().size() < 9 )
                {
                    new NewaKo16ComplexListPDF( "de.jjw.webapp.messages.admin.fightingclass", ko, response,
                                                    ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(),
                                                    getLocale() );
                }
                if ( ko.getFighterListPoolA().size() < 17 && ko.getFighterListPoolA().size() > 8 )
                {
                    new NewaKo32ComplexListPDF( "de.jjw.webapp.messages.admin.fightingclass", ko, response,
                                                    ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(),
                                                    getLocale() );
                }
                if ( ko.getFighterListPoolA().size() < 33 && ko.getFighterListPoolA().size() > 16 )
                {
                    new NewaKo64ComplexListPDF( "de.jjw.webapp.messages.admin.fightingclass", ko, response,
                                                    ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(),
                                                    getLocale() );
                }
            }
            getFacesContext().responseComplete();
        }
        catch ( JJWManagerException e )
        {
            log.error( "NewaclassOverviewAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        catch ( Exception e )
        {
            log.error( "NewaclassOverviewAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;
    }

    public String startClassListener( ValueChangeEvent event )
    {
        try
        {
            Newaclass newaclass = (Newaclass) dataTable.getRowData();
            newaclassManager.startClass( newaclass,
                                             WebExchangeContextHelper.createServiceExchangeContext( getSession() ) );
            getRequest().removeAttribute( ALL_NEWACASSES );
        }
        catch ( JJWManagerException e )
        {
            log.error( e.getStackTrace() );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        catch ( OptimisticLockingException e )
        {
            addErrorElement( new ErrorElement( GLO_OPTIMISTIC_LOCKING ) );
        }
        return RELOAD;
    }

}
