package de.jjw.webapp.filter;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.admin.ConfigJJW;
import de.jjw.service.DatabaseDumpManager;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.admin.ConfigManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.webapp.export.DatabaseExport;

public class StatisticThread
    extends Thread
    implements Runnable
{
    protected final Logger log = Logger.getRootLogger();

    private boolean send = false;

    private boolean onetime = false;

    private ApplicationContext context = null;

    private DatabaseDumpManager databaseDumpManager = null;

    private ConfigManager configManager = null;
    
    ByteArrayOutputStream allZippedExport=null;

    /**
     * 
     * @param context
     * @param onetime
     * @param allZippedExport  parameter for performance, when all Data for sending is collected an zipped
     */
    public StatisticThread( ApplicationContext context, boolean onetime,ByteArrayOutputStream allZippedExport )
    {
        this.context = context;
        this.onetime = onetime;
        this.allZippedExport=allZippedExport;
    }


    @Override
    public void run()
    {
        while ( !send )
        {
            try
            {
                configManager = (ConfigManager) context.getBean( "configManager" );
                ConfigJJW config = configManager.getConfig();
                Timestamp latestChange = getLatestsChangeTimestamp();
                boolean isEventOver = isEventOver();

                if ( isEventOver && ( null == latestChange || latestChange.after( config.getDumpSend() ) ) )
                {
                    DatabaseExport dbe = null;

                    dbe = new DatabaseExport( context,allZippedExport );

                    if ( dbe.sendDatabase( null ) )
                    {
                        send = true;
                        config.setDumpSend( new Timestamp( System.currentTimeMillis() ) );
                        configManager.saveConfig( new ServiceExchangeContext( 0 ), config );
                    }
                }
                else
                    send = true;
            }

            catch ( JJWManagerException e )
            {
                log.error( "could not send dump", e );
            }
            catch ( OptimisticLockingException e )
            {
                log.error( "could not send dump", e );
            }
            catch ( Exception e )
            {
                log.error( "could not send dump", e );
            }
            finally
            {
                if ( !send )
                    try
                    {
                        sleep( 120000 );
                    }
                    catch ( InterruptedException e )
                    {
                        ;
                    }
            }
            if ( onetime )
                break;
        }
    }

    private boolean isEventOver()
    {
        if ( databaseDumpManager == null )
            databaseDumpManager = (DatabaseDumpManager) context.getBean( "databaseDumpManager" );
        try
        {
            return databaseDumpManager.isEventOver();
        }
        catch ( JJWManagerException e )
        {
            return false;
        }
    }

    private Timestamp getLatestsChangeTimestamp()
    {
        if ( databaseDumpManager == null )
            databaseDumpManager = (DatabaseDumpManager) context.getBean( "databaseDumpManager" );
        try
        {
            Timestamp ret = databaseDumpManager.getLatestsChangeTimestamp();
            return ret;
        }
        catch ( JJWManagerException e )
        {
            return null;
        }
    }
}
