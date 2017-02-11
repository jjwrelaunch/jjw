/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : IFightDatabaseConstants.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:47
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

package de.jjw.dao.hibernate.fighting;

import de.jjw.dao.hibernate.IBaseDatabaseConstants;

public interface IFightDatabaseConstants
    extends IBaseDatabaseConstants
{

    String TABLE_FRIENDLY_FIGHT = "FriendlyFight";
    String TABLE_FIGHT = "Fight";
    String SQL_TABLE_FIGHT = "fight_list";
    String FIGHTER_ID_RED = "fighterIdRed";
    String FIGHTER_ID_BLUE = "fighterIdBlue";
    String FIGHTINGCLASS_ID = "fightingclassId";
    String FIGHTINGCLASS = "fightingclass";
    String POINTS_RED = "pointsRed";
    String POINTS_BLUE = "pointsBlue";
    String WINNER_ID = "winnerId";
    String FLAG = "flag";
    String IPPON_RED_I = "ipponRedI";
    String IPPON_RED_II = "ipponRedII";
    String IPPON_RED_III = "ipponRedIII";
    String IPPON_BLUE_I = "ipponBlueI";
    String IPPON_BLUE_II = "ipponBlueII";
    String IPPON_BLUE_III = "ipponBlueIII";
    String FIGHT_TIME = "fightTime";
    String INJURY_TIME_RED = "injuryTimeRed";
    String INJURY_TIME_BLUE = "injuryTimeBlue";
    String FIGHT_TIME_WITH_BREAKS = "fightTimeWithBreaks";
    String OVERALL_FIGHT_TIME = "overallFightTime";
    String MODIFIED_WITH_SECOND_CALL = "modifiedWithSecondCall";
    String SHIDO_RED = "shidoRed";
    String SHIDO_BLUE = "shidoBlue";
    String CHUI_RED = "chuiRed";
    String CHUI_BLUE = "chuiBlue";
    String HANSOKUMAKE_RED = "hansokumakeRed";
    String HANSOKUMAKE_BLUE = "hansokumakeBlue";
    String PROTOKOLL = "protokoll";
    String KIKENGACHI = "kikengachi";
    String FUSENGACHI = "fusengachi";
    String MAIN_ROUND = "mainRound";

    String SAVE_TIME = "saveTime";

}
