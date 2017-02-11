/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightingSimplePoolWebRenderer.java
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

package de.jjw.webapp.jsfTags.renderer;


import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.shared_impl.renderkit.html.HtmlRenderer;

import de.jjw.webapp.taglib.HtmlConstants;

public class FightingSimplePoolWebRenderer
    extends HtmlRenderer
    implements HtmlConstants
{
    /**
     * atrributes
     */


    private static String RESOURCEBUNDLE = "resourcebundle";

    private static String TABLE_WIDTH = "850";

    private static String TABLE_BORDER = "4";

    private ResourceBundle rb = null;

    @Override
    public void encodeEnd( FacesContext context, UIComponent component )
        throws IOException
    {

        Map attrs = component.getAttributes();
//	FightingSimplePoolClass fightingclass = (FightingSimplePoolClass) attrs.get(FIGHTINGCLASS);
        rb = ResourceBundle.getBundle( (String) attrs.get( RESOURCEBUNDLE ) );

        ResponseWriter out = context.getResponseWriter();

        doPooltable( context, component, out );
        doFighttable( context, component, out );
    }

    private void doPooltable( FacesContext context, UIComponent component, ResponseWriter out )
        throws IOException
    {
        out.startElement( JSF_TABLE, component );
        out.writeAttribute( ATTR_WIDTH, TABLE_WIDTH, null );
        out.writeAttribute( ATTR_BORDER, TABLE_BORDER, null );
        out.startElement( JSF_TR, component );
        doPooltableHeadRow( context, component, out );
        out.endElement( JSF_TR );

        out.endElement( JSF_TABLE );
    }

    private void doPooltableHeadRow( FacesContext context, UIComponent component, ResponseWriter out )
        throws IOException
    {
        doTableCell( component, out, "15%", VAL_CENTER, true, "pool.name" );
        doTableCell( component, out, "15%", VAL_CENTER, true, "pool.firstname" );
        doTableCell( component, out, "15%", VAL_CENTER, true, "pool.team" );
        doTableCell( component, out, "13%", VAL_CENTER, true, null );
        doTableCell( component, out, "15%", VAL_CENTER, true, "pool.1" );
        doTableCell( component, out, "15%", VAL_CENTER, true, "pool.2" );
        doTableCell( component, out, "15%", VAL_CENTER, true, "pool.3" );
        doTableCell( component, out, "15%", VAL_CENTER, true, "pool.4" );
        doTableCell( component, out, "15%", VAL_CENTER, true, "pool.5" );
        doTableCell( component, out, "5%", VAL_CENTER, true, "pool.wins" );
        doTableCell( component, out, "6%", VAL_CENTER, true, "pool.points" );
        doTableCell( component, out, "9%", VAL_CENTER, true, "pool.place" );

    }

    private void doTableCell( UIComponent component, ResponseWriter out, String widht, String align, boolean bold,
                              String text )
        throws IOException
    {
        out.startElement( JSF_TD, component );
        if ( widht != null )
        {
            out.writeAttribute( ATTR_WIDTH, widht, null );
        }
        if ( align != null )
        {
            out.writeAttribute( ATTR_ALIGN, align, null );
        }
        if ( bold )
        {
            out.startElement( JSF_BOLD, component );
        }
        if ( text == null )
        {
            out.writeText( NBSP, null );
        }
        else
        {
            out.writeText( rb.getString( text ), null );
        }
        if ( bold )
        {
            out.endElement( JSF_BOLD );
        }
        out.endElement( JSF_TD );
    }

    private void doFighttable( FacesContext context, UIComponent component, ResponseWriter out )
    {
        // TODO Auto-generated method stub

    }
}
