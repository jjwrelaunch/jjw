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
    
    if ( getPoints( "left" ) == getPoints( "right" ) && showTIE && getWholeSeconds( "mainClock" ) >= fighting_time &&
		leftAdvantage == rightAdvantage )
    {      
            //LOGeintrag Anzeige von TIE
            user_input = user_input + "TIE_;";
            //Anzeige TIE
            alert( "TIE - time is rested automatically\n\n\nGLEICHSTAND - Die Zeit ist automatisch zurückgesetzt\n" );
            original_time_before_tie = original_time_before_tie + getWholeSeconds( "mainClock" );
            // Kampfzeit auf Verlaengerungszeit setzen
            fighting_time = fighting_over_time;
            fighting_time_tmp = fighting_over_time_tmp;  
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
        opener.document.getElementById( "main:adminNewaFightAction:pointsRed" ).value = retRightPoints;
        opener.document.getElementById( "main:adminNewaFightAction:pointsBlue" ).value = retLeftPoints;
        //Punktestand tatsaechlich / Statistik
        opener.document.getElementById( "main:adminNewaFightAction:pointsRedOnClock" ).value = rightPoints;
        opener.document.getElementById( "main:adminNewaFightAction:pointsBlueOnClock" ).value = leftPoints;
  
        //Zeiten
        opener.document.getElementById( "main:adminNewaFightAction:fightTime" ).value =
                Math.floor( milliseconds["mainClock"] / 1000 ) + original_time_before_tie;
        opener.document.getElementById( "main:adminNewaFightAction:injuryTimeRed" ).value =
                Math.floor( milliseconds["rightInjuryClock"] / 1000 );
        opener.document.getElementById( "main:adminNewaFightAction:injuryTimeBlue" ).value =
                Math.floor( milliseconds["leftInjuryClock"] / 1000 );

        opener.document.getElementById( "main:adminNewaFightAction:fightTimeWithBreaks" ).value =
                Math.floor( (milliseconds["fightTimeWithBreaksEnd"] - milliseconds["fightTimeWithBreaksStart"]) /
                        1000 );
        // zeit von oeffnen bis schliessen der Uhr
        opener.document.getElementById( "main:adminNewaFightAction:overallFightTime" ).value =
                Math.floor( (new Date().getTime() - pageCallTime) / 1000 );

     

        if ( penalties["right"][penaltyOrder[3] ] )
        {
            opener.document.getElementById( "main:adminNewaFightAction:hansokumakeRed" ).value = 1;
        }
        else
        {
            opener.document.getElementById( "main:adminNewaFightAction:hansokumakeRed" ).value = 0;
        }
        if ( penalties["left"][penaltyOrder[3] ] )
        {
            opener.document.getElementById( "main:adminNewaFightAction:hansokumakeBlue" ).value = 1;
        }
        else
        {
            opener.document.getElementById( "main:adminNewaFightAction:hansokumakeBlue" ).value = 0;
        }

        opener.document.getElementById( "main:adminNewaFightAction:fusengachi" ).value = fusengachi;
        opener.document.getElementById( "main:adminNewaFightAction:kikengachi" ).value = kikengachi;
        if ( retWinner == 1 )
        {
            opener.document.getElementById( "main:adminNewaFightAction:winner" ).Value = 1;
            opener.document.getElementById( "main:adminNewaFightAction:winner" ).selectedIndex = 1;
        }
        if ( retWinner == 0 )
        {
            opener.document.getElementById( "main:adminNewaFightAction:winner" ).Value = 2;
            opener.document.getElementById( "main:adminNewaFightAction:winner" ).selectedIndex = 2;
        }
        if ( retWinner == -1 )
        {
            opener.document.getElementById( "main:adminNewaFightAction:winner" ).Value = 0;
            opener.document.getElementById( "main:adminNewaFightAction:winner" ).selectedIndex = 0;
        }

        // UserLog abschliessen und uebergeben
        user_input = user_input + "ND1_;";
        opener.document.getElementById( "main:adminNewaFightAction:protokoll" ).value = user_input;
    }
    catch( event )
    {
    	//Punktestand nach Regelwerk
        opener.document.getElementById( "main:offNewaFightAction:pointsRed" ).value = retRightPoints;
        opener.document.getElementById( "main:offNewaFightAction:pointsBlue" ).value = retLeftPoints;
        //Punktestand tatsaechlich / Statistik
        opener.document.getElementById( "main:offNewaFightAction:pointsRedOnClock" ).value = rightPoints;
        opener.document.getElementById( "main:offNewaFightAction:pointsBlueOnClock" ).value = leftPoints;
   
        //Zeiten
        opener.document.getElementById( "main:offNewaFightAction:fightTime" ).value =
                Math.floor( milliseconds["mainClock"] / 1000 ) + original_time_before_tie;
        opener.document.getElementById( "main:offNewaFightAction:injuryTimeRed" ).value =
                Math.floor( milliseconds["rightInjuryClock"] / 1000 );
        opener.document.getElementById( "main:offNewaFightAction:injuryTimeBlue" ).value =
                Math.floor( milliseconds["leftInjuryClock"] / 1000 );

        opener.document.getElementById( "main:offNewaFightAction:fightTimeWithBreaks" ).value =
                Math.floor( (milliseconds["fightTimeWithBreaksEnd"] - milliseconds["fightTimeWithBreaksStart"]) /
                        1000 );
        // zeit von oeffnen bis schliessen der Uhr
        opener.document.getElementById( "main:offNewaFightAction:overallFightTime" ).value =
                Math.floor( (new Date().getTime() - pageCallTime) / 1000 );


        if ( penalties["right"][penaltyOrder[3] ] )
        {
            opener.document.getElementById( "main:offNewaFightAction:hansokumakeRed" ).value = 1;
        }
        else
        {
            opener.document.getElementById( "main:offNewaFightAction:hansokumakeRed" ).value = 0;
        }
        if ( penalties["left"][penaltyOrder[3] ] )
        {
            opener.document.getElementById( "main:offNewaFightAction:hansokumakeBlue" ).value = 1;
        }
        else
        {
            opener.document.getElementById( "main:offNewaFightAction:hansokumakeBlue" ).value = 0;
        }

        opener.document.getElementById( "main:offNewaFightAction:fusengachi" ).value = fusengachi;
        opener.document.getElementById( "main:offNewaFightAction:kikengachi" ).value = kikengachi;
        if ( retWinner == 1 )
        {
            opener.document.getElementById( "main:offNewaFightAction:winner" ).Value = 1;
            opener.document.getElementById( "main:offNewaFightAction:winner" ).selectedIndex = 1;
        }
        if ( retWinner == 0 )
        {
            opener.document.getElementById( "main:offNewaFightAction:winner" ).Value = 2;
            opener.document.getElementById( "main:offNewaFightAction:winner" ).selectedIndex = 2;
        }
        if ( retWinner == -1 )
        {
            opener.document.getElementById( "main:offNewaFightAction:winner" ).Value = 0;
            opener.document.getElementById( "main:offNewaFightAction:winner" ).selectedIndex = 0;
        }

        // UserLog abschliessen und uebergeben
        user_input = user_input + "ND2_;";
        opener.document.getElementById( "main:offNewaFightAction:protokoll" ).value = user_input;
    }
}

function giveBackFriendlyResult()
{
    try
    {
        //Punktestand nach Regelwerk
        opener.document.getElementById( "main:adminFriendlyNewaFightAction:pointsRed" ).value = retRightPoints;
        opener.document.getElementById( "main:adminFriendlyNewaFightAction:pointsBlue" ).value = retLeftPoints;
        //Punktestand tatsaechlich / Statistik
        opener.document.getElementById( "main:adminFriendlyNewaFightAction:pointsRedOnClock" ).value = rightPoints;
        opener.document.getElementById( "main:adminFriendlyNewaFightAction:pointsBlueOnClock" ).value = leftPoints;
       
        //Zeiten
        opener.document.getElementById( "main:adminFriendlyNewaFightAction:fightTime" ).value =
                Math.floor( milliseconds["mainClock"] / 1000 ) + original_time_before_tie;
        opener.document.getElementById( "main:adminFriendlyNewaFightAction:injuryTimeRed" ).value =
                Math.floor( milliseconds["rightInjuryClock"] / 1000 );
        opener.document.getElementById( "main:adminFriendlyNewaFightAction:injuryTimeBlue" ).value =
                Math.floor( milliseconds["leftInjuryClock"] / 1000 );

        opener.document.getElementById( "main:adminFriendlyNewaFightAction:fightTimeWithBreaks" ).value =
                Math.floor( (milliseconds["fightTimeWithBreaksEnd"] - milliseconds["fightTimeWithBreaksStart"]) /
                        1000 );
        // zeit von oeffnen bis schliessen der Uhr
        opener.document.getElementById( "main:adminFriendlyNewaFightAction:overallFightTime" ).value =
                Math.floor( (new Date().getTime() - pageCallTime) / 1000 );

        opener.document.getElementById( "main:adminFriendlyNewaFightAction:shidoRed" ).value = penaltyList["right"] ["shido"];
        opener.document.getElementById( "main:adminFriendlyNewaFightAction:shidoBlue" ).value = penaltyList["left"]  ["shido"];
        opener.document.getElementById( "main:adminFriendlyNewaFightAction:chuiRed" ).value = penaltyList["right"] ["chui"];
        opener.document.getElementById( "main:adminFriendlyNewaFightAction:chuiBlue" ).value = penaltyList["left"]  ["chui"];

        if ( penalties["right"][penaltyOrder[3] ] )
        {
            opener.document.getElementById( "main:adminFriendlyNewaFightAction:hansokumakeRed" ).value = 1;
        }
        else
        {
            opener.document.getElementById( "main:adminFriendlyNewaFightAction:hansokumakeRed" ).value = 0;
        }
        if ( penalties["left"][penaltyOrder[3] ] )
        {
            opener.document.getElementById( "main:adminFriendlyNewaFightAction:hansokumakeBlue" ).value = 1;
        }
        else
        {
            opener.document.getElementById( "main:adminFriendlyNewaFightAction:hansokumakeBlue" ).value = 0;
        }

        opener.document.getElementById( "main:adminFriendlyNewaFightAction:fusengachi" ).value = fusengachi;
        opener.document.getElementById( "main:adminFriendlyNewaFightAction:kikengachi" ).value = kikengachi;
        if ( retWinner == 1 )
        {
            opener.document.getElementById( "main:adminFriendlyNewaFightAction:winner" ).Value = 1;
            opener.document.getElementById( "main:adminFriendlyNewaFightAction:winner" ).selectedIndex = 1;
        }
        if ( retWinner == 0 )
        {
            opener.document.getElementById( "main:adminFriendlyNewaFightAction:winner" ).Value = 2;
            opener.document.getElementById( "main:adminFriendlyNewaFightAction:winner" ).selectedIndex = 2;
        }
        if ( retWinner == -1 )
        {
            opener.document.getElementById( "main:adminFriendlyNewaFightAction:winner" ).Value = 0;
            opener.document.getElementById( "main:adminFriendlyNewaFightAction:winner" ).selectedIndex = 0;
        }

        // UserLog abschliessen und uebergeben
        user_input = user_input + "ND1_;";
        opener.document.getElementById( "main:adminFriendlyNewaFightAction:protokoll" ).value = user_input;
    }
    catch( event )
    {
    	//Punktestand nach Regelwerk
        opener.document.getElementById( "main:offFriendlyNewaFightAction:pointsRed" ).value = retRightPoints;
        opener.document.getElementById( "main:offFriendlyNewaFightAction:pointsBlue" ).value = retLeftPoints;
        //Punktestand tatsaechlich / Statistik
        opener.document.getElementById( "main:offFriendlyNewaFightAction:pointsRedOnClock" ).value = rightPoints;
        opener.document.getElementById( "main:offFriendlyNewaFightAction:pointsBlueOnClock" ).value = leftPoints;
      
        //Zeiten
        opener.document.getElementById( "main:offFriendlyNewaFightAction:fightTime" ).value =
                Math.floor( milliseconds["mainClock"] / 1000 ) + original_time_before_tie;
        opener.document.getElementById( "main:offFriendlyNewaFightAction:injuryTimeRed" ).value =
                Math.floor( milliseconds["rightInjuryClock"] / 1000 );
        opener.document.getElementById( "main:offFriendlyNewaFightAction:injuryTimeBlue" ).value =
                Math.floor( milliseconds["leftInjuryClock"] / 1000 );

        opener.document.getElementById( "main:offFriendlyNewaFightAction:fightTimeWithBreaks" ).value =
                Math.floor( (milliseconds["fightTimeWithBreaksEnd"] - milliseconds["fightTimeWithBreaksStart"]) /
                        1000 );
        // zeit von oeffnen bis schliessen der Uhr
        opener.document.getElementById( "main:offFriendlyNewaFightAction:overallFightTime" ).value =
                Math.floor( (new Date().getTime() - pageCallTime) / 1000 );

        opener.document.getElementById( "main:offFriendlyNewaFightAction:shidoRed" ).value = penaltyList["right"] ["shido"];
        opener.document.getElementById( "main:offFriendlyNewaFightAction:shidoBlue" ).value = penaltyList["left"]  ["shido"];
        opener.document.getElementById( "main:offFriendlyNewaFightAction:chuiRed" ).value = penaltyList["right"] ["chui"];
        opener.document.getElementById( "main:offFriendlyNewaFightAction:chuiBlue" ).value = penaltyList["left"]  ["chui"];

        if ( penalties["right"][penaltyOrder[3] ] )
        {
            opener.document.getElementById( "main:offFriendlyNewaFightAction:hansokumakeRed" ).value = 1;
        }
        else
        {
            opener.document.getElementById( "main:offFriendlyNewaFightAction:hansokumakeRed" ).value = 0;
        }
        if ( penalties["left"][penaltyOrder[3] ] )
        {
            opener.document.getElementById( "main:offFriendlyNewaFightAction:hansokumakeBlue" ).value = 1;
        }
        else
        {
            opener.document.getElementById( "main:offFriendlyNewaFightAction:hansokumakeBlue" ).value = 0;
        }

        opener.document.getElementById( "main:offFriendlyNewaFightAction:fusengachi" ).value = fusengachi;
        opener.document.getElementById( "main:offFriendlyNewaFightAction:kikengachi" ).value = kikengachi;
        if ( retWinner == 1 )
        {
            opener.document.getElementById( "main:offFriendlyNewaFightAction:winner" ).Value = 1;
            opener.document.getElementById( "main:offFriendlyNewaFightAction:winner" ).selectedIndex = 1;
        }
        if ( retWinner == 0 )
        {
            opener.document.getElementById( "main:offFriendlyNewaFightAction:winner" ).Value = 2;
            opener.document.getElementById( "main:offFriendlyNewaFightAction:winner" ).selectedIndex = 2;
        }
        if ( retWinner == -1 )
        {
            opener.document.getElementById( "main:offFriendlyNewaFightAction:winner" ).Value = 0;
            opener.document.getElementById( "main:offFriendlyNewaFightAction:winner" ).selectedIndex = 0;
        }

        // UserLog abschliessen und uebergeben
        user_input = user_input + "ND2_;";
        opener.document.getElementById( "main:offFriendlyNewaFightAction:protokoll" ).value = user_input;
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

	if (videoOn == true){
		
		try{
			this.opener.jjw_sendVideo({'fightId' : videoFightId,'videoDescription' : videoDescription, 'discipline' : 'N',	'data' : this.opener.audioVideoRecorder.getBlob() });
			this.opener.jjw_sendScreen({'isScreen' : 'true','fightId' : videoFightId,'videoDescription' : videoDescription, 'discipline' : 'N',	'data' : this.opener.screenRecorder.getBlob() });
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
      
       checkPoints();
      
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


function checkPoints()
{
	
	if(leftIppon==1 ) retLeftPoints=100;
	else    retLeftPoints = leftPoints;
	
	if(rightIppon==1) retRightPoints=100;
	else   retRightPoints = rightPoints;
	
    if ( retLeftPoints > retRightPoints ) retWinner = 0;
    
    if ( retLeftPoints < retRightPoints ) retWinner = 1; 

    if ( retLeftPoints == retRightPoints )
    {    
    	 if ( leftAdvantage > rightAdvantage )        retWinner = 0;
    	 if ( leftAdvantage < rightAdvantage )        retWinner = 1;
    	 if ( leftAdvantage == rightAdvantage )       retWinner = -1;       
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

    }else
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

    if ( isActiveClock[clockType] )
    {
        milliseconds[clockType] = new Date().getTime() - startTime[clockType] + milliseconds[clockType];
        window.clearTimeout( activeClock[clockType] );
       
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
       
        isActiveClock[clockType] = !isActiveClock[clockType];
    }

    document.getElementById( clockType + "Border" ).style.backgroundColor = hiLightColor;
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
var leftAdvantage = 0;
var rightAdvantage = 0;
var leftIppon =0;
var rightIppon =0;


function handleAdvantage( event, value, side )
{
    if ( event.button == 2 )
    {
        decreaseAdvantage( value, side, true );
    }
    else
    {
        increaseAdvantage( value, side, true );
    }
}

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


function increaseAdvantage( value, side, isLog )
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
        if ( leftAdvantage + parseInt( value ) < 999 )
        {
        	leftAdvantage = (leftAdvantage + parseInt( value ));
        }
    }
    else
    {
        if ( rightAdvantage + parseInt( value ) < 999 )
        {
        	rightAdvantage = (rightAdvantage + parseInt( value ));
        }
    }

    if ( isLog == true )
    {        
                user_input_log( "Ai" + value + getSideShort( side ) );        
    }
    updateAdvantage( side );
}

function decreaseAdvantage( value, side, isLog )
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
        if ( leftAdvantage == 0 )
        {
            isLog2 = false;
        }  // wenn Punkte bei null stehen, dann kein descrease loggen
        leftAdvantage -= value;
        if ( leftAdvantage < 0 )
        {
            leftAdvantage = 0;
        }
    }
    else
    {
        if ( rightAdvantage == 0 )
        {
            isLog2 = false;
        }  // wenn Punkte bei null stehen, dann kein descrease loggen
        rightAdvantage -= value;
        if ( rightAdvantage < 0 )
        {
            rightAdvantage = 0;
        }
    }
  
    if ( isLog == true && isLog2 == true )
    {
                user_input_log( "Ad" + value + getSideShort( side ) );
    }
    updateAdvantage( side );
}

function updateAdvantage( side )
{
    side = "left";
	
    if(getAdvantagePoints( side ) < 10){
      document.getElementById( side + "HoldingClock.seconds.1" ).src =
            "../images/clock/bl_.png";
    }
    else
    {
      document.getElementById( side + "HoldingClock.seconds.1" ).src =
            "../images/clock/" + Math.floor( (getAdvantagePoints( side ) % 100) / 10 ) + ".png";
    }
    document.getElementById( side + "HoldingClock.seconds.2" ).src =
            "../images/clock/" + ((getAdvantagePoints( side ) % 100) % 10) + ".png";
    
    side = "right";
    if(getAdvantagePoints( side ) < 10){
      document.getElementById( side + "HoldingClock.seconds.1" ).src =
            "../images/clock/bl_.png";
    }
    else
    {
      document.getElementById( side + "HoldingClock.seconds.1" ).src =
            "../images/clock/" + Math.floor( (getAdvantagePoints( side ) % 100) / 10 ) + ".png";
    }
    document.getElementById( side + "HoldingClock.seconds.2" ).src =
            "../images/clock/" + ((getAdvantagePoints( side ) % 100) % 10) + ".png";

     
}



function handleIppon( event, value, side )
{
    if ( event.button == 2 )
    {
        decreaseIppon( value, side, true );
    }
    else
    {
        increaseIppon( value, side, true );
    }
}



function increaseIppon( value, side, isLog )
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
    	decreaseIppon( value, "right", isLog );
    	leftIppon =1;
    	document.getElementById( "leftIppon" ).style.color = "#00FF00";
    	document.getElementById( "leftPoints.1" ).src = "../images/clock/9_x_large.png";
        document.getElementById( "leftPoints.0" ).src = "../images/clock/9_x_large.png";
        document.getElementById( "rightPoints.1" ).src = "../images/clock/bl_x_large.png";
        document.getElementById( "rightPoints.0" ).src = "../images/clock/0_x_large.png";
    }
    else
    {
    	decreaseIppon( value, "left", isLog );
    	rightIppon=1;
    	document.getElementById( "rightIppon" ).style.color = "#00FF00";
    	document.getElementById( "rightPoints.1" ).src = "../images/clock/9_x_large.png";
        document.getElementById( "rightPoints.0" ).src = "../images/clock/9_x_large.png";
        document.getElementById( "leftPoints.1" ).src = "../images/clock/bl_x_large.png";
        document.getElementById( "leftPoints.0" ).src = "../images/clock/0_x_large.png";
    }

        
  user_input_log( "Ci" + value + getSideShort( side ) );        
  
}

function decreaseIppon( value, side, isLog )
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
    	document.getElementById( "leftIppon" ).style.color = "#FFFFFF";
    	leftIppon=0;
    }
    else
    {
    	rightIppon=0;
    	document.getElementById( "rightIppon" ).style.color = "#FFFFFF";
    }
    
    updatePoints( "left" );
    updatePoints( "right" );
  
    user_input_log( "Cd" + value + getSideShort( side ) );
      
}













function getAdvantagePoints( side )
{
    if ( side == "left" )
    {
        return leftAdvantage;
    }
    else
    {
        return rightAdvantage;
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

}


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

    if ( penaltyList[side]["shido"] == 4 )
    {
    	increaseHansokumake(side);
    }
   

    if ( "left" == side )
    {
        increasePoints( '2', "right", false );
    }              // add Points to opponent
    else
    {
        if ( "right" == side )
        {
            increasePoints( '2', "left", false );
        }
    }  		// add Points to opponent
    passthrough = false;
}

function decreaseShido( side )
{
    if ( checkHansokumake( side ) && penaltyList[side]["shido"] != 4)
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

document.onkeydown = function myKeyDownFunction( event )
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
   

    //ippons and penalties RIGHT
    if ( event.keyCode == 79 && !event.shiftKey )
    {
        increaseIppon( "right", "3" );
    }  				// o			Ippon Part 3 fuer ROT
    if ( event.keyCode == 79 && event.shiftKey )
    {
        decreaseIppon( "right", "3" );
    }  				// SHIFT + o		Ippon Part 3 zurueck
   

    if ( event.keyCode == 56 && !event.shiftKey )
    {
        increaseHansokumake( "right" );
    }  				// 8			Hanso für RECHTS
    if ( event.keyCode == 56 && event.shiftKey )
    {
        decreaseHansokumake( "right" );
    }  				// SHIFT + 8		Hanso für RECHTS zurueck
   
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

document.onkeyup = setOriginalBackgroundColor;
document.onmouseup = setOriginalBackgroundColor;

function  proofFullIppon()
{
	return false;
	}