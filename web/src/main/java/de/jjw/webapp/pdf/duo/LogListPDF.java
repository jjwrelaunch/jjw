/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : LogListPDF.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:47
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

package de.jjw.webapp.pdf.duo;

import java.io.ByteArrayOutputStream;
import java.util.ResourceBundle;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import de.jjw.model.duo.DuoFight;
import de.jjw.model.duo.FriendlyDuoFight;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;

public class LogListPDF
    implements IValueConstants
{
    protected final Logger log = Logger.getRootLogger();

    ResourceBundle rb;

    PdfContentByte cb;

    DuoFight fight;

    String headLine = "";

    private HttpServletResponse response;

    public LogListPDF( ResourceBundle rb, PdfContentByte cb, DuoFight fight, String headLine )
    {
        this.rb = rb;
        this.cb = cb;
        this.fight = fight;
        if ( headLine != null )
        {
            this.headLine = headLine;
        }

    }

    public LogListPDF( String rb, String headLine, HttpServletResponse response )
    {
        this.rb = ResourceBundle.getBundle( rb, response.getLocale() );
        this.response = response;
        this.headLine = headLine;
    }

    public LogListPDF( String rb, String headLine, HttpServletResponse response, FriendlyDuoFight friendlyFight )
        throws Exception
    {
        this.rb = ResourceBundle.getBundle( rb, response.getLocale() );
        this.response = response;
        this.headLine = headLine;
        fight = new DuoFight();
        fight.setDuoTeamRed( friendlyFight.getDuoTeamRed() );
        fight.setDuoTeamBlue( friendlyFight.getDuoTeamBlue() );

        Document doc = new Document( PageSize.A4, _36, _36, _36, _36 );
        ByteArrayOutputStream baos = new ByteArrayOutputStream( 1024 );
        PdfWriter writer = PdfWriter.getInstance( doc, baos );
        doc.open();

        cb = writer.getDirectContent();
        createFightList( TypeUtil.STRING_DEFAULT, TypeUtil.STRING_DEFAULT, TypeUtil.STRING_DEFAULT );
        cb.stroke();

        doc.close();

        response.setContentLength( baos.size() );

        ServletOutputStream out = response.getOutputStream();
        baos.writeTo( out );
        baos.flush();
    }

    public void createFightListBlanco()
        throws Exception
    {
        Document doc = new Document( PageSize.A4, _36, _36, _36, _36 );
        ByteArrayOutputStream baos = new ByteArrayOutputStream( 1024 );
        PdfWriter writer = PdfWriter.getInstance( doc, baos );
        doc.open();

        cb = writer.getDirectContent();
        createFightList( TypeUtil.STRING_DEFAULT, TypeUtil.STRING_DEFAULT, TypeUtil.STRING_DEFAULT );
        cb.stroke();

        doc.close();

        response.setContentLength( baos.size() );

        ServletOutputStream out = response.getOutputStream();
        baos.writeTo( out );
        baos.flush();
    }

    /**
     * erstellen der Kampfliste
     */
    public void createFightList( String age, String weightClass, String fightNumber )
    {
        try
        {
            createFrames( cb );
            createStdText( cb );
            writeText( _56, 750, headLine, cb, BOOLEAN_TRUE, 22, _1 );
            writeText( _56, _685, rb.getString( "pdf.logList.age" ) + " " + age, cb, BOOLEAN_TRUE, _12, _1 );
            writeText( _56, 665, rb.getString( "pdf.logList.duoclass" ) + " " + weightClass, cb, BOOLEAN_TRUE, _12, _1 );
            writeText( _56, 645, rb.getString( "pdf.logList.fightNumber" ) + " " + fightNumber, cb, BOOLEAN_TRUE, _12,
                       _1 );
            // writeText(56,625,rb.getString ("pdf.logList.fightType")
            // + " "+ fightType,cb,BOOLEAN_TRUE,12,1);
            writeText( 24, 24, rb.getString( "pdf.copyright" ) + "", cb, BOOLEAN_FALSE, _8, _1 );

            if ( fight != null )
            {
                if ( fight.getDuoTeamRed() != null )
                {
                    writeText( _286, _635, correctOutput( fight.getDuoTeamRed().getName() + " / "
                        + fight.getDuoTeamRed().getName2() ), cb, BOOLEAN_TRUE, _10, _0 );
                    writeText( _286, _595, correctOutput( fight.getDuoTeamRed().getFirstname() + " / "
                        + fight.getDuoTeamRed().getFirstname2() ), cb, BOOLEAN_TRUE, _10, _0 );
                    writeText( _286, _555, correctOutput( fight.getDuoTeamRed().getTeam().getTeamName() ), cb,
                               BOOLEAN_TRUE, _10, _0 );
                }
                if ( fight.getDuoTeamBlue() != null )
                {
                    writeText( _286 + _180, _635, correctOutput( fight.getDuoTeamBlue().getName() + " / "
                        + fight.getDuoTeamBlue().getName2() ), cb, BOOLEAN_TRUE, _10, _0 );
                    writeText( _286 + _180, _595, correctOutput( fight.getDuoTeamBlue().getFirstname() + " / "
                        + fight.getDuoTeamBlue().getFirstname2() ), cb, BOOLEAN_TRUE, _10, _0 );
                    writeText( _286 + _180, _555, correctOutput( fight.getDuoTeamBlue().getTeam().getTeamName() ), cb,
                               BOOLEAN_TRUE, _10, _0 );
                }
            }
            return;
        }
        catch ( Exception e )
        {
            log.error( "logList.createFightList: Exception: \n " + e.toString(), e );
        }
    }

    /**
     * wenn str=="&nbsp;" dann wird "" zurückgegeben sonst str
     * 
     * @param str
     */
    private String correctOutput( String str )
    {
        if ( str.equalsIgnoreCase( "nbsp;" ) )
        {
            return "";
        }
        else
        {
            return str;
        }
    }

    /**
     * schreibt alle TextAusgaben, die fuer eine blanko Liste notwendig sind
     * 
     * @param cb PdfContentByte
     * @throws Exception
     */
    private void createStdText( PdfContentByte cb )
        throws Exception
    {
        writeText( _286, _685, rb.getString( "pdf.logList.red" ), cb, BOOLEAN_TRUE, _14, _0 );
        writeText( _286 + _180, _685, rb.getString( "pdf.logList.blue" ), cb, BOOLEAN_TRUE, _14, _0 );
        writeText( _198, _650, rb.getString( "pdf.logList.name" ), cb, BOOLEAN_FALSE, _8, _1 );
        writeText( _198 + _180, _650, rb.getString( "pdf.logList.name" ), cb, BOOLEAN_FALSE, _8, _1 );
        writeText( _198, _610, rb.getString( "pdf.logList.firstName" ), cb, BOOLEAN_FALSE, _8, _1 );
        writeText( _198 + _180, _610, rb.getString( "pdf.logList.firstName" ), cb, BOOLEAN_FALSE, _8, _1 );
        writeText( _198, _570, rb.getString( "pdf.logList.team" ), cb, BOOLEAN_FALSE, _8, _1 );
        writeText( _198 + _180, _570, rb.getString( "pdf.logList.team" ), cb, BOOLEAN_FALSE, _8, _1 );

        writeText( _60, 485, rb.getString( "pdf.logList.series1" ), cb, true, 14, 1 );
        writeText( _60, 405, rb.getString( "pdf.logList.series2" ), cb, true, 14, 1 );
        writeText( _60, 325, rb.getString( "pdf.logList.series3" ), cb, true, 14, 1 );
        writeText( _60, 245, rb.getString( "pdf.logList.series4" ), cb, true, 14, 1 );
        writeText( _60, 185, rb.getString( "pdf.logList.result" ), cb, true, 14, 1 );

        writeText( _56, 150, rb.getString( "pdf.logList.winner" ), cb, BOOLEAN_TRUE, _14, _1 );
        writeText( _56, 100, rb.getString( "pdf.logList.signatures" ), cb, BOOLEAN_TRUE, _14, _1 );
        return;
    }

    /**
     * verarbeitet die Ausgabe eines Textes an die gegebene Position mit der Größe size
     * 
     * @param x Koordinate
     * @param y Koordinate
     * @param text AusgabeText
     * @param cb PdfContentByte
     * @param bold Fettdruck
     * @param size Schriftgrösse
     * @param loc 0 für center, 1 für left
     * @throws Exception
     */
    private void writeText( int x, int y, String text, PdfContentByte cb, boolean bold, int size, int loc )
        throws Exception
    {
        BaseFont bf;
        try
        {
            if ( bold )
            {
                bf = BaseFont.createFont( BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
            }
            else
            {
                bf = BaseFont.createFont( BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
            }

            cb.stroke();
            cb.beginText();
            cb.setFontAndSize( bf, size );
            if ( loc == _0 )
            {
                cb.showTextAligned( PdfContentByte.ALIGN_CENTER, text, x, y, _0 );
            }
            if ( loc == _1 )
            {
                cb.showTextAligned( PdfContentByte.ALIGN_LEFT, text, x, y, _0 );
            }
            cb.endText();
        }
        catch ( Exception e )
        {
            log.error( "logList.writeText: Exception: \n can not create pdf document \n" + e.getMessage(), e );
        }
        return;
    }

    /**
     * baut die Rahmenvorlage für die KampfListe
     * 
     * @param cb PdfContentByte
     */
    private void createFrames( PdfContentByte cb )
    {
        try
        {
            cb.rectangle( 56, 460, 140, 60 );
            cb.rectangle( 56, 380, 140, 60 );
            cb.rectangle( 56, 300, 140, 60 );
            cb.rectangle( 56, 220, 140, 60 );

            cb.rectangle( 56, 180, 140, 20 );
            for ( int i = 0; i < 2; i++ )
            {
                cb.rectangle( i * 180 + 196, 660, 180, 40 );
                cb.rectangle( i * 180 + 196, 620, 180, 40 );
                cb.rectangle( i * 180 + 196, 580, 180, 40 );
                cb.rectangle( i * 180 + 196, 540, 180, 40 );

                cb.rectangle( i * 180 + 196, 460, 180, 60 );
                cb.rectangle( i * 180 + 196, 380, 180, 60 );
                cb.rectangle( i * 180 + 196 + 150, 460, 30, 30 );
                cb.rectangle( i * 180 + 196 + 150, 380, 30, 30 );
                cb.rectangle( i * 180 + 196, 300, 180, 60 );
                cb.rectangle( i * 180 + 196, 220, 180, 60 );
                cb.rectangle( i * 180 + 196 + 150, 300, 30, 30 );
                cb.rectangle( i * 180 + 196 + 150, 220, 30, 30 );
                cb.rectangle( i * 180 + 196, 180, 180, 20 );
            }
            createPointsFrame( cb, 196, 500 );
            createPointsFrame( cb, 180 + 196, 480 );
            createPointsFrame( cb, 196, 400 );
            createPointsFrame( cb, 180 + 196, 420 );
            createPointsFrame( cb, 196, 340 );
            createPointsFrame( cb, 180 + 196, 320 );
            createPointsFrame( cb, 196, 240 );
            createPointsFrame( cb, 180 + 196, 260 );

            cb.stroke();
        }
        catch ( Exception e )
        {
            log.error( "logList.createFrames: Exception: \ncan not create pdf document \n" + e.getMessage(), e );
        }
        return;
    }

    private void createPointsFrame( PdfContentByte cb, int x, int y )
    {
        cb.rectangle( x, y, 25, 20 );
        cb.rectangle( x + 25, y, 25, 20 );
        cb.rectangle( x + 50, y, 25, 20 );
        cb.rectangle( x + 75, y, 25, 20 );
        cb.rectangle( x + 100, y, 25, 20 );
        cb.stroke();
    }

}
