/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoclassAction.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:44
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

package de.jjw.webapp.action.off;

import java.io.Serializable;

import de.jjw.model.admin.Fightsystem;
import de.jjw.model.duo.DuoDoublePoolClass;
import de.jjw.model.duo.DuoKoClass;
import de.jjw.model.duo.DuoSimplePoolClass;
import de.jjw.model.duo.Duoclass;
import de.jjw.service.duo.DuoclassManager;
import de.jjw.service.duo.UserDuoclassManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.util.IValueConstants;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.constants.admin.IDuoclassConstants;
import de.jjw.webapp.jsfTags.component.DuoDoublePoolWebComponent;
import de.jjw.webapp.jsfTags.component.DuoKoWebComponent;
import de.jjw.webapp.jsfTags.component.DuoSimplePoolWebComponent;

public class DuoclassAction
    extends BasePage
    implements Serializable, IGlobalWebConstants, IDuoclassConstants
{

    private static String DUOCLASS = "duoclass";

    DuoclassManager duoclassManager;

    private Duoclass duoclass = null;

    private UserDuoclassManager userDuoclassManager = null;

    public Duoclass getDuoclassDisplay()
    {

        if ( getRequest().getParameter( HTML_PARAM_DUOCLASS_ID ) == null )
        {
            // error missing parameter
            return null;
        }
        else
        {
            Long duoclassId = null;
            duoclassId = Long.valueOf( getRequest().getParameter( HTML_PARAM_DUOCLASS_ID ) );
            try
            {
                switch ( getDuoclassManager().getFightsystemOfDuoclass( duoclassId ) )
                {
                    case Fightsystem.SIMPLE_POOL:
                        if ( duoclass == null )
                        {
                            if ( getRequest().getAttribute( DUOCLASS ) == null )
                            {
                                setDuoclass( getDuoclassManager().getDuoclassSimplePool( duoclassId ) );
                                getRequest().setAttribute( DUOCLASS, duoclassId );
                            }
                            else
                                setDuoclass( (Duoclass) getRequest().getAttribute( DUOCLASS ) );
                        }
                        setDuoSimplePoolWebComponent( new DuoSimplePoolWebComponent() );
                        getDuoSimplePoolWebComponent().setDuoclass( (DuoSimplePoolClass) duoclass );
                        break;

                    case Fightsystem.DOUBLE_POOL:
                        if ( duoclass == null )
                        {
                            if ( getRequest().getAttribute( DUOCLASS ) == null )
                            {
                                setDuoclass( getDuoclassManager().getDuoclassDoublePool( duoclassId ) );
                                getRequest().setAttribute( DUOCLASS, duoclassId );
                            }
                            else
                                setDuoclass( (Duoclass) getRequest().getAttribute( DUOCLASS ) );
                        }
                        setDuoDoublePoolWebComponent( new DuoDoublePoolWebComponent() );
                        getDuoDoublePoolWebComponent().setDuoclass( (DuoDoublePoolClass) duoclass );
                        break;

                    case Fightsystem.DOUBLE_KO:
                        if ( duoclass == null )
                        {
                            if ( getRequest().getAttribute( DUOCLASS ) == null )
                            {
                                setDuoclass( getDuoclassManager().getDuoclassKo( duoclassId ) );
                                getRequest().setAttribute( DUOCLASS, duoclassId );
                            }
                            else
                                setDuoclass( (Duoclass) getRequest().getAttribute( DUOCLASS ) );
                        }
                        setDuoKoWebComponent( new DuoKoWebComponent() );
                        getDuoKoWebComponent().setDuoclass( (DuoKoClass) duoclass );
                        break;
                }
            }
            catch ( JJWManagerException e )
            {
                addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
                return null;
            }

        }

        return duoclass;
    }

    /**
     * @return Returns the duoclass.
     */
    public Duoclass getDuoclass()
    {
        return duoclass;
    }

    /**
     * @param duoclass The duoclass to set.
     */
    public void setDuoclass( Duoclass duoclass )
    {
        this.duoclass = duoclass;
    }

    /**
     * @param duoclassManager The duoclassManager to set.
     */
    public void setDuoclassManager( DuoclassManager duoclassManager )
    {
        this.duoclassManager = duoclassManager;
    }

    public DuoclassManager getDuoclassManager()
    {
        return duoclassManager;
    }

    private DuoSimplePoolWebComponent duoSimplePoolWebComponent = null;

    private DuoDoublePoolWebComponent duoDoublePoolWebComponent = null;

    private DuoKoWebComponent duoKoWebComponent = null;

    /**
     * calculate if the user can edit the pool
     * 
     * @return
     */
    public String getReadOnly()
    {
        try
        {
            if ( !userDuoclassManager.isAccessForTatamiUser( getCtx().getUserId(), getDuoclass().getId() ) )
                return IValueConstants.STRING_TRUE;
            else
                return IValueConstants.STRING_FALSE;
        }
        catch ( Exception e )
        {
            return IValueConstants.STRING_TRUE;
        }
    }

    public DuoSimplePoolWebComponent getDuoSimplePoolWebComponent()
    {
        return duoSimplePoolWebComponent;
    }

    public void setDuoSimplePoolWebComponent( DuoSimplePoolWebComponent duoSimplePoolWebComponent )
    {
        this.duoSimplePoolWebComponent = duoSimplePoolWebComponent;
    }

    public DuoDoublePoolWebComponent getDuoDoublePoolWebComponent()
    {
        return duoDoublePoolWebComponent;
    }

    public void setDuoDoublePoolWebComponent( DuoDoublePoolWebComponent duoDoublePoolWebComponent )
    {
        this.duoDoublePoolWebComponent = duoDoublePoolWebComponent;
    }

    public DuoKoWebComponent getDuoKoWebComponent()
    {
        return duoKoWebComponent;
    }

    public void setDuoKoWebComponent( DuoKoWebComponent duoKoWebComponent )
    {
        this.duoKoWebComponent = duoKoWebComponent;
    }

    public UserDuoclassManager getUserDuoclassManager()
    {
        return userDuoclassManager;
    }

    public void setUserDuoclassManager( UserDuoclassManager userDuoclassManager )
    {
        this.userDuoclassManager = userDuoclassManager;
    }

}
