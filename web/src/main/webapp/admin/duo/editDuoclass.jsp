<%@ include file="/common/taglibs.jsp" %>
<%@ page import="de.jjw.webapp.IGlobalWebConstants" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : editDuoclass.jsp
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

<f:loadBundle basename="de.jjw.webapp.messages.admin.duoclass" var="msg"/>
<t:htmlTag value="h3"><h:outputText value="#{msg['duoclassNewHeadline']}"/></t:htmlTag>
<h:form id="adminDuoclassAction">
  <h:inputHidden id="id" value="#{adminDuoclassAction.duoclass.id}"/>
  <h:inputHidden id="change" value=""/>
 <t:panelGrid columns="2">
 
      <h:outputLabel for="age" value="#{msg['duoclassAge']}"/>
      <h:selectOneMenu id="age" value="#{adminDuoclassAction.duoclass.age}" required="false" tabindex="1"
                           converter="jjw.webapp.converter.AgeConverter"
                           onchange="document.getElementById('main:adminDuoclassAction:change').value= 'change'; myevent=true;  document.getElementById('main:adminDuoclassAction:addDuoclass').click()">
        <f:selectItems value="#{adminDuoclassAction.ageListALL}"/>
        <jjw:codestableErrorMark field="age"/>
      </h:selectOneMenu>
      
      <h:outputLabel for="sex" value="#{msg['duoclassSex']}"/>
      <h:selectOneMenu id="sex" value="#{adminDuoclassAction.duoclass.sex}" required="false" tabindex="2">
        <f:selectItems value="#{adminDuoclassAction.sexList}"/>
        <jjw:codestableErrorMark field="sex"/>
      </h:selectOneMenu>
          
      <h:outputLabel for="duoclass" value="#{msg['duoclassClassName']}"/>
      <h:inputText value="#{adminDuoclassAction.duoclass.duoclassName}" id="duoclassName" required="false" size="30"
                       tabindex="5" maxlength="30">
        <jjw:codestableErrorMark field="duoclassName"/>
      </h:inputText>
      
      <h:outputText value="&nbsp;" escape="false"/>
      <h:outputText value="&nbsp;" escape="false"/>   
      
        <% if ( request.getAttribute( IGlobalWebConstants.WEB_ADMIN_DUOCLASS_NEW ) != null )  {    %>
        <h:commandButton value="#{msg['edit']}" action="#{adminDuoclassAction.addDuoclass}" id="addDuoclass"
                         tabindex="6"
                         onclick="if(myevent !=true) document.getElementById('main:adminDuoclassAction:change').value= '';"/>
        <% }        else        {     %>
        <h:commandButton value="#{msg['edit']}" action="#{adminDuoclassAction.editDuoclass}" id="addDuoclass"
                         tabindex="6"
                         onclick="if(myevent !=true) document.getElementById('main:adminDuoclassAction:change').value= '';"/>
        <%} %>
      
      <h:commandButton value="#{msg['abort']}" action="#{adminDuoclassAction.gotoAllDuoclasses}"
                           id="gotoAllDuoclasses" tabindex="7"/>                                 
  </t:panelGrid>
  <br/><br/>
</h:form>

<c:if test="${ (adminDuoclassAction.duoclassesByAge != null && fn:length(adminDuoclassAction.duoclassesByAge) > 0)}">
  <t:htmlTag value="h3"><h:outputText value="#{msg['duoclassHeadlineForYouth']}"/></t:htmlTag>
  <h:dataTable value="#{adminDuoclassAction.duoclassesByAge}" var="cty" border="0" cellspacing="0" cellpadding="0"
               styleClass="fancyTable" rowClasses="odd,even" binding="#{adminDuoclassAction.dataTable}" width="300px">
    <h:column>
      <f:facet name="header">
        <h:outputText value="#{msg.duoclassAge}"/>
      </f:facet>
      <h:outputText value="#{cty.age.ageShort}"/>
    </h:column>
    <h:column>
      <f:facet name="header">
        <h:outputText value="#{msg.duoclassSex}"/>
      </f:facet>
      <h:outputText value="#{cty.sexWeb}"/>
    </h:column>
    <h:column>
      <f:facet name="header">
        <h:outputText value="#{msg.duoclass}"/>
      </f:facet>
      <h:outputText value="#{cty.duoclassName}"/>
    </h:column>
  </h:dataTable>
</c:if>
<script>
  jjw_setFocus( '${focus}' );
  jjw_resetValue( 'main:adminDuoclassAction:change' );
</script>