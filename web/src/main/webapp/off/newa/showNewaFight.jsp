<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : showNewaFight.jsp
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
<h:form id="offNewaFightAction">
  <h:inputHidden id="fightId" value="#{offNewaFightAction.fight.id}"/>


<p>&nbsp;</p>
<table align="center">
<tbody><tr>
<td  bgcolor="red" id="showFightRedName">

  </br>
  <h:outputText value="#{offNewaFightAction.fight.fighterRed.firstname}"/>
  <h:outputText value="&nbsp;" escape="false"/>
  <h:outputText value="#{offNewaFightAction.fight.fighterRed.name}"/>
  </br></br>
  <span id="showFightRedPoints">      
  <h:inputText value="#{offNewaFightAction.pointsRed}" id="pointsRed" required="false"
                     size="3" tabindex="1"
                     maxlength="3"/><jjw:codestableErrorMark field="pointsRed"/></span>
  </br></br>
</td>
<td  bgcolor="blue" id="showFightBlueName">
  </br>
  <h:outputText value="#{offNewaFightAction.fight.fighterBlue.firstname}"/>
  <h:outputText value="&nbsp;" escape="false"/>
  <h:outputText value="#{offNewaFightAction.fight.fighterBlue.name}"/>
  </br></br>
  <span id="showFightBluePoints">
  <h:inputText value="#{offNewaFightAction.pointsBlue}" id="pointsBlue" required="false"
                     size="3" tabindex="1"
                     maxlength="3"/></span>
  </br></br>
<jjw:codestableErrorMark field="pointsBlue"/>
 </td>
</tr>
</tbody></table>

 
  <p>&nbsp;</p>
  <h:outputText value="#{msg['winnerIs']}"/>
  <h:outputText value="&nbsp;" escape="false"/>
  <h:selectOneMenu id="winner" value="#{offNewaFightAction.winner}" required="false" tabindex="3">
    <f:selectItems value="#{offNewaFightAction.winList}"/>
  </h:selectOneMenu>
  <jjw:codestableErrorMark field="winner"/>
  <p>&nbsp;</p>
  <h:commandButton value="#{msg['save']}"  action="#{offNewaFightAction.save}" id="save" tabindex="4"/>
  <h:commandButton value="#{msg['abort']}" action="#{offNewaFightAction.abort}" id="abort" tabindex="5"/>
  <p>&nbsp;</p>

  <h:commandLink id="open"
                 onclick="openNewaClockWindow(#{offNewaFightAction.fight.id});">
    <t:graphicImage value="/images/display.png" title="#{msg['winnerIs']}" rendered="true" border="0" alt=""/>
    <t:htmlTag value="br"/>
    <h:outputText value="#{msg['openClock']}"/>
  </h:commandLink>

<p>&nbsp;</p>
 <p align="center">
 
 <c:if test="${offNewaFightAction.fight != null}">
 <c:if test="${offNewaFightAction.fight.winnerId > 0}">
	<table class="fancyTable">
		<tr class="odd">
			<td><t:outputText value="&nbsp;" escape="false"/></td>
			<td><h:outputText value="#{msg['fighterRed'] }"/></td>
			<td><h:outputText value="#{msg['fighterBlue'] }"/></td>
		</tr>
		<tr class="even">
			<td><h:outputText value="#{newa['pool.points'] }"/></td>
			<td><h:outputText value="#{offNewaFightAction.fight.pointsRed}"/></td>
			<td><h:outputText value="#{offNewaFightAction.fight.pointsBlue}"/></td>
		</tr>
		<tr class="odd">
			<td><h:outputText value="#{newa['pool.points.advantage'] }"/></td>
			<td><h:outputText value="#{adminNewaFightAction.fight.advantageRedWeb}"/></td>
			<td><h:outputText value="#{adminNewaFightAction.fight.advantageBlueWeb}"/></td>
		</tr>
	</table>	
</c:if>
</c:if>
</p>

  <h:inputHidden id="fightTime" value="#{offNewaFightAction.fight.fightTime}"/>
  <h:inputHidden id="injuryTimeRed" value="#{offNewaFightAction.fight.injuryTimeRed}"/>
  <h:inputHidden id="injuryTimeBlue" value="#{offNewaFightAction.fight.injuryTimeBlue}"/>
  <h:inputHidden id="fightTimeWithBreaks" value="#{offNewaFightAction.fight.fightTimeWithBreaks}"/>
  <h:inputHidden id="overallFightTime" value="#{offNewaFightAction.fight.overallFightTime}"/>

  <h:inputHidden id="shidoRed" value="#{offNewaFightAction.fight.shidoRed}"/>
  <h:inputHidden id="shidoBlue" value="#{offNewaFightAction.fight.shidoBlue}"/>
  <h:inputHidden id="advantageRed" value="#{offNewaFightAction.fight.advantageRed}"/>
  <h:inputHidden id="advantageBlue" value="#{offNewaFightAction.fight.advantageBlue}"/>
  <h:inputHidden id="hansokumakeRed" value="#{offNewaFightAction.fight.hansokumakeRed}"/>
  <h:inputHidden id="hansokumakeBlue" value="#{offNewaFightAction.fight.hansokumakeBlue}"/>
  <h:inputHidden id="protokoll" value="#{offNewaFightAction.fight.protokoll}"/>
  <h:inputHidden id="fusengachi" value="#{offNewaFightAction.fight.fusengachi}"/>
  <h:inputHidden id="kikengachi" value="#{offNewaFightAction.fight.kikengachi}"/>

  <h:inputHidden id="pointsBlueOnClock" value="#{offNewaFightAction.fight.pointsBlueOnClock}"/>
  <h:inputHidden id="pointsRedOnClock" value="#{offNewaFightAction.fight.pointsRedOnClock}"/>

</h:form>
	
