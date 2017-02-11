/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightingKoClass.java
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

package de.jjw.model.fighting;

import de.jjw.model.admin.Fightsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FightingKoClass
    extends Fightingclass
{

    protected List<FightingKoItem> fighterListPoolA = null;

    protected Map<Integer, Fight> fightListMapPoolA = null;

    protected Map<Integer, Fight> fightListLoserMapPoolA = null;

    protected List<FightingKoItem> fighterListPoolB = null;

    protected Map<Integer, Fight> fightListMapPoolB = null;

    protected Map<Integer, Fight> fightListLoserMapPoolB = null;
    
    protected Fight original3rdPlaceFightA;
    
    protected Fight original3rdPlaceFightB;

    protected Fight finalFight;
    
    protected boolean thirdPlaceFightsChanged =false;

    public List<FightingKoItem> getFighterListPoolA()
    {
        return fighterListPoolA;
    }

    /**
     * @param fighterList the fighterList to set
     */
    public void setFighterListPoolA( List<FightingKoItem> fighterListPoolA )
    {
        this.fighterListPoolA = fighterListPoolA;
    }

    /**
     * @param fighterList the fighterList to set
     */
    public void addFighterListPoolA( FightingKoItem fighterListPoolA )
    {
        if ( this.fighterListPoolA == null )
        {
            this.fighterListPoolA = new ArrayList<FightingKoItem>();
        }
        this.fighterListPoolA.add( fighterListPoolA );
    }

    public void putFightListMapPoolA( Integer number, Fight fight )
    {
        if ( this.fightListMapPoolA == null )
        {
            this.fightListMapPoolA = new HashMap<Integer, Fight>( 11 );
        }
        this.fightListMapPoolA.put( number, fight );
    }

    public Map<Integer, Fight> getFightListMapPoolA()
    {
        return fightListMapPoolA;
    }

    public void putFightListLoserMapPoolA( Integer number, Fight fight )
    {
        if ( this.fightListLoserMapPoolA == null )
        {
            this.fightListLoserMapPoolA = new HashMap<Integer, Fight>( 11 );
        }
        this.fightListLoserMapPoolA.put( number, fight );
    }

    public Map<Integer, Fight> getFightListLooserMapPoolA()
    {
        return fightListLoserMapPoolA;
    }

    public void setFightListMapPoolA( Map<Integer, Fight> fightListMapPoolA )
    {
        this.fightListMapPoolA = fightListMapPoolA;
    }

    public void setFightListLoserMapPoolA( Map<Integer, Fight> fightListLoserMapPoolA )
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

    public List<FightingKoItem> getFighterListPoolB()
    {
        return fighterListPoolB;
    }

    /**
     * @param fighterList the fighterList to set
     */
    public void setFighterListPoolB( List<FightingKoItem> fighterListPoolB )
    {
        this.fighterListPoolB = fighterListPoolB;
    }

    /**
     * @param fighterList the fighterList to set
     */
    public void addFighterListPoolB( FightingKoItem fighterListPoolB )
    {
        if ( this.fighterListPoolB == null )
        {
            this.fighterListPoolB = new ArrayList<FightingKoItem>();
        }
        this.fighterListPoolB.add( fighterListPoolB );
    }

    public void putFightListMapPoolB( Integer number, Fight fight )
    {
        if ( this.fightListMapPoolB == null )
        {
            this.fightListMapPoolB = new HashMap<Integer, Fight>( 11 );
        }
        this.fightListMapPoolB.put( number, fight );
    }

    public Map<Integer, Fight> getFightListMapPoolB()
    {
        return fightListMapPoolB;
    }

    public void setFightListMapPoolB( Map<Integer, Fight> fightListMapPoolB )
    {
        this.fightListMapPoolB = fightListMapPoolB;
    }

    public void putFightListLoserMapPoolB( Integer number, Fight fight )
    {
        if ( this.fightListLoserMapPoolB == null )
        {
            this.fightListLoserMapPoolB = new HashMap<Integer, Fight>( 11 );
        }
        this.fightListLoserMapPoolB.put( number, fight );
    }

    public Map<Integer, Fight> getFightListLooserMapPoolB()
    {
        return fightListLoserMapPoolB;
    }

    public void setFightListLoserMapPoolB( Map<Integer, Fight> fightListLoserMapPoolB )
    {
        this.fightListLoserMapPoolB = fightListLoserMapPoolB;
    }

    public Fight getFinalFight()
    {
        return finalFight;
    }

    public void setFinalFight( Fight finalFight )
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

    public Fight getOriginal3rdPlaceFightA()
    {
        return original3rdPlaceFightA;
    }

    public void setOriginal3rdPlaceFightA( Fight original3rdPlaceFightA )
    {
        this.original3rdPlaceFightA = original3rdPlaceFightA;
    }

    public Fight getOriginal3rdPlaceFightB()
    {
        return original3rdPlaceFightB;
    }

    public void setOriginal3rdPlaceFightB( Fight original3rdPlaceFightB )
    {
        this.original3rdPlaceFightB = original3rdPlaceFightB;
    }


}
