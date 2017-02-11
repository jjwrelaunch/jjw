/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : ImageResizer.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:48
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

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: Tino
 * Date: 31/05/2010
 * Time: 9:43:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class ImageResizer
    extends HttpServlet
{
    /**
     * Servlet resizes an image located in a directory in a web project ex.
     * /image?root=/albums&file=/thumbs/imagename.jpg&width=270&height=100 ex.
     * /image?file=/thumbs/imagename.jpg&width=270 (default root, calculated
     * height)
     */
    private static final long serialVersionUID = -8285774993751841288L;

    public void doGet( HttpServletRequest request, HttpServletResponse response )
    {
        // Optional: Only supports output of jpg and png, defaults to png if not
        // specified
        String imageOutput = getParam( request, "output", "jpg" );

        // Set the mime type of the image
        if ( "png".equals( imageOutput ) )
        {
            response.setContentType( "image/png" );
        }
        else
        {
            response.setContentType( "image/jpeg" );
        }

        // Server Location of the image
        String imageLoc = getServletContext().getRealPath( request.getServletPath() );

        try
        {
            // Read the original image from the Server Location
            BufferedImage bufferedImage = ImageIO.read( new File( imageLoc ) );
            // Write the image

            if ( hasParam( request, "width" ) || hasParam( request, "height" ) )
            {
                int width;
                int height;
                if ( hasParam( request, "width" ) && hasParam( request, "height" ) )
                {
                    width = Integer.parseInt( getParam( request, "width", "0" ) );
                    height = Integer.parseInt( getParam( request, "height", "0" ) );
                }
                else if ( hasParam( request, "width" ) )
                {
                    // Required: Width image should be resized to
                    width = Integer.parseInt( getParam( request, "width", "0" ) );
                    height = width * bufferedImage.getHeight() / bufferedImage.getWidth();
                }
                else
                {
                    // Required: Width image should be resized to
                    height = Integer.parseInt( getParam( request, "height", "0" ) );
                    width = height * bufferedImage.getWidth() / bufferedImage.getHeight();
                }

                ImageIO.write( createResizedCopy( bufferedImage, width, height ), imageOutput,
                               response.getOutputStream() );
            }
            else
            {
                ImageIO.write( bufferedImage, imageOutput, response.getOutputStream() );
            }


        }
        catch ( Exception e )
        {
            System.out.println( "Problem with image: " + imageLoc + e );

        }
    }


    public static BufferedImage createResizedCopy( BufferedImage originalImage, int scaledWidth, int scaledHeight )
    {
        GraphicsConfiguration gc = originalImage.createGraphics().getDeviceConfiguration();
        BufferedImage scaledBI = gc.createCompatibleImage( scaledWidth, scaledHeight, Transparency.TRANSLUCENT );
        Graphics2D g = scaledBI.createGraphics();
        g.setComposite( AlphaComposite.Src );
        g.setRenderingHint( RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON );
//        g.setColor( new Color( 0, 0, 0, 0 ) );
//        g.fillRect( 0, 0, scaledWidth, scaledHeight );
//        g.setComposite( AlphaComposite.SrcOver );
        g.drawImage( originalImage, 0, 0, scaledWidth, scaledHeight, null );
        g.dispose();
        return scaledBI;
    }

    private boolean hasParam( HttpServletRequest request, String param )
    {
        String parameter = request.getParameter( param );
        return !( parameter == null || "".equals( parameter ) );
    }

    // Check the param if it's not present return the default

    private String getParam( HttpServletRequest request, String param, String def )
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

}
