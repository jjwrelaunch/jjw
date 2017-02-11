/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : ConfigJJW.java
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

package de.jjw.model.admin;

import java.io.Serializable;
import java.sql.Timestamp;

import de.jjw.model.BaseObject;
import de.jjw.util.TypeUtil;

/**
 * @hibernate.class table="config"
 */
public class ConfigJJW
    extends BaseObject
    implements Serializable
{

    protected long id;

    protected String eventName = TypeUtil.STRING_DEFAULT;

    protected String eventLocation = TypeUtil.STRING_DEFAULT;

    protected String eventDate = TypeUtil.STRING_DEFAULT;

    protected int certificationType;

    protected int logo = TypeUtil.INT_DEFAULT;

    protected boolean fighterDeleteable;

    protected String websiteHeadLine1 = TypeUtil.STRING_DEFAULT;

    protected String websiteHeadLine2 = TypeUtil.STRING_DEFAULT;

    protected String pdfHeadLine1 = TypeUtil.STRING_DEFAULT;

    protected String pdfHeadLine2 = TypeUtil.STRING_DEFAULT;

    protected int fightRevenge = TypeUtil.INT_DEFAULT;

    protected int certificationPlaces = TypeUtil.INT_DEFAULT;

    public static final int TEAM_LOGO = 1;

    public static final int COUNTRY_LOGO = 2;

    protected Timestamp dumpSend;
    
    protected boolean videoOn;

    /**
     * @return Returns the id.
     * @hibernate.id column="id" generator-class="increment"
     */
    public long getId()
    {
        return id;
    }

    public void setId( long id )
    {
        this.id = id;
    }

    /**
     * @hibernate.property column="eventName" length="30"
     */
    public String getEventName()
    {
        return eventName;
    }

    public void setEventName( String eventName )
    {
        this.eventName = eventName;
    }

    /**
     * @return Returns the description.
     * @hibernate.property column="eventLocation" length="30"
     */
    public String getEventLocation()
    {
        return eventLocation;
    }

    public void setEventLocation( String eventLocation )
    {
        this.eventLocation = eventLocation;
    }

    /**
     * @hibernate.property column="eventDate" length="30"
     */
    public String getEventDate()
    {
        return eventDate;
    }

    public void setEventDate( String eventDate )
    {
        this.eventDate = eventDate;
    }

    /**
     * @hibernate.property column="certificationType"
     */
    public int getCertificationType()
    {
        return certificationType;
    }

    public void setCertificationType( int certificationType )
    {
        this.certificationType = certificationType;
    }

    /**
     * @hibernate.property column="logo"
     */
    public int getLogo()
    {
        return logo;
    }

    public void setLogo( int logo )
    {
        this.logo = logo;
    }

    /**
     * @hibernate.property column="fighterDeleteable" type="yes_no"
     */
    public boolean isFighterDeleteable()
    {
        return fighterDeleteable;
    }

    public void setFighterDeleteable( boolean fighterDeleteable )
    {
        this.fighterDeleteable = fighterDeleteable;
    }

    /**
     * @return Returns the websiteHeadLine1
     * @hibernate.property column="websiteHeadLine1" length="30"
     */
    public String getWebsiteHeadLine1()
    {
        return websiteHeadLine1;
    }

    public void setWebsiteHeadLine1( String websiteHeadLine1 )
    {
        this.websiteHeadLine1 = websiteHeadLine1;
    }

    /**
     * @hibernate.property column="websiteHeadLine2" length="30"
     */
    public String getWebsiteHeadLine2()
    {
        return websiteHeadLine2;
    }

    public void setWebsiteHeadLine2( String websiteHeadLine2 )
    {
        this.websiteHeadLine2 = websiteHeadLine2;
    }

    /**
     * @hibernate.property column="pdfHeadLine1" length="30"
     */
    public String getPdfHeadLine1()
    {
        return pdfHeadLine1;
    }

    public void setPdfHeadLine1( String pdfHeadLine1 )
    {
        this.pdfHeadLine1 = pdfHeadLine1;
    }

    /**
     * @hibernate.property column="pdfHeadLine2" length="30"
     */
    public String getPdfHeadLine2()
    {
        return pdfHeadLine2;
    }

    public void setPdfHeadLine2( String pdfHeadLine2 )
    {
        this.pdfHeadLine2 = pdfHeadLine2;
    }

    /**
     * @hibernate.property column="fightRevenge"
     */
    public int getFightRevenge()
    {
        return fightRevenge;
    }

    public void setFightRevenge( int fightRevenge )
    {
        this.fightRevenge = fightRevenge;
    }


    /**
     * @hibernate.property column="certificationPlaces"
     */
    public int getCertificationPlaces()
    {
        return certificationPlaces;
    }

    /**
     * @param certificationPlaces the certificationPlaces to set
     */
    public void setCertificationPlaces( int certificationPlaces )
    {
        this.certificationPlaces = certificationPlaces;
    }

    /**
     * @hibernate.property column="send" not-null="true"
     * @return the dumpSend
     */
    public Timestamp getDumpSend()
    {
        return dumpSend;
    }

    /**
     * @param dumpSend the dumpSend to set
     */
    public void setDumpSend( Timestamp dumpSend )
    {
        this.dumpSend = dumpSend;
    }
    
    
    /**
     * @hibernate.property column="videoOn" type="yes_no"
     * @return the dumpSend
     */
    public boolean isVideoOn()
    {
        return videoOn;
    }

    public void setVideoOn( boolean videoOn )
    {
        this.videoOn = videoOn;
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

    @Override
    public String toString()
    {
        // TODO Auto-generated method stub
        return null;
    }


}
