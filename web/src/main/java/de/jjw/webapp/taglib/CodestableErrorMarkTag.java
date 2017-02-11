/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : CodestableErrorMarkTag.java
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

package de.jjw.webapp.taglib;

import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.action.validation.ErrorElement;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Vector;

/**
 * Class ist responsible to mark the input fields, which raised errors
 *
 * @author joerg.boehme
 */
public class CodestableErrorMarkTag
    extends TagSupport
    implements IGlobalWebConstants
{

    private String field = null;

    private static String CODESTABLE_ERROR_MARK = "codestableErrorMark";

    private static int NULL = 0;

    @Override
    public int doStartTag()
        throws JspException
    {
        if ( pageContext.getRequest().getAttribute( WEB_DISPLAY_ERRORS ) == null )
        {
            CodestableErrorWebHelper.handleErrorDisply( pageContext.getRequest(), pageContext.getSession() );
        }

        doHtml();
        return SKIP_BODY;
    }

    private void doHtml()
    {
        Vector<ErrorElement> errors = WebExchangeContextHelper.getErrorVector( pageContext.getSession() );
        if ( null != errors && field != null )
        {
            int errorNumber = 1;
            StringBuffer sb = null;
            boolean incrementErrorNumber = false;

            for ( ErrorElement error : errors )
            {
                sb = new StringBuffer();
                sb.append("<b>");
                incrementErrorNumber = false;
                if ( error.getErrorElements() != null )
                {
                    for ( String errorItem : error.getErrorElements() )
                    {

                        if ( errorItem.equals( field ) )
                        {
                            incrementErrorNumber = true;
                            if ( pageContext.getRequest().getAttribute( CODESTABLE_ERROR_MARK ) == null )
                            {
                                sb.append( String.valueOf( errorNumber ) );
                            }
                            else
                            {
                                errorNumber =
                                    TypeUtil.toInt( pageContext.getRequest().getAttribute( CODESTABLE_ERROR_MARK ) );
                                if ( errorNumber != TypeUtil.INT_MIN )
                                {
                                    sb.append( String.valueOf( errorNumber ) );
                                }
                                else
                                {
                                    sb.append( String.valueOf( ++NULL ) );
                                }
                            }

                            if ( incrementErrorNumber )
                            {
                                errorNumber++;

                                pageContext.getRequest().setAttribute( CODESTABLE_ERROR_MARK,
                                                                       Integer.valueOf( errorNumber ) );
                            }
                            try
                            {
                            	sb.append("</b>");
                                pageContext.getOut().println( sb.toString() );
                            }
                            catch ( IOException e )
                            {
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * @return Returns the field.
     */
    public String getField()
    {
        return field;
    }

    /**
     * @param field The field to set.
     */
    public void setField( String field )
    {
        this.field = field;
    }
}
