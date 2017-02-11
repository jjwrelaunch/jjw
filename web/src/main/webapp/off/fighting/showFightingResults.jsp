<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : showFightingResults.jsp
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

<f:loadBundle basename="de.jjw.webapp.messages.fighter" var="msg"/>
<t:htmlTag value="h3"><h:outputText value="#{msg['admin.FightingResultHeadline'] }"/></t:htmlTag>

<h:outputText value="#{adminFightingResultsAction.text}"/>

<c:if test="${adminFightingResultsAction.fighters == null ||fn:length(adminFightingResultsAction.fighters) == 0}">
  <h:outputText value="#{msg['results.noresults'] }"/>
</c:if>

<c:if test="${adminFightingResultsAction.fighters != null}">

  <c:if test="${fn:length(adminFightingResultsAction.fighters) > 0}">

    <t:dataTable value="#{adminFightingResultsAction.fighters}" var="cty" border="0" cellspacing="0" cellpadding="0"
                 styleClass="fancyTable" rowClasses="odd,even" width="800px"
                 binding="#{adminFightingResultsAction.dataTable}">
      <t:column>
        <f:facet name="header">
          <h:outputText value="#{msg.name}"/>
        </f:facet>
        <h:outputText value="#{cty.name}"/>
      </t:column>
      <t:column>
        <f:facet name="header">
          <h:outputText value="#{msg.firstname}"/>
        </f:facet>
        <h:outputText value="#{cty.firstname}"/>
      </t:column>
      <t:column>
        <f:facet name="header">
          <h:outputText value="#{msg.team}"/>
        </f:facet>
        <h:outputText value="#{cty.team.teamName}"/>
      </t:column>
      <t:column>
        <f:facet name="header">
          <h:outputText value="#{msg.age}"/>
        </f:facet>
        <h:outputText value="#{cty.age.ageShort}"/>
      </t:column>
      <t:column>
        <f:facet name="header">
          <h:outputText value="#{msg.sex}"/>
        </f:facet>
        <h:outputText value="#{cty.sexWeb}"/>
      </t:column>
      <t:column>
        <f:facet name="header">
          <h:outputText value="#{msg.region}"/>
        </f:facet>
        <h:outputText value="#{cty.team.region.regionShort}"/>
      </t:column>
      <t:column>
        <f:facet name="header">
          <h:outputText value="#{msg.country}"/>
        </f:facet>
        <h:outputText value="#{cty.team.country.countryShort}"/>
      </t:column>
      <t:column>
        <f:facet name="header">
          <h:outputText value="#{msg.weight}"/>
        </f:facet>
        <h:outputText value="#{cty.weight}"/>
      </t:column>
      <t:column>
        <f:facet name="header">
          <h:outputText value="#{msg.weightclass}"/>
        </f:facet>
        <h:outputText value="#{cty.fightingclass.weightclass}"/>
      </t:column>
      <t:column>
        <f:facet name="header">
          <h:outputText value="#{msg.place}"/>
        </f:facet>
        <h:outputText value="#{cty.placeWeb}"/>
      </t:column>
    </t:dataTable>
  </c:if>
</c:if>