/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoFightWeb.java
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

package de.jjw.service.modelWeb;

import de.jjw.model.duo.DuoFight;
import de.jjw.util.TypeUtil;

public class DuoFightWeb
    extends DuoFight
{
    protected DuoclassWeb duoclass;

    protected String fightReady;

    @Override
    public DuoclassWeb getDuoclass()
    {
        return duoclass;
    }

    public void setDuoclass( DuoclassWeb duoclass )
    {
        this.duoclass = duoclass;
    }

    /**
     * @return the fightReady
     */
    public String getFightReady()
    {
        return fightReady;
    }

    /**
     * @param fightReady the fightReady to set
     */
    public void setFightReady( String fightReady )
    {
        this.fightReady = fightReady;
    }

    public String getPointsBlueWeb()
    {
        if ( getPointsBlueMax() >= TypeUtil.DOUBLE_DEFAULT )
            return String.valueOf( getPointsBlueMax() );
        else
            return "";
    }

    public String getPointsRedWeb()
    {
        if ( getPointsRedMax() >= TypeUtil.DOUBLE_DEFAULT )
            return String.valueOf( getPointsRedMax() );
        else
            return "";
    }

}
