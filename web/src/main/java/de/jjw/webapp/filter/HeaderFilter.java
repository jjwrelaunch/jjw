/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : ExpireFilter.java
 * Created : 05 Jun 2011
 * Last Modified: Sun, 05 Jun 2011 20:55:49
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class HeaderFilter
    implements Filter
{

    private FilterConfig filterConfig;

    private Map<String, String> headersMap;

    private static final SimpleDateFormat httpDateFormat = new SimpleDateFormat( "EEE, dd MMM yyyy HH:mm:ss z",
                                                                                 Locale.GERMAN );

    static
    {
        httpDateFormat.setTimeZone( TimeZone.getTimeZone( "CET" ) );
    }

    public synchronized static String getHttpDate( Date date )
    {
        return httpDateFormat.format( date );
    }

    public synchronized static Date getDateFromHttpDate( String date )
        throws ParseException
    {
        return httpDateFormat.parse( date );
    }

    public void init( FilterConfig filterConfig )
        throws ServletException
    {
        this.filterConfig = filterConfig;

        String headerParam = filterConfig.getInitParameter( "header" );
        if ( headerParam == null )
        {
            // log.warn("No headers were found in the web.xml (init-param) for the HeaderFilter !");
            return;
        }

        // Init the header list :
        headersMap = new LinkedHashMap<String, String>();

        if ( headerParam.contains( "|" ) )
        {
            String[] headers = headerParam.split( "|" );
            for ( String header : headers )
            {
                parseHeader( header );
            }
        }
        else
        {
            parseHeader( headerParam );
        }

    }

    public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain )
        throws IOException, ServletException
    {
        if ( headersMap != null )
        {
            // Add the header to the response
            Set<Entry<String, String>> headers = headersMap.entrySet();
            for ( Entry<String, String> header : headers )
            {
                ( (HttpServletResponse) response ).setHeader( header.getKey(), header.getValue() );
            }
        }
        // Continue
        chain.doFilter( request, response );
    }

    public void destroy()
    {
        this.filterConfig = null;
        this.headersMap = null;
    }

    private void parseHeader( String header )
    {
        String headerName = header.substring( 0, header.indexOf( ":" ) );
        if ( !headersMap.containsKey( headerName ) )
        {
            Date now = new Date();
            headersMap.put( headerName,
                            getHttpDate( new Date( now.getTime()
                                + Long.valueOf( header.substring( header.indexOf( ":" ) + 1 ) ) * 1000 ) ) );
        }
    }
}