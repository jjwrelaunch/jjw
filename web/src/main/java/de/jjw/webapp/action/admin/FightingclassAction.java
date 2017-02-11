/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightingclassAction.java
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

package de.jjw.webapp.action.admin;


import de.jjw.model.admin.Fightsystem;
import de.jjw.model.fighting.FightingDoublePoolClass;
import de.jjw.model.fighting.FightingKoClass;
import de.jjw.model.fighting.FightingSimplePoolClass;
import de.jjw.model.fighting.Fightingclass;
import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.exception.FightingclassCreateException;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.fighting.FightingclassManager;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.jsfTags.component.FightingDoublePoolWebComponent;
import de.jjw.webapp.jsfTags.component.FightingKoWebComponent;
import de.jjw.webapp.jsfTags.component.FightingSimplePoolWebComponent;

public class FightingclassAction
    extends BasePage
    implements IGlobalWebConstants
{

    private static String FIGHTINGCLASS = "fightingclass";

    private FightingclassManager fightingclassManager;

    private FightingSimplePoolWebComponent fightingSimplePoolWebComponent = null;

    private FightingDoublePoolWebComponent fightingDoublePoolWebComponent = null;

    private FightingKoWebComponent fightingKoWebComponent = null;

    private Fightingclass fightingclass = null;

    private boolean createSuccessfull = false;

    private String sexWebForPools = "";

    public String createFightingclass()
    {
        try
        {
            getFightingclassManager().createFightingclasses( new ServiceExchangeContext(
                WebExchangeContextHelper.getWebExchangeContext( getSession() ).getUserId() ) );
            createSuccessfull = true;
        }
        catch ( FightingclassCreateException e )
        {
            addErrorElement( new ErrorElement( ADMIN_FIGHTINGCLASS_CAN_NOT_CREATE_CLASSES ) );
        }

        return null;
    }

    public boolean isCreateSuccessfull()
    {
        return createSuccessfull;
    }

    public void setFightingclassManager( FightingclassManager fightingclassManager )
    {
        this.fightingclassManager = fightingclassManager;
    }

    public void setFightingclass( Fightingclass fightingclass )
    {
        this.fightingclass = fightingclass;
        // if (null ==fightingSimplePoolWebComponent)
        // fightingSimplePoolWebComponent = new
        // FightingSimplePoolWebComponent();
        // fightingSimplePoolWebComponent.setFightingclass((FightingSimplePoolClass)
        // fightingclass);
        // getRequest().setAttribute("test", fightingSimplePoolWebComponent);
    }

    public Fightingclass getFightingclass()
    {

        if ( getRequest().getParameter( HTML_PARAM_FIGHTINGCLASS_ID ) == null )
        {
            // error missing parameter
            return null;
        }
        else
        {
            Long fightingclassId = null;
            fightingclassId = Long.valueOf( getRequest().getParameter( HTML_PARAM_FIGHTINGCLASS_ID ) );
            try
            {
                switch ( getFightingclassManager().getFightsystemOfFightingclass( fightingclassId ) )
                {
                    case Fightsystem.SIMPLE_POOL:
                        if ( fightingclass == null )
                        {
                            if ( getRequest().getAttribute( FIGHTINGCLASS ) == null )
                            {
                                setFightingclass( getFightingclassManager().getFightingclassSimplePool( fightingclassId ) );
                                getRequest().setAttribute( FIGHTINGCLASS, fightingclass );
                            }
                            else
                                setFightingclass( (Fightingclass) getRequest().getAttribute( FIGHTINGCLASS ) );
                        }
                        setFightingSimplePoolWebComponent( new FightingSimplePoolWebComponent() );
                        getFightingSimplePoolWebComponent().setFightingclass( (FightingSimplePoolClass) fightingclass );

                        break;
                    case Fightsystem.DOUBLE_POOL:
                        if ( fightingclass == null )
                        {
                            if ( getRequest().getAttribute( FIGHTINGCLASS ) == null )
                            {
                                setFightingclass( getFightingclassManager().getFightingclassDoublePool( fightingclassId ) );
                                getRequest().setAttribute( FIGHTINGCLASS, fightingclass );
                            }
                            else
                                setFightingclass( (Fightingclass) getRequest().getAttribute( FIGHTINGCLASS ) );
                        }
                        setFightingDoublePoolWebComponent( new FightingDoublePoolWebComponent() );
                        getFightingDoublePoolWebComponent().setFightingclass( (FightingDoublePoolClass) fightingclass );
                        break;

                    case Fightsystem.DOUBLE_KO:
                        if ( fightingclass == null )
                        {
                            if ( getRequest().getAttribute( FIGHTINGCLASS ) == null )
                            {
                                setFightingclass( getFightingclassManager().getFightingclassKo( fightingclassId ) );
                                getRequest().setAttribute( FIGHTINGCLASS, fightingclass );
                            }
                            else
                                setFightingclass( (Fightingclass) getRequest().getAttribute( FIGHTINGCLASS ) );
                        }
                        setFightingKoWebComponent( new FightingKoWebComponent() );
                        getFightingKoWebComponent().setFightingclass( (FightingKoClass) fightingclass );
                        break;
                }
                sexWebForPools =
                    CodestableMain.getInstance().getCodestableCodeValueByType( TYPE_SEX, fightingclass.getSex(),
                                                                               getLocale() );
            }
            catch ( JJWManagerException e )
            {
                addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
                return null;
            }

        }
        return fightingclass;
    }

    public String getSexWebForPools()
    {
        return sexWebForPools;
    }

    public FightingSimplePoolWebComponent getFightingSimplePoolWebComponent()
    {
        return fightingSimplePoolWebComponent;
    }

    public void setFightingSimplePoolWebComponent( FightingSimplePoolWebComponent fightingSimplePoolWebComponent )
    {
        this.fightingSimplePoolWebComponent = fightingSimplePoolWebComponent;
    }


    public FightingDoublePoolWebComponent getFightingDoublePoolWebComponent()
    {
        return fightingDoublePoolWebComponent;
    }

    public void setFightingDoublePoolWebComponent( FightingDoublePoolWebComponent fightingDoublePoolWebComponent )
    {
        this.fightingDoublePoolWebComponent = fightingDoublePoolWebComponent;
    }

    public FightingclassManager getFightingclassManager()
    {
        if ( fightingclassManager == null )
        {
            log.warn( "keine fightingclassManager Injection" );
        }
        return fightingclassManager;
    }

    public FightingKoWebComponent getFightingKoWebComponent()
    {
        return fightingKoWebComponent;
    }

    public void setFightingKoWebComponent( FightingKoWebComponent fightingKoWebComponent )
    {
        this.fightingKoWebComponent = fightingKoWebComponent;
    }
}
