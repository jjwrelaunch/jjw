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
<h:form id="adminNewaFightAction">
  <h:inputHidden id="fightId" value="#{adminNewaFightAction.fight.id}"/>

<p>&nbsp;</p>
<table align="center">
<tbody><tr>
<td  bgcolor="red" id="showFightRedName">

  </br>
  <h:outputText value="#{adminNewaFightAction.fight.fighterRed.firstname}"/>
  <h:outputText value="&nbsp;" escape="false"/>
  <h:outputText value="#{adminNewaFightAction.fight.fighterRed.name}"/>
  </br></br>
  <span id="showFightRedPoints">      
  <h:inputText value="#{adminNewaFightAction.pointsRed}" id="pointsRed" required="false"
                     size="3" tabindex="1"
                     maxlength="3"/><jjw:codestableErrorMark field="pointsRed"/></span>
  </br></br>
</td>
<td  bgcolor="blue" id="showFightBlueName">
  </br>
  <h:outputText value="#{adminNewaFightAction.fight.fighterBlue.firstname}"/>
  <h:outputText value="&nbsp;" escape="false"/>
  <h:outputText value="#{adminNewaFightAction.fight.fighterBlue.name}"/>
  </br></br>
  <span id="showFightBluePoints">
  <h:inputText value="#{adminNewaFightAction.pointsBlue}" id="pointsBlue" required="false"
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
  <h:selectOneMenu id="winner" value="#{adminNewaFightAction.winner}" required="false" tabindex="3">
    <f:selectItems value="#{adminNewaFightAction.winList}"/>
  </h:selectOneMenu>
  <jjw:codestableErrorMark field="winner"/>
  <p>&nbsp;</p>
  <h:commandButton value="#{msg['save']}" action="#{adminNewaFightAction.save}" id="save" tabindex="4"/>
  <h:commandButton value="#{msg['abort']}" action="#{adminNewaFightAction.abort}" id="abort" tabindex="5"/>
  <p>&nbsp;</p>

  <h:commandLink id="open"
                 onclick="openNewaClockWindow(#{adminNewaFightAction.fight.id});">
    <t:graphicImage value="/images/display.png" title="#{msg['winnerIs']}" rendered="true" border="0" alt=""/>
    <t:htmlTag value="br"/>
    <h:outputText value="#{msg['openClock']}"/>
  </h:commandLink>


 
  <h:inputHidden id="fightTime" value="#{adminNewaFightAction.fight.fightTime}"/>
  <h:inputHidden id="injuryTimeRed" value="#{adminNewaFightAction.fight.injuryTimeRed}"/>
  <h:inputHidden id="injuryTimeBlue" value="#{adminNewaFightAction.fight.injuryTimeBlue}"/>
  <h:inputHidden id="fightTimeWithBreaks" value="#{adminNewaFightAction.fight.fightTimeWithBreaks}"/>
  <h:inputHidden id="overallFightTime" value="#{adminNewaFightAction.fight.overallFightTime}"/>

  <h:inputHidden id="shidoRed" value="#{adminNewaFightAction.fight.shidoRed}"/>
  <h:inputHidden id="shidoBlue" value="#{adminNewaFightAction.fight.shidoBlue}"/>
  <h:inputHidden id="chuiRed" value="#{adminNewaFightAction.fight.chuiRed}"/>
  <h:inputHidden id="chuiBlue" value="#{adminNewaFightAction.fight.chuiBlue}"/>
  <h:inputHidden id="hansokumakeRed" value="#{adminNewaFightAction.fight.hansokumakeRed}"/>
  <h:inputHidden id="hansokumakeBlue" value="#{adminNewaFightAction.fight.hansokumakeBlue}"/>
  <h:inputHidden id="protokoll" value="#{adminNewaFightAction.fight.protokoll}"/>
  <h:inputHidden id="fusengachi" value="#{adminNewaFightAction.fight.fusengachi}"/>
  <h:inputHidden id="kikengachi" value="#{adminNewaFightAction.fight.kikengachi}"/>

  <h:inputHidden id="pointsBlueOnClock" value="#{adminNewaFightAction.fight.pointsBlueOnClock}"/>
  <h:inputHidden id="pointsRedOnClock" value="#{adminNewaFightAction.fight.pointsRedOnClock}"/>

</h:form>
	
