/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : LocaleFilter.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:46
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
import java.util.Locale;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import de.jjw.util.Constants;

/**
 * Filter to wrap request with a request including user preferred locale.
 */
public class LocaleFilter
    extends OncePerRequestFilter
{

    public void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain chain )
        throws IOException, ServletException
    {

        String locale = request.getParameter( "locale" );
        Locale preferredLocale = null;

        if ( locale != null )
        {
            preferredLocale = new Locale( locale );
        }

        HttpSession session = request.getSession( false );

        if ( session != null )
        {
            if ( preferredLocale == null )
            {
                preferredLocale = (Locale) session.getAttribute( Constants.PREFERRED_LOCALE_KEY );
            }
            else
            {
                session.setAttribute( Constants.PREFERRED_LOCALE_KEY, preferredLocale );
                Config.set( session, Config.FMT_LOCALE, preferredLocale );
            }

            if ( preferredLocale != null && !( request instanceof LocaleRequestWrapper ) )
            {
                request = new LocaleRequestWrapper( request, preferredLocale );
                LocaleContextHolder.setLocale( preferredLocale );
            }
        }

        chain.doFilter( request, response );

        // Reset thread-bound LocaleContext.
        LocaleContextHolder.setLocaleContext( null );
    }
}
