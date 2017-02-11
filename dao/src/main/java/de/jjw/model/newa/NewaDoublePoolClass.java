/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaDoublePoolClass.java
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

public class NewaDoublePoolClass
    extends Newaclass
{

    protected List<NewaDoublePoolItem> fighterListPoolA = null;

    protected Map<Integer, NewaFight> fightListMapPoolA = null;

    protected List<NewaDoublePoolItem> fighterListPoolB = null;

    protected Map<Integer, NewaFight> fightListMapPoolB = null;

    protected NewaFight finalFight;
    
    protected NewaFight halfFinalFight1;
    
    protected NewaFight halfFinalFight2;
    
    protected Map<Long,ArrayList<NewaFighter>> resultMap = new HashMap<Long,ArrayList<NewaFighter>>();

    public List<NewaDoublePoolItem> getFighterListPoolA()
    {
        return fighterListPoolA;
    }

    /**
     * @param fighterList the fighterList to set
     */
    public void setFighterListPoolA( List<NewaDoublePoolItem> fighterListPoolA )
    {
        this.fighterListPoolA = fighterListPoolA;
    }

    /**
     * @param fighterList the fighterList to set
     */
    public void addFighterListPoolA( NewaDoublePoolItem fighterListPoolA )
    {
        if ( this.fighterListPoolA == null )
        {
            this.fighterListPoolA = new ArrayList<NewaDoublePoolItem>();
        }
        this.fighterListPoolA.add( fighterListPoolA );
    }

    public void putFightListMapPoolA( Integer nummer, NewaFight fight )
    {
        if ( this.fightListMapPoolA == null )
        {
            this.fightListMapPoolA = new HashMap<Integer, NewaFight>( 11 );
        }
        this.fightListMapPoolA.put( nummer, fight );
    }

    public Map<Integer, NewaFight> getFightListMapPoolA()
    {
        return fightListMapPoolA;
    }

    public void setFightListMapPoolA( Map<Integer, NewaFight> fightListMapPoolA )
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

    public List<NewaDoublePoolItem> getFighterListPoolB()
    {
        return fighterListPoolB;
    }

    /**
     * @param fighterList the fighterList to set
     */
    public void setFighterListPoolB( List<NewaDoublePoolItem> fighterListPoolB )
    {
        this.fighterListPoolB = fighterListPoolB;
    }

    /**
     * @param fighterList the fighterList to set
     */
    public void addFighterListPoolB( NewaDoublePoolItem fighterListPoolB )
    {
        if ( this.fighterListPoolB == null )
        {
            this.fighterListPoolB = new ArrayList<NewaDoublePoolItem>();
        }
        this.fighterListPoolB.add( fighterListPoolB );
    }

    public void putFightListMapPoolB( Integer nummer, NewaFight fight )
    {
        if ( this.fightListMapPoolB == null )
        {
            this.fightListMapPoolB = new HashMap<Integer, NewaFight>( 11 );
        }
        this.fightListMapPoolB.put( nummer, fight );
    }

    public Map<Integer, NewaFight> getFightListMapPoolB()
    {
        return fightListMapPoolB;
    }

    public void setFightListMapPoolB( Map<Integer, NewaFight> fightListMapPoolB )
    {
        this.fightListMapPoolB = fightListMapPoolB;
    }

    public NewaFight getFinalFight()
    {
        return finalFight;
    }

    public void setFinalFight( NewaFight finalFight )
    {
        this.finalFight = finalFight;
    }

    public NewaFight getHalfFinalFight1()
    {
        return halfFinalFight1;
    }

    public void setHalfFinalFight1( NewaFight halfFinalFight1 )
    {
        this.halfFinalFight1 = halfFinalFight1;
    }

    public NewaFight getHalfFinalFight2()
    {
        return halfFinalFight2;
    }

    public void setHalfFinalFight2( NewaFight halfFinalFight2 )
    {
        this.halfFinalFight2 = halfFinalFight2;
    }

    public Map<Long, ArrayList<NewaFighter>> getResultMap()
    {
        return resultMap;
    }

    public void setResultMap( Map<Long, ArrayList<NewaFighter>> resultMap )
    {
        this.resultMap = resultMap;
    }
    
    


}
