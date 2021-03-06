<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : markDuoForExport.jsp
  ~ Created : 17 Jun 2010
  ~ Last Modified: Thu, 17 Jun 2010 17:57:02
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
        $( "#duoTeamForExportTable > tbody > tr" ).separator( function()
        {
          return $( "td:nth-child(" + column + ")", this ).text();
        } );
      }

      highlight( "<h:outputText value="#{adminShowDuoTeamAction.sortColumn}"/>" );

      var alert = 0;

      // Initialise Plugin
      var options = {
        clearFiltersControls: [$( '#cleanfilters' )],
        additionalFilterTriggers: [$( '#onlyyes' ), $( '#onlyno' ), $( '#quickfind' )],
        matchingRow: function( state, tr, textTokens )
        {
          if ( !state || !state.id )
          {
            return true;
          }
          switch ( state.id )
          {
            case 'onlyyes': return state.value !== 'true' ||
                tr.children( 'td:eq(10)' ).children( 'img[src$="tick.png"]' ).size() > 0;
            case 'onlyno': return state.value !== 'true' ||
                tr.children( 'td:eq(10)' ).children( 'img[src$="x.png"]' ).size() > 0;
            default: return true;
          }
        }
      };
      $( '#duoTeamForExportTable' ).tableFilter( options );
    } );
  </script>
</f:verbatim>

<f:loadBundle basename="de.jjw.webapp.messages.duoTeam" var="msg"/>
<t:htmlTag value="h3"><h:outputText value="#{msg['admin.allDuoTeamHeadline'] }"/></t:htmlTag>
<h:form id="adminShowDuoTeamAction">

  <h:outputText value="#{adminShowDuoTeamAction.text}"/>
  <c:if test="${adminShowDuoTeamAction.duoTeams != null}">
    <c:if test="${fn:length(adminShowDuoTeamAction.duoTeams) > 0}">
      <f:verbatim>
        <p>Quick Find: <input type="text" id="quickfind"/> <a id="cleanfilters" href="#">Clear Filters</a> &nbsp;
          Gewogen: <input type="checkbox" id="onlyyes"/> Nicht gewogen: <input type="checkbox" id="onlyno"/></p>
      </f:verbatim>
      <t:dataTable sortColumn="#{adminShowDuoTeamAction.sortColumn}" value="#{adminShowDuoTeamAction.duoTeams}"
                   var="cty" border="0" cellspacing="0" cellpadding="0"
                   styleClass="fancyTable" rowClasses="odd,even" width="800px" id="duoTeamForExportTable" forceId="true"
                   binding="#{adminShowDuoTeamAction.dataTable}">
        <t:column sortable="true" defaultSorted="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="1" arrow="true">
              <h:outputText value="#{msg.name}"/>
            </t:commandSortHeader>
          </f:facet>          
            <h:outputText value="#{cty.name}"/>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="2" arrow="true">
              <h:outputText value="#{msg.firstname}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.firstname}"/>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="3" arrow="true">
              <h:outputText value="#{msg.name}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText title="#{cty.firstname2} #{cty.name2}" value="#{cty.name2}"/>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="4" arrow="true">
              <h:outputText value="#{msg.firstname}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.firstname2}"/>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="5" arrow="true">
              <h:outputText value="#{msg.team}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:commandLink action="#{adminShowDuoTeamAction.duoTeamPerTeam}">
            <h:outputText value="#{cty.team.teamName}"/>
            <f:param id="duoTeamPerTeam" name="duoTeamPer" value="#{adminShowDuoTeamAction.duoTeamPer}"/>
            <f:param id="duoTeamPerIdTeam" name="duoTeamPerId" value="#{adminShowDuoTeamAction.duoTeamPerId}"/>
          </h:commandLink>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="6" arrow="true">
              <h:outputText value="#{msg.age}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:commandLink action="#{adminShowDuoTeamAction.duoTeamPerAge}">
            <h:outputText value="#{cty.age.ageShort}"/>
            <f:param id="duoTeamPerAge" name="duoTeamPer" value="#{adminShowDuoTeamAction.duoTeamPer}"/>
            <f:param id="duoTeamPerIdAge" name="duoTeamPerId" value="#{adminShowDuoTeamAction.duoTeamPerId}"/>
          </h:commandLink>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="7" arrow="true">
              <h:outputText value="#{msg.sex}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.sexWeb}"/>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="8" arrow="true">
              <h:outputText value="#{msg.region}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:commandLink action="#{adminShowDuoTeamAction.duoTeamPerRegion}">
            <h:outputText value="#{cty.team.region.regionShort}"/>
            <f:param id="duoTeamPerRegion" name="duoTeamPer" value="#{adminShowDuoTeamAction.duoTeamPer}"/>
            <f:param id="duoTeamPerIdRegion" name="duoTeamPerId" value="#{adminShowDuoTeamAction.duoTeamPerId}"/>
          </h:commandLink>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="9" arrow="true">
              <h:outputText value="#{msg.country}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:commandLink action="#{adminShowDuoTeamAction.duoTeamPerCountry}">
            <h:outputText value="#{cty.team.country.countryShort}"/>
            <f:param id="duoTeamPerCountry" name="duoTeamPer" value="#{adminShowDuoTeamAction.duoTeamPer}"/>
            <f:param id="duoTeamPerIdCountry" name="duoTeamPerId" value="#{adminShowDuoTeamAction.duoTeamPerId}"/>
          </h:commandLink>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="10" arrow="true">
              <h:outputText value="#{msg.duoclassName}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:commandLink action="#{adminShowDuoTeamAction.duoTeamPerDuoclass}">
            <h:outputText value="#{cty.duoclass.duoclassName}"/>
            <f:param id="duoTeamPerDuoclass" name="duoTeamPer" value="#{adminShowDuoTeamAction.duoTeamPer}"/>
            <f:param id="duoTeamPerIdDuoclass" name="duoTeamPerId" value="#{adminShowDuoTeamAction.duoTeamPerId}"/>
          </h:commandLink>
        </t:column>
        <t:column sortable="true">
					<f:facet name="header">
						<t:commandSortHeader columnName="11" arrow="true">
							<h:outputText value="#{msg.place}" />
						</t:commandSortHeader>
					</f:facet>
					<h:outputText value="#{cty.place}" />
		</t:column>
		
        <t:column sortable="true" styleClass="center">
          <f:facet name="header">
            <t:commandSortHeader columnName="ready" arrow="true">
              <h:outputText value="#{msg.registrated}"/>
            </t:commandSortHeader>
          </f:facet>
          <t:graphicImage value="#{cty.readyWeb}" border="0" alt=""/>
        </t:column>
        <t:column sortable="true" styleClass="center">
					<f:facet name="header">
						<t:commandSortHeader columnName="13" arrow="true">
							<h:outputText value="#{msg.markForExport}" />
						</t:commandSortHeader>
					</f:facet>
					<h:selectBooleanCheckbox onchange="submit()"
						value="#{cty.markForExport}"
						valueChangeListener="#{adminShowDuoTeamAction.toggleMarkForExportListener}" />
				</t:column>
      </t:dataTable>
    </c:if>
  </c:if>
</h:form>