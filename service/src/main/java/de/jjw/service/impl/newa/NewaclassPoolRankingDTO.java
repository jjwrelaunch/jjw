/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaclassPoolRankingDTO.java
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

package de.jjw.service.impl.newa;

import de.jjw.util.TypeUtil;

public class NewaclassPoolRankingDTO
    implements Comparable
{

    private long id;

    private int wins = TypeUtil.INT_EMPTY;

    private int ubs = TypeUtil.INT_EMPTY;
    
    private int ubAdvantages = TypeUtil.INT_EMPTY;

    private int place = TypeUtil.INT_EMPTY;

    public long getId()
    {
        return id;
    }

    public void setId( long id )
    {
        this.id = id;
    }

    public int getWins()
    {
        return wins;
    }

    public void setWins( int wins )
    {
        this.wins = wins;
    }

    public int getUbs()
    {
        return ubs;
    }

    public void setUbs( int ubs )
    {
        this.ubs = ubs;
    }

    public int getPlace()
    {
        return place;
    }

    public void setPlace( int place )
    {
        this.place = place;
    }
    
    

    public int getUbAdvantages()
    {
        return ubAdvantages;
    }

    public void setUbAdvantages( int ubAdvantages )
    {
        this.ubAdvantages = ubAdvantages;
    }

    public int compareTo( Object o )
    {
        NewaclassPoolRankingDTO item = (NewaclassPoolRankingDTO) o;

        if ( TypeUtil.INT_DEFAULT == this.getWins() && TypeUtil.INT_DEFAULT < item.getWins() )
        {
            return 1;
        }
        if ( TypeUtil.INT_DEFAULT == item.getWins() && TypeUtil.INT_DEFAULT < this.getWins() )
        {
            return -1;
        }

        if ( this.getWins() < item.getWins() )
        {
            return 1;
        }
        if ( this.getWins() > item.getWins() )
        {
            return -1;
        }
        if ( this.getWins() == item.getWins() )
        {

            if ( TypeUtil.INT_DEFAULT == this.getUbs() && TypeUtil.INT_DEFAULT < item.getUbs() )
            {
                return 1;
            }
            if ( TypeUtil.INT_DEFAULT == item.getUbs() && TypeUtil.INT_DEFAULT < this.getUbs() )
            {
                return -1;
            }

            if ( this.getUbs() < item.getUbs() )
            {
                return 1;
            }
            if ( this.getUbs() > item.getUbs() )
            {
                return -1;
            }
            if ( this.getUbs() == item.getUbs() )
            {
                return 0;
            }
        }

        return 0;
    }


}
