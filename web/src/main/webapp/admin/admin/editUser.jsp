<%@ include file="/common/taglibs.jsp" %>
<%@ page import="de.jjw.webapp.IGlobalWebConstants" %>


<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : editUser.jsp
  ~ Created : 05 Jun 2010
  ~ Last Modified: Sat, 05 Jun 2010 21:02:55
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

<f:loadBundle basename="de.jjw.webapp.messages.admin.user" var="msg"/>
<t:htmlTag value="h3"><h:outputText value="#{msg['userHeadlineEdit'] }"/></t:htmlTag>
<p >
<h:form id="adminUserAction">
  <h:inputHidden id="id" value="#{adminUserAction.user.id}"/>
  <t:panelGrid columns="2">
    
      <h:outputLabel for="username" value="#{msg['username']}"/>      
      <h:inputText value="#{adminUserAction.user.username}" id="username" required="false" size="30" tabindex="1">
        <jjw:codestableErrorMark field="username"/>
      </h:inputText>    
    
      <h:outputLabel for="password" value="#{msg['password']}"/>
      <h:inputSecret value="#{adminUserAction.user.password}" id="password" required="false" size="30"
                         tabindex="2">
        <jjw:codestableErrorMark field="password"/>
      </h:inputSecret>
    
      <h:outputLabel for="confirmPassword" value="#{msg['confirmPassword']}"/>
      <h:inputSecret value="#{adminUserAction.user.confirmPassword}" id="confirmPassword" required="false" size="30"
                         tabindex="3">
        <jjw:codestableErrorMark field="confirmPassword"/>
      </h:inputSecret>
    
      <h:outputLabel for="firstName" value="#{msg['firstName']}"/>
      <h:inputText value="#{adminUserAction.user.firstName}" id="firstName" required="false" size="30"
                       tabindex="4">
        <jjw:codestableErrorMark field="firstName"/>
      </h:inputText>     
    
      <h:outputLabel for="lastName" value="#{msg['lastName']}"/>
      <h:inputText value="#{adminUserAction.user.lastName}" id="lastName" required="false" size="30" tabindex="5">
        <jjw:codestableErrorMark field="lastName"/>
      </h:inputText>
    
      <h:outputLabel for="context" value="#{msg['context']}"/>
      <h:selectOneMenu id="context" value="#{adminUserAction.user.context}" required="false" tabindex="6">
        <f:selectItems value="#{adminUserAction.contextList}"/>
        <jjw:codestableErrorMark field="context"/>
      </h:selectOneMenu>
            
      <h:outputLabel for="enable" value="#{msg['enable']}"/>
      <h:selectOneMenu id="enable" value="#{adminUserAction.user.enabled}" required="false" tabindex="7">
        <f:selectItems value="#{adminUserAction.yesNoList}"/>
        <jjw:codestableErrorMark field="enable"/>
      </h:selectOneMenu>
     
      <%
        if ( request.getAttribute( IGlobalWebConstants.WEB_ADMIN_USER_NEW ) != null )
        {
      %>
        <h:commandButton value="#{msg['addUser']}" action="#{adminUserAction.addUser}" id="addUser" tabindex="30"/>
        <%
        }
        else
        {
        %>
        <h:commandButton value="#{msg['editUser']}" action="#{adminUserAction.editUser}" id="editUser" tabindex="30"/>
        <%} %>
      <h:commandButton value="#{msg['userAbort']}" action="#{adminUserAction.gotoAllUsers}" id="showUser"
                           tabindex="40"/>
    
  </t:panelGrid>
</h:form>
</p>

	