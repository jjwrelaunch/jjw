/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : GZIPFilter.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:50
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

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @web.filter name="compressionFilter"
 */
public class GZIPFilter
    extends OncePerRequestFilter
{
    protected final Logger log = Logger.getRootLogger();

    @Override
    public void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain chain )
        throws IOException, ServletException
    {

        if ( isGZIPSupported( request ) )
        {
            if ( log.isDebugEnabled() )
            {
                log.debug( "GZIP supported, compressing response" );
            }

            GZIPResponseWrapper wrappedResponse = new GZIPResponseWrapper( response );

            chain.doFilter( request, wrappedResponse );
            wrappedResponse.finishResponse();

            return;
        }

        chain.doFilter( request, response );
    }

    /**
     * Convenience method to test for GZIP cababilities
     * 
     * @param req The current user request
     * @return boolean indicating GZIP support
     */
    private boolean isGZIPSupported( HttpServletRequest req )
    {

        String browserEncodings = req.getHeader( "accept-encoding" );
        boolean supported = ( ( browserEncodings != null ) && ( browserEncodings.indexOf( "gzip" ) != -1 ) );

        String userAgent = req.getHeader( "user-agent" );

        if ( ( userAgent != null ) && userAgent.startsWith( "httpunit" ) )
        {
            log.debug( "httpunit detected, disabling filter..." );

            return false;
        }
        else
        {
            return supported;
        }
    }
}
