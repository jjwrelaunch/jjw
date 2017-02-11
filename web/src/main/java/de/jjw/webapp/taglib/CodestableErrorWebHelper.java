/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : CodestableErrorWebHelper.java
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

import java.util.Vector;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.action.validation.ErrorElement;

/**
 * This Class manage the display of Errors. It takes the ErrorList and Remove
 * the last errors.
 *
 * @author joerg.boehme
 */
public class CodestableErrorWebHelper
    implements IGlobalWebConstants
{
    
    public static void handleErrorDisply( ServletRequest request, HttpSession session )
    {
        Vector<ErrorElement> errors = WebExchangeContextHelper.getErrorVector( session );
        Vector<ErrorElement> markErrors = new Vector<ErrorElement>( MAX_ERRORS_TO_DISPLAY + 1 );
        if ( null != errors )
        {
            String valueText = null;
            int i = 0;
            for ( ErrorElement error : errors )
            {
                valueText = CodestableMain.getInstance().getCodestableErrorValueById( error.getErrorMassage(),
                                                                                      request.getLocale() );
                if ( !TypeUtil.isEmptyOrDefault( valueText ) )
                {
                    i++;
                    markErrors.add( error );
                }
                if ( i == MAX_ERRORS_TO_DISPLAY )
                {
                    break;
                }
            }
            WebExchangeContextHelper.clearErrorVector( session );
            WebExchangeContextHelper.addErrorVector( markErrors, session );
        }
        request.setAttribute( WEB_DISPLAY_ERRORS, WEB_DISPLAY_ERRORS );
    }
}