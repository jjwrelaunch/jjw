<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : showRegions.jsp
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

<h:form id="adminRegionAction">
  <f:loadBundle basename="de.jjw.webapp.messages.admin.region" var="msg"/>
  <t:htmlTag value="h3"><h:outputText value="#{msg['regionHeadline'] }"/></t:htmlTag>
  <h:panelGrid columns="3">
    <h:outputLabel for="regionShort" value="#{msg['regionId']}"/>
    <h:outputText value=""/>
    <h:inputText value="#{adminRegionAction.region.regionShort}"
                 id="regionShort" required="false" size="3" tabindex="1" maxlength="3">
    	<jjw:codestableErrorMark field="regionShort"/>
    </h:inputText>

    <h:outputLabel for="description" value="#{msg['regionDescription']}"/>
    <h:outputText value=""/>
    <h:inputText value="#{adminRegionAction.region.description}"
                 id="description" required="false" size="30" tabindex="2"
                 maxlength="30">
    	<jjw:codestableErrorMark field="description"/>
    </h:inputText>


    <h:outputLabel for="country" value="#{msg['country']}"/>
    <h:outputText value=""/>
    <h:selectOneMenu id="country"
                     value="#{adminRegionAction.region.country}" required="false"
                     tabindex="3" converter="jjw.webapp.converter.CountryConverter">
      <f:selectItems value="#{adminRegionAction.countryListALL}"/>
      <jjw:codestableErrorMark field="country"/>
    </h:selectOneMenu>
    


    <h:commandButton value="#{msg['regionAdd']}"
                     action="#{adminRegionAction.addRegion}" id="addRegion" tabindex="3"/>
    <h:outputText value=""/>
    <h:outputText value=""/>
  </h:panelGrid>
  
   <f:verbatim><p>&nbsp; </p></f:verbatim>
  
  <c:if test="${adminRegionAction.regions != null}">

    <c:if test="${fn:length(adminRegionAction.regions) > 0}">

      <h:dataTable value="#{adminRegionAction.regions}" var="cty"
                   border="0" cellspacing="0" cellpadding="0" styleClass="fancyTable" rowClasses="odd,even"
                   binding="#{adminRegionAction.dataTable}">
        <t:column>
          <f:facet name="header">
            <h:outputText value="#{msg.regionId}"/>
          </f:facet>
          <h:outputText value="#{cty.regionShort}"/>
        </t:column>
        <t:column>
          <f:facet name="header">
            <h:outputText value="#{msg.regionDescription}"/>
          </f:facet>
          <h:outputText value="#{cty.description}"/>
        </t:column>
        <t:column>
          <f:facet name="header">
            <h:outputText value="#{msg.countryDescription}"/>
          </f:facet>
          <h:outputText value="#{cty.country.description}"/>
        </t:column>

        <t:column styleClass="center">
          <f:facet name="header">
            <h:outputText value="&nbsp;" escape="false"/>
          </f:facet>
          <h:commandLink action="#{adminRegionAction.deleteRegion}">
            <t:graphicImage value="/images/trash.png" title="#{msg.delete}" rendered="true" border="0" alt=""/>
          </h:commandLink>
        </t:column>
      </h:dataTable>
    </c:if>
  </c:if>
</h:form>
