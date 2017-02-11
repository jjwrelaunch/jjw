/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : UserManager.java
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

package de.jjw.service;

import java.util.List;

import de.jjw.dao.UserDao;
import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.Account;
import de.jjw.model.User;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.exception.UserInUseException;


/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 * <p/>
 * <p><a href="UserManager.java.html"><i>View Source</i></a></p>
 */
public interface UserManager
{

    public void setUserDao( UserDao userDao );

    /**
     * Retrieves a user by userId.  An exception is thrown if user not found
     *
     * @param userId
     * @return User
     */
    public User getUser( long userId );

    /**
     * check if the combination of username and password correct
     * if username exist and password is wrong, then increase the
     * failure logincounter.
     * When max is reached, then block login
     *
     * @param username
     * @param password
     * @return
     */
    public Account testLoginAllowed( String username, String password );


    /**
     * Finds a user by their username.
     *
     * @param username
     * @return User a populated user object
     */
    public User getUserByUsernameNoFoundException( String username );

    /**
     * Retrieves a list of users, filtering with parameters on a user object
     *
     * @param user parameters to filter on
     * @return List
     * @throws JJWManagerException
     */
    public List getUsers()
        throws JJWManagerException;

    /**
     * Retrieves a list of users, filtering with parameters on a user object
     *
     * @param user parameters to filter on
     * @return List
     * @throws JJWManagerException
     */
    public List getTatamiUsersFighting()
        throws JJWManagerException;

    /**
     * Retrieves a list of users, filtering with parameters on a user object
     *
     * @param user parameters to filter on
     * @return List
     * @throws JJWManagerException
     */
    public List getTatamiUsersDuo()
        throws JJWManagerException;

    /**
     * Retrieves a list of users, filtering with parameters on a user object
     * 
     * @param user parameters to filter on
     * @return List
     * @throws JJWManagerException
     */
    public List getTatamiUsersNewa()
        throws JJWManagerException;

    /**
     * Saves a user's information
     *
     * @param user the user's information
     * @throws UserExistsException
     */
    public void saveUser( User user, ServiceExchangeContext ctx )
        throws UserExistsException, OptimisticLockingException;

    /**
     * Removes a user from the database by their userId
     * 
     * @param userId the user's id
     * @throws JJWManagerException
     * @throws UserInUseException
     */
    public void removeUser( Long userId )
        throws JJWManagerException, UserInUseException;

    /**
     * writes the last logIn date
     *
     * @param username
     */
    public void logInUser( String username );

    public void handleDeleteRoleAssignment();
}
