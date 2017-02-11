/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : ConfigMain.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:46
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

package de.jjw.webapp.util;

import org.apache.log4j.Logger;

import de.jjw.model.admin.ConfigJJW;

public class ConfigMain
{

    protected final Logger log = Logger.getRootLogger();

    private static ConfigMain instance = new ConfigMain();

    private static ConfigJJW eventConfiguration = new ConfigJJW();

    public static ConfigMain getInstance()
    {
        return instance;
    }

    private ConfigMain()
    {

    }

    public ConfigJJW getEventConfiguration()
    {
        return eventConfiguration;
    }

    public void setEventConfiguration( ConfigJJW eventConfiguration )
    {
        ConfigMain.eventConfiguration = eventConfiguration;
    }


}
