/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : CodestableErrorMessageTag.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:42
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

import java.io.IOException;
import java.util.Vector;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.action.validation.ErrorElement;

/**
 * Class is responsible to show errormessages to user
 *
 * @author joerg.boehme
 */
public class CodestableErrorMessageTag
    extends TagSupport
    implements HtmlConstants
{

    private static String TABLE_CELL_BEGIN = "<td class= \"CodestableErrorMessage\">";

    @Override
    public int doStartTag()
        throws JspException
    {
        doHtml();
        return SKIP_BODY;
    }

    private void doHtml()
    {
        Vector<ErrorElement> errors = WebExchangeContextHelper.getErrorVector( pageContext.getSession() );
        if ( null != errors )
        {
            String valueText = null;
            StringBuffer sb = new StringBuffer();
            sb.append( "<div id=\"error\">" );
            int i =0;
            for ( ErrorElement error : errors )
            {
            	i++;
                valueText = i + " " +CodestableMain.getInstance().getCodestableErrorValueById( error.getErrorMassage(),
                                                                                      pageContext.getRequest().getLocale() );
                if ( !TypeUtil.isEmptyOrDefault( valueText ) )
                {
                    sb.append( "<div class=\"errorItem\">" );
                    sb.append( valueText );
                    sb.append( "</div>" );
                }
                if ( i == IGlobalWebConstants.MAX_ERRORS_TO_DISPLAY )
                {
                    break;
                }
            }
            sb.append( "</div>" );
            try
            {
                pageContext.getOut().println( sb.toString() );
            }
            catch ( IOException e )
            {
            }
        }
    }
}
