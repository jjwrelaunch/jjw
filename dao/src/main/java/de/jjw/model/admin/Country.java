/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : Country.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:42
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
 * @hibernate.class table="country"
 */
public class Country
    extends BaseObject
    implements Serializable
{


    protected Long id;

    protected String countryShort;

    protected String description = "";

    protected Set<Region> regionsForCountry;

    protected Set<Team> teamsForCountry;


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
     * @return Returns the countryShort.
     * @hibernate.property column="countryShort"
     */
    public String getCountryShort()
    {
        return countryShort;
    }

    /**
     * @param countryShort The countryShort to set.
     */
    public void setCountryShort( String countryShort )
    {
        this.countryShort = countryShort;
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
            Country country = (Country) o;

            if ( ( this.getCreateDate() == null && null == country.getCreateDate() && this.getCreateId() == null &&
                null == country.getCreateId() && this.getDescription() == null && null == country.getDescription() &&
                this.getId() == null && null == country.getId() && this.getModificationDate() == null &&
                null == country.getModificationDate() && this.getModificationId() == null &&
                null == country.getModificationId() && this.getCountryShort() == null &&
                null == country.getCountryShort() ) ||

                ( this.getCreateDate() == null && null == country.getCreateDate() && this.getCreateId() == null &&
                    null == country.getCreateId() && this.getDescription().equals( country.getDescription() ) &&
                    ( ( this.getId() == null && null == country.getId() ) || this.getId().equals( country.getId() ) ) &&
                    this.getModificationDate() == null && null == country.getModificationDate() &&
                    this.getModificationId() == null && null == country.getModificationId() &&
                    this.getCountryShort() == null && null == country.getCountryShort() ) ||

                ( this.getCreateDate().equals( country.getCreateDate() ) &&
                    this.getCreateId().equals( country.getCreateId() ) &&
                    this.getDescription().equals( country.getDescription() ) &&
                    this.getId().equals( country.getId() ) &&
                    this.getModificationDate().equals( country.getModificationDate() ) &&
                    this.getModificationId().equals( country.getModificationId() ) &&
                    this.getCountryShort().equals( country.getCountryShort() ) ) )
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
     * @hibernate.set table="Region" inverse="true"
     * @hibernate.collection-key column="id"
     * @hibernate.collection-one-to-many class="de.jjw.model.admin.Region"
     */

    public Set<Region> getRegionsForCountry()
    {
        return regionsForCountry;
    }

    public void setRegionsForCountry( Set<Region> regionsForCountry )
    {
        this.regionsForCountry = regionsForCountry;
    }

    /**
     * inverse="true"
     *
     * @hibernate.set
     * @hibernate.key column="id"
     * @hibernate.one-to-many class="de.jjw.model.Team"
     */

    public Set<Team> getTeamsForCountry()
    {
        return teamsForCountry;
    }

    public void setTeamsForCountry( Set<Team> teamsForCountry )
    {
        this.teamsForCountry = teamsForCountry;
    }


}
