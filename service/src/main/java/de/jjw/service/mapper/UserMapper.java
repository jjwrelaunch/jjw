/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : UserMapper.java
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

package de.jjw.service.mapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import de.jjw.model.Role;
import de.jjw.model.User;
import de.jjw.service.modelWeb.UserWeb;
import de.jjw.service.util.IGlobalProjectConstants;
import de.jjw.util.ICodestableTypeConstants;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;

public class UserMapper
    implements ICodestableTypeConstants, IValueConstants, IGlobalProjectConstants
{

    public static List<UserWeb> mapUserListFromDB( List<User> users )
    {
        if ( users == null )
        {
            return null;
        }

        List<UserWeb> ret = new ArrayList<UserWeb>( users.size() + 1 );
        UserWeb userWeb;
        for ( User user : users )
        {
            userWeb = mapUserFromDB( user );

            if ( user.isEnabled() )
            {
                userWeb.setEnableWeb( IMAGE_JJW_DIR + OK_GIF );
            }
            else
            {
                userWeb.setEnableWeb( IMAGE_JJW_DIR + X_GIF );
            }

            if ( _3 == user.getContext() )
            {
                userWeb.setContextWeb( "Tatami" );
            }
            if ( _4 == user.getContext() )
            {
                userWeb.setContextWeb( "Admin" );
            }

            if ( user.getTatamiTime() != null && !TypeUtil.isEmptyOrDefault( user.getTatamiTime() ) )
            {
                long estimatedTime = ( (long) user.getTatamiTime() );
                StringBuffer sb = new StringBuffer();

                sb.append( (int) estimatedTime / 3600 + ":" );
                estimatedTime = estimatedTime - ( (int) estimatedTime / 3600 ) * 3600;

                if ( ( (int) estimatedTime / 60 ) < 10 )
                {
                    sb.append( "0" );
                }
                sb.append( String.valueOf( (int) estimatedTime / 60 ) );
                userWeb.setTatamiTimeWeb( sb.toString() );
            }
            else
            {
                userWeb.setTatamiTimeWeb( "0:00" );
            }

            ret.add( userWeb );
        }

        return ret;
    }

    /**
     * maps a User from DB and get a new User object
     *
     * @param user
     * @return
     */
    public static UserWeb mapUserFromDB( User user )
    {
        UserWeb ret = new UserWeb();

        ret.setAccountEnabled( user.isEnabled() );
        ret.setContext( user.getContext() );
        ret.setUsername( new String( user.getUsername() ) );
        ret.setFirstName( new String( user.getFirstName() ) );
        ret.setLastName( new String( user.getLastName() ) );
//		ret.setAccountExpireDateWeb(new Timestamp(user.getAccountExpireDate().getTime()));
//		ret.setAccountLockedUntilDateWeb(new Timestamp(user.getAccountLockedUntilDate().getTime()));

        ret.setCreateDate( new Timestamp( user.getCreateDate().getTime() ) );
        ret.setCreateId( new Long( user.getCreateId() ) );
        ret.setId( new Long( user.getId() ) );
        ret.setModificationDate( new Timestamp( user.getModificationDate().getTime() ) );
        ret.setModificationId( new Long( user.getModificationId() ) );
        if ( user.getTatamiTime() == null )
        {
            ret.setTatamiTime( IValueConstants.LONG_DEFAULT );
        }
        else
        {
            ret.setTatamiTime( user.getTatamiTime() );
        }
        return ret;
    }

    /*
    * Function maps and set a new ModificationDate
    *
    */

    public static void mapUserToDB( User user, User userDB, boolean deepMapping, Role role )
    {

        userDB.setAccountEnabled( user.isEnabled() );
        userDB.setContext( user.getContext() );

        userDB.setUsername( new String( user.getUsername() ) );
        userDB.setFirstName( new String( user.getFirstName() ) );
        userDB.setLastName( new String( user.getLastName() ) );

        if ( !TypeUtil.isEmptyOrDefault( user.getPassword() ) &&
            !TypeUtil.isEmptyOrDefault( user.getConfirmPassword() ) &&
            user.getPassword().equals( user.getConfirmPassword() ) )
        {
            userDB.setPassword( new String( user.getPassword() ) );
            userDB.setConfirmPassword( new String( user.getConfirmPassword() ) );
        }

//        userDB.setAccountExpireDate(new Timestamp(user.getAccountExpireDate().getTime()));
//        userDB.setAccountLockedUntilDate(new Timestamp(user.getAccountLockedUntilDate().getTime()));

        userDB.setLastLogInDate( user.getLastLogInDate() );
        userDB.setLogInErrors( user.getLogInErrors() );
        userDB.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
        userDB.setModificationId( Long.valueOf( user.getModificationId() ) );
        if ( role != null )
        {
            userDB.getRoles().clear();
            userDB.addRole( role );
        }
    }


}
