/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaclassAction.java
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

package de.jjw.webapp.action.off.newa;


import de.jjw.model.admin.Fightsystem;
import de.jjw.model.newa.NewaDoublePoolClass;
import de.jjw.model.newa.NewaKoClass;
import de.jjw.model.newa.NewaSimplePoolClass;
import de.jjw.model.newa.Newaclass;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.newa.NewaclassManager;
import de.jjw.service.newa.UserNewaclassManager;
import de.jjw.util.IValueConstants;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.jsfTags.component.newa.NewaDoublePoolWebComponent;
import de.jjw.webapp.jsfTags.component.newa.NewaKoWebComponent;
import de.jjw.webapp.jsfTags.component.newa.NewaSimplePoolWebComponent;

public class NewaclassAction
    extends BasePage
    implements IGlobalWebConstants
{

    private static String NEWACLASS = "newaclass";

    private NewaclassManager newaclassManager;

    public static final String CREATE_SUCCESFULL = "createSuccesfull";

    private NewaSimplePoolWebComponent newaSimplePoolWebComponent = null;

    private NewaDoublePoolWebComponent newaDoublePoolWebComponent = null;

    private NewaKoWebComponent newaKoWebComponent = null;

    private Newaclass newaclass = null;

    private UserNewaclassManager userNewaclassManager = null;

    public void setNewaclassManager( NewaclassManager newaclassManager )
    {
        this.newaclassManager = newaclassManager;
    }

    public void setNewaclass( Newaclass newaclass )
    {
        this.newaclass = newaclass;
    }

    public Newaclass getNewaclass()
    {

        if ( getRequest().getParameter( HTML_PARAM_NEWACLASS_ID ) == null )
        {
            // error missing parameter
            return null;
        }
        else
        {
            Long newaclassId = null;
            newaclassId = Long.valueOf( getRequest().getParameter( HTML_PARAM_NEWACLASS_ID ) );
            try
            {
                switch ( getNewaclassManager().getFightsystemOfNewaclass( newaclassId ) )
                {
                    case Fightsystem.SIMPLE_POOL:
                        if ( newaclass == null )
                        {
                            if ( getRequest().getAttribute( NEWACLASS ) == null )
                            {
                                setNewaclass( getNewaclassManager().getNewaclassSimplePool( newaclassId ) );
                                getRequest().setAttribute( NEWACLASS, newaclass );
                            }
                            else
                                setNewaclass( (Newaclass) getRequest().getAttribute( NEWACLASS ) );
                        }
                        setNewaSimplePoolWebComponent( new NewaSimplePoolWebComponent() );
                        getNewaSimplePoolWebComponent().setNewaclass( (NewaSimplePoolClass) newaclass );

                        break;
                    case Fightsystem.DOUBLE_POOL:
                        if ( newaclass == null )
                        {
                            if ( getRequest().getAttribute( NEWACLASS ) == null )
                            {
                                setNewaclass( getNewaclassManager().getNewaclassDoublePool( newaclassId ) );
                                getRequest().setAttribute( NEWACLASS, newaclass );
                            }
                            else
                                setNewaclass( (Newaclass) getRequest().getAttribute( NEWACLASS ) );
                        }
                        setNewaDoublePoolWebComponent( new NewaDoublePoolWebComponent() );
                        getNewaDoublePoolWebComponent().setNewaclass( (NewaDoublePoolClass) newaclass );
                        break;

                    case Fightsystem.DOUBLE_KO:
                        if ( newaclass == null )
                        {
                            if ( getRequest().getAttribute( NEWACLASS ) == null )
                            {
                                setNewaclass( getNewaclassManager().getNewaclassKo( newaclassId ) );
                                getRequest().setAttribute( NEWACLASS, newaclass );
                            }
                            else
                                setNewaclass( (Newaclass) getRequest().getAttribute( NEWACLASS ) );
                        }
                        setNewaKoWebComponent( new NewaKoWebComponent() );
                        getNewaKoWebComponent().setNewaclass( (NewaKoClass) newaclass );
                        break;
                }
            }
            catch ( JJWManagerException e )
            {
                addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
                return null;
            }

        }
        return newaclass;
    }

    /**
     * calculate if the user can edit the pool
     * 
     * @return
     */
    public String getReadOnly()
    {
        try
        {
            if ( !userNewaclassManager.isAccessForTatamiUser( getCtx().getUserId(), getNewaclass().getId() ) )
                return IValueConstants.STRING_TRUE;
            else
                return IValueConstants.STRING_FALSE;
        }
        catch ( Exception e )
        {
            return IValueConstants.STRING_TRUE;
        }
    }

    public NewaSimplePoolWebComponent getNewaSimplePoolWebComponent()
    {
        return newaSimplePoolWebComponent;
    }

    public void setNewaSimplePoolWebComponent( NewaSimplePoolWebComponent newaSimplePoolWebComponent )
    {
        this.newaSimplePoolWebComponent = newaSimplePoolWebComponent;
    }

    public NewaDoublePoolWebComponent getNewaDoublePoolWebComponent()
    {
        return newaDoublePoolWebComponent;
    }

    public void setNewaDoublePoolWebComponent( NewaDoublePoolWebComponent newaDoublePoolWebComponent )
    {
        this.newaDoublePoolWebComponent = newaDoublePoolWebComponent;
    }

    public NewaclassManager getNewaclassManager()
    {
        if ( newaclassManager == null )
        {
            log.warn( "keine newaclassManager Injection" );
        }
        return newaclassManager;
    }

    public NewaKoWebComponent getNewaKoWebComponent()
    {
        return newaKoWebComponent;
    }

    public void setNewaKoWebComponent( NewaKoWebComponent newaKoWebComponent )
    {
        this.newaKoWebComponent = newaKoWebComponent;
    }

    public UserNewaclassManager getUserNewaclassManager()
    {
        return userNewaclassManager;
    }

    public void setUserNewaclassManager( UserNewaclassManager userNewaclassManager )
    {
        this.userNewaclassManager = userNewaclassManager;
    }

}
