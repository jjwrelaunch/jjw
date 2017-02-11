/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : JJWImageServlet.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 21:32:01
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

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import de.jjw.model.Team;
import de.jjw.service.TeamManager;
import de.jjw.util.TypeUtil;

public class JJWImageServlet
    extends HttpServlet
{

    // Constants ----------------------------------------------------------------------------------

    private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.

    private static final Map<Team, BufferedImage> cache = new HashMap<Team, BufferedImage>();

    private static final int DEFAULT_LOGO_WIDTH = 120;

    private static final int DEFAULT_LOGO_HEIGHT = 100;

    // Statics ------------------------------------------------------------------------------------

    // private static ImageDAO imageDAO = DAOFactory.getImageDAO();

    // Actions ------------------------------------------------------------------------------------

    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
        throws ServletException, IOException
    {
        try
        {

        // Get ID from request.
        String imageId = request.getParameter( "id" );
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext( getServletContext() );

        // Check if ID is supplied to the request.
        if ( TypeUtil.isEmptyOrDefault( imageId ) )
        {
            // Do your thing if the ID is not supplied to the request.
            // Throw an exception, or send 404, or show default/warning image, or just ignore it.
            response.sendError( HttpServletResponse.SC_NOT_FOUND ); // 404.
            return;
        }

        TeamManager teamManager = (TeamManager) context.getBean( "teamManager" );
        Team team = teamManager.getTeam( Long.valueOf( imageId ) );
        synchronized ( cache )
        {
            if ( cache.get( team ) == null && team.getLogo() == null )
            {
                response.sendError( HttpServletResponse.SC_NOT_FOUND ); // 404.
                return;
            }
        }

        BufferedImage logoImage;
        ByteArrayInputStream bs;

        int targetWidth = Integer.parseInt( getParam( request, "w", String.valueOf( DEFAULT_LOGO_WIDTH ) ) );

            // synchronized ( cache )
            // {
            // if ( ( logoImage = cache.get( team ) ) == null ) // not cached
            // {
            // logoImage = ImageIO.read( new BufferedInputStream( new ByteArrayInputStream( team.getLogo() ) ) );
            //
            // if ( logoImage.getWidth() <= targetWidth && logoImage.getHeight() <= DEFAULT_LOGO_HEIGHT )
            // {
            // // do nothing
            // }
            // else
            // // resize
            // {
            // if ( (double) logoImage.getWidth() / logoImage.getHeight() > targetWidth / DEFAULT_LOGO_HEIGHT )
            // {
            // int width = targetWidth < logoImage.getWidth() ? targetWidth : logoImage.getWidth();
            // int height =
            // targetWidth < logoImage.getWidth() ? ( targetWidth * logoImage.getHeight() / logoImage.getWidth() )
            // : ( width * logoImage.getHeight() / logoImage.getWidth() );
            //
            // logoImage = createResizedCopy( logoImage, width, height );
            // }
            // else
            // {
            // int height =
            // DEFAULT_LOGO_HEIGHT < logoImage.getHeight() ? DEFAULT_LOGO_HEIGHT
            // : logoImage.getHeight();
            // int width =
            // DEFAULT_LOGO_HEIGHT < logoImage.getHeight() ? ( DEFAULT_LOGO_HEIGHT
            // * logoImage.getWidth() / logoImage.getHeight() )
            // : ( height * logoImage.getWidth() / logoImage.getHeight() );
            //
            // logoImage = createResizedCopy( logoImage, width, height );
            // }
            //
            // }
            // cache.put( team, logoImage );
            // }
            // else
            // // cached
            // {
            // // do nothing since the image is already cached
            // }
            // }

        // Init servlet response.
        // response.reset();
            // response.setBufferSize( DEFAULT_BUFFER_SIZE );
            // response.setHeader("Content-Type", image.getContentType());
        // response.setHeader("Content-Length", String.valueOf(image.getLength()));
        // response.setHeader("Content-Disposition", "inline; filename=\"" + image.getName() + "\"");

        // Prepare streams.
        BufferedInputStream input = null;
        BufferedOutputStream output = null;


            // // Open streams.
            // input = new BufferedInputStream( ImageIO.write( logoImage, ), DEFAULT_BUFFER_SIZE );
            input = new BufferedInputStream( new ByteArrayInputStream( team.getLogo() ) );
            output = new BufferedOutputStream( response.getOutputStream() );

            // Write file contents to response.
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ( ( length = input.read( buffer ) ) > 0 )
            {
                output.write( buffer, 0, length );
            }

            // ImageIO.write( logoImage, "PNG", response.getOutputStream() );

            // Finalize task.
            output.flush();
        }
        catch ( Exception e )
        {
            log( "can not read logo", e );
        }
        finally
        {
            // Gently close streams.
            // close( output );
            // close( input );
            // response.flushBuffer();
        }
    }

    public static boolean hasParam( HttpServletRequest request, String param )
    {
        String parameter = request.getParameter( param );
        return !( parameter == null || "".equals( parameter ) );
    }

    public static BufferedImage createResizedCopy( BufferedImage originalImage, int scaledWidth, int scaledHeight )
    {
        GraphicsConfiguration gc = originalImage.createGraphics().getDeviceConfiguration();
        BufferedImage scaledBI = gc.createCompatibleImage( scaledWidth, scaledHeight, Transparency.TRANSLUCENT );
        Graphics2D g = scaledBI.createGraphics();
        g.setComposite( AlphaComposite.Src );
        g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
        // g.setColor( new Color( 0, 0, 0, 0 ) );
        // g.fillRect( 0, 0, scaledWidth, scaledHeight );
        // g.setComposite( AlphaComposite.SrcOver );
        g.drawImage( originalImage, 0, 0, scaledWidth, scaledHeight, null );
        g.dispose();
        return scaledBI;
    }

    // Check the param if it's not present return the default

    public static String getParam( HttpServletRequest request, String param, String def )
    {
        String parameter = request.getParameter( param );
        if ( parameter == null || "".equals( parameter ) )
        {
            return def;
        }
        else
        {
            return parameter;
        }
    }

    // Helpers (can be refactored to public utility class) ----------------------------------------

    private static void close( Closeable resource )
    {
        if ( resource != null )
        {
            try
            {
                resource.close();
            }
            catch ( IOException e )
            {
                // Do your thing with the exception. Print it, log it or mail it.
                e.printStackTrace();
            }
        }
    }

}
