/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : HtmlConstants.java
 * Created : 17 Jun 2010
 * Last Modified: Thu, 17 Jun 2010 14:59:50
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

package de.jjw.webapp.taglib;

public interface HtmlConstants
{
    public static final String CR_LF = "\n";

    public static final String NBSP = "&nbsp;";

    public static final String BOLD_BEGIN = "<b>";

    public static final String BOLD_END = "</b>";

    public static final String TABLE_BEGIN = "<table>";

    public static final String TABLE_END = "</table>";

    public static final String TABLE_ROW_BEGIN = "<tr>";

    public static final String TABLE_ROW_END = "</tr>";

    public static final String TABLE_CELL_BEGIN = "<td>";

    public static final String TABLE_CELL_END = "</td>";

    public static final String ATTR_HEIGHT = "height";

    public static final String ATTR_WIDTH = "width";

    public static final String ATTR_BORDER = "border";

    public static final String ATTR_BORDERCOLOR = "bordercolor";

    public static final String ATTR_ALIGN = "align";

    public static final String ATTR_COLSPAN = "colspan";

    public static final String ATTR_ROWSPAN = "rowspan";

    public static final String ATTR_HREF = "href";

    public static final String ATTR_BGCOLOR = "bgcolor";

    public static final String ATTR_STYLE = "style";

    public static final String ATTR_CLASS = "class";

    public static final String ATTR_SIZE = "size";

    public static final String ATTR_CELLPADDING = "cellpadding";

    public static final String ATTR_CELLSPACING = "cellspacing";

    public static final String ATTR_ONCONTEXTMENUE = "oncontextmenu";

    public static final String ATTR_ONMOUSEDOWN = "onMouseDown";

    public static final String VAL_CENTER = "center";

    public static final String VAL_LEFT = "left";

    public static final String VAL_RIGHT = "right";

    public static final String JSF_TABLE = "table";

    public static final String JSF_TR = "tr";

    public static final String JSF_TD = "td";

    public static final String JSF_TH = "th";

    public static final String JSF_THEAD = "thead";

    public static final String JSF_BOLD = "b";

    public static final String JSF_PARAGRAPH = "p";

    public static final String JSF_A = "a";

    public static final String JSF_FONT = "font";

    public static final String JSF_BR = "br";

    // String JSF_TABLE = "table";
    // String JSF_TABLE = "table";

    public static final String FANCY_TABLE = "fancyTable";

    public static String TABLE_WIDTH = "800px";

    public static String TABLE_WIDTH_KO32 = "815px";

    public static String TABLE_WIDTH_KO32_SOLACE = "925px";

    public static String TABLE_WIDTH_KO64 = "950px";

    public static String TABLE_WIDTH_KO64_SOLACE = "1000px";

    public static final String TABLE_CELLPADDING = "0";

    public static final String TABLE_CELLSPACING = "0";

    public static final String TABLE_BORDER = "0";

    public static final int FighterRed = 1;

    public static final int FighterBlue = 2;

    public static final boolean NO_FIRSTNAME = false;

    public static final boolean FIRSTNAME = true;

    public static final boolean NO_BOLD = false;

    public static final boolean BOLD = true;

    public static final int PLAIN = 0;

    public static final int HIGHLIGHT_WINNER = 1 << 1;

    public static final int BORDER_BOTTOM = 1 << 2;

    public static final int BORDER_LEFT = 1 << 3;

    public static final int BORDER_RIGHT = 1 << 4;

    public static final int BORDER_TOP = 1 << 5;

    public static final int HIGHLIGHT_FIGHT = 1 << 6;

    public static final String _120 = "120";

    public static final String _100 = "100";

    public static final String _150 = "150";

    public static final String _15 = "15";

    public static final String _31 = "31";

    public static final String COLOR_WHITE = "#FFFFFF";
}
