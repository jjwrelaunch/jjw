<%@page import="org.apache.log4j.Logger"%>
<%@ page language="java" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : error.jsp
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

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<% Logger log = Logger.getRootLogger(); %>
<html>
<head>
  <title><fmt:message key="errorPage.title"/></title>
  <link rel="stylesheet" type="text/css" media="all" href="<c:url value="/styles/default.css"/>"/>
</head>

<body>
<div id="screen">
  <div id="content">
    <h1><fmt:message key="errorPage.heading"/></h1>
    <%// @ include file="/common/messages.jsp" %>
    <% if ( exception != null && log.isDebugEnabled() )
    { %>
    <pre><% exception.printStackTrace( new java.io.PrintWriter( out ) ); %></pre>
    <% }
    else if ( (Exception) request.getAttribute( "javax.servlet.error.exception" ) != null )
    { %>
    <pre><%
      ( (Exception) request.getAttribute( "javax.servlet.error.exception" ) ).printStackTrace(
          new java.io.PrintWriter( out ) );
    %></pre>
    <% } %>
  </div>
</body>
</html>
