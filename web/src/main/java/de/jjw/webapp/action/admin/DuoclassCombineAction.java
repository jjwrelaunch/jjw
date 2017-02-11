/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoclassCombineAction.java
 * Created : 06 Jan 2011
 * Last Modified: Thu, 06 Jan 2011 20:55:41
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

import java.util.List;
import java.util.Vector;

import javax.faces.component.UIData;

import de.jjw.model.duo.Duoclass;
import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.service.duo.DuoclassManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.modelWeb.DuoclassWeb;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;

public class DuoclassCombineAction
    extends BasePage
    implements IGlobalWebConstants
{
    protected DuoclassManager duoclassManager;

    protected UIData dataTable2;

    private static String ALL_CHILD_DUOCLASS = "allChildDuoclasses";

    private static String ALL_COMBINABLE_DUOCLASS = "allCombinableDuoclasses";

    private static String DUOCLASS = "Duoclasses";

    protected DuoclassWeb duoclass = null;

    public DuoclassWeb getDuoclass()
    {
        Vector<ErrorElement> errors = new Vector<ErrorElement>();
        if ( duoclassManager == null )
        {
            log.warn( "keine duoclassManager Injection" );
        }
        try
        {
            if ( getRequest().getAttribute( DUOCLASS ) == null )
            {
                if ( null != getRequest().getAttribute( WEB_ADMIN_DUOCLASS_NEW ) )
                {
                    duoclass = duoclassManager.getDuoclass( (Long) getRequest().getAttribute( WEB_ADMIN_DUOCLASS_NEW ) );
                    duoclass.setSexWeb( CodestableMain.getInstance().getCodestableCodeValueByType( TYPE_SEX,
                                                                                                   duoclass.getSex(),
                                                                                                   getLocale() ) );
                    getRequest().setAttribute( DUOCLASS, DUOCLASS );
                }
                else
                {
                    if ( duoclass == null )
                    {
                        duoclass =
                            duoclassManager.getDuoclass( Long.valueOf( getRequest().getParameter( "main:adminDuoclassCombineAction:id" ) ) );
                        duoclass.setSexWeb( CodestableMain.getInstance().getCodestableCodeValueByType( TYPE_SEX,
                                                                                                       duoclass.getSex(),
                                                                                                       getLocale() ) );
                        getRequest().setAttribute( DUOCLASS, DUOCLASS );
                    }
                }
            }
        }
        catch ( JJWManagerException e )
        {
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
        }
        return duoclass;
    }

    public List<DuoclassWeb> getChildclasses()
    {
        List<DuoclassWeb> duoclasses = null;
        if ( duoclassManager == null )
        {
            log.warn( "keine duoclassManager Injection" );
        }
        if ( getRequest().getAttribute( ALL_CHILD_DUOCLASS ) == null )
        {
            try
            {
                duoclasses = duoclassManager.getChildDuoclasses( getDuoclass().getId(), getLocale() );
            }
            catch ( JJWManagerException e )
            {
                addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
                return null;
            }
            getRequest().setAttribute( ALL_CHILD_DUOCLASS, duoclasses );
        }
        return (List<DuoclassWeb>) getRequest().getAttribute( ALL_CHILD_DUOCLASS );
    }

    public List<DuoclassWeb> getCombinableDuoclasses()
    {
        List<DuoclassWeb> duoclasses = null;
        if ( duoclassManager == null )
        {
            log.warn( "keine duoclassManager Injection" );
        }
        if ( getRequest().getAttribute( ALL_COMBINABLE_DUOCLASS ) == null )
        {
            try
            {
                duoclasses = duoclassManager.getCombinableDuoclasses( getDuoclass().getId(), getLocale() );

            }
            catch ( JJWManagerException e )
            {
                addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
                return null;
            }
            getRequest().setAttribute( ALL_COMBINABLE_DUOCLASS, duoclasses );
        }
        return (List<DuoclassWeb>) getRequest().getAttribute( ALL_COMBINABLE_DUOCLASS );
    }

    public boolean isRendergetChildclasses()
    {
        if ( getCombinableDuoclasses() == null || getChildclasses().isEmpty() )
            return false;
        else
            return true;
    }

    public boolean isRenderCombinableDuoclasses()
    {
        if ( getCombinableDuoclasses() == null || getCombinableDuoclasses().isEmpty() )
            return false;
        else
            return true;
    }

    public String addDuoclassToClass()
    {
        Vector<ErrorElement> errors = new Vector<ErrorElement>();
        try
        {
            if ( ( (Duoclass) getDataTable2().getRowData() ).isDeleteStop() || getDuoclass().isDeleteStop() )
            {
                errors.add( new ErrorElement( FIGHTING_WEIGHTCLASS_NOT_COMBINABLE ) );
                setErrorMessageVector( errors );
                return null;
            }
            duoclassManager.addDuoclassToClass( getDuoclass().getId(),
                                                ( (Duoclass) getDataTable2().getRowData() ).getId() );
        }
        catch ( JJWManagerException e )
        {
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
        }
        getRequest().removeAttribute( ALL_COMBINABLE_DUOCLASS );
        getRequest().removeAttribute( ALL_CHILD_DUOCLASS );
        return "combineDuoclass";
    }

    public String removeDuoclassFromParent()
    {
        Vector<ErrorElement> errors = new Vector<ErrorElement>();
        try
        {
            if ( ( (Duoclass) getDataTable().getRowData() ).isDeleteStop() || getDuoclass().isDeleteStop() )
            {
                errors.add( new ErrorElement( FIGHTING_WEIGHTCLASS_NOT_REMOVEABLE_FROM_PARENT ) );
                setErrorMessageVector( errors );
                return null;
            }
            duoclassManager.removeDuoclassFromParent( getDuoclass().getId(),
                                                      ( (Duoclass) getDataTable().getRowData() ).getId() );
            getRequest().removeAttribute( ALL_CHILD_DUOCLASS );
            getRequest().removeAttribute( ALL_COMBINABLE_DUOCLASS );
        }
        catch ( JJWManagerException e )
        {
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
        }
        return null;
    }

    public DuoclassManager getDuoclassManager()
    {
        return duoclassManager;
    }

    public void setDuoclassManager( DuoclassManager duoclassManager )
    {
        this.duoclassManager = duoclassManager;
    }

    public void setDuoclassWeb( DuoclassWeb duoclass )
    {
        this.duoclass = duoclass;
    }

    public UIData getDataTable2()
    {
        return dataTable2;
    }

    public void setDataTable2( UIData dataTable2 )
    {
        this.dataTable2 = dataTable2;
    }

}
