/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaclassCombineAction.java
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


package de.jjw.webapp.action.admin.newa;

import java.util.List;
import java.util.Vector;

import javax.faces.component.UIData;

import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.model.newa.Newaclass;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.modelWeb.NewaclassWeb;
import de.jjw.service.newa.NewaWeightclassManager;
import de.jjw.service.newa.NewaclassManager;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;

public class NewaclassCombineAction
    extends BasePage
    implements  IGlobalWebConstants   
{
    protected NewaclassManager newaclassManager;

    protected NewaWeightclassManager newaWeightclassManager;
 protected UIData dataTable2;
 
    private static String ALL_CHILD_NEWACLASS = "allChildNewaclasses";

    private static String ALL_COMBINABLE_NEWACLASS = "allCombinableNewaclasses";

    private static String NEWACLASS = "Newaclasses";
 
    protected NewaclassWeb newaclass = null;
 
    public NewaclassWeb getNewaclass()
    {
        if ( newaWeightclassManager == null )
     {
            log.warn( "keine newaweightclassManager Injection" );
     }
        if ( getRequest().getAttribute( NEWACLASS ) == null )
        {
            if ( null != getRequest().getAttribute( WEB_ADMIN_NEWA_WEIGHTCLASS_NEW ) )
            {
                newaclass =
                    newaWeightclassManager.getWeightclass( (Long) getRequest().getAttribute( WEB_ADMIN_NEWA_WEIGHTCLASS_NEW ) );
                newaclass.setSexWeb( CodestableMain.getInstance().getCodestableCodeValueByType( TYPE_SEX,
                                                                                                newaclass.getSex(),
                                                                                                getLocale() ) );
		
                getRequest().setAttribute( NEWACLASS, NEWACLASS );
            }
        else
        {
                if ( newaclass == null )
            {
                    newaclass =
                        newaWeightclassManager.getWeightclass( Long.valueOf( getRequest().getParameter( "main:adminNewaclassCombineAction:id" ) ) );
                    newaclass.setSexWeb( CodestableMain.getInstance().getCodestableCodeValueByType( TYPE_SEX,
                                                                                                    newaclass.getSex(),
                                                                                                        getLocale() ) );
                    getRequest().setAttribute( NEWACLASS, NEWACLASS );
                }
            }
        }
        return newaclass;
 }
 
 
    public List<NewaclassWeb> getChildclasses()
    {
        List<NewaclassWeb> newaclasses = null;
        if ( newaclassManager == null )
        {
            log.warn( "keine newaclassManager Injection" );
        }
        if ( getRequest().getAttribute( ALL_CHILD_NEWACLASS ) == null )
        {
            try
            {
                newaclasses = newaclassManager.getChildNewaclasses( getNewaclass().getId(), getLocale() );
            }
            catch ( JJWManagerException e )
            {
                addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
                return null;
            }
            getRequest().setAttribute( ALL_CHILD_NEWACLASS, newaclasses );
        }
        return (List<NewaclassWeb>) getRequest().getAttribute( ALL_CHILD_NEWACLASS );
    }
 
 
    public List<NewaclassWeb> getCombinableNewaclasses()
    {
        List<NewaclassWeb> fightingclasses = null;
        if ( newaclassManager == null )
        {
            log.warn( "keine newaclassManager Injection" );
        }
        if ( getRequest().getAttribute( ALL_COMBINABLE_NEWACLASS ) == null )
        {
            try
            {
                fightingclasses = newaclassManager.getCombinableNewaclasses( getNewaclass().getId(), getLocale() );

            }
            catch ( JJWManagerException e )
            {
                addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
                return null;
            }
            getRequest().setAttribute( ALL_COMBINABLE_NEWACLASS, fightingclasses );
        }
        return (List<NewaclassWeb>) getRequest().getAttribute( ALL_COMBINABLE_NEWACLASS );
    }

 
 
 public boolean isRendergetChildclasses(){
        if ( getCombinableNewaclasses() == null || getChildclasses().isEmpty() )
		 return false;
	 else return true;
 }
 
    public boolean isRenderCombinableNewaclasses()
    {
        if ( getCombinableNewaclasses() == null || getCombinableNewaclasses().isEmpty() )
		 return false;
	 else return true;
 }
 
 
    public String addNewaclassToClass()
    {
	 Vector<ErrorElement> errors = new Vector<ErrorElement>();
        try
        {
            if ( ( (Newaclass) getDataTable2().getRowData() ).isDeleteStop() || getNewaclass().isDeleteStop() )
            {
                errors.add( new ErrorElement( NEWA_WEIGHTCLASS_NOT_COMBINABLE ) );
                setErrorMessageVector( errors );
                return null;
            }
            newaclassManager.addNewaclassToClass( getNewaclass().getId(),
                                                  ( (Newaclass) getDataTable2().getRowData() ).getId() );
        }
        catch ( JJWManagerException e )
        {
		errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
        setErrorMessageVector( errors );        
	}	 
        getRequest().removeAttribute( ALL_COMBINABLE_NEWACLASS );
        getRequest().removeAttribute( ALL_CHILD_NEWACLASS );
        return "combineWeightclass";
 }
 
    public String removeNewaclassFromParent()
    {
	 Vector<ErrorElement> errors = new Vector<ErrorElement>();
	 try {
            if ( ( (Newaclass) getDataTable().getRowData() ).isDeleteStop() ||
					getNewaclass().isDeleteStop()){
			 errors.add( new ErrorElement( FIGHTING_WEIGHTCLASS_NOT_REMOVEABLE_FROM_PARENT ) );
                setErrorMessageVector( errors );
                return null;
		 }		 
            newaclassManager.removeNewaclassFromParent( getNewaclass().getId(),
                                                            ( (Newaclass) getDataTable().getRowData() ).getId() );
            getRequest().removeAttribute( ALL_CHILD_NEWACLASS );
            getRequest().removeAttribute( ALL_COMBINABLE_NEWACLASS );
	} catch (JJWManagerException e) {		
		errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
        setErrorMessageVector( errors );        
	}	 
	 return null;
 }
 
    public NewaclassManager getNewaclassManager()
    {
        return newaclassManager;
}

    public void setNewaclassManager( NewaclassManager newaclassManager )
    {
        this.newaclassManager = newaclassManager;
}


    public NewaWeightclassManager getNewaWeightclassManager()
    {
        return newaWeightclassManager;
}


    public void setNewaWeightclassManager( NewaWeightclassManager newaWeightclassManager )
    {
        this.newaWeightclassManager = newaWeightclassManager;
}


    public void setNewaclassWeb( NewaclassWeb newaclass )
    {
        this.newaclass = newaclass;
}


public UIData getDataTable2() {
	return dataTable2;
}


public void setDataTable2(UIData dataTable2) {
	this.dataTable2 = dataTable2;
}
 
}