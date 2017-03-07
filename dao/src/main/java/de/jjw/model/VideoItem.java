/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : VideoItem.java
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

package de.jjw.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import de.jjw.model.admin.Country;
import de.jjw.model.admin.Region;
import de.jjw.model.fighting.Fighter;

/**
 * @hibernate.class table="video"
 */
public class VideoItem
    extends BaseObject
    implements Serializable
{

    private Long id;

    private String text;
    
    private String description;

    private Long fightId;

    private String discepline;

    private byte[] video;
    
    private long size;
    
    private long participanIdRed;

    private long participanIdBlue;
    
    private String nameRed; // not in db
    
    private String nameBlue; // not in db
    
    private String sex; // not in db
    
    private String classname; // not in db
    
    private String ageDescription; // not in db
    
    private String filename;
    
    private boolean isScreenVideo = false;

    public VideoItem(){
        super();
    }

    public VideoItem( Long id, String text, String description, Long fightId, String discepline,long participanIdRed,long participanIdBlue )
    {
        super();
        this.id = id;
        this.text = text;
        this.description = description;
        this.fightId = fightId;
        this.discepline = discepline;
        this.video= null;
        
        this.participanIdRed=participanIdRed;
        this.participanIdBlue=participanIdBlue;
        
    }

    /**
     * @hibernate.property column="video"
     */
    public byte[] getVideo()
    {
        return video;
    }

    public void setVideo( byte[] video )
    {
        this.video = video;
    }

    /**
     * @hibernate.id column="id" generator-class="increment"
     */
    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    /**
     * @hibernate.property column="text"
     */
    public String getText()
    {
        return text;
    }

    public void setText( String text )
    {
        this.text = text;
    }

    /**
     * @hibernate.property column="description"
     */
    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    /**
     * @hibernate.property column="fightId"
     */
    public Long getFightId()
    {
        return fightId;
    }

    public void setFightId( Long fightId )
    {
        this.fightId = fightId;
    }

    /**
     * @hibernate.property column="discepline"
     */
    public String getDiscepline()
    {
        return discepline;
    }

    public void setDiscepline( String discepline )
    {
        this.discepline = discepline;
    }

    
    
    /**
     * @hibernate.property column="participanIdRed"
     */
    public long getParticipanIdRed()
    {
        return participanIdRed;
    }

    public void setParticipanIdRed( long participanIdRed )
    {
        this.participanIdRed = participanIdRed;
    }

    /**
     * @hibernate.property column="participanIdBlue"
     */
    public long getParticipanIdBlue()
    {
        return participanIdBlue;
    }

    public void setParticipanIdBlue( long participanIdBlue )
    {
        this.participanIdBlue = participanIdBlue;
    }

    
    
    public long getSize()
    {
        return size;
    }

    public void setSize( long size )
    {
        this.size = size;
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

    public String getNameRed()
    {
        return nameRed;
    }

    public void setNameRed( String nameRed )
    {
        this.nameRed = nameRed;
    }

    public String getNameBlue()
    {
        return nameBlue;
    }

    public void setNameBlue( String nameBlue )
    {
        this.nameBlue = nameBlue;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex( String sex )
    {
        this.sex = sex;
    }

    public String getClassname()
    {
        return classname;
    }

    public void setClassname( String classname )
    {
        this.classname = classname;
    }

    public String getAgeDescription()
    {
        return ageDescription;
    }

    public void setAgeDescription( String ageDescription )
    {
        this.ageDescription = ageDescription;
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFilename( String filename )
    {
        this.filename = filename;
    }

    public boolean isScreenVideo()
    {
        return isScreenVideo;
    }

    public void setScreenVideo( boolean isScreenVideo )
    {
        this.isScreenVideo = isScreenVideo;
    }

  
   
}
