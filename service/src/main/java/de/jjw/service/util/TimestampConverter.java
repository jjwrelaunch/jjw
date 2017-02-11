/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : TimestampConverter.java
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

package de.jjw.service.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.lang.StringUtils;

/**
 * This class is converts a java.util.Date to a String and a String to a
 * java.util.Date for use as a Timestamp. It is used by BeanUtils when copying
 * properties.
 * <p/>
 * <p>
 * <a href="TimestampConverter.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author
 */
public class TimestampConverter
    extends DateConverter
{
    public static final String TS_FORMAT = DateUtil.getDatePattern() + " HH:mm:ss.S";

    protected Object convertToDate( Class type, Object value )
    {
        DateFormat df = new SimpleDateFormat( TS_FORMAT );
        if ( value instanceof String )
        {
            try
            {
                if ( StringUtils.isEmpty( value.toString() ) )
                {
                    return null;
                }

                return df.parse( (String) value );
            }
            catch ( Exception pe )
            {
                throw new ConversionException( "Error converting String to Timestamp" );
            }
        }

        throw new ConversionException( "Could not convert " + value.getClass().getName() + " to " + type.getName() );
    }

    protected Object convertToString( Class type, Object value )
    {
        DateFormat df = new SimpleDateFormat( TS_FORMAT );
        if ( value instanceof Date )
        {
            try
            {
                return df.format( value );
            }
            catch ( Exception e )
            {
                throw new ConversionException( "Error converting Timestamp to String" );
            }
        }

        return value.toString();
    }
}
