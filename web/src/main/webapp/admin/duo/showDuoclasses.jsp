<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : showDuoclasses.jsp
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
        $( "#duoclassTable > tbody > tr" ).separator( function()
        {
          return $( "td:nth-child(" + column + ")", this ).text();
        } );
      }

      highlight( "<h:outputText value="#{adminDuoclassAction.sortColumn}"/>" );
      // Initialise Plugin
      var options = {
        clearFiltersControls: [$( '#cleanfilters' )],
        additionalFilterTriggers: [$( '#quickfind' )]
      };
      $( '#duoclassTable' ).tableFilter( options );
    } );
  </script>
</f:verbatim>
<f:loadBundle basename="de.jjw.webapp.messages.admin.duoclass" var="msg"/>
<t:htmlTag value="h3"><h:outputText value="#{msg['duoclassHeadline'] }"/></t:htmlTag>
<h:form id="adminDuoclassAction">
<f:verbatim>
	<p> &nbsp;</p>
</f:verbatim>
  <h:commandButton value="#{msg['createFightingList']}" action="#{adminDuoclassAction.gotoCreateDuoList}"
                   id="gotoCreateDuoList" tabindex="2"/>
  <h:commandButton value="#{msg['gotoNewDuoclass']}" action="#{adminDuoclassAction.gotoNewDuoclass}"
                   id="gotoNewDuoclass" tabindex="3"/>
  <h:outputText value="#{adminDuoclassAction.text}"/>
  <c:if test="${adminDuoclassAction.ages == null || fn:length(adminDuoclassAction.ages) == 0}">
    <h:outputText value="#{msg.duoclassNoAgeAviable}"/>
  </c:if>
  
  <c:if test="${ !(adminDuoclassAction.ages == null) && !(fn:length(adminDuoclassAction.ages) == 0) &&
	(adminDuoclassAction.duoclasses != null && fn:length(adminDuoclassAction.duoclasses) > 0)}">
		<f:verbatim>
			<p>&nbsp;</p>
		</f:verbatim>
		<f:verbatim>
			<p>Quick Find: <input type="text" id="quickfind" /> <a
				id="cleanfilters" href="#">Clear Filters</a></p>
		</f:verbatim>

		<t:dataTable value="#{adminDuoclassAction.duoclasses}" var="cty" border="0" cellspacing="0" cellpadding="0"
                 styleClass="fancyTable" rowClasses="odd,even" width="800px" forceId="true"
                 binding="#{adminDuoclassAction.dataTable}" id="duoclassTable">
      <t:column defaultSorted="true" sortable="true">
        <f:facet name="header">
        <t:commandSortHeader columnName="1" arrow="true">
          <h:outputText value="#{msg.duoclassAge}"/>
        </t:commandSortHeader>
        </f:facet>
        <h:outputText value="#{cty.age.ageShort}"/>
      </t:column>
      <t:column sortable="true">
        <f:facet name="header">
         <t:commandSortHeader columnName="2" arrow="true">
          <h:outputText value="#{msg.duoclassSex}"/>
          </t:commandSortHeader>
        </f:facet>
        <h:outputText value="#{cty.sexWeb}"/>
      </t:column>
      <t:column sortable="true">
        <f:facet name="header">
         <t:commandSortHeader columnName="3" arrow="true">
          <h:outputText value="#{msg.duoclass}"/>
          </t:commandSortHeader>
        </f:facet>
        <h:outputText value="#{cty.duoclassName}"/>
      </t:column>
      <t:column sortable="true">
        <f:facet name="header">
        <t:commandSortHeader columnName="4" arrow="true">
          <h:outputText value="#{msg.duoclassDeleteStop}"/>
          </t:commandSortHeader>
        </f:facet>
        <h:selectBooleanCheckbox value="#{cty.deleteStop}" disabled="true"/>
      </t:column>
      <t:column sortable="true">
        <f:facet name="header">
        <t:commandSortHeader columnName="5" arrow="true">
          <h:outputText value="#{msg.duoclassComplete}"/>
        </t:commandSortHeader>
        </f:facet>
        <h:selectBooleanCheckbox value="#{cty.complete}" disabled="true"/>
      </t:column>
      <t:column sortable="true">
        <f:facet name="header">
        <t:commandSortHeader columnName="6" arrow="true">
          <h:outputText value="#{msg.duoclassNumberOfFighter}"/>
        </t:commandSortHeader>
        </f:facet>
        <h:outputText value="#{cty.numberOfTeams}"/>
      </t:column>
      <t:column sortable="true">
        <f:facet name="header">
        <t:commandSortHeader columnName="7" arrow="true">
          <h:outputText value="#{msg.duoclassNumberOfPotentialFighter}"/>
        </t:commandSortHeader>
        </f:facet>
        <h:outputText value="#{cty.numberOfPotentialTeams}"/>
      </t:column>

      <t:column styleClass="center">
        <f:facet name="header">
          <h:outputText value="#{msg.duoclassCertificationPrint}"/>
        </f:facet>
        <h:outputText value="&nbsp;" escape="false"/>
        <t:graphicImage value="#{cty.certificationWeb}" title="#{cty.certificationPrint}" rendered="#{cty.render}"
                        border="0" alt=""/>
      </t:column>

      <t:column>
        <f:facet name="header">
          <h:outputText value="&nbsp;" escape="false"/>
        </f:facet>
        <h:commandLink action="#{adminDuoclassAction.gotoEditDuoclass}">
          <t:graphicImage value="/images/edit.png" title="#{msg.editTitle}" rendered="true" border="0" alt=""/>
        </h:commandLink>
         <h:outputText value="&nbsp;" escape="false"/>
        <h:commandLink action="#{adminDuoclassAction.gotoCombineDuoclass}">
          <t:graphicImage value="/images/register_2.png" title="#{msg.combine}" rendered="#{cty.canBeParentClass}" border="0" alt=""/>
        </h:commandLink>
        <h:commandLink action="#{adminDuoclassAction.deleteDuoclass}">
          <t:graphicImage value="/images/trash.png" title="#{msg.delete}" rendered="true" border="0" alt=""/>
        </h:commandLink>
      </t:column>
    </t:dataTable>
  </c:if>
</h:form>