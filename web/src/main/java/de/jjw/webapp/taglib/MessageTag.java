/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : MessageTag.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:47
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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ResourceBundle;

public class MessageTag
    extends TagSupport
{

    private String messageResource;

    private String message;

    public int doStartTag()
        throws JspException
    {

        ResourceBundle res = ResourceBundle.getBundle( "de.jjw.webapp.messages." + messageResource,
                                                       pageContext.getRequest().getLocale() );
        try
        {
            pageContext.getOut().print( res.getString( message ) );
        }
        catch ( IOException e )
        {
            //print nothing
        }
        return SKIP_BODY;
    }

    /**
     * @return Returns the message.
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * @param message The message to set.
     */
    public void setMessage( String message )
    {
        this.message = message;
    }

    /**
     * @return Returns the messageResource.
     */
    public String getMessageResource()
    {
        return messageResource;
    }

    /**
     * @param messageResource The messageResource to set.
     */
    public void setMessageResource( String messageResource )
    {
        this.messageResource = messageResource;
    }


}
