/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : UserCounterListener.java
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

package de.jjw.webapp.listener;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContext;

import de.jjw.model.User;

/**
 * UserCounterListener class used to count the current number of active users for the applications. Does this by
 * counting how many user objects are stuffed into the session. It Also grabs these users and exposes them in the
 * servlet context.
 * 
 * @web.listener
 */
public class UserCounterListener
    implements ServletContextListener, HttpSessionAttributeListener
{
    public static final String COUNT_KEY = "userCounter";

    public static final String USERS_KEY = "userNames";

    public static final String EVENT_KEY = "SECURITY_CONTEXT_KEY";

    protected final Logger log = Logger.getRootLogger();

    private transient ServletContext servletContext;

    private int counter;

    private Set users;

    public synchronized void contextInitialized( ServletContextEvent sce )
    {
        servletContext = sce.getServletContext();
        servletContext.setAttribute( ( COUNT_KEY ), Integer.toString( counter ) );
    }

    public synchronized void contextDestroyed( ServletContextEvent event )
    {
        servletContext = null;
        users = null;
        counter = 0;
    }

    synchronized void incrementUserCounter()
    {
        counter = Integer.parseInt( (String) servletContext.getAttribute( COUNT_KEY ) );
        counter++;
        servletContext.setAttribute( COUNT_KEY, Integer.toString( counter ) );

        if ( log.isDebugEnabled() )
        {
            log.debug( "User Count: " + counter );
        }
    }

    synchronized void decrementUserCounter()
    {
        int counter = Integer.parseInt( (String) servletContext.getAttribute( COUNT_KEY ) );
        counter--;

        if ( counter < 0 )
        {
            counter = 0;
        }

        servletContext.setAttribute( COUNT_KEY, Integer.toString( counter ) );

        if ( log.isDebugEnabled() )
        {
            log.debug( "User Count: " + counter );
        }
    }

    synchronized void addUsername( Object user )
    {
        users = (Set) servletContext.getAttribute( USERS_KEY );

        if ( users == null )
        {
            users = new HashSet();
        }

        if ( log.isDebugEnabled() )
        {
            if ( users.contains( user ) )
            {
                log.debug( "User already logged in, adding anyway..." );
            }
        }

        users.add( user );
        servletContext.setAttribute( USERS_KEY, users );
        incrementUserCounter();
    }

    synchronized void removeUsername( Object user )
    {
        users = (Set) servletContext.getAttribute( USERS_KEY );

        if ( users != null )
        {
            users.remove( user );
        }

        servletContext.setAttribute( USERS_KEY, users );
        decrementUserCounter();
    }

    /**
     * This method is designed to catch when user's login and record their name
     * 
     * @see javax.servlet.http.HttpSessionAttributeListener#attributeAdded(javax.servlet.http.HttpSessionBindingEvent)
     */
    public void attributeAdded( HttpSessionBindingEvent event )
    {
        log.debug( "event.name: " + event.getName() );
        if ( event.getName().equals( EVENT_KEY ) )
        {
            SecurityContext securityContext = (SecurityContext) event.getValue();
            User user = (User) securityContext.getAuthentication().getPrincipal();
            addUsername( user );
        }
    }

    /**
     * When user's logout, remove their name from the hashMap
     * 
     * @see javax.servlet.http.HttpSessionAttributeListener#attributeRemoved(javax.servlet.http.HttpSessionBindingEvent)
     */
    public void attributeRemoved( HttpSessionBindingEvent event )
    {
        if ( event.getName().equals( EVENT_KEY ) )
        {
            SecurityContext securityContext = (SecurityContext) event.getValue();
            User user = (User) securityContext.getAuthentication().getPrincipal();
            removeUsername( user );
        }
    }

    /**
     * @see javax.servlet.http.HttpSessionAttributeListener#attributeReplaced(javax.servlet.http.HttpSessionBindingEvent)
     */
    public void attributeReplaced( HttpSessionBindingEvent event )
    {
        // I don't really care if the user changes their information
    }
}
