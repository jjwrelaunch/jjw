/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2011.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : Preview.java
 * Created : 08 Feb 2011
 * Last Modified: Tue, 08 Feb 2011 20:55:48
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

package de.jjw.model;

import java.util.ArrayList;
import java.util.List;

import de.jjw.model.admin.Age;
import de.jjw.model.duo.Duoclass;
import de.jjw.model.fighting.Fightingclass;
import de.jjw.model.newa.Newaclass;

/**
 * @hibernate.class table="preview"
 */
public class Preview
    extends BaseObject
{
    public static String DISCEPLINE_DUO = "Duo";

    public static String DISCEPLINE_FIGHTING = "Fighting";

    public static String DISCEPLINE_NEWA = "Newaza";

    public static String DISCEPLINE_DUO_SHORT = "D";

    public static String DISCEPLINE_FIGHTING_SHORT = "F";

    public static String DISCEPLINE_NEWA_SHORT = "N";

    protected Long id;

    protected long fighterIdRed;

    protected long fighterIdBlue;

    protected String tatami;

    protected int orderNumber;

    protected String nameRed;

    protected String nameBlue;

    protected String sex;

    protected long ageId;

    protected long fightingclassId;

    protected long duoclassId;

    protected long newaclassId;

    protected long fightId;

    protected Fightingclass fightingclass;

    protected Duoclass duoclass;

    protected Newaclass newaclass;

    protected Age age;

    protected long userIdOfTatami;

    /**
     * @hibernate.id column="id" generator-class="increment"
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
     * @hibernate.property column="fighterIdRed"
     */
    public long getFighterIdRed()
    {
        return fighterIdRed;
    }

    /**
     * @param fighterIdRed the fighterIdRed to set
     */
    public void setFighterIdRed( long fighterIdRed )
    {
        this.fighterIdRed = fighterIdRed;
    }

    /**
     * @hibernate.property column="fighterIdBlue"
     */
    public long getFighterIdBlue()
    {
        return fighterIdBlue;
    }

    /**
     * @param fighterIdBlue the fighterIdBlue to set
     */
    public void setFighterIdBlue( long fighterIdBlue )
    {
        this.fighterIdBlue = fighterIdBlue;
    }

    /**
     * @hibernate.property column="tatami"
     */
    public String getTatami()
    {
        return tatami;
    }

    /**
     * @param tatami the tatami to set
     */
    public void setTatami( String tatami )
    {
        this.tatami = tatami;
    }

    /**
     * @hibernate.property column="orderNumber"
     */
    public int getOrderNumber()
    {
        return orderNumber;
    }

    /**
     * @param orderNumber the orderNumber to set
     */
    public void setOrderNumber( int orderNumber )
    {
        this.orderNumber = orderNumber;
    }

    /**
     * @hibernate.property column="nameRed"
     */
    public String getNameRed()
    {
        return nameRed;
    }

    /**
     * @param nameRed the nameRed to set
     */
    public void setNameRed( String nameRed )
    {
        this.nameRed = nameRed;
    }

    /**
     * @hibernate.property column="nameBlue"
     */
    public String getNameBlue()
    {
        return nameBlue;
    }

    /**
     * @param nameBlue the nameBlue to set
     */
    public void setNameBlue( String nameBlue )
    {
        this.nameBlue = nameBlue;
    }

    /**
     * @hibernate.property column="sex"
     */
    public String getSex()
    {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex( String sex )
    {
        this.sex = sex;
    }

    /**
     * @hibernate.property column="ageId"
     */
    public long getAgeId()
    {
        return ageId;
    }

    /**
     * @param age the age to set
     */
    public void setAgeId( long ageId )
    {
        this.ageId = ageId;
    }

    /**
     * @hibernate.property column="fightingclassId"
     */
    public long getFightingclassId()
    {
        return fightingclassId;
    }

    /**
     * @param fightingclassId the fightingclassId to set
     */
    public void setFightingclassId( long fightingclassId )
    {
        this.fightingclassId = fightingclassId;
    }

    /**
     * @hibernate.property column="duoclassId"
     */
    public long getDuoclassId()
    {
        return duoclassId;
    }

    /**
     * @param duoclassId the duoclassId to set
     */
    public void setDuoclassId( long duoclassId )
    {
        this.duoclassId = duoclassId;
    }

    /**
     * @hibernate.property column="fightId"
     */
    public long getFightId()
    {
        return fightId;
    }

    /**
     * @param fightId the fightId to set
     */
    public void setFightId( long fightId )
    {
        this.fightId = fightId;
    }

    /**
     * @return the fightingclass
     * @hibernate.many-to-one not-null="true" class="de.jjw.model.fighting.Fightingclass" column="fightingclassId"
     *                        insert="false" update="false"
     */
    public Fightingclass getFightingclass()
    {
        return fightingclass;
    }

    /**
     * @param fightingclass the fightingclass to set
     */
    public void setFightingclass( Fightingclass fightingclass )
    {
        this.fightingclass = fightingclass;
    }

    /**
     * @return the duoclass
     * @hibernate.many-to-one not-null="true" class="de.jjw.model.duo.Duoclass" column="duoclassId" insert="false"
     *                        update="false"
     */
    public Duoclass getDuoclass()
    {
        return duoclass;
    }

    /**
     * @param duoclass the duoclass to set
     */
    public void setDuoclass( Duoclass duoclass )
    {
        this.duoclass = duoclass;
    }

    /**
     * @return the newaclassId
     * @hibernate.property column="newaclassId"
     */
    public long getNewaclassId()
    {
        return newaclassId;
    }

    /**
     * @param newaclassId the newaclassId to set
     */
    public void setNewaclassId( long newaclassId )
    {
        this.newaclassId = newaclassId;
    }

    /**
     * @return the newaclass
     * @hibernate.many-to-one not-null="true" class="de.jjw.model.newa.Newaclass" column="newaclassId" insert="false"
     *                        update="false"
     */
    public Newaclass getNewaclass()
    {
        return newaclass;
    }

    /**
     * @param newaclass the newaclass to set
     */
    public void setNewaclass( Newaclass newaclass )
    {
        this.newaclass = newaclass;
    }

    /**
     * @return the age
     * @hibernate.many-to-one not-null="true" class="de.jjw.model.admin.Age" column="ageId" insert="false"
     *                        update="false"
     */
    public Age getAge()
    {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge( Age age )
    {
        this.age = age;
    }

    /**
     * @hibernate.property column="userIdOfTatami"
     */
    public long getUserIdOfTatami()
    {
        return userIdOfTatami;
    }

    /**
     * @param userIdOfTatami the userIdOfTatami to set
     */
    public void setUserIdOfTatami( long userIdOfTatami )
    {
        this.userIdOfTatami = userIdOfTatami;
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
}
