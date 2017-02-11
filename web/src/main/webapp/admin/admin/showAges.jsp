<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : showAges.jsp
  ~ Created : 17 Jun 2010
  ~ Last Modified: Thu, 17 Jun 2010 14:19:04
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

<h:form id="adminAgeAction">
  <f:loadBundle basename="de.jjw.webapp.messages.admin.age" var="msg"/>
  <t:htmlTag value="h3"><h:outputText value="#{msg['ageHeadline'] }"/></t:htmlTag>

  <p><h:commandButton value="#{msg['gotoNewAge']}"
                      action="#{adminAgeAction.gotoNewAge}" id="gotoNewAge" tabindex="3"/>
  </p>
  <c:if test="${adminAgeAction.ages != null}">

    <c:if test="${fn:length(adminAgeAction.ages) > 0}">

      <h:dataTable value="#{adminAgeAction.ages}" var="cty"
                   border="0" cellspacing="0" cellpadding="0" styleClass="fancyTable" rowClasses="odd,even"
                   binding="#{adminAgeAction.dataTable}">
        <t:column>
          <f:facet name="header">
            <h:outputText value="#{msg.ageId}"/>
          </f:facet>
          <h:outputText value="#{cty.ageShort}"/>
        </t:column>
        <t:column>
          <f:facet name="header">
            <h:outputText value="#{msg.ageDescription}"/>
          </f:facet>
          <h:outputText value="#{cty.description}"/>
        </t:column>

        <t:column>
          <f:facet name="header">
            <h:outputText value="#{msg.ageFightingTime_Table}"/>
          </f:facet>
          <h:outputText value="#{cty.fightingTime}"/>
        </t:column>
         <t:column>
          <f:facet name="header">
            <h:outputText value="#{msg.ageFightingTimeNewa_Table}"/>
          </f:facet>
          <h:outputText value="#{cty.fightingTimeNewa}"/>
        </t:column>
        <t:column>
          <f:facet name="header">
            <h:outputText value="#{msg.ageFightingRounds_Table}"/>
          </f:facet>
          <h:outputText value="#{cty.fightingRounds}"/>
        </t:column>
        <t:column>
          <f:facet name="header">
            <h:outputText value="#{msg.ageOvertime_Table}"/>
          </f:facet>
          <h:outputText value="#{cty.overtime}"/>
        </t:column>
         <t:column>
          <f:facet name="header">
            <h:outputText value="#{msg.ageInjurytime_Table}"/>
          </f:facet>
          <h:outputText value="#{cty.injurytime}"/>
        </t:column>
        <t:column>
          <f:facet name="header">
            <h:outputText value="#{msg.ageEstimatedTime_Table}"/>
          </f:facet>
          <h:outputText value="#{cty.estimatedTime}"/>
        </t:column>
        <t:column>
          <f:facet name="header">
            <h:outputText value="#{msg.ageEstimatedTimeDuo_Table}"/>
          </f:facet>
          <h:outputText value="#{cty.estimatedTimeDuo}"/>
        </t:column>
        <t:column>
          <f:facet name="header">
            <h:outputText value="#{msg.ageEstimatedTimeNewa_Table}"/>
          </f:facet>
          <h:outputText value="#{cty.estimatedTimeNewa}"/>
        </t:column>

        <t:column styleClass="center">
          <f:facet name="header">
            <h:outputText value="&nbsp;" escape="false"/>
          </f:facet>
          <h:commandLink action="#{adminAgeAction.gotoEditAge}" title="#{msg.gotoEditAge_title}">
            <t:graphicImage value="/images/edit.png" title="#{msg.gotoEditAge_title}" rendered="true" border="0" alt=""/>
          </h:commandLink>
          <h:outputText value="&nbsp;" escape="false"/>
          <h:commandLink action="#{adminAgeAction.deleteAge}" title="#{msg.delete_title}">
            <t:graphicImage value="/images/trash.png" title="#{msg.delete_title}" rendered="true" border="0" alt=""/>
          </h:commandLink>
        </t:column>
      </h:dataTable>
    </c:if>
  </c:if>
</h:form>

