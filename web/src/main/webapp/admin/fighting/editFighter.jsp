<%@ include file="/common/taglibs.jsp" %>
<%@ page import="de.jjw.webapp.IGlobalWebConstants" %>
<%@ page import="de.jjw.webapp.WebExchangeContextHelper" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : editFighter.jsp
  ~ Created : 17 Jun 2010
  ~ Last Modified: Thu, 17 Jun 2010 18:49:12
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

<f:loadBundle basename="de.jjw.webapp.messages.fighter" var="msg"/>
<t:htmlTag value="h3"><h:outputText value="#{msg['admin.inputFighterHeadline']}"/></t:htmlTag>

<h:outputText value="#{adminFighterAction.text}"/>

<h:form id="adminFighterAction">
  <h:inputHidden id="id" value="#{adminFighterAction.fighter.id}"/>
  <h:inputHidden id="markForExport" value="#{adminFighterAction.fighter.markForExport}"/>
  <h:inputHidden id="change" value=""/>
  <t:panelGrid columns="2">
    
      <h:outputLabel for="name" value="#{msg['name']}"/>      
      <h:inputText value="#{adminFighterAction.fighter.name}"
                     id="name" required="false" size="30" tabindex="1" maxlength="30" immediate="true">
        <jjw:codestableErrorMark field="name"/>
      </h:inputText>
     
      <h:outputLabel for="firstname" value="#{msg['firstname']}"/>
      <h:inputText value="#{adminFighterAction.fighter.firstname}" id="firstname" required="false" size="30"
                       tabindex="2" maxlength="30">
        <jjw:codestableErrorMark field="firstname"/>
      </h:inputText>
      
      <h:outputLabel for="yearOfBirth" value="#{msg['yearOfBirth']}"/>
      <h:selectOneMenu id="yearOfBirth" value="#{adminFighterAction.fighter.yearOfBirth}" required="false" tabindex="3">
        <f:selectItems value="#{adminFighterAction.yearOfBirthList}"/>
        <jjw:codestableErrorMark field="yearOfBirth"/>
      </h:selectOneMenu>
     
      <h:outputLabel for="country" value="#{msg['country']}"/>      
      <h:selectOneMenu id="country" value="#{adminFighterAction.country}" required="false" tabindex="3"
                         converter="jjw.webapp.converter.CountryConverter"
                         onchange="document.getElementById('main:adminFighterAction:change').value= 'change'; myevent=true;  document.getElementById('main:adminFighterAction:region').value= '-9223372036854775808'; document.getElementById('main:adminFighterAction:team').value= '-9223372036854775808';  document.getElementById('main:adminFighterAction:addFighter').click()">
        <f:selectItems value="#{adminFighterAction.countryListALL}"/>
        <jjw:codestableErrorMark field="country"/>
      </h:selectOneMenu>

      <h:outputLabel for="region" value="#{msg['region']}"/>
      <h:selectOneMenu id="region" value="#{adminFighterAction.region}" required="false" tabindex="4"
                           converter="jjw.webapp.converter.RegionConverterForFighter"
                           onchange="document.getElementById('main:adminFighterAction:change').value= 'change'; myevent=true; document.getElementById('main:adminFighterAction:team').value= '-9223372036854775808';document.getElementById('main:adminFighterAction:addFighter').click()">
        <f:selectItems value="#{adminFighterAction.regionList}"/>
        <jjw:codestableErrorMark field="region"/>
      </h:selectOneMenu>
     
      <h:outputLabel for="team" value="#{msg['team']}"/>      
      <h:selectOneMenu id="team" value="#{adminFighterAction.team}" required="false" tabindex="5"
                         converter="jjw.webapp.converter.TeamConverter">
          <f:selectItems value="#{adminFighterAction.teamList}"/>
      <jjw:codestableErrorMark field="team"/>
      </h:selectOneMenu>
      
      <h:outputLabel for="sex" value="#{msg['sex']}"/>
      <h:selectOneMenu id="sex" value="#{adminFighterAction.fighter.sex}" required="false" tabindex="6">
        <f:selectItems value="#{adminFighterAction.sexList}"/>
        <jjw:codestableErrorMark field="sex"/>
      </h:selectOneMenu>
          
      <h:outputLabel for="age" value="#{msg['age']}"/>
      <h:selectOneMenu id="age" value="#{adminFighterAction.fighter.age}" required="false" tabindex="7"
                           converter="jjw.webapp.converter.AgeConverter">
        <f:selectItems value="#{adminFighterAction.ageListALL}"/>
        <jjw:codestableErrorMark field="age"/>
      </h:selectOneMenu>
     
      <h:outputLabel for="weight" value="#{msg['weight']}"/>
      <h:inputText value="#{adminFighterAction.fighter.weight}"
                     id="weight" required="false" size="30" tabindex="8" maxlength="10">
       <jjw:codestableErrorMark field="weight"/>
      </h:inputText> 
      
      <h:outputLabel for="ready" value="#{msg['ready']}"/>
      <h:selectOneMenu id="ready" value="#{adminFighterAction.fighter.ready}" required="false" tabindex="9">
        <f:selectItems value="#{adminFighterAction.readyList}"/>
        <jjw:codestableErrorMark field="ready"/>
      </h:selectOneMenu>
     
      <h:outputText value="&nbsp;" escape="false"/>
      <h:outputText value="&nbsp;" escape="false"/>
            
        <%if ( request.getAttribute( IGlobalWebConstants.WEB_ADMIN_FIGHTER ) == null ) { %>
        <h:commandButton value="#{msg['edit']}"
                         onclick="if(myevent !=true) document.getElementById('main:adminFighterAction:change').value= '';"
                         action="#{adminFighterAction.addFighter}" id="addFighter" tabindex="10"/>
        <% } else  {  %>
       
        <h:commandButton value="#{msg['edit']}"
                         onclick="if(myevent !=true) document.getElementById('main:adminFighterAction:change').value= '';"
                         action="#{adminFighterAction.editFighter}" id="addFighter" tabindex="10"/>
        <%} %>
      
      <h:commandButton value="#{msg['abort']}" action="#{adminFighterAction.gotoAllFighter}" id="gotoAllFighter"
                           tabindex="11"/>
     
    <%if ( request.getAttribute( IGlobalWebConstants.WEB_ADMIN_FIGHTER ) == null ) {  %>
    
        <t:panelGroup colspan="2">       
        <h:commandButton value="#{msg['editAndNew']}"
                         onclick="if(myevent !=true) document.getElementById('adminFighterAction:change').value= '';"
                         action="#{adminFighterAction.addFighterAndNextTeammember}" id="addFighterAndNextTeammember"
                         tabindex="12"/>
        </t:panelGroup>
    <%}%>
  </t:panelGrid>
</h:form>

<script>
  jjw_setFocus( '${focus}' );
  jjw_resetValue( 'main:adminFighterAction:change' );
</script>