package de.jjw.webapp.filter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class InitServlet
    extends HttpServlet
{
    protected final Logger log = Logger.getRootLogger();

    @Override
    public void init()
        throws ServletException
    {
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext( getServletContext() );
        super.init();
        StatisticThread sThread = new StatisticThread( context, false, null );

        Thread runThread = new Thread( sThread, "StatisticThread" );
        runThread.setDaemon( true );
        runThread.start();
    }

}
