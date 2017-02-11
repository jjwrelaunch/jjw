<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : fightingclassOverview.jsp
  ~ Created : 18 Jun 2010
  ~ Last Modified: Fri, 18 Jun 2010 15:03:43
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

      highlight( "<t:outputText value="#{adminFightingclassOverviewAction.sortColumn}"/>" );

      // Initialise Plugin
      var options = {
        clearFiltersControls: [$( '#cleanfilters' )],
        additionalFilterTriggers: [$( '#quickfind' )]
      };
      $( '#fightingClassTable' ).tableFilter( options );
    } );
  </script>
</f:verbatim>
<f:loadBundle basename="de.jjw.webapp.messages.admin.fightingclass" var="msg"/>
<t:htmlTag value="h3"><t:outputText value="#{msg['fightingclassOverviewHeadline'] }"/></t:htmlTag>
<h:form id="adminFightingclassOverviewAction">

  <h:outputText value="#{adminFightingclassOverviewAction.text}"/>
  <c:if test="${adminFightingclassOverviewAction.fighters != null}">

    <c:if test="${fn:length(adminFightingclassOverviewAction.fighters) > 0}">

      <f:verbatim>
        <p>Quick Find: <input type="text" id="quickfind"/> <a id="cleanfilters" href="#">Clear Filters</a></p>
      </f:verbatim>

      <t:dataTable sortColumn="#{adminFightingclassOverviewAction.sortColumn}"
                   value="#{adminFightingclassOverviewAction.fighters}" var="cty" border="0" width="800px"
                   cellspacing="0" cellpadding="0" styleClass="fancyTable" rowClasses="odd,even"
                   id="fightingClassTable" forceId="true" binding="#{adminFightingclassOverviewAction.dataTable}">
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
              <h:outputText value="#{msg.weightclass}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.weightclass}"/>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="4" arrow="true">
              <h:outputText value="#{msg.fighter}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.numberOfFighter}"/>
        </t:column>
        <t:column styleClass="center">
          <f:facet name="header">
            
              <h:outputText value="#{msg.layout}"/>
            
          </f:facet>
          <h:commandLink action="#{adminFightingclassOverviewAction.showFightingclassAsWeb}" >
            <%--<h:outputText value="#{msg.web}"/>--%>
            <t:graphicImage value="/images/webIcon.png" rendered="true" border="0" alt=""/>
          </h:commandLink>
          <h:commandLink action="#{adminFightingclassOverviewAction.showFightingclassAsPdf}">
            <%--<h:outputText value="#{msg.pdf}"/>--%>
            <t:graphicImage value="/images/pdfIcon.png" rendered="true" border="0" alt=""/>
          </h:commandLink>
        </t:column>

        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="6" arrow="true">
              <h:outputText value="#{msg.deleteStop}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:selectBooleanCheckbox onchange="submit()" value="#{cty.deleteStop}"
                                   valueChangeListener="#{adminFightingclassOverviewAction.toggleDeleteStopListener}"/>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="7" arrow="true">
              <h:outputText value="#{msg.complete}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:selectBooleanCheckbox onchange="submit()" value="#{cty.complete}"
                                   valueChangeListener="#{adminFightingclassOverviewAction.toggleCompleteListener}"/>
        </t:column>
        <t:column sortable="true" styleClass="center">
          <f:facet name="header">
            <t:commandSortHeader columnName="8" arrow="true" >
              <h:outputText value="#{msg.certification}"/>
            </t:commandSortHeader>
          </f:facet>
          <%--<h:outputText value="&nbsp;" escape="false"/>--%>
          <h:commandLink action="#{adminFightingclassOverviewAction.printCertifications}">
            <t:graphicImage value="#{cty.certificationWeb}" rendered="#{cty.complete}" border="0" alt=""/>
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
        <%--<t:column sortable="true">
          <f:facet name="header">
            <h:outputText value="#{msg.openFights}"/>
          </f:facet>
          <h:outputText value="#{cty.numberOfOpenFightsInClass}"/>
        </t:column>--%>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="10" arrow="true">
              <h:outputText value="#{msg.startTime}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="&nbsp;" escape="false"/>
          <h:outputText value="#{cty.estimateBeginTimeWeb}"/>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="11" arrow="true">
              <h:outputText value="#{msg.estimateTime}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.estimateTimeWeb}"/>
          <h:outputText value="&nbsp;" escape="false"/>
        </t:column>
      </t:dataTable>
      </p>
    </c:if>
  </c:if>
</h:form>