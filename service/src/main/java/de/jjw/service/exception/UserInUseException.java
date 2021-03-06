/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2012.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : UserInUseException.java
 * Created : 04 Nov 2012
 * Last Modified: Sat, 04 Nov 2012 20:05:14
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

package de.jjw.service.exception;

public class UserInUseException
    extends Exception
{

    private static String MSG = "user is in use!!!";

    public UserInUseException( String msg )
    {
        super( msg );
    }

    public UserInUseException()
    {
        super( MSG );
    }
}
