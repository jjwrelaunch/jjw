/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : LogInValidator.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:44
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

package de.jjw.webapp.action.validation.extern;

import de.jjw.model.Account;
import de.jjw.model.User;
import de.jjw.service.UserManager;
import de.jjw.util.ICodestableConstants;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.action.validation.ErrorElement;

import java.util.Locale;
import java.util.Vector;

public class LogInValidator
    implements ICodestableConstants
{

    /**
     * @param user
     * @param errors
     * @return
     */
    public static boolean isLogInTeamFormValid( User user, Vector<ErrorElement> errors )
    {
        boolean isValid = true;

        if ( TypeUtil.isEmptyOrDefault( user.getUsername() ) )
        {
            errors.add( new ErrorElement( "", EXT_TEAM_LOGIN_NO_USER ) );
            isValid = false;
        }
        else
        {
            if ( IValueConstants.MIN_LENGHT_USERNAME > user.getUsername().length() )
            {
                errors.add( new ErrorElement( "", EXT_TEAM_LOGIN_USER_OR_PASSWORD_TO_SHORT ) );
                isValid = false;
            }
            else
            {
                if ( IValueConstants.MAX_LENGHT_USERNAME < user.getUsername().length() )
                {
                    errors.add( new ErrorElement( "", EXT_TEAM_LOGIN_USER_TO_LONG ) );
                    isValid = false;
                }
            }
        }

        if ( TypeUtil.isEmptyOrDefault( user.getPassword() ) )
        {
            errors.add( new ErrorElement( "", EXT_TEAM_LOGIN_NO_PASSWORD ) );
            isValid = false;
        }
        else
        {
            if ( IValueConstants.MIN_LENGHT_PASSWORD > user.getPassword().length() )
            {
                errors.add( new ErrorElement( "", EXT_TEAM_LOGIN_USER_OR_PASSWORD_TO_SHORT ) );
                isValid = false;
            }
            else
            {
                if ( IValueConstants.MAX_LENGHT_PASSWORD < user.getPassword().length() )
                {
                    errors.add( new ErrorElement( "", EXT_TEAM_LOGIN_PASSWORD_TO_LONG ) );
                    isValid = false;
                }
            }
        }
        return isValid;
    }

    /**
     * @param user
     * @param errors
     * @param userManager
     * @param locale
     * @return
     */
    public static boolean isLogInTeamBusinessValid( User user, Vector<ErrorElement> errors, UserManager userManager,
                                                    Locale locale )
    {
        boolean isValid = true;

        Account account = userManager.testLoginAllowed( user.getUsername(), user.getPassword() );
        if ( !account.isLoginAllowed() )
        {

            if ( account.isTooMuchWrongTry() )
            {
                errors.add( new ErrorElement( "", EXT_TEAM_LOGIN_TOO_MUCH_WRONG_LOGIN ) );
                return false;
            }

            if ( account.isLoginLocked() )
            {
                errors.add( new ErrorElement( "", EXT_TEAM_LOGIN_LOCKED ) );
                return false;
            }

            errors.add( new ErrorElement( "", EXT_TEAM_LOGIN_WRONG_USER_OR_PASSWORD ) );
            isValid = false;
        }
        return isValid;
    }
}
