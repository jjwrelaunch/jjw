/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : AgeAction.java
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

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import org.springframework.dao.DataIntegrityViolationException;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.admin.Age;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.admin.AgeManager;
import de.jjw.service.exception.AgeInUseException;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.action.validation.admin.AgeValidator;
import de.jjw.webapp.constants.admin.IAgeConstants;

public class AgeAction
    extends BasePage
    implements Serializable, IGlobalWebConstants, IAgeConstants
{
    AgeManager ageManager;

    Age age = new Age();

    List<Age> ages;

    private static String ALL_AGES = "allAges";

    private static String NEW_AGE = "newAge";

    private static String EDIT_AGE = "editAge";

    public List<Age> getAges()
    {
        if ( ageManager == null )
        {
            log.warn( "keine ageManager Injection" );
        }
        if ( getRequest().getAttribute( ALL_AGES ) == null )
        {
            try
            {
                ages = ageManager.getAllAges();
            }
            catch ( JJWManagerException e )
            {
                addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
            }
            getRequest().setAttribute( ALL_AGES, ALL_AGES );
        }
        return ages;
    }

    public String addAge()
    {
        String ret = SUCCESS;
        Vector<ErrorElement> errors = new Vector<ErrorElement>();

        if ( !AgeValidator.isEditAgeValid( getAges(), errors, age ) ||
            !AgeValidator.isNewAgeValid( getAges(), errors, age ) ||
            AgeValidator.isAgeWithoutConflict( getAges(), errors, age ) )
        {
            setErrorMessageVector( errors );
            getRequest().setAttribute( WEB_ADMIN_AGE_NEW, WEB_ADMIN_AGE_NEW );
            return null;
        }
        age.setAgeShort( age.getAgeShort().toUpperCase().trim() );
        age.setDescription( age.getDescription().trim() );
        setTechnicalDBFieldsForCreate( age );
        try
        {
            ageManager.saveAge( age );

        }
        catch ( DataIntegrityViolationException e )
        {
            errors.add( new ErrorElement( FIELD_AGE_SHORT, ADMIN_COUNTRY_EXISTS ) );
            setErrorMessageVector( errors );
            ret = null;
        }
        catch ( Exception e )
        {
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
            log.error( "AgeAction: ", e );
            ret = null;
        }
        getRequest().removeAttribute( ALL_AGES );
        age = new Age();
        return ret;
    }


    public String editAge()
    {
        String ret = SUCCESS;
        Vector<ErrorElement> errors = new Vector<ErrorElement>();
        if ( !AgeValidator.isEditAgeValid( getAges(), errors, age ) ||
            AgeValidator.isAgeWithoutConflict( getAges(), errors, age ) ||
            !AgeValidator.isNewAgeValid( getAges(), errors, age ) )
        {
            setErrorMessageVector( errors );
            return null;
        }

        // TODO: abfrage, ob bereits eine Freigabe für eine Wettkampfklasse
        // der Alterklasse vorliegt, wenn ja, dann keine Änderung zulassen

        // TODO: abfrage, ob es sich um eine Änderung der Start und End Jahre
        // handelt
        // wenn ja, dann testen, ob es fighter/duos gibt, die hiervon betroffen
        // sind, wenn ja exception

        setTechnicalDBFieldsForUpdate( age );
        try
        {
            ageManager.saveAge( age );
        }
        catch ( DataIntegrityViolationException e )
        {
            errors.add( new ErrorElement( FIELD_AGE_SHORT, GEN_GERNERAL_ERROR ) );
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
        age = new Age();
        return ret;
    }

    public String deleteAge()
    {
        String ret = SUCCESS;

        try
        {
            ageManager.removeAge( (Age) dataTable.getRowData() );
        }
        catch ( AgeInUseException e )
        {
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( FIELD_AGE_SHORT, ADMIN_AGE_IN_USE ) );
            setErrorMessageVector( errors );
            return null;
        }
        return ret;
    }


    public String moveAgeUp()
    {
        String ret = SUCCESS;

        try
        {
            ageManager.moveAgeOrderUp( (Age) dataTable.getRowData(), new ServiceExchangeContext(
                WebExchangeContextHelper.getWebExchangeContext( getSession() ).getUserId() ) );
        }
        catch ( JJWManagerException e )
        {
            Vector<ErrorElement> errors = new Vector<ErrorElement>();
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
            return null;
        }
        return ret;
    }

    public String gotoAllAges()
    {
        return ALL_AGES;
    }

    public String gotoNewAge()
    {
        setAge( new Age() );
        String ret = NEW_AGE;
        getRequest().setAttribute( WEB_ADMIN_AGE_NEW, WEB_ADMIN_AGE_NEW );
        return ret;
    }

    public String gotoEditAge()
    {
        setAge( (Age) dataTable.getRowData() );
        return EDIT_AGE;
    }

    /**
     * @return Returns the age.
     */
    public Age getAge()
    {
        return age;
    }

    /**
     * @param age The age to set.
     */
    public void setAge( Age age )
    {
        this.age = age;
    }

    /**
     * @param ageManager The ageManager to set.
     */
    public void setAgeManager( AgeManager ageManager )
    {
        this.ageManager = ageManager;
    }


}
