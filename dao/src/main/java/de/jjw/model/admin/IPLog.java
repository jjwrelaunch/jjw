/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : IPLog.java
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

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @hibernate.class table="ip_log"
 */
public class IPLog
    implements Serializable
{

    protected Long id;

    protected String name;

    protected String pw;

    protected String ip;

    protected Timestamp createDate;

    protected String locale;


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
     * @return the name
     * @hibernate.property column="name"
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName( String name )
    {
        this.name = name;
    }

    /**
     * @return the pw
     * @hibernate.property column="pw"
     */
    public String getPw()
    {
        return pw;
    }

    /**
     * @param pw the pw to set
     */
    public void setPw( String pw )
    {
        this.pw = pw;
    }

    /**
     * @return the ip
     * @hibernate.property column="ip"
     */
    public String getIp()
    {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp( String ip )
    {
        this.ip = ip;
    }


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
     * @return the locale
     * @hibernate.property column="locale"
     */
    public String getLocale()
    {
        return locale;
    }

    /**
     * @param locale the locale to set
     */
    public void setLocale( String locale )
    {
        this.locale = locale;
    }

}
