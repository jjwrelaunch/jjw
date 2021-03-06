<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : duoclassOverview.jsp
  ~ Created : 18 Jun 2010
  ~ Last Modified: Fri, 18 Jun 2010 15:06:49
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
        $( "#duoClassTable > tbody > tr" ).separator( function()
        {
          return $( "td:nth-child(" + column + ")", this ).text();
        } );
      }

      highlight( "<h:outputText value="#{adminShowDuoTeamAction.sortColumn}"/>" );

      // Initialise Plugin
      var options = {
        clearFiltersControls: [$( '#cleanfilters' )],
        additionalFilterTriggers: [$( '#quickfind' )]
      };
      $( '#duoClassTable' ).tableFilter( options );
    } );
  </script>
</f:verbatim>

<f:loadBundle basename="de.jjw.webapp.messages.admin.duoclass" var="msg"/>
<t:htmlTag value="h3"><h:outputText value="#{msg['duoclassOverviewHeadline'] }"/></t:htmlTag>
<h:form id="adminDuoclassOverviewAction">

  <h:outputText value="#{adminDuoclassOverviewAction.text}"/>
  <c:if test="${adminDuoclassOverviewAction.duoclasses != null}">
    <c:if test="${fn:length(adminDuoclassOverviewAction.duoclasses) > 0}">
      <f:verbatim>
        <p>Quick Find: <input type="text" id="quickfind"/> <a id="cleanfilters" href="#">Clear Filters</a></p>
      </f:verbatim>
      <t:dataTable sortColumn="#{adminDuoclassOverviewAction.sortColumn}"
                   value="#{adminDuoclassOverviewAction.duoclasses}" var="cty" border="0" cellspacing="0"
                   cellpadding="0" styleClass="fancyTable" rowClasses="odd,even" width="800px"
                   id="duoClassTable" forceId="true" binding="#{adminDuoclassOverviewAction.dataTable}">
        <t:column sortable="true" defaultSorted="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="1" arrow="true">
              <h:outputText value="#{msg.age}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.age.ageShort}"/>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="2" arrow="true">
              <h:outputText value="#{msg.sex}"/>
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
              <h:outputText value="#{msg.duoTeam}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.numberOfTeams}"/>
        </t:column>
        <t:column styleClass="center">
          <f:facet name="header">
           
              <h:outputText value="#{msg.layout}"/>
           
          </f:facet>

          <h:commandLink action="#{adminDuoclassOverviewAction.showDuoclassAsWeb}">
            <t:graphicImage value="/images/webIcon.png" rendered="true" border="0" alt=""/>
            <%--<h:outputText value="#{msg.web}"/>--%>
          </h:commandLink>
          <h:commandLink action="#{adminDuoclassOverviewAction.showDuoclassAsPdf}">
            <t:graphicImage value="/images/pdfIcon.png" rendered="true" border="0" alt=""/>
            <%--<h:outputText value="#{msg.pdf}"/>--%>
          </h:commandLink>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="6" arrow="true">
              <h:outputText value="#{msg.deleteStop}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:selectBooleanCheckbox onchange="submit()" value="#{cty.deleteStop}"
                                   valueChangeListener="#{adminDuoclassOverviewAction.toggleDeleteStopListener}"/>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="7" arrow="true">
              <h:outputText value="#{msg.complete}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:selectBooleanCheckbox onchange="submit()" value="#{cty.complete}"
                                   valueChangeListener="#{adminDuoclassOverviewAction.toggleCompleteListener}"/>
        </t:column>
        <t:column sortable="true" sortPropertyName="certificationWeb" styleClass="center">
          <f:facet name="header">
            <t:commandSortHeader columnName="8" arrow="true">
              <h:outputText value="#{msg.certification}"/>
            </t:commandSortHeader>
          </f:facet>
          <%--<h:outputText value="&nbsp;" escape="false"/>--%>
          <h:commandLink action="#{adminDuoclassOverviewAction.printCertifications}">
            <t:graphicImage value="#{cty.certificationWeb}" rendered="#{cty.render}" border="0" alt=""/>
          </h:commandLink>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="9" arrow="true">
              <h:outputText value="#{msg.fights}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.numberOfOpenFightsInClass}"/> / <h:outputText value="#{cty.numberOfFightsInClass}"/>
        </t:column>
        <t:column sortable="true" styleClass="center">
          <f:facet name="header">
            <t:commandSortHeader columnName="10" arrow="true">
              <h:outputText value="#{msg.startTime}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="&nbsp;" escape="false"/>
          <h:outputText value="#{cty.estimateBeginTimeWeb}"/>
        </t:column>
        <t:column sortable="true" styleClass="center">
          <f:facet name="header">
            <t:commandSortHeader columnName="11" arrow="true">
              <h:outputText value="#{msg.estimateTime}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.estimateTimeWeb}"/>
          <h:outputText value="&nbsp;" escape="false"/>
        </t:column>
      </t:dataTable>
    </c:if>
  </c:if>
</h:form>