/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : ActionUtil.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:45
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

import de.jjw.util.IValueConstants;
import de.jjw.webapp.IGlobalWebConstants;

import java.io.Serializable;

public class ActionUtil
    extends BasePage
    implements Serializable, IGlobalWebConstants, IValueConstants
{

    private String currentMenu;

    public String getCurrentMenu()
    {
        if ( log.isDebugEnabled() )
        {
            log.debug( "MenuDecider.getCurrentMenu" );
        }

        currentMenu = (String) getSession().getAttribute( WEB_CONST_MENU_TYPE );
        if ( currentMenu == null || STRING_NULL.equals( currentMenu ) )
        {
            currentMenu = WEB_CONST_MENU_EXTERN;
        }

        if ( log.isDebugEnabled() )
        {
            log.debug( "MenuDecider.getCurrentMenu exit" );
        }
        return currentMenu;
    }
}
