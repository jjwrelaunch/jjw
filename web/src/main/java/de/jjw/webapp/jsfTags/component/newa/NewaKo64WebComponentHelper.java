/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaKo64WebComponentHelper.java
 * Created : 17 Jun 2012
 * Last Modified: Sun, 17 Jun 2012 12:55:48
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

import de.jjw.model.fighting.Fight;
import de.jjw.model.newa.NewaFight;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;

public class NewaKo64WebComponentHelper
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
        out.writeAttribute( ATTR_WIDTH, TABLE_WIDTH_KO64, null );

        writeMainRoundTableHeader( component, out, rb );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.1" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._15 ), FighterRed,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( IValueConstants._15 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "1", fightListA.get( IValueConstants._15 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( IValueConstants._7 ), FighterRed,
                     BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( IValueConstants._7 ).getPointsRed() ) );
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
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.33" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._15 ), FighterBlue,
                     BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( IValueConstants._15 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "33", fightListA.get( IValueConstants._7 ), editAccess,
                                       linkToFight, 3, PLAIN, context );
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

        // row 5
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( IValueConstants._3 ), FighterRed,
                     BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListA.get( IValueConstants._3 ).getPointsRed() ) );
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
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.17" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._16 ), FighterRed,
                     BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP,
                     String.valueOf( fightListA.get( IValueConstants._16 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "3", fightListA.get( IValueConstants._16 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "49", fightListA.get( IValueConstants._3 ), editAccess,
                                       linkToFight, 7, PLAIN, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( IValueConstants._7 ), FighterBlue,
                     BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP,
                     String.valueOf( fightListA.get( IValueConstants._7 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 7
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.49" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._16 ), FighterBlue,
                     BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListA.get( IValueConstants._16 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( IValueConstants._1 ), FighterRed,
                     BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListA.get( IValueConstants._1 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 9
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.9" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._17 ), FighterRed,
                     BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( IValueConstants._17 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "5", fightListA.get( IValueConstants._17 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "57", fightListA.get( IValueConstants._1 ), editAccess,
                                       linkToFight, 15, PLAIN, context );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( IValueConstants._8 ), FighterRed,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( IValueConstants._8 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.41" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._17 ), FighterBlue,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( IValueConstants._17 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "35", fightListA.get( IValueConstants._8 ), editAccess,
                                       linkToFight, 3, PLAIN, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( IValueConstants._3 ), FighterBlue,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( IValueConstants._3 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.25" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._18 ), FighterRed,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( IValueConstants._18 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "7", fightListA.get( IValueConstants._18 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( IValueConstants._8 ), FighterBlue,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( IValueConstants._8 ).getPointsBlue() ) );
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
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.57" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._18 ), FighterBlue,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( IValueConstants._18 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( IValueConstants._0 ), FighterRed,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( IValueConstants._0 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.5" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._19 ), FighterRed,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( IValueConstants._19 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "9", fightListA.get( IValueConstants._19 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "61", fightListA.get( IValueConstants._0 ), editAccess,
                                       linkToFight, 31, PLAIN, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( IValueConstants._9 ), FighterRed,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( IValueConstants._9 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.37" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._19 ), FighterBlue,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( IValueConstants._19 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "37", fightListA.get( IValueConstants._9 ), editAccess,
                                       linkToFight, 3, PLAIN, context );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( IValueConstants._4 ), FighterRed,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( IValueConstants._4 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.21" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._20 ), FighterRed,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( IValueConstants._20 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "11", fightListA.get( IValueConstants._20 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "51", fightListA.get( IValueConstants._4 ), editAccess,
                                       linkToFight, 7, PLAIN, context );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( IValueConstants._9 ), FighterBlue,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( IValueConstants._9 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.53" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._20 ), FighterBlue,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( IValueConstants._20 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( IValueConstants._1 ), FighterBlue,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( IValueConstants._1 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.13" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._21 ), FighterRed,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( IValueConstants._21 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "13", fightListA.get( IValueConstants._21 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( IValueConstants._10 ), FighterRed,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( IValueConstants._10 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.45" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._21 ), FighterBlue,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( IValueConstants._21 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "39", fightListA.get( IValueConstants._10 ), editAccess,
                                       linkToFight, 3, PLAIN, context );
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
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( IValueConstants._10 ),
                     FighterBlue, PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( IValueConstants._4 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.29" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._22 ), FighterRed,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( IValueConstants._22 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "15", fightListA.get( IValueConstants._22 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( IValueConstants._10 ),
                     FighterBlue, PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( IValueConstants._10 ).getPointsBlue() ) );
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
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.61" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._22 ), FighterBlue,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( IValueConstants._22 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // -----------------------------------------------------------------------------------------
        // middle - 32 upper part
        // -----------------------------------------------------------------------------------------
        out.startElement( JSF_TR, component );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "11", null );
        out.writeAttribute( ATTR_BGCOLOR, "#CCCCCC", null );
        writeText( out, TypeUtil.STRING_DEFAULT );
        out.endElement( JSF_TD );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, finalFight, FighterRed, BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM, String.valueOf( finalFight.getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );
        // -----------------------------------------------------------------------------------------
        // end middle -32
        // -----------------------------------------------------------------------------------------

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.3" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._23 ), FighterRed,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( IValueConstants._23 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "17", fightListA.get( IValueConstants._23 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "123", finalFight, editAccess,
                                       linkToFight, 63, PLAIN, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( IValueConstants._11 ), FighterRed,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( IValueConstants._11 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.35" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._23 ), FighterBlue,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( IValueConstants._23 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "41", fightListA.get( IValueConstants._11 ), editAccess,
                                       linkToFight, 3, PLAIN, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( IValueConstants._5 ), FighterRed,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( IValueConstants._5 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.19" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._24 ), FighterRed,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( IValueConstants._24 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "19", fightListA.get( IValueConstants._24 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "53", fightListA.get( IValueConstants._5 ), editAccess,
                                       linkToFight, 7, PLAIN, context );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( IValueConstants._11 ),
                     FighterBlue, PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( IValueConstants._11 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.51" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._24 ), FighterBlue,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( IValueConstants._24 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
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
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( IValueConstants._2 ), FighterRed,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( IValueConstants._2 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.11" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._25 ), FighterRed,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( IValueConstants._25 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "21", fightListA.get( IValueConstants._25 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "59", fightListA.get( IValueConstants._2 ), editAccess,
                                       linkToFight, 15, PLAIN, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( IValueConstants._12 ), FighterRed,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( IValueConstants._12 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.43" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._25 ), FighterBlue,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( IValueConstants._25 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "43", fightListA.get( IValueConstants._12 ), editAccess,
                                       linkToFight, 3, PLAIN, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( IValueConstants._5 ), FighterBlue,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( IValueConstants._5 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.27" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._26 ), FighterRed,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( IValueConstants._26 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "23", fightListA.get( IValueConstants._26 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( IValueConstants._12 ),
                     FighterBlue, PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( IValueConstants._12 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.59" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._26 ), FighterBlue,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( IValueConstants._26 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( IValueConstants._0 ), FighterBlue,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( IValueConstants._0 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.7" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._27 ), FighterRed,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( IValueConstants._27 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "25", fightListA.get( IValueConstants._27 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( IValueConstants._13 ), FighterRed,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( IValueConstants._13 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.39" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._27 ), FighterBlue,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( IValueConstants._27 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "45", fightListA.get( IValueConstants._13 ), editAccess,
                                       linkToFight, 3, PLAIN, context );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( IValueConstants._6 ), FighterRed,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( IValueConstants._6 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.23" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._28 ), FighterRed,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( IValueConstants._28 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "27", fightListA.get( IValueConstants._28 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "55", fightListA.get( IValueConstants._6 ), editAccess,
                                       linkToFight, 7, PLAIN, context );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( IValueConstants._13 ),
                     FighterBlue, PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( IValueConstants._13 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.55" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._28 ), FighterBlue,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( IValueConstants._28 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( IValueConstants._2 ), FighterBlue,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( IValueConstants._2 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.15" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._29 ), FighterRed,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( IValueConstants._29 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "47", fightListA.get( IValueConstants._29 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( IValueConstants._14 ), FighterRed,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( IValueConstants._14 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.47" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._29 ), FighterBlue,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( IValueConstants._29 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "29", fightListA.get( IValueConstants._14 ), editAccess,
                                       linkToFight, 3, PLAIN, context );
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
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( IValueConstants._6 ), FighterBlue,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( IValueConstants._6 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.31" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._30 ), FighterRed,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( IValueConstants._30 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "31", fightListA.get( IValueConstants._30 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( IValueConstants._14 ),
                     FighterBlue, PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListA.get( IValueConstants._14 ).getPointsBlue() ) );
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
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.63" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListA.get( IValueConstants._30 ), FighterBlue,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListA.get( IValueConstants._30 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
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
        // middle Part of 64 - duplicate 32 table below -- ended doubling up
        // here
        // -----------------------------------------------------------------------------------------

        // -----------------------------------------------------------------------------------------
        // middle
        // -----------------------------------------------------------------------------------------
        out.startElement( JSF_TR, component );
        out.startElement( JSF_TD, component );
        out.writeAttribute( ATTR_COLSPAN, "13", null );
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

        // -----------------------------------------------------------------------------------------
        // end middle Part of 64 - duplicate 32 table below
        // -----------------------------------------------------------------------------------------

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.2" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._15 ), FighterRed,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( IValueConstants._15 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "1", fightListB.get( IValueConstants._15 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( IValueConstants._7 ), FighterRed,
                     BORDER_BOTTOM );

        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( IValueConstants._7 ).getPointsRed() ) );

        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.34" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._15 ), FighterBlue,
                     BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( IValueConstants._15 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "34", fightListB.get( IValueConstants._7 ), editAccess,
                                       linkToFight, 3, PLAIN, context );

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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( IValueConstants._3 ), FighterRed,
                     BORDER_BOTTOM );

        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListB.get( IValueConstants._3 ).getPointsRed() ) );

        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        // row 5
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.18" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._16 ), FighterRed,
                     BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP,
                     String.valueOf( fightListB.get( IValueConstants._16 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "4", fightListB.get( IValueConstants._16 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "50", fightListB.get( IValueConstants._3 ), editAccess,
                                       linkToFight, 7, PLAIN, context );

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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( IValueConstants._7 ), FighterBlue,
                     BORDER_TOP );

        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP,
                     String.valueOf( fightListB.get( IValueConstants._7 ).getPointsBlue() ) );

        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );

        out.endElement( JSF_TR );

        // row 7
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.50" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._16 ), FighterBlue,
                     BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListB.get( IValueConstants._16 ).getPointsBlue() ) );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( IValueConstants._1 ), FighterRed,
                     BORDER_BOTTOM );

        doTableCell( component, out, _15, VAL_CENTER, BORDER_BOTTOM,
                     String.valueOf( fightListB.get( IValueConstants._1 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );

        out.endElement( JSF_TR );

        // row 9
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.10" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._17 ), FighterRed,
                     BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( IValueConstants._17 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "6", fightListB.get( IValueConstants._17 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "58", fightListB.get( IValueConstants._1 ), editAccess,
                                       linkToFight, 15, PLAIN, context );

        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );

        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( IValueConstants._8 ), FighterRed,
                     PLAIN | BORDER_BOTTOM );

        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( IValueConstants._8 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );

        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.41" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._17 ), FighterBlue,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( IValueConstants._17 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "36", fightListB.get( IValueConstants._8 ), editAccess,
                                       linkToFight, 3, PLAIN, context );

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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( IValueConstants._3 ), FighterBlue,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( IValueConstants._3 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );

        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.26" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._18 ), FighterRed,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( IValueConstants._18 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "8", fightListB.get( IValueConstants._18 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( IValueConstants._8 ), FighterBlue,
                     PLAIN | BORDER_TOP );

        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( IValueConstants._8 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );

        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.58" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._18 ), FighterBlue,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( IValueConstants._18 ).getPointsBlue() ) );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( IValueConstants._0 ), FighterRed,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( IValueConstants._0 ).getPointsRed() ) );

        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );

        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.6" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._19 ), FighterRed,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( IValueConstants._19 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "10", fightListB.get( IValueConstants._19 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "62", fightListB.get( IValueConstants._0 ), editAccess,
                                       linkToFight, 31, PLAIN, context );

        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( IValueConstants._9 ), FighterRed,
                     PLAIN | BORDER_BOTTOM );

        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( IValueConstants._9 ).getPointsRed() ) );

        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );

        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.38" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._19 ), FighterBlue,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( IValueConstants._19 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "38", fightListB.get( IValueConstants._9 ), editAccess,
                                       linkToFight, 3, PLAIN, context );

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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( IValueConstants._4 ), FighterRed,
                     PLAIN | BORDER_BOTTOM );

        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( IValueConstants._4 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );

        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.22" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._20 ), FighterRed,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( IValueConstants._20 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "12", fightListB.get( IValueConstants._20 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "52", fightListB.get( IValueConstants._4 ), editAccess,
                                       linkToFight, 7, PLAIN, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( IValueConstants._9 ), FighterBlue,
                     PLAIN | BORDER_TOP );

        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( IValueConstants._9 ).getPointsBlue() ) );

        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );

        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.54" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._20 ), FighterBlue,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( IValueConstants._20 ).getPointsBlue() ) );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( IValueConstants._1 ), FighterBlue,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( IValueConstants._1 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );

        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.14" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._21 ), FighterRed,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( IValueConstants._21 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "14", fightListB.get( IValueConstants._21 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( IValueConstants._10 ), FighterRed,
                     PLAIN | BORDER_BOTTOM );

        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( IValueConstants._10 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );

        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.46" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._21 ), FighterBlue,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( IValueConstants._21 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "40", fightListB.get( IValueConstants._10 ), editAccess,
                                       linkToFight, 3, PLAIN, context );

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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( IValueConstants._4 ), FighterBlue,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( IValueConstants._4 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );

        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.30" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._22 ), FighterRed,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( IValueConstants._22 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "16", fightListB.get( IValueConstants._22 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( IValueConstants._10 ),
                     FighterBlue, PLAIN | BORDER_TOP );

        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( IValueConstants._10 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );

        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.62" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._22 ), FighterBlue,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( IValueConstants._22 ).getPointsBlue() ) );
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
        writeText( out, TypeUtil.STRING_DEFAULT );
        out.endElement( JSF_TD );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, finalFight, FighterBlue, BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, BORDER_TOP, String.valueOf( finalFight.getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );
        // -----------------------------------------------------------------------------------------
        // end middle
        // -----------------------------------------------------------------------------------------

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.4" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._23 ), FighterRed,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( IValueConstants._23 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "18", fightListB.get( IValueConstants._23 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( IValueConstants._11 ), FighterRed,
                     PLAIN | BORDER_BOTTOM );

        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( IValueConstants._11 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );

        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.36" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._23 ), FighterBlue,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( IValueConstants._23 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "42", fightListB.get( IValueConstants._11 ), editAccess,
                                       linkToFight, 3, PLAIN, context );

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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( IValueConstants._5 ), FighterRed,
                     PLAIN | BORDER_BOTTOM );

        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( IValueConstants._5 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );

        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.20" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._24 ), FighterRed,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( IValueConstants._24 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "20", fightListB.get( IValueConstants._24 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "54", fightListB.get( IValueConstants._5 ), editAccess,
                                       linkToFight, 7, PLAIN, context );

        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );

        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( IValueConstants._11 ),
                     FighterBlue, PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( IValueConstants._11 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );

        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.52" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._24 ), FighterBlue,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( IValueConstants._24 ).getPointsBlue() ) );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( IValueConstants._2 ), FighterRed,
                     PLAIN | BORDER_BOTTOM );

        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( IValueConstants._2 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );

        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.12" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._25 ), FighterRed,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( IValueConstants._25 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "22", fightListB.get( IValueConstants._25 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "60", fightListB.get( IValueConstants._2 ), editAccess,
                                       linkToFight, 15, PLAIN, context );

        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( IValueConstants._12 ), FighterRed,
                     PLAIN | BORDER_BOTTOM );

        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( IValueConstants._12 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );

        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.44" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._25 ), FighterBlue,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( IValueConstants._25 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "44", fightListB.get( IValueConstants._12 ), editAccess,
                                       linkToFight, 3, PLAIN, context );

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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( IValueConstants._5 ), FighterBlue,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( IValueConstants._5 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );

        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.28" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._26 ), FighterRed,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( IValueConstants._26 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "24", fightListB.get( IValueConstants._26 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( IValueConstants._12 ),
                     FighterBlue,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( IValueConstants._12 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );

        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.60" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._26 ), FighterBlue,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( IValueConstants._26 ).getPointsBlue() ) );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( IValueConstants._0 ), FighterBlue,
                     PLAIN | BORDER_TOP );

        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( IValueConstants._0 ).getPointsBlue() ) );

        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.8" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._27 ), FighterRed,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( IValueConstants._27 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "26", fightListB.get( IValueConstants._27 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( IValueConstants._13 ), FighterRed,
                     PLAIN | BORDER_BOTTOM );

        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( IValueConstants._13 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );

        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.40" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._27 ), FighterBlue,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( IValueConstants._27 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "46", fightListB.get( IValueConstants._13 ), editAccess,
                                       linkToFight, 3, PLAIN, context );

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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( IValueConstants._6 ), FighterRed,
                     PLAIN | BORDER_BOTTOM );

        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( IValueConstants._6 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );

        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.24" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._28 ), FighterRed,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( IValueConstants._28 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "28", fightListB.get( IValueConstants._28 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "56", fightListB.get( IValueConstants._6 ), editAccess,
                                       linkToFight, 7, PLAIN, context );

        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _150, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( IValueConstants._13 ),
                     FighterBlue, PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( IValueConstants._13 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );

        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.56" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._28 ), FighterBlue,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( IValueConstants._28 ).getPointsBlue() ) );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( IValueConstants._2 ), FighterBlue,
                     PLAIN | BORDER_TOP );

        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( IValueConstants._2 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );

        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.16" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._29 ), FighterRed,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( IValueConstants._29 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "30", fightListB.get( IValueConstants._29 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( IValueConstants._14 ), FighterRed,
                     PLAIN | BORDER_BOTTOM );

        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( IValueConstants._14 ).getPointsRed() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );

        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.48" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._29 ), FighterBlue,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( IValueConstants._29 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCellWithRowSpanAndLink( component, out, _15, "48", fightListB.get( IValueConstants._14 ), editAccess,
                                       linkToFight, 3, PLAIN, context );
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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( IValueConstants._6 ), FighterBlue,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( IValueConstants._6 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );

        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, rb.getString( "ko.32" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._30 ), FighterRed,
                     PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( IValueConstants._30 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "32", fightListB.get( IValueConstants._30 ), editAccess,
                                       linkToFight, 3, BORDER_TOP | BORDER_BOTTOM, context );

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
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( IValueConstants._14 ),
                     FighterBlue, PLAIN | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP,
                     String.valueOf( fightListB.get( IValueConstants._14 ).getPointsBlue() ) );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );

        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, rb.getString( "ko.64" ) );
        doTableCell( component, out, _150, VAL_CENTER, FIRSTNAME, fightListB.get( IValueConstants._30 ), FighterBlue,
                     PLAIN | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM,
                     String.valueOf( fightListB.get( IValueConstants._30 ).getPointsBlue() ) );
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
        out.writeAttribute( ATTR_WIDTH, TABLE_WIDTH_KO64_SOLACE,null );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );doTableCell( component, out, _15, VAL_CENTER, PLAIN, null );
        doTableCell( component, out, _100, VAL_CENTER, PLAIN, null );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 14 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListA.get( 14 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "79", fightListA.get( 14 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 21, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 6 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListA.get( 6 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "103", fightListA.get( 6 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 14, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 22 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListA.get( 22 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "63", fightListA.get( 22 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 10 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListA.get( 10 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "95", fightListA.get( 10 ), editAccess, linkToFight, 7,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 14, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 14 ), FighterBlue,2, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListA.get( 14 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 4 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListA.get( 4 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "111", fightListA.get( 4 ), editAccess, linkToFight, 14,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 11, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 6 ), FighterBlue, 2, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListA.get( 6 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 11, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 22 ), FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListA.get( 22 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 1, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 2 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListA.get( 2 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "115", fightListA.get( 2 ), editAccess, linkToFight, 5,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 9, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 7, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 15 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListA.get( 15 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "80", fightListA.get( 15 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 1 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListA.get( 1 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "119", fightListA.get( 1 ), editAccess, linkToFight, 27,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 10 ), FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListA.get( 10 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );
      
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 23 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListA.get( 23 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "64", fightListA.get( 23 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );        
        doTableCellWithRowSpan( component, out, null, null, 0, 9, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 2 ), FighterBlue, 2, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListA.get( 2 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 15 ), FighterBlue,2, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListA.get( 15 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 9, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 13, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 23 ), FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListA.get( 23 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 13, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 16, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 16 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListA.get( 16 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "81", fightListA.get( 16 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 7 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListA.get( 7 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "104", fightListA.get( 7 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 24 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListA.get( 24 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "65", fightListA.get( 24 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 11 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListA.get( 11 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "96", fightListA.get( 11 ), editAccess, linkToFight, 7,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 4 ), FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListA.get( 4 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 16 ), 2,FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListA.get( 16 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 9, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 7 ), FighterBlue, 2, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListA.get( 7 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 9, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 1, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 0 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListA.get( 0 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLinkFor3rdPlace( component, out, _15, "121", fightListA.get( 0 ), editAccess, linkToFight, 3,  BORDER_BOTTOM | BORDER_TOP, context, fightListA, fightListB );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 26 ), FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListA.get( 26 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 9, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 8, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        if ( TypeUtil.LONG_EMPTY != fightListA.get( 0 ).getWinnerId() )
        {
            if ( fightListA.get( 0 ).getFighterIdRed() == fightListA.get( 0 ).getWinnerId() )            
                doTableCell( component, out, _100, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER, fightListA.get( 0 ).getFighterRed().getName() );         
            else
            {
                if ( fightListA.get( 0 ).getFighterIdBlue() == fightListA.get( 0 ).getWinnerId() )               
                    doTableCell( component, out, _100, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER, fightListA.get( 0 ).getFighterBlue().getName() );                
                else                
                    doTableCell( component, out, _100, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER,  TypeUtil.STRING_DEFAULT );                
            }
        }
        else
        {
            doTableCell( component, out, _100, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER, TypeUtil.STRING_DEFAULT );
        }
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 9, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 13, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 0 ), FighterBlue, 2, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListA.get( 0 ).getPointsBlue() ) );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 17 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListA.get( 17 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "82", fightListA.get( 17 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 13, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 11 ), FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListA.get( 11 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 13, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );
        
        
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 25 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListA.get( 25 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "66", fightListA.get( 25 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 16, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 17 ), FighterBlue, 2, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListA.get( 17 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 16, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 20, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 25 ), FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListA.get( 25 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 20, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 23, null, 0 ); // quater line
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 18 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListA.get( 18 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "83", fightListA.get( 18 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 16, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 8 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListA.get( 8 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "105", fightListA.get( 8 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 9, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 26 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListA.get( 26 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "67", fightListA.get( 26 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 12 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListA.get( 12 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "97", fightListA.get( 12 ), editAccess, linkToFight, 7,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 9, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 18 ), FighterBlue, 2, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListA.get( 18 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 5 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListA.get( 5 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "112", fightListA.get( 5 ), editAccess, linkToFight, 14,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 8 ), FighterBlue, 2, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListA.get( 8 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 1, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 3 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListA.get( 3 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "116", fightListA.get( 3 ), editAccess, linkToFight, 5,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 26 ), FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListA.get( 26 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 1 ), FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListA.get( 1 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 9, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 7, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 19 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListA.get( 19 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "84", fightListA.get( 19 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 7, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 12 ), FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListA.get( 12 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 3 ), FighterBlue, 2, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListA.get( 3 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 7, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 27 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListA.get( 27 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "68", fightListA.get( 27 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 9, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 11, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 19 ), FighterBlue, 2, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListA.get( 19 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 9, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 11, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 13, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 11, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 27 ), FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListA.get( 27 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 13, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 11, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 16, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 11, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 20 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListA.get( 20 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "85", fightListA.get( 20 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 9, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 11, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 9 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListA.get( 9 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "106", fightListA.get( 9 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 11, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 28 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListA.get( 28 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "69", fightListA.get( 28 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 13 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListA.get( 13 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "98", fightListA.get( 13 ), editAccess, linkToFight, 7,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 5 ), FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListA.get( 5 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 11, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 20 ), FighterBlue, 2, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListA.get( 20 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 14, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 9 ), FighterBlue, 2, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListA.get( 9 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 14, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 28 ), FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListA.get( 28 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 18, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 9, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 18, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 21 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListA.get( 21 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "86", fightListA.get( 21 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 18, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 13 ), FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListA.get( 13 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 18, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 29 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListA.get( 29 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "70", fightListA.get( 29 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 21, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 21 ), FighterBlue, 2, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListA.get( 21 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 21, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 25, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListA.get( 29 ), FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListA.get( 29 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 25, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 28, "", 0 ); // end of 1st looser Round
        out.endElement( JSF_TR );
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 28, "", 0 ); // end of 1st looser Round
        out.endElement( JSF_TR );
        
       ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 14 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListB.get( 14 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "87", fightListB.get( 14 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 21, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 6 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListB.get( 6 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "107", fightListB.get( 6 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 14, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 22 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListB.get( 22 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "71", fightListB.get( 22 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 10 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListB.get( 10 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "99", fightListB.get( 10 ), editAccess, linkToFight, 7,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 14, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 14 ), FighterBlue,2, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListB.get( 14 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 4 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListB.get( 4 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "113", fightListB.get( 4 ), editAccess, linkToFight, 14,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 11, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 6 ), FighterBlue, 2, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListB.get( 6 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 11, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 22 ), FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListB.get( 22 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 1, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 2 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListB.get( 2 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "117", fightListB.get( 2 ), editAccess, linkToFight, 5,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 9, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 7, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 15 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListB.get( 15 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "88", fightListB.get( 15 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 1 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListB.get( 1 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "120", fightListB.get( 1 ), editAccess, linkToFight, 27,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 10 ), FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListB.get( 10 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );
      
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 23 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListB.get( 23 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "72", fightListB.get( 23 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );        
        doTableCellWithRowSpan( component, out, null, null, 0, 9, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 2 ), FighterBlue, 2, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListB.get( 2 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 15 ), FighterBlue,2, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListB.get( 15 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 9, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 13, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 23 ), FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListB.get( 23 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 13, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 16, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 16 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListB.get( 16 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "89", fightListB.get( 16 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 7 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListB.get( 7 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "108", fightListB.get( 7 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 24 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListB.get( 24 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "73", fightListB.get( 24 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 11 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListB.get( 11 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "100", fightListB.get( 11 ), editAccess, linkToFight, 7,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 4 ), FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListB.get( 4 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 16 ), 2,FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListB.get( 16 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 9, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 7 ), FighterBlue, 2, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListB.get( 7 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 9, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 1, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 0 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListB.get( 0 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLinkFor3rdPlace( component, out, _15, "122", fightListB.get( 0 ), editAccess, linkToFight, 3,  BORDER_BOTTOM | BORDER_TOP, context,  fightListA, fightListB );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 26 ), FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListB.get( 26 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 9, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 8, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        if ( TypeUtil.LONG_EMPTY != fightListB.get( 0 ).getWinnerId() )
        {
            if ( fightListB.get( 0 ).getFighterIdRed() == fightListB.get( 0 ).getWinnerId() )            
                doTableCell( component, out, _100, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER, fightListB.get( 0 ).getFighterRed().getName() );         
            else
            {
                if ( fightListB.get( 0 ).getFighterIdBlue() == fightListB.get( 0 ).getWinnerId() )               
                    doTableCell( component, out, _100, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER, fightListB.get( 0 ).getFighterBlue().getName() );                
                else                
                    doTableCell( component, out, _100, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER,  TypeUtil.STRING_DEFAULT );                
            }
        }
        else
        {
            doTableCell( component, out, _100, VAL_CENTER, BORDER_BOTTOM | HIGHLIGHT_WINNER, TypeUtil.STRING_DEFAULT );
        }
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 9, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 13, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 0 ), FighterBlue, 2, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListB.get( 0 ).getPointsBlue() ) );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 17 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListB.get( 17 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "90", fightListB.get( 17 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 13, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 11 ), FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListB.get( 11 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 13, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );
        
        
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 25 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListB.get( 25 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "74", fightListB.get( 25 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 16, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 17 ), FighterBlue, 2, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListB.get( 17 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 16, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 20, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 25 ), FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListB.get( 25 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 20, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 23, null, 0 ); // quater line
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 18 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListB.get( 18 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "91", fightListB.get( 18 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 16, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 8 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListB.get( 8 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "109", fightListB.get( 8 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 9, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 26 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListB.get( 26 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "75", fightListB.get( 26 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 12 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListB.get( 12 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "101", fightListB.get( 12 ), editAccess, linkToFight, 7,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 9, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 18 ), FighterBlue, 2, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListB.get( 18 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 5 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListB.get( 5 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "114", fightListB.get( 5 ), editAccess, linkToFight, 14,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 8 ), FighterBlue, 2, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListB.get( 8 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 1, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 3 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListB.get( 3 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "118", fightListB.get( 3 ), editAccess, linkToFight, 5,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 26 ), FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListB.get( 26 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 1 ), FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListB.get( 1 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 9, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 7, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 19 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListB.get( 19 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "84", fightListB.get( 19 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 7, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 12 ), FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListB.get( 12 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 3 ), FighterBlue, 2, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListB.get( 3 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 7, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 27 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListB.get( 27 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "76", fightListB.get( 27 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 9, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 11, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 19 ), FighterBlue, 2, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListB.get( 19 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 9, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 11, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 13, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 11, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 27 ), FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListB.get( 27 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 13, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 11, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 16, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 11, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 20 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListB.get( 20 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "93", fightListB.get( 20 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 9, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 11, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 9 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListB.get( 9 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "110", fightListB.get( 9 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 11, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 28 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListB.get( 28 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "77", fightListB.get( 28 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 13 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListB.get( 13 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "102", fightListB.get( 13 ), editAccess, linkToFight, 7,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 5 ), FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListB.get( 5 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 11, null, 0 );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 20 ), FighterBlue, 2, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListB.get( 20 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 14, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 9 ), FighterBlue, 2, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListB.get( 9 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 14, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 28 ), FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListB.get( 28 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 18, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 9, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 18, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 4, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 21 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListB.get( 21 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "94", fightListB.get( 21 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 18, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 6, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 13 ), FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListB.get( 13 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 18, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 29 ), FighterRed, PLAIN  | BORDER_TOP );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_TOP, String.valueOf( fightListB.get( 29 ).getPointsRed() ) );
        doTableCellWithRowSpanAndLink( component, out, _15, "78", fightListB.get( 29 ), editAccess, linkToFight, 4,  BORDER_BOTTOM | BORDER_TOP, context );
        doTableCellWithRowSpan( component, out, null, null, 0, 3, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 21, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 21 ), FighterBlue, 2, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListB.get( 21 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 21, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCellWithRowSpan( component, out, null, null, 0, 2, null, 0 );
        doTableCellWithRowSpan( component, out, null, null, 0, 25, null, 0 );
        out.endElement( JSF_TR );
        
        out.startElement( JSF_TR, component );
        doTableCell( component, out, _100, VAL_CENTER, NO_FIRSTNAME, fightListB.get( 29 ), FighterBlue, PLAIN  | BORDER_BOTTOM );
        doTableCell( component, out, _15, VAL_CENTER, PLAIN | BORDER_BOTTOM, String.valueOf( fightListB.get( 29 ).getPointsBlue() ) );
        doTableCellWithRowSpan( component, out, null, null, 0, 25, null, 0 );
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
        writeText( out, rb.getString( "round_of_64" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, component );
        out.writeAttribute( ATTR_WIDTH, "130", null );
        out.writeAttribute( ATTR_COLSPAN, "2", null );
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
