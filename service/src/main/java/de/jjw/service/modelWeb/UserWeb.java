/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : UserWeb.java
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

package de.jjw.service.modelWeb;

import de.jjw.model.User;
import de.jjw.service.util.IGlobalProjectConstants;

public class UserWeb
    extends User
    implements IGlobalProjectConstants
{

    private String contextWeb;

    private String enableWeb;

    private String tatamiTimeWeb;


    public String getContextWeb()
    {
        return contextWeb;
    }

    public void setContextWeb( String contextWeb )
    {
        this.contextWeb = contextWeb;
    }

    public String getEnableWeb()
    {
        return enableWeb;
    }

    public void setEnableWeb( String enableWeb )
    {
        this.enableWeb = enableWeb;
    }

    public String getTatamiTimeWeb()
    {
        return tatamiTimeWeb;
    }

    public void setTatamiTimeWeb( String tatamiTimeWeb )
    {
        this.tatamiTimeWeb = tatamiTimeWeb;
    }


}
