<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : showTeams.jsp
  ~ Created : 17 Jun 2010
  ~ Last Modified: Thu, 17 Jun 2010 14:19:05
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
        $( "#showTeams > tbody > tr" ).separator( function()
        {
          if ( column == 0 || column == 1 )
          {
            return $( "td:nth-child(" + column + ")", this ).text().charAt( 0 );
          }
          else
          {
            return $( "td:nth-child(" + column + ")", this ).text();
          }
        } );
      }

      highlight( "<h:outputText value="#{adminTeamAction.sortColumn}"/>" );

      var alert = 0;

      // Initialise Plugin
      var options = {
        clearFiltersControls: [$( '#cleanfilters' )],
        additionalFilterTriggers: [$( '#quickfind' )]
      };
      $( '#showTeams' ).tableFilter( options );
    } );
  </script>
</f:verbatim>

<f:loadBundle basename="de.jjw.webapp.messages.extern.Team" var="msg"/>
<t:htmlTag value="h3"><h:outputText value="#{msg['admin.teamHeadline'] }"/></t:htmlTag>
<h:form id="adminTeamAction">
  <h:commandButton value="#{msg['admin.gotoNewTeam']}"
                   action="#{adminTeamAction.gotoNewTeam}" id="gotoNewTeam" tabindex="3"/>
                   
  <h:commandButton value="#{msg['admin.gotoUploadFile']}"
                   action="#{adminTeamAction.gotoUploadApplications}" id="gotoUploadApplications" tabindex="4"/>
											
  <h:outputText value="#{adminTeamAction.text}"/>
  <c:if test="${adminTeamAction.teams != null}">

    <c:if test="${fn:length(adminTeamAction.teams) > 0}">
      <f:verbatim>
        <p>Quick Find: <input type="text" id="quickfind"/> <a id="cleanfilters" href="#">Clear Filters</a></p>
      </f:verbatim>
      <t:dataTable sortColumn="#{adminTeamAction.sortColumn}" value="#{adminTeamAction.teams}" var="cty"
                   border="0" cellspacing="0" cellpadding="0" styleClass="fancyTable" rowClasses="odd,even"
                   binding="#{adminTeamAction.dataTable}" id="showTeams" forceId="true" align="center">       
        <t:column sortable="true" defaultSorted="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="2" arrow="true">
              <h:outputText value="#{msg.teamName}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.teamName}"/>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="3" arrow="true">
              <h:outputText value="#{msg.country}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.country.description}"/>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="4" arrow="true">
              <h:outputText value="#{msg.region}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.region.description}"/>
        </t:column>
        <t:column styleClass="center">
          <f:facet name="header">
            <h:outputText value="&nbsp;" escape="false"/>
          </f:facet>
          <h:commandLink action="#{adminTeamAction.gotoEditTeam}">
            <t:graphicImage value="/images/edit.png" title="#{msg.editTitle}" rendered="true" border="0" alt=""/>
          </h:commandLink>
          <h:outputText value="&nbsp;" escape="false"/>
          <h:commandLink action="#{adminTeamAction.gotoUploadTeam}">
            <t:graphicImage value="/images/register.png" title="#{msg.upload}" rendered="true" border="0" alt=""/>
          </h:commandLink>
          <h:outputText value="&nbsp;" escape="false"/>
          <h:commandLink action="#{adminTeamAction.deleteTeam}">
            <t:graphicImage value="/images/trash.png" title="#{msg.delete}" rendered="true" border="0" alt=""/>
          </h:commandLink>
        </t:column>
      </t:dataTable>
    </c:if>
  </c:if>
</h:form>