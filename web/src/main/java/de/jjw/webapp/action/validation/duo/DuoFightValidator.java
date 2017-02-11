/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoFightValidator.java
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

import java.util.Vector;

import de.jjw.util.ICodestableConstants;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.action.admin.DuoFightAction;
import de.jjw.webapp.action.admin.FriendlyDuoFightAction;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.constants.admin.IFightConstants;

public class DuoFightValidator
    implements IFightConstants, IValueConstants, ICodestableConstants, IGlobalWebConstants
{

    public static boolean isRequiredFieldsValid( DuoFightAction fightAction, Vector<ErrorElement> errors )
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

        if ( TypeUtil.isNegativ( fightAction.getPointsRedMax() ) )
        {
            errors.add( new ErrorElement( FIELD_POINTS_RED_MAX, GEN_VALUE_NOT_NEGATIV ) );
        }
        if ( TypeUtil.isNegativ( fightAction.getPointsBlueMax() ) )
        {
            errors.add( new ErrorElement( FIELD_POINTS_BLUE_Max, GEN_VALUE_NOT_NEGATIV ) );
        }

        if ( ( fightAction.getPointsBlue() * 10 ) % 5 != 0 )
        {
            errors.add( new ErrorElement( FIELD_POINTS_BLUE, DUO_FIGHT_NO_VALID_POINTS ) );
        }

        if ( ( fightAction.getPointsRed() * 10 ) % 5 != 0 )
        {
            errors.add( new ErrorElement( FIELD_POINTS_RED, DUO_FIGHT_NO_VALID_POINTS ) );
        }

        if ( ( fightAction.getPointsBlueMax() * 10 ) % 5 != 0 )
        {
            errors.add( new ErrorElement( FIELD_POINTS_BLUE_Max, DUO_FIGHT_NO_VALID_POINTS ) );
        }

        if ( ( fightAction.getPointsRedMax() * 10 ) % 5 != 0 )
        {
            errors.add( new ErrorElement( FIELD_POINTS_RED_MAX, DUO_FIGHT_NO_VALID_POINTS ) );
        }

        if ( errors.size() > 0 )
        {
            return false;
        }

        if ( fightAction.getPointsBlue() > DUO_FIGHT_MAX_POINTS )
        {
            errors.add( new ErrorElement( FIELD_POINTS_BLUE, DUO_FIGHT_POINTS_TO_BIG ) );
        }

        if ( fightAction.getPointsRed() > DUO_FIGHT_MAX_POINTS )
        {
            errors.add( new ErrorElement( FIELD_POINTS_RED, DUO_FIGHT_POINTS_TO_BIG ) );
        }

        if ( fightAction.getPointsBlueMax() > DUO_FIGHT_MAX_POINTS )
        {
            errors.add( new ErrorElement( FIELD_POINTS_BLUE_Max, DUO_FIGHT_POINTS_TO_BIG ) );
        }

        if ( fightAction.getPointsRedMax() > DUO_FIGHT_MAX_POINTS )
        {
            errors.add( new ErrorElement( FIELD_POINTS_RED_MAX, DUO_FIGHT_POINTS_TO_BIG ) );
        }

        if ( errors.size() > 0 )
        {
            return false;
        }

        return isValid;
    }

    /**
     * friendly
     * 
     * @param fightAction
     * @param errors
     * @return
     */
    public static boolean isRequiredFieldsValid( FriendlyDuoFightAction fightAction, Vector<ErrorElement> errors )
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

        if ( TypeUtil.isNegativ( fightAction.getPointsRedMax() ) )
        {
            errors.add( new ErrorElement( FIELD_POINTS_RED_MAX, GEN_VALUE_NOT_NEGATIV ) );
        }
        if ( TypeUtil.isNegativ( fightAction.getPointsBlueMax() ) )
        {
            errors.add( new ErrorElement( FIELD_POINTS_BLUE_Max, GEN_VALUE_NOT_NEGATIV ) );
        }

        if ( ( fightAction.getPointsBlue() * 10 ) % 5 != 0 )
        {
            errors.add( new ErrorElement( FIELD_POINTS_BLUE, DUO_FIGHT_NO_VALID_POINTS ) );
        }

        if ( ( fightAction.getPointsRed() * 10 ) % 5 != 0 )
        {
            errors.add( new ErrorElement( FIELD_POINTS_RED, DUO_FIGHT_NO_VALID_POINTS ) );
        }

        if ( ( fightAction.getPointsBlueMax() * 10 ) % 5 != 0 )
        {
            errors.add( new ErrorElement( FIELD_POINTS_BLUE_Max, DUO_FIGHT_NO_VALID_POINTS ) );
        }

        if ( ( fightAction.getPointsRedMax() * 10 ) % 5 != 0 )
        {
            errors.add( new ErrorElement( FIELD_POINTS_RED_MAX, DUO_FIGHT_NO_VALID_POINTS ) );
        }

        if ( errors.size() > 0 )
        {
            return false;
        }

        if ( fightAction.getPointsBlue() > DUO_FIGHT_MAX_POINTS )
        {
            errors.add( new ErrorElement( FIELD_POINTS_BLUE, DUO_FIGHT_POINTS_TO_BIG ) );
        }

        if ( fightAction.getPointsRed() > DUO_FIGHT_MAX_POINTS )
        {
            errors.add( new ErrorElement( FIELD_POINTS_RED, DUO_FIGHT_POINTS_TO_BIG ) );
        }

        if ( fightAction.getPointsBlueMax() > DUO_FIGHT_MAX_POINTS )
        {
            errors.add( new ErrorElement( FIELD_POINTS_BLUE_Max, DUO_FIGHT_POINTS_TO_BIG ) );
        }

        if ( fightAction.getPointsRedMax() > DUO_FIGHT_MAX_POINTS )
        {
            errors.add( new ErrorElement( FIELD_POINTS_RED_MAX, DUO_FIGHT_POINTS_TO_BIG ) );
        }

        if ( errors.size() > 0 )
        {
            return false;
        }

        return isValid;
    }

    public static boolean isBusinessLogicValid( DuoFightAction fightAction, Vector<ErrorElement> errors )
    {
        boolean isValid = true;
        if ( fightAction.getWinner() != TypeUtil.INT_DEFAULT )
        {
            if ( fightAction.isOvertime()
                && ( fightAction.getPointsBlue() >= fightAction.getPointsBlueMax() || fightAction.getPointsRed() >= fightAction.getPointsRedMax() ) )
            {
                errors.add( new ErrorElement( DUO_FIGHT_WRONG_FIGHT_RESULT ) );
            }

            if ( fightAction.isOvertime() && ( fightAction.getPointsBlueMax() == fightAction.getPointsRedMax() ) )
            {
                errors.add( new ErrorElement( DUO_FIGHT_NEED_EXTRA_ROUND ) );
            }

            if ( !fightAction.isOvertime() && ( fightAction.getPointsBlue() == fightAction.getPointsRed() )
                && fightAction.getPointsBlue() != TypeUtil.DOUBLE_DEFAULT )
            {
                errors.add( new ErrorElement( DUO_FIGHT_NEED_EXTRA_ROUND ) );
            }

            if ( fightAction.isOvertime() && ( fightAction.getPointsBlue() != fightAction.getPointsRed() ) )
            {
                errors.add( new ErrorElement( DUO_FIGHT_POINTS_MUST_EQAL_BY_OVERTIME ) );
            }

            if ( errors.size() > 0 )
            {
                return false;
            }

            // no overtime, red won but blue has more points
            if ( !fightAction.isOvertime() && fightAction.getWinner() == FIGHT_WINNER_RED
                && fightAction.getPointsRed() <= fightAction.getPointsBlue() )
            {
                errors.add( new ErrorElement( DUO_FIGHT_WRONG_WINNER ) );
            }

            if ( !fightAction.isOvertime() && fightAction.getWinner() == FIGHT_WINNER_BLUE
                && fightAction.getPointsRed() >= fightAction.getPointsBlue() )
            {
                errors.add( new ErrorElement( DUO_FIGHT_WRONG_WINNER ) );
            }

            if ( fightAction.isOvertime() && fightAction.getWinner() == FIGHT_WINNER_RED
                && fightAction.getPointsRedMax() <= fightAction.getPointsBlueMax() )
            {
                errors.add( new ErrorElement( DUO_FIGHT_WRONG_WINNER ) );
            }

            if ( fightAction.isOvertime() && fightAction.getWinner() == FIGHT_WINNER_BLUE
                && fightAction.getPointsRedMax() >= fightAction.getPointsBlueMax() )
            {
                errors.add( new ErrorElement( DUO_FIGHT_WRONG_WINNER ) );
            }
        }
        if ( errors.size() > 0 )
        {
            return false;
        }

        return isValid;
    }

    /**
     * friendly
     * 
     * @param fightAction
     * @param errors
     * @return
     */
    public static boolean isBusinessLogicValid( FriendlyDuoFightAction fightAction, Vector<ErrorElement> errors )
    {
        boolean isValid = true;
        if ( fightAction.getWinner() != TypeUtil.INT_DEFAULT )
        {
            if ( fightAction.isOvertime()
                && ( fightAction.getPointsBlue() >= fightAction.getPointsBlueMax() || fightAction.getPointsRed() >= fightAction.getPointsRedMax() ) )
            {
                errors.add( new ErrorElement( DUO_FIGHT_WRONG_FIGHT_RESULT ) );
            }

            if ( fightAction.isOvertime() && ( fightAction.getPointsBlueMax() == fightAction.getPointsRedMax() ) )
            {
                errors.add( new ErrorElement( DUO_FIGHT_NEED_EXTRA_ROUND ) );
            }

            if ( !fightAction.isOvertime() && ( fightAction.getPointsBlue() == fightAction.getPointsRed() )
                && fightAction.getPointsBlue() != TypeUtil.DOUBLE_DEFAULT )
            {
                errors.add( new ErrorElement( DUO_FIGHT_NEED_EXTRA_ROUND ) );
            }

            if ( fightAction.isOvertime() && ( fightAction.getPointsBlue() != fightAction.getPointsRed() ) )
            {
                errors.add( new ErrorElement( DUO_FIGHT_POINTS_MUST_EQAL_BY_OVERTIME ) );
            }

            if ( errors.size() > 0 )
            {
                return false;
            }

            // no overtime, red won but blue has more points
            if ( !fightAction.isOvertime() && fightAction.getWinner() == FIGHT_WINNER_RED
                && fightAction.getPointsRed() <= fightAction.getPointsBlue() )
            {
                errors.add( new ErrorElement( DUO_FIGHT_WRONG_WINNER ) );
            }

            if ( !fightAction.isOvertime() && fightAction.getWinner() == FIGHT_WINNER_BLUE
                && fightAction.getPointsRed() >= fightAction.getPointsBlue() )
            {
                errors.add( new ErrorElement( DUO_FIGHT_WRONG_WINNER ) );
            }

            if ( fightAction.isOvertime() && fightAction.getWinner() == FIGHT_WINNER_RED
                && fightAction.getPointsRedMax() <= fightAction.getPointsBlueMax() )
            {
                errors.add( new ErrorElement( DUO_FIGHT_WRONG_WINNER ) );
            }

            if ( fightAction.isOvertime() && fightAction.getWinner() == FIGHT_WINNER_BLUE
                && fightAction.getPointsRedMax() >= fightAction.getPointsBlueMax() )
            {
                errors.add( new ErrorElement( DUO_FIGHT_WRONG_WINNER ) );
            }
        }
        if ( errors.size() > 0 )
        {
            return false;
        }

        return isValid;
    }

    public static boolean isRequiredFieldsValid( de.jjw.webapp.action.off.DuoFightAction fightAction,
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

        if ( TypeUtil.isNegativ( fightAction.getPointsRedMax() ) )
        {
            errors.add( new ErrorElement( FIELD_POINTS_RED_MAX, GEN_VALUE_NOT_NEGATIV ) );
        }
        if ( TypeUtil.isNegativ( fightAction.getPointsBlueMax() ) )
        {
            errors.add( new ErrorElement( FIELD_POINTS_BLUE_Max, GEN_VALUE_NOT_NEGATIV ) );
        }

        if ( ( fightAction.getPointsBlue() * 10 ) % 5 != 0 )
        {
            errors.add( new ErrorElement( FIELD_POINTS_BLUE, DUO_FIGHT_NO_VALID_POINTS ) );
        }

        if ( ( fightAction.getPointsRed() * 10 ) % 5 != 0 )
        {
            errors.add( new ErrorElement( FIELD_POINTS_RED, DUO_FIGHT_NO_VALID_POINTS ) );
        }

        if ( ( fightAction.getPointsBlueMax() * 10 ) % 5 != 0 )
        {
            errors.add( new ErrorElement( FIELD_POINTS_BLUE_Max, DUO_FIGHT_NO_VALID_POINTS ) );
        }

        if ( ( fightAction.getPointsRedMax() * 10 ) % 5 != 0 )
        {
            errors.add( new ErrorElement( FIELD_POINTS_RED_MAX, DUO_FIGHT_NO_VALID_POINTS ) );
        }

        if ( errors.size() > 0 )
        {
            return false;
        }

        if ( fightAction.getPointsBlue() > DUO_FIGHT_MAX_POINTS )
        {
            errors.add( new ErrorElement( FIELD_POINTS_BLUE, DUO_FIGHT_POINTS_TO_BIG ) );
        }

        if ( fightAction.getPointsRed() > DUO_FIGHT_MAX_POINTS )
        {
            errors.add( new ErrorElement( FIELD_POINTS_RED, DUO_FIGHT_POINTS_TO_BIG ) );
        }

        if ( fightAction.getPointsBlueMax() > DUO_FIGHT_MAX_POINTS )
        {
            errors.add( new ErrorElement( FIELD_POINTS_BLUE_Max, DUO_FIGHT_POINTS_TO_BIG ) );
        }

        if ( fightAction.getPointsRedMax() > DUO_FIGHT_MAX_POINTS )
        {
            errors.add( new ErrorElement( FIELD_POINTS_RED_MAX, DUO_FIGHT_POINTS_TO_BIG ) );
        }

        if ( errors.size() > 0 )
        {
            return false;
        }

        return isValid;
    }

    public static boolean isBusinessLogicValid( de.jjw.webapp.action.off.DuoFightAction fightAction,
                                                Vector<ErrorElement> errors )
    {
        boolean isValid = true;
        if ( fightAction.getWinner() != TypeUtil.INT_DEFAULT )
        {
            if ( fightAction.isOvertime()
                && ( fightAction.getPointsBlue() >= fightAction.getPointsBlueMax() || fightAction.getPointsRed() >= fightAction.getPointsRedMax() ) )
            {
                errors.add( new ErrorElement( DUO_FIGHT_WRONG_FIGHT_RESULT ) );
            }

            if ( fightAction.isOvertime() && ( fightAction.getPointsBlueMax() == fightAction.getPointsRedMax() ) )
            {
                errors.add( new ErrorElement( DUO_FIGHT_NEED_EXTRA_ROUND ) );
            }

            if ( !fightAction.isOvertime() && ( fightAction.getPointsBlue() == fightAction.getPointsRed() )
                && fightAction.getPointsBlue() != TypeUtil.DOUBLE_DEFAULT )
            {
                errors.add( new ErrorElement( DUO_FIGHT_NEED_EXTRA_ROUND ) );
            }

            if ( fightAction.isOvertime() && ( fightAction.getPointsBlue() != fightAction.getPointsRed() ) )
            {
                errors.add( new ErrorElement( DUO_FIGHT_POINTS_MUST_EQAL_BY_OVERTIME ) );
            }

            if ( errors.size() > 0 )
            {
                return false;
            }

            // no overtime, red won but blue has more points
            if ( !fightAction.isOvertime() && fightAction.getWinner() == FIGHT_WINNER_RED
                && fightAction.getPointsRed() <= fightAction.getPointsBlue() )
            {
                errors.add( new ErrorElement( DUO_FIGHT_WRONG_WINNER ) );
            }

            if ( !fightAction.isOvertime() && fightAction.getWinner() == FIGHT_WINNER_BLUE
                && fightAction.getPointsRed() >= fightAction.getPointsBlue() )
            {
                errors.add( new ErrorElement( DUO_FIGHT_WRONG_WINNER ) );
            }

            if ( fightAction.isOvertime() && fightAction.getWinner() == FIGHT_WINNER_RED
                && fightAction.getPointsRedMax() <= fightAction.getPointsBlueMax() )
            {
                errors.add( new ErrorElement( DUO_FIGHT_WRONG_WINNER ) );
            }

            if ( fightAction.isOvertime() && fightAction.getWinner() == FIGHT_WINNER_BLUE
                && fightAction.getPointsRedMax() >= fightAction.getPointsBlueMax() )
            {
                errors.add( new ErrorElement( DUO_FIGHT_WRONG_WINNER ) );
            }
        }
        if ( errors.size() > 0 )
        {
            return false;
        }

        return isValid;
    }
}
