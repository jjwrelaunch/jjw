<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : automaticWeightclass.jsp
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

<f:loadBundle basename="de.jjw.webapp.messages.admin.weightclass" var="msg"/>
<t:htmlTag value="h3"><h:outputText value="#{msg['automaticWeightclassNewHeadline']}"/></t:htmlTag>
<h:form id="adminWeightclassAction">
  <h:inputHidden id="id" value="" binding="#{adminWeightclassAction.id}"/>
  <h:panelGrid columns="3">

    <h:outputLabel for="automaticAge" value="#{msg['weightclassAge']}"/>
    <h:outputText value="&nbsp;" escape="false"/>
    <h:selectOneMenu id="automaticAge" value="#{adminWeightclassAction.automaticAge}" required="false" tabindex="1"
                     converter="jjw.webapp.converter.AgeConverter">
      <f:selectItems value="#{adminWeightclassAction.agesForAutomaticFightingclassCreation}"/>
    </h:selectOneMenu><jjw:codestableErrorMark field="automaticAge"/>

    <h:commandButton value="#{msg['edit']}" action="#{adminWeightclassAction.autoWeightclass}" id="autoWeightclass"
                     tabindex="6"/>
    <h:outputText value="&nbsp;" escape="false"/>
    <h:commandButton value="#{msg['abort']}" action="#{adminWeightclassAction.gotoAllWeightclasses}"
                     id="gotoAllWeightclasses" tabindex="7"/>
  </h:panelGrid>
</h:form>