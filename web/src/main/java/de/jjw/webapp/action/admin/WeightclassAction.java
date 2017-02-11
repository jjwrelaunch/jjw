/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : WeightclassAction.java
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

package de.jjw.webapp.action.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.faces.model.SelectItem;

import org.springframework.dao.DataIntegrityViolationException;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.admin.Age;
import de.jjw.model.fighting.Fightingclass;
import de.jjw.model.generalhelper.Codestable;
import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.service.admin.AgeManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.exception.WeightclassChildParentDeleteException;
import de.jjw.service.exception.WeightclassInUseException;
import de.jjw.service.fighting.FighterManager;
import de.jjw.service.fighting.FightingclassManager;
import de.jjw.service.fighting.WeightclassManager;
import de.jjw.service.modelWeb.FightingclassWeb;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.action.validation.fighting.WeightclassValidator;
import de.jjw.webapp.constants.admin.IWeightclassConstants;


public class WeightclassAction
    extends BasePage
    implements Serializable, IGlobalWebConstants, IWeightclassConstants
{

    private static String NEW_WEIGHTCLASS = "newWeightclass";

    private static String EDIT_WEIGHTCLASS = "editWeightclass";

    private static String ALL_WEIGHTCLASS = "allWeightclasses";
    
    private static String COMBINE_WEIGHTCLASS = "combineWeightclass";

    private static String ALL_AGE = "allAge";

    private static String AUTO_WEIGHTCLASS = "automaticWeightclass";
    
    private static String  CREATE_LIST ="createFightingclass";

    WeightclassManager weightclassManager;

    FightingclassManager fightingclassManager;

    FighterManager fighterManager;

    AgeManager ageManager;

    Fightingclass fightingclass = new Fightingclass();

    List<FightingclassWeb> fightingclasses;

    List<FightingclassWeb> fightingclassesByAge;

    List<Age> ages;

    Age automaticAge = new Age();

    String id;  // used for automatic class

    public List<FightingclassWeb> getWeightclasses()
    {
        if ( weightclassManager == null )
        {
            log.warn( "keine weightclassManager Injection" );
        }
        if ( getRequest().getAttribute( ALL_WEIGHTCLASS ) == null )
        {
            fightingclasses = weightclassManager.getAllWeightclasses( getLocale() );
            getRequest().setAttribute( ALL_WEIGHTCLASS, ALL_WEIGHTCLASS );
        }
        return fightingclasses;
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
        String ret = ALL_WEIGHTCLASS;
        Vector<ErrorElement> errors = new Vector<ErrorElement>();

        if ( "change".equals( getRequest().getParameter( "main:adminWeightclassAction:change" ) ) )
        {
            fightingclassesByAge = weightclassManager.getWeightclassByAge( fightingclass.getAge(), getLocale() );
            return null;
        }

        if ( !WeightclassValidator.isRequiredFieldsValid( fightingclass, errors ) )
        {
            setErrorMessageVector( errors );
            fightingclassesByAge = weightclassManager.getWeightclassByAge( fightingclass.getAge(), getLocale() );
            return null;
        }

        if ( !WeightclassValidator.isBusinessLogicValid( getWeightclasses(), fightingclass, errors ) )
        {
            setErrorMessageVector( errors );
            fightingclassesByAge = weightclassManager.getWeightclassByAge( fightingclass.getAge(), getLocale() );
            return null;
        }

        setTechnicalDBFieldsForCreate( fightingclass );
        try
        {
            weightclassManager.saveWeightclass( fightingclass );

        }
        catch ( DataIntegrityViolationException e )
        {
            errors.add( new ErrorElement( FIELD_WEIGHTCLASS, FIGHTING_WEIGHTCLASS_EXISTS ) );
            setErrorMessageVector( errors );
            ret = null;
        }
        catch ( Exception e )
        {

        }
        setWeightclass( new Fightingclass() );
        return ret;
    }


    public String editWeightclass()
    {
        String ret = ALL_WEIGHTCLASS;

        if ( "change".equals( getRequest().getParameter( "main:adminWeightclassAction:change" ) ) )
        {
            fightingclassesByAge = weightclassManager.getWeightclassByAge( fightingclass.getAge(), getLocale() );
            return null;
        }

        Vector<ErrorElement> errors = new Vector<ErrorElement>();
        try
        {
            if ( !fighterManager.getFighterByFightingclass( fightingclass.getId(), getLocale(), false ).isEmpty() )
            {
                // if there are fighter in this class than only the description can be change
                Fightingclass fc = weightclassManager.getWeightclass( fightingclass.getId() );
                if ( !fc.equalsAgeSexWeights( fightingclass ) )
                {
                    errors.add( new ErrorElement( FIGHTING_WEIGHTCLASS_EDIT_WEIGHTCLASS ) );
                    setErrorMessageVector( errors );
                    fightingclassesByAge =
                        weightclassManager.getWeightclassByAge( fightingclass.getAge(), getLocale() );
                    return null;
                }
            }

            if ( !WeightclassValidator.isRequiredFieldsValid( fightingclass, errors ) )
            {
                setErrorMessageVector( errors );
                fightingclassesByAge = weightclassManager.getWeightclassByAge( fightingclass.getAge(), getLocale() );
                return null;
            }

            setTechnicalDBFieldsForUpdate( fightingclass );

            weightclassManager.saveWeightclass( fightingclass );
        }
        catch ( DataIntegrityViolationException e )
        {
            errors.add( new ErrorElement( FIELD_WEIGHTCLASS, FIGHTING_WEIGHTCLASS_EXISTS ) );
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
        setWeightclass( new Fightingclass() );
        return ret;
    }

    public String deleteWeightclass()
    {
        String ret = SUCCESS;

        try
        {
            weightclassManager.removeWeightclass( (Fightingclass) dataTable.getRowData() );
        }
        catch ( WeightclassInUseException e )
        {
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( FIELD_WEIGHTCLASS, FIGHTING_WEIGHTCLASS_IN_USE ) );
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
        catch ( WeightclassChildParentDeleteException e )
        {
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement(FIGHTING_WEIGHTCLASS_CHILD_PARENT  ) );
            setErrorMessageVector( errors );
            return null;
        }
        return ret;
    }

    public String gotoNewWeightclass()
    {
    	try {
			if(ageManager.getAllAges().isEmpty()){
				addErrorElement( new ErrorElement( FIGHTING_WEIGHTCLASS_NO_AGE) );
				return null;
			}
		} catch (JJWManagerException e) {
			addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
			return null;
		}
    	
        getRequest().setAttribute( WEB_ADMIN_WEIGHTCLASS_NEW, WEB_ADMIN_WEIGHTCLASS_NEW );
        return NEW_WEIGHTCLASS;
    }

    public String gotoEditWeightclass()
    {
        setWeightclass( (Fightingclass) dataTable.getRowData() );
        getRequest().setAttribute( FIRST_AGE_ENTRY, ( (Fightingclass) dataTable.getRowData() ).getAge() );
        return EDIT_WEIGHTCLASS;
    }

    public String gotoCombineWeightclass()
    {
        getRequest().setAttribute( WEB_ADMIN_WEIGHTCLASS_NEW, ( (Fightingclass) dataTable.getRowData() ).getId() );
        return COMBINE_WEIGHTCLASS;
    }
    
    public String gotoAllWeightclasses()
    {
        return ALL_WEIGHTCLASS;
    }
    
    public String gotoCreateFightingList()
    {
        return CREATE_LIST;
    }

    public String gotoAutoWeightclass()
    {
        try
        {
            if ( fightingclassManager.getAgesForAutomaticFightingclassCreation() == null )
            {
                addErrorElement( new ErrorElement( FIGHTING_WEIGHTCLASS_CAN_NOT_CREATE_AUTO_WEIGHTCLASS ) );
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
            fightingclassManager.createAutomaticFightingclasses( automaticAge.getId(),
                                                                 WebExchangeContextHelper.getWebExchangeContext(
                                                                     getSession() ).getUserId() );

        }
        catch ( JJWManagerException e )
        {
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
            return null;
        }
        return ALL_WEIGHTCLASS;
    }

    public String sortFighterIn()
    {
        try
        {
            fightingclassManager.sortFighterIntoClasses(
                WebExchangeContextHelper.getWebExchangeContext( getSession() ).getUserId() );
        }
        catch ( JJWManagerException e )
        {
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
            return null;
        }
        return ALL_WEIGHTCLASS;
    }

    public List getAgesForAutomaticFightingclassCreation()
    {
        List ret = new ArrayList();
        List<Age> ages;
        try
        {
            ages = fightingclassManager.getAgesForAutomaticFightingclassCreation();

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
    public Fightingclass getWeightclass()
    {
        return fightingclass;
    }

    /**
     * @param weightclass The weightclass to set.
     */
    public void setWeightclass( Fightingclass fightingclass )
    {
        this.fightingclass = fightingclass;
    }

    /**
     * @param weightclassManager The weightclassManager to set.
     */
    public void setWeightclassManager( WeightclassManager weightclassManager )
    {
        this.weightclassManager = weightclassManager;
    }

    /**
     * @param ageManager The ageManager to set.
     */
    public void setAgeManager( AgeManager ageManager )
    {
        this.ageManager = ageManager;
    }

    public List<FightingclassWeb> getWeightclassesByAge()
    {
        if ( fightingclassesByAge == null )
        {
            try
            {

                if ( getRequest().getAttribute( FIRST_AGE_ENTRY ) != null )
                {
                    return fightingclassesByAge =
                        weightclassManager.getWeightclassByAge( (Age) getRequest().getAttribute( FIRST_AGE_ENTRY ),
                                                                getLocale() );
                }
                else if ( getAgeListALL().size() > TypeUtil.INT_DEFAULT )
                {
                    return fightingclassesByAge = weightclassManager.getWeightclassByAge(
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
            return fightingclassesByAge;
        }
    }

    public String getText()
    {
        return "";
    }

    public FighterManager getFighterManager()
    {
        return fighterManager;
    }

    public void setFighterManager( FighterManager fighterManager )
    {
        this.fighterManager = fighterManager;
    }

    public FightingclassManager getFightingclassManager()
    {
        return fightingclassManager;
    }

    public void setFightingclassManager( FightingclassManager fightingclassManager )
    {
        this.fightingclassManager = fightingclassManager;
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
