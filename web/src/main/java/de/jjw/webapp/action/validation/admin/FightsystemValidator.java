/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightsystemValidator.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:48
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

import de.jjw.model.admin.Fightsystem;
import de.jjw.util.ICodestableConstants;
import de.jjw.util.IValueConstants;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.constants.admin.IFightsystemConstants;

import java.util.List;
import java.util.Vector;

public class FightsystemValidator
    implements IFightsystemConstants, IValueConstants, ICodestableConstants
{


    /**
     * @param weightclasses
     * @param weightclass
     * @param errors
     * @return
     */
    public static boolean isBusinessLogicValid( Fightsystem fightsystemPool, Fightsystem fightsystemDPool,
                                                Fightsystem fightsystemKo, List<ErrorElement> errors )
    {
        boolean isValid = true;

        if ( fightsystemPool.getMaxParticipant() <= fightsystemDPool.getMinParticipant() )
        {
            Vector errorFields = new Vector( 2 );
            errorFields.add( FIELD_POOL_MAX_PARTICIPANT );
            errorFields.add( FIELD_DPOOL_MIN_PARTICIPANT );
            errors.add( new ErrorElement( errorFields, ADMIN_FIGHTSYSTEM_MAX_POOL_MIN_DPOOL ) );
            isValid = false;
        }

        if ( fightsystemPool.getMaxParticipant() <= fightsystemDPool.getMinParticipant() )
        {
            Vector errorFields = new Vector( 2 );
            errorFields.add( FIELD_POOL_MAX_PARTICIPANT );
            errorFields.add( FIELD_DPOOL_MIN_PARTICIPANT );
            errors.add( new ErrorElement( errorFields, ADMIN_FIGHTSYSTEM_MAX_POOL_MIN_DPOOL ) );
            isValid = false;
        }

        return isValid;
    }


    public static boolean isRequiredFieldsValid( Fightsystem fightsystemPool, Fightsystem fightsystemDPool,
                                                 Fightsystem fightsystemKo, List<ErrorElement> errors )
    {
        boolean isValid = true;
        if ( fightsystemPool.getMinParticipant() < 1 || fightsystemPool.getMinParticipant() > 5 )
        {
            errors.add( new ErrorElement( FIELD_POOL_MIN_PARTICIPANT, ADMIN_FIGHTSYSTEM_ONLY_1_TO_5 ) );
            isValid = false;
        }
        if ( fightsystemPool.getMaxParticipant() < 1 || fightsystemPool.getMaxParticipant() > 5 )
        {
            errors.add( new ErrorElement( FIELD_POOL_MAX_PARTICIPANT, ADMIN_FIGHTSYSTEM_ONLY_1_TO_5 ) );
            isValid = false;
        }

        if ( fightsystemPool.getMinParticipant() > fightsystemPool.getMaxParticipant() )
        {
            Vector errorFields = new Vector( 2 );
            errorFields.add( FIELD_POOL_MAX_PARTICIPANT );
            errorFields.add( FIELD_POOL_MIN_PARTICIPANT );
            errors.add( new ErrorElement( errorFields, ADMIN_FIGHTSYSTEM_MIN_GREATER_MAX ) );
            isValid = false;
        }

        if ( fightsystemDPool.getMinParticipant() < 4 || fightsystemDPool.getMinParticipant() > 10 )
        {
            errors.add( new ErrorElement( FIELD_DPOOL_MIN_PARTICIPANT, ADMIN_FIGHTSYSTEM_ONLY_4_TO_10 ) );
            isValid = false;
        }
        if ( fightsystemDPool.getMaxParticipant() < 4 || fightsystemDPool.getMaxParticipant() > 10 )
        {
            errors.add( new ErrorElement( FIELD_DPOOL_MAX_PARTICIPANT, ADMIN_FIGHTSYSTEM_ONLY_4_TO_10 ) );
            isValid = false;
        }
        if ( fightsystemDPool.getMinParticipant() > fightsystemDPool.getMaxParticipant() )
        {
            Vector errorFields = new Vector( 2 );
            errorFields.add( FIELD_DPOOL_MAX_PARTICIPANT );
            errorFields.add( FIELD_DPOOL_MIN_PARTICIPANT );
            errors.add( new ErrorElement( errorFields, ADMIN_FIGHTSYSTEM_MIN_GREATER_MAX ) );
            isValid = false;
        }

        if ( fightsystemKo.getMinParticipant() < 8 || fightsystemKo.getMinParticipant() > 128 )
        {
            errors.add( new ErrorElement( FIELD_KO_MIN_PARTICIPANT, ADMIN_FIGHTSYSTEM_ONLY_8_TO_128 ) );
            isValid = false;
        }
        if ( fightsystemKo.getMaxParticipant() < 8 || fightsystemKo.getMaxParticipant() > 128 )
        {
            errors.add( new ErrorElement( FIELD_KO_MAX_PARTICIPANT, ADMIN_FIGHTSYSTEM_ONLY_8_TO_128 ) );
            isValid = false;
        }
        if ( fightsystemKo.getMinParticipant() > fightsystemKo.getMaxParticipant() )
        {
            Vector errorFields = new Vector( 2 );
            errorFields.add( FIELD_KO_MAX_PARTICIPANT );
            errorFields.add( FIELD_KO_MIN_PARTICIPANT );
            errors.add( new ErrorElement( errorFields, ADMIN_FIGHTSYSTEM_MIN_GREATER_MAX ) );
            isValid = false;
        }
        return isValid;
    }
}
