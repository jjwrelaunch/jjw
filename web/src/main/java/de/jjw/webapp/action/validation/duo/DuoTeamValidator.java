/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoTeamValidator.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:49
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

package de.jjw.webapp.action.validation.duo;

import de.jjw.model.duo.DuoTeam;
import de.jjw.service.modelWeb.DuoTeamWeb;
import de.jjw.util.ICodestableConstants;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.constants.admin.IDuoTeamConstants;

import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

public class DuoTeamValidator
    implements IDuoTeamConstants, IValueConstants, ICodestableConstants
{
    protected final static Logger log = Logger.getRootLogger();

    public static boolean isRequiredFieldsValid( List<DuoTeamWeb> duoteams, Vector<ErrorElement> errors )
    {
        boolean isValid = true;

        for ( DuoTeamWeb item : duoteams )
        {
            if ( !isRequiredFieldsValid( item, errors ) )
            {
                log.error( "\n\n\n" + item.getName() + "  can not import!!!  \n\n\n" );
                return false;
            }
        }

        if ( errors.size() > 0 )
        {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isRequiredFieldsValid( DuoTeam duoTeam, Vector<ErrorElement> errors )
    {
        boolean isValid = true;

        if ( TypeUtil.isEmptyOrDefault( duoTeam.getName() ) )
        {
            errors.add( new ErrorElement( FIELD_NAME, GEN_NO_VALUE ) );
        }
        if ( TypeUtil.isEmptyOrDefault( duoTeam.getFirstname() ) )
        {
            errors.add( new ErrorElement( FIELD_FIRSTNAME, GEN_NO_VALUE ) );
        }
        
        if ( TypeUtil.isEmptyOrDefault( duoTeam.getName2() ) )
        {
            errors.add( new ErrorElement( FIELD_NAME, GEN_NO_VALUE ) );
        }
        if ( TypeUtil.isEmptyOrDefault( duoTeam.getFirstname2() ) )
        {
            errors.add( new ErrorElement( FIELD_FIRSTNAME, GEN_NO_VALUE ) );
        }

        if ( duoTeam.getTeam() == null || TypeUtil.isEmptyOrDefault(duoTeam.getTeam().getId() ) )
        {
            errors.add( new ErrorElement( FIELD_TEAM, ADMIN_DUOTEAM_NO_TEAM ) );
        }

        if ( duoTeam.getAge() == null || TypeUtil.isEmptyOrDefault( duoTeam.getAge().getId() ) )
        {
            errors.add( new ErrorElement( FIELD_AGE, ADMIN_DUOTEAM_NO_AGE ) );
        }

        
        if ( errors.size() > 0 )
        {
            isValid = false;
        }
        return isValid;

    }

    public static boolean isBusinessLogicValid( DuoTeam duoTeam, List<ErrorElement> errors )
    {
        boolean isValid = true;

//        if (ICodestableConstants.FIGHTER_NOT_READY.equals(duoTeam.getReady()) && duoTeam.getWeight() <= TypeUtil.DOUBLE_DEFAULT) 
//        	errors.add(new ErrorElement(FIELD_WEIGHT, ADMIN_FIGHTER_NEGATIV_WEIGHT));

        if ( errors.size() > 0 )
        {
            isValid = false;
        }
        return isValid;

    }

    public static boolean isDoubleNameDuoTeam( List<DuoTeamWeb> list )
    {
        if ( list == null || list.isEmpty() )
            return false;

        for ( int i = 0; i < list.size(); i++ )
        {
            for ( int j = i + 1; j < list.size(); j++ )
            {
                if ( list.get( i ).getName().equals( list.get( j ).getName() )
                    && list.get( i ).getFirstname().equals( list.get( j ).getFirstname() )
                    || list.get( i ).getName2().equals( list.get( j ).getName() )
                    && list.get( i ).getFirstname2().equals( list.get( j ).getFirstname() )
                    || list.get( i ).getName2().equals( list.get( j ).getName2() )
                    && list.get( i ).getFirstname2().equals( list.get( j ).getFirstname2() )
                    || list.get( i ).getName().equals( list.get( j ).getName2() )
                    && list.get( i ).getFirstname().equals( list.get( j ).getFirstname2() ) )
                    return true;
            }
        }
        return false;
    }
}
