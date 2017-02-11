/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : UserNewaclass.java
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

import de.jjw.model.BaseObject;
import de.jjw.model.User;
import de.jjw.util.TypeUtil;

/**
 * @hibernate.class table="user_newaclass"
 */
public class UserNewaclass
    extends BaseObject
    implements Serializable
{

    //protected long id = TypeUtil.LONG_MIN;

    protected Long id = null;

    protected Newaclass newaclass;

    protected User user;

    protected long orderNumber;

    protected long userId;

    protected long newaclassId;


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
     * @hibernate.many-to-one class="de.jjw.model.newa.Newaclass" column="newaclass_id" update="false" insert="false"
     */
    public Newaclass getNewaclass()
    {
        return newaclass;
    }

    /**
     * @param newaclass The Newaclass to set.
     */
    public void setNewaclass( Newaclass newaclass )
    {
        this.newaclass = newaclass;
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
            UserNewaclass fighter = (UserNewaclass) o;
            if (

                this.getUser().equals( fighter.getUser() ) &&

            this.getNewaclass().equals( fighter.getNewaclass() ) &&

                    this.getCreateDate().equals( fighter.getCreateDate() ) &&
                    this.getCreateId().equals( fighter.getCreateId() ) && this.getId() == fighter.getId() &&
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
     * @hibernate.property column="newaclass_id"
     */
    public long getNewaclassId()
    {
        return newaclassId;
    }

    public void setNewaclassId( long newaclassId )
    {
        this.newaclassId = newaclassId;
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
