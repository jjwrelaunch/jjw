/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoKo16WebComponentHelper.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:43
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

import de.jjw.model.duo.DuoFight;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.taglib.HtmlConstants;

public class DuoKo16WebComponentHelper
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
            if ( TypeUtil.DOUBLE_MIN_STRING.equals( text ) )
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

    public static void doMainround( UIComponent component, FacesContext context, ResponseWriter out, String style,
                                    ResourceBundle rb, Map<Integer, DuoFight> fightListA,
                                    Map<Integer, DuoFight> fightListB, DuoFight finalFight, boolean editAccess,
                                    String linkToFight )
        throws IOException
    {
        out.startElement( JSF_TABLE, component );
        out.writeAttribute( ATTR_CLASS, style, null );
        out.writeAttribute( ATTR_BORDER, TABLE_BORDER, null );
        out.writeAttribute( ATTR_CELLPADDING, TABLE_CELLPADDING, null );
        out.writeAttribute( ATTR_CELLSPACING, TABLE_CELLSPACING, null );
        out.writeAttribute( ATTR_WIDTH, TABLE_WIDTH, null );

        writeMainRoundTableHeader( component, out, rb );

        // row 1
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.1" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( 3 ), FighterRed, BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( 3 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "1", fightListA.get( 3 ), editAccess, linkToFight, 3,
                                       BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _120, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 2
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 1 ), FighterRed, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 1 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _120, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 3
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.9" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( 3 ), FighterBlue, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 3 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "9", fightListA.get( 1 ), editAccess, linkToFight, 3,
                                       PLAIN, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _120, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 4
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 0 ), FighterRed, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 0 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _120, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 5
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.5" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( 4 ), FighterRed, BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( 4 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "2", fightListA.get( 4 ), editAccess, linkToFight, 3,
                                       BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "21", fightListA.get( 0 ), editAccess, linkToFight, 7,
                                       PLAIN, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _120, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 6
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 1 ), FighterBlue, BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP, String.valueOf( fightListA.get( 1 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _120, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 7
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM, rb.getString( "ko.13" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( 4 ), FighterBlue, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 4 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _120, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 8
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, finalFight, FighterRed, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM, String.valueOf( finalFight.getPointsRed() ) );
        doTableCell( component, out, _120, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 9
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.3" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( 5 ), FighterRed, BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( 5 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "3", fightListA.get( 5 ), editAccess, linkToFight, 3,
                                       BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "27", finalFight, editAccess, linkToFight, 15, PLAIN,
                                       context );
        doTableCell( component, out, _120, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 10
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 2 ), FighterRed, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 2 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _120, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 11
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.11" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( 5 ), FighterBlue, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 5 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "10", fightListA.get( 2 ), editAccess, linkToFight, 3,
                                       BORDER_RIGHT, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _120, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 12
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 0 ), FighterBlue, BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP, String.valueOf( fightListA.get( 0 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _120, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 13
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.7" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( 6 ), FighterRed, BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( 6 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "2", fightListA.get( 6 ), editAccess, linkToFight, 3,
                                       BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _120, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 14
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 2 ), FighterBlue, BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP, String.valueOf( fightListA.get( 2 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _120, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 15
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM, rb.getString( "ko.15" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( 6 ), FighterBlue, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 6 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _120, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // -----------------------------------------------------------------------------------------
        // middle
        // -----------------------------------------------------------------------------------------
        // row 16
        out.startElement( JSF_TR, component );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "9", null );
        out.writeAttribute( ATTR_BGCOLOR, "#e5e5e5", null );
        writeText( out, TypeUtil.STRING_DEFAULT );
        out.endElement( JSF_TD );
        if ( TypeUtil.LONG_EMPTY != finalFight.getWinnerId() )
        {
            if ( finalFight.getTeamIdRed() == finalFight.getWinnerId() )
            {
                doTableCell( component, out, _120, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER,
                             finalFight.getDuoTeamRed().getName() + " / " + finalFight.getDuoTeamRed().getName2() );
            }
            else
            {
                if ( finalFight.getTeamIdBlue() == finalFight.getWinnerId() )
                {
                    doTableCell( component, out, _120, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER,
                                 finalFight.getDuoTeamBlue().getName() + " / " + finalFight.getDuoTeamBlue().getName2() );
                }
                else
                {
                    doTableCell( component, out, _120, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER,
                                 TypeUtil.STRING_DEFAULT );
                }
            }
        }
        else
        {
            doTableCell( component, out, _120, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER, TypeUtil.STRING_DEFAULT );
        }
        out.endElement( JSF_TR );
        // -----------------------------------------------------------------------------------------
        // end middle
        // -----------------------------------------------------------------------------------------

        // row 17
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.2" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( 3 ), FighterRed, BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( 3 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "5", fightListB.get( 3 ), editAccess, linkToFight, 3,
                                       BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _120, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 18
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 1 ), FighterRed, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 1 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _120, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 19
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM, rb.getString( "ko.10" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( 3 ), FighterBlue, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 3 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "11", fightListB.get( 1 ), editAccess, linkToFight, 3,
                                       BORDER_RIGHT, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _120, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 20
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 0 ), FighterRed, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 0 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _120, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 21
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP, rb.getString( "ko.6" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( 4 ), FighterRed, BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP, String.valueOf( fightListB.get( 4 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "6", fightListB.get( 4 ), editAccess, linkToFight, 3,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "22", fightListB.get( 0 ), editAccess, linkToFight, 7,
                                       BORDER_RIGHT, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _120, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 22
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 1 ), FighterBlue, BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP, String.valueOf( fightListB.get( 1 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _120, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 23
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM, rb.getString( "ko.14" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( 4 ), FighterBlue, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 4 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _120, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 24
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, finalFight, FighterBlue, BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP,
                     String.valueOf( finalFight.getPointsBlue() ) );
        doTableCell( component, out, _120, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 25
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP, rb.getString( "ko.4" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( 5 ), FighterRed, BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP, String.valueOf( fightListB.get( 5 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "7", fightListB.get( 5 ), editAccess, linkToFight, 3,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _120, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 26
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 2 ), FighterRed, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 2 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _120, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 27
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM, rb.getString( "ko.12" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( 5 ), FighterBlue, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 5 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "12", fightListB.get( 2 ), editAccess, linkToFight, 3,
                                       BORDER_RIGHT, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _120, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 28
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 0 ), FighterBlue, BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP, String.valueOf( fightListB.get( 0 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _120, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 29
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP, rb.getString( "ko.8" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( 6 ), FighterRed, BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP, String.valueOf( fightListB.get( 6 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "8", fightListB.get( 6 ), editAccess, linkToFight, 3,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _120, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 30
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 2 ), FighterBlue, BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP, String.valueOf( fightListB.get( 2 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _120, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 31
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM, rb.getString( "ko.16" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( 6 ), FighterBlue, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 6 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _120, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.endElement( JSF_TABLE );
    }

    public static void doLooserRound( UIComponent component, FacesContext context, ResponseWriter out, String style,
                                      ResourceBundle rb, Map<Integer, DuoFight> fightListA,
                                      Map<Integer, DuoFight> fightListB, boolean editAccess, String linkToFight )
        throws IOException
    {
        out.startElement( JSF_TABLE, component );
        out.writeAttribute( ATTR_CLASS, style, null );
        out.writeAttribute( ATTR_BORDER, TABLE_BORDER, null );
        out.writeAttribute( ATTR_CELLPADDING, TABLE_CELLPADDING, null );
        out.writeAttribute( ATTR_CELLSPACING, TABLE_CELLSPACING, null );
        out.writeAttribute( ATTR_WIDTH, TABLE_WIDTH, null );

        // writeLooserRoundTableHeader( component, out, rb );

        // row 1
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 2 ), FighterRed, BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP, String.valueOf( fightListA.get( 2 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "17", fightListA.get( 2 ), editAccess, linkToFight, 3,
                                       BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 2
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 4 ), FighterRed, BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP, String.valueOf( fightListA.get( 4 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "13", fightListA.get( 4 ), editAccess, linkToFight, 3,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 1 ), FighterRed, BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP, String.valueOf( fightListA.get( 1 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "23", fightListA.get( 1 ), editAccess, linkToFight, 5,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 0 ), FighterRed, BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP, String.valueOf( fightListA.get( 0 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLinkFor3rdPlace( component, out, _15, "25", fightListA.get( 0 ), editAccess, linkToFight, 3,
                                       BORDER_TOP | BORDER_BOTTOM, context,fightListA, fightListB );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 3
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, null, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 2 ), FighterBlue, 2, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 2 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        if ( TypeUtil.LONG_EMPTY != fightListA.get( 0 ).getWinnerId() )
        {
            if ( fightListA.get( 0 ).getTeamIdRed() == fightListA.get( 0 ).getWinnerId() )
            {
                doTableCell( component, out, _120, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER,
                             fightListA.get( 0 ).getDuoTeamRed().getName() + " / "
                                 + fightListA.get( 0 ).getDuoTeamRed().getName2() );
            }
            else
            {
                if ( fightListA.get( 0 ).getTeamIdBlue() == fightListA.get( 0 ).getWinnerId() )
                {
                    doTableCell( component, out, _120, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER,
                                 fightListA.get( 0 ).getDuoTeamBlue().getName() + " / "
                                     + fightListA.get( 0 ).getDuoTeamBlue().getName2() );
                }
                else
                {
                    doTableCell( component, out, _120, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER,
                                 TypeUtil.STRING_DEFAULT );
                }
            }
        }
        else
        {
            doTableCell( component, out, _120, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER, TypeUtil.STRING_DEFAULT );
        }
        out.endElement( JSF_TR );

        // row 4
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 4 ), FighterBlue, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 4 ).getPointsBlue() ) );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, null, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 0 ), FighterBlue, 2, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 0 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 5
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 3 ), FighterRed, BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP, String.valueOf( fightListA.get( 3 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "18", fightListA.get( 3 ), editAccess, linkToFight, 3,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 6
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 5 ), FighterRed, BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP, String.valueOf( fightListA.get( 5 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "14", fightListA.get( 5 ), editAccess, linkToFight, 3,
                                       BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 1 ), FighterBlue, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 1 ).getPointsBlue() ) );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 7
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, null, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 3 ), FighterBlue, 2, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 3 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 8
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 5 ), FighterBlue, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 5 ).getPointsBlue() ) );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // -----------------------------------------------------------------------------------------
        // middle
        // -----------------------------------------------------------------------------------------

        // row 9
        out.startElement( JSF_TR, component );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "15", null );
        out.writeAttribute( ATTR_BGCOLOR, "#e5e5e5", null );
        writeText( out, TypeUtil.STRING_DEFAULT );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );
        // -----------------------------------------------------------------------------------------
        // end middle
        // -----------------------------------------------------------------------------------------

        // row 10
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 2 ), FighterRed, BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP, String.valueOf( fightListB.get( 2 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "19", fightListB.get( 2 ), editAccess, linkToFight, 3,
                                       BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 11
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 4 ), FighterRed, BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP, String.valueOf( fightListB.get( 4 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "15", fightListB.get( 4 ), editAccess, linkToFight, 3,
                                       BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 1 ), FighterRed, BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP, String.valueOf( fightListB.get( 1 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "24", fightListB.get( 1 ), editAccess, linkToFight, 5,
                                       BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 0 ), FighterRed, BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP, String.valueOf( fightListB.get( 0 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLinkFor3rdPlace( component, out, _15, "26", fightListB.get( 0 ), editAccess, linkToFight, 3,
                                       BORDER_TOP | BORDER_BOTTOM, context,fightListA, fightListB );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 12
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, null, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 2 ), FighterBlue, 2, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 2 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        if ( TypeUtil.LONG_EMPTY != fightListB.get( 0 ).getWinnerId() )
        {
            if ( fightListB.get( 0 ).getTeamIdRed() == fightListB.get( 0 ).getWinnerId() )
            {
                doTableCell( component, out, _120, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER,
                             fightListB.get( 0 ).getDuoTeamRed().getName() + " / "
                                 + fightListB.get( 0 ).getDuoTeamRed().getName2() );
            }
            else
            {
                if ( fightListB.get( 0 ).getTeamIdBlue() == fightListB.get( 0 ).getWinnerId() )
                {
                    doTableCell( component, out, _120, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER,
                                 fightListB.get( 0 ).getDuoTeamBlue().getName() + " / "
                                     + fightListB.get( 0 ).getDuoTeamBlue().getName2() );
                }
                else
                {
                    doTableCell( component, out, _120, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER,
                                 TypeUtil.STRING_DEFAULT );
                }
            }
        }
        else
        {
            doTableCell( component, out, _120, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER, TypeUtil.STRING_DEFAULT );
        }
        out.endElement( JSF_TR );

        // row 13
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 4 ), FighterBlue, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 4 ).getPointsBlue() ) );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, null, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 0 ), FighterBlue, 2, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 0 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 14
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 3 ), FighterRed, BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP, String.valueOf( fightListB.get( 3 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "20", fightListB.get( 3 ), editAccess, linkToFight, 3,
                                       BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 15
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 5 ), FighterRed, BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP, String.valueOf( fightListB.get( 5 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "16", fightListB.get( 5 ), editAccess, linkToFight, 3,
                                       BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 1 ), FighterBlue, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 1 ).getPointsBlue() ) );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 16
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, null, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 3 ), FighterBlue, 2, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 3 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 17
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 5 ), FighterBlue, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 5 ).getPointsBlue() ) );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.endElement( JSF_TABLE );
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
                                       boolean firstname, DuoFight fight, int redOrBlue, int style )
        throws IOException
    {
        if ( redOrBlue == FighterRed )
        {
            if ( fight.getTeamIdRed() == fight.getWinnerId() )
            {
                if ( firstname )
                {
                    if ( fight.getDuoTeamRed() == null )
                    {
                        doTableCell( component, out, width, valign, HIGHLIGHT_WINNER | style, TypeUtil.STRING_DEFAULT );
                    }
                    else
                    {
                        doTableCell( component, out, width, valign, HIGHLIGHT_WINNER | style,
                                     fight.getDuoTeamRed().getName() + " / " + fight.getDuoTeamRed().getName2() );
                    }
                }
                else if ( fight.getDuoTeamRed() == null )
                {
                    doTableCell( component, out, width, valign, HIGHLIGHT_WINNER | style, TypeUtil.STRING_DEFAULT );
                }
                else
                {
                    doTableCell( component, out, width, valign, HIGHLIGHT_WINNER | style,
                                 fight.getDuoTeamRed().getName() + " / " + fight.getDuoTeamRed().getName2() );
                }
            }
            else
            {
                if ( firstname )
                {
                    if ( fight.getDuoTeamRed() == null )
                    {
                        doTableCell( component, out, width, valign, PLAIN | style, TypeUtil.STRING_DEFAULT );
                    }
                    else
                    {
                        doTableCell( component, out, width, valign, PLAIN | style, fight.getDuoTeamRed().getName()
                            + " / " + fight.getDuoTeamRed().getName2() );
                    }
                }
                else if ( fight.getDuoTeamRed() == null )
                {
                    doTableCell( component, out, width, valign, PLAIN | style, TypeUtil.STRING_DEFAULT );
                }
                else
                {
                    doTableCell( component, out, width, valign, PLAIN | style, fight.getDuoTeamRed().getName() + " / "
                        + fight.getDuoTeamRed().getName2() );
                }
            }
        }
        if ( redOrBlue == FighterBlue )
        {
            if ( fight.getTeamIdBlue() == fight.getWinnerId() )
            {
                if ( firstname )
                {
                    if ( fight.getDuoTeamBlue() == null )
                    {
                        doTableCell( component, out, width, valign, HIGHLIGHT_WINNER | style, TypeUtil.STRING_DEFAULT );
                    }
                    else
                    {
                        doTableCell( component, out, width, valign, HIGHLIGHT_WINNER | style,
                                     fight.getDuoTeamBlue().getName() + " / " + fight.getDuoTeamBlue().getName2() );
                    }
                }
                else if ( fight.getDuoTeamBlue() == null )
                {
                    doTableCell( component, out, width, valign, HIGHLIGHT_WINNER | style, TypeUtil.STRING_DEFAULT );
                }
                else
                {
                    doTableCell( component, out, width, valign, HIGHLIGHT_WINNER | style,
                                 fight.getDuoTeamBlue().getName() + " / " + fight.getDuoTeamBlue().getName2() );
                }
            }
            else
            {
                if ( firstname )
                {
                    if ( fight.getDuoTeamBlue() == null )
                    {
                        doTableCell( component, out, width, valign, PLAIN | style, TypeUtil.STRING_DEFAULT );
                    }
                    else
                    {
                        doTableCell( component, out, width, valign, PLAIN | style,
 fight.getDuoTeamBlue().getName()
                            + " " + fight.getDuoTeamBlue().getName2() );
                    }
                }
                else if ( fight.getDuoTeamBlue() == null )
                {
                    doTableCell( component, out, width, valign, PLAIN | style, TypeUtil.STRING_DEFAULT );
                }
                else
                {
                    doTableCell( component, out, width, valign, PLAIN | style, fight.getDuoTeamBlue().getName() + " / "
                        + fight.getDuoTeamBlue().getName2() );
                }
            }
        }

    }

    protected static void doTableCell( UIComponent component, ResponseWriter out, String widht, String valign,
                                       boolean firstname, DuoFight fight, int redOrBlue, int colspan, int style )
        throws IOException
    {
        if ( redOrBlue == FighterRed )
        {
            if ( fight.getTeamIdRed() == fight.getWinnerId() )
            {
                if ( firstname )
                {
                    if ( fight.getDuoTeamRed() == null )
                    {
                        doTableCell( component, out, widht, valign, HIGHLIGHT_WINNER | style, colspan,
                                     TypeUtil.STRING_DEFAULT );
                    }
                    else
                    {
                        doTableCell( component, out, widht, valign, HIGHLIGHT_WINNER | style, colspan,
                                     fight.getDuoTeamRed().getName() + " / " + fight.getDuoTeamRed().getName2() );
                    }
                }
                else if ( fight.getDuoTeamRed() == null )
                {
                    doTableCell( component, out, widht, valign, HIGHLIGHT_WINNER | style, colspan,
                                 TypeUtil.STRING_DEFAULT );
                }
                else
                {
                    doTableCell( component, out, widht, valign, HIGHLIGHT_WINNER | style, colspan,
                                 fight.getDuoTeamRed().getName() + " / " + fight.getDuoTeamRed().getName2() );
                }
            }
            else
            {
                if ( firstname )
                {
                    if ( fight.getDuoTeamRed() == null )
                    {
                        doTableCell( component, out, widht, valign, PLAIN | style, colspan, TypeUtil.STRING_DEFAULT );
                    }
                    else
                    {
                        doTableCell( component, out, widht, valign, PLAIN | style, colspan,
                                     fight.getDuoTeamRed().getName() + " / " + fight.getDuoTeamRed().getName2() );
                    }
                }
                else if ( fight.getDuoTeamRed() == null )
                {
                    doTableCell( component, out, widht, valign, PLAIN | style, colspan, TypeUtil.STRING_DEFAULT );
                }
                else
                {
                    doTableCell( component, out, widht, valign, PLAIN | style, colspan, fight.getDuoTeamRed().getName()
                        + " / " + fight.getDuoTeamRed().getName2() );
                }
            }
        }
        if ( redOrBlue == FighterBlue )
        {
            if ( fight.getTeamIdRed() == fight.getWinnerId() )
            {
                if ( firstname )
                {
                    if ( fight.getDuoTeamBlue() == null )
                    {
                        doTableCell( component, out, widht, valign, HIGHLIGHT_WINNER | style, colspan,
                                     TypeUtil.STRING_DEFAULT );
                    }
                    else
                    {
                        doTableCell( component, out, widht, valign, HIGHLIGHT_WINNER | style, colspan,
                                     fight.getDuoTeamBlue().getName() + " / " + fight.getDuoTeamBlue().getName2() );
                    }
                }
                else if ( fight.getDuoTeamBlue() == null )
                {
                    doTableCell( component, out, widht, valign, PLAIN | style, colspan, TypeUtil.STRING_DEFAULT );
                }
                else
                {
                    doTableCell( component, out, widht, valign, PLAIN | style, colspan,
                                 fight.getDuoTeamBlue().getName() + " / " + fight.getDuoTeamBlue().getName2() );
                }
            }
            else
            {
                if ( firstname )
                {
                    if ( fight.getDuoTeamBlue() == null )
                    {
                        doTableCell( component, out, widht, valign, PLAIN | style, colspan, TypeUtil.STRING_DEFAULT );
                    }
                    else
                    {
                        doTableCell( component, out, widht, valign, PLAIN | style, colspan,
                                     fight.getDuoTeamBlue().getName() + " / " + fight.getDuoTeamBlue().getName2() );
                    }
                }
                else if ( fight.getDuoTeamBlue() == null )
                {
                    doTableCell( component, out, widht, valign, PLAIN | style, colspan, TypeUtil.STRING_DEFAULT );
                }
                else
                {
                    doTableCell( component, out, widht, valign, HIGHLIGHT_WINNER | style, colspan,
                                 fight.getDuoTeamBlue().getName() + " / " + fight.getDuoTeamBlue().getName2() );
                }
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
                                                         String text, DuoFight fight, boolean editAccess,
                                                         String linkToFight, int rowspan, int style,
                                                         FacesContext context, Map<Integer, DuoFight> fightListA , Map<Integer, DuoFight> fightListB  )
        throws IOException
    {
       if(    TypeUtil.LONG_EMPTY == fightListA.get(0).getTeamIdRed()
            || TypeUtil.LONG_EMPTY == fightListA.get(0).getTeamIdBlue()
            || TypeUtil.LONG_EMPTY == fightListB.get(0).getTeamIdRed()
            || TypeUtil.LONG_EMPTY == fightListB.get(0).getTeamIdBlue()  )
           //no access
            doTableCellWithRowSpanAndLink( component, out, width, text, fight, false, linkToFight, rowspan, style, context );
        else
            doTableCellWithRowSpanAndLink( component, out, width, text, fight, editAccess, linkToFight, rowspan, style, context );
    }
    
    protected static void doTableCellWithRowSpanAndLink( UIComponent component, ResponseWriter out, String widht,
                                                         String text, DuoFight fight, boolean editAccess,
                                                         String linkToFight, int rowspan, int style,
                                                         FacesContext context )
        throws IOException
    {

        out.startElement( JSF_TD, component );
        if ( widht != null )
        {
            out.writeAttribute( ATTR_WIDTH, widht, null );
        }
        out.writeAttribute( ATTR_ALIGN, VAL_CENTER, null );
        if ( rowspan > 0 )
        {
            out.writeAttribute( ATTR_ROWSPAN, rowspan, null );
        }
        if ( editAccess && fight != null && fight.getTeamIdRed() > 0 && fight.getTeamIdBlue() > 0 )
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
            sb2.append( "?system=D&id=" );
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

    protected static void doTableCellWithRowSpan( UIComponent component, ResponseWriter out, String widht,
                                                  String align, boolean bold, int style, int colspan, String text,
                                                  int rowspan )
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

    protected static void doTableCell( UIComponent component, ResponseWriter out, String widht, String align,
                                       int style, String text )
        throws IOException
    {
        doTableCell( component, out, widht, align, style, 0, text );
    }

    protected static void writeMainRoundTableHeader( UIComponent component, ResponseWriter out, ResourceBundle rb )
        throws IOException
    {
        out.startElement( JSF_THEAD, component );
        out.startElement( JSF_TR, component );

        out.startElement( JSF_TH, component );
        out.writeAttribute( ATTR_WIDTH, "220", null );
        out.writeAttribute( ATTR_COLSPAN, "4", null );
        writeText( out, rb.getString( "round_of_16" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, component );
        out.writeAttribute( ATTR_WIDTH, "150", null );
        out.writeAttribute( ATTR_COLSPAN, "2", null );
        writeText( out, rb.getString( "quarterfinal" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, component );
        out.writeAttribute( ATTR_WIDTH, "150", null );
        out.writeAttribute( ATTR_COLSPAN, "2", null );
        writeText( out, rb.getString( "semifinal" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, component );
        out.writeAttribute( ATTR_WIDTH, "150", null );
        out.writeAttribute( ATTR_COLSPAN, "2", null );
        writeText( out, rb.getString( "final" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, component );
        out.writeAttribute( ATTR_WIDTH, "100", null );
        writeText( out, rb.getString( "champion" ) );
        out.endElement( JSF_TH );

        out.endElement( JSF_TR );
        out.endElement( JSF_THEAD );
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

        out.writeAttribute( ATTR_STYLE, styleString, null );
        // out.startElement( JSF_BOLD_MASK, component );
    }
}
