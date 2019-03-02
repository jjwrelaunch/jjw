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
<h:form id="offFightAction">
  <h:inputHidden id="fightId" value="#{offFightAction.fight.id}"/>

<p>&nbsp;</p>
<table align="center">
<tbody><tr>
<td  bgcolor="red" id="showFightRedName">

  </br>
  <h:outputText value="#{offFightAction.fight.fighterRed.firstname}"/>
  <h:outputText value="&nbsp;" escape="false"/>
  <h:outputText value="#{offFightAction.fight.fighterRed.name}"/>
  </br></br>
  <span id="showFightRedPoints">      
  <h:inputText value="#{offFightAction.pointsRed}" id="pointsRed" required="false"
                     size="3" tabindex="1"
                     maxlength="3"/><jjw:codestableErrorMark field="pointsRed"/></span>
  </br></br>
</td>
<td  bgcolor="blue" id="showFightBlueName">
  </br>
  <h:outputText value="#{offFightAction.fight.fighterBlue.firstname}"/>
  <h:outputText value="&nbsp;" escape="false"/>
  <h:outputText value="#{offFightAction.fight.fighterBlue.name}"/>
  </br></br>
  <span id="showFightBluePoints">
  <h:inputText value="#{offFightAction.pointsBlue}" id="pointsBlue" required="false"
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
  <h:selectOneMenu id="winner" value="#{offFightAction.winner}" required="false" tabindex="3">
    <f:selectItems value="#{offFightAction.winList}"/>
  </h:selectOneMenu>
  <jjw:codestableErrorMark field="winner"/>
  <p>&nbsp;</p>
  <h:commandButton value="#{msg['save']}"  action="#{offFightAction.save}" id="save" tabindex="4"/>
  <h:commandButton value="#{msg['abort']}" action="#{offFightAction.abort}" id="abort" tabindex="5"/>
  <p>&nbsp;</p>

  <h:commandLink id="open"
                 onclick="openFightingClockWindow(#{offFightAction.fight.id});">
    <t:graphicImage value="/images/display.png" title="#{msg['winnerIs']}" rendered="true" border="0" alt=""/>
    <t:htmlTag value="br"/>
    <h:outputText value="#{msg['openClock']}"/>
  </h:commandLink>

  
 <p>&nbsp;</p>
  <t:commandLink action="#{offFightAction.visualizeAllFights}"
			value="#{msg.visualize}" target="_blank" />
 
 

  <h:inputHidden id="ipponRedI" value="#{offFightAction.fight.ipponRedI}"/>
  <h:inputHidden id="ipponRedII" value="#{offFightAction.fight.ipponRedII}"/>
  <h:inputHidden id="ipponRedIII" value="#{offFightAction.fight.ipponRedIII}"/>
  <h:inputHidden id="ipponBlueI" value="#{offFightAction.fight.ipponBlueI}"/>
  <h:inputHidden id="ipponBlueII" value="#{offFightAction.fight.ipponBlueII}"/>
  <h:inputHidden id="ipponBlueIII" value="#{offFightAction.fight.ipponBlueIII}"/>
  <h:inputHidden id="fightTime" value="#{offFightAction.fight.fightTime}"/>
  <h:inputHidden id="injuryTimeRed" value="#{offFightAction.fight.injuryTimeRed}"/>
  <h:inputHidden id="injuryTimeBlue" value="#{offFightAction.fight.injuryTimeBlue}"/>
  <h:inputHidden id="fightTimeWithBreaks" value="#{offFightAction.fight.fightTimeWithBreaks}"/>
  <h:inputHidden id="overallFightTime" value="#{offFightAction.fight.overallFightTime}"/>

  <h:inputHidden id="shidoRed" value="#{offFightAction.fight.shidoRed}"/>
  <h:inputHidden id="shidoBlue" value="#{offFightAction.fight.shidoBlue}"/>
  <h:inputHidden id="chuiRed" value="#{offFightAction.fight.chuiRed}"/>
  <h:inputHidden id="chuiBlue" value="#{offFightAction.fight.chuiBlue}"/>
  <h:inputHidden id="hansokumakeRed" value="#{offFightAction.fight.hansokumakeRed}"/>
  <h:inputHidden id="hansokumakeBlue" value="#{offFightAction.fight.hansokumakeBlue}"/>
  <h:inputHidden id="protokoll" value="#{offFightAction.fight.protokoll}"/>
  <h:inputHidden id="fusengachi" value="#{offFightAction.fight.fusengachi}"/>
  <h:inputHidden id="kikengachi" value="#{offFightAction.fight.kikengachi}"/>

  <h:inputHidden id="pointsBlueOnClock" value="#{offFightAction.fight.pointsBlueOnClock}"/>
  <h:inputHidden id="pointsRedOnClock" value="#{offFightAction.fight.pointsRedOnClock}"/>

</h:form>
	
