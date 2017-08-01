package de.jjw.webapp.pdf;

/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : BasePoolPDF.java
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


import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.itextpdf.text.BaseColor;

import de.jjw.model.duo.DuoDoublePoolClass;
import de.jjw.model.duo.DuoDoublePoolItem;
import de.jjw.model.duo.DuoFight;
import de.jjw.model.duo.DuoSimplePoolItem;
import de.jjw.model.fighting.Fight;
import de.jjw.model.fighting.FightingDoublePoolClass;
import de.jjw.model.fighting.FightingDoublePoolItem;
import de.jjw.model.fighting.FightingSimplePoolItem;
import de.jjw.model.newa.NewaDoublePoolClass;
import de.jjw.model.newa.NewaDoublePoolItem;
import de.jjw.model.newa.NewaFight;
import de.jjw.model.newa.NewaSimplePoolItem;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;

public class BasePoolPDF
    extends BasePDF
    implements IValueConstants
{
    protected ResourceBundle rbFighting;

    protected ResourceBundle rbDuo;

    protected ResourceBundle rb;

    private static String Fights[] = { "1-2", "3-4", "1-5", "2-3", "4-5", "1-3", "2-4", "3-5", "1-4", "2-5" };

    private static int FightNr[] = { _1, _2, 5, 6, 9, _10, 13, _14, 17, _18, 3, 4, 7, 8, 11, 12, 15, 16, 19, _20 };

    /**
     * erstellt die ErgebnisTabelle für einen Pool
     * 
     * @param konst
     * @return gibt die unterste Position der Tabelle zurück
     * @throws Exception
     */
    protected int createResultTable( int konst )
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
                writeText( j + _40, _545 - konst - i * height, rbFighting.getString( "pdf.pool.name" ), BOOLEAN_FALSE );
            }

            j += _80;
            cb.rectangle( j, _540 - konst - i * height, _80, height );
            if ( i == _0 )
            {
                writeText( j + _40, _545 - konst - i * height, rbFighting.getString( "pdf.pool.firstname" ),
                           BOOLEAN_FALSE );
            }

            j += _80;
            cb.rectangle( j, _540 - konst - i * height, _80, height );
            if ( i == _0 )
            {
                writeText( j + _40, _545 - konst - i * height, rbFighting.getString( "pdf.pool.team" ), BOOLEAN_FALSE );
            }

            j += _80;
            cb.rectangle( j, _540 - konst - i * height, _20, height );
            if ( i == _0 )
            {
                writeText( j + _10, _545 - konst - i * height, rbFighting.getString( "pdf.pool.nb" ), BOOLEAN_FALSE );
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
                writeText( j + _15, _545 - konst - i * height, rbFighting.getString( "pdf.pool.wins" ), BOOLEAN_FALSE );
            }

            j += _30;
            cb.rectangle( j, _540 - konst - i * height, _30, height );
            if ( i == _0 )
            {
                writeText( j + _15, _545 - konst - i * height, rbFighting.getString( "pdf.pool.points" ), BOOLEAN_FALSE );
            }

            j += _30;
            cb.rectangle( j, _540 - konst - i * height, _30, height );
            if ( i == _0 )
            {
                writeText( j + _15, _545 - konst - i * height, rbFighting.getString( "pdf.pool.place" ), BOOLEAN_FALSE );
            }
            cb.stroke();
        }
        return konst;
    }

    protected void fillResultTableFightingPool( int pdfHeight, List<FightingSimplePoolItem> fighterList )
        throws Exception
    {
        int height = _18, j = _0;
        pdfHeight += height;
        for ( int i = _0; i < fighterList.size(); i++ )
        {
            j = _24;
            writeText( j + _40, _545 - pdfHeight - i * height, fighterList.get( i ).getFighter().getName(),
                       BOOLEAN_FALSE );
            j += _80;
            writeText( j + _40, _545 - pdfHeight - i * height, fighterList.get( i ).getFighter().getFirstname(),
                       BOOLEAN_FALSE );
            j += _80;
            writeText( j + _40, _545 - pdfHeight - i * height,
                       fighterList.get( i ).getFighter().getTeam().getTeamName(), BOOLEAN_FALSE, 7, 0 );
            j += _80;

            j += _20;

            if ( i == ( _0 ) )
            {
                j += _40;
            }
            writeText( j + _7, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getResultFight1() ), BOOLEAN_FALSE );
            writeText( j + _27, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getUbFight1() ), BOOLEAN_FALSE );
            j += _40;
            if ( i == ( _1 ) )
            {
                j += _40;
            }
            writeText( j + _7, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getResultFight2() ), BOOLEAN_FALSE );
            writeText( j + _27, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getUbFight2() ), BOOLEAN_FALSE );
            j += _40;
            if ( i == ( 2 ) )
            {
                j += _40;
            }
            writeText( j + _7, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getResultFight3() ), BOOLEAN_FALSE );
            writeText( j + _27, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getUbFight3() ), BOOLEAN_FALSE );
            j += _40;
            if ( i == ( 3 ) )
            {
                j += _40;
            }
            writeText( j + _7, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getResultFight4() ), BOOLEAN_FALSE );
            writeText( j + _27, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getUbFight4() ), BOOLEAN_FALSE );
            j += _40;

            if ( i == ( 4 ) )
            {
                j += _40;
            }

            writeText( j + _15, _545 - pdfHeight - i * height,
                       String.valueOf( fighterList.get( i ).getResult().getWinCount() ), BOOLEAN_FALSE );
            j += _30;
            writeText( j + _15, _545 - pdfHeight - i * height,
                       String.valueOf( fighterList.get( i ).getResult().getUBCount() ), BOOLEAN_FALSE );
            j += _30;
            writeText( j + _15, _545 - pdfHeight - i * height, String.valueOf( fighterList.get( i ).getPlace() ),
                       BOOLEAN_FALSE );
            cb.stroke();
        }
    }

    protected void fillResultTableNewaPool( int pdfHeight, List<NewaSimplePoolItem> fighterList )
        throws Exception
    {
        int height = _18, j = _0;
        pdfHeight += height;
        for ( int i = _0; i < fighterList.size(); i++ )
        {
            j = _24;
            writeText( j + _40, _545 - pdfHeight - i * height, fighterList.get( i ).getFighter().getName(),
                       BOOLEAN_FALSE );
            j += _80;
            writeText( j + _40, _545 - pdfHeight - i * height, fighterList.get( i ).getFighter().getFirstname(),
                       BOOLEAN_FALSE );
            j += _80;
            writeText( j + _40, _545 - pdfHeight - i * height,
                       fighterList.get( i ).getFighter().getTeam().getTeamName(), BOOLEAN_FALSE, 7, 0 );
            j += _80;

            j += _20;

            if ( i == ( _0 ) )
            {
                j += _40;
            }
            writeText( j + _7, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getResultFight1() ), BOOLEAN_FALSE );
            writeText( j + _27, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getUbFight1() ), BOOLEAN_FALSE );
            j += _40;
            if ( i == ( _1 ) )
            {
                j += _40;
            }
            writeText( j + _7, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getResultFight2() ), BOOLEAN_FALSE );
            writeText( j + _27, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getUbFight2() ), BOOLEAN_FALSE );
            j += _40;
            if ( i == ( 2 ) )
            {
                j += _40;
            }
            writeText( j + _7, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getResultFight3() ), BOOLEAN_FALSE );
            writeText( j + _27, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getUbFight3() ), BOOLEAN_FALSE );
            j += _40;
            if ( i == ( 3 ) )
            {
                j += _40;
            }
            writeText( j + _7, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getResultFight4() ), BOOLEAN_FALSE );
            writeText( j + _27, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getUbFight4() ), BOOLEAN_FALSE );
            j += _40;

            if ( i == ( 4 ) )
            {
                j += _40;
            }

            writeText( j + _15, _545 - pdfHeight - i * height,
                       String.valueOf( fighterList.get( i ).getResult().getWinCount() ), BOOLEAN_FALSE );
            j += _30;
            writeText( j + _15, _545 - pdfHeight - i * height,
                       String.valueOf( fighterList.get( i ).getResult().getUBCount() ), BOOLEAN_FALSE );
            j += _30;
            writeText( j + _15, _545 - pdfHeight - i * height, String.valueOf( fighterList.get( i ).getPlace() ),
                       BOOLEAN_FALSE );
            cb.stroke();
        }
    }

    protected void fillResultTableDuoPool( int pdfHeight, List<DuoSimplePoolItem> duoList )
        throws Exception
    {
        int height = _18, j = _0;
        pdfHeight += height;
        for ( int i = _0; i < duoList.size(); i++ )
        {
            j = _24;
            writeText( j + _40, _545 - pdfHeight - i * height, duoList.get( i ).getDuoTeam().getName() + " / "
                + duoList.get( i ).getDuoTeam().getName2(), BOOLEAN_FALSE, 7, 0 );
            j += _80;
            writeText( j + _40, _545 - pdfHeight - i * height, duoList.get( i ).getDuoTeam().getFirstname() + " / "
                + duoList.get( i ).getDuoTeam().getFirstname2(), BOOLEAN_FALSE, 7, 0 );
            j += _80;
            writeText( j + _40, _545 - pdfHeight - i * height, duoList.get( i ).getDuoTeam().getTeam().getTeamName(),
                       BOOLEAN_FALSE, 7, 0 );
            j += _80;

            j += _20;

            if ( i == ( _0 ) )
            {
                j += _40;
            }
            writeText( j + _7, _540 - pdfHeight - i * height + _5,
                       String.valueOf( duoList.get( i ).getResult().getResultFight1() ), BOOLEAN_FALSE );
            writeText( j + _27, _540 - pdfHeight - i * height + _5,
                       String.valueOf( duoList.get( i ).getResult().getUbFight1() ), BOOLEAN_FALSE );
            j += _40;
            if ( i == ( _1 ) )
            {
                j += _40;
            }
            writeText( j + _7, _540 - pdfHeight - i * height + _5,
                       String.valueOf( duoList.get( i ).getResult().getResultFight2() ), BOOLEAN_FALSE );
            writeText( j + _27, _540 - pdfHeight - i * height + _5,
                       String.valueOf( duoList.get( i ).getResult().getUbFight2() ), BOOLEAN_FALSE );
            j += _40;
            if ( i == ( 2 ) )
            {
                j += _40;
            }
            writeText( j + _7, _540 - pdfHeight - i * height + _5,
                       String.valueOf( duoList.get( i ).getResult().getResultFight3() ), BOOLEAN_FALSE );
            writeText( j + _27, _540 - pdfHeight - i * height + _5,
                       String.valueOf( duoList.get( i ).getResult().getUbFight3() ), BOOLEAN_FALSE );
            j += _40;
            if ( i == ( 3 ) )
            {
                j += _40;
            }
            writeText( j + _7, _540 - pdfHeight - i * height + _5,
                       String.valueOf( duoList.get( i ).getResult().getResultFight4() ), BOOLEAN_FALSE );
            writeText( j + _27, _540 - pdfHeight - i * height + _5,
                       String.valueOf( duoList.get( i ).getResult().getUbFight4() ), BOOLEAN_FALSE );
            j += _40;

            if ( i == ( 4 ) )
            {
                j += _40;
            }

            writeText( j + _15, _545 - pdfHeight - i * height,
                       String.valueOf( duoList.get( i ).getResult().getWinCount() ), BOOLEAN_FALSE );
            j += _30;
            writeText( j + _15, _545 - pdfHeight - i * height,
                       String.valueOf( duoList.get( i ).getResult().getUBCount() ), BOOLEAN_FALSE );
            j += _30;
            writeText( j + _15, _545 - pdfHeight - i * height, String.valueOf( duoList.get( i ).getPlace() ),
                       BOOLEAN_FALSE );
            cb.stroke();
        }
    }

    protected void fillResultTableFightingDPool( int pdfHeight, List<FightingDoublePoolItem> fighterList )
        throws Exception
    {
        int height = _18, j = _0;
        pdfHeight += height;
        for ( int i = _0; i < fighterList.size(); i++ )
        {
            j = _24;
            writeText( j + _40, _545 - pdfHeight - i * height, fighterList.get( i ).getFighter().getName(),
                       BOOLEAN_FALSE );
            j += _80;
            writeText( j + _40, _545 - pdfHeight - i * height, fighterList.get( i ).getFighter().getFirstname(),
                       BOOLEAN_FALSE );
            j += _80;
            writeText( j + _40, _545 - pdfHeight - i * height,
                       fighterList.get( i ).getFighter().getTeam().getTeamName(), BOOLEAN_FALSE, 7, 0 );
            j += _80;

            j += _20;

            if ( i == ( _0 ) )
            {
                j += _40;
            }
            writeText( j + _7, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getResultFight1() ), BOOLEAN_FALSE );
            writeText( j + _27, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getUbFight1() ), BOOLEAN_FALSE );
            j += _40;
            if ( i == ( _1 ) )
            {
                j += _40;
            }
            writeText( j + _7, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getResultFight2() ), BOOLEAN_FALSE );
            writeText( j + _27, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getUbFight2() ), BOOLEAN_FALSE );
            j += _40;
            if ( i == ( 2 ) )
            {
                j += _40;
            }
            writeText( j + _7, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getResultFight3() ), BOOLEAN_FALSE );
            writeText( j + _27, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getUbFight3() ), BOOLEAN_FALSE );
            j += _40;
            if ( i == ( 3 ) )
            {
                j += _40;
            }
            writeText( j + _7, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getResultFight4() ), BOOLEAN_FALSE );
            writeText( j + _27, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getUbFight4() ), BOOLEAN_FALSE );
            j += _40;

            if ( i == ( 4 ) )
            {
                j += _40;
            }

            writeText( j + _15, _545 - pdfHeight - i * height,
                       String.valueOf( fighterList.get( i ).getResult().getWinCount() ), BOOLEAN_FALSE );
            j += _30;
            writeText( j + _15, _545 - pdfHeight - i * height,
                       String.valueOf( fighterList.get( i ).getResult().getUBCount() ), BOOLEAN_FALSE );
            j += _30;
            writeText( j + _15, _545 - pdfHeight - i * height, String.valueOf( fighterList.get( i ).getPlace() ),
                       BOOLEAN_FALSE );
            cb.stroke();
        }
    }

    protected void fillResultTableNewaDPool( int pdfHeight, List<NewaDoublePoolItem> fighterList )
        throws Exception
    {
        int height = _18, j = _0;
        pdfHeight += height;
        for ( int i = _0; i < fighterList.size(); i++ )
        {
            j = _24;
            writeText( j + _40, _545 - pdfHeight - i * height, fighterList.get( i ).getFighter().getName(),
                       BOOLEAN_FALSE );
            j += _80;
            writeText( j + _40, _545 - pdfHeight - i * height, fighterList.get( i ).getFighter().getFirstname(),
                       BOOLEAN_FALSE );
            j += _80;
            writeText( j + _40, _545 - pdfHeight - i * height,
                       fighterList.get( i ).getFighter().getTeam().getTeamName(), BOOLEAN_FALSE, 7, 0 );
            j += _80;

            j += _20;

            if ( i == ( _0 ) )
            {
                j += _40;
            }
            writeText( j + _7, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getResultFight1() ), BOOLEAN_FALSE );
            writeText( j + _27, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getUbFight1() ), BOOLEAN_FALSE );
            j += _40;
            if ( i == ( _1 ) )
            {
                j += _40;
            }
            writeText( j + _7, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getResultFight2() ), BOOLEAN_FALSE );
            writeText( j + _27, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getUbFight2() ), BOOLEAN_FALSE );
            j += _40;
            if ( i == ( 2 ) )
            {
                j += _40;
            }
            writeText( j + _7, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getResultFight3() ), BOOLEAN_FALSE );
            writeText( j + _27, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getUbFight3() ), BOOLEAN_FALSE );
            j += _40;
            if ( i == ( 3 ) )
            {
                j += _40;
            }
            writeText( j + _7, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getResultFight4() ), BOOLEAN_FALSE );
            writeText( j + _27, _540 - pdfHeight - i * height + _5,
                       String.valueOf( fighterList.get( i ).getResult().getUbFight4() ), BOOLEAN_FALSE );
            j += _40;

            if ( i == ( 4 ) )
            {
                j += _40;
            }

            writeText( j + _15, _545 - pdfHeight - i * height,
                       String.valueOf( fighterList.get( i ).getResult().getWinCount() ), BOOLEAN_FALSE );
            j += _30;
            writeText( j + _15, _545 - pdfHeight - i * height,
                       String.valueOf( fighterList.get( i ).getResult().getUBCount() ), BOOLEAN_FALSE );
            j += _30;
            writeText( j + _15, _545 - pdfHeight - i * height, String.valueOf( fighterList.get( i ).getPlace() ),
                       BOOLEAN_FALSE );
            cb.stroke();
        }
    }

    protected void fillResultTableDuoDPool( int konst, List<DuoDoublePoolItem> duoTeamList )
        throws Exception
    {
        int height = _18, j = _0;
        konst += height;
        for ( int i = _0; i < duoTeamList.size(); i++ )
        {
            j = _24;
            writeText( j + _40, _545 - konst - i * height, duoTeamList.get( i ).getDuoTeam().getName() + " / "
                + duoTeamList.get( i ).getDuoTeam().getName2(), BOOLEAN_FALSE, 7, 0 );
            j += _80;
            writeText( j + _40, _545 - konst - i * height, duoTeamList.get( i ).getDuoTeam().getFirstname() + " / "
                + duoTeamList.get( i ).getDuoTeam().getFirstname2(), BOOLEAN_FALSE, 7, 0 );
            j += _80;
            writeText( j + _40, _545 - konst - i * height, duoTeamList.get( i ).getDuoTeam().getTeam().getTeamName(),
                       BOOLEAN_FALSE, 7, 0 );
            j += _80;

            j += _20;

            if ( i == ( _0 ) )
            {
                j += _40;
            }
            writeText( j + 7, _540 - konst - i * height + 5,
                       String.valueOf( duoTeamList.get( i ).getResult().getResultFight1() ), BOOLEAN_FALSE );
            writeText( j + 27, _540 - konst - i * height + 5,
                       String.valueOf( duoTeamList.get( i ).getResult().getUbFight1() ), BOOLEAN_FALSE );
            j += _40;
            if ( i == ( _1 ) )
            {
                j += _40;
            }
            writeText( j + 7, _540 - konst - i * height + 5,
                       String.valueOf( duoTeamList.get( i ).getResult().getResultFight2() ), BOOLEAN_FALSE );
            writeText( j + 27, _540 - konst - i * height + 5,
                       String.valueOf( duoTeamList.get( i ).getResult().getUbFight2() ), BOOLEAN_FALSE );
            j += _40;
            if ( i == ( _2 ) )
            {
                j += _40;
            }
            writeText( j + 7, _540 - konst - i * height + 5,
                       String.valueOf( duoTeamList.get( i ).getResult().getResultFight3() ), BOOLEAN_FALSE );
            writeText( j + 27, _540 - konst - i * height + 5,
                       String.valueOf( duoTeamList.get( i ).getResult().getUbFight3() ), BOOLEAN_FALSE );
            j += _40;
            if ( i == ( 3 ) )
            {
                j += _40;
            }
            writeText( j + 7, _540 - konst - i * height + 5,
                       String.valueOf( duoTeamList.get( i ).getResult().getResultFight4() ), BOOLEAN_FALSE );
            writeText( j + 27, _540 - konst - i * height + 5,
                       String.valueOf( duoTeamList.get( i ).getResult().getUbFight4() ), BOOLEAN_FALSE );
            j += _40;

            if ( i == ( 4 ) )
            {
                j += _40;
            }

            writeText( j + 15, _545 - konst - i * height,
                       String.valueOf( duoTeamList.get( i ).getResult().getWinCount() ), BOOLEAN_FALSE );
            j += _30;
            writeText( j + 15, _545 - konst - i * height,
                       String.valueOf( duoTeamList.get( i ).getResult().getUBCount() ), BOOLEAN_FALSE );
            j += _30;
            writeText( j + 15, _545 - konst - i * height, String.valueOf( duoTeamList.get( i ).getPlace() ),
                       BOOLEAN_FALSE );
            cb.stroke();
        }
    }

    /**
     * Erstellt die Tabelle mit den einzelnen Kampfpaarungen
     * 
     * @param konst gibt an in welcher Höhe die Tabelle plaziert wird
     * @param kindOfPool 0 fur Pool, 1 für DPool.PoolA, 2 für DPool.PoolB
     * @throws Exception
     */
    protected int createFightTable( int konst, int kindOfPool )
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
                    writeText( j + _10, _545 - konst - i * height, "" + FightNr[_10 * kindOfPool + _5 * m + i - _1],
                               BOOLEAN_FALSE );
                    j += _20;
                }
                cb.rectangle( j, _540 - konst - i * height, 90, height );
                if ( i == _0 )
                {
                    writeText( j + _45, _545 - konst - i * height, rbFighting.getString( "pdf.fightTable.fighterRed" ),
                               BOOLEAN_FALSE );
                }

                j += 90;

                cb.rectangle( j, _540 - konst - i * height, 90, height );
                if ( i == _0 )
                {
                    writeText( j + _45, _545 - konst - i * height,
                               rbFighting.getString( "pdf.fightTable.fighterBlue" ), BOOLEAN_FALSE );
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

    protected void fillFightTableFighting( int konst, Map<Integer, Fight> fightListMap )
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
                fight = ( fightListMap.get( Integer.valueOf( _5 * m + i + _1 ) ) );
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

    protected void fillFightTableNewa( int konst, Map<Integer, NewaFight> fightListMap )
        throws Exception
    {
        int height = _18;
        konst += height;
        NewaFight fight;

        for ( int m = _0; m < 2; m++ )
        {
            for ( int i = _0; i < _5; i++ )
            {
                int j = _24 + m * 290;
                j += _40;
                fight = ( fightListMap.get( Integer.valueOf( _5 * m + i + _1 ) ) );
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

    protected void fillFightTableDuo( int konst, Map<Integer, DuoFight> fightListMap )
        throws Exception
    {
        int height = _18;
        konst += height;
        DuoFight fight;

        for ( int m = _0; m < 2; m++ )
        {
            for ( int i = _0; i < _5; i++ )
            {
                int j = _24 + m * 290;
                j += _40;
                fight = ( fightListMap.get( Integer.valueOf( _5 * m + i + _1 ) ) );
                if ( fight != null )
                {

                    writeText( j + _45, _545 - konst - i * height, fight.getDuoTeamRed().getName() + " / "
                        + fight.getDuoTeamRed().getName2(), doBoldOutput( fight, _1 ), 8, 0 );
                    j += 90;
                    writeText( j + _45, _545 - konst - i * height, fight.getDuoTeamBlue().getName() + " / "
                        + fight.getDuoTeamBlue().getName2(), doBoldOutput( fight, _0 ), 8, 0 );
                    j += 90;
                    writeText( j + _10, _545 - konst - i * height, String.valueOf( fight.getPointsRedMax() ),
                               BOOLEAN_FALSE );
                    j += _20;
                    writeText( j + _10, _545 - konst - i * height, String.valueOf( fight.getPointsBlueMax() ),
                               BOOLEAN_FALSE );
                }
            }// end of for i
        }// end of for m
        cb.stroke();
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

    /* @param Fight FightObj
    * @param redWhite 1 für rot, 0 für weiss
    * @return liefert 1 für Sieger und 0 für Verlierer des Kampfes
    */
    private boolean doBoldOutput( NewaFight fight, int redWhite )
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

    /**
     * @param Fight FightObj
     * @param redWhite 1 für rot, 0 für weiss
     * @return liefert 1 für Sieger und 0 für Verlierer des Kampfes
     */
    private boolean doBoldOutput( DuoFight fight, int redWhite )
    {
        if ( redWhite == _1 && fight.getWinnerId() == fight.getTeamIdRed() )
        {
            return BOOLEAN_TRUE;
        }
        if ( redWhite == _0 && fight.getWinnerId() == fight.getTeamIdBlue() )
        {
            return BOOLEAN_TRUE;
        }
        return BOOLEAN_FALSE;
    }

    /**
     * erstellt die Baum für die Endkämpfe
     * 
     * @param x Koordinate
     * @param y Koordinate
     */
    protected void createFinals( int x, int y )
    {
        int height = _18; // konst

        cb.rectangle( x + _80, y, _0, -3 * height );
        cb.rectangle( x, y, _80, height );
        y -= 36;

        cb.rectangle( x + _80, y, _80, height );
        y -= 36;
        cb.rectangle( x, y, _80, height );
        y -= 31;

         y -= 31; // Finale
         cb.rectangle( x + _80, y, _0, -3 * height );
         cb.rectangle(x, y, _80, height);
         y -= 36;
         cb.rectangle( x + _80, y, _80, height );
         y -= 36;
         cb.rectangle(x, y, _80, height);
         y -= 36;
         
        cb.stroke();

        return;
    }

    protected void fillFinalsFighting( int x, int y,  FightingDoublePoolClass fightingclass, ResourceBundle rb_ )
        throws Exception
    {

        Fight halfFinalFight1 = fightingclass.getHalfFinalFight1();
        Fight halfFinalFight2 = fightingclass.getHalfFinalFight2();
        Fight finalFight = fightingclass.getFinalFight();
        // Finale
        writeText( x, y + 23, "A1", BOOLEAN_TRUE, _10 );

        if ( halfFinalFight1.getFighterRed() != null )
        {
            if ( halfFinalFight1.getWinnerId() == halfFinalFight1.getFighterIdRed() )
            {
                writeText( x + _40, y + 5, halfFinalFight1.getFighterRed().getName(), BOOLEAN_TRUE );
            }
            else
            {
                writeText( x + _40, y + 5, halfFinalFight1.getFighterRed().getName(), BOOLEAN_FALSE );
            }
        }
        y -= 36;
        if ( finalFight.getWinnerId() != TypeUtil.LONG_MIN )
        {
            if ( finalFight.getWinnerId() == finalFight.getFighterIdRed() )
            {
                writeText( x + 120, y + 5, finalFight.getFighterRed().getName(), BOOLEAN_TRUE );
                writeText( x + 190, y + 5, rb_.getString( "pdf.1stPlace" ), BOOLEAN_FALSE );
            }
            else
            {
                writeText( x + 120, y + 5, finalFight.getFighterRed().getName(), BOOLEAN_FALSE );
            }
        }


        y -= 36;
        writeText( x, y + 23, "B2", BOOLEAN_TRUE, _10 );
        if ( halfFinalFight1.getFighterBlue() != null )
        {
            if ( halfFinalFight1.getWinnerId() == halfFinalFight1.getFighterIdBlue() )
            {
                writeText( x + _40, y + 5, halfFinalFight1.getFighterBlue().getName(), BOOLEAN_TRUE );
            }
            else
            {
                writeText( x + _40, y + 5, halfFinalFight1.getFighterBlue().getName(), BOOLEAN_FALSE );
            }
            
        }
        y -= 31;

        y -= 31;
    
         writeText(x,y+23,"B1",BOOLEAN_TRUE,_10);
         if ( halfFinalFight2.getFighterRed() != null )
         {
             if ( halfFinalFight2.getWinnerId() == halfFinalFight2.getFighterIdRed() )
             {
                 writeText( x + _40, y + 5, halfFinalFight2.getFighterRed().getName(), BOOLEAN_TRUE );
             }
             else
             {
                 writeText( x + _40, y + 5, halfFinalFight2.getFighterRed().getName(), BOOLEAN_FALSE );
             }
         }
        
         y-=36;
        
         if ( finalFight.getWinnerId() != TypeUtil.LONG_MIN )
         {
             if ( finalFight.getWinnerId() == finalFight.getFighterIdBlue() )
             {
                 writeText( x + 120, y + 5, finalFight.getFighterBlue().getName(), BOOLEAN_TRUE );
                 writeText( x + 190, y + 5, rb_.getString( "pdf.1stPlace" ), BOOLEAN_FALSE );
             }
             else
             {
                 writeText( x + 120, y + 5, finalFight.getFighterBlue().getName(), BOOLEAN_FALSE );
             }
         }
        
       
         y-=36;
         writeText(x,y+23,"A2",BOOLEAN_TRUE,_10);
         if ( halfFinalFight2.getFighterBlue() != null )
         {
             if ( halfFinalFight2.getWinnerId() == halfFinalFight2.getFighterIdBlue() )
             {
                 writeText( x + _40, y + 5, halfFinalFight2.getFighterBlue().getName(), BOOLEAN_TRUE );
             }
             else
             {
                 writeText( x + _40, y + 5, halfFinalFight2.getFighterBlue().getName(), BOOLEAN_FALSE );
             }
             
         }
        
         y-=36;

        cb.stroke();
        return;
    }

    protected void fillFinalsNewa( int x, int y, NewaDoublePoolClass newaclass, ResourceBundle rb_ )
        throws Exception
    {

        NewaFight halfFinalFight1 = newaclass.getHalfFinalFight1();
        NewaFight halfFinalFight2 = newaclass.getHalfFinalFight2();
        NewaFight finalFight = newaclass.getFinalFight();
        // Finale
        writeText( x, y + 23, "A1", BOOLEAN_TRUE, _10 );

        if ( halfFinalFight1.getFighterRed() != null )
        {
            if ( halfFinalFight1.getWinnerId() == halfFinalFight1.getFighterIdRed() )
            {
                writeText( x + _40, y + 5, halfFinalFight1.getFighterRed().getName(), BOOLEAN_TRUE );
            }
            else
            {
                writeText( x + _40, y + 5, halfFinalFight1.getFighterRed().getName(), BOOLEAN_FALSE );
            }
        }
        y -= 36;
        if ( finalFight.getWinnerId() != TypeUtil.LONG_MIN )
        {
            if ( finalFight.getWinnerId() == finalFight.getFighterIdRed() )
            {
                writeText( x + 120, y + 5, finalFight.getFighterRed().getName(), BOOLEAN_TRUE );
                writeText( x + 190, y + 5, rb_.getString( "pdf.1stPlace" ), BOOLEAN_FALSE );
            }
            else
            {
                writeText( x + 120, y + 5, finalFight.getFighterRed().getName(), BOOLEAN_FALSE );
            }
        }

        y -= 36;
        writeText( x, y + 23, "B2", BOOLEAN_TRUE, _10 );
        if ( halfFinalFight1.getFighterBlue() != null )
        {
            if ( halfFinalFight1.getWinnerId() == halfFinalFight1.getFighterIdBlue() )
            {
                writeText( x + _40, y + 5, halfFinalFight1.getFighterBlue().getName(), BOOLEAN_TRUE );
            }
            else
            {
                writeText( x + _40, y + 5, halfFinalFight1.getFighterBlue().getName(), BOOLEAN_FALSE );
            }

        }
        y -= 31;

        y -= 31;

        writeText( x, y + 23, "B1", BOOLEAN_TRUE, _10 );
        if ( halfFinalFight2.getFighterRed() != null )
        {
            if ( halfFinalFight2.getWinnerId() == halfFinalFight2.getFighterIdRed() )
            {
                writeText( x + _40, y + 5, halfFinalFight2.getFighterRed().getName(), BOOLEAN_TRUE );
            }
            else
            {
                writeText( x + _40, y + 5, halfFinalFight2.getFighterRed().getName(), BOOLEAN_FALSE );
            }
        }

        y -= 36;

        if ( finalFight.getWinnerId() != TypeUtil.LONG_MIN )
        {
            if ( finalFight.getWinnerId() == finalFight.getFighterIdBlue() )
            {
                writeText( x + 120, y + 5, finalFight.getFighterBlue().getName(), BOOLEAN_TRUE );
                writeText( x + 190, y + 5, rb_.getString( "pdf.1stPlace" ), BOOLEAN_FALSE );
            }
            else
            {
                writeText( x + 120, y + 5, finalFight.getFighterBlue().getName(), BOOLEAN_FALSE );
            }
        }

        y -= 36;
        writeText( x, y + 23, "A2", BOOLEAN_TRUE, _10 );
        if ( halfFinalFight2.getFighterBlue() != null )
        {
            if ( halfFinalFight2.getWinnerId() == halfFinalFight2.getFighterIdBlue() )
            {
                writeText( x + _40, y + 5, halfFinalFight2.getFighterBlue().getName(), BOOLEAN_TRUE );
            }
            else
            {
                writeText( x + _40, y + 5, halfFinalFight2.getFighterBlue().getName(), BOOLEAN_FALSE );
            }

        }

        y -= 36;

        cb.stroke();
        return;
    }

    protected void fillFinalsDuo( int x, int y, DuoDoublePoolClass duoclass , ResourceBundle rb_ )
        throws Exception
    {

        DuoFight halfFinalFight1 = duoclass.getHalfFinalFight1();
        DuoFight halfFinalFight2 = duoclass.getHalfFinalFight2();
        DuoFight finalFight = duoclass.getFinalFight();
        // Finale
        writeText( x, y + 23, "A1", BOOLEAN_TRUE, _10 );

        if ( halfFinalFight1.getDuoTeamRed() != null )
        {
            if ( halfFinalFight1.getWinnerId() == halfFinalFight1.getTeamIdRed() )
            {
                writeText( x + _40, y + 5, halfFinalFight1.getDuoTeamRed().getName() + " / "+ halfFinalFight1.getDuoTeamRed().getName2(), BOOLEAN_TRUE );
            }
            else
            {
                writeText( x + _40, y + 5, halfFinalFight1.getDuoTeamRed().getName() + " / "+ halfFinalFight1.getDuoTeamRed().getName2(), BOOLEAN_FALSE );
            }
        }
        y -= 36;
        if ( finalFight.getWinnerId() != TypeUtil.LONG_MIN )
        {
            if ( finalFight.getWinnerId() == finalFight.getTeamIdRed() )
            {
                writeText( x + 120, y + 5, finalFight.getDuoTeamRed().getName() + " / "+ finalFight.getDuoTeamRed().getName2(), BOOLEAN_TRUE );
                writeText( x + 190, y + 5, rb_.getString( "pdf.1stPlace" ), BOOLEAN_FALSE );
            }
            else
            {
                writeText( x + 120, y + 5, finalFight.getDuoTeamRed().getName()+ " / "+ finalFight.getDuoTeamRed().getName2(), BOOLEAN_FALSE );
            }
        }

             
        y -= 36;
        writeText( x, y + 23, "B2", BOOLEAN_TRUE, _10 );
        if ( halfFinalFight1.getDuoTeamBlue() != null )
        {
            if ( halfFinalFight1.getWinnerId() == halfFinalFight1.getTeamIdBlue() )
            {
                writeText( x + _40, y + 5, halfFinalFight1.getDuoTeamBlue().getName() + " / "+ halfFinalFight1.getDuoTeamBlue().getName2(), BOOLEAN_TRUE );
            }
            else
            {
                writeText( x + _40, y + 5, halfFinalFight1.getDuoTeamBlue().getName() + " / "+ halfFinalFight1.getDuoTeamBlue().getName2(), BOOLEAN_FALSE );
            }
            
        }
        y -= 31;

        y -= 31;
        
        writeText(x,y+23,"B1",BOOLEAN_TRUE,_10);
        if ( halfFinalFight2.getDuoTeamRed() != null )
        {
            if ( halfFinalFight2.getWinnerId() == halfFinalFight2.getTeamIdRed() )
            {
                writeText( x + _40, y + 5, halfFinalFight2.getDuoTeamRed().getName()+ " / "+ halfFinalFight2.getDuoTeamRed().getName2(), BOOLEAN_TRUE );
            }
            else
            {
                writeText( x + _40, y + 5, halfFinalFight2.getDuoTeamRed().getName() + " / "+ halfFinalFight2.getDuoTeamRed().getName2(), BOOLEAN_FALSE );
            }
        }
       
        y-=36;
       
        if ( finalFight.getWinnerId() != TypeUtil.LONG_MIN )
        {
            if ( finalFight.getWinnerId() == finalFight.getTeamIdBlue() )
            {
                writeText( x + 120, y + 5, finalFight.getDuoTeamBlue().getName() + " / "+ finalFight.getDuoTeamBlue().getName2(), BOOLEAN_TRUE );
                writeText( x + 190, y + 5, rb_.getString( "pdf.1stPlace" ), BOOLEAN_FALSE );
            }
            else
            {
                writeText( x + 120, y + 5, finalFight.getDuoTeamBlue().getName() + " / "+ finalFight.getDuoTeamBlue().getName2(), BOOLEAN_FALSE );
            }
        }
       
      
        y-=36;
        writeText(x,y+23,"A2",BOOLEAN_TRUE,_10);
        if ( halfFinalFight2.getDuoTeamBlue() != null )
        {
            if ( halfFinalFight2.getWinnerId() == halfFinalFight2.getTeamIdBlue() )
            {
                writeText( x + _40, y + 5, halfFinalFight2.getDuoTeamBlue().getName() + " / "+ halfFinalFight2.getDuoTeamBlue().getName2(), BOOLEAN_TRUE );
            }
            else
            {
                writeText( x + _40, y + 5, halfFinalFight2.getDuoTeamBlue().getName() + " / "+ halfFinalFight2.getDuoTeamBlue().getName2(), BOOLEAN_FALSE );
            }
            
        }
       
        y-=36;

       cb.stroke();
       return;
    }
}
