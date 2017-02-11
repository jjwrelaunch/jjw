/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : RegionValidator.java
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

package de.jjw.webapp.action.validation.admin;

import de.jjw.model.admin.Region;
import de.jjw.util.ICodestableConstants;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.constants.admin.IRegionConstants;

import java.util.List;
import java.util.Vector;

public class RegionValidator
    implements ICodestableConstants, IRegionConstants
{

    public static boolean isNewRegionValid( List<Region> regionList, Vector<ErrorElement> errors, Region newRegion )
    {
        boolean ret = true;

        if (newRegion.getCountry() == null){
        	 errors.add( new ErrorElement( FIELD_COUNTRY, ADMIN_REGION_NO_COUNTRY ) );
             return false;        	
        }
        // testen, damit nur Buchstaben A-Z erlaubt sind (3 Zeichen)

        if ( isRegionExists( regionList, errors, newRegion ) )
        {
            return false;
        }

        ret = isRegionDescriptionValid( errors, newRegion );
        if ( TypeUtil.isEmptyOrDefault( newRegion.getRegionShort().trim() ) )
        {
            errors.add( new ErrorElement( FIELD_REGION_SHORT, GEN_NO_VALUE ) );
            ret = false;
        }

        return ret;
    }


    public static boolean isEditRegionValid( List<Region> regionList, Vector<ErrorElement> errors, Region newRegion )
    {
        boolean ret = true;
        // test, damit nur Buchstaben A-Z erlaubt sind (3 Zeichen)

        if ( isRegionExists( regionList, errors, newRegion ) )
        {
            return false;
        }

        ret = isRegionDescriptionValid( errors, newRegion );
        return ret;
    }


    /**
     * chek, if there is a description for the Region und the description
     * lenght is valid
     *
     * @param errors
     * @param newRegion
     * @return
     */
    private static boolean isRegionDescriptionValid( Vector<ErrorElement> errors, Region newRegion )
    {
        boolean ret=true;
        if ( TypeUtil.isEmptyOrDefault( newRegion.getDescription().trim() ) )
        {
        	 errors.add( new ErrorElement( FIELD_DESCRIPTION, ADMIN_REGION_NO_DESCRIPTION ) );
             ret= false;  

        }
        else
        {
            if ( newRegion.getDescription().length() > MAX_LENGTH_DESCRIPTION )
            {
                errors.add( new ErrorElement( FIELD_DESCRIPTION, ADMIN_REGION_DESCRIPTION_TO_LONG ) );
                ret = false;
            }
            
        }
        return ret;
    }

    /**
     * Check, if there is a region with this id
     *
     * @param regionList
     * @param errors
     * @param newRegion
     * @return
     */
    private static boolean isRegionExists( List<Region> regionList, Vector<ErrorElement> errors, Region newRegion )
    {
        for ( Region region : regionList )
        {
            if ( region.getRegionShort().equals( newRegion.getRegionShort() ) )
            {
                errors.add( new ErrorElement( FIELD_REGION_SHORT, ADMIN_REGION_EXISTS ) );
                return true;
            }
        }
        return false;
    }

}
