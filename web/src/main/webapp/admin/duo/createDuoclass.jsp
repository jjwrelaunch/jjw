<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : createDuoclass.jsp
  ~ Created : 17 Jun 2010
  ~ Last Modified: Thu, 17 Jun 2010 14:59:49
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

<f:loadBundle basename="de.jjw.webapp.messages.admin.duoclass" var="msg"/>
<t:htmlTag value="h3"><h:outputText value="#{msg['duoclassHeadline']}"/></t:htmlTag>
<h:form id="adminDuoclassAction">

  <table width="800px">
    <tr>
      <td>
        <h:commandButton value="#{msg['duoclassButton']}"
                         action="#{adminDuoclassAction.createDuoclass}"
                         id="createDuoclass" tabindex="1"/>
      </td>
    </tr>
  </table>
  <t:htmlTag value="h3"><h:outputText value="#{msg['duoclassSuccess']}" rendered="#{adminDuoclassAction.createSuccessfull}"/></t:htmlTag>
</h:form>