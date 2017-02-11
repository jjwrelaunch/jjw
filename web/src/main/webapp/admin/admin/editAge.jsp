<%@ include file="/common/taglibs.jsp" %>
<%@ page import="de.jjw.webapp.IGlobalWebConstants" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : editAge.jsp
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

<h:form id="adminAgeAction">
  <f:loadBundle basename="de.jjw.webapp.messages.admin.age" var="msg"/>
  <t:htmlTag value="h3"><h:outputText value="#{msg['age.newAge.Headline'] }"/></t:htmlTag>
  <h:inputHidden id="id" value="#{adminAgeAction.age.id}"/>

  <t:panelGrid columns="2">
      <h:outputLabel for="ageShort" value="#{msg['ageId']}"/>
      <h:inputText value="#{adminAgeAction.age.ageShort}" id="ageShort" required="false" size="3" tabindex="1"
                       maxlength="3">
        <jjw:codestableErrorMark field="ageShort"/>
      </h:inputText>
      
      <h:outputLabel for="description" value="#{msg['ageDescription']}"/>
      <h:inputText value="#{adminAgeAction.age.description}" id="description" required="false" size="30"
                       tabindex="2" maxlength="30">
        <jjw:codestableErrorMark field="description"/>
      </h:inputText>    
    
      <h:outputLabel for="startAge" value="#{msg['ageStartAge']}"/>
      <h:inputText value="#{adminAgeAction.age.startAge}" id="startAge" required="false" size="4"  tabindex="3" maxlength="4">
        <jjw:codestableErrorMark field="startAge"/>
       </h:inputText>   
        
        <h:outputLabel for="ageLimit" value="#{msg['ageAgeLimit']}" />
      <h:inputText value="#{adminAgeAction.age.ageLimit}" id="ageLimit" required="false" size="4" tabindex="4" maxlength="4">
        <jjw:codestableErrorMark field="ageLimit" />    
      </h:inputText>
                
      <h:outputLabel for="fightingTime" value="#{msg['ageFightingTime']}" />
      <h:inputText value="#{adminAgeAction.age.fightingTime}" id="fightingTime" required="false" size="4" tabindex="5" maxlength="4">
        <jjw:codestableErrorMark field="fightingTime" />    
        </h:inputText>
      
      <h:outputLabel for="fightingTimeNewa" value="#{msg['ageFightingTimeNewa']}" />
      <h:inputText value="#{adminAgeAction.age.fightingTimeNewa}" id="fightingTimeNewa" required="false" size="4" tabindex="6" maxlength="4">
        <jjw:codestableErrorMark field="fightingTimeNewa" />        
      </h:inputText>
           
      <h:outputLabel for="fightingRounds" value="#{msg['ageFightingRounds']}" />
      <h:inputText value="#{adminAgeAction.age.fightingRounds}" id="fightingRounds" required="false" size="1" tabindex="7" maxlength="1">
        <jjw:codestableErrorMark field="fightingRounds" />
      </h:inputText>    
    
      <h:outputLabel for="overtime" value="#{msg['ageOvertime']}" />
      <h:inputText value="#{adminAgeAction.age.overtime}" id="overtime" required="false" size="4" tabindex="8" maxlength="4">
        <jjw:codestableErrorMark field="overtime" />
      </h:inputText>
      
      <h:outputLabel for="overtime" value="#{msg['ageInjurytime']}" />
      <h:inputText value="#{adminAgeAction.age.injurytime}" id="injurytime" required="false" size="4" tabindex="9" maxlength="4">
        <jjw:codestableErrorMark field="overtime" />
      </h:inputText>
          
      <h:outputLabel for="estimatedTime" value="#{msg['estimatedTime']}" />
      <h:inputText value="#{adminAgeAction.age.estimatedTime}" id="estimatedTime" required="false" size="5" tabindex="10" maxlength="5">
        <jjw:codestableErrorMark field="estimatedTime" />
      </h:inputText>
      
      <h:outputLabel for="estimatedTimeDuo" value="#{msg['estimatedTimeDuo']}" />
      <h:inputText value="#{adminAgeAction.age.estimatedTimeDuo}" id="estimatedTimeDuo" required="false" size="5" tabindex="11" maxlength="5">
        <jjw:codestableErrorMark field="estimatedTimeDuo" />
      </h:inputText>
      
       <h:outputLabel for="estimatedTimeNewa" value="#{msg['estimatedTimeNewa']}" />
      <h:inputText value="#{adminAgeAction.age.estimatedTimeNewa}" id="estimatedTimeNewa" required="false" size="5" tabindex="12" maxlength="5">
        <jjw:codestableErrorMark field="estimatedTimeNewa" />
      </h:inputText>        
    
      <%
        if ( request.getAttribute( IGlobalWebConstants.WEB_ADMIN_AGE_NEW ) != null )
        {
      %>
        <h:commandButton value="#{msg['ageAdd']}" action="#{adminAgeAction.addAge}" id="addAge" tabindex="20" />
        <%
        }
        else
        {
        %>
        <h:commandButton value="#{msg['ageEdit']}" action="#{adminAgeAction.editAge}" id="editAge" tabindex="21" />
        <%} %>
      
      
      
      <h:commandButton value="#{msg['ageAbort']}" action="#{adminAgeAction.gotoAllAges}" id="allAges" tabindex="22" />
 
  </t:panelGrid>
</h:form>