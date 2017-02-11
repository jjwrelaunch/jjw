/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : TypeUtil.java
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

package de.jjw.util;

import java.sql.Timestamp;

public class TypeUtil
    implements IValueConstants
{


    public static String getStringDefault()
    {
        return STRING_DEFAULT;
    }

    public static boolean isEmptyOrDefault( String value )
    {
        if ( value == null || STRING_DEFAULT.equals( value ) )
        {
            return true;
        }
        return false;
    }

    public static boolean isEmpty( Object value )
    {
        if ( value == null )
        {
            return true;
        }
        return false;
    }

    public static boolean isEmpty( String value )
    {
        if ( value == null )
        {
            return true;
        }
        return false;
    }

    public static boolean isDefault( String value )
    {
        if ( value != null && STRING_DEFAULT.equals( value ) )
        {
            return true;
        }
        return false;
    }

    public static boolean isEmptyOrDefault( int value )
    {
        if ( value == INT_MIN || value == INT_DEFAULT )
        {
            return true;
        }
        return false;
    }

    public static boolean isEmpty( int value )
    {
        if ( value == INT_MIN )
        {
            return true;
        }
        return false;
    }

    public static boolean isDefault( int value )
    {
        if ( value == INT_DEFAULT )
        {
            return true;
        }
        return false;
    }

    public static boolean isNegativ( int value )
    {
        if ( value < INT_DEFAULT )
        {
            return true;
        }
        return false;
    }

    public static boolean isEmptyOrDefault( double value )
    {
        if ( value == DOUBLE_MIN || value == DOUBLE_DEFAULT )
        {
            return true;
        }
        return false;
    }

    public static boolean isEmpty( double value )
    {
        if ( value == DOUBLE_MIN )
        {
            return true;
        }
        return false;
    }

    public static boolean isDefault( double value )
    {
        if ( value == DOUBLE_DEFAULT )
        {
            return true;
        }
        return false;
    }

    public static boolean isNegativ( double value )
    {
        if ( value < DOUBLE_DEFAULT )
        {
            return true;
        }
        return false;
    }

    public static boolean isEmptyOrDefault( long value )
    {
        if ( value == LONG_MIN || value == LONG_DEFAULT )
        {
            return true;
        }
        return false;
    }

    public static boolean isEmpty( long value )
    {
        if ( value == LONG_MIN )
        {
            return true;
        }
        return false;
    }

    public static boolean isDefault( long value )
    {
        if ( value == LONG_DEFAULT )
        {
            return true;
        }
        return false;
    }

    public static boolean isNegativ( long value )
    {
        if ( value < LONG_DEFAULT )
        {
            return true;
        }
        return false;
    }

    public static boolean isEmptyOrDefault( byte value )
    {
        if ( value == BYTE_MIN || value == BYTE_DEFAULT )
        {
            return true;
        }
        return false;
    }

    public static boolean isEmpty( byte value )
    {
        if ( value == BYTE_MIN )
        {
            return true;
        }
        return false;
    }

    public static boolean isDefault( byte value )
    {
        if ( value == BYTE_DEFAULT )
        {
            return true;
        }
        return false;
    }

    public static boolean isNegativ( byte value )
    {
        if ( value < BYTE_DEFAULT )
        {
            return true;
        }
        return false;
    }

    public static Timestamp getCurrentTimestamp()
    {
        return new Timestamp( System.currentTimeMillis() );
    }

    public static boolean equalsCurrentTimestamp( Timestamp aTimestamp )
    {
        return getCurrentTimestamp().equals( aTimestamp );
    }

    public static boolean isTimestampOlderThenCurrent( Timestamp aTimestamp )
    {
        return 1 == aTimestamp.compareTo( getCurrentTimestamp() );
    }

    public static String toString( String value )
    {
        if ( value == null )
        {
            return "";
        }
        return String.valueOf( value );
    }

    public static String toString( Long value )
    {
        if ( value == null )
        {
            return "";
        }
        return String.valueOf( value.longValue() );
    }

    public static String toString( long value )
    {
        return String.valueOf( value );
    }

    public static String toString( int value )
    {
        if ( value == INT_MIN )
        {
            return "";
        }
        return String.valueOf( value );
    }

    public static String toString( byte value )
    {
        return String.valueOf( value );
    }

    public static String toString( double value )
    {
        if ( value == INT_MIN || value == DOUBLE_MIN )
        {
            return "";
        }
        return String.valueOf( value );
    }

    public static int toInt( Object value )
    {
        if ( value == null )
        {
            return INT_MIN;
        }
        else
        {
            try
            {
                return Integer.valueOf( String.valueOf( value ) );
            }
            catch ( Exception e )
            {
                return INT_MIN;
            }
        }
    }

}
