/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : TeamParticipantDto.java
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

package de.jjw.util.dto;

import java.io.Serializable;

public class TeamParticipantDto
    implements Serializable
{
    private String teamname;

    private long teamId;

    private int fighterCount;

    private int duoCount;


    public TeamParticipantDto( String teamname, int fighterCount, int duoCount, long teamId )
    {
        super();
        this.teamname = teamname;
        this.fighterCount = fighterCount;
        this.duoCount = duoCount;
        this.teamId = teamId;
    }

    public String getTeamname()
    {
        return teamname;
    }

    public void setTeamname( String teamname )
    {
        this.teamname = teamname;
    }

    public int getFighterCount()
    {
        return fighterCount;
    }

    public void setFighterCount( int fighterCount )
    {
        this.fighterCount = fighterCount;
    }

    public int getDuoCount()
    {
        return duoCount;
    }

    public void setDuoCount( int duoCount )
    {
        this.duoCount = duoCount;
    }

    /**
     * @return the teamId
     */
    public long getTeamId()
    {
        return teamId;
    }

    /**
     * @param teamId the teamId to set
     */
    public void setTeamId( long teamId )
    {
        this.teamId = teamId;
    }

}
