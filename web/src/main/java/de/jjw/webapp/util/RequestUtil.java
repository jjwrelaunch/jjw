/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : RequestUtil.java
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

package de.jjw.webapp.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


/**
 * Convenience class for setting and retrieving cookies.
 */
public class RequestUtil
{
    protected final static Logger log = Logger.getRootLogger();

    /**
     * Convenience method to set a cookie
     *
     * @param response
     * @param name
     * @param value
     * @param path
     */
    public static void setCookie( HttpServletResponse response, String name, String value, String path )
    {
        if ( log.isDebugEnabled() )
        {
            log.debug( "Setting cookie '" + name + "' on path '" + path + "'" );
        }

        Cookie cookie = new Cookie( name, value );
        cookie.setSecure( false );
        cookie.setPath( path );
        cookie.setMaxAge( 3600 * 24 * 30 ); // 30 days

        response.addCookie( cookie );
    }

    /**
     * Convenience method to get a cookie by name
     *
     * @param request the current request
     * @param name    the name of the cookie to find
     * @return the cookie (if found), null if not found
     */
    public static Cookie getCookie( HttpServletRequest request, String name )
    {
        Cookie[] cookies = request.getCookies();
        Cookie returnCookie = null;

        if ( cookies == null )
        {
            return returnCookie;
        }

        for ( int i = 0; i < cookies.length; i++ )
        {
            Cookie thisCookie = cookies[i];

            if ( thisCookie.getName().equals( name ) )
            {
                // cookies with no value do me no good!
                if ( !thisCookie.getValue().equals( "" ) )
                {
                    returnCookie = thisCookie;

                    break;
                }
            }
        }

        return returnCookie;
    }

    /**
     * Convenience method for deleting a cookie by name
     *
     * @param response the current web response
     * @param cookie   the cookie to delete
     * @param path     the path on which the cookie was set (i.e. /appfuse)
     */
    public static void deleteCookie( HttpServletResponse response, Cookie cookie, String path )
    {
        if ( cookie != null )
        {
            // Delete the cookie by setting its maximum age to zero
            cookie.setMaxAge( 0 );
            cookie.setPath( path );
            response.addCookie( cookie );
        }
    }

    /**
     * Convenience method to get the application's URL based on request
     * variables.
     */
    public static String getAppURL( HttpServletRequest request )
    {
        StringBuffer url = new StringBuffer();
        int port = request.getServerPort();
        if ( port < 0 )
        {
            port = 80; // Work around java.net.URL bug
        }
        String scheme = request.getScheme();
        url.append( scheme );
        url.append( "://" );
        url.append( request.getServerName() );
        if ( ( scheme.equals( "http" ) && ( port != 80 ) ) || ( scheme.equals( "https" ) && ( port != 443 ) ) )
        {
            url.append( ':' );
            url.append( port );
        }
        url.append( request.getContextPath() );
        return url.toString();
    }
}
