<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : menuExtern.jsp
  ~ Created : 05 Jun 2010
  ~ Last Modified: Sat, 05 Jun 2010 21:02:53
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

<h:form id="menuAction">
  <ul class="level1">
    <ul class="level2">
      <li><h:commandLink action="#{menuAction.start}" value="#{menu_msg.menu_ext_start}"/></li>
      <li><h:commandLink action="#{menuAction.login}" value="#{menu_msg.menu_ext_login}"/></li>
      <li><h:commandLink action="#{menuAction.teamParticipants}" value="#{menu_msg.menu_ext_teams}"/></li>
    </ul>

    <li><h:outputText value="#{menu_msg.menu_fighting}" styleClass="bold"/></li>
    <ul class="level2">
      <li><h:commandLink action="#{menuAction.showFighter}"                         value="#{menu_msg.menu_ext_fighting_showFighter}"/></li>
      <li><h:commandLink action="#{menuAction.fightingclassOverview}"               value="#{menu_msg.menu_ext_fighting_fightingclassOverview}"/></li>
      <li><h:commandLink action="#{menuAction.showFightingResults}"                 value="#{menu_msg.menu_ext_fighting_results}"/></li>
      <li><h:commandLink action="#{menuAction.showFightingResultsAsPdf}"            value="#{menu_msg.menu_ext_fighting_resultsPDF}" target="_blank"/></li>
    </ul>

    <li><h:outputText value="#{menu_msg.menu_duo}" styleClass="bold"/></li>
    <ul class="level2">
      <li class="level2"><h:commandLink action="#{menuAction.showDuoTeams}"         value="#{menu_msg.menu_ext_duo_showDuoTeams}"/></li>
      <li class="level2"><h:commandLink action="#{menuAction.duoclassOverview}"     value="#{menu_msg.menu_ext_duo_duoclassOverview}"/></li>
      <li class="level2"><h:commandLink action="#{menuAction.showDuoResults}"       value="#{menu_msg.menu_ext_duo_showDuoResults}"/></li>
      <li class="level2"><h:commandLink action="#{menuAction.showDuoResultsAsPdf}"  value="#{menu_msg.menu_ext_duo_resultsPDF}" target="_blank"/></li>
    </ul>
  
    <li><h:outputText value="#{menu_msg.menu_newa}" styleClass="bold"/></li>
    <ul class="level2">
      <li><h:commandLink action="#{menuAction.showNewaFighter}"                         value="#{menu_msg.menu_ext_newa_showFighter}"/></li>
      <li><h:commandLink action="#{menuAction.newaclassOverview}"               value="#{menu_msg.menu_ext_newa_fightingclassOverview}"/></li>
      <li><h:commandLink action="#{menuAction.showNewaResults}"                 value="#{menu_msg.menu_ext_newa_results}"/></li>
      <li><h:commandLink action="#{menuAction.showNewaResultsAsPdf}"            value="#{menu_msg.menu_ext_newa_resultsPDF}" target="_blank"/></li>
    </ul>
  
   <li><h:outputText value="#{menu_msg.menu_press}" styleClass="bold"/></li>
    <ul class="level2">
      <li><h:commandLink action="#{menuAction.pressInfos}" value="#{menu_msg.menu_press}"/></li>
      <li><h:commandLink action="#{menuAction.showMedalRanking}" value="#{menu_msg.menu_showMedalRanking}"/></li>
      <li><h:commandLink action="#{menuAction.allPreviews}" value="#{menu_msg.menu_allPreviews}"/></li>      
    </ul>
	</ul>
</h:form>