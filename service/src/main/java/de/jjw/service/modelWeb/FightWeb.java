/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightWeb.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:43
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

import de.jjw.model.fighting.Fight;
import de.jjw.util.TypeUtil;

public class FightWeb
    extends Fight
{
    protected FightingclassWeb fightingclass;

    protected String fightReady;

    public FightingclassWeb getFightingclass()
    {
        return fightingclass;
    }

    public void setFightingclass( FightingclassWeb fightingclass )
    {
        this.fightingclass = fightingclass;
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
        if ( getPointsBlue() >= TypeUtil.INT_DEFAULT )
            return String.valueOf( getPointsBlue() );
        else
            return "";
    }

    public String getPointsRedWeb()
    {
        if ( getPointsRed() >= TypeUtil.INT_DEFAULT )
            return String.valueOf( getPointsRed() );
        else
            return "";
    }

}
