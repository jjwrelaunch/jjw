/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : UserValidator.java
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

package de.jjw.webapp.action.validation;

import java.util.List;
import java.util.Vector;

import de.jjw.model.User;
import de.jjw.service.modelWeb.UserWeb;
import de.jjw.util.ICodestableConstants;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.constants.admin.IUserConstants;

public class UserValidator
    implements ICodestableConstants, IUserConstants
{
    public static boolean isNewUserValid( List<UserWeb> userList, Vector<ErrorElement> errors, User newUser )
    {
        boolean ret = true;

        if ( TypeUtil.isEmptyOrDefault( newUser.getUsername() ) )
        {
            errors.add( new ErrorElement( FIELD_USERNAME, GEN_NO_VALUE ) );
        }

        if ( TypeUtil.isEmptyOrDefault( newUser.getPassword() ) )
        {
            errors.add( new ErrorElement( FIELD_PASSWORD, GEN_NO_VALUE ) );
        }

        if ( TypeUtil.isEmptyOrDefault( newUser.getConfirmPassword() ) )
        {
            errors.add( new ErrorElement( FIELD_CONFIRM_PASSWORD, GEN_NO_VALUE ) );
        }

        if ( errors.size() > 0 )
        {
            return false;
        }

        if ( newUser.getUsername().length() > USER_MAX_LENGTH_DESCRIPTION )
        {
            errors.add( new ErrorElement( FIELD_USERNAME, ADMIN_USER_INPUT_TO_LONG ) );
        }

        if ( newUser.getPassword().length() > USER_MAX_LENGTH_DESCRIPTION )
        {
            errors.add( new ErrorElement( FIELD_PASSWORD, ADMIN_USER_INPUT_TO_LONG ) );
        }

        if ( newUser.getConfirmPassword().length() > USER_MAX_LENGTH_DESCRIPTION )
        {
            errors.add( new ErrorElement( FIELD_CONFIRM_PASSWORD, ADMIN_USER_INPUT_TO_LONG ) );
        }

        if ( newUser.getFirstName().length() > USER_MAX_LENGTH_DESCRIPTION )
        {
            errors.add( new ErrorElement( FIELD_FIRSTNAME, ADMIN_USER_INPUT_TO_LONG ) );
        }

        if ( newUser.getLastName().length() > USER_MAX_LENGTH_DESCRIPTION )
        {
            errors.add( new ErrorElement( FIELD_LASTNAME, ADMIN_USER_INPUT_TO_LONG ) );
        }

        if ( newUser.getUsername().length() < USER_MIN_PASSWORD_LENGHT )
        {
            errors.add( new ErrorElement( FIELD_USERNAME, ADMIN_USER_USERNAME_TO_SHORT ) );
        }

        if ( newUser.getPassword().length() < USER_MIN_PASSWORD_LENGHT )
        {
            errors.add( new ErrorElement( FIELD_PASSWORD, ADMIN_USER_PASSWORD_TO_SHORT ) );
        }

        if ( newUser.getConfirmPassword().length() < USER_MIN_PASSWORD_LENGHT )
        {
            errors.add( new ErrorElement( FIELD_CONFIRM_PASSWORD, ADMIN_USER_PASSWORD_TO_SHORT ) );
        }

        if ( !newUser.getPassword().equals( newUser.getConfirmPassword() ) )
        {
            errors.add( new ErrorElement( FIELD_CONFIRM_PASSWORD, ADMIN_USER_PASSWORD_CONFIRM_WRONG ) );
        }

        if ( errors.size() > 0 )
        {
            return false;
        }

        if ( isUserExists( userList, errors, newUser ) )
        {
            return false;
        }

        return ret;
    }

    public static boolean isEditUserValid( List<UserWeb> userList, Vector<ErrorElement> errors, User newUser )
    {
        boolean ret = true;
        if ( TypeUtil.isEmptyOrDefault( newUser.getUsername() ) )
        {
            errors.add( new ErrorElement( FIELD_USERNAME, GEN_NO_VALUE ) );
        }

        if ( TypeUtil.isEmptyOrDefault( newUser.getPassword() ) )
        {
            errors.add( new ErrorElement( FIELD_PASSWORD, GEN_NO_VALUE ) );
        }

        if ( TypeUtil.isEmptyOrDefault( newUser.getConfirmPassword() ) )
        {
            errors.add( new ErrorElement( FIELD_CONFIRM_PASSWORD, GEN_NO_VALUE ) );
        }
        if ( errors.size() > 0 )
        {
            return false;
        }

        if ( newUser.getUsername().length() > USER_MAX_LENGTH_DESCRIPTION )
        {
            errors.add( new ErrorElement( FIELD_USERNAME, ADMIN_USER_INPUT_TO_LONG ) );
        }

        if ( newUser.getPassword().length() > USER_MAX_LENGTH_DESCRIPTION )
        {
            errors.add( new ErrorElement( FIELD_PASSWORD, ADMIN_USER_INPUT_TO_LONG ) );
        }

        if ( newUser.getConfirmPassword().length() > USER_MAX_LENGTH_DESCRIPTION )
        {
            errors.add( new ErrorElement( FIELD_CONFIRM_PASSWORD, ADMIN_USER_INPUT_TO_LONG ) );
        }

        if ( newUser.getFirstName().length() > USER_MAX_LENGTH_DESCRIPTION )
        {
            errors.add( new ErrorElement( FIELD_FIRSTNAME, ADMIN_USER_INPUT_TO_LONG ) );
        }

        if ( newUser.getLastName().length() > USER_MAX_LENGTH_DESCRIPTION )
        {
            errors.add( new ErrorElement( FIELD_LASTNAME, ADMIN_USER_INPUT_TO_LONG ) );
        }

        if ( newUser.getUsername().length() < USER_MIN_PASSWORD_LENGHT )
        {
            errors.add( new ErrorElement( FIELD_USERNAME, ADMIN_USER_USERNAME_TO_SHORT ) );
        }

        if ( !TypeUtil.isEmptyOrDefault( newUser.getPassword() )
            && !TypeUtil.isEmptyOrDefault( newUser.getConfirmPassword() ) )
        {

            if ( newUser.getPassword().length() < USER_MIN_PASSWORD_LENGHT )
            {
                errors.add( new ErrorElement( FIELD_PASSWORD, ADMIN_USER_PASSWORD_TO_SHORT ) );
            }

            if ( newUser.getConfirmPassword().length() < USER_MIN_PASSWORD_LENGHT )
            {
                errors.add( new ErrorElement( FIELD_CONFIRM_PASSWORD, ADMIN_USER_PASSWORD_TO_SHORT ) );
            }

            if ( !newUser.getPassword().equals( newUser.getConfirmPassword() ) )
            {
                errors.add( new ErrorElement( FIELD_CONFIRM_PASSWORD, ADMIN_USER_PASSWORD_CONFIRM_WRONG ) );
            }
        }

        if ( errors.size() > 0 )
        {
            return false;
        }

        if ( isUserExists( userList, errors, newUser ) )
        {
            return false;
        }

        return ret;
    }

    /**
     * Check, if there is a age with this id
     * 
     * @param userList
     * @param errors
     * @param newUser
     * @return
     */

    private static boolean isUserExists( List<UserWeb> userList, Vector<ErrorElement> errors, User newUser )
    {
        for ( User item : userList )
        {
            if ( item.getUsername().equals( newUser.getUsername() )
                && item.getId().longValue() != newUser.getId().longValue() )
            {
                errors.add( new ErrorElement( FIELD_USERNAME, ADMIN_AGE_EXISTS ) );
                return true;
            }
        }
        return false;
    }

}
