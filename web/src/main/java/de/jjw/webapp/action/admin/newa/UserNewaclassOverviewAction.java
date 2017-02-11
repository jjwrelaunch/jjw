/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : UserNewaclassOverviewAction.java
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

package de.jjw.webapp.action.admin.newa;


import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.ListDataModel;

import de.jjw.model.newa.UserNewaclass;
import de.jjw.service.UserManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.modelWeb.NewaclassWeb;
import de.jjw.service.modelWeb.UserWeb;
import de.jjw.service.newa.NewaclassManager;
import de.jjw.service.newa.UserNewaclassManager;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.DataModelNewa;
import de.jjw.webapp.action.validation.ErrorElement;

public class UserNewaclassOverviewAction
    extends BasePage
    implements IGlobalWebConstants
{


    private static String RELOAD = "reload";

    private UserNewaclassManager userNewaclassManager;

    private NewaclassManager newaclassManager;

    private UserManager userManager;

    private DataModelNewa model = null;

    List<UserNewaclass> userNewaclasses = null;

    public void init()
    {
        try
        {
            List<String> headList = new ArrayList<String>();
            List<String> estimatedTatamiTime = new ArrayList<String>();
            List<UserWeb> users = userManager.getTatamiUsersNewa();

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
 new ListDataModel( newaclassManager.getInUseNewaclasses( null, getLocale() ) ) );
            model.setAssignMap( userNewaclassManager.getAssignMap( getLocale() ) );
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
                NewaclassWeb fc = (NewaclassWeb) dataTable.getRowData();
                userNewaclassManager.toggleAccess( fc.getId(),
                                                   ( (UserWeb) userManager.getTatamiUsersFighting().get(
                    Integer.valueOf( model.getModelHead().getRowIndex() ) ) ).getId(),
                                                       WebExchangeContextHelper.createServiceExchangeContext(
                                                           getSession() ) );
                userNewaclassManager.resetOrderNumbers(  ( (UserWeb) userManager.getTatamiUsersFighting().get(
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

            NewaclassWeb fc = (NewaclassWeb) dataTable.getRowData();
            userNewaclassManager.moveUp( fc.getId(),
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

            NewaclassWeb fc = (NewaclassWeb) dataTable.getRowData();
            userNewaclassManager.moveDown( fc.getId(),
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

    
    public NewaclassManager getFightingclassManager()
    {
        return newaclassManager;
    }

    public void setNewaclassManager( NewaclassManager newaclassManager )
    {
        this.newaclassManager = newaclassManager;
    }

    public UserManager getUserManager()
    {
        return userManager;
    }

    public void setUserManager( UserManager userManager )
    {
        this.userManager = userManager;
    }

    public void setUserNewaclassManager( UserNewaclassManager userNewaclassManager )
    {
        this.userNewaclassManager = userNewaclassManager;
    }

    public DataModelNewa getModel()
    {
        if ( model == null )
        {
            model = new DataModelNewa();
            init();
        }
        return model;
    }

    public void setModel( DataModelNewa model )
    {
        this.model = model;
    }
}
