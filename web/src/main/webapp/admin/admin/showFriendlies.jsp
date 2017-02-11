<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="t" uri="http://myfaces.apache.org/tomahawk" %>
<%@ include file="/common/taglibs.jsp" %>

<f:verbatim>
  <script type="text/javascript" src="<c:url value='/scripts/jquery-1.4.2.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/scripts/picnet.table.filter.min.js'/>"></script>
  <script type="text/javascript" src="<c:url value='/scripts/jquery.highlighter.0.1.js'/>"></script>
  <script type="text/javascript">
    $( document ).ready( function()
    {
      function highlight( column )
      {
        $( "#showFighterTable > tbody > tr" ).separator( function()
        {
          return $( "td:nth-child(" + column + ")", this ).text();
        } );
      }
      highlight( "<h:outputText value="#{adminShowFriendlyFighterAction.sortColumn}"/>" );
      var alert = 0;
      // Initialise Plugin
      var options = {
        clearFiltersControls: [$( '#cleanfilters' )],
        additionalFilterTriggers: [$( '#onlyyes' ), $( '#onlyno' ), $( '#quickfind' )],
        matchingRow: function( state, tr, textTokens )
        {
          if ( !state || !state.id )
          {
            return true;
          }
          switch ( state.id )
          {
            case 'onlyyes': return state.value !== 'true' ||
                tr.children( 'td:eq(9)' ).children( 'img[src$="tick.png"]' ).size() > 0;
            case 'onlyno': return state.value !== 'true' ||
                tr.children( 'td:eq(9)' ).children( 'img[src$="x.png"]' ).size() > 0;
            default: return true;
          }
        }
      };
      $( '#showFighterTable' ).tableFilter( options );
    } );
  </script>
</f:verbatim>

<f:loadBundle basename="de.jjw.webapp.messages.admin.friendly" var="msg"/>
<t:htmlTag value="h3"><h:outputText value="#{msg['friendlyHeadline'] }"/></t:htmlTag>
<h:form id="adminShowFriendlyFighterAction">

	<h:commandButton value="#{msg['newFightFighting']}" action="#{adminShowFriendlyFighterAction.gotoNewFight}"
                     id="gotoNewFight" tabindex="3" />

  <h:outputText value="#{adminShowFriendlyFighterAction.text}"/>
  <c:if test="${adminShowFriendlyFighterAction.friendlyfights != null}">
    <c:if test="${fn:length(adminShowFriendlyFighterAction.friendlyfights) > 0}">
      <f:verbatim>
        <p>Quick Find: <input type="text" id="quickfind"/> <a id="cleanfilters" href="#">Clear Filters</a></p>
      </f:verbatim>
      <t:dataTable sortColumn="#{adminShowFriendlyFighterAction.sortColumn}"
                   value="#{adminShowFriendlyFighterAction.friendlyfights}" var="cty" border="0"
                   cellspacing="0" cellpadding="0" id="showFighterTable" forceId="true"
                   styleClass="fancyTable" rowClasses="odd,even" width="800px"
                   binding="#{adminShowFriendlyFighterAction.dataTable}">
        <t:column sortable="true" defaultSorted="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="1" arrow="true">
              <h:outputText value="#{msg.name}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:commandLink action="#{adminShowFriendlyFighterAction.showFight}">
            <h:outputText value="#{cty.fighterRed.name}"/> 
            <h:outputText value="&nbsp;" escape="false"/>
            <h:outputText value="#{cty.fighterRed.firstname}"/>
          </h:commandLink>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="2" arrow="true">
              <h:outputText value="#{msg.team}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.fighterRed.team.teamName}"/>          
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="4" arrow="true">
              <h:outputText value="#{msg.age}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.fighterRed.age.ageShort}"/>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="8" arrow="true">
              <h:outputText value="#{msg.weight}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.fighterRed.weight}"/>
        </t:column> 
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="2" arrow="true">
              <h:outputText value="#{msg.name}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.fighterBlue.name}"/>
          <h:outputText value="&nbsp;" escape="false"/>
          <h:outputText value="#{cty.fighterBlue.firstname}"/>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="3" arrow="true">
              <h:outputText value="#{msg.team}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.fighterBlue.team.teamName}"/>          
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="4" arrow="true">
              <h:outputText value="#{msg.age}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.fighterBlue.age.ageShort}"/>
        </t:column>       
           <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="8" arrow="true">
              <h:outputText value="#{msg.weight}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.fighterBlue.weight}"/>
        </t:column>          
         <t:column sortable="true" styleClass="center">
          <f:facet name="header">
            <t:commandSortHeader columnName="10" arrow="true">
              <h:outputText value="#{msg.result}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.pointsRedWeb}"/>:<h:outputText value="#{cty.pointsBlueWeb}"/>
        </t:column>    
         <t:column sortable="true" styleClass="center">
          <f:facet name="header">
            <t:commandSortHeader columnName="10" arrow="true">
              <h:outputText value="#{msg.over}"/>
            </t:commandSortHeader>
          </f:facet>
          <t:graphicImage value="#{cty.fightReady}" border="0" alt=""/>
          <h:commandLink action="#{adminShowFriendlyFighterAction.deleteFight}">
            <t:graphicImage value="/images/trash.png" title="#{msg.delete}" rendered="true" border="0" alt=""/>
          </h:commandLink>
          <h:commandLink action="#{adminShowFriendlyFighterAction.showLogListFighting}" target="_blank">
          	<t:graphicImage value="/images/pdfIcon.png" rendered="true" border="0" alt=""/>
          </h:commandLink>
        </t:column>                   
      </t:dataTable>
    </c:if>
  </c:if>
  
  
<f:verbatim>
	<p>&nbsp;</p>
</f:verbatim> 
  
  
  
  	<h:commandButton value="#{msg['newFightDuo']}" action="#{adminShowFriendlyFighterAction.gotoNewDuoFight}"
                     id="gotoNewDuoFight" tabindex="3" />

  <h:outputText value="#{adminShowFriendlyFighterAction.text}"/>
  <c:if test="${adminShowFriendlyFighterAction.friendlyDuofights != null}">
    <c:if test="${fn:length(adminShowFriendlyFighterAction.friendlyDuofights) > 0}">
      <f:verbatim>
        <p>Quick Find: <input type="text" id="quickfind"/> <a id="cleanfilters2" href="#">Clear Filters</a></p>
      </f:verbatim>
      <t:dataTable sortColumn="#{adminShowFriendlyFighterAction.sortColumn}"
                   value="#{adminShowFriendlyFighterAction.friendlyDuofights}" var="cty" border="0"
                   cellspacing="0" cellpadding="0" id="showTeamTable" forceId="true"
                   styleClass="fancyTable" rowClasses="odd,even" width="800px"
                   binding="#{adminShowFriendlyFighterAction.dataTable2}">
        <t:column sortable="true" defaultSorted="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="1" arrow="true">
              <h:outputText value="#{msg.name}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:commandLink action="#{adminShowFriendlyFighterAction.showDuoFight}">
            <h:outputText value="#{cty.duoTeamRed.name}"/> 
            <h:outputText value="&nbsp;" escape="false"/>
            <h:outputText value="#{cty.duoTeamRed.firstname}"/>
          </h:commandLink>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="2" arrow="true">
              <h:outputText value="#{msg.team}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.duoTeamRed.team.teamName}"/>          
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="4" arrow="true">
              <h:outputText value="#{msg.age}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.duoTeamRed.age.ageShort}"/>
        </t:column>        
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="2" arrow="true">
              <h:outputText value="#{msg.name}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.duoTeamBlue.name}"/>
          <h:outputText value="&nbsp;" escape="false"/>
          <h:outputText value="#{cty.duoTeamBlue.firstname}"/>
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="3" arrow="true">
              <h:outputText value="#{msg.team}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.duoTeamBlue.team.teamName}"/>          
        </t:column>
        <t:column sortable="true">
          <f:facet name="header">
            <t:commandSortHeader columnName="4" arrow="true">
              <h:outputText value="#{msg.age}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.duoTeamBlue.age.ageShort}"/>
        </t:column>                        
         <t:column sortable="true" styleClass="center">
          <f:facet name="header">
            <t:commandSortHeader columnName="10" arrow="true">
              <h:outputText value="#{msg.result}"/>
            </t:commandSortHeader>
          </f:facet>
          <h:outputText value="#{cty.pointsRedWeb}"/>:<h:outputText value="#{cty.pointsBlueWeb}"/>
        </t:column>    
         <t:column sortable="true" styleClass="center">
          <f:facet name="header">
            <t:commandSortHeader columnName="10" arrow="true">
              <h:outputText value="#{msg.over}"/>
            </t:commandSortHeader>
          </f:facet>
          <t:graphicImage value="#{cty.fightReady}" border="0" alt=""/>
          <h:commandLink action="#{adminShowFriendlyFighterAction.deleteDuoFight}">
            <t:graphicImage value="/images/trash.png" title="#{msg.delete}" rendered="true" border="0" alt=""/>
          </h:commandLink>
          <h:commandLink action="#{adminShowFriendlyFighterAction.showLogListDuo}" target="_blank">
          	<t:graphicImage value="/images/pdfIcon.png" rendered="true" border="0" alt=""/>
          </h:commandLink>
        </t:column>          
      </t:dataTable>
    </c:if>
  </c:if>
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
</h:form>