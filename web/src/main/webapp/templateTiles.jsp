<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ include file="/common/taglibs.jsp" %>
<%@ page import="de.jjw.webapp.WebExchangeContextHelper" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : templateTiles.jsp
  ~ Created : 15 Jun 2010
  ~ Last Modified: Fri, 11 Jun 2010 22:29:25
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

<f:view>
  <html>
  <head> 
    <title>Ju-Jutsu Web</title>   
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/style.css'/>"/>
     <script type="text/javascript" src="<c:url value='/scripts/jjw_generals.js'/>">
    myevent = false;
  </script>
  </head>
  <body>  
  <div class="mainWrapper">
    <f:subview id="header">
      <tiles:insertAttribute name="header"/>
    </f:subview>

    <div class="clearBoth"></div>


    <div class="navColumnLeft">
      <!-- h:messages/-->
      <f:subview id="menu">

        <tiles:insertAttribute name="menu"/>
      </f:subview>
    </div>

    <div class="mainContent">
      <%if ( WebExchangeContextHelper.isErrors( session ) )    {  %>
      <jjw:codestableErrorMessage/>
      <%} %>
      <f:subview id="main">
        <tiles:insertAttribute name="main"/>
      </f:subview>
    </div>

    <div class="clearBoth"></div>

    <f:subview id="footer">
      <tiles:insertAttribute name="footer"/>
    </f:subview>

  </div>
  </body>
 
  </html>
</f:view>
<%WebExchangeContextHelper.clearErrorVector( session ); %>

