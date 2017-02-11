<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : showFight.jsp
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
<h:form id="adminFightAction">
  <h:inputHidden id="fightId" value="#{adminFightAction.fight.id}"/>

<p>&nbsp;</p>
<table align="center">
<tbody><tr>
<td  bgcolor="red" id="showFightRedName">

  </br>
  <h:outputText value="#{adminFightAction.fight.fighterRed.firstname}"/>
  <h:outputText value="&nbsp;" escape="false"/>
  <h:outputText value="#{adminFightAction.fight.fighterRed.name}"/>
  </br></br>
  <span id="showFightRedPoints">      
  <h:inputText value="#{adminFightAction.pointsRed}" id="pointsRed" required="false"
                     size="3" tabindex="1"
                     maxlength="3"/><jjw:codestableErrorMark field="pointsRed"/></span>
  </br></br>
</td>
<td  bgcolor="blue" id="showFightBlueName">
  </br>
  <h:outputText value="#{adminFightAction.fight.fighterBlue.firstname}"/>
  <h:outputText value="&nbsp;" escape="false"/>
  <h:outputText value="#{adminFightAction.fight.fighterBlue.name}"/>
  </br></br>
  <span id="showFightBluePoints">
  <h:inputText value="#{adminFightAction.pointsBlue}" id="pointsBlue" required="false"
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
  <h:selectOneMenu id="winner" value="#{adminFightAction.winner}" required="false" tabindex="3">
    <f:selectItems value="#{adminFightAction.winList}"/>
  </h:selectOneMenu>
  <jjw:codestableErrorMark field="winner"/>
  <p>&nbsp;</p>
  <h:commandButton value="#{msg['save']}" action="#{adminFightAction.save}" id="save" tabindex="4"/>
  <h:commandButton value="#{msg['abort']}" action="#{adminFightAction.abort}" id="abort" tabindex="5"/>
  <p>&nbsp;</p>

  <h:commandLink id="open"
                 onclick="openFightingClockWindow(#{adminFightAction.fight.id});">
    <t:graphicImage value="/images/display.png" title="#{msg['winnerIs']}" rendered="true" border="0" alt=""/>
    <t:htmlTag value="br"/>
    <h:outputText value="#{msg['openClock']}"/>
  </h:commandLink>


  <h:inputHidden id="ipponRedI" value="#{adminFightAction.fight.ipponRedI}"/>
  <h:inputHidden id="ipponRedII" value="#{adminFightAction.fight.ipponRedII}"/>
  <h:inputHidden id="ipponRedIII" value="#{adminFightAction.fight.ipponRedIII}"/>
  <h:inputHidden id="ipponBlueI" value="#{adminFightAction.fight.ipponBlueI}"/>
  <h:inputHidden id="ipponBlueII" value="#{adminFightAction.fight.ipponBlueII}"/>
  <h:inputHidden id="ipponBlueIII" value="#{adminFightAction.fight.ipponBlueIII}"/>
  <h:inputHidden id="fightTime" value="#{adminFightAction.fight.fightTime}"/>
  <h:inputHidden id="injuryTimeRed" value="#{adminFightAction.fight.injuryTimeRed}"/>
  <h:inputHidden id="injuryTimeBlue" value="#{adminFightAction.fight.injuryTimeBlue}"/>
  <h:inputHidden id="fightTimeWithBreaks" value="#{adminFightAction.fight.fightTimeWithBreaks}"/>
  <h:inputHidden id="overallFightTime" value="#{adminFightAction.fight.overallFightTime}"/>

  <h:inputHidden id="shidoRed" value="#{adminFightAction.fight.shidoRed}"/>
  <h:inputHidden id="shidoBlue" value="#{adminFightAction.fight.shidoBlue}"/>
  <h:inputHidden id="chuiRed" value="#{adminFightAction.fight.chuiRed}"/>
  <h:inputHidden id="chuiBlue" value="#{adminFightAction.fight.chuiBlue}"/>
  <h:inputHidden id="hansokumakeRed" value="#{adminFightAction.fight.hansokumakeRed}"/>
  <h:inputHidden id="hansokumakeBlue" value="#{adminFightAction.fight.hansokumakeBlue}"/>
  <h:inputHidden id="protokoll" value="#{adminFightAction.fight.protokoll}"/>
  <h:inputHidden id="fusengachi" value="#{adminFightAction.fight.fusengachi}"/>
  <h:inputHidden id="kikengachi" value="#{adminFightAction.fight.kikengachi}"/>

  <h:inputHidden id="pointsBlueOnClock" value="#{adminFightAction.fight.pointsBlueOnClock}"/>
  <h:inputHidden id="pointsRedOnClock" value="#{adminFightAction.fight.pointsRedOnClock}"/>

</h:form>
	
