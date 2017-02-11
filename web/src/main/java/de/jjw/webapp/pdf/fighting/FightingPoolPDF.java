/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightingPoolPDF.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 21:58:16
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
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import de.jjw.model.fighting.Fight;
import de.jjw.model.fighting.FightingSimplePoolClass;
import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.util.ICodestableTypeConstants;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.pdf.BasePDF;

public class FightingPoolPDF
    implements IValueConstants
{

    protected final Logger log = Logger.getRootLogger();

    private static final String SPACE = " ";

    private static final String KG = " Kg";

    private ResourceBundle rb;

    private FightingSimplePoolClass fightingclass;

    // private Document doc;

    PdfContentByte cb;

    private String headLine = "";

    private HttpServletResponse response;

    private static final String Fights[] = { "1-2", "3-4", "1-5", "2-3", "4-5", "1-3", "2-4", "3-5", "1-4", "2-5" };

    private static final String FightNr[] =
        { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "1", "2", "5", "6", "9", "10", "13", "14", "17", "18",
            "3", "4", "7", "8", "11", "12", "15", "16", "19", "20", };

    public FightingPoolPDF( String ressourceBundle, FightingSimplePoolClass fightingclass,
                            HttpServletResponse response, String headLine, Locale local )
        throws Exception
    {
        this.response = response;
        this.rb = ResourceBundle.getBundle( ressourceBundle, local );
        this.fightingclass = fightingclass;

        this.headLine = headLine;
        createPool();

    }

    public FightingPoolPDF( String ressourceBundle, HttpServletResponse response, String headLine, Locale local )
    {
        this.response = response;
        this.rb = ResourceBundle.getBundle( ressourceBundle, local );
        this.headLine = headLine;
    }

    public void createPoolBlanco()
        throws Exception
    {
        Document doc = new Document( PageSize.A4.rotate(), _36, _36, _36, _36 );

        ByteArrayOutputStream baos = new ByteArrayOutputStream( 1024 );

        PdfWriter writer = PdfWriter.getInstance( doc, baos );

        doc.open();

        cb = writer.getDirectContent();

        doc.setPageSize( PageSize.A4.rotate() );
        doc.newPage();
        doc.add( new Paragraph( SPACE ) );

        writeText( 600, _540, headLine, BOOLEAN_TRUE, 16 );

        writeText( _24, _24, rb.getString( "pdf.copyright" ) + "", BOOLEAN_FALSE, 8 );
        createResultTable( 150 );

        createFightTable( 280, _0 );

        doc.close();

        response.setContentLength( baos.size() );

        ServletOutputStream out = response.getOutputStream();
        baos.writeTo( out );
        baos.flush();
    }

    private void createPool()
        throws Exception
    {
        try
        {
            Document doc = new Document( PageSize.A4.rotate(), _36, _36, _36, _36 );

            ByteArrayOutputStream baos = new ByteArrayOutputStream( 1024 );

            PdfWriter writer = PdfWriter.getInstance( doc, baos );

            doc.open();

            cb = writer.getDirectContent();

            doc.setPageSize( PageSize.A4.rotate() );
            doc.newPage();
            doc.add( new Paragraph( SPACE ) );

            writeText( 600, _540, headLine, BOOLEAN_TRUE, 16 );
            writeText( 600, 510, fightingclass.getAge().getDescription()
                + SPACE
                + CodestableMain.getInstance().getCodestableCodeValueByType( ICodestableTypeConstants.TYPE_SEX,
                                                                             fightingclass.getSex(),
                                                                             response.getLocale() ) + SPACE
                + fightingclass.getWeightclass() + KG, BOOLEAN_TRUE, 14 );
            writeText( _24, _24, rb.getString( "pdf.copyright" ) + "", BOOLEAN_FALSE, 8 );
            createResultTable( 150 );
            fillResultTable( 150 );
            createFightTable( 280, _0 );
            fillFightTable( 280 );

            BasePDF.addWatermark( writer.getDirectContentUnder(), "Fighting", 140, true );

            Fight fight;
            LogListPDF FL;
            for ( int i = _0; i < _10; i++ )
            {
                fight = fightingclass.getFightListMap().get( Integer.valueOf( i + _1 ) );
                if ( fight == null )
                {
                    continue;
                }
                doc.setPageSize( PageSize.A4 );
                doc.newPage();
                doc.add( new Paragraph( SPACE ) );
                FL = new LogListPDF( rb, cb, fight, headLine );
                FL.createFightList( fightingclass.getAge().getDescription(), fightingclass.getWeightclass(),
                                    String.valueOf( i + _1 ) );
                cb.stroke();
            }
            doc.close();

            response.setContentLength( baos.size() );

            ServletOutputStream out = response.getOutputStream();
            baos.writeTo( out );
            baos.flush();

        }
        catch ( Exception e )
        {
            log.error( "FightingPoolPDF.createPool: Exception: \ncan not create pdf document \n", e );
            throw e;
        }
    }

    /**
     * erstellt die ErgebnisTabelle für einen Pool
     * 
     * @param konst
     * @return gibt die unterste Position der Tabelle zurück
     * @throws Exception
     */
    private int createResultTable( int konst )
        throws Exception
    {
        cb.setColorStroke( new BaseColor( 0, 0, 0 ) );
        cb.setLineWidth( 1f );
        int height = _18; // konst

        for ( int i = _0; i < 6; i++ )
        {
            int j = _24;
            cb.rectangle( j, _540 - konst - i * height, _80, height );
            if ( i == _0 )
            {
                writeText( j + _40, _545 - konst - i * height, rb.getString( "pdf.pool.name" ), BOOLEAN_FALSE );
            }

            j += _80;
            cb.rectangle( j, _540 - konst - i * height, _80, height );
            if ( i == _0 )
            {
                writeText( j + _40, _545 - konst - i * height, rb.getString( "pdf.pool.firstname" ), BOOLEAN_FALSE );
            }

            j += _80;
            cb.rectangle( j, _540 - konst - i * height, _80, height );
            if ( i == _0 )
            {
                writeText( j + _40, _545 - konst - i * height, rb.getString( "pdf.pool.team" ), BOOLEAN_FALSE );
            }

            j += _80;
            cb.rectangle( j, _540 - konst - i * height, _20, height );
            if ( i == _0 )
            {
                writeText( j + _10, _545 - konst - i * height, rb.getString( "pdf.pool.nb" ), BOOLEAN_FALSE );
            }
            else
            {
                writeText( j + _10, _545 - konst - i * height, String.valueOf( i ), BOOLEAN_FALSE );
            }

            // 1-5
            j += _20;
            if ( i == _0 )
            { // Kopfzeile
                cb.rectangle( j, _540 - konst - i * height, _40, height );
                writeText( j + _20, _545 - konst - i * height, "1", BOOLEAN_FALSE );
                j += _40;
                cb.rectangle( j, _540 - konst - i * height, _40, height );
                writeText( j + _20, _545 - konst - i * height, "2", BOOLEAN_FALSE );
                j += _40;
                cb.rectangle( j, _540 - konst - i * height, _40, height );
                writeText( j + _20, _545 - konst - i * height, "3", BOOLEAN_FALSE );
                j += _40;
                cb.rectangle( j, _540 - konst - i * height, _40, height );
                writeText( j + _20, _545 - konst - i * height, "4", BOOLEAN_FALSE );
                j += _40;
                cb.rectangle( j, _540 - konst - i * height, _40, height );
                writeText( j + _20, _545 - konst - i * height, "5", BOOLEAN_FALSE );
            }
            else
            {
                for ( int k = _0; k < 4; k++ )
                {
                    if ( i == ( k + _1 ) )
                    {
                        GrayOrDoubleField( BOOLEAN_TRUE, j, _540 - konst - i * height, height );
                        j += _40;
                    }
                    GrayOrDoubleField( BOOLEAN_FALSE, j, _540 - konst - i * height, height );
                    j += _40;
                    if ( ( i == _5 ) && ( k == 3 ) )
                    {
                        GrayOrDoubleField( BOOLEAN_TRUE, j, _540 - konst - i * height, height );
                        j += _40;
                    }
                }
                j -= _40;
            }
            // //
            j += _40;
            cb.rectangle( j, _540 - konst - i * height, _30, height );
            if ( i == _0 )
            {
                writeText( j + _15, _545 - konst - i * height, rb.getString( "pdf.pool.wins" ), BOOLEAN_FALSE );
            }

            j += _30;
            cb.rectangle( j, _540 - konst - i * height, _30, height );
            if ( i == _0 )
            {
                writeText( j + _15, _545 - konst - i * height, rb.getString( "pdf.pool.points" ), BOOLEAN_FALSE );
            }

            j += _30;
            cb.rectangle( j, _540 - konst - i * height, _30, height );
            if ( i == _0 )
            {
                writeText( j + _15, _545 - konst - i * height, rb.getString( "pdf.pool.place" ), BOOLEAN_FALSE );
            }
            cb.stroke();
        }
        return konst;
    }

    /**
     * Erstellt die Tabelle mit den einzelnen Kampfpaarungen
     * 
     * @param konst gibt an in welcher Höhe die Tabelle plaziert wird
     * @param kindOfPool 0 fur Pool, 1 für DPool.PoolA, 2 für DPool.PoolB
     * @throws Exception
     */
    private int createFightTable( int konst, int kindOfPool )
        throws Exception
    {
        int height = _18;// konst

        for ( int m = _0; m < 2; m++ ) // m für linke und rechte seite
        {
            for ( int i = _0; i < 6; i++ )
            {
                int j = _24 + m * 290;
                if ( i == _0 )
                {
                    cb.rectangle( j, _540 - konst - i * height, _40, height );
                    j += _40;
                }
                else
                {
                    cb.rectangle( j, _540 - konst - i * height, _20, height );
                    writeText( j + _10, _545 - konst - i * height, Fights[m * _5 + i - _1], BOOLEAN_FALSE );
                    j += _20;
                    cb.rectangle( j, _540 - konst - i * height, _20, height );
                    writeText( j + _10, _545 - konst - i * height, FightNr[_10 * kindOfPool + _5 * m + i - _1],
                               BOOLEAN_FALSE );
                    j += _20;
                }
                cb.rectangle( j, _540 - konst - i * height, 90, height );
                if ( i == _0 )
                {
                    writeText( j + _45, _545 - konst - i * height, rb.getString( "pdf.fightTable.fighterRed" ),
                               BOOLEAN_FALSE );
                }

                j += 90;

                cb.rectangle( j, _540 - konst - i * height, 90, height );
                if ( i == _0 )
                {
                    writeText( j + _45, _545 - konst - i * height, rb.getString( "pdf.fightTable.fighterBlue" ),
                               BOOLEAN_FALSE );
                }

                j += 90;
                if ( i == _0 )
                {
                    cb.rectangle( j, _540 - konst - i * height, _40, height );
                    j += _40;
                }
                else
                {
                    cb.rectangle( j, _540 - konst - i * height, _20, height );
                    j += _20;
                    cb.rectangle( j, _540 - konst - i * height, _20, height );
                }
            }// end of for i
        }// end of for m
        cb.stroke();

        return konst;
    }

    private void fillFightTable( int konst )
        throws Exception
    {
        int height = _18;
        konst += height;
        Fight fight;

        for ( int m = _0; m < 2; m++ )
        {
            for ( int i = _0; i < _5; i++ )
            {
                int j = _24 + m * 290;
                j += _40;
                fight = ( fightingclass.getFightListMap().get( Integer.valueOf( _5 * m + i + _1 ) ) );
                if ( fight != null )
                {

                    writeText( j + _45, _545 - konst - i * height, fight.getFighterRed().getName(),
                               doBoldOutput( fight, _1 ) );
                    j += 90;
                    writeText( j + _45, _545 - konst - i * height, fight.getFighterBlue().getName(),
                               doBoldOutput( fight, _0 ) );
                    j += 90;
                    writeText( j + _10, _545 - konst - i * height, String.valueOf( fight.getPointsRed() ),
                               BOOLEAN_FALSE );
                    j += _20;
                    writeText( j + _10, _545 - konst - i * height, String.valueOf( fight.getPointsBlue() ),
                               BOOLEAN_FALSE );
                }
            }// end of for i
        }// end of for m
        cb.stroke();
    }

    private void fillResultTable( int konst )
        throws Exception
    {
        int height = _18, j = _0;
        konst += height;
        for ( int i = _0; i < fightingclass.getFighterList().size(); i++ )
        {
            j = _24;
            writeText( j + _40, _545 - konst - i * height,
                       fightingclass.getFighterList().get( i ).getFighter().getName(), BOOLEAN_FALSE );
            j += _80;
            writeText( j + _40, _545 - konst - i * height,
                       fightingclass.getFighterList().get( i ).getFighter().getFirstname(), BOOLEAN_FALSE );
            j += _80;
            writeText( j + _40, _545 - konst - i * height,
                       fightingclass.getFighterList().get( i ).getFighter().getTeam().getTeamName(), BOOLEAN_FALSE, 7,
                       1 );
            j += _80;

            j += _20;

            if ( i == ( _0 ) )
            {
                j += _40;
            }
            writeText( j + _7, _540 - konst - i * height + _5,
                       String.valueOf( fightingclass.getFighterList().get( i ).getResult().getResultFight1() ),
                       BOOLEAN_FALSE );
            writeText( j + _27, _540 - konst - i * height + _5,
                       String.valueOf( fightingclass.getFighterList().get( i ).getResult().getUbFight1() ),
                       BOOLEAN_FALSE );
            j += _40;
            if ( i == ( _1 ) )
            {
                j += _40;
            }
            writeText( j + _7, _540 - konst - i * height + _5,
                       String.valueOf( fightingclass.getFighterList().get( i ).getResult().getResultFight2() ),
                       BOOLEAN_FALSE );
            writeText( j + _27, _540 - konst - i * height + _5,
                       String.valueOf( fightingclass.getFighterList().get( i ).getResult().getUbFight2() ),
                       BOOLEAN_FALSE );
            j += _40;
            if ( i == ( 2 ) )
            {
                j += _40;
            }
            writeText( j + _7, _540 - konst - i * height + _5,
                       String.valueOf( fightingclass.getFighterList().get( i ).getResult().getResultFight3() ),
                       BOOLEAN_FALSE );
            writeText( j + _27, _540 - konst - i * height + _5,
                       String.valueOf( fightingclass.getFighterList().get( i ).getResult().getUbFight3() ),
                       BOOLEAN_FALSE );
            j += _40;
            if ( i == ( 3 ) )
            {
                j += _40;
            }
            writeText( j + _7, _540 - konst - i * height + _5,
                       String.valueOf( fightingclass.getFighterList().get( i ).getResult().getResultFight4() ),
                       BOOLEAN_FALSE );
            writeText( j + _27, _540 - konst - i * height + _5,
                       String.valueOf( fightingclass.getFighterList().get( i ).getResult().getUbFight4() ),
                       BOOLEAN_FALSE );
            j += _40;

            if ( i == ( 4 ) )
            {
                j += _40;
            }

            writeText( j + _15, _545 - konst - i * height,
                       String.valueOf( fightingclass.getFighterList().get( i ).getResult().getWinCount() ),
                       BOOLEAN_FALSE );
            j += _30;
            writeText( j + _15, _545 - konst - i * height,
                       String.valueOf( fightingclass.getFighterList().get( i ).getResult().getUBCount() ),
                       BOOLEAN_FALSE );
            j += _30;
            writeText( j + _15, _545 - konst - i * height,
                       String.valueOf( fightingclass.getFighterList().get( i ).getPlace() ), BOOLEAN_FALSE );
            cb.stroke();
        }
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
    private void writeText( int x, int y, String text, boolean bold )
        throws Exception
    {
        BaseFont bf;

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

        cb.setFontAndSize( bf, _10 );
        cb.showTextAligned( PdfContentByte.ALIGN_CENTER, text, x, y, _0 );
        cb.endText();

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
    private void writeText( int x, int y, String text, boolean bold, int size )
        throws Exception
    {
        BaseFont bf;

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

        cb.setFontAndSize( bf, size );
        cb.showTextAligned( PdfContentByte.ALIGN_LEFT, text, x, y, _0 );
        cb.endText();

        return;
    }

    /**
     * verarbeitet die Ausgabe eines Textes an die gegebene Position
     * 
     * @param x Koordinate
     * @param y Koordinate
     * @param text Ausgabetext
     * @param bold soll fett geschrieben werden
     * @param size Schriftgroesse
     * @param align 0: left, 1: center, default is 0
     * @throws Exception
     */
    private void writeText( int x, int y, String text, boolean bold, int size, int align )
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
        if ( ( text.equalsIgnoreCase( "&nbsp;" ) ) || ( text.equalsIgnoreCase( String.valueOf( TypeUtil.INT_MIN ) ) )
            || ( text.equalsIgnoreCase( String.valueOf( (double) TypeUtil.INT_MIN ) ) ) )
        {
            text = "";
        }
        cb.stroke();
        cb.beginText();

        cb.setFontAndSize( bf, size );
        if ( align == 1 )
            cb.showTextAligned( PdfContentByte.ALIGN_CENTER, text, x, y, _0 );
        else
            cb.showTextAligned( PdfContentByte.ALIGN_LEFT, text, x, y, _0 );
        cb.endText();

        return;
    }

    private void GrayOrDoubleField( boolean gray, int x, int y, int height )
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
     * @param Fight FightObj
     * @param redWhite 1 für rot, 0 für weiss
     * @return liefert 1 für Sieger und 0 für Verlierer des Kampfes
     */
    private boolean doBoldOutput( Fight fight, int redWhite )
    {
        if ( redWhite == _1 && fight.getWinnerId() == fight.getFighterIdRed() )
        {
            return BOOLEAN_TRUE;
        }
        if ( redWhite == _0 && fight.getWinnerId() == fight.getFighterIdBlue() )
        {
            return BOOLEAN_TRUE;
        }
        return BOOLEAN_FALSE;
    }
}
