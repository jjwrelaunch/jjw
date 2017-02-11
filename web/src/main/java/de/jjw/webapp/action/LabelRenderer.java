/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : LabelRenderer.java
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

package de.jjw.webapp.action;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * Custom LabelRenderer component that adds asterisks for required
 * fields and a colon.  Based off of an example by David Geary
 * on the MyFaces Mailing list.
 *
 * @author Matt Raible
 */
public class LabelRenderer
    extends Renderer
{
    protected final Logger log = Logger.getRootLogger();

    public boolean getRendersChildren()
    {
        return false;
    }

    public void encodeBegin( FacesContext context, UIComponent component )
        throws java.io.IOException
    {
        ResponseWriter writer = context.getResponseWriter();

        Map attrs = component.getAttributes();
        String id = (String) attrs.get( "for" );

        UIInput input = (UIInput) component.findComponent( id );

        if ( ( input != null ) && input.isRequired() )
        {
            writer.write( "*&nbsp;" );
        }

        writer.startElement( "label", component );

        String styleClass = (String) component.getAttributes().get( "styleClass" );

        boolean hasErrors = hasMessages( context, input );

        if ( styleClass != null )
        {
            writer.writeAttribute( "class", styleClass, null );
        }
        else if ( ( input != null ) && input.isRequired() )
        {
            writer.writeAttribute( "class", "required", null );
        }

        String renderedId = ( input != null ) ? input.getClientId( context ) : component.getClientId( context );
        writer.writeAttribute( "for", renderedId, null );
        writer.write( attrs.get( "value" ).toString() );
    }

    public void encodeEnd( FacesContext context, UIComponent component )
        throws java.io.IOException
    {
        ResponseWriter writer = context.getResponseWriter();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        String marker = ( request.getLocale().equals( Locale.FRENCH ) ) ? " :" : ":";

        // Allow there to be no value - useful for creating empty cells
        // in panelGrid
        Map attrs = component.getAttributes();

        if ( "".equals( attrs.get( "value" ).toString() ) )
        {
            marker = "";
        }

        writer.write( marker );
        writer.endElement( "label" );
    }

    private boolean hasMessages( FacesContext context, UIComponent component )
    {
        Iterator it = context.getClientIdsWithMessages();
        boolean found = false;

        while ( it.hasNext() )
        {
            String id = (String) it.next();

            if ( ( component != null ) && id.equals( component.getClientId( context ) ) )
            {
                found = true;
            }
        }

        return found;
    }
}
