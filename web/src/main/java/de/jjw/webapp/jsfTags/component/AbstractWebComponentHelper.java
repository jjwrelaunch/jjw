/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : Ko16WebComponentHelper.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:47
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

package de.jjw.webapp.jsfTags.component;

import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.http.HttpServletRequest;

import de.jjw.model.fighting.Fight;
import de.jjw.model.fighting.Fighter;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.taglib.HtmlConstants;

public abstract class AbstractWebComponentHelper
    implements HtmlConstants, IGlobalWebConstants
{
    public static final String CELL_HIGHLIGHT = "font-weight:bold;";// background:#88ffaa;

    public static final String BORDER_BLACK_2 = "2px solid black;";

    private static final String HIGHLIGHT_FIGHT_STRING = "background-color:Gold;font-weight:bold;";

    /**
     * writes the text or &nbsp; if test is ""
     * 
     * @param out
     * @param text
     * @throws IOException
     */
    public static void writeText( ResponseWriter out, String text )
        throws IOException
    {
        if ( TypeUtil.isDefault( text ) )
        {
            out.write( NBSP );
        }
        else
        {
            if ( TypeUtil.INT_MIN_STRING.equals( text ) )
            {
                out.write( NBSP );
            }
            else
            {
                if ( !TypeUtil.isEmpty( text ) )
                {
                    out.writeText( text, null );
                }
            }
        }
    }

    /**
     * @param component
     * @param out
     * @param width
     * @param valign
     * @param firstname
     * @param fight
     * @param redOrBlue 1:red 2:blue
     * @throws IOException
     */
    protected static void doTableCell( UIComponent component, ResponseWriter out, String width, String valign,
                                       boolean firstname, Fight fight, int redOrBlue, int style )
        throws IOException
    {
        doTableCell( component, out, width, valign, firstname, fight, redOrBlue, 0, style );
    }

    protected static void doTableCell( UIComponent component, ResponseWriter out, String width, String valign,
                                       boolean firstname, Fight fight, int redOrBlue, int colspan, int style )
        throws IOException
    {
        Fighter fighter;
        if ( redOrBlue == FighterRed )
        {
            fighter = fight.getFighterRed();
        }
        else if ( redOrBlue == FighterBlue )
        {
            fighter = fight.getFighterBlue();
        }
        else
        {
            fighter = null;
        }

        int cellStyle = style;
        if ( fighter != null && fighter.getId() == fight.getWinnerId() )
        {
            cellStyle |= HIGHLIGHT_WINNER;
        }
        else
        {
            cellStyle |= PLAIN;
        }

        if ( fighter == null )
        {
            doTableCell( component, out, width, valign, cellStyle, colspan, TypeUtil.STRING_DEFAULT );
        }
        else
        {
            if ( firstname )
            {
                doTableCell( component, out, width, valign, cellStyle, colspan,
                             fighter.getFirstname() + " " + fighter.getName() );
            }
            else
            {
                doTableCell( component, out, width, valign, cellStyle, colspan, fighter.getName() );
            }
        }
    }
    
    /**
     * this method should only called for the 3rd place fights in a tree.
     * Because of the repechage change the acces for this fight is only
     * allowed when all 4 participant of the 2 fights are set
     * 
     * @param component
     * @param out
     * @param width
     * @param text
     * @param fight
     * @param editAccess
     * @param linkToFight
     * @param rowspan
     * @param style
     * @param context
     * @param fightListA
     * @param fightListB
     * @throws IOException
     */
    protected static void doTableCellWithRowSpanAndLinkFor3rdPlace( UIComponent component, ResponseWriter out, String width,
                                                         String text, Fight fight, boolean editAccess,
                                                         String linkToFight, int rowspan, int style,
                                                         FacesContext context, Map<Integer, Fight> fightListA , Map<Integer, Fight> fightListB  )
        throws IOException
    {
       if(    TypeUtil.LONG_EMPTY == fightListA.get(0).getFighterIdRed()
            || TypeUtil.LONG_EMPTY == fightListA.get(0).getFighterIdBlue()
            || TypeUtil.LONG_EMPTY == fightListB.get(0).getFighterIdRed()
            || TypeUtil.LONG_EMPTY == fightListB.get(0).getFighterIdBlue()  )
           //no access
            doTableCellWithRowSpanAndLink( component, out, width, text, fight, false, linkToFight, rowspan, style, context );
        else
            doTableCellWithRowSpanAndLink( component, out, width, text, fight, editAccess, linkToFight, rowspan, style, context );
    }

    protected static void doTableCellWithRowSpanAndLink( UIComponent component, ResponseWriter out, String width,
                                                         String text, Fight fight, boolean editAccess,
                                                         String linkToFight, int rowspan, int style,
                                                         FacesContext context )
        throws IOException
    {

        out.startElement( JSF_TD, component );
        if ( width != null )
        {
            out.writeAttribute( ATTR_WIDTH, width, null );
        }
        out.writeAttribute( ATTR_ALIGN, VAL_CENTER, null );
        if ( rowspan > 0 )
        {
            out.writeAttribute( ATTR_ROWSPAN, rowspan, null );
        }
        if ( editAccess && fight != null && fight.getFighterIdRed() > 0 && fight.getFighterIdBlue() > 0 )
        {
            writeStyleAttributes( out, style | BORDER_RIGHT | HIGHLIGHT_FIGHT );
            out.startElement( JSF_A, component );
            out.writeAttribute( ATTR_HREF, linkToFight + fight.getId().longValue(), null );
            out.writeAttribute( ATTR_ONCONTEXTMENUE, "return false;", null );

            String contextPath = ( (HttpServletRequest) context.getExternalContext().getRequest() ).getContextPath();
            StringBuffer sb2 = new StringBuffer();
            sb2.append( HANDLE_LINK_CLICK );
            sb2.append( "(event,'" );
            sb2.append( linkToFight + fight.getId().longValue() );
            sb2.append( "','" );
            sb2.append( contextPath );
            sb2.append( PREVIEW_URL );
            sb2.append( "?system=F&id=" );
            sb2.append( fight.getId() );
            sb2.append( "');" );

            out.writeAttribute( ATTR_ONMOUSEDOWN, sb2.toString(), null );
            out.write( text );
            out.endElement( JSF_A );
        }
        else
        {
            writeStyleAttributes( out, style | BORDER_RIGHT );
            out.write( text );
        }
        out.endElement( JSF_TD );
    }

    protected static void doTableCell( UIComponent component, ResponseWriter out, String width, String align,
                                       int style, int colspan, String text )
        throws IOException
    {
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_BORDERCOLOR, COLOR_WHITE, null );
        if ( colspan > 0 )
        {
            out.writeAttribute( ATTR_COLSPAN, colspan, null );
        }
        if ( width != null )
        {
            out.writeAttribute( ATTR_WIDTH, width, null );
        }
        if ( align != null && !TypeUtil.isEmptyOrDefault( text ) )
        {
            out.writeAttribute( ATTR_ALIGN, align, null );
        }
        if ( style > 0 )
        {
            writeStyleAttributes( out, style );
        }
        writeText( out, text );
        out.endElement( JSF_TD );
    }

    private static void writeStyleAttributes( ResponseWriter out, int style )
        throws IOException
    {
        String styleString = "";
        if ( ( style & BORDER_BOTTOM ) == BORDER_BOTTOM )
        {
            styleString = "border-bottom: " + BORDER_BLACK_2;
        }
        if ( ( style & BORDER_LEFT ) == BORDER_LEFT )
        {
            styleString += "border-left: " + BORDER_BLACK_2;
        }
        if ( ( style & BORDER_RIGHT ) == BORDER_RIGHT )
        {
            styleString += "border-right: " + BORDER_BLACK_2;
        }
        if ( ( style & BORDER_TOP ) == BORDER_TOP )
        {
            styleString += "border-top: " + BORDER_BLACK_2;
        }
        if ( ( style & HIGHLIGHT_WINNER ) == HIGHLIGHT_WINNER )
        {
            styleString += CELL_HIGHLIGHT;
        }
        if ( ( style & HIGHLIGHT_FIGHT ) == HIGHLIGHT_FIGHT )
        {
            styleString += HIGHLIGHT_FIGHT_STRING;
        }

        if ( styleString.length() > 0 )
        {
            out.writeAttribute( ATTR_STYLE, styleString, null );
        }
        // out.startElement( JSF_BOLD_MASK, component );
    }

    protected static void doTableCellWithRowSpan( UIComponent component, ResponseWriter out, String width,
                                                  String align, int style, int colspan, String text, int rowspan )
        throws IOException
    {
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_BORDERCOLOR, COLOR_WHITE, null );
        if ( colspan > 0 )
        {
            out.writeAttribute( ATTR_COLSPAN, colspan, null );
        }
        if ( rowspan > 0 )
        {
            out.writeAttribute( ATTR_ROWSPAN, rowspan, null );
        }
        // if ( width != null )
        // {
        // out.writeAttribute( ATTR_WIDTH, width, null );
        // }
        if ( align != null && !TypeUtil.isEmptyOrDefault( text ) )
        {
            out.writeAttribute( ATTR_ALIGN, align, null );
        }
        if ( style > 0 )
        {
            writeStyleAttributes( out, style );
        }
        writeText( out, text );
        out.endElement( JSF_TD );
    }

    protected static void doTableCell( UIComponent component, ResponseWriter out, String width, String align,
                                       int style, String text )
        throws IOException
    {
        doTableCell( component, out, width, align, style, 0, text );
    }
}
