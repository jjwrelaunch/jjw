/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : LocaleRequestWrapper.java
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

package de.jjw.webapp.filter;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.log4j.Logger;

/**
 * HttpRequestWrapper overriding methods getLocale(), getLocales() to include
 * the user's preferred locale.
 */
public class LocaleRequestWrapper
    extends HttpServletRequestWrapper
{
    protected final Logger log = Logger.getRootLogger();

    private final Locale preferredLocale;

    public LocaleRequestWrapper( HttpServletRequest decorated, Locale userLocale )
    {
        super( decorated );
        preferredLocale = userLocale;
        if ( null == preferredLocale )
        {
            log.error( "preferred locale = null, it is an unexpected value!" );
        }
    }

    /**
     * @see javax.servlet.ServletRequestWrapper#getLocale()
     */
    public Locale getLocale()
    {
        if ( null != preferredLocale )
        {
            return preferredLocale;
        }
        else
        {
            return super.getLocale();
        }
    }

    /**
     * @see javax.servlet.ServletRequestWrapper#getLocales()
     */
    public Enumeration getLocales()
    {
        if ( null != preferredLocale )
        {
            List l = Collections.list( super.getLocales() );
            if ( l.contains( preferredLocale ) )
            {
                l.remove( preferredLocale );
            }
            l.add( 0, preferredLocale );
            return Collections.enumeration( l );
        }
        else
        {
            return super.getLocales();
        }
    }

}
