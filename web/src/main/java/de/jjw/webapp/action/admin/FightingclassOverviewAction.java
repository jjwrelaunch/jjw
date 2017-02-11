/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightingclassOverviewAction.java
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
import de.jjw.model.fighting.FightingCertification;
import de.jjw.model.fighting.FightingKoClass;
import de.jjw.model.fighting.Fightingclass;
import de.jjw.service.DatabaseDumpManager;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.fighting.FightingclassManager;
import de.jjw.service.fighting.WeightclassManager;
import de.jjw.service.modelWeb.FightingclassWeb;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.filter.StatisticThread;
import de.jjw.webapp.pdf.fighting.FightingCertificationPDF;
import de.jjw.webapp.pdf.fighting.FightingDPoolPDF;
import de.jjw.webapp.pdf.fighting.FightingKo16ComplexListPDF;
import de.jjw.webapp.pdf.fighting.FightingKo32ComplexListPDF;
import de.jjw.webapp.pdf.fighting.FightingKo64ComplexListPDF;
import de.jjw.webapp.pdf.fighting.FightingPoolPDF;
import de.jjw.webapp.util.ConfigMain;

public class FightingclassOverviewAction
    extends BasePage
    implements IGlobalWebConstants
{
    private static String RELOAD = "fightingclassOverview";

    private FightingclassManager fightingclassManager;

    private WeightclassManager weightclassManager;

    private DatabaseDumpManager databaseDumpManager;

    private String ALL_FIGHTINGCASSES = "all_fightingclasses";

    List<FightingclassWeb> fightingclasses = null;

    public void setFightingclassManager( FightingclassManager fightingclassManager )
    {
        this.fightingclassManager = fightingclassManager;
    }

    public List<FightingclassWeb> getDeleteStopFightingclasses(){
        List<FightingclassWeb> retList = new ArrayList<FightingclassWeb>(40);
        
        for(FightingclassWeb item : this.getFighters()){
            if (item.isDeleteStop()) retList.add( item );
        }
        return retList;        
    }
    
    
    public List<FightingclassWeb> getFighters()
    {
        if ( fightingclassManager == null )
        {
            log.warn( "keine fighterManager Injection" );
        }
        if ( getRequest().getAttribute( ALL_FIGHTINGCASSES ) == null )
        {
            try
            {
                fightingclasses = fightingclassManager.getInUseFightingclasses( null, getLocale() );
            }
            catch ( JJWManagerException e )
            {
                log.error( e.getStackTrace() );
                addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
            }
            getRequest().setAttribute( ALL_FIGHTINGCASSES, ALL_FIGHTINGCASSES );
        }
        return fightingclasses;
    }

    public String showFightingclassAsWeb()
    {

        String contextPath = getFacesContext().getExternalContext().getRequestContextPath();

        StringBuffer sb = new StringBuffer( 120 );
        sb.append( contextPath );
        if ( getFacesContext().getExternalContext().getUserPrincipal() != null )
        {
            sb.append( SHOW_FIGHTINGCLASS );
        }
        else
        {
            sb.append( SHOW_FIGHTINGCLASS_EXTERN );
        }
        sb.append( HTML_QUESTION_MARK ).append( HTML_PARAM_FIGHTINGCLASS_ID ).append( HTML_EQUAL ).append( String.valueOf( ( (Fightingclass) dataTable.getRowData() ).getId() ) );
        try
        {

            getFacesContext().getExternalContext().redirect( getFacesContext().getExternalContext().encodeActionURL( sb.toString() ) );
            return null;
        }
        catch ( IOException e1 )
        {
            log.error( "FightingclassOverviewAction", e1 );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }

        return RELOAD;
    }

    public String showFightingclassAsPdf()
    {

        HttpServletResponse response = (HttpServletResponse) getFacesContext().getExternalContext().getResponse();

        response.setHeader( "Expires", "0" );
        response.setHeader( "Cache-Control", "must-revalidate, post-check=0, pre-check=0" );
        response.setHeader( "Pragma", "public" );
        response.setContentType( "application/pdf" );
        // response.addHeader("Content-disposition", "attachment; filename=\"" + "fileName" + ".pdf\"");

        Fightingclass fightingclass = (Fightingclass) dataTable.getRowData();
        try
        {
            if ( fightingclass.getFightsystem() == Fightsystem.SIMPLE_POOL )

            {
                new FightingPoolPDF( "de.jjw.webapp.messages.admin.fightingclass",
                                     fightingclassManager.getFightingclassSimplePool( fightingclass.getId() ),
                                     response, ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(),
                                     getLocale() );
            }

            if ( fightingclass.getFightsystem() == Fightsystem.DOUBLE_POOL )

            {
                new FightingDPoolPDF( "de.jjw.webapp.messages.admin.fightingclass",
                                      fightingclassManager.getFightingclassDoublePool( fightingclass.getId() ),
                                      response, ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(),
                                      getLocale() );
            }

            if ( fightingclass.getFightsystem() == Fightsystem.DOUBLE_KO )
            {

                FightingKoClass ko = fightingclassManager.getFightingclassKo( fightingclass.getId() );
                if ( ko.getFighterListPoolA().size() <= 8 )
                {
                    new FightingKo16ComplexListPDF( "de.jjw.webapp.messages.admin.fightingclass", ko, response,
                                                    ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(),
                                                    getLocale() );
                }
                if ( ko.getFighterListPoolA().size() <= 16 && ko.getFighterListPoolA().size() > 8 )
                {
                    new FightingKo32ComplexListPDF( "de.jjw.webapp.messages.admin.fightingclass", ko, response,
                                                    ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(),
                                                    getLocale() );
                }
                if ( ko.getFighterListPoolA().size() <= 32 && ko.getFighterListPoolA().size() > 16 )
                {
                    new FightingKo64ComplexListPDF( "de.jjw.webapp.messages.admin.fightingclass", ko, response,
                                                    ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(),
                                                    getLocale() );
                }
            }
            getFacesContext().responseComplete();
        }
        catch ( JJWManagerException e )
        {
            log.error( "FightingclassOverviewAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        catch ( Exception e )
        {
            log.error( "FightingclassOverviewAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;

    }

    public String toggleDeleteStopListener( ValueChangeEvent event )
    {
        try
        {
            Fightingclass fightingclass = (Fightingclass) dataTable.getRowData();
            if ( fightingclass.isComplete() )
            {
                addErrorElement( new ErrorElement( FIGHTINGCLASS_NO_TOGGLE_DELETE_STOP ) );
                fightingclass.setDeleteStop( false );
                ( (HtmlSelectBooleanCheckbox) event.getSource() ).setValue( event.getOldValue() );
            }
            else
            {
                fightingclassManager.toggleDeleteStop( fightingclass,
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

    public void toggleCompleteListener( ValueChangeEvent event )
    {
        try
        {
            Fightingclass fightingclass = (Fightingclass) dataTable.getRowData();
            if ( !fightingclass.isDeleteStop() )
            {
                addErrorElement( new ErrorElement( FIGHTINGCLASS_NO_TOGGLE_COMPLETE ) );
                fightingclass.setComplete( false );
                // set old value
                ( (HtmlSelectBooleanCheckbox) event.getSource() ).setValue( event.getOldValue() );
            }
            else
            {
                fightingclassManager.toggleComplete( fightingclass,
                                                     WebExchangeContextHelper.createServiceExchangeContext( getSession() ) );
                if ( databaseDumpManager.isEventOver() )
                {
                    StatisticThread sThread =
                        new StatisticThread(
                                             WebApplicationContextUtils.getWebApplicationContext( getServletContext() ),
                                             true, null );

                    Thread runThread = new Thread( sThread, "StatisticThreadFighting" );
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

        Fightingclass fightingclass = (Fightingclass) dataTable.getRowData();
        try
        {
            if ( !fightingclass.isDeleteStop() || !fightingclass.isComplete() )
                throw new Exception( "try to print classes which not ended" );
            FightingCertification dto = fightingclassManager.getFightingCertification( fightingclass.getId() );
            dto.setCertificationStyle( ConfigMain.getInstance().getEventConfiguration().getCertificationType() );
            new FightingCertificationPDF( "de.jjw.webapp.messages.admin.fightingclass", response, dto,
                                          getRequest().getLocale() );
            weightclassManager.setFightingclassAsPrinted( fightingclass,
                                                          new ServiceExchangeContext(
                                                                                      WebExchangeContextHelper.getWebExchangeContext( getSession() ).getUserId() ) );

            getFacesContext().responseComplete();
        }
        catch ( JJWManagerException e )
        {
            log.error( "FightingclassOverviewAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        catch ( Exception e )
        {
            log.error( "FightingclassOverviewAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;
    }

    public void setWeightclassManager( WeightclassManager weightclassManager )
    {
        this.weightclassManager = weightclassManager;
    }

    /**
     * @param databaseDumpManager the databaseDumpManager to set
     */
    public void setDatabaseDumpManager( DatabaseDumpManager databaseDumpManager )
    {
        this.databaseDumpManager = databaseDumpManager;
    }

}
