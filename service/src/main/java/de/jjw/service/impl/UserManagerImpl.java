/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : UserManagerImpl.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:42
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

package de.jjw.service.impl;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import de.jjw.dao.RoleDao;
import de.jjw.dao.UserDao;
import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.Account;
import de.jjw.model.User;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.UserExistsException;
import de.jjw.service.UserManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.exception.UserInUseException;
import de.jjw.service.mapper.UserMapper;
import de.jjw.service.modelWeb.UserWeb;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;

/**
 * Implementation of UserManager interface.</p>
 * <p/>
 * <p>
 * <a href="UserManagerImpl.java.html"><i>View Source</i></a>
 * </p>
 */
public class UserManagerImpl
    extends BaseManager
    implements UserManager, IValueConstants
{
    private UserDao dao;

    private RoleDao roleDao;

    /**
     * Set the Dao for communication with the data layer.
     * 
     * @param dao
     */
    public void setUserDao( UserDao dao )
    {
        this.dao = dao;
    }

    /**
     * @see de.jjw.service.UserManager#getUser(java.lang.String)
     */
    public User getUser( long userId )
    {
        return dao.getUser( userId );
    }

    /**
     * @see de.jjw.service.UserManager#getUsers(de.jjw.model.User)
     */
    public List<UserWeb> getUsers()
        throws JJWManagerException
    {
        try
        {
            return UserMapper.mapUserListFromDB( dao.getUsers() );
        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }

    /**
     * @see de.jjw.service.UserManager#getTatamiUsers(de.jjw.model.User)
     */
    public List<UserWeb> getTatamiUsersFighting()
        throws JJWManagerException
    {
        try
        {
            return UserMapper.mapUserListFromDB( dao.getTatamiUsersFighting() );
        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }

    /**
     * @see de.jjw.service.UserManager#getTatamiUsers(de.jjw.model.User)
     */
    public List<UserWeb> getTatamiUsersDuo()
        throws JJWManagerException
    {
        try
        {
            return UserMapper.mapUserListFromDB( dao.getTatamiUsersDuo() );
        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }

    /**
     * @see de.jjw.service.UserManager#getTatamiUsers(de.jjw.model.User)
     */
    public List<UserWeb> getTatamiUsersNewa()
        throws JJWManagerException
    {
        try
        {
            return UserMapper.mapUserListFromDB( dao.getTatamiUsersNewa() );
        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }

    /**
     * @see de.jjw.service.UserManager#saveUser(de.jjw.model.User)
     */
    public void saveUser( User user, ServiceExchangeContext ctx )
        throws UserExistsException, OptimisticLockingException
    {

        try
        {
            if ( null == user.getId() || TypeUtil.isEmptyOrDefault( user.getId().longValue() ) )
            {
                user.setId( null );
                user.setLogInErrors( INTEGER_0 );
                user.setLogInErrorsAll( 0l );
                if ( user.getContext() == IValueConstants._4 )
                {
                    user.addRole( roleDao.getRoleByName( "admin" ) );
                }
                if ( user.getContext() == IValueConstants._3 )
                {
                    user.addRole( roleDao.getRoleByName( "user" ) );
                }
                dao.saveObject( user );
            }
            else
            {
                user.setModificationId( ctx.getUserId() );
                user.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                optimisticDao.testLocking( TABLE_ACCOUNT, user.getId(), user.getModificationDate() );
                if ( user.getContext() == IValueConstants._4 )
                {
                    UserMapper.mapUserToDB( user, dao.getUser( user.getId() ), false, roleDao.getRoleByName( "admin" ) );
                }
                if ( user.getContext() == IValueConstants._3 )
                {
                    UserMapper.mapUserToDB( user, dao.getUser( user.getId() ), false, roleDao.getRoleByName( "user" ) );
                }
            }

        }
        catch ( DataIntegrityViolationException e )
        {
            throw new UserExistsException( "User '" + user.getUsername() + "' already exists!" );
        }
    }

    /**
     * @throws JJWManagerException
     * @throws UserInUseException
     * @see de.jjw.service.UserManager#removeUser(java.lang.String)
     */
    public void removeUser( Long userId )
        throws JJWManagerException, UserInUseException
    {
        if ( log.isDebugEnabled() )
        {
            log.debug( "removing user: " + userId );
        }

        List<User> users = dao.getUsers();
        int adminCount = 0;
        boolean deleteAdmin = false;
        for ( User user : users )
        {
            if ( _4 == user.getContext() )
            {
                adminCount++;
            }
            if ( user.getId().equals( userId ) && _4 == user.getContext() )
            {
                deleteAdmin = true;
            }
        }

        if ( deleteAdmin && adminCount < 2 )
        {
            throw new JJWManagerException();
        }
        int result = dao.removeUser( userId );
        if ( result == 1 )
            throw new UserInUseException();
    }

    public User getUserByUsername( String username )
        throws UsernameNotFoundException
    {
        return (User) dao.getUserByUsername( username );
    }

    public User getUserByUsernameNoFoundException( String username )
    {
        User user = null;
        try
        {
            user = (User) dao.getUserByUsername( username );
        }
        catch ( UsernameNotFoundException e )
        {
            // do nothing, null will return
        }
        return user;
    }

    public Account testLoginAllowed( String username, String password )
    {
        if ( log.isDebugEnabled() )
        {
            log.debug( "testLoginAllowed user: " + username );// + "  password: " +password);
        }
        return dao.testLoginAllowed( username, password );
    }

    public void logInUser( String username )
    {
        if ( log.isDebugEnabled() )
        {
            log.debug( "Usermanager.logInUser" );
        }
        dao.logInUser( username );
    }

    public RoleDao getRoleDao()
    {
        return roleDao;
    }

    public void setRoleDao( RoleDao roleDao )
    {
        this.roleDao = roleDao;
    }

    public void handleDeleteRoleAssignment()
    {
        dao.handleDeleteRoleAssignment();
    }
}
