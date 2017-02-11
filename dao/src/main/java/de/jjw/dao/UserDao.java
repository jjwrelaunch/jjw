/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : UserDao.java
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

package de.jjw.dao;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.Account;
import de.jjw.model.User;


/**
 * User Data Access Object (Dao) interface.
 * <p/>
 * <p>
 * <a href="UserDao.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author
 */
public interface UserDao
    extends Dao
{
    /**
     * Gets users information based on user id.
     *
     * @param userId the user's id
     * @return user populated user object
     */
    public User getUser( Long userId );

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
     * Gets users information based on login name.
     *
     * @param username the user's username
     * @return userDetails populated userDetails object
     */
    public UserDetails loadUserByUsername( String username )
        throws UsernameNotFoundException;

    /**
     * Gets users information based on login name.
     *
     * @param username the user's username
     * @return userDetails populated userDetails object
     */
    public User getUserByUsername( String username )
        throws UsernameNotFoundException;

    /**
     * Gets a list of users based on parameters passed in.
     *
     * @return List populated list of users
     */
    public List<User> getUsers();

    /**
     * Gets a list of tatamiUsers based on parameters passed in.
     *
     * @return List populated list of users
     */
    public List<User> getTatamiUsersFighting()
        throws JJWDataLayerException;

    /**
     * Gets a list of tatamiUsers based on parameters passed in.
     *
     * @return List populated list of users
     */
    public List<User> getTatamiUsersDuo()
        throws JJWDataLayerException;

    /**
     * Gets a list of tatamiUsers based on parameters passed in.
     * 
     * @return List populated list of users
     */
    public List<User> getTatamiUsersNewa()
        throws JJWDataLayerException;

    /**
     * Saves a user's information
     *
     * @param user the object to be saved
     */
    public void saveUser( User user );

    /**
     * Removes a user from the database by id
     * 
     * @param userId the user's id
     * @return
     */
    public int removeUser( Long userId );


    /**
     * set new Lastlog in Date on DB
     *
     * @param username
     */
    public void logInUser( String username );

    public void handleDeleteRoleAssignment();

}
