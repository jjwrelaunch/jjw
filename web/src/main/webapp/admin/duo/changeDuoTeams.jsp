<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : changeDuoTeams.jsp
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

<f:loadBundle basename="de.jjw.webapp.messages.duoTeam" var="msg"/>
<t:htmlTag value="h3"><h:outputText value="#{msg['changeDuoTeamHeadline'] }"/></t:htmlTag>
<h:form id="adminChangeDuoTeamAction">
  <h:inputHidden id="duoclassId" value="#{adminChangeDuoTeamAction.duoclassId}"/>

  <table>
    <tr>
      <td><h:outputText value="#{msg['duoTeamNumber1']}"/></td>
      <td><h:outputText value="#{msg['duoTeamNumber2']}"/></td>
    </tr>
    <tr>
      <td><h:inputText value="#{adminChangeDuoTeamAction.duoTeam1}" id="duoTeam1" required="false" size="3" tabindex="1"
                       maxlength="3"/><jjw:codestableErrorMark field="duoTeam1"/></td>
      <td><h:inputText value="#{adminChangeDuoTeamAction.duoTeam2}" id="duoTeam2" required="false" size="3" tabindex="1"
                       maxlength="3"/><jjw:codestableErrorMark field="duoTeam2"/></td>
    </tr>
  </table>
  <p>&nbsp;</p>
  <h:commandButton value="#{msg['changeDuoTeams']}" action="#{adminChangeDuoTeamAction.changeFights}" id="changeFights"
                   tabindex="4"/>
  <p>&nbsp;</p>
</h:form>