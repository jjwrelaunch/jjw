<%@ include file="/common/taglibs.jsp" %>
<%@ page import="de.jjw.webapp.IGlobalWebConstants" %>
<%@ page import="de.jjw.webapp.WebExchangeContextHelper" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : editNotRegDuoTeam.jsp
  ~ Created : 24 Jul 2011
  ~ Last Modified: Sun, 24 Jul 2011 14:59:49
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
<t:htmlTag value="h3"><h:outputText value="#{msg['admin.inputDuoTeamHeadline']}"/></t:htmlTag>
<h:outputText value="#{adminDuoTeamAction.text}"/>

<h:form id="adminDuoTeamAction">
  <h:inputHidden id="id" value="#{adminDuoTeamAction.duoTeam.id}"/>
  <h:inputHidden id="change" value=""/>
  <t:panelGrid columns="2">
 	  <h:outputLabel for="name" value="#{msg['name']}"/>      
      <h:inputText value="#{adminDuoTeamAction.duoTeam.name}"
                     id="name" required="false" size="30" tabindex="1" maxlength="30" immediate="true">
         <jjw:codestableErrorMark field="name"/>
      </h:inputText>
     
      <h:outputLabel for="firstname" value="#{msg['firstname']}"/>
      <h:inputText value="#{adminDuoTeamAction.duoTeam.firstname}" id="firstname" required="false" size="30"
                       tabindex="2" maxlength="30">
        <jjw:codestableErrorMark field="firstname"/>
      </h:inputText>
      
      <h:outputLabel for="name2" value="#{msg['name']}"/>      
      <h:inputText value="#{adminDuoTeamAction.duoTeam.name2}"
                     id="name2" required="false" size="30" tabindex="3" maxlength="30" immediate="true">
        <jjw:codestableErrorMark field="name2"/>
      </h:inputText>
      
      <h:outputLabel for="firstname2" value="#{msg['firstname']}"/>
      <h:inputText value="#{adminDuoTeamAction.duoTeam.firstname2}" id="firstname2" required="false" size="30"
                       tabindex="4" maxlength="30">
        <jjw:codestableErrorMark field="firstname2"/>
      </h:inputText>
          
      <h:outputLabel for="country" value="#{msg['country']}"/>      
      <h:selectOneMenu id="country" value="#{adminDuoTeamAction.country}" required="false" tabindex="5"
                         converter="jjw.webapp.converter.CountryConverter"
                         onchange="document.getElementById('main:adminDuoTeamAction:change').value= 'change'; myevent=true;  document.getElementById('main:adminDuoTeamAction:region').value= '-9223372036854775808'; document.getElementById('main:adminDuoTeamAction:team').value= '-9223372036854775808';  document.getElementById('main:adminDuoTeamAction:addDuoTeam').click()">
          <f:selectItems value="#{adminDuoTeamAction.countryListALL}"/>
          <jjw:codestableErrorMark field="country"/>
      </h:selectOneMenu>
          
      <h:outputLabel for="region" value="#{msg['region']}"/>
      <h:selectOneMenu id="region" value="#{adminDuoTeamAction.region}" required="false" tabindex="6"
                           converter="jjw.webapp.converter.RegionConverterForDuoTeam"
                           onchange="document.getElementById('main:adminDuoTeamAction:change').value= 'change'; myevent=true; document.getElementById('main:adminDuoTeamAction:team').value= '-9223372036854775808';document.getElementById('main:adminDuoTeamAction:addDuoTeam').click()">
        <f:selectItems value="#{adminDuoTeamAction.regionList}"/>
        <jjw:codestableErrorMark field="region"/>
      </h:selectOneMenu>      
    
      <h:outputLabel for="team" value="#{msg['team']}"/>      
      <h:selectOneMenu id="team" value="#{adminDuoTeamAction.team}" required="false" tabindex="7"
                         converter="jjw.webapp.converter.TeamConverter">
          <f:selectItems value="#{adminDuoTeamAction.teamList}"/>
          <jjw:codestableErrorMark field="team"/>
      </h:selectOneMenu>
      
      <h:outputLabel for="sex" value="#{msg['sex']}"/>
      <h:selectOneMenu id="sex" value="#{adminDuoTeamAction.duoTeam.sex}" required="false" tabindex="8">
        <f:selectItems value="#{adminDuoTeamAction.sexList}"/>
        <jjw:codestableErrorMark field="sex"/>
      </h:selectOneMenu>      
   
      <h:outputLabel for="age" value="#{msg['age']}"/>
      <h:selectOneMenu id="age" value="#{adminDuoTeamAction.duoTeam.age}" required="false" tabindex="9"
                           converter="jjw.webapp.converter.AgeConverter">
        <f:selectItems value="#{adminDuoTeamAction.ageListALL}"/>
        <jjw:codestableErrorMark field="age"/>
      </h:selectOneMenu>
      
      <h:outputLabel for="ready" value="#{msg['ready']}"/>
      <h:selectOneMenu id="ready" value="#{adminDuoTeamAction.duoTeam.ready}" required="false" tabindex="10">
        <f:selectItems value="#{adminDuoTeamAction.readyList}"/>
        <jjw:codestableErrorMark field="ready"/>
      </h:selectOneMenu>
      
      <h:outputText value="&nbsp;" escape="false"/>
      <h:outputText value="&nbsp;" escape="false"/>
      
     
        <h:commandButton value="#{msg['edit']}"
                         onclick="if(myevent !=true) document.getElementById('main:adminDuoTeamAction:change').value= '';"
                         action="#{adminDuoTeamAction.editNotRegDuoTeam}" id="editDuoTeam" tabindex="11"/> 
      
      <h:commandButton value="#{msg['abort']}" action="#{adminDuoTeamAction.gotoNotRegDuoTeams}" id="gotoNotRegDuoTeams"
                           tabindex="12"/>     
    
  
  </t:panelGrid>
</h:form>
<script>
  jjw_setFocus( '${focus}' );
  jjw_resetValue( 'main:adminDuoTeamAction:change' );
</script>