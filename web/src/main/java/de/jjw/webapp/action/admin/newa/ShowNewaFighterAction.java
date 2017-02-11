/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : ShowNewaFighterAction.java
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

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.admin.ConfigJJW;
import de.jjw.model.fighting.Fighter;
import de.jjw.model.newa.NewaFighter;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.admin.ConfigManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.exception.JJWPoolBlockedException;
import de.jjw.service.exception.NewaFighterInUseException;
import de.jjw.service.modelWeb.NewaFighterWeb;
import de.jjw.service.newa.NewaFighterManager;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.constants.admin.INewaFighterConstants;

public class ShowNewaFighterAction
    extends BasePage
    implements Serializable, IGlobalWebConstants, INewaFighterConstants
{

    private static final String RELOAD = "showNewaFighter";
    
    private static final String RELOAD_EXPORT = "markNewaForExport";

    private static final String FIGHTER_BY = "fighterBy";

    private static String GOTO_FIGHTER = "editNewaFighter";

    private static String ALL_FIGHTER = "allNewaFighter";

    private static String ALL_NOT_REGISTRATED_FIGHTER = "allNotRegistratedNewaFighter";

    NewaFighterManager newaFighterManager = null;

    ConfigManager configManager = null;

    List<NewaFighterWeb> fighters = null;

    List<NewaFighterWeb> notRegistratedFighters = null;

    private int fighterPer = 0;

    private long fighterPerId = 0; // id of age, country, team etc

    private boolean notReg = false; // not registrated fighter

    private static final int FIGHTER_PER_AGE = 1;

    private static final int FIGHTER_PER_COUNTRY = 2;

    private static final int FIGHTER_PER_REGION = 3;

    private static final int FIGHTER_PER_TEAM = 4;

    private static final int FIGHTER_PER_WEIGHTCLASS = 5;

    public String gotoEditFighter()
    {
        String ret = GOTO_FIGHTER;
        getRequest().setAttribute( WEB_ADMIN_FIGHTER, dataTable.getRowData() );
        return ret;
    }

    public String deleteFighter()
    {
        String ret = null;
        try
        {
            newaFighterManager.removeFighter( (NewaFighter) dataTable.getRowData() );
            ret = RELOAD;
        }
        catch ( NewaFighterInUseException e )
        {
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( NEWA_FIGHTER_NOT_DELETEABLE ) );
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

    public List<NewaFighterWeb> getFighters()
    {
        if ( newaFighterManager == null )
        {
            log.warn( "keine newafighterManager Injection" );
        }
        try
        {
            if ( getRequest().getAttribute( ALL_FIGHTER ) == null )
            {
                getCorrectFighterList();

                getRequest().setAttribute( ALL_FIGHTER, ALL_FIGHTER );
            }

        }
        catch ( JJWManagerException e )
        {
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
        }
        return fighters;
    }

    /**
     * calculates the correct fighter list
     * 
     * @throws NumberFormatException
     * @throws JJWManagerException
     */
    private void getCorrectFighterList()
        throws NumberFormatException, JJWManagerException
    {
        FacesContext context = FacesContext.getCurrentInstance();
        Map requestMap = context.getExternalContext().getRequestParameterMap();
        fighterPerId =
            Integer.valueOf( requestMap.get( "fighterPerId" ) == null ? "0" : (String) requestMap.get( "fighterPerId" ) );
        fighterPer =
            Integer.valueOf( requestMap.get( "fighterPer" ) == null ? "0" : (String) requestMap.get( "fighterPer" ) );
        // notReg = Boolean.valueOf("true".equals((String) requestMap.get("notReg")) ? true : false);

        if ( TypeUtil.isEmptyOrDefault( fighterPerId ) || TypeUtil.isEmptyOrDefault( fighterPer ) )
        {
            if ( notReg )
            {
                fighters = newaFighterManager.getAllNotRegistratedFighters( getLocale() );
            }
            else
            {
                fighters = newaFighterManager.getAllNewaFighters( getLocale() );
            }
        }
        else
        {
            switch ( fighterPer )
            {
                case FIGHTER_PER_AGE:
                    fighters = newaFighterManager.getFighterByAge( fighterPerId, getLocale(), notReg );
                    break;
                case FIGHTER_PER_COUNTRY:
                    fighters = newaFighterManager.getFighterByCountry( fighterPerId, getLocale(), notReg );
                    break;
                case FIGHTER_PER_REGION:
                    fighters = newaFighterManager.getFighterByRegion( fighterPerId, getLocale(), notReg );
                    break;
                case FIGHTER_PER_TEAM:
                    fighters = newaFighterManager.getFighterByTeam( fighterPerId, getLocale(), notReg );
                    break;
                case FIGHTER_PER_WEIGHTCLASS:
                    fighters = newaFighterManager.getFighterByNewaclass( fighterPerId, getLocale(), notReg );
                    break;
            }
        }
    }

    public List<NewaFighterWeb> getNotRegistratedFighters()
    {
        if ( newaFighterManager == null )
        {
            log.warn( "keine fighterManager Injection" );
        }
        try
        {
            notReg = true;
            if ( getRequest().getAttribute( ALL_NOT_REGISTRATED_FIGHTER ) == null )
            {
                getCorrectFighterList();
                getRequest().setAttribute( ALL_NOT_REGISTRATED_FIGHTER, ALL_NOT_REGISTRATED_FIGHTER );
            }

        }
        catch ( JJWManagerException e )
        {
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
        }
        return notRegistratedFighters = fighters;
    }

    public String registerFast()
    {
        try
        {
            newaFighterManager.registerFast( ( (NewaFighter) dataTable.getRowData() ).getId(),
                                         new ServiceExchangeContext(
                                                                     WebExchangeContextHelper.getWebExchangeContext(
                                                                                                                     getSession() ).getUserId() ) );
            notReg = true;
            getCorrectFighterList();
            notRegistratedFighters = fighters;
            return FIGHTER_BY;
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
            errors.add( new ErrorElement( ADMIN_FIGHTER_BLOCKED_POOL ) );
            setErrorMessageVector( errors );
        }
        return null;
    }

    /**
     * @param newaFighterManager The fighterManager to set.
     */
    public void setNewaFighterManager( NewaFighterManager newaFighterManager )
    {
        this.newaFighterManager = newaFighterManager;
    }

    public String fighterPerTeam()
    {
        try
        {
            FacesContext context = FacesContext.getCurrentInstance();
            Map requestMap = context.getExternalContext().getRequestParameterMap();
            notReg = Boolean.valueOf( "true".equals( requestMap.get( "notReg" ) ) ? true : false );
            notRegistratedFighters =
                fighters =
                    newaFighterManager.getFighterByTeam( ( (NewaFighter) dataTable.getRowData() ).getTeam().getId(),
                                                     getLocale(), notReg );
            fighterPer = FIGHTER_PER_TEAM;
            fighterPerId = ( (NewaFighter) dataTable.getRowData() ).getTeam().getId();
            return FIGHTER_BY;
        }
        catch ( JJWManagerException e )
        {
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
        }
        return null;
    }

    public String fighterPerNewaclass()
    {
        try
        {
            FacesContext context = FacesContext.getCurrentInstance();
            Map requestMap = context.getExternalContext().getRequestParameterMap();
            notReg = Boolean.valueOf( "true".equals( requestMap.get( "notReg" ) ) ? true : false );
            notRegistratedFighters =
                fighters =
                    newaFighterManager.getFighterByNewaclass( ( (NewaFighter) dataTable.getRowData() ).getNewaclass().getId(),
                                                              getLocale(), notReg );
            fighterPer = FIGHTER_PER_WEIGHTCLASS;
            fighterPerId = ( (NewaFighter) dataTable.getRowData() ).getNewaclass().getId();
            return FIGHTER_BY;
        }
        catch ( JJWManagerException e )
        {
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
        }
        return null;
    }

    public String fighterPerAge()
    {
        try
        {
            FacesContext context = FacesContext.getCurrentInstance();
            Map requestMap = context.getExternalContext().getRequestParameterMap();
            notReg = Boolean.valueOf( "true".equals( requestMap.get( "notReg" ) ) ? true : false );
            notRegistratedFighters =
                fighters =
                    newaFighterManager.getFighterByAge( ( (NewaFighter) dataTable.getRowData() ).getAge().getId(),
                                                        getLocale(),
                                                    notReg );
            fighterPer = FIGHTER_PER_AGE;
            fighterPerId = ( (NewaFighter) dataTable.getRowData() ).getAge().getId();
            return FIGHTER_BY;
        }
        catch ( JJWManagerException e )
        {
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
        }
        return null;
    }

    public String fighterPerRegion()
    {
        try
        {
            FacesContext context = FacesContext.getCurrentInstance();
            Map requestMap = context.getExternalContext().getRequestParameterMap();
            notReg = Boolean.valueOf( "true".equals( requestMap.get( "notReg" ) ) ? true : false );
            notRegistratedFighters =
                fighters =
                    newaFighterManager.getFighterByRegion( ( (NewaFighter) dataTable.getRowData() ).getTeam().getRegion().getId(),
                                                       getLocale(), notReg );
            fighterPer = FIGHTER_PER_REGION;
            fighterPerId = ( (NewaFighter) dataTable.getRowData() ).getTeam().getRegion().getId();
            return FIGHTER_BY;
        }
        catch ( JJWManagerException e )
        {
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
        }
        return null;
    }

    public String fighterPerCountry()
    {
        try
        {
            FacesContext context = FacesContext.getCurrentInstance();
            Map requestMap = context.getExternalContext().getRequestParameterMap();
            notReg = Boolean.valueOf( "true".equals( requestMap.get( "notReg" ) ) ? true : false );
            notRegistratedFighters =
                fighters =
                    newaFighterManager.getFighterByCountry( ( (NewaFighter) dataTable.getRowData() ).getTeam().getCountry().getId(),
                                                        getLocale(), notReg );
            fighterPer = FIGHTER_PER_COUNTRY;
            fighterPerId = ( (NewaFighter) dataTable.getRowData() ).getTeam().getCountry().getId();
            return FIGHTER_BY;
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
            NewaFighter fighter = (NewaFighter) dataTable.getRowData();          
            
            newaFighterManager.toggleMarkForExport( fighter,
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
    
    public int getFighterPer()
    {
        return fighterPer;
    }

    public void setFighterPer( int fighterPer )
    {
        this.fighterPer = fighterPer;
    }

    public long getFighterPerId()
    {
        return fighterPerId;
    }

    public void setFighterPerId( long fighterPerId )
    {
        this.fighterPerId = fighterPerId;
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
