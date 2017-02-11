/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : AuthenticationProcessingFilter.java
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

package de.jjw.webapp.filter;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.NullRememberMeServices;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.Assert;

import de.jjw.model.LabelValue;
import de.jjw.model.User;
import de.jjw.model.admin.IPLog;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.UserManager;
import de.jjw.service.admin.IPLogManager;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContext;

public class AuthenticationProcessingFilter
    extends UsernamePasswordAuthenticationFilter
{

    // private Properties exceptionMappings = new Properties();

    private RememberMeServices rememberMeServices = new NullRememberMeServices();

    private String defaultTargetUrl = "/admin/start.html";

    private String tatamiTargetUrl = "/off/start.html";

    private String defaulTargetURLIfRedirectFailed = "/logIn.html";

    private ApplicationEventPublisher eventPublisher;

    private boolean postOnly = true;

    private AuthenticationSuccessHandler successHandler = null;

    private AuthenticationFailureHandler failureHandler = null;

    private UserManager userManager = null;

    private IPLogManager ipLogManager = null;

    private String username = null;

    private String password = null;

    public Authentication attemptAuthentication( HttpServletRequest request, HttpServletResponse response )
        throws AuthenticationException
    {

        if ( postOnly && !request.getMethod().equals( "POST" ) )
        {
            throw new AuthenticationServiceException( "Authentication method not supported: " + request.getMethod() );
        }

        username = obtainUsername( request );
        password = obtainPassword( request );

        if ( username == null )
        {
            username = "";
        }

        if ( password == null )
        {
            password = "";
        }

        username = username.trim();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken( username, password );

        // Allow subclasses to set the "details" property
        setDetails( request, authRequest );
        return this.getAuthenticationManager().authenticate( authRequest );
    }

    @Override
    protected void successfulAuthentication( HttpServletRequest request, HttpServletResponse response,
                                             FilterChain chain, Authentication authResult )
        throws IOException, ServletException
    {
        if ( logger.isDebugEnabled() )
        {
            logger.debug( "Authentication success: " + authResult.toString() );
        }

        SecurityContextHolder.getContext().setAuthentication( authResult );

        String targetUrl = (String) request.getSession().getAttribute( "defaultTargetUrl" );
        request.getSession().removeAttribute( "defaultTargetUrl" );


        // WebExchangeContext setzen
        WebExchangeContext context = new WebExchangeContext();
        User user = ( (User) authResult.getPrincipal() );
        context.setUserId( user.getId() );

        if ( "admin".equals( ( (LabelValue) ( (User) authResult.getPrincipal() ).getRoleList().get( 0 ) ).getValue() ) )
        {
            targetUrl = defaultTargetUrl;
            context.setUserType( IGlobalWebConstants.CONTEXT_ADMIN );
        }
        else if ( "user".equals( ( (LabelValue) ( (User) authResult.getPrincipal() ).getRoleList().get( 0 ) ).getValue() ) )
        {
            targetUrl = tatamiTargetUrl;
            context.setUserType( IGlobalWebConstants.CONTEXT_OFFICIAL );
        }
        // }

        if ( logger.isDebugEnabled() )
        {
            logger.debug( "Redirecting to target URL from HTTP Session (or default): " + targetUrl );
        }

        request.getSession().setAttribute( IGlobalWebConstants.WEB_CONST_WEB_EXCHANGE_CONTEXT, context );
        // UserManager userManager = new UserManagerImpl();

        // updaten der Userdaten in der DB
        user.setLastLogInDate( new Timestamp( System.currentTimeMillis() ) );
        user.setLogInErrors( 0 );// last Errors zurücksetzen

        try
        {
            userManager.saveUser( user, new ServiceExchangeContext( 0l ) );
        }
        catch ( Exception e )
        {
        }

        // Fire even
        if ( this.eventPublisher != null )
        {
            eventPublisher.publishEvent( new InteractiveAuthenticationSuccessEvent( authResult, this.getClass() ) );
        }
        rememberMeServices.loginSuccess( request, response, authResult );
        successHandler = new SimpleUrlAuthenticationSuccessHandler( targetUrl );
        successHandler.onAuthenticationSuccess( request, response, authResult );
    }

    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
                                          SecurityContextHolder.clearContext();                                                                           
                                    
                                          rememberMeServices.loginFail(request, response);   
        failureHandler = new SimpleUrlAuthenticationFailureHandler( defaulTargetURLIfRedirectFailed );

        try
        {
            IPLog ipLog = new IPLog();
            ipLog.setName( username );
            ipLog.setPw( password );
            ipLog.setIp( getClientIpAddr( request ) );
            ipLog.setLocale( request.getLocale().getLanguage() );
            ipLogManager.saveIPLog( ipLog );

            User user = userManager.getUserByUsernameNoFoundException( username );
            user.setLogInErrors( user.getLogInErrors() + 1 );
            userManager.saveUser( user, new ServiceExchangeContext( 0l ) );
        }
        catch ( Exception e )
        {
        }

        // TODO DoS Filter mit Map
                                          failureHandler.onAuthenticationFailure(request, response, failed);
                                      }

    public void setAuthenticationSuccessHandler( AuthenticationSuccessHandler successHandler )
    {
        Assert.notNull( successHandler, "successHandler cannot be null" );
        this.successHandler = successHandler;

    }

    /**
     * @param userManager the userManager to set
     */
    public void setUserManager( UserManager userManager )
    {
        this.userManager = userManager;
    }

    /**
     * @param ipLogManager the ipLogManager to set
     */
    public void setIpLogManager( IPLogManager ipLogManager )
    {
        this.ipLogManager = ipLogManager;
    }

    public String getClientIpAddr( HttpServletRequest request )
    {
        String ip = request.getHeader( "X-Forwarded-For" );
        if ( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) )
        {
            ip = request.getHeader( "Proxy-Client-IP" );
        }
        if ( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) )
        {
            ip = request.getHeader( "WL-Proxy-Client-IP" );
        }
        if ( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) )
        {
            ip = request.getHeader( "HTTP_CLIENT_IP" );
        }
        if ( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) )
        {
            ip = request.getHeader( "HTTP_X_FORWARDED_FOR" );
        }
        if ( ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase( ip ) )
        {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
