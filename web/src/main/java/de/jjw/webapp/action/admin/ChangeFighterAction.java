/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : ChangeFighterAction.java
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

package de.jjw.webapp.action.admin;

import java.io.Serializable;
import java.util.Vector;

import de.jjw.service.fighting.FightingclassManager;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.action.validation.fighting.FighterChangeValidator;

public class ChangeFighterAction
    extends BasePage
    implements Serializable, IGlobalWebConstants
{

    private FightingclassManager fightingclassManager;

    private int fighter1 = TypeUtil.INT_DEFAULT;

    private int fighter2 = TypeUtil.INT_DEFAULT;

    private long fightingclassId = TypeUtil.LONG_MIN;

    public String getFightingclass_Id()
    {
        Vector<ErrorElement> errors = new Vector<ErrorElement>();
        try
        {
            if ( getRequest().getParameter( HTML_PARAM_FIGHTINGCLASS_ID ) == null )
            {
                // error missing parameter
                return null;
            }
            else
            {
                return getRequest().getParameter( HTML_PARAM_FIGHTINGCLASS_ID );
            }
        }
        catch ( Exception e )
        {
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
            return null;
        }

    }

    public String changeFights()
    {
        String ret = SUCCESS;
        Vector<ErrorElement> errors = new Vector<ErrorElement>();
        try
        {

            if ( !FighterChangeValidator.isRequiredFieldsValid( this, errors ) )
            {
                setErrorMessageVector( errors );
                return null;
            }

            if ( !FighterChangeValidator.isBusinessLogicValid(
                                                               this,
                                                               fightingclassManager.getNumberOfFighterInFightingclass( fightingclassId ),
                                                               errors ) )
            {
                setErrorMessageVector( errors );
                return null;
            }
            fightingclassManager.changeFighterInClass( getFighter1(), getFighter2(), fightingclassId );

            // navigate to class
            String contextPath = getFacesContext().getExternalContext().getRequestContextPath();

            StringBuffer sb = new StringBuffer( 120 );
            sb.append( contextPath ).append( SHOW_FIGHTINGCLASS ).append( HTML_QUESTION_MARK ).append(
                                                                                                       HTML_PARAM_FIGHTINGCLASS_ID ).append(
                                                                                                                                             HTML_EQUAL ).append(
                                                                                                                                                                  fightingclassId );

            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     sb.toString() ) );
            return null;

        }
        catch ( Exception e )
        {
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
            return null;
        }

    }

    public FightingclassManager getFightingclassManager()
    {
        return fightingclassManager;
    }

    public void setFightingclassManager( FightingclassManager fightingclassManager )
    {
        this.fightingclassManager = fightingclassManager;
    }

    public int getFighter1()
    {
        return fighter1;
    }

    public void setFighter1( int fighter1 )
    {
        this.fighter1 = fighter1;
    }

    public int getFighter2()
    {
        return fighter2;
    }

    public void setFighter2( int fighter2 )
    {
        this.fighter2 = fighter2;
    }

    public long getFightingclassId()
    {
        if ( TypeUtil.isEmpty( fightingclassId ) && !TypeUtil.isEmptyOrDefault( getFightingclass_Id() ) )
        {
            return Long.valueOf( getFightingclass_Id() );
        }
        return fightingclassId;
    }

    public void setFightingclassId( long fightingclassId )
    {
        this.fightingclassId = fightingclassId;
    }

}
