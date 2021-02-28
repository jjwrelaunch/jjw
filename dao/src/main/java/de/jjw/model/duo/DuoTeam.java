/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoTeam.java
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

package de.jjw.model.duo;

import java.io.Serializable;

import de.jjw.model.BaseObject;
import de.jjw.model.Team;
import de.jjw.model.admin.Age;
import de.jjw.util.TypeUtil;

/**
 * @hibernate.class table="duoteam"
 */
public class DuoTeam
    extends BaseObject
    implements Serializable
{

    protected Long id = null;

    protected String name;

    protected String firstname;

    protected String name2;

    protected String firstname2;

    protected Team team;

    protected String sex = "";

    protected Duoclass duoclass;

    protected String ready;

    protected Age age;

    protected int place;

    protected boolean delete;

    protected long ageId;

    protected long teamId;

    protected long duoclassId;

    protected long originalDuoclassId;

    protected int yearOfBirth;    

    protected int monthOfBirth;
    
    protected int dayOfBirth;

    protected int yearOfBirth2;

    protected int monthOfBirth2;
    
    protected int dayOfBirth2;

    protected boolean markForExport;
    
    /**
     * @return Returns the firstname.
     * @hibernate.property column="firstname" length="30"
     */
    public String getFirstname()
    {
        return firstname;
    }

    /**
     * @param firstname The firstname to set.
     */
    public void setFirstname( String firstname )
    {
        this.firstname = firstname;
    }

    /**
     * @return Returns the firstname2.
     * @hibernate.property column="firstname2" length="30"
     */
    public String getFirstname2()
    {
        return firstname2;
    }

    /**
     * @param firstname The firstname2 to set.
     */
    public void setFirstname2( String firstname2 )
    {
        this.firstname2 = firstname2;
    }

    /**
     * @return Returns the id.
     * @hibernate.id column="id" generator-class="increment" unsaved-value="null"
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
        if ( id != null && TypeUtil.isDefault( id ) )
        {
            id = null;
        }
        else
        {
            this.id = id;
        }
    }

    /**
     * @return Returns the name.
     * @hibernate.property column="name" length="30"
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name The name to set.
     */
    public void setName( String name )
    {
        this.name = name;
    }

    /**
     * @return Returns the name2.
     * @hibernate.property column="name2" length="30"
     */
    public String getName2()
    {
        return name2;
    }

    /**
     * @param name The name2 to set.
     */
    public void setName2( String name2 )
    {
        this.name2 = name2;
    }


    /**
     * @return Returns the age.
     * @hibernate.many-to-one not-null="true" class="de.jjw.model.admin.Age" column="age" update="false" insert="false"
     */
    public Age getAge()
    {
        return age;
    }

    /**
     * @param age The age to set.
     */
    public void setAge( Age age )
    {
        this.age = age;
    }

    /**
     * @return Returns the team.
     * @hibernate.many-to-one not-null="true" class="de.jjw.model.Team" column="team" update="false" insert="false"
     */
    public Team getTeam()
    {
        return team;
    }

    /**
     * @param team The team to set.
     */
    public void setTeam( Team team )
    {
        this.team = team;
    }

    /**
     * @return Returns the place.
     * @hibernate.property column="place"
     */
    public int getPlace()
    {
        return place;
    }

    /**
     * @param place The place to set.
     */
    public void setPlace( int place )
    {
        this.place = place;
    }

    /**
     * @return Returns the ready.
     * @hibernate.property column="ready" length="1"
     */
    public String getReady()
    {
        return ready;
    }

    //public boolean isReady() {
    //   return ready;
    // }

    /**
     * @param ready The ready to set.
     */
    public void setReady( String ready )
    {
        this.ready = ready;
    }


    /**
     * @return Returns the sex.
     * @hibernate.property column="sex"  not-null="true"
     */
    public String getSex()
    {
        return sex;
    }

    /**
     * @param sex The sex to set.
     */
    public void setSex( String sex )
    {
        this.sex = sex;
    }


    /**
     * @return Returns the duoclass
     * @hibernate.many-to-one class="de.jjw.model.duo.Duoclass" column="duoclass" update="false" insert="false"
     */
    public Duoclass getDuoclass()
    {
        return duoclass;
    }

    /**
     * @param duoclass The duoclass to set.
     */
    public void setDuoclass( Duoclass duoclass )
    {
        this.duoclass = duoclass;
    }


    /**
     * @return Returns the delete.
     * @hibernate.property column="delete_flag" length="1" type="yes_no"
     */
    public boolean isDelete()
    {
        return delete;
    }

    /**
     * @param delete The delete to set.
     */
    public void setDelete( boolean delete )
    {
        this.delete = delete;
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
        try
        {
            if ( o == null )
            {
                return false;
            }
            DuoTeam team = (DuoTeam) o;
            if ( this.getAge().equals( team.getAge() ) && this.getFirstname().equals( team.getFirstname() ) &&
                this.getName().equals( team.getName() ) && this.getFirstname2().equals( team.getFirstname2() ) &&
                this.getName2().equals( team.getName2() ) && this.getPlace() == team.getPlace() &&
                this.getSex().equals( team.getSex() ) && this.getTeam().equals( team.getTeam() ) &&
                this.getDuoclass().equals( team.getDuoclass() ) && this.getReady().equals( team.getReady() ) &&
                this.isDelete() == team.isDelete() &&

                this.getCreateDate().equals( team.getCreateDate() ) &&
                this.getCreateId().equals( team.getCreateId() ) && this.getId() == team.getId() &&
                this.getModificationDate().equals( team.getModificationDate() ) &&
                this.getModificationId().equals( team.getModificationId() ) )
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

   
    public boolean equalsIgnoreFighterName( Object o )
    {
        try
        {
            if ( o == null )
            {
                return false;
            }
            DuoTeam team = (DuoTeam) o;
            if ( this.getAge().equals( team.getAge() ) && this.getPlace() == team.getPlace()
                && this.getSex().equals( team.getSex() ) && this.getTeam().equals( team.getTeam() )
                && this.getDuoclassId() == team.getDuoclassId() && this.getReady().equals( team.getReady() )
                && this.isDelete() == team.isDelete() )
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
     * @param ageId
     * @hibernate.property column="age"
     */
    public long getAgeId()
    {
        return ageId;
    }

    public void setAgeId( long ageId )
    {
        this.ageId = ageId;
    }

    /**
     * @param teamId
     * @hibernate.property column="team"
     */
    public long getTeamId()
    {
        return teamId;
    }

    public void setTeamId( long teamId )
    {
        this.teamId = teamId;
    }

    /**
     * @param duoclassId
     * @hibernate.property column="duoclass"
     */
    public long getDuoclassId()
    {
        return duoclassId;
    }

    public void setDuoclassId( long duoclassId )
    {
        this.duoclassId = duoclassId;
    }

    /**
     * @param originalDuoclassId
     * @hibernate.property column="originalDuoclass"
     */
    public long getOriginalDuoclassId()
    {
        return originalDuoclassId;
    }

    public void setOriginalDuoclassId( long originalDuoclassId )
    {
        this.originalDuoclassId = originalDuoclassId;
    }

    /**
     * @return the yearOfBirth
     */
    public int getYearOfBirth()
    {
        return yearOfBirth;
    }

    /**
     * @param yearOfBirth the yearOfBirth to set
     */
    public void setYearOfBirth( int yearOfBirth )
    {
        this.yearOfBirth = yearOfBirth;
    }

    /**
     * @return the yearOfBirth2
     */
    public int getYearOfBirth2()
    {
        return yearOfBirth2;
    }

    /**
     * @param yearOfBirth2 the yearOfBirth2 to set
     */
    public void setYearOfBirth2( int yearOfBirth2 )
    {
        this.yearOfBirth2 = yearOfBirth2;
    }
    

    public int getMonthOfBirth() {
		return monthOfBirth;
	}

	public void setMonthOfBirth(int monthOfBirth) {
		this.monthOfBirth = monthOfBirth;
	}

	public int getDayOfBirth() {
		return dayOfBirth;
	}

	public void setDayOfBirth(int dayOfBirth) {
		this.dayOfBirth = dayOfBirth;
	}

	public int getMonthOfBirth2() {
		return monthOfBirth2;
	}

	public void setMonthOfBirth2(int monthOfBirth2) {
		this.monthOfBirth2 = monthOfBirth2;
	}

	public int getDayOfBirth2() {
		return dayOfBirth2;
	}

	public void setDayOfBirth2(int dayOfBirth2) {
		this.dayOfBirth2 = dayOfBirth2;
	}

	public boolean isMarkForExport()
    {
        return markForExport;
    }

    public void setMarkForExport( boolean markForExport )
    {
        this.markForExport = markForExport;
    }

        
}
