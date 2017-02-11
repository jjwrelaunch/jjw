/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightingDoublePoolClass.java
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

package de.jjw.model.fighting;

import de.jjw.model.admin.Fightsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FightingDoublePoolClass
    extends Fightingclass
{

    protected List<FightingDoublePoolItem> fighterListPoolA = null;

    protected Map<Integer, Fight> fightListMapPoolA = null;

    protected List<FightingDoublePoolItem> fighterListPoolB = null;

    protected Map<Integer, Fight> fightListMapPoolB = null;

    protected Fight finalFight;
    
    protected Fight halfFinalFight1;
    
    protected Fight halfFinalFight2;
    
    protected Map<Long,ArrayList<Fighter>> resultMap = new HashMap<Long,ArrayList<Fighter>>();

    public List<FightingDoublePoolItem> getFighterListPoolA()
    {
        return fighterListPoolA;
    }

    /**
     * @param fighterList the fighterList to set
     */
    public void setFighterListPoolA( List<FightingDoublePoolItem> fighterListPoolA )
    {
        this.fighterListPoolA = fighterListPoolA;
    }

    /**
     * @param fighterList the fighterList to set
     */
    public void addFighterListPoolA( FightingDoublePoolItem fighterListPoolA )
    {
        if ( this.fighterListPoolA == null )
        {
            this.fighterListPoolA = new ArrayList<FightingDoublePoolItem>();
        }
        this.fighterListPoolA.add( fighterListPoolA );
    }

    public void putFightListMapPoolA( Integer nummer, Fight fight )
    {
        if ( this.fightListMapPoolA == null )
        {
            this.fightListMapPoolA = new HashMap<Integer, Fight>( 11 );
        }
        this.fightListMapPoolA.put( nummer, fight );
    }

    public Map<Integer, Fight> getFightListMapPoolA()
    {
        return fightListMapPoolA;
    }

    public void setFightListMapPoolA( Map<Integer, Fight> fightListMapPoolA )
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

    public List<FightingDoublePoolItem> getFighterListPoolB()
    {
        return fighterListPoolB;
    }

    /**
     * @param fighterList the fighterList to set
     */
    public void setFighterListPoolB( List<FightingDoublePoolItem> fighterListPoolB )
    {
        this.fighterListPoolB = fighterListPoolB;
    }

    /**
     * @param fighterList the fighterList to set
     */
    public void addFighterListPoolB( FightingDoublePoolItem fighterListPoolB )
    {
        if ( this.fighterListPoolB == null )
        {
            this.fighterListPoolB = new ArrayList<FightingDoublePoolItem>();
        }
        this.fighterListPoolB.add( fighterListPoolB );
    }

    public void putFightListMapPoolB( Integer nummer, Fight fight )
    {
        if ( this.fightListMapPoolB == null )
        {
            this.fightListMapPoolB = new HashMap<Integer, Fight>( 11 );
        }
        this.fightListMapPoolB.put( nummer, fight );
    }

    public Map<Integer, Fight> getFightListMapPoolB()
    {
        return fightListMapPoolB;
    }

    public void setFightListMapPoolB( Map<Integer, Fight> fightListMapPoolB )
    {
        this.fightListMapPoolB = fightListMapPoolB;
    }

    public Fight getFinalFight()
    {
        return finalFight;
    }

    public void setFinalFight( Fight finalFight )
    {
        this.finalFight = finalFight;
    }

    public Fight getHalfFinalFight1()
    {
        return halfFinalFight1;
    }

    public void setHalfFinalFight1( Fight halfFinalFight1 )
    {
        this.halfFinalFight1 = halfFinalFight1;
    }

    public Fight getHalfFinalFight2()
    {
        return halfFinalFight2;
    }

    public void setHalfFinalFight2( Fight halfFinalFight2 )
    {
        this.halfFinalFight2 = halfFinalFight2;
    }

    public Map<Long,ArrayList<Fighter>> getResultMap()
    {
        return resultMap;
    }

    public void setResultMap( Map<Long,ArrayList<Fighter>> resultMap )
    {
        this.resultMap = resultMap;
    }

    

}
