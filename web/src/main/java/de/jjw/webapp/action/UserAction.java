/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : UserAction.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:43
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

package de.jjw.webapp.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.faces.model.SelectItem;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.User;
import de.jjw.model.generalhelper.Codestable;
import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.UserExistsException;
import de.jjw.service.UserManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.exception.UserInUseException;
import de.jjw.service.modelWeb.UserWeb;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.action.validation.UserValidator;
import de.jjw.webapp.constants.admin.IAgeConstants;

public class UserAction
    extends BasePage
    implements Serializable, IGlobalWebConstants, IAgeConstants
{
    UserManager userManager;

    User user = new User();

    List<UserWeb> users;

    private static String ALL_USERS = "allUsers";

    private static String NEW_USER = "newUser";

    private static String EDIT_USER = "editUser";


    public List<UserWeb> getUsers()
    {
        if ( userManager == null )
        {
            log.warn( "keine userManager Injection" );
        }
        if ( getRequest().getAttribute( ALL_USERS ) == null )
        {
            try
            {
                users = userManager.getUsers();
            }
            catch ( JJWManagerException e )
            {
                addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
            }
            getRequest().setAttribute( ALL_USERS, ALL_USERS );
        }
        return users;
    }

    public String addUser()
    {
        String ret = SUCCESS;
        Vector<ErrorElement> errors = new Vector<ErrorElement>();

        if ( !UserValidator.isNewUserValid( getUsers(), errors, user ) )
        {
            setErrorMessageVector( errors );
            getRequest().setAttribute( WEB_ADMIN_USER_NEW, WEB_ADMIN_USER_NEW );
            return null;
        }

        if ( !TypeUtil.isEmptyOrDefault( user.getPassword() ) )
        {
            encodePasswords();
        }

        setTechnicalDBFieldsForCreate( user );
        try
        {
            userManager.saveUser( user, new ServiceExchangeContext(
                WebExchangeContextHelper.getWebExchangeContext( getSession() ).getUserId() ) );

        }
        catch ( UserExistsException e )
        {
            errors.add( new ErrorElement( FIELD_AGE_SHORT, ADMIN_COUNTRY_EXISTS ) );
            setErrorMessageVector( errors );
            getRequest().setAttribute( WEB_ADMIN_USER_NEW, WEB_ADMIN_USER_NEW );
            ret = null;
        }
        catch ( Exception e )
        {
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
            getRequest().setAttribute( WEB_ADMIN_USER_NEW, WEB_ADMIN_USER_NEW );
            ret = null;
        }
        getRequest().removeAttribute( ALL_USERS );
        user = new User();
        return ret;
    }


    public String editUser()
    {
        String ret = SUCCESS;
        Vector<ErrorElement> errors = new Vector<ErrorElement>();
        if ( !UserValidator.isEditUserValid( getUsers(), errors, user ) )
        {
            setErrorMessageVector( errors );
            return null;
        }

        if ( !TypeUtil.isEmptyOrDefault( user.getPassword() ) )
        {
            encodePasswords();
        }
        try
        {
            userManager.saveUser( user, new ServiceExchangeContext(
                WebExchangeContextHelper.getWebExchangeContext( getSession() ).getUserId() ) );
        }
        catch ( UserExistsException e )
        {
            errors.add( new ErrorElement( FIELD_AGE_SHORT, ADMIN_COUNTRY_EXISTS ) );
            setErrorMessageVector( errors );
            ret = null;
        }
        catch ( OptimisticLockingException e )
        {
            errors.add( new ErrorElement( GLO_OPTIMISTIC_LOCKING ) );
            setErrorMessageVector( errors );
            ret = null;
        }
        catch ( Exception e )
        {
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
            ret = null;
        }
        user = new User();
        return ret;
    }

    public String deleteUser()
    {
        String ret = SUCCESS;

        try
        {
        	if (((User) dataTable.getRowData()).getId().equals(WebExchangeContextHelper.getWebExchangeContext(
                    getSession() ).getUserId() )){
        		Vector<ErrorElement> errors = new Vector<ErrorElement>();
                errors.add( new ErrorElement( ADMIN_USER_CAN_NOT_DELETE_SELF ) );
                setErrorMessageVector( errors );
                return null;
        	}
        	
           	userManager.removeUser(((User) dataTable.getRowData()).getId());
        }
        catch ( UserInUseException e )
        {// AgeInUseException
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
            log.error( "useraction", e );
            return null;
        }
        catch ( Exception e )
        {//AgeInUseException
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
            log.error( "useraction", e );
            return null;
        }
        return ret;
    }


    public String gotoAllUsers()
    {
        return ALL_USERS;
    }

    public String gotoNewUser()
    {
        setUser( new User() );
        String ret = NEW_USER;
        getRequest().setAttribute( WEB_ADMIN_USER_NEW, WEB_ADMIN_USER_NEW );
        return ret;
    }

    public String gotoEditUser()
    {
        setUser( (User) dataTable.getRowData() );
        return EDIT_USER;
    }

    /**
     * @return Returns the age.
     */
    public User getUser()
    {
        return user;
    }

    /**
     * @param age The age to set.
     */
    public void setUser( User user )
    {
        this.user = user;
    }

    /**
     * @param ageManager The ageManager to set.
     */
    public void setUserManager( UserManager userManager )
    {
        this.userManager = userManager;
    }


    public List getYesNoList()
    {
        List ret = new ArrayList();

        List<Codestable> list =
            CodestableMain.getInstance().getCodestableCodeByType( TYPE_YES_NO, getRequest().getLocale() );
        for ( Codestable entry : list )
        {
            //ret.add(new SelectItem(entry.getId(),entry.getValue()));
            if ( "1".equals( entry.getId() ) )
            {
                ret.add( new SelectItem( "true", entry.getValue() ) );
            }
            else
            {
                ret.add( new SelectItem( "false", entry.getValue() ) );
            }
        }
        return ret;
    }

    public List getContextList()
    {
        List ret = new ArrayList();

        List<Codestable> list =
            CodestableMain.getInstance().getCodestableCodeByType( TYPE_CONTEXT, getRequest().getLocale() );
        for ( Codestable entry : list )
        {
            ret.add( new SelectItem( entry.getId(), entry.getValue() ) );
        }
        return ret;
    }

    private void encodePasswords()
    {
        if ( passwordEncoder == null )
            passwordEncoder = new ShaPasswordEncoder();
        user.setPassword( passwordEncoder.encodePassword( user.getPassword(), null ) );
        user.setConfirmPassword( passwordEncoder.encodePassword( user.getConfirmPassword(), null ) );
    }
}
