<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : duoPool.jsp
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

<f:loadBundle basename="de.jjw.webapp.messages.admin.duoclass" var="msg"/>
<t:htmlTag value="h3"><h:outputText value="#{adminDuoclassAction.duoclassDisplay.duoclassName}"/></t:htmlTag>
<h:form id="adminDuoclassAction">

  <h:inputHidden id="duoclassId" value="#{adminDuoclassAction.duoclassDisplay.id}"/>
  <c:if test="${adminDuoclassAction.duoclassDisplay != null}"> </c:if>

  <c:if test="${adminDuoclassAction.duoSimplePoolWebComponent != null}">
    <jjw:duoSimplePoolWeb messageResource="de.jjw.webapp.messages.admin.duoclass"
                          binding="#{adminDuoclassAction.duoSimplePoolWebComponent}"
                          readOnly="false"/>
  </c:if>

  <c:if test="${adminDuoclassAction.duoDoublePoolWebComponent != null}">
    <jjw:duoDoublePoolWeb messageResource="de.jjw.webapp.messages.admin.duoclass"
                          binding="#{adminDuoclassAction.duoDoublePoolWebComponent}"
                          readOnly="false"/>
  </c:if>

  <c:if test="${adminDuoclassAction.duoKoWebComponent != null}">
    <jjw:duoKoWeb messageResource="de.jjw.webapp.messages.admin.duoclass"
                  binding="#{adminDuoclassAction.duoKoWebComponent}"
                  readOnly="false"/>
  </c:if>

</h:form>
