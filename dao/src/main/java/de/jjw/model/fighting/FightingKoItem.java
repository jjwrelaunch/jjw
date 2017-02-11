/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightingKoItem.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:42
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
 * @hibernate.class table="fighting_ko_poolentries"
 */
public class FightingKoItem
    extends BaseObject
{

    protected Long id;

    protected Long fightingclassId;

    protected Fightingclass fightingclass;

    protected Fighter fighter;

    protected long fighterId;

    protected Integer numberInPool;

    protected String poolPart;

    // not persistable fields

    private FightingDoublePoolItemResult result = new FightingDoublePoolItemResult();

    private int place = TypeUtil.INT_MIN;

    private int poolPlace = TypeUtil.INT_MIN;

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
     * @hibernate.many-to-one not-null="false" class="de.jjw.model.fighting.Fighter" column="fighterId" insert="false"
     *                        update="false"
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

    public FightingDoublePoolItemResult getResult()
    {
        return result;
    }

    public void setResult( FightingDoublePoolItemResult result )
    {
        this.result = result;
    }

    /**
     * @return the numberInPool
     * @hibernate.property column="poolPart"
     */
    public String getPoolPart()
    {
        return poolPart;
    }

    public void setPoolPart( String poolPart )
    {
        this.poolPart = poolPart;
    }

    public int getPoolPlace()
    {
        return poolPlace;
    }

    public void setPoolPlace( int poolPlace )
    {
        this.poolPlace = poolPlace;
    }
}
