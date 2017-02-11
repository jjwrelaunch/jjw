<%@ include file="/common/taglibs.jsp" %>
<%@ page import="de.jjw.webapp.IGlobalWebConstants" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : uploadTeam.jsp
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
<t:htmlTag value="h3"><h:outputText value="#{msg['uploadHeadlineTeam']}"/><h:outputText value="&nbsp;" escape="false"/><h:outputText value="#{adminUploadTeamAction.team.teamName}"/></t:htmlTag>
<h:form id="adminUploadTeamAction" enctype="multipart/form-data">
  <h:inputHidden id="teamId" value="#{adminUploadTeamAction.teamId}"/>
 
  <t:panelGrid columns="3">
           
     <h:outputLabel for="uFile2" value="#{msg['upload']}"/>
     <h:outputText value=""/>
     <t:inputFileUpload id="uFile2" value="#{adminUploadTeamAction.registerFile}"/> 
     
     <h:commandButton value="#{msg['uploadFile']}" action="#{adminUploadTeamAction.uploadTeam}" id="uploadTeam"/>
      <h:outputText value="&nbsp;" escape="false"/>
     <h:outputText value="&nbsp;" escape="false"/>
       
       
       <h:outputText value="&nbsp;" escape="false"/>
     <h:outputText value="&nbsp;" escape="false"/>
     <h:outputText value="&nbsp;" escape="false"/>
      
     
     </t:panelGrid>
     <c:if test="${adminUploadTeamAction.fighterList != null}">
     <t:htmlTag value="h3"><h:outputText value="#{msg['fighting']}"/></t:htmlTag>
      
     <t:dataTable 
                   value="#{adminUploadTeamAction.fighterList}" var="cty" border="0"
                   cellspacing="0" cellpadding="0" id="showFighterTable" forceId="true"
                   styleClass="fancyTable" rowClasses="odd,even" width="800px"
                   binding="#{adminUploadTeamAction.dataTable}" >

        <t:column sortable="false" defaultSorted="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="1" arrow="true">
              <h:outputText value="#{msg.name}"/>
            </t:commandSortHeader>
          </f:facet>          
            <h:outputText value="#{cty.name}"/>
        </t:column>
        <t:column sortable="false">
          <f:facet name="header">
            <t:commandSortHeader columnName="2" arrow="true">
              <h:outputText value="#{msg.firstname}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.firstname}"/>
        </t:column>     
        <t:column sortable="false">
          <f:facet name="header">
            <t:commandSortHeader columnName="4" arrow="true">
              <h:outputText value="#{msg.age}"/>
            </t:commandSortHeader>
          </f:facet>         
            <h:outputText value="#{cty.age.ageShort}"/>         
        </t:column>
        <t:column sortable="false">
          <f:facet name="header">
            <t:commandSortHeader columnName="5" arrow="true">
              <h:outputText value="#{msg.sex}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.sexWeb}"/>
        </t:column>            
        <t:column sortable="false">
          <f:facet name="header">
            <t:commandSortHeader columnName="8" arrow="true">
              <h:outputText value="#{msg.weight}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.weight}"/>
        </t:column>       
      </t:dataTable>
      </c:if>
      
      <p>&nbsp;</p>
      
       <c:if test="${adminUploadTeamAction.duoteamList != null}">
       <t:htmlTag value="h3"><h:outputText value="#{msg['duo']}"/></t:htmlTag>
     <t:dataTable 
                   value="#{adminUploadTeamAction.duoteamList}" var="cty2" border="0"
                   cellspacing="0" cellpadding="0" id="showDuoTeamTable" forceId="true"
                   styleClass="fancyTable" rowClasses="odd,even" width="800px"
                   binding="#{adminUploadTeamAction.dataTable2}" >
 		
        <t:column sortable="false" defaultSorted="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="1" arrow="true">
              <h:outputText value="#{msg.name}"/>
            </t:commandSortHeader>
          </f:facet>          
            <h:outputText value="#{cty2.name}"/>
        </t:column>
        <t:column sortable="false">
          <f:facet name="header">
            <t:commandSortHeader columnName="2" arrow="true">
              <h:outputText value="#{msg.firstname}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty2.firstname}"/>
        </t:column>     
        <t:column sortable="false" defaultSorted="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="1b" arrow="true">
              <h:outputText value="#{msg.name}"/>
            </t:commandSortHeader>
          </f:facet>          
            <h:outputText value="#{cty2.name2}"/>
        </t:column>
        <t:column sortable="false">
          <f:facet name="header">
            <t:commandSortHeader columnName="2b" arrow="true">
              <h:outputText value="#{msg.firstname}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty2.firstname2}"/>
        </t:column>     
        <t:column sortable="false">
          <f:facet name="header">
            <t:commandSortHeader columnName="4" arrow="true">
              <h:outputText value="#{msg.age}"/>
            </t:commandSortHeader>
          </f:facet>         
            <h:outputText value="#{cty2.age.ageShort}"/>         
        </t:column>
        <t:column sortable="false">
          <f:facet name="header">
            <t:commandSortHeader columnName="5" arrow="true">
              <h:outputText value="#{msg.sex}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty2.sexWeb}"/>
        </t:column>            
              
      </t:dataTable>
      </c:if>
      
      <p>&nbsp;</p>
      
       <c:if test="${adminUploadTeamAction.newaFighterList != null}">
       <t:htmlTag value="h3"><h:outputText value="#{msg['newa']}"/></t:htmlTag>
     <t:dataTable 
                   value="#{adminUploadTeamAction.newaFighterList}" var="cty3" border="0"
                   cellspacing="0" cellpadding="0" id="showNewaFighterTable" forceId="true"
                   styleClass="fancyTable" rowClasses="odd,even" width="800px"
                   binding="#{adminUploadTeamAction.dataTable3}" >
    
        <t:column sortable="false" defaultSorted="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="1" arrow="true">
              <h:outputText value="#{msg.name}"/>
            </t:commandSortHeader>
          </f:facet>          
            <h:outputText value="#{cty3.name}"/>
        </t:column>
        <t:column sortable="false">
          <f:facet name="header">
            <t:commandSortHeader columnName="2" arrow="true">
              <h:outputText value="#{msg.firstname}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty3.firstname}"/>
        </t:column>     
        <t:column sortable="false">
          <f:facet name="header">
            <t:commandSortHeader columnName="4" arrow="true">
              <h:outputText value="#{msg.age}"/>
            </t:commandSortHeader>
          </f:facet>         
            <h:outputText value="#{cty3.age.ageShort}"/>         
        </t:column>
        <t:column sortable="false">
          <f:facet name="header">
            <t:commandSortHeader columnName="5" arrow="true">
              <h:outputText value="#{msg.sex}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty3.sexWeb}"/>
        </t:column>            
        <t:column sortable="false">
          <f:facet name="header">
            <t:commandSortHeader columnName="8" arrow="true">
              <h:outputText value="#{msg.weight}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty3.weight}"/>
        </t:column>
   
      </t:dataTable>
      </c:if>
      
      
           
     <h:commandButton value="#{msg['admin.teamAbort']}" action="#{adminUploadTeamAction.gotoAllTeams}" id="showTeam"
                           tabindex="18"/>
</h:form>
<script>
  jjw_setFocus( '${focus}' );
  jjw_resetValue( 'main:adminUploadTeamAction:change' );
</script>