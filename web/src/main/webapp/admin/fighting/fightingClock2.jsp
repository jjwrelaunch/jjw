<%@page import="java.util.List"%>
<%@ include file="/common/taglibs.jsp" %>

<f:view> 

<h:form id="adminFightingClockAction">
<f:loadBundle basename="de.jjw.webapp.messages.admin.fight" var="msg"/>

<html>
<head>
 <title>
    <h:outputText value="#{adminFightingClockAction.fight.fightingclass.age.description}"/>
    &nbsp;
    <h:outputText value="#{adminFightingClockAction.fight.fightingclass.sexWeb}"/>
    &nbsp;
    <h:outputText value="#{adminFightingClockAction.fight.fightingclass.weightclass}"/></title>
<LINK REL="STYLESHEET" TYPE="text/css" HREF="../style_fighting_clock2.css" TITLE="classic"/>
 <script type="text/javascript" src="../scripts/jquery-1.4.2.min.js"></script>
 <script type="text/javascript" src="../scripts/RecordRTC.js"/></script>
 <script type="text/javascript" src="../scripts/video.js"/></script>
 <script type="text/javascript" src="../scripts/jjw_generals.js"/></script>
 <script type="text/javascript" src="../javascript_fighting2.js"/></script>
    <script>
	  $(window).bind('beforeunload', function(){
		  sendVideo();
		  return;
}	  );
	
	  var videoOn=<h:outputText value="#{adminFightingClockAction.videoWeb}"/>;
      var paraAgeDescription='<h:outputText value="#{adminFightingClockAction.fight.fightingclass.age.description}"/>';
      var videoElement = document.getElementById('video');
	  var videoElementScreen = document.getElementById('videoScreen');
	  var videoFightId='<h:outputText value="#{adminFightingClockAction.fight.id}"/>';
	  var videoDescription='<h:outputText value="#{adminFightingClockAction.fight.fightingclass.age.description}"/> <h:outputText value="#{adminFightingClockAction.fight.fightingclass.weightclass}"/> <h:outputText value="#{adminFightingClockAction.fight.fightingclass.sexWeb}"/>';
</script>
</head>
<body oncontextmenu="return false" onLoad='startfight(
		paraAgeDescription,
		<h:outputText value="#{adminFightingClockAction.fight.fightingclass.age.fightingTime}"/>,
		 <h:outputText value="#{adminFightingClockAction.fight.fightingclass.age.overtime}"/>,
		15, <h:outputText value="#{adminFightingClockAction.fight.fightingclass.age.injurytime}"/>);'
		onunload="shutClock();">
		

<div id="time_frame" align="center"
     style="display:none; visibility:hidden; position:absolute; z-index:99; top:114px; left:433px; 
	 height: auto; width:152px; background-color:#000000; border:2px solid black; color:#00FF00; font-size: 96px; font-weight:bold; font-family:Arial;" onClick="javascript:timeframeclose()">
  T<br/> I<br/>M<br/>E
</div>		
		

<!--
####################################################################################################################
Soundframe
####################################################################################################################
-->
<iframe marginwidth="0" marginheight="0" name="sound_frame" id="sound_frame" frameborder="0" src="./leer" width="0" height="0"></iframe>
        

<div id="loadfield" style="width:100%; height:100%; display:block; background-color:#ffffff; padding:0px; margin:0px;"
     align="center">

  <div style="color:#FFFFFF; background-color:#666666;">
    <!-- Altersklasse - Geschlecht - Gewichtsklasse -->
    <table width="100%" cellpadding="0" cellspacing="0" border="0">
      <tr>
        <td width="33%" align="left">
          <div id="loadfield_age_1" class="loadfield_age_text">&nbsp;</div>
        </td>
        <td width="34%" align="center">
          <div id="loadfield_sex_1" class="loadfield_sex_text">&nbsp;</div>
        </td>
        <td width="33%" align="right">
          <div id="loadfield_weight_1" class="loadfield_weight_text">&nbsp;</div>
        </td>
      </tr>
    </table>
    <!-- Angaben in die vorangegangenen Spalten schreiben -->
    <script
        language="javascript">showAK( '<h:outputText value="#{adminFightingClockAction.fight.fightingclass.age.description}" />', '<h:outputText value="#{adminFightingClockAction.fight.fightingclass.sexWeb}" />', '<h:outputText value="#{adminFightingClockAction.fight.fightingclass.weightclass}" />' );</script>
  </div>

  <div id="call_red_1" align="center"
       style="height:170px; width:100%; background-image: url('<h:outputText value="#{adminFightingClockAction.imageCommandRedWithContext}"/>'); ;margin-right:20px;">
    <h:outputText value="#{adminFightingClockAction.fight.fighterRed.firstname}"/>
    <br/>
    <span  id="call_red_1_name" style="font-size:100px;"><h:outputText value="#{adminFightingClockAction.fight.fighterRed.name}"/></span>
  </div>

  <hr/>

  <div id="call_blue_1" align="center"
       style="height:170px; width:100%; background-image: url('<h:outputText value="#{adminFightingClockAction.imageCommandBlueWithContext}"/>'); ;margin-right:20px;">
    <h:outputText value="#{adminFightingClockAction.fight.fighterBlue.firstname}"/>
    <br/>
    <span  id="call_blue_1_name" style="font-size:100px;"><h:outputText value="#{adminFightingClockAction.fight.fighterBlue.name}"/></span>
  </div>

  <hr/>

  <div style="display:block; overflow:hidden; width:100%; height:131px;">
   <t:dataTable value="#{adminFightingClockAction.previewForTatami}" var="cty" rows="1"
                   border="0" cellspacing="0" cellpadding="0" binding="#{adminFightingClockAction.dataTable}"  >
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
        <td width="35%" align="left" valign="top" style="font-family:Courier; font-size:12px; color:#000000;">
          <a href="#" onclick="window.close()" style="background-color:#AAAAAA;">schliessen</a>
        </td>
        <td width="30%" align="center" valign="top" style="font-family:Courier; font-size:12px; color:#000000;"
            onClick="handleAnzeige('2');">
         
          <span style="background-color:#AAAAAA;">&nbsp;vorbereiten&nbsp;</span>
         
        </td>
        <td width="35%" align="right" valign="top" style="font-family:Courier; font-size:12px; color:#000000;">
          <a href="#" onclick="handleAnzeige('0')" style="background-color:#AAAAAA;">k&auml;mpfen</a>
        </td>
      </tr>
    </table>
  </div>
</div>

<!-- preparation screen -->
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
    <t:dataTable value="#{adminFightingClockAction.previewForTatami}" var="cty2" rows="3"
                   border="0" cellspacing="0" cellpadding="0" binding="#{adminFightingClockAction.dataTable2}"  >
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


<!-- log screen-->
<div id="log" style="height: 210px;width: 260px; border-color: black; border-style: solid;overflow-y : auto; font-size:14px; position: absolute;top: 50%;
left: 50%;
margin-top: -105px;
margin-left: -130px;
display:none; visibility:hidden;
z-index: 100;
background-color: white;">
&nbsp;
</div>





<!-- clock screen-->
<div id="mainbody" style="display:none; visibility:hidden; padding:0px; margin:0px;">
<table id="globalTab" width="100%" height="700" border="0" cellspacing="0" cellpadding="0">

<tr height="285" bgcolor="red" id="redTr">
<td>

<table bgcolor="red" width="100%">
     <td valign="top" >
        <table width="100%" >
        <tr>
          <td id="redNameTd" height="80" style="padding-left:10px;" valign="top"  colspan="5" >
            <span id="redSpanName" class="VornameWhite">
              <h:outputText value="#{adminFightingClockAction.fight.fighterRed.firstname}"/>&nbsp;<h:outputText  value="#{adminFightingClockAction.fight.fighterRed.name}"/>   </span>
            <br clear="all"/>
             <span  id="redSpanTeam" class="Verein">
              <h:outputText value="#{adminFightingClockAction.fight.fighterRed.team.teamName}"/> </span>
          </td>
        </tr>
        <tr>
          <td width="120" valign="top">
          <table cellpadding="0" cellspacing="0">
          <tr>
            <td >
              <a href="#" onClick="resetClock('right')"><t:graphicImage value="#{adminFightingClockAction.imageCommandRed}" id="rightFlag" height="100" width="100" style="border:3px solid white;" alt=""/></a>
            </td>
          </tr>
           <tr>
            <td valign="top" height="50" id="redKikTd">
               <a href="#" onClick="toggleClock('right')"><img src="../images/clock/fusen_kiken.png" id="rightKiken"></a>			   
            </td>
          </tr>
          </table>
          </td>

          <td width="10px">&nbsp;</td>

          <td width="100px" valign="top">
		                                     <!-- Rahmen außen rechte Uhr Holding -->
        <table cellpadding="4" cellspacing="0">                 <!-- Rahmen innen rechte Uhr Holding -->
          <tr>
            <td id="rightHoldingClockBorder" bgcolor="white">   <!-- Rahmen innen rechte Uhr Holding -->
              <table cellpadding="0" cellspacing="0" class="clock"> <!-- Tabelle rechte Uhr Holding -->
                <!-- up arrows -->
                <tr>
                  <td class="clock">
                    <a href="#" onClick="increaseClock('10', 'rightHoldingClock')"
                       onMouseOver="changeClockSettingsImage('rightHoldingClock.UpImage.1', 'up.png')"
                       onMouseOut="changeClockSettingsImage('rightHoldingClock.UpImage.1', 'up_draft.png')">

                      <img id="rightHoldingClock.UpImage.1" src="../images/clock/up_draft.png" border="0"> </a>
                  </td>

                  <td class="clock">
                    <a href="#" onClick="increaseClock('1', 'rightHoldingClock')"
                       onMouseOver="changeClockSettingsImage('rightHoldingClock.UpImage.0', 'up.png')"
                       onMouseOut="changeClockSettingsImage('rightHoldingClock.UpImage.0', 'up_draft.png')">

                      <img id="rightHoldingClock.UpImage.0" src="../images/clock/up_draft.png" border="0"> </a>
                  </td>
                </tr>
                <!-- clock -->
                <tr>
                  <td class="clock">
                    <img src="../images/clock/0.png" onMouseDown="startStopClock('rightHoldingClock')"
                         id="rightHoldingClock.seconds.1" border="0">
                  </td>

                  <td class="clock">
                    <img src="../images/clock/0.png" onMouseDown="startStopClock('rightHoldingClock')"
                         id="rightHoldingClock.seconds.2" border="0">
                  </td>
                </tr>
                <!-- down arrows -->
                <tr>
                  <td class="clock">
                    <a href="#" onClick="decreaseClock('10', 'rightHoldingClock')"
                       onMouseOver="changeClockSettingsImage('rightHoldingClock.DownImage.1', 'down.png')"
                       onMouseOut="changeClockSettingsImage('rightHoldingClock.DownImage.1', 'down_draft.png')">

                      <img id="rightHoldingClock.DownImage.1" src="../images/clock/down_draft.png" border="0"> </a>
                  </td>

                  <td class="clock">
                    <a href="#" onClick="decreaseClock('1', 'rightHoldingClock')"
                       onMouseOver="changeClockSettingsImage('rightHoldingClock.DownImage.0', 'down.png')"
                       onMouseOut="changeClockSettingsImage('rightHoldingClock.DownImage.0', 'down_draft.png')">

                      <img id="rightHoldingClock.DownImage.0" src="../images/clock/down_draft.png" border="0"> </a>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
		  <tr>
			<td>
				<span class="osaekomi">Osaekomi</span>
			</td>		  
		  </tr>
        </table>
          
          </td>

          <td>&nbsp;</td>

          <td align="right" valign="top">
			  <table cellpadding="0" cellspacing="0">
			  <td>
				<table cellpadding="0" cellspacing="0" border="0" width="200px" id="rightPenaltyTable">
				  <tr>
					<td width="100px" align="left"  onMouseDown="handleShido(event,'right');" ><span class="displayLabels">Shido</span>
					</td>
					<td width="65px" id="rightPenaltyTDFlag" align="center" onMouseDown="handleShido(event,'right');" style="border:3px solid red; background-color:#000000;">
					  <img src="../images/clock/bl_ippon.png" id="rightShido2ImageFlag" border="0" >
					  <img src="../images/clock/0_ippon.png" id="rightShidoImageFlag" border="0">
					</td>
				  </tr>
				  <tr>
					<td align="left" onMouseDown="handleChui(event,'right');"  ><span class="displayLabels">Chui</span>
					</td>
					<td width="65px" align="center"  onMouseDown="handleChui(event,'right');" style="border:3px solid red; background-color:#000000;">
					  <img src="../images/clock/bl_ippon.png" id="rightChui2ImageFlag" border="0" >
					  <img src="../images/clock/0_ippon.png" id="rightChuiImageFlag" border="0">
					</td>
				  </tr>
				  <tr>
					<td align="left" onMouseDown="handleHansokumake(event,'right');" > <span class="displayLabels">Hans.</span>
					</td>
					<td width="65px" align="center"  onMouseDown="handleHansokumake(event,'right');" style="border:3px solid red; background-color:#000000;">
					  <img src="../images/clock/bl_ippon.png" id="rightHansokumake2ImageFlag" border="0" >
					  <img src="../images/clock/0_ippon.png" id="rightHansokumakeImageFlag" border="0">
					</td>
				  </tr>
				</table>
			  </td>
			  <td >
				  <table cellpadding="0" cellspacing="0" width="210px" align="right" id="rightIppons">
				  <tr>
					<td width="100px" align="center"  onMouseDown="handleIppon(event,'right','1');"><span class="displayLabels">Part&nbsp;1</span>
					</td>
					<td width="65px" id="rightIppon1TDFlag" align="center" style="border:3px solid red; background-color:#000000;"
						onMouseDown="handleIppon(event,'right','1');" >
					  <img src="../images/clock/bl_ippon.png" id="rightIppon11ImageFlag" border="0" >
					  <img src="../images/clock/0_ippon.png" id="rightIppon1ImageFlag" border="0" >
					</td>
				  </tr>
				  <tr>
					<td align="center" onMouseDown="handleIppon(event,'right','2');"><span class="displayLabels">Part&nbsp;2</span>
					</td>
					<td width="60px" id="rightIppon2TDFlag" align="center" style="border:3px solid red; background-color:#000000;"
						onMouseDown="handleIppon(event,'right','2');">
					  <img src="../images/clock/bl_ippon.png" id="rightIppon22ImageFlag" border="0">
					  <img src="../images/clock/0_ippon.png" id="rightIppon2ImageFlag" border="0">
					</td>
				  </tr>
				  <tr>
					<td align="center" onMouseDown="handleIppon(event,'right','3');"><span class="displayLabels">Part&nbsp;3</span>
					</td>
					<td width="60px" id="rightIppon3TDFlag" align="center" style="border:3px solid red; background-color:#000000;"
						 onMouseDown="handleIppon(event,'right','3');">
					  <img src="../images/clock/bl_ippon.png" id="rightIppon33ImageFlag" border="0" >
					  <img src="../images/clock/0_ippon.png" id="rightIppon3ImageFlag" border="0">
					</td>
				  </tr>
				  </table>
			  </td>		
			  <td width = "1px" id="rightSpaceAfterIppon">&nbsp;</td>	  
			</table>
          </td>


        </tr>
        </table>

     </td>
     <td align="right" width="200" id="rightPointsSpace">
        <table cellpadding="0" cellspacing="0" id="rightPointsBorderTable" class="points-inner-border">
        <tr>
          <td>                         <!-- Punktetabelle Rahmen innen-->
            <table cellspacing="0" cellpadding="0" class="points">        <!-- Punktetabelle -->
            <tr>
              <td align="right"><a href="#" onClick="increasePoints('10', 'right')"
                  onMouseOver="changeImage('rightPointsUpImage.1','up_large.png')"
                  onMouseOut="changeImage('rightPointsUpImage.1','up_large_draft.png')">
                  <img id="rightPointsUpImage.1" src="../images/clock/up_large_draft.png" title="" border="0"></a>
              </td>
              <td align="right"><a href="#" onClick="increasePoints('1', 'right')"
                  onMouseOver="changeImage('rightPointsUpImage.0','up_large.png')"
                  onMouseOut="changeImage('rightPointsUpImage.0','up_large_draft.png')">
                  <img id="rightPointsUpImage.0" src="../images/clock/up_large_draft.png" border="0"> </a>
              </td>
            </tr>
            <tr>
              <td>
                <img src="../images/clock/bl_x_large.png" id="rightPoints.1" onMouseDown="handlePoints(event,'1','right')" border="0" height="200" >
              </td>
              <td>
                <img src="../images/clock/0_x_large.png" id="rightPoints.0" onMouseDown="handlePoints(event,'1','right')" border="0"  height="200">
              </td>
            </tr>
            <tr>
              <td align="right"><a href="#" onClick="decreasePoints('10', 'right')"
                  onMouseOver="changeImage('rightPointsDownImage.1','down_large.png')"
                  onMouseOut="changeImage('rightPointsDownImage.1','down_large_draft.png')">
                  <img id="rightPointsDownImage.1" src="../images/clock/down_large_draft.png" border="0"></a>
              </td>
              <td align="right"><a href="#" onClick="decreasePoints('1', 'right')"
                  onMouseOver="changeImage('rightPointsDownImage.0','down_large.png')"
                  onMouseOut="changeImage('rightPointsDownImage.0','down_large_draft.png')">
                  <img id="rightPointsDownImage.0" src="../images/clock/down_large_draft.png" border="0"></a>
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


<tr height="285" bgcolor="blue" id="blueTr">
<td>
<!-- blue -->
<table bgcolor="blue" width="100%" >
     <td valign="top" >
        <table width="100%">
        <tr>
          <td id="blueNameTd" height="80" style="padding-left:10px;" valign="top"  colspan="5" >
            <span id="blueSpanName" class="VornameWhite">
              <h:outputText value="#{adminFightingClockAction.fight.fighterBlue.firstname}"/>&nbsp;<h:outputText
            value="#{adminFightingClockAction.fight.fighterBlue.name}"/>   </span>
            <br clear="all"/>
             <span id="blueSpanTeam" class="Verein">
              <h:outputText value="#{adminFightingClockAction.fight.fighterBlue.team.teamName}"/> </span>
          </td>
        </tr>
        <tr>
          <td width="120" valign="top">
          <table cellpadding="0" cellspacing="0">
          <tr>
            <td>
              <a href="#" onClick="resetClock('left')"><t:graphicImage value="#{adminFightingClockAction.imageCommandBlue}" id="leftFlag" height="100" width="100" style="border:3px solid white;" alt=""/></a>
            </td>
          </tr>
           <tr>
            <td valign="top" height="50" id="blueKikTd">
				<a href="#" onClick="toggleClock('left')"><img src="../images/clock/fusen_kiken.png" id="leftKiken"></a>               
            </td>
          </tr>
          </table>
          </td>

          <td width="10px">&nbsp;</td>

          <td  width="100px" valign="top">
			
                                   <!-- Rahmen außen rechte Uhr Holding -->
			<table cellpadding="4" cellspacing="0">                 <!-- Rahmen innen rechte Uhr Holding -->
			  <tr>
				<td id="leftHoldingClockBorder" bgcolor="white">   <!-- Rahmen innen rechte Uhr Holding -->
				  <table cellpadding="0" cellspacing="0" class="clock"> <!-- Tabelle rechte Uhr Holding -->
					<!-- up arrows -->
					<tr>
					  <td class="clock">
						<a href="#" onClick="increaseClock('10', 'leftHoldingClock')"
						   onMouseOver="changeClockSettingsImage('leftHoldingClock.UpImage.1', 'up.png')"
						   onMouseOut="changeClockSettingsImage('leftHoldingClock.UpImage.1', 'up_draft.png')">

						  <img id="leftHoldingClock.UpImage.1" src="../images/clock/up_draft.png" border="0"> </a>
					  </td>

					  <td class="clock">
						<a href="#" onClick="increaseClock('1', 'leftHoldingClock')"
						   onMouseOver="changeClockSettingsImage('leftHoldingClock.UpImage.0', 'up.png')"
						   onMouseOut="changeClockSettingsImage('leftHoldingClock.UpImage.0', 'up_draft.png')">

						  <img id="leftHoldingClock.UpImage.0" src="../images/clock/up_draft.png" border="0"> </a>
					  </td>
					</tr>
					<!-- clock -->
					<tr>
					  <td class="clock">
						<img src="../images/clock/0.png" onMouseDown="startStopClock('leftHoldingClock')"
							 id="leftHoldingClock.seconds.1" border="0">
					  </td>

					  <td class="clock">
						<img src="../images/clock/0.png" onMouseDown="startStopClock('leftHoldingClock')"
							 id="leftHoldingClock.seconds.2" border="0">
					  </td>
					</tr>
					<!-- down arrows -->
					<tr>
					  <td class="clock">
						<a href="#" onClick="decreaseClock('10', 'leftHoldingClock')"
						   onMouseOver="changeClockSettingsImage('leftHoldingClock.DownImage.1', 'down.png')"
						   onMouseOut="changeClockSettingsImage('leftHoldingClock.DownImage.1', 'down_draft.png')">

						  <img id="leftHoldingClock.DownImage.1" src="../images/clock/down_draft.png" border="0"> </a>
					  </td>

					  <td class="clock">
						<a href="#" onClick="decreaseClock('1', 'leftHoldingClock')"
						   onMouseOver="changeClockSettingsImage('leftHoldingClock.DownImage.0', 'down.png')"
						   onMouseOut="changeClockSettingsImage('leftHoldingClock.DownImage.0', 'down_draft.png')">

						  <img id="leftHoldingClock.DownImage.0" src="../images/clock/down_draft.png" border="0"> </a>
					  </td>
					</tr>
				  </table>
				</td>
			  </tr>
			  <tr>
				<td>
					<span class="osaekomi">Osaekomi</span>
				</td>		  
			</tr>
			</table>
          </td>

          <td>&nbsp;</td>

         <td align="right" >		
			 <table  cellpadding="0" cellspacing="0">
			 <td >
				  <table cellpadding="0" cellspacing="0" border="0" width="200px" id="leftPenaltyTable">
				  <tr>
					<td width="100px" align="left"  onMouseDown="handleShido(event,'left');"> <span class="displayLabels">Shido</span>
					</td>
					<td width="65px" id="leftPenaltyTDFlag" align="center" onMouseDown="handleShido(event,'left');" style="border:3px solid blue; background-color:#000000;">
					  <img src="../images/clock/bl_ippon.png" id="leftShido2ImageFlag" border="0" >
					  <img src="../images/clock/0_ippon.png" id="leftShidoImageFlag" border="0">
					</td>
				  </tr>
				  <tr>
					<td align="left" onMouseDown="handleChui(event,'left');" ><span class="displayLabels">Chui</span>
					</td>
					<td width="60px" align="center"  onMouseDown="handleChui(event,'left');" style="border:3px solid blue; background-color:#000000;">
					  <img src="../images/clock/bl_ippon.png" id="leftChui2ImageFlag" border="0">
					  <img src="../images/clock/0_ippon.png" id="leftChuiImageFlag" border="0">
					</td>
				  </tr>
				  <tr>
					<td align="left" onMouseDown="handleHansokumake(event,'left');">
					  <span class="displayLabels">Hans.</span>
					</td>
					<td width="60px" align="center" onMouseDown="handleHansokumake(event,'left');" style="border:3px solid blue; background-color:#000000;">
					  <img src="../images/clock/bl_ippon.png" id="leftHansokumake2ImageFlag" border="0">
					  <img src="../images/clock/0_ippon.png" id="leftHansokumakeImageFlag" border="0">
					</td>
				  </tr>
				  </table>
			  </td>			 
			 
			 <td >
				 <table  cellpadding="0" cellspacing="0" align="right" width="210" id="leftIppons">
				  <tr>
					<td width="100px" align="center"  onMouseDown="handleIppon(event,'left','1');"><span class="displayLabels">Part&nbsp;1</span>
					</td>
					<td width="60px" id="leftIppon1TDFlag" align="center" style="border:3px solid blue; background-color:#000000;"
						onMouseDown="handleIppon(event,'left','1');">
					  <img src="../images/clock/bl_ippon.png" id="leftIppon11ImageFlag" border="0"  >
					  <img src="../images/clock/0_ippon.png" id="leftIppon1ImageFlag" border="0">
					</td>
				  </tr>
				  <tr>
					<td align="center" onMouseDown="handleIppon(event,'left','2');"><span class="displayLabels">Part&nbsp;2</span>
					</td>
					<td width="60px" id="leftIppon2TDFlag" align="center" style="border:3px solid blue; background-color:#000000;"
						onMouseDown="handleIppon(event,'left','2');">
					  <img src="../images/clock/bl_ippon.png" id="leftIppon22ImageFlag" border="0" >
					  <img src="../images/clock/0_ippon.png" id="leftIppon2ImageFlag" border="0">
					</td>
				  </tr>
				  <tr>
					<td align="center" onMouseDown="handleIppon(event,'left','3');"><span class="displayLabels">Part&nbsp;3</span>
					</td>
					<td width="60px" id="leftIppon3TDFlag" align="center" style="border:3px solid blue; background-color:#000000;"
						 onMouseDown="handleIppon(event,'left','3');">
					  <img src="../images/clock/bl_ippon.png" id="leftIppon33ImageFlag" border="0">
					  <img src="../images/clock/0_ippon.png" id="leftIppon3ImageFlag" border="0">
					</td>
				  </tr>
				  </table>
			  </td>
			  <td width = "1px" id="leftSpaceAfterIppon">&nbsp;</td>
		   </table>
		  </td>
        </tr>        
		</table>

     </td>
     <td align="right" width="200" id="leftPointsSpace">
        <table cellpadding="0" cellspacing="0" id="leftPointsBorderTable" class="points-inner-border">
        <tr>
          <td>                         <!-- Punktetabelle Rahmen innen-->
            <table cellspacing="0" cellpadding="0" class="points">        <!-- Punktetabelle -->
            <tr>
              <td align="right"><a href="#" onClick="increasePoints('10', 'left')"
                  onMouseOver="changeImage('leftPointsUpImage.1','up_large.png')"
                  onMouseOut="changeImage('leftPointsUpImage.1','up_large_draft.png')">
                  <img id="leftPointsUpImage.1" src="../images/clock/up_large_draft.png" title="" border="0"></a>
              </td>
              <td align="right"><a href="#" onClick="increasePoints('1', 'left')"
                  onMouseOver="changeImage('leftPointsUpImage.0','up_large.png')"
                  onMouseOut="changeImage('leftPointsUpImage.0','up_large_draft.png')">
                  <img id="leftPointsUpImage.0" src="../images/clock/up_large_draft.png" border="0"> </a>
              </td>
            </tr>
            <tr>
              <td>
                <img src="../images/clock/bl_x_large.png" id="leftPoints.1" onMouseDown="handlePoints(event,'1','left')" border="0"  height="200">
              </td>
              <td>
                <img src="../images/clock/0_x_large.png" id="leftPoints.0" onMouseDown="handlePoints(event,'1','left')" border="0"  height="200">
              </td>
            </tr>
            <tr>
              <td align="right"><a href="#" onClick="decreasePoints('10', 'left')"
                  onMouseOver="changeImage('leftPointsDownImage.1','down_large.png')"
                  onMouseOut="changeImage('leftPointsDownImage.1','down_large_draft.png')">
                  <img id="leftPointsDownImage.1" src="../images/clock/down_large_draft.png" border="0"></a>
              </td>
              <td align="right"><a href="#" onClick="decreasePoints('1', 'left')"
                  onMouseOver="changeImage('leftPointsDownImage.0','down_large.png')"
                  onMouseOut="changeImage('leftPointsDownImage.0','down_large_draft.png')">
                  <img id="leftPointsDownImage.0" src="../images/clock/down_large_draft.png" border="0"></a>
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


<tr  height="180" bgcolor="black" id="clockTr">
<td height="100%">

<table id="clockPart" width="100%" border="0" cellspacing="0" cellpadding="0"> <!-- Uhrentabelle -->
<tr>
<td width="243px" valign="bottom" bgcolor="black">


<div id="leftHoldingClock" bgcolor="black" width="300px">
  <table cellpadding="4" cellspacing="0" >                     <!-- Rahmen außen linke Uhr Holding -->
    <tr>
      <td>                                   <!-- Rahmen außen linke Uhr Holding -->
         <!-- video screen -->
			<div style="display:block; visibility:visible; background-color:#000000; padding:0px; margin:0px;">
				<video  id="video" width="300"></video>
				<video  id="videoScreen" width="0" heigth="0"></video>
			</div>
      </td>
    </tr>
  </table>
</div>
</td>

<!-- middleFeld -->
<td width="353px" class="clock" align="center">
  <!-- feste Breite um verzerrungen in hoeherer aufloesung zu vermeiden -->
  <!-- mainClock -->
  <div id="div_mainClock" style="display:block; z-index:2;">
    <table cellpadding="4" cellspacing="0">
      <tr>
        <td bgcolor="black">
          <table cellpadding="10" cellspacing="0">
            <tr>
              <td id="mainClockBorder" bgcolor="white">
                <table cellpadding="0" cellspacing="0" class="clock" bgcolor="black">
                  <!-- up arrows -->
                  <tr>
                    <td><a href="#" onClick="decreaseClock('600', 'mainClock')"
                           onMouseOver="changeClockSettingsImage('mainClock.UpImage.3', 'up_large.png')"
                           onMouseOut="changeClockSettingsImage('mainClock.UpImage.3', 'up_large_draft.png')">

                      <img id="mainClock.UpImage.3" src="../images/clock/up_large_draft.png" title="" border="0"></a>
                    </td>

                    <td><a href="#" onClick="decreaseClock('60', 'mainClock')"
                           onMouseOver="changeClockSettingsImage('mainClock.UpImage.2', 'up_large.png')"
                           onMouseOut="changeClockSettingsImage('mainClock.UpImage.2', 'up_large_draft.png')">

                      <img id="mainClock.UpImage.2" src="../images/clock/up_large_draft.png" border="0"></a></td>

                    <td> <!-- separator -->
                    </td>

                    <td><a href="#" onClick="decreaseClock('10', 'mainClock')"
                           onMouseOver="changeClockSettingsImage('mainClock.UpImage.1', 'up_large.png')"
                           onMouseOut="changeClockSettingsImage('mainClock.UpImage.1', 'up_large_draft.png')">

                      <img id="mainClock.UpImage.1" src="../images/clock/up_large_draft.png" border="0"></a></td>

                    <td><a href="#" onClick="decreaseClock('1', 'mainClock')"
                           onMouseOver="changeClockSettingsImage('mainClock.UpImage.0', 'up_large.png')"
                           onMouseOut="changeClockSettingsImage('mainClock.UpImage.0', 'up_large_draft.png')">

                      <img id="mainClock.UpImage.0" src="../images/clock/up_large_draft.png" border="0"> </a></td>
                  </tr>
                  <!-- clock -->
                  <tr>
                    <td class="clock"><img src="../images/clock/0_large.png" onMouseDown="startStopClock('mainClock')" id="mainClock.minutes.1" border="0"></td>
                    <td class="clock"><img src="../images/clock/0_large.png" onMouseDown="startStopClock('mainClock')" id="mainClock.minutes.2" border="0"></td>
                    <td class="clock"><img src="../images/clock/separator_large.png"   onMouseDown="startStopClock('mainClock')"  id="mainClock.separator" border="0"></td>
                    <td class="clock"><img src="../images/clock/0_large.png" onMouseDown="startStopClock('mainClock')" id="mainClock.seconds.1" border="0"></td> 
                    <td class="clock"><img src="../images/clock/0_large.png" onMouseDown="startStopClock('mainClock')" id="mainClock.seconds.2" border="0"></td>
                  </tr>
                  <!-- down arrows -->
                  <tr>
                    <td class="clock"><a href="#" onClick="increaseClock('600', 'mainClock')"
                                         onMouseOver="changeClockSettingsImage('mainClock.DownImage.3', 'down_large.png')"
                                         onMouseOut="changeClockSettingsImage('mainClock.DownImage.3', 'down_large_draft.png')">

                      <img id="mainClock.DownImage.3" src="../images/clock/down_large_draft.png" border="0"></a></td>

                    <td class="clock"><a href="#" onClick="increaseClock('60', 'mainClock')"
                                         onMouseOver="changeClockSettingsImage('mainClock.DownImage.2', 'down_large.png')"
                                         onMouseOut="changeClockSettingsImage('mainClock.DownImage.2', 'down_large_draft.png')">

                      <img id="mainClock.DownImage.2" src="../images/clock/down_large_draft.png" border="0"></a></td>

                    <td class="clock">
                      <div id="setupFightImg" style="display:none;"><a href="#" onclick="showFightSetup()"
                                                                       onMouseOver="changeClockSettingsImage('mainClockSetup', 'setup_large.png')"
                                                                       onMouseOut="changeClockSettingsImage('mainClockSetup', 'setup_large_draft.png')">Kampfeinstellungen</a>
                        <img id="mainClockSetup" src="../images/clock/setup_large_draft.png" border="0"></div>
                      <!-- separator -->
                    </td>

                    <td class="clock"><a href="#" onClick="increaseClock('10', 'mainClock')"
                                         onMouseOver="changeClockSettingsImage('mainClock.DownImage.1', 'down_large.png')"
                                         onMouseOut="changeClockSettingsImage('mainClock.DownImage.1', 'down_large_draft.png')">

                      <img id="mainClock.DownImage.1" src="../images/clock/down_large_draft.png" border="0"></a></td>

                    <td class="clock"><a href="#" onClick="increaseClock('1', 'mainClock')"
                                         onMouseOver="changeClockSettingsImage('mainClock.DownImage.0', 'down_large.png')"
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
</td>


<td  width="300px" align="right" valign="bottom" bgcolor="black"> <!--width="34%"-->
<div id="rightHoldingClock">

  <table cellpadding="4" cellspacing="0">                     <!-- Rahmen außen rechte Uhr Holding -->
    <tr>
      <td >                                   <!-- Rahmen außen rechte Uhr Holding -->
         <!--Werbung oder next Fight -->
		 <a href="#" onClick="shutClock()"> 
		 <c:if test="${fn:length(adminFightingClockAction.previewForTatami) > 0}">
		 <t:dataTable value="#{adminFightingClockAction.previewForTatami}" var="cty3" rows="1"
                   border="0" cellspacing="0" cellpadding="0" binding="#{adminFightingClockAction.dataTable3}"  >
			<t:column width="100%" style="text-align: center;">         
				<h:outputText value="next" style="font-size:12px !important;color:white;"/> 
				<h:outputText value="<br/>" escape="false"/> 
				<h:outputText value="#{cty3.nameRed}" styleClass="font_red td_preparation"  style="font-size:35px !important;"/>
				<h:outputText value="<br/>" escape="false"/> 
				<h:outputText value="#{cty3.classDescription}" style="font-size:12px !important;color:white;"/>
				<h:outputText value="<br/>" escape="false"/> 
				<h:outputText value="#{cty3.nameBlue}" styleClass="font_blue td_preparation"  style="font-size:35px !important;"/>
				<h:outputText value="<br/>" escape="false"/> 
				<h:outputText value="&nbsp;" escape="false"/> 
				<h:outputText value="<br/>" escape="false"/> 
				<h:outputText value="&nbsp;" escape="false"/> 
			</t:column>
		</t:dataTable>  
		</c:if>
		<c:if test="${fn:length(adminFightingClockAction.previewForTatami) == 0}">
			<img src="../images/error-icon.png">
		</c:if>
		</a>
      </td>
    </tr>
  </table>
</div>
</td>

</tr>
</table>


</td>
</tr>
</table>
</div>

<div id="leftInjuryClock" style="display:none; position:absolute; z-index:99; top:384px; left:140px; background: #000000;">
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
								<a href="#" onClick="increaseClock('600', 'leftInjuryClock')"   onMouseOver="changeClockSettingsImage('leftInjuryClock.UpImage.3', 'up.png')"
								   onMouseOut="changeClockSettingsImage('leftInjuryClock.UpImage.3', 'up_draft.png')">
								  <img id="leftInjuryClock.UpImage.3" src="../images/clock/up_draft.png" title="" border="0"> </a>
							  </td>
							  <td class="clock">
								<a href="#" onClick="increaseClock('60', 'leftInjuryClock')"  onMouseOver="changeClockSettingsImage('leftInjuryClock.UpImage.2', 'up.png')"
								   onMouseOut="changeClockSettingsImage('leftInjuryClock.UpImage.2', 'up_draft.png')">
								  <img id="leftInjuryClock.UpImage.2" src="../images/clock/up_draft.png" border="0"> </a>
							  </td>
							  <td class="clock"> <!-- separator -->							  </td>
							  <td class="clock">
								<a href="#" onClick="increaseClock('10', 'leftInjuryClock')"  onMouseOver="changeClockSettingsImage('leftInjuryClock.UpImage.1', 'up.png')"
								   onMouseOut="changeClockSettingsImage('leftInjuryClock.UpImage.1', 'up_draft.png')">
								  <img id="leftInjuryClock.UpImage.1" src="../images/clock/up_draft.png" border="0"> </a>
							  </td>
							  <td class="clock">
								<a href="#" onClick="increaseClock('1', 'leftInjuryClock')"   onMouseOver="changeClockSettingsImage('leftInjuryClock.UpImage.0', 'up.png')"
								   onMouseOut="changeClockSettingsImage('leftInjuryClock.UpImage.0', 'up_draft.png')">
								  <img id="leftInjuryClock.UpImage.0" src="../images/clock/up_draft.png" border="0"> </a>
							  </td>
							</tr>
							<!-- clock -->
							<tr>
							  <td class="clock">
								<img src="../images/clock/0.png" onMouseDown="startStopClock('leftInjuryClock')"  id="leftInjuryClock.minutes.1" border="0">
							  </td>
							  <td class="clock">
								<img src="../images/clock/0.png" onMouseDown="startStopClock('leftInjuryClock')"	 id="leftInjuryClock.minutes.2" border="0">
							  </td>
							  <td class="clock">
								<img src="../images/clock/separator.png" onMouseDown="startStopClock('leftInjuryClock')" border="0">
							  </td>
							  <td class="clock">
								<img src="../images/clock/0.png" onMouseDown="startStopClock('leftInjuryClock')" id="leftInjuryClock.seconds.1" border="0">
							  </td>
							  <td class="clock">
								<img src="../images/clock/0.png" onMouseDown="startStopClock('leftInjuryClock')"  id="leftInjuryClock.seconds.2" border="0">
							  </td>
							</tr>
							<!-- down arrows -->
							<tr>
							  <td class="clock">
								<a href="#" onClick="decreaseClock('600', 'leftInjuryClock')"    onMouseOver="changeClockSettingsImage('leftInjuryClock.DownImage.3', 'down.png')"
								   onMouseOut="changeClockSettingsImage('leftInjuryClock.DownImage.3', 'down_draft.png')">
								  <img id="leftInjuryClock.DownImage.3" src="../images/clock/down_draft.png" border="0"> </a>
							  </td>
							  <td class="clock">
								<a href="#" onClick="decreaseClock('60', 'leftInjuryClock')"   onMouseOver="changeClockSettingsImage('leftInjuryClock.DownImage.2', 'down.png')"
								   onMouseOut="changeClockSettingsImage('leftInjuryClock.DownImage.2', 'down_draft.png')">
								  <img id="leftInjuryClock.DownImage.2" src="../images/clock/down_draft.png" border="0"> </a>
							  </td>
							  <td class="clock"> <!-- separator -->							  </td>
							  <td class="clock">
								<a href="#" onClick="decreaseClock('10', 'leftInjuryClock')"  onMouseOver="changeClockSettingsImage('leftInjuryClock.DownImage.1', 'down.png')"
								   onMouseOut="changeClockSettingsImage('leftInjuryClock.DownImage.1', 'down_draft.png')">
								  <img id="leftInjuryClock.DownImage.1" src="../images/clock/down_draft.png" border="0"> </a>
							  </td>
							  <td class="clock">
								<a href="#" onClick="decreaseClock('1', 'leftInjuryClock')"	   onMouseOver="changeClockSettingsImage('leftInjuryClock.DownImage.0', 'down.png')"
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

<div id="rightInjuryClock" style="display:none; position:absolute; z-index:99; top:98px; left:140px;background: #000000;" >
<table class="injury-border">
	<td>
		 <table  cellspacing="0" cellpadding="0">
		 <tr > 
			<img src="../images/clock/fusen_kiken.png" onClick="activateDeactivateFusenKiken('right')" id="fusen_kiken_right"><img src="../images/clock/injury_red.gif" border="0">
		 </tr>
		 <tr>
		  <table cellpadding="0" cellspacing="0" >                  <!-- Rahmen außen rechte Uhr Holding -->
			<tr>
			  <td bgcolor="black">                                <!-- Rahmen außen rechte Uhr Holding -->
				<table cellpadding="10" cellspacing="0">              <!-- Rahmen innen rechte Uhr Holding -->
				  <tr>
					<td id="rightInjuryClockBorder" bgcolor="white"> <!-- Rahmen innen rechte Uhr Holding -->
					  <table cellpadding="0" cellspacing="0" class="clock"> <!-- Tabelle linke Uhr Injury -->
						<!-- up arrows -->
						<tr>
						  <td class="clock">
							<a href="#" onClick="increaseClock('600', 'rightInjuryClock')"
							   onMouseOver="changeClockSettingsImage('rightInjuryClock.UpImage.3', 'up.png')"
							   onMouseOut="changeClockSettingsImage('rightInjuryClock.UpImage.3', 'up_draft.png')">

							  <img id="rightInjuryClock.UpImage.3" src="../images/clock/up_draft.png" title="" border="0"> </a>
						  </td>

						  <td class="clock">
							<a href="#" onClick="increaseClock('60', 'rightInjuryClock')"
							   onMouseOver="changeClockSettingsImage('rightInjuryClock.UpImage.2', 'up.png')"
							   onMouseOut="changeClockSettingsImage('rightInjuryClock.UpImage.2', 'up_draft.png')">

							  <img id="rightInjuryClock.UpImage.2" src="../images/clock/up_draft.png" border="0"> </a>
						  </td>

						  <td class="clock"> <!-- separator -->
						  </td>

						  <td class="clock">
							<a href="#" onClick="increaseClock('10', 'rightInjuryClock')"
							   onMouseOver="changeClockSettingsImage('rightInjuryClock.UpImage.1', 'up.png')"
							   onMouseOut="changeClockSettingsImage('rightInjuryClock.UpImage.1', 'up_draft.png')">

							  <img id="rightInjuryClock.UpImage.1" src="../images/clock/up_draft.png" border="0"> </a>
						  </td>

						  <td class="clock">
							<a href="#" onClick="increaseClock('1', 'rightInjuryClock')"
							   onMouseOver="changeClockSettingsImage('rightInjuryClock.UpImage.0', 'up.png')"
							   onMouseOut="changeClockSettingsImage('rightInjuryClock.UpImage.0', 'up_draft.png')">

							  <img id="rightInjuryClock.UpImage.0" src="../images/clock/up_draft.png" border="0"> </a>
						  </td>
						</tr>
						<!-- clock -->
						<tr>
						  <td class="clock">
							<img src="../images/clock/0.png" onMouseDown="startStopClock('rightInjuryClock')"
								 id="rightInjuryClock.minutes.1" border="0">
						  </td>

						  <td class="clock">
							<img src="../images/clock/0.png" onMouseDown="startStopClock('rightInjuryClock')"
								 id="rightInjuryClock.minutes.2" border="0">
						  </td>

						  <td class="clock">
							<img src="../images/clock/separator.png" onMouseDown="startStopClock('rightInjuryClock')"
								 border="0">
						  </td>

						  <td class="clock">
							<img src="../images/clock/0.png" onMouseDown="startStopClock('rightInjuryClock')"
								 id="rightInjuryClock.seconds.1" border="0">
						  </td>

						  <td class="clock">
							<img src="../images/clock/0.png" onMouseDown="startStopClock('rightInjuryClock')"
								 id="rightInjuryClock.seconds.2" border="0">
						  </td>
						</tr>
						<!-- down arrows -->
						<tr>
						  <td class="clock">
							<a href="#" onClick="decreaseClock('600', 'rightInjuryClock')"
							   onMouseOver="changeClockSettingsImage('rightInjuryClock.DownImage.3', 'down.png')"
							   onMouseOut="changeClockSettingsImage('rightInjuryClock.DownImage.3', 'down_draft.png')">

							  <img id="rightInjuryClock.DownImage.3" src="../images/clock/down_draft.png" border="0"> </a>
						  </td>

						  <td class="clock">
							<a href="#" onClick="decreaseClock('60', 'rightInjuryClock')"
							   onMouseOver="changeClockSettingsImage('rightInjuryClock.DownImage.2', 'down.png')"
							   onMouseOut="changeClockSettingsImage('rightInjuryClock.DownImage.2', 'down_draft.png')">

							  <img id="rightInjuryClock.DownImage.2" src="../images/clock/down_draft.png" border="0"> </a>
						  </td>

						  <td class="clock">               <!-- separator -->
						  </td>

						  <td class="clock">
							<a href="#" onClick="decreaseClock('10', 'rightInjuryClock')"
							   onMouseOver="changeClockSettingsImage('rightInjuryClock.DownImage.1', 'down.png')"
							   onMouseOut="changeClockSettingsImage('rightInjuryClock.DownImage.1', 'down_draft.png')">

							  <img id="rightInjuryClock.DownImage.1" src="../images/clock/down_draft.png" border="0"> </a>
						  </td>

						  <td class="clock">
							<a href="#" onClick="decreaseClock('1', 'rightInjuryClock')"
							   onMouseOver="changeClockSettingsImage('rightInjuryClock.DownImage.0', 'down.png')"
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


  <!-- KikenWinner -->
<div id="div_KikenWinner" style="display:none; background-color:#FFFFFF; z-index:100; left:220; top:300px; position: absolute;">
    <table width="300" height="220" border="8" bordercolor="#FFFE00" cellpadding="4" cellspacing="0">
      <tr> 
        <td colspan="3" align="center"
            style="font-size:20px; font-weight:bold; color:#FFFFFF; background-color:#000000;">
          KIKENGACHI
        </td>
      </tr>
      <tr>
        <td colspan="3" align="center" style="font-size:12px;">
          please nominate the winner
        </td>
      </tr>
      <tr>
        <td height="30px" align="right" width="33%"
            style="font-size:16px; font-weight:bold; color:#000000; background-color:#000000;">
          <div id="up_blue" style="display:block; height:20px; background-color:#000000;"
               onClick="setKikenWinner('left');">&nbsp;</div>
        </td>
        <td align="right" width="34%"
            style="font-size:16px; font-weight:bold; color:#000000; background-color:#000000;">
          <div id="up_none" style="display:block; height:20px; background-color:#5fff39;"
               onClick="setKikenWinner('none')">&nbsp;</div>
        </td>
        <td align="right" width="33%"
            style="font-size:16px; font-weight:bold; color:#000000; background-color:#000000;">
          <div id="up_red" style="display:block; height:20px; background-color:#000000;"
               onClick="setKikenWinner('right')">&nbsp;</div>
        </td>
      </tr>
      <tr>
        <td height="56px" align="center" class="blue" width="33%"
            style="font-size:16px; font-weight:bold; color:#FFFFFF;"
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
      <tr>
        <td height="30px" align="left" width="33%"
            style="font-size:16px; font-weight:bold; color:#000000; background-color:#000000;">
          <div id="down_blue" style="display:block; height:20px; background-color:#000000;"
               onClick="setKikenWinner('left')">&nbsp;</div>
        </td>
        <td align="left" width="34%" style="font-size:16px; font-weight:bold; color:#000000; background-color:#000000;">
          <div id="down_none" style="display:block; height:20px; background-color:#5fff39;"
               onClick="setKikenWinner('none')">&nbsp;</div>
        </td>
        <td align="left" width="33%" style="font-size:16px; font-weight:bold; color:#000000; background-color:#000000;">
          <div id="down_red" style="display:block; height:20px; background-color:#000000;"
               onClick="setKikenWinner('right')">&nbsp;</div>
        </td>
      </tr>
    </table>
  </div>
  <!-- Ende KikenWinner -->

<div style="display:none; visibility:hidden;">
  <img src="../images/clock/0.png">
  <img src="../images/clock/1.png">
  <img src="../images/clock/2.png">
  <img src="../images/clock/3.png">
  <img src="../images/clock/4.png">
  <img src="../images/clock/5.png">
  <img src="../images/clock/6.png">
  <img src="../images/clock/7.png">
  <img src="../images/clock/8.png">
  <img src="../images/clock/9.png">
  <img src="../images/clock/0_ippon.png">
  <img src="../images/clock/0_ippon_gr.png">
  <img src="../images/clock/0_large.png">
  <img src="../images/clock/1_ippon.png">
  <img src="../images/clock/1_ippon_gr.png">
  <img src="../images/clock/1_large.png">
  <img src="../images/clock/2_ippon.png">
  <img src="../images/clock/2_ippon_gr.png">
  <img src="../images/clock/2_large.png">
  <img src="../images/clock/3_ippon.png">
  <img src="../images/clock/3_ippon_gr.png">
  <img src="../images/clock/3_large.png">
  <img src="../images/clock/4_ippon.png">
  <img src="../images/clock/4_ippon_gr.png">
  <img src="../images/clock/4_large.png">
  <img src="../images/clock/5_ippon.png">
  <img src="../images/clock/5_ippon_gr.png">
  <img src="../images/clock/5_large.png">
  <img src="../images/clock/6_ippon.png">
  <img src="../images/clock/6_ippon_gr.png">
  <img src="../images/clock/6_large.png">
  <img src="../images/clock/7_ippon.png">
  <img src="../images/clock/7_ippon_gr.png">
  <img src="../images/clock/7_large.png">
  <img src="../images/clock/8_ippon.png">
  <img src="../images/clock/8_ippon_gr.png">
  <img src="../images/clock/8_large.png">
  <img src="../images/clock/9_ippon.png">
  <img src="../images/clock/9_ippon_gr.png">
  <img src="../images/clock/9_large.png">
  <img src="../sound/klingel02.swf">
  <img src="../sound/alarm01.swf">
  <img src="../sound/alarm02.swf">
</div>
   </body>
</html>
</h:form>
</f:view>