<%@ include file="/common/taglibs.jsp" %>


<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : showUsers.jsp
  ~ Created : 17 Jun 2010
  ~ Last Modified: Thu, 17 Jun 2010 17:57:02
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

<h:form id="adminUserAction">
  <f:loadBundle basename="de.jjw.webapp.messages.admin.user" var="msg"/>
  <t:htmlTag value="h3"><h:outputText value="#{msg['usersHeadline'] }"/></t:htmlTag>
  <h:commandButton value="#{msg['gotoNewUser']}"
                   action="#{adminUserAction.gotoNewUser}" id="gotoNewUser" tabindex="3"/>

  <c:if test="${adminUserAction.users != null && fn:length(adminUserAction.users) > 0}">
    <t:dataTable value="#{adminUserAction.users}" var="cty" border="0" cellspacing="0" cellpadding="0" width="800px"
                 styleClass="fancyTable" rowClasses="odd,even" binding="#{adminUserAction.dataTable}">
      <t:column sortable="true" defaultSorted="true">
        <f:facet name="header">
          <h:outputText value="#{msg.username}"/>
        </f:facet>
        <h:commandLink action="#{adminUserAction.gotoEditUser}">
          <h:outputText value="#{cty.username}"/>
        </h:commandLink>
      </t:column>
      <t:column sortable="true">
        <f:facet name="header">
          <h:outputText value="#{msg.firstName}"/>
        </f:facet>
        <h:outputText value="#{cty.firstName}"/>
        <h:outputText value="&nbsp;" escape="false"/>
      </t:column>
      <t:column sortable="true">
        <f:facet name="header">
          <h:outputText value="#{msg.lastName}"/>
        </f:facet>
        <h:outputText value="#{cty.lastName}"/>
        <h:outputText value="&nbsp;" escape="false"/>
      </t:column>
      <t:column sortable="true">
        <f:facet name="header">
          <h:outputText value="#{msg.context}"/>
        </f:facet>
        <h:outputText value="#{cty.contextWeb}"/>
      </t:column>
      <t:column sortable="true">
        <f:facet name="header">
          <h:outputText value="#{msg.enable}"/>
        </f:facet>
        <t:graphicImage value="#{cty.enableWeb}" border="0"  alt=""/>
      </t:column>
      <%
        /* t:column sortable="true">
        <f:facet name="header">
          <h:outputText value="#{msg.accountLockedUntilDate}" />
        </f:facet>
        <h:outputText value="#{cty.accountLockedUntilDate}" >
          <f:convertDateTime pattern="HH:mm:ss  dd.MM.yyyy"  type="both" />
        </h:outputText>
      </t:column>
      <t:column sortable="true">
        <f:facet name="header">
          <h:outputText value="#{msg.accountExpireDate}" />
        </f:facet>
        <h:outputText value="#{cty.accountExpireDate}" >
          <f:convertDateTime pattern="HH:mm:ss  dd.MM.yyyy"  type="both" />
        </h:outputText>
      </t:column */
      %>


      <t:column styleClass="center">
        <f:facet name="header">
          <h:outputText value="&nbsp;" escape="false"/>
        </f:facet>
        <h:commandLink action="#{adminUserAction.deleteUser}">
          <t:graphicImage value="/images/trash.png" title="#{msg.delete}" rendered="true" border="0" alt=""/>
        </h:commandLink>
      </t:column>
    </t:dataTable>
  </c:if>
</h:form>

