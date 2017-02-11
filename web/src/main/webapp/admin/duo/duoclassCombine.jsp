<%@ include file="/common/taglibs.jsp" %>
<%@ page import="de.jjw.webapp.IGlobalWebConstants" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : duoclassCombine.jsp
  ~ Created : 17 Jun 2010
  ~ Last Modified: Thu, 06 Jan 2011 14:59:50
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
<h:form id="adminDuoclassCombineAction">
  <h:inputHidden id="id" value="#{adminDuoclassCombineAction.duoclass.id}"/>
  
  <t:panelGrid columns="2">
    <h:outputText value="#{msg['duoclassAge']}"/>
    <h:outputText value="#{adminDuoclassCombineAction.duoclass.age.ageShort}" />
 
    <h:outputText value="#{msg['duoclassSex']}"/>
    <h:outputText value="#{adminDuoclassCombineAction.duoclass.sexWeb}" />
    
    <h:outputText value="#{msg['duoclassClassName']}"/>     
    <h:outputText value="#{adminDuoclassCombineAction.duoclass.duoclassName}"/>
   </t:panelGrid>
  

  <f:verbatim>
  <script type="text/javascript" src="<c:url value='/scripts/jquery-1.4.2.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/scripts/picnet.table.filter.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/scripts/jquery.highlighter.0.1.js'/>"></script>
  <script type="text/javascript">
    $( document ).ready( function()
    {
      function highlight( column )
      {
        $( "#duoClassTable > tbody > tr" ).separator( function()
        {
          return $( "td:nth-child(" + column + ")", this ).text();
        } );
      }

      highlight( "<h:outputText value="#{adminDuoclassCombineAction.sortColumn}"/>" );

      // Initialise Plugin
      var options = {
        clearFiltersControls: [$( '#cleanfilters' )],
        additionalFilterTriggers: [$( '#quickfind' )]
      };
      $( '#duoClassTable' ).tableFilter( options );
    } );
  </script>
</f:verbatim>

 <t:htmlTag value="h3"><h:outputText value="#{msg['childDuoclassesHeadline']}" rendered="#{adminDuoclassCombineAction.rendergetChildclasses}"/></t:htmlTag>
  <t:dataTable value="#{adminDuoclassCombineAction.childclasses}" var="cty" border="0" 
  			   cellspacing="0" cellpadding="0"
               styleClass="fancyTable" rowClasses="odd,even" id="duoClassTable2"
               binding="#{adminDuoclassCombineAction.dataTable}" width="300px"
               rendered="#{adminDuoclassCombineAction.rendergetChildclasses}">
    <t:column>
      <f:facet name="header">
        <h:outputText value="#{msg.duoclassAge}"/>
      </f:facet>
      <h:outputText value="#{cty.age.ageShort}"/>
    </t:column>
    <t:column>
      <f:facet name="header">
        <h:outputText value="#{msg.duoclassSex}"/>
      </f:facet>
      <h:outputText value="#{cty.sexWeb}"/>
    </t:column>
    <t:column>
      <f:facet name="header">
        <h:outputText value="#{msg.duoclassClassName}"/>
      </f:facet>
      <h:outputText value="#{cty.duoclassName}"/>
    </t:column>
       <t:column styleClass="center">
        <f:facet name="header">
          <h:outputText value="&nbsp;" escape="false"/>
        </f:facet>
        <h:commandLink action="#{adminDuoclassCombineAction.removeDuoclassFromParent}">
          <t:graphicImage value="/images/trash.png" title="#{msg.removeDuoclass}" rendered="true" border="0" alt=""/>
        <h:outputText value="&nbsp;" escape="false"/>
        </h:commandLink>
      </t:column>
    
  </t:dataTable>
  

  
  <t:htmlTag value="h3"><h:outputText value="#{msg['combinableDuoclassesHeadline']}" rendered="#{adminDuoclassCombineAction.renderCombinableDuoclasses}"/></t:htmlTag>
  <t:dataTable value="#{adminDuoclassCombineAction.combinableDuoclasses}" var="cty2" 
  			   border="0" cellspacing="0" cellpadding="0"
               styleClass="fancyTable" rowClasses="odd,even" 
               binding="#{adminDuoclassCombineAction.dataTable2}" width="300px"
               rendered="#{adminDuoclassCombineAction.renderCombinableDuoclasses}"
               id="duoClassTable" forceId="true">
      <t:column defaultSorted="true" sortable="true">
        <f:facet name="header">
        	<t:commandSortHeader columnName="1" arrow="true">
       		   <h:outputText title="#{msg.duoclassAgeTooltip}" value="#{msg.duoclassAge}"/>
       		</t:commandSortHeader>
        </f:facet>
        <h:outputText value="#{cty2.age.ageShort}"/>
      </t:column>
      <t:column sortable="true">
        <f:facet name="header">
        	<t:commandSortHeader columnName="2" arrow="true">
          		<h:outputText value="#{msg.duoclassSex}"/>
          	</t:commandSortHeader>
        </f:facet>
        <h:outputText value="#{cty2.sexWeb}"/>
      </t:column>
      <t:column sortable="true">
        <f:facet name="header">
        	<t:commandSortHeader columnName="3" arrow="true">
          		<h:outputText title="#{msg.weightclassTooltip}" value="#{msg.duoclassClassName}"/>
          	</t:commandSortHeader>
        </f:facet>
        <h:outputText value="#{cty2.duoclassName}"/>
      </t:column>     
            <t:column sortable="true">
        <f:facet name="header">
        <t:commandSortHeader columnName="4" arrow="true">
          <h:outputText value="#{msg.duoclassNumberOfFighter}"/>
        </t:commandSortHeader>
        </f:facet>
        <h:outputText value="#{cty2.numberOfTeams}"/>
      </t:column>
      <t:column sortable="true">
        <f:facet name="header">
        <t:commandSortHeader columnName="5" arrow="true">
          <h:outputText value="#{msg.duoclassNumberOfPotentialFighter}"/>
        </t:commandSortHeader>
        </f:facet>
        <h:outputText value="#{cty2.numberOfPotentialTeams}"/>
      </t:column>
     <t:column styleClass="center">
        <f:facet name="header">
          <h:outputText value="&nbsp;" escape="false"/>
        </f:facet>
        <h:commandLink action="#{adminDuoclassCombineAction.addDuoclassToClass}">
          <t:graphicImage value="/images/register_2.png" title="#{msg.addDuoclass}" rendered="true" border="0" alt=""/>
        <h:outputText value="&nbsp;" escape="false"/>
        </h:commandLink>
      </t:column>
  </t:dataTable>
</h:form>