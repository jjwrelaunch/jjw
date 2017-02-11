/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : Fighter.java
 * Created : 15 Jun 2010
 * Last Modified: Tue, 15 Jun 2010 11:50:35
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

import java.io.Serializable;

import de.jjw.model.BaseObject;
import de.jjw.model.Team;
import de.jjw.model.admin.Age;
import de.jjw.util.TypeUtil;

/**
 * @author joerg.boehme
 * @hibernate.class table="fighter"
 */
public class Fighter
    extends BaseObject
    implements Serializable
{

    //protected long id = TypeUtil.LONG_MIN;

    protected Long id = null;

    protected String name;

    protected String firstname;

    protected Team team;

    protected String sex = "";

    protected double weight = 0;

    protected Fightingclass fightingclass;

    protected String ready;

    protected Age age;

    protected int place;

    protected boolean delete;

    protected long ageId;

    protected long teamId;

    protected long fightingclassId;
    
    protected long originalFightingclassId;

    protected int yearOfBirth;
    
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
     * @return Returns the weight.
     * @hibernate.property column="weight"
     */
    public double getWeight()
    {
        return weight;
    }

    /**
     * @param weight The weight to set.
     */
    public void setWeight( double weight )
    {
        this.weight = weight;
    }

    /**
     * @return Returns the fightingclass
     * @hibernate.many-to-one class="de.jjw.model.fighting.Fightingclass" column="fightingclass" update="false" insert="false"
     */
    public Fightingclass getFightingclass()
    {
        return fightingclass;
    }

    /**
     * @param fightingclass The fightingclass to set.
     */
    public void setFightingclass( Fightingclass fightingclass )
    {
        this.fightingclass = fightingclass;
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
            Fighter fighter = (Fighter) o;
            if ( this.getAge().equals( fighter.getAge() ) && this.getFirstname().equals( fighter.getFirstname() ) &&
                this.getName().equals( fighter.getName() ) && this.getPlace() == fighter.getPlace() &&
                this.getSex().equals( fighter.getSex() ) && this.getTeam().equals( fighter.getTeam() ) &&
                this.getWeight() == fighter.getWeight() &&
                this.getFightingclass().equals( fighter.getFightingclass() ) &&
                this.getReady().equals( fighter.getReady() ) && this.isDelete() == fighter.isDelete() &&

                this.getCreateDate().equals( fighter.getCreateDate() ) &&
                this.getCreateId().equals( fighter.getCreateId() ) && this.getId().equals( fighter.getId() ) &&
                this.getModificationDate().equals( fighter.getModificationDate() ) &&
 this.getModificationId().equals( fighter.getModificationId() ) )
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
            Fighter fighter = (Fighter) o;
            if ( this.getAge().equals( fighter.getAge() ) && this.getPlace() == fighter.getPlace()
                && this.getSex().equals( fighter.getSex() ) && this.getTeam().equals( fighter.getTeam() )
                && this.getWeight() == fighter.getWeight() && this.getFightingclassId() == fighter.getFightingclassId()
                && this.getReady().equals( fighter.getReady() ) && this.isDelete() == fighter.isDelete() )
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
     * @param fightingclassId
     * @hibernate.property column="fightingclass"
     */
    public long getFightingclassId()
    {
        return fightingclassId;
    }

    public void setFightingclassId( long fightingclassId )
    {
        this.fightingclassId = fightingclassId;
    }

    /**
     * @param originalFightingclassId
     * @hibernate.property column="originalFightingclass"
     */
	public long getOriginalFightingclassId() {
		return originalFightingclassId;
	}

	public void setOriginalFightingclassId(long originalFightingclassId) {
		this.originalFightingclassId = originalFightingclassId;
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

    public boolean isMarkForExport()
    {
        return markForExport;
    }

    public void setMarkForExport( boolean markForExport )
    {
        this.markForExport = markForExport;
    }

   
    
}
