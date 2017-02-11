/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightingKo32ComplexListPDF.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:49
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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.model.newa.NewaKoClass;
import de.jjw.util.ICodestableTypeConstants;
import de.jjw.util.TypeUtil;

public class NewaKo32ComplexListPDF
    extends NewaKo16ComplexListPDF
{

    public NewaKo32ComplexListPDF( String ressourceBundle, NewaKoClass newaclass,
                                       HttpServletResponse response, String headLine, Locale local )
        throws Exception
    {
        super( ressourceBundle, newaclass, response, headLine, local );

    }

    public NewaKo32ComplexListPDF( String ressourceBundle, HttpServletResponse response, String headLine,
                                       Locale local )
    {
        super( ressourceBundle, response, headLine, local );
    }

    public void createPoolBlanco()
        throws Exception
    {
        Document doc = new Document( PageSize.A4.rotate(), _36, _36, _36, _36 );
        ByteArrayOutputStream baos = new ByteArrayOutputStream( 20000 );
        PdfWriter writer = PdfWriter.getInstance( doc, baos );

        doc.open();
        cb = writer.getDirectContent();
        doc.setPageSize( PageSize.A4.rotate() );
        doc.newPage();
        doc.add( new Paragraph( SPACE ) );

        writeText( _24, _24, rb.getString( "pdf.copyright" ) + "", BOOLEAN_FALSE, 8 );
        writeTextCentral( 420, 540, headLine, true, _16 );

        constMainRound = 570;
        constLoserRound = 500;

        createMainRoundTable( constMainRound );
        createMainRoundStandardText( constMainRound );

        doc.newPage();
        doc.add( new Paragraph( " " ) );
        createLoserRoundTable( constLoserRound );
        writeText( 600, 540, headLine, true, _16 );

        writeText( 24, 24, rb.getString( "pdf.copyright" ) + "", BOOLEAN_FALSE, _8, _1 );
        createLoserRoundStandardText( constLoserRound );

        doc.close();

        response.setContentLength( baos.size() );

        ServletOutputStream out = response.getOutputStream();
        baos.writeTo( out );
        baos.flush();
    }

    @Override
    public void createTables( NewaKoClass newaclass )
        throws Exception
    {
        try
        {
            doc = new Document( PageSize.A4.rotate(), _36, _36, _36, _36 );
            ByteArrayOutputStream baos = new ByteArrayOutputStream( 20000 );
            PdfWriter writer = PdfWriter.getInstance( doc, baos );

            doc.open();
            cb = writer.getDirectContent();
            doc.setPageSize( PageSize.A4.rotate() );
            doc.newPage();
            doc.add( new Paragraph( SPACE ) );

            constMainRound = 570;
            constLoserRound = 500;

            createMainRoundTable( constMainRound );
            createMainRoundStandardText( constMainRound );
            if ( newaclass != null )
            {
                writeTextCentral( 420, 540, headLine, true, _16 );
                writeTextCentral( 420,
                                  510,
                                  newaclass.getAge().getDescription()
                    + SPACE
                    + CodestableMain.getInstance().getCodestableCodeValueByType( ICodestableTypeConstants.TYPE_SEX,
                                                                                                   newaclass.getSex(),
                                                                                 response.getLocale() ) + SPACE
 + newaclass.getWeightclass() + KG, true, 14 );
                // writeText (600,510,rb.getString ("pdf."+fightingclass.getAge ())+SPACE+fightingclass.getSex
                // ()+SPACE+fightingclass.getWeightclass ()+" Kg",BOOLEAN_TRUE,_14,_1);
                writeText( 24, 24, rb.getString( "pdf.copyright" ) + "", BOOLEAN_FALSE, _8, _1 );
                fillMainRoundTable( newaclass );

                cb.stroke();
                doc.newPage();
                doc.add( new Paragraph( " " ) );
                createLoserRoundTable( constLoserRound );
                writeText( 600, 540, headLine, true, _16 );
                writeText( 600,
                           510,
                           newaclass.getAge().getDescription()
                    + SPACE
                    + CodestableMain.getInstance().getCodestableCodeValueByType( ICodestableTypeConstants.TYPE_SEX,
                                                                                            newaclass.getSex(),
                                                                                 response.getLocale() ) + SPACE
 + newaclass.getWeightclass() + KG, true, 14 );
                writeText( 24, 24, rb.getString( "pdf.copyright" ) + "", BOOLEAN_FALSE, _8, _1 );
                createLoserRoundStandardText( constLoserRound );
                fillLoserRoundTable( newaclass );
                manageFightListForKo32( newaclass, writer );
            }
            doc.close();

            response.setContentLength( baos.size() );

            ServletOutputStream out = response.getOutputStream();
            baos.writeTo( out );
            baos.flush();
        }
        catch ( Exception e )
        {
            log.error( "NewaKo32ComplexListPDF.createMainRoundTable: Exception: \ncan not create pdf document \n",
                       e );
            throw e;
        }
    }

    private void createMainRoundStandardText( int konst )
        throws Exception
    {
        int height = 12;
        writeText( _28, konst + _2, String.valueOf( 1 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _812, konst + _2, String.valueOf( _2 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _28, konst - _2 * height + _2, String.valueOf( 17 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _812, konst - _2 * height + _2, String.valueOf( 18 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _28, konst - 4 * height + _2, String.valueOf( 9 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _812, konst - 4 * height + _2, String.valueOf( _10 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _28, konst - 6 * height + _2, String.valueOf( 25 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _812, konst - 6 * height + _2, String.valueOf( 26 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _28, konst - 8 * height + _2, String.valueOf( 5 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _812, konst - 8 * height + _2, String.valueOf( 6 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _28, konst - _10 * height + _2, String.valueOf( 21 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _812, konst - _10 * height + _2, String.valueOf( 22 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _28, konst - 12 * height + _2, String.valueOf( 13 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _812, konst - 12 * height + _2, String.valueOf( 14 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _28, konst - 14 * height + _2, String.valueOf( 29 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _812, konst - 14 * height + _2, String.valueOf( 30 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _28, konst - _16 * height + _2, String.valueOf( 3 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _812, konst - _16 * height + _2, String.valueOf( 4 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _28, konst - 18 * height + _2, String.valueOf( 19 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _812, konst - 18 * height + _2, String.valueOf( _20 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _28, konst - _20 * height + _2, String.valueOf( 11 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _812, konst - _20 * height + _2, String.valueOf( 12 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _28, konst - 22 * height + _2, String.valueOf( 27 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _812, konst - 22 * height + _2, String.valueOf( _28 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _28, konst - 24 * height + _2, String.valueOf( 7 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _812, konst - 24 * height + _2, String.valueOf( 8 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _28, konst - 26 * height + _2, String.valueOf( 23 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _812, konst - 26 * height + _2, String.valueOf( 24 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _28, konst - _28 * height + _2, String.valueOf( 15 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _812, konst - _28 * height + _2, String.valueOf( _16 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _28, konst - 30 * height + _2, String.valueOf( 31 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _812, konst - 30 * height + _2, String.valueOf( 32 ), BOOLEAN_FALSE, _10, _0 );

        writeText( _128, konst - height + _2, String.valueOf( 1 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _712, konst - height + _2, String.valueOf( _2 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _128, konst - 5 * height + _2, String.valueOf( 3 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _712, konst - 5 * height + _2, String.valueOf( 4 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _128, konst - 9 * height + _2, String.valueOf( 5 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _712, konst - 9 * height + _2, String.valueOf( 6 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _128, konst - 13 * height + _2, String.valueOf( 7 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _712, konst - 13 * height + _2, String.valueOf( 8 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _128, konst - 17 * height + _2, String.valueOf( 9 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _712, konst - 17 * height + _2, String.valueOf( _10 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _128, konst - 21 * height + _2, String.valueOf( 11 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _712, konst - 21 * height + _2, String.valueOf( 12 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _128, konst - 25 * height + _2, String.valueOf( 13 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _712, konst - 25 * height + _2, String.valueOf( 14 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _128, konst - 29 * height + _2, String.valueOf( 15 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _712, konst - 29 * height + _2, String.valueOf( _16 ), BOOLEAN_FALSE, _10, _0 );

        writeText( _208, konst - 3 * height + _2, String.valueOf( 17 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _632, konst - 3 * height + _2, String.valueOf( 18 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _208, konst - 11 * height + _2, String.valueOf( 19 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _632, konst - 11 * height + _2, String.valueOf( _20 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _208, konst - 19 * height + _2, String.valueOf( 21 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _632, konst - 19 * height + _2, String.valueOf( 22 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _208, konst - 27 * height + _2, String.valueOf( 23 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _632, konst - 27 * height + _2, String.valueOf( 24 ), BOOLEAN_FALSE, _10, _0 );

        writeText( _288, konst - 7 * height + _2, String.valueOf( 25 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _552, konst - 7 * height + _2, String.valueOf( _26 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _288, konst - 23 * height + _2, String.valueOf( 27 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _552, konst - 23 * height + _2, String.valueOf( _28 ), BOOLEAN_FALSE, _10, _0 );

        writeText( _368, konst - 15 * height + _2, String.valueOf( 29 ), BOOLEAN_FALSE, _10, _0 );
        writeText( 472, konst - 15 * height + _2, String.valueOf( 30 ), BOOLEAN_FALSE, _10, _0 );

        writeText( 290, konst - 15 * height + _2, "A", true, _16, _0 );
        writeText( 550, konst - 15 * height + _2, "B", true, _16, _0 );
    }

    private void createLoserRoundStandardText( int konst )
        throws Exception
    {
        int height = 12;
        writeText( _108, konst - height + _2, String.valueOf( 31 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _108, konst - 5 * height + _2, String.valueOf( 32 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _108, konst - 9 * height + _2, String.valueOf( 33 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _108, konst - 13 * height + _2, String.valueOf( 34 ), BOOLEAN_FALSE, _10, _0 );

        writeText( _214, konst + _2, String.valueOf( 39 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _214, konst - 4 * height + _2, String.valueOf( 40 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _214, konst - 8 * height + _2, String.valueOf( 41 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _214, konst - 12 * height + _2, String.valueOf( 42 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _134, konst + height + _2, String.valueOf( 21 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _134, konst - 3 * height + _2, String.valueOf( 23 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _134, konst - 7 * height + _2, String.valueOf( 17 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _134, konst - 11 * height + _2, String.valueOf( 19 ), BOOLEAN_FALSE, _10, _0 );

        writeText( _294, konst - _2 * height + _2, String.valueOf( 47 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _294, konst - _10 * height + _2, String.valueOf( 48 ), BOOLEAN_FALSE, _10, _0 );

        writeText( _400, konst - height + _2, String.valueOf( 51 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _400, konst - 9 * height + _2, String.valueOf( 52 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _320, konst + _2, String.valueOf( 25 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _320, konst - 8 * height + _2, String.valueOf( 27 ), BOOLEAN_FALSE, _10, _0 );

        writeText( 480, konst - 5 * height + _2, String.valueOf( 55 ), BOOLEAN_FALSE, _10, _0 );

        writeText( 390, konst - 5 * height + _2, "A", true, _16, _0 );

        writeText( 586, konst - 4 * height + _2, String.valueOf( 57 ), BOOLEAN_FALSE, _10, _0 );
        writeText( 506, konst - 3 * height + _2, String.valueOf( 29 ), BOOLEAN_FALSE, _10, _0 );
        // 2. LoserRound
        konst -= _20 * height; // nur Veraendern, wenn konst-=48; in createLoserRoundTable() geaendert wird
        writeText( _108, konst - height + _2, String.valueOf( 35 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _108, konst - 5 * height + _2, String.valueOf( 36 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _108, konst - 9 * height + _2, String.valueOf( 37 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _108, konst - 13 * height + _2, String.valueOf( 38 ), BOOLEAN_FALSE, _10, _0 );

        writeText( _214, konst + _2, String.valueOf( 43 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _214, konst - 4 * height + _2, String.valueOf( 44 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _214, konst - 8 * height + _2, String.valueOf( 45 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _214, konst - 12 * height + _2, String.valueOf( 46 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _134, konst + height + _2, String.valueOf( 22 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _134, konst - 3 * height + _2, String.valueOf( 24 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _134, konst - 7 * height + _2, String.valueOf( 18 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _134, konst - 11 * height + _2, String.valueOf( _20 ), BOOLEAN_FALSE, _10, _0 );

        writeText( _294, konst - _2 * height + _2, String.valueOf( 49 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _294, konst - _10 * height + _2, String.valueOf( 50 ), BOOLEAN_FALSE, _10, _0 );

        writeText( _400, konst - height + _2, String.valueOf( 53 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _400, konst - 9 * height + _2, String.valueOf( 54 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _320, konst + _2, String.valueOf( 26 ), BOOLEAN_FALSE, _10, _0 );
        writeText( _320, konst - 8 * height + _2, String.valueOf( _28 ), BOOLEAN_FALSE, _10, _0 );

        writeText( 480, konst - 5 * height + _2, String.valueOf( 56 ), BOOLEAN_FALSE, _10, _0 );

        writeText( 382, konst - 5 * height + _2, "B", true, _16, _0 );

        writeText( 586, konst - 4 * height + _2, String.valueOf( 58 ), BOOLEAN_FALSE, _10, _0 );
        writeText( 506, konst - 3 * height + _2, String.valueOf( 30 ), BOOLEAN_FALSE, _10, _0 );
    }

    private int createMainRoundTable( int konst )
        throws Exception
    {
        cb.setColorStroke( new BaseColor( 0, 0, 0 ) );
        cb.setLineWidth( 1f );
        int height = 12; // konst

        for ( int i = _0; i < 31; i++ )
        {
            if ( i % _2 == _0 )
            {
                cb.rectangle( _20, konst - i * height, _16, height );
                cb.rectangle( _804, konst - i * height, _16, height );
                cb.rectangle( _36, konst - i * height, _130, height );
                cb.rectangle( _674, konst - i * height, _130, height );
                // writeText(28,konst-i*height+2,String.valueOf (i), BOOLEAN_FALSE,10,0);
            }
            if ( i % _4 == _1 )
            {
                cb.rectangle( _120, konst - i * height, _16, height );
                cb.rectangle( _704, konst - i * height, _16, height );
                cb.rectangle( _136, konst - i * height, _80, height );
                cb.rectangle( _624, konst - i * height, _80, height );
            }

            if ( i % _8 == _3 )
            {
                cb.rectangle( _200, konst - i * height - height, _16, 3 * height );
                cb.rectangle( _624, konst - i * height - height, _16, 3 * height );
                cb.rectangle( 216, konst - i * height, _80, height );
                cb.rectangle( 544, konst - i * height, _80, height );
            }
            if ( i % _16 == _7 )
            {
                cb.rectangle( 280, konst - i * height - 3 * height, _16, 7 * height );
                cb.rectangle( 544, konst - i * height - 3 * height, _16, 7 * height );
                cb.rectangle( 296, konst - i * height, _80, height );
                cb.rectangle( 464, konst - i * height, _80, height );
            }
            if ( i == _15 )
            {
                cb.rectangle( 360, konst - i * height - 7 * height, _16, 15 * height );
                cb.rectangle( 464, konst - i * height - 7 * height, _16, 15 * height );
                cb.rectangle( 376, konst - i * height + height, _70, height );
                cb.rectangle( 394, konst - i * height - height, _70, height );
            }
            cb.stroke();
        }
        return konst;
    }

    private int createLoserRoundTable( int konst )
        throws Exception
    {
        // int[] numberArray={13,13,14,14,15,15,16,16,9,9,10,10,11,11,12,12,5,5,6,6,7,7,8,8,1,1,2,2,3,3,4,4};
        int[] numberArray =
            { 1, 1, 3, 3, 5, 5, 7, 7, 9, 9, 11, 11, 13, 13, 15, 15, _2, _2, 4, 4, 6, 6, 8, 8, _10, _10, 12, 12, 14, 14,
                _16, _16 };
        cb.setColorStroke( new BaseColor( 0, 0, 0 ) );
        cb.setLineWidth( 1f );
        int height = 12; // konst

        for ( int i = _0; i < 32; i++ )
        {
            if ( i % _2 == _0 )
            {
                cb.rectangle( _20, konst - i * height, _16, height );
                cb.rectangle( _36, konst - i * height, _80, height );
                writeText( _28, konst - i * height + _2, String.valueOf( numberArray[i] ), BOOLEAN_FALSE, _10, _0 );
            }
            if ( i % _4 == _1 )
            {
                cb.rectangle( 100, konst - i * height, _16, height );
                cb.rectangle( 116, konst - i * height, 106, height );
                cb.rectangle( 126, konst - i * height + _2 * height, _16, height );
                cb.rectangle( 142, konst - i * height + _2 * height, _80, height );
            }
            if ( i % _4 == _0 )
            {
                cb.rectangle( 206, konst - i * height, _16, height );
                cb.rectangle( 222, konst - i * height, _80, height );
            }
            if ( i % _8 == _2 )
            {
                cb.rectangle( 286, konst - i * height - height, _16, 3 * height );
                cb.rectangle( 302, konst - i * height, 106, height );
                cb.rectangle( 328, konst - i * height + _2 * height, _80, height );
                cb.rectangle( 312, konst - i * height + _2 * height, _16, height );

                cb.rectangle( 392, konst - i * height + height, _16, height );
                cb.rectangle( 408, konst - i * height + height, _80, height );
            }
            if ( i % _16 == _5 )
            {
                cb.rectangle( 472, konst - i * height - 3 * height, _16, 7 * height );
                cb.rectangle( 488, konst - i * height, 106, height );
                cb.rectangle( 514, konst - i * height + _2 * height, _80, height );
                cb.rectangle( 498, konst - i * height + _2 * height, _16, height );

                cb.rectangle( 578, konst - i * height + height, _16, height );
                cb.rectangle( 594, konst - i * height + height, _80, height );
            }
            if ( i == _15 )
            {
                konst -= 48; // siehe createLoserRoundStandardText() bevor Aenderungen vollzogen werden
            }
            cb.stroke();
        }
        return konst;
    }

    private void fillMainRoundTable( NewaKoClass fc )
        throws Exception
    {
        int height = 12;
        int konst = constMainRound;
        writeTextFirstRound( _101, konst + _2, fc.getFightListMapPoolA().get( _7 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _101, konst - _2 * height + _2, fc.getFightListMapPoolA().get( _7 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( _101, konst - 4 * height + _2, fc.getFightListMapPoolA().get( _8 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _101, konst - 6 * height + _2, fc.getFightListMapPoolA().get( _8 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( _101, konst - 8 * height + _2, fc.getFightListMapPoolA().get( _9 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _101, konst - _10 * height + _2, fc.getFightListMapPoolA().get( _9 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( _101, konst - 12 * height + _2, fc.getFightListMapPoolA().get( _10 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _101, konst - 14 * height + _2, fc.getFightListMapPoolA().get( _10 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( _101, konst - _16 * height + _2, fc.getFightListMapPoolA().get( _11 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _101, konst - 18 * height + _2, fc.getFightListMapPoolA().get( _11 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( _101, konst - _20 * height + _2, fc.getFightListMapPoolA().get( _12 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _101, konst - 22 * height + _2, fc.getFightListMapPoolA().get( _12 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( _101, konst - 24 * height + _2, fc.getFightListMapPoolA().get( _13 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _101, konst - 26 * height + _2, fc.getFightListMapPoolA().get( _13 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( _101, konst - _28 * height + _2, fc.getFightListMapPoolA().get( _14 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _101, konst - 30 * height + _2, fc.getFightListMapPoolA().get( _14 ), _8, _0, FIGHTER_BLUE );

        writeTextFirstRound( _739, konst + _2, fc.getFightListMapPoolB().get( _7 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _739, konst - _2 * height + _2, fc.getFightListMapPoolB().get( _7 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( _739, konst - 4 * height + _2, fc.getFightListMapPoolB().get( _8 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _739, konst - 6 * height + _2, fc.getFightListMapPoolB().get( _8 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( _739, konst - 8 * height + _2, fc.getFightListMapPoolB().get( _9 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _739, konst - _10 * height + _2, fc.getFightListMapPoolB().get( _9 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( _739, konst - 12 * height + _2, fc.getFightListMapPoolB().get( _10 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _739, konst - 14 * height + _2, fc.getFightListMapPoolB().get( _10 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( _739, konst - _16 * height + _2, fc.getFightListMapPoolB().get( _11 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _739, konst - 18 * height + _2, fc.getFightListMapPoolB().get( _11 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( _739, konst - _20 * height + _2, fc.getFightListMapPoolB().get( _12 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _739, konst - 22 * height + _2, fc.getFightListMapPoolB().get( _12 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( _739, konst - 24 * height + _2, fc.getFightListMapPoolB().get( _13 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _739, konst - 26 * height + _2, fc.getFightListMapPoolB().get( _13 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( _739, konst - _28 * height + _2, fc.getFightListMapPoolB().get( _14 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _739, konst - 30 * height + _2, fc.getFightListMapPoolB().get( _14 ), _8, _0, FIGHTER_BLUE );
        // Round 4
        writeFighterName( _176, konst - height + _2, fc.getFightListMapPoolA().get( _3 ), _10, _0, FIGHTER_RED );
        writeFighterName( _176, konst - 5 * height + _2, fc.getFightListMapPoolA().get( _3 ), _10, _0, FIGHTER_BLUE );
        writeFighterName( _176, konst - 9 * height + _2, fc.getFightListMapPoolA().get( _4 ), _10, _0, FIGHTER_RED );
        writeFighterName( _176, konst - 13 * height + _2, fc.getFightListMapPoolA().get( _4 ), _10, _0, FIGHTER_BLUE );
        writeFighterName( _176, konst - 17 * height + _2, fc.getFightListMapPoolA().get( _5 ), _10, _0, FIGHTER_RED );
        writeFighterName( _176, konst - 21 * height + _2, fc.getFightListMapPoolA().get( _5 ), _10, _0, FIGHTER_BLUE );
        writeFighterName( _176, konst - 25 * height + _2, fc.getFightListMapPoolA().get( _6 ), _10, _0, FIGHTER_RED );
        writeFighterName( _176, konst - 29 * height + _2, fc.getFightListMapPoolA().get( _6 ), _10, _0, FIGHTER_BLUE );

        writeFighterName( _664, konst - height + _2, fc.getFightListMapPoolB().get( _3 ), _10, _0, FIGHTER_RED );
        writeFighterName( _664, konst - 5 * height + _2, fc.getFightListMapPoolB().get( _3 ), _10, _0, FIGHTER_BLUE );
        writeFighterName( _664, konst - 9 * height + _2, fc.getFightListMapPoolB().get( _4 ), _10, _0, FIGHTER_RED );
        writeFighterName( _664, konst - 13 * height + _2, fc.getFightListMapPoolB().get( _4 ), _10, _0, FIGHTER_BLUE );
        writeFighterName( _664, konst - 17 * height + _2, fc.getFightListMapPoolB().get( _5 ), _10, _0, FIGHTER_RED );
        writeFighterName( _664, konst - 21 * height + _2, fc.getFightListMapPoolB().get( _5 ), _10, _0, FIGHTER_BLUE );
        writeFighterName( _664, konst - 25 * height + _2, fc.getFightListMapPoolB().get( _6 ), _10, _0, FIGHTER_RED );
        writeFighterName( _664, konst - 29 * height + _2, fc.getFightListMapPoolB().get( _6 ), _10, _0, FIGHTER_BLUE );
        // Round3
        writeFighterName( _256, konst - 3 * height + _2, fc.getFightListMapPoolA().get( _1 ), _10, _0, FIGHTER_RED );
        writeFighterName( _256, konst - 11 * height + _2, fc.getFightListMapPoolA().get( _1 ), _10, _0, FIGHTER_BLUE );
        writeFighterName( _256, konst - 19 * height + _2, fc.getFightListMapPoolA().get( _2 ), _10, _0, FIGHTER_RED );
        writeFighterName( _256, konst - 27 * height + _2, fc.getFightListMapPoolA().get( _2 ), _10, _0, FIGHTER_BLUE );

        writeFighterName( _584, konst - 3 * height + _2, fc.getFightListMapPoolB().get( _1 ), _10, _0, FIGHTER_RED );
        writeFighterName( _584, konst - 11 * height + _2, fc.getFightListMapPoolB().get( _1 ), _10, _0, FIGHTER_BLUE );
        writeFighterName( _584, konst - 19 * height + _2, fc.getFightListMapPoolB().get( _2 ), _10, _0, FIGHTER_RED );
        writeFighterName( _584, konst - 27 * height + _2, fc.getFightListMapPoolB().get( _2 ), _10, _0, FIGHTER_BLUE );
        // Round2
        writeFighterName( _336, konst - 7 * height + _2, fc.getFightListMapPoolA().get( _0 ), _10, _0, FIGHTER_RED );
        writeFighterName( _336, konst - 23 * height + _2, fc.getFightListMapPoolA().get( _0 ), _10, _0, FIGHTER_BLUE );

        writeFighterName( _504, konst - 7 * height + _2, fc.getFightListMapPoolB().get( _0 ), _10, _0, FIGHTER_RED );
        writeFighterName( _504, konst - 23 * height + _2, fc.getFightListMapPoolB().get( _0 ), _10, _0, FIGHTER_BLUE );

        // winner
        if ( fc.getFinalFight().getWinnerId().longValue() == fc.getFinalFight().getFighterIdRed()
            && fc.getFinalFight().getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( 406, konst - _14 * height + _2, fc.getFinalFight().getFighterRed().getName(), BOOLEAN_TRUE, _10,
                       _0 );
        }
        else if ( fc.getFinalFight().getFighterRed() != null )
        {
            writeText( 406, konst - _14 * height + _2, fc.getFinalFight().getFighterRed().getName(), BOOLEAN_FALSE,
                       _10, _0 );
        }
        if ( fc.getFinalFight().getWinnerId().longValue() == fc.getFinalFight().getFighterIdBlue()
            && fc.getFinalFight().getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( 434, konst - _16 * height + _2, fc.getFinalFight().getFighterBlue().getName(), BOOLEAN_TRUE,
                       _10, _0 );
        }
        else if ( fc.getFinalFight().getFighterBlue() != null )
        {
            writeText( 434, konst - _16 * height + _2, fc.getFinalFight().getFighterBlue().getName(), BOOLEAN_FALSE,
                       _10, _0 );
        }
    }

    private void fillLoserRoundTable( NewaKoClass fc )
        throws Exception
    {
        int height = 12;
        int konst = constLoserRound;
        writeFighterName( _76, konst + _2, fc.getFightListLooserMapPoolA().get( _10 ), _10, _0, FIGHTER_RED );
        writeFighterName( _76, konst - _2 * height + _2, fc.getFightListLooserMapPoolA().get( _10 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _76, konst - 4 * height + _2, fc.getFightListLooserMapPoolA().get( 11 ), _10, _0, FIGHTER_RED );
        writeFighterName( _76, konst - 6 * height + _2, fc.getFightListLooserMapPoolA().get( 11 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _76, konst - 8 * height + _2, fc.getFightListLooserMapPoolA().get( 12 ), _10, _0, FIGHTER_RED );
        writeFighterName( _76, konst - _10 * height + _2, fc.getFightListLooserMapPoolA().get( 12 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _76, konst - 12 * height + _2, fc.getFightListLooserMapPoolA().get( 13 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _76, konst - 14 * height + _2, fc.getFightListLooserMapPoolA().get( 13 ), _10, _0,
                          FIGHTER_BLUE );
        //
        writeFighterName( _182, konst + height + _2, fc.getFightListLooserMapPoolA().get( 6 ), _10, _0, FIGHTER_RED );
        writeFighterName( _177, konst - height + _2, fc.getFightListLooserMapPoolA().get( 6 ), _10, _0, FIGHTER_BLUE );
        writeFighterName( _182, konst - 3 * height + _2, fc.getFightListLooserMapPoolA().get( 7 ), _10, _0, FIGHTER_RED );
        writeFighterName( _177, konst - 5 * height + _2, fc.getFightListLooserMapPoolA().get( 7 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _182, konst - 7 * height + _2, fc.getFightListLooserMapPoolA().get( 8 ), _10, _0, FIGHTER_RED );
        writeFighterName( _177, konst - 9 * height + _2, fc.getFightListLooserMapPoolA().get( 8 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _182, konst - 11 * height + _2, fc.getFightListLooserMapPoolA().get( 9 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _177, konst - 13 * height + _2, fc.getFightListLooserMapPoolA().get( 9 ), _10, _0,
                          FIGHTER_BLUE );
        //
        writeFighterName( _262, konst + _2, fc.getFightListLooserMapPoolA().get( 4 ), _10, _0, FIGHTER_RED );
        writeFighterName( _262, konst - 4 * height + _2, fc.getFightListLooserMapPoolA().get( 4 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _262, konst - 8 * height + _2, fc.getFightListLooserMapPoolA().get( 5 ), _10, _0, FIGHTER_RED );
        writeFighterName( _262, konst - 12 * height + _2, fc.getFightListLooserMapPoolA().get( 5 ), _10, _0,
                          FIGHTER_BLUE );
        //
        writeFighterName( _368, konst + _2, fc.getFightListLooserMapPoolA().get( _2 ), _10, _0, FIGHTER_RED );
        writeFighterName( _363, konst - _2 * height + _2, fc.getFightListLooserMapPoolA().get( _2 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _368, konst - 8 * height + _2, fc.getFightListLooserMapPoolA().get( 3 ), _10, _0, FIGHTER_RED );
        writeFighterName( _363, konst - _10 * height + _2, fc.getFightListLooserMapPoolA().get( 3 ), _10, _0,
                          FIGHTER_BLUE );
        //
        writeFighterName( _448, konst - height + _2, fc.getFightListLooserMapPoolA().get( 1 ), _10, _0, FIGHTER_RED );
        writeFighterName( _448, konst - 9 * height + _2, fc.getFightListLooserMapPoolA().get( 1 ), _10, _0,
                          FIGHTER_BLUE );

        writeFighterName( 554, konst - _3 * height + _2, fc.getFightListLooserMapPoolA().get( 0 ), _10, _0, FIGHTER_RED );
        writeFighterName( 549, konst - _5 * height + _2, fc.getFightListLooserMapPoolA().get( 0 ), _10, _0,
                          FIGHTER_BLUE );

        if ( fc.getFightListLooserMapPoolA().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolA().get(
                                                                                                                         _0 ).getFighterIdRed()
            && fc.getFightListLooserMapPoolA().get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( 634, konst - _4 * height + _2,
                       fc.getFightListLooserMapPoolA().get( _0 ).getFighterRed().getName(), BOOLEAN_TRUE, _10, _0 );
        }
        if ( fc.getFightListLooserMapPoolA().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolA().get(
                                                                                                                         _0 ).getFighterIdBlue()
            && fc.getFightListLooserMapPoolA().get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( 634, konst - _4 * height + _2,
                       fc.getFightListLooserMapPoolA().get( _0 ).getFighterBlue().getName(), BOOLEAN_TRUE, _10, _0 );
        }

        // 2. Loserrunde
        konst -= _20 * height; // nur Veraendern, wenn konst-=48; in createLoserRoundTable() geaendert wird

        writeFighterName( _76, konst + _2, fc.getFightListLooserMapPoolB().get( _10 ), _10, _0, FIGHTER_RED );
        writeFighterName( _76, konst - _2 * height + _2, fc.getFightListLooserMapPoolB().get( _10 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _76, konst - 4 * height + _2, fc.getFightListLooserMapPoolB().get( 11 ), _10, _0, FIGHTER_RED );
        writeFighterName( _76, konst - 6 * height + _2, fc.getFightListLooserMapPoolB().get( 11 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _76, konst - 8 * height + _2, fc.getFightListLooserMapPoolB().get( 12 ), _10, _0, FIGHTER_RED );
        writeFighterName( _76, konst - _10 * height + _2, fc.getFightListLooserMapPoolB().get( 12 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _76, konst - 12 * height + _2, fc.getFightListLooserMapPoolB().get( 13 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _76, konst - 14 * height + _2, fc.getFightListLooserMapPoolB().get( 13 ), _10, _0,
                          FIGHTER_BLUE );
        //
        writeFighterName( _182, konst + height + _2, fc.getFightListLooserMapPoolB().get( 6 ), _10, _0, FIGHTER_RED );
        writeFighterName( _177, konst - height + _2, fc.getFightListLooserMapPoolB().get( 6 ), _10, _0, FIGHTER_BLUE );
        writeFighterName( _182, konst - 3 * height + _2, fc.getFightListLooserMapPoolB().get( 7 ), _10, _0, FIGHTER_RED );
        writeFighterName( _177, konst - 5 * height + _2, fc.getFightListLooserMapPoolB().get( 7 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _182, konst - 7 * height + _2, fc.getFightListLooserMapPoolB().get( 8 ), _10, _0, FIGHTER_RED );
        writeFighterName( _177, konst - 9 * height + _2, fc.getFightListLooserMapPoolB().get( 8 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _182, konst - 11 * height + _2, fc.getFightListLooserMapPoolB().get( 9 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _177, konst - 13 * height + _2, fc.getFightListLooserMapPoolB().get( 9 ), _10, _0,
                          FIGHTER_BLUE );
        //
        writeFighterName( _262, konst + _2, fc.getFightListLooserMapPoolB().get( 4 ), _10, _0, FIGHTER_RED );
        writeFighterName( _262, konst - 4 * height + _2, fc.getFightListLooserMapPoolB().get( 4 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _262, konst - 8 * height + _2, fc.getFightListLooserMapPoolB().get( 5 ), _10, _0, FIGHTER_RED );
        writeFighterName( _262, konst - 12 * height + _2, fc.getFightListLooserMapPoolB().get( 5 ), _10, _0,
                          FIGHTER_BLUE );
        //
        writeFighterName( _368, konst + _2, fc.getFightListLooserMapPoolB().get( _2 ), _10, _0, FIGHTER_RED );
        writeFighterName( _363, konst - _2 * height + _2, fc.getFightListLooserMapPoolB().get( _2 ), _10, _0,
                          FIGHTER_BLUE );

        writeFighterName( _363, konst - 8 * height + _2, fc.getFightListLooserMapPoolB().get( 3 ), _10, _0, FIGHTER_RED );
        writeFighterName( _368, konst - _10 * height + _2, fc.getFightListLooserMapPoolB().get( 3 ), _10, _0,
                          FIGHTER_BLUE );
        //
        writeFighterName( _448, konst - height + _2, fc.getFightListLooserMapPoolB().get( 1 ), _10, _0, FIGHTER_RED );
        writeFighterName( _448, konst - 9 * height + _2, fc.getFightListLooserMapPoolB().get( 1 ), _10, _0,
                          FIGHTER_BLUE );

        writeFighterName( 554, konst - _3 * height + _2, fc.getFightListLooserMapPoolB().get( 0 ), _10, _0, FIGHTER_RED );
        writeFighterName( 549, konst - _5 * height + _2, fc.getFightListLooserMapPoolB().get( 0 ), _10, _0,
                          FIGHTER_BLUE );

        if ( fc.getFightListLooserMapPoolB().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolB().get(
                                                                                                                         _0 ).getFighterIdRed()
            && fc.getFightListLooserMapPoolB().get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( 634, konst - _4 * height + _2,
                       fc.getFightListLooserMapPoolB().get( _0 ).getFighterRed().getName(), BOOLEAN_TRUE, _10, _0 );
        }
        if ( fc.getFightListLooserMapPoolB().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolB().get(
                                                                                                                         _0 ).getFighterIdBlue()
            && fc.getFightListLooserMapPoolB().get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( 634, konst - _4 * height + _2,
                       fc.getFightListLooserMapPoolB().get( _0 ).getFighterBlue().getName(), BOOLEAN_TRUE, _10, _0 );
        }
    }

    /**
     * organisiert das anlegen der Kampflisten
     * 
     * @param writer
     * @param Ko
     */
    private void manageFightListForKo32( NewaKoClass fc, PdfWriter writer )
    {
        try
        {

            int count = _1;
            // 7 to 14
            for ( int i = _7; i <= _14; i++ )
            {
                if ( !( fc.getFightListMapPoolA().get( i ).getFighterIdBlue() == TypeUtil.LONG_MIN ) )
                {
                    createFightList( fc.getFightListMapPoolA().get( i ), count, writer );
                }
                count++;
                if ( !( fc.getFightListMapPoolB().get( i ).getFighterIdBlue() == TypeUtil.LONG_MIN ) )
                {
                    createFightList( fc.getFightListMapPoolB().get( i ), count, writer );
                }
                count++;
            }
            // 3 to 6
            for ( int i = _3; i <= _6; i++ )
            {
                if ( !( fc.getFightListMapPoolA().get( i ).getFighterIdBlue() == TypeUtil.LONG_MIN ) )
                {
                    createFightList( fc.getFightListMapPoolA().get( i ), count, writer );
                }
                count++;
                if ( !( fc.getFightListMapPoolB().get( i ).getFighterIdBlue() == TypeUtil.LONG_MIN ) )
                {
                    createFightList( fc.getFightListMapPoolB().get( i ), count, writer );
                }
                count++;
            }
            // 1 to 2
            for ( int i = _1; i <= _2; i++ )
            {
                if ( !( fc.getFightListMapPoolA().get( i ).getFighterIdBlue() == TypeUtil.LONG_MIN ) )
                {
                    createFightList( fc.getFightListMapPoolA().get( i ), count, writer );
                }
                count++;
                if ( !( fc.getFightListMapPoolB().get( i ).getFighterIdBlue() == TypeUtil.LONG_MIN ) )
                {
                    createFightList( fc.getFightListMapPoolB().get( i ), count, writer );
                }
                count++;
            }

            count = manageFightList( fc.getFightListMapPoolA(), _0, _0, count, BOOLEAN_FALSE, writer );
            count = manageFightList( fc.getFightListMapPoolB(), _0, _0, count, BOOLEAN_FALSE, writer );

            count = manageFightList( fc.getFightListLooserMapPoolA(), _10, _13, count, BOOLEAN_TRUE, writer );
            count = manageFightList( fc.getFightListLooserMapPoolB(), _10, _13, count, BOOLEAN_TRUE, writer );

            count = manageFightList( fc.getFightListLooserMapPoolA(), _6, _9, count, BOOLEAN_FALSE, writer );
            count = manageFightList( fc.getFightListLooserMapPoolB(), _6, _9, count, BOOLEAN_FALSE, writer );

            count = manageFightList( fc.getFightListLooserMapPoolA(), _4, _5, count, BOOLEAN_FALSE, writer );
            count = manageFightList( fc.getFightListLooserMapPoolB(), _4, _5, count, BOOLEAN_FALSE, writer );
            count = manageFightList( fc.getFightListLooserMapPoolA(), _2, _3, count, BOOLEAN_FALSE, writer );
            count = manageFightList( fc.getFightListLooserMapPoolB(), _2, _3, count, BOOLEAN_FALSE, writer );

            count = manageFightList( fc.getFightListLooserMapPoolA(), _1, _1, count, BOOLEAN_FALSE, writer );
            count = manageFightList( fc.getFightListLooserMapPoolB(), _1, _1, count, BOOLEAN_FALSE, writer );
            count = manageFightList( fc.getFightListLooserMapPoolA(), _0, _0, count, BOOLEAN_FALSE, writer );
            count = manageFightList( fc.getFightListLooserMapPoolB(), _0, _0, count, BOOLEAN_FALSE, writer );

            createFightList( fc.getFinalFight(), count, writer );
        }
        catch ( Exception e )
        {
            log.error( "NewaKo32ComplexListPDF.manageFightListForKo32: Exception: \ncan not create pdf document \n",
                       e );
        }
    }

}
