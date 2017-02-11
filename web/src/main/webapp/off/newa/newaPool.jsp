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

<f:loadBundle basename="de.jjw.webapp.messages.admin.fightingclass" var="msg"/>
<t:htmlTag value="h3"><h:outputText value="#{msg['fightingclassOverviewHeadline'] }"/></t:htmlTag>
<h:form id="offNewaclassAction">

  <h:inputHidden id="fightclassId" value="#{offNewaclassAction.newaclass.id}"/>
  <c:if test="${offNewaclassAction.newaclass != null}"> </c:if>

  <c:if test="${offNewaclassAction.newaSimplePoolWebComponent != null}">
    <jjw:newaSimplePoolWeb messageResource="de.jjw.webapp.messages.admin.fightingclass"
                               binding="#{offNewaclassAction.newaSimplePoolWebComponent}"
                               readOnly="#{offNewaclassAction.readOnly}"/>
  </c:if>

  <c:if test="${offNewaclassAction.newaDoublePoolWebComponent != null}">
    <jjw:newaDoublePoolWeb messageResource="de.jjw.webapp.messages.admin.fightingclass"
                               binding="#{offNewaclassAction.newaDoublePoolWebComponent}"
                               readOnly="#{offNewaclassAction.readOnly}"/>
  </c:if>

  <c:if test="${offNewaclassAction.newaKoWebComponent != null}">
    <jjw:newaKoWeb messageResource="de.jjw.webapp.messages.admin.fightingclass"
                       binding="#{offNewaclassAction.newaKoWebComponent}"
                       readOnly="#{offNewaclassAction.readOnly}"/>
  </c:if>
</h:form>
