/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaFightValidator.java
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

package de.jjw.webapp.action.validation.newa;

import java.util.Vector;

import de.jjw.util.ICodestableConstants;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.action.admin.newa.FriendlyNewaFightAction;
import de.jjw.webapp.action.admin.newa.NewaFightAction;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.constants.admin.IFightConstants;

public class NewaFightValidator
    implements IFightConstants, IValueConstants, ICodestableConstants
{

    public static boolean isRequiredFieldsValid( NewaFightAction fightAction, Vector<ErrorElement> errors )
    {
        boolean isValid = true;

        if ( TypeUtil.isNegativ( fightAction.getPointsRed() ) )
        {
            errors.add( new ErrorElement( FIELD_POINTS_RED, GEN_VALUE_NOT_NEGATIV ) );
        }
        if ( TypeUtil.isNegativ( fightAction.getPointsBlue() ) )
        {
            errors.add( new ErrorElement( FIELD_POINTS_BLUE, GEN_VALUE_NOT_NEGATIV ) );
        }

        if ( errors.size() > 0 )
        {
            return false;
        }
        return isValid;
    }

    /**
     * for friendlyfight
     * 
     * @param fightAction
     * @param errors
     * @return
     */
    public static boolean isRequiredFieldsValid( FriendlyNewaFightAction fightAction, Vector<ErrorElement> errors )
    {
        boolean isValid = true;

        if ( TypeUtil.isNegativ( fightAction.getPointsRed() ) )
        {
            errors.add( new ErrorElement( FIELD_POINTS_RED, GEN_VALUE_NOT_NEGATIV ) );
        }
        if ( TypeUtil.isNegativ( fightAction.getPointsBlue() ) )
        {
            errors.add( new ErrorElement( FIELD_POINTS_BLUE, GEN_VALUE_NOT_NEGATIV ) );
        }

        if ( errors.size() > 0 )
        {
            return false;
        }
        return isValid;
    }

    public static boolean isBusinessLogicValid( NewaFightAction fightAction, Vector<ErrorElement> errors )
    {
        boolean isValid = true;

        if ( fightAction.getPointsRed() > fightAction.getPointsBlue() && fightAction.getWinner() == FIGHT_WINNER_BLUE )
        {
            errors.add( new ErrorElement( FIELD_WINNER, FIGHT_WRONG_FIGHT_RESULT ) );
        }

        if ( fightAction.getPointsRed() < fightAction.getPointsBlue() && fightAction.getWinner() == FIGHT_WINNER_RED )
        {
            errors.add( new ErrorElement( FIELD_WINNER, FIGHT_WRONG_FIGHT_RESULT ) );
        }


        if ( errors.size() > 0 )
        {
            return false;
        }
        return isValid;

    }

    /**
     * for friendly
     * 
     * @param fightAction
     * @param errors
     * @return
     */
    public static boolean isBusinessLogicValid( FriendlyNewaFightAction fightAction, Vector<ErrorElement> errors )
    {
        boolean isValid = true;

        if ( fightAction.getPointsRed() > fightAction.getPointsBlue() && fightAction.getWinner() == FIGHT_WINNER_BLUE )
        {
            errors.add( new ErrorElement( FIELD_WINNER, FIGHT_WRONG_FIGHT_RESULT ) );
        }

        if ( fightAction.getPointsRed() < fightAction.getPointsBlue() && fightAction.getWinner() == FIGHT_WINNER_RED )
        {
            errors.add( new ErrorElement( FIELD_WINNER, FIGHT_WRONG_FIGHT_RESULT ) );
        }

        if ( fightAction.getPointsRed() == INT_DEFAULT && fightAction.getPointsBlue() == INT_DEFAULT
            && fightAction.getWinner() != INT_DEFAULT )
        {
            errors.add( new ErrorElement( FIELD_WINNER, FIGHT_WRONG_ZERO_POINTS ) );
        }

        if ( errors.size() > 0 )
        {
            return false;
        }
        return isValid;

    }

    // --------------official

    public static boolean isRequiredFieldsValid( de.jjw.webapp.action.off.newa.NewaFightAction fightAction,
                                                 Vector<ErrorElement> errors )
    {
        boolean isValid = true;

        if ( TypeUtil.isNegativ( fightAction.getPointsRed() ) )
        {
            errors.add( new ErrorElement( FIELD_POINTS_RED, GEN_VALUE_NOT_NEGATIV ) );
        }
        if ( TypeUtil.isNegativ( fightAction.getPointsBlue() ) )
        {
            errors.add( new ErrorElement( FIELD_POINTS_BLUE, GEN_VALUE_NOT_NEGATIV ) );
        }

        if ( errors.size() > 0 )
        {
            return false;
        }
        return isValid;
    }

    public static boolean isBusinessLogicValid( de.jjw.webapp.action.off.newa.NewaFightAction fightAction,
                                                Vector<ErrorElement> errors )
    {
        boolean isValid = true;

        if ( fightAction.getPointsRed() > fightAction.getPointsBlue() && fightAction.getWinner() == FIGHT_WINNER_BLUE )
        {
            errors.add( new ErrorElement( FIELD_WINNER, FIGHT_WRONG_FIGHT_RESULT ) );
        }

        if ( fightAction.getPointsRed() < fightAction.getPointsBlue() && fightAction.getWinner() == FIGHT_WINNER_RED )
        {
            errors.add( new ErrorElement( FIELD_WINNER, FIGHT_WRONG_FIGHT_RESULT ) );
        }

        if ( fightAction.getPointsRed() == INT_DEFAULT && fightAction.getPointsBlue() == INT_DEFAULT
            && fightAction.getWinner() != INT_DEFAULT )
        {
            errors.add( new ErrorElement( FIELD_WINNER, FIGHT_WRONG_ZERO_POINTS ) );
        }

        if ( errors.size() > 0 )
        {
            return false;
        }
        return isValid;

    }
}
