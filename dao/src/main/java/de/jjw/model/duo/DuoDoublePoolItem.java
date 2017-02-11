/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoDoublePoolItem.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:47
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

import de.jjw.model.BaseObject;
import de.jjw.util.TypeUtil;

/**
 * @hibernate.class table="duo_d_poolentries"
 */
public class DuoDoublePoolItem
    extends BaseObject
{

    protected Long id;

    protected Long duoclassId;

    protected Duoclass duoclass;

    protected DuoTeam duoTeam;

    protected long duoTeamId;

    protected Integer numberInPool;

    protected DuoFight fight1;

    protected DuoFight fight2;

    protected DuoFight fight3;

    protected DuoFight fight4;

    protected DuoFight finalFight;

    protected String poolPart;

    //not persistable fields

    private DuoDoublePoolItemResult result = new DuoDoublePoolItemResult();

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
    public Long getDuoclassId()
    {
        return duoclassId;
    }

    public void setDuoclassId( Long duoclassId )
    {
        this.duoclassId = duoclassId;
    }

    /**
     * @return the poolId
     * @hibernate.many-to-one class="de.jjw.model.duo.Duoclass" column="poolId" insert="false" update="false"
     * @hibernate.id column="poolId"
     */
    public Duoclass getDuoclass()
    {
        return duoclass;
    }

    public void setDuoclass( Duoclass duoclass )
    {
        this.duoclass = duoclass;
    }


    /**
     * @return the duoTeam
     * @hibernate.many-to-one not-null="false" class="de.jjw.model.duo.DuoTeam" column="duoTeamId" insert="false" update="false"
     */
    public DuoTeam getDuoTeam()
    {
        return duoTeam;
    }

    /**
     * @param duoTeam the duoTeam to set
     */
    public void setDuoTeam( DuoTeam duoTeam )
    {
        this.duoTeam = duoTeam;
    }


    /**
     * @return the numberInPool
     * @hibernate.property column="duoTeamId"
     */
    public long getDuoTeamId()
    {
        return duoTeamId;
    }

    public void setDuoTeamId( long duoTeamId )
    {
        this.duoTeamId = duoTeamId;
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
     * @hibernate.many-to-one class="de.jjw.model.duo.DuoFight" column="fight1"
     */
    public DuoFight getFight1()
    {
        return fight1;
    }

    /**
     * @param fight1 the fight1 to set
     */
    public void setFight1( DuoFight fight1 )
    {
        this.fight1 = fight1;
    }

    /**
     * @return the fight2
     * @hibernate.many-to-one class="de.jjw.model.duo.DuoFight" column="fight2"
     */
    public DuoFight getFight2()
    {
        return fight2;
    }

    /**
     * @param fight2 the fight2 to set
     */
    public void setFight2( DuoFight fight2 )
    {
        this.fight2 = fight2;
    }

    /**
     * @return the fight3
     * @hibernate.many-to-one class="de.jjw.model.duo.DuoFight" column="fight3"
     */
    public DuoFight getFight3()
    {
        return fight3;
    }

    /**
     * @param fight3 the fight3 to set
     */
    public void setFight3( DuoFight fight3 )
    {
        this.fight3 = fight3;
    }

    /**
     * @return the fight4
     * @hibernate.many-to-one class="de.jjw.model.duo.DuoFight" column="fight4"
     */
    public DuoFight getFight4()
    {
        return fight4;
    }

    /**
     * @param fight4 the fight4 to set
     */
    public void setFight4( DuoFight fight4 )
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

    public DuoDoublePoolItemResult getResult()
    {
        return result;
    }

    public void setResult( DuoDoublePoolItemResult result )
    {
        this.result = result;
    }

    /**
     * @return the finalFight
     * @hibernate.many-to-one class="de.jjw.model.duo.DuoFight" column="finalFight"
     */
    public DuoFight getFinalFight()
    {
        return finalFight;
    }

    public void setFinalFight( DuoFight finalFight )
    {
        this.finalFight = finalFight;
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
