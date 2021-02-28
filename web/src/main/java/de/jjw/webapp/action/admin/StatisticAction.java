/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : RegionAction.java
 * Created : 23 Jul 2010
 * Last Modified: Fr, 23 Jul 2010 20:55:45
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

package de.jjw.webapp.action.admin;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import de.jjw.model.duo.DuoTeam;
import de.jjw.model.duo.Duoclass;
import de.jjw.model.fighting.Fighter;
import de.jjw.model.fighting.Fightingclass;
import de.jjw.model.newa.NewaFighter;
import de.jjw.model.newa.Newaclass;
import de.jjw.service.DatabaseDumpManager;
import de.jjw.service.MedalRankingManager;
import de.jjw.service.PressInfoManager;
import de.jjw.service.duo.DuoFightManager;
import de.jjw.service.duo.DuoTeamManager;
import de.jjw.service.duo.DuoclassManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.fighting.FightManager;
import de.jjw.service.fighting.FighterManager;
import de.jjw.service.fighting.FightingclassManager;
import de.jjw.service.modelWeb.DuoTeamWeb;
import de.jjw.service.modelWeb.FighterWeb;
import de.jjw.service.modelWeb.NewaFighterWeb;
import de.jjw.service.newa.NewaFightManager;
import de.jjw.service.newa.NewaFighterManager;
import de.jjw.service.newa.NewaclassManager;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.export.DatabaseExport;
import de.jjw.webapp.export.ExportHelper;
import de.jjw.webapp.filter.StatisticThread;
import de.jjw.webapp.pdf.admin.AllClassesPDF;
import de.jjw.webapp.pdf.duo.DuoDPoolPDF;
import de.jjw.webapp.pdf.duo.DuoKo16ComplexListPDF;
import de.jjw.webapp.pdf.duo.DuoKo32ComplexListPDF;
import de.jjw.webapp.pdf.duo.DuoPoolPDF;
import de.jjw.webapp.pdf.duo.DuoTeamResultsPDF;
import de.jjw.webapp.pdf.duo.HighestDuoPointsPDF;
import de.jjw.webapp.pdf.fighting.FastestFightPDF;
import de.jjw.webapp.pdf.fighting.FightingDPoolPDF;
import de.jjw.webapp.pdf.fighting.FightingKo16ComplexListPDF;
import de.jjw.webapp.pdf.fighting.FightingKo32ComplexListPDF;
import de.jjw.webapp.pdf.fighting.FightingKo64ComplexListPDF;
import de.jjw.webapp.pdf.fighting.FightingPoolPDF;
import de.jjw.webapp.pdf.fighting.FightingResultsPDF;
import de.jjw.webapp.pdf.fighting.FightingVisualizelProtokollPDF;
import de.jjw.webapp.pdf.fighting.LogListPDF;
import de.jjw.webapp.pdf.newa.FastestNewaFightPDF;
import de.jjw.webapp.pdf.newa.NewaResultsPDF;
import de.jjw.webapp.util.ConfigMain;


public class StatisticAction
    extends BasePage
    implements IGlobalWebConstants
{

    private FightManager fightManager;

    private FighterManager fighterManager;

    private FightingclassManager fightingclassManager;

    private DuoTeamManager duoTeamManager;

    private DuoclassManager duoclassManager;

    private DuoFightManager duoFightManager;

    private DatabaseDumpManager databaseDumpManager;

    private MedalRankingManager medalRankingManager;

    private PressInfoManager pressInfoManager;

    private NewaFightManager newaFightManager;

    private NewaFighterManager newaFighterManager;

    private NewaclassManager newaclassManager;

    public String FightTimesPDF()
    {
        HttpServletResponse response = getResponseWithPDFHeader();
        try
        {
            new de.jjw.webapp.pdf.admin.FightTimesPDF( "de.jjw.webapp.messages.admin.statistic", response,
                                                       fightManager.getFightTimes() ).createContent();
            getFacesContext().responseComplete();
        }
        catch ( Exception e )
        {
            log.error( "StatisticAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;
    }


    public String AllClasses()
    {
        HttpServletResponse response = getResponseWithPDFHeader();
        try
        {
            Map<Integer, Fightingclass> fc = fightingclassManager.getAllFightingclasses( false );

            Map<Integer, Duoclass> dc = duoclassManager.getAllDuoclasses( false );

            Map<Integer, Newaclass> nc = newaclassManager.getAllNewaclasses( false );

            new AllClassesPDF( "de.jjw.webapp.messages.admin.fightingclass", "de.jjw.webapp.messages.admin.duoclass", "de.jjw.webapp.messages.admin.newaclass",
                               fc, dc, nc, getLocale() ).createAsNewDoc( response );
            getFacesContext().responseComplete();
        }
        catch ( Exception e )
        {
            log.error( "StatisticAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;
    }
    
    public String showFightingclassAsPdf()
    {
        HttpServletResponse response = getResponseWithPDFHeader();
        try
        {
            new FightingResultsPDF( "de.jjw.webapp.messages.fighter", response, fighterManager.getFighterResultList( getLocale() ), getLocale() ).showResults();

            getFacesContext().responseComplete();
        }
        catch ( Exception e )
        {
            log.error( "FightingclassOverviewAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;
    }

    public String showDuoResultAsPdf()
    {
        HttpServletResponse response = getResponseWithPDFHeader();
        try
        {
            new DuoTeamResultsPDF( "de.jjw.webapp.messages.duoTeam", response, duoTeamManager.getDuoTeamResultList( getLocale() ), getLocale() ).showResults();

            getFacesContext().responseComplete();
        }
        catch ( Exception e )
        {
            log.error( "FightingclassOverviewAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;
    }


    private HttpServletResponse getResponseWithPDFHeader()
    {
        HttpServletResponse response = (HttpServletResponse) getFacesContext().getExternalContext().getResponse();

        response.setHeader( "Expires", "0" );
        response.setHeader( "Cache-Control", "must-revalidate, post-check=0, pre-check=0" );
        response.setHeader( "Pragma", "public" );
        response.setContentType( "application/pdf" );
        return response;
    }
    
    public String showNewaFightingclassAsPdf()
    {
        HttpServletResponse response = getResponseWithPDFHeader();
        try
        {
            new NewaResultsPDF( "de.jjw.webapp.messages.fighter", response, newaFighterManager.getFighterResultList( getLocale() ), getLocale() ).showResults();

            getFacesContext().responseComplete();
        }
        catch ( Exception e )
        {
            log.error( "FightingclassOverviewAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;
    }



    public String blancoFightlist()
    {
        HttpServletResponse response = getResponseWithPDFHeader();
        try
        {
            new LogListPDF( "de.jjw.webapp.messages.admin.fightingclass",
                            ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(), response ).createFightListBlanco();
            getFacesContext().responseComplete();
        }
        catch ( Exception e )
        {
            log.error( "StatisticAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;
    }

    public String blancoDuoFightlist()
    {
        HttpServletResponse response = getResponseWithPDFHeader();
        try
        {
            new de.jjw.webapp.pdf.duo.LogListPDF( "de.jjw.webapp.messages.admin.duoclass",
                                                  ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(),
                                                  response ).createFightListBlanco();
            getFacesContext().responseComplete();
        }
        catch ( Exception e )
        {
            log.error( "StatisticAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;
    }
    
    public String blancoNewaFightlist()
    {
        HttpServletResponse response = getResponseWithPDFHeader();
        try
        {
            new de.jjw.webapp.pdf.newa.NewaLogListPDF( "de.jjw.webapp.messages.admin.newaclass",
                            ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(), response ).createFightListBlanco();
            getFacesContext().responseComplete();
        }
        catch ( Exception e )
        {
            log.error( "StatisticAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;
    }


    public String blancoFightingPool()
    {
        HttpServletResponse response = getResponseWithPDFHeader();
        try
        {
            new FightingPoolPDF( "de.jjw.webapp.messages.admin.fightingclass", response,
                                 ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(), getLocale() ).createPoolBlanco();
            getFacesContext().responseComplete();
        }
        catch ( Exception e )
        {
            log.error( "StatisticAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;
    }

    public String blancoDuoPool()
    {
        HttpServletResponse response = getResponseWithPDFHeader();
        try
        {
            new DuoPoolPDF( "de.jjw.webapp.messages.admin.duoclass", response,
                            ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(), getLocale() ).createPoolBlanco();
            getFacesContext().responseComplete();
        }
        catch ( Exception e )
        {
            log.error( "StatisticAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;
    }

    public String blancoFightingDPool()
    {
        HttpServletResponse response = getResponseWithPDFHeader();
        try
        {
            new FightingDPoolPDF( "de.jjw.webapp.messages.admin.fightingclass", response,
                                  ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(), getLocale() ).createPoolBlanco();
            getFacesContext().responseComplete();
        }
        catch ( Exception e )
        {
            log.error( "StatisticAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;
    }

    public String blancoDuoDPool()
    {
        HttpServletResponse response = getResponseWithPDFHeader();
        try
        {
            new DuoDPoolPDF( "de.jjw.webapp.messages.admin.duoclass", response,
                             ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(), getLocale() ).createPoolBlanco();
            getFacesContext().responseComplete();
        }
        catch ( Exception e )
        {
            log.error( "StatisticAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;
    }

    public String blancoFightingKO16()
    {
        HttpServletResponse response = getResponseWithPDFHeader();
        try
        {
            new FightingKo16ComplexListPDF( "de.jjw.webapp.messages.admin.fightingclass", response,
                                            ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(),
                                            getLocale() ).createPoolBlanco();
            getFacesContext().responseComplete();
        }
        catch ( Exception e )
        {
            log.error( "StatisticAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;
    }

    public String blancoDuoKO16()
    {
        HttpServletResponse response = getResponseWithPDFHeader();
        try
        {
            new DuoKo16ComplexListPDF( "de.jjw.webapp.messages.admin.duoclass", response,
                                       ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(), getLocale() ).createPoolBlanco();
            getFacesContext().responseComplete();
        }
        catch ( Exception e )
        {
            log.error( "StatisticAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;
    }

    public String blancoFightingKO32()
    {
        HttpServletResponse response = getResponseWithPDFHeader();
        try
        {
            new FightingKo32ComplexListPDF( "de.jjw.webapp.messages.admin.fightingclass", response,
                                            ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(),
                                            getLocale() ).createPoolBlanco();
            getFacesContext().responseComplete();
        }
        catch ( Exception e )
        {
            log.error( "StatisticAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;
    }

    public String blancoDuoKO32()
    {
        HttpServletResponse response = getResponseWithPDFHeader();
        try
        {
            new DuoKo32ComplexListPDF( "de.jjw.webapp.messages.admin.duoclass", response,
                                       ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(), getLocale() ).createPoolBlanco();
            getFacesContext().responseComplete();
        }
        catch ( Exception e )
        {
            log.error( "StatisticAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;
    }

    public String blancoFightingKO64()
    {
        HttpServletResponse response = getResponseWithPDFHeader();
        try
        {
            new FightingKo64ComplexListPDF( "de.jjw.webapp.messages.admin.fightingclass", response,
                                            ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1(),
                                            getLocale() ).createPoolBlanco();
            getFacesContext().responseComplete();
        }
        catch ( Exception e )
        {
            log.error( "StatisticAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;
    }

    public String fastFight()
    {
        HttpServletResponse response = getResponseWithPDFHeader();
        try
        {
            new FastestFightPDF( "de.jjw.webapp.messages.fighter", response, fightManager.getFastestFights(),
                                 getLocale() ).show();
            getFacesContext().responseComplete();
        }
        catch ( Exception e )
        {
            log.error( "StatisticAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;
    }

    public String fastNewaFight()
    {
        HttpServletResponse response = getResponseWithPDFHeader();
        try
        {
            new FastestNewaFightPDF( "de.jjw.webapp.messages.fighter", response, newaFightManager.getFastestFights(),
                                     getLocale() ).show();
            getFacesContext().responseComplete();
        }
        catch ( Exception e )
        {
            log.error( "StatisticAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;
    }

    public String highestDuoPoints()
    {
        HttpServletResponse response = getResponseWithPDFHeader();
        try
        {
            new HighestDuoPointsPDF( "de.jjw.webapp.messages.duoTeam", response, duoFightManager.getHighestDuoPoints(),
                                     getLocale() ).show();
            getFacesContext().responseComplete();
        }
        catch ( Exception e )
        {
            log.error( "StatisticAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;
    }

    public String dumpDB()
    {
        HttpServletResponse response = (HttpServletResponse) getFacesContext().getExternalContext().getResponse();

        response.setHeader( "Expires", "0" );
        response.setHeader( "Cache-Control", "must-revalidate, post-check=0, pre-check=0" );
        response.setHeader( "Pragma", "public" );
        response.setContentType( "application/zip" );

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

            response.setContentLength( baos.size() );

            ServletOutputStream out = response.getOutputStream();
            baos.writeTo( out );
            baos.flush();

            getFacesContext().responseComplete();
        }
        catch ( Exception e )
        {
            log.error( "StatisticAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;
    }

    /**
     * send database to the cloud
     * 
     * @return
     */
    public String sendExport()
    {
        try
        {
            if ( !databaseDumpManager.isEventOver() )
            {
                addErrorElement( new ErrorElement( ADMIN_STATISTIC_EVENT_NOT_OVER ) );
                return null;
            }
        }
        catch ( JJWManagerException e )
        {
            log.error( e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
            return null;
        }

        DatabaseExport dbe =
            new DatabaseExport( WebApplicationContextUtils.getWebApplicationContext( getServletContext() ), null );
        if ( dbe.sendDatabase( null ) && dbe.getReturnCode() == DatabaseExport.RETURN_SUCCESS )
            addErrorElement( new ErrorElement( ADMIN_STATISTIC_SUCCESSFULL ) );
        else
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        return null;
    }

    public String eventExport()
    {
        HttpServletResponse response = (HttpServletResponse) getFacesContext().getExternalContext().getResponse();

        response.setHeader( "Expires", "0" );
        response.setHeader( "Cache-Control", "must-revalidate, post-check=0, pre-check=0" );
        response.setHeader( "Pragma", "public" );
        response.setContentType( "application/zip" );
        response.setHeader( "Content-Disposition", "attachment; filename=\"export.zip\"" );

        try
        {
            ByteArrayOutputStream baos =
                new ExportHelper().zipAllExportData( response, fightManager, fightingclassManager, databaseDumpManager,
                                                     duoclassManager, fighterManager, duoTeamManager,
                                                     medalRankingManager, pressInfoManager, getLocale(),
                                                     newaclassManager, newaFighterManager );

            response.setContentLength( baos.size() );

            ServletOutputStream out = response.getOutputStream();
            baos.writeTo( out );
            baos.flush();

            getFacesContext().responseComplete();

            if ( databaseDumpManager.isEventOver() )
            {
                StatisticThread sThread =
                    new StatisticThread( WebApplicationContextUtils.getWebApplicationContext( getServletContext() ),
                                         true, baos );

                Thread runThread = new Thread( sThread, "StatisticThread" );
                runThread.setDaemon( true );
                runThread.start();
            }
        }
        catch ( Exception e )
        {
            log.error( "StatisticAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;
    }
    
    public String gotoMarkFighterForExport(){
        return "markFighterForExport";
    }
    
    public String gotoMarkDuoForExport(){
        return "markDuoForExport";
    }
    
    public String gotoMarkNewaForExport(){
        return "markNewaForExport";
    }
    
    public String extortToJSON()
    {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        Gson gson = builder.create();
        JsonObject jsonExport = new JsonObject();

        JsonObject jsonObject = new JsonObject();

        JsonArray jsonFighters = new JsonArray();
        JsonArray jsonDuos = new JsonArray();
        try
        {

            JsonArray jsonNewaFighters = new JsonArray();
            List<FighterWeb> fighters = fighterManager.getFightersForJSONExport( getLocale() );
            List<DuoTeamWeb> duos = duoTeamManager.getDuosForJSONExport( getLocale() );
            List<NewaFighterWeb> newaFighters = newaFighterManager.getFightersForJSONExport( getLocale() );

            for ( FighterWeb item : fighters )
            {
                jsonObject = new JsonObject();
                jsonObject.addProperty( "name", item.getName() );
                jsonObject.addProperty( "firstname", item.getFirstname() );
                jsonObject.addProperty( "gender", item.getSex() );
                jsonObject.addProperty( "yearOfBirth", item.getDayOfBirth() +"."+ item.getMonthOfBirth()+"."+ item.getYearOfBirth() );
                jsonObject.addProperty( "teamId", item.getTeam().getId() );
                jsonObject.addProperty( "team", item.getTeam().getTeamName() );
                jsonObject.addProperty( "weight", item.getWeight() );
                jsonFighters.add( jsonObject );
            }

            for ( DuoTeamWeb item : duos )
            {
                jsonObject = new JsonObject();
                jsonObject.addProperty( "name", item.getName() );
                jsonObject.addProperty( "firstname", item.getFirstname() );
                jsonObject.addProperty( "name2", item.getName2() );
                jsonObject.addProperty( "firstname2", item.getFirstname2() );
                jsonObject.addProperty( "gender", item.getSex() );
                jsonObject.addProperty( "yearOfBirth", item.getDayOfBirth() +"."+ item.getMonthOfBirth()+"."+item.getYearOfBirth() );
                jsonObject.addProperty( "yearOfBirth2",item.getDayOfBirth2() +"."+ item.getMonthOfBirth2()+"."+ item.getYearOfBirth2() );
                jsonObject.addProperty( "teamId", item.getTeam().getId() );
                jsonObject.addProperty( "team", item.getTeam().getTeamName() );
                jsonDuos.add( jsonObject );
            }

            for ( NewaFighterWeb item : newaFighters )
            {
                jsonObject = new JsonObject();
                jsonObject.addProperty( "name", item.getName() );
                jsonObject.addProperty( "firstname", item.getFirstname() );
                jsonObject.addProperty( "gender", item.getSex() );
                jsonObject.addProperty( "yearOfBirth", item.getDayOfBirth() +"."+ item.getMonthOfBirth()+"."+ item.getYearOfBirth() );
                jsonObject.addProperty( "teamId", item.getTeam().getId() );
                jsonObject.addProperty( "team", item.getTeam().getTeamName() );
                jsonObject.addProperty( "weight", item.getWeight() );
                jsonNewaFighters.add( jsonObject );
            }

            if ( !TypeUtil.isEmptyOrDefault( jsonFighters.size() ) )
                jsonExport.add( "fighter", jsonFighters );
            if ( !TypeUtil.isEmptyOrDefault( jsonDuos.size() ) )
                jsonExport.add( "duo", jsonDuos );
            if ( !TypeUtil.isEmptyOrDefault( jsonNewaFighters.size() ) )
                jsonExport.add( "newaza", jsonNewaFighters );

            String exportString = gson.toJson( jsonExport );
            
            HttpServletResponse response = (HttpServletResponse) getFacesContext().getExternalContext().getResponse();

            response.setHeader( "Expires", "0" );
            response.setHeader( "Cache-Control", "must-revalidate, post-check=0, pre-check=0" );
            response.setHeader( "Pragma", "public" );
            response.setContentType( "application/zip" );
            response.setHeader( "Content-Disposition", "attachment; filename=\"export.zip\"" );

            ByteArrayOutputStream baos =
                new ExportHelper().zipJSONExportData( response, exportString, getLocale(), fighters, duos, newaFighters );

            response.setContentLength( baos.size() );

            ServletOutputStream out = response.getOutputStream();
            baos.writeTo( out );
            baos.flush();

            getFacesContext().responseComplete();
        }
        catch ( Exception e )
        {
            log.error( "StatisticAction.extortToJSON()", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }

        return null;
    }
    
    
    public String visualizeAllFights()
    {
        HttpServletResponse response = getResponseWithPDFHeader();
        try
        {
            new FightingVisualizelProtokollPDF( "de.jjw.webapp.messages.fighter", response, getLocale() ).visualizeAllProtokoll( fightManager.getFightsFromFighter() );
            getFacesContext().responseComplete();
        }
        catch ( Exception e )
        {
            log.error( "StatisticAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;
    }

    public FightManager getFightManager()
    {
        return fightManager;
    }

    public void setFightManager( FightManager fightManager )
    {
        this.fightManager = fightManager;
    }

    public FightingclassManager getFightingclassManager()
    {
        return fightingclassManager;
    }

    public void setFightingclassManager( FightingclassManager fightingclassManager )
    {
        this.fightingclassManager = fightingclassManager;
    }

    public DuoclassManager getDuoclassManager()
    {
        return duoclassManager;
    }

    public void setDuoclassManager( DuoclassManager duoclassManager )
    {
        this.duoclassManager = duoclassManager;
    }

    /**
     * @return the databaseDumpManager
     */
    public DatabaseDumpManager getDatabaseDumpManager()
    {
        return databaseDumpManager;
    }

    /**
     * @param databaseDumpManager the databaseDumpManager to set
     */
    public void setDatabaseDumpManager( DatabaseDumpManager databaseDumpManager )
    {
        this.databaseDumpManager = databaseDumpManager;
    }

    /**
     * @return the fighterManager
     */
    public FighterManager getFighterManager()
    {
        return fighterManager;
    }

    /**
     * @param fighterManager the fighterManager to set
     */
    public void setFighterManager( FighterManager fighterManager )
    {
        this.fighterManager = fighterManager;
    }

    /**
     * @return the duoTeamManager
     */
    public DuoTeamManager getDuoTeamManager()
    {
        return duoTeamManager;
    }

    /**
     * @param duoTeamManager the duoTeamManager to set
     */
    public void setDuoTeamManager( DuoTeamManager duoTeamManager )
    {
        this.duoTeamManager = duoTeamManager;
    }

    /**
     * @return the medalRankingManager
     */
    public MedalRankingManager getMedalRankingManager()
    {
        return medalRankingManager;
    }

    /**
     * @param medalRankingManager the medalRankingManager to set
     */
    public void setMedalRankingManager( MedalRankingManager medalRankingManager )
    {
        this.medalRankingManager = medalRankingManager;
    }

    /**
     * @return the pressInfoManager
     */
    public PressInfoManager getPressInfoManager()
    {
        return pressInfoManager;
    }

    /**
     * @param pressInfoManager the pressInfoManager to set
     */
    public void setPressInfoManager( PressInfoManager pressInfoManager )
    {
        this.pressInfoManager = pressInfoManager;
    }

    /**
     * @param newaFightManager the newaFightManager to set
     */
    public void setNewaFightManager( NewaFightManager newaFightManager )
    {
        this.newaFightManager = newaFightManager;
    }

    /**
     * @param newaFighterManager the newaFighterManager to set
     */
    public void setNewaFighterManager( NewaFighterManager newaFighterManager )
    {
        this.newaFighterManager = newaFighterManager;
    }

    /**
     * @param newaclassManager the newaclassManager to set
     */
    public void setNewaclassManager( NewaclassManager newaclassManager )
    {
        this.newaclassManager = newaclassManager;
    }

    /**
     * @param duofightManager the duofightManager to set
     */
    public void setDuoFightManager( DuoFightManager duoFightManager )
    {
        this.duoFightManager = duoFightManager;
    }

}
