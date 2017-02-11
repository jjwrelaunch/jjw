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

package de.jjw.webapp.action.admin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.faces.model.SelectItem;

import org.springframework.dao.DataIntegrityViolationException;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.admin.Age;
import de.jjw.model.admin.Fightsystem;
import de.jjw.model.duo.DuoDoublePoolClass;
import de.jjw.model.duo.DuoKoClass;
import de.jjw.model.duo.DuoSimplePoolClass;
import de.jjw.model.duo.Duoclass;
import de.jjw.model.generalhelper.Codestable;
import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.admin.AgeManager;
import de.jjw.service.duo.DuoTeamManager;
import de.jjw.service.duo.DuoclassManager;
import de.jjw.service.exception.DuoclassChildParentDeleteException;
import de.jjw.service.exception.DuoclassCreateException;
import de.jjw.service.exception.DuoclassInUseException;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.modelWeb.DuoclassWeb;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.action.validation.duo.DuoclassValidator;
import de.jjw.webapp.constants.admin.IDuoclassConstants;
import de.jjw.webapp.jsfTags.component.DuoDoublePoolWebComponent;
import de.jjw.webapp.jsfTags.component.DuoKoWebComponent;
import de.jjw.webapp.jsfTags.component.DuoSimplePoolWebComponent;

public class DuoclassAction
    extends BasePage
    implements Serializable, IGlobalWebConstants, IDuoclassConstants
{

    private static String DUOCLASS = "duoclass";

    private static String NEW_DUOCLASS = "newDuoclass";

    private static String EDIT_DUOCLASS = "editDuoclass";

    private static String ALL_DUOCLASS = "allDuoclasses";

    private static String COMBINE_DUOCLASS = "combineDuoclass";
    
    private static String  CREATE_LIST ="createDuoclass";

    private static String ALL_AGE = "allAge";

    DuoclassManager duoclassManager;

    DuoTeamManager duoTeamManager;

    AgeManager ageManager;

    Duoclass duoclass = new Duoclass();

    List<DuoclassWeb> duoclasses;

    List<DuoclassWeb> duoclassesByAge;

    List<Age> ages;


    public List<DuoclassWeb> getDuoclasses()
    {
        if ( duoclassManager == null )
        {
            log.warn( "keine duoclassManager Injection" );
        }
        if ( getRequest().getAttribute( ALL_DUOCLASS ) == null )
        {
            try
            {
                duoclasses = duoclassManager.getAllDuoclasses( getLocale() );
            }
            catch ( JJWManagerException e )
            {
                addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
            }

            getRequest().setAttribute( ALL_DUOCLASS, ALL_DUOCLASS );
        }
        return duoclasses;
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

    public String addDuoclass()
    {
        String ret = ALL_DUOCLASS;
        Vector<ErrorElement> errors = new Vector<ErrorElement>();

        if ( "change".equals( getRequest().getParameter( "main:adminDuoclassAction:change" ) ) )
        {
            duoclassesByAge = duoclassManager.getDuoclassByAge( duoclass.getAge(), getLocale() );
            return null;
        }

        if ( !DuoclassValidator.isRequiredFieldsValid( duoclass, errors ) )
        {
            setErrorMessageVector( errors );
            duoclassesByAge = duoclassManager.getDuoclassByAge( duoclass.getAge(), getLocale() );
            return null;
        }

        if ( !DuoclassValidator.isBusinessLogicValid( getDuoclasses(), duoclass, errors ) )
        {
            setErrorMessageVector( errors );
            duoclassesByAge = duoclassManager.getDuoclassByAge( duoclass.getAge(), getLocale() );
            return null;
        }

        setTechnicalDBFieldsForCreate( duoclass );
        try
        {
            duoclassManager.saveDuoclass( duoclass );

        }
        catch ( DataIntegrityViolationException e )
        {
            errors.add( new ErrorElement( FIELD_DUOCLASS_NAME, DUO_DUOCLASS_EXISTS ) );
            setErrorMessageVector( errors );
            ret = null;
        }
        catch ( Exception e )
        {

        }
        setDuoclass( new Duoclass() );
        return ret;
    }


    public String editDuoclass()
    {
        String ret = ALL_DUOCLASS;
        Vector<ErrorElement> errors = new Vector<ErrorElement>();

        if ( "change".equals( getRequest().getParameter( "main:adminDuoclassAction:change" ) ) )
        {
            duoclassesByAge = duoclassManager.getDuoclassByAge( duoclass.getAge(), getLocale() );
            return null;
        }

        try
        {
            if ( !duoTeamManager.getDuoTeamByDuoclass( duoclass.getId(), getLocale(), false ).isEmpty() )
            {
                // if there are fighter in this class than only the description can be change
                Duoclass fc = duoclassManager.getDuoclass( duoclass.getId() );
                if ( !fc.equalsAgeSex( duoclass ) )
                {
                    errors.add( new ErrorElement( DUO_DUOCLASS_EDIT_DUOCLASS ) );
                    setErrorMessageVector( errors );
                    duoclassesByAge = duoclassManager.getDuoclassByAge( duoclass.getAge(), getLocale() );
                    return null;
                }
            }

            if ( !DuoclassValidator.isRequiredFieldsValid( duoclass, errors ) )
            {
                setErrorMessageVector( errors );
                duoclassesByAge = duoclassManager.getDuoclassByAge( duoclass.getAge(), getLocale() );
                return null;
            }
            if ( !DuoclassValidator.isBusinessLogicValid( getDuoclasses(), duoclass, errors ) )
            {
                setErrorMessageVector( errors );
                duoclassesByAge = duoclassManager.getDuoclassByAge( duoclass.getAge(), getLocale() );
                return null;
            }

            setTechnicalDBFieldsForUpdate( duoclass );

            duoclassManager.saveDuoclass( duoclass );
        }
        catch ( DataIntegrityViolationException e )
        {
            errors.add( new ErrorElement( FIELD_DUOCLASS_NAME, DUO_DUOCLASS_EXISTS ) );
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
            ret = null;
        }
        setDuoclass( new Duoclass() );
        return ret;
    }

    public String deleteDuoclass()
    {
        String ret = SUCCESS;

        try
        {
            duoclassManager.removeDuoclass( (Duoclass) dataTable.getRowData() );
        }
        catch ( DuoclassInUseException e )
        {
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( FIELD_DUOCLASS_NAME, DUO_DUOCLASS_IN_USE ) );
            setErrorMessageVector( errors );
            return null;
        }
        catch ( JJWManagerException e )
        {
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
            return null;
        }
        catch ( DuoclassChildParentDeleteException e )
        {
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( DUO_DUOCLASS_CHILD_PARENT ) );
            setErrorMessageVector( errors );
            return null;
        }
        return ret;
    }

    public String gotoNewDuoclass()
    {

        String ret = NEW_DUOCLASS;
        getRequest().setAttribute( WEB_ADMIN_DUOCLASS_NEW, WEB_ADMIN_DUOCLASS_NEW );
        return ret;
    }

    public String gotoEditDuoclass()
    {
        setDuoclass( (Duoclass) dataTable.getRowData() );
        getRequest().setAttribute( FIRST_AGE_ENTRY, (Age) ( (Duoclass) dataTable.getRowData() ).getAge() );
        return EDIT_DUOCLASS;
    }

    public String gotoCombineDuoclass()
    {
        getRequest().setAttribute( WEB_ADMIN_DUOCLASS_NEW, ( (Duoclass) dataTable.getRowData() ).getId() );
        return COMBINE_DUOCLASS;
    }

    public String gotoAllDuoclasses()
    {
        String ret = ALL_DUOCLASS;
        return ret;
    }


    public String gotoCreateDuoList()
    {
        return  CREATE_LIST;        
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
            CodestableMain.getInstance().getCodestableCodeByType( TYPE_SEX, getRequest().getLocale() );
        for ( Codestable entry : list )
        {
            ret.add( new SelectItem( entry.getId(), entry.getValue() ) );
        }
        return ret;
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

    /**
     * @param ageManager The ageManager to set.
     */
    public void setAgeManager( AgeManager ageManager )
    {
        this.ageManager = ageManager;
    }

    public List<DuoclassWeb> getDuoclassesByAge()
    {
        if ( duoclassesByAge == null )
        {
            try
            {

                if ( getRequest().getAttribute( FIRST_AGE_ENTRY ) != null )
                {
                    return duoclassesByAge =
                        duoclassManager.getDuoclassByAge( (Age) getRequest().getAttribute( FIRST_AGE_ENTRY ),
                                                          getLocale() );
                }
                else if ( getAgeListALL().size() > TypeUtil.INT_DEFAULT )
                {
                    return duoclassesByAge =
                        duoclassManager.getDuoclassByAge( (Age) ageManager.getAllAges().get( TypeUtil.INT_DEFAULT ),
                                                          getLocale() );
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
            return duoclassesByAge;
        }
    }


    private boolean createSuccessfull = false;

    private DuoSimplePoolWebComponent duoSimplePoolWebComponent = null;

    private DuoDoublePoolWebComponent duoDoublePoolWebComponent = null;

    private DuoKoWebComponent duoKoWebComponent = null;


    public String createDuoclass()
    {
        try
        {
            getDuoclassManager().createDuoclasses( new ServiceExchangeContext(
                WebExchangeContextHelper.getWebExchangeContext( getSession() ).getUserId() ) );
            createSuccessfull = true;
        }
        catch ( DuoclassCreateException e )
        {
            addErrorElement( new ErrorElement( ADMIN_FIGHTINGCLASS_CAN_NOT_CREATE_CLASSES ) );
        }

        return null;
    }

    public boolean isCreateSuccessfull()
    {
        return createSuccessfull;
    }

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
                        if ( duoclass == null || duoclass.getId() == null )
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
                        if ( duoclass == null || duoclass.getId() == null )
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
                        if ( duoclass == null || duoclass.getId() == null )
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

    public DuoTeamManager getDuoTeamManager()
    {
        return duoTeamManager;
    }

    public void setDuoTeamManager( DuoTeamManager duoTeamManager )
    {
        this.duoTeamManager = duoTeamManager;
    }


}
