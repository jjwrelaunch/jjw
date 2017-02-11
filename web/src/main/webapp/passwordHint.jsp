<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : passwordHint.jsp
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

<title><fmt:message key="user.passwordHint"/></title>

<f:view>

  Looking up password hint for <c:out value="${param.username}"/>...

  <h:form id="passwordForm">
    <h:inputHidden id="username" value="#{passwordHint.username}"/>

    <%-- JSF Hack for the Display Tag, from James Violette --%>
    <%-- 1. Create a dummy actionLink, w/ no value         --%>
    <h:commandLink action="#{passwordHint.execute}" id="execute">
      <f:param name="username"/>
    </h:commandLink>

  </h:form>

  <%-- 2. Write your own JavaScript function that's easy to call --%>
  <script type="text/javascript">
    function submitForm()
    {
      var f = document.forms['passwordForm'];
      f.elements['passwordForm:_link_hidden_'].value = 'passwordForm:execute';
      f.elements['username'].value = '<c:out value="${param.username}"/>';
      f.submit();
    }
    window.onload = submitForm;
  </script>

</f:view>
