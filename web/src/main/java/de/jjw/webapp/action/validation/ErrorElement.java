/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : ErrorElement.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:45
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

package de.jjw.webapp.action.validation;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ErrorElement implements Serializable
{

    private List<String> errorElements = null;

    private String errorMassage = null;

    private List<String> errorParameter = null;


    public ErrorElement( String errorMassage )
    {
        this.errorMassage = errorMassage;
    }

    public ErrorElement( String errorElement, String errorMassage, List<String> errorParameter )
    {
        this.errorElements = new ArrayList<String>( 1 );
        errorElements.add( errorElement );
        this.errorMassage = errorMassage;
        this.errorParameter = errorParameter;
    }

    public ErrorElement( String errorElement, String errorMassage )
    {
        this.errorElements = new ArrayList<String>( 1 );
        errorElements.add( errorElement );
        this.errorMassage = errorMassage;
    }

    public ErrorElement( List<String> errorElements, String errorMassage )
    {
        this.errorElements = errorElements;
        this.errorMassage = errorMassage;
    }

    public ErrorElement( List<String> errorElements, String errorMassage, List<String> errorParameter )
    {
        this.errorElements = errorElements;
        this.errorMassage = errorMassage;
        this.errorParameter = errorParameter;
    }

    public List<String> getErrorElements()
    {
        return errorElements;
    }

    public void setErrorElements( List<String> errorElements )
    {
        this.errorElements = errorElements;
    }

    public String getErrorMassage()
    {
        return errorMassage;
    }

    public void setErrorMassage( String errorMassage )
    {
        this.errorMassage = errorMassage;
    }

    public List getErrorParameter()
    {
        return errorParameter;
    }

    public void setErrorParameter( List<String> errorParameter )
    {
        this.errorParameter = errorParameter;
    }


}
