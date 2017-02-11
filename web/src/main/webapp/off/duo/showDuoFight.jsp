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
<h:form id="offDuoFightAction">
  <h:inputHidden id="fightId" value="#{offDuoFightAction.fight.id}"/>

  <table class="fancyTable" cellpadding="0" cellspacing="0" align="center">
    <thead>
    <tr>
      <th><b><h:outputText value="#{msg['teamRed']}"/></b></th>
      <th><b><h:outputText value="#{msg['teamBlue']}"/></b></th>
      <th colspan="2"><h:outputText value="#{msg['scores']}"/></th>
    </tr>
    </thead>
    <tr class="even">
      <td><h:outputText value="#{offDuoFightAction.fight.duoTeamRed.name}"/>
        <h:outputText value="&nbsp;/&nbsp;" escape="false"/>
        <h:outputText value="#{offDuoFightAction.fight.duoTeamRed.name2}"/></td>
      <td><h:outputText value="#{offDuoFightAction.fight.duoTeamBlue.name}"/>
        <h:outputText value="&nbsp;/&nbsp;" escape="false"/>
        <h:outputText value="#{offDuoFightAction.fight.duoTeamBlue.name2}"/></td>
      <td><h:inputText value="#{offDuoFightAction.pointsRed}" id="pointsRed" required="false" size="5" tabindex="1"
                       maxlength="5"/><jjw:codestableErrorMark field="pointsRed"/></td>
      <td><h:inputText value="#{offDuoFightAction.pointsBlue}" id="pointsBlue" required="false" size="5" tabindex="2"
                       maxlength="5"/><jjw:codestableErrorMark field="pointsBlue"/></td>
    </tr>
    <tr class="odd">
      <td><h:outputText value="#{msg['overtime']}"/></td>
      <td><h:selectBooleanCheckbox value="#{offDuoFightAction.overtime}" id="overtime" required="false"
                                   tabindex="3"/></td>
      <td><h:inputText value="#{offDuoFightAction.pointsRedMax}" id="pointsRedMax" required="false" size="5"
                       tabindex="4" maxlength="5"/><jjw:codestableErrorMark field="pointsRedMax"/></td>
      <td><h:inputText value="#{offDuoFightAction.pointsBlueMax}" id="pointsBlueMax" required="false" size="5"
                       tabindex="5" maxlength="5"/><jjw:codestableErrorMark field="pointsBlueMax"/></td>
    </tr>
  </table>
  <p>&nbsp;</p>
  <h:outputText value="#{msg['winnerIs']}"/><h:outputText value="&nbsp;" escape="false"/>
  <h:selectOneMenu id="winner" value="#{offDuoFightAction.winner}" required="false" tabindex="6">
    <f:selectItems value="#{offDuoFightAction.winList}"/>
  </h:selectOneMenu><jjw:codestableErrorMark field="winner"/>
  <p>&nbsp;</p>
  <h:commandButton value="#{msg['save']}" action="#{offDuoFightAction.save}" id="save" tabindex="7"/>
  <h:commandButton value="#{msg['abort']}" action="#{offDuoFightAction.abort}" id="abort" tabindex="8"/>
  <p>&nbsp;</p>

  <h:commandLink id="open"
                 onclick="openDuoClockWindow(#{offDuoFightAction.fight.id});">
    <t:graphicImage value="/images/display.png" title="#{msg['winnerIs']}" rendered="true" border="0" alt=""/>
    <t:htmlTag value="br"/>
    <h:outputText value="#{msg['openClock']}"/>
  </h:commandLink>


  <h:inputHidden id="redSeriesArefI" value="#{offDuoFightAction.fight.redSeriesArefI}"/>
  <h:inputHidden id="redSeriesArefII" value="#{offDuoFightAction.fight.redSeriesArefII}"/>
  <h:inputHidden id="redSeriesArefIII" value="#{offDuoFightAction.fight.redSeriesArefIII}"/>
  <h:inputHidden id="redSeriesArefIV" value="#{offDuoFightAction.fight.redSeriesArefIV}"/>
  <h:inputHidden id="redSeriesArefV" value="#{offDuoFightAction.fight.redSeriesArefV}"/>
  <h:inputHidden id="blueSeriesArefI" value="#{offDuoFightAction.fight.blueSeriesArefI}"/>
  <h:inputHidden id="blueSeriesArefII" value="#{offDuoFightAction.fight.blueSeriesArefII}"/>
  <h:inputHidden id="blueSeriesArefIII" value="#{offDuoFightAction.fight.blueSeriesArefIII}"/>
  <h:inputHidden id="blueSeriesArefIV" value="#{offDuoFightAction.fight.blueSeriesArefIV}"/>
  <h:inputHidden id="blueSeriesArefV" value="#{offDuoFightAction.fight.blueSeriesArefV}"/>

  <h:inputHidden id="redSeriesBrefI" value="#{offDuoFightAction.fight.redSeriesBrefI}"/>
  <h:inputHidden id="redSeriesBrefII" value="#{offDuoFightAction.fight.redSeriesBrefII}"/>
  <h:inputHidden id="redSeriesBrefIII" value="#{offDuoFightAction.fight.redSeriesBrefIII}"/>
  <h:inputHidden id="redSeriesBrefIV" value="#{offDuoFightAction.fight.redSeriesBrefIV}"/>
  <h:inputHidden id="redSeriesBrefV" value="#{offDuoFightAction.fight.redSeriesBrefV}"/>
  <h:inputHidden id="blueSeriesBrefI" value="#{offDuoFightAction.fight.blueSeriesBrefI}"/>
  <h:inputHidden id="blueSeriesBrefII" value="#{offDuoFightAction.fight.blueSeriesBrefII}"/>
  <h:inputHidden id="blueSeriesBrefIII" value="#{offDuoFightAction.fight.blueSeriesBrefIII}"/>
  <h:inputHidden id="blueSeriesBrefIV" value="#{offDuoFightAction.fight.blueSeriesBrefIV}"/>
  <h:inputHidden id="blueSeriesBrefV" value="#{offDuoFightAction.fight.blueSeriesBrefV}"/>

  <h:inputHidden id="redSeriesCrefI" value="#{offDuoFightAction.fight.redSeriesCrefI}"/>
  <h:inputHidden id="redSeriesCrefII" value="#{offDuoFightAction.fight.redSeriesCrefII}"/>
  <h:inputHidden id="redSeriesCrefIII" value="#{offDuoFightAction.fight.redSeriesCrefIII}"/>
  <h:inputHidden id="redSeriesCrefIV" value="#{offDuoFightAction.fight.redSeriesCrefIV}"/>
  <h:inputHidden id="redSeriesCrefV" value="#{offDuoFightAction.fight.redSeriesCrefV}"/>
  <h:inputHidden id="blueSeriesCrefI" value="#{offDuoFightAction.fight.blueSeriesCrefI}"/>
  <h:inputHidden id="blueSeriesCrefII" value="#{offDuoFightAction.fight.blueSeriesCrefII}"/>
  <h:inputHidden id="blueSeriesCrefIII" value="#{offDuoFightAction.fight.blueSeriesCrefIII}"/>
  <h:inputHidden id="blueSeriesCrefIV" value="#{offDuoFightAction.fight.blueSeriesCrefIV}"/>
  <h:inputHidden id="blueSeriesCrefV" value="#{offDuoFightAction.fight.blueSeriesCrefV}"/>

  <h:inputHidden id="redSeriesDrefI" value="#{offDuoFightAction.fight.redSeriesDrefI}"/>
  <h:inputHidden id="redSeriesDrefII" value="#{offDuoFightAction.fight.redSeriesDrefII}"/>
  <h:inputHidden id="redSeriesDrefIII" value="#{offDuoFightAction.fight.redSeriesDrefIII}"/>
  <h:inputHidden id="redSeriesDrefIV" value="#{offDuoFightAction.fight.redSeriesDrefIV}"/>
  <h:inputHidden id="redSeriesDrefV" value="#{offDuoFightAction.fight.redSeriesDrefV}"/>
  <h:inputHidden id="blueSeriesDrefI" value="#{offDuoFightAction.fight.blueSeriesDrefI}"/>
  <h:inputHidden id="blueSeriesDrefII" value="#{offDuoFightAction.fight.blueSeriesDrefII}"/>
  <h:inputHidden id="blueSeriesDrefIII" value="#{offDuoFightAction.fight.blueSeriesDrefIII}"/>
  <h:inputHidden id="blueSeriesDrefIV" value="#{offDuoFightAction.fight.blueSeriesDrefIV}"/>
  <h:inputHidden id="blueSeriesDrefV" value="#{offDuoFightAction.fight.blueSeriesDrefV}"/>
  <h:inputHidden id="fusengachi" value="#{offDuoFightAction.fight.fusengachi}"/>
  <h:inputHidden id="kikengachi" value="#{offDuoFightAction.fight.kikengachi}"/>

  <h:inputHidden id="overallFightTime" value="#{offDuoFightAction.fight.overallFightTime}"/>

</h:form>