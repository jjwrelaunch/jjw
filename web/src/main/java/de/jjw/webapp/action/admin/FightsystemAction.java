/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightsystemAction.java
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

package de.jjw.webapp.action.admin;

import de.jjw.model.admin.Fightsystem;
import de.jjw.service.admin.FightsystemManager;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.action.validation.admin.FightsystemValidator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class FightsystemAction
    extends BasePage
    implements Serializable, IGlobalWebConstants
{

    private static String ALL_FIGHTSYSTEMS = "allFightsystems";

    FightsystemManager fightsystemManager;

    List<Fightsystem> fightsystems;

    Fightsystem fightsystemPool = null;

    Fightsystem fightsystemDPool = null;

    Fightsystem fightsystemKo = null;

    public List<Fightsystem> getFightsystems()
    {
        if ( fightsystemManager == null )
        {
            log.warn( "keine fightsystemManager Injection" );
        }
        if ( getRequest().getAttribute( ALL_FIGHTSYSTEMS ) == null )
        {
            fightsystems = fightsystemManager.getAllFightsystems();
            getRequest().setAttribute( ALL_FIGHTSYSTEMS, ALL_FIGHTSYSTEMS );
        }

        if ( fightsystemPool == null )
        {
            fightsystemPool = fightsystems.get( 0 );
        }
        if ( fightsystemDPool == null )
        {
            fightsystemDPool = fightsystems.get( 1 );
        }
        if ( fightsystemKo == null )
        {
            fightsystemKo = fightsystems.get( 2 );
        }

        return fightsystems;
    }

    public String save()
    {

        Vector<ErrorElement> errors = new Vector<ErrorElement>();
        if ( !FightsystemValidator.isRequiredFieldsValid( fightsystemPool, fightsystemDPool, fightsystemKo, errors ) )
        {
            setErrorMessageVector( errors );
            return null;
        }

        FightsystemValidator.isRequiredFieldsValid( fightsystemPool, fightsystemDPool, fightsystemKo, errors );
        List<Fightsystem> fightsystemList = new ArrayList<Fightsystem>();
        fightsystemList.add( fightsystemPool );
        fightsystemList.add( fightsystemDPool );
        fightsystemList.add( fightsystemKo );

        fightsystemManager.saveFightsystems( WebExchangeContextHelper.createServiceExchangeContext( getSession() ),
                                             fightsystemList );

        return SUCCESS;
    }


    /**
     * @return Returns the fightsystemManager.
     */
    public FightsystemManager getFightsystemManager()
    {
        return fightsystemManager;
    }

    /**
     * @param fightsystemManager The fightsystemManager to set.
     */
    public void setFightsystemManager( FightsystemManager fightsystemManager )
    {
        this.fightsystemManager = fightsystemManager;
    }

    public Fightsystem getFightsystemPool()
    {
        if ( fightsystemPool == null )
        {
            getFightsystems();
        }
        return fightsystemPool;
    }

    public void setFightsystemPool( Fightsystem fightsystemPool )
    {
        this.fightsystemPool = fightsystemPool;
    }

    public Fightsystem getFightsystemDPool()
    {
        if ( fightsystemDPool == null )
        {
            getFightsystems();
        }
        return fightsystemDPool;
    }

    public void setFightsystemDPool( Fightsystem fightsystemDPool )
    {
        this.fightsystemDPool = fightsystemDPool;
    }

    public Fightsystem getFightsystemKo()
    {
        if ( fightsystemKo == null )
        {
            getFightsystems();
        }
        return fightsystemKo;
    }

    public void setFightsystemKo( Fightsystem fightsystemKo )
    {
        this.fightsystemKo = fightsystemKo;
    }


}
