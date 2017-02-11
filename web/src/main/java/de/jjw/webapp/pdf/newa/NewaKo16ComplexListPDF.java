/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaKo16ComplexListPDF.java
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
import java.util.Locale;
import java.util.Map;
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

import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.model.newa.NewaFight;
import de.jjw.model.newa.NewaKoClass;
import de.jjw.util.ICodestableTypeConstants;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.pdf.BasePDF;

public class NewaKo16ComplexListPDF
    implements IValueConstants
{
    protected final Logger log = Logger.getRootLogger();

    protected static final String KG = " Kg";

    protected static final String SPACE = " ";

    protected ResourceBundle rb;

    protected NewaKoClass newaclass;

    protected Document doc;

    protected PdfContentByte cb;

    protected String headLine = "";

    protected HttpServletResponse response;

    protected int constMainRound = _0;

    protected int constLoserRound = _0;

    protected String age = "";

    protected String weightclass = "";

    public NewaKo16ComplexListPDF( String ressourceBundle, NewaKoClass newaclass,
                                       HttpServletResponse response, String headLine, Locale local )
        throws Exception
    {
        this.response = response;
        this.rb = ResourceBundle.getBundle( ressourceBundle, local );
        this.newaclass = newaclass;

        this.headLine = headLine;
        createTables( newaclass );
    }

    public NewaKo16ComplexListPDF( String ressourceBundle, HttpServletResponse response, String headLine,
                                       Locale local )
    {
        this.response = response;
        this.rb = ResourceBundle.getBundle( ressourceBundle, local );
        this.headLine = headLine;
    }

    public void createPoolBlanco()
        throws Exception
    {
        Document doc = new Document( PageSize.A4.rotate(), _36, _36, _36, _36 );

        ByteArrayOutputStream baos = new ByteArrayOutputStream( 10000 );

        PdfWriter writer = PdfWriter.getInstance( doc, baos );

        doc.open();

        cb = writer.getDirectContent();
        doc.setPageSize( PageSize.A4.rotate() );
        doc.newPage();
        doc.add( new Paragraph( SPACE ) );

        writeText( _24, _24, rb.getString( "pdf.copyright" ) + "", BOOLEAN_FALSE, 8 );
        writeText( 600, 540, headLine, BOOLEAN_TRUE, _16, _1 );

        constMainRound = 570;
        constLoserRound = 180;
        createMainRoundTable( constMainRound );
        createMainRoundStandardText( constMainRound );
        createLoserRoundTable( constLoserRound );
        createLoserRoundStandardText( constLoserRound );

        doc.close();

        response.setContentLength( baos.size() );

        ServletOutputStream out = response.getOutputStream();
        baos.writeTo( out );
        baos.flush();
    }

    public void createTables( NewaKoClass newaclass )
        throws Exception
    {
        try
        {
            doc = new Document( PageSize.A4.rotate(), _36, _36, _36, _36 );
            ByteArrayOutputStream baos = new ByteArrayOutputStream( 10000 );
            PdfWriter writer = PdfWriter.getInstance( doc, baos );

            doc.open();
            cb = writer.getDirectContent();
            doc.setPageSize( PageSize.A4.rotate() );
            doc.newPage();
            doc.add( new Paragraph( SPACE ) );

            writeText( 600, 540, headLine, BOOLEAN_TRUE, _16, _1 );
            constMainRound = 570;
            constLoserRound = 180;
            createMainRoundTable( constMainRound );
            createMainRoundStandardText( constMainRound );
            createLoserRoundTable( constLoserRound );
            createLoserRoundStandardText( constLoserRound );

            BasePDF.addWatermark( writer.getDirectContentUnder(), "Newaza", 140, true );

            if ( newaclass != null )
            {
                writeText( 600, 540, headLine, true, 16 );
                writeText( 600,
                           510,
                           newaclass.getAge().getDescription()
                    + SPACE
                    + CodestableMain.getInstance().getCodestableCodeValueByType( ICodestableTypeConstants.TYPE_SEX,
                                                                                            newaclass.getSex(),
                                                                                 response.getLocale() ) + SPACE
 + newaclass.getWeightclass() + KG, true, 14 );
                // writeText (600,510,rb.getString ("pdf."+fightingclass.getAge ())+SPACE+fightingclass.getSex
                // ()+SPACE+fightingclass.getWeightclass ()+" Kg",BOOLEAN_TRUE,_14,_1);
                fillMainRoundTable( newaclass );

                fillLoserRoundTable( newaclass );
                manageFightListForKo16( newaclass, writer );
            }
            doc.close();

            response.setContentLength( baos.size() );

            ServletOutputStream out = response.getOutputStream();
            baos.writeTo( out );
            baos.flush();
        }
        catch ( Exception e )
        {
            log.error( "NewaKo16ComplexListPDF.createTables: Exception: \ncan not create pdf document \n", e );
            throw e;
        }
    }

    private void createMainRoundStandardText( int konst )
        throws Exception
    {
        int height = _12;
        writeText( _28, konst + _2, String.valueOf( _1 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _28, konst - _2 * height + _2, String.valueOf( _9 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _28, konst - _4 * height + _2, String.valueOf( _5 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _28, konst - _6 * height + _2, String.valueOf( _13 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _28, konst - _8 * height + _2, String.valueOf( _3 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _28, konst - _10 * height + _2, String.valueOf( _11 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _28, konst - _12 * height + _2, String.valueOf( _7 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _28, konst - _14 * height + _2, String.valueOf( _15 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _28, konst - _16 * height + _2, String.valueOf( _2 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _28, konst - _18 * height + _2, String.valueOf( _10 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _28, konst - _20 * height + _2, String.valueOf( _6 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _28, konst - _22 * height + _2, String.valueOf( _14 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _28, konst - _24 * height + _2, String.valueOf( _4 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _28, konst - _26 * height + _2, String.valueOf( _12 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _28, konst - _28 * height + _2, String.valueOf( _8 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _28, konst - 30 * height + _2, String.valueOf( _16 ), BOOLEAN_FALSE, _10, _0 );

        writeText( _228, konst - height + _2, String.valueOf( _1 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _228, konst - _5 * height + _2, String.valueOf( _2 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _228, konst - _9 * height + _2, String.valueOf( _3 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _228, konst - _13 * height + _2, String.valueOf( _4 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _228, konst - 17 * height + _2, String.valueOf( _5 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _228, konst - _21 * height + _2, String.valueOf( _6 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _228, konst - _25 * height + _2, String.valueOf( _7 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _228, konst - 29 * height + _2, String.valueOf( _8 ), BOOLEAN_FALSE, _10, _0 );

        writeText( _368, konst - _3 * height + _2, String.valueOf( _9 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _368, konst - _11 * height + _2, String.valueOf( _10 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _368, konst - 19 * height + _2, String.valueOf( _11 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _368, konst - 27 * height + _2, String.valueOf( _12 ), BOOLEAN_FALSE, _10, _0 );

        writeText( _508, konst - _7 * height + _2, String.valueOf( _21 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _508, konst - 23 * height + _2, String.valueOf( _22 ), BOOLEAN_FALSE, _10, _0 );

        writeText( _450, konst - _7 * height + _2, POOL_A, BOOLEAN_TRUE, _16, _0 );
        writeText( _450, konst - 23 * height + _2, "B", BOOLEAN_TRUE, _16, _0 );

        writeText( 648, konst - _15 * height + _2, String.valueOf( 27 ), BOOLEAN_FALSE, _10, _0 );
    }

    private void createLoserRoundStandardText( int konst )
        throws Exception
    {
        int height = _12;
        writeText( _228, konst - height + _2, String.valueOf( _13 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _228, konst - _5 * height + _2, String.valueOf( _14 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _228, konst - _9 * height + _2, String.valueOf( _15 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _228, konst - _13 * height + _2, String.valueOf( _16 ), BOOLEAN_FALSE, _10, _0 );

        writeText( _254, konst + height + _2, String.valueOf( _11 ), BOOLEAN_FALSE, _10, _0 ); // trost
        writeText( _254, konst - _3 * height + _2, String.valueOf( _12 ), BOOLEAN_FALSE, _10, _0 ); // trost
        writeText( _254, konst - _7 * height + _2, String.valueOf( _9 ), BOOLEAN_FALSE, _10, _0 ); // trost
        writeText( _254, konst - _11 * height + _2, String.valueOf( _10 ), BOOLEAN_FALSE, _10, _0 ); // trost

        writeText( _384, konst + _2, String.valueOf( 17 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _384, konst - _4 * height + _2, String.valueOf( _18 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _384, konst - _8 * height + _2, String.valueOf( 19 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _384, konst - _12 * height + _2, String.valueOf( _20 ), BOOLEAN_FALSE, _10, _0 );

        writeText( _524, konst - _2 * height + _2, String.valueOf( 23 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _524, konst - _10 * height + _2, String.valueOf( _24 ), BOOLEAN_FALSE, _10, _0 );

        writeText( _550, konst + _2, String.valueOf( _21 ), BOOLEAN_FALSE, _10, _0 ); // trost
        writeText( _550, konst - _8 * height + _2, String.valueOf( _22 ), BOOLEAN_FALSE, _10, _0 );// trost

        writeText( _450, konst - _2 * height + _2, POOL_A, BOOLEAN_TRUE, _16, _0 );
        writeText( _450, konst - _10 * height + _2, POOL_B, BOOLEAN_TRUE, _16, _0 );

        writeText( _680, konst - height + _2, String.valueOf( _25 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _680, konst - _9 * height + _2, String.valueOf( _26 ), BOOLEAN_FALSE, _10, _0 );
    }

    private int createMainRoundTable( int konst )
        throws Exception
    {
        cb.setColorStroke( new BaseColor( 0, 0, 0 ) );
        cb.setLineWidth( 1f );
        int height = _12; // konst

        for ( int i = _0; i < 31; i++ )
        {
            if ( i % _2 == _0 )
            {
                cb.rectangle( _20, konst - i * height, _16, height );
                cb.rectangle( _36, konst - i * height, 160, height );
                cb.rectangle( _196, konst - i * height, _24, height );
                // writeText(28,konst-i*height+2,String.valueOf (i), BOOLEAN_FALSE,10,0);
            }
            if ( i % _4 == _1 )
            {
                cb.rectangle( _220, konst - i * height - height, _16, _3 * height );
                cb.rectangle( _236, konst - i * height, 100, height );
                cb.rectangle( 336, konst - i * height, _24, height );
            }

            if ( i % _8 == _3 )
            {
                cb.rectangle( 360, konst - i * height - _2 * height, _16, _5 * height );
                cb.rectangle( 376, konst - i * height, 100, height );
                cb.rectangle( 476, konst - i * height, _24, height );
            }
            if ( i % _16 == _7 )
            {
                cb.rectangle( 500, konst - i * height - _4 * height, _16, _9 * height );
                cb.rectangle( 516, konst - i * height, 100, height );
                cb.rectangle( 616, konst - i * height, _24, height );
            }
            if ( i == _15 )
            {
                cb.rectangle( 640, konst - i * height - _8 * height, _16, 17 * height );
                cb.rectangle( 656, konst - i * height, 100, height );
            }
            cb.stroke();
        }
        return konst;
    }

    private int createLoserRoundTable( int konst )
        throws Exception
    {
        cb.setColorStroke( new BaseColor( 0, 0, 0 ) );
        cb.setLineWidth( 1f );
        int height = _12, LoserNr = _0; // konst

        for ( int i = _0; i < _16; i++ )
        {
            if ( i % _2 == _0 )
            { // 1. Runde
                LoserNr++;
                cb.rectangle( _80, konst - i * height, _16, height );
                cb.rectangle( _96, konst - i * height, 100, height );
                cb.rectangle( _196, konst - i * height, _24, height );
                writeText( _88, konst - i * height + _2, String.valueOf( LoserNr ), BOOLEAN_FALSE, _10, _0 );
            }
            if ( i % _4 == _1 )
            { // 2. Runde
                cb.rectangle( _220, konst - i * height - height, _16, _3 * height );
                cb.rectangle( _236, konst - i * height, _116, height );

                cb.rectangle( 246, konst - i * height + _2 * height, _16, height );
                cb.rectangle( 262, konst - i * height + _2 * height, 90, height );

                cb.rectangle( 352, konst - i * height, _24, height );
                cb.rectangle( 352, konst - i * height + _2 * height, _24, height );
                cb.rectangle( 376, konst - i * height, _16, _3 * height );
            }
            if ( i % _4 == _0 )
            { // 3. Runde
                cb.rectangle( 392, konst - i * height, 100, height );
                cb.rectangle( 492, konst - i * height, _24, height );
            }
            if ( i % _8 == _2 )
            { // 4. Runde und Finale
                cb.rectangle( 516, konst - i * height - _2 * height, _16, _5 * height );
                cb.rectangle( 532, konst - i * height, _116, height );

                cb.rectangle( 542, konst - i * height + _2 * height, _16, height );
                cb.rectangle( 558, konst - i * height + _2 * height, 90, height );

                cb.rectangle( 648, konst - i * height, _24, height );
                cb.rectangle( 648, konst - i * height + _2 * height, _24, height );
                cb.rectangle( 672, konst - i * height, _16, _3 * height );
                cb.rectangle( 688, konst - i * height + height, 100, height );
            }

            cb.stroke();
        }
        return konst;
    }

    private void fillMainRoundTable( NewaKoClass fc )
        throws Exception
    {
        int height = _12;
        int konst = constMainRound;
        writeTextFirstRound( _116, konst + _2, fc.getFightListMapPoolA().get( _3 ), _8, _0, FIGHTER_RED );
        writeText( _208, konst + _2, String.valueOf( fc.getFightListMapPoolA().get( _3 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _2 * height + _2, fc.getFightListMapPoolA().get( _3 ), _8, _0, FIGHTER_BLUE );
        writeText( _208, konst - _2 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _3 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _4 * height + _2, fc.getFightListMapPoolA().get( _4 ), _8, _0, FIGHTER_RED );
        writeText( _208, konst - _4 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _4 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _6 * height + _2, fc.getFightListMapPoolA().get( _4 ), _8, _0, FIGHTER_BLUE );
        writeText( _208, konst - _6 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _4 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _8 * height + _2, fc.getFightListMapPoolA().get( _5 ), _8, _0, FIGHTER_RED );
        writeText( _208, konst - _8 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _5 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _10 * height + _2, fc.getFightListMapPoolA().get( _5 ), _8, _0, FIGHTER_BLUE );
        writeText( _208, konst - _10 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _5 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _12 * height + _2, fc.getFightListMapPoolA().get( _6 ), _8, _0, FIGHTER_RED );
        writeText( _208, konst - _12 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _6 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _14 * height + _2, fc.getFightListMapPoolA().get( _6 ), _8, _0, FIGHTER_BLUE );
        writeText( _208, konst - _14 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _6 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _16 * height + _2, fc.getFightListMapPoolB().get( _3 ), _8, _0, FIGHTER_RED );
        writeText( _208, konst - _16 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _3 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _18 * height + _2, fc.getFightListMapPoolB().get( _3 ), _8, _0, FIGHTER_BLUE );
        writeText( _208, konst - _18 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _3 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _20 * height + _2, fc.getFightListMapPoolB().get( _4 ), _8, _0, FIGHTER_RED );
        writeText( _208, konst - _20 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _4 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _22 * height + _2, fc.getFightListMapPoolB().get( _4 ), _8, _0, FIGHTER_BLUE );
        writeText( _208, konst - _22 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _4 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _24 * height + _2, fc.getFightListMapPoolB().get( _5 ), _8, _0, FIGHTER_RED );
        writeText( _208, konst - _24 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _5 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _26 * height + _2, fc.getFightListMapPoolB().get( _5 ), _8, _0, FIGHTER_BLUE );
        writeText( _208, konst - _26 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _5 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _28 * height + _2, fc.getFightListMapPoolB().get( _6 ), _8, _0, FIGHTER_RED );
        writeText( _208, konst - _28 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _6 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - 30 * height + _2, fc.getFightListMapPoolB().get( _6 ), _8, _0, FIGHTER_BLUE );
        writeText( _208, konst - _30 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _6 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        // Round 3
        writeFighterName( _286, konst - height + _2, fc.getFightListMapPoolA().get( _1 ), _10, _0, FIGHTER_RED );
        writeText( _348, konst - height + _2, String.valueOf( fc.getFightListMapPoolA().get( _1 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _286, konst - _5 * height + _2, fc.getFightListMapPoolA().get( _1 ), _10, _0, FIGHTER_BLUE );
        writeText( _348, konst - _5 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _1 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _286, konst - _9 * height + _2, fc.getFightListMapPoolA().get( _2 ), _10, _0, FIGHTER_RED );
        writeText( _348, konst - _9 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _2 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _286, konst - _13 * height + _2, fc.getFightListMapPoolA().get( _2 ), _10, _0, FIGHTER_BLUE );
        writeText( _348, konst - _13 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _2 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _286, konst - 17 * height + _2, fc.getFightListMapPoolB().get( _1 ), _10, _0, FIGHTER_RED );
        writeText( _348, konst - 17 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _1 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _286, konst - _21 * height + _2, fc.getFightListMapPoolB().get( _1 ), _10, _0, FIGHTER_BLUE );
        writeText( _348, konst - _21 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _1 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _286, konst - _25 * height + _2, fc.getFightListMapPoolB().get( _2 ), _10, _0, FIGHTER_RED );
        writeText( _348, konst - _25 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _2 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _286, konst - 29 * height + _2, fc.getFightListMapPoolB().get( _2 ), _10, _0, FIGHTER_BLUE );
        writeText( _348, konst - 29 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _2 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        // Round2
        writeFighterName( _426, konst - _3 * height + _2, fc.getFightListMapPoolA().get( _0 ), _10, _0, FIGHTER_RED );
        writeText( _488, konst - _3 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _0 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _426, konst - _11 * height + _2, fc.getFightListMapPoolA().get( _0 ), _10, _0, FIGHTER_BLUE );
        writeText( _488, konst - _11 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _0 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _426, konst - 19 * height + _2, fc.getFightListMapPoolB().get( _0 ), _10, _0, FIGHTER_RED );
        writeText( _488, konst - 19 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _0 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _426, konst - 27 * height + _2, fc.getFightListMapPoolB().get( _0 ), _10, _0, FIGHTER_BLUE );
        writeText( _488, konst - 27 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _0 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        // Round1
        writeFighterName( _566, konst - _7 * height + _2, fc.getFinalFight(), _10, _0, FIGHTER_RED );
        writeText( _628, konst - _7 * height + _2, String.valueOf( fc.getFinalFight().getPointsRed() ), BOOLEAN_FALSE,
                   _10, _0 );
        writeFighterName( _566, konst - 23 * height + _2, fc.getFinalFight(), _10, _0, FIGHTER_BLUE );
        writeText( _628, konst - 23 * height + _2, String.valueOf( fc.getFinalFight().getPointsBlue() ), BOOLEAN_FALSE,
                   _10, _0 );
        // winner
        if ( fc.getFinalFight().getWinnerId().longValue() == fc.getFinalFight().getFighterIdRed()
            && fc.getFinalFight().getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( _706, konst - _15 * height + _2, fc.getFinalFight().getFighterRed().getName(), BOOLEAN_TRUE,
                       _10, _0 );
        }
        if ( fc.getFinalFight().getWinnerId().longValue() == fc.getFinalFight().getFighterIdBlue()
            && fc.getFinalFight().getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( _706, konst - _15 * height + _2, fc.getFinalFight().getFighterBlue().getName(), BOOLEAN_TRUE,
                       _10, _0 );
        }
    }

    private void fillLoserRoundTable( NewaKoClass fc )
        throws Exception
    {
        int height = _12;
        int konst = constLoserRound;
        writeFighterName( 146, konst + _2, fc.getFightListLooserMapPoolA().get( _4 ), _10, _0, FIGHTER_RED );
        writeText( _208, konst + _2, String.valueOf( fc.getFightListLooserMapPoolA().get( _4 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
        writeFighterName( 146, konst - _2 * height + _2, fc.getFightListLooserMapPoolA().get( _4 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _208, konst - _2 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _4 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( 146, konst - _4 * height + _2, fc.getFightListLooserMapPoolA().get( _5 ), _10, _0,
                          FIGHTER_RED );
        writeText( _208, konst - _4 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _5 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( 146, konst - _6 * height + _2, fc.getFightListLooserMapPoolA().get( _5 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _208, konst - _6 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _5 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( 146, konst - _8 * height + _2, fc.getFightListLooserMapPoolB().get( _4 ), _10, _0,
                          FIGHTER_RED );
        writeText( _208, konst - _8 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _4 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( 146, konst - _10 * height + _2, fc.getFightListLooserMapPoolB().get( _4 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _208, konst - _10 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _4 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( 146, konst - _12 * height + _2, fc.getFightListLooserMapPoolB().get( _5 ), _10, _0,
                          FIGHTER_RED );
        writeText( _208, konst - _12 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _5 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( 146, konst - _14 * height + _2, fc.getFightListLooserMapPoolB().get( _5 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _208, konst - _14 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _5 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        // Round3
        writeFighterName( _307, konst + height + _2, fc.getFightListLooserMapPoolA().get( _2 ), _10, _0, FIGHTER_RED );
        writeText( _364, konst + height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _2 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _302, konst - height + _2, fc.getFightListLooserMapPoolA().get( _2 ), _10, _0, FIGHTER_BLUE );
        writeText( _364, konst - height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _2 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _307, konst - _3 * height + _2, fc.getFightListLooserMapPoolA().get( _3 ), _10, _0,
                          FIGHTER_RED );
        writeText( _364, konst - _3 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _3 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _302, konst - _5 * height + _2, fc.getFightListLooserMapPoolA().get( _3 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _364, konst - _5 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _3 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );

        writeFighterName( _307, konst - _7 * height + _2, fc.getFightListLooserMapPoolB().get( _2 ), _10, _0,
                          FIGHTER_RED );
        writeText( _364, konst - _7 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _2 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _302, konst - _9 * height + _2, fc.getFightListLooserMapPoolB().get( _2 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _364, konst - _9 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _2 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _307, konst - _11 * height + _2, fc.getFightListLooserMapPoolB().get( _3 ), _10, _0,
                          FIGHTER_RED );
        writeText( _364, konst - _11 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _3 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _302, konst - _13 * height + _2, fc.getFightListLooserMapPoolB().get( _3 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _364, konst - _13 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _3 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        // Round2
        writeFighterName( _442, konst + _2, fc.getFightListLooserMapPoolA().get( _1 ), _10, _0, FIGHTER_RED );
        writeText( _504, konst + _2, String.valueOf( fc.getFightListLooserMapPoolA().get( _1 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _442, konst - _4 * height + _2, fc.getFightListLooserMapPoolA().get( _1 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _504, konst - _4 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _1 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );

        writeFighterName( _442, konst - _8 * height + _2, fc.getFightListLooserMapPoolB().get( _1 ), _10, _0,
                          FIGHTER_RED );
        writeText( _504, konst - _8 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _1 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _442, konst - _12 * height + _2, fc.getFightListLooserMapPoolB().get( _1 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _504, konst - _12 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _1 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        // Round 1
        writeFighterName( _603, konst + _2, fc.getFightListLooserMapPoolA().get( _0 ), _10, _0, FIGHTER_RED );
        writeText( _660, konst + _2, String.valueOf( fc.getFightListLooserMapPoolA().get( _0 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
        writeFighterName( 598, konst - _2 * height + _2, fc.getFightListLooserMapPoolA().get( _0 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _660, konst - _2 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _0 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );

        writeFighterName( _603, konst - _8 * height + _2, fc.getFightListLooserMapPoolB().get( _0 ), _10, _0,
                          FIGHTER_RED );
        writeText( _660, konst - _8 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _0 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( 598, konst - _10 * height + _2, fc.getFightListLooserMapPoolB().get( _0 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _660, konst - _10 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _0 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        // 3rd

        if ( fc.getFightListLooserMapPoolA().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolA().get(
                                                                                                                         _0 ).getFighterIdRed()
            && fc.getFightListLooserMapPoolA().get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( _738, konst - height + _2, fc.getFightListLooserMapPoolA().get( _0 ).getFighterRed().getName(),
                       BOOLEAN_TRUE, _10, _0 );
        }
        if ( fc.getFightListLooserMapPoolA().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolA().get(
                                                                                                                         _0 ).getFighterIdBlue()
            && fc.getFightListLooserMapPoolA().get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( _738, konst - height + _2, fc.getFightListLooserMapPoolA().get( _0 ).getFighterBlue().getName(),
                       BOOLEAN_TRUE, _10, _0 );
        }

        if ( fc.getFightListLooserMapPoolB().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolB().get(
                                                                                                                         _0 ).getFighterIdRed()
            && fc.getFightListLooserMapPoolB().get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( _738, konst - _9 * height + _2,
                       fc.getFightListLooserMapPoolB().get( _0 ).getFighterRed().getName(), BOOLEAN_TRUE, _10, _0 );
        }
        if ( fc.getFightListLooserMapPoolB().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolB().get(
                                                                                                                         _0 ).getFighterIdBlue()
            && fc.getFightListLooserMapPoolB().get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( _738, konst - _9 * height + _2,
                       fc.getFightListLooserMapPoolB().get( _0 ).getFighterBlue().getName(), BOOLEAN_TRUE, _10, _0 );
        }

    }

    /**
     * verarbeitet die Ausgabe eines Textes an die gegebene Position mit der Größe size
     * 
     * @param x Koordinate
     * @param y Koordinate
     * @param text AusgabeText
     * @param bold Fettdruck
     * @param size Schriftgrösse
     * @param loc 0 für center, 1 für left
     * @throws Exception
     */
    protected void writeText( int x, int y, String text, boolean bold, int size, int loc )
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
            if ( INT_MIN_STRING.equals( text ) )
            {
                text = "";
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
            log.error( "NewaKo16ComplexListPDF.writeText: Exception: \n can not create pdf document \n", e );
        }
        return;
    }

    /**
     * verarbeitet die Ausgabe eines Textes an die gegebene Position mit der Größe size
     * 
     * @param x Koordinate
     * @param y Koordinate
     * @param text AusgabeText
     * @param bold Fettdruck
     * @param size Schriftgrösse
     * @param loc 0 für center, 1 für left
     * @throws Exception
     */
    protected void writeTextFirstRound( int x, int y, NewaFight fight, int size, int loc, int redOrBlue )
        throws Exception
    {
        BaseFont bf;
        try
        {
            if ( fight == null )
            {
                return;
            }

            // if (bold) bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            bf = BaseFont.createFont( BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
            StringBuffer sb = new StringBuffer( 50 );

            if ( redOrBlue == FIGHTER_RED && fight.getFighterRed() != null )
            {
                if ( fight.getFighterRed().getName() != null )
                {
                    sb.append( fight.getFighterRed().getName() );
                    sb.append( SPACE );
                }
                if ( fight.getFighterRed().getFirstname() != null )
                {
                    sb.append( fight.getFighterRed().getFirstname() );
                    sb.append( SPACE );
                }
                if ( fight.getFighterRed().getTeam() != null )
                {
                    sb.append( fight.getFighterRed().getTeam().getTeamName() );
                }

            }
            if ( redOrBlue == FIGHTER_BLUE && fight.getFighterBlue() != null )
            {
                if ( fight.getFighterBlue().getName() != null )
                {
                    sb.append( fight.getFighterBlue().getName() );
                    sb.append( SPACE );
                }
                if ( fight.getFighterBlue().getFirstname() != null )
                {
                    sb.append( fight.getFighterBlue().getFirstname() );
                    sb.append( SPACE );
                }
                if ( fight.getFighterBlue().getTeam() != null )
                {
                    sb.append( fight.getFighterBlue().getTeam().getTeamName() );
                }
            }

            cb.stroke();
            cb.beginText();
            cb.setFontAndSize( bf, size );
            if ( loc == _0 )
            {
                cb.showTextAligned( PdfContentByte.ALIGN_CENTER, sb.toString(), x, y, _0 );
            }
            if ( loc == _1 )
            {
                cb.showTextAligned( PdfContentByte.ALIGN_LEFT, sb.toString(), x, y, _0 );
            }
            cb.endText();
        }
        catch ( Exception e )
        {
            log.error( "NewaKo16ComplexListPDF.writeText: Exception: \n can not create pdf document \n", e );
        }
        return;
    }

    /**
     * verarbeitet die Ausgabe eines Textes an die gegebene Position mit der Größe size
     * 
     * @param x Koordinate
     * @param y Koordinate
     * @param text AusgabeText
     * @param bold Fettdruck
     * @param size Schriftgrösse
     * @param loc 0 für center, 1 für left
     * @throws Exception
     */
    protected void writeFighterName( int x, int y, NewaFight fight, int size, int loc, int redOrBlue )
        throws Exception
    {
        BaseFont bf;
        try
        {
            if ( fight == null )
            {
                return;
            }

            bf = BaseFont.createFont( BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
            StringBuffer sb = new StringBuffer( 50 );

            if ( redOrBlue == FIGHTER_RED && fight.getFighterRed() != null )
            {
                if ( fight.getFighterRed().getName() != null )
                {
                    sb.append( fight.getFighterRed().getName() );
                }
                if ( fight.getWinnerId().longValue() == fight.getFighterIdRed() )
                {
                    bf = BaseFont.createFont( BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
                }

            }
            if ( redOrBlue == FIGHTER_BLUE && fight.getFighterBlue() != null )
            {
                if ( fight.getFighterBlue().getName() != null )
                {
                    sb.append( fight.getFighterBlue().getName() );
                }
                if ( fight.getWinnerId().longValue() == fight.getFighterIdBlue() )
                {
                    bf = BaseFont.createFont( BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
                }
            }

            cb.stroke();
            cb.beginText();
            cb.setFontAndSize( bf, size );
            if ( loc == _0 )
            {
                cb.showTextAligned( PdfContentByte.ALIGN_CENTER, sb.toString(), x, y, _0 );
            }
            if ( loc == _1 )
            {
                cb.showTextAligned( PdfContentByte.ALIGN_LEFT, sb.toString(), x, y, _0 );
            }
            cb.endText();
        }
        catch ( Exception e )
        {
            log.error( "NewaKo16ComplexListPDF.writeText: Exception: \n can not create pdf document \n", e );
        }
        return;
    }

    /**
     * organisiert das anlegen der Kampflisten
     * 
     * @param fc
     * @param writer
     */
    private void manageFightListForKo16( NewaKoClass fc, PdfWriter writer )
    {
        try
        {
            int count = _1;

            count = manageFightList( fc.getFightListMapPoolA(), _3, _6, count, BOOLEAN_TRUE, writer );
            count = manageFightList( fc.getFightListMapPoolB(), _3, _6, count, BOOLEAN_TRUE, writer );
            count = manageFightList( fc.getFightListMapPoolA(), _1, _2, count, BOOLEAN_FALSE, writer );
            count = manageFightList( fc.getFightListMapPoolB(), _1, _2, count, BOOLEAN_FALSE, writer );

            count = manageFightList( fc.getFightListLooserMapPoolA(), _4, _5, count, BOOLEAN_TRUE, writer );
            count = manageFightList( fc.getFightListLooserMapPoolB(), _4, _5, count, BOOLEAN_TRUE, writer );
            count = manageFightList( fc.getFightListLooserMapPoolA(), _2, _3, count, BOOLEAN_FALSE, writer );
            count = manageFightList( fc.getFightListLooserMapPoolB(), _2, _3, count, BOOLEAN_FALSE, writer );

            count = manageFightList( fc.getFightListMapPoolA(), _0, _0, count, BOOLEAN_FALSE, writer );
            count = manageFightList( fc.getFightListMapPoolB(), _0, _0, count, BOOLEAN_FALSE, writer );

            count = manageFightList( fc.getFightListLooserMapPoolA(), _1, _1, count, BOOLEAN_FALSE, writer );
            count = manageFightList( fc.getFightListLooserMapPoolB(), _1, _1, count, BOOLEAN_FALSE, writer );
            count = manageFightList( fc.getFightListLooserMapPoolA(), _0, _0, count, BOOLEAN_FALSE, writer );
            count = manageFightList( fc.getFightListLooserMapPoolB(), _0, _0, count, BOOLEAN_FALSE, writer );

            createFightList( fc.getFinalFight(), count, writer );
        }
        catch ( Exception e )
        {
            log.error( "FightingKo16ComplexListPDF.manageFightListForKo16: Exception: \ncan not create pdf document \n",
                       e );
        }
    }

    protected int manageFightList( Map<Integer, NewaFight> fightMap, int start, int end, int count, boolean firstRound,
                                   PdfWriter writer )
        throws Exception
    {
        for ( int i = start; i <= end; i++ )
        {

            if ( fightMap.get( i ).getId() == null )
            {
                count++;
                continue;
            }
            createFightList( fightMap.get( i ), count, writer );
            count++;
        }
        return count;
    }

    protected void createFightList( NewaFight fight, int nr, PdfWriter writer )
        throws Exception
    {
        doc.setPageSize( PageSize.A4 );
        doc.newPage();
        doc.add( new Paragraph( SPACE ) );
        NewaLogListPDF FL = new NewaLogListPDF( rb, cb, fight, headLine );
        FL.createFightList( newaclass.getAge().getDescription(), newaclass.getWeightclass(),
                            String.valueOf( nr ) );
        BasePDF.addWatermark( writer.getDirectContentUnder(), "Newaza", 140, false );
        cb.stroke();
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
        cb.showTextAligned( PdfContentByte.ALIGN_LEFT, text, x, y, 0 );
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
    protected void writeTextCentral( int x, int y, String text, boolean bold, int size )
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
        cb.showTextAligned( PdfContentByte.ALIGN_CENTER, text, x, y, 0 );
        cb.endText();

        return;
    }
}
