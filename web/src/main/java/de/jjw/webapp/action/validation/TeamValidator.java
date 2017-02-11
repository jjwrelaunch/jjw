/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : TeamValidator.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:50
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

package de.jjw.webapp.action.validation;

import java.util.List;
import java.util.Vector;

import de.jjw.model.Team;
import de.jjw.util.ICodestableConstants;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.constants.ITeamConstants;


public class TeamValidator
    implements ITeamConstants, IValueConstants, ICodestableConstants
{


    public static boolean isRequiredFieldsValid( Team team, Vector<ErrorElement> errors )
    {
        boolean isValid = true;

        if ( TypeUtil.isEmptyOrDefault( team.getTeamName() ) )
        {
            errors.add( new ErrorElement( FIELD_TEAMNAME, GEN_NO_VALUE ) );
        }
        if ( TypeUtil.isEmptyOrDefault( team.getCountry().getId().longValue() ) )
        {
            errors.add( new ErrorElement( FIELD_COUNTRY, GEN_NO_VALUE ) );
        }
        if ( TypeUtil.isEmptyOrDefault( team.getRegion().getId().longValue() ) )
        {
            errors.add( new ErrorElement( FIELD_REGION, GEN_NO_VALUE ) );
        }

        if ( errors.size() > 0 )
        {
            isValid = false;
        }

        return isValid;
    }

    public static boolean isBusinessLogicValid( Team team, List<ErrorElement> errors, List<Team> teamList,
                                                boolean isEdit )
    {
        // TODO: test if there is a class of fighter or duo deletestop than only
        // corrections
        // on the name are allowed
        for ( Team item : teamList )
        {
            if ( isEdit )
            {

                if ( item.getTeamName().equals( team.getTeamName() ) &&
                    item.getId().longValue() != team.getId().longValue() )
                {
                    errors.add( new ErrorElement( FIELD_TEAMNAME, ADMIN_TEAM_EXISTS ) );
                    return false;
                }
            }
            else
            {

                if ( item.getTeamName().equals( team.getTeamName() ) )
                {
                    errors.add( new ErrorElement( FIELD_TEAMNAME, ADMIN_TEAM_EXISTS ) );
                    return false;
                }
            }
        }
        return true;
    }
}
