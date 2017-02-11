/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : Codestable.java
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

package de.jjw.model.generalhelper;

import java.io.Serializable;

/**
 * @author joerg.boehme
 * @hibernate.class table="codestable"
 */
public class Codestable
    implements Serializable
{

    public static final String ERROR = "ERR";

    public static final String CODE = "COD";

    protected String id;

    protected String type;

    protected String value;

    protected int ordernb;

    protected int level;

    protected String locale;

    protected int syn_id;

    /**
     * @hibernate.property column="id" length="30" not-null="true" unique="true"
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id The id to set.
     */
    public void setId( String id )
    {
        this.id = id;
    }

    /**
     * @hibernate.property column="level" not-null="true"
     */
    public int getLevel()
    {
        return level;
    }

    /**
     * @param level The level to set.
     */
    public void setLevel( int level )
    {
        this.level = level;
    }

    /**
     * @hibernate.property column="ordernb" not-null="true"
     */
    public int getOrder()
    {
        return ordernb;
    }

    /**
     * @param order The order to set.
     */
    public void setOrder( int order )
    {
        this.ordernb = order;
    }

    /**
     * @hibernate.property column="type"  length="3"
     */
    public String getType()
    {
        return type;
    }

    /**
     * @param type The type to set.
     */
    public void setType( String type )
    {
        this.type = type;
    }

    /**
     * @hibernate.property column="value"  length="300"
     */
    public String getValue()
    {
        return value;
    }

    /**
     * @param value The value to set.
     */
    public void setValue( String value )
    {
        this.value = value;
    }

    /**
     * @hibernate.property column="locale"  length="10"
     */
    public String getLocale()
    {
        return locale;
    }

    /**
     * @param locale The locale to set.
     */
    public void setLocale( String locale )
    {
        this.locale = locale;
    }


    /**
     * @hibernate.id column="syn_id"
     */
    private int getSyn_id()
    {
        return syn_id;
    }

    /**
     * @param syn_id the syn_id to set
     */
    public void setSyn_id( int syn_id )
    {
        this.syn_id = syn_id;
    }

}
