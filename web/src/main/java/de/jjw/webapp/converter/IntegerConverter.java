/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : IntegerConverter.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:43
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

package de.jjw.webapp.converter;

import de.jjw.util.ICodestableConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.action.validation.ErrorElement;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.servlet.http.HttpServletRequest;
import java.util.Vector;

public class IntegerConverter
    implements Converter, ICodestableConstants
{

    public static final String CONVERTER_ID = "jjw.webapp.converter.IntegerConverter";

    public Object getAsObject( FacesContext arg0, UIComponent arg1, String arg2 )
        throws ConverterException
    {

        if ( arg0 == null )
        {
            throw new NullPointerException( "facesContext" );
        }
        if ( arg1 == null )
        {
            throw new NullPointerException( "uiComponent" );
        }

        if ( arg2 == null )
        {
            throw new NullPointerException( "uiComponent.object" );
        }
        arg2 = arg2.trim();
        if ( arg2.length() <= 0 )
        {
            setErrorMsg( arg0, arg1 );
            throw new ConverterException();
        }

        try
        {
            return Integer.valueOf( arg2 );
        }
        catch ( NumberFormatException e )
        {
            setErrorMsg( arg0, arg1 );
            throw new ConverterException();
        }

    }

    public String getAsString( FacesContext arg0, UIComponent arg1, Object arg2 )
        throws ConverterException
    {
        if ( arg0 == null )
        {
            throw new NullPointerException( "facesContext" );
        }
        if ( arg1 == null )
        {
            throw new NullPointerException( "uiComponent" );
        }

        if ( arg2 == null )
        {
            return "";
        }

        if ( arg2 instanceof String )
        {
            return (String) arg2;
        }

        try
        {
            return Integer.toString( ( (Number) arg2 ).intValue() );
        }
        catch ( Exception e )
        {
            throw new ConverterException( e );
        }
    }

    /*
    * sets the Converter ErrorMsg in the session
    */
    private void setErrorMsg( FacesContext facesContext, UIComponent uiComponent )
    {
        Vector<ErrorElement> errors = new Vector<ErrorElement>();
        errors.add( new ErrorElement( uiComponent.getId(), GEN_VALUE_NO_VALID_INTEGER ) );
        WebExchangeContextHelper.addErrorVector( errors,
                                                 ( (HttpServletRequest) facesContext.getExternalContext().getRequest() ).getSession() );
    }
}
