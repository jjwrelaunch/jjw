/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : ChangeNewaFighterAction.java
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

package de.jjw.webapp.action.admin.newa;

import java.io.Serializable;
import java.util.Vector;

import de.jjw.service.newa.NewaclassManager;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.action.validation.newa.NewaFighterChangeValidator;

public class ChangeNewaFighterAction
    extends BasePage
    implements Serializable, IGlobalWebConstants
{

    private NewaclassManager newaclassManager;

    private int fighter1 = TypeUtil.INT_DEFAULT;

    private int fighter2 = TypeUtil.INT_DEFAULT;

    private long newaclassId = TypeUtil.LONG_MIN;

    public String getNewaclass_Id()
    {
        Vector<ErrorElement> errors = new Vector<ErrorElement>();
        try
        {
            if ( getRequest().getParameter( HTML_PARAM_NEWACLASS_ID ) == null )
            {
                // error missing parameter
                return null;
            }
            else
            {
                return getRequest().getParameter( HTML_PARAM_NEWACLASS_ID );
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
        Vector<ErrorElement> errors = new Vector<ErrorElement>();
        try
        {

            if ( !NewaFighterChangeValidator.isRequiredFieldsValid( this, errors ) )
            {
                setErrorMessageVector( errors );
                return null;
            }

            if ( !NewaFighterChangeValidator.isBusinessLogicValid(
                                                               this,
                                                                   newaclassManager.getNumberOfFighterInNewaclass( newaclassId ),
                                                               errors ) )
            {
                setErrorMessageVector( errors );
                return null;
            }
            newaclassManager.changeFighterInClass( getFighter1(), getFighter2(), newaclassId );

            // navigate to class
            String contextPath = getFacesContext().getExternalContext().getRequestContextPath();

            StringBuffer sb = new StringBuffer( 120 );
            sb.append( contextPath ).append( SHOW_NEWACLASS ).append( HTML_QUESTION_MARK ).append( HTML_PARAM_NEWACLASS_ID ).append( HTML_EQUAL ).append( newaclassId );

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

    public NewaclassManager getNewaclassManager()
    {
        return newaclassManager;
    }

    public void setNewaclassManager( NewaclassManager newaclassManager )
    {
        this.newaclassManager = newaclassManager;
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

    public long getNewaclassId()
    {
        if ( TypeUtil.isEmpty( newaclassId ) && !TypeUtil.isEmptyOrDefault( getNewaclass_Id() ) )
        {
            return Long.valueOf( getNewaclass_Id() );
        }
        return newaclassId;
    }

    public void setNewaclassId( long newaclassId )
    {
        this.newaclassId = newaclassId;
    }

}
