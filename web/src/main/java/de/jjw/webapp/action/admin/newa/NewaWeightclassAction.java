/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaWeightclassAction.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:41
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
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.faces.model.SelectItem;

import org.springframework.dao.DataIntegrityViolationException;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.admin.Age;
import de.jjw.model.generalhelper.Codestable;
import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.model.newa.Newaclass;
import de.jjw.service.admin.AgeManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.exception.NewaWeightclassChildParentDeleteException;
import de.jjw.service.exception.NewaWeightclassInUseException;
import de.jjw.service.modelWeb.NewaclassWeb;
import de.jjw.service.newa.NewaFighterManager;
import de.jjw.service.newa.NewaWeightclassManager;
import de.jjw.service.newa.NewaclassManager;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.action.validation.newa.NewaWeightclassValidator;
import de.jjw.webapp.constants.admin.INewaWeightclassConstants;


public class NewaWeightclassAction
    extends BasePage
    implements Serializable, IGlobalWebConstants, INewaWeightclassConstants
{

    private static String NEW_NEWA_WEIGHTCLASS = "newNewaWeightclass";

    private static String EDIT_NEWA_WEIGHTCLASS = "editNewaWeightclass";

    private static String ALL_NEWA_WEIGHTCLASS = "allNewaWeightclasses";
    
    private static String COMBINE_NEWA_WEIGHTCLASS = "combineNewaWeightclass";

    private static String ALL_AGE = "allAge";

    private static String AUTO_WEIGHTCLASS = "automaticWeightclass";
    
    private static String  CREATE_LIST ="createNewaclass";

    NewaWeightclassManager newaWeightclassManager;

    NewaclassManager newaclassManager;

    NewaFighterManager newaFighterManager;

    AgeManager ageManager;

    Newaclass newaclass = new Newaclass();

    List<NewaclassWeb> newaclasses;

    List<NewaclassWeb> newaclassesByAge;

    List<Age> ages;

    Age automaticAge = new Age();

    String id;  // used for automatic class

    public List<NewaclassWeb> getNewaWeightclasses()
    {
        if ( newaWeightclassManager == null )
        {
            log.warn( "keine newaweightclassManager Injection" );
        }
        if ( getRequest().getAttribute( ALL_NEWA_WEIGHTCLASS ) == null )
        {
            newaclasses = newaWeightclassManager.getAllWeightclasses( getLocale() );
            getRequest().setAttribute( ALL_NEWA_WEIGHTCLASS, ALL_NEWA_WEIGHTCLASS );
        }
        return newaclasses;
    }

    public List<Age> getAges()
    {
        if ( ageManager == null )
        {
            log.warn( "keine ageManager Injection" );
        }
        if ( getRequest().getAttribute( ALL_AGE ) == null )
        {
            try
            {
                ages = ageManager.getAllAges();
            }
            catch ( JJWManagerException e )
            {
                addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
            }
            getRequest().setAttribute( ALL_AGE, ALL_AGE );
        }
        return ages;
    }

    public String addWeightclass()
    {
        String ret = ALL_NEWA_WEIGHTCLASS;
        Vector<ErrorElement> errors = new Vector<ErrorElement>();

        if ( "change".equals( getRequest().getParameter( "main:admiNewanWeightclassAction:change" ) ) )
        {
            newaclassesByAge = newaWeightclassManager.getWeightclassByAge( newaclass.getAge(), getLocale() );
            return null;
        }

        if ( !NewaWeightclassValidator.isRequiredFieldsValid( newaclass, errors ) )
        {
            setErrorMessageVector( errors );
            newaclassesByAge = newaWeightclassManager.getWeightclassByAge( newaclass.getAge(), getLocale() );
            return null;
        }

        if ( !NewaWeightclassValidator.isBusinessLogicValid( getNewaWeightclasses(), newaclass, errors ) )
        {
            setErrorMessageVector( errors );
            newaclassesByAge = newaWeightclassManager.getWeightclassByAge( newaclass.getAge(), getLocale() );
            return null;
        }

        setTechnicalDBFieldsForCreate( newaclass );
        try
        {
            newaWeightclassManager.saveWeightclass( newaclass );

        }
        catch ( DataIntegrityViolationException e )
        {
            errors.add( new ErrorElement( FIELD_WEIGHTCLASS, NEWA_WEIGHTCLASS_EXISTS ) );
            setErrorMessageVector( errors );
            ret = null;
        }
        catch ( Exception e )
        {

        }
        setNewaclass( new Newaclass() );
        return ret;
    }


    public String editWeightclass()
    {
        String ret = ALL_NEWA_WEIGHTCLASS;

        if ( "change".equals( getRequest().getParameter( "main:adminNewaWeightclassAction:change" ) ) )
        {
            newaclassesByAge = newaWeightclassManager.getWeightclassByAge( newaclass.getAge(), getLocale() );
            return null;
        }

        Vector<ErrorElement> errors = new Vector<ErrorElement>();
        try
        {
            if ( !newaFighterManager.getFighterByNewaclass( newaclass.getId(), getLocale(), false ).isEmpty() )
            {
                // if there are fighter in this class than only the description can be change
                Newaclass fc = newaWeightclassManager.getWeightclass( newaclass.getId() );
                if ( !fc.equalsAgeSexWeights( newaclass ) )
                {
                    errors.add( new ErrorElement( NEWA_WEIGHTCLASS_EDIT_WEIGHTCLASS ) );
                    setErrorMessageVector( errors );
                    newaclassesByAge = newaWeightclassManager.getWeightclassByAge( newaclass.getAge(), getLocale() );
                    return null;
                }
            }

            if ( !NewaWeightclassValidator.isRequiredFieldsValid( newaclass, errors ) )
            {
                setErrorMessageVector( errors );
                newaclassesByAge = newaWeightclassManager.getWeightclassByAge( newaclass.getAge(), getLocale() );
                return null;
            }

            setTechnicalDBFieldsForUpdate( newaclass );

            newaWeightclassManager.saveWeightclass( newaclass );
        }
        catch ( DataIntegrityViolationException e )
        {
            errors.add( new ErrorElement( FIELD_WEIGHTCLASS, NEWA_WEIGHTCLASS_EXISTS ) );
            setErrorMessageVector( errors );
            ret = null;
        }
        catch ( OptimisticLockingException e )
        {
            errors.add( new ErrorElement( GLO_OPTIMISTIC_LOCKING ) );
            setErrorMessageVector( errors );
            ret = null;
        }
        catch ( Exception e )
        {
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
            ret = null;
        }
        setNewaclass( new Newaclass() );
        return ret;
    }

    public String deleteWeightclass()
    {
        String ret = SUCCESS;

        try
        {
            newaWeightclassManager.removeWeightclass( (Newaclass) dataTable.getRowData() );
        }
        catch ( NewaWeightclassInUseException e )
        {
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( FIELD_WEIGHTCLASS, NEWA_WEIGHTCLASS_IN_USE ) );
            setErrorMessageVector( errors );
            return null;
        }
        catch ( JJWManagerException e )
        {
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR) );            
            setErrorMessageVector( errors );
            return null;
        }
        catch ( NewaWeightclassChildParentDeleteException e )
        {
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( NEWA_WEIGHTCLASS_CHILD_PARENT ) );
            setErrorMessageVector( errors );
            return null;
        }
        return ret;
    }

    public String gotoNewWeightclass()
    {
    	try {
			if(ageManager.getAllAges().isEmpty()){
                addErrorElement( new ErrorElement( NEWA_WEIGHTCLASS_NO_AGE ) );
				return null;
			}
		} catch (JJWManagerException e) {
			addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
			return null;
		}
    	
        getRequest().setAttribute( WEB_ADMIN_WEIGHTCLASS_NEW, WEB_ADMIN_WEIGHTCLASS_NEW );
        return NEW_NEWA_WEIGHTCLASS;
    }

    public String gotoEditWeightclass()
    {
        setNewaclass( (Newaclass) dataTable.getRowData() );
        getRequest().setAttribute( FIRST_AGE_ENTRY, ( (Newaclass) dataTable.getRowData() ).getAge() );
        return EDIT_NEWA_WEIGHTCLASS;
    }

    public String gotoCombineWeightclass()
    {
        getRequest().setAttribute( WEB_ADMIN_WEIGHTCLASS_NEW, ( (Newaclass) dataTable.getRowData() ).getId() );
        return COMBINE_NEWA_WEIGHTCLASS;
    }
    
    public String gotoAllWeightclasses()
    {
        return ALL_NEWA_WEIGHTCLASS;
    }

    public String gotoCreateNewaFightingList()
    {
        return CREATE_LIST;
    }
    
    public String gotoAutoWeightclass()
    {
        try
        {
            if ( newaclassManager.getAgesForAutomaticNewaclassCreation() == null )
            {
                addErrorElement( new ErrorElement( NEWA_WEIGHTCLASS_CAN_NOT_CREATE_AUTO_WEIGHTCLASS ) );
                return null;
            }
        }
        catch ( JJWManagerException e )
        {
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
            return null;
        }
        return AUTO_WEIGHTCLASS;
    }

    public String autoWeightclass()
    {
        try
        {
            newaclassManager.createAutomaticNewaclasses( automaticAge.getId(),
                                                                 WebExchangeContextHelper.getWebExchangeContext(
                                                                     getSession() ).getUserId() );

        }
        catch ( JJWManagerException e )
        {
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
            return null;
        }
        return ALL_NEWA_WEIGHTCLASS;
    }

    public String sortFighterIn()
    {
        try
        {
            newaclassManager.sortNewaFighterIntoClasses(
                WebExchangeContextHelper.getWebExchangeContext( getSession() ).getUserId() );
        }
        catch ( JJWManagerException e )
        {
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
            return null;
        }
        return ALL_NEWA_WEIGHTCLASS;
    }

    public List getAgesForAutomaticFightingclassCreation()
    {
        List ret = new ArrayList();
        List<Age> ages;
        try
        {
            ages = newaclassManager.getAgesForAutomaticNewaclassCreation();

            for ( Age age : ages )
            {
                ret.add( new SelectItem( age, age.getDescription(), age.getId().toString() ) );
            }
            //getRequest().setAttribute(FIRST_AGE_ENTRY, (Age) ((SelectItem) ret.get(0)).getValue());
        }
        catch ( JJWManagerException e )
        {
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return ret;
    }

    public List getAgeListALL()
    {
        List ret = new ArrayList();

        List<Age> ages;
        try
        {
            ages = ageManager.getAllAges();

            for ( Age age : ages )
            {
                ret.add( new SelectItem( age, age.getDescription(), age.getId().toString() ) );
            }
            getRequest().setAttribute( FIRST_AGE_ENTRY, (Age) ( (SelectItem) ret.get( 0 ) ).getValue() );
        }
        catch ( JJWManagerException e )
        {
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return ret;
    }

    public List getSexList()
    {
        List ret = new ArrayList();

        List<Codestable> list =
            CodestableMain.getInstance().getCodestableCodeByTypeAndLevel( TYPE_SEX, getRequest().getLocale(), 1 );
        for ( Codestable entry : list )
        {
            ret.add( new SelectItem( entry.getId(), entry.getValue() ) );
        }
        return ret;
    }


    /**
     * @return Returns the weightclass.
     */
    public Newaclass getNewaclass()
    {
        return newaclass;
    }

    /**
     * @param weightclass The weightclass to set.
     */
    public void setNewaclass( Newaclass newaclass )
    {
        this.newaclass = newaclass;
    }

    /**
     * @param weightclassManager The weightclassManager to set.
     */
    public void setNewaWeightclassManager( NewaWeightclassManager weightclassManager )
    {
        this.newaWeightclassManager = weightclassManager;
    }

    /**
     * @param ageManager The ageManager to set.
     */
    public void setAgeManager( AgeManager ageManager )
    {
        this.ageManager = ageManager;
    }

    public List<NewaclassWeb> getNewaclassesByAge()
    {
        if ( newaclassesByAge == null )
        {
            try
            {

                if ( getRequest().getAttribute( FIRST_AGE_ENTRY ) != null )
                {
                    return newaclassesByAge =
                        newaWeightclassManager.getWeightclassByAge( (Age) getRequest().getAttribute( FIRST_AGE_ENTRY ),
                                                                getLocale() );
                }
                else if ( getAgeListALL().size() > TypeUtil.INT_DEFAULT )
                {
                    return newaclassesByAge =
                        newaWeightclassManager.getWeightclassByAge(
                        (Age) ageManager.getAllAges().get( TypeUtil.INT_DEFAULT ), getLocale() );
                }
                else
                {
                    return null;
                }
            }
            catch ( Exception e )
            {
                return null;
            }
        }
        else
        {
            return newaclassesByAge;
        }
    }

    public String getText()
    {
        return "";
    }

    public NewaFighterManager getNewaFighterManager()
    {
        return newaFighterManager;
    }

    public void setNewaFighterManager( NewaFighterManager fighterManager )
    {
        this.newaFighterManager = fighterManager;
    }

    public NewaclassManager getNewaclassManager()
    {
        return newaclassManager;
    }

    public void setNewaclassManager( NewaclassManager newaclassManager )
    {
        this.newaclassManager = newaclassManager;
    }

    public Age getAutomaticAge()
    {
        return automaticAge;
    }

    public void setAutomaticAge( Age automaticAge )
    {
        this.automaticAge = automaticAge;
    }

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }


}
