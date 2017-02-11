<%@ include file="/common/taglibs.jsp" %>

<f:loadBundle basename="de.jjw.webapp.messages.admin.duoclass" var="msg"/>
<t:htmlTag value="h3"><h:outputText value="#{msg['duoclassOverviewHeadline'] }"/></t:htmlTag>
<h:form id="offDuoclassAction">

  <h:inputHidden id="duoclassId" value="#{offDuoclassAction.duoclassDisplay.id}"/>
  <c:if test="${offDuoclassAction.duoclassDisplay != null}"> </c:if>

  <c:if test="${offDuoclassAction.duoSimplePoolWebComponent != null}">
    <jjw:duoSimplePoolWeb messageResource="de.jjw.webapp.messages.admin.duoclass"
                          binding="#{offDuoclassAction.duoSimplePoolWebComponent}"
                          readOnly="#{offDuoclassAction.readOnly}"/>
  </c:if>

  <c:if test="${offDuoclassAction.duoDoublePoolWebComponent != null}">
    <jjw:duoDoublePoolWeb messageResource="de.jjw.webapp.messages.admin.duoclass"
                          binding="#{offDuoclassAction.duoDoublePoolWebComponent}"
                          readOnly="#{offDuoclassAction.readOnly}"/>
  </c:if>

  <c:if test="${offDuoclassAction.duoKoWebComponent != null}">
    <jjw:duoKoWeb messageResource="de.jjw.webapp.messages.admin.duoclass"
                  binding="#{offDuoclassAction.duoKoWebComponent}"
                  readOnly="#{offDuoclassAction.readOnly}"/>
  </c:if>

</h:form>
