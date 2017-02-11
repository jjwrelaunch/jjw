/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : BasePage.java
 * Created : 15 Jun 2010
 * Last Modified: Tue, 15 Jun 2010 21:07:10
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


import java.io.Serializable;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import de.jjw.model.ITechnicalDBFields;
import de.jjw.service.UserManager;
import de.jjw.util.Constants;
import de.jjw.util.ICodestableConstants;
import de.jjw.webapp.WebExchangeContext;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.action.validation.ErrorElement;

public class BasePage
    extends BasePageMother
    implements ICodestableConstants, Serializable
{
    public static final String jstlBundleParam = "javax.servlet.jsp.jstl.fmt.localizationContext";
    //protected final Log log =   LogFactory.getLog(getClass());

    protected final Logger log = Logger.getRootLogger();

    protected UserManager userManager = null;

    protected FacesContext facesContext = null;

    protected String sortColumn;

    protected UIData dataTable;

    protected ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder();

    /**
     * Allow overriding of facesContext for unit tests
     *
     * @param facesContext
     */
    public void setFacesContext( FacesContext facesContext )
    {
        this.facesContext = facesContext;
    }

    public FacesContext getFacesContext()
    {
        if ( facesContext != null )
        {
            // for unit tests
            return facesContext;
        }
        return FacesContext.getCurrentInstance();
    }

    public void setUserManager( UserManager userManager )
    {
        this.userManager = userManager;
    }

    // Convenience methods ====================================================

    public String getParameter( String name )
    {
        return getRequest().getParameter( name );
    }

    public String getBundleName()
    {
        // get name of resource bundle from JSTL settings, JSF makes this too hard
        return getServletContext().getInitParameter( jstlBundleParam );
    }

    /*public Map getCountries() {
        CountryModel model = new CountryModel();

        return model.getCountries(getRequest().getLocale());
    }*/

    public ResourceBundle getBundle()
    {
        return ResourceBundle.getBundle( getBundleName(), getRequest().getLocale() );
    }

    public String getText( String key )
    {
        String message;

        try
        {
            message = getBundle().getString( key );
        }
        catch ( java.util.MissingResourceException mre )
        {
            log.warn( "Missing key for '" + key + "'" );

            return "???" + key + "???";
        }

        return message;
    }

    public String getText( String key, Object arg )
    {
        if ( arg == null )
        {
            return getBundle().getString( key );
        }

        MessageFormat form = new MessageFormat( getBundle().getString( key ) );

        if ( arg instanceof String )
        {
            return form.format( new Object[]{arg} );
        }
        else if ( arg instanceof Object[] )
        {
            return form.format( arg );
        }
        else
        {
            log.error( "arg '" + arg + "' not String or Object[]" );

            return "";
        }
    }

    protected void addMessage( String key, Object arg )
    {
        // JSF Success Messages won't live past a redirect, so I don't use it
        //FacesUtils.addInfoMessage(formatMessage(key, arg));
        List messages = (List) getSession().getAttribute( "messages" );

        if ( messages == null )
        {
            messages = new ArrayList();
        }

        messages.add( getText( key, arg ) );
        getSession().setAttribute( "messages", messages );
    }

    protected void addMessage( String key )
    {
        addMessage( key, null );
    }

    @SuppressWarnings("unchecked")
    protected void addError( List<ErrorElement> errorList )
    {
        if ( errorList != null )
        {
            List<ErrorElement> errors = (List<ErrorElement>) getSession().getAttribute( "errors" );
            if ( errors == null )
            {
                errors = new Vector<ErrorElement>();
            }
            errors.addAll( errorList );
            getSession().setAttribute( "errors", errors );
        }
    }


    /**
     * Convenience method for unit tests.
     *
     * @return
     */
    public boolean hasErrors()
    {
        return ( getSession().getAttribute( "errors" ) != null );
    }

    /**
     * Servlet API Convenience method
     *
     * @return
     */
    protected HttpServletRequest getRequest()
    {
        return (HttpServletRequest) getFacesContext().getExternalContext().getRequest();
    }

    /**
     * Servlet API Convenience method
     *
     * @return
     */
    protected HttpSession getSession()
    {
        return getRequest().getSession();
    }

    /**
     * Servlet API Convenience method
     *
     * @return
     */
    protected HttpServletResponse getResponse()
    {
        return (HttpServletResponse) getFacesContext().getExternalContext().getResponse();
    }

    /**
     * Servlet API Convenience method
     *
     * @return
     */
    protected ServletContext getServletContext()
    {
        return (ServletContext) getFacesContext().getExternalContext().getContext();
    }

    /**
     * Convenience method to get the Configuration HashMap
     * from the servlet context.
     *
     * @return the user's populated form from the session
     */
    protected Map getConfiguration()
    {
        Map config = (HashMap) getServletContext().getAttribute( Constants.CONFIG );

        // so unit tests don't puke when nothing's been set
        if ( config == null )
        {
            return new HashMap();
        }

        return config;
    }


    public void addErrorElement( ErrorElement error )
    {
        WebExchangeContextHelper.addErrorElement( error, getSession() );
    }

    public void setErrorMessageVector( Vector<ErrorElement> error )
    {
        WebExchangeContextHelper.addErrorVector( error, getSession() );
    }

    protected WebExchangeContext getCtx()
    {
        return WebExchangeContextHelper.getWebExchangeContext( getSession() );
    }

    /**
     * set technical DB Fields
     *
     * @param obj
     */
    protected void setTechnicalDBFieldsForCreate( ITechnicalDBFields obj )
    {
        obj.setCreateDate( new Timestamp( System.currentTimeMillis() ) );
        obj.setCreateId( getCtx().getUserId() );
        obj.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
        obj.setModificationId( getCtx().getUserId() );
    }

    /**
     * set technical DB Fields
     *
     * @param obj
     */
    protected void setTechnicalDBFieldsForUpdate( ITechnicalDBFields obj )
    {
        //obj.setModificationDate(new Timestamp(System.currentTimeMillis())); kein neues datum hier setzen, wegen optimisticlocking
        obj.setModificationId( getCtx().getUserId() );
    }

    public Locale getLocale()
    {
        return getRequest().getLocale();
    }

    public String getSortColumn()
    {
        return sortColumn == null ? "0" : sortColumn;
    }

    public void setSortColumn( String sortColumn )
    {
        this.sortColumn = sortColumn;
    }

    public UIData getDataTable()
    {
        return dataTable;
    }

    public void setDataTable( UIData dataTable )
    {
        this.dataTable = dataTable;
    }

    public String getText()
    {
        return "";
    }

    public ShaPasswordEncoder getPasswordEncoder()
    {
        return passwordEncoder;
    }

    public void setPasswordEncoder( ShaPasswordEncoder passwordEncoder )
    {
        this.passwordEncoder = passwordEncoder;
    }


}
