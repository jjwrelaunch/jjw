/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : IDatabaseTableConstants.java
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

package de.jjw.dao;

public interface IDatabaseTableConstants
{

    String TABLE_AGE = "Age";
    String TABLE_ACCOUNT = "User";
    String TABLE_CODESTABLE = "Codestable";
    String TABLE_CONFIG = "ConfigJJW";
    String TABLE_COUNTRY = "Country";
    String TABLE_FIGHTER = "Fighter";
    String TABLE_FIGHT = "Fight";

    String TABLE_FRIENDLY_FIGHT = "FriendlyFight";

    String TABLE_FRIENDLY_NEWA_FIGHT = "FriendlyNewaFight";

    String TABLE_FRIENDLY_DUO_FIGHT = "FriendlyDuoFight";
    String TABLE_REGION = "Region";
    String TABLE_ROLE = "Role";
    String TABLE_TEAM = "Team";
    String TABLE_USER_ROLE = "user_role";
    String TABLE_WEIGHTCLASS = "Fightingclass";
    String TABLE_FIGHTINGCLASS = "Fightingclass";
    String TABLE_DUOCLASS = "Duoclass";
    String TABLE_DUOTEAM = "DuoTeam";
    String TABLE_DUO_FIGHT = "DuoFight";

    String TABLE_FIGHTING_POOL = "Fighting_Pool";

    String TABLE_NEWA_FIGHTER = "NewaFighter";

    String TABLE_NEWA_FIGHT = "NewaFight";

    String TABLE_NEWACLASS = "Newaclass";

    String TABLE_VIDEO = "Video";
    
    int ORDER_BY_NAME = 0;
    int ORDER_BY_FIRSTNAME = 1;
    int ORDER_BY_TEAM = 2;
    int ORDER_BY_AGE = 3;
    int ORDER_BY_SEX = 4;
    int ORDER_BY_REGION = 5;
    int ORDER_BY_COUNTRY = 6;
    int ORDER_BY_WEIGHT = 7;
    int ORDER_BY_READY = 8;
    int ORDER_BY_CLASS = 9;

    //DB Constants
    Long SYSTEM = 1l;
}
