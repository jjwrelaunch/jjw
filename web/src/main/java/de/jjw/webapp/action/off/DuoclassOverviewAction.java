/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoclassOverviewAction.java
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

package de.jjw.webapp.action.off;

import java.io.IOException;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletResponse;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.admin.Fightsystem;
import de.jjw.model.duo.DuoKoClass;
import de.jjw.model.duo.Duoclass;
import de.jjw.service.duo.DuoclassManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.modelWeb.DuoclassWeb;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.pdf.duo.DuoDPoolPDF;
import de.jjw.webapp.pdf.duo.DuoKo16ComplexListPDF;
import de.jjw.webapp.pdf.duo.DuoKo32ComplexListPDF;
import de.jjw.webapp.pdf.duo.DuoPoolPDF;
import de.jjw.webapp.util.ConfigMain;

public class DuoclassOverviewAction
    extends BasePage
    implements IGlobalWebConstants
{

    private static String RELOAD = "duoclassOverview";

    private DuoclassManager duoclassManager;

    private String ALL_DUOCASSES = "all_duoclasses";

    List<DuoclassWeb> duoclasses = null;

    public void setDuoclassManager( DuoclassManager duoclassManager )
    {
        this.duoclassManager = duoclassManager;
    }

    public List<DuoclassWeb> getDuoclasses()
    {
        if ( duoclassManager == null )
        {
            log.warn( "keine duoTeamManager Injection" );
        }
        if ( getRequest().getAttribute( ALL_DUOCASSES ) == null )
        {
            try
            {
                duoclasses =
                    duoclassManager.getInUseDuoclassesForTatami(
                                                                 null,
                                                                 getLocale(),
                                                                 WebExchangeContextHelper.createServiceExchangeContext( getSession() ) );
            }
            catch ( JJWManagerException e )
            {
                log.error( e.getStackTrace() );
                addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
            }
            getRequest().setAttribute( ALL_DUOCASSES, ALL_DUOCASSES );
        }
        return duoclasses;
    }

    public String showDuoclassAsWeb()
    {

        String contextPath = getFacesContext().getExternalContext().getRequestContextPath();

        StringBuffer sb = new StringBuffer( 120 );
        sb.append( contextPath );
        if ( getFacesContext().getExternalContext().getUserPrincipal() != null )
        {
            sb.append( SHOW_DUOCLASS_OFFICIAL );
        }
        else
        {
            sb.append( SHOW_DUOCLASS_EXTERN );
        }
        sb.append( HTML_QUESTION_MARK ).append( HTML_PARAM_DUOCLASS_ID ).append( HTML_EQUAL ).append(
                                                                                                      String.valueOf( ( (Duoclass) dataTable.getRowData() ).getId() ) );
        try
        {

            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     sb.toString() ) );
            return null;
        }
        catch ( IOException e1 )
        {
            log.error( "DuoclassOverviewAction", e1 );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }

        return RELOAD;
    }

    public String showDuoclassAsPdf()
    {

        HttpServletResponse response = (HttpServletResponse) getFacesContext().getExternalContext().getResponse();

        response.setHeader( "Expires", "0" );
        response.setHeader( "Cache-Control", "must-revalidate, post-check=0, pre-check=0" );
        response.setHeader( "Pragma", "public" );
        response.setContentType( "application/pdf" );
        // response.addHeader("Content-disposition", "attachment; filename=\"" + "fileName" + ".pdf\"");

        Duoclass duoclass = (Duoclass) dataTable.getRowData();
        try
        {
            if ( duoclass.getFightsystem() == Fightsystem.SIMPLE_POOL )

            {
                new DuoPoolPDF( "de.jjw.webapp.messages.admin.duoclass",
                                duoclassManager.getDuoclassSimplePool( duoclass.getId() ), response,
                                ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(), getLocale() );
            }

            if ( duoclass.getFightsystem() == Fightsystem.DOUBLE_POOL )

            {
                new DuoDPoolPDF( "de.jjw.webapp.messages.admin.duoclass",
                                 duoclassManager.getDuoclassDoublePool( duoclass.getId() ), response,
                                 ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(), getLocale() );
            }

            if ( duoclass.getFightsystem() == Fightsystem.DOUBLE_KO )
            {

                DuoKoClass ko = duoclassManager.getDuoclassKo( duoclass.getId() );
                if ( ko.getTeamListPoolA().size() < 9 )
                {
                    new DuoKo16ComplexListPDF( "de.jjw.webapp.messages.admin.duoclass", ko, response,
                                               ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(),
                                               getLocale() );
                }
                if ( ko.getTeamListPoolA().size() < 17 && ko.getTeamListPoolA().size() > 8 )
                {
                    new DuoKo32ComplexListPDF( "de.jjw.webapp.messages.admin.duoclass", ko, response,
                                               ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(),
                                               getLocale() );
                }
            }
            getFacesContext().responseComplete();
        }
        catch ( JJWManagerException e )
        {
            log.error( "DuoclassOverviewAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        catch ( Exception e )
        {
            log.error( "DuoclassOverviewAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;

    }

    public String toggleDeleteStopListener( ValueChangeEvent event )
    {
        try
        {
            Duoclass duoclass = (Duoclass) dataTable.getRowData();
            if ( duoclass.isComplete() )
            {
                addErrorElement( new ErrorElement( FIGHTINGCLASS_NO_TOGGLE_DELETE_STOP ) );
                duoclass.setDeleteStop( false );
            }
            else
            {
                duoclassManager.toggleDeleteStop( duoclass,
                                                  WebExchangeContextHelper.createServiceExchangeContext( getSession() ) );
            }
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

    public String startClassListener( ValueChangeEvent event )
    {
        try
        {
            Duoclass duoclass = (Duoclass) dataTable.getRowData();
            duoclassManager.startClass( duoclass, WebExchangeContextHelper.createServiceExchangeContext( getSession() ) );
            getRequest().removeAttribute( ALL_DUOCASSES );
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
