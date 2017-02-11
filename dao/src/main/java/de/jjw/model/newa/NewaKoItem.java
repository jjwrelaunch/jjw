/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaKoItem.java
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

package de.jjw.model.newa;

import de.jjw.model.BaseObject;
import de.jjw.util.TypeUtil;

/**
 * @hibernate.class table="newa_ko_poolentries"
 */
public class NewaKoItem
    extends BaseObject
{

    protected Long id;

    protected Long newaclassId;

    protected Newaclass newaclass;

    protected NewaFighter fighter;

    protected long fighterId;

    protected Integer numberInPool;

    protected String poolPart;

    // not persistable fields

    private NewaDoublePoolItemResult result = new NewaDoublePoolItemResult();

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
    public Long getNewaclassId()
    {
        return newaclassId;
    }

    public void setNewaclassId( Long newaclassId )
    {
        this.newaclassId = newaclassId;
    }

    /**
     * @return the poolId
     * @hibernate.many-to-one class="de.jjw.model.newa.Newaclass" column="poolId" insert="false" update="false"
     * @hibernate.id column="poolId"
     */
    public Newaclass getNewaclass()
    {
        return newaclass;
    }

    public void setNewaclass( Newaclass newaclass )
    {
        this.newaclass = newaclass;
    }

    /**
     * @return the fighter
     * @hibernate.many-to-one not-null="false" class="de.jjw.model.newa.NewaFighter" column="fighterId" insert="false"
     *                        update="false"
     */
    public NewaFighter getFighter()
    {
        return fighter;
    }

    /**
     * @param fighter the fighter to set
     */
    public void setFighter( NewaFighter fighter )
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

    public NewaDoublePoolItemResult getResult()
    {
        return result;
    }

    public void setResult( NewaDoublePoolItemResult result )
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
