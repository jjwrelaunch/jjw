<%@ include file="/common/taglibs.jsp" %>
<%@page import="de.jjw.webapp.util.ConfigMain" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : header.jsp
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

<%-- Put constants into request scope --%>
<div class="headerWrapper">
  <div class="topWrapper">
    <div class="left"><img alt="JJWImage" src="<c:url value="/images/jjw/JJW_W.png"/>" height="100"/>
    </div>
    <div class="left" style="width:890px">
      <h2><%=ConfigMain.getInstance().getEventConfiguration().getWebsiteHeadLine1()%>
      </h2>

      <h2><%=ConfigMain.getInstance().getEventConfiguration().getWebsiteHeadLine2()%>
      </h2>
    </div>
    <div class="right"><img alt="JJWImage" src="<c:url value="/images/jjw/JJW_W.png"/>" height="100"/>
    </div>
  </div>
  <div class="clearBoth"></div>
  <div class="headerMenuBar">
    <img alt="JJWHeader" src="<c:url value="/images/jjw/header.png"/>" width="1180px"/>
  </div>
</div>
