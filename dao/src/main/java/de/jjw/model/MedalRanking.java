/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : MedalRanking.java
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

package de.jjw.model;

import java.io.Serializable;

public class MedalRanking
    extends BaseObject
    implements Serializable, Comparable<MedalRanking>
{

    public MedalRanking( String name, int firstPlace, int secondPlace, int thirdPlace )
    {
        this.name = name;
        this.firstPlace = firstPlace;
        this.secondPlace = secondPlace;
        this.thirdPlace = thirdPlace;
    }

    private String name = "";

    private int firstPlace = 0;

    private int secondPlace = 0;

    private int thirdPlace = 0;

    private String rank = "";

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public int getFirstPlace()
    {
        return firstPlace;
    }

    public void setFirstPlace( int firstPlace )
    {
        this.firstPlace = firstPlace;
    }

    public int getSecondPlace()
    {
        return secondPlace;
    }

    public void setSecondPlace( int secondPlace )
    {
        this.secondPlace = secondPlace;
    }

    public int getThirdPlace()
    {
        return thirdPlace;
    }

    public void setThirdPlace( int thirdPlace )
    {
        this.thirdPlace = thirdPlace;
    }

    public String getRank()
    {
        return rank;
    }

    public void setRank( String rank )
    {
        this.rank = rank;
    }

    @Override
    public boolean equals( Object o )
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int hashCode()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String toString()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int compareTo( MedalRanking o )
    {
        if ( firstPlace > o.getFirstPlace() )
        {
            return -1;
        }
        if ( firstPlace < o.getFirstPlace() )
        {
            return 1;
        }

        if ( firstPlace == o.getFirstPlace() )
        {
            if ( secondPlace > o.getSecondPlace() )
            {
                return -1;
            }
            if ( secondPlace < o.getSecondPlace() )
            {
                return 1;
            }

            if ( secondPlace == o.getSecondPlace() )
            {
                if ( thirdPlace > o.getThirdPlace() )
                {
                    return -1;
                }
                if ( thirdPlace < o.getThirdPlace() )
                {
                    return 1;
                }
            }

        }

        return 0;
    }


}
