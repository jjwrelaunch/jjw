/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : KoImplAbstract.java
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

package de.jjw.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import de.jjw.model.ITechnicalDBFields;
import de.jjw.service.impl.duo.DuoclassKoCreateImpl;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;

public abstract class KoImplAbstract
    implements IValueConstants
{

    protected static final int SIZE_KO_128 = 128;

    protected static final int SIZE_KO_64 = 64;

    protected static final int SIZE_KO_32 = 32;

    protected static final int SIZE_KO_16 = 16;

    protected static Map<Integer, SolaceDTO> SOLACE_MA_16 = new HashMap<Integer, SolaceDTO>( 7 );

    protected static Map<Integer, SolaceDTO> SOLACE_MA_32 = new HashMap<Integer, SolaceDTO>();

    protected static Map<Integer, SolaceDTO> SOLACE_MA_64 = new HashMap<Integer, SolaceDTO>();

    protected static Map<Integer, SolaceDTO> SOLACE_NEXT_FIGHT_MAP = new HashMap<Integer, SolaceDTO>();

    static
    {
        //               FightNr in main           FightNr in loser
        SOLACE_MA_16.put( INTEGER_0, new SolaceDTO( INTEGER_0, false, FIGHTER_RED ) );
        SOLACE_MA_16.put( INTEGER_1, new SolaceDTO( INTEGER_2, true, FIGHTER_RED ) );
        SOLACE_MA_16.put( INTEGER_2, new SolaceDTO( INTEGER_3, true, FIGHTER_RED ) );

        SOLACE_MA_16.put( INTEGER_3, new SolaceDTO( INTEGER_4, false, FIGHTER_RED ) );
        SOLACE_MA_16.put( INTEGER_4, new SolaceDTO( INTEGER_4, false, FIGHTER_BLUE ) );
        SOLACE_MA_16.put( INTEGER_5, new SolaceDTO( INTEGER_5, false, FIGHTER_RED ) );
        SOLACE_MA_16.put( INTEGER_6, new SolaceDTO( INTEGER_5, false, FIGHTER_BLUE ) );

        SOLACE_MA_32.put( INTEGER_0, new SolaceDTO( INTEGER_0, false, FIGHTER_RED ) );

        SOLACE_MA_32.put( INTEGER_1, new SolaceDTO( INTEGER_2, false, FIGHTER_RED ) );
        SOLACE_MA_32.put( INTEGER_2, new SolaceDTO( INTEGER_3, false, FIGHTER_RED ) );

        SOLACE_MA_32.put( INTEGER_3, new SolaceDTO( INTEGER_8, false, FIGHTER_RED ) );
        SOLACE_MA_32.put( INTEGER_4, new SolaceDTO( INTEGER_9, false, FIGHTER_RED ) );
        SOLACE_MA_32.put( INTEGER_5, new SolaceDTO( INTEGER_6, false, FIGHTER_RED ) );
        SOLACE_MA_32.put( INTEGER_6, new SolaceDTO( INTEGER_7, false, FIGHTER_RED ) );

        SOLACE_MA_32.put( INTEGER_7, new SolaceDTO( INTEGER_10, false, FIGHTER_RED ) );
        SOLACE_MA_32.put( INTEGER_8, new SolaceDTO( INTEGER_10, false, FIGHTER_BLUE ) );
        SOLACE_MA_32.put( INTEGER_9, new SolaceDTO( INTEGER_11, false, FIGHTER_RED ) );
        SOLACE_MA_32.put( INTEGER_10, new SolaceDTO( INTEGER_11, false, FIGHTER_BLUE ) );
        SOLACE_MA_32.put( INTEGER_11, new SolaceDTO( INTEGER_12, false, FIGHTER_RED ) );
        SOLACE_MA_32.put( INTEGER_12, new SolaceDTO( INTEGER_12, false, FIGHTER_BLUE ) );
        SOLACE_MA_32.put( INTEGER_13, new SolaceDTO( INTEGER_13, false, FIGHTER_RED ) );
        SOLACE_MA_32.put( INTEGER_14, new SolaceDTO( INTEGER_13, false, FIGHTER_BLUE ) );

        SOLACE_MA_64.put( INTEGER_0, new SolaceDTO( INTEGER_0, false, FIGHTER_RED ) );

        SOLACE_MA_64.put( INTEGER_1, new SolaceDTO( INTEGER_2, false, FIGHTER_RED ) );
        SOLACE_MA_64.put( INTEGER_2, new SolaceDTO( INTEGER_3, false, FIGHTER_RED ) );

        SOLACE_MA_64.put( INTEGER_3, new SolaceDTO( INTEGER_8, false, FIGHTER_RED ) );
        SOLACE_MA_64.put( INTEGER_4, new SolaceDTO( INTEGER_9, false, FIGHTER_RED ) );
        SOLACE_MA_64.put( INTEGER_5, new SolaceDTO( INTEGER_6, false, FIGHTER_RED ) );
        SOLACE_MA_64.put( INTEGER_6, new SolaceDTO( INTEGER_7, false, FIGHTER_RED ) );

        SOLACE_MA_64.put( INTEGER_7, new SolaceDTO( INTEGER_14, true, FIGHTER_RED ) );
        SOLACE_MA_64.put( INTEGER_8, new SolaceDTO( INTEGER_15, true, FIGHTER_RED ) );
        SOLACE_MA_64.put( INTEGER_9, new SolaceDTO( INTEGER_16, true, FIGHTER_RED ) );
        SOLACE_MA_64.put( INTEGER_10, new SolaceDTO( INTEGER_17, true, FIGHTER_RED ) );
        SOLACE_MA_64.put( INTEGER_11, new SolaceDTO( INTEGER_18, true, FIGHTER_RED ) );
        SOLACE_MA_64.put( INTEGER_12, new SolaceDTO( INTEGER_19, true, FIGHTER_RED ) );
        SOLACE_MA_64.put( INTEGER_13, new SolaceDTO( INTEGER_20, true, FIGHTER_RED ) );
        SOLACE_MA_64.put( INTEGER_14, new SolaceDTO( INTEGER_21, true, FIGHTER_RED ) );

        SOLACE_MA_64.put( INTEGER_15, new SolaceDTO( INTEGER_22, false, FIGHTER_RED ) );
        SOLACE_MA_64.put( INTEGER_16, new SolaceDTO( INTEGER_22, false, FIGHTER_BLUE ) );
        SOLACE_MA_64.put( INTEGER_17, new SolaceDTO( INTEGER_23, false, FIGHTER_RED ) );
        SOLACE_MA_64.put( INTEGER_18, new SolaceDTO( INTEGER_23, false, FIGHTER_BLUE ) );
        SOLACE_MA_64.put( INTEGER_19, new SolaceDTO( INTEGER_24, false, FIGHTER_RED ) );
        SOLACE_MA_64.put( INTEGER_20, new SolaceDTO( INTEGER_24, false, FIGHTER_BLUE ) );
        SOLACE_MA_64.put( INTEGER_21, new SolaceDTO( INTEGER_25, false, FIGHTER_RED ) );
        SOLACE_MA_64.put( INTEGER_22, new SolaceDTO( INTEGER_25, false, FIGHTER_BLUE ) );
        SOLACE_MA_64.put( INTEGER_23, new SolaceDTO( INTEGER_26, false, FIGHTER_RED ) );
        SOLACE_MA_64.put( INTEGER_24, new SolaceDTO( INTEGER_26, false, FIGHTER_BLUE ) );
        SOLACE_MA_64.put( INTEGER_25, new SolaceDTO( INTEGER_27, false, FIGHTER_RED ) );
        SOLACE_MA_64.put( INTEGER_26, new SolaceDTO( INTEGER_27, false, FIGHTER_BLUE ) );
        SOLACE_MA_64.put( INTEGER_27, new SolaceDTO( INTEGER_28, false, FIGHTER_RED ) );
        SOLACE_MA_64.put( INTEGER_28, new SolaceDTO( INTEGER_28, false, FIGHTER_BLUE ) );
        SOLACE_MA_64.put( INTEGER_29, new SolaceDTO( INTEGER_29, false, FIGHTER_RED ) );
        SOLACE_MA_64.put( INTEGER_30, new SolaceDTO( INTEGER_29, false, FIGHTER_BLUE ) );

        // <actualFight,nextFight>
        SOLACE_NEXT_FIGHT_MAP.put( INTEGER_1, new SolaceDTO( INTEGER_0, false, FIGHTER_BLUE ) );
        SOLACE_NEXT_FIGHT_MAP.put( INTEGER_2, new SolaceDTO( INTEGER_1, false, FIGHTER_RED ) );
        SOLACE_NEXT_FIGHT_MAP.put( INTEGER_3, new SolaceDTO( INTEGER_1, false, FIGHTER_BLUE ) );
        SOLACE_NEXT_FIGHT_MAP.put( INTEGER_4, new SolaceDTO( INTEGER_2, false, FIGHTER_BLUE ) );
        SOLACE_NEXT_FIGHT_MAP.put( INTEGER_5, new SolaceDTO( INTEGER_3, false, FIGHTER_BLUE ) );

        // KO32
        SOLACE_NEXT_FIGHT_MAP.put( INTEGER_6, new SolaceDTO( INTEGER_4, false, FIGHTER_RED ) );
        SOLACE_NEXT_FIGHT_MAP.put( INTEGER_7, new SolaceDTO( INTEGER_4, false, FIGHTER_BLUE ) );
        SOLACE_NEXT_FIGHT_MAP.put( INTEGER_8, new SolaceDTO( INTEGER_5, false, FIGHTER_RED ) );
        SOLACE_NEXT_FIGHT_MAP.put( INTEGER_9, new SolaceDTO( INTEGER_5, false, FIGHTER_BLUE ) );

        SOLACE_NEXT_FIGHT_MAP.put( INTEGER_10, new SolaceDTO( INTEGER_6, false, FIGHTER_BLUE ) );
        SOLACE_NEXT_FIGHT_MAP.put( INTEGER_11, new SolaceDTO( INTEGER_7, false, FIGHTER_BLUE ) );
        SOLACE_NEXT_FIGHT_MAP.put( INTEGER_12, new SolaceDTO( INTEGER_8, false, FIGHTER_BLUE ) );
        SOLACE_NEXT_FIGHT_MAP.put( INTEGER_13, new SolaceDTO( INTEGER_9, false, FIGHTER_BLUE ) );

        // KO64
        SOLACE_NEXT_FIGHT_MAP.put( INTEGER_14, new SolaceDTO( INTEGER_10, false, FIGHTER_RED ) );
        SOLACE_NEXT_FIGHT_MAP.put( INTEGER_15, new SolaceDTO( INTEGER_10, false, FIGHTER_BLUE ) );
        SOLACE_NEXT_FIGHT_MAP.put( INTEGER_16, new SolaceDTO( INTEGER_11, false, FIGHTER_RED ) );
        SOLACE_NEXT_FIGHT_MAP.put( INTEGER_17, new SolaceDTO( INTEGER_11, false, FIGHTER_BLUE ) );
        SOLACE_NEXT_FIGHT_MAP.put( INTEGER_18, new SolaceDTO( INTEGER_12, false, FIGHTER_RED ) );
        SOLACE_NEXT_FIGHT_MAP.put( INTEGER_19, new SolaceDTO( INTEGER_12, false, FIGHTER_BLUE ) );
        SOLACE_NEXT_FIGHT_MAP.put( INTEGER_20, new SolaceDTO( INTEGER_13, false, FIGHTER_RED ) );
        SOLACE_NEXT_FIGHT_MAP.put( INTEGER_21, new SolaceDTO( INTEGER_13, false, FIGHTER_BLUE ) );

        SOLACE_NEXT_FIGHT_MAP.put( INTEGER_22, new SolaceDTO( INTEGER_14, false, FIGHTER_BLUE ) );
        SOLACE_NEXT_FIGHT_MAP.put( INTEGER_23, new SolaceDTO( INTEGER_15, false, FIGHTER_BLUE ) );
        SOLACE_NEXT_FIGHT_MAP.put( INTEGER_24, new SolaceDTO( INTEGER_16, false, FIGHTER_BLUE ) );
        SOLACE_NEXT_FIGHT_MAP.put( INTEGER_25, new SolaceDTO( INTEGER_17, false, FIGHTER_BLUE ) );
        SOLACE_NEXT_FIGHT_MAP.put( INTEGER_26, new SolaceDTO( INTEGER_18, false, FIGHTER_BLUE ) );
        SOLACE_NEXT_FIGHT_MAP.put( INTEGER_27, new SolaceDTO( INTEGER_19, false, FIGHTER_BLUE ) );
        SOLACE_NEXT_FIGHT_MAP.put( INTEGER_28, new SolaceDTO( INTEGER_20, false, FIGHTER_BLUE ) );
        SOLACE_NEXT_FIGHT_MAP.put( INTEGER_29, new SolaceDTO( INTEGER_21, false, FIGHTER_BLUE ) );

    }

    /**
     * check if the given fight is a firstRound fight
     *
     * @param numberOfFight
     * @param size
     * @return
     */
    protected static boolean isFirstRound( int fightNumber, int fighterCount )
    {

        int poolsize = DuoclassKoCreateImpl.getPoolsize( fighterCount );

        if ( fightNumber >= poolsize / 4 - 1 )
        {
            return true;
        }
        return false;
    }

    protected static int getPoolsize( int fighters )
    {
        if ( fighters <= 2 )
        {
            return 2;
        }
        if ( ( fighters > 2 ) && ( fighters <= 4 ) )
        {
            return 4;
        }
        if ( ( fighters > 4 ) && ( fighters <= 7 ) )
        {
            return 8;
        }
        if ( ( fighters > 7 ) && ( fighters <= 16 ) )
        {
            return SIZE_KO_16;
        }
        if ( ( fighters > 16 ) && ( fighters <= 32 ) )
        {
            return SIZE_KO_32;
        }
        if ( ( fighters > 32 ) && ( fighters <= 64 ) )
        {
            return SIZE_KO_64;
        }
        if ( ( fighters > 64 ) && ( fighters <= 128 ) )
        {
            return SIZE_KO_128;
        }
        return -1;
    }

    protected void setTechnicalDBFieldsForDummyFight( ITechnicalDBFields obj )
    {
        obj.setCreateDate( new Timestamp( System.currentTimeMillis() ) );
        obj.setCreateId( TypeUtil.LONG_OBJECT_EMPTY );
        obj.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
        obj.setModificationId( TypeUtil.LONG_OBJECT_EMPTY );
    }
}
