<%@ include file="/common/taglibs.jsp" %>
<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : preview.jsp
  ~ Created : 12 Feb 2011
  ~ Last Modified: Sat, 12 Feb 2011 21:02:55
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

<h:form id="previewTatamiAction">
  <f:loadBundle basename="de.jjw.webapp.messages.preview" var="msg"/>
  
  
   <t:htmlTag value="h3"><h:outputText value="#{msg['previewHead'] }"/></t:htmlTag>
   
      <t:dataTable value="#{previewTatamiAction.previewForTatami}" var="cty"
                   border="0" cellspacing="0" cellpadding="0" styleClass="fancyTable" rowClasses="odd,even"
                   binding="#{previewTatamiAction.dataTable}" rendered="#{previewTatamiAction.renderPreviewForTatami}">
        <t:column>
          <f:facet name="header">
            <h:outputText value="#{msg.nameRed}"/>
          </f:facet>
          <h:outputText value="#{cty.nameRed}"/>
        </t:column>
        <t:column>
          <f:facet name="header">
            <h:outputText value="#{msg.nameBlue}"/>
          </f:facet>
          <h:outputText value="#{cty.nameBlue}"/>
        </t:column>

        <t:column>
          <f:facet name="header">
            <h:outputText value="#{msg.discepline}"/>
          </f:facet>
          <h:outputText value="#{cty.discepline}"/>
        </t:column>
        <t:column>
          <f:facet name="header">
            <h:outputText value="#{msg.classDescription}"/>
          </f:facet>
          <h:outputText value="#{cty.classDescription}"/>
        </t:column>
              

        <t:column styleClass="center">
          <f:facet name="header">
            <h:outputText value="&nbsp;" escape="false"/>
          </f:facet>
          <h:commandLink action="#{previewTatamiAction.movePreviewUp}" title="#{msg.moveUp_title}">
            <t:graphicImage value="/images/edit.png" title="#{msg.moveUp_title}" rendered="true" border="0" alt=""/>
          </h:commandLink>
           <h:outputText value="&nbsp;" escape="false"/>
          <h:commandLink action="#{previewTatamiAction.deletePreview}" title="#{msg.delete_title}">
            <t:graphicImage value="/images/trash.png" title="#{msg.delete}" rendered="true" border="0" alt=""/>
          </h:commandLink>
         </t:column>
      </t:dataTable>


</h:form>
