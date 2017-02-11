<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : pressInfos.jsp
  ~ Created : 17 Jun 2010
  ~ Last Modified: Thu, 17 Jun 2010 16:46:42
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

<f:loadBundle basename="de.jjw.webapp.messages.pressInfos" var="msg"/>
<h:form id="PressInfoAction">
<t:htmlTag value="h3"><h:outputText value="#{msg['headline'] }"/>
 <t:commandButton action="#{PressInfoAction.showPdf}" id="showPdf" image="/images/pdfIcon.png" title="#{msg['pdf']}"/>
</t:htmlTag>

  <h:panelGrid columns="2" styleClass="fancyTable"
               border="0" cellspacing="0" rowClasses="odd,even" cellpadding="0">

    <h:outputText value="#{msg['eventname'] }"/>
    <h:outputText value="#{PressInfoAction.pressInfo.eventName}"/>

    <h:outputText value="#{msg['eventdate'] }"/>
    <h:outputText value="#{PressInfoAction.pressInfo.eventDate}"/>

    <h:outputText value="#{msg['eventlocation'] }"/>
    <h:outputText value="#{PressInfoAction.pressInfo.eventLocation}"/>

    <h:outputText value="#{msg['totalParticipans'] }"/>
    <h:outputText value="#{PressInfoAction.pressInfo.eventParticipans}"/>

    <h:outputText value="#{msg['totalFights'] }"/>
    <h:outputText value="#{PressInfoAction.pressInfo.eventFights}"/>

    <h:outputText value="#{msg['participansTeams'] }"/>
    <h:outputText value="#{PressInfoAction.pressInfo.eventParticipansTeams}"/>

    <h:outputText value="#{msg['participansRegions'] }"/>
    <h:outputText value="#{PressInfoAction.pressInfo.eventParticipansRegions}"/>

    <h:outputText value="#{msg['participansCountries'] }"/>
    <h:outputText value="#{PressInfoAction.pressInfo.eventParticipansCountries}"/>
  </h:panelGrid>

  <t:htmlTag value="br"/>

  <h:dataTable value="#{PressInfoAction.pressInfo.eventParticipansPerAgeFighter}" var="cty"
               border="0" cellspacing="0" cellpadding="0" rowClasses="odd,even" styleClass="fancyTable">
    <h:column>
      <f:facet name="header">
        <h:outputText value="#{msg['participansFighting'] }" styleClass="bold"/>
      </f:facet>
      <h:outputText value="#{cty.label}"/>
    </h:column>
    <h:column>
      <f:facet name="header">
        <h:outputText value="#{PressInfoAction.pressInfo.eventParticipansFighter}"/>
      </f:facet>
      <h:outputText value="#{cty.value1}"/>
    </h:column>
     <h:column>
      <f:facet name="header">
        <h:outputText value="#{msg['openFights'] }"/>
      </f:facet>
      <h:outputText value="#{cty.value2}"/>
    </h:column>
  </h:dataTable>

  
  <t:htmlTag value="br"/>
   <h:dataTable value="#{PressInfoAction.pressInfo.eventParticipansPerAgeDuoteams}" var="cty2"
               border="0" cellspacing="0" cellpadding="0" rowClasses="odd,even" styleClass="fancyTable">
    <h:column>
      <f:facet name="header">
        <h:outputText value="#{msg['ParticipansDuo'] }" styleClass="bold"/>
      </f:facet>
      <h:outputText value="#{cty2.label}"/>
    </h:column>
    <h:column>
      <f:facet name="header">
        <h:outputText value="#{PressInfoAction.pressInfo.eventParticipansDuoTeams}"/>
      </f:facet>
      <h:outputText value="#{cty2.value1}"/>
    </h:column>
     <h:column>
      <f:facet name="header">
        <h:outputText value="#{msg['openFights'] }"/>
      </f:facet>
      <h:outputText value="#{cty2.value2}"/>
    </h:column>
  </h:dataTable>  
  
  
   <t:htmlTag value="br"/>
   <h:dataTable value="#{PressInfoAction.pressInfo.eventParticipansPerAgeNewaFighter}" var="cty3"
               border="0" cellspacing="0" cellpadding="0" rowClasses="odd,even" styleClass="fancyTable">
    <h:column>
      <f:facet name="header">
        <h:outputText value="#{msg['ParticipansNewa'] }" styleClass="bold"/>
      </f:facet>
      <h:outputText value="#{cty3.label}"/>
    </h:column>
    <h:column>
      <f:facet name="header">
        <h:outputText value="#{PressInfoAction.pressInfo.eventParticipansNewaFighter}"/>
      </f:facet>
      <h:outputText value="#{cty3.value1}"/>
    </h:column>
    <h:column>
      <f:facet name="header">
        <h:outputText value="#{msg['openFights'] }"/>
      </f:facet>
      <h:outputText value="#{cty3.value2}"/>
    </h:column>
  </h:dataTable>  

</h:form>