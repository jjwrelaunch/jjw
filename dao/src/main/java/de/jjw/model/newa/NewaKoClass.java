/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaKoClass.java
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

package de.jjw.model.newa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.jjw.model.admin.Fightsystem;

public class NewaKoClass
    extends Newaclass
{

    protected List<NewaKoItem> fighterListPoolA = null;

    protected Map<Integer, NewaFight> fightListMapPoolA = null;

    protected Map<Integer, NewaFight> fightListLoserMapPoolA = null;

    protected List<NewaKoItem> fighterListPoolB = null;

    protected Map<Integer, NewaFight> fightListMapPoolB = null;

    protected Map<Integer, NewaFight> fightListLoserMapPoolB = null;
    
    protected NewaFight original3rdPlaceFightA;
    
    protected NewaFight original3rdPlaceFightB;

    protected NewaFight finalFight;
    
    protected boolean thirdPlaceFightsChanged =false;

    public List<NewaKoItem> getFighterListPoolA()
    {
        return fighterListPoolA;
    }

    /**
     * @param fighterList the fighterList to set
     */
    public void setFighterListPoolA( List<NewaKoItem> fighterListPoolA )
    {
        this.fighterListPoolA = fighterListPoolA;
    }

    /**
     * @param fighterList the fighterList to set
     */
    public void addFighterListPoolA( NewaKoItem fighterListPoolA )
    {
        if ( this.fighterListPoolA == null )
        {
            this.fighterListPoolA = new ArrayList<NewaKoItem>();
        }
        this.fighterListPoolA.add( fighterListPoolA );
    }

    public void putFightListMapPoolA( Integer number, NewaFight fight )
    {
        if ( this.fightListMapPoolA == null )
        {
            this.fightListMapPoolA = new HashMap<Integer, NewaFight>( 11 );
        }
        this.fightListMapPoolA.put( number, fight );
    }

    public Map<Integer, NewaFight> getFightListMapPoolA()
    {
        return fightListMapPoolA;
    }

    public void putFightListLoserMapPoolA( Integer number, NewaFight fight )
    {
        if ( this.fightListLoserMapPoolA == null )
        {
            this.fightListLoserMapPoolA = new HashMap<Integer, NewaFight>( 11 );
        }
        this.fightListLoserMapPoolA.put( number, fight );
    }

    public Map<Integer, NewaFight> getFightListLooserMapPoolA()
    {
        return fightListLoserMapPoolA;
    }

    public void setFightListMapPoolA( Map<Integer, NewaFight> fightListMapPoolA )
    {
        this.fightListMapPoolA = fightListMapPoolA;
    }

    public void setFightListLoserMapPoolA( Map<Integer, NewaFight> fightListLoserMapPoolA )
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

    public List<NewaKoItem> getFighterListPoolB()
    {
        return fighterListPoolB;
    }

    /**
     * @param fighterList the fighterList to set
     */
    public void setFighterListPoolB( List<NewaKoItem> fighterListPoolB )
    {
        this.fighterListPoolB = fighterListPoolB;
    }

    /**
     * @param fighterList the fighterList to set
     */
    public void addFighterListPoolB( NewaKoItem fighterListPoolB )
    {
        if ( this.fighterListPoolB == null )
        {
            this.fighterListPoolB = new ArrayList<NewaKoItem>();
        }
        this.fighterListPoolB.add( fighterListPoolB );
    }

    public void putFightListMapPoolB( Integer number, NewaFight fight )
    {
        if ( this.fightListMapPoolB == null )
        {
            this.fightListMapPoolB = new HashMap<Integer, NewaFight>( 11 );
        }
        this.fightListMapPoolB.put( number, fight );
    }

    public Map<Integer, NewaFight> getFightListMapPoolB()
    {
        return fightListMapPoolB;
    }

    public void setFightListMapPoolB( Map<Integer, NewaFight> fightListMapPoolB )
    {
        this.fightListMapPoolB = fightListMapPoolB;
    }

    public void putFightListLoserMapPoolB( Integer number, NewaFight fight )
    {
        if ( this.fightListLoserMapPoolB == null )
        {
            this.fightListLoserMapPoolB = new HashMap<Integer, NewaFight>( 11 );
        }
        this.fightListLoserMapPoolB.put( number, fight );
    }

    public Map<Integer, NewaFight> getFightListLooserMapPoolB()
    {
        return fightListLoserMapPoolB;
    }

    public void setFightListLoserMapPoolB( Map<Integer, NewaFight> fightListLoserMapPoolB )
    {
        this.fightListLoserMapPoolB = fightListLoserMapPoolB;
    }

    public NewaFight getFinalFight()
    {
        return finalFight;
    }

    public void setFinalFight( NewaFight finalFight )
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

    public NewaFight getOriginal3rdPlaceFightA()
    {
        return original3rdPlaceFightA;
    }

    public void setOriginal3rdPlaceFightA( NewaFight original3rdPlaceFightA )
    {
        this.original3rdPlaceFightA = original3rdPlaceFightA;
    }

    public NewaFight getOriginal3rdPlaceFightB()
    {
        return original3rdPlaceFightB;
    }

    public void setOriginal3rdPlaceFightB( NewaFight original3rdPlaceFightB )
    {
        this.original3rdPlaceFightB = original3rdPlaceFightB;
    }


}
