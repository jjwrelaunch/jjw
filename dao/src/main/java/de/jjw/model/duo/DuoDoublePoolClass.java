/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoDoublePoolClass.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:41
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

import de.jjw.model.admin.Fightsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DuoDoublePoolClass
    extends Duoclass
{

    protected List<DuoDoublePoolItem> duoListPoolA = null;

    protected Map<Integer, DuoFight> fightListMapPoolA = null;

    protected List<DuoDoublePoolItem> duoListPoolB = null;

    protected Map<Integer, DuoFight> fightListMapPoolB = null;

    protected DuoFight finalFight;
        
    protected DuoFight halfFinalFight1;
    
    protected DuoFight halfFinalFight2;
    
    protected Map<Long,ArrayList<DuoTeam>> resultMap = new HashMap<Long,ArrayList<DuoTeam>>();


    public List<DuoDoublePoolItem> getDuoListPoolA()
    {
        return duoListPoolA;
    }

    /**
     * @param fighterList the fighterList to set
     */
    public void setDuoListPoolA( List<DuoDoublePoolItem> duoListPoolA )
    {
        this.duoListPoolA = duoListPoolA;
    }

    /**
     * @param fighterList the fighterList to set
     */
    public void addDuoListPoolA( DuoDoublePoolItem duoListPoolA )
    {
        if ( this.duoListPoolA == null )
        {
            this.duoListPoolA = new ArrayList<DuoDoublePoolItem>();
        }
        this.duoListPoolA.add( duoListPoolA );
    }

    public void putFightListMapPoolA( Integer nummer, DuoFight fight )
    {
        if ( this.fightListMapPoolA == null )
        {
            this.fightListMapPoolA = new HashMap<Integer, DuoFight>( 11 );
        }
        this.fightListMapPoolA.put( nummer, fight );
    }

    public Map<Integer, DuoFight> getFightListMapPoolA()
    {
        return fightListMapPoolA;
    }

    public void setFightListMapPoolA( Map<Integer, DuoFight> fightListMapPoolA )
    {
        this.fightListMapPoolA = fightListMapPoolA;
    }

    /**
     * @Override
     */
    public int getFightsystem()
    {
        return Fightsystem.DOUBLE_POOL;
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

    public List<DuoDoublePoolItem> getDuoListPoolB()
    {
        return duoListPoolB;
    }

    /**
     * @param fighterList the fighterList to set
     */
    public void setDuoListPoolB( List<DuoDoublePoolItem> duoListPoolB )
    {
        this.duoListPoolB = duoListPoolB;
    }

    /**
     * @param fighterList the fighterList to set
     */
    public void addDuoListPoolB( DuoDoublePoolItem duoListPoolB )
    {
        if ( this.duoListPoolB == null )
        {
            this.duoListPoolB = new ArrayList<DuoDoublePoolItem>();
        }
        this.duoListPoolB.add( duoListPoolB );
    }

    public void putFightListMapPoolB( Integer nummer, DuoFight fight )
    {
        if ( this.fightListMapPoolB == null )
        {
            this.fightListMapPoolB = new HashMap<Integer, DuoFight>( 11 );
        }
        this.fightListMapPoolB.put( nummer, fight );
    }

    public Map<Integer, DuoFight> getFightListMapPoolB()
    {
        return fightListMapPoolB;
    }

    public void setFightListMapPoolB( Map<Integer, DuoFight> fightListMapPoolB )
    {
        this.fightListMapPoolB = fightListMapPoolB;
    }

    public DuoFight getFinalFight()
    {
        return finalFight;
    }

    public void setFinalFight( DuoFight finalFight )
    {
        this.finalFight = finalFight;
    }

    public DuoFight getHalfFinalFight1()
    {
        return halfFinalFight1;
    }

    public void setHalfFinalFight1( DuoFight halfFinalFight1 )
    {
        this.halfFinalFight1 = halfFinalFight1;
    }

    public DuoFight getHalfFinalFight2()
    {
        return halfFinalFight2;
    }

    public void setHalfFinalFight2( DuoFight halfFinalFight2 )
    {
        this.halfFinalFight2 = halfFinalFight2;
    }

    public Map<Long, ArrayList<DuoTeam>> getResultMap()
    {
        return resultMap;
    }

    public void setResultMap( Map<Long, ArrayList<DuoTeam>> resultMap )
    {
        this.resultMap = resultMap;
    }


}
