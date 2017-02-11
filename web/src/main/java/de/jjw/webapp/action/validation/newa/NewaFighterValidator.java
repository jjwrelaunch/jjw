/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaFighterValidator.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:46
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

import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import de.jjw.model.newa.NewaFighter;
import de.jjw.service.modelWeb.NewaFighterWeb;
import de.jjw.util.ICodestableConstants;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.constants.admin.IFighterConstants;

public class NewaFighterValidator
    implements IFighterConstants, IValueConstants, ICodestableConstants
{
    protected final static Logger log = Logger.getRootLogger();

    public static boolean isRequiredFieldsValid( List<NewaFighterWeb> newaFighters, Vector<ErrorElement> errors )
    {
        boolean isValid = true;

        for ( NewaFighterWeb item : newaFighters )
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

    public static boolean isRequiredFieldsValid( NewaFighter fighter, Vector<ErrorElement> errors )
    {
        boolean isValid = true;

        if ( TypeUtil.isEmptyOrDefault( fighter.getName() ) )
        {
            errors.add( new ErrorElement( FIELD_NAME, GEN_NO_VALUE ) );
        }
        if ( TypeUtil.isEmptyOrDefault( fighter.getFirstname() ) )
        {
            errors.add( new ErrorElement( FIELD_FIRSTNAME, GEN_NO_VALUE ) );
        }

        if ( fighter.getTeam() == null || TypeUtil.isEmptyOrDefault( fighter.getTeam().getId() ) )
        {
            errors.add( new ErrorElement( FIELD_TEAM, ADMIN_NEWA_FIGHTER_NO_TEAM ) );
        }

        if ( fighter.getWeight() < TypeUtil.DOUBLE_DEFAULT )
        {
            errors.add( new ErrorElement( FIELD_WEIGHT, ADMIN_NEWA_FIGHTER_NEGATIV_WEIGHT ) );
        }
        if ( fighter.getWeight() > MAX_WEIGHTCLASS )
        {
            errors.add( new ErrorElement( FIELD_WEIGHT, ADMIN_NEWA_FIGHTER_IS_TO_HAVY ) );
        }
        
        if ( fighter.getAge() == null || TypeUtil.isEmptyOrDefault( fighter.getAge().getId() ) )
        {
            errors.add( new ErrorElement( FIELD_AGE, ADMIN_NEWA_FIGHTER_NO_AGE ) );
        }

        if ( errors.size() > 0 )
        {
            isValid = false;
        }
        return isValid;

    }

    public static boolean isBusinessLogicValid( NewaFighter fighter, List<ErrorElement> errors )
    {
        boolean isValid = true;

        if ( ICodestableConstants.FIGHTER_NOT_READY.equals( fighter.getReady() ) &&
            fighter.getWeight() <= TypeUtil.DOUBLE_DEFAULT )
        {
            errors.add( new ErrorElement( FIELD_WEIGHT, ADMIN_NEWA_FIGHTER_NEGATIV_WEIGHT ) );
        }

        if ( errors.size() > 0 )
        {
            isValid = false;
        }
        return isValid;

    }

    public static boolean isDoubleNameNewaFighter( List<NewaFighterWeb> list )
    {
        if ( list == null || list.isEmpty() )
            return false;

        for ( int i = 0; i < list.size(); i++ )
        {
            for ( int j = i + 1; j < list.size(); j++ )
            {
                if ( list.get( i ).getName().equals( list.get( j ).getName() )
                    && list.get( i ).getFirstname().equals( list.get( j ).getFirstname() ) )
                    return true;
            }
        }
        return false;
    }
}
