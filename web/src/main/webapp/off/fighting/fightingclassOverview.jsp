<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : fightingclassOverview.jsp
  ~ Created : 17 Jun 2010
  ~ Last Modified: Thu, 17 Jun 2010 15:22:19
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

<f:verbatim>
  <script type="text/javascript" src="<c:url value='/scripts/jquery-1.4.2.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/scripts/picnet.table.filter.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/scripts/jquery.highlighter.0.1.js'/>"></script>
  <script type="text/javascript">
    $( document ).ready( function()
    {
      function highlight( column )
      {
        $( "#fightingClassTable > tbody > tr" ).separator( function()
        {
          return $( "td:nth-child(" + column + ")", this ).text();
        } );
      }

      highlight( "<h:outputText value="#{tatamiFightingclassOverview.sortColumn}"/>" );

      // Initialise Plugin
      var options = {
        clearFiltersControls: [$( '#cleanfilters' )],
        additionalFilterTriggers: [$( '#quickfind' )]
      };
      $( '#fightingClassTable' ).tableFilter( options );
    } );
  </script>
</f:verbatim>

<f:loadBundle basename="de.jjw.webapp.messages.admin.fightingclass" var="msg"/>
<t:htmlTag value="h3"><h:outputText value="#{msg['fightingclassOverviewHeadline'] }"/></t:htmlTag>
<h:form id="tatamiFightingclassOverview">

  <h:outputText value="#{tatamiFightingclassOverview.text}"/>
  <c:if test="${tatamiFightingclassOverview.fighters != null}">

    <c:if test="${fn:length(tatamiFightingclassOverview.fighters) > 0}">

      <f:verbatim>
        <p>
          Quick Find: <input type="text" id="quickfind"/> <a id="cleanfilters" href="#">Clear Filters</a>
        </p>
      </f:verbatim>

      <t:dataTable sortColumn="#{tatamiFightingclassOverview.sortColumn}"
                   value="#{tatamiFightingclassOverview.fighters}" var="cty" border="0" cellspacing="0"
                   cellpadding="0" styleClass="fancyTable" rowClasses="odd,even" width="800px" id="fightingClassTable"
                   forceId="true" binding="#{tatamiFightingclassOverview.dataTable}">
        <t:column sortable="true" defaultSorted="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="1" arrow="true">
              <h:outputText value="#{msg.age}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.age.ageShort}"/>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="2" arrow="true">
              <h:outputText value="#{msg.sex}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.sexWeb}"/>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="3" arrow="true">
              <h:outputText value="#{msg.weightclass}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.weightclass}"/>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="4" arrow="true">
              <h:outputText value="#{msg.fighter}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.numberOfFighter}"/>
        </t:column>
        <t:column styleClass="center">
          <f:facet name="header">
           
              <h:outputText value="#{msg.layout}"/>
            
          </f:facet>

          <h:commandLink action="#{tatamiFightingclassOverview.showFightingclassAsWeb}" rendered="#{cty.classStartet}">
            <%--<h:outputText value="#{msg.web}"/>--%>
            <t:graphicImage value="/images/webIcon.png" rendered="true" border="0" alt=""/>
          </h:commandLink>
          <t:graphicImage value="/images/blank.gif" rendered="#{cty.notClassStartet}" border="0" alt="" width="24"/>          
          <h:commandLink action="#{tatamiFightingclassOverview.showFightingclassAsPdf}">
            <%--<h:outputText value="#{msg.pdf}"/>--%>
            <t:graphicImage value="/images/pdfIcon.png" rendered="true" border="0" alt=""/>
          </h:commandLink>
        </t:column>
               

        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="9" arrow="true">
              <h:outputText value="#{msg.fights}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.numberOfOpenFightsInClass}"/> / <h:outputText value="#{cty.numberOfFightsInClass}"/> 
        </t:column>
        <t:column sortable="true">
        	<f:facet name="header">
          		<t:commandSortHeader columnName="11" arrow="true">
            		<h:outputText value="#{msg.estimateTime}"/>
          		</t:commandSortHeader>
           </f:facet>
          <h:outputText value="#{cty.estimateTimeWeb}"/>
          <h:outputText value="&nbsp;" escape="false"/>
        </t:column>
        
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="12" arrow="true">
              <h:outputText value="#{msg.start}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:selectBooleanCheckbox onchange="submit()" value="#{cty.classStartet}"
                                   valueChangeListener="#{tatamiFightingclassOverview.startClassListener}"/>
          <h:outputText value="&nbsp;" escape="false"/>
          <h:outputText value="#{cty.estimateBeginTimeWeb}"/>
        </t:column>
        
        <t:column >
          <f:facet name="header">
            <t:commandSortHeader columnName="13" >
              <h:outputText value=" "/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.order}"/>
        </t:column>
      </t:dataTable>
    </c:if>
  </c:if>
</h:form>