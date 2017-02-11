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
<t:htmlTag value="h3"><h:outputText value="#{msg['fightingclassOverviewHeadline'] }"/></t:htmlTag>
<h:form id="offFightingclassAction">

  <h:inputHidden id="fightclassId" value="#{offFightingclassAction.fightingclass.id}"/>
  <c:if test="${offFightingclassAction.fightingclass != null}"> </c:if>

  <c:if test="${offFightingclassAction.fightingSimplePoolWebComponent != null}">
    <jjw:fightingSimplePoolWeb messageResource="de.jjw.webapp.messages.admin.fightingclass"
                               binding="#{offFightingclassAction.fightingSimplePoolWebComponent}"
                               readOnly="#{offFightingclassAction.readOnly}"/>
  </c:if>

  <c:if test="${offFightingclassAction.fightingDoublePoolWebComponent != null}">
    <jjw:fightingDoublePoolWeb messageResource="de.jjw.webapp.messages.admin.fightingclass"
                               binding="#{offFightingclassAction.fightingDoublePoolWebComponent}"
                               readOnly="#{offFightingclassAction.readOnly}"/>
  </c:if>

  <c:if test="${offFightingclassAction.fightingKoWebComponent != null}">
    <jjw:fightingKoWeb messageResource="de.jjw.webapp.messages.admin.fightingclass"
                       binding="#{offFightingclassAction.fightingKoWebComponent}"
                       readOnly="#{offFightingclassAction.readOnly}"/>
  </c:if>
</h:form>
