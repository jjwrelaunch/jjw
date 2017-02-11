/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaSimplePoolClass.java
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

package de.jjw.model.newa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.jjw.model.admin.Fightsystem;

/**
 * @author joerg.boehme
 */
public class NewaSimplePoolClass
    extends Newaclass
{

    protected List<NewaSimplePoolItem> fighterList = null;

    protected Map<Integer, NewaFight> fightListMap = new HashMap<Integer, NewaFight>( 11 );


    /**
     * ibernate.list table="FightingSimplePoolItem"
     * ibernate.collection-key column="id"
     * ibernate.list-index column = "numberInPool" base="1"
     * <p/>
     * ibernate.collection-one-to-many class="de.jjw.model.fighting.FightingSimplePoolItem" cascade="all"
     *
     * @return the fighterList
     */
    public List<NewaSimplePoolItem> getFighterList()
    {
        return fighterList;
    }

    /**
     * @param fighterList the fighterList to set
     */
    public void setFighterList( List<NewaSimplePoolItem> fighterList )
    {
        this.fighterList = fighterList;
    }

    /**
     * @param fighterList the fighterList to set
     */
    public void addFighterList( NewaSimplePoolItem fighterList )
    {
        if ( this.fighterList == null )
        {
            this.fighterList = new ArrayList<NewaSimplePoolItem>();
        }
        this.fighterList.add( fighterList );
    }


    public void putFightListMap( Integer nummer, NewaFight fight )
    {
        if ( this.fightListMap == null )
        {
            this.fightListMap = new HashMap<Integer, NewaFight>( 11 );
        }
        this.fightListMap.put( nummer, fight );
    }

    public Map<Integer, NewaFight> getFightListMap()
    {
        return fightListMap;
    }

    public void setFightListMap( Map<Integer, NewaFight> fightListMap )
    {
        this.fightListMap = fightListMap;
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
