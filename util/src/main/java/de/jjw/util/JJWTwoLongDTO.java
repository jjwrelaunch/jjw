/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : JJWTwoLongDTO.java
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

package de.jjw.util;

public class JJWTwoLongDTO
{

    protected long long1;

    protected long long2;


    public JJWTwoLongDTO( long long1, long long2 )
    {
        super();
        this.long1 = long1;
        this.long2 = long2;
    }

    public long getLong1()
    {
        return long1;
    }

    public void setLong1( long long1 )
    {
        this.long1 = long1;
    }

    public long getLong2()
    {
        return long2;
    }

    public void setLong2( long long2 )
    {
        this.long2 = long2;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) ( long1 ^ ( long1 >>> 32 ) );
        result = prime * result + (int) ( long2 ^ ( long2 >>> 32 ) );
        return result;
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
        {
            return true;
        }
        if ( obj == null )
        {
            return false;
        }
        if ( getClass() != obj.getClass() )
        {
            return false;
        }
        JJWTwoLongDTO other = (JJWTwoLongDTO) obj;
        if ( long1 != other.long1 )
        {
            return false;
        }
        if ( long2 != other.long2 )
        {
            return false;
        }
        return true;
    }


}
