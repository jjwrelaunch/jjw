/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : CountryManager.java
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

package de.jjw.service.admin;

import java.util.List;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.admin.Country;
import de.jjw.service.exception.CountryInUseException;
import de.jjw.service.exception.JJWManagerException;

public interface CountryManager
{


    public Country getCountry( Long countryId );

    public List<Country> getAllCountries();

    public void saveCountry( Country country )
        throws OptimisticLockingException;

    public void removeCountry( Country country )
        throws CountryInUseException, JJWManagerException;

    public void removeCountry( Long countryId )
        throws CountryInUseException, JJWManagerException;

}
