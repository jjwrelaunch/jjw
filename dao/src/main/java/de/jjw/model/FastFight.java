/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FastFight.java
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


public class FastFight
    extends BaseObject
{
    private String name = "";

    private String firstname = "";

    private String team = "";

    private String region = "";

    private String country = "";

    private int fightTime = 0;

    private int sex = 1;

    private String age = "";

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName( String name )
    {
        this.name = name;
    }

    /**
     * @return the firstName
     */
    public String getFirstname()
    {
        return firstname;
    }

    /**
     * @param firstname the firstName to set
     */
    public void setFirstname( String firstname )
    {
        this.firstname = firstname;
    }

    /**
     * @return the team
     */
    public String getTeam()
    {
        return team;
    }

    /**
     * @param team the team to set
     */
    public void setTeam( String team )
    {
        this.team = team;
    }

    /**
     * @return the region
     */
    public String getRegion()
    {
        return region;
    }

    /**
     * @param region the region to set
     */
    public void setRegion( String region )
    {
        this.region = region;
    }

    /**
     * @return the country
     */
    public String getCountry()
    {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry( String country )
    {
        this.country = country;
    }

    /**
     * @return the fightTime
     */
    public int getFightTime()
    {
        return fightTime;
    }

    /**
     * @param fightTime the fightTime to set
     */
    public void setFightTime( int fightTime )
    {
        this.fightTime = fightTime;
    }

    /**
     * @return the sex
     */
    public int getSex()
    {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex( int sex )
    {
        this.sex = sex;
    }

    /**
     * @return the age
     */
    public String getAge()
    {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge( String age )
    {
        this.age = age;
    }

    @Override
    public String toString()
    {
        // TODO Auto-generated method stub
        return null;
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


}
