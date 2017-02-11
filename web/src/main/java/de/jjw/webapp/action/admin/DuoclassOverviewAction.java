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

package de.jjw.webapp.action.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.support.WebApplicationContextUtils;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.admin.Fightsystem;
import de.jjw.model.duo.DuoCertification;
import de.jjw.model.duo.DuoKoClass;
import de.jjw.model.duo.Duoclass;
import de.jjw.service.DatabaseDumpManager;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.duo.DuoclassManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.modelWeb.DuoclassWeb;
import de.jjw.service.modelWeb.FightingclassWeb;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.filter.StatisticThread;
import de.jjw.webapp.pdf.duo.DuoCertificationPDF;
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

    private DatabaseDumpManager databaseDumpManager;

    private String ALL_DUOCASSES = "all_duoclasses";

    List<DuoclassWeb> duoclasses = null;


    public void setDuoclassManager( DuoclassManager duoclassManager )
    {
        this.duoclassManager = duoclassManager;
    }
    
    public List<DuoclassWeb> getDeleteStopDuoclasses(){
        List<DuoclassWeb> retList = new ArrayList<DuoclassWeb>(40);
        
        for(DuoclassWeb item : this.getDuoclasses()){
            if (item.isDeleteStop()) retList.add( item );
        }
        return retList;        
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
                duoclasses = duoclassManager.getInUseDuoclasses( null, getLocale() );
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
            sb.append( SHOW_DUOCLASS );
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
                getFacesContext().getExternalContext().encodeActionURL( sb.toString() ) );
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
        //	response.addHeader("Content-disposition", "attachment; filename=\"" + "fileName" + ".pdf\"");

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
                ( (HtmlSelectBooleanCheckbox) event.getSource() ).setValue( event.getOldValue() );
            }
            else
            {
                duoclassManager.toggleDeleteStop( duoclass, WebExchangeContextHelper.createServiceExchangeContext(
                    getSession() ) );
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


    public void toggleCompleteListener( ValueChangeEvent event )
    {
        try
        {
            Duoclass duoclass = (Duoclass) dataTable.getRowData();
            if ( !duoclass.isDeleteStop() )
            {
                addErrorElement( new ErrorElement( FIGHTINGCLASS_NO_TOGGLE_COMPLETE ) );
                duoclass.setComplete( false );
                // set old value
                ( (HtmlSelectBooleanCheckbox) event.getSource() ).setValue( event.getOldValue() );
            }
            else
            {
                duoclassManager.toggleComplete( duoclass,
                                                WebExchangeContextHelper.createServiceExchangeContext( getSession() ) );
                if ( databaseDumpManager.isEventOver() )
                {
                    StatisticThread sThread =
                        new StatisticThread(
                                             WebApplicationContextUtils.getWebApplicationContext( getServletContext() ),
                                             true, null );

                    Thread runThread = new Thread( sThread, "StatisticThreadDuo" );
                    runThread.setDaemon( true );
                    runThread.start();
                }
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
    }

    public String printCertifications()
    {

        HttpServletResponse response = (HttpServletResponse) getFacesContext().getExternalContext().getResponse();

        response.setHeader( "Expires", "0" );
        response.setHeader( "Cache-Control", "must-revalidate, post-check=0, pre-check=0" );
        response.setHeader( "Pragma", "public" );
        response.setContentType( "application/pdf" );

        Duoclass duoclass = (Duoclass) dataTable.getRowData();
        try
        {

            if ( !duoclass.isDeleteStop() || !duoclass.isComplete() )
                throw new Exception( "try to print classes which not ended" );
            DuoCertification dto = duoclassManager.getDuoCertification( duoclass.getId() );
            dto.setCertificationStyle( ConfigMain.getInstance().getEventConfiguration().getCertificationType() );
            new DuoCertificationPDF( "de.jjw.webapp.messages.admin.duoclass", response, dto );
            duoclassManager.setDuoclassAsPrinted( duoclass, new ServiceExchangeContext(
                WebExchangeContextHelper.getWebExchangeContext( getSession() ).getUserId() ) );

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

    /**
     * @param databaseDumpManager the databaseDumpManager to set
     */
    public void setDatabaseDumpManager( DatabaseDumpManager databaseDumpManager )
    {
        this.databaseDumpManager = databaseDumpManager;
    }

}
