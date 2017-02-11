/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : ConstantsTei.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:43
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

package de.jjw.webapp.taglib;


import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.tagext.TagData;
import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.VariableInfo;

import org.apache.log4j.Logger;

import de.jjw.util.Constants;


/**
 * Implementation of <code>TagExtraInfo</code> for the <b>constants</b>
 * tag, identifying the scripting object(s) to be made visible.
 *
 * @author Matt Raible
 * @version $Revision: 1.5 $ $Date: 2006/01/30 05:00:13 $
 */
public class ConstantsTei
    extends TagExtraInfo
{
    protected final Logger log = Logger.getRootLogger();

    /**
     * Return information about the scripting variables to be created.
     */
    public VariableInfo[] getVariableInfo( TagData data )
    {
        // loop through and expose all attributes
        List vars = new ArrayList();

        try
        {
            String clazz = data.getAttributeString( "className" );

            if ( clazz == null )
            {
                clazz = Constants.class.getName();
            }

            Class c = Class.forName( clazz );

            // if no var specified, get all
            if ( data.getAttributeString( "var" ) == null )
            {
                Field[] fields = c.getDeclaredFields();

                AccessibleObject.setAccessible( fields, true );

                for ( int i = 0; i < fields.length; i++ )
                {
                    String type = fields[i].getType().getName();
                    vars.add( new VariableInfo( fields[i].getName(), ( ( fields[i].getType().isArray() ) ?
                        type.substring( 2, type.length() - 1 ) + "[]" : type ), true, VariableInfo.AT_END ) );
                }
            }
            else
            {
                String var = data.getAttributeString( "var" );
                String type = c.getField( var ).getType().getName();
                vars.add( new VariableInfo( c.getField( var ).getName(), ( ( c.getField( var ).getType().isArray() ) ?
                    type.substring( 2, type.length() - 1 ) + "[]" : type ), true, VariableInfo.AT_END ) );
            }
        }
        catch ( Exception cnf )
        {
            log.error( cnf.getMessage() );
            cnf.printStackTrace();
        }

        return (VariableInfo[]) vars.toArray( new VariableInfo[]{} );
    }
}
