/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : ServiceExchangeContext.java
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

package de.jjw.service;

import java.util.HashMap;

import de.jjw.util.TypeUtil;

public class ServiceExchangeContext
{

    private long userId = TypeUtil.LONG_MIN;

    private HashMap parameter = null;


    public ServiceExchangeContext( long userId )
    {
        super();
        this.userId = userId;
    }

    public ServiceExchangeContext( long userId, HashMap parameter )
    {
        super();
        this.userId = userId;
        this.parameter = parameter;
    }

    public long getUserId()
    {
        return userId;
    }

    public void setUserId( long userId )
    {
        this.userId = userId;
    }

    public HashMap getParameter()
    {
        return parameter;
    }

    public void setParameter( HashMap parameter )
    {
        this.parameter = parameter;
    }


}
