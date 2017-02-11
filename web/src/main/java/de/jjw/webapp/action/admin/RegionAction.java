/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : RegionAction.java
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

package de.jjw.webapp.action.admin;


import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.faces.model.SelectItem;

import org.springframework.dao.DataIntegrityViolationException;

import de.jjw.model.admin.Country;
import de.jjw.model.admin.Region;
import de.jjw.service.admin.CountryManager;
import de.jjw.service.admin.RegionManager;
import de.jjw.service.exception.RegionInUseException;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.action.validation.admin.RegionValidator;
import de.jjw.webapp.constants.admin.IRegionConstants;

public class RegionAction
    extends BasePage
    implements IGlobalWebConstants, IRegionConstants
{

    RegionManager regionManager;

    Region region = new Region();

    List<Region> regions;

    private static String ALL_REGIONS = "allRegions";

    private static String ALL_COUNTRY = "allCountry";

    private CountryManager countryManager = null;

    private List<Country> countries = null;


    /**
     * @param regionManager The regionManager to set.
     */
    public void setRegionManager( RegionManager regionManager )
    {
        this.regionManager = regionManager;
    }

    public List<Region> getRegions()
    {
        if ( regionManager == null )
        {
            log.warn( "keine regionManager Injection" );
        }
        if ( getRequest().getAttribute( ALL_REGIONS ) == null )
        {
            regions = regionManager.getAllRegions();
            getRequest().setAttribute( ALL_REGIONS, ALL_REGIONS );
        }
        return regions;
    }


    public String addRegion()
    {
        String ret = SUCCESS;

        Vector<ErrorElement> errors = new Vector<ErrorElement>();

        if ( !RegionValidator.isNewRegionValid( getRegions(), errors, region ) )
        {
            setErrorMessageVector( errors );
            return null;
        }
        region.setRegionShort( region.getRegionShort().toUpperCase().trim() );
        region.setDescription( region.getDescription().trim() );
        setTechnicalDBFieldsForCreate( region );
        try
        {
            regionManager.saveRegion( region );

        }
        catch ( DataIntegrityViolationException e )
        {
            errors.add( new ErrorElement( FIELD_REGION_SHORT, ADMIN_REGION_EXISTS ) );
            setErrorMessageVector( errors );
            ret = null;
        }
        catch ( Exception e )
        {

        }
        region = new Region();

        return ret;
    }


    public String deleteRegion()
    {
        String ret = SUCCESS;

        try
        {
            regionManager.removeRegion( (Region) dataTable.getRowData() );
        }
        catch ( RegionInUseException e )
        {
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( FIELD_REGION_SHORT, ADMIN_REGION_IN_USE ) );
            setErrorMessageVector( errors );
            return null;
        }

        return ret;
    }

    /**
     * @return Returns the region.
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

    public List<Country> getCountries()
    {
        if ( countryManager == null )
        {
            log.warn( "keine fcountryManager Injection" );
        }
        if ( getRequest().getAttribute( ALL_COUNTRY ) == null )
        {
            countries = countryManager.getAllCountries();
            getRequest().setAttribute( ALL_COUNTRY, ALL_COUNTRY );
        }
        return countries;
    }

    /**
     * @param countryManager The countryManager to set.
     */
    public void setCountryManager( CountryManager countryManager )
    {
        this.countryManager = countryManager;
    }

    /*
    * @see de.jjw.webapp.action.BasePageMother#getCountryListALL()
    */

    @Override
    public List getCountryListALL()
    {
        List ret = new ArrayList();

        List<Country> countries = getCountries();
        for ( Country country : countries )
        {
            ret.add( new SelectItem( country, country.getDescription(), country.getId().toString() ) );
        }
        return ret;
    }


}
