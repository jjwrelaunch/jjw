/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : WebExchangeContextHelper.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:41
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

package de.jjw.webapp;


import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContext;

import de.jjw.model.User;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.webapp.action.validation.ErrorElement;


public class WebExchangeContextHelper
    implements IGlobalWebConstants
{


    public static void createWebExchangeContext( HttpSession session )
    {
        WebExchangeContext ctx = new WebExchangeContext();
        session.setAttribute( WEB_CONST_WEB_EXCHANGE_CONTEXT, ctx );
    }

    public static WebExchangeContext getWebExchangeContext( HttpSession session )
    {
        return (WebExchangeContext) session.getAttribute( WEB_CONST_WEB_EXCHANGE_CONTEXT );
    }

    public static ServiceExchangeContext createServiceExchangeContext( HttpSession session )
    {
        return new ServiceExchangeContext( getWebExchangeContext( session ).getUserId() );
    }

    public static void setTemporaryAttribute( String attributename, Object attribute, HttpSession session )
    {
        WebExchangeContext ctx = (WebExchangeContext) session.getAttribute( WEB_CONST_WEB_EXCHANGE_CONTEXT );
        synchronized ( ctx )
        {
            if ( ctx.getTempAttribute() == null )
            {// no temp Attribute set yet
                ctx.setTempAttribute( new HashMap() );
                ctx.getTempAttribute().put( attributename, attribute );
            }
            else
            {// set new Attribute
                ctx.getTempAttribute().put( attributename, attribute );
            }
        }
    }

    public static Object getTemporaryAttribute( String attributeName, HttpSession session )
    {
        WebExchangeContext ctx = (WebExchangeContext) session.getAttribute( WEB_CONST_WEB_EXCHANGE_CONTEXT );
        if ( ctx.getTempAttribute() == null )
        {// no temp Attribute set yet
            return null;
        }
        else
        {// set new Attribute
            return ctx.getTempAttribute().get( attributeName );
        }
    }

    public static void removeTemporaryAttribute( String attributename, HttpSession session )
    {
        WebExchangeContext ctx = (WebExchangeContext) session.getAttribute( WEB_CONST_WEB_EXCHANGE_CONTEXT );
        synchronized ( ctx )
        {
            if ( ctx.getTempAttribute() == null )
            {// no temp Attribute set yet
                return;
            }
            else
            {// set new Attribute
                ctx.getTempAttribute().remove( attributename );
            }
        }
    }

    public static void removeALLTemporaryAttribute( HttpSession session )
    {
        WebExchangeContext ctx = (WebExchangeContext) session.getAttribute( WEB_CONST_WEB_EXCHANGE_CONTEXT );
        synchronized ( ctx )
        {
            if ( ctx.getTempAttribute() == null )
            {// no temp Attribute set yet
                return;
            }
            else
            {// set new Attribute
                ctx.getTempAttribute().clear();
            }
        }
    }

    public static User getUserAttributeFromSession( HttpSession session )
    {
        SecurityContext securityContext = (SecurityContext) session.getAttribute( WEB_CONST_USER );
        if ( securityContext == null )
        {
            return null;
        }

        return (User) securityContext.getAuthentication().getPrincipal();
    }


    public static long getEventIdLongValue( HttpSession session )
    {
        return getEventIdLong( session ).longValue();
    }

    public static Long getEventIdLong( HttpSession session )
    {
        return (Long) session.getAttribute( WEB_CONST_EVENT_ID );
    }

    public static void setEventId( HttpSession session, long eventId )
    {
        session.setAttribute( WEB_CONST_EVENT_ID, Long.valueOf( eventId ) );
    }

    public static void setMenuExchangeContext( MenuExchangeContext menu, HttpSession session )
    {
        session.setAttribute( WEB_CONST_MENU_EXCHANGE_CONTEXT, menu );
    }

    public static MenuExchangeContext getMenuExchangeContext( HttpSession session )
    {
        MenuExchangeContext menu = (MenuExchangeContext) session.getAttribute( WEB_CONST_MENU_EXCHANGE_CONTEXT );

        if ( menu == null )
        {
            menu = new MenuExchangeContext();
            menu.setEventsMenu( MenuExchangeContext.FULL_MENU );
            menu.setStatisticMenu( MenuExchangeContext.FULL_MENU );
            menu.setTeamMenu( MenuExchangeContext.FULL_MENU );
            setMenuExchangeContext( menu, session );
        }
        return ( menu );
    }

    public static void addErrorElement( ErrorElement error, HttpSession session )
    {
        WebExchangeContext ctx = (WebExchangeContext) session.getAttribute( WEB_CONST_WEB_EXCHANGE_CONTEXT );
        if ( ctx.getErrorVector() != null )
        {
            ctx.addErrorElement( error );
        }
        else
        {
            Vector<ErrorElement> errorVector = new Vector<ErrorElement>();
            errorVector.add( error );
            addErrorVector( errorVector, session );
        }
    }

    public static void addErrorVector( Vector<ErrorElement> error, HttpSession session )
    {
        WebExchangeContext ctx = (WebExchangeContext) session.getAttribute( WEB_CONST_WEB_EXCHANGE_CONTEXT );
        ctx.addErrorVector( error );
    }

    public static void clearErrorVector( HttpSession session )
    {
        WebExchangeContext ctx = (WebExchangeContext) session.getAttribute( WEB_CONST_WEB_EXCHANGE_CONTEXT );
        if ( ctx != null )
        {
            ctx.clearErrors();
        }
    }

    public static boolean isErrors( HttpSession session )
    {
        WebExchangeContext ctx = (WebExchangeContext) session.getAttribute( WEB_CONST_WEB_EXCHANGE_CONTEXT );
        if ( ctx == null )
        {
            return false;
        }
        return ctx.isErrors();
    }

    public static Vector<ErrorElement> getErrorVector( HttpSession session )
    {
        WebExchangeContext ctx = (WebExchangeContext) session.getAttribute( WEB_CONST_WEB_EXCHANGE_CONTEXT );
        return ctx.getErrorVector();
    }
}
