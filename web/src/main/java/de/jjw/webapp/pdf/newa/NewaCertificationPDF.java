/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaCertificationPDF.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:45
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
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.model.newa.NewaCertification;
import de.jjw.model.newa.NewaFighter;
import de.jjw.util.ICodestableTypeConstants;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.util.ConfigMain;



public class NewaCertificationPDF
{

    private static final String KG = " Kg";

    private static final String SPACE = " ";

    private ResourceBundle rb;

    private HttpServletResponse response;

    private PdfContentByte cb;

    public NewaCertificationPDF( String resourceBundle, HttpServletResponse response, NewaCertification dto,
                                     Locale locale )
    {
        this.rb = ResourceBundle.getBundle( resourceBundle );
        this.response = response;
        show( dto, locale );

    }

    public void show( NewaCertification dto, Locale locale )
    {
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream( 1024 );
            Document doc = new Document( PageSize.A4, 36, 36, 36, 36 );
            PdfWriter writer = PdfWriter.getInstance( doc, baos );

            doc.open();
            cb = writer.getDirectContent();

            if ( dto.getFighterList() != null && dto.getFighterList().size() > TypeUtil.INT_DEFAULT )
            {

                for ( NewaFighter fighter : dto.getFighterList() )
                {

                    switch ( dto.getCertificationStyle() )
                    {

                        case 1:

                            writeTextTypeGerman1( doc, fighter.getPlace(),
                                                  fighter.getFirstname() + SPACE + fighter.getName(),
                                                  buildAgeClassCaption( dto, Locale.GERMAN ),
                                                  ConfigMain.getInstance().getEventConfiguration().getEventDate(),
                                                  ConfigMain.getInstance().getEventConfiguration().getEventLocation() );
                            break;

                        case 5:

                            writeTextTypeEnglish1( doc, fighter.getPlace(),
                                                   fighter.getFirstname() + SPACE + fighter.getName(),
                                                   buildAgeClassCaption( dto, Locale.ENGLISH ),
                                                   ConfigMain.getInstance().getEventConfiguration().getEventDate(),
                                                   ConfigMain.getInstance().getEventConfiguration().getEventLocation() );
                            break;

                        default: // default is case 1

                            writeTextTypeGerman1( doc, fighter.getPlace(),
                                                  fighter.getFirstname() + SPACE + fighter.getName(),
                                                  buildAgeClassCaption( dto, Locale.GERMAN ),
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
            System.err.println( "FightingCertificationPDF.show: Exception: \ncan not create pdf document \n"
                + e.getMessage() );
        }
    }

    /**
     * builds the String for age caption
     * 
     * @param dto
     * @param locale
     * @return
     */
    private String buildAgeClassCaption( NewaCertification dto, Locale locale )
    {
        StringBuffer ageClassCaption = new StringBuffer( 80 );
        ageClassCaption.append( dto.getAge() ).append( SPACE );

        ageClassCaption.append( CodestableMain.getInstance().getCodestableCodeValueByType( ICodestableTypeConstants.TYPE_SEX,
                                                                                           dto.getSex(), locale ) );
        ageClassCaption.append( SPACE );
        ageClassCaption.append( dto.getNewaclassCaption() ).append( KG );

        return ageClassCaption.toString();

    }

    /**
     * verarbeitet die Ausgabe eines Textes an die gegebene Position mit der Größe size
     * 
     * @param text AusgabeText
     * @throws Exception
     */
    protected void writeTextTypeGerman1( Document doc, int place, String text, String classText, String eventDate,
                                   String eventLocation )
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

            if ( text == null )
            {
                text = "";
            }
            cb.stroke();
            cb.beginText();
            cb.setFontAndSize( bf, 34 );
            cb.showTextAligned( PdfContentByte.ALIGN_CENTER, text, 300, 500, 0 );
            cb.endText();

            cb.beginText();
            cb.setFontAndSize( bf2, 24 );
            cb.showTextAligned( PdfContentByte.ALIGN_CENTER, rb.getString( "pdf.certification.fighting.classText.de" ),
                                300, 400, 0 );
            cb.endText();

            cb.beginText();
            cb.setFontAndSize( bf, 24 );
            cb.showTextAligned( PdfContentByte.ALIGN_CENTER, classText, 300, 370, 0 );
            cb.endText();

            // deutsch
            cb.beginText();
            cb.setFontAndSize( bf2, 24 );
            cb.showTextAligned( PdfContentByte.ALIGN_CENTER,
                                rb.getString( "pdf.certification.fighting.textBeforePlace.de" ), 300, 340, 0 );
            cb.endText();
            // deutsch
            cb.beginText();
            cb.setFontAndSize( bf, 48 );
            cb.showTextAligned( PdfContentByte.ALIGN_CENTER,
                                String.valueOf( place ) + rb.getString( "pdf.certification.fighting.textAfterPlace.de" ),
                                300, 290, 0 );
            cb.endText();
            cb.beginText();
            cb.setFontAndSize( bf, 12 );
            cb.showTextAligned( PdfContentByte.ALIGN_CENTER, eventDate + SPACE + eventLocation, 300, 30, 0 );
            cb.endText();
        }
        catch ( Exception e )
        {
            System.err.println( "FightingFightList.writeText: Exception: \n can not create pdf document \n"
                + e.getMessage() );
        }
        return;
    }

    protected void writeTextTypeEnglish1( Document doc, int place, String text, String classText, String eventDate,
                                          String eventLocation )
        throws Exception
    {
        BaseFont bf, bf2;
        String place_Suffix = "";

        switch ( place )
        {
            case 1:
                place_Suffix = rb.getString( "pdf.certification.fighting.FIRST_PLACE" );
                break;
            case 2:
                place_Suffix = rb.getString( "pdf.certification.fighting.SECOND_PLACE" );
                break;
            case 3:
                place_Suffix = rb.getString( "pdf.certification.fighting.THIRD_PLACE" );
                break;
            default:
                place_Suffix = rb.getString( "pdf.certification.fighting.FORTH_PLACE" );
        }

        try
        {
            doc.setPageSize( PageSize.A4 );
            doc.newPage();
            doc.add( new Paragraph( SPACE ) );

            bf = BaseFont.createFont( BaseFont.TIMES_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
            bf2 = BaseFont.createFont( BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );

            if ( text == null )
            {
                text = "";
            }
            cb.stroke();
            cb.beginText();
            cb.setFontAndSize( bf, 34 );
            cb.showTextAligned( PdfContentByte.ALIGN_CENTER, text, 300, 500, 0 );// name
            cb.endText();

            cb.stroke();
            cb.beginText();
            cb.setFontAndSize( bf, 30 );
            cb.showTextAligned( PdfContentByte.ALIGN_CENTER, "Newaza", 300, 440, 0 );// name
            cb.endText();

            cb.beginText();
            cb.setFontAndSize( bf2, 24 );
            cb.showTextAligned( PdfContentByte.ALIGN_CENTER, rb.getString( "pdf.certification.fighting.classText.en" ),// class
                                                                                                                       // text
                                                                                                                       // from
                                                                                                                       // property
                                300, 400, 0 );
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
                                place_Suffix + "  " + rb.getString( "pdf.certification.fighting.textAfterPlace.en" ),
                                263,
                                290, 0 );
            cb.endText();

            cb.beginText();
            cb.setFontAndSize( bf, 12 );
            cb.showTextAligned( PdfContentByte.ALIGN_CENTER, eventDate + SPACE + eventLocation, 300, 30, 0 );
            cb.endText();
        }
        catch ( Exception e )
        {
            System.err.println( "FightingFightList.writeText: Exception: \n can not create pdf document \n"
                + e.getMessage() );
        }
        return;
    }
}
