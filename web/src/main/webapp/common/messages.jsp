<%@ include file="/common/taglibs.jsp" %>
<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : messages.jsp
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

<%-- Error Messages --%>
<c:if test="${not empty errors}">
  <div class="error" id="errorMessages">
    <c:forEach var="error" items="${errors}">
      <img src="<c:url value="/images/iconWarning.gif"/>"
           alt="<fmt:message key="icon.warning"/>" class="icon"/>
      <c:out value="${error}" escapeXml="false"/><br/>
    </c:forEach>
  </div>
  <c:remove var="errors" scope="session"/>
</c:if>

<%-- Success Messages --%>
<c:if test="${not empty messages}">
  <div class="message" id="successMessages">
    <c:forEach var="msg" items="${messages}">
      <img src="<c:url value="/images/iconInformation.gif"/>"
           alt="<fmt:message key="icon.information"/>" class="icon"/>
      <c:out value="${msg}" escapeXml="false"/><br/>
    </c:forEach>
  </div>
  <c:remove var="messages" scope="session"/>
</c:if>
