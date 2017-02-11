<%@ include file="/common/taglibs.jsp" %>
<%@ page import="de.jjw.webapp.IGlobalWebConstants" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : editNewaWeightclass.jsp
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

<f:loadBundle basename="de.jjw.webapp.messages.admin.newaWeightclass" var="msg"/>
<t:htmlTag value="h3"><h:outputText value="#{msg['weightclassNewHeadline']}"/></t:htmlTag>
<h:form id="adminNewaWeightclassAction">
  <h:inputHidden id="id" value="#{adminNewaWeightclassAction.newaclass.id}"/>
  <h:inputHidden id="change" value=""/>
  
  <t:panelGrid columns="2">
    <h:outputLabel for="age" value="#{msg['weightclassAge']}"/>
    <h:selectOneMenu id="age" value="#{adminNewaWeightclassAction.newaclass.age}" required="false" tabindex="1"
                           converter="jjw.webapp.converter.AgeConverter"
                           onchange="document.getElementById('main:adminNewaWeightclassAction:change').value= 'change'; myevent=true;  document.getElementById('main:adminNewaWeightclassAction:addWeightclass').click()">
        <f:selectItems value="#{adminNewaWeightclassAction.ageListALL}"/>
        <jjw:codestableErrorMark field="age"/>
    </h:selectOneMenu>
 
    <h:outputLabel for="sex" value="#{msg['weightclassSex']}"/>
      <h:selectOneMenu id="sex" value="#{adminNewaWeightclassAction.newaclass.sex}" required="false" tabindex="2">
        <f:selectItems value="#{adminNewaWeightclassAction.sexList}"/>
        <jjw:codestableErrorMark field="sex"/>
    </h:selectOneMenu>
    
    <h:outputLabel for="startWeight" value="#{msg['editWeightclassStartWeight']}"/>     
    <h:inputText value="#{adminNewaWeightclassAction.newaclass.startWeight}"
                     id="startWeight" required="false" size="30" tabindex="3" maxlength="10">
        <jjw:codestableErrorMark field="startWeight"/>
    </h:inputText>
      
    <h:outputLabel for="weightLimit" value="#{msg['editWeightclassWeightLimit']}"/>
    <h:inputText value="#{adminNewaWeightclassAction.newaclass.weightLimit}" id="weightLimit" required="false"
                       size="30" tabindex="4" maxlength="10">
      <jjw:codestableErrorMark field="weightLimit"/>
      </h:inputText>
    
      <h:outputLabel for="weightclass" value="#{msg['weightclassClassName']}"/>
      <h:inputText value="#{adminNewaWeightclassAction.newaclass.weightclass}" id="weightclass" required="false"
                       size="30" tabindex="5" maxlength="30">
        <jjw:codestableErrorMark field="weightclass"/>
      </h:inputText>
      
      <h:outputText value="&nbsp;" escape="false"/>
      <h:outputText value="&nbsp;" escape="false"/>
     
        <% if ( request.getAttribute( IGlobalWebConstants.WEB_ADMIN_WEIGHTCLASS_NEW ) != null ) {    %>
        <h:commandButton value="#{msg['edit']}" action="#{adminNewaWeightclassAction.addWeightclass}" id="addWeightclass"
                         tabindex="6"
                         onclick="if(myevent !=true) document.getElementById('main:adminNewaWeightclassAction:change').value= '';"/>
        <% }        else    {   %>
        <h:commandButton value="#{msg['edit']}" action="#{adminNewaWeightclassAction.editWeightclass}" id="addWeightclass"
                         tabindex="6"
                         onclick="if(myevent !=true) document.getElementById('main:adminNewaWeightclassAction:change').value= '';"/>
        <%} %>
    
      <h:commandButton value="#{msg['abort']}" action="#{adminNewaWeightclassAction.gotoAllWeightclasses}"
                           id="gotoAllWeightclasses" tabindex="7"/>
     
  </t:panelGrid>
  <br/><br/>
</h:form>

<c:if
    test="${ (adminNewaWeightclassAction.newaclassesByAge != null && fn:length(adminNewaWeightclassAction.newaclassesByAge) > 0)}">
  <t:htmlTag value="h3"><h:outputText value="#{msg['weightclassHeadlineForYouth']}"/></t:htmlTag>
  <h:dataTable value="#{adminNewaWeightclassAction.newaclassesByAge}" var="cty" border="0" cellspacing="0" cellpadding="0"
               styleClass="fancyTable" rowClasses="odd,even" binding="#{adminNewaWeightclassAction.dataTable}" width="300px">
    <h:column>
      <f:facet name="header">
        <h:outputText value="#{msg.weightclassAge}"/>
      </f:facet>
      <h:outputText value="#{cty.age.ageShort}"/>
    </h:column>
    <h:column>
      <f:facet name="header">
        <h:outputText value="#{msg.weightclassSex}"/>
      </f:facet>
      <h:outputText value="#{cty.sexWeb}"/>
    </h:column>
    <h:column>
      <f:facet name="header">
        <h:outputText value="#{msg.weightclass}"/>
      </f:facet>
      <h:outputText value="#{cty.weightclass}"/>
    </h:column>
    <h:column>
      <f:facet name="header">
        <h:outputText value="#{msg.weightclassStartWeight}"/>
      </f:facet>
      <h:outputText value="#{cty.startWeight}"/>
    </h:column>
    <h:column>
      <f:facet name="header">
        <h:outputText value="#{msg.weightclassWeightLimit}"/>
      </f:facet>
      <h:outputText value="#{cty.weightLimit}"/>
    </h:column>
  </h:dataTable>
</c:if>

<script>
  jjw_setFocus( '${focus}' );
  jjw_resetValue( 'main:adminNewaWeightclassAction:change' );
</script>