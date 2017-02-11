/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : IGlobalWebConstants.java
 * Created : 09 Jun 2010
 * Last Modified: Wed, 09 Jun 2010 14:18:09
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

package de.jjw.webapp;

/**
 * This Class contains all constants for Web Layer.
 */
public interface IGlobalWebConstants
{

    boolean DEBUG = true;

    String SUCCESS = "success";

    String WEB_CONST_WEB_EXCHANGE_CONTEXT = "web_const_webExchangeContext";

    String WEB_CONST_MENU_EXCHANGE_CONTEXT = "web_const_menuExchangeContext";

    String WEB_CONST_USER = "SPRING_SECURITY_CONTEXT";

    String WEB_CONST_MENU_TYPE = "web_const_menuType";

    String WEB_CONST_MENU_TYPE_EXTERN = "web_const_menuTypeExtern";

    String WEB_CONST_MENU_TYPE_TEAM = "web_const_menuTypeTeam";

    String WEB_CONST_MENU_TYPE_OFFICAL = "web_const_menuTypeOfficial";

    String WEB_CONST_MENU_TYPE_EVENT = "web_const_menuTypeEvent";

    String WEB_CONST_MENU_TYPE_ADMIN = "web_const_menuTypeAdmin";

    String WEB_CONST_MENU_EXTERN = "/extern/menuExtern";

    String WEB_CONST_MENU_TEAM = "/team/menuTeam";

    String WEB_CONST_MENU_OFFICAL = "/off/menuOfficial";

    String WEB_CONST_MENU_EVENT = "/event/menuEvent";

    String WEB_CONST_MENU_ADMIN = "/admin/menuAdmin";

    String WEB_CONST_EVENT_ID = "web_const_event_id";

    String WEB_CONST_START = "start";

    String WEB_DISPLAY_ERRORS = "web_disply_errors";

    // contex constantants
    int CONTEXT_EXTERN = 0;

    int CONTEXT_TEAM = 1;

    int CONTEXT_EVENT = 2;

    int CONTEXT_OFFICIAL = 3;

    int CONTEXT_ADMIN = 4;

    Integer CONTEXT_EXTERN_AS_INTEGER = Integer.valueOf( 0 );

    Integer CONTEXT_TEAM_AS_INTEGER = Integer.valueOf( 1 );

    Integer CONTEXT_EVENT_AS_INTEGER = Integer.valueOf( 2 );

    Integer CONTEXT_OFFICIAL_AS_INTEGER = Integer.valueOf( 3 );

    Integer CONTEXT_ADMIN_AS_INTEGER = Integer.valueOf( 4 );

    // admin
    String WEB_ADMIN_USER_NEW = "web_admin_user_new";

    String WEB_ADMIN_TEAM_NEW = "web_admin_team_new";

    String WEB_ADMIN_TEAM_BEAN = "web_admin_team_bean";

    String WEB_ADMIN_TEAM_UPLOAD = "web_admin_team_upload";

    String WEB_ADMIN_APPLICATION_UPLOAD = "web_admin_team_upload";

    String WEB_ADMIN_AGE_NEW = "web_admin_age_new";

    String WEB_ADMIN_AGE_BEAN = "web_admin_age_bean";

    String WEB_ADMIN_WEIGHTCLASS_NEW = "web_admin_weightclass_new";

    String WEB_ADMIN_WEIGHTCLASS_BEAN = "web_admin_weightclass_bean";

    String WEB_ADMIN_FIGHTER_NEW = "web_admin_fighter_new";

    String WEB_ADMIN_FIGHTSYSTEM_LIST = "web_admin_fightsystem_list";

    String WEB_ADMIN_FIGHTER = "web_admin_fighter";

    String WEB_ADMIN_FIGHTER_FRIENDLY = "web_admin_fighter_friendly";

    String WEB_ADMIN_FIGHTER_COUNTRY = "web_admin_fighter_country";

    String WEB_ADMIN_FIGHTER_REGION = "web_admin_fighter_region";

    String WEB_ADMIN_FIGHTER_TEAM = "web_admin_fighter_team";

    String WEB_ADMIN_DUOCLASS_NEW = "web_admin_duoclass_new";

    String WEB_ADMIN_DUOCLASS_BEAN = "web_admin_duoclass_bean";

    String WEB_ADMIN_DUOTEAM = "web_admin_duoTeam";

    String WEB_ADMIN_DUOTEAM_FRIENDLY = "web_admin_duoTeam_friendly";

    String WEB_ADMIN_DUOTEAM_COUNTRY = "web_admin_duoTeam_country";

    String WEB_ADMIN_DUOTEAM_REGION = "web_admin_duoTeam_region";

    String WEB_ADMIN_DUOTEAM_TEAM = "web_admin_duoTeam_team";

    String WEB_ADMIN_DUOTEAM_NEW = "web_admin_duoTeam_new";

    // ----------------------------------------------------------------------------
    // urls AND HTML
    // ----------------------------------------------------------------------------
    String SHOW_FIGHTMASK_URL = "/admin/showFight.html";

    String SHOW_FIGHTMASK_URL_OFFICIAL = "/off/showFight.html";

    String SHOW_FIGHTMASK_FRIENDLY_URL = "/admin/showFriendlyFight.html";

    String SHOW_FIGHTMASK_FRIENDLY_URL_OFFICIAL = "/off/showFriendlyFight.html";

    String SHOW_FIGHTINGCLASS = "/admin/fightingPool.html";

    String SHOW_FIGHTINGCLASS_OFFICIAL = "/off/fightingPool.html";

    String SHOW_NEWACLASS_OFFICIAL = "/off/newaPool.html";

    String SHOW_FIGHTINGCLASS_EXTERN = "/fightingPool.html";

    String CHANGE_FIGHTER_URL = "/admin/changeFighter.html";

    String HTML_QUESTION_MARK = "?";

    String HTML_EQUAL = "=";

    String HTML_AND = "&";

    String HTML_NBSP = "&nbsp;";

    String HTML_PARAM_FIGHT = "fightId";

    String HTML_PARAM_FIGHTINGCLASS_ID = "fightingclassId";

    String SHOW_DUO_FIGHTMASK_URL = "/admin/showDuoFight.html";

    String SHOW_DUO_FIGHTMASK_URL_OFFICIAL = "/off/showDuoFight.html";

    String SHOW_DUO_FIGHTMASK_FRIENDLY_URL = "/admin/showFriendlyDuoFight.html";

    String SHOW_DUO_FIGHTMASK_FRIENDLY_URL_OFFICIAL = "/off/showFriendlyDuoFight.html";

    String HTML_PARAM_DUOCLASS_ID = "duoclassId";

    String SHOW_DUOCLASS = "/admin/duoPool.html";

    String SHOW_DUOCLASS_OFFICIAL = "/off/duoPool.html";

    String SHOW_DUOCLASS_EXTERN = "/duoPool.html";

    String CHANGE_DUOTEAM_URL = "/admin/changeDuoTeams.html";

    String EMPTY_ITEM_TEXT = "--------------------";

    String PREVIEW_URL = "/util/preview";

    String HANDLE_LINK_CLICK = "handleLinkClick";

    String HTML_PARAM_NEWACLASS_ID = "newaclassId";

    String CHANGE_NEWA_FIGHTER_URL = "/admin/changeNewaFighter.html";

    String SHOW_NEWA_FIGHTMASK_URL = "/admin/showNewaFight.html";

    String SHOW_NEWA_FIGHTMASK_URL_OFFICIAL = "/off/showNewaFight.html";

    String WEB_ADMIN_NEWA_WEIGHTCLASS_NEW = "web_admin_newa_weightclass_new";

    String SHOW_NEWACLASS = "/admin/newaPool.html";

    String SHOW_NEWACLASS_EXTERN = "/newaPool.html";

    // ----------------
    double DUO_FIGHT_MAX_POINTS = 999.0;
    
    int MAX_ERRORS_TO_DISPLAY = 3;
}
