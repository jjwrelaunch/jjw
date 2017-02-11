<%@ include file="/common/taglibs.jsp" %>


<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : showCountries.jsp
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

<h:form id="adminCountryAction">
  <f:loadBundle basename="de.jjw.webapp.messages.admin.country" var="msg"/>
  <t:htmlTag value="h3"><h:outputText value="#{msg['countryHeadline'] }"/></t:htmlTag>

  <h:panelGrid columns="3">
    <h:outputLabel id="countryShortLabel" for="countryShort" value="#{msg['countryId']}"/>
    <h:outputText value=""/>
    <h:inputText value="#{adminCountryAction.country.countryShort}" id="countryShort" required="false" size="3"
                 tabindex="1" maxlength="3">
    	<jjw:codestableErrorMark field="countryShort"/>             
    </h:inputText>

    <h:outputLabel for="description" id="descriptionLabel" value="#{msg['countryDescription']}"/>
    <h:outputText value=""/>
    <h:inputText value="#{adminCountryAction.country.description}" id="description" required="false" size="30"
                 tabindex="2" maxlength="30">
        <jjw:codestableErrorMark field="description"/> 
    </h:inputText>

    <h:commandButton value="#{msg['countryAdd']}" action="#{adminCountryAction.addCountry}" id="addCountry"
                     tabindex="3"/>
    <h:outputText value=""/>
    <h:outputText value=""/>
  </h:panelGrid>

  <f:verbatim><p>&nbsp; </p></f:verbatim>
  <c:if test="${adminCountryAction.countries != null}">

    <c:if test="${fn:length(adminCountryAction.countries) > 0}">

      <h:dataTable value="#{adminCountryAction.countries}" var="cty" border="0" cellspacing="0" cellpadding="0"
                   styleClass="fancyTable" rowClasses="odd,even" binding="#{adminCountryAction.dataTable}">
        <t:column>
          <f:facet name="header">
            <h:outputText value="#{msg.countryId}"/>
          </f:facet>
          <h:outputText value="#{cty.countryShort}"/>
        </t:column>
        <t:column>
          <f:facet name="header">
            <h:outputText value="#{msg.countryDescription}"/>
          </f:facet>
          <h:outputText value="#{cty.description}"/>
        </t:column>
        <t:column styleClass="center">
          <f:facet name="header">
            <h:outputText value="&nbsp;" escape="false"/>
          </f:facet>
          <h:commandLink action="#{adminCountryAction.deleteCountry}">
            <t:graphicImage value="/images/trash.png" title="#{msg.delete}" rendered="true" border="0" alt=""/>
          </h:commandLink>
        </t:column>
      </h:dataTable>
    </c:if>
  </c:if>
</h:form>

