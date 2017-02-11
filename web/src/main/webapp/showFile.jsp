<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : showFile.jsp
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

<title><fmt:message key="display.title"/></title>
<content tag="heading"><fmt:message key="display.heading"/></content>

<p>Below is a list of attributes that were gathered in FileUpload.java.</p>

<div class="separator"></div>

<table class="detail" cellpadding="5">
  <tr>
    <th>Friendly Name:</th>
    <td><c:out value="${friendlyName}"/></td>
  </tr>
  <tr>
    <th>Filename:</th>
    <td><c:out value="${fileName}"/></td>
  </tr>
  <tr>
    <th>File content type:</th>
    <td><c:out value="${contentType}"/></td>
  </tr>
  <tr>
    <th>File size:</th>
    <td><c:out value="${size}"/></td>
  </tr>
  <tr>
    <th class="tallCell">File Location:</th>
    <td>The file has been written to: <br/>
      <a href="<c:out value="${link}"/>">
        <c:out value="${location}" escapeXml="false"/></a>
    </td>
  </tr>
  <tr>
    <td></td>
    <td class="buttonBar">
      <input type="button" name="done" id="done" value="Done"
             onclick="location.href='mainMenu.html'"/>
      <input type="button" name="done" id="done" value="Upload Another"
             onclick="location.href='selectFile.html'"/>
    </td>
  </tr>
</table>


