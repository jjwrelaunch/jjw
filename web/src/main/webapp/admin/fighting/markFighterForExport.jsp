<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="t" uri="http://myfaces.apache.org/tomahawk"%>
<%@ include file="/common/taglibs.jsp"%>


<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : markFighterForExport.jsp
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
	<script type="text/javascript"
		src="<c:url value='/scripts/jquery-1.4.2.min.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/scripts/picnet.table.filter.min.js'/>"></script>
	<script type="text/javascript"
		src="<c:url value='/scripts/jquery.highlighter.0.1.js'/>"></script>
	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							function highlight(column) {
								$("#FighterExportTable > tbody > tr")
										.separator(
												function() {
													return $(
															"td:nth-child("
																	+ column
																	+ ")", this)
															.text();
												});
							}

							highlight("<h:outputText value="#{adminShowFighterAction.sortColumn}"/>");

							var alert = 0;

							// Initialise Plugin
							var options = {
								clearFiltersControls : [ $('#cleanfilters') ],
								additionalFilterTriggers : [ $('#onlyyes'),
										$('#onlyno'), $('#quickfind') ],
								matchingRow : function(state, tr, textTokens) {
									if (!state || !state.id) {
										return true;
									}
									switch (state.id) {
									case 'onlyyes':
										return state.value !== 'true'
												|| tr
														.children('td:eq(9)')
														.children(
																'img[src$="tick.png"]')
														.size() > 0;
									case 'onlyno':
										return state.value !== 'true'
												|| tr
														.children('td:eq(9)')
														.children(
																'img[src$="x.png"]')
														.size() > 0;
									default:
										return true;
									}
								}
							};
							$('#FighterExportTable').tableFilter(options);
						});
	</script>
</f:verbatim>

<f:loadBundle basename="de.jjw.webapp.messages.fighter" var="msg" />
<t:htmlTag value="h3">
	<h:outputText value="#{msg['admin.allFighterHeadline'] }" />
</t:htmlTag>
<h:form id="adminShowFighterAction">
	<h:outputText value="#{adminShowFighterAction.text}" />
	<c:if test="${adminShowFighterAction.fighters != null}">
		<c:if test="${fn:length(adminShowFighterAction.fighters) > 0}">
			<f:verbatim>
				<p>
					Quick Find: <input type="text" id="quickfind" /> <a
						id="cleanfilters" href="#">Clear Filters</a> &nbsp; Gewogen: <input
						type="checkbox" id="onlyyes" /> Nicht gewogen: <input
						type="checkbox" id="onlyno" />
				</p>
			</f:verbatim>
			<t:dataTable sortColumn="#{adminShowFighterAction.sortColumn}"
				value="#{adminShowFighterAction.fighters}" var="cty" border="0"
				cellspacing="0" cellpadding="0" id="FighterExportTable"
				forceId="true" styleClass="fancyTable" rowClasses="odd,even"
				width="800px" binding="#{adminShowFighterAction.dataTable}">
				<t:column sortable="true" defaultSorted="true">
					<f:facet name="header">
						<t:commandSortHeader columnName="1" arrow="true">
							<h:outputText value="#{msg.name}" />
						</t:commandSortHeader>
					</f:facet>
					<h:outputText value="#{cty.name}" />
				</t:column>
				<t:column sortable="true">
					<f:facet name="header">
						<t:commandSortHeader columnName="2" arrow="true">
							<h:outputText value="#{msg.firstname}" />
						</t:commandSortHeader>
					</f:facet>
					<h:outputText value="#{cty.firstname}" />
				</t:column>
				<t:column sortable="true">
					<f:facet name="header">
						<t:commandSortHeader columnName="3" arrow="true">
							<h:outputText value="#{msg.team}" />
						</t:commandSortHeader>
					</f:facet>
					<h:commandLink action="#{adminShowFighterAction.fighterPerTeam}">
						<h:outputText value="#{cty.team.teamName}" />
						<f:param id="fighterPerTeam" name="fighterPer"
							value="#{adminShowFighterAction.fighterPer}" />
						<f:param id="fighterPerIdTeam" name="fighterPerId"
							value="#{adminShowFighterAction.fighterPerId}" />
					</h:commandLink>
				</t:column>
				<t:column sortable="true">
					<f:facet name="header">
						<t:commandSortHeader columnName="4" arrow="true">
							<h:outputText value="#{msg.age}" />
						</t:commandSortHeader>
					</f:facet>
					<h:commandLink action="#{adminShowFighterAction.fighterPerAge}">
						<h:outputText value="#{cty.age.ageShort}" />
						<f:param id="fighterPerAge" name="fighterPer"
							value="#{adminShowFighterAction.fighterPer}" />
						<f:param id="fighterPerIdAge" name="fighterPerId"
							value="#{adminShowFighterAction.fighterPerId}" />
					</h:commandLink>
				</t:column>
				<t:column sortable="true">
					<f:facet name="header">
						<t:commandSortHeader columnName="5" arrow="true">
							<h:outputText value="#{msg.sex}" />
						</t:commandSortHeader>
					</f:facet>
					<h:outputText value="#{cty.sexWeb}" />
				</t:column>
				<t:column sortable="true">
					<f:facet name="header">
						<t:commandSortHeader columnName="6" arrow="true">
							<h:outputText value="#{msg.region}" />
						</t:commandSortHeader>
					</f:facet>
					<h:commandLink action="#{adminShowFighterAction.fighterPerRegion}">
						<h:outputText value="#{cty.team.region.regionShort}" />
						<f:param id="fighterPerRegion" name="fighterPer"
							value="#{adminShowFighterAction.fighterPer}" />
						<f:param id="fighterPerIdRegion" name="fighterPerId"
							value="#{adminShowFighterAction.fighterPerId}" />
					</h:commandLink>
				</t:column>
				<t:column sortable="true">
					<f:facet name="header">
						<t:commandSortHeader columnName="7" arrow="true">
							<h:outputText value="#{msg.country}" />
						</t:commandSortHeader>
					</f:facet>
					<h:commandLink action="#{adminShowFighterAction.fighterPerCountry}">
						<h:outputText value="#{cty.team.country.countryShort}" />
						<f:param id="fighterPerCountry" name="fighterPer"
							value="#{adminShowFighterAction.fighterPer}" />
						<f:param id="fighterPerIdCountry" name="fighterPerId"
							value="#{adminShowFighterAction.fighterPerId}" />
					</h:commandLink>
				</t:column>
				<t:column sortable="true">
					<f:facet name="header">
						<t:commandSortHeader columnName="8" arrow="true">
							<h:outputText value="#{msg.weight}" />
						</t:commandSortHeader>
					</f:facet>
					<h:outputText value="#{cty.weight}" />
				</t:column>
				<t:column sortable="true">
					<f:facet name="header">
						<t:commandSortHeader columnName="9" arrow="true">
							<h:outputText value="#{msg.weightclass}" />
						</t:commandSortHeader>
					</f:facet>
					<h:commandLink
						action="#{adminShowFighterAction.fighterPerFightingclass}">
						<h:outputText value="#{cty.fightingclass.weightclass}" />
						<f:param id="fighterPer" name="fighterPer"
							value="#{adminShowFighterAction.fighterPer}" />
						<f:param id="fighterPerId" name="fighterPerId"
							value="#{adminShowFighterAction.fighterPerId}" />
					</h:commandLink>
				</t:column>
				<t:column sortable="true">
					<f:facet name="header">
						<t:commandSortHeader columnName="10" arrow="true">
							<h:outputText value="#{msg.place}" />
						</t:commandSortHeader>
					</f:facet>
					<h:outputText value="#{cty.place}" />
				</t:column>
				<t:column sortable="true" styleClass="center">
					<f:facet name="header">
						<t:commandSortHeader columnName="11" arrow="true">
							<h:outputText value="#{msg.registrated}" />
						</t:commandSortHeader>
					</f:facet>
					<t:graphicImage value="#{cty.readyWeb}" border="0" alt="" />
				</t:column>
				<t:column sortable="true" styleClass="center">
					<f:facet name="header">
						<t:commandSortHeader columnName="12" arrow="true">
							<h:outputText value="#{msg.markForExport}" />
						</t:commandSortHeader>
					</f:facet>
					<h:selectBooleanCheckbox onchange="submit()"
						value="#{cty.markForExport}"
						valueChangeListener="#{adminShowFighterAction.toggleMarkForExportListener}" />
				</t:column>
			</t:dataTable>
		</c:if>
	</c:if>
</h:form>