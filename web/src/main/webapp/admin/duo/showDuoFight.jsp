<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : showDuoFight.jsp
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
<h:form id="adminDuoFightAction">
  <h:inputHidden id="fightId" value="#{adminDuoFightAction.fight.id}"/>

  <table class="fancyTable" cellpadding="0" cellspacing="0" align="center">
    <thead>
    <tr>
      <th><b><h:outputText value="#{msg['teamRed']}"/></b></th>
      <th><b><h:outputText value="#{msg['teamBlue']}"/></b></th>
      <th colspan="2"><h:outputText value="#{msg['scores']}"/></th>
    </tr>
    </thead>
    <tr class="even">
      <td><h:outputText value="#{adminDuoFightAction.fight.duoTeamRed.name}"/>
        <h:outputText value="&nbsp;/&nbsp;" escape="false"/>
        <h:outputText value="#{adminDuoFightAction.fight.duoTeamRed.name2}"/></td>
      <td><h:outputText value="#{adminDuoFightAction.fight.duoTeamBlue.name}"/>
        <h:outputText value="&nbsp;/&nbsp;" escape="false"/>
        <h:outputText value="#{adminDuoFightAction.fight.duoTeamBlue.name2}"/></td>
      <td><h:inputText value="#{adminDuoFightAction.pointsRed}" id="pointsRed" required="false" size="5" tabindex="1"
                       maxlength="5"/><jjw:codestableErrorMark field="pointsRed"/></td>
      <td><h:inputText value="#{adminDuoFightAction.pointsBlue}" id="pointsBlue" required="false" size="5" tabindex="2"
                       maxlength="5"/><jjw:codestableErrorMark field="pointsBlue"/></td>
    </tr>
    <tr class="odd">
      <td><h:outputText value="#{msg['overtime']}"/></td>
      <td><h:selectBooleanCheckbox value="#{adminDuoFightAction.overtime}" id="overtime" required="false"
                                   tabindex="3"/></td>
      <td><h:inputText value="#{adminDuoFightAction.pointsRedMax}" id="pointsRedMax" required="false" size="5"
                       tabindex="4" maxlength="5"/><jjw:codestableErrorMark field="pointsRedMax"/></td>
      <td><h:inputText value="#{adminDuoFightAction.pointsBlueMax}" id="pointsBlueMax" required="false" size="5"
                       tabindex="5" maxlength="5"/><jjw:codestableErrorMark field="pointsBlueMax"/></td>
    </tr>
  </table>
  <p>&nbsp;</p>
  <h:outputText value="#{msg['winnerIs']}"/><h:outputText value="&nbsp;" escape="false"/>
  <h:selectOneMenu id="winner" value="#{adminDuoFightAction.winner}" required="false" tabindex="6">
    <f:selectItems value="#{adminDuoFightAction.winList}"/>
  </h:selectOneMenu><jjw:codestableErrorMark field="winner"/>
  <p>&nbsp;</p>
  <h:commandButton value="#{msg['save']}" action="#{adminDuoFightAction.save}" id="save" tabindex="7"/>
  <h:commandButton value="#{msg['abort']}" action="#{adminDuoFightAction.abort}" id="abort" tabindex="8"/>
  <p>&nbsp;</p>

  <h:commandLink id="open"
                 onclick="openDuoClockWindow(#{adminDuoFightAction.fight.id});">
    <t:graphicImage value="/images/display.png" title="#{msg['winnerIs']}" rendered="true" border="0"  alt=""/>
    <t:htmlTag value="br"/>
    <h:outputText value="#{msg['openClock']}"/>
  </h:commandLink>


  <h:inputHidden id="redSeriesArefI" value="#{adminDuoFightAction.fight.redSeriesArefI}"/>
  <h:inputHidden id="redSeriesArefII" value="#{adminDuoFightAction.fight.redSeriesArefII}"/>
  <h:inputHidden id="redSeriesArefIII" value="#{adminDuoFightAction.fight.redSeriesArefIII}"/>
  <h:inputHidden id="redSeriesArefIV" value="#{adminDuoFightAction.fight.redSeriesArefIV}"/>
  <h:inputHidden id="redSeriesArefV" value="#{adminDuoFightAction.fight.redSeriesArefV}"/>
  <h:inputHidden id="blueSeriesArefI" value="#{adminDuoFightAction.fight.blueSeriesArefI}"/>
  <h:inputHidden id="blueSeriesArefII" value="#{adminDuoFightAction.fight.blueSeriesArefII}"/>
  <h:inputHidden id="blueSeriesArefIII" value="#{adminDuoFightAction.fight.blueSeriesArefIII}"/>
  <h:inputHidden id="blueSeriesArefIV" value="#{adminDuoFightAction.fight.blueSeriesArefIV}"/>
  <h:inputHidden id="blueSeriesArefV" value="#{adminDuoFightAction.fight.blueSeriesArefV}"/>

  <h:inputHidden id="redSeriesBrefI" value="#{adminDuoFightAction.fight.redSeriesBrefI}"/>
  <h:inputHidden id="redSeriesBrefII" value="#{adminDuoFightAction.fight.redSeriesBrefII}"/>
  <h:inputHidden id="redSeriesBrefIII" value="#{adminDuoFightAction.fight.redSeriesBrefIII}"/>
  <h:inputHidden id="redSeriesBrefIV" value="#{adminDuoFightAction.fight.redSeriesBrefIV}"/>
  <h:inputHidden id="redSeriesBrefV" value="#{adminDuoFightAction.fight.redSeriesBrefV}"/>
  <h:inputHidden id="blueSeriesBrefI" value="#{adminDuoFightAction.fight.blueSeriesBrefI}"/>
  <h:inputHidden id="blueSeriesBrefII" value="#{adminDuoFightAction.fight.blueSeriesBrefII}"/>
  <h:inputHidden id="blueSeriesBrefIII" value="#{adminDuoFightAction.fight.blueSeriesBrefIII}"/>
  <h:inputHidden id="blueSeriesBrefIV" value="#{adminDuoFightAction.fight.blueSeriesBrefIV}"/>
  <h:inputHidden id="blueSeriesBrefV" value="#{adminDuoFightAction.fight.blueSeriesBrefV}"/>

  <h:inputHidden id="redSeriesCrefI" value="#{adminDuoFightAction.fight.redSeriesCrefI}"/>
  <h:inputHidden id="redSeriesCrefII" value="#{adminDuoFightAction.fight.redSeriesCrefII}"/>
  <h:inputHidden id="redSeriesCrefIII" value="#{adminDuoFightAction.fight.redSeriesCrefIII}"/>
  <h:inputHidden id="redSeriesCrefIV" value="#{adminDuoFightAction.fight.redSeriesCrefIV}"/>
  <h:inputHidden id="redSeriesCrefV" value="#{adminDuoFightAction.fight.redSeriesCrefV}"/>
  <h:inputHidden id="blueSeriesCrefI" value="#{adminDuoFightAction.fight.blueSeriesCrefI}"/>
  <h:inputHidden id="blueSeriesCrefII" value="#{adminDuoFightAction.fight.blueSeriesCrefII}"/>
  <h:inputHidden id="blueSeriesCrefIII" value="#{adminDuoFightAction.fight.blueSeriesCrefIII}"/>
  <h:inputHidden id="blueSeriesCrefIV" value="#{adminDuoFightAction.fight.blueSeriesCrefIV}"/>
  <h:inputHidden id="blueSeriesCrefV" value="#{adminDuoFightAction.fight.blueSeriesCrefV}"/>

  <h:inputHidden id="redSeriesDrefI" value="#{adminDuoFightAction.fight.redSeriesDrefI}"/>
  <h:inputHidden id="redSeriesDrefII" value="#{adminDuoFightAction.fight.redSeriesDrefII}"/>
  <h:inputHidden id="redSeriesDrefIII" value="#{adminDuoFightAction.fight.redSeriesDrefIII}"/>
  <h:inputHidden id="redSeriesDrefIV" value="#{adminDuoFightAction.fight.redSeriesDrefIV}"/>
  <h:inputHidden id="redSeriesDrefV" value="#{adminDuoFightAction.fight.redSeriesDrefV}"/>
  <h:inputHidden id="blueSeriesDrefI" value="#{adminDuoFightAction.fight.blueSeriesDrefI}"/>
  <h:inputHidden id="blueSeriesDrefII" value="#{adminDuoFightAction.fight.blueSeriesDrefII}"/>
  <h:inputHidden id="blueSeriesDrefIII" value="#{adminDuoFightAction.fight.blueSeriesDrefIII}"/>
  <h:inputHidden id="blueSeriesDrefIV" value="#{adminDuoFightAction.fight.blueSeriesDrefIV}"/>
  <h:inputHidden id="blueSeriesDrefV" value="#{adminDuoFightAction.fight.blueSeriesDrefV}"/>
  <h:inputHidden id="fusengachi" value="#{adminDuoFightAction.fight.fusengachi}"/>
  <h:inputHidden id="kikengachi" value="#{adminDuoFightAction.fight.kikengachi}"/>

  <h:inputHidden id="overallFightTime" value="#{adminDuoFightAction.fight.overallFightTime}"/>

</h:form>