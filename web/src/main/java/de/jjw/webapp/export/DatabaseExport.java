package de.jjw.webapp.export;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import de.jjw.model.admin.ConfigJJW;
import de.jjw.service.DatabaseDumpManager;
import de.jjw.service.MedalRankingManager;
import de.jjw.service.PressInfoManager;
import de.jjw.service.admin.ConfigManager;
import de.jjw.service.duo.DuoTeamManager;
import de.jjw.service.duo.DuoclassManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.fighting.FightManager;
import de.jjw.service.fighting.FighterManager;
import de.jjw.service.fighting.FightingclassManager;
import de.jjw.service.newa.NewaFighterManager;
import de.jjw.service.newa.NewaclassManager;

public class DatabaseExport
{
    protected final Logger log = Logger.getRootLogger();

    private ApplicationContext context = null;

    private DatabaseDumpManager databaseDumpManager = null;

    private ConfigManager configManager = null;

    private ByteArrayOutputStream allZippedExport = null;

    public static int RETURN_SUCCESS = 0;

    public static int RETURN_NOT_SEND = 1;

    public static int RETURN_ONLY_DUMP_SEND = 2;

    public int returnCode = RETURN_SUCCESS;

    public DatabaseExport( ApplicationContext context, ByteArrayOutputStream allZippedExport )
    {
        this.context = context;
        this.allZippedExport = allZippedExport;
    }


    public boolean sendDatabase( String url )
    {
        HttpClient client = null;
        HttpPost httppost = null;
        boolean ret = false;

        try
        {
            if ( configManager == null )
                configManager = (ConfigManager) context.getBean( "configManager" );
            ConfigJJW config = configManager.getConfig();
            Timestamp latestChange = getLatestsChangeTimestamp();

            client = new DefaultHttpClient();
            // HttpPost httppost = new HttpPost( "http://localhost:8888/jjwCloud2/stat.do" );
            if ( url == null )
                httppost = new HttpPost( "http://ju-jutsu-web2.appspot.com/stat.do" );
            // httppost = new HttpPost( "http://localhost:8888/stat.do" );
            else
                httppost = new HttpPost( url );

            MultipartEntity reqEntity = new MultipartEntity();
            ByteArrayBody bb = new ByteArrayBody( getZippedDump().toByteArray(), "dump.txt" );
            ByteArrayOutputStream allZippedBaos = getAllZippedData();

            // DatabaseDumpManager databaseDumpManager = (DatabaseDumpManager) context.getBean( "databaseDumpManager" );

            reqEntity.addPart( "EventDate",
                               new StringBody( ( null == config.getEventDate() ) ? "" : config.getEventDate() ) );

            reqEntity.addPart( "EventLocation",
                               new StringBody( ( null == config.getEventLocation() ) ? "" : config.getEventLocation() ) );

            reqEntity.addPart( "EventName",
                               new StringBody( ( null == config.getEventName() ) ? "" : config.getEventName() ) );

            reqEntity.addPart( "PdfHeadLine1",
                               new StringBody( ( null == config.getPdfHeadLine1() ) ? "" : config.getPdfHeadLine1() ) );

            reqEntity.addPart( "PdfHeadLine2",
                               new StringBody( ( null == config.getPdfHeadLine2() ) ? "" : config.getPdfHeadLine2() ) );

            reqEntity.addPart( "WebsiteHeadLine1", new StringBody( ( null == config.getWebsiteHeadLine1() ) ? ""
                            : config.getWebsiteHeadLine1() ) );

            reqEntity.addPart( "WebsiteHeadLine2", new StringBody( ( null == config.getWebsiteHeadLine2() ) ? ""
                            : config.getWebsiteHeadLine2() ) );

            if ( latestChange != null )
            {
                reqEntity.addPart( "lastChange", new StringBody( "LastChange " ) );
                reqEntity.addPart( "lastChangeTime", new StringBody( String.valueOf( latestChange.getTime() ) ) );
                reqEntity.addPart( "lastChangeTimeTextFormat",
                                   new StringBody( new SimpleDateFormat( "dd.MM.yyyy, HH:mm" ).format( latestChange ) ) );
            }
            else
            {
                reqEntity.addPart( "lastChange", new StringBody( "noLastChange " ) );
                reqEntity.addPart( "lastChangeTime", new StringBody( "" ) );
            }
            // set only dump
            reqEntity.addPart( "dump", bb );

            // complete export
            if ( allZippedBaos != null )
            {
                ByteArrayBody all = new ByteArrayBody( allZippedBaos.toByteArray(), "allData.zip" );
                reqEntity.addPart( "allExport", all );
            }

            httppost.setEntity( reqEntity );

            // System.out.println( "executing request " + httppost.getRequestLine() );
            HttpResponse response = client.execute( httppost );
            HttpEntity resEntity = response.getEntity();

            EntityUtils.consume( resEntity );
            client.getConnectionManager().shutdown();
            if ( allZippedBaos != null )
                setReturnCode( RETURN_SUCCESS );
            else
                setReturnCode( RETURN_ONLY_DUMP_SEND );
            ret = true;
        }

        catch ( MalformedURLException e )
        {
            log.error( "could not send dump", e );
            setReturnCode( RETURN_NOT_SEND );
        }
        catch ( IOException e )
        {
            if ( e instanceof java.net.UnknownHostException )
            {
                log.error( "could not find destination" );
            }
            else
            {
                log.error( "could not send dump", e );
            }
            setReturnCode( RETURN_NOT_SEND );
        }
        catch ( JJWManagerException e )
        {
            log.error( "could not send dump", e );
            setReturnCode( RETURN_NOT_SEND );
        }
        catch ( Exception e )
        {
            log.error( "could not send dump", e );
            setReturnCode( RETURN_NOT_SEND );
        }
        return ret;
    }

    private ByteArrayOutputStream getAllZippedData()
    {
        if ( allZippedExport != null )
            return allZippedExport;
        else
        {
            try
            {
                return new ExportHelper().zipAllExportData( null,
                                                            (FightManager) context.getBean( "fightManager" ),
                                                            (FightingclassManager) context.getBean( "fightingclassManager" ),
                                                            (DatabaseDumpManager) context.getBean( "databaseDumpManager" ),
                                                            (DuoclassManager) context.getBean( "duoclassManager" ),
                                                            (FighterManager) context.getBean( "fighterManager" ),
                                                            (DuoTeamManager) context.getBean( "duoTeamManager" ),
                                                            (MedalRankingManager) context.getBean( "medalRankingManager" ),
                                                            (PressInfoManager) context.getBean( "pressInfoManager" ),
                                                            Locale.GERMAN,
                                                            (NewaclassManager) context.getBean( "newaclassManager" ),
                                                            (NewaFighterManager) context.getBean( "newaFighterManager" ) );
            }
            catch ( Exception e )
            {
                log.error( "can not send Data ...", e );
            }
        }
        return null;
    }

    private ByteArrayOutputStream getZippedDump()
        throws JJWManagerException, IOException
    {
        if ( databaseDumpManager == null )
            databaseDumpManager = (DatabaseDumpManager) context.getBean( "databaseDumpManager" );
        try
        {
            String dump = databaseDumpManager.getDatabaseDump();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream( baos );
            ZipEntry entry = new ZipEntry( "dump.sql" );
            entry.setSize( dump.getBytes().length );
            zos.putNextEntry( entry );
            zos.write( dump.getBytes() );
            zos.closeEntry();
            zos.close();
            return baos;
        }
        catch ( JJWManagerException e )
        {
            log.error( "can't read dump", e );
            throw e;
        }
        catch ( IOException e )
        {
            log.error( "can't zip dump", e );
            throw e;
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

    /**
     * @return the allZippedExport
     */
    public ByteArrayOutputStream getAllZippedExport()
    {
        return allZippedExport;
    }

    /**
     * @param allZippedExport the allZippedExport to set
     */
    public void setAllZippedExport( ByteArrayOutputStream allZippedExport )
    {
        this.allZippedExport = allZippedExport;
    }

    /**
     * @return the returnCode
     */
    public int getReturnCode()
    {
        return returnCode;
    }

    /**
     * @param returnCode the returnCode to set
     */
    private void setReturnCode( int returnCode )
    {
        this.returnCode = returnCode;
    }

}
