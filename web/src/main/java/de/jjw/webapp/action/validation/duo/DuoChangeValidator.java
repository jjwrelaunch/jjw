/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoChangeValidator.java
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

package de.jjw.webapp.action.validation.duo;

import de.jjw.util.ICodestableConstants;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.action.admin.ChangeDuoTeamAction;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.constants.admin.IDuoTeamChangeConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DuoChangeValidator
    implements IDuoTeamChangeConstants, IValueConstants, ICodestableConstants
{

    public static boolean isRequiredFieldsValid( ChangeDuoTeamAction changeDuoTeamAction, Vector<ErrorElement> errors )
    {
        boolean isValid = true;
        if ( TypeUtil.isEmptyOrDefault( changeDuoTeamAction.getDuoTeam1() ) )
        {
            errors.add( new ErrorElement( FIELD_DUOTEAM1, GEN_NO_VALUE ) );
        }

        if ( TypeUtil.isEmptyOrDefault( changeDuoTeamAction.getDuoTeam2() ) )
        {
            errors.add( new ErrorElement( FIELD_DUOTEAM2, GEN_NO_VALUE ) );
        }
        
        if ( errors.size() > 0 )
        {
            return false;
        }

        if ( changeDuoTeamAction.getDuoTeam1() < 1 )
        {
            errors.add( new ErrorElement( FIELD_DUOTEAM1, GEN_VALUE_NOT_NEGATIV ) );
        }

        if ( changeDuoTeamAction.getDuoTeam2() < 1 )
        {
            errors.add( new ErrorElement( FIELD_DUOTEAM2, GEN_VALUE_NOT_NEGATIV ) );
        }

        if ( changeDuoTeamAction.getDuoTeam1() == changeDuoTeamAction.getDuoTeam2() )
        {
            List<String> errorList = new ArrayList<String>( 2 );
            errorList.add( FIELD_DUOTEAM1 );
            errorList.add( FIELD_DUOTEAM2 );
            errors.add( new ErrorElement( errorList, ADMIN_CHANGE_FIGHTER_SAME_FIGHTER ) );
        }
        if ( errors.size() > 0 )
        {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isBusinessLogicValid( ChangeDuoTeamAction changeDuoTeamAction, int numberOfDuoTeams,
                                                List<ErrorElement> errors )
    {
        boolean isValid = true;

        if ( changeDuoTeamAction.getDuoTeam1() > numberOfDuoTeams )
        {
            errors.add( new ErrorElement( FIELD_DUOTEAM1, ADMIN_CHANGE_FIGHTER_EMPTY_FIGHTER ) );
        }

        if ( changeDuoTeamAction.getDuoTeam2() > numberOfDuoTeams )
        {
            errors.add( new ErrorElement( FIELD_DUOTEAM2, ADMIN_CHANGE_FIGHTER_EMPTY_FIGHTER ) );
        }

        if ( errors.size() > 0 )
        {
            isValid = false;
        }
        return isValid;

    }

}
