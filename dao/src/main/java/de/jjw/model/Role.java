/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : Role.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:42
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

import org.springframework.security.core.GrantedAuthority;

/**
 * This class is used to represent available roles in the database.</p>
 * <p/>
 * <p><a href="Role.java.html"><i>View Source</i></a></p>
 *
 * @hibernate.class table="role"
 */
public class Role
    extends BaseObject
    implements Serializable, GrantedAuthority
{
    private static final long serialVersionUID = 3690197650654049848L;

    private Long id;

    private String name;

    private String description;

    public Role()
    {
    }

    public Role( String name )
    {
        this.name = name;
    }

    /**
     * @hibernate.id column="id" generator-class="native" unsaved-value="null"
     */
    public Long getId()
    {
        return id;
    }


    public String getAuthority()
    {
        return getName();
    }

    /**
     * @hibernate.property column="name" length="20"
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * @hibernate.property column="description" length="64"
     */
    public String getDescription()
    {
        return this.description;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof Role ) )
        {
            return false;
        }

        final Role role = (Role) o;

        if ( name != null ? !name.equals( role.name ) : role.name != null )
        {
            return false;
        }

        return true;
    }

    public int hashCode()
    {
        return ( name != null ? name.hashCode() : 0 );
    }

    public String toString()
    {
        return "not impl";

    }

}
