<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : showWeightclasses.jsp
  ~ Created : 17 Jun 2010
  ~ Last Modified: Thu, 17 Jun 2010 18:25:14
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
        $( "#fightingClassTable > tbody > tr" ).separator( function()
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
      $( '#fightingClassTable' ).tableFilter( options );
    } );
  </script>
</f:verbatim>
<f:loadBundle basename="de.jjw.webapp.messages.admin.weightclass" var="msg"/>
<t:htmlTag value="h3"><h:outputText value="#{msg['weightclassHeadline'] }"/></t:htmlTag>
<f:verbatim>
	<p> &nbsp;</p>
</f:verbatim>
<h:form id="adminWeightclassAction">
	<h:commandButton value="#{msg['createFightingList']}" action="#{adminWeightclassAction.gotoCreateFightingList}"
                     id="gotoCreateFightingList" tabindex="2" />
    <h:commandButton value="#{msg['gotoNewWeightclass']}" action="#{adminWeightclassAction.gotoNewWeightclass}"
                     id="gotoNewWeightclass" tabindex="3" />
    <h:commandButton value="#{msg['gotoAutoWeightclass']}" action="#{adminWeightclassAction.gotoAutoWeightclass}"
                     id="gotoAutoWeightclass" tabindex="4" title="#{msg['gotoAutoWeightclass_title']}"/>
    <h:commandButton value="#{msg['sortFighterIn']}" action="#{adminWeightclassAction.sortFighterIn}" id="sortFighterIn"
                     tabindex="5" title="#{msg['sortFighterIn_title']}"/>

  <h:outputText value="#{adminWeightclassAction.text}"/>
  <c:if test="${adminWeightclassAction.ages == null || fn:length(adminWeightclassAction.ages) == 0}">
    <h:outputText value="#{msg.weightclassNoAgeAviable}"/>
  </c:if>

  <c:if test="${ !(adminWeightclassAction.ages == null) && !(fn:length(adminWeightclassAction.ages) == 0) &&
	(adminWeightclassAction.weightclasses != null && fn:length(adminWeightclassAction.weightclasses) > 0)}">
		<f:verbatim>
			<p>&nbsp;</p>
		</f:verbatim>

		<f:verbatim>
			<p>Quick Find: <input type="text" id="quickfind" /> <a
				id="cleanfilters" href="#">Clear Filters</a></p>
		</f:verbatim>
		<t:dataTable value="#{adminWeightclassAction.weightclasses}" var="cty" border="0" cellspacing="0" cellpadding="0"
                 styleClass="fancyTable" rowClasses="odd,even" width="800px" forceId="true"
                 binding="#{adminWeightclassAction.dataTable}" id="fightingClassTable">
      <t:column defaultSorted="true" sortable="true">
        <f:facet name="header">
        	<t:commandSortHeader columnName="1" arrow="true">
       		   <h:outputText title="#{msg.weightclassAgeTooltip}" value="#{msg.weightclassAge}"/>
       		</t:commandSortHeader>
        </f:facet>
        <h:outputText value="#{cty.age.ageShort}"/>
      </t:column>
      <t:column sortable="true">
        <f:facet name="header">
        	<t:commandSortHeader columnName="2" arrow="true">
          		<h:outputText value="#{msg.weightclassSex}"/>
          	</t:commandSortHeader>
        </f:facet>
        <h:outputText value="#{cty.sexWeb}"/>
      </t:column>
      <t:column sortable="true">
        <f:facet name="header">
        	<t:commandSortHeader columnName="3" arrow="true">
          		<h:outputText title="#{msg.weightclassTooltip}" value="#{msg.weightclass}"/>
          	</t:commandSortHeader>
        </f:facet>
        <h:outputText value="#{cty.weightclass}"/>
      </t:column>
      <t:column sortable="true">
        <f:facet name="header">
        	<t:commandSortHeader columnName="4" arrow="true">
          		<h:outputText value="#{msg.weightclassStartWeight}"/>
          	</t:commandSortHeader>
        </f:facet>
        <h:outputText value="#{cty.startWeight}"/>
      </t:column>

      <t:column sortable="true">
        <f:facet name="header">
        	<t:commandSortHeader columnName="5" arrow="true">
          		<h:outputText value="#{msg.weightclassWeightLimit}"/>
          	</t:commandSortHeader>
        </f:facet>
        <h:outputText value="#{cty.weightLimit}"/>
      </t:column>
      <t:column sortable="true">
        <f:facet name="header">
        	<t:commandSortHeader columnName="6" arrow="true">
          		<h:outputText value="#{msg.weightclassDeleteStop}"/>
          	</t:commandSortHeader>
        </f:facet>
        <h:selectBooleanCheckbox value="#{cty.deleteStop}" disabled="true"/>
      </t:column>
      <t:column sortable="true">
        <f:facet name="header">
        	<t:commandSortHeader columnName="7" arrow="true">
          		<h:outputText value="#{msg.weightclassComplete}"/>
          	</t:commandSortHeader>
        </f:facet>
        <h:selectBooleanCheckbox value="#{cty.complete}" disabled="true"/>
      </t:column>
      <t:column>
        <f:facet name="header">
        	<t:commandSortHeader columnName="8" arrow="true">
          		<h:outputText value="#{msg.weightclassNumberOfFighter}"/>
          	</t:commandSortHeader>
        </f:facet>
        <h:outputText value="#{cty.numberOfFighter}"/>
      </t:column>
      <t:column>
        <f:facet name="header">
        	<t:commandSortHeader columnName="9" arrow="true">
          		<h:outputText value="#{msg.weightclassNumberOfPotentialFighter}"/>
          	</t:commandSortHeader>
        </f:facet>
        <h:outputText value="#{cty.numberOfPotentialFighter}"/>
      </t:column>      

      <t:column >
        <f:facet name="header">
          <h:outputText value="&nbsp;" escape="false"/>
        </f:facet>
        <h:commandLink action="#{adminWeightclassAction.gotoEditWeightclass}">
          <t:graphicImage value="/images/edit.png" title="#{msg.editTitle}" rendered="true" border="0" alt=""/>        
        </h:commandLink>
        <h:outputText value="&nbsp;" escape="false"/>
        <h:commandLink action="#{adminWeightclassAction.gotoCombineWeightclass}">
          <t:graphicImage value="/images/register_2.png" title="#{msg.combine}" rendered="#{cty.canBeParentClass}" border="0" alt=""/>
        </h:commandLink>
        <h:outputText value="&nbsp;" escape="false"/>
        <h:commandLink action="#{adminWeightclassAction.deleteWeightclass}">
          <t:graphicImage value="/images/trash.png" title="#{msg.delete}" rendered="true" border="0" alt=""/>
        </h:commandLink>
      </t:column>
    </t:dataTable>
  </c:if>
</h:form>