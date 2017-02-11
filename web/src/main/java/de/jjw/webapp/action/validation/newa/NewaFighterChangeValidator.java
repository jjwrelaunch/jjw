/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaFighterChangeValidator.java
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

package de.jjw.webapp.action.validation.newa;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import de.jjw.util.ICodestableConstants;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.action.admin.newa.ChangeNewaFighterAction;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.constants.admin.INewaFighterChangeConstants;

public class NewaFighterChangeValidator
    implements INewaFighterChangeConstants, IValueConstants, ICodestableConstants
{

    public static boolean isRequiredFieldsValid( ChangeNewaFighterAction changeNewaFighterAction,
                                                 Vector<ErrorElement> errors )
    {
        boolean isValid = true;
        if ( TypeUtil.isEmptyOrDefault( changeNewaFighterAction.getFighter1() ) )
        {
            errors.add( new ErrorElement( FIELD_FIGHTER1, GEN_NO_VALUE ) );
        }

        if ( TypeUtil.isEmptyOrDefault( changeNewaFighterAction.getFighter2() ) )
        {
            errors.add( new ErrorElement( FIELD_FIGHTER2, GEN_NO_VALUE ) );
        }

        if ( errors.size() > 0 )
        {
            return false;
        }
        
        if ( changeNewaFighterAction.getFighter1() < 1 )
        {
            errors.add( new ErrorElement( FIELD_FIGHTER1, GEN_VALUE_NOT_NEGATIV ) );
        }

        if ( changeNewaFighterAction.getFighter2() < 1 )
        {
            errors.add( new ErrorElement( FIELD_FIGHTER2, GEN_VALUE_NOT_NEGATIV ) );
        }

        if ( changeNewaFighterAction.getFighter1() == changeNewaFighterAction.getFighter2() )
        {
            List<String> errorList = new ArrayList<String>( 2 );
            errorList.add( FIELD_FIGHTER1 );
            errorList.add( FIELD_FIGHTER2 );
            errors.add( new ErrorElement( errorList, ADMIN_CHANGE_NEWA_FIGHTER_SAME_FIGHTER ) );
        }
        if ( errors.size() > 0 )
        {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isBusinessLogicValid( ChangeNewaFighterAction changeNewaFighterAction, int numberOfFighter,
                                                List<ErrorElement> errors )
    {
        boolean isValid = true;

        if ( changeNewaFighterAction.getFighter1() > numberOfFighter )
        {
            errors.add( new ErrorElement( FIELD_FIGHTER1, ADMIN_CHANGE_NEWA_FIGHTER_EMPTY_FIGHTER ) );
        }

        if ( changeNewaFighterAction.getFighter2() > numberOfFighter )
        {
            errors.add( new ErrorElement( FIELD_FIGHTER2, ADMIN_CHANGE_NEWA_FIGHTER_EMPTY_FIGHTER ) );
        }

        if ( errors.size() > 0 )
        {
            isValid = false;
        }
        return isValid;

    }

}
