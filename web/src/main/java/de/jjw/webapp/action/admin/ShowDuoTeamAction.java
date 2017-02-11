/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : ShowDuoTeamAction.java
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

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.admin.ConfigJJW;
import de.jjw.model.duo.DuoTeam;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.admin.ConfigManager;
import de.jjw.service.duo.DuoTeamManager;
import de.jjw.service.exception.DuoTeamInUseException;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.exception.JJWPoolBlockedException;
import de.jjw.service.modelWeb.DuoTeamWeb;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;

public class ShowDuoTeamAction
    extends BasePage
    implements Serializable, IGlobalWebConstants
{

    private static final String RELOAD_EXPORT = "markDuoForExport";
    
    private static final String RELOAD = "showDuoTeams";

    private static final String DUOTEAM_BY = "duoTeamBy";

    private static String GOTO_DUOTEAM = "editDuoTeam";

    private static String ALL_DUOTEAMS = "allDuoTeams";

    private static String ALL_NOT_REGISTRATED_DUOTEAMS = "allNotRegistratedDuoTeams";

    DuoTeamManager duoTeamManager = null;

    ConfigManager configManager = null;

    private static final int DUOTEAM_PER_AGE = 1;

    private static final int DUOTEAM_PER_COUNTRY = 2;

    private static final int DUOTEAM_PER_REGION = 3;

    private static final int DUOTEAM_PER_TEAM = 4;

    private static final int DUOTEAM_PER_DUOCLASS = 5;

    private int duoTeamPer = 0;

    private long duoTeamPerId = 0; // id of age, country, team etc

    private boolean notReg = false; // not registrated duoTeam

    List<DuoTeamWeb> duoTeams = null;

    List<DuoTeamWeb> notRegistratedDuoTeams = null;

    public String gotoEditDuoTeam()
    {
        String ret = GOTO_DUOTEAM;
        getRequest().setAttribute( WEB_ADMIN_DUOTEAM, dataTable.getRowData() );
        return ret;
    }

    public String deleteDuoTeam()
    {
        String ret = null;
        try
        {
            duoTeamManager.removeDuoTeam( (DuoTeam) dataTable.getRowData() );
            ret = RELOAD;
        }
        catch ( DuoTeamInUseException e )
        {
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( DUO_DUOTEAM_NOT_DELETEABLE ) );
            setErrorMessageVector( errors );
        }
        catch ( JJWManagerException e )
        {
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
        }
        return ret;
    }

    public List<DuoTeamWeb> getDuoTeams()
    {
        if ( duoTeamManager == null )
        {
            log.warn( "keine duoTeamManager Injection" );
        }
        if ( getRequest().getAttribute( ALL_DUOTEAMS ) == null )
        {
            try
            {
                getCorrectDuoTeamList();
            }
            catch ( JJWManagerException e )
            {
                addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
            }
            getRequest().setAttribute( ALL_DUOTEAMS, ALL_DUOTEAMS );
        }
        return duoTeams;
    }

    public List<DuoTeamWeb> getNotRegistratedDuoTeams()
    {
        if ( duoTeamManager == null )
        {
            log.warn( "keine duoTeamManager Injection" );
        }
        notReg = true;
        if ( getRequest().getAttribute( ALL_NOT_REGISTRATED_DUOTEAMS ) == null )
        {
            try
            {
                getCorrectDuoTeamList();
                notRegistratedDuoTeams = duoTeams;
            }
            catch ( JJWManagerException e )
            {
                addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
            }
            getRequest().setAttribute( ALL_NOT_REGISTRATED_DUOTEAMS, ALL_NOT_REGISTRATED_DUOTEAMS );
        }
        return notRegistratedDuoTeams;
    }

    /**
     * calculates the correct duoteam list
     * 
     * @throws NumberFormatException
     * @throws JJWManagerException
     */
    private void getCorrectDuoTeamList()
        throws NumberFormatException, JJWManagerException
    {
        FacesContext context = FacesContext.getCurrentInstance();
        Map requestMap = context.getExternalContext().getRequestParameterMap();
        duoTeamPerId =
            Integer.valueOf( requestMap.get( "duoTeamPerId" ) == null ? "0" : (String) requestMap.get( "duoTeamPerId" ) );
        duoTeamPer =
            Integer.valueOf( requestMap.get( "duoTeamPer" ) == null ? "0" : (String) requestMap.get( "duoTeamPer" ) );
        // notReg = Boolean.valueOf("true".equals((String) requestMap.get("notReg")) ? true : false);

        if ( TypeUtil.isEmptyOrDefault( duoTeamPerId ) || TypeUtil.isEmptyOrDefault( duoTeamPer ) )
        {
            if ( notReg )
            {
                duoTeams = duoTeamManager.getNotRegistratedDuoTeams( getLocale() );
            }
            else
            {
                duoTeams = duoTeamManager.getAllDuoTeams( getLocale() );
            }
        }
        else
        {
            switch ( duoTeamPer )
            {
                case DUOTEAM_PER_AGE:
                    duoTeams = duoTeamManager.getDuoTeamByAge( duoTeamPerId, getLocale(), notReg );
                    break;
                case DUOTEAM_PER_COUNTRY:
                    duoTeams = duoTeamManager.getDuoTeamByCountry( duoTeamPerId, getLocale(), notReg );
                    break;
                case DUOTEAM_PER_REGION:
                    duoTeams = duoTeamManager.getDuoTeamByRegion( duoTeamPerId, getLocale(), notReg );
                    break;
                case DUOTEAM_PER_TEAM:
                    duoTeams = duoTeamManager.getDuoTeamByTeam( duoTeamPerId, getLocale(), notReg );
                    break;
                case DUOTEAM_PER_DUOCLASS:
                    duoTeams = duoTeamManager.getDuoTeamByDuoclass( duoTeamPerId, getLocale(), notReg );
                    break;
            }
        }
    }

    /**
     * @param duoTeamManager The duoTeamManager to set.
     */
    public void setDuoTeamManager( DuoTeamManager duoTeamManager )
    {
        this.duoTeamManager = duoTeamManager;
    }

    public String registerFast()
    {
        try
        {
            duoTeamManager.registerFast(
                                         ( (DuoTeam) dataTable.getRowData() ).getId(),
                                         new ServiceExchangeContext(
                                                                     WebExchangeContextHelper.getWebExchangeContext(
                                                                                                                     getSession() ).getUserId() ) );
            notReg = true;
            getCorrectDuoTeamList();
            notRegistratedDuoTeams = duoTeams;
            return DUOTEAM_BY;
        }
        catch ( JJWManagerException e )
        {
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
        }
        catch ( JJWPoolBlockedException e )
        {
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( ADMIN_DUOTEAM_BLOCKED_POOL ) );
            setErrorMessageVector( errors );
        }
        return null;
    }

    public String duoTeamPerTeam()
    {
        try
        {
            FacesContext context = FacesContext.getCurrentInstance();
            Map requestMap = context.getExternalContext().getRequestParameterMap();
            notReg = Boolean.valueOf( "true".equals( requestMap.get( "notReg" ) ) ? true : false );
            notRegistratedDuoTeams =
                duoTeams =
                    duoTeamManager.getDuoTeamByTeam( ( (DuoTeam) dataTable.getRowData() ).getTeam().getId(),
                                                     getLocale(), notReg );
            duoTeamPer = DUOTEAM_PER_TEAM;
            duoTeamPerId = ( (DuoTeam) dataTable.getRowData() ).getTeam().getId();
            return DUOTEAM_BY;
        }
        catch ( JJWManagerException e )
        {
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
        }
        return null;
    }

    public String duoTeamPerDuoclass()
    {
        try
        {
            FacesContext context = FacesContext.getCurrentInstance();
            Map requestMap = context.getExternalContext().getRequestParameterMap();
            notReg = Boolean.valueOf( "true".equals( requestMap.get( "notReg" ) ) ? true : false );
            notRegistratedDuoTeams =
                duoTeams =
                    duoTeamManager.getDuoTeamByDuoclass( ( (DuoTeam) dataTable.getRowData() ).getDuoclass().getId(),
                                                         getLocale(), notReg );
            duoTeamPer = DUOTEAM_PER_DUOCLASS;
            duoTeamPerId = ( (DuoTeam) dataTable.getRowData() ).getDuoclass().getId();
            return DUOTEAM_BY;
        }
        catch ( JJWManagerException e )
        {
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
        }
        return null;
    }

    public String duoTeamPerAge()
    {
        try
        {
            FacesContext context = FacesContext.getCurrentInstance();
            Map requestMap = context.getExternalContext().getRequestParameterMap();
            notReg = Boolean.valueOf( "true".equals( requestMap.get( "notReg" ) ) ? true : false );
            notRegistratedDuoTeams =
                duoTeams =
                    duoTeamManager.getDuoTeamByAge( ( (DuoTeam) dataTable.getRowData() ).getAge().getId(), getLocale(),
                                                    notReg );
            duoTeamPer = DUOTEAM_PER_AGE;
            duoTeamPerId = ( (DuoTeam) dataTable.getRowData() ).getAge().getId();
            return DUOTEAM_BY;
        }
        catch ( JJWManagerException e )
        {
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
        }
        return null;
    }

    public String duoTeamPerRegion()
    {
        try
        {
            FacesContext context = FacesContext.getCurrentInstance();
            Map requestMap = context.getExternalContext().getRequestParameterMap();
            notReg = Boolean.valueOf( "true".equals( requestMap.get( "notReg" ) ) ? true : false );
            notRegistratedDuoTeams =
                duoTeams =
                    duoTeamManager.getDuoTeamByRegion(
                                                       ( (DuoTeam) dataTable.getRowData() ).getTeam().getRegion().getId(),
                                                       getLocale(), notReg );
            duoTeamPer = DUOTEAM_PER_REGION;
            duoTeamPerId = ( (DuoTeam) dataTable.getRowData() ).getTeam().getRegion().getId();
            return DUOTEAM_BY;
        }
        catch ( JJWManagerException e )
        {
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
        }
        return null;
    }

    public String duoTeamPerCountry()
    {
        try
        {
            FacesContext context = FacesContext.getCurrentInstance();
            Map requestMap = context.getExternalContext().getRequestParameterMap();
            notReg = Boolean.valueOf( "true".equals( requestMap.get( "notReg" ) ) ? true : false );
            notRegistratedDuoTeams =
                duoTeams =
                    duoTeamManager.getDuoTeamByCountry(
                                                        ( (DuoTeam) dataTable.getRowData() ).getTeam().getCountry().getId(),
                                                        getLocale(), notReg );
            duoTeamPer = DUOTEAM_PER_COUNTRY;
            duoTeamPerId = ( (DuoTeam) dataTable.getRowData() ).getTeam().getCountry().getId();
            return DUOTEAM_BY;
        }
        catch ( JJWManagerException e )
        {
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
        }
        return null;
    }

    public String getRenderDelete()
    {
        try
        {
            ConfigJJW config = configManager.getConfig();
            return config.isFighterDeleteable() ? IValueConstants.STRING_TRUE : IValueConstants.STRING_FALSE;
        }
        catch ( Exception e )
        {
            log.error( "ShowFighterAction.renderDelete  ", e );
            return IValueConstants.STRING_FALSE;
        }
    }
    
    public String toggleMarkForExportListener( ValueChangeEvent event )
    {
        try
        {
            DuoTeam duoteam = (DuoTeam) dataTable.getRowData();          
            
            duoTeamManager.toggleMarkForExport( duoteam,
                                                       WebExchangeContextHelper.createServiceExchangeContext( getSession() ) );            
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
        return RELOAD_EXPORT;
    }

    public int getDuoTeamPer()
    {
        return duoTeamPer;
    }

    public void setDuoTeamPer( int duoTeamPer )
    {
        this.duoTeamPer = duoTeamPer;
    }

    public long getDuoTeamPerId()
    {
        return duoTeamPerId;
    }

    public void setDuoTeamPerId( long duoTeamPerId )
    {
        this.duoTeamPerId = duoTeamPerId;
    }

    public boolean isNotReg()
    {
        return notReg;
    }

    public void setNotReg( boolean notReg )
    {
        this.notReg = notReg;
    }

    public void setConfigManager( ConfigManager configManager )
    {
        this.configManager = configManager;
    }
}
