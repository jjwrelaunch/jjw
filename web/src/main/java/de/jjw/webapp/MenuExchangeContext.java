/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : MenuExchangeContext.java
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

package de.jjw.webapp;

import java.io.Serializable;


public class MenuExchangeContext
    implements Serializable
{

    public static final int FULL_MENU = 1;

    public static int COLLAPSED_MENU = 2;

    public static int NO_MENU = 3;

    private int eventsMenu;

    private int teamMenu;

    private int statisticMenu;

    /**
     * @return Returns the eventsMenu.
     */
    public int getEventsMenu()
    {
        return eventsMenu;
    }

    /**
     * @param eventsMenu The eventsMenu to set.
     */
    public void setEventsMenu( int eventsMenu )
    {
        this.eventsMenu = eventsMenu;
    }

    /**
     * @return Returns the statisticMenu.
     */
    public int getStatisticMenu()
    {
        return statisticMenu;
    }

    /**
     * @param statisticMenu The statisticMenu to set.
     */
    public void setStatisticMenu( int statisticMenu )
    {
        this.statisticMenu = statisticMenu;
    }

    /**
     * @return Returns the teamMenu.
     */
    public int getTeamMenu()
    {
        return teamMenu;
    }

    /**
     * @param teamMenu The teamMenu to set.
     */
    public void setTeamMenu( int teamMenu )
    {
        this.teamMenu = teamMenu;
    }


}
