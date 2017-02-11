<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : selectFile.jsp
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

<title><fmt:message key="upload.title"/></title>
<content tag="heading"><fmt:message key="upload.heading"/></content>

<!--
The most important part is to declare your form's enctype to be "multipart/form-data",
and to have a form:file element that maps to your ActionForm's FormFile property
-->
<f:view>
  <f:loadBundle var="text" basename="#{fileUpload.bundleName}"/>

  <p><fmt:message key="upload.message"/></p>

  <div class="separator"></div>

  <h:form id="uploadForm" enctype="multipart/form-data" onsubmit="return validateUploadForm(this)">
    <h:panelGrid columns="3" styleClass="detail" columnClasses="label">

      <h:outputLabel for="name" value="#{text['uploadForm.name']}"/>

      <h:inputText value="#{fileUpload.name}" id="name" size="40" required="true">
        <v:commonsValidator type="required" arg="#{text['uploadForm.name']}"/>
      </h:inputText>
      <t:message for="name" styleClass="fieldError"/>

      <h:outputLabel for="uploadForm:file" value="#{text['uploadForm.file']}"/>

      <t:inputFileUpload id="file" value="#{fileUpload.file}" storage="file" required="true" size="50">
        <v:commonsValidator type="required" arg="#{text['uploadForm.file']}"/>
      </t:inputFileUpload>
      <t:message for="uploadForm:file" styleClass="fieldError"/>

      <h:inputHidden value=""/>

      <h:panelGroup styleClass="buttonBar">
        <h:commandButton value="#{text['button.upload']}" action="#{fileUpload.upload}"
                         id="upload" styleClass="button"/>
        <h:commandButton value="#{text['button.cancel']}" action="mainMenu" immediate="true"
                         id="cancel" styleClass="button" onclick="bCancel=true"/>
      </h:panelGroup>

      <h:inputHidden value=""/>
    </h:panelGrid>
  </h:form>

  <v:validatorScript functionName="validateUploadForm"/>

  <script type="text/javascript">
    Form.focusFirstElement( document.forms["uploadForm"] );
    highlightFormElements();
  </script>

</f:view>
