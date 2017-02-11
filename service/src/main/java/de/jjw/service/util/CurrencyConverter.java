/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : CurrencyConverter.java
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

import java.text.DecimalFormat;
import java.text.ParseException;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * This class is converts a Double to a double-digit String (and vise-versa) by BeanUtils when copying properties.
 * Registered for use in BaseAction.
 * <p/>
 * <p>
 * <a href="CurrencyConverter.java.html"><i>View Source</i></a>
 * </p>
 */
public class CurrencyConverter
    implements Converter
{
    protected final Logger log = Logger.getRootLogger();

    protected final DecimalFormat formatter = new DecimalFormat( "###,###.00" );

    /**
     * Convert a String to a Double and a Double to a String
     * 
     * @param type the class type to output
     * @param value the object to convert
     * @return object the converted object (Double or String)
     */
    public final Object convert( final Class type, final Object value )
    {
        // for a null value, return null
        if ( value == null )
        {
            return null;
        }
        else
        {
            if ( value instanceof String )
            {
                if ( log.isDebugEnabled() )
                {
                    log.debug( "value (" + value + ") instance of String" );
                }

                try
                {
                    if ( StringUtils.isBlank( String.valueOf( value ) ) )
                    {
                        return null;
                    }

                    if ( log.isDebugEnabled() )
                    {
                        log.debug( "converting '" + value + "' to a decimal" );
                    }

                    // formatter.setDecimalSeparatorAlwaysShown(true);
                    Number num = formatter.parse( String.valueOf( value ) );

                    return new Double( num.doubleValue() );
                }
                catch ( ParseException pe )
                {
                    pe.printStackTrace();
                }
            }
            else if ( value instanceof Double )
            {
                if ( log.isDebugEnabled() )
                {
                    log.debug( "value (" + value + ") instance of Double" );
                    log.debug( "returning double: " + formatter.format( value ) );
                }

                return formatter.format( value );
            }
        }

        throw new ConversionException( "Could not convert " + value + " to " + type.getName() + "!" );
    }
}
