<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : showConfig.jsp
  ~ Created : 05 Jun 2010
  ~ Last Modified: Sat, 05 Jun 2010 21:02:55
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

<f:loadBundle basename="de.jjw.webapp.messages.admin.config" var="msg"/>
<t:htmlTag value="h3"><h:outputText value="#{msg['configHeadlineEdit'] }"/></t:htmlTag>
<h:form id="adminConfigAction">
  <h:panelGrid columns="3">

    <h:outputLabel for="eventName" value="#{msg['eventName']}"/>
    <h:outputText value=""/>
    <h:inputText value="#{adminConfigAction.config.eventName}" id="eventName" required="false" size="30" tabindex="1"
                 maxlength="30"/>

    <h:outputLabel for="eventLocation" value="#{msg['eventLocation']}"/>
    <h:outputText value=""/>
    <h:inputText value="#{adminConfigAction.config.eventLocation}" id="eventLocation" required="false" size="30"
                 tabindex="2" maxlength="30"/>

    <h:outputLabel for="eventDate" value="#{msg['eventDate']}"/>
    <h:outputText value=""/>
    <h:inputText value="#{adminConfigAction.config.eventDate}" id="eventDate" required="false" size="30" tabindex="3"
                 maxlength="10"/>

    <h:outputLabel for="websiteHeadLine1" value="#{msg['websiteHeadLine1']}"/>
    <h:outputText value=""/>
    <h:inputText value="#{adminConfigAction.config.websiteHeadLine1}" id="websiteHeadLine1" required="false" size="30"
                 tabindex="4" maxlength="30"/>

    <h:outputLabel for="websiteHeadLine2" value="#{msg['websiteHeadLine2']}"/>
    <h:outputText value=""/>
    <h:inputText value="#{adminConfigAction.config.websiteHeadLine2}" id="websiteHeadLine2" required="false" size="30"
                 tabindex="5" maxlength="30"/>

    <h:outputLabel for="pdfHeadLine1" value="#{msg['pdfHeadLine1']}"/>
    <h:outputText value=""/>
    <h:inputText value="#{adminConfigAction.config.pdfHeadLine1}" id="pdfHeadLine1" required="false" size="30"
                 tabindex="6" maxlength="30"/>

    <h:outputLabel for="pdfHeadLine2" value="#{msg['pdfHeadLine2']}"/>
    <h:outputText value=""/>
    <h:inputText value="#{adminConfigAction.config.pdfHeadLine2}" id="pdfHeadLine2" required="false" size="30"
                 tabindex="7" maxlength="30"/>


    <h:outputLabel for="certificationType" value="#{msg['certificationType']}"/>
    <h:outputText value=""/>
    <h:selectOneMenu id="certificationType" value="#{adminConfigAction.config.certificationType}" required="false"
                     tabindex="8">
      <f:selectItems value="#{adminConfigAction.certificationTypeList}"/>
    </h:selectOneMenu>
    <jjw:codestableErrorMark field="certificationType"/>

    <h:outputLabel for="logo" value="#{msg['logo']}"/>
    <h:outputText value=""/>
    <h:selectOneMenu id="logo" value="#{adminConfigAction.config.logo}" required="false" tabindex="9">
      <f:selectItems value="#{adminConfigAction.logoList}"/>
    </h:selectOneMenu>
    <jjw:codestableErrorMark field="logo"/>

    <h:outputLabel for="fightRevenge" value="#{msg['revenge']}"/>
    <h:outputText value=""/>
    <h:selectOneMenu id="fightRevenge" value="#{adminConfigAction.config.fightRevenge}" required="false" tabindex="9">
      <f:selectItems value="#{adminConfigAction.revengeList}"/>
    </h:selectOneMenu>
    <jjw:codestableErrorMark field="fightRevenge"/>

    <h:outputLabel for="fighterDeleteable" value="#{msg['fighterDeleteable']}"/>
    <h:outputText value=""/>
    <h:selectBooleanCheckbox value="#{adminConfigAction.config.fighterDeleteable}" required="false" tabindex="10"/>

	<h:outputLabel for="certificationPlaces" value="#{msg['certificationPlaces']}"/>
    <h:outputText value=""/>
    <h:inputText value="#{adminConfigAction.config.certificationPlaces}" id="certificationPlaces" required="false" size="30"
                 maxlength="3"/>
                 
    <h:outputLabel for="video" value="#{msg['video']}"/>
    <h:outputText value=""/>
    <h:selectBooleanCheckbox id="video" value="#{adminConfigAction.config.videoOn}" required="false" tabindex="11"/>
    
    
    <h:outputText value="&nbsp;" escape="false"/>
    <h:outputText value="&nbsp;" escape="false"/>
    <h:outputText value="&nbsp;" escape="false"/>

    <h:commandButton value="#{msg['configSave']}" action="#{adminConfigAction.configSave}" id="configSave"
                     tabindex="11"/>
    <h:outputText value=""/>
    <h:outputText value=""/>
  </h:panelGrid>
</h:form>