/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaKo16WebComponentHelper.java
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

package de.jjw.webapp.jsfTags.component.newa;

import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import de.jjw.model.newa.NewaFight;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.taglib.HtmlConstants;

public class NewaKo16WebComponentHelper
    extends AbstractNewaWebComponentHelper
    implements HtmlConstants, IGlobalWebConstants
{

    public static void doMainround( UIComponent component, FacesContext context, ResponseWriter out, String style,
                                    ResourceBundle rb, Map<Integer, NewaFight> fightListA,
                                    Map<Integer, NewaFight> fightListB, NewaFight finalFight, boolean editAccess,
                                    String linkToFight )
        throws IOException
    {
        out.startElement( JSF_TABLE, component );
        out.writeAttribute( ATTR_CLASS, style, null );
        out.writeAttribute( ATTR_BORDER, TABLE_BORDER, null );
        out.writeAttribute( ATTR_CELLPADDING, TABLE_CELLPADDING, null );
        out.writeAttribute( ATTR_CELLSPACING, TABLE_CELLSPACING, null );
        out.writeAttribute( ATTR_WIDTH, TABLE_WIDTH, null );

        // out.writeAttribute(ATTR_BORDERCOLOR, "#f0e68c", null);
        // out.writeAttribute( ATTR_WIDTH, "800", null );

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
        doTableCellWithRowSpanAndLink( component, out, _15, "4", fightListA.get( 6 ), editAccess, linkToFight, 3,
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
            if ( finalFight.getFighterIdRed() == finalFight.getWinnerId() )
            {
                doTableCell( component, out, _120, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER,
                             finalFight.getFighterRed().getName() );
            }
            else
            {
                if ( finalFight.getFighterIdBlue() == finalFight.getWinnerId() )
                {
                    doTableCell( component, out, _120, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER,
                                 finalFight.getFighterBlue().getName() );
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
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP, String.valueOf( finalFight.getPointsBlue() ) );
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
                                      ResourceBundle rb, Map<Integer, NewaFight> fightListA,
                                      Map<Integer, NewaFight> fightListB, boolean editAccess, String linkToFight )
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
            if ( fightListA.get( 0 ).getFighterIdRed() == fightListA.get( 0 ).getWinnerId() )
            {
                doTableCell( component, out, _120, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER,
                             fightListA.get( 0 ).getFighterRed().getName() );
            }
            else
            {
                if ( fightListA.get( 0 ).getFighterIdBlue() == fightListA.get( 0 ).getWinnerId() )
                {
                    doTableCell( component, out, _120, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER,
                                 fightListA.get( 0 ).getFighterBlue().getName() );
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
            if ( fightListB.get( 0 ).getFighterIdRed() == fightListB.get( 0 ).getWinnerId() )
            {
                doTableCell( component, out, _120, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER,
                             fightListB.get( 0 ).getFighterRed().getName() );
            }
            else
            {
                if ( fightListB.get( 0 ).getFighterIdBlue() == fightListB.get( 0 ).getWinnerId() )
                {
                    doTableCell( component, out, _120, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER,
                                 fightListB.get( 0 ).getFighterBlue().getName() );
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

    private static void writeMainRoundTableHeader( UIComponent component, ResponseWriter out, ResourceBundle rb )
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

}
