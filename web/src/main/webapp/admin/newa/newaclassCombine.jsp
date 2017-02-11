<%@ include file="/common/taglibs.jsp" %>
<%@ page import="de.jjw.webapp.IGlobalWebConstants" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : newaclassCombine.jsp
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

<f:loadBundle basename="de.jjw.webapp.messages.admin.newaWeightclass" var="msg"/>
<h:form id="adminNewaclassCombineAction">
  <h:inputHidden id="id" value="#{adminNewaclassCombineAction.newaclass.id}"/>
  
  <t:panelGrid columns="2">
    <h:outputText value="#{msg['weightclassAge']}"/>
    <h:outputText value="#{adminNewaclassCombineAction.newaclass.age.ageShort}" />
 
    <h:outputText value="#{msg['weightclassSex']}"/>
    <h:outputText value="#{adminNewaclassCombineAction.newaclass.sexWeb}" />
    
    <h:outputText value="#{msg['weightclassStartWeight']}"/>     
    <h:outputText value="#{adminNewaclassCombineAction.newaclass.startWeight}"/>

    <h:outputText value="#{msg['weightclassWeightLimit']}"/>
    <h:outputText value="#{adminNewaclassCombineAction.newaclass.weightLimit}"/>
    
    <h:outputText value="#{msg['weightclassClassName']}"/>
    <h:outputText value="#{adminNewaclassCombineAction.newaclass.weightclass}"/>
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
        $( "#newaclassTable > tbody > tr" ).separator( function()
        {
          return $( "td:nth-child(" + column + ")", this ).text();
        } );
      }

      highlight( "<h:outputText value="#{adminNewaclassCombineAction.sortColumn}"/>" );

      // Initialise Plugin
      var options = {
        clearFiltersControls: [$( '#cleanfilters' )],
        additionalFilterTriggers: [$( '#quickfind' )]
      };
      $( '#newaclassTable' ).tableFilter( options );
    } );
  </script>
</f:verbatim>

 <t:htmlTag value="h3"><h:outputText value="#{msg['childFightingclassesHeadline']}" rendered="#{adminNewaclassCombineAction.rendergetChildclasses}"/></t:htmlTag>
  <t:dataTable value="#{adminNewaclassCombineAction.childclasses}" var="cty" border="0" 
  			   cellspacing="0" cellpadding="0"
               styleClass="fancyTable" rowClasses="odd,even" id="newaclassTable2"
               binding="#{adminNewaclassCombineAction.dataTable}" width="300px"
               rendered="#{adminNewaclassCombineAction.rendergetChildclasses}">
    <t:column>
      <f:facet name="header">
        <h:outputText value="#{msg.weightclassAge}"/>
      </f:facet>
      <h:outputText value="#{cty.age.ageShort}"/>
    </t:column>
    <t:column>
      <f:facet name="header">
        <h:outputText value="#{msg.weightclassSex}"/>
      </f:facet>
      <h:outputText value="#{cty.sexWeb}"/>
    </t:column>
    <t:column>
      <f:facet name="header">
        <h:outputText value="#{msg.weightclass}"/>
      </f:facet>
      <h:outputText value="#{cty.weightclass}"/>
    </t:column>
    <t:column>
      <f:facet name="header">
        <h:outputText value="#{msg.weightclassStartWeight}"/>
      </f:facet>
      <h:outputText value="#{cty.startWeight}"/>
    </t:column>
    <t:column>
      <f:facet name="header">
        <h:outputText value="#{msg.weightclassWeightLimit}"/>
      </f:facet>
      <h:outputText value="#{cty.weightLimit}"/>
    </t:column>
       <t:column styleClass="center">
        <f:facet name="header">
          <h:outputText value="&nbsp;" escape="false"/>
        </f:facet>
        <h:commandLink action="#{adminNewaclassCombineAction.removeNewaclassFromParent}">
          <t:graphicImage value="/images/trash.png" title="#{msg.removeFightingclass}" rendered="true" border="0" alt=""/>
        <h:outputText value="&nbsp;" escape="false"/>
        </h:commandLink>
      </t:column>
    
  </t:dataTable>
  

  
  <t:htmlTag value="h3"><h:outputText value="#{msg['combinableFightingclassesHeadline']}" rendered="#{adminNewaclassCombineAction.renderCombinableNewaclasses}"/></t:htmlTag>
  <t:dataTable value="#{adminNewaclassCombineAction.combinableNewaclasses}" var="cty2" 
  			   border="0" cellspacing="0" cellpadding="0"
               styleClass="fancyTable" rowClasses="odd,even" 
               binding="#{adminNewaclassCombineAction.dataTable2}" width="300px"
               rendered="#{adminNewaclassCombineAction.renderCombinableNewaclasses}"
               id="newaclassTable" forceId="true">
      <t:column defaultSorted="true" sortable="true">
        <f:facet name="header">
        	<t:commandSortHeader columnName="1" arrow="true">
       		   <h:outputText title="#{msg.weightclassAgeTooltip}" value="#{msg.weightclassAge}"/>
       		</t:commandSortHeader>
        </f:facet>
        <h:outputText value="#{cty2.age.ageShort}"/>
      </t:column>
      <t:column sortable="true">
        <f:facet name="header">
        	<t:commandSortHeader columnName="2" arrow="true">
          		<h:outputText value="#{msg.weightclassSex}"/>
          	</t:commandSortHeader>
        </f:facet>
        <h:outputText value="#{cty2.sexWeb}"/>
      </t:column>
      <t:column sortable="true">
        <f:facet name="header">
        	<t:commandSortHeader columnName="3" arrow="true">
          		<h:outputText title="#{msg.weightclassTooltip}" value="#{msg.weightclass}"/>
          	</t:commandSortHeader>
        </f:facet>
        <h:outputText value="#{cty2.weightclass}"/>
      </t:column>
      <t:column sortable="true">
        <f:facet name="header">
        	<t:commandSortHeader columnName="4" arrow="true">
          		<h:outputText value="#{msg.weightclassStartWeight}"/>
          	</t:commandSortHeader>
        </f:facet>
        <h:outputText value="#{cty2.startWeight}"/>
      </t:column>
    <t:column sortable="true">
      <f:facet name="header">
      	<t:commandSortHeader columnName="5" arrow="true">
       		<h:outputText value="#{msg.weightclassWeightLimit}"/>
        </t:commandSortHeader>
      </f:facet>
      <h:outputText value="#{cty2.weightLimit}"/>
    </t:column>
          <t:column>
        <f:facet name="header">
        	<t:commandSortHeader columnName="6" arrow="true">
          		<h:outputText value="#{msg.weightclassNumberOfFighter}"/>
          	</t:commandSortHeader>
        </f:facet>
        <h:outputText value="#{cty2.numberOfFighter}"/>
      </t:column>
      <t:column>
        <f:facet name="header">
        	<t:commandSortHeader columnName="7" arrow="true">
          		<h:outputText value="#{msg.weightclassNumberOfPotentialFighter}"/>
          	</t:commandSortHeader>
        </f:facet>
        <h:outputText value="#{cty2.numberOfPotentialFighter}"/>
      </t:column>  
     <t:column styleClass="center">
        <f:facet name="header">
          <h:outputText value="&nbsp;" escape="false"/>
        </f:facet>
        <h:commandLink action="#{adminNewaclassCombineAction.addNewaclassToClass}">
          <t:graphicImage value="/images/register_2.png" title="#{msg.addFightingclass}" rendered="true" border="0" alt=""/>
        <h:outputText value="&nbsp;" escape="false"/>
        </h:commandLink>
      </t:column>
  </t:dataTable>
</h:form>