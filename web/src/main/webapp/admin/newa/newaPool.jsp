<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : newaPool.jsp
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

<f:loadBundle basename="de.jjw.webapp.messages.admin.newaclass" var="msg"/>
<t:htmlTag value="h3">
	<h:outputText value="#{adminNewaclassAction.newaclass.age.description}"/>
	<h:outputText value=" "/>
	<h:outputText value="#{adminNewaclassAction.sexWebForPools}"/>
	<h:outputText value=" "/>
	<h:outputText value="#{adminNewaclassAction.newaclass.weightclass}"/>
</t:htmlTag>
<h:form id="adminNewaclassAction">

  <h:inputHidden id="newaclassId" value="#{adminNewaclassAction.newaclass.id}"/>
  <c:if test="${adminNewaclassAction.newaclass != null}"> </c:if>

  <c:if test="${adminNewaclassAction.newaSimplePoolWebComponent != null}">
    <jjw:newaSimplePoolWeb messageResource="de.jjw.webapp.messages.admin.newaclass"
                               binding="#{adminNewaclassAction.newaSimplePoolWebComponent}"
                               readOnly="false"/>
  </c:if>

  <c:if test="${adminNewaclassAction.newaDoublePoolWebComponent != null}">
    <jjw:newaDoublePoolWeb messageResource="de.jjw.webapp.messages.admin.newaclass"
                               binding="#{adminNewaclassAction.newaDoublePoolWebComponent}"
                               readOnly="false"/>
  </c:if>

  <c:if test="${adminNewaclassAction.newaKoWebComponent != null}">
    <jjw:newaKoWeb messageResource="de.jjw.webapp.messages.admin.newaclass"
                       binding="#{adminNewaclassAction.newaKoWebComponent}"
                       readOnly="false"/>
  </c:if>
</h:form>