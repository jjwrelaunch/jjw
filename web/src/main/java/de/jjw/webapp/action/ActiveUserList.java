/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : ActiveUserList.java
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

package de.jjw.webapp.action;

import de.jjw.model.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ActiveUserList
    extends BasePage
    implements Serializable
{

    private String sort = "username";

    private boolean ascending = false;

    public String getSort()
    {
        return sort;
    }

    public void setSort( String sort )
    {
        this.sort = sort;
    }

    public boolean isAscending()
    {
        return ascending;
    }

    public void setAscending( boolean ascending )
    {
        this.ascending = ascending;
    }

    public List getUsers()
    {
        List l = new ArrayList( (Collection) getServletContext().getAttribute( "userNames" ) );

        Comparator comparator = new Comparator()
        {
            public int compare( Object o1, Object o2 )
            {
                User u1 = (User) o1;
                User u2 = (User) o2;
                if ( sort == null )
                {
                    return 0;
                }
                if ( sort.equals( "username" ) )
                {
                    return !ascending
                        ? u1.getUsername().toLowerCase().compareTo( u2.getUsername().toLowerCase() )
                        : u2.getUsername().toLowerCase().compareTo( u1.getUsername().toLowerCase() );
                }
                if ( sort.equals( "fullName" ) )
                {
                    return !ascending
                        ? u1.getFullName().toLowerCase().compareTo( u2.getFullName().toLowerCase() )
                        : u2.getFullName().toLowerCase().compareTo( u1.getFullName().toLowerCase() );
                }
                return 0;
            }
        };
        Collections.sort( l, comparator );
        return l;
    }

}
