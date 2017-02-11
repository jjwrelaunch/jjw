package de.jjw.webapp.export;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import de.jjw.model.duo.Duoclass;
import de.jjw.model.fighting.Fightingclass;
import de.jjw.model.newa.NewaFighter;
import de.jjw.model.newa.Newaclass;
import de.jjw.service.DatabaseDumpManager;
import de.jjw.service.MedalRankingManager;
import de.jjw.service.PressInfoManager;
import de.jjw.service.duo.DuoTeamManager;
import de.jjw.service.duo.DuoclassManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.fighting.FightManager;
import de.jjw.service.fighting.FighterManager;
import de.jjw.service.fighting.FightingclassManager;
import de.jjw.service.modelWeb.DuoTeamWeb;
import de.jjw.service.modelWeb.FighterWeb;
import de.jjw.service.modelWeb.NewaFighterWeb;
import de.jjw.service.newa.NewaFighterManager;
import de.jjw.service.newa.NewaclassManager;
import de.jjw.webapp.pdf.MedalsPDF;
import de.jjw.webapp.pdf.PressInfoPDF;
import de.jjw.webapp.pdf.admin.AllClassesPDF;
import de.jjw.webapp.pdf.admin.JSONExportPDF;
import de.jjw.webapp.pdf.duo.DuoTeamResultsPDF;
import de.jjw.webapp.pdf.fighting.FastestFightPDF;
import de.jjw.webapp.pdf.fighting.FightingResultsPDF;
import de.jjw.webapp.pdf.newa.NewaResultsPDF;

public class ExportHelper
{

    public ByteArrayOutputStream zipAllExportData( HttpServletResponse response, FightManager fightManager,
                                                   FightingclassManager fightingclassManager,
                                                   DatabaseDumpManager databaseDumpManager,
                                                   DuoclassManager duoclassManager, FighterManager fighterManager,
                                                   DuoTeamManager duoTeamManager,
                                                   MedalRankingManager medalRankingManager,
                                                   PressInfoManager pressInfoManager, Locale local,
                                                   NewaclassManager newaclassManager,
                                                   NewaFighterManager newaFighterManager )
        throws IOException, JJWManagerException
    {

        String dump = databaseDumpManager.getDatabaseDump();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream( baos );
        ZipEntry entry = new ZipEntry( "dump.sql" );
        entry.setSize( dump.getBytes().length );
        zos.putNextEntry( entry );
        zos.write( dump.getBytes() );
        zos.closeEntry();
        // all classes
        entry = new ZipEntry( "classes.pdf" );

        Map<Integer, Fightingclass> fc = fightingclassManager.getAllFightingclasses( false );

        Map<Integer, Duoclass> dc = duoclassManager.getAllDuoclasses( false );

        Map<Integer, Newaclass> nc = newaclassManager.getAllNewaclasses( false );
        ByteArrayOutputStream baos_class =
            new AllClassesPDF( "de.jjw.webapp.messages.admin.fightingclass", "de.jjw.webapp.messages.admin.duoclass",
                               fc, dc, nc, local ).createAsNewDocAsBAOS( local );
        entry.setSize( baos_class.size() );
        zos.putNextEntry( entry );
        zos.write( baos_class.toByteArray() );
        baos_class.flush();
        baos_class.close();
        zos.closeEntry();

        // fight times
        entry = new ZipEntry( "fight_times.pdf" );
        ByteArrayOutputStream baos_times =
            new de.jjw.webapp.pdf.admin.FightTimesPDF( "de.jjw.webapp.messages.admin.statistic", response,
                                                       fightManager.getFightTimes() ).createContentAsBAOS();
        entry.setSize( baos_times.size() );
        zos.putNextEntry( entry );
        zos.write( baos_times.toByteArray() );
        baos_times.flush();
        baos_times.close();
        zos.closeEntry();

        // fighting results
        entry = new ZipEntry( "fighting_results.pdf" );
        ByteArrayOutputStream baosFightingResults =
            new FightingResultsPDF( "de.jjw.webapp.messages.fighter", response,
                                    fighterManager.getFighterResultList( local ), local ).createContentAsBAOS();

        entry.setSize( baosFightingResults.size() );
        zos.putNextEntry( entry );
        zos.write( baosFightingResults.toByteArray() );
        baosFightingResults.flush();
        baosFightingResults.close();
        zos.closeEntry();

        // fastest fights
        entry = new ZipEntry( "fast_fights.pdf" );
        ByteArrayOutputStream baosFastestFights =
            new FastestFightPDF( "de.jjw.webapp.messages.fighter", response, fightManager.getFastestFights(), local ).createContentAsBAOS();

        entry.setSize( baosFastestFights.size() );
        zos.putNextEntry( entry );
        zos.write( baosFastestFights.toByteArray() );
        baosFastestFights.flush();
        baosFastestFights.close();
        zos.closeEntry();

        // duo results
        entry = new ZipEntry( "duo_results.pdf" );
        ByteArrayOutputStream baosDuoResults =
            new DuoTeamResultsPDF( "de.jjw.webapp.messages.duoTeam", response,
                                   duoTeamManager.getDuoTeamResultList( local ), local ).createContentAsBAOS();

        entry.setSize( baosDuoResults.size() );
        zos.putNextEntry( entry );
        zos.write( baosDuoResults.toByteArray() );
        baosDuoResults.flush();
        baosDuoResults.close();
        zos.closeEntry();

        // newa results
        entry = new ZipEntry( "newa_results.pdf" );
        ByteArrayOutputStream baosNewaResults =
            new NewaResultsPDF( "de.jjw.webapp.messages.fighter", response,
                                newaFighterManager.getFighterResultList( local ), local ).createContentAsBAOS();

        entry.setSize( baosNewaResults.size() );
        zos.putNextEntry( entry );
        zos.write( baosNewaResults.toByteArray() );
        baosNewaResults.flush();
        baosNewaResults.close();
        zos.closeEntry();

        // medal ranking
        entry = new ZipEntry( "medalByTeam.pdf" );
        ByteArrayOutputStream baosmedals =
            new MedalsPDF( "de.jjw.webapp.messages.medalRanking", response,
                           medalRankingManager.getMedalRankingByTeam(), null, null ).createContentAsBAOS();

        entry.setSize( baosmedals.size() );
        zos.putNextEntry( entry );
        zos.write( baosmedals.toByteArray() );
        baosmedals.flush();
        baosmedals.close();
        zos.closeEntry();

        // medal ranking
        entry = new ZipEntry( "medalByRegion.pdf" );
        ByteArrayOutputStream baosmedalsRegion =
            new MedalsPDF( "de.jjw.webapp.messages.medalRanking", response,
                           medalRankingManager.getMedalRankingByRegion(), null, null ).createContentAsBAOS();

        entry.setSize( baosmedalsRegion.size() );
        zos.putNextEntry( entry );
        zos.write( baosmedalsRegion.toByteArray() );
        baosmedalsRegion.flush();
        baosmedalsRegion.close();
        zos.closeEntry();

        // medal ranking
        entry = new ZipEntry( "medalByCountry.pdf" );
        ByteArrayOutputStream baosmedalsCountry =
            new MedalsPDF( "de.jjw.webapp.messages.medalRanking", response,
                           medalRankingManager.getMedalRankingByRegion(), null, null ).createContentAsBAOS();

        entry.setSize( baosmedalsCountry.size() );
        zos.putNextEntry( entry );
        zos.write( baosmedalsCountry.toByteArray() );
        baosmedalsCountry.flush();
        baosmedalsCountry.close();
        zos.closeEntry();

        // press
        entry = new ZipEntry( "press.pdf" );
        ByteArrayOutputStream baospress =
            new PressInfoPDF( "de.jjw.webapp.messages.pressInfos", response, pressInfoManager.getPressInfo(), local ).createContentAsBAOS();

        entry.setSize( baospress.size() );
        zos.putNextEntry( entry );
        zos.write( baospress.toByteArray() );
        baospress.flush();
        baospress.close();
        zos.closeEntry();

        zos.close();
        return baos;
    }

    public ByteArrayOutputStream zipJSONExportData( HttpServletResponse response, String jSONString, Locale locale,
                                                    List<FighterWeb> fighters, List<DuoTeamWeb> duos,
                                                    List<NewaFighterWeb> newaFighters ) throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream( baos );
        ZipEntry entry = new ZipEntry( "jsonExport.jjw" );
        entry.setSize( jSONString.getBytes().length );
        zos.putNextEntry( entry );
        zos.write( jSONString.getBytes() );
        zos.closeEntry();
        // all classes
        entry = new ZipEntry( "export.pdf" );

        ByteArrayOutputStream baos_class =
            new JSONExportPDF( "de.jjw.webapp.messages.admin.statistic",response,
                               fighters, duos, newaFighters, locale ).createContentAsBAOS();
        entry.setSize( baos_class.size() );
        zos.putNextEntry( entry );
        zos.write( baos_class.toByteArray() );
        baos_class.flush();
        baos_class.close();
        zos.closeEntry();

        zos.close();
        return baos;
    }
}
