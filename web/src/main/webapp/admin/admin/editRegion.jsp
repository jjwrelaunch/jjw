<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : editRegion.jsp
  ~ Created : 05 Jun 2010
  ~ Last Modified: Sat, 05 Jun 2010 21:02:53
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
  <f:loadBundle basename="de.jjw.webapp.messages.admin.region" var="msg"/>
  <t:htmlTag value="h3"><h:outputText value="#{msg['regionHeadlineEdit'] }"/></t:htmlTag>
  <p>
    <h:form id="adminRegionAction">
      <h:panelGrid columns="3">
        <h:outputLabel for="regionShort" value="#{msg['regionId']}"/>
        <h:outputText value=""/>
        <h:inputText value="#{adminRegionAction.region.regionShort}" id="regionShort" required="false" size="3"
                     tabindex="1"/>

        <h:outputLabel for="description" value="#{msg['regionDescription']}"/>
        <h:outputText value=""/>
        <h:inputText value="#{adminRegionAction.region.description}" id="description" required="false" size="30"
                     tabindex="2"/>

        <h:commandButton value="#{msg['regionEdit']}" action="#{adminRegionAction.editRegion}" id="editRegion"
                         tabindex="3"/>
        <h:outputText value=""/>
        <h:commandButton value="#{msg['regionAbort']}" action="#{adminRegionAction.showRegions}" id="showRegion"
                         tabindex="4"/>
      </h:panelGrid>
    </h:form>
  </p>
</f:view>
	