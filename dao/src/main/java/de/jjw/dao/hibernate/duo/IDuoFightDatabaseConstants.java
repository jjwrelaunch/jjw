/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : IDuoFightDatabaseConstants.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:46
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

package de.jjw.dao.hibernate.duo;

import de.jjw.dao.hibernate.IBaseDatabaseConstants;

public interface IDuoFightDatabaseConstants
    extends IBaseDatabaseConstants
{
    String TABLE_FRIENDLY_DUO_FIGHT = "FriendlyDuoFight";
    String TABLE_DUO_FIGHT = "DuoFight";
    String SQL_TABLE_DUO_FIGHT = "duo_fight_list";
    String TEAM_ID_RED = "teamIdRed";
    String TEAM_ID_BLUE = "teamIdBlue";
    String DUOCLASS_ID = "duoclassId";
    String DUOCLASS = "duoclass";
    String POINTS_RED = "pointsRed";
    String POINTS_BLUE = "pointsBlue";
    String POINTS_RED_MAX = "pointsRedMax";
    String POINTS_BLUE_MAX = "pointsBlueMax";
    String WINNER_ID = "winnerId";
    String FLAG = "flag";
    String MAIN_ROUND = "mainRound";

    String FIGHT_TIME = "fightTime";
    String INJURY_TIME_RED = "injuryTimeRed";
    String INJURY_TIME_BLUE = "injuryTimeBlue";
    String FIGHT_TIME_WITH_BREAKS = "fightTimeWithBreaks";
    String OVERALL_FIGHT_TIME = "overallFightTime";
    String MODIFIED_WITH_SECOND_CALL = "modifiedWithSecondCall";

    String PROTOKOLL = "protokoll";
    String KIKENGACHI = "kikengachi";
    String FUSENGACHI = "fusengachi";

}
