<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : footer.jsp
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
  
<span id="legalNote"> Version 19.0.0 | &copy; 2019 <a href="http://www.ju-jutsu-web.de">ju-jutsu-web.de</a> 
<%-- c:if test="${applicationScope.userCounter != null}">

  <authz:authorize ifAllGranted="admin">
    <a href="<c:url value="/activeUsers.html"/>"><fmt:message
        key="mainMenu.activeUsers"/><c:if test="${userCounter >= 0}">${userCounter}</c:if></a>:
  </authz:authorize> 

</c:if --%> </span>