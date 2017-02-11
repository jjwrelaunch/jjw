/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : Utility.java
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

package de.jjw.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;


public class Utility
{

    protected final Logger log = Logger.getRootLogger();

    private static Utility instance = new Utility();


    public static Utility getInstance()
    {
        return instance;
    }

    private Utility()
    {

    }

    public Object deepCopy( Object obj )
    {
        try
        {
            ByteArrayOutputStream bufOutStream = new ByteArrayOutputStream();
            ObjectOutputStream outStream;

            outStream = new ObjectOutputStream( bufOutStream );

            // Objekt im byte-Array speichern
            outStream.writeObject( obj );
            outStream.close();

            // Pufferinhalt abrufen
            byte[] buffer = bufOutStream.toByteArray();
            // ObjectInputStream erzeugen
            ByteArrayInputStream bufInStream = new ByteArrayInputStream( buffer );
            ObjectInputStream inStream = new ObjectInputStream( bufInStream );
            // Objekt wieder auslesen

            return inStream.readObject();
        }
        catch ( IOException e )
        {
            ;
        }
        catch ( ClassNotFoundException e )
        {
            ;
        }
        return null;
    }

    public String formatTimeFromSeconds( long seconds )
    {
        long minutes, newSeconds, hours;
        hours = 0;
        newSeconds= seconds;
        StringBuffer sb = new StringBuffer();
        
        //hour
        if ( seconds > 3600 ){
            hours =  (int) ( seconds / 3600 );
            newSeconds= seconds -(hours *3600);
        }
        sb.append( hours );
        sb.append( ":" );
   
        minutes =  (int) ( newSeconds / 60 );
        if (  minutes  < 10 )
            sb.append( "0" );
        sb.append( minutes );

        return sb.toString();
    }
}
