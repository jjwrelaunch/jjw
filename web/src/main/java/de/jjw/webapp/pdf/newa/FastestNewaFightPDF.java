/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FastestNewaFightPDF.java
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

package de.jjw.webapp.pdf.newa;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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

import de.jjw.model.FastFight;
import de.jjw.webapp.util.ConfigMain;


public class FastestNewaFightPDF
{

    private ResourceBundle rb;


    private PdfContentByte cb;

    private Document doc;

    private String headLine1;

    private String headLine2;

    private HttpServletResponse response;

    private Locale locale;

    protected final Logger log = Logger.getRootLogger();

    private Map<Integer, List<FastFight>> fastFightMap = null;

    public FastestNewaFightPDF( String ressourceBundle, HttpServletResponse response,
                            Map<Integer, List<FastFight>> fastFightMap,
                            Locale locale )
    {
        this.rb = ResourceBundle.getBundle( ressourceBundle, locale );
        this.response = response;
        this.fastFightMap = fastFightMap;
        this.locale = locale;

    }

    public void show()
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
            log.error( "FastestFightPDF.show: Exception: \ncan not create pdf document \n" + e.getMessage(), e );
        }
    }

    public ByteArrayOutputStream createContentAsBAOS()
    {
        try
        {
            doc = new Document( PageSize.A4, 36, 36, 36, 36 );
            ByteArrayOutputStream baos = new ByteArrayOutputStream( 8192 );
            PdfWriter writer = PdfWriter.getInstance( doc, baos );
            doc.open();
            cb = writer.getDirectContent();

            headLine1 = ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine1();
            headLine2 = ConfigMain.getInstance().getEventConfiguration().getPdfHeadLine2();

            writeText( 24, 24, rb.getString( "pdf.copyright" ) + "", false, 8, 1 );
            int rowIndex = 800;
            writeText( 300, rowIndex, headLine1, true, 16, 0 );
            rowIndex -= 30;
            writeText( 300, rowIndex, headLine2, true, 16, 0 );

            if ( fastFightMap == null || fastFightMap.size() == 0 )
            {
                rowIndex -= 30;
                writeText( 300, rowIndex, rb.getString( "pdf.fastestFight.noFight" ), true, 16, 0 );
            }
            else
            {
                rowIndex -= 30;
                boolean newpage = false;
                for ( List<FastFight> list : fastFightMap.values() )
                {
                    if ( newpage )
                    {
                        newPage();
                        rowIndex = 740;
                    }

                    if ( list != null && list.size() > 0 )
                    {
                        writeText( 300, rowIndex, rb.getString( "pdf.fastestFight.headLine" ) + " "
                            + list.get( 0 ).getAge(), true, 16, 0 );
                        rowIndex -= 30;

                        boolean isMale = true;
                        for ( FastFight item : list )
                        {
                            if ( isMale && item.getSex() == 2 )
                            {
                                // seperate space when female beginns
                                isMale = false;
                                rowIndex -= 25;
                            }
                            writeText( 50, rowIndex, item.getFirstname() + " " + item.getName(), false, 12, 1 );
                            writeText( 300, rowIndex, item.getTeam(), false, 12, 1 );
                            writeText( 440, rowIndex, item.getRegion(), false, 12, 1 );
                            writeText( 480, rowIndex, item.getCountry(), false, 12, 1 );
                            writeText( 560, rowIndex, item.getFightTime() + " s", false, 12, 2 );
                            rowIndex -= 15;

                        }
                        newpage = true;
                    }
                }
            }

            doc.close();
            return baos;
        }
        catch ( Exception e )
        {
            log.error( "FastestFightPDF.show: Exception: \ncan not create pdf document \n" + e.getMessage(), e );
            return null;
        }
    }

    private void newPage()
    {
        try
        {
            doc.setPageSize( PageSize.A4 );
            doc.newPage();
            doc.add( new Paragraph( " " ) );
            writeText( 24, 24, rb.getString( "pdf.copyright" ) + "", false, 8, 1 );
            int rowIndex = 800;
            writeText( 300, rowIndex, headLine1, true, 16, 0 );
            rowIndex -= 30;
            writeText( 300, rowIndex, headLine2, true, 16, 0 );

        }
        catch ( Exception e )
        {
            log.error( "FastestFightPDF.newPage: Exception: \ncan not create pdf document \n" + e.getMessage(), e );
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
     * @param loc 0 fuer center, 1 fuer left 2 right
     * @throws Exception
     */
    private void writeText( int x, int y, String text, boolean bold, int size, int loc )
        throws Exception
    {
        BaseFont bf;
        try
        {
            if ( bold )
                bf = BaseFont.createFont( BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
            else
                bf = BaseFont.createFont( BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
            if ( ( text.equalsIgnoreCase( "&nbsp;" ) ) || ( text.equalsIgnoreCase( "-1" ) ) )
                text = "";
            cb.stroke();
            cb.beginText();
            cb.setFontAndSize( bf, size );
            if ( loc == 0 )
                cb.showTextAligned( PdfContentByte.ALIGN_CENTER, text, x, y, 0 );
            if ( loc == 1 )
                cb.showTextAligned( PdfContentByte.ALIGN_LEFT, text, x, y, 0 );
            if ( loc == 2 )
                cb.showTextAligned( PdfContentByte.ALIGN_RIGHT, text, x, y, 0 );
            cb.endText();
        }
        catch ( Exception e )
        {
            log.error( "FastestFightPDF.writeText: Exception: \n can not create pdf document \n" + e.getMessage(), e );
        }
    }

}
