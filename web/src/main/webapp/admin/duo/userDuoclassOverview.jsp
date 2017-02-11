<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : userDuoclassOverview.jsp
  ~ Created : 05 Jun 2010
  ~ Last Modified: Sat, 05 Jun 2010 21:02:54
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
<f:verbatim>
  <script type="text/javascript" src="<c:url value='/scripts/jquery-1.4.2.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/scripts/picnet.table.filter.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/scripts/jquery.highlighter.0.1.js'/>"></script>
  <script type="text/javascript">
    $( document ).ready( function()
    {
      function highlight( column )
      {
        $( "#duoClassTable2 > tbody > tr" ).separator( function()
        {
          return $( "td:nth-child(" + column + ")", this ).text();
        } );
      }

      highlight( "<h:outputText value="#{adminWeightclassAction.sortColumn}"/>" );

      // Initialise Plugin
      var options = {
        clearFiltersControls: [$( '#cleanfilters' )],
        additionalFilterTriggers: [$( '#quickfind' )]
      };
      $( '#duoClassTable2' ).tableFilter( options );
    } );
  </script>
</f:verbatim>
<f:loadBundle basename="de.jjw.webapp.messages.admin.user" var="msg"/>
<t:htmlTag value="h3"><h:outputText value="#{msg['userDuoclassOverviewHeadline'] }"/></t:htmlTag>
<h:form id="adminUserDuoclassOverviewAction">

  <h:outputText value="#{adminUserDuoclassOverviewAction.text}"/>
	<f:verbatim>
			<p>Quick Find: <input type="text" id="quickfind" /> <a
				id="cleanfilters" href="#">Clear Filters</a></p>
		</f:verbatim>

  <t:dataTable value="#{adminUserDuoclassOverviewAction.model.modelValue}" var="cty" forceId="true"
               border="0" cellspacing="0" cellpadding="0" styleClass="fancyTable" rowClasses="odd,even"
               binding="#{adminUserDuoclassOverviewAction.dataTable}" id="duoClassTable2">
    <t:column>
      <f:facet name="header">
       <h:outputText value="#{msg['userDuoclassOverview_age']}"/>
      </f:facet>
      <h:outputText value="#{adminUserDuoclassOverviewAction.model.modelDescriptionValue.age.ageShort}"/>
    </t:column>
    <t:column>
      <f:facet name="header">
        <h:outputText value="#{msg['userDuoclassOverview_sex']}"/>
      </f:facet>
      <h:outputText value="#{adminUserDuoclassOverviewAction.model.modelDescriptionValue.sexWeb}"/>
    </t:column>
    <t:column>
      <f:facet name="header">
        <h:outputText value="#{msg['userDuoclassOverview_class']}"/>
      </f:facet>
      <h:outputText value="#{adminUserDuoclassOverviewAction.model.modelDescriptionValue.duoclassName}"/>
    </t:column>
    <t:column>
      <f:facet name="header">
        <h:outputText value="#{msg['userDuoclassOverview_openFights']}"/>
      </f:facet>
      <h:outputText value="#{adminUserDuoclassOverviewAction.model.modelDescriptionValue.numberOfOpenFightsInClass}"/> / 
      <h:outputText value="#{adminUserDuoclassOverviewAction.model.modelDescriptionValue.numberOfFightsInClass}"/>
    </t:column>
     <t:column>
      <f:facet name="header">
        <h:outputText value="#{msg['userDuoclassOverview_begin']}"/>
      </f:facet>
      <h:outputText
          value="#{adminUserDuoclassOverviewAction.model.modelDescriptionValue.estimateBeginTimeWeb}"/>
      <h:outputText value="&nbsp;" escape="false"/>
    </t:column>

    <t:columns sortable="true" value="#{adminUserDuoclassOverviewAction.model.modelHead}" var="vali" styleClass="alignmentColumnRight">
      <f:facet name="header">
        <t:div>
          <h:outputText value="#{adminUserDuoclassOverviewAction.model.modelHeadValue}"/>
          <h:outputText value="&nbsp;" escape="false"/>
          (<h:outputText value="#{adminUserDuoclassOverviewAction.model.estimatedTatamiTimeValue}"/>)
        </t:div>
      </f:facet>
      
      <h:outputText value="#{adminUserDuoclassOverviewAction.model.modelCellValueOrder}"/>
       <h:outputText value="&nbsp;" escape="false"/><h:outputText value="&nbsp;" escape="false"/><h:outputText value="&nbsp;" escape="false"/>
       <h:commandLink action="#{adminUserDuoclassOverviewAction.moveUp}" title="#{msg['userDuoclassOverview_moveUp']}">
            <t:graphicImage value="/images/desc.gif"  rendered="true" border="0" alt="" />
          </h:commandLink>
       <h:commandLink action="#{adminUserDuoclassOverviewAction.moveDown}" title="#{msg['userDuoclassOverview_moveDown']}">
            <t:graphicImage value="/images/asc.gif"  rendered="true" border="0" alt="" />
          </h:commandLink>
          <h:outputText value="&nbsp;" escape="false"/><h:outputText value="&nbsp;" escape="false"/><h:outputText value="&nbsp;" escape="false"/>
      <t:selectBooleanCheckbox onchange="submit()" value="#{adminUserDuoclassOverviewAction.model.modelCellValue}"
                               valueChangeListener="#{adminUserDuoclassOverviewAction.toggleUserListener}"/>
    </t:columns>
  </t:dataTable>
</h:form>