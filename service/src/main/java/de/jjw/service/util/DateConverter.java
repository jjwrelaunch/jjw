/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DateConverter.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:49
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

package de.jjw.service.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;


/**
 * This class is converts a java.util.Date to a String
 * and a String to a java.util.Date.
 * <p/>
 * <p>
 * <a href="DateConverter.java.html"><i>View Source</i></a>
 * </p>
 */
public class DateConverter
    implements Converter
{
    public static final String TS_FORMAT = DateUtil.getDatePattern() + " HH:mm:ss.S";

    public Object convert( Class type, Object value )
    {
        if ( value == null )
        {
            return null;
        }
        else if ( type == Timestamp.class )
        {
            return convertToDate( type, value, TS_FORMAT );
        }
        else if ( type == Date.class )
        {
            return convertToDate( type, value, DateUtil.getDatePattern() );
        }
        else if ( type == String.class )
        {
            return convertToString( type, value );
        }

        throw new ConversionException( "Could not convert " + value.getClass().getName() + " to " + type.getName() );
    }

    protected Object convertToDate( Class type, Object value, String pattern )
    {
        DateFormat df = new SimpleDateFormat( pattern );
        if ( value instanceof String )
        {
            try
            {
                if ( StringUtils.isEmpty( value.toString() ) )
                {
                    return null;
                }

                Date date = df.parse( (String) value );
                if ( type.equals( Timestamp.class ) )
                {
                    return new Timestamp( date.getTime() );
                }
                return date;
            }
            catch ( Exception pe )
            {
                pe.printStackTrace();
                throw new ConversionException( "Error converting String to Date" );
            }
        }

        throw new ConversionException( "Could not convert " + value.getClass().getName() + " to " + type.getName() );
    }

    protected Object convertToString( Class type, Object value )
    {

        if ( value instanceof Date )
        {
            DateFormat df = new SimpleDateFormat( DateUtil.getDatePattern() );
            if ( value instanceof Timestamp )
            {
                df = new SimpleDateFormat( TS_FORMAT );
            }

            try
            {
                return df.format( value );
            }
            catch ( Exception e )
            {
                e.printStackTrace();
                throw new ConversionException( "Error converting Date to String" );
            }
        }
        else
        {
            return value.toString();
        }
    }
}
