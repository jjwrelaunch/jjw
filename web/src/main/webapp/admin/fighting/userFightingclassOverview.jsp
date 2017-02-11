<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : userFightingclassOverview.jsp
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
  
  <f:verbatim>
  <script type="text/javascript" src="<c:url value='/scripts/jquery-1.4.2.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/scripts/picnet.table.filter.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/scripts/jquery.highlighter.0.1.js'/>"></script>
  <script type="text/javascript">
    $( document ).ready( function()
    {
      function highlight( column )
      {
        $( "#fightingClassTable2 > tbody > tr" ).separator( function()
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
      $( '#fightingClassTable2' ).tableFilter( options );
    } );
  </script>
</f:verbatim>

<f:loadBundle basename="de.jjw.webapp.messages.admin.user" var="msg"/>
<t:htmlTag value="h3"><h:outputText value="#{msg['userFightingclassOverviewHeadline'] }"/></t:htmlTag>
<h:form id="adminUserFightingclassOverviewAction">

  <h:outputText value="#{adminUserFightingclassOverviewAction.text}"/>

 <f:verbatim>
			<p>Quick Find: <input type="text" id="quickfind" /> <a
				id="cleanfilters" href="#">Clear Filters</a></p>
		</f:verbatim>
  <t:dataTable value="#{adminUserFightingclassOverviewAction.model.modelValue}" var="cty" forceId="true"
               border="0" cellspacing="0" cellpadding="0" styleClass="fancyTable" rowClasses="odd,even"
               binding="#{adminUserFightingclassOverviewAction.dataTable}" id="fightingClassTable2">
    <t:column>
      <f:facet name="header">
        <h:outputText value="#{msg['userFightingclassOverview_age']}"/>
      </f:facet>
      <h:outputText value="#{adminUserFightingclassOverviewAction.model.modelDescriptionValue.age.ageShort}"/>
    </t:column>
    <t:column>
      <f:facet name="header">
        <h:outputText value="#{msg['userFightingclassOverview_sex']}"/>
      </f:facet>
      <h:outputText value="#{adminUserFightingclassOverviewAction.model.modelDescriptionValue.sexWeb}"/>
    </t:column>
    <t:column>
      <f:facet name="header">
        <h:outputText value="#{msg['userFightingclassOverview_class']}"/>
      </f:facet>
      <h:outputText value="#{adminUserFightingclassOverviewAction.model.modelDescriptionValue.weightclass}"/>
    </t:column>
    <t:column>
      <f:facet name="header">
        <h:outputText value="#{msg['userFightingclassOverview_openFights']}"/>
      </f:facet>
      <h:outputText value="#{adminUserFightingclassOverviewAction.model.modelDescriptionValue.numberOfOpenFightsInClass}"/> / 
      <h:outputText value="#{adminUserFightingclassOverviewAction.model.modelDescriptionValue.numberOfFightsInClass}"/>
    </t:column>
    <t:column>
      <f:facet name="header">
        <h:outputText value="#{msg['userFightingclassOverview_begin']}"/>
      </f:facet>
      <h:outputText value="#{adminUserFightingclassOverviewAction.model.modelDescriptionValue.estimateBeginTimeWeb}"/>
      <h:outputText value="&nbsp;" escape="false"/>
    </t:column>

    <t:columns sortable="true" value="#{adminUserFightingclassOverviewAction.model.modelHead}" var="vali" styleClass="alignmentColumnRight">
      <f:facet name="header">
        <t:div>
          <h:outputText value="#{adminUserFightingclassOverviewAction.model.modelHeadValue}"/>
          <h:outputText value="&nbsp;" escape="false"/>
          (<h:outputText value="#{adminUserFightingclassOverviewAction.model.estimatedTatamiTimeValue}"/>)
        </t:div>
      </f:facet>     
       <h:outputText value="#{adminUserFightingclassOverviewAction.model.modelCellValueOrder}"/>
       <h:outputText value="&nbsp;" escape="false"/><h:outputText value="&nbsp;" escape="false"/><h:outputText value="&nbsp;" escape="false"/>
       <h:commandLink action="#{adminUserFightingclassOverviewAction.moveUp}" title="#{msg['userFightingclassOverview_moveUp']}">
            <t:graphicImage value="/images/desc.gif"  rendered="true" border="0" alt="" />
          </h:commandLink>
       <h:commandLink action="#{adminUserFightingclassOverviewAction.moveDown}" title="#{msg['userFightingclassOverview_moveDown']}">
            <t:graphicImage value="/images/asc.gif" rendered="true" border="0" alt="" />
          </h:commandLink>
          <h:outputText value="&nbsp;" escape="false"/><h:outputText value="&nbsp;" escape="false"/><h:outputText value="&nbsp;" escape="false"/>
        <t:selectBooleanCheckbox onchange="submit()" value="#{adminUserFightingclassOverviewAction.model.modelCellValue}"
                               valueChangeListener="#{adminUserFightingclassOverviewAction.toggleUserListener}"/>
    </t:columns>

  </t:dataTable>
</h:form>