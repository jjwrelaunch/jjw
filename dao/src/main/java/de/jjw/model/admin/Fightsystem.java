/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : Fightsystem.java
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

import de.jjw.model.BaseObject;
import de.jjw.util.TypeUtil;

import java.io.Serializable;


/**
 * @hibernate.class table="fightsystem"
 */
public class Fightsystem
    extends BaseObject
    implements Serializable
{

    public static final int SIMPLE_POOL = 1;

    public static final int DOUBLE_POOL = 2;

    public static final int SIMPLE_KO = 3;

    public static final int DOUBLE_KO = 4;


    protected Long id;

    protected int minParticipant;

    protected int maxParticipant;

    protected int fightsystemType;

    protected String minParticipantString;

    protected String maxParticipantString;

    protected String fightsystemTypeString;


    /**
     * @return Returns the fightsystemType.
     * @hibernate.property column="system"
     */
    public Integer getFightsystemType()
    {
        return fightsystemType;
    }

    /**
     * @param fightsystemType The fightsystemType to set.
     */
    public void setFightsystemType( Integer fightsystemType )
    {
        this.fightsystemType = fightsystemType;
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
     * @return Returns the maxParticipant.
     * @hibernate.property column="maxFighter"
     */
    public int getMaxParticipant()
    {
        return maxParticipant;
    }

    /**
     * @param maxParticipant The maxParticipant to set.
     */
    public void setMaxParticipant( int maxParticipant )
    {
        this.maxParticipant = maxParticipant;
    }

    /**
     * @return Returns the minParticipant.
     * @hibernate.property column="minFighter"
     */
    public int getMinParticipant()
    {
        return minParticipant;
    }

    /**
     * @param minParticipant The minParticipant to set.
     */
    public void setMinParticipant( int minParticipant )
    {
        this.minParticipant = minParticipant;
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

    /**
     * @return Returns the fightsystemTypeString.
     */
    public String getFightsystemTypeString()
    {
        if ( !TypeUtil.isEmpty( fightsystemTypeString ) )
        {
            return fightsystemTypeString;
        }
        else
        {
            return TypeUtil.toString( fightsystemType );
        }
    }

    /**
     * @param fightsystemTypeString The fightsystemTypeString to set.
     */
    public void setFightsystemTypeString( String fightsystemTypeString )
    {
        this.fightsystemTypeString = fightsystemTypeString;

    }
    /**
     * @return Returns the maxParticipantString.
     */
//    public String getMaxParticipantString() {
//    	if (!TypeUtil.isEmpty(maxParticipantString))
//        	return maxParticipantString;
//            else return maxParticipant == null ? TypeUtil.STRING_DEFAULT :TypeUtil.toString(maxParticipant);
//    }

    /**
     * @param maxParticipantString The maxParticipantString to set.
     */
    public void setMaxParticipantString( String maxParticipantString )
    {
        this.maxParticipantString = maxParticipantString;

    }
    /**
     * @return Returns the minParticipantString.
     */
//    public String getMinParticipantString() {
//		if (!TypeUtil.isEmpty(minParticipantString))
//			return minParticipantString;
//		else
//			return minParticipant == null ? TypeUtil.STRING_DEFAULT :TypeUtil.toString(minParticipant);
//	}

    /**
     * @param minParticipantString The minParticipantString to set.
     */
    public void setMinParticipantString( String minParticipantString )
    {
        this.minParticipantString = minParticipantString;

    }


}
