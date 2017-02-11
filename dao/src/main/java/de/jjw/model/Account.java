/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : Account.java
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

package de.jjw.model;

import de.jjw.util.TypeUtil;

import java.io.Serializable;

public class Account
    extends BaseObject
    implements Serializable
{

    protected boolean loginAllowed = false;          // no mapping from or to DB

    protected boolean tooMuchWrongTry = false;

    protected boolean loginLocked = false;

    protected boolean newLogin = false;

    protected Long userId = TypeUtil.LONG_OBJECT_EMPTY;

    @Override
    public String toString()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean equals( Object o )
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int hashCode()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @return Returns the loginAllowed.
     */
    public boolean isLoginAllowed()
    {
        return loginAllowed;
    }

    /**
     * @param loginAllowed The loginAllowed to set.
     */
    public void setLoginAllowed( boolean loginAllowed )
    {
        this.loginAllowed = loginAllowed;
    }

    /**
     * @return Returns the loginLocked.
     */
    public boolean isLoginLocked()
    {
        return loginLocked;
    }

    /**
     * @param loginLocked The loginLocked to set.
     */
    public void setLoginLocked( boolean loginLocked )
    {
        this.loginLocked = loginLocked;
    }

    /**
     * @return Returns the newLogin.
     */
    public boolean isNewLogin()
    {
        return newLogin;
    }

    /**
     * @param newLogin The newLogin to set.
     */
    public void setNewLogin( boolean newLogin )
    {
        this.newLogin = newLogin;
    }

    /**
     * @return Returns the tooMuchWrongTry.
     */
    public boolean isTooMuchWrongTry()
    {
        return tooMuchWrongTry;
    }

    /**
     * @param tooMuchWrongTry The tooMuchWrongTry to set.
     */
    public void setTooMuchWrongTry( boolean tooMuchWrongTry )
    {
        this.tooMuchWrongTry = tooMuchWrongTry;
    }

    /**
     * @return Returns the userId.
     */
    public Long getUserId()
    {
        return userId;
    }

    /**
     * @param userId The userId to set.
     */
    public void setUserId( Long userId )
    {
        this.userId = userId;
    }


}
