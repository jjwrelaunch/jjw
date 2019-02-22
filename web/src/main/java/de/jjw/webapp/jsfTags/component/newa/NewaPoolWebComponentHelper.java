/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaPoolWebComponentHelper.java
 * Created : 16 Jun 2010
 * Last Modified: Wed, 16 Jun 2010 13:19:36
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

package de.jjw.webapp.jsfTags.component.newa;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.http.HttpServletRequest;

import de.jjw.model.newa.NewaDoublePoolItem;
import de.jjw.model.newa.NewaFight;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.taglib.HtmlConstants;

public class NewaPoolWebComponentHelper
    implements HtmlConstants, IGlobalWebConstants
{

    /**
     * writes the text or &nbsp; if test is null or ""
     * 
     * @param out
     * @param text
     * @throws IOException
     */
    public static void writeText( ResponseWriter out, String text )
        throws IOException
    {
        if ( TypeUtil.isEmptyOrDefault( text ) )
        {
            out.write( NBSP );
        }
        else
        {
            out.writeText( text, null );
        }
    }

    public static void doPooltable( UIComponent component, FacesContext context, ResponseWriter out, String tableWidht,
                                    String tableBorder, ResourceBundle rb, List<NewaDoublePoolItem> fighterList )
        throws IOException
    {
        out.startElement( JSF_TABLE, component );
        out.writeAttribute( ATTR_CLASS, FANCY_TABLE, null );
        out.writeAttribute( ATTR_BORDER, TABLE_BORDER, null );
        out.writeAttribute( ATTR_CELLPADDING, TABLE_CELLPADDING, null );
        out.writeAttribute( ATTR_CELLSPACING, TABLE_CELLSPACING, null );
        out.writeAttribute( ATTR_WIDTH, TABLE_WIDTH, null );

        doPooltableHeadRow( component, context, out, rb );
        doPooltableRow( component, context, out, fighterList );
        out.endElement( JSF_TABLE );
    }

    public static void doPooltableRow( UIComponent component, FacesContext context, ResponseWriter out,
                                       List<NewaDoublePoolItem> fighterList )
        throws IOException
    {
        NewaDoublePoolItem item;

        for ( int i = 1; i < 6; i++ )
        {
            if ( fighterList.size() >= i )
            {
                item = fighterList.get( i - 1 );
                out.startElement( JSF_TR, component );
                out.writeAttribute( ATTR_CLASS, i % 2 == 0 ? "odd" : "even", null );
                doTableCell( component, out, "15%", VAL_CENTER, false, item.getFighter().getName() );
                doTableCell( component, out, "15%", VAL_CENTER, false, item.getFighter().getFirstname() );
                doTableCell( component, out, "15%", VAL_CENTER, false, item.getFighter().getTeam().getTeamName() );
                doTableCell( component, out, "3%", VAL_CENTER, false, String.valueOf( i ) );
                if ( i == 1 )
                {
                    doTableCell( component, out, "6%", VAL_CENTER, false, 2, null, "invalid" );
                }
                doTableCell( component, out, "2%", VAL_CENTER, false,
                             TypeUtil.toString( item.getResult().getResultFight1() ) );
                doTableCell( component, out, "4%", VAL_CENTER, false,
                             TypeUtil.toString( item.getResult().getUbFight1() ) );
                if ( i == 2 )
                {
                    doTableCell( component, out, "6%", VAL_CENTER, false, 2, null, "invalid" );
                }
                doTableCell( component, out, "2%", VAL_CENTER, false,
                             TypeUtil.toString( item.getResult().getResultFight2() ) );
                doTableCell( component, out, "4%", VAL_CENTER, false,
                             TypeUtil.toString( item.getResult().getUbFight2() ) );
                if ( i == 3 )
                {
                    doTableCell( component, out, "6%", VAL_CENTER, false, 2, null, "invalid" );
                }
                doTableCell( component, out, "2%", VAL_CENTER, false,
                             TypeUtil.toString( item.getResult().getResultFight3() ) );
                doTableCell( component, out, "4%", VAL_CENTER, false,
                             TypeUtil.toString( item.getResult().getUbFight3() ) );
                if ( i == 4 )
                {
                    doTableCell( component, out, "6%", VAL_CENTER, false, 2, null, "invalid" );
                }
                doTableCell( component, out, "2%", VAL_CENTER, false,
                             TypeUtil.toString( item.getResult().getResultFight4() ) );
                doTableCell( component, out, "4%", VAL_CENTER, false,
                             TypeUtil.toString( item.getResult().getUbFight4() ) );
                if ( i == 5 )
                {
                    doTableCell( component, out, "6%", VAL_CENTER, false, 2, null, "invalid" );
                }
                doTableCell( component, out, "5%", VAL_CENTER, false,
                             TypeUtil.toString( item.getResult().getWinCount() ) );
                doTableCell( component, out, "6%", VAL_CENTER, false, TypeUtil.toString( item.getResult().getUBCount() ) );
                doTableCell( component,out, "6%", VAL_CENTER, false, TypeUtil.toString( item.getResult().getUBAdvantageCount()) );
                doTableCell( component, out, "9%", VAL_CENTER, false, TypeUtil.toString( item.getPoolPlace() ) );
                out.endElement( JSF_TR );
            }
            else
            {
                doEmptyPooltableRow( component, out, i );
            }
        }
    }

    /**
     * @param component
     * @param context
     * @param out
     * @param tableWidth
     * @param tableBorder
     * @param rb
     * @param fightListMap
     * @param editAccess
     * @param linkToFight
     * @param fightNumbering For Pool A use 1 for Pool B use 11
     * @throws IOException
     */
    public static void doFighttable( UIComponent component, FacesContext context, ResponseWriter out,
                                     String tableWidth, String tableBorder, ResourceBundle rb,
                                     Map<Integer, NewaFight> fightListMap, boolean editAccess, String linkToFight,
                                     int fightNumbering )
        throws IOException
    {
        int i = fightNumbering;
        out.startElement( JSF_TABLE, component );
        out.writeAttribute( ATTR_CLASS, FANCY_TABLE, null );
        out.writeAttribute( ATTR_BORDER, TABLE_BORDER, null );
        out.writeAttribute( ATTR_CELLPADDING, TABLE_CELLPADDING, null );
        out.writeAttribute( ATTR_CELLSPACING, TABLE_CELLSPACING, null );
        out.writeAttribute( ATTR_WIDTH, TABLE_WIDTH, null );

        out.startElement( JSF_THEAD, component );
        out.startElement( JSF_TR, component );
        doTableHeadCell( component, out, "42", null, false, 2, null );
        doTableHeadCell( component, out, "139", VAL_CENTER, true, rb.getString( "fightTable.fighterRed" ) );
        doTableHeadCell( component, out, "139", VAL_CENTER, true, rb.getString( "fightTable.fighterBlue" ) );
        doTableHeadCell( component, out, "42", null, false, 2, null );

        // row span =6----------
        out.startElement( JSF_TH, component );
        // out.writeAttribute( ATTR_ROWSPAN, 6, null );
        out.writeAttribute( ATTR_STYLE,
                            "background:white;border-left:1px solid bbb;border-right:1px solid bbb;border-bottom:0",
                            null );
        out.writeAttribute( ATTR_WIDTH, 48, "SPACER" );
        writeText( out, null );
        out.endElement( JSF_TH );
        // ---------------------

        doTableHeadCell( component, out, "42", null, false, 2, null );
        doTableHeadCell( component, out, "139", VAL_CENTER, true, rb.getString( "fightTable.fighterRed" ) );
        doTableHeadCell( component, out, "139", VAL_CENTER, true, rb.getString( "fightTable.fighterBlue" ) );
        doTableHeadCell( component, out, "42", null, false, 2, null );
        out.endElement( JSF_TR );
        out.endElement( JSF_THEAD );

        i++;
        out.startElement( JSF_TR, component );
        out.writeAttribute( ATTR_CLASS, i % 2 == 0 ? "odd" : "even", null );
        doTableCell( component, out, "18", VAL_CENTER, true, TypeUtil.toString( i - 1 ) );
        doTableCellsLinkFight( component, out, rb.getString( "fightTable.1-2" ), fightListMap.get( 1 ), context,
                               editAccess, linkToFight );

        doTableCell( component, out, "48", null, false, "SPACER" );

        doTableCell( component, out, "18", VAL_CENTER, true, TypeUtil.toString( i + 4 ) );
        doTableCellsLinkFight( component, out, rb.getString( "fightTable.1-3" ), fightListMap.get( 6 ), context,
                               editAccess, linkToFight );
        out.endElement( JSF_TR );

        i++;
        out.startElement( JSF_TR, component );
        out.writeAttribute( ATTR_CLASS, i % 2 == 0 ? "odd" : "even", null );
        doTableCell( component, out, "18", VAL_CENTER, true, TypeUtil.toString( i - 1 ) );
        doTableCellsLinkFight( component, out, rb.getString( "fightTable.3-4" ), fightListMap.get( 2 ), context,
                               editAccess, linkToFight );

        doTableCell( component, out, "48", null, false, "SPACER" );

        doTableCell( component, out, "18", VAL_CENTER, true, TypeUtil.toString( i + 4 ) );
        doTableCellsLinkFight( component, out, rb.getString( "fightTable.2-4" ), fightListMap.get( 7 ), context,
                               editAccess, linkToFight );
        out.endElement( JSF_TR );

        i++;
        out.startElement( JSF_TR, component );
        out.writeAttribute( ATTR_CLASS, i % 2 == 0 ? "odd" : "even", null );
        doTableCell( component, out, "18", VAL_CENTER, true, TypeUtil.toString( i - 1 ) );
        doTableCellsLinkFight( component, out, rb.getString( "fightTable.1-5" ), fightListMap.get( 3 ), context,
                               editAccess, linkToFight );

        doTableCell( component, out, "48", null, false, "SPACER" );

        doTableCell( component, out, "18", VAL_CENTER, true, TypeUtil.toString( i + 4 ) );
        doTableCellsLinkFight( component, out, rb.getString( "fightTable.3-5" ), fightListMap.get( 8 ), context,
                               editAccess, linkToFight );
        out.endElement( JSF_TR );

        i++;
        out.startElement( JSF_TR, component );
        out.writeAttribute( ATTR_CLASS, i % 2 == 0 ? "odd" : "even", null );
        doTableCell( component, out, "18", VAL_CENTER, true, TypeUtil.toString( i - 1 ) );
        doTableCellsLinkFight( component, out, rb.getString( "fightTable.2-3" ), fightListMap.get( 4 ), context,
                               editAccess, linkToFight );

        doTableCell( component, out, "48", null, false, "SPACER" );

        doTableCell( component, out, "18", VAL_CENTER, true, TypeUtil.toString( i + 4 ) );
        doTableCellsLinkFight( component, out, rb.getString( "fightTable.1-4" ), fightListMap.get( 9 ), context,
                               editAccess, linkToFight );
        out.endElement( JSF_TR );

        i++;
        out.startElement( JSF_TR, component );
        out.writeAttribute( ATTR_CLASS, i % 2 == 0 ? "odd" : "even", null );
        doTableCell( component, out, "18", VAL_CENTER, true, TypeUtil.toString( i - 1 ) );
        doTableCellsLinkFight( component, out, rb.getString( "fightTable.4-5" ), fightListMap.get( 5 ), context,
                               editAccess, linkToFight );

        doTableCell( component, out, "48", null, false, "SPACER" );

        doTableCell( component, out, "18", VAL_CENTER, true, TypeUtil.toString( i + 4 ) );
        doTableCellsLinkFight( component, out, rb.getString( "fightTable.2-5" ), fightListMap.get( 10 ), context,
                               editAccess, linkToFight );
        out.endElement( JSF_TR );

        out.endElement( JSF_TABLE );
    }

    public static void doTableCellsLinkFight( UIComponent component, ResponseWriter out, String text, NewaFight fight,
                                              FacesContext context, boolean editAccess, String linkToFight )
        throws IOException
    {
        if ( editAccess && fight != null )
        {
            out.startElement( JSF_TD, component );
            out.writeAttribute( ATTR_ALIGN, VAL_CENTER, null );

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
            sb2.append( "?system=N&id=" );
            sb2.append( fight.getId() );
            sb2.append( "');" );

            out.writeAttribute( ATTR_ONMOUSEDOWN, sb2.toString(), null );
            out.write( text );
            out.endElement( JSF_A );
            out.endElement( JSF_TD );

        }
        else
        {
            // pair
            doTableCell( component, out, TypeUtil.toString( 24 ), VAL_CENTER, false, text );
        }

        if ( fight == null )
        {
            // fighter red and blue
            doTableCell( component, out, TypeUtil.toString( 139 ), null, false, null );
            doTableCell( component, out, TypeUtil.toString( 139 ), null, false, null );
            // fighter points red and blue
            doTableCell( component, out, TypeUtil.toString( 24 ), null, false, null );
            doTableCell( component, out, TypeUtil.toString( 24 ), null, false, null );
        }
        else
        {
            // fighter red and blue
            if ( fight.getWinnerId() != null && fight.getWinnerId().equals( fight.getFighterRed().getId() ) )
            {
                doTableCell( component, out, TypeUtil.toString( 139 ), VAL_CENTER, true,
                             fight.getFighterRed().getName() );
            }
            else
            {
                doTableCell( component, out, TypeUtil.toString( 139 ), VAL_CENTER, false,
                             fight.getFighterRed().getName() );
            }

            if ( fight.getWinnerId() != null && fight.getWinnerId().equals( fight.getFighterBlue().getId() ) )
            {
                doTableCell( component, out, TypeUtil.toString( 139 ), VAL_CENTER, true,
                             fight.getFighterBlue().getName() );
            }
            else
            {
                doTableCell( component, out, TypeUtil.toString( 139 ), VAL_CENTER, false,
                             fight.getFighterBlue().getName() );
            }

            // fighter points red and blue
            doTableCell( component, out, TypeUtil.toString( 24 ), VAL_CENTER, false,
                         TypeUtil.toString( fight.getPointsRed() ) );
            doTableCell( component, out, TypeUtil.toString( 24 ), VAL_CENTER, false,
                         TypeUtil.toString( fight.getPointsBlue() ) );
        }
    }

    public static void doEmptyPooltableRow( UIComponent component, ResponseWriter out, int pooltableRow )
        throws IOException
    {
        out.startElement( JSF_TR, component );
        out.writeAttribute( ATTR_CLASS, pooltableRow % 2 == 0 ? "odd" : "even", null );
        doTableCell( component, out, "15%", VAL_CENTER, false, "" );
        doTableCell( component, out, "15%", VAL_CENTER, false, "" );
        doTableCell( component, out, "15%", VAL_CENTER, false, "" );
        doTableCell( component, out, "3%", VAL_CENTER, false, String.valueOf( pooltableRow ) );
        if ( pooltableRow == 1 )
        {
            doTableCell( component, out, "6%", VAL_CENTER, false, 2, "", "invalid" );
        }
        doTableCell( component, out, "2%", VAL_CENTER, false, "" );
        doTableCell( component, out, "4%", VAL_CENTER, false, "" );
        if ( pooltableRow == 2 )
        {
            doTableCell( component, out, "6%", VAL_CENTER, false, 2, "", "invalid" );
        }
        doTableCell( component, out, "2%", VAL_CENTER, false, "" );
        doTableCell( component, out, "4%", VAL_CENTER, false, "" );
        if ( pooltableRow == 3 )
        {
            doTableCell( component, out, "6%", VAL_CENTER, false, 2, "", "invalid" );
        }
        doTableCell( component, out, "2%", VAL_CENTER, false, "" );
        doTableCell( component, out, "4%", VAL_CENTER, false, "" );
        if ( pooltableRow == 4 )
        {
            doTableCell( component, out, "6%", VAL_CENTER, false, 2, "", "invalid" );
        }
        doTableCell( component, out, "2%", VAL_CENTER, false, "" );
        doTableCell( component, out, "4%", VAL_CENTER, false, "" );
        if ( pooltableRow == 5 )
        {
            doTableCell( component, out, "6%", VAL_CENTER, false, 2, "", "invalid" );
        }
        doTableCell( component, out, "5%", VAL_CENTER, false, "" );
        doTableCell( component, out, "6%", VAL_CENTER, false, "" );
        doTableCell( component, out, "6%", VAL_CENTER, false, "" );
        doTableCell( component, out, "9%", VAL_CENTER, false, "" );
        out.endElement( JSF_TR );
    }

    public static void doPooltableHeadRow( UIComponent component, FacesContext context, ResponseWriter out,
                                           ResourceBundle rb )
        throws IOException
    {
        out.startElement( JSF_THEAD, component );
        out.startElement( JSF_TR, component );

        out.startElement( JSF_TH, component );
        out.writeAttribute( ATTR_WIDTH, "100", null );
        writeText( out, rb.getString( "pool.name" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, component );
        out.writeAttribute( ATTR_WIDTH, "100", null );
        writeText( out, rb.getString( "pool.firstname" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, component );
        out.writeAttribute( ATTR_WIDTH, "100", null );
        writeText( out, rb.getString( "pool.team" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, component );
        out.writeAttribute( ATTR_WIDTH, "30", null );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, component );
        out.writeAttribute( ATTR_WIDTH, "50", null );
        out.writeAttribute( ATTR_COLSPAN, "2", null );
        writeText( out, rb.getString( "pool.1" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, component );
        out.writeAttribute( ATTR_WIDTH, "50", null );
        out.writeAttribute( ATTR_COLSPAN, "2", null );
        writeText( out, rb.getString( "pool.2" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, component );
        out.writeAttribute( ATTR_WIDTH, "50", null );
        out.writeAttribute( ATTR_COLSPAN, "2", null );
        writeText( out, rb.getString( "pool.3" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, component );
        out.writeAttribute( ATTR_WIDTH, "50", null );
        out.writeAttribute( ATTR_COLSPAN, "2", null );
        writeText( out, rb.getString( "pool.4" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, component );
        out.writeAttribute( ATTR_WIDTH, "50", null );
        out.writeAttribute( ATTR_COLSPAN, "2", null );
        writeText( out, rb.getString( "pool.5" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, component );
        out.writeAttribute( ATTR_WIDTH, "80", null );
        writeText( out, rb.getString( "pool.wins" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, component );
        out.writeAttribute( ATTR_WIDTH, "80", null );
        writeText( out, rb.getString( "pool.points" ) );
        out.endElement( JSF_TH );
        
        out.startElement( JSF_TH, component );
        out.writeAttribute( ATTR_WIDTH, "80", null );
        writeText( out, rb.getString( "pool.points.advantage" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, component );
        out.writeAttribute( ATTR_WIDTH, "80", null );
        writeText( out, rb.getString( "pool.place" ) );
        out.endElement( JSF_TH );

        out.endElement( JSF_TR );
        out.endElement( JSF_THEAD );
    }

    public static void doTableCell( UIComponent component, ResponseWriter out, String width, String align,
                                    boolean bold, int colspan, String text, String styleClass )
        throws IOException
    {
        out.startElement( JSF_TD, component );
        if ( colspan > 0 )
        {
            out.writeAttribute( ATTR_COLSPAN, colspan, null );
        }
        if ( styleClass != null )
        {
            out.writeAttribute( ATTR_CLASS, styleClass, null );
        }
        if ( width != null )
        {
            out.writeAttribute( ATTR_WIDTH, width, null );
        }
        if ( align != null && !TypeUtil.isEmptyOrDefault( text ) )
        {
            out.writeAttribute( ATTR_ALIGN, align, null );
        }
        if ( "SPACER".equals( text ) )
        {
            out.writeAttribute( ATTR_STYLE, "border: 0;background:white;", null );
        }
        else
        {
            out.writeAttribute( ATTR_STYLE, "border-left: 1px solid #BBBBBB;", null );
        }
        if ( bold )
        {
            out.startElement( JSF_BOLD, component );
        }
        if ( !"SPACER".equals( text ) )
        {
            writeText( out, text );
        }
        if ( bold )
        {
            out.endElement( JSF_BOLD );
        }
        out.endElement( JSF_TD );
    }

    public static void doTableCell( UIComponent component, ResponseWriter out, String width, String align,
                                    boolean bold, String text )
        throws IOException
    {
        doTableCell( component, out, width, align, bold, 0, text, null );
    }




    private static void doTableHeadCell( UIComponent component, ResponseWriter out, String width, String align,
                                         boolean bold, int colspan, String text )
        throws IOException
    {
        out.startElement( JSF_TH, component );
        if ( colspan > 0 )
        {
            out.writeAttribute( ATTR_COLSPAN, colspan, null );
        }

        // if ( width != null )
        // {
        // out.writeAttribute( ATTR_WIDTH, width, null );
        // }
        if ( align != null && !TypeUtil.isEmptyOrDefault( text ) )
        {
            out.writeAttribute( ATTR_ALIGN, align, null );
        }
        if ( bold )
        {
            out.startElement( JSF_BOLD, component );
        }
        writeText( out, text );
        if ( bold )
        {
            out.endElement( JSF_BOLD );
        }
        out.endElement( JSF_TH );
    }

    private static void doTableHeadCell( UIComponent component, ResponseWriter out, String width, String align,
                                         boolean bold, String text )
        throws IOException
    {
        doTableHeadCell( component, out, width, align, bold, 0, text );
    }



}
