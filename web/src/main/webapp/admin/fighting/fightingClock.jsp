<%@page import="java.util.List"%>
<%@ include file="/common/taglibs.jsp" %>
<%--
  ~ This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
  ~ Copyright (c) 2010.
  ~ Joerg Boehme / Tino Schlegel
  ~
  ~ File    : fightingClock.jsp
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

<f:view>

<h:form id="adminFightingClockAction">
<f:loadBundle basename="de.jjw.webapp.messages.admin.fight" var="msg"/>
<%
  
  //String labelNextFight = "";
  //String labelPreparation = "";

%>

<html>
<head>

  <title>
    <h:outputText value="#{adminFightingClockAction.fight.fightingclass.age.description}"/>
    &nbsp;
    <h:outputText value="#{adminFightingClockAction.fight.fightingclass.sexWeb}"/>
    &nbsp;
    <h:outputText value="#{adminFightingClockAction.fight.fightingclass.weightclass}"/></title>

  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
  <meta name="version" content="display V1.09.01">
  <LINK REL="STYLESHEET" TYPE="text/css" HREF="../style_fighting_clock.css" TITLE="classic"/>
  <script type="text/javascript" src="../javascript_fighting.js"></script>
</head>

<body oncontextmenu="return false" onLoad='startfight(
		"<h:outputText value="#{adminFightingClockAction.fight.fightingclass.age.description}"/>",
		<h:outputText value="#{adminFightingClockAction.fight.fightingclass.age.fightingTime}"/>,
		 <h:outputText value="#{adminFightingClockAction.fight.fightingclass.age.overtime}"/>,
		15, 
		<h:outputText value="#{adminFightingClockAction.fight.fightingclass.age.injurytime}"/>); 'onunload="shutClock();" >

<!--
###################################################################################################################
Zeitframe
###################################################################################################################
-->
<div id="time_frame" align="center"
     style="display:none; visibility:hidden; position:absolute; z-index:99; top:114px; left:433px; height: auto; width:153px; background-color:#000000; border:2px solid black; color:#00FF00; font-size: 96px; font-weight:bold; font-family:Arial;">
  Z<br/>
  E<br/>I<br/>T
</div>
<!--
###################################################################################################################
-->

<!--
####################################################################################################################
Soundframe
####################################################################################################################
-->
<iframe marginwidth="0" marginheight="0" name="sound_frame" id="sound_frame" frameborder="0" src="./leer" width="0"
        height="0"></iframe>
<!--
####################################################################################################################
Anzeige: Kämpfer aufrufen
####################################################################################################################
-->

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
       style="height:170px; width:100%; background-image: url('<h:outputText value="#{adminFightingClockAction.imageCommandRed}"/>'); ;margin-right:20px;">
    <h:outputText value="#{adminFightingClockAction.fight.fighterRed.firstname}"/>
    <br/>
    <span style="font-size:100px;"><h:outputText value="#{adminFightingClockAction.fight.fighterRed.name}"/></span>
  </div>

  <hr/>

  <div id="call_blue_1" align="center"
       style="height:170px; width:100%; background-image: url('<h:outputText value="#{adminFightingClockAction.imageCommandBlue}"/>'); ;margin-right:20px;">
    <h:outputText value="#{adminFightingClockAction.fight.fighterBlue.firstname}"/>
    <br/>
    <span style="font-size:100px;"><h:outputText value="#{adminFightingClockAction.fight.fighterBlue.name}"/></span>
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
<!--
Ende: Kämpfer aufrufen
##################################################################################################################
-->

<!--
##################################################################################################################
Anzeige: Kämpfer in Vorbereitung
##################################################################################################################
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
<!--
Ende: Kämpfer in Vorbereitung
#################################################################################################################################
-->


<!--
################################################################################################################
Anzeige: Wettkampfanzeige
################################################################################################################
-->

<div id="mainbody" style="display:none; visibility:hidden; padding:0px; margin:0px;">
<table width="100%" border="4" cellspacing="0" cellpadding="0" height="100%"> <!-- outer table -->
<tr>
<td height="100%">
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
<tr>
<td width="50%" class="white" valign="top">
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="white"
         style="background: transparent url('<h:outputText value="#{adminFightingClockAction.imageCommandBlue}"/>') no-repeat top right;">
    <tr>
      <td height="90" style="padding-left:10px;">
        <br/>

        <span class="VornameWhite">
          <h:outputText value="#{adminFightingClockAction.fight.fighterBlue.firstname}"/>&nbsp;<h:outputText
            value="#{adminFightingClockAction.fight.fighterBlue.name}"/>
        </span>

              <span class="VereinWhite"><h:outputText
                  value="#{adminFightingClockAction.fight.fighterBlue.team.teamName}"/></span>
      </td>
    </tr>

    <tr>                                                                  <!-- linke Seite Punkte -->
      <td align="center">
        <table cellpadding="0" cellspacing="0" class="points-outer-border"> <!-- Punktetabelle Rahmen außen-->
          <tr>
            <td>
              <table cellpadding="0" cellspacing="0" id="leftPointsBorderTable"
                     class="points-inner-border">
                <tr>
                  <td>                         <!-- Punktetabelle Rahmen innen-->
                    <table cellspacing="0" cellpadding="0" class="points">        <!-- Punktetabelle -->
                      <tr>
                        <td align="right"><a href="#" onClick="increasePoints('100', 'left')"
                                             onMouseOver="changeImage('leftPointsUpImage.2','up_large.png')"
                                             onMouseOut="changeImage('leftPointsUpImage.2','up_large_draft.png')">

                          <img id="leftPointsUpImage.2" src="../images/clock/up_large_draft.png" title=""
                               border="0"></a>
                        </td>

                        <td align="right"><a href="#" onClick="increasePoints('10', 'left')"
                                             onMouseOver="changeImage('leftPointsUpImage.1','up_large.png')"
                                             onMouseOut="changeImage('leftPointsUpImage.1','up_large_draft.png')">

                          <img id="leftPointsUpImage.1" src="../images/clock/up_large_draft.png" border="0"> </a>
                        </td>

                        <td align="right"><a href="#" onClick="increasePoints('1', 'left')"
                                             onMouseOver="changeImage('leftPointsUpImage.0','up_large.png')"
                                             onMouseOut="changeImage('leftPointsUpImage.0','up_large_draft.png')">

                          <img id="leftPointsUpImage.0" src="../images/clock/up_large_draft.png" border="0"> </a>
                        </td>
                      </tr>

                      <tr>
                        <td>
                          <img src="../images/clock/0_x_large.png" id="leftPoints.2"
                               onMouseDown="handlePoints(event,'1','left')" border="0">
                        </td>
                        <td>
                          <img src="../images/clock/0_x_large.png" id="leftPoints.1"
                               onMouseDown="handlePoints(event,'1','left')" border="0">
                        </td>

                        <td>
                          <img src="../images/clock/0_x_large.png" id="leftPoints.0"
                               onMouseDown="handlePoints(event,'1','left')" border="0">
                        </td>
                      </tr>

                      <tr>
                        <td><a href="#" onClick="decreasePoints('100', 'left')"
                               onMouseOver="changeImage('leftPointsDownImage.2','down_large.png')"
                               onMouseOut="changeImage('leftPointsDownImage.2','down_large_draft.png')">

                          <img id="leftPointsDownImage.2" src="../images/clock/down_large_draft.png" title=""
                               border="0"></a></td>

                        <td><a href="#" onClick="decreasePoints('10', 'left')"
                               onMouseOver="changeImage('leftPointsDownImage.1','down_large.png')"
                               onMouseOut="changeImage('leftPointsDownImage.1','down_large_draft.png')">

                          <img id="leftPointsDownImage.1" src="../images/clock/down_large_draft.png" border="0">
                        </a></td>

                        <td>
                          <a href="#" onClick="decreasePoints('1', 'left')"
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
          </tr>
        </table>
      </td>
    </tr>

    <!-- neue Ipponanzeige -->
    <tr>
      <td align="center">

        <table width="348px" cellpadding="0" cellspacing="0">
          <tr>
            <td height="35px" colspan="3">
              <table width="100%" cellspacing="0" cellpadding="0" style="border:4px solid #666666;">
                <tr>

                  <td id="leftIppon1TDFlag" align="center" width="33%"
                      style="border:2px solid white; background-color:#000000;"
                      onMouseDown="handleIppon(event,'left','1');"><img src="../images/clock/ippon_active0.png"
                                                                        id="leftIppon1ImageFlag" border="0"></td>
                  <td id="leftIppon2TDFlag" align="center" width="34%"
                      style="border:2px solid white; background-color:#000000;"
                      onMouseDown="handleIppon(event,'left','2');"><img src="../images/clock/ippon_active0.png"
                                                                        id="leftIppon2ImageFlag" border="0"></td>
                  <td id="leftIppon3TDFlag" align="center" width="33%"
                      style="border:2px solid white; background-color:#000000;"
                      onMouseDown="handleIppon(event,'left','3');"><img src="../images/clock/ippon_active0.png"
                                                                        id="leftIppon3ImageFlag" border="0"></td>
                </tr>
              </table>
            </td>
          </tr>

          <tr>
            <td align="center" width="33%"
                style="color:white; font-size:25px; font-weight:bold; font-family:Arial;"
                onMouseDown="handleIppon(event,'left','1');">Part 1
            </td>
            <td align="center" width="34%"
                style="color:white; font-size:25px; font-weight:bold; font-family:Arial;"
                onMouseDown="handleIppon(event,'left','2');">Part 2
            </td>
            <td align="center" width="33%"
                style="color:white; font-size:25px; font-weight:bold; font-family:Arial;"
                onMouseDown="handleIppon(event,'left','3');">Part 3
            </td>
          </tr>


        </table>

      </td>
    </tr>

    <tr>                                                  <!-- linke Seite Strafen und Ippons -->
      <td align="center">
        <!--  Strafen links -->
        <br/>
        <table cellpadding="0" cellspacing="0" border="0" width="75%">
          <tr>
            <td align="center" width="25%" onMouseDown="handleShido(event,'left');">
              <span class="displayLabels">Shido</span>
            </td>
            <td align="center" width="40" onMouseDown="handleShido(event,'left');">
              <img src="../images/clock/ippon_active0.png" id="leftShidoImageFlag" border="0">
            </td>
            <td align="center" width="25%">
              <img src="../images/clock/ippon_active0.png" id="leftHansokumakeImageFlag" border="0"
                   style="visibility:hidden;">
            </td>
            <td align="center" width="40" onMouseDown="handleChui(event,'left');">
              <img src="../images/clock/ippon_active0.png" id="leftChuiImageFlag" border="0">
            </td>
            <td align="center" width="25%" onMouseDown="handleChui(event,'left');">
              <span class="displayLabels">Chui</span>
            </td>
          </tr>
          <tr>
            <td colspan="5" align="center">
              <span class="displayLabels"><a href="#" onclick="handleHansokumake(event,'left')">Hansokumake</a></span>
            </td>
          </tr>
        </table>
        <!--  Strafen links -->

      </td>
    </tr>
  </table>
</td>

<td bgcolor="#AAAAAA" width="2">&nbsp;</td>
<!-- Trenner von linker und rechter Seite oberer Bereich -->

<!-- rechte Spalte oberer Bereich -->
<td width="50%" class="red" valign="top">
  <table width="100%" border="0" cellspacing="0" cellpadding="0"
         style="background: transparent url('<h:outputText value="#{adminFightingClockAction.imageCommandRed}"/>') no-repeat top right;">
    <tr>
      <td height="90" style="padding-left:10px;">
        <br/>

        <span class="VornameWhite"><h:outputText
            value="#{adminFightingClockAction.fight.fighterRed.firstname}"/>&nbsp;<h:outputText
            value="#{adminFightingClockAction.fight.fighterRed.name}"/>
        </span>

        <span class="VereinRed"><h:outputText
            value="#{adminFightingClockAction.fight.fighterRed.team.teamName}"/></span>
      </td>
    </tr>

    <tr>                                                                          <!-- rechte Seite Punkte -->
      <td align="center">
        <table cellpadding="0" cellspacing="0" class="points-outer-border">
          <tr>
            <td> <!-- Punktetabelle Rahmen -->
              <table cellpadding="0" cellspacing="0" id="rightPointsBorderTable" class="points-inner-border">
                <tr>
                  <td>
                    <table border="0" cellspacing="0" cellpadding="0" class="points">     <!-- Punktetabelle -->
                      <tr>
                        <td align="right"><a href="#" onClick="increasePoints('100', 'right')"
                                             onMouseOver="changeImage('rightPointsUpImage.2','up_large.png')"
                                             onMouseOut="changeImage('rightPointsUpImage.2','up_large_draft.png')">

                          <img id="rightPointsUpImage.2" src="../images/clock/up_large_draft.png" title="" border="0">
                        </a>
                        </td>

                        <td align="right"><a href="#" onClick="increasePoints('10', 'right')"
                                             onMouseOver="changeImage('rightPointsUpImage.1','up_large.png')"
                                             onMouseOut="changeImage('rightPointsUpImage.1','up_large_draft.png')">

                          <img id="rightPointsUpImage.1" src="../images/clock/up_large_draft.png" border="0"></a></td>

                        <td align="right"><a href="#" onClick="increasePoints('1', 'right')"
                                             onMouseOver="changeImage('rightPointsUpImage.0','up_large.png')"
                                             onMouseOut="changeImage('rightPointsUpImage.0','up_large_draft.png')">

                          <img id="rightPointsUpImage.0" src="../images/clock/up_large_draft.png" border="0"> </a>
                        </td>
                      </tr>

                      <tr>
                        <td><a href="#" onMouseDown="handlePoints(event,'1','right')">
                          <img src="../images/clock/0_x_large.png" id="rightPoints.2" border="0"></a></td>

                        <td><a href="#" onMouseDown="handlePoints(event,'1','right')">
                          <img src="../images/clock/0_x_large.png" id="rightPoints.1" border="0"></a></td>

                        <td><a href="#" onMouseDown="handlePoints(event,'1','right')">
                          <img src="../images/clock/0_x_large.png" id="rightPoints.0" border="0"></a></td>
                      </tr>

                      <tr>
                        <td><a href="#" onClick="decreasePoints('100', 'right')"
                               onMouseOver="changeImage('rightPointsDownImage.2','down_large.png')"
                               onMouseOut="changeImage('rightPointsDownImage.2','down_large_draft.png')">

                          <img id="rightPointsDownImage.2" src="../images/clock/down_large_draft.png" title=""
                               border="0"></a></td>

                        <td><a href="#" onClick="decreasePoints('10', 'right')"
                               onMouseOver="changeImage('rightPointsDownImage.1','down_large.png')"
                               onMouseOut="changeImage('rightPointsDownImage.1','down_large_draft.png')">

                          <img id="rightPointsDownImage.1" src="../images/clock/down_large_draft.png" border="0"> </a>
                        </td>

                        <td>
                          <a href="#" onClick="decreasePoints('1', 'right')"
                             onMouseOver="changeImage('rightPointsDownImage.0','down_large.png')"
                             onMouseOut="changeImage('rightPointsDownImage.0','down_large_draft.png')">

                            <img id="rightPointsDownImage.0" src="../images/clock/down_large_draft.png" border="0"> </a>
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

    <!-- neue Ipponanzeige -->
    <tr>
      <td align="center">

        <table width="348px" cellpadding="0" cellspacing="0">
          <tr>
            <td height="35px" colspan="3">
              <table width="100%" cellspacing="0" cellpadding="0" style="border:4px solid #666666;">
                <tr>

                  <td id="rightIppon1TDFlag" align="center" width="33%"
                      style="border:2px solid white; background-color:#000000;"
                      onMouseDown="handleIppon(event,'right','1');"><img src="../images/clock/ippon_active0.png"
                                                                         id="rightIppon1ImageFlag" border="0"></td>
                  <td id="rightIppon2TDFlag" align="center" width="34%"
                      style="border:2px solid white; background-color:#000000;"
                      onMouseDown="handleIppon(event,'right','2');"><img src="../images/clock/ippon_active0.png"
                                                                         id="rightIppon2ImageFlag" border="0"></td>
                  <td id="rightIppon3TDFlag" align="center" width="33%"
                      style="border:2px solid white; background-color:#000000;"
                      onMouseDown="handleIppon(event,'right','3');"><img src="../images/clock/ippon_active0.png"
                                                                         id="rightIppon3ImageFlag" border="0"></td>
                </tr>
              </table>
            </td>
          </tr>

          <tr>
            <td align="center" width="33%" style="color:white; font-size:25px; font-weight:bold; font-family:Arial;"
                onMouseDown="handleIppon(event,'right','1');">Part 1
            </td>
            <td align="center" width="34%" style="color:white; font-size:25px; font-weight:bold; font-family:Arial;"
                onMouseDown="handleIppon(event,'right','2');">Part 2
            </td>
            <td align="center" width="33%" style="color:white; font-size:25px; font-weight:bold; font-family:Arial;"
                onMouseDown="handleIppon(event,'right','3');">Part 3
            </td>
          </tr>


        </table>

      </td>
    </tr>

    <tr>               <!-- rechte Seite Strafen und Ippons -->
      <td align="center">
        <!--  Strafen rechts -->
        <br/>
        <table cellpadding="0" cellspacing="0" border="0" width="75%">
          <tr>
            <td align="center" width="25%" onMouseDown="handleShido(event,'right');">
              <span class="displayLabels">Shido</span>
            </td>
            <td align="center" width="40" onMouseDown="handleShido(event,'right');">
              <img src="../images/clock/ippon_active0.png" id="rightShidoImageFlag" border="0"/>
            </td>
            <td align="center" width="25%">
              <img src="../images/clock/ippon_active0.png" id="rightHansokumakeImageFlag" border="0"
                   style="visibility:hidden;"/>
            </td>
            <td align="center" width="40" onMouseDown="handleChui(event,'right');">
              <img src="../images/clock/ippon_active0.png" id="rightChuiImageFlag" border="0">
            </td>
            <td align="center" width="25%" onMouseDown="handleChui(event,'right');">
              <span class="displayLabels">Chui</span>
            </td>
          </tr>
          <tr>
            <td colspan="5" align="center">
              <span class="displayLabels"><a href="#" onclick="handleHansokumake(event,'right')">Hansokumake</a></span>
            </td>
          </tr>
        </table>
        <!--  Strafen recht -->
      </td>
    </tr>
  </table>
</td>
</tr>
</table>
</td>
</tr>
<!-- Ende oberer Bereich -->

<tr>                                                              <!-- unterer Bereich mit den Uhren -->
<td>
<table width="100%" border="0" cellspacing="0" cellpadding="0"> <!-- Uhrentabelle -->
<tr>
<td class="white" width="243px" valign="bottom">

<div id="leftHoldingClockCaption">

  <!-- // von switchClock auf toggleClock umgestellt - 2007 by Sebastian Dressel -->

  <span class="smallClockCaption"><a href="#" onClick="toggleClock('left')">Osaekomi</a></span>

  <!--	  // Reset der leftHoldingClock -->
  <a href="#" onClick="resetClock('left')" onMouseOver="showInfo('leftHolding')" onMouseOut="hideInfo('leftHolding')">
    <img src="../images/clock/reset.png" border="0"></a>
  <!--      // Ende Reset der leftHoldingClock
           // 2007 by Sebastian Dressel
  -->
</div>

<div id="leftInjuryClockCaption" style="display:none;">
  <!-- // von switchClock auf toggleClock umgestellt - 2007 by Sebastian Dressel -->
  <span class="smallClockCaption"><a href="#" onClick="toggleClock('left')">Verletzungszeit</a></span>

  <!--	  // Fusengachi / Kikengachi BLAU -->
  <a href="#" onClick="activateDeactivateFusenKiken('left')">
    <img src="../images/clock/fusen_kiken.png" border="0" id="fusen_kiken_left"></a>
  <!--      // Ende Fusengachi / Kikengachi BLAU
           // 2007 by Sebastian Dressel
  -->

</div>

<div id="leftHoldingClock">
  <table cellpadding="4" cellspacing="0">                     <!-- Rahmen außen linke Uhr Holding -->
    <tr>
      <td bgcolor="black">                                   <!-- Rahmen außen linke Uhr Holding -->
        <table cellpadding="10" cellspacing="0">                 <!-- Rahmen innen linke Uhr Holding -->
          <tr>
            <td id="leftHoldingClockBorder" bgcolor="white">   <!-- Rahmen innen linke Uhr Holding -->
              <table cellpadding="0" cellspacing="0" class="clock"> <!-- Tabelle linke Uhr Holding -->
                <!-- up arrows -->
                <tr>
                  <td class="clock">
                    <a href="#" onClick="increaseClock('600', 'leftHoldingClock')"
                       onMouseOver="changeClockSettingsImage('leftHoldingClock.UpImage.3', 'up.png')"
                       onMouseOut="changeClockSettingsImage('leftHoldingClock.UpImage.3', 'up_draft.png')">

                      <img id="leftHoldingClock.UpImage.3" src="../images/clock/up_draft.png" title="" border="0"> </a>
                  </td>

                  <td class="clock">
                    <a href="#" onClick="increaseClock('60', 'leftHoldingClock')"
                       onMouseOver="changeClockSettingsImage('leftHoldingClock.UpImage.2', 'up.png')"
                       onMouseOut="changeClockSettingsImage('leftHoldingClock.UpImage.2', 'up_draft.png')">

                      <img id="leftHoldingClock.UpImage.2" src="../images/clock/up_draft.png" border="0"> </a>
                  </td>

                  <td class="clock"> <!-- separator -->
                  </td>

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
                         id="leftHoldingClock.minutes.1" border="0">
                  </td>

                  <td class="clock">
                    <img src="../images/clock/0.png" onMouseDown="startStopClock('leftHoldingClock')"
                         id="leftHoldingClock.minutes.2" border="0">
                  </td>

                  <td class="clock">
                    <img src="../images/clock/separator.png" onMouseDown="startStopClock('leftHoldingClock')"
                         border="0">
                  </td>

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
                    <a href="#" onClick="decreaseClock('600', 'leftHoldingClock')"
                       onMouseOver="changeClockSettingsImage('leftHoldingClock.DownImage.3', 'down.png')"
                       onMouseOut="changeClockSettingsImage('leftHoldingClock.DownImage.3', 'down_draft.png')">

                      <img id="leftHoldingClock.DownImage.3" src="../images/clock/down_draft.png" border="0"> </a>
                  </td>

                  <td class="clock">
                    <a href="#" onClick="decreaseClock('60', 'leftHoldingClock')"
                       onMouseOver="changeClockSettingsImage('leftHoldingClock.DownImage.2', 'down.png')"
                       onMouseOut="changeClockSettingsImage('leftHoldingClock.DownImage.2', 'down_draft.png')">

                      <img id="leftHoldingClock.DownImage.2" src="../images/clock/down_draft.png" border="0"> </a>
                  </td>

                  <td class="clock"> <!-- separator -->
                  </td>

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
        </table>
      </td>
    </tr>
  </table>
</div>

<div id="leftInjuryClock" style="display:none">
  <table cellpadding="4" cellspacing="0">                  <!-- Rahmen außen linke Uhr Injury -->
    <tr>
      <td bgcolor="black">                                <!-- Rahmen außen linke Uhr Injury -->
        <table cellpadding="10" cellspacing="0">              <!-- Rahmen innen linke Uhr Injury -->
          <tr>
            <td id="leftInjuryClockBorder" bgcolor="white"> <!-- Rahmen innen linke Uhr Injury -->
              <table cellpadding="0" cellspacing="0" class="clock"> <!-- Tabelle linke Uhr Injury -->
                <!-- up arrows -->
                <tr>
                  <td class="clock">
                    <a href="#" onClick="increaseClock('600', 'leftInjuryClock')"
                       onMouseOver="changeClockSettingsImage('leftInjuryClock.UpImage.3', 'up.png')"
                       onMouseOut="changeClockSettingsImage('leftInjuryClock.UpImage.3', 'up_draft.png')">

                      <img id="leftInjuryClock.UpImage.3" src="../images/clock/up_draft.png" title="" border="0"> </a>
                  </td>

                  <td class="clock">
                    <a href="#" onClick="increaseClock('60', 'leftInjuryClock')"
                       onMouseOver="changeClockSettingsImage('leftInjuryClock.UpImage.2', 'up.png')"
                       onMouseOut="changeClockSettingsImage('leftInjuryClock.UpImage.2', 'up_draft.png')">

                      <img id="leftInjuryClock.UpImage.2" src="../images/clock/up_draft.png" border="0"> </a>
                  </td>

                  <td class="clock"> <!-- separator -->
                  </td>

                  <td class="clock">
                    <a href="#" onClick="increaseClock('10', 'leftInjuryClock')"
                       onMouseOver="changeClockSettingsImage('leftInjuryClock.UpImage.1', 'up.png')"
                       onMouseOut="changeClockSettingsImage('leftInjuryClock.UpImage.1', 'up_draft.png')">

                      <img id="leftInjuryClock.UpImage.1" src="../images/clock/up_draft.png" border="0"> </a>
                  </td>

                  <td class="clock">
                    <a href="#" onClick="increaseClock('1', 'leftInjuryClock')"
                       onMouseOver="changeClockSettingsImage('leftInjuryClock.UpImage.0', 'up.png')"
                       onMouseOut="changeClockSettingsImage('leftInjuryClock.UpImage.0', 'up_draft.png')">

                      <img id="leftInjuryClock.UpImage.0" src="../images/clock/up_draft.png" border="0"> </a>
                  </td>
                </tr>
                <!-- clock -->
                <tr>
                  <td class="clock">
                    <img src="../images/clock/0.png" onMouseDown="startStopClock('leftInjuryClock')"
                         id="leftInjuryClock.minutes.1" border="0">
                  </td>

                  <td class="clock">
                    <img src="../images/clock/0.png" onMouseDown="startStopClock('leftInjuryClock')"
                         id="leftInjuryClock.minutes.2" border="0">
                  </td>

                  <td class="clock">
                    <img src="../images/clock/separator.png" onMouseDown="startStopClock('leftInjuryClock')" border="0">
                  </td>

                  <td class="clock">
                    <img src="../images/clock/0.png" onMouseDown="startStopClock('leftInjuryClock')"
                         id="leftInjuryClock.seconds.1" border="0">
                  </td>

                  <td class="clock">
                    <img src="../images/clock/0.png" onMouseDown="startStopClock('leftInjuryClock')"
                         id="leftInjuryClock.seconds.2" border="0">
                  </td>
                </tr>
                <!-- down arrows -->
                <tr>
                  <td class="clock">
                    <a href="#" onClick="decreaseClock('600', 'leftInjuryClock')"
                       onMouseOver="changeClockSettingsImage('leftInjuryClock.DownImage.3', 'down.png')"
                       onMouseOut="changeClockSettingsImage('leftInjuryClock.DownImage.3', 'down_draft.png')">

                      <img id="leftInjuryClock.DownImage.3" src="../images/clock/down_draft.png" border="0"> </a>
                  </td>

                  <td class="clock">
                    <a href="#" onClick="decreaseClock('60', 'leftInjuryClock')"
                       onMouseOver="changeClockSettingsImage('leftInjuryClock.DownImage.2', 'down.png')"
                       onMouseOut="changeClockSettingsImage('leftInjuryClock.DownImage.2', 'down_draft.png')">

                      <img id="leftInjuryClock.DownImage.2" src="../images/clock/down_draft.png" border="0"> </a>
                  </td>

                  <td class="clock"> <!-- separator -->
                  </td>

                  <td class="clock">
                    <a href="#" onClick="decreaseClock('10', 'leftInjuryClock')"
                       onMouseOver="changeClockSettingsImage('leftInjuryClock.DownImage.1', 'down.png')"
                       onMouseOut="changeClockSettingsImage('leftInjuryClock.DownImage.1', 'down_draft.png')">

                      <img id="leftInjuryClock.DownImage.1" src="../images/clock/down_draft.png" border="0"> </a>
                  </td>

                  <td class="clock">
                    <a href="#" onClick="decreaseClock('1', 'leftInjuryClock')"
                       onMouseOver="changeClockSettingsImage('leftInjuryClock.DownImage.0', 'down.png')"
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
</div>
</td>

<!-- Bereich rechts neben Holding/Injury links -->
<td class="blue" align="left" valign="bottom" style="padding-left:5px; padding-bottom:5px;" border="0">
  <img src="../images/clock/blank.png" width="36px" border="0">
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
                    <td><a href="#" onClick="increaseClock('600', 'mainClock')"
                           onMouseOver="changeClockSettingsImage('mainClock.UpImage.3', 'up_large.png')"
                           onMouseOut="changeClockSettingsImage('mainClock.UpImage.3', 'up_large_draft.png')">

                      <img id="mainClock.UpImage.3" src="../images/clock/up_large_draft.png" title="" border="0"></a>
                    </td>

                    <td><a href="#" onClick="increaseClock('60', 'mainClock')"
                           onMouseOver="changeClockSettingsImage('mainClock.UpImage.2', 'up_large.png')"
                           onMouseOut="changeClockSettingsImage('mainClock.UpImage.2', 'up_large_draft.png')">

                      <img id="mainClock.UpImage.2" src="../images/clock/up_large_draft.png" border="0"></a></td>

                    <td> <!-- separator -->
                    </td>

                    <td><a href="#" onClick="increaseClock('10', 'mainClock')"
                           onMouseOver="changeClockSettingsImage('mainClock.UpImage.1', 'up_large.png')"
                           onMouseOut="changeClockSettingsImage('mainClock.UpImage.1', 'up_large_draft.png')">

                      <img id="mainClock.UpImage.1" src="../images/clock/up_large_draft.png" border="0"></a></td>

                    <td><a href="#" onClick="increaseClock('1', 'mainClock')"
                           onMouseOver="changeClockSettingsImage('mainClock.UpImage.0', 'up_large.png')"
                           onMouseOut="changeClockSettingsImage('mainClock.UpImage.0', 'up_large_draft.png')">

                      <img id="mainClock.UpImage.0" src="../images/clock/up_large_draft.png" border="0"> </a></td>
                  </tr>
                  <!-- clock -->
                  <tr>
                    <td class="clock"><img src="../images/clock/0_large.png" onMouseDown="startStopClock('mainClock')"
                                           id="mainClock.minutes.1" border="0"></td>

                    <td class="clock"><img src="../images/clock/0_large.png" onMouseDown="startStopClock('mainClock')"
                                           id="mainClock.minutes.2" border="0"></td>

                    <td class="clock"><img src="../images/clock/separator_large.png"
                                           onMouseDown="startStopClock('mainClock')" border="0"></td>

                    <td class="clock"><img src="../images/clock/0_large.png" onMouseDown="startStopClock('mainClock')"
                                           id="mainClock.seconds.1" border="0"></td>

                    <td class="clock"><img src="../images/clock/0_large.png" onMouseDown="startStopClock('mainClock')"
                                           id="mainClock.seconds.2" border="0"></td>
                  </tr>
                  <!-- down arrows -->
                  <tr>
                    <td class="clock"><a href="#" onClick="decreaseClock('600', 'mainClock')"
                                         onMouseOver="changeClockSettingsImage('mainClock.DownImage.3', 'down_large.png')"
                                         onMouseOut="changeClockSettingsImage('mainClock.DownImage.3', 'down_large_draft.png')">

                      <img id="mainClock.DownImage.3" src="../images/clock/down_large_draft.png" border="0"></a></td>

                    <td class="clock"><a href="#" onClick="decreaseClock('60', 'mainClock')"
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

                    <td class="clock"><a href="#" onClick="decreaseClock('10', 'mainClock')"
                                         onMouseOver="changeClockSettingsImage('mainClock.DownImage.1', 'down_large.png')"
                                         onMouseOut="changeClockSettingsImage('mainClock.DownImage.1', 'down_large_draft.png')">

                      <img id="mainClock.DownImage.1" src="../images/clock/down_large_draft.png" border="0"></a></td>

                    <td class="clock"><a href="#" onClick="decreaseClock('1', 'mainClock')"
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
  <!-- Ende mainClock -->
  <!-- KikenWinner -->
  <div id="div_KikenWinner" style="display:none; background-color:#FFFFFF; z-index:50; padding:4px;">
    <table width="100%" height="100%" border="0" cellpadding="4" cellspacing="0">
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
</td>
<!-- Ende middleFeld -->

<!-- Bereich links neben der Holding/Injury rechts -->
<td class="red" align="right" valign="bottom" style="padding-right:5px; padding-bottom:5px;" border="0">
  <img src="../images/clock/blank.png" width="36px" border="0">
</td>

<!-- Holding/Injury rechts -->
<td class="red" width="243px" align="right" valign="bottom"> <!--width="34%"-->
<div id="rightHoldingClockCaption">
  <!--	  // Reset der rightHoldingClock und Eintrag der einzelnen Haltegriffzeiten
  -->
  <a href="#" onClick="resetClock('right')" onMouseOver="showInfo('rightHolding')"
     onMouseOut="hideInfo('rightHolding')">
    <img src="../images/clock/reset.png" border="0"></a>
  <!--      // Ende Reset der rightHoldingClock
           // 2007 by Sebastian Dressel
  -->
  <!-- // von switchClock auf toggleClock umgestellt - 2007 by Sebastian Dressel -->
  <span class="smallClockCaption"><a href="#" onClick="toggleClock('right')">Osaekomi</a></span>
</div>

<div id="rightInjuryClockCaption" style="display:none;">

  <!--      // Fusengachi / Kikengachi ROT-->
  <a href="#" onClick="activateDeactivateFusenKiken('right')">
    <img src="../images/clock/fusen_kiken.png" id="fusen_kiken_right" border="0"></a>
  <!--      // Ende Fusengachi / Kikengachi ROT
            // 2007 by Sebastian Dressel
  -->
  <!-- // von switchClock auf toggleClock umgestellt - 2007 by Sebastian Dressel -->
  <span class="smallClockCaption"><a href="#" onClick="toggleClock('right')">Verletzungszeit</a></span>
</div>


<div id="rightHoldingClock">

  <table cellpadding="4" cellspacing="0">                     <!-- Rahmen außen rechte Uhr Holding -->
    <tr>
      <td bgcolor="black">                                   <!-- Rahmen außen rechte Uhr Holding -->
        <table cellpadding="10" cellspacing="0">                 <!-- Rahmen innen rechte Uhr Holding -->
          <tr>
            <td id="rightHoldingClockBorder" bgcolor="white">   <!-- Rahmen innen rechte Uhr Holding -->
              <table cellpadding="0" cellspacing="0" class="clock"> <!-- Tabelle rechte Uhr Holding -->
                <!-- up arrows -->
                <tr>
                  <td class="clock">
                    <a href="#" onClick="increaseClock('600', 'rightHoldingClock')"
                       onMouseOver="changeClockSettingsImage('rightHoldingClock.UpImage.3', 'up.png')"
                       onMouseOut="changeClockSettingsImage('rightHoldingClock.UpImage.3', 'up_draft.png')">

                      <img id="rightHoldingClock.UpImage.3" src="../images/clock/up_draft.png" title="" border="0"> </a>
                  </td>

                  <td class="clock">
                    <a href="#" onClick="increaseClock('60', 'rightHoldingClock')"
                       onMouseOver="changeClockSettingsImage('rightHoldingClock.UpImage.2', 'up.png')"
                       onMouseOut="changeClockSettingsImage('rightHoldingClock.UpImage.2', 'up_draft.png')">

                      <img id="rightHoldingClock.UpImage.2" src="../images/clock/up_draft.png" border="0"> </a>
                  </td>

                  <td class="clock"> <!-- separator -->
                  </td>

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
                         id="rightHoldingClock.minutes.1" border="0">
                  </td>

                  <td class="clock">
                    <img src="../images/clock/0.png" onMouseDown="startStopClock('rightHoldingClock')"
                         id="rightHoldingClock.minutes.2" border="0">
                  </td>

                  <td class="clock">
                    <img src="../images/clock/separator.png" onMouseDown="startStopClock('rightHoldingClock')"
                         border="0">
                  </td>

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
                    <a href="#" onClick="decreaseClock('600', 'rightHoldingClock')"
                       onMouseOver="changeClockSettingsImage('rightHoldingClock.DownImage.3', 'down.png')"
                       onMouseOut="changeClockSettingsImage('rightHoldingClock.DownImage.3', 'down_draft.png')">

                      <img id="rightHoldingClock.DownImage.3" src="../images/clock/down_draft.png" border="0"> </a>
                  </td>

                  <td class="clock">
                    <a href="#" onClick="decreaseClock('60', 'rightHoldingClock')"
                       onMouseOver="changeClockSettingsImage('rightHoldingClock.DownImage.2', 'down.png')"
                       onMouseOut="changeClockSettingsImage('rightHoldingClock.DownImage.2', 'down_draft.png')">

                      <img id="rightHoldingClock.DownImage.2" src="../images/clock/down_draft.png" border="0"> </a>
                  </td>

                  <td class="clock">                  <!-- separator -->
                  </td>

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
        </table>
      </td>
    </tr>
  </table>
</div>

<div id="rightInjuryClock" style="display:none">
  <table cellpadding="4" cellspacing="0">                  <!-- Rahmen außen rechte Uhr Holding -->
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
</div>
</td>
</tr>
</table>
</td>
</tr>
</table>

</div>

<div class="Info" id="leftHolding">Holdings</div>
<div class="Info" id="rightHolding">Holdings</div>
<div class="Info" id="leftInjury">Injury</div>
<div class="Info" id="rightInjury">Injury</div>

<div class="Injury" id="InjuryWinner">InjuryTest</div>

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
</div>


</h:form>
</f:view>
</body>


</html>