/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : Newaclass.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:48
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

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import de.jjw.model.BaseObject;
import de.jjw.model.User;
import de.jjw.model.admin.Age;
import de.jjw.util.TypeUtil;

/**
 * @hibernate.class table="newaclass"
 */
public class Newaclass
    extends BaseObject
    implements Serializable
{

    protected Long id;

    protected Age age;

    protected String sex;

    protected String weightclass;

    protected double startWeight;

    protected double weightLimit;

    protected boolean deleteStop = false;

    protected boolean complete = false;

    protected boolean inuse = false;

    protected long numberOfFighter = TypeUtil.LONG_MIN;

    protected long numberOfPotentialFighter = TypeUtil.LONG_MIN;

    protected Set user;

    protected boolean certificationPrint;

    protected Timestamp startTime = null;

    protected Timestamp endTime = null;

    protected String officialsLog;

    protected long numberOfFightsInClass = TypeUtil.LONG_MIN;

    protected long numberOfOpenFightsInClass = TypeUtil.LONG_MIN;

    protected long averageFighttimeInClass = TypeUtil.LONG_MIN;

    protected int fightsystem;

    protected Set tatamis = new HashSet();
    
    protected Set childs;
    
    protected Newaclass parent;

    protected Long parentId;


    public Newaclass()
    {
        super();
    }

    /**
     * Constructor for database
     *
     * @param newaclass
     * @param numberOfOpenFightsInClass
     * @param averageFighttimeInClass
     * @param numberOfFightsInClass
     */
    public Newaclass( Newaclass newaclass, long numberOfFighter, long numberOfFightsInClass,
                          long completedFights, int fightsystem, Object createDate, Object modificationDate
                          //, Long averageFighttimeInClass
    )
    {
        super();
        this.id = newaclass.getId();
        this.age = newaclass.getAge();
        this.sex = newaclass.getSex();
        this.weightclass = newaclass.getWeightclass();
        this.startWeight = newaclass.getStartWeight();
        this.weightLimit = newaclass.getWeightLimit();
        this.deleteStop = newaclass.isDeleteStop();
        this.complete = newaclass.isComplete();
        this.inuse = newaclass.isInuse();
        this.numberOfFighter = numberOfFighter;
        this.user = newaclass.getUser();
        this.certificationPrint = newaclass.isCertificationPrint();
        this.startTime = newaclass.getStartTime();
        this.endTime = newaclass.getEndTime();
        this.officialsLog = newaclass.getOfficialsLog();
        this.numberOfFightsInClass = numberOfFightsInClass;
        this.numberOfOpenFightsInClass = numberOfFightsInClass - completedFights;
        //	this.averageFighttimeInClass = averageFighttimeInClass==null? TypeUtil.LONG_EMPTY :averageFighttimeInClass;
        this.fightsystem = newaclass.getFightsystem();
        this.createDate = newaclass.getCreateDate();
        this.createId = newaclass.getCreateId();
        this.modificationDate = newaclass.getModificationDate();
        this.modificationId = newaclass.getModificationId();
        this.fightsystem = fightsystem;
        // if ( modificationDate != null )
        // {
        // this.startTime = ( (java.sql.Timestamp) createDate ).equals( (java.sql.Timestamp) modificationDate )
        // ? null
        // : (java.sql.Timestamp) modificationDate;
        // } because of explicit setting in off mode
        this.parentId = newaclass.getParentId();
        this.parent = newaclass.getParent();
    }

    /**
     * @param newaclass
     * @param numberOfFighter
     * @param numberOfPotentialFighter
     */
    public Newaclass( Newaclass newaclass, long numberOfPotentialFighter, long numberOfFighter )
    {
        super();
        this.id = newaclass.getId();
        this.age = newaclass.getAge();
        this.sex = newaclass.getSex();
        this.weightclass = newaclass.getWeightclass();
        this.startWeight = newaclass.getStartWeight();
        this.weightLimit = newaclass.getWeightLimit();
        this.deleteStop = newaclass.isDeleteStop();
        this.complete = newaclass.isComplete();
        this.inuse = newaclass.isInuse();
        this.numberOfFighter = numberOfFighter;
        this.numberOfPotentialFighter = numberOfPotentialFighter;
        this.user = newaclass.getUser();
        this.certificationPrint = newaclass.isCertificationPrint();
        this.startTime = newaclass.getStartTime();
        this.endTime = newaclass.getEndTime();
        this.officialsLog = newaclass.getOfficialsLog();
        //	this.averageFighttimeInClass = averageFighttimeInClass==null? TypeUtil.LONG_EMPTY :averageFighttimeInClass;
        this.fightsystem = newaclass.getFightsystem();
        this.createDate = newaclass.getCreateDate();
        this.createId = newaclass.getCreateId();
        this.modificationDate = newaclass.getModificationDate();
        this.modificationId = newaclass.getModificationId();
        this.parentId = newaclass.getParentId();
        this.parent = newaclass.getParent();
    }


    /**
     * @return Returns the certificationPrint.
     * @hibernate.property column="certificationPrint" type="yes_no"
     */
    public boolean isCertificationPrint()
    {
        return certificationPrint;
    }

    /**
     * @param certificationPrint The certificationPrint to set.
     */
    public void setCertificationPrint( boolean certificationPrint )
    {
        this.certificationPrint = certificationPrint;
    }

    /**
     * @return Returns the complete.
     * @hibernate.property column="complete" type="yes_no"
     */
    public boolean isComplete()
    {
        return complete;
    }

    /**
     * @param complete The complete to set.
     */
    public void setComplete( boolean complete )
    {
        this.complete = complete;
    }

    /**
     * @return Returns the deleteStop.
     * @hibernate.property column="deleteStop" type="yes_no"
     */
    public boolean isDeleteStop()
    {
        return deleteStop;
    }

    /**
     * @param deleteStop The deleteStop to set.
     */
    public void setDeleteStop( boolean deleteStop )
    {
        this.deleteStop = deleteStop;
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
     * @return Returns the numberOfFighter.
     */
    public long getNumberOfFighter()
    {
        return numberOfFighter;
    }

    /**
     * @param numberOfFighter The numberOfFighter to set.
     */
    public void setNumberOfFighter( long numberOfFighter )
    {
        this.numberOfFighter = numberOfFighter;
    }

    /**
     * @return Returns the user.
     */
    public Set getUser()
    {
        return user;
    }

    /**
     * @param user The user to set.
     */
    public void setUser( Set user )
    {
        this.user = user;
    }

    /**
     * @hibernate.property column="startTime"
     */
    public Timestamp getStartTime()
    {

        return this.startTime;
    }

    /**
     * @hibernate.property column="endTime"
     */
    public Timestamp getEndTime()
    {

        return this.endTime;
    }

    /**
     * @hibernate.property column="officialsLog" length="1000"
     */
    public String getOfficialsLog()
    {

        return this.officialsLog;
    }


    public void setStartTime( Timestamp startTime )
    {
        this.startTime = startTime;
    }

    public void setEndTime( Timestamp endTime )
    {
        this.endTime = endTime;
    }

    public void setOfficialsLog( String officialsLog )
    {
        this.officialsLog = officialsLog;
    }

    /**
     * @return Returns the inuse.
     * @hibernate.property column="inuse" type="yes_no"
     */
    public boolean isInuse()
    {
        return inuse;
    }

    public void setInuse( boolean inuse )
    {
        this.inuse = inuse;
    }

    public int getFightsystem()
    {
        return fightsystem;
    }

    public void setFightsystem( int fightsystem )
    {
        this.fightsystem = fightsystem;
    }


    /**
     * @return Returns the age.
     * @hibernate.many-to-one not-null="true" class="de.jjw.model.admin.Age" column="age"
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
     * @return Returns the sex.
     * @hibernate.property column="sex"
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
     * @return Returns the startWeight.
     * @hibernate.property column="startWeight"
     */
    public double getStartWeight()
    {
        return startWeight;
    }

    /**
     * @param startWeight The startWeight to set.
     */
    public void setStartWeight( double startWeight )
    {
        this.startWeight = startWeight;
    }


    /**
     * @return Returns the weightclass.
     * @hibernate.property column="weightclass"
     */
    public String getWeightclass()
    {
        return weightclass;
    }

    /**
     * @param weightclass The weightclass to set.
     */
    public void setWeightclass( String weightclass )
    {
        this.weightclass = weightclass;
    }

    /**
     * @return Returns the weightLimit.
     * @hibernate.property column="weightLimit"
     */
    public double getWeightLimit()
    {
        return weightLimit;
    }

    /**
     * @param weightLimit The weightLimit to set.
     */
    public void setWeightLimit( double weightLimit )
    {
        this.weightLimit = weightLimit;
    }


    public long getNumberOfFightsInClass()
    {
        return numberOfFightsInClass;
    }

    public void setNumberOfFightsInClass( long numberOfFightsInClass )
    {
        this.numberOfFightsInClass = numberOfFightsInClass;
    }

    public long getNumberOfOpenFightsInClass()
    {
        return numberOfOpenFightsInClass;
    }

    public void setNumberOfOpenFightsInClass( long numberOfOpenFightsInClass )
    {
        this.numberOfOpenFightsInClass = numberOfOpenFightsInClass;
    }

    public long getAverageFighttimeInClass()
    {
        return averageFighttimeInClass;
    }

    public void setAverageFighttimeInClass( long averageFighttimeInClass )
    {
        this.averageFighttimeInClass = averageFighttimeInClass;
    }


    public long getNumberOfPotentialFighter()
    {
        return numberOfPotentialFighter;
    }

    public void setNumberOfPotentialFighter( long numberOfPotentialFighter )
    {
        this.numberOfPotentialFighter = numberOfPotentialFighter;
    }

    /**
     * @hibernate.set table="user_role" cascade="save-update" lazy="false"
     * @hibernate.collection-key column="user_id"
     * @hibernate.collection-many-to-many class="de.jjw.model.Role" column="role_id"
     */
    public Set getTatamis()
    {
        return tatamis;
    }

    public void setTatamis( Set tatamis )
    {
        this.tatamis = tatamis;
    }

    public void addTatamis( User tatami )
    {
        this.tatamis.add( tatami );
    }

    
    /**
     * @hibernate.set table="Newaclass" inverse="true"
     * @hibernate.collection-key column="parent"
     * @hibernate.collection-one-to-many class="de.jjw.model.newa.Newaclass"
     */
    public Set<Newaclass> getChilds()
    {
		return childs;
	}

	public void setChilds(Set childs) {
		this.childs = childs;
	}

    /**
     * @return Returns parent
     * @hibernate.many-to-one class="de.jjw.model.newa.Newaclass" column="parent" insert="false" update="false"
     */
    public Newaclass getParent()
    {
		return parent;
	}

    public void setParent( Newaclass parent )
    {
		this.parent = parent;
	}

    /**
     * @return Returns the parentId.
     * @hibernate.property column="parent"
     */
    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId( Long parentId )
    {
        this.parentId = parentId;
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
            Newaclass newaclass = (Newaclass) o;

            if ( this.getAge().equals( newaclass.getAge() ) &&
                this.getCreateDate().equals( newaclass.getCreateDate() ) &&
                this.getCreateId().equals( newaclass.getCreateId() ) &&
                this.getId().equals( newaclass.getId() ) &&
                this.getModificationDate().equals( newaclass.getModificationDate() ) &&
                this.getModificationId().equals( newaclass.getModificationId() ) &&
                this.getNumberOfFighter() == newaclass.getNumberOfFighter() &&
                this.getSex().equals( newaclass.getSex() ) &&
                this.getStartWeight() == newaclass.getStartWeight() &&
                this.getUser().equals( newaclass.getUser() ) &&
                this.getWeightclass().equals( newaclass.getWeightclass() ) &&
                this.getWeightLimit() == newaclass.getWeightLimit() )
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

    public boolean equalsAgeSexWeights( Object o )
    {
        try
        {
            if ( o == null )
            {
                return false;
            }
            Newaclass newaclass = (Newaclass) o;

            if ( this.getAge().equals( newaclass.getAge() ) && this.getSex().equals( newaclass.getSex() )
                && this.getStartWeight() == newaclass.getStartWeight()
                && this.getWeightLimit() == newaclass.getWeightLimit() )
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

    @Override
    public String toString()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public static final HashMap PROPERTY_MAP = new HashMap<String, String>();

    static
    {
        PROPERTY_MAP.put( "age", "age" );
        PROPERTY_MAP.put( "sex", "sex" );
        PROPERTY_MAP.put( "weightclass", "weightclass" );
        PROPERTY_MAP.put( "startWeight", "startWeight" );
        PROPERTY_MAP.put( "weightLimit", "weightLimit" );
        PROPERTY_MAP.put( "deleteStop", "deleteStop" );
        PROPERTY_MAP.put( "complete", "complete" );
        PROPERTY_MAP.put( "certificationPrint", "certificationPrint" );
        PROPERTY_MAP.put( "startTime", "startTime" );
        PROPERTY_MAP.put( "endTime", "endTime" );
        PROPERTY_MAP.put( "officialsLog", "officialsLog" );
        PROPERTY_MAP.put( "inuse", "inuse" );

    }
}
