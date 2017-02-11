/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : UserFightingclassOverviewAction.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:49
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


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ListDataModel;

import de.jjw.model.fighting.Fighter;
import de.jjw.model.fighting.UserFightingclass;
import de.jjw.service.UserManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.fighting.FightingclassManager;
import de.jjw.service.fighting.UserFightingclassManager;
import de.jjw.service.modelWeb.FightingclassWeb;
import de.jjw.service.modelWeb.UserWeb;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.DataModel;
import de.jjw.webapp.action.validation.ErrorElement;

public class UserFightingclassOverviewAction
    extends BasePage
    implements IGlobalWebConstants
{


    private static String RELOAD = "reload";

    private UserFightingclassManager userFightingclassManager;

    private FightingclassManager fightingclassManager;

    private UserManager userManager;

    private DataModel model = null;

    List<UserFightingclass> userFightingclasses = null;

    public void init()
    {

        try
        {
            List<String> headList = new ArrayList<String>();
            List<String> estimatedTatamiTime = new ArrayList<String>();
            List<UserWeb> users = userManager.getTatamiUsersFighting();

            for ( UserWeb user : users )
            {
                headList.add( user.getUsername() );
                estimatedTatamiTime.add( user.getTatamiTimeWeb() );
            }
            model.setDataTable( getDataTable() );
            model.setModelUserValue( new ListDataModel( users ) );
            model.setEstimatedTatamiTime( new ListDataModel( estimatedTatamiTime ) );
            model.setModelHead( new ListDataModel( headList ) );
            model.setModelValue(
                new ListDataModel( fightingclassManager.getInUseFightingclasses( null, getLocale() ) ) );
            model.setAssignMap( userFightingclassManager.getAssignMap( getLocale() ) );
        }
        catch ( JJWManagerException e )
        {
            log.error( e.getStackTrace() );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
    }

    public void toggleUserListener( ValueChangeEvent event )
    {
        try
        {
            if ( !event.getNewValue().equals( Boolean.valueOf( (String) event.getOldValue() ) ) )
            {
                FightingclassWeb fc = (FightingclassWeb) dataTable.getRowData();
                userFightingclassManager.toggleAccess( fc.getId(), ( (UserWeb) userManager.getTatamiUsersFighting().get(
                    Integer.valueOf( model.getModelHead().getRowIndex() ) ) ).getId(),
                                                       WebExchangeContextHelper.createServiceExchangeContext(
                                                           getSession() ) );
                userFightingclassManager.resetOrderNumbers(  ( (UserWeb) userManager.getTatamiUsersFighting().get(
                    Integer.valueOf( model.getModelHead().getRowIndex() ) ) ).getId() );
                init();
            }
        }
        catch ( Exception e )
        {
            log.error( e.getStackTrace() );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
    }
    
    public String moveUp()
    {
        try
        {

            FightingclassWeb fc = (FightingclassWeb) dataTable.getRowData();
            userFightingclassManager.moveUp( fc.getId(),
                                                   ( (UserWeb) userManager.getTatamiUsersFighting().get( Integer.valueOf( model.getModelHead().getRowIndex() ) ) ).getId(),
                                                   WebExchangeContextHelper.createServiceExchangeContext( getSession() ) );

        }
        catch ( JJWManagerException e )
        {
            log.error( e.getStackTrace() );
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
        }
       
        return RELOAD;
    }
    
    public String moveDown()
    {
        try
        {

            FightingclassWeb fc = (FightingclassWeb) dataTable.getRowData();
            userFightingclassManager.moveDown( fc.getId(),
                                                   ( (UserWeb) userManager.getTatamiUsersFighting().get( Integer.valueOf( model.getModelHead().getRowIndex() ) ) ).getId(),
                                                   WebExchangeContextHelper.createServiceExchangeContext( getSession() ) );

        }
        catch ( JJWManagerException e )
        {
            log.error( e.getStackTrace() );
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
        }
       
        return RELOAD;
    }

    public FightingclassManager getFightingclassManager()
    {
        return fightingclassManager;
    }

    public void setFightingclassManager( FightingclassManager fightingclassManager )
    {
        this.fightingclassManager = fightingclassManager;
    }

    public UserManager getUserManager()
    {
        return userManager;
    }

    public void setUserManager( UserManager userManager )
    {
        this.userManager = userManager;
    }

    public void setUserFightingclassManager( UserFightingclassManager userFightingclassManager )
    {
        this.userFightingclassManager = userFightingclassManager;
    }

    public DataModel getModel()
    {
        if ( model == null )
        {
            model = new DataModel();
            init();
        }
        return model;
    }

    public void setModel( DataModel model )
    {
        this.model = model;
    }
}
