/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : UserDuoclassOverviewAction.java
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
import java.util.Vector;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ListDataModel;

import de.jjw.model.duo.UserDuoclass;
import de.jjw.service.UserManager;
import de.jjw.service.duo.DuoclassManager;
import de.jjw.service.duo.UserDuoclassManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.modelWeb.DuoclassWeb;
import de.jjw.service.modelWeb.UserWeb;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.DataModelDuo;
import de.jjw.webapp.action.validation.ErrorElement;

public class UserDuoclassOverviewAction
    extends BasePage
    implements IGlobalWebConstants
{


    private static String RELOAD = "reload";

    private UserDuoclassManager userDuoclassManager;

    private DuoclassManager duoclassManager;

    private UserManager userManager;

    private DataModelDuo model = null;

    List<UserDuoclass> userDuoclasses = null;

    public void init()
    {

        try
        {
            List<String> headList = new ArrayList<String>();
            List<String> estimatedTatamiTime = new ArrayList<String>();
            List<UserWeb> users = userManager.getTatamiUsersDuo();

            for ( UserWeb user : users )
            {
                headList.add( user.getUsername() );
                estimatedTatamiTime.add( user.getTatamiTimeWeb() );
            }
            model.setDataTable( getDataTable() );
            model.setModelUserValue( new ListDataModel( users ) );
            model.setEstimatedTatamiTime( new ListDataModel( estimatedTatamiTime ) );
            model.setModelHead( new ListDataModel( headList ) );
            model.setModelValue( new ListDataModel( duoclassManager.getInUseDuoclasses( null, getLocale() ) ) );
            model.setAssignMap( userDuoclassManager.getAssignMap( getLocale() ) );
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
                DuoclassWeb dc = (DuoclassWeb) dataTable.getRowData();
                userDuoclassManager.toggleAccess( dc.getId(), ( (UserWeb) userManager.getTatamiUsersFighting().get(
                    Integer.valueOf( model.getModelHead().getRowIndex() ) ) ).getId(),
                                                  WebExchangeContextHelper.createServiceExchangeContext(
                                                      getSession() ) );
                userDuoclassManager.resetOrderNumbers(  ( (UserWeb) userManager.getTatamiUsersFighting().get(
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

            DuoclassWeb fc = (DuoclassWeb) dataTable.getRowData();
            userDuoclassManager.moveUp( fc.getId(),
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

            DuoclassWeb fc = (DuoclassWeb) dataTable.getRowData();
            userDuoclassManager.moveDown( fc.getId(),
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

    public DuoclassManager getDuoclassManager()
    {
        return duoclassManager;
    }

    public void setDuoclassManager( DuoclassManager duoclassManager )
    {
        this.duoclassManager = duoclassManager;
    }

    public UserManager getUserManager()
    {
        return userManager;
    }

    public void setUserManager( UserManager userManager )
    {
        this.userManager = userManager;
    }

    public void setUserDuoclassManager( UserDuoclassManager userDuoclassManager )
    {
        this.userDuoclassManager = userDuoclassManager;
    }

    public DataModelDuo getModel()
    {
        if ( model == null )
        {
            model = new DataModelDuo();
            init();
        }
        return model;
    }

    public void setModel( DataModelDuo model )
    {
        this.model = model;
    }
}
