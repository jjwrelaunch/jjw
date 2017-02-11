/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : Team.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:44
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
import java.sql.Timestamp;
import java.util.Set;

import de.jjw.model.admin.Country;
import de.jjw.model.admin.Region;
import de.jjw.model.fighting.Fighter;

/**
 * @hibernate.class table="team"
 */
public class Team
    extends BaseObject
    implements Serializable
{

    private Long id;

    private String teamName;



    private int teamtype;

    private byte[] logo;

    private Country country = new Country();

    private long countryId;

    private Region region = new Region();

    private long regionId;


    private Timestamp createDate;

    private Timestamp modificationDate;

    private Long createId;

    private Long modificationId;

    private Set<Fighter> fighterForTeam;

    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer( "" );
        sb.append( "id" + this.id ).append( "teamName" + this.teamName ).append( this.teamName ).append( "teamtype"
                                                                                                             + this.teamtype ).append( "region"
                                                                                                                                           + this.region ).append( "country"
                                                                                                                                                                       + this.country ).toString();
        return sb.toString();
    }

    @Override
    public boolean equals( Object o )
    {

        try
        {
            Team team = (Team) o;
            if ( (

                this.getCountry() == null && null == team.getCountry() && this.getRegion() == null &&
                    null == team.getRegion() &&

                this.getTeamName() == null && null == team.getTeamName() && this.getTeamtype() == team.getTeamtype() &&

                    this.getCreateDate() == null && null == team.getCreateDate() && this.getCreateId() == null &&
                    null == team.getCreateId() && this.getId() == null && null == team.getId() &&
                    this.getModificationDate() == null && null == team.getModificationDate() &&
                    this.getModificationId() == null && null == team.getModificationId() ) || (

                this.getCountry().equals( team.getCountry() ) && this.getRegion().equals( team.getRegion() ) &&

                this.getTeamName() == null && null == team.getTeamName() && this.getTeamtype() == team.getTeamtype() &&

                    this.getCreateDate() == null && null == team.getCreateDate() && this.getCreateId() == null &&
                    null == team.getCreateId() && this.getId().equals( team.getId() ) &&
                    this.getModificationDate() == null && null == team.getModificationDate() &&
                    this.getModificationId() == null && null == team.getModificationId() ) || (

                this.getCountry().equals( team.getCountry() ) && this.getRegion().equals( team.getRegion() ) &&

                this.getTeamName().equals( team.getTeamName() ) &&
                    this.getTeamtype() == team.getTeamtype() &&

                    this.getCreateDate().equals( team.getCreateDate() ) &&
                    this.getCreateId().equals( team.getCreateId() ) && this.getId().equals( team.getId() ) &&
                    this.getModificationDate().equals( team.getModificationDate() ) &&
                    this.getModificationId().equals( team.getModificationId() ) )

                )
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
        return getTeamName().hashCode();
    }

    /**
     * @hibernate.property column="logo"
     */
    public byte[] getLogo()
    {
        return logo;
    }

    public void setLogo( byte[] logo )
    {
        this.logo = logo;
    }

    /**
     * @hibernate.id column="id" generator-class="increment"
     */
    public Long getId()
    {
        return id;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    /**
     * @hibernate.property column="teamName" not-null="true"
     */
    public String getTeamName()
    {
        return teamName;
    }

    public void setTeamName( String teamName )
    {
        this.teamName = teamName;
    }

    /**
     * @hibernate.property column="teamtype" not-null="true"
     */

    public int getTeamtype()
    {
        return teamtype;
    }

    public void setTeamtype( int teamtype )
    {
        this.teamtype = teamtype;
    }


    /**
     * column="region"
     *
     * @return Returns the region.
     * @hibernate.many-to-one not-null="true"  class="de.jjw.model.admin.Region"  update="false" insert="false"
     */
    public Region getRegion()
    {
        return region;
    }

    /**
     * @param region The region to set.
     */
    public void setRegion( Region region )
    {
        this.region = region;
    }


    /**
     * column="country"   cascade="none" updateable="false" name="country"
     *
     * @return Returns the country.
     * @hibernate.many-to-one not-null="true" class="de.jjw.model.admin.Country"  insert="false" update="false"
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


    /**
     * @hibernate.property column="createDate" not-null="true"
     */
    public Timestamp getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate( Timestamp createDate )
    {
        this.createDate = createDate;
    }

    /**
     * @hibernate.property column="createId"
     */
    public Long getCreateId()
    {
        return createId;
    }

    public void setCreateId( Long createId )
    {
        this.createId = createId;
    }

    /**
     * @hibernate.property column="modificationDate" not-null="true"
     */
    public Timestamp getModificationDate()
    {
        return modificationDate;
    }

    public void setModificationDate( Timestamp modificationDate )
    {
        this.modificationDate = modificationDate;
    }

    /**
     * @hibernate.property column="modificationId"
     */
    public Long getModificationId()
    {
        return modificationId;
    }

    public void setModificationId( Long modificationId )
    {
        this.modificationId = modificationId;
    }

    public long getCountryId()
    {
        return countryId;
    }

    /**
     * @param countryId
     * @hibernate.property column="country"
     */
    public void setCountryId( long countryId )
    {
        this.countryId = countryId;
    }

    public long getRegionId()
    {
        return regionId;
    }

    /**
     * @param regionId
     * @hibernate.property column="region"  type"org.springframework.orm.hibernate3.support.BlobByteArrayType"
     */
    public void setRegionId( long regionId )
    {
        this.regionId = regionId;
    }

    /**
     * inverse="true"
     * hibernate.set
     * hibernate.key column="id"
     * <p/>
     * hibernate.one-to-many class="de.jjw.model.fighting.Fighter"
     */
    public Set<Fighter> getFighterForTeam()
    {
        return fighterForTeam;
    }

    public void setFighterForTeam( Set<Fighter> fighterForTeam )
    {
        this.fighterForTeam = fighterForTeam;
    }


}
