<%@ include file="/common/taglibs.jsp" %>
<%@ page import="de.jjw.webapp.IGlobalWebConstants" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : editWeightclass.jsp
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

<f:loadBundle basename="de.jjw.webapp.messages.admin.weightclass" var="msg"/>
<t:htmlTag value="h3"><h:outputText value="#{msg['weightclassNewHeadline']}"/></t:htmlTag>
<h:form id="adminWeightclassAction">
  <h:inputHidden id="id" value="#{adminWeightclassAction.weightclass.id}"/>
  <h:inputHidden id="change" value=""/>
  
  <t:panelGrid columns="2">
    <h:outputLabel for="age" value="#{msg['weightclassAge']}"/>
    <h:selectOneMenu id="age" value="#{adminWeightclassAction.weightclass.age}" required="false" tabindex="1"
                           converter="jjw.webapp.converter.AgeConverter"
                           onchange="document.getElementById('main:adminWeightclassAction:change').value= 'change'; myevent=true;  document.getElementById('main:adminWeightclassAction:addWeightclass').click()">
        <f:selectItems value="#{adminWeightclassAction.ageListALL}"/>
        <jjw:codestableErrorMark field="age"/>
    </h:selectOneMenu>
 
    <h:outputLabel for="sex" value="#{msg['weightclassSex']}"/>
      <h:selectOneMenu id="sex" value="#{adminWeightclassAction.weightclass.sex}" required="false" tabindex="2">
        <f:selectItems value="#{adminWeightclassAction.sexList}"/>
        <jjw:codestableErrorMark field="sex"/>
    </h:selectOneMenu>
    
    <h:outputLabel for="startWeight" value="#{msg['editWeightclassStartWeight']}"/>     
    <h:inputText value="#{adminWeightclassAction.weightclass.startWeight}"
                     id="startWeight" required="false" size="30" tabindex="3" maxlength="10">
        <jjw:codestableErrorMark field="startWeight"/>
    </h:inputText>
      
    <h:outputLabel for="weightLimit" value="#{msg['editWeightclassWeightLimit']}"/>
    <h:inputText value="#{adminWeightclassAction.weightclass.weightLimit}" id="weightLimit" required="false"
                       size="30" tabindex="4" maxlength="10">
      <jjw:codestableErrorMark field="weightLimit"/>
      </h:inputText>
    
      <h:outputLabel for="weightclass" value="#{msg['weightclassClassName']}"/>
      <h:inputText value="#{adminWeightclassAction.weightclass.weightclass}" id="weightclass" required="false"
                       size="30" tabindex="5" maxlength="30">
        <jjw:codestableErrorMark field="weightclass"/>
      </h:inputText>
      
      <h:outputText value="&nbsp;" escape="false"/>
      <h:outputText value="&nbsp;" escape="false"/>
     
        <% if ( request.getAttribute( IGlobalWebConstants.WEB_ADMIN_WEIGHTCLASS_NEW ) != null ) {    %>
        <h:commandButton value="#{msg['edit']}" action="#{adminWeightclassAction.addWeightclass}" id="addWeightclass"
                         tabindex="6"
                         onclick="if(myevent !=true) document.getElementById('main:adminWeightclassAction:change').value= '';"/>
        <% }        else    {   %>
        <h:commandButton value="#{msg['edit']}" action="#{adminWeightclassAction.editWeightclass}" id="addWeightclass"
                         tabindex="6"
                         onclick="if(myevent !=true) document.getElementById('main:adminWeightclassAction:change').value= '';"/>
        <%} %>
    
      <h:commandButton value="#{msg['abort']}" action="#{adminWeightclassAction.gotoAllWeightclasses}"
                           id="gotoAllWeightclasses" tabindex="7"/>
     
  </t:panelGrid>
  <br/><br/>
</h:form>

<c:if
    test="${ (adminWeightclassAction.weightclassesByAge != null && fn:length(adminWeightclassAction.weightclassesByAge) > 0)}">
  <t:htmlTag value="h3"><h:outputText value="#{msg['weightclassHeadlineForYouth']}"/></t:htmlTag>
  <h:dataTable value="#{adminWeightclassAction.weightclassesByAge}" var="cty" border="0" cellspacing="0" cellpadding="0"
               styleClass="fancyTable" rowClasses="odd,even" binding="#{adminWeightclassAction.dataTable}" width="300px">
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
  jjw_resetValue( 'main:adminWeightclassAction:change' );
</script>