/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : CountryAction.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:43
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

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import javax.faces.component.UIData;

import org.springframework.dao.DataIntegrityViolationException;

import de.jjw.model.admin.Country;
import de.jjw.service.admin.CountryManager;
import de.jjw.service.exception.CountryInUseException;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.action.validation.admin.CountryValidator;
import de.jjw.webapp.constants.admin.ICountryConstants;

public class CountryAction
    extends BasePage
    implements Serializable, IGlobalWebConstants, ICountryConstants
{

    CountryManager countryManager;

    Country country = new Country();

    List<Country> countries;

    private static String ALL_COUNTRIES = "allCountries";


    /**
     * @param countryManager The countryManager to set.
     */
    public void setCountryManager( CountryManager countryManager )
    {
        this.countryManager = countryManager;
    }

    public List<Country> getCountries()
    {
        if ( countryManager == null )
        {
            log.warn( "keine countryManager Injection" );
        }
        if ( getRequest().getAttribute( ALL_COUNTRIES ) == null )
        {
            countries = countryManager.getAllCountries();
            getRequest().setAttribute( ALL_COUNTRIES, ALL_COUNTRIES );
        }
        return countries;
    }

    public String addCountry()
    {
        String ret = SUCCESS;

        Vector<ErrorElement> errors = new Vector<ErrorElement>();

        if ( !CountryValidator.isNewCountryValid( getCountries(), errors, country ) )
        {
            setErrorMessageVector( errors );
            return null;
        }
        country.setCountryShort( country.getCountryShort().toUpperCase().trim() );
        country.setDescription( country.getDescription().trim() );
        setTechnicalDBFieldsForCreate( country );
        try
        {
            countryManager.saveCountry( country );
            getRequest().removeAttribute( ALL_COUNTRIES );

        }
        catch ( DataIntegrityViolationException e )
        {
            errors.add( new ErrorElement( FIELD_COUNTRY_SHORT, ADMIN_COUNTRY_EXISTS ) );
            setErrorMessageVector( errors );
            ret = null;
        }
        catch ( Exception e )
        {
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
            log.error( "CountryAction", e );
            return null;
        }

        country = new Country();
        return ret;
    }


    public String deleteCountry()
    {
        String ret = SUCCESS;

        try
        {
            countryManager.removeCountry( (Country) dataTable.getRowData() );
        }
        catch ( CountryInUseException e )
        {
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( FIELD_COUNTRY_SHORT, ADMIN_COUNTRY_IN_USE ) );
            setErrorMessageVector( errors );
            return null;
        }
        catch ( Exception e )
        {
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
            log.error( "CountryAction", e );
            return null;
        }
        return ret;
    }

    public String showCountries()
    {
        String ret = SUCCESS;
        return ret;
    }


    /**
     * @return Returns the country.
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

    public UIData getDataTable()
    {
        return dataTable;
    }

    public void setDataTable( UIData dataTable )
    {
        this.dataTable = dataTable;
    }

}
