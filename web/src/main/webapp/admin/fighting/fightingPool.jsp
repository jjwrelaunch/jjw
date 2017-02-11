<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : fightingPool.jsp
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

<f:loadBundle basename="de.jjw.webapp.messages.admin.fightingclass" var="msg"/>
<t:htmlTag value="h3">
	<h:outputText value="#{adminFightingclassAction.fightingclass.age.description}"/>
	<h:outputText value=" "/>
	<h:outputText value="#{adminFightingclassAction.sexWebForPools}"/>
	<h:outputText value=" "/>
	<h:outputText value="#{adminFightingclassAction.fightingclass.weightclass}"/>
</t:htmlTag>
<h:form id="adminFightingclassAction">

  <h:inputHidden id="fightclassId" value="#{adminFightingclassAction.fightingclass.id}"/>
  <c:if test="${adminFightingclassAction.fightingclass != null}"> </c:if>

  <c:if test="${adminFightingclassAction.fightingSimplePoolWebComponent != null}">
    <jjw:fightingSimplePoolWeb messageResource="de.jjw.webapp.messages.admin.fightingclass"
                               binding="#{adminFightingclassAction.fightingSimplePoolWebComponent}"
                               readOnly="false"/>
  </c:if>

  <c:if test="${adminFightingclassAction.fightingDoublePoolWebComponent != null}">
    <jjw:fightingDoublePoolWeb messageResource="de.jjw.webapp.messages.admin.fightingclass"
                               binding="#{adminFightingclassAction.fightingDoublePoolWebComponent}"
                               readOnly="false"/>
  </c:if>

  <c:if test="${adminFightingclassAction.fightingKoWebComponent != null}">
    <jjw:fightingKoWeb messageResource="de.jjw.webapp.messages.admin.fightingclass"
                       binding="#{adminFightingclassAction.fightingKoWebComponent}"
                       readOnly="false"/>
  </c:if>
</h:form>