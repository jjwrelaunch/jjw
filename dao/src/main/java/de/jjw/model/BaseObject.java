/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : BaseObject.java
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

package de.jjw.model;



import java.io.Serializable;
import java.sql.Timestamp;


/**
 * Base class for Model objects.  Child objects should implement toString(),
 * equals() and hashCode();
 * <p/>
 * <p>
 * <a href="BaseObject.java.html"><i>View Source</i></a>
 * </p>
 */
public abstract class BaseObject
    implements Serializable, ITechnicalDBFields, Cloneable
{
    public abstract String toString();

    public abstract boolean equals( Object o );

    public abstract int hashCode();

    protected Timestamp createDate;

    protected Timestamp modificationDate;

    protected Long createId;

    protected Long modificationId;

    /**
     * @hibernate.property column="createDate" not-null="true"
     */
    public Timestamp getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate( Timestamp createDate )
    {
        this.createDate = createDate;
    }

    /**
     * @hibernate.property column="createId"
     */
    public Long getCreateId()
    {
        return createId;
    }

    public void setCreateId( Long createId )
    {
        this.createId = createId;
    }

    /**
     * @hibernate.property column="modificationDate" not-null="true"
     */
    public Timestamp getModificationDate()
    {
        return modificationDate;
    }


    public void setModificationDate( Timestamp modificationDate )
    {
        this.modificationDate = modificationDate;
    }

    /**
     * @hibernate.property column="modificationId" not-null="true"
     */
    public Long getModificationId()
    {
        return modificationId;
    }

    public void setModificationId( Long modificationId )
    {
        this.modificationId = modificationId;
    }

}
