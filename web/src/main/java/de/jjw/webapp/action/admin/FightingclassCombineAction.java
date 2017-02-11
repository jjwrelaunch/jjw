/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightingclassCombineAction.java
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

import de.jjw.model.fighting.Fightingclass;
import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.fighting.FightingclassManager;
import de.jjw.service.fighting.WeightclassManager;
import de.jjw.service.modelWeb.FightingclassWeb;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;

public class FightingclassCombineAction  extends BasePage
    implements  IGlobalWebConstants   
{
 protected  FightingclassManager fightingclassManager;
 protected WeightclassManager weightclassManager ;
 protected UIData dataTable2;
 
 private static String ALL_CHILD_FIGHTINGCLASS = "allChildFightingclasses";
 private static String ALL_COMBINABLE_FIGHTINGCLASS = "allCombinableFightingclasses";
 private static String FIGHTINGCLASS = "Fightingclasses";
 
    protected FightingclassWeb fightingclass = null;
 
 public FightingclassWeb getFightingclass(){
	 if ( weightclassManager == null )
     {
         log.warn( "keine weightclassManager Injection" );
     }
     if ( getRequest().getAttribute( FIGHTINGCLASS ) == null )
        {
            if ( null != getRequest().getAttribute( WEB_ADMIN_WEIGHTCLASS_NEW ) )
            {
			fightingclass= weightclassManager.getWeightclass(
					 (Long)getRequest().getAttribute(WEB_ADMIN_WEIGHTCLASS_NEW) );
			fightingclass.setSexWeb(
	                CodestableMain.getInstance().getCodestableCodeValueByType( TYPE_SEX, fightingclass.getSex(), getLocale() ) );
		
                getRequest().setAttribute( FIGHTINGCLASS, FIGHTINGCLASS );
            }
        else
        {
            if ( fightingclass == null )
            {
            fightingclass =
                weightclassManager.getWeightclass( Long.valueOf( getRequest().getParameter( "main:adminFightingclassCombineAction:id" ) ) );
            fightingclass.setSexWeb( CodestableMain.getInstance().getCodestableCodeValueByType( TYPE_SEX,
                                                                                                fightingclass.getSex(),
                                                                                                        getLocale() ) );
            getRequest().setAttribute( FIGHTINGCLASS, FIGHTINGCLASS );
                }
            }
        }
     return fightingclass;
 }
 
 
 public List<FightingclassWeb> getChildclasses()
    {
        List<FightingclassWeb> fightingclasses = null;
        if ( fightingclassManager == null )
        {
            log.warn( "keine fightingclassManager Injection" );
        }
        if ( getRequest().getAttribute( ALL_CHILD_FIGHTINGCLASS ) == null )
        {
            try
            {
                fightingclasses =
                    fightingclassManager.getChildFightingclasses( getFightingclass().getId(), getLocale() );
            }
            catch ( JJWManagerException e )
            {
                addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
                return null;
            }
            getRequest().setAttribute( ALL_CHILD_FIGHTINGCLASS, fightingclasses );
        }
        return (List<FightingclassWeb>) getRequest().getAttribute( ALL_CHILD_FIGHTINGCLASS );
    }
 
 
 public List<FightingclassWeb> getCombinableFightingclasses()
    {
        List<FightingclassWeb> fightingclasses = null;
        if ( fightingclassManager == null )
        {
            log.warn( "keine fightingclassManager Injection" );
        }
        if ( getRequest().getAttribute( ALL_COMBINABLE_FIGHTINGCLASS ) == null )
        {
            try
            {
                fightingclasses =
                    fightingclassManager.getCombinableFightingclasses( getFightingclass().getId(), getLocale() );

            }
            catch ( JJWManagerException e )
            {
                addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
                return null;
            }
            getRequest().setAttribute( ALL_COMBINABLE_FIGHTINGCLASS, fightingclasses );
        }
        return (List<FightingclassWeb>) getRequest().getAttribute( ALL_COMBINABLE_FIGHTINGCLASS );
    }

 
 
 public boolean isRendergetChildclasses(){
	 if (getCombinableFightingclasses() == null ||  getChildclasses().isEmpty())
		 return false;
	 else return true;
 }
 
 public boolean isRenderCombinableFightingclasses(){
	 if (getCombinableFightingclasses() == null ||  getCombinableFightingclasses().isEmpty())
		 return false;
	 else return true;
 }
 
 
 public String addFightingclassToClass(){
	 Vector<ErrorElement> errors = new Vector<ErrorElement>();
	 try {
            if ( ( (Fightingclass) getDataTable2().getRowData() ).isDeleteStop() ||
				getFightingclass().isDeleteStop() ){
			 errors.add( new ErrorElement( FIGHTING_WEIGHTCLASS_NOT_COMBINABLE ) );
                setErrorMessageVector( errors );
                return null;
		 }		 
            fightingclassManager.addFightingclassToClass( getFightingclass().getId(),
                                                          ( (Fightingclass) getDataTable2().getRowData() ).getId() );
	} catch (JJWManagerException e) {		
		errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
        setErrorMessageVector( errors );        
	}	 
        getRequest().removeAttribute( ALL_COMBINABLE_FIGHTINGCLASS );
        getRequest().removeAttribute( ALL_CHILD_FIGHTINGCLASS );
        return "combineWeightclass";
 }
 
 public String removeFightingclassFromParent(){
	 Vector<ErrorElement> errors = new Vector<ErrorElement>();
	 try {
		 if(((Fightingclass)getDataTable().getRowData()).isDeleteStop()||
					getFightingclass().isDeleteStop()){
			 errors.add( new ErrorElement( FIGHTING_WEIGHTCLASS_NOT_REMOVEABLE_FROM_PARENT ) );
                setErrorMessageVector( errors );
                return null;
		 }		 
		fightingclassManager.removeFightingclassFromParent(getFightingclass().getId(), ((Fightingclass)getDataTable().getRowData()).getId());
            getRequest().removeAttribute( ALL_CHILD_FIGHTINGCLASS );
            getRequest().removeAttribute( ALL_COMBINABLE_FIGHTINGCLASS );
	} catch (JJWManagerException e) {		
		errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
        setErrorMessageVector( errors );        
	}	 
	 return null;
 }
 
public FightingclassManager getFightingclassManager() {
	return fightingclassManager;
}

public void setFightingclassManager(FightingclassManager fightingclassManager) {
	this.fightingclassManager = fightingclassManager;
}


public WeightclassManager getWeightclassManager() {
	return weightclassManager;
}


public void setWeightclassManager(WeightclassManager weightclassManager) {
	this.weightclassManager = weightclassManager;
}


public void setFightingclassWeb(FightingclassWeb fightingclass) {
	this.fightingclass = fightingclass;
}


public UIData getDataTable2() {
	return dataTable2;
}


public void setDataTable2(UIData dataTable2) {
	this.dataTable2 = dataTable2;
}
 
}