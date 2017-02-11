<%@ include file="/common/taglibs.jsp" %>

<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : showStatistics.jsp
  ~ Created : 23 Jul 2010
  ~ Last Modified: Fr, 23 Jul 2010 14:19:04
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

<h:form id="adminStatisticAction">
  <f:loadBundle basename="de.jjw.webapp.messages.admin.statistic" var="msg"/>
  <t:htmlTag value="h3"><h:outputText value="#{msg['statisticHeadline'] }"/></t:htmlTag>

	<t:panelGrid columns="1">
		<t:htmlTag value="b"><t:outputText value="#{msg.resultsHeadline}"/></t:htmlTag>		
		<t:commandLink action="#{adminStatisticAction.FightTimesPDF}"
			value="#{msg.fightTimes}" target="_blank" />		
                 
      <t:commandLink action="#{adminStatisticAction.showFightingclassAsPdf}"
			value="#{msg.fightingResults}" target="_blank" />
	  <t:commandLink action="#{adminStatisticAction.showDuoResultAsPdf}"
			value="#{msg.duoResults}" target="_blank" />
	  <t:commandLink action="#{adminStatisticAction.showNewaFightingclassAsPdf}"
			value="#{msg.newaResults}" target="_blank" />
			
	  <t:commandLink action="#{adminStatisticAction.AllClasses}"
			value="#{msg.pdfPools}" target="_blank" />
			
	  <t:commandLink action="#{adminStatisticAction.fastFight}"
			value="#{msg.fastFight}" target="_blank" />
	  <t:commandLink action="#{adminStatisticAction.highestDuoPoints}"
			value="#{msg.highestDuoPoints}" target="_blank" />
			
	  <t:commandLink action="#{adminStatisticAction.fastNewaFight}"
			value="#{msg.fastNewaFight}" target="_blank" />
			
	</t:panelGrid>
	
	<f:verbatim><p>&nbsp;</p></f:verbatim>
	
	<t:panelGrid columns="3">
		<t:htmlTag value="b"><t:outputText value="#{msg.listHeadline}"/></t:htmlTag>
		<t:outputText value="&nbsp;" escape="false"/>
		<t:outputText value="&nbsp;" escape="false"/>
		
		<t:outputText value="#{msg.fighting}"/>
		<t:outputText value="#{msg.duo}"/>
		<t:outputText value="#{msg.newaza}"/>
		
		<t:commandLink action="#{adminStatisticAction.blancoFightingPool}"
			value="#{msg.poolList}" target="_blank" />
		<t:commandLink action="#{adminStatisticAction.blancoDuoPool}"
			value="#{msg.poolList}" target="_blank" />
		<t:outputText value="&nbsp;" escape="false"/>
                 
    	<t:commandLink action="#{adminStatisticAction.blancoFightingDPool}"
			value="#{msg.DpoolList}" target="_blank" />
		<t:commandLink action="#{adminStatisticAction.blancoDuoDPool}"
			value="#{msg.DpoolList}" target="_blank" />
		<t:outputText value="&nbsp;" escape="false"/>
			
		<t:commandLink action="#{adminStatisticAction.blancoFightingKO16}"
			value="#{msg.ko16List}" target="_blank" />
		<t:commandLink action="#{adminStatisticAction.blancoDuoKO16}"
			value="#{msg.ko16List}" target="_blank" />
		<t:outputText value="&nbsp;" escape="false"/>
			
		<t:commandLink action="#{adminStatisticAction.blancoFightingKO32}"
			value="#{msg.ko32List}" target="_blank" />
		<t:commandLink action="#{adminStatisticAction.blancoDuoKO32}"
			value="#{msg.ko32List}" target="_blank" />
		<t:outputText value="&nbsp;" escape="false"/>
			
		<t:commandLink action="#{adminStatisticAction.blancoFightingKO64}"
			value="#{msg.ko64List}" target="_blank" />
		<t:outputText value="&nbsp;" escape="false"/>
		<t:outputText value="&nbsp;" escape="false"/>
			
		<t:commandLink action="#{adminStatisticAction.blancoFightlist}"
			value="#{msg.logList}" target="_blank" />
		<t:commandLink action="#{adminStatisticAction.blancoDuoFightlist}"
			value="#{msg.logList}" target="_blank" />
		<t:commandLink action="#{adminStatisticAction.blancoNewaFightlist}"
			value="#{msg.logList}" target="_blank" />
	</t:panelGrid>
	
	<f:verbatim><p>&nbsp;</p></f:verbatim>
	
	<t:panelGrid columns="2">
		<t:htmlTag value="b"><t:outputText value="#{msg.exportHeadline}"/></t:htmlTag>
		<t:outputText value="&nbsp;" escape="false"/>
		
		<t:commandLink action="#{adminStatisticAction.dumpDB}"
			value="#{msg.dump}" target="_blank" />
		<t:commandLink action="#{adminStatisticAction.gotoMarkFighterForExport}"
			value="#{msg.markFighterForExport}"/>
		
		<t:commandLink action="#{adminStatisticAction.eventExport}"
			value="#{msg.eventExport}" target="_blank" />
		<t:commandLink action="#{adminStatisticAction.gotoMarkDuoForExport}"
			value="#{msg.markDuoForExport}"/>
			
		<t:commandLink action="#{adminStatisticAction.sendExport}"
			value="#{msg.sendExport}" />
		<t:commandLink action="#{adminStatisticAction.gotoMarkNewaForExport}"
			value="#{msg.markNewaForExport}"/>
			
		<t:outputText value="&nbsp;" escape="false"/>
		
		<t:commandLink action="#{adminStatisticAction.extortToJSON}"
			value="#{msg.extortToJSON}" />
	</t:panelGrid>

</h:form>

