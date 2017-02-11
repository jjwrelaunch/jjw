package de.jjw.webapp.pdf;

/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : BasePDF.java
 * Created : 18 Aug 2010
 * Last Modified: Wed, 18 Aug 2010 21:58:16
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;

import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;

public class BasePDF
    implements IValueConstants
{

    protected PdfContentByte cb;

    protected ByteArrayOutputStream baos;

    protected Document doc;

    protected final Logger log = Logger.getRootLogger();

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
    protected void writeText( int x, int y, String text, boolean bold, int size, int loc )
        throws Exception
    {
        BaseFont bf;
        try
        {
            if ( text.equals( String.valueOf( INT_EMPTY ) ) || text.equals( String.valueOf( LONG_EMPTY ) )
                || text.equals( String.valueOf( DOUBLE_EMPTY ) ) )
                text = "";
            if ( bold )
                bf = BaseFont.createFont( BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
            else
                bf = BaseFont.createFont( BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
            if ( ( text.equalsIgnoreCase( "&nbsp;" ) ) || ( text.equalsIgnoreCase( "-1" ) ) )
                text = "";
            cb.stroke();
            cb.beginText();
            // log.debug( "cb.beginText" );

            cb.setFontAndSize( bf, size );
            if ( loc == 0 )
                cb.showTextAligned( PdfContentByte.ALIGN_CENTER, text, x, y, 0 );
            if ( loc == 1 )
                cb.showTextAligned( PdfContentByte.ALIGN_LEFT, text, x, y, 0 );
            if ( loc == 2 )
                cb.showTextAligned( PdfContentByte.ALIGN_RIGHT, text, x, y, 0 );
            cb.endText();
            // log.debug( "cb.endText" );
        }
        catch ( Exception e )
        {
            System.err.println( "showMedalsPDF.writeText: Exception: \n can not create pdf document \n"
                + e.getMessage() );
        }
        return;
    }

    /**
     * verarbeitet die Ausgabe eines Textes linksorientiert an die gegebene Position
     * 
     * @param x Koordinate
     * @param y Koordinate
     * @param text Ausgabetext
     * @param bold soll fett geschrieben werden
     * @param size Schriftgroesse
     * @throws Exception
     */
    protected void writeText( int x, int y, String text, boolean bold, int size )
        throws Exception
    {
        BaseFont bf;

        if ( text.equals( String.valueOf( INT_EMPTY ) ) || text.equals( String.valueOf( LONG_EMPTY ) )
            || text.equals( String.valueOf( DOUBLE_EMPTY ) ) )
            text = "";
        if ( bold )
        {
            bf = BaseFont.createFont( BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
        }
        else
        {
            bf = BaseFont.createFont( BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
        }
        if ( ( text.equalsIgnoreCase( "&nbsp;" ) ) || ( text.equalsIgnoreCase( String.valueOf( TypeUtil.INT_MIN ) ) ) )
        {
            text = "";
        }
        cb.stroke();
        cb.beginText();
        // log.debug( "cb.beginText" );

        cb.setFontAndSize( bf, size );
        cb.showTextAligned( PdfContentByte.ALIGN_LEFT, text, x, y, _0 );
        cb.endText();
        // log.debug( "cb.endText" );
        return;
    }

    /**
     * verarbeitet die Ausgabe eines Textes zentriert an die gegebene Position
     * 
     * @param x Koordinate
     * @param y Koordinate
     * @param text Ausgabetext
     * @param bold soll fett geschrieben werden
     * @throws Exception
     */
    protected void writeText( int x, int y, String text, boolean bold )
        throws Exception
    {
        BaseFont bf;
        if ( text.equals( String.valueOf( INT_EMPTY ) ) || text.equals( String.valueOf( LONG_EMPTY ) )
            || text.equals( String.valueOf( DOUBLE_EMPTY ) ) )
            text = "";
        if ( bold )
        {
            bf = BaseFont.createFont( BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
        }
        else
        {
            bf = BaseFont.createFont( BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
        }
        if ( ( text.equalsIgnoreCase( "&nbsp;" ) ) || ( text.equalsIgnoreCase( String.valueOf( TypeUtil.INT_MIN ) ) ) )
        {
            text = "";
        }
        cb.stroke();
        cb.beginText();
        // log.debug( "cb.beginText" );

        cb.setFontAndSize( bf, _10 );
        cb.showTextAligned( PdfContentByte.ALIGN_CENTER, text, x, y, _0 );
        cb.endText();
        // log.debug( "cb.endText" );
        return;
    }

    protected void GrayOrDoubleField( boolean gray, int x, int y, int height )
        throws Exception
    {
        if ( gray )
        {
            cb.stroke();
            cb.rectangle( x, y, _40, height );
            cb.stroke();
        }
        else
        {
            cb.rectangle( x, y, 14, height );
            x += 14;
            cb.rectangle( x, y, 26, height );
        }
    }

    /**
     * verarbeitet die Ausgabe eines Textes linksorientiert an die gegebene Position
     * 
     * @param x Koordinate
     * @param y Koordinate
     * @param text Ausgabetext
     * @param bold soll fett geschrieben werden
     * @param size Schriftgroesse
     * @throws Exception
     */
    protected void writeTextCentral( int x, int y, String text, boolean bold, int size )
        throws Exception
    {
        BaseFont bf;
        if ( text.equals( String.valueOf( INT_EMPTY ) ) || text.equals( String.valueOf( LONG_EMPTY ) )
            || text.equals( String.valueOf( DOUBLE_EMPTY ) ) )
            text = "";
        if ( bold )
        {
            bf = BaseFont.createFont( BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
        }
        else
        {
            bf = BaseFont.createFont( BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
        }
        if ( ( text.equalsIgnoreCase( "&nbsp;" ) ) || ( text.equalsIgnoreCase( String.valueOf( TypeUtil.INT_MIN ) ) ) )
        {
            text = "";
        }
        cb.stroke();
        cb.beginText();
        // log.debug( "cb.beginText" );

        cb.setFontAndSize( bf, size );
        cb.showTextAligned( PdfContentByte.ALIGN_CENTER, text, x, y, 0 );
        cb.endText();
        // log.debug( "cb.endText" );
        return;
    }

    public static void addWatermark( PdfContentByte cb, String text, int textsize, boolean isLandscape )
    {
        PdfGState gs = new PdfGState();
        gs.setFillOpacity( 0.4f );
        cb.setGState( gs );
        cb.beginText();
        try
        {
            cb.setFontAndSize( BaseFont.createFont( BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED ),
                               textsize );
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        catch ( DocumentException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        cb.setColorFill( BaseColor.LIGHT_GRAY );
        if ( isLandscape )
            cb.showTextAligned( PdfContentByte.ALIGN_CENTER, text, 400, 300, -45 );
        else
            cb.showTextAligned( PdfContentByte.ALIGN_CENTER, text, 300, 400, -45 );

        cb.endText();
        cb.stroke();
    }
}
