<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : showNotRegistratedNewaFighter.jsp
  ~ Created : 17 Jun 2010
  ~ Last Modified: Thu, 17 Jun 2010 14:59:50
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
        $( "#showFighterTable > tbody > tr" ).separator( function()
        {
          return $( "td:nth-child(" + column + ")", this ).text();
        } );
      }

      highlight( "<h:outputText value="#{adminShowNewaFighterAction.sortColumn}"/>" );

      var alert = 0;

      // Initialise Plugin
      var options = {
        clearFiltersControls: [$( '#cleanfilters' )],
        additionalFilterTriggers: [$( '#quickfind' )]
      };
      $( '#showFighterTable' ).tableFilter( options );
    } );
  </script>
</f:verbatim>

<f:loadBundle basename="de.jjw.webapp.messages.fighter" var="msg"/>
<t:htmlTag value="h3"><h:outputText value="#{msg['admin.allNotRegistratedFighterHeadline'] }"/></t:htmlTag>
<h:form id="adminShowNewaFighterAction">

  <h:outputText value="#{adminShowNewaFighterAction.text}"/>
  <c:if test="${adminShowNewaFighterAction.notRegistratedFighters != null}">

    <c:if test="${fn:length(adminShowNewaFighterAction.notRegistratedFighters) > 0}">

      <f:verbatim>
        <p>Quick Find: <input type="text" id="quickfind"/> <a id="cleanfilters" href="#">Clear Filters</a></p>
      </f:verbatim>
      <t:dataTable sortColumn="#{adminShowNewaFighterAction.sortColumn}"
                   value="#{adminShowNewaFighterAction.notRegistratedFighters}" var="cty" border="0" cellspacing="0"
                   cellpadding="0" styleClass="fancyTable" rowClasses="odd,even" width="800px"
                   id="showFighterTable" forceId="true" binding="#{adminShowNewaFighterAction.dataTable}">
        <t:column sortable="true" defaultSorted="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="1" arrow="true">
              <h:outputText value="#{msg.name}"/>
            </t:commandSortHeader>
          </f:facet>
           <h:commandLink action="#{adminShowNewaFighterAction.gotoEditFighter}">
            <h:outputText value="#{cty.name}"/>
          </h:commandLink>
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
              <h:outputText value="#{msg.team}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:commandLink action="#{adminShowNewaFighterAction.fighterPerTeam}">
            <h:outputText value="#{cty.team.teamName}"/>
            <f:param id="fighterPerTeam" name="fighterPer" value="#{adminShowNewaFighterAction.fighterPer}"/>
            <f:param id="fighterPerIdTeam" name="fighterPerId" value="#{adminShowNewaFighterAction.fighterPerId}"/>
            <f:param id="notRegTeam" name="notReg" value="#{adminShowNewaFighterAction.notReg}"/>
          </h:commandLink>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="4" arrow="true">
              <h:outputText value="#{msg.age}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:commandLink action="#{adminShowNewaFighterAction.fighterPerAge}">
            <h:outputText value="#{cty.age.ageShort}"/>
            <f:param id="fighterPerAge" name="fighterPer" value="#{adminShowNewaFighterAction.fighterPer}"/>
            <f:param id="fighterPerIdAge" name="fighterPerId" value="#{adminShowNewaFighterAction.fighterPerId}"/>
            <f:param id="notRegAge" name="notReg" value="#{adminShowNewaFighterAction.notReg}"/>
          </h:commandLink>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="5" arrow="true">
              <h:outputText value="#{msg.sex}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.sexWeb}"/>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="6" arrow="true">
              <h:outputText value="#{msg.region}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:commandLink action="#{adminShowNewaFighterAction.fighterPerRegion}">
            <h:outputText value="#{cty.team.region.regionShort}"/>
            <f:param id="fighterPerRegion" name="fighterPer" value="#{adminShowNewaFighterAction.fighterPer}"/>
            <f:param id="fighterPerIdRegion" name="fighterPerId" value="#{adminShowNewaFighterAction.fighterPerId}"/>
            <f:param id="notRegRegion" name="notReg" value="#{adminShowNewaFighterAction.notReg}"/>
          </h:commandLink>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="7" arrow="true">
              <h:outputText value="#{msg.country}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:commandLink action="#{adminShowNewaFighterAction.fighterPerCountry}">
            <h:outputText value="#{cty.team.country.countryShort}"/>
            <f:param id="fighterPerCountry" name="fighterPer" value="#{adminShowNewaFighterAction.fighterPer}"/>
            <f:param id="fighterPerIdCountry" name="fighterPerId" value="#{adminShowNewaFighterAction.fighterPerId}"/>
            <f:param id="notRegCountry" name="notReg" value="#{adminShowNewaFighterAction.notReg}"/>
          </h:commandLink>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="8" arrow="true">
              <h:outputText value="#{msg.weight}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.weight}"/>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="9" arrow="true">
              <h:outputText value="#{msg.weightclass}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:commandLink action="#{adminShowNewaFighterAction.fighterPerNewaclass}">
            <h:outputText value="#{cty.newaclass.weightclass}"/>
            <f:param id="fighterPer" name="fighterPer" value="#{adminShowNewaFighterAction.fighterPer}"/>
            <f:param id="fighterPerId" name="fighterPerId" value="#{adminShowNewaFighterAction.fighterPerId}"/>
            <f:param id="notRegWeightclass" name="notReg" value="#{adminShowNewaFighterAction.notReg}"/>
          </h:commandLink>
          <h:outputText value="&nbsp;" escape="false"/>
        </t:column>

        <t:column>
          <f:facet name="header">
            <h:outputText value="#{msg.editHeading}"/>
          </f:facet>
          <h:commandLink action="#{adminShowNewaFighterAction.registerFast}" rendered="#{cty.renderRegisterFast}">
            <t:graphicImage value="/images/register.png" title="#{msg.register}" rendered="true" border="0" alt=""/>
            <f:param id="fighterPerFast" name="fighterPer" value="#{adminShowNewaFighterAction.fighterPer}"/>
            <f:param id="fighterPerIdFast" name="fighterPerId" value="#{adminShowNewaFighterAction.fighterPerId}"/>
          </h:commandLink>
          <h:outputText value=" " escape="true"/>
          <h:outputText value="&nbsp;" escape="false"/>
          <h:commandLink action="#{adminShowNewaFighterAction.deleteFighter}">
            <t:graphicImage value="/images/trash.png" title="#{msg.delete}" rendered="#{adminShowNewaFighterAction.renderDelete}" border="0" alt=""/>
          </h:commandLink>
        </t:column>
      </t:dataTable>
    </c:if>
  </c:if>
</h:form>