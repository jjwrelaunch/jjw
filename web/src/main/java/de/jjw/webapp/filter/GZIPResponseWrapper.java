/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : GZIPResponseWrapper.java
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
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.log4j.Logger;

/**
 * Wraps Response for GZipFilter
 */
public class GZIPResponseWrapper
    extends HttpServletResponseWrapper
{
    protected final Logger log = Logger.getRootLogger();

    protected HttpServletResponse origResponse = null;

    protected ServletOutputStream stream = null;

    protected PrintWriter writer = null;

    protected int error = 0;

    public GZIPResponseWrapper( HttpServletResponse response )
    {
        super( response );
        origResponse = response;
    }

    public ServletOutputStream createOutputStream()
        throws IOException
    {
        return ( new GZIPResponseStream( origResponse ) );
    }

    public void finishResponse()
    {
        try
        {
            if ( writer != null )
            {
                writer.close();
            }
            else
            {
                if ( stream != null )
                {
                    stream.close();
                }
            }
        }
        catch ( IOException e )
        {
        }
    }

    @Override
    public void flushBuffer()
        throws IOException
    {
        if ( stream != null )
        {
            stream.flush();
        }
    }

    @Override
    public ServletOutputStream getOutputStream()
        throws IOException
    {
        if ( writer != null )
        {
            throw new IllegalStateException( "getWriter() has already been called!" );
        }

        if ( stream == null )
        {
            stream = createOutputStream();
        }

        return ( stream );
    }

    @Override
    public PrintWriter getWriter()
        throws IOException
    {

        if ( this.origResponse != null && this.origResponse.isCommitted() )
        {
            return super.getWriter();
        }

        if ( writer != null )
        {
            return ( writer );
        }

        if ( stream != null )
        {
            throw new IllegalStateException( "getOutputStream() has already been called!" );
        }

        stream = createOutputStream();
        writer = new PrintWriter( new OutputStreamWriter( stream, origResponse.getCharacterEncoding() ) );

        return ( writer );
    }

    @Override
    public void setContentLength( int length )
    {
    }

    /**
     * @see javax.servlet.http.HttpServletResponse#sendError(int, java.lang.String)
     */
    @Override
    public void sendError( int error, String message )
        throws IOException
    {
        super.sendError( error, message );
        this.error = error;

        if ( log.isDebugEnabled() )
        {
            log.debug( "sending error: " + error + " [" + message + "]" );
        }
    }
}
