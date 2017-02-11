/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : WebExchangeContext.java
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

package de.jjw.webapp;

import de.jjw.webapp.action.validation.ErrorElement;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Vector;


public class WebExchangeContext
    implements Serializable
{

    private long userId;

    private int userType;

    private long actualEvent;

    private HashMap tempAttribute = null;

    private MenuExchangeContext menu;

    private Vector<ErrorElement> errors = new Vector<ErrorElement>();

    /**
     * @return Returns the tempAttribute.
     */
    public HashMap getTempAttribute()
    {
        return tempAttribute;
    }

    /**
     * @param tempAttribute The tempAttribute to set.
     */
    public void setTempAttribute( HashMap tempAttribute )
    {
        this.tempAttribute = tempAttribute;
    }

    /**
     * @return Returns the userId.
     */
    public long getUserId()
    {
        return userId;
    }

    /**
     * @param userId The userId to set.
     */
    public void setUserId( long userId )
    {
        this.userId = userId;
    }

    /**
     * @return Returns the userType.
     */
    public int getUserType()
    {
        return userType;
    }

    /**
     * @param userType The userType to set.
     */
    public void setUserType( int userType )
    {
        this.userType = userType;
    }

    /**
     * @return Returns the actualEvent.
     */
    public long getActualEvent()
    {
        return actualEvent;
    }

    /**
     * @param actualEvent The actualEvent to set.
     */
    public void setActualEvent( long actualEvent )
    {
        this.actualEvent = actualEvent;
    }

    /**
     * @return Returns the menu.
     */
    public MenuExchangeContext getMenu()
    {
        return menu;
    }

    /**
     * @param menu The menu to set.
     */
    public void setMenu( MenuExchangeContext menu )
    {
        this.menu = menu;
    }

    public void addErrorElement( ErrorElement error )
    {
        this.errors.add( error );
    }

    public void addErrorVector( Vector<ErrorElement> error )
    {
        this.errors.addAll( error );
    }

    public void clearErrors()
    {
        errors.clear();
    }

    public boolean isErrors()
    {
        return errors.size() > 0;
    }

    public Vector<ErrorElement> getErrorVector()
    {
        return errors;
    }
}
