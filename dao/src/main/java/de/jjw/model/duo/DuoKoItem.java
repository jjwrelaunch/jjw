/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoKoItem.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:44
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
 * @hibernate.class table="duo_ko_poolentries"
 */
public class DuoKoItem
    extends BaseObject
{

    protected Long id;

    protected Long duoclassId;

    protected Duoclass duoclass;

    protected DuoTeam duoTeam;

    protected long duoTeamId;

    protected Integer numberInPool;

    protected String poolPart;

    protected DuoFight fightRound1;

    protected DuoFight fightRound2;

    protected DuoFight fightRound3;

    protected DuoFight fightRound4;

    protected DuoFight fightRound5;

    protected DuoFight fightRound6;

    protected DuoFight fightRound7;

    // not persistable fields

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
     * @return the
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
     * @return the fighter
     * @hibernate.many-to-one not-null="false" class="de.jjw.model.duo.DuoTeam" column="duoTeamId" insert="false"
     *                        update="false"
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

    public DuoFight getFightRound1()
    {
        return fightRound1;
    }

    public void setFightRound1( DuoFight fightRound1 )
    {
        this.fightRound1 = fightRound1;
    }

    public DuoFight getFightRound2()
    {
        return fightRound2;
    }

    public void setFightRound2( DuoFight fightRound2 )
    {
        this.fightRound2 = fightRound2;
    }

    public DuoFight getFightRound3()
    {
        return fightRound3;
    }

    public void setFightRound3( DuoFight fightRound3 )
    {
        this.fightRound3 = fightRound3;
    }

    public DuoFight getFightRound4()
    {
        return fightRound4;
    }

    public void setFightRound4( DuoFight fightRound4 )
    {
        this.fightRound4 = fightRound4;
    }

    public DuoFight getFightRound5()
    {
        return fightRound5;
    }

    public void setFightRound5( DuoFight fightRound5 )
    {
        this.fightRound5 = fightRound5;
    }

    public DuoFight getFightRound6()
    {
        return fightRound6;
    }

    public void setFightRound6( DuoFight fightRound6 )
    {
        this.fightRound6 = fightRound6;
    }

    public DuoFight getFightRound7()
    {
        return fightRound7;
    }

    public void setFightRound7( DuoFight fightRound7 )
    {
        this.fightRound7 = fightRound7;
    }
}
