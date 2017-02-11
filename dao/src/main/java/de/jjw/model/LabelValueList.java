/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : LabelValueList.java
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

package de.jjw.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

public class LabelValueList
    implements Comparable, Serializable
{
    private String label = null;

    private List<String> valueList = null;

    public static final Comparator CASE_INSENSITIVE_ORDER = new Comparator()
    {
        public int compare( Object o1, Object o2 )
        {
            String label1 = ( (LabelValueList) o1 ).getLabel();
            String label2 = ( (LabelValueList) o2 ).getLabel();
            return label1.compareToIgnoreCase( label2 );
        }
    };

    public LabelValueList()
    {
        super();
    }

    public LabelValueList( String label, List<String> valueList )
    {
        this.label = label;
        this.valueList = valueList;
    }

    public int compareTo( Object o )
    {
        // Implicitly tests for the correct type, throwing
        // ClassCastException as required by interface
        String otherLabel = ( (LabelValueList) o ).getLabel();

        return this.getLabel().compareTo( otherLabel );
    }

    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer( "LabelValue[" );
        sb.append( this.label );
        sb.append( ", " );
        sb.append( this.valueList );
        sb.append( "]" );
        return ( sb.toString() );
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj == this )
        {
            return true;
        }

        if ( !( obj instanceof LabelValueList ) )
        {
            return false;
        }

        LabelValueList bean = (LabelValueList) obj;
        int nil = ( this.getValueList() == null ) ? 1 : 0;
        nil += ( bean.getValueList() == null ) ? 1 : 0;

        if ( nil == 2 )
        {
            return true;
        }
        else if ( nil == 1 )
        {
            return false;
        }
        else
        {
            return this.getValueList().equals( bean.getValueList() );
        }

    }

    @Override
    public int hashCode()
    {
        return ( this.getValueList() == null ) ? 17 : this.getValueList().hashCode();
    }

    public String getLabel()
    {
        return this.label;
    }

    public void setLabel( String label )
    {
        this.label = label;
    }
    
    public List<String> getValueList()
    {
        return this.valueList;
    }

    public String getValue(int index)
    {
        return this.valueList.get( index );
    }

    public void setValueList( List<String> value )
    {
        this.valueList = value;
    }
    
    public String getValue1()
    {
        if ( this.valueList != null && this.valueList.size() >= 1 && this.valueList.get( 0 ) != null )
            return this.valueList.get( 0 );
        else
            return "";
    }
    
    public String getValue2()
    {
        if ( this.valueList != null && this.valueList.size() >= 2 && this.valueList.get( 1 ) != null )
            return this.valueList.get( 1 );
        else
            return "";
    }
}
