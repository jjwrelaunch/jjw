/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : IValueConstants.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:44
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

package de.jjw.util;

public interface IValueConstants
{

    long SYSTEM_USER = -1;

    boolean BOOLEAN_TRUE = true;

    boolean BOOLEAN_FALSE = false;

    long YEAR_2000_IN_MS = 946681200000l;

    int INT_MIN = Integer.MIN_VALUE;

    int INT_MAX = Integer.MAX_VALUE;

    int INT_DEFAULT = 0;

    int INT_EMPTY = Integer.MIN_VALUE;

    String INT_MIN_STRING = String.valueOf( Integer.MIN_VALUE );

    long LONG_MIN = Long.MIN_VALUE;

    long LONG_MAX = Long.MAX_VALUE;

    long LONG_DEFAULT = 0l;

    long LONG_EMPTY = Long.MIN_VALUE;

    byte BYTE_MIN = Byte.MIN_VALUE;

    byte BYTE_MAX = Byte.MAX_VALUE;

    byte BYTE_DEFAULT = 0;

    byte BYTE_EMPTY = Byte.MIN_VALUE;

    double DOUBLE_MIN = Double.MIN_VALUE;

    double DOUBLE_MAX = Double.MAX_VALUE;

    double DOUBLE_DEFAULT = 0.0;

    double DOUBLE_EMPTY = Double.MIN_VALUE;

    String DOUBLE_MIN_STRING = String.valueOf( Double.MIN_VALUE );

    String STRING_NULL = null;

    String STRING_EMPTY = null;

    String STRING_DEFAULT = "";

    String SPACE = " ";

    String SLASH_WITH_SPACE = " / ";

    String STRING_0 = "0";

    String STRING_1 = "1";

    String STRING_TRUE = "true";

    String STRING_FALSE = "false";

    Long LONG_OBJECT_DEFAULT = Long.valueOf( 0 );

    Long LONG_OBJECT_EMPTY = Long.valueOf( Long.MIN_VALUE );

    // db Login
    int MAX_LOGIN_FAILURE = 5;

    long LOCKED_TIME_AFTER_MAX_FAILURE = 1800000; // 30 min

    // db Modification Ids
    Long MODIFICATION_ID_WRONG_LOGIN = Long.valueOf( -1 );

    Long MODIFICATION_ID_LOCKED_TIME_FREE = Long.valueOf( -2 );

    Long MODIFICATION_ID_LOGIN = Long.valueOf( -3 );

    Integer INTEGER_0 = Integer.valueOf( 0 );

    Integer INTEGER_1 = Integer.valueOf( 1 );

    Integer INTEGER_2 = Integer.valueOf( 2 );

    Integer INTEGER_3 = Integer.valueOf( 3 );

    Integer INTEGER_4 = Integer.valueOf( 4 );

    Integer INTEGER_5 = Integer.valueOf( 5 );

    Integer INTEGER_6 = Integer.valueOf( 6 );

    Integer INTEGER_7 = Integer.valueOf( 7 );

    Integer INTEGER_8 = Integer.valueOf( 8 );

    Integer INTEGER_9 = Integer.valueOf( 9 );

    Integer INTEGER_10 = Integer.valueOf( 10 );

    Integer INTEGER_11 = Integer.valueOf( 11 );

    Integer INTEGER_12 = Integer.valueOf( 12 );

    Integer INTEGER_13 = Integer.valueOf( 13 );

    Integer INTEGER_14 = Integer.valueOf( 14 );

    Integer INTEGER_15 = Integer.valueOf( 15 );

    Integer INTEGER_16 = Integer.valueOf( 16 );

    Integer INTEGER_17 = Integer.valueOf( 17 );

    Integer INTEGER_18 = Integer.valueOf( 18 );

    Integer INTEGER_19 = Integer.valueOf( 19 );

    Integer INTEGER_20 = Integer.valueOf( 20 );

    Integer INTEGER_21 = Integer.valueOf( 21 );

    Integer INTEGER_22 = Integer.valueOf( 22 );

    Integer INTEGER_23 = Integer.valueOf( 23 );

    Integer INTEGER_24 = Integer.valueOf( 24 );

    Integer INTEGER_25 = Integer.valueOf( 25 );

    Integer INTEGER_26 = Integer.valueOf( 26 );

    Integer INTEGER_27 = Integer.valueOf( 27 );

    Integer INTEGER_28 = Integer.valueOf( 28 );

    Integer INTEGER_29 = Integer.valueOf( 29 );

    Integer INTEGER_30 = Integer.valueOf( 30 );

    Long LONG_1 = Long.valueOf( 1 );

    // User, Password
    int MIN_LENGHT_USERNAME = 8;

    int MAX_LENGHT_USERNAME = 20;

    int MIN_LENGHT_PASSWORD = 8;

    int MAX_LENGHT_PASSWORD = 20;

    int LEANGTH_0 = 0;

    int LEANGTH_10 = 10;

    int LEANGTH_25 = 25;

    int LEANGTH_30 = 30;

    int LEANGTH_50 = 50;

    // fihgtsystem
    int FIGHTSYTEM_MAX_PARTICANT = 128;

    // weightclass Max
    double MAX_WEIGHTCLASS = 1000.0;

    // fight
    int FIGHT_WINNER_RED = 1;

    int FIGHT_WINNER_BLUE = 2;

    String FINAL_FIGHT = "f";
    
    String HALF_FINAL_FIGHT_1 = "h1";
    
    String HALF_FINAL_FIGHT_2 = "h2";

    // dpool
    String POOL_A = "A";

    String POOL_B = "B";

    int FIGHTER_RED = 1;

    int FIGHTER_BLUE = 2;

    // numbers
    int _25 = 25;

    int _36 = 36;

    int _21 = 21;

    int _7 = 7;

    int _9 = 9;

    int _11 = 11;

    int _13 = 13;

    int _88 = 88;

    int _196 = 196;

    int _96 = 96;

    int _80 = 80;

    int _236 = 236;

    int _220 = 220;

    int _14 = 14;

    int _16 = 16;

    int _17 = 17;

    int _18 = 18;

    int _19 = 19;

    int _20 = 20;

    int _22 = 22;

    int _23 = 23;

    int _4 = 4;

    int _24 = 24;

    int _26 = 26;

    int _29 = 29;

    int _30 = 30;

    int _6 = 6;

    int _1 = 1;

    int _15 = 15;

    int _660 = 660;

    int _603 = 603;

    int _8 = 8;

    int _12 = 12;

    int _504 = 504;

    int _442 = 442;

    int _5 = 5;

    int _0 = 0;

    int _3 = 3;

    int _2 = 2;
    
    int _34 = 34;

    int _307 = 307;

    int _302 = 302;

    int _364 = 364;

    int _706 = 706;

    int _738 = 738;

    int _628 = 628;

    int _566 = 566;

    int _488 = 488;

    int _426 = 426;

    int _348 = 348;

    int _286 = 286;

    int _116 = 116;

    int _208 = 208;

    int _680 = 680;

    int _550 = 550;

    int _524 = 524;

    int _384 = 384;

    int _254 = 254;

    int _450 = 450;

    int _508 = 508;

    int _368 = 368;

    int _228 = 228;

    int _10 = 10;

    int _28 = 28;

    public static final int _290 = 290;

    public static final int _45 = 45;

    public static final int _90 = 90;

    public static final int _545 = 545;

    public static final int _540 = 540;

    public static final int _40 = 40;

    public static final int _190 = 190;

    public static final int _385 = 385;

    public static final int _445 = 445;

    public static final int _635 = 635;

    public static final int _595 = 595;

    public static final int _555 = 555;

    public static final int _650 = 650;

    public static final int _210 = 210;

    public static final int _240 = 240;

    public static final int _270 = 270;

    public static final int _300 = 300;

    public static final int _330 = 330;

    public static final int _360 = 360;

    public static final int _420 = 420;

    public static final int _480 = 480;

    public static final int _520 = 520;

    public static final int _140 = 140;

    public static final int _500 = 500;

    public static final int _310 = 310;

    public static final int _610 = 610;

    public static final int _570 = 570;

    public static final int _505 = 505;

    public static final int _60 = 60;

    public static final int _198 = 198;

    public static final int _180 = 180;

    public static final int _685 = 685;

    public static final int _56 = 56;

    int _27 = 27;

    int _128 = 128;

    int _320 = 320;

    int _400 = 400;

    int _294 = 294;

    int _134 = 134;

    int _214 = 214;

    int _108 = 108;

    int _101 = 101;

    int _739 = 739;

    int _176 = 176;

    int _664 = 664;

    int _256 = 256;

    int _584 = 584;

    int _336 = 336;

    int _674 = 674;

    int _804 = 804;

    int _200 = 200;

    int _70 = 70;

    int _130 = 130;

    int _624 = 624;

    int _136 = 136;

    int _704 = 704;

    int _120 = 120;

    int _288 = 288;

    int _552 = 552;

    int _632 = 632;

    int _712 = 712;

    int _812 = 812;

    int _448 = 448;

    int _363 = 363;

    int _262 = 262;

    int _177 = 177;

    int _182 = 182;

    int _76 = 76;
}
