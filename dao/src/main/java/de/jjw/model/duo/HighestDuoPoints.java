/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : HighestDuoPoints.java
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

package de.jjw.model.duo;

import de.jjw.model.BaseObject;


public class HighestDuoPoints
    extends BaseObject
    implements Comparable
{
    private String name = "";

    private String firstname = "";

    private String name2 = "";

    private String firstname2 = "";

    private String team = "";

    private String region = "";

    private String country = "";

    private double points = 0;

    private double pointsMax = 0;

    private int sex = 1;

    private String classname = "";

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

    /**
     * @return the name2
     */
    public String getName2()
    {
        return name2;
    }

    /**
     * @param name2 the name2 to set
     */
    public void setName2( String name2 )
    {
        this.name2 = name2;
    }

    /**
     * @return the firstname2
     */
    public String getFirstname2()
    {
        return firstname2;
    }

    /**
     * @param firstname2 the firstname2 to set
     */
    public void setFirstname2( String firstname2 )
    {
        this.firstname2 = firstname2;
    }

    /**
     * @return the points
     */
    public double getPoints()
    {
        return points;
    }

    /**
     * @param points the points to set
     */
    public void setPoints( double points )
    {
        this.points = points;
    }

    /**
     * @return the pointsMax
     */
    public double getPointsMax()
    {
        return pointsMax;
    }

    /**
     * @param pointsMax the pointsMax to set
     */
    public void setPointsMax( double pointsMax )
    {
        this.pointsMax = pointsMax;
    }

    /**
     * @return the classname
     */
    public String getClassname()
    {
        return classname;
    }

    /**
     * @param classname the classname to set
     */
    public void setClassname( String classname )
    {
        this.classname = classname;
    }

    @Override
    public String toString()
    {
        // TODO Auto-generated method stub
        return null;
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
    public int compareTo( Object o )
    {
        HighestDuoPoints item = (HighestDuoPoints) o;

        if ( this.getPoints() > item.getPoints() )
            return -1;
        if ( this.getPoints() < item.getPoints() )
            return 1;

        if ( this.getPoints() == item.getPoints() )
        {
            if ( this.getPointsMax() > item.getPointsMax() )
                return -1;
            if ( this.getPointsMax() < item.getPointsMax() )
                return 1;
        }
        return 0;
    }

}
