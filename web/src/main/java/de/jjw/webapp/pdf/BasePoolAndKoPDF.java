package de.jjw.webapp.pdf;

import java.util.Map;

/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : BasePoolAndKoPDF.java
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

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;

import de.jjw.model.duo.DuoFight;
import de.jjw.model.duo.DuoKoClass;
import de.jjw.model.fighting.Fight;
import de.jjw.model.fighting.FightingKoClass;
import de.jjw.model.newa.NewaFight;
import de.jjw.model.newa.NewaKoClass;
import de.jjw.util.TypeUtil;

public class BasePoolAndKoPDF
    extends BasePoolPDF

{
    protected static String[] loserRoundAArrayKO64 = { "1", "", "3", "", "5", "", "7", "", "9", "", "11", "", "13", "", "15",
        "", "17", "", "19", "", "21", "", "23", "", "25", "", "27", "", "29", "", "31", "" };

    protected static String[] loserRoundBArrayKO64 = { "2", "", "4", "", "6", "", "8", "", "10", "", "12", "", "14", "",
        "16", "", "18", "", "20", "", "22", "", "24", "", "26", "", "28", "", "30", "", "32", "" };

    protected int createMainRoundTableKO16( int konst )
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

    protected int createLoserRoundTableKO16( int konst )
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

    protected void createMainRoundStandardTextKO16( int konst )
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

    protected void createLoserRoundStandardTextKO16( int konst )
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

    protected void fillMainRoundTableKO16Fighting( int constMainRound, FightingKoClass fc )
        throws Exception
    {
        int height = _12;
        int konst = constMainRound;
        writeTextFirstRound( _116, konst + _2, fc.getFightListMapPoolA().get( _3 ), _8, _0, FIGHTER_RED );
        writeText( _208, konst + _2, String.valueOf( fc.getFightListMapPoolA().get( _3 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _2 * height + _2, fc.getFightListMapPoolA().get( _3 ), _8, _0,
                             FIGHTER_BLUE );
        writeText( _208, konst - _2 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _3 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _4 * height + _2, fc.getFightListMapPoolA().get( _4 ), _8, _0, FIGHTER_RED );
        writeText( _208, konst - _4 * height + _2, String.valueOf( fc.getFightListMapPoolA().get( _4 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _6 * height + _2, fc.getFightListMapPoolA().get( _4 ), _8, _0,
                             FIGHTER_BLUE );
        writeText( _208, konst - _6 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _4 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _8 * height + _2, fc.getFightListMapPoolA().get( _5 ), _8, _0, FIGHTER_RED );
        writeText( _208, konst - _8 * height + _2, String.valueOf( fc.getFightListMapPoolA().get( _5 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _10 * height + _2, fc.getFightListMapPoolA().get( _5 ), _8, _0,
                             FIGHTER_BLUE );
        writeText( _208, konst - _10 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _5 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _12 * height + _2, fc.getFightListMapPoolA().get( _6 ), _8, _0,
                             FIGHTER_RED );
        writeText( _208, konst - _12 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _6 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _14 * height + _2, fc.getFightListMapPoolA().get( _6 ), _8, _0,
                             FIGHTER_BLUE );
        writeText( _208, konst - _14 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _6 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _16 * height + _2, fc.getFightListMapPoolB().get( _3 ), _8, _0,
                             FIGHTER_RED );
        writeText( _208, konst - _16 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _3 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _18 * height + _2, fc.getFightListMapPoolB().get( _3 ), _8, _0,
                             FIGHTER_BLUE );
        writeText( _208, konst - _18 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _3 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _20 * height + _2, fc.getFightListMapPoolB().get( _4 ), _8, _0,
                             FIGHTER_RED );
        writeText( _208, konst - _20 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _4 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _22 * height + _2, fc.getFightListMapPoolB().get( _4 ), _8, _0,
                             FIGHTER_BLUE );
        writeText( _208, konst - _22 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _4 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _24 * height + _2, fc.getFightListMapPoolB().get( _5 ), _8, _0,
                             FIGHTER_RED );
        writeText( _208, konst - _24 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _5 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _26 * height + _2, fc.getFightListMapPoolB().get( _5 ), _8, _0,
                             FIGHTER_BLUE );
        writeText( _208, konst - _26 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _5 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _28 * height + _2, fc.getFightListMapPoolB().get( _6 ), _8, _0,
                             FIGHTER_RED );
        writeText( _208, konst - _28 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _6 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - 30 * height + _2, fc.getFightListMapPoolB().get( _6 ), _8, _0,
                             FIGHTER_BLUE );
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
        writeText( _348, konst - _9 * height + _2, String.valueOf( fc.getFightListMapPoolA().get( _2 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _286, konst - _13 * height + _2, fc.getFightListMapPoolA().get( _2 ), _10, _0, FIGHTER_BLUE );
        writeText( _348, konst - _13 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _2 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _286, konst - 17 * height + _2, fc.getFightListMapPoolB().get( _1 ), _10, _0, FIGHTER_RED );
        writeText( _348, konst - 17 * height + _2, String.valueOf( fc.getFightListMapPoolB().get( _1 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
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
        writeText( _488, konst - _3 * height + _2, String.valueOf( fc.getFightListMapPoolA().get( _0 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _426, konst - _11 * height + _2, fc.getFightListMapPoolA().get( _0 ), _10, _0, FIGHTER_BLUE );
        writeText( _488, konst - _11 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _0 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _426, konst - 19 * height + _2, fc.getFightListMapPoolB().get( _0 ), _10, _0, FIGHTER_RED );
        writeText( _488, konst - 19 * height + _2, String.valueOf( fc.getFightListMapPoolB().get( _0 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
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
            writeText( _706, konst - _15 * height + _2, fc.getFinalFight().getFighterRed().getName(), BOOLEAN_TRUE, _10,
                       _0 );
        }
        if ( fc.getFinalFight().getWinnerId().longValue() == fc.getFinalFight().getFighterIdBlue()
            && fc.getFinalFight().getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( _706, konst - _15 * height + _2, fc.getFinalFight().getFighterBlue().getName(), BOOLEAN_TRUE,
                       _10, _0 );
        }
    }

    protected void fillMainRoundTableKO16Newa( int constMainRound, NewaKoClass fc )
        throws Exception
    {
        int height = _12;
        int konst = constMainRound;
        writeTextFirstRound( _116, konst + _2, fc.getFightListMapPoolA().get( _3 ), _8, _0, FIGHTER_RED );
        writeText( _208, konst + _2, String.valueOf( fc.getFightListMapPoolA().get( _3 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _2 * height + _2, fc.getFightListMapPoolA().get( _3 ), _8, _0,
                             FIGHTER_BLUE );
        writeText( _208, konst - _2 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _3 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _4 * height + _2, fc.getFightListMapPoolA().get( _4 ), _8, _0, FIGHTER_RED );
        writeText( _208, konst - _4 * height + _2, String.valueOf( fc.getFightListMapPoolA().get( _4 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _6 * height + _2, fc.getFightListMapPoolA().get( _4 ), _8, _0,
                             FIGHTER_BLUE );
        writeText( _208, konst - _6 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _4 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _8 * height + _2, fc.getFightListMapPoolA().get( _5 ), _8, _0, FIGHTER_RED );
        writeText( _208, konst - _8 * height + _2, String.valueOf( fc.getFightListMapPoolA().get( _5 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _10 * height + _2, fc.getFightListMapPoolA().get( _5 ), _8, _0,
                             FIGHTER_BLUE );
        writeText( _208, konst - _10 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _5 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _12 * height + _2, fc.getFightListMapPoolA().get( _6 ), _8, _0,
                             FIGHTER_RED );
        writeText( _208, konst - _12 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _6 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _14 * height + _2, fc.getFightListMapPoolA().get( _6 ), _8, _0,
                             FIGHTER_BLUE );
        writeText( _208, konst - _14 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _6 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _16 * height + _2, fc.getFightListMapPoolB().get( _3 ), _8, _0,
                             FIGHTER_RED );
        writeText( _208, konst - _16 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _3 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _18 * height + _2, fc.getFightListMapPoolB().get( _3 ), _8, _0,
                             FIGHTER_BLUE );
        writeText( _208, konst - _18 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _3 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _20 * height + _2, fc.getFightListMapPoolB().get( _4 ), _8, _0,
                             FIGHTER_RED );
        writeText( _208, konst - _20 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _4 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _22 * height + _2, fc.getFightListMapPoolB().get( _4 ), _8, _0,
                             FIGHTER_BLUE );
        writeText( _208, konst - _22 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _4 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _24 * height + _2, fc.getFightListMapPoolB().get( _5 ), _8, _0,
                             FIGHTER_RED );
        writeText( _208, konst - _24 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _5 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _26 * height + _2, fc.getFightListMapPoolB().get( _5 ), _8, _0,
                             FIGHTER_BLUE );
        writeText( _208, konst - _26 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _5 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _28 * height + _2, fc.getFightListMapPoolB().get( _6 ), _8, _0,
                             FIGHTER_RED );
        writeText( _208, konst - _28 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _6 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - 30 * height + _2, fc.getFightListMapPoolB().get( _6 ), _8, _0,
                             FIGHTER_BLUE );
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
        writeText( _348, konst - _9 * height + _2, String.valueOf( fc.getFightListMapPoolA().get( _2 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _286, konst - _13 * height + _2, fc.getFightListMapPoolA().get( _2 ), _10, _0, FIGHTER_BLUE );
        writeText( _348, konst - _13 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _2 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _286, konst - 17 * height + _2, fc.getFightListMapPoolB().get( _1 ), _10, _0, FIGHTER_RED );
        writeText( _348, konst - 17 * height + _2, String.valueOf( fc.getFightListMapPoolB().get( _1 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
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
        writeText( _488, konst - _3 * height + _2, String.valueOf( fc.getFightListMapPoolA().get( _0 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _426, konst - _11 * height + _2, fc.getFightListMapPoolA().get( _0 ), _10, _0, FIGHTER_BLUE );
        writeText( _488, konst - _11 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _0 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _426, konst - 19 * height + _2, fc.getFightListMapPoolB().get( _0 ), _10, _0, FIGHTER_RED );
        writeText( _488, konst - 19 * height + _2, String.valueOf( fc.getFightListMapPoolB().get( _0 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
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
            writeText( _706, konst - _15 * height + _2, fc.getFinalFight().getFighterRed().getName(), BOOLEAN_TRUE, _10,
                       _0 );
        }
        if ( fc.getFinalFight().getWinnerId().longValue() == fc.getFinalFight().getFighterIdBlue()
            && fc.getFinalFight().getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( _706, konst - _15 * height + _2, fc.getFinalFight().getFighterBlue().getName(), BOOLEAN_TRUE,
                       _10, _0 );
        }
    }

    protected void fillLoserRoundTableKO16Fighting( int constLoserRound, FightingKoClass fc )
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
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _4 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );
        writeFighterName( 146, konst - _4 * height + _2, fc.getFightListLooserMapPoolA().get( _5 ), _10, _0,
                          FIGHTER_RED );
        writeText( _208, konst - _4 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _5 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( 146, konst - _6 * height + _2, fc.getFightListLooserMapPoolA().get( _5 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _208, konst - _6 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _5 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );
        writeFighterName( 146, konst - _8 * height + _2, fc.getFightListLooserMapPoolB().get( _4 ), _10, _0,
                          FIGHTER_RED );
        writeText( _208, konst - _8 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _4 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( 146, konst - _10 * height + _2, fc.getFightListLooserMapPoolB().get( _4 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _208, konst - _10 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _4 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );
        writeFighterName( 146, konst - _12 * height + _2, fc.getFightListLooserMapPoolB().get( _5 ), _10, _0,
                          FIGHTER_RED );
        writeText( _208, konst - _12 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _5 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( 146, konst - _14 * height + _2, fc.getFightListLooserMapPoolB().get( _5 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _208, konst - _14 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _5 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );
        // Round3
        writeFighterName( _307, konst + height + _2, fc.getFightListLooserMapPoolA().get( _2 ), _10, _0, FIGHTER_RED );
        writeText( _364, konst + height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _2 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _302, konst - height + _2, fc.getFightListLooserMapPoolA().get( _2 ), _10, _0, FIGHTER_BLUE );
        writeText( _364, konst - height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _2 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );
        writeFighterName( _307, konst - _3 * height + _2, fc.getFightListLooserMapPoolA().get( _3 ), _10, _0,
                          FIGHTER_RED );
        writeText( _364, konst - _3 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _3 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _302, konst - _5 * height + _2, fc.getFightListLooserMapPoolA().get( _3 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _364, konst - _5 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _3 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );

        writeFighterName( _307, konst - _7 * height + _2, fc.getFightListLooserMapPoolB().get( _2 ), _10, _0,
                          FIGHTER_RED );
        writeText( _364, konst - _7 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _2 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _302, konst - _9 * height + _2, fc.getFightListLooserMapPoolB().get( _2 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _364, konst - _9 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _2 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );
        writeFighterName( _307, konst - _11 * height + _2, fc.getFightListLooserMapPoolB().get( _3 ), _10, _0,
                          FIGHTER_RED );
        writeText( _364, konst - _11 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _3 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _302, konst - _13 * height + _2, fc.getFightListLooserMapPoolB().get( _3 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _364, konst - _13 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _3 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );
        // Round2
        writeFighterName( _442, konst + _2, fc.getFightListLooserMapPoolA().get( _1 ), _10, _0, FIGHTER_RED );
        writeText( _504, konst + _2, String.valueOf( fc.getFightListLooserMapPoolA().get( _1 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _442, konst - _4 * height + _2, fc.getFightListLooserMapPoolA().get( _1 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _504, konst - _4 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _1 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );

        writeFighterName( _442, konst - _8 * height + _2, fc.getFightListLooserMapPoolB().get( _1 ), _10, _0,
                          FIGHTER_RED );
        writeText( _504, konst - _8 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _1 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _442, konst - _12 * height + _2, fc.getFightListLooserMapPoolB().get( _1 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _504, konst - _12 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _1 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );
        // Round 1
        writeFighterName( _603, konst + _2, fc.getFightListLooserMapPoolA().get( _0 ), _10, _0, FIGHTER_RED );
        writeText( _660, konst + _2, String.valueOf( fc.getFightListLooserMapPoolA().get( _0 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
        writeFighterName( 598, konst - _2 * height + _2, fc.getFightListLooserMapPoolA().get( _0 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _660, konst - _2 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _0 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );

        writeFighterName( _603, konst - _8 * height + _2, fc.getFightListLooserMapPoolB().get( _0 ), _10, _0,
                          FIGHTER_RED );
        writeText( _660, konst - _8 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _0 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( 598, konst - _10 * height + _2, fc.getFightListLooserMapPoolB().get( _0 ), _10, _0,
                          FIGHTER_RED );
        writeText( _660, konst - _10 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _0 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );
        // 3rd

        if ( fc.getFightListLooserMapPoolA().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolA().get( _0 ).getFighterIdRed()
            && fc.getFightListLooserMapPoolA().get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( _738, konst - height + _2, fc.getFightListLooserMapPoolA().get( _0 ).getFighterRed().getName(),
                       BOOLEAN_TRUE, _10, _0 );
        }
        if ( fc.getFightListLooserMapPoolA().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolA().get( _0 ).getFighterIdBlue()
            && fc.getFightListLooserMapPoolA().get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( _738, konst - height + _2, fc.getFightListLooserMapPoolA().get( _0 ).getFighterBlue().getName(),
                       BOOLEAN_TRUE, _10, _0 );
        }

        if ( fc.getFightListLooserMapPoolB().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolB().get( _0 ).getFighterIdRed()
            && fc.getFightListLooserMapPoolB().get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( _738, konst - _9 * height + _2,
                       fc.getFightListLooserMapPoolB().get( _0 ).getFighterRed().getName(), BOOLEAN_TRUE, _10, _0 );
        }
        if ( fc.getFightListLooserMapPoolB().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolB().get( _0 ).getFighterIdBlue()
            && fc.getFightListLooserMapPoolB().get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( _738, konst - _9 * height + _2,
                       fc.getFightListLooserMapPoolB().get( _0 ).getFighterBlue().getName(), BOOLEAN_TRUE, _10, _0 );
        }
    }

    protected void fillLoserRoundTableKO16Newa( int constLoserRound, NewaKoClass fc )
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
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _4 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );
        writeFighterName( 146, konst - _4 * height + _2, fc.getFightListLooserMapPoolA().get( _5 ), _10, _0,
                          FIGHTER_RED );
        writeText( _208, konst - _4 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _5 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( 146, konst - _6 * height + _2, fc.getFightListLooserMapPoolA().get( _5 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _208, konst - _6 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _5 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );
        writeFighterName( 146, konst - _8 * height + _2, fc.getFightListLooserMapPoolB().get( _4 ), _10, _0,
                          FIGHTER_RED );
        writeText( _208, konst - _8 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _4 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( 146, konst - _10 * height + _2, fc.getFightListLooserMapPoolB().get( _4 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _208, konst - _10 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _4 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );
        writeFighterName( 146, konst - _12 * height + _2, fc.getFightListLooserMapPoolB().get( _5 ), _10, _0,
                          FIGHTER_RED );
        writeText( _208, konst - _12 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _5 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( 146, konst - _14 * height + _2, fc.getFightListLooserMapPoolB().get( _5 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _208, konst - _14 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _5 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );
        // Round3
        writeFighterName( _307, konst + height + _2, fc.getFightListLooserMapPoolA().get( _2 ), _10, _0, FIGHTER_RED );
        writeText( _364, konst + height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _2 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _302, konst - height + _2, fc.getFightListLooserMapPoolA().get( _2 ), _10, _0, FIGHTER_BLUE );
        writeText( _364, konst - height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _2 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );
        writeFighterName( _307, konst - _3 * height + _2, fc.getFightListLooserMapPoolA().get( _3 ), _10, _0,
                          FIGHTER_RED );
        writeText( _364, konst - _3 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _3 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _302, konst - _5 * height + _2, fc.getFightListLooserMapPoolA().get( _3 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _364, konst - _5 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _3 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );

        writeFighterName( _307, konst - _7 * height + _2, fc.getFightListLooserMapPoolB().get( _2 ), _10, _0,
                          FIGHTER_RED );
        writeText( _364, konst - _7 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _2 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _302, konst - _9 * height + _2, fc.getFightListLooserMapPoolB().get( _2 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _364, konst - _9 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _2 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );
        writeFighterName( _307, konst - _11 * height + _2, fc.getFightListLooserMapPoolB().get( _3 ), _10, _0,
                          FIGHTER_RED );
        writeText( _364, konst - _11 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _3 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _302, konst - _13 * height + _2, fc.getFightListLooserMapPoolB().get( _3 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _364, konst - _13 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _3 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );
        // Round2
        writeFighterName( _442, konst + _2, fc.getFightListLooserMapPoolA().get( _1 ), _10, _0, FIGHTER_RED );
        writeText( _504, konst + _2, String.valueOf( fc.getFightListLooserMapPoolA().get( _1 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _442, konst - _4 * height + _2, fc.getFightListLooserMapPoolA().get( _1 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _504, konst - _4 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _1 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );

        writeFighterName( _442, konst - _8 * height + _2, fc.getFightListLooserMapPoolB().get( _1 ), _10, _0,
                          FIGHTER_RED );
        writeText( _504, konst - _8 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _1 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _442, konst - _12 * height + _2, fc.getFightListLooserMapPoolB().get( _1 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _504, konst - _12 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _1 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );
        // Round 1
        writeFighterName( _603, konst + _2, fc.getFightListLooserMapPoolA().get( _0 ), _10, _0, FIGHTER_RED );
        writeText( _660, konst + _2, String.valueOf( fc.getFightListLooserMapPoolA().get( _0 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
        writeFighterName( 598, konst - _2 * height + _2, fc.getFightListLooserMapPoolA().get( _0 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _660, konst - _2 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _0 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );

        writeFighterName( _603, konst - _8 * height + _2, fc.getFightListLooserMapPoolB().get( _0 ), _10, _0,
                          FIGHTER_RED );
        writeText( _660, konst - _8 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _0 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( 598, konst - _10 * height + _2, fc.getFightListLooserMapPoolB().get( _0 ), _10, _0,
                          FIGHTER_RED );
        writeText( _660, konst - _10 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _0 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );
        // 3rd

        if ( fc.getFightListLooserMapPoolA().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolA().get( _0 ).getFighterIdRed()
            && fc.getFightListLooserMapPoolA().get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( _738, konst - height + _2, fc.getFightListLooserMapPoolA().get( _0 ).getFighterRed().getName(),
                       BOOLEAN_TRUE, _10, _0 );
        }
        if ( fc.getFightListLooserMapPoolA().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolA().get( _0 ).getFighterIdBlue()
            && fc.getFightListLooserMapPoolA().get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( _738, konst - height + _2, fc.getFightListLooserMapPoolA().get( _0 ).getFighterBlue().getName(),
                       BOOLEAN_TRUE, _10, _0 );
        }

        if ( fc.getFightListLooserMapPoolB().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolB().get( _0 ).getFighterIdRed()
            && fc.getFightListLooserMapPoolB().get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( _738, konst - _9 * height + _2,
                       fc.getFightListLooserMapPoolB().get( _0 ).getFighterRed().getName(), BOOLEAN_TRUE, _10, _0 );
        }
        if ( fc.getFightListLooserMapPoolB().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolB().get( _0 ).getFighterIdBlue()
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
    protected void writeTextFirstRound( int x, int y, Fight fight, int size, int loc, int redOrBlue )
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
            System.err.println( "FightingFightList.writeText: Exception: \n can not create pdf document \n"
                + e.getMessage() );
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
            System.err.println( "FightingFightList.writeText: Exception: \n can not create pdf document \n"
                + e.getMessage() );
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
    protected void writeTextFirstRound( int x, int y, DuoFight fight, int size, int loc, int redOrBlue )
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

            if ( redOrBlue == FIGHTER_RED && fight.getDuoTeamRed() != null )
            {
                if ( fight.getDuoTeamRed().getName() != null )
                {
                    sb.append( fight.getDuoTeamRed().getName() );
                    sb.append( SPACE );
                }
                if ( fight.getDuoTeamRed().getFirstname() != null )
                {
                    sb.append( fight.getDuoTeamRed().getFirstname() );
                    sb.append( SPACE );
                }
                if ( fight.getDuoTeamRed().getTeam() != null )
                {
                    sb.append( fight.getDuoTeamRed().getTeam().getTeamName() );
                }

            }
            if ( redOrBlue == FIGHTER_BLUE && fight.getDuoTeamBlue() != null )
            {
                if ( fight.getDuoTeamBlue().getName() != null )
                {
                    sb.append( fight.getDuoTeamBlue().getName() );
                    sb.append( SPACE );
                }
                if ( fight.getDuoTeamBlue().getFirstname() != null )
                {
                    sb.append( fight.getDuoTeamBlue().getFirstname() );
                    sb.append( SPACE );
                }
                if ( fight.getDuoTeamBlue().getTeam() != null )
                {
                    sb.append( fight.getDuoTeamBlue().getTeam().getTeamName() );
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
            System.err.println( "FightingFightList.writeText: Exception: \n can not create pdf document \n"
                + e.getMessage() );
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
    protected void writeFighterName( int x, int y, Fight fight, int size, int loc, int redOrBlue )
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
            // log.debug( "cb.beginText" );
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
            // log.debug( "cb.endText" );
        }
        catch ( Exception e )
        {
            System.err.println( "BasePoolAndKoPDF.writeText: Exception: \n can not create pdf document \n"
                + e.getMessage() );
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
            // log.debug( "cb.beginText" );
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
            // log.debug( "cb.endText" );
        }
        catch ( Exception e )
        {
            System.err.println( "BasePoolAndKoPDF.writeText: Exception: \n can not create pdf document \n"
                + e.getMessage() );
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
    protected void writeFighterName( int x, int y, DuoFight fight, int size, int loc, int redOrBlue )
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

            if ( redOrBlue == FIGHTER_RED && fight.getDuoTeamRed() != null )
            {
                if ( fight.getDuoTeamRed().getName() != null )
                {
                    sb.append( fight.getDuoTeamRed().getName() );
                }
                if ( fight.getWinnerId().longValue() == fight.getTeamIdRed() )
                {
                    bf = BaseFont.createFont( BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED );
                }

            }
            if ( redOrBlue == FIGHTER_BLUE && fight.getDuoTeamBlue() != null )
            {
                if ( fight.getDuoTeamBlue().getName() != null )
                {
                    sb.append( fight.getDuoTeamBlue().getName() );
                }
                if ( fight.getWinnerId().longValue() == fight.getTeamIdBlue() )
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
            System.err.println( "FightingFightList.writeText: Exception: \n can not create pdf document \n"
                + e.getMessage() );
        }
        return;
    }

    protected int createMainRoundTableKO32( int konst )
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

    protected int createLoserRoundTableKO32( int konst )
        throws Exception
    {
        // int[] numberArray={13,13,14,14,15,15,16,16,9,9,10,10,11,11,12,12,5,5,6,6,7,7,8,8,1,1,2,2,3,3,4,4};
        int[] numberArray = { 1, 1, 3, 3, 5, 5, 7, 7, 9, 9, 11, 11, 13, 13, 15, 15, _2, _2, 4, 4, 6, 6, 8, 8, _10, _10,
            12, 12, 14, 14, _16, _16 };
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

    protected void createMainRoundStandardTextKO32( int konst )
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

    protected void createLoserRoundStandardTextKO32( int konst )
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

    protected void fillMainRoundTableKO32Fighting( int constMainRound, FightingKoClass fc )
        throws Exception
    {
        int height = 12;
        int konst = constMainRound;
        writeTextFirstRound( _101, konst + _2, fc.getFightListMapPoolA().get( _7 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _101, konst - _2 * height + _2, fc.getFightListMapPoolA().get( _7 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _101, konst - 4 * height + _2, fc.getFightListMapPoolA().get( _8 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _101, konst - 6 * height + _2, fc.getFightListMapPoolA().get( _8 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( _101, konst - 8 * height + _2, fc.getFightListMapPoolA().get( _9 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _101, konst - _10 * height + _2, fc.getFightListMapPoolA().get( _9 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _101, konst - 12 * height + _2, fc.getFightListMapPoolA().get( _10 ), _8, _0,
                             FIGHTER_RED );
        writeTextFirstRound( _101, konst - 14 * height + _2, fc.getFightListMapPoolA().get( _10 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _101, konst - _16 * height + _2, fc.getFightListMapPoolA().get( _11 ), _8, _0,
                             FIGHTER_RED );
        writeTextFirstRound( _101, konst - 18 * height + _2, fc.getFightListMapPoolA().get( _11 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _101, konst - _20 * height + _2, fc.getFightListMapPoolA().get( _12 ), _8, _0,
                             FIGHTER_RED );
        writeTextFirstRound( _101, konst - 22 * height + _2, fc.getFightListMapPoolA().get( _12 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _101, konst - 24 * height + _2, fc.getFightListMapPoolA().get( _13 ), _8, _0,
                             FIGHTER_RED );
        writeTextFirstRound( _101, konst - 26 * height + _2, fc.getFightListMapPoolA().get( _13 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _101, konst - _28 * height + _2, fc.getFightListMapPoolA().get( _14 ), _8, _0,
                             FIGHTER_RED );
        writeTextFirstRound( _101, konst - 30 * height + _2, fc.getFightListMapPoolA().get( _14 ), _8, _0,
                             FIGHTER_BLUE );

        writeTextFirstRound( _739, konst + _2, fc.getFightListMapPoolB().get( _7 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _739, konst - _2 * height + _2, fc.getFightListMapPoolB().get( _7 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _739, konst - 4 * height + _2, fc.getFightListMapPoolB().get( _8 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _739, konst - 6 * height + _2, fc.getFightListMapPoolB().get( _8 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( _739, konst - 8 * height + _2, fc.getFightListMapPoolB().get( _9 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _739, konst - _10 * height + _2, fc.getFightListMapPoolB().get( _9 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _739, konst - 12 * height + _2, fc.getFightListMapPoolB().get( _10 ), _8, _0,
                             FIGHTER_RED );
        writeTextFirstRound( _739, konst - 14 * height + _2, fc.getFightListMapPoolB().get( _10 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _739, konst - _16 * height + _2, fc.getFightListMapPoolB().get( _11 ), _8, _0,
                             FIGHTER_RED );
        writeTextFirstRound( _739, konst - 18 * height + _2, fc.getFightListMapPoolB().get( _11 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _739, konst - _20 * height + _2, fc.getFightListMapPoolB().get( _12 ), _8, _0,
                             FIGHTER_RED );
        writeTextFirstRound( _739, konst - 22 * height + _2, fc.getFightListMapPoolB().get( _12 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _739, konst - 24 * height + _2, fc.getFightListMapPoolB().get( _13 ), _8, _0,
                             FIGHTER_RED );
        writeTextFirstRound( _739, konst - 26 * height + _2, fc.getFightListMapPoolB().get( _13 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _739, konst - _28 * height + _2, fc.getFightListMapPoolB().get( _14 ), _8, _0,
                             FIGHTER_RED );
        writeTextFirstRound( _739, konst - 30 * height + _2, fc.getFightListMapPoolB().get( _14 ), _8, _0,
                             FIGHTER_BLUE );
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
            writeText( 406, konst - _14 * height + _2, fc.getFinalFight().getFighterRed().getName(), BOOLEAN_FALSE, _10,
                       _0 );
        }
        if ( fc.getFinalFight().getWinnerId().longValue() == fc.getFinalFight().getFighterIdBlue()
            && fc.getFinalFight().getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( 434, konst - _16 * height + _2, fc.getFinalFight().getFighterBlue().getName(), BOOLEAN_TRUE, _10,
                       _0 );
        }
        else if ( fc.getFinalFight().getFighterBlue() != null )
        {
            writeText( 434, konst - _16 * height + _2, fc.getFinalFight().getFighterBlue().getName(), BOOLEAN_FALSE,
                       _10, _0 );
        }
    }

    protected void fillMainRoundTableKO32Newa( int constMainRound, NewaKoClass fc )
        throws Exception
    {
        int height = 12;
        int konst = constMainRound;
        writeTextFirstRound( _101, konst + _2, fc.getFightListMapPoolA().get( _7 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _101, konst - _2 * height + _2, fc.getFightListMapPoolA().get( _7 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _101, konst - 4 * height + _2, fc.getFightListMapPoolA().get( _8 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _101, konst - 6 * height + _2, fc.getFightListMapPoolA().get( _8 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( _101, konst - 8 * height + _2, fc.getFightListMapPoolA().get( _9 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _101, konst - _10 * height + _2, fc.getFightListMapPoolA().get( _9 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _101, konst - 12 * height + _2, fc.getFightListMapPoolA().get( _10 ), _8, _0,
                             FIGHTER_RED );
        writeTextFirstRound( _101, konst - 14 * height + _2, fc.getFightListMapPoolA().get( _10 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _101, konst - _16 * height + _2, fc.getFightListMapPoolA().get( _11 ), _8, _0,
                             FIGHTER_RED );
        writeTextFirstRound( _101, konst - 18 * height + _2, fc.getFightListMapPoolA().get( _11 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _101, konst - _20 * height + _2, fc.getFightListMapPoolA().get( _12 ), _8, _0,
                             FIGHTER_RED );
        writeTextFirstRound( _101, konst - 22 * height + _2, fc.getFightListMapPoolA().get( _12 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _101, konst - 24 * height + _2, fc.getFightListMapPoolA().get( _13 ), _8, _0,
                             FIGHTER_RED );
        writeTextFirstRound( _101, konst - 26 * height + _2, fc.getFightListMapPoolA().get( _13 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _101, konst - _28 * height + _2, fc.getFightListMapPoolA().get( _14 ), _8, _0,
                             FIGHTER_RED );
        writeTextFirstRound( _101, konst - 30 * height + _2, fc.getFightListMapPoolA().get( _14 ), _8, _0,
                             FIGHTER_BLUE );

        writeTextFirstRound( _739, konst + _2, fc.getFightListMapPoolB().get( _7 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _739, konst - _2 * height + _2, fc.getFightListMapPoolB().get( _7 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _739, konst - 4 * height + _2, fc.getFightListMapPoolB().get( _8 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _739, konst - 6 * height + _2, fc.getFightListMapPoolB().get( _8 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( _739, konst - 8 * height + _2, fc.getFightListMapPoolB().get( _9 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _739, konst - _10 * height + _2, fc.getFightListMapPoolB().get( _9 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _739, konst - 12 * height + _2, fc.getFightListMapPoolB().get( _10 ), _8, _0,
                             FIGHTER_RED );
        writeTextFirstRound( _739, konst - 14 * height + _2, fc.getFightListMapPoolB().get( _10 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _739, konst - _16 * height + _2, fc.getFightListMapPoolB().get( _11 ), _8, _0,
                             FIGHTER_RED );
        writeTextFirstRound( _739, konst - 18 * height + _2, fc.getFightListMapPoolB().get( _11 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _739, konst - _20 * height + _2, fc.getFightListMapPoolB().get( _12 ), _8, _0,
                             FIGHTER_RED );
        writeTextFirstRound( _739, konst - 22 * height + _2, fc.getFightListMapPoolB().get( _12 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _739, konst - 24 * height + _2, fc.getFightListMapPoolB().get( _13 ), _8, _0,
                             FIGHTER_RED );
        writeTextFirstRound( _739, konst - 26 * height + _2, fc.getFightListMapPoolB().get( _13 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _739, konst - _28 * height + _2, fc.getFightListMapPoolB().get( _14 ), _8, _0,
                             FIGHTER_RED );
        writeTextFirstRound( _739, konst - 30 * height + _2, fc.getFightListMapPoolB().get( _14 ), _8, _0,
                             FIGHTER_BLUE );
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
            writeText( 406, konst - _14 * height + _2, fc.getFinalFight().getFighterRed().getName(), BOOLEAN_FALSE, _10,
                       _0 );
        }
        if ( fc.getFinalFight().getWinnerId().longValue() == fc.getFinalFight().getFighterIdBlue()
            && fc.getFinalFight().getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( 434, konst - _16 * height + _2, fc.getFinalFight().getFighterBlue().getName(), BOOLEAN_TRUE, _10,
                       _0 );
        }
        else if ( fc.getFinalFight().getFighterBlue() != null )
        {
            writeText( 434, konst - _16 * height + _2, fc.getFinalFight().getFighterBlue().getName(), BOOLEAN_FALSE,
                       _10, _0 );
        }
    }

    protected void fillLoserRoundTableKO32Fighting( int constLoserRound, FightingKoClass fc )
        throws Exception
    {
        int height = 12;
        int konst = constLoserRound;
        writeFighterName( _76, konst + _2, fc.getFightListLooserMapPoolA().get( _10 ), _10, _0, FIGHTER_RED );
        writeFighterName( _76, konst - _2 * height + _2, fc.getFightListLooserMapPoolA().get( _10 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _76, konst - 4 * height + _2, fc.getFightListLooserMapPoolA().get( 11 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _76, konst - 6 * height + _2, fc.getFightListLooserMapPoolA().get( 11 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _76, konst - 8 * height + _2, fc.getFightListLooserMapPoolA().get( 12 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _76, konst - _10 * height + _2, fc.getFightListLooserMapPoolA().get( 12 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _76, konst - 12 * height + _2, fc.getFightListLooserMapPoolA().get( 13 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _76, konst - 14 * height + _2, fc.getFightListLooserMapPoolA().get( 13 ), _10, _0,
                          FIGHTER_BLUE );
        //
        writeFighterName( _182, konst + height + _2, fc.getFightListLooserMapPoolA().get( 6 ), _10, _0, FIGHTER_RED );
        writeFighterName( _177, konst - height + _2, fc.getFightListLooserMapPoolA().get( 6 ), _10, _0, FIGHTER_BLUE );
        writeFighterName( _182, konst - 3 * height + _2, fc.getFightListLooserMapPoolA().get( 7 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _177, konst - 5 * height + _2, fc.getFightListLooserMapPoolA().get( 7 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _182, konst - 7 * height + _2, fc.getFightListLooserMapPoolA().get( 8 ), _10, _0,
                          FIGHTER_RED );
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
        writeFighterName( _262, konst - 8 * height + _2, fc.getFightListLooserMapPoolA().get( 5 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _262, konst - 12 * height + _2, fc.getFightListLooserMapPoolA().get( 5 ), _10, _0,
                          FIGHTER_BLUE );
        //
        writeFighterName( _368, konst + _2, fc.getFightListLooserMapPoolA().get( _2 ), _10, _0, FIGHTER_RED );
        writeFighterName( _363, konst - _2 * height + _2, fc.getFightListLooserMapPoolA().get( _2 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _368, konst - 8 * height + _2, fc.getFightListLooserMapPoolA().get( 3 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _363, konst - _10 * height + _2, fc.getFightListLooserMapPoolA().get( 3 ), _10, _0,
                          FIGHTER_BLUE );
        //
        writeFighterName( _448, konst - height + _2, fc.getFightListLooserMapPoolA().get( 1 ), _10, _0, FIGHTER_RED );
        writeFighterName( _448, konst - 9 * height + _2, fc.getFightListLooserMapPoolA().get( 1 ), _10, _0,
                          FIGHTER_BLUE );

        writeFighterName( 554, konst - _3 * height + _2, fc.getFightListLooserMapPoolA().get( 0 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( 549, konst - _5 * height + _2, fc.getFightListLooserMapPoolA().get( 0 ), _10, _0,
                          FIGHTER_BLUE );

        if ( fc.getFightListLooserMapPoolA().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolA().get( _0 ).getFighterIdRed()
            && fc.getFightListLooserMapPoolA().get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( 634, konst - _4 * height + _2,
                       fc.getFightListLooserMapPoolA().get( _0 ).getFighterRed().getName(), BOOLEAN_TRUE, _10, _0 );
        }
        if ( fc.getFightListLooserMapPoolA().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolA().get( _0 ).getFighterIdBlue()
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
        writeFighterName( _76, konst - 4 * height + _2, fc.getFightListLooserMapPoolB().get( 11 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _76, konst - 6 * height + _2, fc.getFightListLooserMapPoolB().get( 11 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _76, konst - 8 * height + _2, fc.getFightListLooserMapPoolB().get( 12 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _76, konst - _10 * height + _2, fc.getFightListLooserMapPoolB().get( 12 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _76, konst - 12 * height + _2, fc.getFightListLooserMapPoolB().get( 13 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _76, konst - 14 * height + _2, fc.getFightListLooserMapPoolB().get( 13 ), _10, _0,
                          FIGHTER_BLUE );
        //
        writeFighterName( _182, konst + height + _2, fc.getFightListLooserMapPoolB().get( 6 ), _10, _0, FIGHTER_RED );
        writeFighterName( _177, konst - height + _2, fc.getFightListLooserMapPoolB().get( 6 ), _10, _0, FIGHTER_BLUE );
        writeFighterName( _182, konst - 3 * height + _2, fc.getFightListLooserMapPoolB().get( 7 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _177, konst - 5 * height + _2, fc.getFightListLooserMapPoolB().get( 7 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _182, konst - 7 * height + _2, fc.getFightListLooserMapPoolB().get( 8 ), _10, _0,
                          FIGHTER_RED );
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
        writeFighterName( _262, konst - 8 * height + _2, fc.getFightListLooserMapPoolB().get( 5 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _262, konst - 12 * height + _2, fc.getFightListLooserMapPoolB().get( 5 ), _10, _0,
                          FIGHTER_BLUE );
        //
        writeFighterName( _368, konst + _2, fc.getFightListLooserMapPoolB().get( _2 ), _10, _0, FIGHTER_RED );
        writeFighterName( _363, konst - _2 * height + _2, fc.getFightListLooserMapPoolB().get( _2 ), _10, _0,
                          FIGHTER_BLUE );

        writeFighterName( _363, konst - 8 * height + _2, fc.getFightListLooserMapPoolB().get( 3 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _368, konst - _10 * height + _2, fc.getFightListLooserMapPoolB().get( 3 ), _10, _0,
                          FIGHTER_BLUE );
        //
        writeFighterName( _448, konst - height + _2, fc.getFightListLooserMapPoolB().get( 1 ), _10, _0, FIGHTER_RED );
        writeFighterName( _448, konst - 9 * height + _2, fc.getFightListLooserMapPoolB().get( 1 ), _10, _0,
                          FIGHTER_BLUE );

        writeFighterName( 554, konst - _3 * height + _2, fc.getFightListLooserMapPoolB().get( 0 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( 549, konst - _5 * height + _2, fc.getFightListLooserMapPoolB().get( 0 ), _10, _0,
                          FIGHTER_BLUE );

        if ( fc.getFightListLooserMapPoolB().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolB().get( _0 ).getFighterIdRed()
            && fc.getFightListLooserMapPoolB().get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( 634, konst - _4 * height + _2,
                       fc.getFightListLooserMapPoolB().get( _0 ).getFighterRed().getName(), BOOLEAN_TRUE, _10, _0 );
        }
        if ( fc.getFightListLooserMapPoolB().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolB().get( _0 ).getFighterIdBlue()
            && fc.getFightListLooserMapPoolB().get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( 634, konst - _4 * height + _2,
                       fc.getFightListLooserMapPoolB().get( _0 ).getFighterBlue().getName(), BOOLEAN_TRUE, _10, _0 );
        }
    }

    protected void fillLoserRoundTableKO32Newa( int constLoserRound, NewaKoClass fc )
        throws Exception
    {
        int height = 12;
        int konst = constLoserRound;
        writeFighterName( _76, konst + _2, fc.getFightListLooserMapPoolA().get( _10 ), _10, _0, FIGHTER_RED );
        writeFighterName( _76, konst - _2 * height + _2, fc.getFightListLooserMapPoolA().get( _10 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _76, konst - 4 * height + _2, fc.getFightListLooserMapPoolA().get( 11 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _76, konst - 6 * height + _2, fc.getFightListLooserMapPoolA().get( 11 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _76, konst - 8 * height + _2, fc.getFightListLooserMapPoolA().get( 12 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _76, konst - _10 * height + _2, fc.getFightListLooserMapPoolA().get( 12 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _76, konst - 12 * height + _2, fc.getFightListLooserMapPoolA().get( 13 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _76, konst - 14 * height + _2, fc.getFightListLooserMapPoolA().get( 13 ), _10, _0,
                          FIGHTER_BLUE );
        //
        writeFighterName( _182, konst + height + _2, fc.getFightListLooserMapPoolA().get( 6 ), _10, _0, FIGHTER_RED );
        writeFighterName( _177, konst - height + _2, fc.getFightListLooserMapPoolA().get( 6 ), _10, _0, FIGHTER_BLUE );
        writeFighterName( _182, konst - 3 * height + _2, fc.getFightListLooserMapPoolA().get( 7 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _177, konst - 5 * height + _2, fc.getFightListLooserMapPoolA().get( 7 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _182, konst - 7 * height + _2, fc.getFightListLooserMapPoolA().get( 8 ), _10, _0,
                          FIGHTER_RED );
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
        writeFighterName( _262, konst - 8 * height + _2, fc.getFightListLooserMapPoolA().get( 5 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _262, konst - 12 * height + _2, fc.getFightListLooserMapPoolA().get( 5 ), _10, _0,
                          FIGHTER_BLUE );
        //
        writeFighterName( _368, konst + _2, fc.getFightListLooserMapPoolA().get( _2 ), _10, _0, FIGHTER_RED );
        writeFighterName( _363, konst - _2 * height + _2, fc.getFightListLooserMapPoolA().get( _2 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _368, konst - 8 * height + _2, fc.getFightListLooserMapPoolA().get( 3 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _363, konst - _10 * height + _2, fc.getFightListLooserMapPoolA().get( 3 ), _10, _0,
                          FIGHTER_BLUE );
        //
        writeFighterName( _448, konst - height + _2, fc.getFightListLooserMapPoolA().get( 1 ), _10, _0, FIGHTER_RED );
        writeFighterName( _448, konst - 9 * height + _2, fc.getFightListLooserMapPoolA().get( 1 ), _10, _0,
                          FIGHTER_BLUE );

        writeFighterName( 554, konst - _3 * height + _2, fc.getFightListLooserMapPoolA().get( 0 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( 549, konst - _5 * height + _2, fc.getFightListLooserMapPoolA().get( 0 ), _10, _0,
                          FIGHTER_BLUE );

        if ( fc.getFightListLooserMapPoolA().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolA().get( _0 ).getFighterIdRed()
            && fc.getFightListLooserMapPoolA().get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( 634, konst - _4 * height + _2,
                       fc.getFightListLooserMapPoolA().get( _0 ).getFighterRed().getName(), BOOLEAN_TRUE, _10, _0 );
        }
        if ( fc.getFightListLooserMapPoolA().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolA().get( _0 ).getFighterIdBlue()
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
        writeFighterName( _76, konst - 4 * height + _2, fc.getFightListLooserMapPoolB().get( 11 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _76, konst - 6 * height + _2, fc.getFightListLooserMapPoolB().get( 11 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _76, konst - 8 * height + _2, fc.getFightListLooserMapPoolB().get( 12 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _76, konst - _10 * height + _2, fc.getFightListLooserMapPoolB().get( 12 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _76, konst - 12 * height + _2, fc.getFightListLooserMapPoolB().get( 13 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _76, konst - 14 * height + _2, fc.getFightListLooserMapPoolB().get( 13 ), _10, _0,
                          FIGHTER_BLUE );
        //
        writeFighterName( _182, konst + height + _2, fc.getFightListLooserMapPoolB().get( 6 ), _10, _0, FIGHTER_RED );
        writeFighterName( _177, konst - height + _2, fc.getFightListLooserMapPoolB().get( 6 ), _10, _0, FIGHTER_BLUE );
        writeFighterName( _182, konst - 3 * height + _2, fc.getFightListLooserMapPoolB().get( 7 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _177, konst - 5 * height + _2, fc.getFightListLooserMapPoolB().get( 7 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _182, konst - 7 * height + _2, fc.getFightListLooserMapPoolB().get( 8 ), _10, _0,
                          FIGHTER_RED );
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
        writeFighterName( _262, konst - 8 * height + _2, fc.getFightListLooserMapPoolB().get( 5 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _262, konst - 12 * height + _2, fc.getFightListLooserMapPoolB().get( 5 ), _10, _0,
                          FIGHTER_BLUE );
        //
        writeFighterName( _368, konst + _2, fc.getFightListLooserMapPoolB().get( _2 ), _10, _0, FIGHTER_RED );
        writeFighterName( _363, konst - _2 * height + _2, fc.getFightListLooserMapPoolB().get( _2 ), _10, _0,
                          FIGHTER_BLUE );

        writeFighterName( _363, konst - 8 * height + _2, fc.getFightListLooserMapPoolB().get( 3 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _368, konst - _10 * height + _2, fc.getFightListLooserMapPoolB().get( 3 ), _10, _0,
                          FIGHTER_BLUE );
        //
        writeFighterName( _448, konst - height + _2, fc.getFightListLooserMapPoolB().get( 1 ), _10, _0, FIGHTER_RED );
        writeFighterName( _448, konst - 9 * height + _2, fc.getFightListLooserMapPoolB().get( 1 ), _10, _0,
                          FIGHTER_BLUE );

        writeFighterName( 554, konst - _3 * height + _2, fc.getFightListLooserMapPoolB().get( 0 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( 549, konst - _5 * height + _2, fc.getFightListLooserMapPoolB().get( 0 ), _10, _0,
                          FIGHTER_BLUE );

        if ( fc.getFightListLooserMapPoolB().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolB().get( _0 ).getFighterIdRed()
            && fc.getFightListLooserMapPoolB().get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( 634, konst - _4 * height + _2,
                       fc.getFightListLooserMapPoolB().get( _0 ).getFighterRed().getName(), BOOLEAN_TRUE, _10, _0 );
        }
        if ( fc.getFightListLooserMapPoolB().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolB().get( _0 ).getFighterIdBlue()
            && fc.getFightListLooserMapPoolB().get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( 634, konst - _4 * height + _2,
                       fc.getFightListLooserMapPoolB().get( _0 ).getFighterBlue().getName(), BOOLEAN_TRUE, _10, _0 );
        }
    }

    protected void fillMainRoundTableKO16Duo( int constMainRound, DuoKoClass fc )
        throws Exception
    {
        int height = _12;
        int konst = constMainRound;
        writeTextFirstRound( _116, konst + _2, fc.getFightListMapPoolA().get( _3 ), _8, _0, FIGHTER_RED );
        writeText( _208, konst + _2, String.valueOf( fc.getFightListMapPoolA().get( _3 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _2 * height + _2, fc.getFightListMapPoolA().get( _3 ), _8, _0,
                             FIGHTER_BLUE );
        writeText( _208, konst - _2 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _3 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _4 * height + _2, fc.getFightListMapPoolA().get( _4 ), _8, _0, FIGHTER_RED );
        writeText( _208, konst - _4 * height + _2, String.valueOf( fc.getFightListMapPoolA().get( _4 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _6 * height + _2, fc.getFightListMapPoolA().get( _4 ), _8, _0,
                             FIGHTER_BLUE );
        writeText( _208, konst - _6 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _4 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _8 * height + _2, fc.getFightListMapPoolA().get( _5 ), _8, _0, FIGHTER_RED );
        writeText( _208, konst - _8 * height + _2, String.valueOf( fc.getFightListMapPoolA().get( _5 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _10 * height + _2, fc.getFightListMapPoolA().get( _5 ), _8, _0,
                             FIGHTER_BLUE );
        writeText( _208, konst - _10 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _5 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _12 * height + _2, fc.getFightListMapPoolA().get( _6 ), _8, _0,
                             FIGHTER_RED );
        writeText( _208, konst - _12 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _6 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _14 * height + _2, fc.getFightListMapPoolA().get( _6 ), _8, _0,
                             FIGHTER_BLUE );
        writeText( _208, konst - _14 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _6 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _16 * height + _2, fc.getFightListMapPoolB().get( _3 ), _8, _0,
                             FIGHTER_RED );
        writeText( _208, konst - _16 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _3 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _18 * height + _2, fc.getFightListMapPoolB().get( _3 ), _8, _0,
                             FIGHTER_BLUE );
        writeText( _208, konst - _18 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _3 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _20 * height + _2, fc.getFightListMapPoolB().get( _4 ), _8, _0,
                             FIGHTER_RED );
        writeText( _208, konst - _20 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _4 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _22 * height + _2, fc.getFightListMapPoolB().get( _4 ), _8, _0,
                             FIGHTER_BLUE );
        writeText( _208, konst - _22 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _4 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _24 * height + _2, fc.getFightListMapPoolB().get( _5 ), _8, _0,
                             FIGHTER_RED );
        writeText( _208, konst - _24 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _5 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _26 * height + _2, fc.getFightListMapPoolB().get( _5 ), _8, _0,
                             FIGHTER_BLUE );
        writeText( _208, konst - _26 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _5 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - _28 * height + _2, fc.getFightListMapPoolB().get( _6 ), _8, _0,
                             FIGHTER_RED );
        writeText( _208, konst - _28 * height + _2,
                   String.valueOf( fc.getFightListMapPoolB().get( _6 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeTextFirstRound( _116, konst - 30 * height + _2, fc.getFightListMapPoolB().get( _6 ), _8, _0,
                             FIGHTER_BLUE );
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
        writeText( _348, konst - _9 * height + _2, String.valueOf( fc.getFightListMapPoolA().get( _2 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _286, konst - _13 * height + _2, fc.getFightListMapPoolA().get( _2 ), _10, _0, FIGHTER_BLUE );
        writeText( _348, konst - _13 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _2 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _286, konst - 17 * height + _2, fc.getFightListMapPoolB().get( _1 ), _10, _0, FIGHTER_RED );
        writeText( _348, konst - 17 * height + _2, String.valueOf( fc.getFightListMapPoolB().get( _1 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
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
        writeText( _488, konst - _3 * height + _2, String.valueOf( fc.getFightListMapPoolA().get( _0 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _426, konst - _11 * height + _2, fc.getFightListMapPoolA().get( _0 ), _10, _0, FIGHTER_BLUE );
        writeText( _488, konst - _11 * height + _2,
                   String.valueOf( fc.getFightListMapPoolA().get( _0 ).getPointsBlue() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _426, konst - 19 * height + _2, fc.getFightListMapPoolB().get( _0 ), _10, _0, FIGHTER_RED );
        writeText( _488, konst - 19 * height + _2, String.valueOf( fc.getFightListMapPoolB().get( _0 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
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
        if ( fc.getFinalFight().getWinnerId().longValue() == fc.getFinalFight().getTeamIdRed()
            && fc.getFinalFight().getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( _706, konst - _15 * height + _2, fc.getFinalFight().getDuoTeamRed().getName(), BOOLEAN_TRUE, _10,
                       _0 );
        }
        if ( fc.getFinalFight().getWinnerId().longValue() == fc.getFinalFight().getTeamIdBlue()
            && fc.getFinalFight().getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( _706, konst - _15 * height + _2, fc.getFinalFight().getDuoTeamBlue().getName(), BOOLEAN_TRUE,
                       _10, _0 );
        }
    }

    protected void fillLoserRoundTableKO16Duo( int constLoserRound, DuoKoClass fc )
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
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _4 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );
        writeFighterName( 146, konst - _4 * height + _2, fc.getFightListLooserMapPoolA().get( _5 ), _10, _0,
                          FIGHTER_RED );
        writeText( _208, konst - _4 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _5 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( 146, konst - _6 * height + _2, fc.getFightListLooserMapPoolA().get( _5 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _208, konst - _6 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _5 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );
        writeFighterName( 146, konst - _8 * height + _2, fc.getFightListLooserMapPoolB().get( _4 ), _10, _0,
                          FIGHTER_RED );
        writeText( _208, konst - _8 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _4 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( 146, konst - _10 * height + _2, fc.getFightListLooserMapPoolB().get( _4 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _208, konst - _10 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _4 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );
        writeFighterName( 146, konst - _12 * height + _2, fc.getFightListLooserMapPoolB().get( _5 ), _10, _0,
                          FIGHTER_RED );
        writeText( _208, konst - _12 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _5 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( 146, konst - _14 * height + _2, fc.getFightListLooserMapPoolB().get( _5 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _208, konst - _14 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _5 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );
        // Round3
        writeFighterName( _307, konst + height + _2, fc.getFightListLooserMapPoolA().get( _2 ), _10, _0, FIGHTER_RED );
        writeText( _364, konst + height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _2 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _302, konst - height + _2, fc.getFightListLooserMapPoolA().get( _2 ), _10, _0, FIGHTER_BLUE );
        writeText( _364, konst - height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _2 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );
        writeFighterName( _307, konst - _3 * height + _2, fc.getFightListLooserMapPoolA().get( _3 ), _10, _0,
                          FIGHTER_RED );
        writeText( _364, konst - _3 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _3 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _302, konst - _5 * height + _2, fc.getFightListLooserMapPoolA().get( _3 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _364, konst - _5 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _3 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );

        writeFighterName( _307, konst - _7 * height + _2, fc.getFightListLooserMapPoolB().get( _2 ), _10, _0,
                          FIGHTER_RED );
        writeText( _364, konst - _7 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _2 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _302, konst - _9 * height + _2, fc.getFightListLooserMapPoolB().get( _2 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _364, konst - _9 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _2 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );
        writeFighterName( _307, konst - _11 * height + _2, fc.getFightListLooserMapPoolB().get( _3 ), _10, _0,
                          FIGHTER_RED );
        writeText( _364, konst - _11 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _3 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _302, konst - _13 * height + _2, fc.getFightListLooserMapPoolB().get( _3 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _364, konst - _13 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _3 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );
        // Round2
        writeFighterName( _442, konst + _2, fc.getFightListLooserMapPoolA().get( _1 ), _10, _0, FIGHTER_RED );
        writeText( _504, konst + _2, String.valueOf( fc.getFightListLooserMapPoolA().get( _1 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _442, konst - _4 * height + _2, fc.getFightListLooserMapPoolA().get( _1 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _504, konst - _4 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _1 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );

        writeFighterName( _442, konst - _8 * height + _2, fc.getFightListLooserMapPoolB().get( _1 ), _10, _0,
                          FIGHTER_RED );
        writeText( _504, konst - _8 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _1 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( _442, konst - _12 * height + _2, fc.getFightListLooserMapPoolB().get( _1 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _504, konst - _12 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _1 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );
        // Round 1
        writeFighterName( _603, konst + _2, fc.getFightListLooserMapPoolA().get( _0 ), _10, _0, FIGHTER_RED );
        writeText( _660, konst + _2, String.valueOf( fc.getFightListLooserMapPoolA().get( _0 ).getPointsRed() ),
                   BOOLEAN_FALSE, _10, _0 );
        writeFighterName( 598, konst - _2 * height + _2, fc.getFightListLooserMapPoolA().get( _0 ), _10, _0,
                          FIGHTER_BLUE );
        writeText( _660, konst - _2 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolA().get( _0 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );

        writeFighterName( _603, konst - _8 * height + _2, fc.getFightListLooserMapPoolB().get( _0 ), _10, _0,
                          FIGHTER_RED );
        writeText( _660, konst - _8 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _0 ).getPointsRed() ), BOOLEAN_FALSE, _10, _0 );
        writeFighterName( 598, konst - _10 * height + _2, fc.getFightListLooserMapPoolB().get( _0 ), _10, _0,
                          FIGHTER_RED );
        writeText( _660, konst - _10 * height + _2,
                   String.valueOf( fc.getFightListLooserMapPoolB().get( _0 ).getPointsBlue() ), BOOLEAN_FALSE, _10,
                   _0 );
        // 3rd

        if ( fc.getFightListLooserMapPoolA().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolA().get( _0 ).getTeamIdRed()
            && fc.getFightListLooserMapPoolA().get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( _738, konst - height + _2, fc.getFightListLooserMapPoolA().get( _0 ).getDuoTeamRed().getName(),
                       BOOLEAN_TRUE, _10, _0 );
        }
        if ( fc.getFightListLooserMapPoolA().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolA().get( _0 ).getTeamIdBlue()
            && fc.getFightListLooserMapPoolA().get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( _738, konst - height + _2, fc.getFightListLooserMapPoolA().get( _0 ).getDuoTeamBlue().getName(),
                       BOOLEAN_TRUE, _10, _0 );
        }

        if ( fc.getFightListLooserMapPoolB().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolB().get( _0 ).getTeamIdRed()
            && fc.getFightListLooserMapPoolB().get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( _738, konst - _9 * height + _2,
                       fc.getFightListLooserMapPoolB().get( _0 ).getDuoTeamRed().getName(), BOOLEAN_TRUE, _10, _0 );
        }
        if ( fc.getFightListLooserMapPoolB().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolB().get( _0 ).getTeamIdBlue()
            && fc.getFightListLooserMapPoolB().get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( _738, konst - _9 * height + _2,
                       fc.getFightListLooserMapPoolB().get( _0 ).getDuoTeamBlue().getName(), BOOLEAN_TRUE, _10, _0 );
        }

    }

    protected void fillMainRoundTableKO32Duo( int constMainRound, DuoKoClass fc )
        throws Exception
    {
        int height = 12;
        int konst = constMainRound;
        writeTextFirstRound( _101, konst + _2, fc.getFightListMapPoolA().get( _7 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _101, konst - _2 * height + _2, fc.getFightListMapPoolA().get( _7 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _101, konst - 4 * height + _2, fc.getFightListMapPoolA().get( _8 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _101, konst - 6 * height + _2, fc.getFightListMapPoolA().get( _8 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( _101, konst - 8 * height + _2, fc.getFightListMapPoolA().get( _9 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _101, konst - _10 * height + _2, fc.getFightListMapPoolA().get( _9 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _101, konst - 12 * height + _2, fc.getFightListMapPoolA().get( _10 ), _8, _0,
                             FIGHTER_RED );
        writeTextFirstRound( _101, konst - 14 * height + _2, fc.getFightListMapPoolA().get( _10 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _101, konst - _16 * height + _2, fc.getFightListMapPoolA().get( _11 ), _8, _0,
                             FIGHTER_RED );
        writeTextFirstRound( _101, konst - 18 * height + _2, fc.getFightListMapPoolA().get( _11 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _101, konst - _20 * height + _2, fc.getFightListMapPoolA().get( _12 ), _8, _0,
                             FIGHTER_RED );
        writeTextFirstRound( _101, konst - 22 * height + _2, fc.getFightListMapPoolA().get( _12 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _101, konst - 24 * height + _2, fc.getFightListMapPoolA().get( _13 ), _8, _0,
                             FIGHTER_RED );
        writeTextFirstRound( _101, konst - 26 * height + _2, fc.getFightListMapPoolA().get( _13 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _101, konst - _28 * height + _2, fc.getFightListMapPoolA().get( _14 ), _8, _0,
                             FIGHTER_RED );
        writeTextFirstRound( _101, konst - 30 * height + _2, fc.getFightListMapPoolA().get( _14 ), _8, _0,
                             FIGHTER_BLUE );

        writeTextFirstRound( _739, konst + _2, fc.getFightListMapPoolB().get( _7 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _739, konst - _2 * height + _2, fc.getFightListMapPoolB().get( _7 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _739, konst - 4 * height + _2, fc.getFightListMapPoolB().get( _8 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _739, konst - 6 * height + _2, fc.getFightListMapPoolB().get( _8 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( _739, konst - 8 * height + _2, fc.getFightListMapPoolB().get( _9 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( _739, konst - _10 * height + _2, fc.getFightListMapPoolB().get( _9 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _739, konst - 12 * height + _2, fc.getFightListMapPoolB().get( _10 ), _8, _0,
                             FIGHTER_RED );
        writeTextFirstRound( _739, konst - 14 * height + _2, fc.getFightListMapPoolB().get( _10 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _739, konst - _16 * height + _2, fc.getFightListMapPoolB().get( _11 ), _8, _0,
                             FIGHTER_RED );
        writeTextFirstRound( _739, konst - 18 * height + _2, fc.getFightListMapPoolB().get( _11 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _739, konst - _20 * height + _2, fc.getFightListMapPoolB().get( _12 ), _8, _0,
                             FIGHTER_RED );
        writeTextFirstRound( _739, konst - 22 * height + _2, fc.getFightListMapPoolB().get( _12 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _739, konst - 24 * height + _2, fc.getFightListMapPoolB().get( _13 ), _8, _0,
                             FIGHTER_RED );
        writeTextFirstRound( _739, konst - 26 * height + _2, fc.getFightListMapPoolB().get( _13 ), _8, _0,
                             FIGHTER_BLUE );
        writeTextFirstRound( _739, konst - _28 * height + _2, fc.getFightListMapPoolB().get( _14 ), _8, _0,
                             FIGHTER_RED );
        writeTextFirstRound( _739, konst - 30 * height + _2, fc.getFightListMapPoolB().get( _14 ), _8, _0,
                             FIGHTER_BLUE );
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
        if ( fc.getFinalFight().getWinnerId().longValue() == fc.getFinalFight().getTeamIdRed()
            && fc.getFinalFight().getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( 406, konst - _14 * height + _2, fc.getFinalFight().getDuoTeamRed().getName(), BOOLEAN_TRUE, _10,
                       _0 );
        }
        else if ( fc.getFinalFight().getDuoTeamRed() != null )
        {
            writeText( 406, konst - _14 * height + _2, fc.getFinalFight().getDuoTeamRed().getName(), BOOLEAN_FALSE, _10,
                       _0 );
        }
        if ( fc.getFinalFight().getWinnerId().longValue() == fc.getFinalFight().getTeamIdBlue()
            && fc.getFinalFight().getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( 434, konst - _16 * height + _2, fc.getFinalFight().getDuoTeamBlue().getName(), BOOLEAN_TRUE, _10,
                       _0 );
        }
        else if ( fc.getFinalFight().getDuoTeamBlue() != null )
        {
            writeText( 434, konst - _16 * height + _2, fc.getFinalFight().getDuoTeamBlue().getName(), BOOLEAN_FALSE,
                       _10, _0 );
        }
    }

    protected void fillLoserRoundTableKO32Duo( int constLoserRound, DuoKoClass fc )
        throws Exception
    {
        int height = 12;
        int konst = constLoserRound;
        writeFighterName( _76, konst + _2, fc.getFightListLooserMapPoolA().get( _10 ), _10, _0, FIGHTER_RED );
        writeFighterName( _76, konst - _2 * height + _2, fc.getFightListLooserMapPoolA().get( _10 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _76, konst - 4 * height + _2, fc.getFightListLooserMapPoolA().get( 11 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _76, konst - 6 * height + _2, fc.getFightListLooserMapPoolA().get( 11 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _76, konst - 8 * height + _2, fc.getFightListLooserMapPoolA().get( 12 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _76, konst - _10 * height + _2, fc.getFightListLooserMapPoolA().get( 12 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _76, konst - 12 * height + _2, fc.getFightListLooserMapPoolA().get( 13 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _76, konst - 14 * height + _2, fc.getFightListLooserMapPoolA().get( 13 ), _10, _0,
                          FIGHTER_BLUE );
        //
        writeFighterName( _182, konst + height + _2, fc.getFightListLooserMapPoolA().get( 6 ), _10, _0, FIGHTER_RED );
        writeFighterName( _177, konst - height + _2, fc.getFightListLooserMapPoolA().get( 6 ), _10, _0, FIGHTER_BLUE );
        writeFighterName( _182, konst - 3 * height + _2, fc.getFightListLooserMapPoolA().get( 7 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _177, konst - 5 * height + _2, fc.getFightListLooserMapPoolA().get( 7 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _182, konst - 7 * height + _2, fc.getFightListLooserMapPoolA().get( 8 ), _10, _0,
                          FIGHTER_RED );
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
        writeFighterName( _262, konst - 8 * height + _2, fc.getFightListLooserMapPoolA().get( 5 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _262, konst - 12 * height + _2, fc.getFightListLooserMapPoolA().get( 5 ), _10, _0,
                          FIGHTER_BLUE );
        //
        writeFighterName( _368, konst + _2, fc.getFightListLooserMapPoolA().get( _2 ), _10, _0, FIGHTER_RED );
        writeFighterName( _363, konst - _2 * height + _2, fc.getFightListLooserMapPoolA().get( _2 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _368, konst - 8 * height + _2, fc.getFightListLooserMapPoolA().get( 3 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _363, konst - _10 * height + _2, fc.getFightListLooserMapPoolA().get( 3 ), _10, _0,
                          FIGHTER_BLUE );
        //
        writeFighterName( _448, konst - height + _2, fc.getFightListLooserMapPoolA().get( 1 ), _10, _0, FIGHTER_RED );
        writeFighterName( _448, konst - 9 * height + _2, fc.getFightListLooserMapPoolA().get( 1 ), _10, _0,
                          FIGHTER_BLUE );

        writeFighterName( 554, konst - _3 * height + _2, fc.getFightListLooserMapPoolA().get( 0 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( 549, konst - _5 * height + _2, fc.getFightListLooserMapPoolA().get( 0 ), _10, _0,
                          FIGHTER_BLUE );

        if ( fc.getFightListLooserMapPoolA().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolA().get( _0 ).getTeamIdRed()
            && fc.getFightListLooserMapPoolA().get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( 634, konst - _4 * height + _2,
                       fc.getFightListLooserMapPoolA().get( _0 ).getDuoTeamRed().getName(), BOOLEAN_TRUE, _10, _0 );
        }
        if ( fc.getFightListLooserMapPoolA().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolA().get( _0 ).getTeamIdBlue()
            && fc.getFightListLooserMapPoolA().get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( 634, konst - _4 * height + _2,
                       fc.getFightListLooserMapPoolA().get( _0 ).getDuoTeamBlue().getName(), BOOLEAN_TRUE, _10, _0 );
        }

        // 2. Loserrunde
        konst -= _20 * height; // nur Veraendern, wenn konst-=48; in createLoserRoundTable() geaendert wird

        writeFighterName( _76, konst + _2, fc.getFightListLooserMapPoolB().get( _10 ), _10, _0, FIGHTER_RED );
        writeFighterName( _76, konst - _2 * height + _2, fc.getFightListLooserMapPoolB().get( _10 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _76, konst - 4 * height + _2, fc.getFightListLooserMapPoolB().get( 11 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _76, konst - 6 * height + _2, fc.getFightListLooserMapPoolB().get( 11 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _76, konst - 8 * height + _2, fc.getFightListLooserMapPoolB().get( 12 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _76, konst - _10 * height + _2, fc.getFightListLooserMapPoolB().get( 12 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _76, konst - 12 * height + _2, fc.getFightListLooserMapPoolB().get( 13 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _76, konst - 14 * height + _2, fc.getFightListLooserMapPoolB().get( 13 ), _10, _0,
                          FIGHTER_BLUE );
        //
        writeFighterName( _182, konst + height + _2, fc.getFightListLooserMapPoolB().get( 6 ), _10, _0, FIGHTER_RED );
        writeFighterName( _177, konst - height + _2, fc.getFightListLooserMapPoolB().get( 6 ), _10, _0, FIGHTER_BLUE );
        writeFighterName( _182, konst - 3 * height + _2, fc.getFightListLooserMapPoolB().get( 7 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _177, konst - 5 * height + _2, fc.getFightListLooserMapPoolB().get( 7 ), _10, _0,
                          FIGHTER_BLUE );
        writeFighterName( _182, konst - 7 * height + _2, fc.getFightListLooserMapPoolB().get( 8 ), _10, _0,
                          FIGHTER_RED );
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
        writeFighterName( _262, konst - 8 * height + _2, fc.getFightListLooserMapPoolB().get( 5 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _262, konst - 12 * height + _2, fc.getFightListLooserMapPoolB().get( 5 ), _10, _0,
                          FIGHTER_BLUE );
        //
        writeFighterName( _368, konst + _2, fc.getFightListLooserMapPoolB().get( _2 ), _10, _0, FIGHTER_RED );
        writeFighterName( _363, konst - _2 * height + _2, fc.getFightListLooserMapPoolB().get( _2 ), _10, _0,
                          FIGHTER_BLUE );

        writeFighterName( _363, konst - 8 * height + _2, fc.getFightListLooserMapPoolB().get( 3 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( _368, konst - _10 * height + _2, fc.getFightListLooserMapPoolB().get( 3 ), _10, _0,
                          FIGHTER_BLUE );
        //
        writeFighterName( _448, konst - height + _2, fc.getFightListLooserMapPoolB().get( 1 ), _10, _0, FIGHTER_RED );
        writeFighterName( _448, konst - 9 * height + _2, fc.getFightListLooserMapPoolB().get( 1 ), _10, _0,
                          FIGHTER_BLUE );

        writeFighterName( 554, konst - _3 * height + _2, fc.getFightListLooserMapPoolB().get( 0 ), _10, _0,
                          FIGHTER_RED );
        writeFighterName( 549, konst - _5 * height + _2, fc.getFightListLooserMapPoolB().get( 0 ), _10, _0,
                          FIGHTER_BLUE );

        if ( fc.getFightListLooserMapPoolB().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolB().get( _0 ).getTeamIdRed()
            && fc.getFightListLooserMapPoolB().get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( 634, konst - _4 * height + _2,
                       fc.getFightListLooserMapPoolB().get( _0 ).getDuoTeamRed().getName(), BOOLEAN_TRUE, _10, _0 );
        }
        if ( fc.getFightListLooserMapPoolB().get( _0 ).getWinnerId().longValue() == fc.getFightListLooserMapPoolB().get( _0 ).getTeamIdBlue()
            && fc.getFightListLooserMapPoolB().get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( 634, konst - _4 * height + _2,
                       fc.getFightListLooserMapPoolB().get( _0 ).getDuoTeamBlue().getName(), BOOLEAN_TRUE, _10, _0 );
        }
    }

    protected int createMainRoundTableKO64( int konst )
        throws Exception
    {
        cb.setColorStroke( new BaseColor( 0x0, 0x0, 0x0 ) );
        cb.setLineWidth( 1f );
        int height = 12; // konst

        for ( int i = 0; i < 63; i++ )
        {
            if ( i % 2 == 0 )
            {
                cb.rectangle( 20, konst - i * height, 16, height );
                cb.rectangle( 36, konst - i * height, 130, height );
                // writeText(28,konst-i*height+2,String.valueOf (i), false,10,0);
            }
            if ( i % 4 == 1 )
            {
                cb.rectangle( 150, konst - i * height, 16, height );
                cb.rectangle( 166, konst - i * height, 80, height );
            }

            if ( i % 8 == 3 )
            {
                cb.rectangle( 230, konst - i * height - height, 16, 3 * height );
                cb.rectangle( 246, konst - i * height, 80, height );
            }
            if ( i % 16 == 7 )
            {
                cb.rectangle( 310, konst - i * height - 3 * height, 16, 7 * height );
                cb.rectangle( 326, konst - i * height, 80, height );
            }
            if ( i == 15 || i == 47 )
            {
                cb.rectangle( 390, konst - i * height - 7 * height, 16, 15 * height );
                cb.rectangle( 406, konst - i * height, 80, height );
            }
            if ( i == 31 )
            {
                cb.rectangle( 470, konst - i * height - 15 * height, 16, 31 * height );
                cb.rectangle( 486, konst - i * height, 80, height );
            }
            cb.stroke();
        }
        return konst;

    }

    protected void createMainRoundAStandardTextKO64( int konst )
        throws Exception
    {
        int height = 12;
        writeText( 28, konst + 2, String.valueOf( 1 ), false, 10, 0 );
        writeText( 28, konst - 32 * height + 2, String.valueOf( 3 ), false, 10, 0 );
        writeText( 28, konst - 2 * height + 2, String.valueOf( 33 ), false, 10, 0 );
        writeText( 28, konst - 34 * height + 2, String.valueOf( 35 ), false, 10, 0 );
        writeText( 28, konst - 4 * height + 2, String.valueOf( 17 ), false, 10, 0 );
        writeText( 28, konst - 36 * height + 2, String.valueOf( 19 ), false, 10, 0 );
        writeText( 28, konst - 6 * height + 2, String.valueOf( 49 ), false, 10, 0 );
        writeText( 28, konst - 38 * height + 2, String.valueOf( 51 ), false, 10, 0 );
        writeText( 28, konst - 8 * height + 2, String.valueOf( 9 ), false, 10, 0 );
        writeText( 28, konst - 40 * height + 2, String.valueOf( 11 ), false, 10, 0 );
        writeText( 28, konst - 10 * height + 2, String.valueOf( 41 ), false, 10, 0 );
        writeText( 28, konst - 42 * height + 2, String.valueOf( 43 ), false, 10, 0 );
        writeText( 28, konst - 12 * height + 2, String.valueOf( 25 ), false, 10, 0 );
        writeText( 28, konst - 44 * height + 2, String.valueOf( 27 ), false, 10, 0 );
        writeText( 28, konst - 14 * height + 2, String.valueOf( 57 ), false, 10, 0 );
        writeText( 28, konst - 46 * height + 2, String.valueOf( 59 ), false, 10, 0 );
        writeText( 28, konst - 16 * height + 2, String.valueOf( 5 ), false, 10, 0 );
        writeText( 28, konst - 48 * height + 2, String.valueOf( 7 ), false, 10, 0 );
        writeText( 28, konst - 18 * height + 2, String.valueOf( 37 ), false, 10, 0 );
        writeText( 28, konst - 50 * height + 2, String.valueOf( 39 ), false, 10, 0 );
        writeText( 28, konst - 20 * height + 2, String.valueOf( 21 ), false, 10, 0 );
        writeText( 28, konst - 52 * height + 2, String.valueOf( 23 ), false, 10, 0 );
        writeText( 28, konst - 22 * height + 2, String.valueOf( 53 ), false, 10, 0 );
        writeText( 28, konst - 54 * height + 2, String.valueOf( 55 ), false, 10, 0 );
        writeText( 28, konst - 24 * height + 2, String.valueOf( 13 ), false, 10, 0 );
        writeText( 28, konst - 56 * height + 2, String.valueOf( 15 ), false, 10, 0 );
        writeText( 28, konst - 26 * height + 2, String.valueOf( 45 ), false, 10, 0 );
        writeText( 28, konst - 58 * height + 2, String.valueOf( 47 ), false, 10, 0 );
        writeText( 28, konst - 28 * height + 2, String.valueOf( 29 ), false, 10, 0 );
        writeText( 28, konst - 60 * height + 2, String.valueOf( 31 ), false, 10, 0 );
        writeText( 28, konst - 30 * height + 2, String.valueOf( 61 ), false, 10, 0 );
        writeText( 28, konst - 62 * height + 2, String.valueOf( 63 ), false, 10, 0 );

        writeText( 158, konst - height + 2, String.valueOf( 1 ), false, 10, 0 );
        writeText( 158, konst - 33 * height + 2, String.valueOf( 17 ), false, 10, 0 );
        writeText( 158, konst - 5 * height + 2, String.valueOf( 3 ), false, 10, 0 );
        writeText( 158, konst - 37 * height + 2, String.valueOf( 19 ), false, 10, 0 );
        writeText( 158, konst - 9 * height + 2, String.valueOf( 5 ), false, 10, 0 );
        writeText( 158, konst - 41 * height + 2, String.valueOf( 21 ), false, 10, 0 );
        writeText( 158, konst - 13 * height + 2, String.valueOf( 7 ), false, 10, 0 );
        writeText( 158, konst - 45 * height + 2, String.valueOf( 23 ), false, 10, 0 );
        writeText( 158, konst - 17 * height + 2, String.valueOf( 9 ), false, 10, 0 );
        writeText( 158, konst - 49 * height + 2, String.valueOf( 25 ), false, 10, 0 );
        writeText( 158, konst - 21 * height + 2, String.valueOf( 11 ), false, 10, 0 );
        writeText( 158, konst - 53 * height + 2, String.valueOf( 27 ), false, 10, 0 );
        writeText( 158, konst - 25 * height + 2, String.valueOf( 13 ), false, 10, 0 );
        writeText( 158, konst - 57 * height + 2, String.valueOf( 29 ), false, 10, 0 );
        writeText( 158, konst - 29 * height + 2, String.valueOf( 15 ), false, 10, 0 );
        writeText( 158, konst - 61 * height + 2, String.valueOf( 31 ), false, 10, 0 );

        writeText( 238, konst - 3 * height + 2, String.valueOf( 33 ), false, 10, 0 );
        writeText( 238, konst - 35 * height + 2, String.valueOf( 41 ), false, 10, 0 );
        writeText( 238, konst - 11 * height + 2, String.valueOf( 35 ), false, 10, 0 );
        writeText( 238, konst - 43 * height + 2, String.valueOf( 43 ), false, 10, 0 );
        writeText( 238, konst - 19 * height + 2, String.valueOf( 37 ), false, 10, 0 );
        writeText( 238, konst - 51 * height + 2, String.valueOf( 45 ), false, 10, 0 );
        writeText( 238, konst - 27 * height + 2, String.valueOf( 39 ), false, 10, 0 );
        writeText( 238, konst - 59 * height + 2, String.valueOf( 47 ), false, 10, 0 );

        writeText( 318, konst - 7 * height + 2, String.valueOf( 49 ), false, 10, 0 );
        writeText( 318, konst - 39 * height + 2, String.valueOf( 53 ), false, 10, 0 );
        writeText( 318, konst - 23 * height + 2, String.valueOf( 51 ), false, 10, 0 );
        writeText( 318, konst - 55 * height + 2, String.valueOf( 55 ), false, 10, 0 );

        writeText( 398, konst - 15 * height + 2, String.valueOf( 57 ), false, 10, 0 );
        writeText( 398, konst - 47 * height + 2, String.valueOf( 59 ), false, 10, 0 );
        writeText( 478, konst - 31 * height + 2, String.valueOf( 61 ), false, 10, 0 );
        writeText( 420, konst - 31 * height + 2, "Pool A", true, 16, 0 );
    }

    protected void createMainRoundBStandardTextKO64( int konst )
        throws Exception
    {
        int height = 12;
        writeText( 28, konst + 2, String.valueOf( 2 ), false, 10, 0 );
        writeText( 28, konst - 32 * height + 2, String.valueOf( 4 ), false, 10, 0 );
        writeText( 28, konst - 2 * height + 2, String.valueOf( 34 ), false, 10, 0 );
        writeText( 28, konst - 34 * height + 2, String.valueOf( 36 ), false, 10, 0 );
        writeText( 28, konst - 4 * height + 2, String.valueOf( 18 ), false, 10, 0 );
        writeText( 28, konst - 36 * height + 2, String.valueOf( 20 ), false, 10, 0 );
        writeText( 28, konst - 6 * height + 2, String.valueOf( 50 ), false, 10, 0 );
        writeText( 28, konst - 38 * height + 2, String.valueOf( 52 ), false, 10, 0 );
        writeText( 28, konst - 8 * height + 2, String.valueOf( 10 ), false, 10, 0 );
        writeText( 28, konst - 40 * height + 2, String.valueOf( 12 ), false, 10, 0 );
        writeText( 28, konst - 10 * height + 2, String.valueOf( 41 ), false, 10, 0 );
        writeText( 28, konst - 42 * height + 2, String.valueOf( 44 ), false, 10, 0 );
        writeText( 28, konst - 12 * height + 2, String.valueOf( 26 ), false, 10, 0 );
        writeText( 28, konst - 44 * height + 2, String.valueOf( 28 ), false, 10, 0 );
        writeText( 28, konst - 14 * height + 2, String.valueOf( 58 ), false, 10, 0 );
        writeText( 28, konst - 46 * height + 2, String.valueOf( 60 ), false, 10, 0 );
        writeText( 28, konst - 16 * height + 2, String.valueOf( 6 ), false, 10, 0 );
        writeText( 28, konst - 48 * height + 2, String.valueOf( 8 ), false, 10, 0 );
        writeText( 28, konst - 18 * height + 2, String.valueOf( 38 ), false, 10, 0 );
        writeText( 28, konst - 50 * height + 2, String.valueOf( 40 ), false, 10, 0 );
        writeText( 28, konst - 20 * height + 2, String.valueOf( 22 ), false, 10, 0 );
        writeText( 28, konst - 52 * height + 2, String.valueOf( 24 ), false, 10, 0 );
        writeText( 28, konst - 22 * height + 2, String.valueOf( 54 ), false, 10, 0 );
        writeText( 28, konst - 54 * height + 2, String.valueOf( 56 ), false, 10, 0 );
        writeText( 28, konst - 24 * height + 2, String.valueOf( 14 ), false, 10, 0 );
        writeText( 28, konst - 56 * height + 2, String.valueOf( 16 ), false, 10, 0 );
        writeText( 28, konst - 26 * height + 2, String.valueOf( 46 ), false, 10, 0 );
        writeText( 28, konst - 58 * height + 2, String.valueOf( 48 ), false, 10, 0 );
        writeText( 28, konst - 28 * height + 2, String.valueOf( 30 ), false, 10, 0 );
        writeText( 28, konst - 60 * height + 2, String.valueOf( 32 ), false, 10, 0 );
        writeText( 28, konst - 30 * height + 2, String.valueOf( 62 ), false, 10, 0 );
        writeText( 28, konst - 62 * height + 2, String.valueOf( 64 ), false, 10, 0 );

        writeText( 158, konst - height + 2, String.valueOf( 2 ), false, 10, 0 );
        writeText( 158, konst - 33 * height + 2, String.valueOf( 18 ), false, 10, 0 );
        writeText( 158, konst - 5 * height + 2, String.valueOf( 4 ), false, 10, 0 );
        writeText( 158, konst - 37 * height + 2, String.valueOf( 20 ), false, 10, 0 );
        writeText( 158, konst - 9 * height + 2, String.valueOf( 6 ), false, 10, 0 );
        writeText( 158, konst - 41 * height + 2, String.valueOf( 22 ), false, 10, 0 );
        writeText( 158, konst - 13 * height + 2, String.valueOf( 8 ), false, 10, 0 );
        writeText( 158, konst - 45 * height + 2, String.valueOf( 24 ), false, 10, 0 );
        writeText( 158, konst - 17 * height + 2, String.valueOf( 10 ), false, 10, 0 );
        writeText( 158, konst - 49 * height + 2, String.valueOf( 26 ), false, 10, 0 );
        writeText( 158, konst - 21 * height + 2, String.valueOf( 12 ), false, 10, 0 );
        writeText( 158, konst - 53 * height + 2, String.valueOf( 28 ), false, 10, 0 );
        writeText( 158, konst - 25 * height + 2, String.valueOf( 14 ), false, 10, 0 );
        writeText( 158, konst - 57 * height + 2, String.valueOf( 30 ), false, 10, 0 );
        writeText( 158, konst - 29 * height + 2, String.valueOf( 16 ), false, 10, 0 );
        writeText( 158, konst - 61 * height + 2, String.valueOf( 32 ), false, 10, 0 );

        writeText( 238, konst - 3 * height + 2, String.valueOf( 34 ), false, 10, 0 );
        writeText( 238, konst - 35 * height + 2, String.valueOf( 42 ), false, 10, 0 );
        writeText( 238, konst - 11 * height + 2, String.valueOf( 36 ), false, 10, 0 );
        writeText( 238, konst - 43 * height + 2, String.valueOf( 44 ), false, 10, 0 );
        writeText( 238, konst - 19 * height + 2, String.valueOf( 38 ), false, 10, 0 );
        writeText( 238, konst - 51 * height + 2, String.valueOf( 46 ), false, 10, 0 );
        writeText( 238, konst - 27 * height + 2, String.valueOf( 40 ), false, 10, 0 );
        writeText( 238, konst - 59 * height + 2, String.valueOf( 48 ), false, 10, 0 );

        writeText( 318, konst - 7 * height + 2, String.valueOf( 50 ), false, 10, 0 );
        writeText( 318, konst - 39 * height + 2, String.valueOf( 54 ), false, 10, 0 );
        writeText( 318, konst - 23 * height + 2, String.valueOf( 52 ), false, 10, 0 );
        writeText( 318, konst - 55 * height + 2, String.valueOf( 56 ), false, 10, 0 );

        writeText( 398, konst - 15 * height + 2, String.valueOf( 58 ), false, 10, 0 );
        writeText( 398, konst - 47 * height + 2, String.valueOf( 60 ), false, 10, 0 );
        writeText( 478, konst - 31 * height + 2, String.valueOf( 62 ), false, 10, 0 );
        writeText( 420, konst - 31 * height + 2, "Pool B", true, 16, 0 );
    }

    protected void createLoserRoundAStandardTextKO64( int konst )
        throws Exception
    {
        int height = 12;

        writeText( 108, konst - 1 * height + 2, String.valueOf( 63 ), false, 10, 0 );
        writeText( 108, konst - 5 * height + 2, String.valueOf( 64 ), false, 10, 0 );
        writeText( 108, konst - 9 * height + 2, String.valueOf( 65 ), false, 10, 0 );
        writeText( 108, konst - 13 * height + 2, String.valueOf( 66 ), false, 10, 0 );
        writeText( 108, konst - 17 * height + 2, String.valueOf( 67 ), false, 10, 0 );
        writeText( 108, konst - 21 * height + 2, String.valueOf( 68 ), false, 10, 0 );
        writeText( 108, konst - 25 * height + 2, String.valueOf( 69 ), false, 10, 0 );
        writeText( 108, konst - 29 * height + 2, String.valueOf( 70 ), false, 10, 0 );
        writeText( 134, konst + height + 2, String.valueOf( 37 ), false, 10, 0 );
        writeText( 134, konst - 3 * height + 2, String.valueOf( 39 ), false, 10, 0 );
        writeText( 134, konst - 7 * height + 2, String.valueOf( 33 ), false, 10, 0 );
        writeText( 134, konst - 11 * height + 2, String.valueOf( 35 ), false, 10, 0 );
        writeText( 134, konst - 15 * height + 2, String.valueOf( 45 ), false, 10, 0 );
        writeText( 134, konst - 19 * height + 2, String.valueOf( 47 ), false, 10, 0 );
        writeText( 134, konst - 23 * height + 2, String.valueOf( 41 ), false, 10, 0 );
        writeText( 134, konst - 27 * height + 2, String.valueOf( 43 ), false, 10, 0 );

        writeText( 214, konst - 0 * height + 2, String.valueOf( 79 ), false, 10, 0 );
        writeText( 214, konst - 4 * height + 2, String.valueOf( 80 ), false, 10, 0 );
        writeText( 214, konst - 8 * height + 2, String.valueOf( 81 ), false, 10, 0 );
        writeText( 214, konst - 12 * height + 2, String.valueOf( 82 ), false, 10, 0 );
        writeText( 214, konst - 16 * height + 2, String.valueOf( 83 ), false, 10, 0 );
        writeText( 214, konst - 20 * height + 2, String.valueOf( 84 ), false, 10, 0 );
        writeText( 214, konst - 24 * height + 2, String.valueOf( 85 ), false, 10, 0 );
        writeText( 214, konst - 28 * height + 2, String.valueOf( 86 ), false, 10, 0 );

        writeText( 294, konst - 2 * height + 2, String.valueOf( 95 ), false, 10, 0 );
        writeText( 294, konst - 10 * height + 2, String.valueOf( 96 ), false, 10, 0 );
        writeText( 294, konst - 18 * height + 2, String.valueOf( 97 ), false, 10, 0 );
        writeText( 294, konst - 26 * height + 2, String.valueOf( 98 ), false, 10, 0 );
        writeText( 320, konst + 2, String.valueOf( 49 ), false, 10, 0 );
        writeText( 320, konst - 8 * height + 2, String.valueOf( 51 ), false, 10, 0 );
        writeText( 320, konst - 16 * height + 2, String.valueOf( 53 ), false, 10, 0 );
        writeText( 320, konst - 24 * height + 2, String.valueOf( 55 ), false, 10, 0 );

        writeText( 400, konst - 1 * height + 2, String.valueOf( 103 ), false, 10, 0 );
        writeText( 400, konst - 9 * height + 2, String.valueOf( 104 ), false, 10, 0 );
        writeText( 400, konst - 17 * height + 2, String.valueOf( 105 ), false, 10, 0 );
        writeText( 400, konst - 25 * height + 2, String.valueOf( 106 ), false, 10, 0 );

        writeText( 480, konst - 5 * height + 2, String.valueOf( 111 ), false, 10, 0 );
        writeText( 480, konst - 21 * height + 2, String.valueOf( 112 ), false, 10, 0 );
        writeText( 506, konst - 3 * height + 2, String.valueOf( 57 ), false, 10, 0 );
        writeText( 506, konst - 19 * height + 2, String.valueOf( 59 ), false, 10, 0 );

        writeText( 586, konst - 4 * height + 2, String.valueOf( 115 ), false, 10, 0 );
        writeText( 586, konst - 20 * height + 2, String.valueOf( 116 ), false, 10, 0 );

        writeText( 666, konst - 12 * height + 2, String.valueOf( 119 ), false, 10, 0 );
        writeText( 708, konst - 30 * height + 2, String.valueOf( 121 ), false, 10, 0 );
        writeText( 628, konst - 29 * height + 2, String.valueOf( 61 ), false, 10, 0 );
        writeText( 628, konst - 31 * height + 2, String.valueOf( 119 ), false, 10, 0 );

       
    }

    protected void createLoserRoundBStandardTextKO64( int konst )
        throws Exception
    {
        int height = 12;

        writeText( 108, konst - 1 * height + 2, String.valueOf( 71 ), false, 10, 0 );
        writeText( 108, konst - 5 * height + 2, String.valueOf( 72 ), false, 10, 0 );
        writeText( 108, konst - 9 * height + 2, String.valueOf( 73 ), false, 10, 0 );
        writeText( 108, konst - 13 * height + 2, String.valueOf( 74 ), false, 10, 0 );
        writeText( 108, konst - 17 * height + 2, String.valueOf( 75 ), false, 10, 0 );
        writeText( 108, konst - 21 * height + 2, String.valueOf( 76 ), false, 10, 0 );
        writeText( 108, konst - 25 * height + 2, String.valueOf( 77 ), false, 10, 0 );
        writeText( 108, konst - 29 * height + 2, String.valueOf( 78 ), false, 10, 0 );
        writeText( 134, konst + height + 2, String.valueOf( 38 ), false, 10, 0 );
        writeText( 134, konst - 3 * height + 2, String.valueOf( 40 ), false, 10, 0 );
        writeText( 134, konst - 7 * height + 2, String.valueOf( 42 ), false, 10, 0 );
        writeText( 134, konst - 11 * height + 2, String.valueOf( 44 ), false, 10, 0 );
        writeText( 134, konst - 15 * height + 2, String.valueOf( 46 ), false, 10, 0 );
        writeText( 134, konst - 19 * height + 2, String.valueOf( 48 ), false, 10, 0 );
        writeText( 134, konst - 23 * height + 2, String.valueOf( 50 ), false, 10, 0 );
        writeText( 134, konst - 27 * height + 2, String.valueOf( 52 ), false, 10, 0 );

        writeText( 214, konst - 0 * height + 2, String.valueOf( 87 ), false, 10, 0 );
        writeText( 214, konst - 4 * height + 2, String.valueOf( 88 ), false, 10, 0 );
        writeText( 214, konst - 8 * height + 2, String.valueOf( 89 ), false, 10, 0 );
        writeText( 214, konst - 12 * height + 2, String.valueOf( 90 ), false, 10, 0 );
        writeText( 214, konst - 16 * height + 2, String.valueOf( 91 ), false, 10, 0 );
        writeText( 214, konst - 20 * height + 2, String.valueOf( 92 ), false, 10, 0 );
        writeText( 214, konst - 24 * height + 2, String.valueOf( 93 ), false, 10, 0 );
        writeText( 214, konst - 28 * height + 2, String.valueOf( 94 ), false, 10, 0 );

        writeText( 294, konst - 2 * height + 2, String.valueOf( 99 ), false, 10, 0 );
        writeText( 294, konst - 10 * height + 2, String.valueOf( 100 ), false, 10, 0 );
        writeText( 294, konst - 18 * height + 2, String.valueOf( 101 ), false, 10, 0 );
        writeText( 294, konst - 26 * height + 2, String.valueOf( 102 ), false, 10, 0 );
        writeText( 320, konst + 2, String.valueOf( 50 ), false, 10, 0 );
        writeText( 320, konst - 8 * height + 2, String.valueOf( 52 ), false, 10, 0 );
        writeText( 320, konst - 16 * height + 2, String.valueOf( 54 ), false, 10, 0 );
        writeText( 320, konst - 24 * height + 2, String.valueOf( 56 ), false, 10, 0 );

        writeText( 400, konst - 1 * height + 2, String.valueOf( 107 ), false, 10, 0 );
        writeText( 400, konst - 9 * height + 2, String.valueOf( 108 ), false, 10, 0 );
        writeText( 400, konst - 17 * height + 2, String.valueOf( 109 ), false, 10, 0 );
        writeText( 400, konst - 25 * height + 2, String.valueOf( 110 ), false, 10, 0 );

        writeText( 480, konst - 5 * height + 2, String.valueOf( 113 ), false, 10, 0 );
        writeText( 480, konst - 21 * height + 2, String.valueOf( 114 ), false, 10, 0 );
        writeText( 506, konst - 3 * height + 2, String.valueOf( 58 ), false, 10, 0 );
        writeText( 506, konst - 19 * height + 2, String.valueOf( 60 ), false, 10, 0 );

        writeText( 586, konst - 4 * height + 2, String.valueOf( 117 ), false, 10, 0 );
        writeText( 586, konst - 20 * height + 2, String.valueOf( 118 ), false, 10, 0 );

        writeText( 666, konst - 12 * height + 2, String.valueOf( 120 ), false, 10, 0 );
        writeText( 708, konst - 30 * height + 2, String.valueOf( 122 ), false, 10, 0 );
        writeText( 628, konst - 29 * height + 2, String.valueOf( 62 ), false, 10, 0 );
        writeText( 628, konst - 31 * height + 2, String.valueOf( 120 ), false, 10, 0 );

    }

    protected int createLoserRoundTableKO64( int konst, String[] numbersFirstRound )
        throws Exception
    {
        cb.setColorStroke( new BaseColor( 0x0, 0x0, 0x0 ) );
        cb.setLineWidth( 1f );
        int height = 12, LoserNr = 0; // konst

        for ( int i = 0; i < 32; i++ )
        {
            if ( i % 2 == 0 )
            {
                LoserNr++;
                cb.rectangle( 20, konst - i * height, 16, height );
                cb.rectangle( 36, konst - i * height, 80, height );
                writeText( 28, konst - i * height + 2, numbersFirstRound[i], false, 10, 0 );
            }
            if ( i % 4 == 1 )
            {
                cb.rectangle( 100, konst - i * height, 16, height );
                cb.rectangle( 116, konst - i * height, 106, height );
                cb.rectangle( 126, konst - i * height + 2 * height, 16, height );
                cb.rectangle( 142, konst - i * height + 2 * height, 80, height );
            }
            if ( i % 4 == 0 )
            {
                cb.rectangle( 206, konst - i * height, 16, height );
                cb.rectangle( 222, konst - i * height, 80, height );
            }
            if ( i % 8 == 2 )
            {
                cb.rectangle( 286, konst - i * height - height, 16, 3 * height );
                cb.rectangle( 302, konst - i * height, 106, height );
                cb.rectangle( 328, konst - i * height + 2 * height, 80, height );
                cb.rectangle( 312, konst - i * height + 2 * height, 16, height );

                cb.rectangle( 392, konst - i * height + height, 16, height );
                cb.rectangle( 408, konst - i * height + height, 80, height );
            }
            if ( i % 16 == 5 )
            {
                cb.rectangle( 472, konst - i * height - 3 * height, 16, 7 * height );
                cb.rectangle( 488, konst - i * height, 106, height );
                cb.rectangle( 514, konst - i * height + 2 * height, 80, height );
                cb.rectangle( 498, konst - i * height + 2 * height, 16, height );

                cb.rectangle( 578, konst - i * height + height, 16, height );
                cb.rectangle( 594, konst - i * height + height, 80, height );
            }

            if ( i % 32 == 12 )
            {
                cb.rectangle( 658, konst - i * height - 7 * height, 16, 15 * height );
                cb.rectangle( 674, konst - i * height, 80, height );

                cb.rectangle( 620, konst - 3 * i * height + 5 * height, 16, height );
                cb.rectangle( 620, konst - 3 * i * height + 7 * height, 16, height );
                cb.rectangle( 636, konst - 3 * i * height + 5 * height, 80, height );
                cb.rectangle( 636, konst - 3 * i * height + 7 * height, 80, height );

                cb.rectangle( 700, konst - 3 * i * height + height + 5 * height, 16, height );
                cb.rectangle( 716, konst - 3 * i * height + height + 5 * height, 80, height );
            }
            cb.stroke();
        }
        return konst;
    }

    protected void createFinalKO64( int konst )
        throws Exception
    {
        int height = 12;
        int konstFinal = 4;
        

        cb.rectangle( 406, konstFinal + 6 * height, 80, height );
        cb.rectangle( 406, konstFinal + 4 * height, 80, height );
        cb.rectangle( 470, konstFinal + 5 * height, 16, height );
        cb.rectangle( 486, konstFinal + 5 * height, 80, height );
        writeText( 478, konstFinal + 5 * height + 2, String.valueOf( 123 ), false, 10, 0 );
        cb.stroke();
    }

    protected void fillMainRoundTableKO64( Map<Integer, Fight> fightListMapPool, int constMainRound )
        throws Exception
    {
        int height = 12;
        int konst = constMainRound;
        writeTextFirstRound( 101, konst + 2, fightListMapPool.get( _15 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 2 * height + 2, fightListMapPool.get( _15 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( 101, konst - 4 * height + 2, fightListMapPool.get( _16 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 6 * height + 2, fightListMapPool.get( _16 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( 101, konst - 8 * height + 2, fightListMapPool.get( _17 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 10 * height + 2, fightListMapPool.get( _17 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( 101, konst - 12 * height + 2, fightListMapPool.get( _18 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 14 * height + 2, fightListMapPool.get( _18 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( 101, konst - 16 * height + 2, fightListMapPool.get( _19 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 18 * height + 2, fightListMapPool.get( _19 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( 101, konst - 20 * height + 2, fightListMapPool.get( _20 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 22 * height + 2, fightListMapPool.get( _20 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( 101, konst - 24 * height + 2, fightListMapPool.get( _21 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 26 * height + 2, fightListMapPool.get( _21 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( 101, konst - 28 * height + 2, fightListMapPool.get( _22 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 30 * height + 2, fightListMapPool.get( _22 ), _8, _0, FIGHTER_BLUE );

        writeTextFirstRound( 101, konst - 32 * height + 2, fightListMapPool.get( _23 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 34 * height + 2, fightListMapPool.get( _23 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( 101, konst - 36 * height + 2, fightListMapPool.get( _24 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 38 * height + 2, fightListMapPool.get( _24 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( 101, konst - 40 * height + 2, fightListMapPool.get( _25 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 42 * height + 2, fightListMapPool.get( _25 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( 101, konst - 44 * height + 2, fightListMapPool.get( _26 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 46 * height + 2, fightListMapPool.get( _26 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( 101, konst - 48 * height + 2, fightListMapPool.get( _27 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 50 * height + 2, fightListMapPool.get( _27 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( 101, konst - 52 * height + 2, fightListMapPool.get( _28 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 54 * height + 2, fightListMapPool.get( _28 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( 101, konst - 56 * height + 2, fightListMapPool.get( _29 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 58 * height + 2, fightListMapPool.get( _29 ), _8, _0, FIGHTER_BLUE );
        writeTextFirstRound( 101, konst - 60 * height + 2, fightListMapPool.get( _30 ), _8, _0, FIGHTER_RED );
        writeTextFirstRound( 101, konst - 62 * height + 2, fightListMapPool.get( _30 ), _8, _0, FIGHTER_BLUE );
        // Round 5
        writeFighterName( 206, konst - height + 2, fightListMapPool.get( _7 ), _8, _0, FIGHTER_RED );
        writeFighterName( 206, konst - 5 * height + 2, fightListMapPool.get( _7 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 206, konst - 9 * height + 2, fightListMapPool.get( _8 ), _8, _0, FIGHTER_RED );
        writeFighterName( 206, konst - 13 * height + 2, fightListMapPool.get( _8 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 206, konst - 17 * height + 2, fightListMapPool.get( _9 ), _8, _0, FIGHTER_RED );
        writeFighterName( 206, konst - 21 * height + 2, fightListMapPool.get( _9 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 206, konst - 25 * height + 2, fightListMapPool.get( _10 ), _8, _0, FIGHTER_RED );
        writeFighterName( 206, konst - 29 * height + 2, fightListMapPool.get( _10 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 206, konst - 33 * height + 2, fightListMapPool.get( _11 ), _8, _0, FIGHTER_RED );
        writeFighterName( 206, konst - 37 * height + 2, fightListMapPool.get( _11 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 206, konst - 41 * height + 2, fightListMapPool.get( _12 ), _8, _0, FIGHTER_RED );
        writeFighterName( 206, konst - 45 * height + 2, fightListMapPool.get( _12 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 206, konst - 49 * height + 2, fightListMapPool.get( _13 ), _8, _0, FIGHTER_RED );
        writeFighterName( 206, konst - 53 * height + 2, fightListMapPool.get( _13 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 206, konst - 57 * height + 2, fightListMapPool.get( _14 ), _8, _0, FIGHTER_RED );
        writeFighterName( 206, konst - 61 * height + 2, fightListMapPool.get( _14 ), _8, _0, FIGHTER_BLUE );
        // Round 4
        writeFighterName( 286, konst - 3 * height + 2, fightListMapPool.get( _3 ), _8, _0, FIGHTER_RED );
        writeFighterName( 286, konst - 11 * height + 2, fightListMapPool.get( _3 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 286, konst - 19 * height + 2, fightListMapPool.get( _4 ), _8, _0, FIGHTER_RED );
        writeFighterName( 286, konst - 27 * height + 2, fightListMapPool.get( _4 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 286, konst - 35 * height + 2, fightListMapPool.get( _5 ), _8, _0, FIGHTER_RED );
        writeFighterName( 286, konst - 43 * height + 2, fightListMapPool.get( _5 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 286, konst - 51 * height + 2, fightListMapPool.get( _6 ), _8, _0, FIGHTER_RED );
        writeFighterName( 286, konst - 59 * height + 2, fightListMapPool.get( _6 ), _8, _0, FIGHTER_BLUE );
        // Round3
        writeFighterName( 366, konst - 7 * height + 2, fightListMapPool.get( _1 ), _8, _0, FIGHTER_RED );
        writeFighterName( 366, konst - 23 * height + 2, fightListMapPool.get( _1 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 366, konst - 39 * height + 2, fightListMapPool.get( _2 ), _8, _0, FIGHTER_RED );
        writeFighterName( 366, konst - 55 * height + 2, fightListMapPool.get( _2 ), _8, _0, FIGHTER_BLUE );
        // Round2
        writeFighterName( 446, konst - 15 * height + 2, fightListMapPool.get( _0 ), _8, _0, FIGHTER_RED );
        writeFighterName( 446, konst - 47 * height + 2, fightListMapPool.get( _0 ), _8, _0, FIGHTER_BLUE );

        if ( fightListMapPool.get( _0 ).getWinnerId().longValue() == fightListMapPool.get( _0 ).getFighterIdRed()
            && fightListMapPool.get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeFighterName( 526, konst - 31 * height + 2, fightListMapPool.get( _0 ), _8, _0, FIGHTER_RED );
        }
        else
        {
            writeFighterName( 526, konst - 31 * height + 2, fightListMapPool.get( _0 ), _8, _0, FIGHTER_BLUE );
        }
    }

    protected void fillFinalKO64( FightingKoClass fc )
        throws Exception
    {
        int height = 12;
        int konstFinal = 4;

        writeFighterName( 446, konstFinal + 6 * height + 2, fc.getFinalFight(), _8, _0, FIGHTER_RED );
        writeFighterName( 446, konstFinal + 4 * height + 2, fc.getFinalFight(), _8, _0, FIGHTER_BLUE );

        if ( fc.getFinalFight().getWinnerId().longValue() == fc.getFinalFight().getFighterIdRed()
            && fc.getFinalFight().getWinnerId().longValue() != TypeUtil.LONG_EMPTY )

            writeText( 526, konstFinal + 5 * height + 2, fc.getFinalFight().getFighterRed().getName(), BOOLEAN_TRUE,
                       _10, _0 );

        if ( fc.getFinalFight().getWinnerId().longValue() == fc.getFinalFight().getFighterIdBlue()
            && fc.getFinalFight().getWinnerId().longValue() != TypeUtil.LONG_EMPTY )

            writeText( 526, konstFinal + 5 * height + 2, fc.getFinalFight().getFighterBlue().getName(), BOOLEAN_TRUE,
                       _10, _0 );
        cb.stroke();
    }

    protected void fillLoserRoundTableKO64( Map<Integer, Fight> fightListMapPool, int constLoserRound )
        throws Exception
    {
        int height = 12;
        int konst = constLoserRound;

        writeFighterName( 76, konst + 2, fightListMapPool.get( _22 ), _8, _0, FIGHTER_RED );
        writeFighterName( 76, konst - 2 * height + 2, fightListMapPool.get( _22 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 76, konst - 4 * height + 2, fightListMapPool.get( _23 ), _8, _0, FIGHTER_RED );
        writeFighterName( 76, konst - 6 * height + 2, fightListMapPool.get( _23 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 76, konst - 8 * height + 2, fightListMapPool.get( _24 ), _8, _0, FIGHTER_RED );
        writeFighterName( 76, konst - 10 * height + 2, fightListMapPool.get( _24 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 76, konst - 12 * height + 2, fightListMapPool.get( _25 ), _8, _0, FIGHTER_RED );
        writeFighterName( 76, konst - 14 * height + 2, fightListMapPool.get( _25 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 76, konst - 16 * height + 2, fightListMapPool.get( _26 ), _8, _0, FIGHTER_RED );
        writeFighterName( 76, konst - 18 * height + 2, fightListMapPool.get( _26 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 76, konst - 20 * height + 2, fightListMapPool.get( _27 ), _8, _0, FIGHTER_RED );
        writeFighterName( 76, konst - 22 * height + 2, fightListMapPool.get( _27 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 76, konst - 24 * height + 2, fightListMapPool.get( _28 ), _8, _0, FIGHTER_RED );
        writeFighterName( 76, konst - 26 * height + 2, fightListMapPool.get( _28 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 76, konst - 28 * height + 2, fightListMapPool.get( _29 ), _8, _0, FIGHTER_RED );
        writeFighterName( 76, konst - 30 * height + 2, fightListMapPool.get( _29 ), _8, _0, FIGHTER_BLUE );

        writeFighterName( 182, konst + 1 * height + 2, fightListMapPool.get( _14 ), _8, _0, FIGHTER_RED );
        writeFighterName( 177, konst - 1 * height + 2, fightListMapPool.get( _14 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 182, konst - 3 * height + 2, fightListMapPool.get( _15 ), _8, _0, FIGHTER_RED );
        writeFighterName( 177, konst - 5 * height + 2, fightListMapPool.get( _15 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 182, konst - 7 * height + 2, fightListMapPool.get( _16 ), _8, _0, FIGHTER_RED );
        writeFighterName( 177, konst - 9 * height + 2, fightListMapPool.get( _16 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 182, konst - 11 * height + 2, fightListMapPool.get( _17 ), _8, _0, FIGHTER_RED );
        writeFighterName( 177, konst - 13 * height + 2, fightListMapPool.get( _17 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 182, konst - 15 * height + 2, fightListMapPool.get( _18 ), _8, _0, FIGHTER_RED );
        writeFighterName( 177, konst - 17 * height + 2, fightListMapPool.get( _18 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 182, konst - 19 * height + 2, fightListMapPool.get( _19 ), _8, _0, FIGHTER_RED );
        writeFighterName( 177, konst - 21 * height + 2, fightListMapPool.get( _19 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 182, konst - 23 * height + 2, fightListMapPool.get( _20 ), _8, _0, FIGHTER_RED );
        writeFighterName( 177, konst - 25 * height + 2, fightListMapPool.get( _20 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 182, konst - 27 * height + 2, fightListMapPool.get( _21 ), _8, _0, FIGHTER_RED );
        writeFighterName( 177, konst - 29 * height + 2, fightListMapPool.get( _21 ), _8, _0, FIGHTER_BLUE );

        writeFighterName( 262, konst + 0 * height + 2, fightListMapPool.get( _10 ), _8, _0, FIGHTER_RED );
        writeFighterName( 262, konst - 4 * height + 2, fightListMapPool.get( _10 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 262, konst - 8 * height + 2, fightListMapPool.get( _11 ), _8, _0, FIGHTER_RED );
        writeFighterName( 262, konst - 12 * height + 2, fightListMapPool.get( _11 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 262, konst - 16 * height + 2, fightListMapPool.get( _12 ), _8, _0, FIGHTER_RED );
        writeFighterName( 262, konst - 20 * height + 2, fightListMapPool.get( _12 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 262, konst - 24 * height + 2, fightListMapPool.get( _13 ), _8, _0, FIGHTER_RED );
        writeFighterName( 262, konst - 28 * height + 2, fightListMapPool.get( _13 ), _8, _0, FIGHTER_BLUE );

        //
        writeFighterName( 368, konst + 0 * height + 2, fightListMapPool.get( _6 ), _8, _0, FIGHTER_RED );
        writeFighterName( 368, konst - 2 * height + 2, fightListMapPool.get( _6 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 368, konst - 8 * height + 2, fightListMapPool.get( _7 ), _8, _0, FIGHTER_RED );
        writeFighterName( 368, konst - 10 * height + 2, fightListMapPool.get( _7 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 368, konst - 16 * height + 2, fightListMapPool.get( _8 ), _8, _0, FIGHTER_RED );
        writeFighterName( 368, konst - 18 * height + 2, fightListMapPool.get( _8 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 368, konst - 24 * height + 2, fightListMapPool.get( _9 ), _8, _0, FIGHTER_RED );
        writeFighterName( 368, konst - 26 * height + 2, fightListMapPool.get( _9 ), _8, _0, FIGHTER_BLUE );
        //
        writeFighterName( 448, konst - 1 * height + 2, fightListMapPool.get( _4 ), _8, _0, FIGHTER_RED );
        writeFighterName( 448, konst - 9 * height + 2, fightListMapPool.get( _4 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 448, konst - 17 * height + 2, fightListMapPool.get( _5 ), _8, _0, FIGHTER_RED );
        writeFighterName( 448, konst - 25 * height + 2, fightListMapPool.get( _5 ), _8, _0, FIGHTER_BLUE );
        //
        writeFighterName( 554, konst - 3 * height + 2, fightListMapPool.get( _2 ), _8, _0, FIGHTER_RED );
        writeFighterName( 549, konst - 5 * height + 2, fightListMapPool.get( _2 ), _8, _0, FIGHTER_BLUE );
        writeFighterName( 554, konst - 19 * height + 2, fightListMapPool.get( _3 ), _8, _0, FIGHTER_RED );
        writeFighterName( 549, konst - 21 * height + 2, fightListMapPool.get( _3 ), _8, _0, FIGHTER_BLUE );
        //
        writeFighterName( 634, konst - 4 * height + 2, fightListMapPool.get( _1 ), _8, _0, FIGHTER_RED );
        writeFighterName( 634, konst - 20 * height + 2, fightListMapPool.get( _1 ), _8, _0, FIGHTER_BLUE );

        // writeText( 714, konst - 12 * height + 2, Ko.LoserRoundA[1].getNameWhite(), true, 8, 0 );
        writeFighterName( 714, konst - 12 * height + 2, fightListMapPool.get( _0 ), _10, _0, FIGHTER_BLUE );

        writeFighterName( 666, konst - 29 * height + 2, fightListMapPool.get( _0 ), _10, _0, FIGHTER_RED );
        writeFighterName( 666, konst - 31 * height + 2, fightListMapPool.get( _0 ), _10, _0, FIGHTER_BLUE );
        // 3rd

        if ( fightListMapPool.get( _0 ).getWinnerId().longValue() == fightListMapPool.get( _0 ).getFighterIdRed()
            && fightListMapPool.get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( 756, konst - 30 * height + 2, fightListMapPool.get( _0 ).getFighterRed().getName(), BOOLEAN_TRUE,
                       _8, _0 );
        }
        if ( fightListMapPool.get( _0 ).getWinnerId().longValue() == fightListMapPool.get( _0 ).getFighterIdBlue()
            && fightListMapPool.get( _0 ).getWinnerId().longValue() != TypeUtil.LONG_EMPTY )
        {
            writeText( 756, konst - 30 * height + 2, fightListMapPool.get( _0 ).getFighterBlue().getName(),
                       BOOLEAN_TRUE, _8, _0 );
        }
    }

}
