/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : Region.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:45
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

package de.jjw.model.admin;

import de.jjw.model.BaseObject;
import de.jjw.model.Team;

import java.io.Serializable;
import java.util.Set;

/**
 * @author joerg.boehme
 * @hibernate.class table="region"
 */
public class Region
    extends BaseObject
    implements Serializable
{

    protected Long id;

    protected String regionShort;

    protected String description = "";

    protected Country country;

    protected Set<Team> teamsForRegion;

    /**
     * @return Returns the description.
     * @hibernate.property column="description" length="30"
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description The description to set.
     */
    public void setDescription( String description )
    {
        this.description = description;
    }

    /**
     * @return Returns the regionShort.
     * @hibernate.property column="regionShort"
     */
    public String getRegionShort()
    {
        return regionShort;
    }

    /**
     * @param regionShort The regionShort to set.
     */
    public void setRegionShort( String regionShort )
    {
        this.regionShort = regionShort;
    }

    /**
     * @return Returns the country.
     * @hibernate.many-to-one class="de.jjw.model.admin.Country" column="country"
     */
    public Country getCountry()
    {
        return country;
    }

    /**
     * @param country The country to set.
     */
    public void setCountry( Country country )
    {
        this.country = country;
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
        try
        {
            Region region = (Region) o;

            if ( ( this.getCreateDate() == null && null == region.getCreateDate() && this.getCreateId() == null &&
                null == region.getCreateId() && this.getDescription() == null && null == region.getDescription() &&
                this.getId() == null && null == region.getId() && this.getModificationDate() == null &&
                null == region.getModificationDate() && this.getModificationId() == null &&
                null == region.getModificationId() && this.getRegionShort() == null &&
                null == region.getRegionShort() && this.getCountry() == null && null == region.getCountry() )

                || ( this.getCreateDate() == null && null == region.getCreateDate() && this.getCreateId() == null &&
                null == region.getCreateId() && this.getDescription().equals( region.getDescription() ) &&
                ( ( this.getId() == null && null == region.getId() ) || this.getId().equals( region.getId() ) ) &&
                this.getModificationDate() == null && null == region.getModificationDate() &&
                this.getModificationId() == null && null == region.getModificationId() &&
                this.getRegionShort() == null && null == region.getRegionShort() && this.getCountry() == null &&
                null == region.getCountry() ) || ( this.getCreateDate().equals( region.getCreateDate() ) &&
                this.getCreateId().equals( region.getCreateId() ) &&
                this.getDescription().equals( region.getDescription() ) && this.getId().equals( region.getId() ) &&
                this.getModificationDate().equals( region.getModificationDate() ) &&
                this.getModificationId().equals( region.getModificationId() ) &&
                this.getRegionShort().equals( region.getRegionShort() ) &&
                this.getCountry().equals( region.getCountry() ) ) )
            {
                return true;
            }

        }
        catch ( Exception e )
        {
            ;
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * @return Returns the id.
     * @hibernate.id column="id" generator-class="increment"
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @param id The id to set.
     */
    public void setId( Long id )
    {
        this.id = id;
    }

    /**
     * @hibernate.set table="Team" inverse="true"
     * @hibernate.collection-key column="id"
     * @hibernate.collection-one-to-many class="de.jjw.model.Team"
     */
    public Set<Team> getTeamsForRegion()
    {
        return teamsForRegion;
    }

    public void setTeamsForRegion( Set<Team> teamsForRegion )
    {
        this.teamsForRegion = teamsForRegion;
    }


}
