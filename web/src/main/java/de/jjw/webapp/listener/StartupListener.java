/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : StartupListener.java
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

package de.jjw.webapp.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.RollingFileAppender;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import de.jjw.model.admin.ConfigJJW;
import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.service.LookupManager;
import de.jjw.service.UserManager;
import de.jjw.service.admin.ConfigManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.generalhelper.CodestableManager;
import de.jjw.util.Constants;
import de.jjw.webapp.util.ConfigMain;

/**
 * <p>StartupListener class used to initialize and database settings
 * and populate any application-wide drop-downs.
 * <p/>
 * <p>Keep in mind that this listener is executed outside of OpenSessionInViewFilter,
 * so if you're using Hibernate you'll have to explicitly initialize all loaded data at the
 * Dao or service level to avoid LazyInitializationException. Hibernate.initialize() works
 * well for doing this.
 *
 * @web.listener
 */
public class StartupListener
    extends ContextLoaderListener
    implements ServletContextListener
{

    private static final Logger log = Logger.getRootLogger( );

	public void contextInitialized( ServletContextEvent event )
    {
    	//((org.apache.log4j.RollingFileAppender)  ((java.util.Vector<java.util.Enumeration>)log.getAllAppenders()).get(1)).getLayout().
    	
		try {
			ConsoleAppender console = ((org.apache.log4j.ConsoleAppender) log
					.getAllAppenders().nextElement());
			String patternLayout = ((org.apache.log4j.PatternLayout) console
					.getLayout()).getConversionPattern();

			String appContextName = event.getServletContext().getRealPath("");
			if (appContextName.lastIndexOf("/") > 0) {
				appContextName = appContextName.substring(appContextName
						.lastIndexOf("/") + 1);
			} else {
				appContextName = appContextName.substring(appContextName
						.lastIndexOf("\\") + 1);
			}

			patternLayout= patternLayout.replaceFirst("JJW", appContextName);
			((org.apache.log4j.PatternLayout) console.getLayout())
					.setConversionPattern(patternLayout);
			RollingFileAppender file = ((org.apache.log4j.RollingFileAppender) log
					.getAllAppenders().nextElement());
			((org.apache.log4j.PatternLayout) file.getLayout())
					.setConversionPattern(patternLayout);
		} catch (Exception e) {
			;
		}
		
        log.fatal(
            "---------------------------------------------------------------------------------------------------------------------------------------" );
        log.fatal( "" );
        log.fatal( "" );
        if ( log.isDebugEnabled() )
        {
            log.debug( "initializing context..." );
        }

        // call Spring's context ContextLoaderListener to initialize
        // all the context files specified in web.xml
        super.contextInitialized( event );

        ServletContext context = event.getServletContext();

        setupContext( context );
        setupCodestable( context );
        setupConfigEntries( context );
        handleDeleteRoleAssignment( context );
    }

    private void handleDeleteRoleAssignment( ServletContext context )
    {
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext( context );

        UserManager mgr = (UserManager) ctx.getBean( "userManager" );
        mgr.handleDeleteRoleAssignment();
    }

    private static void setupConfigEntries( ServletContext context )
    {
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext( context );

        ConfigManager mgr = (ConfigManager) ctx.getBean( "configManager" );
        try
        {
            ConfigMain.getInstance().setEventConfiguration( mgr.getConfig() );
        }
        catch ( JJWManagerException e )
        {
            ConfigMain.getInstance().setEventConfiguration( new ConfigJJW() );
        }

        if ( log.isDebugEnabled() )
        {
            log.debug( "CodestableMain Loading complete [OK]" );
        }
    }

    private static void setupCodestable( ServletContext context )
    {
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext( context );

        CodestableManager mgr = (CodestableManager) ctx.getBean( "codestableManager" );
        CodestableMain.getInstance().setALL_ENTRIES( mgr.getAllEntriesByLocaleList() );

        if ( log.isDebugEnabled() )
        {
            log.debug( "CodestableMain Loading complete [OK]" );
        }
    }

    public static void setupContext( ServletContext context )
    {
        ApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext( context );

        LookupManager mgr = (LookupManager) ctx.getBean( "lookupManager" );

        // get list of possible roles
        context.setAttribute( Constants.AVAILABLE_ROLES, mgr.getAllRoles() );

        if ( log.isDebugEnabled() )
        {
            log.debug( "Drop-down initialization complete [OK]" );
        }
    }
}
