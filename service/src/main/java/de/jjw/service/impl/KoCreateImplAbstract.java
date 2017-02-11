/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : KoCreateImplAbstract.java
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

package de.jjw.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import de.jjw.model.ITechnicalDBFields;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.util.TypeUtil;

public abstract class KoCreateImplAbstract
{

    public static final int SIZE_KO_128 = 128;

    public static final int SIZE_KO_64 = 64;

    public static final int SIZE_KO_32 = 32;

    public static final int SIZE_KO_16 = 16;

    protected static final String ROUND2 = "round2";

    protected static final String ROUND3 = "round3";

    protected static final String ROUND4 = "round4";

    protected static final String ROUND5 = "round5";

    protected static final String ROUND6 = "round6";

    protected static final String ROUND7 = "round7";

    protected static final String ROUND8 = "round8";

    protected static final String LOSERROUND1 = "sround1";

    protected static final String LOSERROUND2 = "sround2";

    protected static final String LOSERROUND3 = "sround3";

    protected static final String LOSERROUND4 = "sround4";

    protected static final String LOSERROUND5 = "sround5";

    protected static final String LOSERROUND6 = "sround6";

    protected static final String LOSERROUND7 = "sround7";

    protected static final String LOSERROUND8 = "sround8";

    protected static Map<Integer, Integer> POSITION_MAP_16 = new HashMap<Integer, Integer>();

    protected static Map<Integer, Integer> POSITION_MAP_32 = new HashMap<Integer, Integer>();

    protected static Map<Integer, Integer> POSITION_MAP_64 = new HashMap<Integer, Integer>();

    protected static Map<Integer, Integer> FIGHT_NUMER_MAIN_MAP = new HashMap<Integer, Integer>();

    protected static Map<Integer, Integer> LOSER_ROUND_FIGHT_CREATE_MAP = new HashMap<Integer, Integer>();

    static
    {
        POSITION_MAP_16.put( Integer.valueOf( 0 ), Integer.valueOf( 3 ) );
        POSITION_MAP_16.put( Integer.valueOf( 1 ), Integer.valueOf( 5 ) );
        POSITION_MAP_16.put( Integer.valueOf( 2 ), Integer.valueOf( 4 ) );
        POSITION_MAP_16.put( Integer.valueOf( 3 ), Integer.valueOf( 6 ) );

        POSITION_MAP_32.put( Integer.valueOf( 0 ), Integer.valueOf( 7 ) );
        POSITION_MAP_32.put( Integer.valueOf( 1 ), Integer.valueOf( 11 ) );
        POSITION_MAP_32.put( Integer.valueOf( 2 ), Integer.valueOf( 9 ) );
        POSITION_MAP_32.put( Integer.valueOf( 3 ), Integer.valueOf( 13 ) );
        POSITION_MAP_32.put( Integer.valueOf( 4 ), Integer.valueOf( 8 ) );
        POSITION_MAP_32.put( Integer.valueOf( 5 ), Integer.valueOf( 12 ) );
        POSITION_MAP_32.put( Integer.valueOf( 6 ), Integer.valueOf( 10 ) );
        POSITION_MAP_32.put( Integer.valueOf( 7 ), Integer.valueOf( 14 ) );

        POSITION_MAP_64.put( Integer.valueOf( 0 ), Integer.valueOf( 15 ) );
        POSITION_MAP_64.put( Integer.valueOf( 1 ), Integer.valueOf( 23 ) );
        POSITION_MAP_64.put( Integer.valueOf( 2 ), Integer.valueOf( 19 ) );
        POSITION_MAP_64.put( Integer.valueOf( 3 ), Integer.valueOf( 27 ) );
        POSITION_MAP_64.put( Integer.valueOf( 4 ), Integer.valueOf( 17 ) );
        POSITION_MAP_64.put( Integer.valueOf( 5 ), Integer.valueOf( 25 ) );
        POSITION_MAP_64.put( Integer.valueOf( 6 ), Integer.valueOf( 21 ) );
        POSITION_MAP_64.put( Integer.valueOf( 7 ), Integer.valueOf( 29 ) );

        POSITION_MAP_64.put( Integer.valueOf( 8 ), Integer.valueOf( 16 ) );
        POSITION_MAP_64.put( Integer.valueOf( 9 ), Integer.valueOf( 24 ) );
        POSITION_MAP_64.put( Integer.valueOf( 10 ), Integer.valueOf( 20 ) );
        POSITION_MAP_64.put( Integer.valueOf( 11 ), Integer.valueOf( 28 ) );
        POSITION_MAP_64.put( Integer.valueOf( 12 ), Integer.valueOf( 18 ) );
        POSITION_MAP_64.put( Integer.valueOf( 13 ), Integer.valueOf( 26 ) );
        POSITION_MAP_64.put( Integer.valueOf( 14 ), Integer.valueOf( 22 ) );
        POSITION_MAP_64.put( Integer.valueOf( 15 ), Integer.valueOf( 30 ) );

        FIGHT_NUMER_MAIN_MAP.put( Integer.valueOf( 3 ), Integer.valueOf( 1 ) );
        FIGHT_NUMER_MAIN_MAP.put( Integer.valueOf( 4 ), Integer.valueOf( 2 ) );

        FIGHT_NUMER_MAIN_MAP.put( Integer.valueOf( 5 ), Integer.valueOf( 3 ) );
        FIGHT_NUMER_MAIN_MAP.put( Integer.valueOf( 6 ), Integer.valueOf( 4 ) );
        FIGHT_NUMER_MAIN_MAP.put( Integer.valueOf( 7 ), Integer.valueOf( 5 ) );
        FIGHT_NUMER_MAIN_MAP.put( Integer.valueOf( 8 ), Integer.valueOf( 6 ) );

        // ko32
        FIGHT_NUMER_MAIN_MAP.put( Integer.valueOf( 9 ), Integer.valueOf( 7 ) );
        FIGHT_NUMER_MAIN_MAP.put( Integer.valueOf( 10 ), Integer.valueOf( 8 ) );
        FIGHT_NUMER_MAIN_MAP.put( Integer.valueOf( 11 ), Integer.valueOf( 9 ) );
        FIGHT_NUMER_MAIN_MAP.put( Integer.valueOf( 12 ), Integer.valueOf( 10 ) );
        FIGHT_NUMER_MAIN_MAP.put( Integer.valueOf( 13 ), Integer.valueOf( 11 ) );
        FIGHT_NUMER_MAIN_MAP.put( Integer.valueOf( 14 ), Integer.valueOf( 12 ) );
        FIGHT_NUMER_MAIN_MAP.put( Integer.valueOf( 15 ), Integer.valueOf( 13 ) );
        FIGHT_NUMER_MAIN_MAP.put( Integer.valueOf( 16 ), Integer.valueOf( 14 ) );

        // ko64
        FIGHT_NUMER_MAIN_MAP.put( Integer.valueOf( 17 ), Integer.valueOf( 15 ) );
        FIGHT_NUMER_MAIN_MAP.put( Integer.valueOf( 18 ), Integer.valueOf( 16 ) );
        FIGHT_NUMER_MAIN_MAP.put( Integer.valueOf( 19 ), Integer.valueOf( 17 ) );
        FIGHT_NUMER_MAIN_MAP.put( Integer.valueOf( 20 ), Integer.valueOf( 18 ) );
        FIGHT_NUMER_MAIN_MAP.put( Integer.valueOf( 21 ), Integer.valueOf( 19 ) );
        FIGHT_NUMER_MAIN_MAP.put( Integer.valueOf( 22 ), Integer.valueOf( 20 ) );
        FIGHT_NUMER_MAIN_MAP.put( Integer.valueOf( 23 ), Integer.valueOf( 21 ) );
        FIGHT_NUMER_MAIN_MAP.put( Integer.valueOf( 24 ), Integer.valueOf( 22 ) );
        FIGHT_NUMER_MAIN_MAP.put( Integer.valueOf( 25 ), Integer.valueOf( 23 ) );
        FIGHT_NUMER_MAIN_MAP.put( Integer.valueOf( 26 ), Integer.valueOf( 24 ) );
        FIGHT_NUMER_MAIN_MAP.put( Integer.valueOf( 27 ), Integer.valueOf( 25 ) );
        FIGHT_NUMER_MAIN_MAP.put( Integer.valueOf( 28 ), Integer.valueOf( 26 ) );
        FIGHT_NUMER_MAIN_MAP.put( Integer.valueOf( 29 ), Integer.valueOf( 27 ) );
        FIGHT_NUMER_MAIN_MAP.put( Integer.valueOf( 30 ), Integer.valueOf( 28 ) );
        FIGHT_NUMER_MAIN_MAP.put( Integer.valueOf( 31 ), Integer.valueOf( 29 ) );
        FIGHT_NUMER_MAIN_MAP.put( Integer.valueOf( 32 ), Integer.valueOf( 30 ) );

        // for Ko16
        LOSER_ROUND_FIGHT_CREATE_MAP.put( Integer.valueOf( 4 ), Integer.valueOf( 2 ) );
        LOSER_ROUND_FIGHT_CREATE_MAP.put( Integer.valueOf( 5 ), Integer.valueOf( 3 ) );
        LOSER_ROUND_FIGHT_CREATE_MAP.put( Integer.valueOf( 6 ), Integer.valueOf( 4 ) );
        LOSER_ROUND_FIGHT_CREATE_MAP.put( Integer.valueOf( 7 ), Integer.valueOf( 5 ) );
        LOSER_ROUND_FIGHT_CREATE_MAP.put( Integer.valueOf( 8 ), Integer.valueOf( 6 ) );

        // for Ko32
        LOSER_ROUND_FIGHT_CREATE_MAP.put( Integer.valueOf( 9 ), Integer.valueOf( 8 ) );
        LOSER_ROUND_FIGHT_CREATE_MAP.put( Integer.valueOf( 10 ), Integer.valueOf( 9 ) );
        LOSER_ROUND_FIGHT_CREATE_MAP.put( Integer.valueOf( 11 ), Integer.valueOf( 10 ) );
        LOSER_ROUND_FIGHT_CREATE_MAP.put( Integer.valueOf( 12 ), Integer.valueOf( 11 ) );
        LOSER_ROUND_FIGHT_CREATE_MAP.put( Integer.valueOf( 13 ), Integer.valueOf( 12 ) );
        LOSER_ROUND_FIGHT_CREATE_MAP.put( Integer.valueOf( 14 ), Integer.valueOf( 13 ) );
        LOSER_ROUND_FIGHT_CREATE_MAP.put( Integer.valueOf( 15 ), Integer.valueOf( 14 ) );
        LOSER_ROUND_FIGHT_CREATE_MAP.put( Integer.valueOf( 16 ), Integer.valueOf( 15 ) );

        // for Ko64
        LOSER_ROUND_FIGHT_CREATE_MAP.put( Integer.valueOf( 17 ), Integer.valueOf( 18 ) );
        LOSER_ROUND_FIGHT_CREATE_MAP.put( Integer.valueOf( 18 ), Integer.valueOf( 19 ) );
        LOSER_ROUND_FIGHT_CREATE_MAP.put( Integer.valueOf( 19 ), Integer.valueOf( 20 ) );
        LOSER_ROUND_FIGHT_CREATE_MAP.put( Integer.valueOf( 20 ), Integer.valueOf( 21 ) );
        LOSER_ROUND_FIGHT_CREATE_MAP.put( Integer.valueOf( 21 ), Integer.valueOf( 22 ) );
        LOSER_ROUND_FIGHT_CREATE_MAP.put( Integer.valueOf( 22 ), Integer.valueOf( 23 ) );
        LOSER_ROUND_FIGHT_CREATE_MAP.put( Integer.valueOf( 23 ), Integer.valueOf( 24 ) );
        LOSER_ROUND_FIGHT_CREATE_MAP.put( Integer.valueOf( 24 ), Integer.valueOf( 25 ) );
        LOSER_ROUND_FIGHT_CREATE_MAP.put( Integer.valueOf( 25 ), Integer.valueOf( 26 ) );
        LOSER_ROUND_FIGHT_CREATE_MAP.put( Integer.valueOf( 26 ), Integer.valueOf( 27 ) );
        LOSER_ROUND_FIGHT_CREATE_MAP.put( Integer.valueOf( 27 ), Integer.valueOf( 28 ) );
        LOSER_ROUND_FIGHT_CREATE_MAP.put( Integer.valueOf( 28 ), Integer.valueOf( 29 ) );
        LOSER_ROUND_FIGHT_CREATE_MAP.put( Integer.valueOf( 29 ), Integer.valueOf( 30 ) );
        LOSER_ROUND_FIGHT_CREATE_MAP.put( Integer.valueOf( 30 ), Integer.valueOf( 31 ) );
        LOSER_ROUND_FIGHT_CREATE_MAP.put( Integer.valueOf( 31 ), Integer.valueOf( 32 ) );
        LOSER_ROUND_FIGHT_CREATE_MAP.put( Integer.valueOf( 32 ), Integer.valueOf( 33 ) );

    }

    protected static int getMainRoundPosition( int positionOfFighter, int poolSize )
    {

        switch ( poolSize )
        {

            case SIZE_KO_16:
                return POSITION_MAP_16.get( Integer.valueOf( positionOfFighter ) ).intValue();

            case SIZE_KO_32:
                return POSITION_MAP_32.get( Integer.valueOf( positionOfFighter ) ).intValue();

            case SIZE_KO_64:
                return POSITION_MAP_64.get( Integer.valueOf( positionOfFighter ) ).intValue();

        }
        // TODO weitere KOs
        return TypeUtil.INT_MIN;
    }

    protected static Map MAIN_LOSER_FIGHT_MAP = new HashMap<Integer, Integer>();

    static
    {

        MAIN_LOSER_FIGHT_MAP.put( Integer.valueOf( 3 ), Integer.valueOf( 4 ) );
        MAIN_LOSER_FIGHT_MAP.put( Integer.valueOf( 4 ), Integer.valueOf( 4 ) );
        MAIN_LOSER_FIGHT_MAP.put( Integer.valueOf( 5 ), Integer.valueOf( 5 ) );
        MAIN_LOSER_FIGHT_MAP.put( Integer.valueOf( 6 ), Integer.valueOf( 5 ) );

        // KO 32
        MAIN_LOSER_FIGHT_MAP.put( Integer.valueOf( 7 ), Integer.valueOf( 10 ) );
        MAIN_LOSER_FIGHT_MAP.put( Integer.valueOf( 8 ), Integer.valueOf( 10 ) );
        MAIN_LOSER_FIGHT_MAP.put( Integer.valueOf( 9 ), Integer.valueOf( 11 ) );
        MAIN_LOSER_FIGHT_MAP.put( Integer.valueOf( 10 ), Integer.valueOf( 11 ) );
        MAIN_LOSER_FIGHT_MAP.put( Integer.valueOf( 11 ), Integer.valueOf( 12 ) );
        MAIN_LOSER_FIGHT_MAP.put( Integer.valueOf( 12 ), Integer.valueOf( 12 ) );
        MAIN_LOSER_FIGHT_MAP.put( Integer.valueOf( 13 ), Integer.valueOf( 13 ) );
        MAIN_LOSER_FIGHT_MAP.put( Integer.valueOf( 14 ), Integer.valueOf( 13 ) );

        // KO 64
        MAIN_LOSER_FIGHT_MAP.put( Integer.valueOf( 15 ), Integer.valueOf( 22 ) );
        MAIN_LOSER_FIGHT_MAP.put( Integer.valueOf( 16 ), Integer.valueOf( 22 ) );
        MAIN_LOSER_FIGHT_MAP.put( Integer.valueOf( 17 ), Integer.valueOf( 23 ) );
        MAIN_LOSER_FIGHT_MAP.put( Integer.valueOf( 18 ), Integer.valueOf( 23 ) );
        MAIN_LOSER_FIGHT_MAP.put( Integer.valueOf( 19 ), Integer.valueOf( 24 ) );
        MAIN_LOSER_FIGHT_MAP.put( Integer.valueOf( 20 ), Integer.valueOf( 24 ) );
        MAIN_LOSER_FIGHT_MAP.put( Integer.valueOf( 21 ), Integer.valueOf( 25 ) );
        MAIN_LOSER_FIGHT_MAP.put( Integer.valueOf( 22 ), Integer.valueOf( 25 ) );
        MAIN_LOSER_FIGHT_MAP.put( Integer.valueOf( 23 ), Integer.valueOf( 26 ) );
        MAIN_LOSER_FIGHT_MAP.put( Integer.valueOf( 24 ), Integer.valueOf( 26 ) );
        MAIN_LOSER_FIGHT_MAP.put( Integer.valueOf( 25 ), Integer.valueOf( 27 ) );
        MAIN_LOSER_FIGHT_MAP.put( Integer.valueOf( 26 ), Integer.valueOf( 27 ) );
        MAIN_LOSER_FIGHT_MAP.put( Integer.valueOf( 27 ), Integer.valueOf( 28 ) );
        MAIN_LOSER_FIGHT_MAP.put( Integer.valueOf( 28 ), Integer.valueOf( 28 ) );
        MAIN_LOSER_FIGHT_MAP.put( Integer.valueOf( 29 ), Integer.valueOf( 29 ) );
        MAIN_LOSER_FIGHT_MAP.put( Integer.valueOf( 30 ), Integer.valueOf( 29 ) );

        // TODO for Ko64 to Ko128
    }

    public static int getPoolsize( int fighters )
    {
        if ( fighters <= 2 )
        {
            return 2;
        }
        if ( ( fighters > 2 ) && ( fighters <= 4 ) )
        {
            return 4;
        }
        if ( ( fighters > 4 ) && ( fighters < 8 ) )
        {
            return 8;
        }
        if ( ( fighters >= 8 ) && ( fighters <= 16 ) )
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

    /**
     * get the Number of Fights in the mainRound without the final
     * 
     * @param numberOfFighter
     * @return
     */
    public static int getNumberOfFightsInMainRound( int numberOfFighter )
    {
        // if ((NumberOfFighter > 2) && (NumberOfFighter <= 4)) return 4;
        // if ((NumberOfFighter > 4) && (NumberOfFighter <= 8)) return 8;
        if ( ( numberOfFighter > 2 ) && ( numberOfFighter <= 16 ) )
        {
            return 14;
        }// 16;
        if ( ( numberOfFighter > 16 ) && ( numberOfFighter <= 32 ) )
        {
            return 30;
        }// 32;
        if ( ( numberOfFighter > 32 ) && ( numberOfFighter <= 64 ) )
        {
            return 62;
        }// 64;
        if ( ( numberOfFighter > 64 ) && ( numberOfFighter <= 128 ) )
        {
            return 126;
        }// 128;
        return -1;
    }

    protected static String getMainRoundFlag( int fightInPool )
    {
        if ( fightInPool == 0 )
        {
            return ROUND2;
        }
        if ( ( fightInPool > 0 ) && ( fightInPool <= 2 ) )
        {
            return ROUND3;
        }
        if ( ( fightInPool > 2 ) && ( fightInPool <= 6 ) )
        {
            return ROUND4;
        }
        if ( ( fightInPool > 6 ) && ( fightInPool <= 14 ) )
        {
            return ROUND5;
        }
        if ( ( fightInPool > 14 ) && ( fightInPool <= 30 ) )
        {
            return ROUND6;
        }
        if ( ( fightInPool > 30 ) && ( fightInPool <= 62 ) )
        {
            return ROUND7;
        }
        if ( ( fightInPool > 62 ) && ( fightInPool <= 126 ) )
        {
            return ROUND8;
        }

        return null;
    }

    protected static int getNumberOfFightsInALoserRound( int NumberOfFighter )
    {
        // if ((NumberOfFighter > 2) && (NumberOfFighter <= 4)) return 0;
        // if ((NumberOfFighter > 4) && (NumberOfFighter <= 8)) return 4;
        if ( ( NumberOfFighter > 2 ) && ( NumberOfFighter <= 16 ) )
        {
            return 10;
        }
        if ( ( NumberOfFighter > 16 ) && ( NumberOfFighter <= 32 ) )
        {
            return 26;
        }
        if ( ( NumberOfFighter > 32 ) && ( NumberOfFighter <= 64 ) )
        {
            return 58;
        }
        if ( ( NumberOfFighter > 64 ) && ( NumberOfFighter <= 128 ) )
        {
            return 84;
        }
        return -1;
    }

    protected static String getLoserRoundFlag( int fightInPool )
    {
        if ( fightInPool == 0 )
        {
            return LOSERROUND1;
        }
        if ( fightInPool == 1 )
        {
            return LOSERROUND2;
        }
        if ( ( fightInPool > 1 ) && ( fightInPool <= 3 ) )
        {
            return LOSERROUND3;
        }
        if ( ( fightInPool > 3 ) && ( fightInPool <= 5 ) )
        {
            return LOSERROUND4;
        }
        if ( ( fightInPool > 5 ) && ( fightInPool <= 9 ) )
        {
            return LOSERROUND5;
        }
        if ( ( fightInPool > 9 ) && ( fightInPool <= 13 ) )
        {
            return LOSERROUND6;
        }
        if ( ( fightInPool > 13 ) && ( fightInPool <= 32 ) )
        {
            return LOSERROUND7;
        }
        if ( ( fightInPool > 32 ) && ( fightInPool <= 64 ) )
        {
            return LOSERROUND8;
        }

        return null;
    }

    /**
     * set technical DB Fields
     * 
     * @param obj
     */
    protected void setTechnicalDBFieldsForCreate( ITechnicalDBFields obj, ServiceExchangeContext ctx )
    {
        obj.setCreateDate( new Timestamp( System.currentTimeMillis() ) );
        obj.setCreateId( ctx.getUserId() );
        obj.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
        obj.setModificationId( ctx.getUserId() );
    }

    /**
     * set technical DB Fields
     * 
     * @param obj
     */
    protected void setTechnicalDBFieldsForUpdate( ITechnicalDBFields obj, ServiceExchangeContext ctx )
    {
        obj.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
        obj.setModificationId( ctx.getUserId() );
    }

}
