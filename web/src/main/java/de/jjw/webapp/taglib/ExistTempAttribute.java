/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : ExistTempAttribute.java
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

import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class ExistTempAttribute
    extends TagSupport
    implements IGlobalWebConstants
{

    private boolean deleteAttribute = false;

    private String attributeName = null;

    @Override
    public int doStartTag()
        throws JspException
    {
        if ( attributeName != null &&
            WebExchangeContextHelper.getTemporaryAttribute( attributeName, pageContext.getSession() ) != null &&
            attributeName.equals(
                WebExchangeContextHelper.getTemporaryAttribute( attributeName, pageContext.getSession() ) ) )
        {
            ;
        }

        return EVAL_BODY_INCLUDE;
    }

    /**
     * @return Returns the deleteAttribute.
     */
    public boolean isDeleteAttribute()
    {
        return deleteAttribute;
    }

    /**
     * @param deleteAttribute The deleteAttribute to set.
     */
    public void setDeleteAttribute( boolean deleteAttribute )
    {
        this.deleteAttribute = deleteAttribute;
    }


}
