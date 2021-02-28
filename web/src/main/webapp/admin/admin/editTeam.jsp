<%@ include file="/common/taglibs.jsp" %>
<%@ page import="de.jjw.webapp.IGlobalWebConstants" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : editTeam.jsp
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

<f:loadBundle basename="de.jjw.webapp.messages.extern.Team" var="msg"/>
<t:htmlTag value="h3"><h:outputText value="#{msg['registerHeadline']}"/></t:htmlTag>
<h:form id="adminTeamAction" enctype="multipart/form-data">
  <h:inputHidden id="id" value="#{adminTeamAction.team.id}"/>
  <h:inputHidden id="change" value=""/>
    <t:htmlTag value="h3"><h:outputText value="#{msg['teamHeadlineTeamData'] }"/></t:htmlTag>
  <t:panelGrid columns="3">
      
      <h:outputLabel for="teamName" value="#{msg['teamName']}"/>
      <h:outputText value=""/>
      <h:inputText value="#{adminTeamAction.team.teamName}" id="teamName" required="false" size="30" tabindex="2"
                       maxlength="30">
      	<jjw:codestableErrorMark field="teamName"/>
	  </h:inputText>                       
                       
      <h:outputLabel for="country" value="#{msg['country']}"/>
	  <h:outputText value=""/>
      <h:selectOneMenu id="country" value="#{adminTeamAction.team.country}" required="false" tabindex="3"
                           converter="jjw.webapp.converter.CountryConverter"
                           onchange="document.getElementById('main:adminTeamAction:change').value= 'change'; myevent=true; document.getElementById('main:adminTeamAction:region').value= '-9223372036854775808'; document.getElementById('main:adminTeamAction:addTeam').click()">
        <f:selectItems value="#{adminTeamAction.countryListALL}"/>
        <jjw:codestableErrorMark field="country"/>
      </h:selectOneMenu>         
      
      <h:outputLabel for="region" value="#{msg['region']}"/>
      <h:outputText value=""/>
      <h:selectOneMenu id="region" value="#{adminTeamAction.team.region}" required="false" tabindex="4"
                           converter="jjw.webapp.converter.RegionConverter">
        <f:selectItems value="#{adminTeamAction.regionListALL}"/>
        <jjw:codestableErrorMark field="region"/>
      </h:selectOneMenu>
      
     <h:outputLabel for="externId" value="#{msg['externId']}"/>
      <h:outputText value=""/>
      <h:inputText value="#{adminTeamAction.team.externId}" id="externId" required="false" size="10" tabindex="2"
                       maxlength="10">
      	<jjw:codestableErrorMark field="externId"/>
	  </h:inputText>    
     
     
    <c:if test="${adminTeamAction.imageCommand != null}">
      
      <t:graphicImage value="#{adminTeamAction.imageCommand}" border="0" alt=""/>
      <h:outputText value=""/>
      <h:outputText value=""/>
      
      <h:outputLabel for="delete" value="#{msg['delete']}"/>
      <h:outputText value=""/>   
      <h:selectBooleanCheckbox id="delete" value="#{adminTeamAction.delete}"/>	        
    </c:if>
    
        <h:outputLabel for="uFile" value="#{msg['logo']}"/>
     <h:outputText value=""/>
        <t:inputFileUpload id="uFile" value="#{adminTeamAction.logoFile}"/>
   
   	 <t:panelGroup colspan="3">
      <h:outputText value="#{msg['logoAdvise']}"/>
      </t:panelGroup>
       
       <h:outputText value="&nbsp;" escape="false"/>
     <h:outputText value="&nbsp;" escape="false"/>
     <h:outputText value="&nbsp;" escape="false"/>
       
        <%
          if ( request.getAttribute( IGlobalWebConstants.WEB_ADMIN_TEAM_NEW ) != null )
          {
        %>
        <h:commandButton value="#{msg['admin.teamEdit']}" action="#{adminTeamAction.addTeam}" id="addTeam" tabindex="17"
                         onclick="if(myevent !=true) document.getElementById('main:adminTeamAction:change').value= '';"/>
        <%
        }
        else
        {
        %>
        <h:commandButton value="#{msg['admin.teamEdit']}" action="#{adminTeamAction.editTeam}" id="addTeam"
                         tabindex="17"
                         onclick="if(myevent !=true) document.getElementById('main:adminTeamAction:change').value= '';"/>
        <%} %>
     <h:outputText value=""/>
     <h:outputText value=""/>
     
     <h:commandButton value="#{msg['admin.teamAbort']}" action="#{adminTeamAction.gotoAllTeams}" id="showTeam"
                           tabindex="18"/>
     <h:outputText value=""/>
     <h:outputText value=""/>
     </t:panelGrid>   
</h:form>
<script>
  jjw_setFocus( '${focus}' );
  jjw_resetValue( 'main:adminTeamAction:change' );
</script>