/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : ChangeDuoTeamAction.java
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

package de.jjw.webapp.action.admin;

import de.jjw.service.duo.DuoclassManager;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.action.validation.duo.DuoChangeValidator;

import java.io.Serializable;
import java.util.Vector;

public class ChangeDuoTeamAction
    extends BasePage
    implements Serializable, IGlobalWebConstants
{

    private DuoclassManager duoclassManager;

    private int duoTeam1 = TypeUtil.INT_DEFAULT;

    private int duoTeam2 = TypeUtil.INT_DEFAULT;

    private long duoclassId = TypeUtil.LONG_MIN;

    public String getDuoclass_Id()
    {
        if ( getRequest().getParameter( HTML_PARAM_DUOCLASS_ID ) == null )
        {
            // error missing parameter
            return null;
        }
        else
        {
            return getRequest().getParameter( HTML_PARAM_DUOCLASS_ID );
        }

    }

    public String changeFights()
    {
        String ret = SUCCESS;
        Vector<ErrorElement> errors = new Vector<ErrorElement>();
        try
        {

            if ( !DuoChangeValidator.isRequiredFieldsValid( this, errors ) )
            {
                setErrorMessageVector( errors );
                return null;
            }

            if ( !DuoChangeValidator.isBusinessLogicValid( this,
                                                           duoclassManager.getNumberOfDuoTeamInDuoclass( duoclassId ),
                                                           errors ) )
            {
                setErrorMessageVector( errors );
                return null;
            }
            duoclassManager.changeDuoTeamsInClass( getDuoTeam1(), getDuoTeam2(), duoclassId );

            // navigate to class
            String contextPath = getFacesContext().getExternalContext().getRequestContextPath();

            StringBuffer sb = new StringBuffer( 120 );
            sb.append( contextPath ).append( SHOW_DUOCLASS ).append( HTML_QUESTION_MARK ).append(
                HTML_PARAM_DUOCLASS_ID ).append( HTML_EQUAL ).append( duoclassId );

            getFacesContext().getExternalContext().redirect(
                getFacesContext().getExternalContext().encodeActionURL( sb.toString() ) );
            return null;

        }
        catch ( Exception e )
        {
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
            return null;
        }

    }

    public DuoclassManager getDuoclassManager()
    {
        return duoclassManager;
    }

    public void setDuoclassManager( DuoclassManager duoclassManager )
    {
        this.duoclassManager = duoclassManager;
    }

    public int getDuoTeam1()
    {
        return duoTeam1;
    }

    public void setDuoTeam1( int duoTeam1 )
    {
        this.duoTeam1 = duoTeam1;
    }

    public int getDuoTeam2()
    {
        return duoTeam2;
    }

    public void setDuoTeam2( int duoTeam2 )
    {
        this.duoTeam2 = duoTeam2;
    }

    public long getDuoclassId()
    {
        if ( TypeUtil.isEmpty( duoclassId ) && !TypeUtil.isEmptyOrDefault( getDuoclass_Id() ) )
        {
            return Long.valueOf( getDuoclass_Id() );
        }
        return duoclassId;
    }

    public void setDuoclassId( long duoclassId )
    {
        this.duoclassId = duoclassId;
    }

}
