/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoSimplePoolClass.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:44
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

package de.jjw.model.duo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.jjw.model.admin.Fightsystem;

public class DuoSimplePoolClass
    extends Duoclass
{

    protected List<DuoSimplePoolItem> duoTeamList = null;

    protected Map<Integer, DuoFight> duoFightListMap = new HashMap<Integer, DuoFight>( 11 );


    /**
     * @return the duoTeamList
     */
    public List<DuoSimplePoolItem> getDuoTeamList()
    {
        return duoTeamList;
    }

    /**
     * @param duoTeamList the duoTeamList to set
     */
    public void setDuoTeamList( List<DuoSimplePoolItem> duoTeamList )
    {
        this.duoTeamList = duoTeamList;
    }

    /**
     * @param duoTeamList the fighterList to set
     */
    public void addDuoTeamList( DuoSimplePoolItem duoTeamList )
    {
        if ( this.duoTeamList == null )
        {
            this.duoTeamList = new ArrayList<DuoSimplePoolItem>();
        }
        this.duoTeamList.add( duoTeamList );
    }


    public void putFightListMap( Integer nummer, DuoFight fight )
    {
        if ( this.duoFightListMap == null )
        {
            this.duoFightListMap = new HashMap<Integer, DuoFight>( 11 );
        }
        this.duoFightListMap.put( nummer, fight );
    }

    public Map<Integer, DuoFight> getDuoFightListMap()
    {
        return duoFightListMap;
    }

    public void setDuoFightListMap( Map<Integer, DuoFight> duoFightListMap )
    {
        this.duoFightListMap = duoFightListMap;
    }

    /**
     * @Override
     */
    public int getFightsystem()
    {
        return Fightsystem.SIMPLE_POOL;
    }


    @Override
    public String toString()
    {

        return null;
    }

    @Override
    public boolean equals( Object o )
    {

        return false;
    }

    @Override
    public int hashCode()
    {

        return 0;
    }

}
