/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : Age.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:46
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

package de.jjw.model.admin;

import java.io.Serializable;

import de.jjw.model.BaseObject;

/**
 * @hibernate.class table="age"
 */
public class Age
    extends BaseObject
    implements Serializable
{

    protected Long id;

    protected String ageShort;

    protected String description = "";

    protected int orderNumber;

    protected int fightingTime;

    protected int fightingRounds;

    protected int startAge;

    protected int ageLimit;

    protected int overtime;

    protected int injurytime;

    protected int estimatedTime;

    protected int estimatedTimeDuo;

    protected int estimatedTimeNewa;

    protected int fightingTimeNewa;
//    protected Set<Fighter> fightersForAge;


    /**
     * @return Returns the description.
     * @hibernate.property column="description" length="15"
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description The description to set.
     */
    public void setDescription( String description )
    {
        this.description = description;
    }

    /**
     * @return Returns the ageShort.
     * @hibernate.property column="ageShort"
     */
    public String getAgeShort()
    {
        return ageShort;
    }

    /**
     * @param ageShort The ageShort to set.
     */
    public void setAgeShort( String ageShort )
    {
        this.ageShort = ageShort;
    }


    /**
     * @return Returns the ageLimit.
     * @hibernate.property column="ageLimit"
     */
    public int getAgeLimit()
    {
        return ageLimit;
    }

    /**
     * @param ageLimit The ageLimit to set.
     */
    public void setAgeLimit( int ageLimit )
    {
        this.ageLimit = ageLimit;
    }

    /**
     * @return Returns the fightingRounds.
     * @hibernate.property column="fightingRounds"
     */
    public int getFightingRounds()
    {
        return fightingRounds;
    }

    /**
     * @param fightingRounds The fightingRounds to set.
     */
    public void setFightingRounds( int fightingRounds )
    {
        this.fightingRounds = fightingRounds;
    }

    /**
     * @return Returns the fightingTime in seconds.
     * @hibernate.property column="fightingTime"
     */
    public int getFightingTime()
    {
        return fightingTime;
    }

    /**
     * @param fightingTime The fightingTime to set.
     */
    public void setFightingTime( int fightingTime )
    {
        this.fightingTime = fightingTime;
    }

    /**
     * @return Returns the orderNumber.
     * @hibernate.property column="orderNumber"
     */
    public int getOrderNumber()
    {
        return orderNumber;
    }

    public void setOrderNumber( int orderNumber )
    {
        this.orderNumber = orderNumber;
    }

    /**
     * @return Returns the startAge.
     * @hibernate.property column="startAge"
     */
    public int getStartAge()
    {
        return startAge;
    }

    /**
     * @param startAge The startAge to set.
     */
    public void setStartAge( int startAge )
    {
        this.startAge = startAge;
    }

    /**
     * @return Returns the overtime.
     * @hibernate.property column="overtime"
     */
    public int getOvertime()
    {
        return overtime;
    }

    /**
     * @param overtime The overtime to set.
     */
    public void setOvertime( int overtime )
    {
        this.overtime = overtime;
    }

    /**
     * @return Returns the injurytime.
     * @hibernate.property column="injurytime"
     */
    public int getInjurytime()
    {
        return injurytime;
    }

    public void setInjurytime( int injurytime )
    {
        this.injurytime = injurytime;
    }

    /**
     * @return Returns the estimatedTime.
     * @hibernate.property column="estimatedTime"
     */
    public int getEstimatedTime()
    {
        return estimatedTime;
    }

    public void setEstimatedTime( int estimatedTime )
    {
        this.estimatedTime = estimatedTime;
    }

    /**
     * @return Returns the estimatedTime.
     * @hibernate.property column="estimatedTimeDuo"
     */
    public int getEstimatedTimeDuo()
    {
        return estimatedTimeDuo;
    }

    public void setEstimatedTimeDuo( int estimatedTimeDuo )
    {
        this.estimatedTimeDuo = estimatedTimeDuo;
    }

    @Override
    public String toString()
    {

        return "";
    }

    @Override
    public boolean equals( Object o )
    {
        try
        {
            if ( o == null )
            {
                return false;
            }

            Age age = (Age) o;
            if ( this.getAgeLimit() == age.getAgeLimit() && this.getAgeShort().equals( age.getAgeShort() ) &&
                this.getCreateDate().equals( age.getCreateDate() ) && this.getCreateId().equals( age.getCreateId() ) &&
                this.getDescription().equals( age.getDescription() ) &&
                this.getFightingRounds() == age.getFightingRounds() &&
                this.getFightingTime() == age.getFightingTime() && this.getId().equals( age.getId() ) &&
                this.getModificationDate().equals( age.getModificationDate() ) &&
                this.getModificationId().equals( age.getModificationId() ) && this.getOvertime() == age.getOvertime() &&
                this.getOrderNumber() == age.getOrderNumber() && this.getStartAge() == age.getStartAge() )
            {
                return true;
            }

        }
        catch ( Exception e )
        {
            ;
        }

        return false;
    }

    @Override
    public int hashCode()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @return Returns the id.
     * @hibernate.id column="id" generator-class="increment"
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @param id The id to set.
     */
    public void setId( Long id )
    {
        this.id = id;
    }

    /**
     * @return the estimatedTimeNewa
     * @hibernate.property column="estimatedTimeNewa"
     */
    public int getEstimatedTimeNewa()
    {
        return estimatedTimeNewa;
    }

    /**
     * @param estimatedTimeNewa the estimatedTimeNewa to set
     */
    public void setEstimatedTimeNewa( int estimatedTimeNewa )
    {
        this.estimatedTimeNewa = estimatedTimeNewa;
    }

    /**
     * @return the fightingTimeNewa
     * @hibernate.property column="fightingTimeNewa"
     */
    public int getFightingTimeNewa()
    {
        return fightingTimeNewa;
    }

    /**
     * @param fightingTimeNewa the fightingTimeNewa to set
     */
    public void setFightingTimeNewa( int fightingTimeNewa )
    {
        this.fightingTimeNewa = fightingTimeNewa;
    }

}
