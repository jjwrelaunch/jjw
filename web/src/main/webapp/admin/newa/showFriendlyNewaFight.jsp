<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : showFriendlyNewaFight.jsp
  ~ Created : 16 Jun 2010
  ~ Last Modified: Wed, 16 Jun 2010 13:46:41
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
<h:form id="adminFriendlyNewaFightAction">
  <h:inputHidden id="fightId" value="#{adminFriendlyNewaFightAction.fight.id}"/>

  <div id="fightWrapper">
    <div id="showFightRedWrapper">
        <%--<div id="showFightRed"><h:outputText value="#{msg['fighterRed']}"/></div>--%>
      <div id="showFightRedName">
        <h:outputText value="#{adminFriendlyNewaFightAction.fight.fighterRed.firstname}"/>
        <h:outputText value="&nbsp;" escape="false"/>
        <h:outputText value="#{adminFriendlyNewaFightAction.fight.fighterRed.name}"/></div>

      <div id="showFightRedPoints">
        <h:inputText value="#{adminFriendlyNewaFightAction.pointsRed}" id="pointsRed" required="false"
                     size="3" tabindex="1"
                     maxlength="3"/><jjw:codestableErrorMark field="pointsRed"/></div>
    </div>

    <div id="showFightBlueWrapper">
        <%--<div id="showFightBlue"><h:outputText value="#{msg['fighterBlue']}"/></div>--%>
      <div id="showFightBlueName">
        <h:outputText value="#{adminFriendlyNewaFightAction.fight.fighterBlue.firstname}"/>
        <h:outputText value="&nbsp;" escape="false"/>
        <h:outputText value="#{adminFriendlyNewaFightAction.fight.fighterBlue.name}"/></div>

      <div id="showFightBluePoints">
        <h:inputText value="#{adminFriendlyNewaFightAction.pointsBlue}" id="pointsBlue" required="false"
                     size="3" tabindex="1"
                     maxlength="3"/>
        <jjw:codestableErrorMark field="pointsBlue"/></div>
    </div>
  </div>
  <p>&nbsp;</p>
  <h:outputText value="#{msg['winnerIs']}"/>
  <h:outputText value="&nbsp;" escape="false"/>
  <h:selectOneMenu id="winner" value="#{adminFriendlyNewaFightAction.winner}" required="false" tabindex="3">
    <f:selectItems value="#{adminFriendlyNewaFightAction.winList}"/>
  </h:selectOneMenu>
  <jjw:codestableErrorMark field="winner"/>
  <p>&nbsp;</p>
  <h:commandButton value="#{msg['save']}" action="#{adminFriendlyNewaFightAction.save}" id="save" tabindex="4"/>
  <h:commandButton value="#{msg['abort']}" action="#{adminFriendlyNewaFightAction.abort}" id="abort" tabindex="5"/>
  <p>&nbsp;</p>

  <h:commandLink id="open"
                 onclick="openFriendlyFightingClockWindow(#{adminFriendlyNewaFightAction.fight.id});">
    <t:graphicImage value="/images/display.png" title="#{msg['winnerIs']}" rendered="true" border="0" alt=""/>
    <t:htmlTag value="br"/>
    <h:outputText value="#{msg['openClock']}"/>
  </h:commandLink>



  <h:inputHidden id="fightTime" value="#{adminFriendlyNewaFightAction.fight.fightTime}"/>
  <h:inputHidden id="injuryTimeRed" value="#{adminFriendlyNewaFightAction.fight.injuryTimeRed}"/>
  <h:inputHidden id="injuryTimeBlue" value="#{adminFriendlyNewaFightAction.fight.injuryTimeBlue}"/>
  <h:inputHidden id="fightTimeWithBreaks" value="#{adminFriendlyNewaFightAction.fight.fightTimeWithBreaks}"/>
  <h:inputHidden id="overallFightTime" value="#{adminFriendlyNewaFightAction.fight.overallFightTime}"/>

  <h:inputHidden id="shidoRed" value="#{adminFriendlyNewaFightAction.fight.shidoRed}"/>
  <h:inputHidden id="shidoBlue" value="#{adminFriendlyNewaFightAction.fight.shidoBlue}"/>
  <h:inputHidden id="advantageRed" value="#{adminFriendlyNewaFightAction.fight.advantageRed}"/>
  <h:inputHidden id="advantageBlue" value="#{adminFriendlyNewaFightAction.fight.advantageBlue}"/>
  <h:inputHidden id="hansokumakeRed" value="#{adminFriendlyNewaFightAction.fight.hansokumakeRed}"/>
  <h:inputHidden id="hansokumakeBlue" value="#{adminFriendlyNewaFightAction.fight.hansokumakeBlue}"/>
  <h:inputHidden id="protokoll" value="#{adminFriendlyNewaFightAction.fight.protokoll}"/>
  <h:inputHidden id="fusengachi" value="#{adminFriendlyNewaFightAction.fight.fusengachi}"/>
  <h:inputHidden id="kikengachi" value="#{adminFriendlyNewaFightAction.fight.kikengachi}"/>

  <h:inputHidden id="pointsBlueOnClock" value="#{adminFriendlyNewaFightAction.fight.pointsBlueOnClock}"/>
  <h:inputHidden id="pointsRedOnClock" value="#{adminFriendlyNewaFightAction.fight.pointsRedOnClock}"/>

</h:form>
	
