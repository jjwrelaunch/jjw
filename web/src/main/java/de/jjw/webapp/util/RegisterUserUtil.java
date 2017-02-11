/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : RegisterUserUtil.java
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

package de.jjw.webapp.util;

import de.jjw.model.User;
import de.jjw.service.UserManager;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;

public class RegisterUserUtil
    implements IGlobalWebConstants
{

    public static void registerTeamUser( User user, UserManager manager, String teamName )
    {
        String pw = createPassword();

        user.setAccountEnabled( true );
        user.setAccountExpired( false );
        user.setAccountLocked( false );
        user.setConfirmPassword( pw );
        user.setContext( CONTEXT_TEAM_AS_INTEGER );
        user.setCreateDate( TypeUtil.getCurrentTimestamp() );
        user.setCredentialsExpired( false );
        user.setEnabled( true );
        user.setModificationDate( TypeUtil.getCurrentTimestamp() );
        user.setModificationId( TypeUtil.LONG_OBJECT_DEFAULT );
        user.setPassword( pw );
        user.setUsername( createTeamName( manager, teamName ) );
        user.setLogInErrorsAll( TypeUtil.LONG_OBJECT_DEFAULT );
        //todo: rolle
    }

    private static String createTeamName( UserManager manager, String teamName )
    {
        String newTeamName = teamName.substring( 0, 8 );
        String hlp = null;
        if ( null != manager.getUserByUsernameNoFoundException( newTeamName ) )
        {
            hlp = newTeamName + "_1";
            newTeamName = hlp;
            int i = 1;

            while ( null != manager.getUserByUsernameNoFoundException( newTeamName ) )
            {
                hlp = newTeamName.substring( 0, newTeamName.indexOf( "_" ) + 1 ) + i;
                newTeamName = hlp;
                i++;
            }
        }
        return newTeamName;
    }

    private static String createPassword()
    {
        //Todo:  algorithmus einbauen
        return "aaa";
    }
}
