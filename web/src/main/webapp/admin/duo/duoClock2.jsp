<%@ include file="/common/taglibs.jsp" %>
<f:view>

<h:form id="adminDuoClockAction">
<f:loadBundle basename="de.jjw.webapp.messages.admin.fight" var="msg"/>

<html>
<head>
<LINK REL="STYLESHEET" TYPE="text/css" HREF="../style_duo_clock2.css" TITLE="classic"/>
<script type="text/javascript" src="../scripts/jquery-1.4.2.min.js"></script>
 <script type="text/javascript" src="../scripts/RecordRTC.js"/></script>
 <script type="text/javascript" src="../scripts/video.js"/></script>
 <script type="text/javascript" src="../scripts/jjw_generals.js"/></script>
 <script type="text/javascript" src="../javascript_duo2.js"></script>
    <script>
	  $(window).bind('beforeunload', function(){
		  sendVideo();
		  return;
		});
	  var videoOn=<h:outputText value="#{adminDuoClockAction.videoWeb}"/>;
      var paraAgeDescription='<h:outputText value="#{adminDuoClockAction.fight.duoclass.age.description}"/>';
      var videoElement = document.getElementById('video');
      var videoElementScreen = document.getElementById('videoScreen');
	  var videoFightId='<h:outputText value="#{adminDuoClockAction.fight.id}"/>';
	  var videoDescription='<h:outputText value="#{adminDuoClockAction.fight.duoclass.age.description}"/> <h:outputText value="#{adminDuoClockAction.fight.duoclass.duoclassName}"/> <h:outputText value="#{adminDuoClockAction.fight.duoclass.sexWeb}"/>';
</script>
<title><h:outputText value="#{adminDuoClockAction.fight.duoclass}"/></title>
</head>
 <script>
      var paraAgeDescription='<h:outputText value="#{adminDuoClockAction.fight.duoclass.age.ageShort}"/>';
</script>
<body oncontextmenu="return false" onLoad='startfight(paraAgeDescription,    
    <h:outputText value="#{adminDuoClockAction.fight.duoclass.age.fightingTime}"/>,
	<h:outputText value="#{adminDuoClockAction.fight.duoclass.age.overtime}"/>,
	15, 
	<h:outputText value="#{adminDuoClockAction.fight.duoclass.age.injurytime}"/>);'
	onunload="shutClock();" >
	
<!--
#################################################################################################################################
Anzeige: Kämpfer aufrufen
#################################################################################################################################
-->

<div id="loadfield" style="width:100%; height:100%; display:block; background-color:#ffffff; padding:0px; margin:0px;"
     align="center">

  <div style="color:#FFFFFF; background-color:#666666;">
    <!-- Altersklasse - Geschlecht - Gewichtsklasse -->
    <div id="loadfield_weight_1" class="loadfield_sex_text">&nbsp;</div>
    <!-- Angaben in die vorangegangenen Spalten schreiben -->
  </div>

  <script
      language="javascript">showAK( '<h:outputText value="#{adminDuoClockAction.fight.duoclass.duoclassName}" />' );</script>

  <div id="call_red_1" align="center"
       style="background-image: url('<h:outputText value="#{adminDuoClockAction.imageCommandRedWithContext}"/>');margin-right:20px">
    <h:outputText value="#{adminDuoClockAction.fight.duoTeamRed.firstname}"/>
    <h:outputText value="&nbsp;/&nbsp;" escape="false"/>
    <h:outputText value="#{adminDuoClockAction.fight.duoTeamRed.firstname2}"/>
    <br/>
    <span id="call_red_1_name" class="call_red_name">
      <h:outputText value="#{adminDuoClockAction.fight.duoTeamRed.name}"/>
      <h:outputText value="&nbsp;/&nbsp;" escape="false"/>
      <h:outputText value="#{adminDuoClockAction.fight.duoTeamRed.name2}"/>
    </span>
  </div>

  <hr/>

  <div id="call_blue" align="center"
       style="background-image: url('<h:outputText value="#{adminDuoClockAction.imageCommandBlueWithContext}"/>'); margin-right:20px">
    <h:outputText value="#{adminDuoClockAction.fight.duoTeamBlue.firstname}"/>
    <h:outputText value="&nbsp;/&nbsp;" escape="false"/>
    <h:outputText value="#{adminDuoClockAction.fight.duoTeamBlue.firstname2}"/>
    <br/>
    <span id="call_blue_1_name"  class="call_blue_name">
      <h:outputText value="#{adminDuoClockAction.fight.duoTeamBlue.name}"/>
      <h:outputText value="&nbsp;/&nbsp;" escape="false"/>
      <h:outputText value="#{adminDuoClockAction.fight.duoTeamBlue.name2}"/>
    </span>
  </div>
  <hr/>

  <div style="display:block; overflow:hidden; width:100%; height:131px;">
    <t:dataTable value="#{adminDuoClockAction.previewForTatami}" var="cty" rows="1"
                   border="0" cellspacing="0" cellpadding="0" binding="#{adminDuoClockAction.dataTable}"  >
        <t:column width="40%" styleClass="font_red td_preparation"  style="font-size:50px !important;">          
          <h:outputText value="#{cty.nameRed}"/>
        </t:column>
         <t:column width="20%" styleClass="font_black td_preparation"  style="font-size:30px !important;"  >          
          <h:outputText value="#{cty.classDescription}" />
        </t:column>
         <t:column width="40%" styleClass="font_blue td_preparation"  style="font-size:50px !important;">          
          <h:outputText value="#{cty.nameBlue}" />
        </t:column>
   </t:dataTable>  
  </div>

  <div align="left">
    <table width="100%" cellpadding="0" cellspacing="0" border="0">
      <tr>
        <td width="35%" align="left" valign="top" style="font-family:Courier; font-size:12px; color:#000000;"
            onClick="window.close();">
          <span style="background-color:#AAAAAA;">&nbsp;schliessen&nbsp;</span>
        </td>
        <td width="30%" align="center" valign="top" style="font-family:Courier; font-size:12px; color:#000000;"
            onClick="handleAnzeige('2');">
         
          <span style="background-color:#AAAAAA;">&nbsp;vorbereiten&nbsp;</span>
         
        </td>
        <td width="35%" align="right" valign="top" style="font-family:Courier; font-size:12px; color:#000000;"
            onClick="handleAnzeige('0');">
          <span style="background-color:#AAAAAA;">&nbsp;k&auml;mpfen&nbsp;</span>
        </td>
      </tr>
    </table>
  </div>
</div>
<!--
#################################################################################################################################
-->

<!--
#################################################################################################################################
Anzeige: Kämpfer in Vorbereitung
#################################################################################################################################
-->

<div id="preparationfield"
     style="width:100%; height:100%; display:none; visibility:hidden; background-color:#ffffff; padding:0px; margin:0px;"
     align="center">

  <div style="color:#FFFFFF; background-color:#666666;">
    <!-- Altersklasse - Geschlecht - Gewichtsklasse -->
    <table width="100%" cellpadding="0" cellspacing="0" border="0">
      <tr>
        <td width="33%" align="left">
          <div id="loadfield_age_2" class="loadfield_age_text">&nbsp;</div>
        </td>
        <td width="34%" align="center">
          <div id="loadfield_sex_2" class="loadfield_sex_text"><h:outputText value="#{msg.inPreparation}"/>
          </div>
        </td>
        <td width="33%" align="right">
          <div id="loadfield_weight_2" class="loadfield_weight_text">&nbsp;</div>
        </td>
      </tr>
    </table>
    <!-- Angaben in die vorangegangenen Spalten schreiben -->
  </div>

  <div id="call_red_2" align="center" style="height:593px; width:100%; display:block; overflow:hidden; font-size:42px;">
    <t:dataTable value="#{adminDuoClockAction.previewForTatami}" var="cty2" rows="3"
                   border="0" cellspacing="0" cellpadding="0" binding="#{adminDuoClockAction.dataTable2}"  >
        <t:column width="40%" styleClass="font_red td_preparation"  style="font-size:50px !important;">          
          <h:outputText value="#{cty2.nameRed}"/>
        </t:column>
         <t:column width="20%" styleClass="font_black td_preparation"  style="font-size:30px !important;"  >          
          <h:outputText value="#{cty2.classDescription}" />
        </t:column>
         <t:column width="40%" styleClass="font_blue td_preparation"  style="font-size:50px !important;">          
          <h:outputText value="#{cty2.nameBlue}" />
        </t:column>
   </t:dataTable>    
  </div>


  <div align="left">
    <table width="100%" cellpadding="0" cellspacing="0" border="0">
      <tr>
        <td width="35%" align="left" valign="top" style="font-family:Courier; font-size:12px; color:#000000;"
            onClick="window.close();">
          <span style="background-color:#AAAAAA;">&nbsp;schliessen&nbsp;</span>
        </td>
        <td width="30%" align="center" valign="top" style="font-family:Courier; font-size:12px; color:#000000;"
            onClick="handleAnzeige('1');">
          <span style="background-color:#AAAAAA;">&nbsp;aufrufen&nbsp;</span>
        </td>
        <td width="35%" align="right" valign="top" style="font-family:Courier; font-size:12px; color:#000000;"
            onClick="handleAnzeige('0');">
          <span style="background-color:#AAAAAA;">&nbsp;k&auml;mpfen&nbsp;</span>
        </td>
      </tr>
    </table>
  </div>
</div>
<!--
Ende: Kämpfer in Vorbereitung
#################################################################################################################################
-->


<!--
#################################################################################################################################
Anzeige: Wettkampfanzeige
#################################################################################################################################
-->

<div id="mainbody" style="display:none; visibility:hidden; padding:0px; margin:0px;">    
		
		
<table id="globalTab" width="100%" border="0" cellspacing="0" cellpadding="0" height="700">
<tr id="redTr" height="340" bgcolor="red">
<td> 
     <table bgcolor="red" width="100%" >
     <td valign="top" >
        <table width="100%" >
        <tr>
          <td id="tdNameRed" height="100" style="padding-left:10px;" valign="top"  colspan="6" >
            <span id="redSpanName" class="VornameWhite">
              <h:outputText value="#{adminDuoClockAction.fight.duoTeamRed.firstname}"/>&nbsp;<h:outputText
            value="#{adminDuoClockAction.fight.duoTeamRed.name}"/> / 
            <h:outputText value="#{adminDuoClockAction.fight.duoTeamRed.firstname2}"/>&nbsp;<h:outputText
            value="#{adminDuoClockAction.fight.duoTeamRed.name2}"/>   </span>
            <br clear="all"/>
             <span id="redSpanTeam" class="Verein"><h:outputText value="#{adminDuoClockAction.fight.duoTeamRed.team.teamName}"/> </span>
          </td>
        </tr>
        <tr>
          <td width="390"  >
          <table>
			  <td>
				  <table>
					  <tr>
						<td><a href="#" onClick="shutClock();"><t:graphicImage value="#{adminDuoClockAction.imageCommandRed}" id="rightFlag" height="100" width="100" style="border:3px solid white;" alt=""/></a></td>
					  </tr>
					  <tr>
						<td valign="bottom" >
						   <a href="#" onClick="toggleClock('right')"><img src="../images/clock/fusen_kiken.png" id="rightKiken"></a>	
						</td>
					  </tr>
				  </table>
			  </td>
			  
				<td width="40">&nbsp;</td>	
				<td align="center">
				  <input type="text" tabindex="1" onFocus="this.select()" onChange="updatePoints('right')" class="sets" id="rSet1_1" size=1 maxlength=3 ondrop="this.value=''">
				  <input type="text" tabindex="2" onFocus="this.select()" onChange="updatePoints('right')" class="sets" id="rSet1_2" size=1 maxlength=3 ondrop="this.value=''">
				  <input type="text" tabindex="3" onFocus="this.select()" onChange="updatePoints('right')" class="sets" id="rSet1_3" size=1 maxlength=3 ondrop="this.value=''">
				  <input type="text" tabindex="4" onFocus="this.select()" onChange="updatePoints('right')" class="sets" id="rSet1_4" size=1 maxlength=3 ondrop="this.value=''">
				  <input type="text" tabindex="5" onFocus="this.select()" onChange="updatePoints('right')" class="sets" id="rSet1_5" size=1 maxlength=3 ondrop="this.value=''">
				  <br/><br/>
				  <font style="font-size: 18px;  font-family: Tahoma, Arial, Helvetica, sans-serif; font-size: 18px; font-weight: bold; color: #FFFFFF;">A</font>
				</td>
				<td width="10">&nbsp;</td>
				<td align="center">
				  <input type="text" tabindex="16" onFocus="this.select()" onChange="updatePoints('right')" class="sets" id="rSet2_1" size=1 maxlength=3 ondrop="this.value=''">
				  <input type="text" tabindex="17" onFocus="this.select()" onChange="updatePoints('right')" class="sets" id="rSet2_2" size=1 maxlength=3 ondrop="this.value=''">
				  <input type="text" tabindex="18" onFocus="this.select()" onChange="updatePoints('right')" class="sets" id="rSet2_3" size=1 maxlength=3 ondrop="this.value=''">
				  <input type="text" tabindex="19" onFocus="this.select()" onChange="updatePoints('right')" class="sets" id="rSet2_4" size=1 maxlength=3 ondrop="this.value=''">
				  <input type="text" tabindex="20" onFocus="this.select()" onChange="updatePoints('right')" class="sets" id="rSet2_5" size=1 maxlength=3 ondrop="this.value=''">
				  <br/><br/>
				  <font style="font-size: 18px;  font-family: Tahoma, Arial, Helvetica, sans-serif; font-size: 18px; font-weight: bold; color: #FFFFFF;">B</font>
				</td>
				<td width="10">&nbsp;</td>
				<td align="center">
				  <input type="text" tabindex="21" onFocus="this.select()" onChange="updatePoints('right')" class="sets" id="rSet3_1" size=1 maxlength=3 ondrop="this.value=''">
				  <input type="text" tabindex="22" onFocus="this.select()" onChange="updatePoints('right')" class="sets" id="rSet3_2" size=1 maxlength=3 ondrop="this.value=''">
				  <input type="text" tabindex="23" onFocus="this.select()" onChange="updatePoints('right')" class="sets" id="rSet3_3" size=1 maxlength=3 ondrop="this.value=''">
				  <input type="text" tabindex="24" onFocus="this.select()" onChange="updatePoints('right')" class="sets" id="rSet3_4" size=1 maxlength=3 ondrop="this.value=''">
				  <input type="text" tabindex="25" onFocus="this.select()" onChange="updatePoints('right')" class="sets" id="rSet3_5" size=1 maxlength=3 ondrop="this.value=''">
				  <br/><br/>
				  <font style="font-size: 18px;  font-family: Tahoma, Arial, Helvetica, sans-serif; font-size: 18px; font-weight: bold; color: #FFFFFF;">C</font>
				</td>
				<td width="10">&nbsp;</td>
							  					   
			</table>			
		  </td>

          <td width="5px">&nbsp;</td>



          <td align="right" >
          <table cellpadding="0" cellspacing="0" class="points-outer-border"> <!-- Punktetabelle Rahmen außen-->
          <tr>
            <td>
            <table cellpadding="0" cellspacing="0" id="rightPointsBorderTable"  class="points-inner-border">
            <tr>
              <td>                         <!-- Punktetabelle Rahmen innen-->
                <table cellspacing="0" cellpadding="0" class="points">        <!-- Punktetabelle -->
                  <tr>
                    <td align="right">
                      <img id="rightPointsUpImage.2" src="../images/clock/up_large_draft.png" title="" border="0" onClick="increasePoints('100', 'right')" onMouseOver="changeImage('rightPointsUpImage.2','up_large.png')"  onMouseOut="changeImage('rightPointsUpImage.2','up_large_draft.png')">
                    </td>
                    <td align="right">
                      <img id="rightPointsUpImage.1" src="../images/clock/up_large_draft.png" border="0" onClick="increasePoints('10', 'right')"           onMouseOver="changeImage('rightPointsUpImage.1','up_large.png')"  onMouseOut="changeImage('rightPointsUpImage.1','up_large_draft.png')">
                    </td>
                    <td align="right">
                      <img id="rightPointsUpImage.0" src="../images/clock/up_large_draft.png" border="0" onClick="increasePoints('1', 'right')"            onMouseOver="changeImage('rightPointsUpImage.0','up_large.png')"  onMouseOut="changeImage('rightPointsUpImage.0','up_large_draft.png')">
                    </td>
                    <td>
                    </td>
                    <td align="right">
                      <img id="rightPointsUpImage.-1" src="../images/clock/up_large_draft.png" border="0"   onClick="increasePoints('0.5', 'right')"        onMouseOver="changeImage('rightPointsUpImage.-1','up_large.png')"  onMouseOut="changeImage('rightPointsUpImage.-1','up_large_draft.png')">
                    </td>
                  </tr>

                  <tr>
                    <td><img src="../images/clock/bl_x_large.png" id="rightPoints.2" border="0" height="220" onClick="increasePoints('1', 'right')"></td>
                    <td><img src="../images/clock/bl_x_large.png" id="rightPoints.1" border="0" height="220" onClick="increasePoints('1', 'right')"></td>
                    <td><img src="../images/clock/0_x_large.png" id="rightPoints.0" border="0"  height="220" onClick="increasePoints('1', 'right')"></td>
                    <td><img src="../images/clock/kommaseparator_duo_large.png" id="rightPoints.S" height="220" border="0" onClick="increasePoints('1', 'right')"></td>
                    <td><img src="../images/clock/0_x_large.png" id="rightPoints.-1" border="0"  height="220" onClick="increasePoints('0.5', 'right')"></td>
                  </tr>

                  <tr>
                    <td>
                      <img id="rightPointsDownImage.2" src="../images/clock/down_large_draft.png" title="" border="0" onClick="decreasePoints('100', 'right')"
                           onMouseOver="changeImage('rightPointsDownImage.2','down_large.png')"  onMouseOut="changeImage('rightPointsDownImage.2','down_large_draft.png')">
                    </td>
                    <td>
                      <img id="rightPointsDownImage.1" src="../images/clock/down_large_draft.png" border="0"  onClick="decreasePoints('10', 'right')"
                           onMouseOver="changeImage('rightPointsDownImage.1','down_large.png')"  onMouseOut="changeImage('rightPointsDownImage.1','down_large_draft.png')">
                    </td>
                    <td>
                      <img id="rightPointsDownImage.0" src="../images/clock/down_large_draft.png" border="0"  onClick="decreasePoints('1', 'right')"
                           onMouseOver="changeImage('rightPointsDownImage.0','down_large.png')"  onMouseOut="changeImage('rightPointsDownImage.0','down_large_draft.png')"></td>
                    <td>
                    </td>
                    <td>
                      <img id="rightPointsDownImage.-1" src="../images/clock/down_large_draft.png" border="0" onClick="decreasePoints('0.5', 'right')"
                           onMouseOver="changeImage('rightPointsDownImage.-1','down_large.png')"  onMouseOut="changeImage('rightPointsDownImage.-1','down_large_draft.png')"></td>
                  </tr>
                </table>
              </td>
            </tr>
          </table>
        </td>
      </tr>
         </table>
         </td>
         </tr>
        </table>
        </td>
        
	</table>
 </td>
</tr>
   
<tr id="blueTr" height="340" bgcolor="blue">
<td>

<!-- blue -->
<table bgcolor="blue" width="100%" >
     <td valign="top" >
        <table width="100%">
        <tr>
          <td id="tdNameBlue" height="100" style="padding-left:10px;" valign="top"  colspan="6" >
            <span id="blueSpanName" class="VornameWhite"><h:outputText value="#{adminDuoClockAction.fight.duoTeamBlue.firstname}"/>&nbsp;<h:outputText
            value="#{adminDuoClockAction.fight.duoTeamBlue.name}"/>   / 
              <h:outputText value="#{adminDuoClockAction.fight.duoTeamBlue.firstname2}"/>&nbsp;<h:outputText
            value="#{adminDuoClockAction.fight.duoTeamBlue.name2}"/>   </span>
            <br clear="all"/>
             <span id="blueSpanTeam" class="Verein"><h:outputText value="#{adminDuoClockAction.fight.duoTeamBlue.team.teamName}"/> </span>
          </td>
        </tr>
        <tr>
          <td width="390">
          <table>
			<td>
				  <table>
					  <tr>
						<td><a href="#" onClick="shutClock();"> <t:graphicImage value="#{adminDuoClockAction.imageCommandBlue}" id="leftFlag" height="100" width="100" style="border:3px solid white;" alt=""/></a></td>
					  </tr>
					  <tr>
						<td valign="bottom" >
						   <a href="#" onClick="toggleClock('left')"><img src="../images/clock/fusen_kiken.png" id="leftKiken"></a>	
						</td>
					  </tr>
				  </table>
			  </td>
            
			<td width="40">&nbsp;</td>	
              <td align="center">
                <input type="text" tabindex="6" onFocus="this.select()" onChange="updatePoints('left')" class="sets" id="lSet1_1" size=1 maxlength=3 ondrop="this.value=''">
                <input type="text" tabindex="7" onFocus="this.select()" onChange="updatePoints('left')" class="sets" id="lSet1_2" size=1 maxlength=3 ondrop="this.value=''">
                <input type="text" tabindex="8" onFocus="this.select()" onChange="updatePoints('left')" class="sets" id="lSet1_3" size=1 maxlength=3 ondrop="this.value=''">
                <input type="text" tabindex="9" onFocus="this.select()" onChange="updatePoints('left')" class="sets" id="lSet1_4" size=1 maxlength=3 ondrop="this.value=''">
                <input type="text" tabindex="10" onFocus="this.select()" onChange="updatePoints('left')" class="sets" id="lSet1_5" size=1 maxlength=3 ondrop="this.value=''">
                <br/><br/>
                <font style="font-size: 18px;  font-family: Tahoma, Arial, Helvetica, sans-serif; font-size: 18px; font-weight: bold; color: #FFFFFF;">A</font>
              </td>
	   		  <td width="10">&nbsp;</td>
              <td align="center">
                <input type="text" tabindex="11" onFocus="this.select()" onChange="updatePoints('left')" class="sets" id="lSet2_1" size=1 maxlength=3 ondrop="this.value=''">
                <input type="text" tabindex="12" onFocus="this.select()" onChange="updatePoints('left')" class="sets" id="lSet2_2" size=1 maxlength=3 ondrop="this.value=''">
                <input type="text" tabindex="13" onFocus="this.select()" onChange="updatePoints('left')" class="sets" id="lSet2_3" size=1 maxlength=3 ondrop="this.value=''">
                <input type="text" tabindex="14" onFocus="this.select()" onChange="updatePoints('left')" class="sets" id="lSet2_4" size=1 maxlength=3 ondrop="this.value=''">
                <input type="text" tabindex="15" onFocus="this.select()" onChange="updatePoints('left')" class="sets" id="lSet2_5" size=1 maxlength=3 ondrop="this.value=''">
                <br/><br/>
                <font style="font-size: 18px;  font-family: Tahoma, Arial, Helvetica, sans-serif; font-size: 18px; font-weight: bold; color: #FFFFFF;">B</font>
              </td>
			  <td width="10">&nbsp;</td>
              <td align="center">
                <input type="text" tabindex="26" onFocus="this.select()" onChange="updatePoints('left')" class="sets" id="lSet3_1" size=1 maxlength=3 ondrop="this.value=''">
                <input type="text" tabindex="27" onFocus="this.select()" onChange="updatePoints('left')" class="sets" id="lSet3_2" size=1 maxlength=3 ondrop="this.value=''">
                <input type="text" tabindex="28" onFocus="this.select()" onChange="updatePoints('left')" class="sets" id="lSet3_3" size=1 maxlength=3 ondrop="this.value=''">
                <input type="text" tabindex="29" onFocus="this.select()" onChange="updatePoints('left')" class="sets" id="lSet3_4" size=1 maxlength=3 ondrop="this.value=''">
                <input type="text" tabindex="30" onFocus="this.select()" onChange="updatePoints('left')" class="sets" id="lSet3_5" size=1 maxlength=3 ondrop="this.value=''">
                <br/><br/>
                <font style="font-size: 18px;  font-family: Tahoma, Arial, Helvetica, sans-serif; font-size: 18px; font-weight: bold; color: #FFFFFF;">C</font>
              </td>
		  	  <td width="10">&nbsp;</td>
             
            
          
          </table>
          </td>

          <td width="10px">&nbsp;</td>


        <td align="right" >
        <table cellpadding="0" cellspacing="0" class="points-outer-border">
      <tr>
        <td> <!-- Punktetabelle Rahmen -->
          <table cellpadding="0" cellspacing="0" id="leftPointsBorderTable" class="points-inner-border">
            <tr>
              <td>
                <table border="0" cellspacing="0" cellpadding="0" class="points">     <!-- Punktetabelle -->
                  <tr>
                    <td>
                      <img id="leftPointsUpImage.2" src="../images/clock/up_large_draft.png" title="" border="0"  onClick="increasePoints('100', 'left')"
                           onMouseOver="changeImage('leftPointsUpImage.2','up_large.png')"  onMouseOut="changeImage('leftPointsUpImage.2','up_large_draft.png')">
                    </td>
                    <td>
                      <img id="leftPointsUpImage.1" src="../images/clock/up_large_draft.png" border="0"        onClick="increasePoints('10', 'left')"
                           onMouseOver="changeImage('leftPointsUpImage.1','up_large.png')"              onMouseOut="changeImage('leftPointsUpImage.1','up_large_draft.png')">
                    </td>
                    <td>
                      <img id="leftPointsUpImage.0" src="../images/clock/up_large_draft.png" border="0"  onClick="increasePoints('1', 'left')"
                           onMouseOver="changeImage('leftPointsUpImage.0','up_large.png')"              onMouseOut="changeImage('leftPointsUpImage.0','up_large_draft.png')">
                    </td>
                    <td>
                    </td>
                    <td>
                      <img id="leftPointsUpImage.-1" src="../images/clock/up_large_draft.png" border="0"  onClick="increasePoints('0.5', 'left')"
                           onMouseOver="changeImage('leftPointsUpImage.-1','up_large.png')"              onMouseOut="changeImage('leftPointsUpImage.-1','up_large_draft.png')">
                    </td>
                  </tr>

                  <tr>
                    <td><img src="../images/clock/bl_x_large.png" id="leftPoints.2" border="0"  height="220" onClick="increasePoints('1', 'left')"></td>
                    <td><img src="../images/clock/bl_x_large.png" id="leftPoints.1" border="0" height="220" onClick="increasePoints('1', 'left')"></td>
                    <td><img src="../images/clock/0_x_large.png" id="leftPoints.0" border="0" height="220" onClick="increasePoints('1', 'left')"></td>
                    <td><img src="../images/clock/kommaseparator_duo_large.png" id="leftPoints.S" height="220" onClick="increasePoints('1', 'left')"></td>
                    <td><img src="../images/clock/0_x_large.png" id="leftPoints.-1" border="0" height="220" onClick="increasePoints('0.5', 'left')"></td>
                  </tr>

                  <tr>
                    <td>
                      <img id="leftPointsDownImage.2" src="../images/clock/down_large_draft.png" title=""   border="0" onClick="decreasePoints('100', 'left')"
                           onMouseOver="changeImage('leftPointsDownImage.2','down_large.png')"             onMouseOut="changeImage('leftPointsDownImage.2','down_large_draft.png')">
                    </td>
                    <td>
                      <img id="leftPointsDownImage.1" src="../images/clock/down_large_draft.png" border="0" onClick="decreasePoints('10', 'left')"
                           onMouseOver="changeImage('leftPointsDownImage.1','down_large.png')"             onMouseOut="changeImage('leftPointsDownImage.1','down_large_draft.png')">
                    </td>
                    <td>
                      <img id="leftPointsDownImage.0" src="../images/clock/down_large_draft.png" border="0" onClick="decreasePoints('1', 'left')"
                           onMouseOver="changeImage('leftPointsDownImage.0','down_large.png')"             onMouseOut="changeImage('leftPointsDownImage.0','down_large_draft.png')">
                    </td>
                    <td>
                    </td>
                    <td>
                      <img id="leftPointsDownImage.-1" src="../images/clock/down_large_draft.png" border="0" onClick="decreasePoints('0.5', 'left')"
                           onMouseOver="changeImage('leftPointsDownImage.-1','down_large.png')"             onMouseOut="changeImage('leftPointsDownImage.-1','down_large_draft.png')">
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
        </td>
        </tr>
        </table>
	</td>
</table>
</td>
</tr>

</table>
</div>


 <div id="rightInjuryClock" style="display:none; position:absolute; z-index:99; top:144px; left:146px; background: #000000;"   >
<table class="injury-border">
	<td>
		 <table  cellspacing="0" cellpadding="0">
		 <tr > 
			<img src="../images/clock/fusen_kiken.png" onClick="activateDeactivateFusenKiken('right')" id="fusen_kiken_right"><img
			src="../images/clock/injury_red.gif" border="0">
		 </tr>
		 <tr>
			<table  cellspacing="0" cellpadding="0">                  <!-- Rahmen außen rechte Uhr Holding -->
			  <tr>
				<td bgcolor="black">                                <!-- Rahmen außen rechte Uhr Holding -->
				  <table cellpadding="10" cellspacing="0">              <!-- Rahmen innen rechte Uhr Holding -->
					<tr>
					  <td id="rightInjuryClockBorder" bgcolor="white"> <!-- Rahmen innen rechte Uhr Holding -->
						<table cellpadding="0" cellspacing="0" class="clock"> <!-- Tabelle linke Uhr Injury -->
						  <!-- up arrows -->
						  <tr>
							<td class="clock">
							  <a href="#" onClick="increaseClock('600', 'rightInjuryClock')" onMouseOver="changeClockSettingsImage('rightInjuryClock.UpImage.3', 'up.png')"
								 onMouseOut="changeClockSettingsImage('rightInjuryClock.UpImage.3', 'up_draft.png')">
								<img id="rightInjuryClock.UpImage.3" src="../images/clock/up_draft.png" title="" border="0">
							  </a>
							</td>
							<td class="clock">
							  <a href="#" onClick="increaseClock('60', 'rightInjuryClock')" onMouseOver="changeClockSettingsImage('rightInjuryClock.UpImage.2', 'up.png')"
								 onMouseOut="changeClockSettingsImage('rightInjuryClock.UpImage.2', 'up_draft.png')">
								<img id="rightInjuryClock.UpImage.2" src="../images/clock/up_draft.png" border="0"> </a>
							</td>
							<td class="clock"> <!-- separator --> </td>
							<td class="clock">
							  <a href="#" onClick="increaseClock('10', 'rightInjuryClock')" onMouseOver="changeClockSettingsImage('rightInjuryClock.UpImage.1', 'up.png')"
								 onMouseOut="changeClockSettingsImage('rightInjuryClock.UpImage.1', 'up_draft.png')">
								<img id="rightInjuryClock.UpImage.1" src="../images/clock/up_draft.png" border="0"> </a>
							</td>
							<td class="clock">
							  <a href="#" onClick="increaseClock('1', 'rightInjuryClock')" onMouseOver="changeClockSettingsImage('rightInjuryClock.UpImage.0', 'up.png')"
								 onMouseOut="changeClockSettingsImage('rightInjuryClock.UpImage.0', 'up_draft.png')">
								<img id="rightInjuryClock.UpImage.0" src="../images/clock/up_draft.png" border="0"> </a>
							</td>
						  </tr>
						  <!-- clock -->
						  <tr>
							<td class="clock">
							  <img src="../images/clock/0.png" id="rightInjuryClock.minutes.1" onMouseDown="startStopClock(clockDisplayed ['right']);" border="0">
							</td>
							<td class="clock">
							  <img src="../images/clock/0.png" id="rightInjuryClock.minutes.2" onMouseDown="startStopClock(clockDisplayed ['right']);" border="0">
							</td>
							<td class="clock">
							  <img src="../images/clock/separator.png" onMouseDown="startStopClock(clockDisplayed ['right']);" border="0">
							</td>
							<td class="clock">
							  <img src="../images/clock/0.png" id="rightInjuryClock.seconds.1" onMouseDown="startStopClock(clockDisplayed ['right']);" border="0">
							</td>
							<td class="clock">
							  <img src="../images/clock/0.png" id="rightInjuryClock.seconds.2" onMouseDown="startStopClock(clockDisplayed ['right']);" border="0">
							</td>
						  </tr>
						  <!-- down arrows -->
						  <tr>
							<td class="clock">
							  <a href="#" onClick="decreaseClock('600', 'rightInjuryClock')" onMouseOver="changeClockSettingsImage('rightInjuryClock.DownImage.3', 'down.png')"
								 onMouseOut="changeClockSettingsImage('rightInjuryClock.DownImage.3', 'down_draft.png')">
								<img id="rightInjuryClock.DownImage.3" src="../images/clock/down_draft.png" border="0"> </a>
							</td>
							<td class="clock">
							  <a href="#" onClick="decreaseClock('60', 'rightInjuryClock')" onMouseOver="changeClockSettingsImage('rightInjuryClock.DownImage.2', 'down.png')"
								 onMouseOut="changeClockSettingsImage('rightInjuryClock.DownImage.2', 'down_draft.png')">
								<img id="rightInjuryClock.DownImage.2" src="../images/clock/down_draft.png" border="0"> </a>
							</td>
							<td class="clock"> <!-- separator --> </td>
							<td class="clock">
							  <a href="#" onClick="decreaseClock('10', 'rightInjuryClock')"onMouseOver="changeClockSettingsImage('rightInjuryClock.DownImage.1', 'down.png')"
								 onMouseOut="changeClockSettingsImage('rightInjuryClock.DownImage.1', 'down_draft.png')">
								<img id="rightInjuryClock.DownImage.1" src="../images/clock/down_draft.png" border="0"> </a>
							</td>
							<td class="clock">
							  <a href="#" onClick="decreaseClock('1', 'rightInjuryClock')" onMouseOver="changeClockSettingsImage('rightInjuryClock.DownImage.0', 'down.png')"
								 onMouseOut="changeClockSettingsImage('rightInjuryClock.DownImage.0', 'down_draft.png')">
								<img id="rightInjuryClock.DownImage.0" src="../images/clock/down_draft.png" border="0"> </a>
							</td>
						  </tr>
						</table>
					  </td>
					</tr>
				  </table>
				</td>
			  </tr>
			</table>
		</tr>
		</table>
	</td>
</table>
</div>

 <div id="leftInjuryClock" style="display:none; position:absolute; z-index:99; top:444px; left:146px; background-color: #000000;">
 <table class="injury-border">
	<td>
		 <table  cellspacing="0" cellpadding="0">
		 <tr >
			<img src="../images/clock/fusen_kiken.png" onClick="activateDeactivateFusenKiken('left')" id="fusen_kiken_left"><img src="../images/clock/injury_red.gif" border="0">
		 </tr>
		 <tr>
			<table cellpadding="0" cellspacing="0">                  <!-- Rahmen außen linke Uhr Injury -->
			  <tr>
				<td bgcolor="black">                                <!-- Rahmen außen linke Uhr Injury -->
				  <table cellpadding="10" cellspacing="0">              <!-- Rahmen innen linke Uhr Injury -->
					<tr>
					  <td id="leftInjuryClockBorder" bgcolor="white"> <!-- Rahmen innen linke Uhr Injury -->
						<table cellpadding="0" cellspacing="0" class="clock"> <!-- Tabelle linke Uhr Injury -->
						  <!-- up arrows -->
						  <tr>
							<td class="clock">
							  <a href="#" onClick="increaseClock('600', 'leftInjuryClock')" onMouseOver="changeClockSettingsImage('leftInjuryClock.UpImage.3', 'up.png')"
								 onMouseOut="changeClockSettingsImage('leftInjuryClock.UpImage.3', 'up_draft.png')">
								<img id="leftInjuryClock.UpImage.3" src="../images/clock/up_draft.png" title="" border="0"> </a>
							</td>
							<td class="clock">
							  <a href="#" onClick="increaseClock('60', 'leftInjuryClock')" onMouseOver="changeClockSettingsImage('leftInjuryClock.UpImage.2', 'up.png')"
								 onMouseOut="changeClockSettingsImage('leftInjuryClock.UpImage.2', 'up_draft.png')">
								<img id="leftInjuryClock.UpImage.2" src="../images/clock/up_draft.png" border="0"> </a>
							</td>
							<td class="clock"> <!-- separator -->                    </td>
							<td class="clock">
							  <a href="#" onClick="increaseClock('10', 'leftInjuryClock')"  onMouseOver="changeClockSettingsImage('leftInjuryClock.UpImage.1', 'up.png')"
								 onMouseOut="changeClockSettingsImage('leftInjuryClock.UpImage.1', 'up_draft.png')">
								<img id="leftInjuryClock.UpImage.1" src="../images/clock/up_draft.png" border="0"> </a>
							</td>
							<td class="clock">
							  <a href="#" onClick="increaseClock('1', 'leftInjuryClock')" onMouseOver="changeClockSettingsImage('leftInjuryClock.UpImage.0', 'up.png')"
								 onMouseOut="changeClockSettingsImage('leftInjuryClock.UpImage.0', 'up_draft.png')">
								<img id="leftInjuryClock.UpImage.0" src="../images/clock/up_draft.png" border="0"> </a>
							</td>
						  </tr>
						  <!-- clock -->
						  <tr>
							<td class="clock">
							  <img src="../images/clock/0.png" id="leftInjuryClock.minutes.1" border="0" onMouseDown="startStopClock(clockDisplayed ['left']);">
							</td>
							<td class="clock">
							  <img src="../images/clock/0.png" id="leftInjuryClock.minutes.2" border="0"  onMouseDown="startStopClock(clockDisplayed ['left']);">
							</td>
							<td class="clock">
							  <img src="../images/clock/separator.png" border="0"  onMouseDown="startStopClock(clockDisplayed ['left']);">
							</td>
							<td class="clock">
							  <img src="../images/clock/0.png" id="leftInjuryClock.seconds.1" border="0" onMouseDown="startStopClock(clockDisplayed ['left']);">
							</td>
							<td class="clock">
							  <img src="../images/clock/0.png" id="leftInjuryClock.seconds.2" border="0" onMouseDown="startStopClock(clockDisplayed ['left']);">
							</td>
						  </tr>
						  <!-- down arrows -->
						  <tr>
							<td class="clock">
							  <a href="#" onClick="decreaseClock('600', 'leftInjuryClock')"  onMouseOver="changeClockSettingsImage('leftInjuryClock.DownImage.3', 'down.png')"
								 onMouseOut="changeClockSettingsImage('leftInjuryClock.DownImage.3', 'down_draft.png')">
								<img id="leftInjuryClock.DownImage.3" src="../images/clock/down_draft.png" border="0"> </a>
							</td>
							<td class="clock">
							  <a href="#" onClick="decreaseClock('60', 'leftInjuryClock')"  onMouseOver="changeClockSettingsImage('leftInjuryClock.DownImage.2', 'down.png')"
								 onMouseOut="changeClockSettingsImage('leftInjuryClock.DownImage.2', 'down_draft.png')">
								<img id="leftInjuryClock.DownImage.2" src="../images/clock/down_draft.png" border="0"> </a>
							</td>
							<td class="clock"> <!-- separator -->       </td>
							<td class="clock">
							  <a href="#" onClick="decreaseClock('10', 'leftInjuryClock')"   onMouseOver="changeClockSettingsImage('leftInjuryClock.DownImage.1', 'down.png')"
								 onMouseOut="changeClockSettingsImage('leftInjuryClock.DownImage.1', 'down_draft.png')">
								<img id="leftInjuryClock.DownImage.1" src="../images/clock/down_draft.png" border="0"> </a>
							</td>
							<td class="clock">
							  <a href="#" onClick="decreaseClock('1', 'leftInjuryClock')"  onMouseOver="changeClockSettingsImage('leftInjuryClock.DownImage.0', 'down.png')"
								 onMouseOut="changeClockSettingsImage('leftInjuryClock.DownImage.0', 'down_draft.png')">
								<img id="leftInjuryClock.DownImage.0" src="../images/clock/down_draft.png" border="0"> </a>
							</td>
						  </tr>
						</table>
					  </td>
					</tr>
				  </table>
				</td>
			  </tr>
			</table>
		</tr>
	</table>
	</td>
</table>
</div>

<!--#########################################################################################################
Uhrentabelle -- wird nicht mehr angezeigt
######################################################################################################### -->

<div id="duoClocks"
     style="display:none; position:absolute; z-index:100; left:0px; top:580px; width:100%; height:auto; visibility:hidden; background-color:#FFFFFF; margin:0px;">
<table width="100%">
<tr>                                                              <!-- unterer Bereich mit den Uhren -->
<td>
<table width="100%" border="0" cellspacing="0" cellpadding="0"> <!-- Uhrentabelle -->
<tr>
<td bgcolor="black" width="300px" valign="top" algin="left">
 <!-- video screen -->
			<div style="display:none; visibility:hidden; background-color:#000000; padding:0px; margin:0px;">
				<video  id="video" width="300"></video>
				<video  id="videoScreen" width="0" heigth="0"></video>
			</div>
</td>
<!-- middleTable -->
<td width="353px" class="clock" align="center">
  <!-- mainClock -->
  <div id="div_mainClock" style="display:block; z-index:2;">
    <table cellpadding="4" cellspacing="0">
      <tr>
        <td bgcolor="black">
          <table cellpadding="10" cellspacing="0">
            <tr>
              <td id="mainClockBorder" bgcolor="white">
                <table cellpadding="0" cellspacing="0" class="clock">
                  <!-- up arrows -->
                  <tr>
                    <td><a href="#" onClick="increaseClock('600', 'mainClock')"  onMouseOver="changeClockSettingsImage('mainClock.UpImage.3', 'up_large.png')"
                           onMouseOut="changeClockSettingsImage('mainClock.UpImage.3', 'up_large_draft.png')">
                      <img id="mainClock.UpImage.3" src="../images/clock/up_large_draft.png" title="" border="0"></a>
                    </td>
                    <td><a href="#" onClick="increaseClock('60', 'mainClock')"  onMouseOver="changeClockSettingsImage('mainClock.UpImage.2', 'up_large.png')"
                           onMouseOut="changeClockSettingsImage('mainClock.UpImage.2', 'up_large_draft.png')">
                      <img id="mainClock.UpImage.2" src="../images/clock/up_large_draft.png" border="0"></a></td>
                    <td> <!-- separator -->                    </td>
                    <td><a href="#" onClick="increaseClock('10', 'mainClock')"  onMouseOver="changeClockSettingsImage('mainClock.UpImage.1', 'up_large.png')"
                           onMouseOut="changeClockSettingsImage('mainClock.UpImage.1', 'up_large_draft.png')">
                      <img id="mainClock.UpImage.1" src="../images/clock/up_large_draft.png" border="0"></a></td>
                    <td><a href="#" onClick="increaseClock('1', 'mainClock')"   onMouseOver="changeClockSettingsImage('mainClock.UpImage.0', 'up_large.png')"
                           onMouseOut="changeClockSettingsImage('mainClock.UpImage.0', 'up_large_draft.png')">
                      <img id="mainClock.UpImage.0" src="../images/clock/up_large_draft.png" border="0"> </a></td>
                  </tr>
                  <!-- clock -->
                  <tr>
                    <td class="clock"><img src="../images/clock/0_large.png" id="mainClock.minutes.1" border="0" onMouseDown="startStopClock('mainClock');"></td>
                    <td class="clock"><img src="../images/clock/0_large.png" id="mainClock.minutes.2" border="0" onMouseDown="startStopClock('mainClock');"></td>
                    <td class="clock"><img src="../images/clock/separator_large.png" border="0"                  onMouseDown="startStopClock('mainClock');"></td>
                    <td class="clock"><img src="../images/clock/0_large.png" id="mainClock.seconds.1" border="0" onMouseDown="startStopClock('mainClock');"></td>
                    <td class="clock"><img src="../images/clock/0_large.png" id="mainClock.seconds.2" border="0" onMouseDown="startStopClock('mainClock');"></td>
                  </tr>
                  <!-- down arrows -->
                  <tr>
                    <td class="clock"><a href="#" onClick="decreaseClock('600', 'mainClock')" onMouseOver="changeClockSettingsImage('mainClock.DownImage.3', 'down_large.png')"
                                         onMouseOut="changeClockSettingsImage('mainClock.DownImage.3', 'down_large_draft.png')">
                      <img id="mainClock.DownImage.3" src="../images/clock/down_large_draft.png" border="0"></a></td>
                    <td class="clock">
									<a href="#" onClick="decreaseClock('60', 'mainClock')"  onMouseOver="changeClockSettingsImage('mainClock.DownImage.2', 'down_large.png')"
                                         onMouseOut="changeClockSettingsImage('mainClock.DownImage.2', 'down_large_draft.png')">
                      <img id="mainClock.DownImage.2" src="../images/clock/down_large_draft.png" border="0"></a></td>
                    <td class="clock"> <!-- separator -->             </td>
                    <td class="clock"><a href="#" onClick="decreaseClock('10', 'mainClock')"  onMouseOver="changeClockSettingsImage('mainClock.DownImage.1', 'down_large.png')"
                                         onMouseOut="changeClockSettingsImage('mainClock.DownImage.1', 'down_large_draft.png')">
                      <img id="mainClock.DownImage.1" src="../images/clock/down_large_draft.png" border="0"></a></td>
                    <td class="clock"><a href="#" onClick="decreaseClock('1', 'mainClock')"   onMouseOver="changeClockSettingsImage('mainClock.DownImage.0', 'down_large.png')"
                                         onMouseOut="changeClockSettingsImage('mainClock.DownImage.0', 'down_large_draft.png')">
                      <img id="mainClock.DownImage.0" src="../images/clock/down_large_draft.png" border="0"></a></td>
                  </tr>
                </table>
              </td>
            </tr>
          </table>
        </td>
      </tr>
    </table>
  </div>
  <!-- Ende mainClock -->
</td>

<td bgcolor="black" width="300px" align="right" valign="top">

</td>

</tr>
</table>

</td>
</tr>
</table>
</div>

 <!-- KikenWinner -->
  <div id="div_KikenWinner" style="display:none; background-color:#FFFFFF; z-index:100; padding:4px; left:220; top:300px; position: absolute;">
    <table width="300" height="220"border="8" bordercolor="#FFFE00" cellpadding="4" cellspacing="0">
      <tr>
        <td colspan="3" align="center" style="font-size:20px; font-weight:bold; color:#FFFFFF; background-color:#000000;">
          KIKENGACHI
        </td>
      </tr>
      <tr>
        <td colspan="3" align="center" style="font-size:12px;">
          please nominate the winner
        </td>
      </tr>
      <tr height="30px">
        <td align="right" width="33%" style="font-size:16px; font-weight:bold; color:#000000; background-color:#000000;">
          <div id="up_blue" style="display:block; height:20px; background-color:#000000;"     onClick="setKikenWinner('left');">&nbsp;</div>
        </td>
        <td align="right" width="34%" style="font-size:16px; font-weight:bold; color:#000000; background-color:#000000;">
          <div id="up_none" style="display:block; height:20px; background-color:#5fff39;"     onClick="setKikenWinner('none')">&nbsp;</div>
        </td>
        <td align="right" width="33%" style="font-size:16px; font-weight:bold; color:#000000; background-color:#000000;">
          <div id="up_red" style="display:block; height:20px; background-color:#000000;"      onClick="setKikenWinner('right')">&nbsp;</div>
        </td>
      </tr>
      <tr height="56px">
        <td align="center" class="blue" width="33%" style="font-size:16px; font-weight:bold; color:#FFFFFF;"
            onClick="setKikenWinner('left')">
          BLUE
        </td>
        <td align="center" width="34%"
            style="font-size:16px; font-weight:bold; color:#FFFFFF; background-color:#888888;"
            onClick="setKikenWinner('none')">
          NONE
        </td>
        <td align="center" class="red" width="33%" style="font-size:16px; font-weight:bold; color:#FFFFFF;"
            onClick="setKikenWinner('right')">
          RED
        </td>
      </tr>
      <tr height="30px">
        <td align="left" width="33%" style="font-size:16px; font-weight:bold; color:#000000; background-color:#000000;">
          <div id="down_blue" style="display:block; height:20px; background-color:#000000;" onClick="setKikenWinner('left')">&nbsp;</div>
        </td>
        <td align="left" width="34%" style="font-size:16px; font-weight:bold; color:#000000; background-color:#000000;">
          <div id="down_none" style="display:block; height:20px; background-color:#5fff39;"  onClick="setKikenWinner('none')">&nbsp;</div>
        </td>
        <td align="left" width="33%" style="font-size:16px; font-weight:bold; color:#000000; background-color:#000000;">
          <div id="down_red" style="display:block; height:20px; background-color:#000000;"   onClick="setKikenWinner('right')">&nbsp;</div>
        </td>
      </tr>
    </table>
  </div>
  <!-- Ende KikenWinner -->


<div style="display:none; visibility:hidden;">
  <img src="../images/clock/1.png">
  <img src="../images/clock/2.png">
  <img src="../images/clock/3.png">
  <img src="../images/clock/4.png">
  <img src="../images/clock/5.png">
  <img src="../images/clock/6.png">
  <img src="../images/clock/7.png">
  <img src="../images/clock/8.png">
  <img src="../images/clock/9.png">
  <img src="../images/clock/1_small.png">
  <img src="../images/clock/1_large.png">
  <img src="../images/clock/2_small.png">
  <img src="../images/clock/2_large.png">
  <img src="../images/clock/3_small.png">
  <img src="../images/clock/3_large.png">
  <img src="../images/clock/4_small.png">
  <img src="../images/clock/4_large.png">
  <img src="../images/clock/5_small.png">
  <img src="../images/clock/5_large.png">
  <img src="../images/clock/6_small.png">
  <img src="../images/clock/6_large.png">
  <img src="../images/clock/7_small.png">
  <img src="../images/clock/7_large.png">
  <img src="../images/clock/8_small.png">
  <img src="../images/clock/8_large.png">
  <img src="../images/clock/9_small.png">
  <img src="../images/clock/9_large.png">
  <img src="../images/clock/1_x_large.png">
  <img src="../images/clock/2_x_large.png">
  <img src="../images/clock/3_x_large.png">
  <img src="../images/clock/4_x_large.png">
  <img src="../images/clock/5_x_large.png">
  <img src="../images/clock/6_x_large.png">
  <img src="../images/clock/7_x_large.png">
  <img src="../images/clock/8_x_large.png">
  <img src="../images/clock/9_x_large.png">
    <img src="../sound/klingel02.swf">
  <img src="../sound/alarm01.swf">
  <img src="../sound/alarm02.swf">
</div>
</body>

</html>
</h:form>
</f:view>