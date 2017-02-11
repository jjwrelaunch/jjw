/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : UserDuoclass.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:47
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

import de.jjw.model.BaseObject;
import de.jjw.model.User;
import de.jjw.util.TypeUtil;

import java.io.Serializable;

/**
 * @hibernate.class table="user_duoclass"
 */
public class UserDuoclass
    extends BaseObject
    implements Serializable
{

    //protected long id = TypeUtil.LONG_MIN;

    protected Long id = null;

    protected Duoclass duoclass;

    protected User user;

    protected long orderNumber;

    protected long userId;

    protected long duoclassId;


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
     * @return Returns the duoclass
     * @hibernate.many-to-one class="de.jjw.model.duo.Duoclass" column="duoclass_id" update="false" insert="false"
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

    @Override
    public boolean equals( Object o )
    {

        try
        {
            if ( o == null )
            {
                return false;
            }
            UserDuoclass duoteam = (UserDuoclass) o;
            if (

                this.getUser().equals( duoteam.getUser() ) &&

                    this.getDuoclass().equals( duoteam.getDuoclass() ) &&

                    this.getCreateDate().equals( duoteam.getCreateDate() ) &&
                    this.getCreateId().equals( duoteam.getCreateId() ) && this.getId() == duoteam.getId() &&
                    this.getModificationDate().equals( duoteam.getModificationDate() ) &&
                    this.getModificationId().equals( duoteam.getModificationId() ) )
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
     * @hibernate.property column="duoclass_id"
     */
    public long getDuoclassId()
    {
        return duoclassId;
    }

    public void setDuoclassId( long duoclassId )
    {
        this.duoclassId = duoclassId;
    }

    @Override
    public String toString()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public long getOrderNumber()
    {
        return orderNumber;
    }

    public void setOrderNumber( long orderNumber )
    {
        this.orderNumber = orderNumber;
    }

}
