/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoKoClass.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:45
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

public class DuoKoClass
    extends Duoclass
{

    protected List<DuoKoItem> teamListPoolA = null;

    protected Map<Integer, DuoFight> fightListMapPoolA = null;

    protected Map<Integer, DuoFight> fightListLoserMapPoolA = null;

    protected List<DuoKoItem> teamListPoolB = null;

    protected Map<Integer, DuoFight> fightListMapPoolB = null;

    protected Map<Integer, DuoFight> fightListLoserMapPoolB = null;
    
    protected DuoFight original3rdPlaceFightA;
    
    protected DuoFight original3rdPlaceFightB;

    protected DuoFight finalFight;
    
    protected boolean thirdPlaceFightsChanged =false;

    public List<DuoKoItem> getTeamListPoolA()
    {
        return teamListPoolA;
    }

    /**
     * @param fighterList the fighterList to set
     */
    public void setTeamListPoolA( List<DuoKoItem> teamListPoolA )
    {
        this.teamListPoolA = teamListPoolA;
    }

    /**
     * @param fighterList the fighterList to set
     */
    public void addTeamListPoolA( DuoKoItem teamListPoolA )
    {
        if ( this.teamListPoolA == null )
        {
            this.teamListPoolA = new ArrayList<DuoKoItem>();
        }
        this.teamListPoolA.add( teamListPoolA );
    }

    public void putFightListMapPoolA( Integer number, DuoFight fight )
    {
        if ( this.fightListMapPoolA == null )
        {
            this.fightListMapPoolA = new HashMap<Integer, DuoFight>( 11 );
        }
        this.fightListMapPoolA.put( number, fight );
    }

    public Map<Integer, DuoFight> getFightListMapPoolA()
    {
        return fightListMapPoolA;
    }

    public void putFightListLoserMapPoolA( Integer number, DuoFight fight )
    {
        if ( this.fightListLoserMapPoolA == null )
        {
            this.fightListLoserMapPoolA = new HashMap<Integer, DuoFight>( 11 );
        }
        this.fightListLoserMapPoolA.put( number, fight );
    }

    public Map<Integer, DuoFight> getFightListLooserMapPoolA()
    {
        return fightListLoserMapPoolA;
    }

    public void setFightListMapPoolA( Map<Integer, DuoFight> fightListMapPoolA )
    {
        this.fightListMapPoolA = fightListMapPoolA;
    }

    public void setFightListLoserMapPoolA( Map<Integer, DuoFight> fightListLoserMapPoolA )
    {
        this.fightListLoserMapPoolA = fightListLoserMapPoolA;
    }

    /**
     * @Override
     */
    public int getFightsystem()
    {
        return Fightsystem.DOUBLE_KO;
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

    public List<DuoKoItem> getTeamListPoolB()
    {
        return teamListPoolB;
    }

    /**
     * @param fighterList the fighterList to set
     */
    public void setTeamListPoolB( List<DuoKoItem> teamListPoolB )
    {
        this.teamListPoolB = teamListPoolB;
    }

    /**
     * @param fighterList the fighterList to set
     */
    public void addTeamListPoolB( DuoKoItem teamListPoolB )
    {
        if ( this.teamListPoolB == null )
        {
            this.teamListPoolB = new ArrayList<DuoKoItem>();
        }
        this.teamListPoolB.add( teamListPoolB );
    }

    public void putFightListMapPoolB( Integer number, DuoFight fight )
    {
        if ( this.fightListMapPoolB == null )
        {
            this.fightListMapPoolB = new HashMap<Integer, DuoFight>( 11 );
        }
        this.fightListMapPoolB.put( number, fight );
    }

    public Map<Integer, DuoFight> getFightListMapPoolB()
    {
        return fightListMapPoolB;
    }

    public void setFightListMapPoolB( Map<Integer, DuoFight> fightListMapPoolB )
    {
        this.fightListMapPoolB = fightListMapPoolB;
    }

    public void putFightListLoserMapPoolB( Integer number, DuoFight fight )
    {
        if ( this.fightListLoserMapPoolB == null )
        {
            this.fightListLoserMapPoolB = new HashMap<Integer, DuoFight>( 11 );
        }
        this.fightListLoserMapPoolB.put( number, fight );
    }

    public Map<Integer, DuoFight> getFightListLooserMapPoolB()
    {
        return fightListLoserMapPoolB;
    }

    public void setFightListLoserMapPoolB( Map<Integer, DuoFight> fightListLoserMapPoolB )
    {
        this.fightListLoserMapPoolB = fightListLoserMapPoolB;
    }

    public DuoFight getFinalFight()
    {
        return finalFight;
    }

    public void setFinalFight( DuoFight finalFight )
    {
        this.finalFight = finalFight;
    }

    public boolean isThirdPlaceFightsChanged()
    {
        return thirdPlaceFightsChanged;
    }

    public void setThirdPlaceFightsChanged( boolean thirdPlaceFightsChanged )
    {
        this.thirdPlaceFightsChanged = thirdPlaceFightsChanged;
    }

    public DuoFight getOriginal3rdPlaceFightA()
    {
        return original3rdPlaceFightA;
    }

    public void setOriginal3rdPlaceFightA( DuoFight original3rdPlaceFightA )
    {
        this.original3rdPlaceFightA = original3rdPlaceFightA;
    }

    public DuoFight getOriginal3rdPlaceFightB()
    {
        return original3rdPlaceFightB;
    }

    public void setOriginal3rdPlaceFightB( DuoFight original3rdPlaceFightB )
    {
        this.original3rdPlaceFightB = original3rdPlaceFightB;
    }


}
