/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : MedalsPDF.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:43
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

package de.jjw.webapp.pdf;

import java.io.ByteArrayOutputStream;
import java.util.List;
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

import de.jjw.model.MedalRanking;
import de.jjw.model.admin.Age;
import de.jjw.webapp.util.ConfigMain;

public class MedalsPDF
{
    private ResourceBundle rb;

    private HttpServletResponse response;

    private PdfContentByte cb;

    private Document doc;

    private int rowIndex = 0;

    private Age age;

    private String org = "";

    private List<MedalRanking> medals;

    protected final Logger log = Logger.getRootLogger();

    public MedalsPDF( String resourceBundle, HttpServletResponse response, List<MedalRanking> medals, String org,
                      Age age )
    {
        this.rb = ResourceBundle.getBundle( resourceBundle );
        this.response = response;
        this.org = org;
        this.age = age;
        this.medals=medals;
    }

    public void showMedals()
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
            log.error( "MedalsPDF.showMedals: Exception: \ncan not create pdf document \n" + e.getMessage(), e );
        }
    }

    public ByteArrayOutputStream createContentAsBAOS()
    {
        try
        {

            String headLine1 = ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1();
            String headLine2 = ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine2();
            doc = new Document( PageSize.A4, 36, 36, 36, 36 );
            ByteArrayOutputStream baos = new ByteArrayOutputStream( 2048 );
            PdfWriter writer = PdfWriter.getInstance( doc, baos );
            doc.open();
            cb = writer.getDirectContent();
            writeText( 24, 24, rb.getString( "pdf.copyright" ) + "", false, 8, 1 );
            rowIndex = 800;
            writeText( 300, rowIndex, headLine1, true, 16, 0 );
            rowIndex -= 30;
            writeText( 300, rowIndex, headLine2, true, 16, 0 );
            rowIndex -= 50;
            StringBuffer sb = new StringBuffer( 50 );
            sb.append( rb.getString( "pdf.MedalRanking.Headline" ) );
            sb.append( " " );
            if ( org != null )
            {
                sb.append( rb.getString( "pdf.MedalRanking.org" + org ) );
            }
            if ( org != null && age != null )
            {
                sb.append( " " );
                sb.append( rb.getString( "pdf.MedalRanking.age" ) );
                sb.append( " " );
                sb.append( age.getDescription() );
            }

            writeText( 300, rowIndex, sb.toString(), true, 14, 0 );
            rowIndex -= 50;
            writeText( 300, rowIndex, rb.getString( "pdf.MedalRanking.firstPlace" ), true, 12, 0 );
            writeText( 400, rowIndex, rb.getString( "pdf.MedalRanking.secondPlace" ), true, 12, 0 );
            writeText( 500, rowIndex, rb.getString( "pdf.MedalRanking.thirdPlace" ), true, 12, 0 );
            rowIndex -= 20;
            for ( int i = 0; i < medals.size(); i++ )
            {
                rowIndex -= 15;
                if ( i % 5 == 0 )
                {
                    rowIndex -= 5;
                } // aller 5 Zeilen
                if ( !isEnoughSpaceOnPage() )
                {
                    newPage();
                }
                writeText( 50, rowIndex, medals.get( i ).getRank(), true, 12, 1 );
                writeText( 75, rowIndex, medals.get( i ).getName(), true, 12, 1 );
                writeText( 300, rowIndex, String.valueOf( medals.get( i ).getFirstPlace() ), false, 12, 0 );
                writeText( 400, rowIndex, String.valueOf( medals.get( i ).getSecondPlace() ), false, 12, 0 );
                writeText( 500, rowIndex, String.valueOf( medals.get( i ).getThirdPlace() ), false, 12, 0 );
            }
            doc.close();
            return baos;
        }
        catch ( Exception e )
        {
            log.error( "MedalsPDF.showResults: Exception: \ncan not create pdf document \n" + e.getMessage(), e );
            return null;
        }
    }

    private boolean isEnoughSpaceOnPage()
    {
        // rowIndex und NumberOfFighterEntries betrachten
        if ( rowIndex > 50 )
        {
            return true;
        }
        return false;
    }

    private void newPage()
    {
        try
        {
            doc.setPageSize( PageSize.A4 );
            doc.newPage();
            doc.add( new Paragraph( " " ) );
            writeText( 24, 24, rb.getString( "pdf.copyright" ) + "", false, 8, 1 );
            rowIndex = 800;
        }
        catch ( Exception e )
        {
            log.error( "MedalsPDF.newPage: Exception: \ncan not create pdf document \n" + e.getMessage(), e );
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
            log.error( "MedalsPDF.writeText: Exception: \n can not create pdf document \n" + e.getMessage(), e );
        }
        return;
    }

}
