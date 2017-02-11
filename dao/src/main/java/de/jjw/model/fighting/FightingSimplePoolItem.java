/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightingSimplePoolItem.java
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

import de.jjw.model.BaseObject;
import de.jjw.util.TypeUtil;


/**
 * @author joerg.boehme
 * @hibernate.class table="fighting_poolentries"
 */
public class FightingSimplePoolItem
    extends BaseObject
    implements Comparable
{

    protected Long id;

    protected Long fightingclassId;

    protected Fightingclass fightingclass;

    protected Fighter fighter;

    protected long fighterId;

    protected Integer numberInPool;

    protected Fight fight1;

    protected Fight fight2;

    protected Fight fight3;

    protected Fight fight4;

    //not persistable fields

    private FightingSimplePoolItemResult result = new FightingSimplePoolItemResult();

    private int place = TypeUtil.INT_MIN;


    /**
     * @return the id
     * @hibernate.id column="id" generator-class="increment" unsaved-value="null"
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId( Long id )
    {
        this.id = id;
    }


    /**
     * @return the numberInPool
     * @hibernate.property column="poolId"
     */
    public Long getFightingclassId()
    {
        return fightingclassId;
    }

    public void setFightingclassId( Long fightingclassId )
    {
        this.fightingclassId = fightingclassId;
    }

    /**
     * @return the poolId
     * @hibernate.many-to-one class="de.jjw.model.fighting.Fightingclass" column="poolId" insert="false" update="false"
     * @hibernate.id column="poolId"
     */
    public Fightingclass getFightingclass()
    {
        return fightingclass;
    }

    public void setFightingclass( Fightingclass fightingclass )
    {
        this.fightingclass = fightingclass;
    }


    /**
     * @return the fighter
     * @hibernate.many-to-one not-null="false" class="de.jjw.model.fighting.Fighter" column="fighterId" insert="false" update="false"
     */
    public Fighter getFighter()
    {
        return fighter;
    }

    /**
     * @param fighter the fighter to set
     */
    public void setFighter( Fighter fighter )
    {
        this.fighter = fighter;
    }

    /**
     * @return the numberInPool
     * @hibernate.property column="fighterId"
     */
    public long getFighterId()
    {
        return fighterId;
    }

    public void setFighterId( long fighterId )
    {
        this.fighterId = fighterId;
    }


    /**
     * @return the numberInPool
     * @hibernate.property column="numberInPool"
     */
    public Integer getNumberInPool()
    {
        return numberInPool;
    }

    /**
     * @param numberInPool the numberInPool to set
     */
    public void setNumberInPool( Integer numberInPool )
    {
        this.numberInPool = numberInPool;
    }

    /**
     * @return the fight1
     * @hibernate.many-to-one class="de.jjw.model.fighting.Fight" column="fight1"
     */
    public Fight getFight1()
    {
        return fight1;
    }

    /**
     * @param fight1 the fight1 to set
     */
    public void setFight1( Fight fight1 )
    {
        this.fight1 = fight1;
    }

    /**
     * @return the fight2
     * @hibernate.many-to-one class="de.jjw.model.fighting.Fight" column="fight2"
     */
    public Fight getFight2()
    {
        return fight2;
    }

    /**
     * @param fight2 the fight2 to set
     */
    public void setFight2( Fight fight2 )
    {
        this.fight2 = fight2;
    }

    /**
     * @return the fight3
     * @hibernate.many-to-one class="de.jjw.model.fighting.Fight" column="fight3"
     */
    public Fight getFight3()
    {
        return fight3;
    }

    /**
     * @param fight3 the fight3 to set
     */
    public void setFight3( Fight fight3 )
    {
        this.fight3 = fight3;
    }

    /**
     * @return the fight4
     * @hibernate.many-to-one class="de.jjw.model.fighting.Fight" column="fight4"
     */
    public Fight getFight4()
    {
        return fight4;
    }

    /**
     * @param fight4 the fight4 to set
     */
    public void setFight4( Fight fight4 )
    {
        this.fight4 = fight4;
    }


    @Override
    public String toString()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean equals( Object o )
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int hashCode()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    public int getPlace()
    {
        return place;
    }

    public void setPlace( int place )
    {
        this.place = place;
    }

    public int getPoolPlace()
    {
        return place;
    }

    public void setPoolPlace( int place )
    {
        this.place = place;
    }

    public FightingSimplePoolItemResult getResult()
    {
        return result;
    }

    public void setResult( FightingSimplePoolItemResult result )
    {
        this.result = result;
    }

    public int compareTo( Object o )
    {

        FightingSimplePoolItem item = (FightingSimplePoolItem) o;

        if ( this.getResult().getWinCount() > item.getResult().getWinCount() )
        {
            return 1;
        }
        if ( this.getResult().getWinCount() < item.getResult().getWinCount() )
        {
            return -1;
        }

        if ( this.getResult().getWinCount() == item.getResult().getWinCount() )
        {
            if ( this.getResult().getUBCount() > item.getResult().getUBCount() )
            {
                return 1;
            }
            if ( this.getResult().getUBCount() > item.getResult().getUBCount() )
            {
                return -1;
            }
            if ( this.getResult().getUBCount() == item.getResult().getUBCount() )
            {
                return 0;
            }
        }

        return 0;
    }
}
