<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : showFightsystem.jsp
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

<f:loadBundle basename="de.jjw.webapp.messages.admin.fightsystem" var="msg"/>
<t:htmlTag value="h3"><h:outputText value="#{msg['fightsystemHeadline'] }"/></t:htmlTag>
<h:form id="adminFightsystemAction">
  <h:outputText value="#{adminFightsystemAction.text}"/>

  <h:panelGrid columns="3">

    <h:outputText id="headFightsystem" value="#{msg['fightsystem']}"/>
    <h:outputText id="headMinPar" value="#{msg['minParticipants']}"/>
    <h:outputText id="headMaxPar" value="#{msg['maxParticipants']}"/>

    <h:outputText id="fsPool" value="#{msg['poolLabel']}"/>
    <h:inputText value="#{adminFightsystemAction.fightsystemPool.minParticipant}" id="fsPoolMinPar" required="false"
                 size="3" tabindex="1" maxlength="1"/>
    <h:inputText value="#{adminFightsystemAction.fightsystemPool.maxParticipant}" id="fsPoolMaxPar" required="false"
                 size="3" tabindex="2" maxlength="1"/>


    <h:outputText id="fsDPool" value="#{msg['dpoolLabel']}"/>
    <h:inputText value="#{adminFightsystemAction.fightsystemDPool.minParticipant}" id="fsDPoolMinPar" required="false"
                 size="3" tabindex="3" maxlength="1"/>
    <h:inputText value="#{adminFightsystemAction.fightsystemDPool.maxParticipant}" id="fsDPoolMaxPar" required="false"
                 size="3" tabindex="4" maxlength="2"/>

    <h:outputText id="fsKo" value="#{msg['koLabel']}"/>
    <h:inputText value="#{adminFightsystemAction.fightsystemKo.minParticipant}" id="fsKoMinPar" required="false"
                 size="3" tabindex="5" maxlength="2"/>
    <h:inputText value="#{adminFightsystemAction.fightsystemKo.maxParticipant}" id="fsKoMaxPar" required="false"
                 size="3" tabindex="6" maxlength="3"/>

  </h:panelGrid>

  <table>
    <td>
      <h:commandButton value="#{msg['save']}" id="save" action="#{adminFightsystemAction.save}"/>
    </td>

  </table>

</h:form>