<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : createFightingclass.jsp
  ~ Created : 17 Jun 2010
  ~ Last Modified: Thu, 17 Jun 2010 14:59:50
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

<f:loadBundle basename="de.jjw.webapp.messages.admin.fightingclass" var="msg"/>
<t:htmlTag value="h3"><h:outputText value="#{msg['fightingclassHeadline']}"/></t:htmlTag>
<h:form id="adminFightingclassAction">

  <table width="800px">
    <tr>
      <td>
        <h:commandButton value="#{msg['fightingclassButton']}"
                         action="#{adminFightingclassAction.createFightingclass}"
                         id="createFightingclass" tabindex="1"/>
      </td>
    </tr>
  </table>
  
  <t:htmlTag value="h3"><h:outputText value="#{msg['fightingclassSuccess']}" rendered="#{adminFightingclassAction.createSuccessfull}"/></t:htmlTag>
</h:form>