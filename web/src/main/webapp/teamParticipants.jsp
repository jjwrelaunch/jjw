<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : teamParticipants.jsp
  ~ Created : 17 Jun 2010
  ~ Last Modified: Thu, 17 Jun 2010 18:49:12
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
          if ( column == 1 )
          {
            return $( "td:nth-child(" + column + ")", this ).text().charAt( 0 );
          }
          else
          {
            return $( "td:nth-child(" + column + ")", this ).text();
          }
        } );
      }

      highlight( "<h:outputText value="#{TeamParticipantsAction.sortColumn}"/>" );

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
<t:htmlTag value="h3"><h:outputText value="#{msg['teamParticipants.headline'] }"/></t:htmlTag>
<h:form id="TeamParticipantsAction">

  <c:if test="${TeamParticipantsAction.teamList != null && fn:length(TeamParticipantsAction.teamList) > 0}">
    <f:verbatim>
      <p>Quick Find: <input type="text" id="quickfind"/> <a id="cleanfilters" href="#">Clear Filters</a></p>
    </f:verbatim>
    <t:dataTable sortColumn="#{TeamParticipantsAction.sortColumn}" value="#{TeamParticipantsAction.teamList}" var="cty"
                 border="0"
                 cellspacing="0" cellpadding="0" styleClass="fancyTable" rowClasses="odd,even" id="showTeams"
                 forceId="true"
                 binding="#{TeamParticipantsAction.dataTable}" sortable="false" align="center">
      <t:column sortable="true" defaultSorted="true">
        <f:facet name="header">
          <t:commandSortHeader columnName="1" arrow="true">
            <h:outputText value="#{msg['teamParticipants.team']}"/>
          </t:commandSortHeader>
        </f:facet>
        <h:outputText value="#{cty.teamname}"/>
      </t:column>
      <t:column sortable="true">
        <f:facet name="header">
          <t:commandSortHeader columnName="2" arrow="true">
            <h:outputText value="#{msg['teamParticipants.fighter']}"/>
          </t:commandSortHeader>
        </f:facet>
        <h:outputText value="#{cty.fighterCount}"/>
      </t:column>
      <t:column sortable="true">
        <f:facet name="header">
          <t:commandSortHeader columnName="3" arrow="true">
            <h:outputText value="#{msg['teamParticipants.duo']}"/>
          </t:commandSortHeader>
        </f:facet>
        <h:outputText value="#{cty.duoCount}"/>
      </t:column>
      <t:column >
        <f:facet name="header">         
            <h:outputText value="#{msg['teamParticipants.pdf']}"/>
        </f:facet>
        <t:commandLink action="#{TeamParticipantsAction.teamResultsPDF}" target="_blank">           
            <t:graphicImage value="/images/pdfIcon.png" rendered="true" border="0" alt=""/>
          </t:commandLink>
      </t:column>
    </t:dataTable>
  </c:if>
</h:form>