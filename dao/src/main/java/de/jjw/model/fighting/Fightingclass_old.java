/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : Fightingclass_old.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:43
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
import de.jjw.model.admin.Fightsystem;

import java.io.Serializable;
import java.sql.Timestamp;

public abstract class Fightingclass_old
    extends BaseObject
    implements Serializable
{


    protected Fightsystem fightsystem;

    protected Long id;

    protected Integer complete;

    protected Integer print;

    protected Integer finish;

    protected Timestamp _startTime = null;

    protected Timestamp _endTime = null;

    protected String _officialsLog;

    protected Weightclass weightclass;


    public abstract int getFightsystem();


    /**
     * @hibernate.property column="complete" length="1" type="yes_no"
     */
    public Integer getComplete()
    {

        return this.complete;
    }


    /**
     * @hibernate.property column="finish" length="1" type="yes_no"
     */
    public Integer getFinish()
    {

        return this.finish;
    }


    /**
     * @hibernate.id column="id" generator-class="increment" unsaved-value="null"
     */
    public Long getId()
    {

        return this.id;
    }


    /**
     * @hibernate.property column="print" length="1" type="yes_no"
     */
    public Integer getPrint()
    {

        return this.print;
    }


    public void setComplete( Integer complete )
    {
        this.complete = complete;

    }


    public void setFightsystem( Fightsystem fightsystem )
    {
        this.fightsystem = fightsystem;

    }


    public void setFinish( Integer finish )
    {
        this.finish = finish;

    }


    public void setId( Long id )
    {
        this.id = id;

    }


    public void setPrint( Integer print )
    {
        this.print = print;

    }


    /**
     * @return the weightclass
     * @hibernate.many-to-one class="de.jjw.model.fighting.Weightclass" column="weightclassId" name="fightingclass" unique="true"
     */
    public Weightclass getWeightclass()
    {
        return weightclass;
    }


    /**
     * @param weightclass the weightclass to set
     */
    public void setWeightclass( Weightclass weightclass )
    {
        this.weightclass = weightclass;
    }


}
