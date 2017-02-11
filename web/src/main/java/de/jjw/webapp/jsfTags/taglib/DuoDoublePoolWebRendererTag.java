/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoDoublePoolWebRendererTag.java
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

package de.jjw.webapp.jsfTags.taglib;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentELTag;


public class DuoDoublePoolWebRendererTag
    extends UIComponentELTag
{

    public String getComponentType()
    {
        return "de.jjw.webapp.jsfTags.component.DuoDoublePoolWebComponent";
    }

    public String getRendererType()
    {
        return null;
    }

    private ValueExpression readOnly;

    //private String messageResource;
    private ValueExpression pool;

    private ValueExpression messageResource;

//	public String getReadOnly() {

    //		return readOnly;
//	}
    public void setReadOnly( ValueExpression newValue )
    {
        readOnly = newValue;
    }
//	public String getMessageResource() {

    //		return messageResource;
//	}
    public void setMessageResource( ValueExpression newValue )
    {
        messageResource = newValue;
    }

    public ValueExpression getPool()
    {
        return pool;
    }

    public void setPool( ValueExpression newValue )
    {
        pool = newValue;
    }

    protected void setProperties( UIComponent component )
    {
        super.setProperties( component );

        if ( messageResource != null )
        {
            component.setValueExpression( "messageResource", messageResource );
        }

        if ( readOnly != null )
        {
            component.setValueExpression( "readOnly", readOnly );
        }

        if ( pool != null )
        {
            component.setValueExpression( "pool", pool );
        }
    }

}
