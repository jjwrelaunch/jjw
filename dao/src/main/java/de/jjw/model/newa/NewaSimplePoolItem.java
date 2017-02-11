/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaSimplePoolItem.java
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

import de.jjw.model.BaseObject;
import de.jjw.util.TypeUtil;


/**
 * @author joerg.boehme
 * @hibernate.class table="newa_poolentries"
 */
public class NewaSimplePoolItem
    extends BaseObject
    implements Comparable
{

    protected Long id;

    protected Long newaclassId;

    protected Newaclass newaclass;

    protected NewaFighter fighter;

    protected long fighterId;

    protected Integer numberInPool;

    protected NewaFight fight1;

    protected NewaFight fight2;

    protected NewaFight fight3;

    protected NewaFight fight4;

    //not persistable fields

    private NewaSimplePoolItemResult result = new NewaSimplePoolItemResult();

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

    /**
     * @return the fight1
     * @hibernate.many-to-one class="de.jjw.model.newa.NewaFight" column="fight1"
     */
    public NewaFight getFight1()
    {
        return fight1;
    }

    /**
     * @param fight1 the fight1 to set
     */
    public void setFight1( NewaFight fight1 )
    {
        this.fight1 = fight1;
    }

    /**
     * @return the fight2
     * @hibernate.many-to-one class="de.jjw.model.newa.NewaFight" column="fight2"
     */
    public NewaFight getFight2()
    {
        return fight2;
    }

    /**
     * @param fight2 the fight2 to set
     */
    public void setFight2( NewaFight fight2 )
    {
        this.fight2 = fight2;
    }

    /**
     * @return the fight3
     * @hibernate.many-to-one class="de.jjw.model.newa.NewaFight" column="fight3"
     */
    public NewaFight getFight3()
    {
        return fight3;
    }

    /**
     * @param fight3 the fight3 to set
     */
    public void setFight3( NewaFight fight3 )
    {
        this.fight3 = fight3;
    }

    /**
     * @return the fight4
     * @hibernate.many-to-one class="de.jjw.model.newa.NewaFight" column="fight4"
     */
    public NewaFight getFight4()
    {
        return fight4;
    }

    /**
     * @param fight4 the fight4 to set
     */
    public void setFight4( NewaFight fight4 )
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

    public NewaSimplePoolItemResult getResult()
    {
        return result;
    }

    public void setResult( NewaSimplePoolItemResult result )
    {
        this.result = result;
    }

    public int compareTo( Object o )
    {

        NewaSimplePoolItem item = (NewaSimplePoolItem) o;

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
