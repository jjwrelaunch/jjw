/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : LogListPDF.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:42
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

package de.jjw.webapp.pdf.fighting;

import java.io.ByteArrayOutputStream;
import java.util.ResourceBundle;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import de.jjw.model.fighting.Fight;
import de.jjw.model.fighting.FriendlyFight;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;

public class LogListPDF
    implements IValueConstants
{

    ResourceBundle rb;

    PdfContentByte cb;

    Fight fight;

    String headLine = "";

    private HttpServletResponse response;

    public LogListPDF( ResourceBundle rb, PdfContentByte cb, Fight fight, String headLine )
    {
        this.rb = rb;
        this.cb = cb;
        this.fight = fight;
        if ( headLine != null )
        {
            this.headLine = headLine;
        }

    }

    public LogListPDF( String rb, String headLine, HttpServletResponse response, FriendlyFight friendlyFight )
        throws Exception
    {
        this.rb = ResourceBundle.getBundle( rb, response.getLocale() );
        this.response = response;
        this.headLine = headLine;
        // fake the fight
        fight = new Fight();
        fight.setFighterRed( friendlyFight.getFighterRed() );
        fight.setFighterBlue( friendlyFight.getFighterBlue() );
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

    public LogListPDF( String rb, String headLine, HttpServletResponse response )
    {
        this.rb = ResourceBundle.getBundle( rb, response.getLocale() );
        this.response = response;
        this.headLine = headLine;
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
            writeText( _56, 665, rb.getString( "pdf.logList.weightclass" ) + " " + weightClass, cb, BOOLEAN_TRUE, _12,
                       _1 );
            writeText( _56, 645, rb.getString( "pdf.logList.fightNumber" ) + " " + fightNumber, cb, BOOLEAN_TRUE, _12,
                       _1 );
            // writeText(56,625,rb.getString ("pdf.logList.fightType")
            // + " "+ fightType,cb,BOOLEAN_TRUE,12,1);
            writeText( 24, 24, rb.getString( "pdf.copyright" ) + "", cb, BOOLEAN_FALSE, _8, _1 );

            if ( fight != null )
            {
                if ( fight.getFighterRed() != null )
                {
                    writeText( _286, _635, correctOutput( fight.getFighterRed().getName() ), cb, BOOLEAN_TRUE, _12, _0 );
                    writeText( _286, _595, correctOutput( fight.getFighterRed().getFirstname() ), cb, BOOLEAN_TRUE,
                               _12, _0 );
                    writeText( _286, _555, correctOutput( fight.getFighterRed().getTeam().getTeamName() ), cb,
                               BOOLEAN_TRUE, _12, _0 );
                }
                if ( fight.getFighterBlue() != null )
                {
                    writeText( _286 + _180, _635, correctOutput( fight.getFighterBlue().getName() ), cb, BOOLEAN_TRUE,
                               _12, _0 );
                    writeText( _286 + _180, _595, correctOutput( fight.getFighterBlue().getFirstname() ), cb,
                               BOOLEAN_TRUE, _12, _0 );
                    writeText( _286 + _180, _555, correctOutput( fight.getFighterBlue().getTeam().getTeamName() ), cb,
                               BOOLEAN_TRUE, _12, _0 );
                }
            }
            return;
        }
        catch ( Exception e )
        {
            System.err.println( "logList.createFightList: Exception: \n " + e.toString() );
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
     * schreibt alle TextAusgaben, die für eine blanko Liste notwendig sind
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

        writeText( _60, _505, rb.getString( "pdf.logList.part1" ), cb, BOOLEAN_TRUE, _14, _1 );
        writeText( _60, _445, rb.getString( "pdf.logList.part2" ), cb, BOOLEAN_TRUE, _14, _1 );
        writeText( _60, _385, rb.getString( "pdf.logList.part3" ), cb, BOOLEAN_TRUE, _14, _1 );
        writeText( _60, _190, rb.getString( "pdf.logList.result" ), cb, BOOLEAN_TRUE, _14, _1 );

        writeText( _60, _310, rb.getString( "pdf.logList.shido" ), cb, BOOLEAN_TRUE, _12, _1 );
        writeText( _60, 280, rb.getString( "pdf.logList.chui" ), cb, BOOLEAN_TRUE, _12, _1 );
        writeText( _60, 250, rb.getString( "pdf.logList.hansokumake" ), cb, BOOLEAN_TRUE, _12, _1 );

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
            System.err.println( "logList.writeText: Exception: \n can not create pdf document \n" + e.getMessage() );
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
            cb.rectangle( _56, _480, _140, _60 ); // part1
            cb.rectangle( _56, _420, _140, _60 );
            cb.rectangle( _56, _360, _140, _60 );
            cb.rectangle( _56, _330, _500, _30 ); // leer

            cb.rectangle( _56, _300, _500, _30 ); // shido
            cb.rectangle( _56, _270, _500, _30 ); // chui
            cb.rectangle( _56, _240, _500, _30 ); // hanso
            cb.rectangle( _56, _210, _500, _30 ); // leer
            cb.rectangle( _56, _180, _500, _30 ); // result
            // cb.rectangle(56,420,140,30);
            for ( int i = _0; i < _2; i++ )
            {
                cb.rectangle( i * _180 + _196, _180, _180, _520 );
                cb.rectangle( i * _180 + _196, 620, _180, _40 );
                cb.rectangle( i * _180 + _196, _540, _180, _40 );
                cb.rectangle( i * _180 + _196, _480, _90, _60 );
                cb.rectangle( i * _180 + _196, _420, _90, _60 );
                cb.rectangle( i * _180 + _196, _360, _90, _60 );
                // cb.rectangle(i*180+196,420,90,30);cb.rectangle(i*180+286,420,90,30);
            }

            cb.stroke();
        }
        catch ( Exception e )
        {
            System.err.println( "logList.createFrames: Exception: \ncan not create pdf document \n" + e.getMessage() );
        }
        return;
    }

}
