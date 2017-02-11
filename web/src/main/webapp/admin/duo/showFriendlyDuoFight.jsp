<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : showFriendlyDuoFight.jsp
  ~ Created : 17 Jun 2010
  ~ Last Modified: Thu, 17 Jun 2010 18:36:59
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

<f:loadBundle basename="de.jjw.webapp.messages.admin.fight" var="msg"/>
<t:htmlTag value="h3"><h:outputText value="#{msg['fightHeadline'] }"/></t:htmlTag>
<h:form id="adminFriendlyDuoFightAction">
  <h:inputHidden id="fightId" value="#{adminFriendlyDuoFightAction.fight.id}"/>

  <table class="fancyTable" cellpadding="0" cellspacing="0" align="center">
    <thead>
    <tr>
      <th><b><h:outputText value="#{msg['teamRed']}"/></b></th>
      <th><b><h:outputText value="#{msg['teamBlue']}"/></b></th>
      <th colspan="2"><h:outputText value="#{msg['scores']}"/></th>
    </tr>
    </thead>
    <tr class="even">
      <td><h:outputText value="#{adminFriendlyDuoFightAction.fight.duoTeamRed.name}"/>
        <h:outputText value="&nbsp;/&nbsp;" escape="false"/>
        <h:outputText value="#{adminFriendlyDuoFightAction.fight.duoTeamRed.name2}"/></td>
      <td><h:outputText value="#{adminFriendlyDuoFightAction.fight.duoTeamBlue.name}"/>
        <h:outputText value="&nbsp;/&nbsp;" escape="false"/>
        <h:outputText value="#{adminFriendlyDuoFightAction.fight.duoTeamBlue.name2}"/></td>
      <td><h:inputText value="#{adminFriendlyDuoFightAction.pointsRed}" id="pointsRed" required="false" size="5" tabindex="1"
                       maxlength="5"/><jjw:codestableErrorMark field="pointsRed"/></td>
      <td><h:inputText value="#{adminFriendlyDuoFightAction.pointsBlue}" id="pointsBlue" required="false" size="5" tabindex="2"
                       maxlength="5"/><jjw:codestableErrorMark field="pointsBlue"/></td>
    </tr>
    <tr class="odd">
      <td><h:outputText value="#{msg['overtime']}"/></td>
      <td><h:selectBooleanCheckbox value="#{adminFriendlyDuoFightAction.overtime}" id="overtime" required="false"
                                   tabindex="3"/></td>
      <td><h:inputText value="#{adminFriendlyDuoFightAction.pointsRedMax}" id="pointsRedMax" required="false" size="5"
                       tabindex="4" maxlength="5"/><jjw:codestableErrorMark field="pointsRedMax"/></td>
      <td><h:inputText value="#{adminFriendlyDuoFightAction.pointsBlueMax}" id="pointsBlueMax" required="false" size="5"
                       tabindex="5" maxlength="5"/><jjw:codestableErrorMark field="pointsBlueMax"/></td>
    </tr>
  </table>
  <p>&nbsp;</p>
  <h:outputText value="#{msg['winnerIs']}"/><h:outputText value="&nbsp;" escape="false"/>
  <h:selectOneMenu id="winner" value="#{adminFriendlyDuoFightAction.winner}" required="false" tabindex="6">
    <f:selectItems value="#{adminFriendlyDuoFightAction.winList}"/>
  </h:selectOneMenu><jjw:codestableErrorMark field="winner"/>
  <p>&nbsp;</p>
  <h:commandButton value="#{msg['save']}" action="#{adminFriendlyDuoFightAction.save}" id="save" tabindex="7"/>
  <h:commandButton value="#{msg['abort']}" action="#{adminFriendlyDuoFightAction.abort}" id="abort" tabindex="8"/>
  <p>&nbsp;</p>

  <h:commandLink id="open"
                 onclick="openFriendlyDuoClockWindow(#{adminFriendlyDuoFightAction.fight.id});">
    <t:graphicImage value="/images/display.png" title="#{msg['winnerIs']}" rendered="true" border="0"  alt=""/>
    <t:htmlTag value="br"/>
    <h:outputText value="#{msg['openClock']}"/>
  </h:commandLink>


  <h:inputHidden id="redSeriesArefI" value="#{adminFriendlyDuoFightAction.fight.redSeriesArefI}"/>
  <h:inputHidden id="redSeriesArefII" value="#{adminFriendlyDuoFightAction.fight.redSeriesArefII}"/>
  <h:inputHidden id="redSeriesArefIII" value="#{adminFriendlyDuoFightAction.fight.redSeriesArefIII}"/>
  <h:inputHidden id="redSeriesArefIV" value="#{adminFriendlyDuoFightAction.fight.redSeriesArefIV}"/>
  <h:inputHidden id="redSeriesArefV" value="#{adminFriendlyDuoFightAction.fight.redSeriesArefV}"/>
  <h:inputHidden id="blueSeriesArefI" value="#{adminFriendlyDuoFightAction.fight.blueSeriesArefI}"/>
  <h:inputHidden id="blueSeriesArefII" value="#{adminFriendlyDuoFightAction.fight.blueSeriesArefII}"/>
  <h:inputHidden id="blueSeriesArefIII" value="#{adminFriendlyDuoFightAction.fight.blueSeriesArefIII}"/>
  <h:inputHidden id="blueSeriesArefIV" value="#{adminFriendlyDuoFightAction.fight.blueSeriesArefIV}"/>
  <h:inputHidden id="blueSeriesArefV" value="#{adminFriendlyDuoFightAction.fight.blueSeriesArefV}"/>

  <h:inputHidden id="redSeriesBrefI" value="#{adminFriendlyDuoFightAction.fight.redSeriesBrefI}"/>
  <h:inputHidden id="redSeriesBrefII" value="#{adminFriendlyDuoFightAction.fight.redSeriesBrefII}"/>
  <h:inputHidden id="redSeriesBrefIII" value="#{adminFriendlyDuoFightAction.fight.redSeriesBrefIII}"/>
  <h:inputHidden id="redSeriesBrefIV" value="#{adminFriendlyDuoFightAction.fight.redSeriesBrefIV}"/>
  <h:inputHidden id="redSeriesBrefV" value="#{adminFriendlyDuoFightAction.fight.redSeriesBrefV}"/>
  <h:inputHidden id="blueSeriesBrefI" value="#{adminFriendlyDuoFightAction.fight.blueSeriesBrefI}"/>
  <h:inputHidden id="blueSeriesBrefII" value="#{adminFriendlyDuoFightAction.fight.blueSeriesBrefII}"/>
  <h:inputHidden id="blueSeriesBrefIII" value="#{adminFriendlyDuoFightAction.fight.blueSeriesBrefIII}"/>
  <h:inputHidden id="blueSeriesBrefIV" value="#{adminFriendlyDuoFightAction.fight.blueSeriesBrefIV}"/>
  <h:inputHidden id="blueSeriesBrefV" value="#{adminFriendlyDuoFightAction.fight.blueSeriesBrefV}"/>

  <h:inputHidden id="redSeriesCrefI" value="#{adminFriendlyDuoFightAction.fight.redSeriesCrefI}"/>
  <h:inputHidden id="redSeriesCrefII" value="#{adminFriendlyDuoFightAction.fight.redSeriesCrefII}"/>
  <h:inputHidden id="redSeriesCrefIII" value="#{adminFriendlyDuoFightAction.fight.redSeriesCrefIII}"/>
  <h:inputHidden id="redSeriesCrefIV" value="#{adminFriendlyDuoFightAction.fight.redSeriesCrefIV}"/>
  <h:inputHidden id="redSeriesCrefV" value="#{adminFriendlyDuoFightAction.fight.redSeriesCrefV}"/>
  <h:inputHidden id="blueSeriesCrefI" value="#{adminFriendlyDuoFightAction.fight.blueSeriesCrefI}"/>
  <h:inputHidden id="blueSeriesCrefII" value="#{adminFriendlyDuoFightAction.fight.blueSeriesCrefII}"/>
  <h:inputHidden id="blueSeriesCrefIII" value="#{adminFriendlyDuoFightAction.fight.blueSeriesCrefIII}"/>
  <h:inputHidden id="blueSeriesCrefIV" value="#{adminFriendlyDuoFightAction.fight.blueSeriesCrefIV}"/>
  <h:inputHidden id="blueSeriesCrefV" value="#{adminFriendlyDuoFightAction.fight.blueSeriesCrefV}"/>

  <h:inputHidden id="redSeriesDrefI" value="#{adminFriendlyDuoFightAction.fight.redSeriesDrefI}"/>
  <h:inputHidden id="redSeriesDrefII" value="#{adminFriendlyDuoFightAction.fight.redSeriesDrefII}"/>
  <h:inputHidden id="redSeriesDrefIII" value="#{adminFriendlyDuoFightAction.fight.redSeriesDrefIII}"/>
  <h:inputHidden id="redSeriesDrefIV" value="#{adminFriendlyDuoFightAction.fight.redSeriesDrefIV}"/>
  <h:inputHidden id="redSeriesDrefV" value="#{adminFriendlyDuoFightAction.fight.redSeriesDrefV}"/>
  <h:inputHidden id="blueSeriesDrefI" value="#{adminFriendlyDuoFightAction.fight.blueSeriesDrefI}"/>
  <h:inputHidden id="blueSeriesDrefII" value="#{adminFriendlyDuoFightAction.fight.blueSeriesDrefII}"/>
  <h:inputHidden id="blueSeriesDrefIII" value="#{adminFriendlyDuoFightAction.fight.blueSeriesDrefIII}"/>
  <h:inputHidden id="blueSeriesDrefIV" value="#{adminFriendlyDuoFightAction.fight.blueSeriesDrefIV}"/>
  <h:inputHidden id="blueSeriesDrefV" value="#{adminFriendlyDuoFightAction.fight.blueSeriesDrefV}"/>
  <h:inputHidden id="fusengachi" value="#{adminFriendlyDuoFightAction.fight.fusengachi}"/>
  <h:inputHidden id="kikengachi" value="#{adminFriendlyDuoFightAction.fight.kikengachi}"/>

  <h:inputHidden id="overallFightTime" value="#{adminFriendlyDuoFightAction.fight.overallFightTime}"/>

</h:form>