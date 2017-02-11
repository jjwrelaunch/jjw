<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : loginForm.jsp
  ~ Created : 17 Jun 2010
  ~ Last Modified: Thu, 17 Jun 2010 15:51:13
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

<form method="post" id="loginForm" action="<c:url value="/j_spring_security_check"/>">
  <table width="800px">
    <tr>
      <td colspan="2">
        <c:if test="${param.error != null}">
          <div class="warning" id="loginError">
            <fmt:message key="errors.password.mismatch"/>
          </div>
        </c:if>
      </td>
    </tr>
    <tr>
      <th>
        <label for="j_username" class="required">
          * <fmt:message key="label.username"/>:
        </label>
      </th>
      <td>
        <input type="text" name="j_username" id="j_username" size="35" tabindex="1"/>
      </td>
    </tr>
    <tr>
      <th style="white-space: nowrap">
        <label for="j_password" class="required">
          * <fmt:message key="label.password"/>:
        </label>
      </th>
      <td>
        <input type="password" name="j_password" id="j_password" size="35" tabindex="2"/>
      </td>
    </tr>

    <tr>
      <td></td>
      <td>
        <input type="submit" class="button" name="login" value="<fmt:message key="button.login"/>" tabindex="4"/>       
      </td>
    </tr>    
  </table>
</form>