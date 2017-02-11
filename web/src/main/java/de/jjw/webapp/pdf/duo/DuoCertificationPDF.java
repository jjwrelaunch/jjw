/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoCertificationPDF.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:41
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

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import de.jjw.model.duo.DuoCertification;
import de.jjw.model.duo.DuoTeam;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.util.ConfigMain;

public class DuoCertificationPDF
{

    private static final String SPACE = " ";

    private ResourceBundle rb;

    private HttpServletResponse response;

    private PdfContentByte cb;

    public DuoCertificationPDF( String resourceBundle, HttpServletResponse response, DuoCertification dto )
    {
        this.rb = ResourceBundle.getBundle( resourceBundle );
        this.response = response;
        show( dto );

    }

    public void show( DuoCertification dto )
    {
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream( 1024 );
            Document doc = new Document( PageSize.A4, 36, 36, 36, 36 );
            PdfWriter writer = PdfWriter.getInstance( doc, baos );

            doc.open();
            cb = writer.getDirectContent();

            if ( dto.getDuoTeamList() != null && dto.getDuoTeamList().size() > TypeUtil.INT_DEFAULT )
            {
                for ( DuoTeam duoTeam : dto.getDuoTeamList() )
                {

                    switch ( dto.getCertificationStyle() )
                    {

                        case 1:
                            writeTextTypeGerman1( doc, duoTeam.getPlace(),
                                                  duoTeam.getFirstname() + SPACE + duoTeam.getName(),
                                                  duoTeam.getFirstname2() + SPACE + duoTeam.getName2(),
                                                  dto.getDuoclassCaption(),
                                                  ConfigMain.getInstance().getEventConfiguration().getEventDate(),
                                                  ConfigMain.getInstance().getEventConfiguration().getEventLocation() );
                            writeTextTypeGerman1( doc, duoTeam.getPlace(),
                                                  duoTeam.getFirstname2() + SPACE + duoTeam.getName2(),
                                                  duoTeam.getFirstname() + SPACE + duoTeam.getName(),
                                                  dto.getDuoclassCaption(),
                                                  ConfigMain.getInstance().getEventConfiguration().getEventDate(),
                                                  ConfigMain.getInstance().getEventConfiguration().getEventLocation() );
                            break;

                        case 5:
                            writeTextTypeEnglish1( doc, duoTeam.getPlace(),
                                                   duoTeam.getFirstname() + SPACE + duoTeam.getName(),
                                                   duoTeam.getFirstname2() + SPACE + duoTeam.getName2(),
                                                   dto.getDuoclassCaption(),
                                                   ConfigMain.getInstance().getEventConfiguration().getEventDate(),
                                                   ConfigMain.getInstance().getEventConfiguration().getEventLocation() );
                            writeTextTypeEnglish1( doc, duoTeam.getPlace(),
                                                   duoTeam.getFirstname2() + SPACE + duoTeam.getName2(),
                                                   duoTeam.getFirstname() + SPACE + duoTeam.getName(),
                                                   dto.getDuoclassCaption(),
                                                   ConfigMain.getInstance().getEventConfiguration().getEventDate(),
                                                   ConfigMain.getInstance().getEventConfiguration().getEventLocation() );
                            break;

                        default: // default is case 1
                            writeTextTypeGerman1( doc, duoTeam.getPlace(),
                                                  duoTeam.getFirstname() + SPACE + duoTeam.getName(),
                                                  duoTeam.getFirstname2() + SPACE + duoTeam.getName2(),
                                                  dto.getDuoclassCaption(),
                                                  ConfigMain.getInstance().getEventConfiguration().getEventDate(),
                                                  ConfigMain.getInstance().getEventConfiguration().getEventLocation() );
                            writeTextTypeGerman1( doc, duoTeam.getPlace(),
                                                  duoTeam.getFirstname2() + SPACE + duoTeam.getName2(),
                                                  duoTeam.getFirstname() + SPACE + duoTeam.getName(),
                                                  dto.getDuoclassCaption(),
                                                  ConfigMain.getInstance().getEventConfiguration().getEventDate(),
                                                  ConfigMain.getInstance().getEventConfiguration().getEventLocation() );
                            break;
                    }
                }

            }
            else
            {
                doc.setPageSize( PageSize.A4 );
                doc.newPage();
                doc.add( new Paragraph( SPACE ) );
            }

            doc.close();
            response.setContentLength( baos.size() );

            ServletOutputStream out = response.getOutputStream();
            baos.writeTo( out );
            baos.flush();

        }
        catch ( Exception e )
        {
            System.err.println( "duoCertificationPDF.show: Exception: \ncan not create pdf document \n"
                + e.getMessage() );
        }
    }

    /**
     * verarbeitet die Ausgabe eines Textes an die gegebene Position mit der Größe size
     * 
     * @param name1 AusgabeText
     * @throws Exception
     */
    protected void writeTextTypeGerman1( Document doc, int place, String name1, String name2, String classText,
                                   String eventDate, String eventLocation )
        throws Exception
    {
        BaseFont bf, bf2;

        try
        {
            doc.setPageSize( PageSize.A4 );
            doc.newPage();
            doc.add( new Paragraph( SPACE ) );

            bf = BaseFont.createFont( BaseFont.TIMES_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
            bf2 = BaseFont.createFont( BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );

            if ( name1 == null )
            {
                name1 = "";
            }
            cb.stroke();
            cb.beginText();
            cb.setFontAndSize( bf, 34 );
            cb.showTextAligned( PdfContentByte.ALIGN_CENTER, name1, 300, 520, 0 );
            cb.showTextAligned( PdfContentByte.ALIGN_CENTER, name2, 300, 480, 0 );
            cb.endText();

            cb.beginText();
            cb.setFontAndSize( bf2, 24 );
            cb.showTextAligned( PdfContentByte.ALIGN_CENTER, rb.getString( "pdf.certification.duo.classText.de" ), 300,
                                400, 0 );
            cb.endText();

            cb.beginText();
            cb.setFontAndSize( bf, 24 );
            cb.showTextAligned( PdfContentByte.ALIGN_CENTER, classText, 300, 370, 0 );
            cb.endText();

            // deutsch
            cb.beginText();
            cb.setFontAndSize( bf2, 24 );
            cb.showTextAligned( PdfContentByte.ALIGN_CENTER,
                                rb.getString( "pdf.certification.duo.textBeforePlace.de" ), 300, 340, 0 );
            cb.endText();
            // deutsch
            cb.beginText();
            cb.setFontAndSize( bf, 48 );
            cb.showTextAligned( PdfContentByte.ALIGN_CENTER,
                                String.valueOf( place ) + rb.getString( "pdf.certification.duo.textAfterPlace.de" ),
                                300, 290, 0 );
            cb.endText();

            cb.beginText();
            cb.setFontAndSize( bf, 12 );
            cb.showTextAligned( PdfContentByte.ALIGN_CENTER, eventDate + SPACE + eventLocation, 300, 30, 0 );
            cb.endText();
        }
        catch ( Exception e )
        {
            System.err.println( "DuoCertificationPDF.writeText: Exception: \n can not create pdf document \n"
                + e.getMessage() );
        }
        return;
    }

    protected void writeTextTypeEnglish1( Document doc, int place, String name1, String name2, String classText,
                                          String eventDate, String eventLocation )
        throws Exception
    {
        BaseFont bf, bf2;
        String place_Suffix = "";

        switch ( place )
        {
            case 1:
                place_Suffix = rb.getString( "pdf.certification.duo.FIRST_PLACE" );
                break;
            case 2:
                place_Suffix = rb.getString( "pdf.certification.duo.SECOND_PLACE" );
                break;
            case 3:
                place_Suffix = rb.getString( "pdf.certification.duo.THIRD_PLACE" );
                break;
            default:
                place_Suffix = rb.getString( "pdf.certification.duo.FORTH_PLACE" );
        }

        try
        {
            doc.setPageSize( PageSize.A4 );
            doc.newPage();
            doc.add( new Paragraph( SPACE ) );

            bf = BaseFont.createFont( BaseFont.TIMES_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
            bf2 = BaseFont.createFont( BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );

            if ( name1 == null )
            {
                name1 = "";
            }
            cb.stroke();
            cb.beginText();
            cb.setFontAndSize( bf, 34 );
            cb.showTextAligned( PdfContentByte.ALIGN_CENTER, name1, 300, 520, 0 );
            cb.showTextAligned( PdfContentByte.ALIGN_CENTER, name2, 300, 480, 0 );
            cb.endText();

            cb.beginText();
            cb.setFontAndSize( bf, 24 );
            cb.showTextAligned( PdfContentByte.ALIGN_CENTER, classText, 300, 370, 0 );
            cb.endText();

            cb.beginText();
            cb.setFontAndSize( bf, 48 );
            cb.showTextAligned( PdfContentByte.ALIGN_LEFT, String.valueOf( place ), 240, 290, 0 );
            cb.endText();

            cb.beginText();
            cb.setFontAndSize( bf, 28 );
            cb.showTextAligned( PdfContentByte.ALIGN_LEFT,
                                place_Suffix + "  " + rb.getString( "pdf.certification.duo.textAfterPlace.en" ), 263,
                                290,
                                0 );
            cb.endText();

            cb.beginText();
            cb.setFontAndSize( bf, 12 );
            cb.showTextAligned( PdfContentByte.ALIGN_CENTER, eventDate + SPACE + eventLocation, 300, 30, 0 );
            cb.endText();
        }
        catch ( Exception e )
        {
            System.err.println( "DuoCertificationPDF.writeText: Exception: \n can not create pdf document \n"
                + e.getMessage() );
        }
        return;
    }
}
