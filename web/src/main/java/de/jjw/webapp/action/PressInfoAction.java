/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : PressInfoAction.java
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

package de.jjw.webapp.action;

import javax.servlet.http.HttpServletResponse;

import de.jjw.model.PressInfo;
import de.jjw.service.PressInfoManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.pdf.PressInfoPDF;

public class PressInfoAction
    extends BasePage
    implements IGlobalWebConstants
{
    private static String INFO = "info";

    private PressInfoManager pressInfoManager;

    private PressInfo info;

    public PressInfo getPressInfo()
    {
        if ( pressInfoManager == null )
        {
            log.warn( "keine pressInfoManager Injection" );
        }
        if ( getRequest().getAttribute( INFO ) == null )
        {
            try
            {
                info = pressInfoManager.getPressInfo();
            }
            catch ( JJWManagerException e )
            {
                addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
            }
            getRequest().setAttribute( INFO, INFO );
        }
        return info;
    }

    public String showPdf()
    {
        try
        {
            HttpServletResponse response = (HttpServletResponse) getFacesContext().getExternalContext().getResponse();

            response.setHeader( "Expires", "0" );
            response.setHeader( "Cache-Control", "must-revalidate, post-check=0, pre-check=0" );
            response.setHeader( "Pragma", "public" );
            response.setContentType( "application/pdf" );

            new PressInfoPDF( "de.jjw.webapp.messages.pressInfos", response, getPressInfo(), getLocale() ).showPress();

            getFacesContext().responseComplete();
        }
        catch ( Exception e )
        {
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }

        return null;
    }

    public PressInfoManager getPressInfoManager()
    {
        return pressInfoManager;
    }

    public void setPressInfoManager( PressInfoManager pressInfoManager )
    {
        this.pressInfoManager = pressInfoManager;
    }

    public PressInfo getInfo()
    {
        return info;
    }

    public void setInfo( PressInfo info )
    {
        this.info = info;
    }

}
