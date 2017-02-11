/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : Reload.java
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

package de.jjw.webapp.action;

import de.jjw.webapp.listener.StartupListener;

import java.io.IOException;
import java.io.Serializable;

/**
 * JSF Page class to handle reloading options in servlet context.
 */
public class Reload
    extends BasePage
    implements Serializable
{

    public String execute()
        throws IOException
    {

        if ( log.isDebugEnabled() )
        {
            log.debug( "Entering 'execute' method" );
        }

        StartupListener.setupContext( getServletContext() );
        addMessage( "reload.succeeded" );

        return "mainMenu";
    }

}
