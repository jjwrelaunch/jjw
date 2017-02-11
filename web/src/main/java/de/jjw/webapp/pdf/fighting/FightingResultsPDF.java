/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightingResultsPDF.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:46
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
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.service.modelWeb.FighterWeb;
import de.jjw.util.ICodestableTypeConstants;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.util.ConfigMain;

public class FightingResultsPDF
{
    protected final Logger log = Logger.getRootLogger();
    private ByteArrayOutputStream baos;

    private List<FighterWeb> fighter;

    private PdfContentByte cb;

    private Document doc;

    private int rowIndex = 0;

    private ResourceBundle rb;

    HttpServletResponse response;

    Locale locale;

    public FightingResultsPDF( String ressourceBundle, HttpServletResponse response, List<FighterWeb> fighter,
                               Locale locale )
    {
        this.rb = ResourceBundle.getBundle( ressourceBundle, locale );
        this.response = response;
        this.fighter = fighter;
        this.locale = locale;
    }

    public void showResults()
    {
        try
        {
            ByteArrayOutputStream baos = createContentAsBAOS();

            response.setContentLength( baos.size() );

            ServletOutputStream out = response.getOutputStream();
            baos.writeTo( out );
            baos.flush();
        }
        catch ( Exception e )
        {
            log.error( "FightingResultsPDF.showResults: Exception: \ncan not create pdf document \n" + e.getMessage(),
                       e );
        }
    }

    public ByteArrayOutputStream createContentAsBAOS()
    {
        try
        {
            String headLine1 = ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1();
            String headLine2 = ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine2();

            int numberOfRankedFighterInAClass = 0;
            doc = new Document( PageSize.A4, 36, 36, 36, 36 );
            ByteArrayOutputStream baos = new ByteArrayOutputStream( 4096 );
            PdfWriter writer = PdfWriter.getInstance( doc, baos );
            doc.open();
            cb = writer.getDirectContent();
            writeText( 24, 24, rb.getString( "pdf.copyright" ) + "", false, 8, 1 );
            rowIndex = 800;
            writeText( 300, rowIndex, headLine1, true, 16, 0 );
            rowIndex -= 30;
            writeText( 300, rowIndex, headLine2, true, 16, 0 );
            rowIndex -= 20;

            if ( fighter.isEmpty() )
            {
                rowIndex -= 20;
                writeText( 300, rowIndex, rb.getString( "results.noresults" ), true, 16, 0 );
            }

            for ( int i = 0; i < fighter.size(); )
            {
                rowIndex -= 15;
                numberOfRankedFighterInAClass = getNumberOfRankedFighterFromAClass( i );
                if ( !isEnoughSpaceOnPage( numberOfRankedFighterInAClass ) )
                {
                    newPage();
                }
                doFighterEntries( i, numberOfRankedFighterInAClass, locale );
                i += numberOfRankedFighterInAClass;
            }
            doc.close();
            return baos;

        }
        catch ( Exception e )
        {
            log.error( "FightingResultsPDF.showResults: Exception: \ncan not create pdf document \n" + e.getMessage(),
                       e );
            return null;
        }
    }

    /**
     * erstel lt die Eintraege pro Klasse und setzt rowIndex weiter
     * 
     * @param startIndex
     * @throws Exception
     */
    private void doFighterEntries( int startIndex, int numberOfFighterEntries, Locale locale )
        throws Exception
    {// write class headline and fighter
        StringBuffer text = new StringBuffer( 30 );
        text.append( fighter.get( startIndex ).getAge().getDescription() );
        text.append( TypeUtil.SPACE );
        text.append( CodestableMain.getInstance().getCodestableCodeValueByType( ICodestableTypeConstants.TYPE_SEX,
                                                                                fighter.get( startIndex ).getSex(),
                                                                                locale ) );
        text.append( TypeUtil.SPACE );
        text.append( fighter.get( startIndex ).getFightingclass().getWeightclass() );
        writeText( 50, rowIndex, text.toString(), true, 11, 1 );
        rowIndex -= 15;
        for ( int i = 0; i < numberOfFighterEntries; i++ )
        {
            text = new StringBuffer( 60 );
            text.append( fighter.get( i + startIndex ).getPlace() + ". " );
            text.append( fighter.get( i + startIndex ).getFirstname() ).append( TypeUtil.SPACE );
            text.append( fighter.get( i + startIndex ).getName() );
            writeText( 50, rowIndex, text.toString(), false, 10, 1 );

            writeText( 300, rowIndex, fighter.get( i + startIndex ).getTeam().getTeamName(), false, 10, 1 );
            writeText( 470, rowIndex, fighter.get( i + startIndex ).getTeam().getRegion().getRegionShort(), false, 10,
                       1 );
            writeText( 510, rowIndex, fighter.get( i + startIndex ).getTeam().getCountry().getCountryShort(), false,
                       10, 1 );
            rowIndex -= 13;
        }
    }

    private boolean isEnoughSpaceOnPage( int numberOfFighterEntries )
    {
        // rowIndex und NumberOfFighterEntries betrachten
        if ( rowIndex > ( numberOfFighterEntries + 1 ) * 15 )
        {
            return true;
        }
        return false;
    }

    private int getNumberOfRankedFighterFromAClass( int startIndex )
    {
        // List FighterList = new ArrayList();
        int ret = 1;
        Long fightingclassId = fighter.get( startIndex ).getFightingclassId();

        // FighterList.add (Fighter[startIndex]);
        startIndex++;
        while ( ( startIndex < fighter.size() )
            && fightingclassId.equals( fighter.get( startIndex ).getFightingclassId() ) )
        {
            startIndex++;
            ret++;
        }
        return ret;
    }

    private void newPage()
    {
        try
        {
            doc.setPageSize( PageSize.A4 );
            doc.newPage();
            doc.add( new Paragraph( TypeUtil.SPACE ) );
            writeText( 24, 24, rb.getString( "pdf.copyright" ) + "", false, 8, 1 );
            rowIndex = 800;
        }
        catch ( Exception e )
        {
            log.error( "FightingResultsPDF.newPage: Exception: \ncan not create pdf document \n" + e.getMessage(), e );
        }
    }

    /**
     * verarbeitet die Ausgabe eines Textes an die gegebene Position mit der Groesse size
     * 
     * @param x Koordinate
     * @param y Koordinate
     * @param text AusgabeText
     * @param bold Fettdruck
     * @param size Schriftgroesse
     * @param loc 0 fuer center, 1 fuer left
     * @throws Exception
     */
    private void writeText( int x, int y, String text, boolean bold, int size, int loc )
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
            if ( ( text.equalsIgnoreCase( "&nbsp;" ) ) || ( text.equalsIgnoreCase( "-1" ) ) )
            {
                text = "";
            }
            cb.stroke();
            cb.beginText();
            cb.setFontAndSize( bf, size );
            if ( loc == 0 )
            {
                cb.showTextAligned( PdfContentByte.ALIGN_CENTER, text, x, y, 0 );
            }
            if ( loc == 1 )
            {
                cb.showTextAligned( PdfContentByte.ALIGN_LEFT, text, x, y, 0 );
            }
            cb.endText();
        }
        catch ( Exception e )
        {
            log.error( "showFightingResultsPDF.writeText: Exception: \n can not create pdf document \n"
                           + e.getMessage(), e );
        }
        return;
    }

}
