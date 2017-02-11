/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : ConstantsTag.java
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

package de.jjw.webapp.taglib;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

import de.jjw.util.Constants;

/**
 * @jsp.tag name="constants" bodycontent="empty" tei-class="de.jjw.webapp.taglib.ConstantsTei"
 */
public class ConstantsTag
    extends TagSupport
{
    private static final long serialVersionUID = 3258417209566116146L;

    protected final Logger log = Logger.getRootLogger();

    /**
     * The class to expose the variables from.
     */
    public String clazz = Constants.class.getName();

    /**
     * The scope to be put the variable in.
     */
    protected String scope = null;

    /**
     * The single variable to expose.
     */
    protected String var = null;

    @Override
    public int doStartTag()
        throws JspException
    {
        // Using reflection, get the available field names in the class
        Class c = null;
        int toScope = PageContext.PAGE_SCOPE;

        if ( scope != null )
        {
            toScope = getScope( scope );
        }

        try
        {
            c = Class.forName( clazz );
        }
        catch ( ClassNotFoundException cnf )
        {
            log.error( "ClassNotFound - maybe a typo?" );
            throw new JspException( cnf.getMessage() );
        }

        try
        {
            // if var is null, expose all variables
            if ( var == null )
            {
                Field[] fields = c.getDeclaredFields();

                AccessibleObject.setAccessible( fields, true );

                for ( int i = 0; i < fields.length; i++ )
                {
                    /*if (log.isDebugEnabled()) {
                       log.debug("putting '" + fields[i].getName() + "=" +
                                 fields[i].get(this) + "' into " + scope +
                                 " scope");
                       }*/
                    pageContext.setAttribute( fields[i].getName(), fields[i].get( this ), toScope );
                }
            }
            else
            {
                try
                {
                    Object value = c.getField( var ).get( this );
                    pageContext.setAttribute( c.getField( var ).getName(), value, toScope );
                }
                catch ( NoSuchFieldException nsf )
                {
                    log.error( nsf.getMessage() );
                    throw new JspException( nsf );
                }
            }
        }
        catch ( IllegalAccessException iae )
        {
            log.error( "Illegal Access Exception - maybe a classloader issue?" );
            throw new JspException( iae );
        }

        // Continue processing this page
        return ( SKIP_BODY );
    }

    /**
     * @jsp.attribute
     */
    public void setClassName( String clazz )
    {
        this.clazz = clazz;
    }

    public String getClassName()
    {
        return this.clazz;
    }

    /**
     * @jsp.attribute
     */
    public void setScope( String scope )
    {
        this.scope = scope;
    }

    public String getScope()
    {
        return ( this.scope );
    }

    /**
     * @jsp.attribute
     */
    public void setVar( String var )
    {
        this.var = var;
    }

    public String getVar()
    {
        return ( this.var );
    }

    /**
     * Release all allocated resources.
     */
    @Override
    public void release()
    {
        super.release();
        clazz = null;
        scope = Constants.class.getName();
    }

    /**
     * Maps lowercase JSP scope names to their PageContext integer constant values.
     */
    private static final Map scopes = new HashMap();

    /**
     * Initialize the scope names map and the encode variable with the Java 1.4 method if available.
     */
    static
    {
        scopes.put( "page", Integer.valueOf( PageContext.PAGE_SCOPE ) );
        scopes.put( "request", Integer.valueOf( PageContext.REQUEST_SCOPE ) );
        scopes.put( "session", Integer.valueOf( PageContext.SESSION_SCOPE ) );
        scopes.put( "application", Integer.valueOf( PageContext.APPLICATION_SCOPE ) );
    }

    /**
     * Converts the scope name into its corresponding PageContext constant value.
     * 
     * @param scopeName Can be "page", "request", "session", or "application" in any case.
     * @return The constant representing the scope (ie. PageContext.REQUEST_SCOPE).
     * @throws JspException if the scopeName is not a valid name.
     */
    public int getScope( String scopeName )
        throws JspException
    {
        Integer scope = (Integer) scopes.get( scopeName.toLowerCase() );

        if ( scope == null )
        {
            throw new JspException( "Scope '" + scopeName + "' not a valid option" );
        }

        return scope.intValue();
    }
}
