/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : StringUtil.java
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

import java.io.IOException;
import java.security.MessageDigest;

import org.apache.log4j.Logger;


/**
 * String Utility Class This is used to encode passwords programmatically
 * <p/>
 * <p>
 * <a h
 * ref="StringUtil.java.html"><i>View Source</i></a>
 * </p>
 *
 * @author
 */
public class StringUtil
{
    //~ Static fields/initializers =============================================

    protected final static Logger log = Logger.getRootLogger();

    //~ Methods ================================================================

    /**
     * Encode a string using algorithm specified in web.xml and return the
     * resulting encrypted password. If exception, the plain credentials
     * string is returned
     *
     * @param password  Password or other credentials to use in authenticating
     *                  this username
     * @param algorithm Algorithm used to do the digest
     * @return encypted password based on the algorithm.
     */
    public static String encodePassword( String password, String algorithm )
    {
        byte[] unencodedPassword = password.getBytes();

        MessageDigest md = null;

        try
        {
            // first create an instance, given the provider
            md = MessageDigest.getInstance( algorithm );
        }
        catch ( Exception e )
        {
            log.error( "Exception: " + e );

            return password;
        }

        md.reset();

        // call the update method one or more times
        // (useful when you don't know the size of your data, eg. stream)
        md.update( unencodedPassword );

        // now calculate the hash
        byte[] encodedPassword = md.digest();

        StringBuffer buf = new StringBuffer();

        for ( int i = 0; i < encodedPassword.length; i++ )
        {
            if ( ( encodedPassword[i] & 0xff ) < 0x10 )
            {
                buf.append( "0" );
            }

            buf.append( Long.toString( encodedPassword[i] & 0xff, 16 ) );
        }

        return buf.toString();
    }
}