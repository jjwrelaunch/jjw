/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : javascript_fighting.js
 * Created : 16 Jun 2010
 * Last Modified: Wed, 16 Jun 2010 12:48:03
 *
 * Ju Jutsu Web is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Ju Jutsu Web is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Ju Jutsu Web.  If not, see <http://www.gnu.org/licenses/>.
 */

var isfriend=false;
var leftIsWhite = true;
var maxTimeinMilliseconds = 5999000;
var i = 0; 						//counter
var clockUpdateIntervall = 100; // Intervall, in der die Uhrenanzeigen aktualisiert werden

var stdColor = "#FFFFFF";	// Farbe, die standardmaessige wiederhergestellt wird
var hiLightColor = "#00FF00";   // Farbe, die beim starten / stoppen der Uhren angezeigt wird
var hiLightColorStop = "#FF0000";   // Farbe, die beim stoppen der Uhren angezeigt wird
var maxTimeColor = "#FFFE00";	// Farbe, die bei erreichen der maximalen Kampfzeit angezeigt werden soll

var original_time_before_tie = 0;// Merkt sich die Kampfzeit bis zum Gleichstand und wird am Ende zur Kampfzeit addiert.

var sound_on = true;			// interne Abfrage - nicht aendern - TRUE wenn Sound gespielt werden soll
var sound_on_global = true      // FALSE wenn Sound generell abgeschaltet sein soll
var soundnummer = 1;			// Abzuspielender Sound 2: maennlich 1: weiblich

var osaekomi_time = 15;			// Haltegriffzeit
var osaekomi_time_tmp = 15;		//

var fighting_time = 180;		// Kampfzeit in Sekunden
var fighting_time_tmp = 180;

var fighting_over_time = 120;		// Verlaengerung in Sekunden
var fighting_over_time_tmp = 120;

var injury_time = 120;		// Verletzungszeit in Sekunden
var injury_time_tmp = 120;

var showTIE = true;				// TRUE : Gleichstand wird angezeigt FALSE : nicht angezeigt

var firstcall = true;
var pageCallTime = new Date().getTime();
var passthrough = false;	// Spezialvariable, setzt ggf. eine bevorstehende Prüfung aus (Bsp.: Punkte addieren bei FullIppon)

var fusengachi = 0;				// 0 = kein fusengachi; 1 = fusengachi ROT; 2 = fusengachi BLAU; -1 = fusengachi ROT + BLAU
var kikengachi = 0;				// 0 = keine kikengachi; 1 = kikengachi ROT; 2 = kikengachi BLAU; -1 = kikengachi ROT + BLAU
var fusengachi_winner = 0		// Gewinner wenn Aufgabe durch Krankheit / Verletzung / Nichterscheinen; 0 = keiner; 1 = Gewinner Rot; 2 = Gewinner Blau

var user_input = "GO__;";			// protokollierte Benutzereingaben 


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

function init(){
	//set Clock
	fighting_time_init = fighting_time;
	fighting_time=fighting_time-0.1;
	updateClock("mainClock");
	fighting_time = fighting_time_init;
	resizeclock();
	jjw_startRecordingScreen();
}

function resizeclock(){
        document.getElementById( "time_frame" ).style.left = screen.width/2-76 + "px";
		//document.getElementById( "clockTr" ).style.height = (screen.height-20-2*(285+ (screen.height-16- 768)/2)) + "px";  
		
        
        screenDiff= ermittleScreenDiff();
        document.getElementById( "clockTr" ).style.height=180+"px"
        document.getElementById( "redTr" ).style.height = (285+screenDiff) + "px";
		document.getElementById( "blueTr" ).style.height = (285+ screenDiff) + "px";
		document.getElementById( "rightInjuryClock" ).style.top = (98+screenDiff)+"px";
		document.getElementById( "leftInjuryClock" ).style.top =(384+2*screenDiff)+ "px";
		
		document.getElementById( "leftPoints.0" ).style.height = (200+ screenDiff) + "px";
		document.getElementById( "leftPoints.1" ).style.height = (200+ screenDiff) + "px";
		document.getElementById( "rightPoints.0" ).style.height = (200+ screenDiff) + "px";
		document.getElementById( "rightPoints.1" ).style.height = (200+ screenDiff) + "px";
		
		document.getElementById( "leftShidoImageFlag" ).style.height = (45+ parseInt(screenDiff/4)) + "px";
		document.getElementById( "leftShido2ImageFlag" ).style.height = (45+ parseInt(screenDiff/4)) + "px";
		document.getElementById( "rightShidoImageFlag" ).style.height = (45+ parseInt(screenDiff/4)) + "px";
		document.getElementById( "rightShido2ImageFlag" ).style.height = (45+ parseInt(screenDiff/4)) + "px";
		
		document.getElementById( "leftChuiImageFlag" ).style.height = (45+ parseInt(screenDiff/4)) + "px";
		document.getElementById( "leftChui2ImageFlag" ).style.height = (45+ parseInt(screenDiff/4)) + "px";
		document.getElementById( "rightChuiImageFlag" ).style.height = (45+ parseInt(screenDiff/4)) + "px";
		document.getElementById( "rightChui2ImageFlag" ).style.height = (45+ parseInt(screenDiff/4)) + "px";
		
		document.getElementById( "leftHansokumakeImageFlag" ).style.height = (45+ parseInt(screenDiff/4)) + "px";
		document.getElementById( "leftHansokumake2ImageFlag" ).style.height = (45+ parseInt(screenDiff/4)) + "px";
		document.getElementById( "rightHansokumakeImageFlag" ).style.height = (45+ parseInt(screenDiff/4)) + "px";
		document.getElementById( "rightHansokumake2ImageFlag" ).style.height = (45+ parseInt(screenDiff/4)) + "px";
		
		document.getElementById( "leftIppon1ImageFlag" ).style.height = (45+ parseInt(screenDiff/4)) + "px";
		document.getElementById( "leftIppon11ImageFlag" ).style.height = (45+ parseInt(screenDiff/4)) + "px";
		document.getElementById( "rightIppon1ImageFlag" ).style.height = (45+ parseInt(screenDiff/4)) + "px";
		document.getElementById( "rightIppon11ImageFlag" ).style.height = (45+ parseInt(screenDiff/4)) + "px";
		
		if(screen.width>1024) {
			// ab 1600 der Rahmen um strafen und Parts auf 300-350px, Beschriftung vergrößern und Fighternamen auf 50px, osaekomi größer
			//leftIppons, leftPenaltyTable und die rights dazu
			// <td align="right" width="210" id="leftIppons"> --> alignment middle
			document.getElementById( "leftIppon1TDFlag" ).style.width = 70 + parseInt(screenDiff/4*1.5) + "px";
			document.getElementById( "rightIppon1TDFlag" ).style.width = 70 + parseInt(screenDiff/4*1.5)+ "px";
		}
		
		if(screen.width>1400) {
			
			document.getElementById( "leftPenaltyTDFlag" ).style.width = 80 + "px";
			document.getElementById( "rightPenaltyTDFlag" ).style.width = 80+ "px";
			document.getElementById( "leftIppons" ).style.width = 250 + "px";
			document.getElementById( "leftPenaltyTable" ).style.width = 250 + "px";
			document.getElementById( "rightIppons" ).style.width = 250 + "px";
			document.getElementById( "rightPenaltyTable" ).style.width = 250 + "px";
			
			document.getElementById( "rightSpaceAfterIppon" ).style.fontSize="100px";
			document.getElementById( "leftSpaceAfterIppon" ).style.fontSize="100px"
			
		}
		
		document.getElementById( "leftIppon2ImageFlag" ).style.height = (45+ parseInt(screenDiff/4)) + "px";
		document.getElementById( "leftIppon22ImageFlag" ).style.height = (45+ parseInt(screenDiff/4)) + "px";
		document.getElementById( "rightIppon2ImageFlag" ).style.height = (45+ parseInt(screenDiff/4)) + "px";
		document.getElementById( "rightIppon22ImageFlag" ).style.height = (45+ parseInt(screenDiff/4)) + "px";
		
		document.getElementById( "leftIppon3ImageFlag" ).style.height = (45+ parseInt(screenDiff/4)) + "px";
		document.getElementById( "leftIppon33ImageFlag" ).style.height = (45+ parseInt(screenDiff/4)) + "px";
		document.getElementById( "rightIppon3ImageFlag" ).style.height = (45+ parseInt(screenDiff/4)) + "px";
		document.getElementById( "rightIppon33ImageFlag" ).style.height = (45+ parseInt(screenDiff/4)) + "px";
		
		
		document.getElementById( "leftHoldingClock.seconds.1" ).style.height = (83+ parseInt(screenDiff/4)) + "px";
		document.getElementById( "leftHoldingClock.seconds.2" ).style.height = (83+ parseInt(screenDiff/4)) + "px";
		document.getElementById( "rightHoldingClock.seconds.1" ).style.height = (83+ parseInt(screenDiff/4)) + "px";
		document.getElementById( "rightHoldingClock.seconds.2" ).style.height = (83+ parseInt(screenDiff/4)) + "px";
		
		if(screen.height < 769){
			document.getElementById( "globalTab" ).style.height = "600px";
			document.getElementById( "clockTr" ).style.height = "180px";
			document.getElementById( "redTr" ).style.height = "260px";
			document.getElementById( "blueTr" ).style.height =  "260px";
			
			document.getElementById( "rightInjuryClock" ).style.top = "50px";
			document.getElementById( "leftInjuryClock" ).style.top = "290px";
			
			document.getElementById( "redKikTd" ).style.height =  "30px";
			document.getElementById( "blueKikTd" ).style.height =  "30px";
			
			document.getElementById( "redSpanName" ).style.fontSize="30px";
			document.getElementById( "redSpanTeam" ).style.fontSize="20px";
			document.getElementById( "redNameTd" ).style.height =  "50px";
			
			document.getElementById( "blueSpanName" ).style.fontSize="30px"
		    document.getElementById( "blueSpanTeam" ).style.fontSize="20px";			
			document.getElementById( "blueNameTd" ).style.height =  "50px";
			
//			document.getElementById( "mainClock.seconds.1" ).style.height =  "100px";
//			document.getElementById( "mainClock.seconds.2" ).style.height =  "100px";
//			document.getElementById( "mainClock.separator" ).style.height =  "100px";
//			document.getElementById( "mainClock.minutes.1" ).style.height =  "100px";
//			document.getElementById( "mainClock.minutes.2" ).style.height =  "100px";
			
			document.getElementById( "call_red_1" ).style.fontSize="50px";
			document.getElementById( "call_red_1_name" ).style.fontSize="75px";
			document.getElementById( "call_blue_1" ).style.fontSize="50px";
			document.getElementById( "call_blue_1_name" ).style.fontSize="75px";
			
			document.getElementById( "call_red_1" ).style.height =  "130px";
			document.getElementById( "call_blue_1" ).style.height =  "130px";
					
		}
		
}

function ermittleScreenDiff(){
	//ermitteln des Skalierungsfaktors
	diff=parseInt((screen.height-12- 768)/2);
	if (2*285+2*diff+180+32 >screen.height)
		diff=parseInt((screen.height-68-180-2*285)/2)
	
//	if (screen.height> 1000)
//		diff = parseInt((1012-180-2*285)/2)
		
		return diff;
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function increaseHeight(defaultHeight){
	   if (defaultHeight>0) return defaultHeight+ (screen.height-16- 768)/2;
	   return (screen.height-16- 768)/2;
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function user_input_log( user_control )
{
    // StartStop mainClock [CSM_]
    if ( user_control == "SSmainClock" )
    {
        user_input = user_input + "CSM_" + ":" + getWholeSeconds( "mainClock" ) + ";";
    }
    // StartStop leftHoldingClock  [CSHL]
    if ( user_control == "SSleftHoldingClock" )
    {
        user_input = user_input + "CSHL" + ":" + getWholeSeconds( "mainClock" ) + ";";
    }
    // StartStop rightHoldingClock [CSHR]
    if ( user_control == "SSrightHoldingClock" )
    {
        user_input = user_input + "CSHR" + ":" + getWholeSeconds( "mainClock" ) + ";";
    }
    // StartStop leftInjuryClock [CSIL]
    if ( user_control == "SSleftInjuryClock" )
    {
        user_input = user_input + "CSIL" + ":" + getWholeSeconds( "leftInjuryClock" ) + ";";
    }
    // StartStop rightInjuryClock [CSIR]
    if ( user_control == "SSrightInjuryClock" )
    {
        user_input = user_input + "CSIR" + ":" + getWholeSeconds( "rightInjuryClock" ) + ";";
    }
    // Alle anderen Aktionen
    if ( user_control != "SSmainClock" && user_control != "SSleftHoldingClock" &&
            user_control != "SSrightHoldingClock" && user_control != "SSrightInjuryClock" &&
            user_control != "SSleftInjuryClock" )
    {
        user_input = user_input + user_control + ":" + getWholeSeconds( "mainClock" ) + ";";
    }
}

function alarmUserLog()
{
    alert( user_input );
}

/**
 * gibt L, R oder ein "_" zurueck
 * @param side
 */
function getSideShort( side )
{
    if ( "left" == side )
    {
        return "L";
    }
    if ( "right" == side )
    {
        return "R";
    }

    return "_";
}

function changeImage( id, bild )
{
    document.getElementById( id ).src = "../images/clock/" + bild;
}

function changeClockSettingsImage( id, bild )
{
    if ( !isActiveClock[id.substring( 0, id.indexOf( "." ) )] )
    {
        document.getElementById( id ).src = "../images/clock/" + bild;
    }
}

function gleichstand()
{
    var _red = 0, _blue = 0;

    if ( ipponList["left"] ["1"] > 0 )
    {
        _blue = _blue + 1;
    }
    if ( ipponList["left"] ["2"] > 0 )
    {
        _blue = _blue + 1;
    }
    if ( ipponList["left"] ["3"] > 0 )
    {
        _blue = _blue + 1;
    }
    if ( ipponList["right"] ["1"] > 0 )
    {
        _red = _red + 1;
    }
    if ( ipponList["right"] ["2"] > 0 )
    {
        _red = _red + 1;
    }
    if ( ipponList["right"] ["3"] > 0 )
    {
        _red = _red + 1;
    }

    if ( getPoints( "left" ) == getPoints( "right" ) && showTIE && getWholeSeconds( "mainClock" ) >= fighting_time &&
            _red == _blue )
    {
        showTIE = false;
        tmp_leftIppons = ipponList["left"] ["1"] + ipponList["left"] ["2"] + ipponList["left"] ["3"];
        tmp_rightIppons = ipponList["right"] ["1"] + ipponList["right"] ["2"] + ipponList["right"] ["3"];
        if ( tmp_leftIppons == tmp_rightIppons )
        {
            //LOGeintrag Anzeige von TIE
            user_input = user_input + "TIE_;";
            //Anzeige TIE
            alert( "TIE - \n\nlet the scorebord untouched\n\n\nGLEICHSTAND - \n\nLassen Sie alle Werte stehen\n" );
            original_time_before_tie = original_time_before_tie + getWholeSeconds( "mainClock" );
            // Kampfzeit auf Verlaengerungszeit setzen
            fighting_time = fighting_over_time;
            fighting_time_tmp = fighting_over_time_tmp;
        }
    }
}

function soundLaden()
{
    if ( sound_on && sound_on_global )
    {
        window.frames['sound_frame'].location.href = '../sound/klingel02.swf';
        sound_on = false;
    }
}

function alarmSound( soundnumber )
{
    if ( sound_on_global )
    {
        window.frames['sound_frame'].location.href = '../sound/alarm0' + soundnumber + '.swf';
    }
}

function handleKikenWinner( side )
{
    if ( side == "left" )
    {
        if ( fusengachi_winner == 0 )
        {
            setKikenWinner( "left" );
            return;
        }
        if ( fusengachi_winner == 1 )
        {
            setKikenWinner( "none" );
            return;
        }
        if ( fusengachi_winner == 2 )
        {
            setKikenWinner( "right" );
            return;
        }
    }
    if ( side == "right" )
    {
        if ( fusengachi_winner == 0 )
        {
            setKikenWinner( "right" );
            return;
        }
        if ( fusengachi_winner == 1 )
        {
            setKikenWinner( "left" );
            return;
        }
        if ( fusengachi_winner == 2 )
        {
            setKikenWinner( "none" );
            return;
        }
    }
}

function setKikenWinner( side )
{
    document.getElementById( "down_blue" ).style.backgroundColor = "#000000";
    document.getElementById( "up_blue" ).style.backgroundColor = "#000000";
    document.getElementById( "down_red" ).style.backgroundColor = "#000000";
    document.getElementById( "up_red" ).style.backgroundColor = "#000000";
    document.getElementById( "down_none" ).style.backgroundColor = "#000000";
    document.getElementById( "up_none" ).style.backgroundColor = "#000000";
    if ( side == "left" )
    {
        document.getElementById( "down_blue" ).style.backgroundColor = "#5fff39";
        document.getElementById( "up_blue" ).style.backgroundColor = "#5fff39";
        fusengachi_winner = 2;
    }
    if ( side == "right" )
    {
        document.getElementById( "down_red" ).style.backgroundColor = "#5fff39";
        document.getElementById( "up_red" ).style.backgroundColor = "#5fff39";
        fusengachi_winner = 1;
    }
    if ( side == "none" )
    {
        document.getElementById( "down_none" ).style.backgroundColor = "#5fff39";
        document.getElementById( "up_none" ).style.backgroundColor = "#5fff39";
        fusengachi_winner = 0;
    }
}

function giveBackResult()
{
    try
    {
        //Punktestand nach Regelwerk
        opener.document.getElementById( "main:adminFightAction:pointsRed" ).value = retRightPoints;
        opener.document.getElementById( "main:adminFightAction:pointsBlue" ).value = retLeftPoints;
        //Punktestand tatsaechlich / Statistik
        opener.document.getElementById( "main:adminFightAction:pointsRedOnClock" ).value = rightPoints;
        opener.document.getElementById( "main:adminFightAction:pointsBlueOnClock" ).value = leftPoints;
        //Anzahl Ippons
        opener.document.getElementById( "main:adminFightAction:ipponRedI" ).value = ipponList["right"] ["1"];
        opener.document.getElementById( "main:adminFightAction:ipponRedII" ).value = ipponList["right"] ["2"];
        opener.document.getElementById( "main:adminFightAction:ipponRedIII" ).value = ipponList["right"] ["3"];
        opener.document.getElementById( "main:adminFightAction:ipponBlueI" ).value = ipponList["left"] ["1"];
        opener.document.getElementById( "main:adminFightAction:ipponBlueII" ).value = ipponList["left"] ["2"];
        opener.document.getElementById( "main:adminFightAction:ipponBlueIII" ).value = ipponList["left"] ["3"];
        //Zeiten
        opener.document.getElementById( "main:adminFightAction:fightTime" ).value =
                Math.floor( milliseconds["mainClock"] / 1000 ) + original_time_before_tie;
        opener.document.getElementById( "main:adminFightAction:injuryTimeRed" ).value =
                Math.floor( milliseconds["rightInjuryClock"] / 1000 );
        opener.document.getElementById( "main:adminFightAction:injuryTimeBlue" ).value =
                Math.floor( milliseconds["leftInjuryClock"] / 1000 );

        opener.document.getElementById( "main:adminFightAction:fightTimeWithBreaks" ).value =
                Math.floor( (milliseconds["fightTimeWithBreaksEnd"] - milliseconds["fightTimeWithBreaksStart"]) /
                        1000 );
        // zeit von oeffnen bis schliessen der Uhr
        opener.document.getElementById( "main:adminFightAction:overallFightTime" ).value =
                Math.floor( (new Date().getTime() - pageCallTime) / 1000 );

        opener.document.getElementById( "main:adminFightAction:shidoRed" ).value = penaltyList["right"] ["shido"];
        opener.document.getElementById( "main:adminFightAction:shidoBlue" ).value = penaltyList["left"]  ["shido"];
        opener.document.getElementById( "main:adminFightAction:chuiRed" ).value = penaltyList["right"] ["chui"];
        opener.document.getElementById( "main:adminFightAction:chuiBlue" ).value = penaltyList["left"]  ["chui"];

        if ( penalties["right"][penaltyOrder[3] ] )
        {
            opener.document.getElementById( "main:adminFightAction:hansokumakeRed" ).value = 1;
        }
        else
        {
            opener.document.getElementById( "main:adminFightAction:hansokumakeRed" ).value = 0;
        }
        if ( penalties["left"][penaltyOrder[3] ] )
        {
            opener.document.getElementById( "main:adminFightAction:hansokumakeBlue" ).value = 1;
        }
        else
        {
            opener.document.getElementById( "main:adminFightAction:hansokumakeBlue" ).value = 0;
        }

        opener.document.getElementById( "main:adminFightAction:fusengachi" ).value = fusengachi;
        opener.document.getElementById( "main:adminFightAction:kikengachi" ).value = kikengachi;
        if ( retWinner == 1 )
        {
            opener.document.getElementById( "main:adminFightAction:winner" ).Value = 1;
            opener.document.getElementById( "main:adminFightAction:winner" ).selectedIndex = 1;
        }
        if ( retWinner == 0 )
        {
            opener.document.getElementById( "main:adminFightAction:winner" ).Value = 2;
            opener.document.getElementById( "main:adminFightAction:winner" ).selectedIndex = 2;
        }
        if ( retWinner == -1 )
        {
            opener.document.getElementById( "main:adminFightAction:winner" ).Value = 0;
            opener.document.getElementById( "main:adminFightAction:winner" ).selectedIndex = 0;
        }

        // UserLog abschliessen und uebergeben
        user_input = user_input + "ND1_;";
        opener.document.getElementById( "main:adminFightAction:protokoll" ).value = user_input;
    }
    catch( event )
    {
    	//Punktestand nach Regelwerk
        opener.document.getElementById( "main:offFightAction:pointsRed" ).value = retRightPoints;
        opener.document.getElementById( "main:offFightAction:pointsBlue" ).value = retLeftPoints;
        //Punktestand tatsaechlich / Statistik
        opener.document.getElementById( "main:offFightAction:pointsRedOnClock" ).value = rightPoints;
        opener.document.getElementById( "main:offFightAction:pointsBlueOnClock" ).value = leftPoints;
        //Anzahl Ippons
        opener.document.getElementById( "main:offFightAction:ipponRedI" ).value = ipponList["right"] ["1"];
        opener.document.getElementById( "main:offFightAction:ipponRedII" ).value = ipponList["right"] ["2"];
        opener.document.getElementById( "main:offFightAction:ipponRedIII" ).value = ipponList["right"] ["3"];
        opener.document.getElementById( "main:offFightAction:ipponBlueI" ).value = ipponList["left"] ["1"];
        opener.document.getElementById( "main:offFightAction:ipponBlueII" ).value = ipponList["left"] ["2"];
        opener.document.getElementById( "main:offFightAction:ipponBlueIII" ).value = ipponList["left"] ["3"];
        //Zeiten
        opener.document.getElementById( "main:offFightAction:fightTime" ).value =
                Math.floor( milliseconds["mainClock"] / 1000 ) + original_time_before_tie;
        opener.document.getElementById( "main:offFightAction:injuryTimeRed" ).value =
                Math.floor( milliseconds["rightInjuryClock"] / 1000 );
        opener.document.getElementById( "main:offFightAction:injuryTimeBlue" ).value =
                Math.floor( milliseconds["leftInjuryClock"] / 1000 );

        opener.document.getElementById( "main:offFightAction:fightTimeWithBreaks" ).value =
                Math.floor( (milliseconds["fightTimeWithBreaksEnd"] - milliseconds["fightTimeWithBreaksStart"]) /
                        1000 );
        // zeit von oeffnen bis schliessen der Uhr
        opener.document.getElementById( "main:offFightAction:overallFightTime" ).value =
                Math.floor( (new Date().getTime() - pageCallTime) / 1000 );

        opener.document.getElementById( "main:offFightAction:shidoRed" ).value = penaltyList["right"] ["shido"];
        opener.document.getElementById( "main:offFightAction:shidoBlue" ).value = penaltyList["left"]  ["shido"];
        opener.document.getElementById( "main:offFightAction:chuiRed" ).value = penaltyList["right"] ["chui"];
        opener.document.getElementById( "main:offFightAction:chuiBlue" ).value = penaltyList["left"]  ["chui"];

        if ( penalties["right"][penaltyOrder[3] ] )
        {
            opener.document.getElementById( "main:offFightAction:hansokumakeRed" ).value = 1;
        }
        else
        {
            opener.document.getElementById( "main:offFightAction:hansokumakeRed" ).value = 0;
        }
        if ( penalties["left"][penaltyOrder[3] ] )
        {
            opener.document.getElementById( "main:offFightAction:hansokumakeBlue" ).value = 1;
        }
        else
        {
            opener.document.getElementById( "main:offFightAction:hansokumakeBlue" ).value = 0;
        }

        opener.document.getElementById( "main:offFightAction:fusengachi" ).value = fusengachi;
        opener.document.getElementById( "main:offFightAction:kikengachi" ).value = kikengachi;
        if ( retWinner == 1 )
        {
            opener.document.getElementById( "main:offFightAction:winner" ).Value = 1;
            opener.document.getElementById( "main:offFightAction:winner" ).selectedIndex = 1;
        }
        if ( retWinner == 0 )
        {
            opener.document.getElementById( "main:offFightAction:winner" ).Value = 2;
            opener.document.getElementById( "main:offFightAction:winner" ).selectedIndex = 2;
        }
        if ( retWinner == -1 )
        {
            opener.document.getElementById( "main:offFightAction:winner" ).Value = 0;
            opener.document.getElementById( "main:offFightAction:winner" ).selectedIndex = 0;
        }

        // UserLog abschliessen und uebergeben
        user_input = user_input + "ND2_;";
        opener.document.getElementById( "main:offFightAction:protokoll" ).value = user_input;
    }
}

function giveBackFriendlyResult()
{
    try
    {
        //Punktestand nach Regelwerk
        opener.document.getElementById( "main:adminFriendlyFightAction:pointsRed" ).value = retRightPoints;
        opener.document.getElementById( "main:adminFriendlyFightAction:pointsBlue" ).value = retLeftPoints;
        //Punktestand tatsaechlich / Statistik
        opener.document.getElementById( "main:adminFriendlyFightAction:pointsRedOnClock" ).value = rightPoints;
        opener.document.getElementById( "main:adminFriendlyFightAction:pointsBlueOnClock" ).value = leftPoints;
        //Anzahl Ippons
        opener.document.getElementById( "main:adminFriendlyFightAction:ipponRedI" ).value = ipponList["right"] ["1"];
        opener.document.getElementById( "main:adminFriendlyFightAction:ipponRedII" ).value = ipponList["right"] ["2"];
        opener.document.getElementById( "main:adminFriendlyFightAction:ipponRedIII" ).value = ipponList["right"] ["3"];
        opener.document.getElementById( "main:adminFriendlyFightAction:ipponBlueI" ).value = ipponList["left"] ["1"];
        opener.document.getElementById( "main:adminFriendlyFightAction:ipponBlueII" ).value = ipponList["left"] ["2"];
        opener.document.getElementById( "main:adminFriendlyFightAction:ipponBlueIII" ).value = ipponList["left"] ["3"];
        //Zeiten
        opener.document.getElementById( "main:adminFriendlyFightAction:fightTime" ).value =
                Math.floor( milliseconds["mainClock"] / 1000 ) + original_time_before_tie;
        opener.document.getElementById( "main:adminFriendlyFightAction:injuryTimeRed" ).value =
                Math.floor( milliseconds["rightInjuryClock"] / 1000 );
        opener.document.getElementById( "main:adminFriendlyFightAction:injuryTimeBlue" ).value =
                Math.floor( milliseconds["leftInjuryClock"] / 1000 );

        opener.document.getElementById( "main:adminFriendlyFightAction:fightTimeWithBreaks" ).value =
                Math.floor( (milliseconds["fightTimeWithBreaksEnd"] - milliseconds["fightTimeWithBreaksStart"]) /
                        1000 );
        // zeit von oeffnen bis schliessen der Uhr
        opener.document.getElementById( "main:adminFriendlyFightAction:overallFightTime" ).value =
                Math.floor( (new Date().getTime() - pageCallTime) / 1000 );

        opener.document.getElementById( "main:adminFriendlyFightAction:shidoRed" ).value = penaltyList["right"] ["shido"];
        opener.document.getElementById( "main:adminFriendlyFightAction:shidoBlue" ).value = penaltyList["left"]  ["shido"];
        opener.document.getElementById( "main:adminFriendlyFightAction:chuiRed" ).value = penaltyList["right"] ["chui"];
        opener.document.getElementById( "main:adminFriendlyFightAction:chuiBlue" ).value = penaltyList["left"]  ["chui"];

        if ( penalties["right"][penaltyOrder[3] ] )
        {
            opener.document.getElementById( "main:adminFriendlyFightAction:hansokumakeRed" ).value = 1;
        }
        else
        {
            opener.document.getElementById( "main:adminFriendlyFightAction:hansokumakeRed" ).value = 0;
        }
        if ( penalties["left"][penaltyOrder[3] ] )
        {
            opener.document.getElementById( "main:adminFriendlyFightAction:hansokumakeBlue" ).value = 1;
        }
        else
        {
            opener.document.getElementById( "main:adminFriendlyFightAction:hansokumakeBlue" ).value = 0;
        }

        opener.document.getElementById( "main:adminFriendlyFightAction:fusengachi" ).value = fusengachi;
        opener.document.getElementById( "main:adminFriendlyFightAction:kikengachi" ).value = kikengachi;
        if ( retWinner == 1 )
        {
            opener.document.getElementById( "main:adminFriendlyFightAction:winner" ).Value = 1;
            opener.document.getElementById( "main:adminFriendlyFightAction:winner" ).selectedIndex = 1;
        }
        if ( retWinner == 0 )
        {
            opener.document.getElementById( "main:adminFriendlyFightAction:winner" ).Value = 2;
            opener.document.getElementById( "main:adminFriendlyFightAction:winner" ).selectedIndex = 2;
        }
        if ( retWinner == -1 )
        {
            opener.document.getElementById( "main:adminFriendlyFightAction:winner" ).Value = 0;
            opener.document.getElementById( "main:adminFriendlyFightAction:winner" ).selectedIndex = 0;
        }

        // UserLog abschliessen und uebergeben
        user_input = user_input + "ND1_;";
        opener.document.getElementById( "main:adminFriendlyFightAction:protokoll" ).value = user_input;
    }
    catch( event )
    {
    	//Punktestand nach Regelwerk
        opener.document.getElementById( "main:offFriendlyFightAction:pointsRed" ).value = retRightPoints;
        opener.document.getElementById( "main:offFriendlyFightAction:pointsBlue" ).value = retLeftPoints;
        //Punktestand tatsaechlich / Statistik
        opener.document.getElementById( "main:offFriendlyFightAction:pointsRedOnClock" ).value = rightPoints;
        opener.document.getElementById( "main:offFriendlyFightAction:pointsBlueOnClock" ).value = leftPoints;
        //Anzahl Ippons
        opener.document.getElementById( "main:offFriendlyFightAction:ipponRedI" ).value = ipponList["right"] ["1"];
        opener.document.getElementById( "main:offFriendlyFightAction:ipponRedII" ).value = ipponList["right"] ["2"];
        opener.document.getElementById( "main:offFriendlyFightAction:ipponRedIII" ).value = ipponList["right"] ["3"];
        opener.document.getElementById( "main:offFriendlyFightAction:ipponBlueI" ).value = ipponList["left"] ["1"];
        opener.document.getElementById( "main:offFriendlyFightAction:ipponBlueII" ).value = ipponList["left"] ["2"];
        opener.document.getElementById( "main:offFriendlyFightAction:ipponBlueIII" ).value = ipponList["left"] ["3"];
        //Zeiten
        opener.document.getElementById( "main:offFriendlyFightAction:fightTime" ).value =
                Math.floor( milliseconds["mainClock"] / 1000 ) + original_time_before_tie;
        opener.document.getElementById( "main:offFriendlyFightAction:injuryTimeRed" ).value =
                Math.floor( milliseconds["rightInjuryClock"] / 1000 );
        opener.document.getElementById( "main:offFriendlyFightAction:injuryTimeBlue" ).value =
                Math.floor( milliseconds["leftInjuryClock"] / 1000 );

        opener.document.getElementById( "main:offFriendlyFightAction:fightTimeWithBreaks" ).value =
                Math.floor( (milliseconds["fightTimeWithBreaksEnd"] - milliseconds["fightTimeWithBreaksStart"]) /
                        1000 );
        // zeit von oeffnen bis schliessen der Uhr
        opener.document.getElementById( "main:offFriendlyFightAction:overallFightTime" ).value =
                Math.floor( (new Date().getTime() - pageCallTime) / 1000 );

        opener.document.getElementById( "main:offFriendlyFightAction:shidoRed" ).value = penaltyList["right"] ["shido"];
        opener.document.getElementById( "main:offFriendlyFightAction:shidoBlue" ).value = penaltyList["left"]  ["shido"];
        opener.document.getElementById( "main:offFriendlyFightAction:chuiRed" ).value = penaltyList["right"] ["chui"];
        opener.document.getElementById( "main:offFriendlyFightAction:chuiBlue" ).value = penaltyList["left"]  ["chui"];

        if ( penalties["right"][penaltyOrder[3] ] )
        {
            opener.document.getElementById( "main:offFriendlyFightAction:hansokumakeRed" ).value = 1;
        }
        else
        {
            opener.document.getElementById( "main:offFriendlyFightAction:hansokumakeRed" ).value = 0;
        }
        if ( penalties["left"][penaltyOrder[3] ] )
        {
            opener.document.getElementById( "main:offFriendlyFightAction:hansokumakeBlue" ).value = 1;
        }
        else
        {
            opener.document.getElementById( "main:offFriendlyFightAction:hansokumakeBlue" ).value = 0;
        }

        opener.document.getElementById( "main:offFriendlyFightAction:fusengachi" ).value = fusengachi;
        opener.document.getElementById( "main:offFriendlyFightAction:kikengachi" ).value = kikengachi;
        if ( retWinner == 1 )
        {
            opener.document.getElementById( "main:offFriendlyFightAction:winner" ).Value = 1;
            opener.document.getElementById( "main:offFriendlyFightAction:winner" ).selectedIndex = 1;
        }
        if ( retWinner == 0 )
        {
            opener.document.getElementById( "main:offFriendlyFightAction:winner" ).Value = 2;
            opener.document.getElementById( "main:offFriendlyFightAction:winner" ).selectedIndex = 2;
        }
        if ( retWinner == -1 )
        {
            opener.document.getElementById( "main:offFriendlyFightAction:winner" ).Value = 0;
            opener.document.getElementById( "main:offFriendlyFightAction:winner" ).selectedIndex = 0;
        }

        // UserLog abschliessen und uebergeben
        user_input = user_input + "ND2_;";
        opener.document.getElementById( "main:offFriendlyFightAction:protokoll" ).value = user_input;
    }
}


function checkFusenKikenResult()
{
    if ( (isActiveFusen["left"]) || (isActiveFusen["right"]) )
    {
        if ( (isActiveFusen["left"]) && (isActiveFusen["right"]) )
        {
            fusengachi = -1;	// beide haben Fusen/Kiken
            fusengachi_winner = 0;
            retLeftPoints = 0;
            retRightPoints = 0;
            retWinner = -1;
            return;
        }
        if ( (isActiveFusen["left"]) && (!isActiveFusen["right"]) )
        {
            fusengachi = 2;	// Blau hat Fusen/Kiken
            fusengachi_winner = 1;
            retLeftPoints = 0;
            retRightPoints = 14;
            retWinner = 1;
            return;

        }
        if ( (!isActiveFusen["left"]) && (isActiveFusen["right"]) )
        {
            fusengachi = 1;	// Rot hat Fusen/Kiken
            fusengachi_winner = 2;
            retLeftPoints = 14;
            retRightPoints = 0;
            retWinner = 0;
            return;
        }
    }
    if ( (isActiveKiken["left"]) || (isActiveKiken["right"]) )
    {
        if ( isActiveKiken["left"] && isActiveKiken["right"] )
        {
            kikengachi = -1;
        }
        if ( isActiveKiken["left"] && !isActiveKiken["right"] )
        {
            kikengachi = 2;
        }
        if ( !isActiveKiken["left"] && isActiveKiken["right"] )
        {
            kikengachi = 1;
        }
        if ( fusengachi_winner == 0 )
        {
            retLeftPoints = 0;
            retRightPoints = 0;
            retWinner = -1;
            return;
        }
        if ( fusengachi_winner == 1 )
        {
            retLeftPoints = 0;
            retRightPoints = 14;
            retWinner = 1;
            return;
        }
        if ( fusengachi_winner == 2 )
        {
            retLeftPoints = 14;
            retRightPoints = 0;
            retWinner = 0;
            return;
        }
    }
}

//========================================================================================
// do result
//========================================================================================
var retLeftPoints = 0;
var retRightPoints = 0;
var retWinner = -1;
var isShutClockOnce=false;

function shutClock()
{
	if (isShutClockOnce) return;
	if (isfriend) {
		shutFriendlyClock();
		return;
	}
	
	if (videoOn == true){
		
		try{
			jjw_stopRecording();
			jjw_stopRecordingScreen();
		}
		catch (e){}
	}
		
    doResult();
    giveBackResult();	
    	
	var exit = confirm("schließen / close?");

	if (videoOn == true)
	{
		try{
			this.opener.jjw_sendVideo({'fightId' : videoFightId,'videoDescription' : videoDescription, 'discipline' : 'F',	'data' : this.opener.audioVideoRecorder.getBlob() });
			this.opener.jjw_sendScreen({'isScreen' : 'true','fightId' : videoFightId,'videoDescription' : videoDescription, 'discipline' : 'F',	'data' : this.opener.screenRecorder.getBlob() });
		}
		catch (e){}
	}
	
    if(!exit){
        window.stop();
    }
    else
	{
    this.opener.focus();
    this.close();
	}
	isShutClockOnce=true;
}

function shutFriendlyClock()
{
    doResult();
    giveBackFriendlyResult();		
    this.opener.focus();
    this.close();
}

function doResult()
{
    if ( penalties["right"][penaltyOrder[3]] || penalties["left"][penaltyOrder[3]] )
    {
        checkPenalties();
    }
    else
    {
        if ( (ipponList["left"] ["1"] && ipponList["left"]["2"] && ipponList["left"]["3"]) ||
                (ipponList["right"]["1"] && ipponList["right"] ["2"] && ipponList["right"]["3"] ) )
        {
            checkIppons();
        }
        else
        {
            checkPoints();
        }
    }
    checkFusenKikenResult();
}

function checkPenalties()
{
    if ( penalties["right"][penaltyOrder[3]] && penalties["left"][penaltyOrder[3]] )
    {
        retLeftPoints = 0;
        retRightPoints = 0;
        retWinner = -1;
        return;
    }
    if ( penalties["right"][penaltyOrder[3]] )
    {
        retLeftPoints = 14;
        retRightPoints = 0;
        retWinner = 0;
        return;
    }
    if ( penalties["left"][penaltyOrder[3]] )
    {
        retLeftPoints = 0;
        retRightPoints = 14;
        retWinner = 1;
        return;
    }
}

function checkIppons()
{
    if ( ipponList["left"] ["1"] && ipponList["left"]["3"] && ipponList["left"] ["2"] )
    {
        retLeftPoints = 14;
        retRightPoints = 0;
        retWinner = 0;
        return;
    }
    if ( ipponList["right"]["2"] && ipponList["right"] ["1"] && ipponList["right"]["3"] )
    {
        retLeftPoints = 0;
        retRightPoints = 14;
        retWinner = 1;
        return;
    }
}

function checkPoints()
{
    retLeftPoints = leftPoints;
    retRightPoints = rightPoints;
    if ( leftPoints > rightPoints )
    {
        retWinner = 0;
    }
    if ( leftPoints < rightPoints )
    {
        retWinner = 1;
    }

    if ( leftPoints == rightPoints )
    {
        var _red = 0, _blue = 0;

        if ( ipponList["left"] ["1"] > 0 )
        {
            _blue = _blue + 1;
        }
        if ( ipponList["left"] ["2"] > 0 )
        {
            _blue = _blue + 1;
        }
        if ( ipponList["left"] ["3"] > 0 )
        {
            _blue = _blue + 1;
        }

        if ( ipponList["right"] ["1"] > 0 )
        {
            _red = _red + 1;
        }
        if ( ipponList["right"] ["2"] > 0 )
        {
            _red = _red + 1;
        }
        if ( ipponList["right"] ["3"] > 0 )
        {
            _red = _red + 1;
        }

        if ( _red > _blue )
        {
            retWinner = 1;
        }
        if ( _red < _blue )
        {
            retWinner = 0;
        }

        if ( _red == _blue )
        {
            if ( ( ipponList["left"] ["1"] + ipponList["left"]  ["2"] + ipponList["left"]  ["3"] ) >
                    (ipponList["right"] ["1"] + ipponList["right"] ["2"] + ipponList["right"] ["3"]) )
            {
                retWinner = 0;
            }
            if ( ( ipponList["left"] ["1"] + ipponList["left"]  ["2"] + ipponList["left"]  ["3"] ) <
                    (ipponList["right"] ["1"] + ipponList["right"] ["2"] + ipponList["right"] ["3"]) )
            {
                retWinner = 1;
            }
            if ( ( ipponList["left"] ["1"] + ipponList["left"]  ["2"] + ipponList["left"]  ["3"] ) ==
                    (ipponList["right"] ["1"] + ipponList["right"] ["2"] + ipponList["right"] ["3"]) )
            {
                retWinner = -1;
            }
        }
    }
}
//========================================================================================
// clock controls
//========================================================================================
function switchClock( clockType )
{
	 // only show injury time when mainclock is stopped
	if( document.getElementById( clockType ).style.display == "none" && !isActiveClock['mainClock'])
	{
		document.getElementById( clockType ).style.display = "block";
		document.getElementById( clockType ).style.visibility ="visible";
	}
	else
	{	
		document.getElementById( clockType ).style.display = "none";
		document.getElementById( clockType ).style.visibility ="hidden";		
	}	
}

function toggleClock( side )
{  
        switchClock( side + "InjuryClock" );
}

function increaseClock( value, clockType )
{
    // römische Zahlen verwenden, wobei Y=60 ;-)
    var value_temp = value;
    if ( 10 == value )
    {
        value_temp = "X";
    }
    if ( 60 == value )
    {
        value_temp = "Y"
    }
    if ( 600 == value )
    {
        value_temp = "Z"
    }

    if ( clockType == "mainClock" )
    {
        user_input_log( "Mi" + value_temp + "_" );
    }
    if ( clockType == "leftHoldingClock" )
    {
        user_input_log( "Hi" + value_temp + "L" );
    }
    if ( clockType == "rightHoldingClock" )
    {
        user_input_log( "Hi" + value_temp + "R" );
    }
    if ( clockType == "leftInjuryClock" )
    {
        user_input_log( "Wi" + value_temp + "L" );
    }
    if ( clockType == "rightInjuryClock" )
    {
        user_input_log( "Wi" + value_temp + "R" );
    }
    if ( !isActiveClock[clockType] && (milliseconds[clockType] + (value * 1000)) < maxTimeinMilliseconds )
    {
        milliseconds[clockType] = milliseconds[clockType] + (value * 1000);
        updateClock( clockType );
    }
	        updateClock( clockType );
}

function decreaseClock( value, clockType )
{
    // römische Zahlen verwenden, wobei Y=60 ;-)
    var value_temp = value;
    if ( 10 == value )
    {
        value_temp = "X";
    }
    if ( 60 == value )
    {
        value_temp = "Y"
    }
    if ( 600 == value )
    {
        value_temp = "Z"
    }

    if ( clockType == "mainClock" && milliseconds[clockType] >= 0 )
    {
        user_input_log( "Md" + value_temp + "_" );
    }
    if ( clockType == "leftHoldingClock" && milliseconds[clockType] >= 0 )
    {
        user_input_log( "Hd" + value_temp + "L" );
    }
    if ( clockType == "rightHoldingClock" && milliseconds[clockType] >= 0 )
    {
        user_input_log( "Hd" + value_temp + "R" );
    }
    if ( clockType == "leftInjuryClock" && milliseconds[clockType] >= 0 )
    {
        user_input_log( "Wd" + value_temp + "L" );
    }
    if ( clockType == "rightInjuryClock" && milliseconds[clockType] >= 0 )
    {
        user_input_log( "Wd" + value_temp + "R" );
    }

    if ( !isActiveClock[clockType] && ((milliseconds[clockType] - value * 1000) >= 0 || clockType == "mainClock" ))
    {
        milliseconds[clockType] = milliseconds[clockType] - (value * 1000);
        updateClock( clockType );
    }
        updateClock( clockType );
    // if ((clockType == "mainClock") && (getWholeSeconds("mainClock") < fighting_time)) document.onkeyup = setOriginalBackgroundColor;
}

function getMinutes( clockType )
{
    if ( isActiveClock[clockType] )
    {
        return Math.floor( (new Date().getTime() - startTime[clockType] + milliseconds[clockType]) / 1000 / 60 );
    }
    else
    {
        return Math.floor( milliseconds[clockType] / 1000 / 60 );
    }
}

function getMinutes2( clockType, counterStart )
{
    var ret=0;
	if (clockType =="mainClock")
	{
		if ( isActiveClock[clockType] )
		{
			ret = Math.floor( (counterStart -(new Date().getTime() - startTime[clockType] + milliseconds[clockType]) / 1000) / 60 );
			if (Math.ceil( (counterStart -(new Date().getTime() - startTime[clockType] + milliseconds[clockType]) / 1000 )) % 60==0)
			ret++;
		}
		else
		{
			ret = Math.floor( (counterStart - milliseconds[clockType] / 1000) / 60 );
			if (Math.ceil( (counterStart - milliseconds[clockType] / 1000 )) % 60 == 0)
			ret++;
		}
	}
	else
	{
		if ( isActiveClock[clockType] )
		{
			ret = Math.floor( (counterStart -(new Date().getTime() - startTime[clockType] + milliseconds[clockType]) / 1000) / 60 );
		}
		else
		{
			ret = Math.floor( (counterStart - milliseconds[clockType] / 1000) / 60 );
		}

	}
    return (ret<0)? 0 : ret
}

function getSeconds( clockType )
{
    if ( isActiveClock[clockType] )
    {
        return Math.floor( (new Date().getTime() - startTime[clockType] + milliseconds[clockType]) / 1000 ) % 60;
    }
    else
    {
        return Math.floor( milliseconds[clockType] / 1000 ) % 60;
    }
}

function getSeconds2( clockType, counterStart )
{
    var ret=0;
	if (clockType =="mainClock")
	{
		if ( isActiveClock[clockType] )
		{
			ret= Math.ceil( (counterStart -(new Date().getTime() - startTime[clockType] + milliseconds[clockType]) / 1000 )) % 60;
		}
		else
		{
			ret= Math.ceil( (counterStart - milliseconds[clockType] / 1000 )) % 60;
		}
	}
	else
	{
		if ( isActiveClock[clockType] )
		{
			ret= Math.floor( (counterStart -(new Date().getTime() - startTime[clockType] + milliseconds[clockType]) / 1000 )) % 60;
		}
		else
		{
			ret= Math.floor( (counterStart - milliseconds[clockType] / 1000 )) % 60;
		}
	}
    return (ret<0)? 0 : ret
}

//neue Funktion zum ermitteln der GesamtZeit in Sekunden
// JBO 2008
function getWholeSeconds( clockType )
{
    if ( isActiveClock[clockType] )
    {
        return Math.floor( (new Date().getTime() - startTime[clockType] + milliseconds[clockType]) / 1000 );
    }
    else
    {
        return Math.floor( milliseconds[clockType] / 1000 );
    }
}

//clockType In (main, leftInjury, rightInjury, leftHolding, rightHolding)
function updateClock( clockType )
{
    var stopper = false;
    var zwischen = 0;
    window.clearTimeout( activeClock[clockType] );

    if(clockType == "mainClock"){   
	document.getElementById( clockType + '.minutes.1' ).src =
            "../images/clock/" + Math.floor( getMinutes2( clockType ,fighting_time ) / 10 ) +
                    ((clockType == "mainClock") ? "_large.png" : ".png");
        document.getElementById( clockType + '.minutes.2' ).src =
            "../images/clock/" + getMinutes2( clockType,fighting_time  ) % 10 + ((clockType == "mainClock") ? "_large.png" : ".png");
        document.getElementById( clockType + '.seconds.1' ).src =
            "../images/clock/" + Math.floor( getSeconds2( clockType,fighting_time  ) / 10 ) +
                    ((clockType == "mainClock") ? "_large.png" : ".png");
        document.getElementById( clockType + '.seconds.2' ).src =
            "../images/clock/" + getSeconds2( clockType,fighting_time  ) % 10 + ((clockType == "mainClock") ? "_large.png" : ".png");

    }
    else
    {
		if(clockType!="leftHoldingClock"  && clockType!="rightHoldingClock")
		{
			document.getElementById( clockType + '.minutes.1' ).src ="../images/clock/" + Math.floor( getMinutes( clockType ) / 10 ) +
						((clockType == "mainClock") ? "_large.png" : ".png");
			document.getElementById( clockType + '.minutes.2' ).src = "../images/clock/" + getMinutes( clockType ) % 10 + ((clockType == "mainClock") ? "_large.png" : ".png");
		}
		document.getElementById( clockType + '.seconds.1' ).src ="../images/clock/" + Math.floor( getSeconds( clockType ) / 10 ) +
                    ((clockType == "mainClock") ? "_large.png" : ".png");
		document.getElementById( clockType + '.seconds.2' ).src ="../images/clock/" + getSeconds( clockType ) % 10 + ((clockType == "mainClock") ? "_large.png" : ".png");

    }
    // Pruefung auf maximale Haltegriffzeit angegeben in osaekomi_time
    if ( (clockType != "mainClock") && (clockType != "leftInjuryClock") && (clockType != "rightInjuryClock") )
    {
        if ( getSeconds( clockType ) >= osaekomi_time )
        {
            stopper = true;
            startStopClock( clockType );
            alarmSound( '2' );
            if ( isActiveClock['mainClock'] )
            {
                startStopClock( 'mainClock' );
            } 	// wenn Haltegriffzeit auf maximum wird die Kampfzeit gestoppt
        }
    }
    // Ende der Pruefung auf maximale Haltegriffzeit

    // Pruefung auf maximale Verletzungszeit angegeben in osaekomi_time
    //
    if ( (clockType != "mainClock") && (clockType != "leftHoldingClock") && (clockType != "rightHoldingClock") )
    {
        if ( getWholeSeconds( clockType ) >= injury_time )
        {
            stopper = true;
            startStopClock( clockType );
            alarmSound( '2' );
            if ( isActiveClock['mainClock'] )
            {
                startStopClock( 'mainClock' );
            } 	// wenn Verletzungszeit auf maximum wird die Kampfzeit gestoppt
            setMaxTimeBackgroundColor();
        }
    }
    // Ende der Pruefung auf maximale Verletzungszeit

    // Pruefung auf Kampfzeit angegeben in fighting_time
    //
    if ( clockType == "mainClock" )
    {
        if ( (getWholeSeconds( "mainClock" ) >= fighting_time) && (!isActiveClock['leftHoldingClock']) &&
                (!isActiveClock['rightHoldingClock']) )    // wenn Haltegriffzeit läuft darf Kampfzeit nicht gestoppt werden
        {
            stopper = true;
            if ( isActiveClock['mainClock'] )
            {
                startStopClock( clockType );
            }
            setMaxTimeBackgroundColor();		// Rahmen gelb einfaerben
        }
    }
    // Ende Pruefung auf maximale Kampfzeit

    if ( isActiveClock[clockType] && !stopper )
    {
        activeClock[clockType] = window.setTimeout( ("updateClock('" + clockType + "')"), clockUpdateIntervall );
    }

}

function startStopClock( clockType )
{
    var tttime = getWholeSeconds( "mainClock" );
    if ( firstcall != true && clockType == "mainClock" )
    {
        milliseconds["fightTimeWithBreaksEnd"] = new Date().getTime();        
    }
    if ( firstcall == true && clockType == "mainClock" )
    {
        milliseconds["fightTimeWithBreaksStart"] = new Date().getTime();
       
        firstcall = false;
    }

    if (isActiveClock[clockType])
	{
		color = hiLightColorStop;
	} else
	{
		color = hiLightColor;
	}
    
    if ( isActiveClock[clockType] )
    {
        milliseconds[clockType] = new Date().getTime() - startTime[clockType] + milliseconds[clockType];
        window.clearTimeout( activeClock[clockType] );
        if ( (clockType == "mainClock") && (isActiveClock['leftHoldingClock']) )
        {
            startStopClock( 'leftHoldingClock' );
        } 			// Linke Haltegriffzeit stoppen wenn Kampfzeit gestoppt wird
        if ( (clockType == "mainClock") && (isActiveClock['rightHoldingClock']) )
        {
            startStopClock( 'rightHoldingClock' );
        }     	// Rechte Haltegriffzeit stoppen wenn Kampfzeit gestoppt wird
        isActiveClock[clockType] = !isActiveClock[clockType];
        if ( tttime >= fighting_time && clockType == "mainClock" )
        {
            setMaxTimeBackgroundColor();		// Rahmen gelb einfärben
        }

    }
    else
    {
        if ( proofHansokumake() )
        {
            return;
        }																						// Disqualifikation - keine Zeiten starten
        if ( proofFullIppon() )
        {
            return;
        }																						// FullIppon - keine Zeiten starten
        if ( proofFusenKiken() )
        {
            return;
        }																						// Fusengachi / Kikengachi - keine Zeit starten

        if ( ((clockType == "rightInjuryClock") || (clockType == "leftInjuryClock")) && (isActiveClock['mainClock']) )
        {
            return;
        }	// Verletzungszeit nur starten wenn HKZ NICHT läuft

        if ( (clockType == "mainClock") && (isActiveClock['leftInjuryClock']) )
        {
            startStopClock( 'leftInjuryClock' );
			toggleClock( 'left' );
        }			// Verletzungszeit links stoppen wenn HKZ gestartet wird
        if ( (clockType == "mainClock") && (isActiveClock['rightInjuryClock']) )
        {
            startStopClock( 'rightInjuryClock' );
			toggleClock( 'right' );
        }			// Verletzungszeit rechts stoppen wenn HKZ gestartet wird

        startTime[clockType] = new Date().getTime();
        activeClock[clockType] = window.setTimeout( ("updateClock('" + clockType + "')"), clockUpdateIntervall );
        if ( (!isActiveClock['mainClock']) && (clockType == "leftHoldingClock") )
        {
            startStopClock( 'mainClock' );
        }				// ggf. Kampfzeit starten, wenn linke Haltegriffzeit startet
        if ( (!isActiveClock['mainClock']) && (clockType == "rightHoldingClock") )
        {
            startStopClock( 'mainClock' );
        }				// ggf. Kampfzeit starten, wenn rechte Haltegriffzeit startet
        if ( (clockType == "leftHoldingClock") && (isActiveClock['rightHoldingClock']) )
        {
            startStopClock( 'rightHoldingClock' );
        }	// rechte Haltegriffzeit stoppen wenn linke gestartet wird
        if ( (clockType == "rightHoldingClock") && (isActiveClock['leftHoldingClock']) )
        {
            startStopClock( 'leftHoldingClock' );
        }  // linke Haltegriffzeit stoppen wenn rechte gestartet wird
        isActiveClock[clockType] = !isActiveClock[clockType];
    }

    document.getElementById( clockType + "Border" ).style.backgroundColor = color;
    user_input_log( "SS" + clockType ); // user_input_log

}

function resetClock( side )
{
    if ( side == "mainClock" )
    {
        if ( !isActiveClock["mainClock"] )
        {
            milliseconds["mainClock"] = fighting_over_time;
        }
        updateClock( "mainClock" );
    }
    else
    {
        if ( !isActiveClock[clockDisplayed [side]] &&
		    document.getElementById("rightInjuryClock").style.display == "none" && document.getElementById("leftInjuryClock").style.display == "none")
        {
            writeHoldingTime( "end", clockDisplayed [side] );																		// Kampfzeit zum Ende der Haltegriffzeit plus Haltedauer
            milliseconds[clockDisplayed [side]] = 0;
            updateClock( clockDisplayed [side] );
        }
    }
    user_input_log( "Cr_" + getSideShort( side ) ); // user_input_log
}

var clockDisplayed = new Array();
clockDisplayed ["left"] = "leftHoldingClock";
clockDisplayed ["right"] = "rightHoldingClock";

var activeClock = new Array();
activeClock["mainClock"] = 0;
activeClock["leftInjuryClock"] = 0;
activeClock["leftHoldingClock"] = 0;
activeClock["rightInjuryClock"] = 0;
activeClock["rightHoldingClock"] = 0;

var milliseconds = new Array();
milliseconds["mainClock"] = 0;
milliseconds["leftInjuryClock"] = 0;
milliseconds["leftHoldingClock"] = 0;
milliseconds["rightInjuryClock"] = 0;
milliseconds["rightHoldingClock"] = 0;
milliseconds["fightTimeWithBreaksStart"] = 0;				// JB neu
milliseconds["fightTimeWithBreaksEnd"] = 0;					// JB neu

var startTime = new Array();
startTime["mainClock"] = 0;
startTime["leftInjuryClock"] = 0;
startTime["leftHoldingClock"] = 0;
startTime["rightInjuryClock"] = 0;
startTime["rightHoldingClock"] = 0;

var isActiveClock = new Array();
isActiveClock["mainClock"] = false;
isActiveClock["leftInjuryClock"] = false;
isActiveClock["leftHoldingClock"] = false;
isActiveClock["rightInjuryClock"] = false;
isActiveClock["rightHoldingClock"] = false;

//========================================================================================
// Aufgabe durch Verletzung / Nichterscheinen
//========================================================================================

var isActiveFusen = new Array();
isActiveFusen["left"] = false;
isActiveFusen["right"] = false;

var isActiveKiken = new Array();
isActiveKiken["left"] = false;
isActiveKiken["right"] = false;

var blinkFusen = new Array();
blinkFusen["left"] = false;
blinkFusen["right"] = false;

var isActiveKikenWinner = false;

// Abfragefeld Gewinner nach Kikengachi einblenden
function activateKikenWinner()
{
    if ( isActiveKiken["left"] && isActiveKiken["right"] )
    {
        setKikenWinner( "none" );
    }
    if ( isActiveKiken["left"] && !isActiveKiken["right"] )
    {
        setKikenWinner( "right" );
    }
    if ( !isActiveKiken["left"] && isActiveKiken["right"] )
    {
        setKikenWinner( "left" );
    }
    if ( isActiveKikenWinner )
    {
        return;
    }
    document.getElementById( "div_KikenWinner" ).style.left=screen.width/2-150 + "px";
    document.getElementById( "div_KikenWinner" ).style.top=285+screenDiff-110 + "px";
    document.getElementById( "div_KikenWinner" ).style.display = "block";
    document.getElementById( "div_KikenWinner" ).style.visibility ="visible";
    isActiveKikenWinner = !isActiveKikenWinner;
}

// Abfragefeld Gewinner nach Kikengachi ausblenden
function deactivateKikenWinner()
{
    if ( !isActiveKikenWinner )
    {
        return;
    }
    isActiveKikenWinner = !isActiveKikenWinner;
    document.getElementById( "div_KikenWinner" ).style.display = "none";
    document.getElementById( "div_KikenWinner" ).style.visibility ="hidden";
}

function checkFusen( side )
{
    if ( isActiveFusen[side] )
    {
        return true;
    }
    return false;
}

function checkKiken( side )
{
    if ( isActiveKiken[side] )
    {
        return true;
    }
    return false;
}

function proofFusenKiken()
{
    if ( isActiveFusen["left"] || isActiveFusen["right"] || isActiveKiken["left"] || isActiveKiken["right"] )
    {
        return true;
    }
    return false;
}

function blinkFusenKiken( side )
{
    if ( blinkFusen[side] )
    {
        document.getElementById( "fusen_kiken_" + side ).src = "../images/clock/fusen_kiken_large.png";
    }
    else
    {
        document.getElementById( "fusen_kiken_" + side ).src = "../images/clock/fusen_kiken.png";
    }
    blinkFusen[side] = !blinkFusen[side];
}

function activateDeactivateFusenKiken( side )
{
    if ( (getMinutes( "mainClock" ) == 0) && (getSeconds( "mainClock" ) == 0) )
    {
        if ( isActiveFusen[side] )
        {
            if ( side == "left" )
            {
                window.clearInterval( blinkerFusenLeft );
            }
            else
            {
                window.clearInterval( blinkerFusenRight );
            }
            user_input = user_input + "IFD" + getSideShort( side ) + ":" + getWholeSeconds( 'mainClock' ) + ";";
            document.getElementById( "fusen_kiken_" + side ).src = "../images/clock/fusen_kiken.png";
            blinkFusen[side] = false;
        }
        else
        {
            if ( isActiveClock["mainClock"] )
            {
                return;
            }			// Aufgabe nur moeglich wenn HKZ nicht laeuft
            user_input = user_input + "IFA" + getSideShort( side ) + ":" + getWholeSeconds( 'mainClock' ) + ";";
            document.getElementById( "fusen_kiken_" + side ).src = "../images/clock/fusen_kiken_large.png";
            if ( side == "left" )
            {
                blinkerFusenLeft = window.setInterval( "blinkFusenKiken('left')", 1000 );
            }
            else
            {
                blinkerFusenRight = window.setInterval( "blinkFusenKiken('right')", 1000 );
            }
        }
        isActiveFusen[side] = !isActiveFusen[side];
    }
    else
    {
        if ( isActiveKiken[side] && !isActiveFusen[side] )
        {
            if ( side == "left" )
            {
                window.clearInterval( blinkerFusenLeft );
            }
            else
            {
                window.clearInterval( blinkerFusenRight );
            }
            user_input = user_input + "IKD" + getSideShort( side ) + ":" + getWholeSeconds( 'mainClock' ) + ";";
            document.getElementById( "fusen_kiken_" + side ).src = "../images/clock/fusen_kiken.png";
            blinkFusen[side] = false;
        }
        else
        {
            if ( isActiveClock["mainClock"] )
            {
                return;
            }			// Aufgabe nur moeglich wenn HKZ nicht laeuft
            user_input = user_input + "IKA" + getSideShort( side ) + ":" + getWholeSeconds( 'mainClock' ) + ";";
            document.getElementById( "fusen_kiken_" + side ).src = "../images/clock/fusen_kiken_large.png";
            if ( side == "left" )
            {
                blinkerFusenLeft = window.setInterval( "blinkFusenKiken('left')", 1000 );
            }
            else
            {
                blinkerFusenRight = window.setInterval( "blinkFusenKiken('right')", 1000 );
            }
        }
        isActiveKiken[side] = !isActiveKiken[side];
        if ( isActiveKiken["left"] || isActiveKiken["right"] )
        {
            activateKikenWinner();
        }
        else
        {
            deactivateKikenWinner();
        }
    }

    updatePoints( side );									// Punkte muessen aktualisiert werden, um Fusengachi / Kikengachi zu aktivieren / deaktivieren
}

//========================================================================================

//========================================================================================
// Point controls
//========================================================================================

var leftPoints = 0;
var rightPoints = 0;

function handlePoints( event, value, side )
{
    if ( event.button == 2 )
    {
        decreasePoints( value, side, true );
    }
    else
    {
        increasePoints( value, side, true );
    }
}

function getPoints( side )
{
    if ( side == "left" )
    {
        return leftPoints;
    }
    else
    {
        return rightPoints;
    }
}

function increasePoints( value, side, isLog )
{
    if ( (proofFullIppon()) && (!passthrough) )
    {
        return;
    }			// FullIppon   - keine Punkte mehr dazu
    if ( (proofHansokumake()) && (!passthrough) )
    {
        return;
    }			// Hansokumake - keine Punkte mehr dazu

    if ( side == "left" )
    {
        if ( leftPoints + parseInt( value ) < 999 )
        {
            leftPoints = (leftPoints + parseInt( value ));
        }
    }
    else
    {
        if ( rightPoints + parseInt( value ) < 999 )
        {
            rightPoints = (rightPoints + parseInt( value ));
        }
    }
    //JBO: mögliche Werte sind 1-9 und 10, 100
    // 10 und 100 werden römisch dargestellt, damit die LogBefehlsbreite von 3 Zeichen nicht überschritten wird
    if ( isLog == true )
    {
        if ( 100 == value )
        {
            user_input_log( "Pi" + "C" + getSideShort( side ) );
        }
        else
        {
            if ( 10 == value )
            {
                user_input_log( "Pi" + "X" + getSideShort( side ) );
            }
            else
            {
                user_input_log( "Pi" + value + getSideShort( side ) );
            }
        }
    }
    updatePoints( side );
}

function decreasePoints( value, side, isLog )
{
    var isLog2 = true;
    if ( (proofFullIppon()) && (!passthrough) )
    {
        return;
    }			// FullIppon   - keine Punkte mehr dazu
    if ( (proofHansokumake()) && (!passthrough) )
    {
        return;
    }			// Hansokumake - keine Punkte mehr dazu

    if ( side == "left" )
    {
        if ( leftPoints == 0 )
        {
            isLog2 = false;
        }  // wenn Punkte bei null stehen, dann kein descrease loggen
        leftPoints -= value;
        if ( leftPoints < 0 )
        {
            leftPoints = 0;
        }
    }
    else
    {
        if ( rightPoints == 0 )
        {
            isLog2 = false;
        }  // wenn Punkte bei null stehen, dann kein descrease loggen
        rightPoints -= value;
        if ( rightPoints < 0 )
        {
            rightPoints = 0;
        }
    }
    //JBO: mögliche Werte sind 1-9 und 10, 100
    // 10 und 100 werden römisch dargestellt, damit die LogBefehlsbreite von 3 Zeichen nicht überschritten wird
    if ( isLog == true && isLog2 == true )
    {

        if ( 100 == value )
        {
            user_input_log( "Pd" + "C" + getSideShort( side ) );
        }
        else
        {
            if ( 10 == value )
            {
                user_input_log( "Pd" + "X" + getSideShort( side ) );
            }
            else
            {
                user_input_log( "Pd" + value + getSideShort( side ) );
            }
        }
    }
    updatePoints( side );
}

function updatePoints( side )
{
    // Wenn FusenKiken dann muss die Anzeige auf + gesetzt werden
    document.getElementById( side + "PointsBorderTable" ).style.backgroundColor = hiLightColor;
    side = "left";
	
    if(getPoints( side ) < 10){
      document.getElementById( side + "Points.1" ).src =
            "../images/clock/bl_x_large.png";
    }
    else
    {
      document.getElementById( side + "Points.1" ).src =
            "../images/clock/" + Math.floor( (getPoints( side ) % 100) / 10 ) + "_x_large.png";
    }
    document.getElementById( side + "Points.0" ).src =
            "../images/clock/" + ((getPoints( side ) % 100) % 10) + "_x_large.png";
    
    side = "right";
    if(getPoints( side ) < 10){
      document.getElementById( side + "Points.1" ).src =
            "../images/clock/bl_x_large.png";
    }
    else
    {
      document.getElementById( side + "Points.1" ).src =
            "../images/clock/" + Math.floor( (getPoints( side ) % 100) / 10 ) + "_x_large.png";
    }
    document.getElementById( side + "Points.0" ).src =
            "../images/clock/" + ((getPoints( side ) % 100) % 10) + "_x_large.png";

    if ( proofFusenKiken() )
    {
        if ( checkFusen( "left" ) || checkKiken( "left" ) )
        {           
            if ( isActiveFusen["left"] )
            {
                document.getElementById( "leftPoints.1" ).src = "../images/clock/F_x_large.png";
            }
            else
            {
                document.getElementById( "leftPoints.1" ).src = "../images/clock/injury_x_large.png";
            }
            document.getElementById( "leftPoints.0" ).src = "../images/clock/bl_x_large.png";
        }
        if ( checkFusen( "right" ) || checkKiken( "right" ) )
        {
            if ( isActiveFusen["right"] )
            {
                document.getElementById( "rightPoints.1" ).src = "../images/clock/F_x_large.png";
            }
            else
            {
                document.getElementById( "rightPoints.1" ).src = "../images/clock/injury_x_large.png";
            }
            document.getElementById( "rightPoints.0" ).src = "../images/clock/bl_x_large.png";
        }
    }

    // Wenn Hansokumake dann muss die Anzeige auf H gesetzt werden
    if ( proofHansokumake() )
    {
        if ( checkHansokumake( "left" ) )
        {
            document.getElementById( "leftPoints.1" ).src = "../images/clock/hanso_x_large.png";
            document.getElementById( "leftPoints.0" ).src = "../images/clock/bl_x_large.png";
            if ( !checkHansokumake( "right" ) )
            {
                document.getElementById( "rightPoints.1" ).src = "../images/clock/1_x_large.png";
                document.getElementById( "rightPoints.0" ).src = "../images/clock/4_x_large.png";
            }
        }
        if ( checkHansokumake( "right" ) )
        {
            document.getElementById( "rightPoints.1" ).src = "../images/clock/hanso_x_large.png";
            document.getElementById( "rightPoints.0" ).src = "../images/clock/bl_x_large.png";
            if ( !checkHansokumake( "left" ) )
            {
                document.getElementById( "leftPoints.1" ).src = "../images/clock/1_x_large.png";
                document.getElementById( "leftPoints.0" ).src = "../images/clock/4_x_large.png";
            }
        }
        return
    }

    // Wenn FullIppon dann muss die Anzeige auf 14:0 gesetzt werden
    if ( proofFullIppon() )
    {
        if ( checkFullIppon( "left" ) )
        {
            document.getElementById( "leftPoints.1" ).src = "../images/clock/1_x_large.png";
            document.getElementById( "leftPoints.0" ).src = "../images/clock/4_x_large.png";

            document.getElementById( "rightPoints.1" ).src = "../images/clock/0_x_large.png";
            document.getElementById( "rightPoints.0" ).src = "../images/clock/0_x_large.png";
        }
        else
        {
            document.getElementById( "rightPoints.1" ).src = "../images/clock/1_x_large.png";
            document.getElementById( "rightPoints.0" ).src = "../images/clock/4_x_large.png";

            document.getElementById( "leftPoints.1" ).src = "../images/clock/0_x_large.png";
            document.getElementById( "leftPoints.0" ).src = "../images/clock/0_x_large.png";
        }
        return;
    }
}

//========================================================================================

//========================================================================================
// Ippon functions
//========================================================================================

var ipponList = new Array();
ipponList["left"] = new Array();
ipponList["right"] = new Array();
ipponList["left"]  ["1"] = 0;
ipponList["right"] ["1"] = 0;
ipponList["left"]  ["2"] = 0;
ipponList["right"] ["2"] = 0;
ipponList["left"]  ["3"] = 0;
ipponList["right"] ["3"] = 0;

var ippons = new Array();
ippons["left"] = new Array();
ippons["right"] = new Array();
ippons["left"] ["1"] = false;
ippons["right"]["1"] = false;
ippons["left"] ["2"] = false;
ippons["right"]["2"] = false;
ippons["left"] ["3"] = false;
ippons["right"]["3"] = false;

function handleIppon( event, side, ippon )
{
    if ( event.button == 2 )
    {
        decreaseIppon( side, ippon );
    }
    else
    {
        increaseIppon( side, ippon );
    }
}

function setIppon( side, ippon )
{
    ippons[side][ippon ] = !ippons[side][ippon];
}

function checkFullIppon( side )
{
    if ( (ipponList[side]["1"]) && (ipponList[side]["2"]) && (ipponList[side]["3"]) )
    {
        return true;
    }
    return false;
}

function proofFullIppon()
    // prueft beide seiten auf full ippon und setzt das Blinken in Gang
    // benoetigt function checkFullIppon(side)
{
    var proof = false;
    if ( checkFullIppon( "left" ) )
    {        
        proof = true;
    }
    if ( checkFullIppon( "right" ) )
    {       
        proof = true;
    }
    if ( proof )
    {
        if ( isActiveClock["mainClock"] )
        {
            startStopClock( "mainClock" );
        }	// HKZ stoppen wenn fullIppon
        return true;
    }
    return false;
}

function increaseIppon( side, ippon )
{
    if ( (proofFullIppon()) && (!passthrough) )
    {
        return;
    }		// FullIppon   - keine Ippon mehr dazu
    if ( proofHansokumake() )
    {
        return;
    }						// Hansokumake - keine Ippons mehr dazu

    ipponList[side][ippon]++;
    
    changeImage( side + "Ippon" + ippon + "ImageFlag", ipponList[side][ippon] % 10 + "_ippon_gr.png" );

    if ( ipponList[side][ippon] < 10 )
    {
	if ( ipponList[side][ippon] > 0 ) 
	{
		changeImage( side + "Ippon" + ippon + ippon + "ImageFlag", "bl_ippon_gr.png" );
	}
	else
	{
        	changeImage( side + "Ippon" + ippon + ippon + "ImageFlag", "bl_ippon.png" );
	}

    }
    else
    {
        changeImage( side + "Ippon" + ippon + ippon + "ImageFlag",  Math.floor((ipponList[side][ippon] % 100)/10) + "_ippon_gr.png" );
    }

    passthrough = true;					// passthrough auf true setzen um Punkte addieren zu koennen
    user_input_log( "Ii" + ippon + getSideShort( side ) );			// User Input Log
    increasePoints( '2', side, false );  		// add Points
    passthrough = false;				// passthrough auf false setzen, dass pruefung stattfinden kann.
    proofFullIppon();					// erneut auf fullIppon pruefen und ggf. blinker aktivieren.


  	// ipponbackground workaround 04.05.2013
    if ( ipponList["right"]["1"] > 0 ) document.getElementById("rightIppon1TDFlag").style.backgroundColor="00ff00";
    if ( ipponList["right"]["2"] > 0 ) document.getElementById("rightIppon2TDFlag").style.backgroundColor="00ff00";
    if ( ipponList["right"]["3"] > 0 ) document.getElementById("rightIppon3TDFlag").style.backgroundColor="00ff00";

    if ( ipponList["left"]["1"] > 0 ) document.getElementById("leftIppon1TDFlag").style.backgroundColor="00ff00";
    if ( ipponList["left"]["2"] > 0 ) document.getElementById("leftIppon2TDFlag").style.backgroundColor="00ff00";
    if ( ipponList["left"]["3"] > 0 ) document.getElementById("leftIppon3TDFlag").style.backgroundColor="00ff00";

}

function decreaseIppon( side, ippon )
{
    if ( proofHansokumake() )
    {
        return;
    }		// Hansokumake - keine Ippons mehr dazu

    ipponList[side][ippon]--;
    if ( ipponList[side][ippon] < 0 )
    {
        ipponList[side][ippon] = 0;
    }
    else
    {
        // changeImage(side + "Ippon" + ippon + "ImageFlag", "ippon_active"+ ipponList[side][ippon]+".png");
        // alle auflisten wegen der aktualisierung wenn fullippon rausgenommen wird

         changeImage( side + "Ippon" + ippon + "ImageFlag", ipponList[side][ippon] % 10 + "_ippon_gr.png" );
	if (ipponList[side][ippon] == 0) changeImage( side + "Ippon" + ippon + "ImageFlag", ipponList[side][ippon] % 10 + "_ippon.png" );

    if ( ipponList[side][ippon] < 10 )
    {
        if ( ipponList[side][ippon] > 0 ) 
	{
		changeImage( side + "Ippon" + ippon + ippon + "ImageFlag", "bl_ippon_gr.png" );
	}
	else
	{
		changeImage( side + "Ippon" + ippon + ippon + "ImageFlag", "bl_ippon.png" );
	}
    }
    else
    {
        changeImage( side + "Ippon" + ippon + ippon + "ImageFlag",  Math.floor((ipponList[side][ippon] % 100)/10) + "_ippon_gr.png" );
    }

        passthrough = true;					// passthrough auf true setzen um Punkte addieren zu koennen
        user_input_log( "Id" + ippon + getSideShort( side ) );
        decreasePoints( '2', side, false );
        passthrough = false;				// passthrough auf false setzen, dass pruefung stattfinden kann.
    }

  	// ipponbackground workaround 04.05.2013
    if ( ipponList["right"]["1"] == 0 ) document.getElementById("rightIppon1TDFlag").style.backgroundColor="000000";
    if ( ipponList["right"]["2"] == 0 ) document.getElementById("rightIppon2TDFlag").style.backgroundColor="000000";
    if ( ipponList["right"]["3"] == 0 ) document.getElementById("rightIppon3TDFlag").style.backgroundColor="000000";

    if ( ipponList["left"]["1"] == 0 ) document.getElementById("leftIppon1TDFlag").style.backgroundColor="000000";
    if ( ipponList["left"]["2"] == 0 ) document.getElementById("leftIppon2TDFlag").style.backgroundColor="000000";
    if ( ipponList["left"]["3"] == 0 ) document.getElementById("leftIppon3TDFlag").style.backgroundColor="000000";
}

//========================================================================================

//========================================================================================
// Pentaly functions
//========================================================================================

var penaltyOrder = new Array();
penaltyOrder[0] = "Shido";
penaltyOrder[1] = "Chui";
penaltyOrder[2] = "Keikoku";
penaltyOrder[3] = "Hansokumake";

var penalties = new Array();
penalties["left"] = new Array();
penalties["right"] = new Array();
penalties["right"][penaltyOrder[0] ] = false;
penalties["left"][penaltyOrder[0] ] = false;
penalties["right"][penaltyOrder[1] ] = false;
penalties["left"][penaltyOrder[1] ] = false;
penalties["right"][penaltyOrder[2] ] = false;
penalties["left"][penaltyOrder[2] ] = false;
penalties["right"][penaltyOrder[3] ] = false;
penalties["left"][penaltyOrder[3] ] = false;

var penaltyList = new Array();
penaltyList["left"] = new Array();
penaltyList["right"] = new Array();
penaltyList["left"]  ["shido"] = 0;
penaltyList["right"] ["shido"] = 0;
penaltyList["left"]  ["chui"] = 0;
penaltyList["right"] ["chui"] = 0;

function checkHansokumake( side )
{
    if ( penalties[side][penaltyOrder[3] ] )
    {
        return true;
    }
    return false;
}

function proofHansokumake()
{
    // Liefert TRUE wenn einer oder beide Kämpfer disqualifiziert wurden
    if ( (penalties["left"][penaltyOrder[3] ]) || (penalties["right"][penaltyOrder[3] ]) )
    {
        return true;
    }
    return false;
}

function handleShido( event, side )
{
    if ( event.button == 2 )
    {
        decreaseShido( side );
    }
    else
    {
        increaseShido( side );
    }
}

function increaseShido( side )
{
    if ( checkHansokumake( side ) )
    {
        return;
    }
    passthrough = true;
    penaltyList[side]["shido"]++;
   
    user_input_log( "PSi" + getSideShort( side ) );			// User Input Log

    changeImage(side + "ShidoImageFlag", penaltyList[side]["shido"] % 10 + "_ippon.png" );

    if ( penaltyList[side]["shido"] < 10 )
    {
        changeImage( side + "Shido2ImageFlag", "bl_ippon.png" );
    }
    else
    {
        changeImage(side + "Shido2ImageFlag",  Math.floor((penaltyList[side]["shido"] % 100)/10) + "_ippon.png" );
    }

    if ( "left" == side )
    {
        increasePoints( '1', "right", false );
    }              // add Points to opponent
    else
    {
        if ( "right" == side )
        {
            increasePoints( '1', "left", false );
        }
    }  		// add Points to opponent
    passthrough = false;
}

function decreaseShido( side )
{
    if ( checkHansokumake( side ) )
    {
        return;
    }
    passthrough = true;
    penaltyList[side]["shido"]--;
    if ( penaltyList[side]["shido"] < 0 )
    {
        penaltyList[side]["shido"] = 0;
    }
    else
    {
        user_input_log( "PSd" + getSideShort( side ) );			// User Input Log
       changeImage(side + "ShidoImageFlag", penaltyList[side]["shido"] % 10 + "_ippon.png" );

    if ( penaltyList[side]["shido"] < 10 )
    {
        changeImage( side + "Shido2ImageFlag", "bl_ippon.png" );
    }
    else
    {
        changeImage(side + "Shido2ImageFlag",  Math.floor((penaltyList[side]["shido"] % 100)/10) + "_ippon.png" );
    }
        if ( "left" == side )
        {
            decreasePoints( '1', "right", false );
        }  // decreasePoints  to opponent
        else
        {
            if ( "right" == side )
            {
                decreasePoints( '1', "left", false );
            }
        }  // decreasePoints  to opponent
    }
    passthrough = false;
}

function handleChui( event, side )
{
    if ( event.button == 2 )
    {
        decreaseChui( side );
    }
    else
    {
        increaseChui( side );
    }
}

function increaseChui( side )
{
    if ( checkHansokumake( side ) )
    {
        return;
    }
    passthrough = true;
    penaltyList[side]["chui"]++;
    user_input_log( "PCi" + getSideShort( side ) );			// User Input Log
    if ( penaltyList[side]["chui"] > 1 )
    {
        penaltyList[side]["chui"] = 2;
        increaseHansokumake( side );
    }
    changeImage( side + "ChuiImageFlag",  penaltyList[side]["chui"] + "_ippon.png" );

    if ( "left" == side )
    {
        increasePoints( '2', "right", false );
    }  // add Points to opponent
    else
    {
        if ( "right" == side )
        {
            increasePoints( '2', "left", false );
        }
    }  // add Points to opponent
    passthrough = false;
}

function decreaseChui( side )
{
    if ( checkHansokumake( side ) && penaltyList[side]["chui"] != 2 )
    {
        return;
    }
    passthrough = true;
    penaltyList[side]["chui"]--;

    if ( penaltyList[side]["chui"] < 0 )
    {
        penaltyList[side]["chui"] = 0;
    }
    else
    {
        user_input_log( "PCd" + getSideShort( side ) );			// User Input Log
        changeImage( side + "ChuiImageFlag",  penaltyList[side]["chui"] + "_ippon.png" );
        decreaseHansokumake( side );
        if ( "left" == side )
        {
            decreasePoints( '2', "right", false );
        }  // decreasePoints  to opponent
        else
        {
            if ( "right" == side )
            {
                decreasePoints( '2', "left", false );
            }
        }  // decreasePoints  to opponent
    }
    passthrough = false;
}

function handleHansokumake( event, side )
{
    if ( event.button == 2 )
    {
        decreaseHansokumake( side );
    }
    else
    {
        increaseHansokumake( side );
    }
}

function increaseHansokumake( side )
{
    penalties[side][penaltyOrder[3] ] = true;
    changeImage( side + penaltyOrder[3] + "ImageFlag",  "1_ippon.png" );
    if ( (proofHansokumake) && (isActiveClock['mainClock']) )
    {
        startStopClock( "mainClock" );
    }
    updatePoints( side )				// muss aufgerufen werden, um Hansokumake anzueigen
    user_input_log( "PHi" + getSideShort( side ) );			// User Input Log
}

function decreaseHansokumake( side )
{
    if ( penaltyList[side]["chui"] == 2 )
    {
        decreaseChui( side );
        return;
    }
    if ( penalties[side][penaltyOrder[3]] )
    {
        if ( penalties[side][penaltyOrder[3] ] )
        {
            user_input_log( "PHd" + getSideShort( side ) );
        }			// User Input Log
        penalties[side][penaltyOrder[3] ] = false;        
        changeImage( side + penaltyOrder[3] + "ImageFlag",  "0_ippon.png" );
        updatePoints( side );			// muss aufgerufen werden, um Punkte wieder anzuzeigen
    }
}

//========================================================================================

//========================================================================================
// LastHoldings Anzeige
//========================================================================================

info = null;
var textLeftHolding = "<b>Haltegriffzeiten</b><br/>";
var leftHoldingCounter = 0;
var leftHolding = new Array();
leftHolding[0] = "<b>Haltegriffzeiten</b><br/>";
leftHolding[1] = "";
leftHolding[2] = "";
leftHolding[3] = "";
leftHolding[4] = "";
leftHolding[5] = "";
leftHolding[6] = "";
leftHolding[7] = "";
leftHolding[8] = "";
leftHolding[9] = "";
leftHolding[10] = "";

var textRightHolding = "<b>Haltegriffzeiten</b><br/>";
var rightHoldingCounter = 0;
var rightHolding = new Array();
rightHolding[0] = "<b>Haltegriffzeiten</b><br/>";
rightHolding[1] = "";
rightHolding[2] = "";
rightHolding[3] = "";
rightHolding[4] = "";
rightHolding[5] = "";
rightHolding[6] = "";
rightHolding[7] = "";
rightHolding[8] = "";
rightHolding[9] = "";
rightHolding[10] = "";

var textLeftInjury = "<b>Verletzungszeiten</b><br/>";
var textRightInjury = "<b>Verletzungszeiten</b><br/>";

function writeHoldingTime( timeType, clockType )
{
    var writeTextLeft_tmp = "";
    var writeTextRight_tmp = "";

    if ( timeType == "end" )
    {
        if ( clockType ==
                "leftHoldingClock" )                                                                                     // Endzeit der linken Haltegriffzeit eintragen
        {
            textLeftHolding = "";
            leftHoldingCounter += 1;
            writeTextLeft_tmp += getMinutes( 'mainClock' ) + ":";
            if ( getSeconds( 'mainClock' ) < 10 )
            {
                writeTextLeft_tmp += "0" + getSeconds( 'mainClock' ) + "-->" +
                        Math.floor( (getSeconds( 'leftHoldingClock' ) / 10) ) +
                        Math.floor( (getSeconds( 'leftHoldingClock' ) % 10) ) + "<br/>";
            }
            else
            {
                writeTextLeft_tmp +=
                        getSeconds( 'mainClock' ) + "-->" + Math.floor( (getSeconds( 'leftHoldingClock' ) / 10) ) +
                                Math.floor( (getSeconds( 'leftHoldingClock' ) % 10) ) + "<br/>";
            }

            if ( leftHoldingCounter >= 11 )
            {
                for ( var i = 1; i <= 9; i++ )
                {
                    leftHolding[i] = leftHolding[i + 1];
                }
                leftHolding[10] = writeTextLeft_tmp;
            }
            else
            {
                leftHolding[leftHoldingCounter] = writeTextLeft_tmp;
            }
            for ( var j = 0; j <= 10; j++ )
            {
                textLeftHolding = textLeftHolding + leftHolding[j];
            }
        }
        if ( clockType ==
                "rightHoldingClock" )                                                                                     // Endzeit der rechten Haltegriffzeit eintragen
        {
            textRightHolding = "";
            rightHoldingCounter += 1;
            writeTextRight_tmp += getMinutes( 'mainClock' ) + ":";
            if ( getSeconds( 'mainClock' ) < 10 )
            {
                writeTextRight_tmp += "0" + getSeconds( 'mainClock' ) + "-->" +
                        Math.floor( (getSeconds( 'rightHoldingClock' ) / 10) ) +
                        Math.floor( (getSeconds( 'rightHoldingClock' ) % 10) ) + "<br/>";
            }
            else
            {
                writeTextRight_tmp +=
                        getSeconds( 'mainClock' ) + "-->" + Math.floor( (getSeconds( 'rightHoldingClock' ) / 10) ) +
                                Math.floor( (getSeconds( 'rightHoldingClock' ) % 10) ) + "<br/>";
            }

            if ( rightHoldingCounter >= 11 )
            {
                for ( var i = 1; i <= 9; i++ )
                {
                    rightHolding[i] = rightHolding[i + 1];
                }
                rightHolding[10] = writeTextRight_tmp;
            }
            else
            {
                rightHolding[rightHoldingCounter] = writeTextRight_tmp;
            }
            for ( var j = 0; j <= 10; j++ )
            {
                textRightHolding += rightHolding[j];
            }
        }
    }
}

function updateInfo( event )
{
    x = (document.all) ? window.event.x + document.body.scrollLeft : event.pageX;
    y = (document.all) ? window.event.y + document.body.scrollTop : event.pageY;
    if ( info != null )
    {
        info.style.left = (x + 20) + "px";
        info.style.top = (y - 60) + "px";
    }
}

function showInfo( id )
{
    if ( !isShiftKey )
    {
        return;
    }
    info = document.getElementById( id );
    info.style.display = "block"
    if ( id == "leftHolding" )
    {
        info.innerHTML = textLeftHolding;
    }
    if ( id == "rightHolding" )
    {
        info.innerHTML = textRightHolding;
    }
    if ( id == "leftInjury" )
    {
        info.innerHTML = textLeftInjury;
    }
    if ( id == "rightInjury" )
    {
        info.innerHTML = textRightInjury;
    }

}

function hideInfo()
{
    if ( !info )
    {
        return;
    }
    info.style.display = "none";
}

//========================================================================================
// CookieSetup fuer Verletzungszeit, Haltegriffzeit, Kampfzeit -
//========================================================================================

function readCookie( name )
{
    var nameEQ = name + "=";
    var ca = document.cookie.split( ';' );
    for ( var i = 0; i < ca.length; i++ )
    {
        var c = ca[i];
        while ( c.charAt( 0 ) == ' ' )
        {
            c = c.substring( 1, c.length );
        }
        if ( c.indexOf( nameEQ ) == 0 )
        {
            return c.substring( nameEQ.length, c.length );
        }
    }
    return false;
}

function writeCookie( name, value )
{
    var cookietext = "";
    cookietext = name + "=" + value;
    document.cookie = cookietext;
}

function resetCookie()            // Schaut ob Angaben zu Kampf, Halte und verletzungszeit gespeichert sind und ergänzt ggf.
{
    if ( !readCookie( "fightingtime" ) )
    {
        writeCookie( "fightingtime", String( fighting_time ) );
    }
    if ( !readCookie( "injurytime" ) )
    {
        writeCookie( "injurytime", String( injury_time ) );
    }
    if ( !readCookie( "holdingtime" ) )
    {
        writeCookie( "holdingtime", String( osaekomi_time ) );
    }
    if ( !readCookie( "fightingOverTime" ) )
    {
        writeCookie( "fightingOverTime", String( fighting_over_time ) );
    }

    fighting_time = Number( readCookie( "fightingtime" ) );
    injury_time = Number( readCookie( "injurytime" ) );
    osaekomi_time = Number( readCookie( "holdingtime" ) );
    fighting_over_time = Number( readCookie( "fightingOverTime" ) );

    fighting_time_tmp = fighting_time;
    injury_time_tmp = injury_time;
    osaekomi_time_tmp = osaekomi_time;
    fighting_over_time_tmp = fighting_over_time;
}

//========================================================================================
// Anzeigenwechsel Aufruf - 
//========================================================================================
var isActiveAnzeige = false;

function handleAnzeige( displayForm )
{
    //start video
	if (videoOn== true){
		
		try{
			jjw_startRecording();
		}
		catch (e){}
	}
		
	
	// if (isActiveAnzeige)
    user_input = user_input + "DP" + displayForm + "_;";
    if ( (displayForm == "1" || displayForm == "2") )
    {
        if ( isActiveClock["mainClock"] )
        {
            return;
        }
        // Umbau wegen Preparation Display 2009 by SD
        if ( displayForm == "1" )
        {
            document.getElementById( "loadfield" ).style.visibility = "visible";
            document.getElementById( "loadfield" ).style.display = "block";
            document.getElementById( "preparationfield" ).style.visibility = "hidden";
            document.getElementById( "preparationfield" ).style.display = "none";
        }
        if ( displayForm == "2" )
        {
            document.getElementById( "preparationfield" ).style.visibility = "visible";
            document.getElementById( "preparationfield" ).style.display = "block";
            document.getElementById( "loadfield" ).style.visibility = "hidden";
            document.getElementById( "loadfield" ).style.display = "none";
        }
        //
        document.getElementById( "mainbody" ).style.visibility = "hidden";
        document.getElementById( "mainbody" ).style.display = "none";
        document.getElementById( "time_frame" ).style.right = "8";
        document.getElementById( "time_frame" ).style.left = "";
        document.getElementById( "time_frame" ).style.top = "167";

        isActiveAnzeige = false;
    }
    else
    {
        if ( !isActiveAnzeige )
        {
            document.getElementById( "loadfield" ).style.visibility = "hidden";
            document.getElementById( "loadfield" ).style.display = "none";
            document.getElementById( "mainbody" ).style.visibility = "visible";
            document.getElementById( "mainbody" ).style.display = "block";
        }
        if ( isActiveAnzeige )
        {
            document.getElementById( "loadfield" ).style.visibility = "visible";
            document.getElementById( "loadfield" ).style.display = "block";
            document.getElementById( "mainbody" ).style.visibility = "hidden";
            document.getElementById( "mainbody" ).style.display = "none";
        }
        // Preparation Display 2009 by SD
        document.getElementById( "preparationfield" ).style.visibility = "hidden";
        document.getElementById( "preparationfield" ).style.display = "none";
        //
        document.getElementById( "time_frame" ).style.left = "433";
        document.getElementById( "time_frame" ).style.top = "114";
        isActiveAnzeige = !isActiveAnzeige;
    }
}

//========================================================================================
// Key Events
//========================================================================================
var isShiftKey = false;

function myKeyDownFunction( event )
{
    if ( event.shiftKey )
    {
        isShiftKey = true;
    }
    if ( event.keyCode == 32 && !event.shiftKey )
    {
        startStopClock( "mainClock" );
    } 				//leer			HKZ starten/stoppen
    if ( event.keyCode == 32 && event.shiftKey && event.ctrlKey )
    {
        resetClock( "mainClock" );
    } 		//SHIFT + CTRL + leer   HKZ resetten
    if ( event.keyCode == 70 && !event.shiftKey )
    {
        startStopClock( clockDisplayed ["left"] );
    }  	// f	linke Uhr starten/stoppen
    if ( event.keyCode == 74 && !event.shiftKey )
    {
        startStopClock( clockDisplayed ["right"] );
    }  	// j	rechte Uhr starten/stoppen

    if ( event.keyCode == 70 && event.shiftKey )
    {
        resetClock( "left" );
    }  							// SHIFT + f	linke Uhr resetten
    if ( event.keyCode == 74 && event.shiftKey )
    {
        resetClock( "right" );
    }  							// SHIFT + j	rechte Uhr resetten

    if ( event.keyCode == 86 && event.shiftKey )
    {
        activateDeactivateFusenKiken( "left" );
    }			// SHIFT + v	Fusengachi / Kikengachi BLAU
    if ( event.keyCode == 78 && event.shiftKey )
    {
        activateDeactivateFusenKiken( "right" );
    }			// SHIFT + n	Fusengachi / Kikengachi ROT

    if ( event.keyCode == 71 )
    {
        toggleClock( "left" );
    }  											// g			linke Uhr umschalen
    if ( event.keyCode == 72 )
    {
        toggleClock( "right" );
    }  											// h			rechte Uhr umschalten

    if ( event.keyCode == 81 && !event.shiftKey )
    {
        increasePoints( 1, "left", true );
    }  					// q			addiere Punkte fuer Rot
    if ( event.keyCode == 81 && event.shiftKey )
    {
        decreasePoints( 1, "left", true );
    }  					// SHIFT + q		subtrahiere Punkte fuer Rot

    if ( event.keyCode == 80 && !event.shiftKey )
    {
        increasePoints( 1, "right", true );
    }  					// p			addiere Punkte fuer Blau
    if ( event.keyCode == 80 && event.shiftKey )
    {
        decreasePoints( 1, "right", true );
    }  					// SHIFT + p		subtrahiere Punkte fuer Blau

    //ippons and penalties LEFT
    if ( event.keyCode == 87 && !event.shiftKey )
    {
        increaseIppon( "left", "3" );
    }  				// w			Ippon Part 3 fuer Blau
    if ( event.keyCode == 87 && event.shiftKey )
    {
        decreaseIppon( "left", "3" );
    }  				// SHIFT + w		Ippon Part 3 zurueck
    if ( event.keyCode == 69 && !event.shiftKey )
    {
        increaseIppon( "left", "2" );
    }   				// e			Ippon Part 2 fuer Blau
    if ( event.keyCode == 69 && event.shiftKey )
    {
        decreaseIppon( "left", "2" );
    }   				// SHIFT + e		Ippon Part 2 zurueck
    if ( event.keyCode == 82 && !event.shiftKey )
    {
        increaseIppon( "left", "1" );
    }   				// r			Ippon Part 1 fuer Blau
    if ( event.keyCode == 82 && event.shiftKey )
    {
        decreaseIppon( "left", "1" );
    }   				// SHIFT + r		Ippon Part 1 zurueck

    if ( event.keyCode == 51 && !event.shiftKey )
    {
        increaseHansokumake( "left" );
    }  				// 3			Hansokumake für LINKS
    if ( event.keyCode == 51 && event.shiftKey )
    {
        decreaseHansokumake( "left" );
    }  				// SHIFT + 3		Hansokumake für LINKS zurueck
    if ( event.keyCode == 49 && !event.shiftKey )
    {
        increaseShido( "left" );
    }  						// 1            Shido wird erhöht für LINKS
    if ( event.keyCode == 49 && event.shiftKey )
    {
        decreaseShido( "left" );
    }  					// SHIFT + 1    	Shido wird erniedrigt für LINKS
    if ( event.keyCode == 50 && !event.shiftKey )
    {
        increaseChui( "left" );
    }  						// 2			Chui wird erhöht für LINKS
    if ( event.keyCode == 50 && event.shiftKey )
    {
        decreaseChui( "left" );
    }  						// SHIFT + 2		Chui wird erniedrigt für LINKS

    //ippons and penalties RIGHT
    if ( event.keyCode == 79 && !event.shiftKey )
    {
        increaseIppon( "right", "3" );
    }  				// o			Ippon Part 3 fuer ROT
    if ( event.keyCode == 79 && event.shiftKey )
    {
        decreaseIppon( "right", "3" );
    }  				// SHIFT + o		Ippon Part 3 zurueck
    if ( event.keyCode == 73 && !event.shiftKey )
    {
        increaseIppon( "right", "2" );
    }  				// i			Ippon Part 2 fuer ROT
    if ( event.keyCode == 73 && event.shiftKey )
    {
        decreaseIppon( "right", "2" );
    }  				// SHIFT + i		Ippon Part 2 zurueck
    if ( event.keyCode == 85 && !event.shiftKey )
    {
        increaseIppon( "right", "1" );
    } 					// u			Ippon Part 1 fuer ROT
    if ( event.keyCode == 85 && event.shiftKey )
    {
        decreaseIppon( "right", "1" );
    }  				// SHIFT + u		Ippon Part 1 zurueck

    if ( event.keyCode == 56 && !event.shiftKey )
    {
        increaseHansokumake( "right" );
    }  				// 8			Hanso für RECHTS
    if ( event.keyCode == 56 && event.shiftKey )
    {
        decreaseHansokumake( "right" );
    }  				// SHIFT + 8		Hanso für RECHTS zurueck
    if ( event.keyCode == 57 && !event.shiftKey )
    {
        increaseChui( "right" );
    }  						// 9			Chui wird erhöht für RECHTS
    if ( event.keyCode == 57 && event.shiftKey )
    {
        decreaseChui( "right" );
    }  					// SHIFT + 9		Chui wird erniedrigt für RECHTS
    if ( event.keyCode == 48 && !event.shiftKey )
    {
        increaseShido( "right" );
    }  						// 0			Shido wird erhöht für RECHTS
    if ( event.keyCode == 48 && event.shiftKey )
    {
        decreaseShido( "right" );
    }  					// SHIFT + 0		Shido wird erniedrigt für RECHTS

    if ( event.keyCode == 51 && event.shiftKey )
    {
        handleAnzeige( "0" );
    }  						// # 191			WettkampfAnzeige / Aufrufanzeige wird angezeigt
    if ( event.keyCode == 52 && event.shiftKey )
    {
        handleAnzeige( "1" );
    }							// SHIFT + 4		Wettkampfanzeige angezeigt
    if ( event.keyCode == 53 && event.shiftKey )
    {
        handleAnzeige( "2" );
    }							// SHIFT + 5		PreparationAnzeige anzeigen

    if ( event.keyCode == 39 && !event.shiftKey )
    {
        handleKikenWinner( "right" );
    }					// -->			Gewinner Kikengachi weiterschalten
    if ( event.keyCode == 37 && !event.shiftKey )
    {
        handleKikenWinner( "left" );
    }				// <--			Gewinner Kikengachi weiterschalten

    //	  if (event.keyCode == 114 && event.shiftKey && event.ctrlKey)   alarmUserLog();			//SHIFT + CTRL + F3   Anzeige des Input Strings

    if ( event.keyCode == 113 && event.shiftKey && event.ctrlKey )
    {
        shutClock();
    } 				//SHIFT + CTRL + F2   Anzeige schliessen und Daten uebergeben
    if ( event.keyCode == 112 && event.shiftKey && event.ctrlKey )
    {
        alarmSound( '1' );
    } 			//SHIFT + CTRL + F1   Alarmsound abspielen
    if ( event.keyCode == 115 && event.altKey )
    {
        shutClock();
    }
}
//========================================================================================
// Hintergrundfarben functions
//========================================================================================
var blinkColorJumper = false;
var blinkColorActive = false;

function setOriginalBackgroundColor( event )
{
    // Wenn Zeit > maxKampfzeit und mainClock nicht aktiv dann weiter blinken lassen
    if ( getWholeSeconds( "mainClock" ) >= fighting_time && !isActiveClock["mainClock"] )
    {
        exit;
    }
    //
    isShiftKey = false;
    document.getElementById( "time_frame" ).style.display = "none";
    document.getElementById( "time_frame" ).style.visibility = "hidden";
    document.getElementById( "mainClockBorder" ).style.backgroundColor = stdColor;
    document.getElementById( "rightInjuryClockBorder" ).style.backgroundColor = stdColor;
    document.getElementById( "rightHoldingClockBorder" ).style.backgroundColor = stdColor;
    document.getElementById( "leftInjuryClockBorder" ).style.backgroundColor = stdColor;
    document.getElementById( "leftHoldingClockBorder" ).style.backgroundColor = stdColor;
    document.getElementById( "leftPointsBorderTable" ).style.backgroundColor = stdColor;
    document.getElementById( "rightPointsBorderTable" ).style.backgroundColor = stdColor;
    document.body.style.backgroundColor = stdColor;
    if ( blinkColorActive )
    {
        window.clearInterval( maxTimeBlinker );
        blinkColorActive = false;
        showTIE = true;
        sound_on = true;
        document.onkeyup = setOriginalBackgroundColor;
        document.onmouseup = setOriginalBackgroundColor;
    }
}

function timeframeclose()
{
    document.getElementById( "time_frame" ).style.display = "none";
    document.getElementById( "time_frame" ).style.visibility = "hidden";
}

function maxTimeBlinkerBackground()

{
    var activeBlinkColor = hiLightColor;
    if ( blinkColorJumper )
    {
        activeBlinkColor = maxTimeColor;
    }

    document.body.style.backgroundColor = activeBlinkColor;
    document.getElementById( "time_frame" ).style.color = activeBlinkColor;
    document.getElementById( "mainClockBorder" ).style.backgroundColor = activeBlinkColor;
    document.getElementById( "rightInjuryClockBorder" ).style.backgroundColor = activeBlinkColor;
    document.getElementById( "rightHoldingClockBorder" ).style.backgroundColor = activeBlinkColor;
    document.getElementById( "leftInjuryClockBorder" ).style.backgroundColor = activeBlinkColor;
    document.getElementById( "leftHoldingClockBorder" ).style.backgroundColor = activeBlinkColor;
    document.getElementById( "leftPointsBorderTable" ).style.backgroundColor = activeBlinkColor;
    document.getElementById( "rightPointsBorderTable" ).style.backgroundColor = activeBlinkColor;
    blinkColorJumper = !blinkColorJumper;    
    // Wenn die Zeit < maxKampfzeit unbedingt das Blinken stoppen, wenn nicht InjuryTime abgelaufen
    if ( (getWholeSeconds( "mainClock" ) < fighting_time) && (getWholeSeconds( "leftInjuryClock" ) < injury_time) &&
            (getWholeSeconds( "rightInjuryClock" ) < injury_time) )
    {
        setOriginalBackgroundColor();
    }
    // Wenn Hauptkampfzeit aktiv dann den original Hintergrund anzeigen
    if ( isActiveClock["leftHoldingClock"] || isActiveClock["rightHoldingClock"] )
    {
        setOriginalBackgroundColor();
    }
    //
}

function setMaxTimeBackgroundColor()
{
    isShiftKey = false;
    document.getElementById( "time_frame" ).style.display = "block";
    document.getElementById( "time_frame" ).style.visibility = "visible";
    document.getElementById( "mainClockBorder" ).style.backgroundColor = maxTimeColor;
    document.getElementById( "rightInjuryClockBorder" ).style.backgroundColor = maxTimeColor;
    document.getElementById( "rightHoldingClockBorder" ).style.backgroundColor = maxTimeColor;
    document.getElementById( "leftInjuryClockBorder" ).style.backgroundColor = maxTimeColor;
    document.getElementById( "leftHoldingClockBorder" ).style.backgroundColor = maxTimeColor;
    document.getElementById( "leftPointsBorderTable" ).style.backgroundColor = maxTimeColor;
    document.getElementById( "rightPointsBorderTable" ).style.backgroundColor = maxTimeColor;
    document.body.style.backgroundColor = maxTimeColor;

    if ( !blinkColorActive )
    {
        blinkColorActive = true;
        maxTimeBlinker = window.setInterval( "maxTimeBlinkerBackground()", 1000 );
        document.onkeyup = setMaxTimeBackgroundColor;		// Ab jetzt immer MaxTimeBackgroundColor
        document.onmouseup = setMaxTimeBackgroundColor;
        soundLaden();										// Sound abspielen
        gleichstand();										// auf Gleichstand prüfen
        sound_on = false;
        showTIE = false;
    }
}
//------------------------------------------------------------------------------------------------------

<!-- Die maximale Kampfzeit, Haltegriffzeit und Verletzungszeit werden ins Cookie geschrieben -->
function startfight( _altersklasse, _fightingtime, _fightingOverTime, _holdingtime, _injurytime )
{
    var altersklasse = _altersklasse;
    fighting_time=parseInt(_fightingtime);
    
	init();
    fightingtext = "fightingtime=" + _fightingtime;
    fightingtextOvertime = "fightingOverTime=" + _fightingOverTime;
    holgingtext = "holdingtime=15";
    injurytext = "injurytime=" + _injurytime;
    // if ((altersklasse != "u18") && (altersklasse != "S") && (altersklasse != "u21")) fightingtext = "fightingtime=2";
    document.cookie = fightingtext;
    document.cookie = fightingtextOvertime;
    document.cookie = injurytext;
    resetCookie();
    <!-- dringend erforderlich!! -->
    /* aufrufTOanzeige(); */
}

<!-- Altersklasse, Geschlecht und Gewichtsklasse werden im Aufruf angezeigt -->
function showAK( _altersklasse, _geschlecht, _gwklasse )
{
    altersklasse = _altersklasse;
    geschlecht = _geschlecht;
    gwklasse=_gwklasse
        
    document.getElementById( "loadfield_age_1" ).innerHTML = altersklasse;
    document.getElementById( "loadfield_sex_1" ).innerHTML = geschlecht;
    document.getElementById( "loadfield_weight_1" ).innerHTML = gwklasse;

    
}

document.onmousemove = updateInfo;  				
document.onkeydown = myKeyDownFunction;
document.onkeyup = setOriginalBackgroundColor;
document.onmouseup = setOriginalBackgroundColor;