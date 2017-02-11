<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : newaclassOverview.jsp
  ~ Created : 17 Jun 2010
  ~ Last Modified: Thu, 17 Jun 2010 15:22:19
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

      highlight( "<h:outputText value="#{adminNewaclassOverviewAction.sortColumn}"/>" );

      // Initialise Plugin
      var options = {
        clearFiltersControls: [$( '#cleanfilters' )],
        additionalFilterTriggers: [$( '#quickfind' )]
      };
      $( '#fightingClassTable' ).tableFilter( options );
    } );
  </script>
</f:verbatim>

<f:loadBundle basename="de.jjw.webapp.messages.admin.newaclass" var="msg"/>
<t:htmlTag value="h3"><h:outputText value="#{msg['fightingclassOverviewHeadline'] }"/></t:htmlTag>
<h:form id="adminNewaclassOverviewAction">

  <h:outputText value="#{adminNewaclassOverviewAction.text}"/>
  <c:if test="${adminNewaclassOverviewAction.deleteStopNewaclasses != null}">

    <c:if test="${fn:length(adminNewaclassOverviewAction.deleteStopNewaclasses) > 0}">

      <f:verbatim>
        <p>Quick Find: <input type="text" id="quickfind"/> <a id="cleanfilters" href="#">Clear Filters</a></p>
      </f:verbatim>
      <t:dataTable sortColumn="#{adminNewaclassOverviewAction.sortColumn}"
                   value="#{adminNewaclassOverviewAction.deleteStopNewaclasses}" var="cty" border="0" cellspacing="0"
                   cellpadding="0" styleClass="fancyTable" rowClasses="odd,even" width="400px" id="fightingClassTable"
                   forceId="true" binding="#{adminNewaclassOverviewAction.dataTable}" align="center">
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

          <h:commandLink action="#{adminNewaclassOverviewAction.showNewaclassAsWeb}">
            <%--<h:outputText value="#{msg.web}"/>--%>
            <t:graphicImage value="/images/webIcon.png" rendered="true" border="0" alt=""/>
          </h:commandLink>
          <h:commandLink action="#{adminNewaclassOverviewAction.showNewaclassAsPdf}">
            <%--<h:outputText value="#{msg.pdf}"/>--%>
            <t:graphicImage value="/images/pdfIcon.png" rendered="true" border="0" alt=""/>
          </h:commandLink>
        </t:column>
      </t:dataTable>
    </c:if>
  </c:if>
</h:form>