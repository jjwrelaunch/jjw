<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : editCountry.jsp
  ~ Created : 05 Jun 2010
  ~ Last Modified: Sat, 05 Jun 2010 21:02:54
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

<f:view>
  <f:loadBundle basename="de.jjw.webapp.messages.admin.country" var="msg"/>
  <t:htmlTag value="h3"><h:outputText value="#{msg['countryHeadlineEdit'] }"/></t:htmlTag>
  <h:form id="adminCountryAction">
    <h:panelGrid columns="3">
      <h:outputLabel for="countryShort" value="#{msg['countryId']}"/>
      <h:outputText value=""/>
      <h:inputText value="#{adminCountryAction.country.countryShort}" id="countryShort" required="false" size="3"
                   tabindex="1"/>

      <h:outputLabel for="description" value="#{msg['countryDescription']}"/>
      <h:outputText value=""/>
      <h:inputText value="#{adminCountryAction.country.description}" id="description" required="false" size="30"
                   tabindex="2"/>

      <h:commandButton value="#{msg['countryEdit']}" action="#{adminCountryAction.editCountry}" id="editCountry"
                       tabindex="3"/>
      <h:outputText value=""/>
      <h:commandButton value="#{msg['countryAbort']}" action="#{adminCountryAction.showCountries}" id="showCountry"
                       tabindex="4"/>
    </h:panelGrid>
  </h:form>
</f:view>
	