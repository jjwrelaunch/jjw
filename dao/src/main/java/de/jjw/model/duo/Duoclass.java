/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : Duoclass.java
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

package de.jjw.model.duo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Set;

import de.jjw.model.BaseObject;
import de.jjw.model.admin.Age;
import de.jjw.util.TypeUtil;

/**
 * @hibernate.class table="duoclass"
 */
public class Duoclass
    extends BaseObject
    implements Serializable
{

    protected Long id;

    protected Age age;

    protected String sex;

    protected boolean deleteStop = false;

    protected boolean complete = false;

    protected boolean inuse = false;

    protected long numberOfTeams = TypeUtil.LONG_MIN;

    protected long numberOfPotentialTeams = TypeUtil.LONG_MIN;

    protected Set user;

    protected boolean certificationPrint;

    protected Timestamp startTime = null;

    protected Timestamp endTime = null;

    protected String officialsLog;

    protected long numberOfFightsInClass = TypeUtil.LONG_MIN;

    protected long numberOfOpenFightsInClass = TypeUtil.LONG_MIN;

    protected long averageFighttimeInClass = TypeUtil.LONG_MIN;

    protected int fightsystem;

    protected String duoclassName;

    protected Set childs;

    protected Duoclass parent;

    protected Long parentId;


    public Duoclass()
    {
        super();
    }

    /**
     * Constructor for database
     *
     * @param duoclass
     * @param numberOfOpenFightsInClass
     * @param averageFighttimeInClass
     * @param numberOfFightsInClass
     */
    public Duoclass( Duoclass duoclass, long numberOfTeams, long numberOfFightsInClass, long completedFights,
                     int fightsystem, Object createDate, Object modificationDate
                     //, Long averageFighttimeInClass
    )
    {
        super();
        this.id = duoclass.getId();
        this.age = duoclass.getAge();
        this.sex = duoclass.getSex();
        this.deleteStop = duoclass.isDeleteStop();
        this.complete = duoclass.isComplete();
        this.inuse = duoclass.isInuse();
        this.numberOfTeams = numberOfTeams;
        this.user = duoclass.getUser();
        this.certificationPrint = duoclass.isCertificationPrint();
        this.startTime = duoclass.getStartTime();
        this.endTime = duoclass.getEndTime();
        this.officialsLog = duoclass.getOfficialsLog();
        this.numberOfFightsInClass = numberOfFightsInClass;
        this.numberOfOpenFightsInClass = numberOfFightsInClass - completedFights;
        //	this.averageFighttimeInClass = averageFighttimeInClass==null? TypeUtil.LONG_EMPTY :averageFighttimeInClass;
        this.fightsystem = duoclass.getFightsystem();
        this.createDate = duoclass.getCreateDate();
        this.createId = duoclass.getCreateId();
        this.modificationDate = duoclass.getModificationDate();
        this.modificationId = duoclass.getModificationId();
        this.fightsystem = fightsystem;
        // if ( modificationDate != null )
        // {
        // this.startTime = ( (java.sql.Timestamp) createDate ).equals( (java.sql.Timestamp) modificationDate )
        // ? null
        // : (java.sql.Timestamp) modificationDate;
        // } @see Fightingclass.java
        this.duoclassName = duoclass.getDuoclassName();
        this.parentId = duoclass.getParentId();
        this.parent = duoclass.getParent();
    }

    /**
     * @param duoclass
     * @param numberOfTeams
     * @param numberOfPotentialFighter
     */
    public Duoclass( Duoclass duoclass, long numberOfPotentialFighter, long numberOfTeams )
    {
        super();
        this.id = duoclass.getId();
        this.age = duoclass.getAge();
        this.sex = duoclass.getSex();
        this.deleteStop = duoclass.isDeleteStop();
        this.complete = duoclass.isComplete();
        this.inuse = duoclass.isInuse();
        this.numberOfTeams = numberOfTeams;
        this.numberOfPotentialTeams = numberOfPotentialFighter;
        this.user = duoclass.getUser();
        this.certificationPrint = duoclass.isCertificationPrint();
        this.startTime = duoclass.getStartTime();
        this.endTime = duoclass.getEndTime();
        this.officialsLog = duoclass.getOfficialsLog();
        //	this.averageFighttimeInClass = averageFighttimeInClass==null? TypeUtil.LONG_EMPTY :averageFighttimeInClass;
        this.fightsystem = duoclass.getFightsystem();
        this.createDate = duoclass.getCreateDate();
        this.createId = duoclass.getCreateId();
        this.modificationDate = duoclass.getModificationDate();
        this.modificationId = duoclass.getModificationId();
        this.duoclassName = duoclass.getDuoclassName();
        this.parentId = duoclass.getParentId();
        this.parent = duoclass.getParent();
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
     * @return Returns the numberOfTeams.
     */
    public long getNumberOfTeams()
    {
        return numberOfTeams;
    }

    /**
     * @param numberOfTeams The numberOfTeams to set.
     */
    public void setNumberOfTeams( long numberOfTeams )
    {
        this.numberOfTeams = numberOfTeams;
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


    public long getNumberOfPotentialTeams()
    {
        return numberOfPotentialTeams;
    }

    public void setNumberOfPotentialTeams( long numberOfPotentialTeams )
    {
        this.numberOfPotentialTeams = numberOfPotentialTeams;
    }


    /**
     * @return Returns the duoclass.
     * @hibernate.property column="duoclassName"
     */
    public String getDuoclassName()
    {
        return duoclassName;
    }

    public void setDuoclassName( String duoclass )
    {
        this.duoclassName = duoclass;
    }

    /**
     * @hibernate.set table="Duoclass" inverse="true"
     * @hibernate.collection-key column="parent"
     * @hibernate.collection-one-to-many class="de.jjw.model.duo.Duoclass"
     */
    public Set<Duoclass> getChilds()
    {
        return childs;
    }

    public void setChilds( Set childs )
    {
        this.childs = childs;
    }

    /**
     * @return Returns parent
     * @hibernate.many-to-one class="de.jjw.model.duo.Duoclass" column="parent" insert="false" update="false"
     */
    public Duoclass getParent()
    {
        return parent;
    }

    public void setParent( Duoclass parent )
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
            Duoclass duoclass = (Duoclass) o;

            if ( this.getAge().equals( duoclass.getAge() ) && this.getCreateDate().equals( duoclass.getCreateDate() ) &&
                this.getCreateId().equals( duoclass.getCreateId() ) && this.getId().equals( duoclass.getId() ) &&
                this.getModificationDate().equals( duoclass.getModificationDate() ) &&
                this.getModificationId().equals( duoclass.getModificationId() ) &&
                this.getNumberOfTeams() == duoclass.getNumberOfTeams() && this.getSex().equals( duoclass.getSex() ) &&
                this.getUser().equals( duoclass.getUser() ) &&
                this.getDuoclassName().equals( duoclass.getDuoclassName() )

                )
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


    public boolean equalsAgeSex( Object o )
    {
        try
        {
            if ( o == null )
            {
                return false;
            }
            Duoclass duoclass = (Duoclass) o;

            if ( this.getAge().equals( duoclass.getAge() ) && this.getSex().equals( duoclass.getSex() ) )
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
        PROPERTY_MAP.put( "deleteStop", "deleteStop" );
        PROPERTY_MAP.put( "complete", "complete" );
        PROPERTY_MAP.put( "certificationPrint", "certificationPrint" );
        PROPERTY_MAP.put( "startTime", "startTime" );
        PROPERTY_MAP.put( "endTime", "endTime" );
        PROPERTY_MAP.put( "officialsLog", "officialsLog" );
        PROPERTY_MAP.put( "inuse", "inuse" );

    }
}
