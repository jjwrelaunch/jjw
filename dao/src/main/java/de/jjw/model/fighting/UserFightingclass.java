/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : UserFightingclass.java
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

package de.jjw.model.fighting;

import de.jjw.model.BaseObject;
import de.jjw.model.User;
import de.jjw.util.TypeUtil;

import java.io.Serializable;

/**
 * @hibernate.class table="user_weightclass"
 */
public class UserFightingclass
    extends BaseObject
    implements Serializable
{

    //protected long id = TypeUtil.LONG_MIN;

    protected Long id = null;

    protected Fightingclass fightingclass;

    protected User user;

    protected long userId;

    protected long fightingclassId;
    
    protected long orderNumber;
    


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
     * @return Returns the user.
     * @hibernate.many-to-one not-null="true" class="de.jjw.model.User" column="user_id" update="false" insert="false"
     */
    public User getUser()
    {
        return user;
    }

    /**
     * @param user The age to set.
     */
    public void setUser( User user )
    {
        this.user = user;
    }

    /**
     * @return Returns the fightingclass
     * @hibernate.many-to-one class="de.jjw.model.fighting.Fightingclass" column="weightclass_id" update="false" insert="false"
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

    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        UserFightingclass other = (UserFightingclass) obj;
        if ( fightingclass == null )
        {
            if ( other.fightingclass != null )
                return false;
        }
        else if ( !fightingclass.equals( other.fightingclass ) )
            return false;
        if ( fightingclassId != other.fightingclassId )
            return false;
        if ( id == null )
        {
            if ( other.id != null )
                return false;
        }
        else if ( !id.equals( other.id ) )
            return false;
        if ( orderNumber != other.orderNumber )
            return false;
        if ( user == null )
        {
            if ( other.user != null )
                return false;
        }
        else if ( !user.equals( other.user ) )
            return false;
        if ( userId != other.userId )
            return false;
        return true;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( fightingclass == null ) ? 0 : fightingclass.hashCode() );
        result = prime * result + (int) ( fightingclassId ^ ( fightingclassId >>> 32 ) );
        result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
        result = prime * result + (int) ( orderNumber ^ ( orderNumber >>> 32 ) );
        result = prime * result + ( ( user == null ) ? 0 : user.hashCode() );
        result = prime * result + (int) ( userId ^ ( userId >>> 32 ) );
        return result;
    }

    /**
     * @param teamId
     * @hibernate.property column="user_id"
     */
    public long getUserId()
    {
        return userId;
    }

    public void setUserId( long userId )
    {
        this.userId = userId;
    }

    /**
     * @param
     * @hibernate.property column="weightclass_id"
     */
    public long getFightingclassId()
    {
        return fightingclassId;
    }

    public void setFightingclassId( long fightingclassId )
    {
        this.fightingclassId = fightingclassId;
    }

    @Override
    public String toString()
    {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param
     * @hibernate.property column="order"
     */
    public long getOrderNumber()
    {
        return orderNumber;
    }

    public void setOrderNumber( long orderNumber )
    {
        this.orderNumber = orderNumber;
    }

    
}
