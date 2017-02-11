/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : MessageFilter.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:49
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

package de.jjw.webapp.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * Filter to remove messages form the session and put them in the request
 * - to solve the redirect after post issue.
 * <p/>
 * @web.filter display-name="Message Filter" name="messageFilter"
 */
public class MessageFilter
    implements Filter
{
    protected final Logger log = Logger.getRootLogger();

    public void doFilter( ServletRequest req, ServletResponse res, FilterChain chain )
        throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) req;

        // grab messages from the session and put them into request
        // this is so they're not lost in a redirect
        Object messages = request.getSession().getAttribute( "messages" );

        if ( messages != null )
        {
            request.setAttribute( "messages", messages );
            request.getSession().removeAttribute( "messages" );
        }

        // grab errors from the session and put them into request
        // this is so they're not lost in a redirect
        Object errors = request.getSession().getAttribute( "errors" );

        if ( errors != null )
        {
            request.setAttribute( "errors", errors );
            request.getSession().removeAttribute( "errors" );
        }
        chain.doFilter( req, res );
    }

    public void init( FilterConfig filterConfig )
    {
    }

    public void destroy()
    {
    }
}