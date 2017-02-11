<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : medalRanking.jsp
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

<f:loadBundle basename="de.jjw.webapp.messages.medalRanking" var="msg"/>
<t:htmlTag value="h3"><h:outputText value="#{msg['medalHeadline'] }"/></t:htmlTag>
<h:form id="MedalRankingAction">

  <t:dataTable value="#{MedalRankingAction.medalRanking}" var="cty" border="0" cellspacing="0"
               cellpadding="0" styleClass="fancyTable" rowClasses="odd,even" width="400px"
               binding="#{MedalRankingAction.dataTable}" sortable="false">
    <t:column>
      <f:facet name="header">
        <h:outputText value="#{msg.place}"/>
      </f:facet>
      <h:outputText value="#{cty.rank}"/>
    </t:column>
    <t:column>
      <f:facet name="header">
        <h:outputText value="#{msg.team}"/>
      </f:facet>
      <h:outputText value="#{cty.name}"/>
    </t:column>
    <t:column>
      <f:facet name="header">
        <h:outputText value="#{msg.firstPlace}"/>
      </f:facet>
      <h:outputText value="#{cty.firstPlace}"/>
    </t:column>
    <t:column>
      <f:facet name="header">
        <h:outputText value="#{msg.secondPlace}"/>
      </f:facet>
      <h:outputText value="#{cty.secondPlace}"/>
    </t:column>
    <t:column>
      <f:facet name="header">
        <h:outputText value="#{msg.thirdPlace}"/>
      </f:facet>
      <h:outputText value="#{cty.thirdPlace}"/>
    </t:column>
  </t:dataTable>

  <t:panelGrid columns="2">
    <h:outputLabel id="orgLabel" for="org" value="#{msg['orgLabel']}"/>
    <h:selectOneMenu id="org" value="#{MedalRankingAction.org}" required="false" tabindex="1">
      <f:selectItems value="#{MedalRankingAction.orgALL}"/>
    </h:selectOneMenu>

    <h:outputLabel for="age" id="ageLabel" value="#{msg['ageLabel']}"/>
    <h:selectOneMenu id="age" value="#{MedalRankingAction.age}" required="false" tabindex="2"
                     converter="jjw.webapp.converter.AgeConverter">
      <f:selectItems value="#{MedalRankingAction.ageListALL}"/>
    </h:selectOneMenu>

	<h:outputText value="&nbsp;" escape="false"/>      
	<h:outputText value="&nbsp;" escape="false"/>      
	
    <t:commandButton action="#{MedalRankingAction.showWeb}" id="showWeb" tabindex="3" image="/images/webIcon.png" title="#{msg['web']}"/>    
    <t:commandButton action="#{MedalRankingAction.showPdf}" id="showPdf" tabindex="4" image="/images/pdfIcon.png" title="#{msg['pdf']}"/>

  </t:panelGrid>

</h:form>