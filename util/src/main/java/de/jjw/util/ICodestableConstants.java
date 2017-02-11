/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : ICodestableConstants.java
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

package de.jjw.util;

public interface ICodestableConstants
    extends ICodestableTypeConstants
{

    String GEN_NO_VALUE = "gen_0001";

    String GEN_VALUE_TO_LESS_CHARACTER = "gen_0002";

    String GEN_VALUE_TO_MANY_CHARACTER = "gen_0003";

    String GEN_VALUE_NO_VALID_INTEGER = "gen_0004";

    String GEN_VALUE_NO_VALID_DECIMAL = "gen_0005";

    String GEN_GERNERAL_ERROR = "gen_0006";

    String GEN_VALUE_NOT_NEGATIV = "gen_0007";

    String GEN_ACCESS_VIOLATION = "gen_0008";

    String GLO_OPTIMISTIC_LOCKING = "glo_optimistic_locking";

    String EXT_TEAM_LOGIN_WRONG_USER_OR_PASSWORD = "ext_team_logIn_0001";

    String EXT_TEAM_LOGIN_TOO_MUCH_WRONG_LOGIN = "ext_team_logIn_0002";

    String EXT_TEAM_LOGIN_LOCKED = "ext_team_logIn_0003";

    String EXT_TEAM_LOGIN_USER_OR_PASSWORD_TO_SHORT = "ext_team_logIn_0004";

    String EXT_TEAM_LOGIN_NO_USER = "ext_team_logIn_0005";

    String EXT_TEAM_LOGIN_NO_PASSWORD = "ext_team_logIn_0006";

    String EXT_TEAM_LOGIN_USER_TO_LONG = "ext_team_logIn_0007";

    String EXT_TEAM_LOGIN_PASSWORD_TO_LONG = "ext_team_logIn_0008";

    String ADMIN_COUNTRY_EXISTS = "admin_country_001";

    String ADMIN_COUNTRY_NO_DESCRIPTION = "admin_country_002";

    String ADMIN_COUNTRY_DESCRIPTION_TO_LONG = "admin_country_003";

    String ADMIN_COUNTRY_WRONG_ID = "admin_country_004";

    String ADMIN_COUNTRY_IN_USE = "admin_country_005";

    String ADMIN_REGION_EXISTS = "admin_region_001";

    String ADMIN_REGION_NO_DESCRIPTION = "admin_region_002";

    String ADMIN_REGION_DESCRIPTION_TO_LONG = "admin_region_003";

    String ADMIN_REGION_WRONG_ID = "admin_region_004";

    String ADMIN_REGION_IN_USE = "admin_region_005";
    
    String ADMIN_REGION_NO_COUNTRY = "admin_region_006";

    String ADMIN_AGE_EXISTS = "admin_age_001";

    String ADMIN_AGE_NO_DESCRIPTION = "admin_age_002";

    String ADMIN_AGE_DESCRIPTION_TO_LONG = "admin_age_003";

    String ADMIN_AGE_WRONG_ID = "admin_age_004";

    String ADMIN_AGE_IN_USE = "admin_age_005";

    String ADMIN_AGE_CONFICT_WITH_ANOTHER_AGE = "admin_age_006";

    // Fightsystem

    String ADMIN_FIGHTSYSTEM_ONLY_1_TO_5 = "admin_fightsystem_001";

    String ADMIN_FIGHTSYSTEM_ONLY_4_TO_10 = "admin_fightsystem_002";

    String ADMIN_FIGHTSYSTEM_ONLY_8_TO_128 = "admin_fightsystem_003";

    String ADMIN_FIGHTSYSTEM_MIN_GREATER_MAX = "admin_fightsystem_004";

    String ADMIN_FIGHTSYSTEM_MAX_POOL_MIN_DPOOL = "admin_fightsystem_005";

    String ADMIN_FIGHTSYSTEM_MAX_DPOOL_MIN_KO = "admin_fightsystem_006";

    // Team
    String ADMIN_TEAM_EXISTS = "admin_team_001";

    String ADMIN_TEAM_NO_DESCRIPTION = "admin_team_002";

    String ADMIN_TEAM_DESCRIPTION_TO_LONG = "admin_team_003";

    String ADMIN_TEAM_WRONG_ID = "admin_team_004";

    String ADMIN_TEAM_IN_USE = "admin_team_005";

    String ADMIN_TEAM_CONFICT_WITH_ANOTHER_TEAM = "admin_team_006";

    String ADMIN_TEAM_WRONG_VERSION_UPLOAD = "admin_team_007";

    String ADMIN_TEAM_UPLOAD_ERROR = "admin_team_008";

    String ADMIN_TEAM_UPLOAD_ALREADY_MEMBER_EXISTS = "admin_team_009";

    String ADMIN_TEAM_UPLOAD_DOUBLE_PARTICIPANT = "admin_team_010";

    // Weigthclass
    String FIGHTING_WEIGHTCLASS_EXISTS = "fighting_weightclass_001";

    String FIGHTING_WEIGHTCLASS_NO_DESCRIPTION = "fighting_weightclass_002";

    String FIGHTING_WEIGHTCLASS_DESCRIPTION_TO_LONG = "fighting_weightclass_003";

    String FIGHTING_WEIGHTCLASS_WRONG_ID = "fighting_weightclass_004";

    String FIGHTING_WEIGHTCLASS_IN_USE = "fighting_weightclass_005";

    String FIGHTING_WEIGHTCLASS_CONFICT_WITH_ANOTHER_WEIGHTCLASS = "fighting_weightclass_006";

    String FIGHTING_WEIGHTCLASS_MORE_THAN_MAX_WEIGHT = "fighting_weightclass_007";

    String FIGHTING_WEIGHTCLASS_LIMIT_IS_UNDER_OR_STARTWEIGHT = "fighting_weightclass_008";

    String FIGHTING_WEIGHTCLASS_COLLISION_WITH_OTHER_WEIGHTCLASS = "fighting_weightclass_009";

    String FIGHTING_WEIGHTCLASS_RENAME_WEIGHTCLASS = "fighting_weightclass_010";

    String FIGHTING_WEIGHTCLASS_EDIT_WEIGHTCLASS = "fighting_weightclass_011";

    String FIGHTING_WEIGHTCLASS_CAN_NOT_CREATE_AUTO_WEIGHTCLASS = "fighting_weightclass_012";
    
    String FIGHTING_WEIGHTCLASS_NO_AGE = "fighting_weightclass_013";
    
    String FIGHTING_WEIGHTCLASS_NOT_COMBINABLE = "fighting_weightclass_014";
    
    String FIGHTING_WEIGHTCLASS_NOT_REMOVEABLE_FROM_PARENT = "fighting_weightclass_015";

    String FIGHTING_WEIGHTCLASS_CHILD_PARENT = "fighting_weightclass_016";

    // Fightingclass
    String FIGHTINGCLASS_NO_TOGGLE_DELETE_STOP = "fightingclass_001";

    String FIGHTINGCLASS_NO_TOGGLE_COMPLETE = "fightingclass_002";

    // input fighter
    String ADMIN_FIGHTER_NEGATIV_WEIGHT = "admin_fighter_001";

    String ADMIN_FIGHTER_IS_TO_HAVY = "admin_fighter_002";

    String ADMIN_FIGHTER_ALREADY_EXIST = "admin_fighter_003";

    String ADMIN_FIGHTER_NO_TEAM = "admin_fighter_004";
    
    String ADMIN_FIGHTER_NO_AGE = "admin_fighter_005";
    
    String ADMIN_FIGHTER_BLOCKED_POOL = "admin_fighter_006";

    String ADMIN_FIGHTER_ILLEGAL_REGISTRATION = "admin_fighter_007";

    // change Fighter
    String ADMIN_CHANGE_FIGHTER_SAME_FIGHTER = "admin_change_fighter_001";

    String ADMIN_CHANGE_FIGHTER_EMPTY_FIGHTER = "admin_change_fighter_002";

    // fightingclass
    String ADMIN_FIGHTINGCLASS_CAN_NOT_CREATE_CLASSES = "admin_fightingclass_001";

    // ShowFighter
    String FIGHTING_FIGHTER_NOT_DELETEABLE = "fighting_showFighter_001";

    // fight
    String FIGHT_WRONG_FIGHT_RESULT = "fight_001";

    String FIGHT_WRONG_ZERO_POINTS = "fight_002";

    // fight
    String DUO_FIGHT_WRONG_FIGHT_RESULT = "duofight_001";

    String DUO_FIGHT_NEED_EXTRA_ROUND = "duofight_002";

    String DUO_FIGHT_WRONG_WINNER = "duofight_003";

    String DUO_FIGHT_NO_VALID_POINTS = "duofight_004";

    String DUO_FIGHT_POINTS_TO_BIG = "duofight_005";

    String DUO_FIGHT_POINTS_MUST_EQAL_BY_OVERTIME = "duofight_006";

    String FIGHTER_READY = "1";

    String FIGHTER_NOT_READY = "0";

    // duoclass
    String DUO_DUOCLASS_EXISTS = "duo_duoclass_001";

    String DUO_DUOCLASS_NO_DESCRIPTION = "duo_duoclass_002";

    String DUO_DUOCLASS_DESCRIPTION_TO_LONG = "duo_duoclass_003";

    String DUO_DUOCLASS_WRONG_ID = "duo_duoclass_004";

    String DUO_DUOCLASS_IN_USE = "duo_duoclass_005";

    String DUO_DUOCLASS_CONFICT_WITH_ANOTHER_WEIGHTCLASS = "duo_duoclass_006";

    String DUO_DUOCLASS_COLLISION_WITH_OTHER_WEIGHTCLASS = "duo_duoclass_009";

    String DUO_DUOCLASS_EDIT_DUOCLASS = "duo_duoclass_010";

    String DUO_DUOCLASS_NOT_COMBINABLE = "duo_duoclass_011";

    String DUO_DUOCLASS_NOT_REMOVEABLE_FROM_PARENT = "duo_duoclass_012";

    String DUO_DUOCLASS_CHILD_PARENT = "duo_duoclass_013";

    // input duo
    String ADMIN_DUOTEAM_ALREADY_EXIST = "admin_duoTeam_003";

    String ADMIN_DUOTEAM_NO_TEAM = "admin_duoTeam_004";
    
    String ADMIN_DUOTEAM_NO_AGE = "admin_duoTeam_005";
    
    String ADMIN_DUOTEAM_BLOCKED_POOL = "admin_duoTeam_006";

    String ADMIN_DUOTEAM_ILLEGAL_REGISTRATION = "admin_duoTeam_007";

    // show duoteams
    String DUO_DUOTEAM_NOT_DELETEABLE = "duo_showDuoTeams_001";

    // NEWA
    // change newa Fighter
    String ADMIN_CHANGE_NEWA_FIGHTER_SAME_FIGHTER = "admin_change_newa_fighter_001";

    String ADMIN_CHANGE_NEWA_FIGHTER_EMPTY_FIGHTER = "admin_change_newa_fighter_002";

    // fightingclass
    String ADMIN_NEWACLASS_CAN_NOT_CREATE_CLASSES = "admin_newaclass_001";


    String NEWACLASS_NO_TOGGLE_DELETE_STOP = "newalass_001";

    String NEWACLASS_NO_TOGGLE_COMPLETE = "newaclass_002";

    // input fighter
    String ADMIN_NEWA_FIGHTER_NEGATIV_WEIGHT = "admin_newa_fighter_001";

    String ADMIN_NEWA_FIGHTER_IS_TO_HAVY = "admin_newa_fighter_002";

    String ADMIN_NEWA_FIGHTER_ALREADY_EXIST = "admin_newa_fighter_003";

    String ADMIN_NEWA_FIGHTER_NO_TEAM = "admin_newa_fighter_004";

    String ADMIN_NEWA_FIGHTER_NO_AGE = "admin_newa_fighter_005";

    String ADMIN_NEWA_FIGHTER_BLOCKED_POOL = "admin_newa_fighter_006";

    String ADMIN_NEWA_FIGHTER_ILLEGAL_REGISTRATION = "admin_newa_fighter_007";

    // Weigthclass
    String NEWA_WEIGHTCLASS_EXISTS = "newa_weightclass_001";

    String NEWA_WEIGHTCLASS_NO_DESCRIPTION = "newa_weightclass_002";

    String NEWA_WEIGHTCLASS_DESCRIPTION_TO_LONG = "newa_weightclass_003";

    String NEWA_WEIGHTCLASS_WRONG_ID = "newa_weightclass_004";

    String NEWA_WEIGHTCLASS_IN_USE = "newa_weightclass_005";

    String NEWA_WEIGHTCLASS_CONFICT_WITH_ANOTHER_WEIGHTCLASS = "newa_weightclass_006";

    String NEWA_WEIGHTCLASS_MORE_THAN_MAX_WEIGHT = "newa_weightclass_007";

    String NEWA_WEIGHTCLASS_LIMIT_IS_UNDER_OR_STARTWEIGHT = "newa_weightclass_008";

    String NEWA_WEIGHTCLASS_COLLISION_WITH_OTHER_WEIGHTCLASS = "newa_weightclass_009";

    String NEWA_WEIGHTCLASS_RENAME_WEIGHTCLASS = "newa_weightclass_010";

    String NEWA_WEIGHTCLASS_EDIT_WEIGHTCLASS = "newa_weightclass_011";

    String NEWA_WEIGHTCLASS_CAN_NOT_CREATE_AUTO_WEIGHTCLASS = "newa_weightclass_012";

    String NEWA_WEIGHTCLASS_NO_AGE = "newa_weightclass_013";

    String NEWA_WEIGHTCLASS_NOT_COMBINABLE = "newa_weightclass_014";

    String NEWA_WEIGHTCLASS_NOT_REMOVEABLE_FROM_PARENT = "newa_weightclass_015";

    String NEWA_WEIGHTCLASS_CHILD_PARENT = "newa_weightclass_016";

    // ShowFighter
    String NEWA_FIGHTER_NOT_DELETEABLE = "newa_showFighter_001";

    // Medalranking
    int MEDALRANKING_ORG_TEAM = 1;

    int MEDALRANKING_ORG_REGION = 2;

    int MEDALRANKING_ORG_COUNTRY = 3;

    String ADMIN_USER_INPUT_TO_LONG = "admin_user_001";

    String ADMIN_USER_USERNAME_TO_SHORT = "admin_user_002";

    String ADMIN_USER_PASSWORD_TO_SHORT = "admin_user_003";

    String ADMIN_USER_PASSWORD_CONFIRM_WRONG = "admin_user_004";

    String ADMIN_USER_EXISTS = "admin_user_005";
    
    String ADMIN_USER_CAN_NOT_DELETE_SELF = "admin_user_006";

    // statistic
    String ADMIN_STATISTIC_SUCCESSFULL = "admin_statistic_001";

    String ADMIN_STATISTIC_EVENT_NOT_OVER = "admin_statistic_002";
}
