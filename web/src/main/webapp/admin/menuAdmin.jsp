<%@ include file="/common/taglibs.jsp" %>
<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : menuAdmin.jsp
  ~ Created : 05 Jun 2010
  ~ Last Modified: Sat, 05 Jun 2010 21:02:55
  ~
  ~ Ju Jutsu Web is free software: you can redistribute it and/or modify
  ~ it under the terms of the GNU General Public License as published by
  ~ the Free Software Foundation, either version 3 of the License, or
  ~ (at your option) any later version.
  ~
  ~ Ju Jutsu Web is distributed in the hope that it will be useful,
  ~ but WITHOUT ANY WARRANTY; without even the implied warranty of
  ~ MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  ~ GNU General Public License for more details.
  ~
  ~ You should have received a copy of the GNU General Public License
  ~ along with Ju Jutsu Web.  If not, see <http://www.gnu.org/licenses/>.
  --%>

<f:loadBundle basename="de.jjw.webapp.messages.menu.Menu" var="menu_msg"/>

<h:form id="adminMenuAction">
  <ul class="level1">
    <ul class="level2">
      <li><h:commandLink action="#{adminMenuAction.start}" value="#{menu_msg.menu_ext_start}"/></li>
      <li><h:commandLink action="#{adminMenuAction.logout}" value="#{menu_msg.menu_admin_logout}"/></li>
      <li><h:commandLink action="#{adminMenuAction.teamParticipants}" value="#{menu_msg.menu_ext_teams}"/></li>      
    </ul>

    <li><h:outputText value="#{menu_msg.menu_fighting}" styleClass="bold"/></li>
    <ul class="level2">
      <li><h:outputText value="#{menu_msg.menu_fighting_fighers}"/></li>
      <ul class="level3">
        <li><h:commandLink action="#{adminMenuAction.editFighter}"                  value="#{menu_msg.menu_admin_fighting_editFighter}"/></li>
        <li><h:commandLink action="#{adminMenuAction.showFighter}"                  value="#{menu_msg.menu_admin_fighting_showFighter}"/></li>
        <li><h:commandLink action="#{adminMenuAction.showNotRegistratedFighter}"    value="#{menu_msg.menu_admin_fighting_showNotRegistratedFighter}"/></li>
      </ul>

      <li><h:outputText value="#{menu_msg.menu_fighting_classes}"/></li>
      <ul class="level3">        
        <li><h:commandLink action="#{adminMenuAction.fightingclassOverview}"        value="#{menu_msg.menu_admin_fighting_fightingclassOverview}"/></li>
        <li><h:commandLink action="#{adminMenuAction.userFightingclassOverview}"    value="#{menu_msg.menu_admin_fighting_assignUser}"/></li>
        <li><h:commandLink action="#{adminMenuAction.showWeightclasses}"            value="#{menu_msg.menu_admin_fighting_showWeightclasses}"/></li>
      </ul>

      <li><h:outputText value="#{menu_msg.menu_fighting_results}"/></li>
      <ul class="level3">
        <li><h:commandLink action="#{adminMenuAction.showFightingResults}"          value="#{menu_msg.menu_admin_fighting_results}"/></li>
      </ul>
    </ul>

    <li><h:outputText value="#{menu_msg.menu_duo}" styleClass="bold"/></li>
    <ul class="level2">
      <li><h:outputText value="#{menu_msg.menu_fighting_team}"/></li>
      <ul class="level3">
        <li><h:commandLink action="#{adminMenuAction.editDuoTeam}"                  value="#{menu_msg.menu_admin_duo_editDuoTeam}"/></li>
        <li><h:commandLink action="#{adminMenuAction.showDuoTeams}"                 value="#{menu_msg.menu_admin_duo_showDuoTeams}"/></li>
        <li><h:commandLink action="#{adminMenuAction.showNotRegistratedDuoTeams}"   value="#{menu_msg.menu_admin_duo_showNotRegistratedDuoTeams}"/></li>
      </ul>

      <li><h:outputText value="#{menu_msg.menu_fighting_classes}"/></li>
      <ul class="level3">        
        <li><h:commandLink action="#{adminMenuAction.duoclassOverview}"             value="#{menu_msg.menu_admin_duo_duoclassOverview}"/></li>
        <li><h:commandLink action="#{adminMenuAction.userDuoclassOverview}"         value="#{menu_msg.menu_admin_duo_assignUser}"/></li>
        <li><h:commandLink action="#{adminMenuAction.showDuoclasses}"               value="#{menu_msg.menu_admin_duo_showDuoclasses}"/></li>
      </ul>

      <li><h:outputText value="#{menu_msg.menu_fighting_results}"/></li>
      <ul class="level3">
        <li><h:commandLink action="#{adminMenuAction.showDuoResults}"               value="#{menu_msg.menu_admin_duo_showDuoResults}"/></li>                   
      </ul>
    </ul>

	<li><h:outputText value="#{menu_msg.menu_newa}" styleClass="bold"/></li>
    <ul class="level2">
      <li><h:outputText value="#{menu_msg.menu_fighting_fighers}"/></li>
      <ul class="level3">
        <li><h:commandLink action="#{adminMenuAction.editNewaFighter}"                  value="#{menu_msg.menu_admin_newa_editFighter}"/></li>
        <li><h:commandLink action="#{adminMenuAction.showNewaFighter}"                  value="#{menu_msg.menu_admin_newa_showFighter}"/></li>
        <li><h:commandLink action="#{adminMenuAction.showNotRegistratedNewaFighter}"    value="#{menu_msg.menu_admin_newa_showNotRegistratedFighter}"/></li>
      </ul>

      <li><h:outputText value="#{menu_msg.menu_fighting_classes}"/></li>
      <ul class="level3">        
        <li><h:commandLink action="#{adminMenuAction.newaclassOverview}"        value="#{menu_msg.menu_admin_newa_fightingclassOverview}"/></li>
        <li><h:commandLink action="#{adminMenuAction.userNewaclassOverview}"    value="#{menu_msg.menu_admin_newa_assignUser}"/></li>
        <li><h:commandLink action="#{adminMenuAction.showNewaWeightclasses}"            value="#{menu_msg.menu_admin_newa_showWeightclasses}"/></li>
      </ul>

      <li><h:outputText value="#{menu_msg.menu_fighting_results}"/></li>
      <ul class="level3">
        <li><h:commandLink action="#{adminMenuAction.showNewaResults}"          value="#{menu_msg.menu_admin_newa_results}"/></li>
      </ul>
    </ul>

    <li><h:outputText value="#{menu_msg.menu_admin}" styleClass="bold"/></li>
    <ul class="level2">
      <li><h:commandLink action="#{adminMenuAction.showUsers}" value="#{menu_msg.menu_admin_showUsers}"/></li>
      <li><h:commandLink action="#{adminMenuAction.showCountries}" value="#{menu_msg.menu_admin_showCountries}"/></li>
      <li><h:commandLink action="#{adminMenuAction.showRegions}" value="#{menu_msg.menu_admin_showRegions}"/></li>
      <li><h:commandLink action="#{adminMenuAction.showTeams}" value="#{menu_msg.menu_admin_showTeams}"/></li>
      <li><h:commandLink action="#{adminMenuAction.showAges}" value="#{menu_msg.menu_admin_showAges}"/></li>      
      <li><h:commandLink action="#{adminMenuAction.showConfig}" value="#{menu_msg.menu_admin_showConfig}"/></li>
      <li><h:commandLink action="#{adminMenuAction.showFightsystem}" value="#{menu_msg.menu_admin_showFightsystem}"/></li>
      <li><h:commandLink action="#{adminMenuAction.showStatistics}" value="#{menu_msg.menu_admin_statistics}"/></li>
      <li><h:commandLink action="#{adminMenuAction.showFriendlies}" value="#{menu_msg.menu_admin_friendlies}"/></li>
    </ul>

    <li><h:outputText value="#{menu_msg.menu_press}" styleClass="bold"/></li>
    <ul class="level2">
      <li><h:commandLink action="#{adminMenuAction.pressInfos}" value="#{menu_msg.menu_press}"/></li>
      <li><h:commandLink action="#{adminMenuAction.showMedalRanking}" value="#{menu_msg.menu_showMedalRanking}"/></li>      
      <li><h:commandLink action="#{adminMenuAction.allPreviews}" value="#{menu_msg.menu_allPreviews}"/></li>  
    </ul>
  </ul>
</h:form>