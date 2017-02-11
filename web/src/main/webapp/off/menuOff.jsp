<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : menuOff.jsp
  ~ Created : 05 Jun 2010
  ~ Last Modified: Sat, 05 Jun 2010 21:02:54
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

<h:form id="offMenuAction">
  <ul class="level1">
    <ul class="level2">
      <li><h:commandLink action="#{offMenuAction.start}" value="#{menu_msg.menu_off_start}"/></li>
      <li><h:commandLink action="#{offMenuAction.logout}" value="#{menu_msg.menu_off_logout}"/></li>
      <li><h:commandLink action="#{offMenuAction.teamParticipants}" value="#{menu_msg.menu_off_teams}"/></li>
    </ul>


    <li><h:outputText value="#{menu_msg.menu_fighting}" styleClass="bold"/></li>
    <ul class="level2">
      <li><h:commandLink action="#{offMenuAction.showFighter}"                     value="#{menu_msg.menu_off_fighting_showFighter}"/></li>
      <li><h:commandLink action="#{offMenuAction.fightingclassOverview}"           value="#{menu_msg.menu_off_fighting_fightingclassOverview}"/></li>
      <li><h:commandLink action="#{offMenuAction.showFightingResults}"             value="#{menu_msg.menu_off_fighting_results}"/></li>
      <li><h:commandLink action="#{offMenuAction.showFightingResultsAsPdf}"        value="#{menu_msg.menu_off_fighting_resultsPDF}" target="_blank"/></li>
    </ul>


    <li><h:outputText value="#{menu_msg.menu_duo}" styleClass="bold"/></li>
    <ul class="level2">
      <li><h:commandLink action="#{offMenuAction.showDuoTeams}"					   value="#{menu_msg.menu_off_duo_showDuoTeams}"/></li>
      <li><h:commandLink action="#{offMenuAction.duoclassOverview}"                value="#{menu_msg.menu_off_duo_duoclassOverview}"/></li>
      <li><h:commandLink action="#{offMenuAction.showDuoResults}"                  value="#{menu_msg.menu_off_duo_showDuoResults}"/></li>
      <li><h:commandLink action="#{offMenuAction.showDuoResultsAsPdf}"             value="#{menu_msg.menu_off_duo_resultsPDF}" target="_blank"/></li>
    </ul>
    
    <li><h:outputText value="#{menu_msg.menu_newa}" styleClass="bold"/></li>
    <ul class="level2">
      <li><h:commandLink action="#{offMenuAction.showNewaFighter}"                     value="#{menu_msg.menu_off_newa_showFighter}"/></li>
      <li><h:commandLink action="#{offMenuAction.newaclassOverview}"           value="#{menu_msg.menu_off_newa_fightingclassOverview}"/></li>
      <li><h:commandLink action="#{offMenuAction.showNewaResults}"             value="#{menu_msg.menu_off_newa_results}"/></li>
      <li><h:commandLink action="#{offMenuAction.showNewaResultsAsPdf}"        value="#{menu_msg.menu_off_newa_resultsPDF}" target="_blank"/></li>
    </ul>

    <li><h:outputText value="#{menu_msg.menu_press}" styleClass="bold"/></li>
    <ul class="level2">
      <li><h:commandLink action="#{offMenuAction.pressInfos}" value="#{menu_msg.menu_press}"/></li>
      <li><h:commandLink action="#{offMenuAction.showMedalRanking}" value="#{menu_msg.menu_showMedalRanking}"/></li>
      <li><h:commandLink action="#{offMenuAction.showTatamiPreview}" value="#{menu_msg.menu_off_duo_tatami_preview}"/></li>
      <li><h:commandLink action="#{offMenuAction.allPreviews}" value="#{menu_msg.menu_allPreviews}"/></li>
    </ul>
</h:form>