/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaKo32WebComponentHelper.java
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

package de.jjw.webapp.jsfTags.component.newa;

import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import de.jjw.model.newa.NewaFight;
import de.jjw.util.TypeUtil;

public class NewaKo32WebComponentHelper
    extends AbstractNewaWebComponentHelper
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
        out.writeAttribute( ATTR_WIDTH, TABLE_WIDTH_KO32, null );

        writeMainRoundTableHeader( component, out, rb );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.1" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( 7 ), FighterRed, PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( 7 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "1", fightListA.get( 7 ), editAccess, linkToFight, 3,
                                       BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 3 ), FighterRed, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 3 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.17" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( 7 ), FighterBlue, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 7 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "17", fightListA.get( 3 ), editAccess, linkToFight, 3,
                                       PLAIN, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 5
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 1 ), FighterRed, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 1 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 5
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.9" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( 8 ), FighterRed, BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP, String.valueOf( fightListA.get( 8 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "3", fightListA.get( 8 ), editAccess, linkToFight, 3,
                                       BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "25", fightListA.get( 1 ), editAccess, linkToFight, 7,
                                       PLAIN, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 6
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 3 ), FighterBlue, BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP, String.valueOf( fightListA.get( 3 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 7
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.25" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( 8 ), FighterBlue, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 8 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 0 ), FighterRed, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 0 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 9
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.5" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( 9 ), FighterRed, BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( 9 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "5", fightListA.get( 9 ), editAccess, linkToFight, 3,
                                       BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "29", fightListA.get( 0 ), editAccess, linkToFight, 15,
                                       PLAIN, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 4 ), FighterRed, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 4 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.21" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( 9 ), FighterBlue, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 9 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "19", fightListA.get( 4 ), editAccess, linkToFight, 3,
                                       PLAIN, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 1 ), FighterBlue, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( 1 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.13" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( 10 ), FighterRed, PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( 10 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "7", fightListA.get( 10 ), editAccess, linkToFight, 3,
                                       BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 4 ), FighterBlue, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( 4 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.29" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( 10 ), FighterBlue, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 10 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // 2nd Quarter
        out.startElement( JSF_TR, component );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "9", null );
        out.writeAttribute( ATTR_BGCOLOR, "#CCCCCC", null );
        writeText( out, TypeUtil.STRING_DEFAULT );
        out.endElement( JSF_TD );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, finalFight, FighterRed, PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( finalFight.getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.3" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( 11 ), FighterRed, PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( 11 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "9", fightListA.get( 11 ), editAccess, linkToFight, 3,
                                       BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "59", finalFight, editAccess, linkToFight, 31, PLAIN,
                                       context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 5 ), FighterRed, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 5 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.19" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( 11 ), FighterBlue, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 11 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "21", fightListA.get( 5 ), editAccess, linkToFight, 3,
                                       PLAIN, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 2 ), FighterRed, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 2 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.11" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( 12 ), FighterRed, PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( 12 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "11", fightListA.get( 12 ), editAccess, linkToFight, 3,
                                       BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "27", fightListA.get( 2 ), editAccess, linkToFight, 7,
                                       PLAIN, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 5 ), FighterBlue, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( 5 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.27" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( 12 ), FighterBlue, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 12 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 0 ), FighterBlue, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( 0 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.7" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( 13 ), FighterRed, PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( 13 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "13", fightListA.get( 13 ), editAccess, linkToFight, 3,
                                       BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 6 ), FighterRed, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 6 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.23" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( 13 ), FighterBlue, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 13 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "23", fightListA.get( 6 ), editAccess, linkToFight, 3,
                                       PLAIN, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 2 ), FighterBlue, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( 2 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.15" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( 14 ), FighterRed, PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( 14 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "15", fightListA.get( 14 ), editAccess, linkToFight, 3,
                                       BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 6 ), FighterBlue, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( 6 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.31" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( 14 ), FighterBlue, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 14 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // -----------------------------------------------------------------------------------------
        // middle
        // -----------------------------------------------------------------------------------------
        out.startElement( JSF_TR, component );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "11", null );
        out.writeAttribute( ATTR_BGCOLOR, "#CCCCCC", null );
        writeText( out, TypeUtil.STRING_DEFAULT );
        out.endElement( JSF_TD );
        if ( TypeUtil.LONG_EMPTY != finalFight.getWinnerId() )
        {
            if ( finalFight.getFighterIdRed() == finalFight.getWinnerId() )
            {
                doTableCell( component, out, _100, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER,
                             finalFight.getFighterRed().getName() );
            }
            else
            {
                if ( finalFight.getFighterIdBlue() == finalFight.getWinnerId() )
                {
                    doTableCell( component, out, _100, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER,
                                 finalFight.getFighterBlue().getName() );
                }
                else
                {
                    doTableCell( component, out, _100, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER,
                                 TypeUtil.STRING_DEFAULT );
                }
            }
        }
        else
        {
            doTableCell( component, out, _100, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER, TypeUtil.STRING_DEFAULT );
        }
        out.endElement( JSF_TR );
        // -----------------------------------------------------------------------------------------
        // end middle
        // -----------------------------------------------------------------------------------------

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.2" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( 7 ), FighterRed, PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( 7 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "2", fightListB.get( 7 ), editAccess, linkToFight, 3,
                                       BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 3 ), FighterRed, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 3 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.18" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( 7 ), FighterBlue, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 7 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "18", fightListB.get( 3 ), editAccess, linkToFight, 3,
                                       PLAIN, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 1 ), FighterRed, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 1 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.10" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( 8 ), FighterRed, PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( 8 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "4", fightListB.get( 8 ), editAccess, linkToFight, 3,
                                       BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "26", fightListB.get( 1 ), editAccess, linkToFight, 7,
                                       PLAIN, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 3 ), FighterBlue, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( 3 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.26" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( 8 ), FighterBlue, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 8 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 0 ), FighterRed, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 0 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.6" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( 9 ), FighterRed, PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( 9 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "6", fightListB.get( 9 ), editAccess, linkToFight, 3,
                                       BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "30", fightListB.get( 0 ), editAccess, linkToFight, 15,
                                       PLAIN, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 4 ), FighterRed, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 4 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.22" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( 9 ), FighterBlue, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 9 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "20", fightListB.get( 4 ), editAccess, linkToFight, 3,
                                       PLAIN, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 1 ), FighterBlue, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( 1 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.14" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( 10 ), FighterRed, PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( 10 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "8", fightListB.get( 10 ), editAccess, linkToFight, 3,
                                       BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 4 ), FighterBlue, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( 4 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.30" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( 10 ), FighterBlue, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 10 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "9", null );
        out.writeAttribute( ATTR_BGCOLOR, "#CCCCCC", null );
        writeText( out, TypeUtil.STRING_DEFAULT );
        out.endElement( JSF_TD );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, finalFight, FighterBlue, PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( finalFight.getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.4" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( 11 ), FighterRed, PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( 11 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "10", fightListB.get( 11 ), editAccess, linkToFight, 3,
                                       BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 5 ), FighterRed, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 5 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.20" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( 11 ), FighterBlue, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 11 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "22", fightListB.get( 5 ), editAccess, linkToFight, 3,
                                       PLAIN, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 2 ), FighterRed, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 2 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.12" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( 12 ), FighterRed, PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( 12 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "12", fightListB.get( 12 ), editAccess, linkToFight, 3,
                                       BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "28", fightListB.get( 2 ), editAccess, linkToFight, 7,
                                       PLAIN, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 5 ), FighterBlue, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( 5 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.28" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( 12 ), FighterBlue, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 12 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 0 ), FighterBlue, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( 0 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.8" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( 13 ), FighterRed, PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( 13 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "14", fightListB.get( 13 ), editAccess, linkToFight, 3,
                                       BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 6 ), FighterRed, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 6 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.24" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( 13 ), FighterBlue, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 13 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "24", fightListB.get( 6 ), editAccess, linkToFight, 3,
                                       PLAIN, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 2 ), FighterBlue, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( 2 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.16" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( 14 ), FighterRed, PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( 14 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "16", fightListB.get( 14 ), editAccess, linkToFight, 3,
                                       BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 6 ), FighterBlue, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( 6 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.32" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( 14 ), FighterBlue, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 14 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );

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
        out.writeAttribute( ATTR_WIDTH, TABLE_WIDTH_KO32, null );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 6 ), FighterRed, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( 6 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "39", fightListA.get( 6 ), editAccess, linkToFight, 3,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "15", null );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 10 ), FighterRed, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( 10 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "31", fightListA.get( 10 ), editAccess, linkToFight, 3,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 4 ), FighterRed, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( 4 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "47", fightListA.get( 4 ), editAccess, linkToFight, 5,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 2 ), FighterRed, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( 2 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "51", fightListA.get( 2 ), editAccess, linkToFight, 3,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "8", null );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, null, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 6 ), FighterBlue, 2, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 6 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 1 ), FighterRed, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( 1 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "55", fightListA.get( 1 ), editAccess, linkToFight, 9,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "5", null );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 10 ), FighterBlue, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 10 ).getPointsBlue() ) );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "6", null );
        out.endElement( JSF_TD );
        doTableCell( component, out, null, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 2 ), FighterBlue, 2, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 2 ).getPointsBlue() ) );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "8", null );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "4", null );
        out.endElement( JSF_TD );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 7 ), FighterRed, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( 7 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "40", fightListA.get( 7 ), editAccess, linkToFight, 3,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 0 ), FighterRed, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( 0 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLinkFor3rdPlace( component, out, _15, "57", fightListA.get( 0 ), editAccess, linkToFight, 3,
                                       BORDER_BOTTOM | BORDER_TOP, context, fightListA, fightListB  );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 11 ), FighterRed, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( 11 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "32", fightListA.get( 11 ), editAccess, linkToFight, 3,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 4 ), FighterBlue, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 4 ).getPointsBlue() ) );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "10", null );
        out.endElement( JSF_TD );
        if ( TypeUtil.LONG_EMPTY != fightListA.get( 0 ).getWinnerId() )
        {
            if ( fightListA.get( 0 ).getFighterIdRed() == fightListA.get( 0 ).getWinnerId() )
            {
                doTableCell( component, out, _100, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER,
                             fightListA.get( 0 ).getFighterRed().getName() );
            }
            else
            {
                if ( fightListA.get( 0 ).getFighterIdBlue() == fightListA.get( 0 ).getWinnerId() )
                {
                    doTableCell( component, out, _100, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER,
                                 fightListA.get( 0 ).getFighterBlue().getName() );
                }
                else
                {
                    doTableCell( component, out, _100, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER,
                                 TypeUtil.STRING_DEFAULT );
                }
            }
        }
        else
        {
            doTableCell( component, out, _100, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER, TypeUtil.STRING_DEFAULT );
        }
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, null, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 7 ), FighterBlue, 2, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 7 ).getPointsBlue() ) );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "9", null );
        out.endElement( JSF_TD );
        doTableCell( component, out, null, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 0 ), FighterBlue, 2, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 0 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 11 ), FighterBlue, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 11 ).getPointsBlue() ) );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "19", null );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );
        // --------------------------
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 8 ), FighterRed, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( 8 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "41", fightListA.get( 8 ), editAccess, linkToFight, 3,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "15", null );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 12 ), FighterRed, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( 12 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "33", fightListA.get( 12 ), editAccess, linkToFight, 3,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 5 ), FighterRed, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( 5 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "48", fightListA.get( 5 ), editAccess, linkToFight, 5,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 3 ), FighterRed, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( 3 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "52", fightListA.get( 3 ), editAccess, linkToFight, 3,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "8", null );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, null, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 8 ), FighterBlue, 2, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 8 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 1 ), FighterBlue, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 1 ).getPointsBlue() ) );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "5", null );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 12 ), FighterBlue, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 12 ).getPointsBlue() ) );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "6", null );
        out.endElement( JSF_TD );
        doTableCell( component, out, null, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 3 ), FighterBlue, 2, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 3 ).getPointsBlue() ) );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "8", null );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "4", null );
        out.endElement( JSF_TD );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 9 ), FighterRed, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( 9 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "42", fightListA.get( 9 ), editAccess, linkToFight, 3,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "15", null );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 13 ), FighterRed, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( 13 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "34", fightListA.get( 13 ), editAccess, linkToFight, 3,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 5 ), FighterBlue, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 5 ).getPointsBlue() ) );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "12", null );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, null, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 9 ), FighterBlue, 2, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 9 ).getPointsBlue() ) );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "15", null );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 13 ), FighterBlue, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( 13 ).getPointsBlue() ) );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "19", null );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        // -----------------------------------------------------------------------------------------
        // middle
        // -----------------------------------------------------------------------------------------
        out.startElement( JSF_TR, component );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "22", null );
        out.writeAttribute( ATTR_BGCOLOR, "#CCCCCC", null );
        writeText( out, TypeUtil.STRING_DEFAULT );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );
        // -----------------------------------------------------------------------------------------
        // end middle
        // -----------------------------------------------------------------------------------------
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 6 ), FighterRed, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( 6 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "43", fightListB.get( 6 ), editAccess, linkToFight, 3,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "15", null );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 10 ), FighterRed, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( 10 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "35", fightListB.get( 10 ), editAccess, linkToFight, 3,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 4 ), FighterRed, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( 4 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "49", fightListB.get( 4 ), editAccess, linkToFight, 5,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 2 ), FighterRed, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( 2 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "53", fightListB.get( 2 ), editAccess, linkToFight, 3,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "8", null );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, null, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 6 ), FighterBlue, 2, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 6 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 1 ), FighterRed, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( 1 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "56", fightListB.get( 1 ), editAccess, linkToFight, 9,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "5", null );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 10 ), FighterBlue, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 10 ).getPointsBlue() ) );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "6", null );
        out.endElement( JSF_TD );
        doTableCell( component, out, null, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 2 ), FighterBlue, 2, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 2 ).getPointsBlue() ) );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "8", null );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "4", null );
        out.endElement( JSF_TD );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 7 ), FighterRed, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( 7 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "44", fightListB.get( 7 ), editAccess, linkToFight, 3,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 0 ), FighterRed, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( 0 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLinkFor3rdPlace( component, out, _15, "58", fightListB.get( 0 ), editAccess, linkToFight, 3,
                                       BORDER_BOTTOM | BORDER_TOP, context, fightListA, fightListB  );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 11 ), FighterRed, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( 11 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "36", fightListB.get( 11 ), editAccess, linkToFight, 3,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 4 ), FighterBlue, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 4 ).getPointsBlue() ) );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "10", null );
        out.endElement( JSF_TD );
        if ( TypeUtil.LONG_EMPTY != fightListB.get( 0 ).getWinnerId() )
        {
            if ( fightListB.get( 0 ).getFighterIdRed() == fightListB.get( 0 ).getWinnerId() )
            {
                doTableCell( component, out, _100, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER,
                             fightListB.get( 0 ).getFighterRed().getName() );
            }
            else
            {
                if ( fightListB.get( 0 ).getFighterIdBlue() == fightListB.get( 0 ).getWinnerId() )
                {
                    doTableCell( component, out, _100, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER,
                                 fightListB.get( 0 ).getFighterBlue().getName() );
                }
                else
                {
                    doTableCell( component, out, _100, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER,
                                 TypeUtil.STRING_DEFAULT );
                }
            }
        }
        else
        {
            doTableCell( component, out, _100, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER, TypeUtil.STRING_DEFAULT );
        }
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, null, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 7 ), FighterBlue, 2, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 7 ).getPointsBlue() ) );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "9", null );
        out.endElement( JSF_TD );
        doTableCell( component, out, null, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 0 ), FighterBlue, 2, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 0 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 11 ), FighterBlue, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 11 ).getPointsBlue() ) );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "19", null );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );
        // --------------------------
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 8 ), FighterRed, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( 8 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "45", fightListB.get( 8 ), editAccess, linkToFight, 3,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "15", null );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 12 ), FighterRed, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( 12 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "37", fightListB.get( 12 ), editAccess, linkToFight, 3,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 5 ), FighterRed, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( 5 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "50", fightListB.get( 5 ), editAccess, linkToFight, 5,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 3 ), FighterRed, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( 3 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "54", fightListB.get( 3 ), editAccess, linkToFight, 3,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "8", null );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, null, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 8 ), FighterBlue, 2, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 8 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 1 ), FighterBlue, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 1 ).getPointsRed() ) );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "5", null );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 12 ), FighterBlue, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 12 ).getPointsBlue() ) );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "6", null );
        out.endElement( JSF_TD );
        doTableCell( component, out, null, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 3 ), FighterBlue, 2, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 3 ).getPointsBlue() ) );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "8", null );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "4", null );
        out.endElement( JSF_TD );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 9 ), FighterRed, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( 9 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "46", fightListB.get( 9 ), editAccess, linkToFight, 3,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "15", null );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 13 ), FighterRed, PLAIN
            | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( 13 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "38", fightListB.get( 13 ), editAccess, linkToFight, 3,
                                       BORDER_BOTTOM | BORDER_TOP, context );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 5 ), FighterBlue, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 5 ).getPointsBlue() ) );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "12", null );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, null, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 9 ), FighterBlue, 2, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 9 ).getPointsBlue() ) );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "15", null );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 13 ), FighterBlue, PLAIN
            | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( 13 ).getPointsBlue() ) );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "19", null );
        out.endElement( JSF_TD );
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
        writeText( out, rb.getString( "round_of_32" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, component );
        out.writeAttribute( ATTR_WIDTH, "130", null );
        out.writeAttribute( ATTR_COLSPAN, "2", null );
        writeText( out, rb.getString( "round_of_16" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, component );
        out.writeAttribute( ATTR_WIDTH, "130", null );
        out.writeAttribute( ATTR_COLSPAN, "2", null );
        writeText( out, rb.getString( "quarterfinal" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, component );
        out.writeAttribute( ATTR_WIDTH, "130", null );
        out.writeAttribute( ATTR_COLSPAN, "2", null );
        writeText( out, rb.getString( "semifinal" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, component );
        out.writeAttribute( ATTR_WIDTH, "130", null );
        out.writeAttribute( ATTR_COLSPAN, "2", null );
        writeText( out, rb.getString( "final" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, component );
        out.writeAttribute( ATTR_WIDTH, "110", null );
        writeText( out, rb.getString( "champion" ) );
        out.endElement( JSF_TH );

        out.endElement( JSF_TR );
        out.endElement( JSF_THEAD );
    }

}
