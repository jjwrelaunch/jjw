/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : ErrorMessageTag.java
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

package de.jjw.webapp.taglib;


import de.jjw.util.TypeUtil;
import de.jjw.webapp.action.validation.ErrorElement;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

public class ErrorMessageTag
    extends TagSupport
    implements HtmlConstants
{


    private Map errorMap = null;

    @SuppressWarnings("unchecked")
    public int doStartTag()
        throws JspException
    {

        Map errorMaps = (Map) pageContext.getSession().getAttribute( "ErrorMaps" );
        //the right map for the language fom request
        errorMap = (Map) errorMaps.get( pageContext.getRequest().getLocale() );
        List<ErrorElement> errors = (List<ErrorElement>) pageContext.getSession().getAttribute( "errors" );

        // build error table
        if ( errors != null && errors.size() > TypeUtil.INT_DEFAULT )
        {
            try
            {
                pageContext.getOut().print( "<table class=\"errortable\">" );
                for ( ErrorElement error : errors )
                {
                    pageContext.getOut().print( TABLE_ROW_BEGIN + TABLE_CELL_BEGIN );
                    addNewErrorOutput( error );
                    pageContext.getOut().println( TABLE_CELL_END + TABLE_ROW_END );
                }
                pageContext.getOut().println( TABLE_END );
            }
            catch ( Exception e )
            {
            }
        }
        return SKIP_BODY;
    }

    /**
     * build the errormessage for the errorelement
     *
     * @param error
     * @throws Exception
     */
    private void addNewErrorOutput( ErrorElement error )
        throws Exception
    {
        if ( error.getErrorParameter() == null )
        {
            pageContext.getOut().println( (String) errorMap.get( error.getErrorMassage() ) );
        }
        else
        {
            pageContext.getOut().println( MessageFormat.format( (String) errorMap.get( error.getErrorMassage() ),
                                                                error.getErrorParameter().toArray() ) );
        }
    }
}
