/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : javascript_duo.js
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 21:02:57
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
var i = 0; //counter
var clockUpdateIntervall = 250; // Intervall, in der die Uhrenanzeigen aktualisiert werden
var hiLightColor = "#00FF00";  // Farbe, die beim starten / stoppen der Uhren angezeigt wird
var hiLightColorStop = "#FF0000";   // Farbe, die beim stoppen der Uhren angezeigt wird

var injury_time ;				// Verletzungszeit in Sekunden

var pageCallTime = new Date().getTime();

var fusengachi = 0;				// 0 = kein fusengachi; 1 = fusengachi ROT; 2 = fusengachi BLAU; -1 = fusengachi ROT + BLAU
var kikengachi = 0;				// 0 = keine kikengachi; 1 = kikengachi ROT; 2 = kikengachi BLAU; -1 = kikengachi ROT + BLAU
var fusengachi_winner = 0		// Gewinner wenn Aufgabe durch Krankheit / Verletzung / Nichterscheinen; 0 = keiner; 1 = Gewinner Rot; 2 = Gewinner Blau


function init()
{
	
	screenDiff= parseInt((screen.height-12- 768)/2);
	
	document.getElementById( "rightPoints.2" ).style.height = (200+ screenDiff)+"px";
	document.getElementById( "rightPoints.1" ).style.height = (200+ screenDiff)+"px";
	document.getElementById( "rightPoints.0" ).style.height = (200+ screenDiff)+"px";
	document.getElementById( "rightPoints.S" ).style.height = (200+ screenDiff)+"px";
	document.getElementById( "rightPoints.-1" ).style.height = (200+ screenDiff)+"px";
	
	document.getElementById( "leftPoints.2" ).style.height = (200+ screenDiff)+"px";
	document.getElementById( "leftPoints.1" ).style.height = (200+ screenDiff)+"px";
	document.getElementById( "leftPoints.0" ).style.height = (200+ screenDiff)+"px";
	document.getElementById( "leftPoints.S" ).style.height = (200+ screenDiff)+"px";
	document.getElementById( "leftPoints.-1" ).style.height = (200+ screenDiff)+"px";
	
	
	if(screen.width>1400) {	
		document.getElementById( "redSpanName" ).style.fontSize="45px";
		document.getElementById( "blueSpanName" ).style.fontSize="45px";		
	}	
	
	document.getElementById( "globalTab" ).style.height = screen.height-25+"px";
	
	if(screen.height < 769){
		document.getElementById( "call_red_1" ).style.fontSize="30px";
	    document.getElementById( "call_blue" ).style.fontSize="30px";
	    document.getElementById( "call_red_1_name" ).style.fontSize="50px";
	    document.getElementById( "call_blue_1_name" ).style.fontSize="50px";
	    
	    document.getElementById( "redSpanName" ).style.fontSize="30px";
	    document.getElementById( "redSpanTeam" ).style.fontSize="20px";
	    document.getElementById( "blueSpanName" ).style.fontSize="30px";
	    document.getElementById( "blueSpanTeam" ).style.fontSize="20px";	
	    
	    document.getElementById( "rightInjuryClock" ).style.top="300px";	
	    document.getElementById( "globalTab" ).style.height = screen.height-25+"px";
	    document.getElementById( "call_red_2" ).style.height = "450px";
	    document.getElementById( "duoClocks" ).style.top="200px";
	    
	    document.getElementById( "redTr" ).style.height = "300px";
	    document.getElementById( "blueTr" ).style.height = "300px";
	    
	    var pointsHeight="176px";
	    document.getElementById( "leftPoints.2" ).style.height = pointsHeight;
	    document.getElementById( "leftPoints.1" ).style.height = pointsHeight;
	    document.getElementById( "leftPoints.0" ).style.height = pointsHeight;
	    document.getElementById( "leftPoints.S" ).style.height = pointsHeight;
	    document.getElementById( "leftPoints.-1" ).style.height = pointsHeight;
	    
	    document.getElementById( "rightPoints.2" ).style.height = pointsHeight;
	    document.getElementById( "rightPoints.1" ).style.height = pointsHeight;
	    document.getElementById( "rightPoints.0" ).style.height = pointsHeight;
	    document.getElementById( "rightPoints.S" ).style.height = pointsHeight;
	    document.getElementById( "rightPoints.-1" ).style.height = pointsHeight;
	   
	    document.getElementById( "rightInjuryClock" ).style.top = "122px";
		document.getElementById( "leftInjuryClock" ).style.top = "445px";
		
		 document.getElementById( "tdNameRed" ).style.height = "81px";
		 document.getElementById( "tdNameBlue" ).style.height = "81px";
		
	}
	resizeSeriesPoints();
}


function resizeSeriesPoints(){
	
	if(screen.height>800) {
		var elements = document.getElementsByClassName("sets");
		for (var i in elements) {
			if (elements.hasOwnProperty(i)) {
				elements[i].style.fontSize = '28px';
			}	  	  
		}
	
		elements =document.getElementsByClassName("setsHigh_right");
		for (var i in elements) {
			if (elements.hasOwnProperty(i)) {
				elements[i].style.fontSize = '28px';
			}	  	  
		}
	}
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

//========================================================================================
// do result
//========================================================================================
var retLeftPoints = 0.0, retLeftPointsMax = 0.0;
var retRightPoints = 0.0, retRightPointsMax = 0.0;
var overtime = false;
var retWinner = -1;
var isShutClockOnce=false;

function shutClock()
{
	if (isShutClockOnce) return;
	if (isfriend) {
		shutFriendlyClock();
		return;
	}
	if (videoOn == true ){
		try{
			jjw_stopRecording();
			jjw_stopRecordingScreen();
		}
		catch (e){}
	}
	
    doResult();
    giveBackResult();

    var exit = confirm("schließen / close?");
	if (videoOn == true ){
		
		
		try{
			this.opener.jjw_sendVideo({'fightId' : videoFightId,'videoDescription' : videoDescription, 'discipline' : 'D',	'data' : this.opener.audioVideoRecorder.getBlob() });
			this.opener.jjw_sendScreen({'isScreen' : 'true','fightId' : videoFightId,'videoDescription' : videoDescription, 'discipline' : 'D',	'data' : this.opener.screenRecorder.getBlob() });
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

function giveBackResult()
{
    try
    {
        opener.document.getElementById( "main:adminDuoFightAction:pointsRed" ).value =  retRightPoints;
        opener.document.getElementById( "main:adminDuoFightAction:pointsBlue" ).value =  retLeftPoints;
        opener.document.getElementById( "main:adminDuoFightAction:overtime" ).checked = overtime;
        opener.document.getElementById( "main:adminDuoFightAction:pointsRedMax" ).disabled = !overtime;
        opener.document.getElementById( "main:adminDuoFightAction:pointsBlueMax" ).disabled = !overtime;
        opener.document.getElementById( "main:adminDuoFightAction:pointsRedMax" ).value =  retRightPointsMax;
        opener.document.getElementById( "main:adminDuoFightAction:pointsBlueMax" ).value =  retLeftPointsMax;

        //KaRi Bewertungen speichern

        opener.document.getElementById( "main:adminDuoFightAction:redSeriesArefI" ).value =  (document.getElementById( "rSet1_1" ).value != '')   ? document.getElementById( "rSet1_1" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:redSeriesArefII" ).value =  (document.getElementById( "rSet1_2" ).value != '')  ? document.getElementById( "rSet1_2" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:redSeriesArefIII" ).value =  (document.getElementById( "rSet1_3" ).value != '') ? document.getElementById( "rSet1_3" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:redSeriesArefIV" ).value =  (document.getElementById( "rSet1_4" ).value != '')  ? document.getElementById( "rSet1_4" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:redSeriesArefV" ).value =  (document.getElementById( "rSet1_5" ).value != '')   ? document.getElementById( "rSet1_5" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:blueSeriesArefI" ).value =  (document.getElementById( "lSet1_1" ).value != '')  ? document.getElementById( "lSet1_1" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:blueSeriesArefII" ).value =  (document.getElementById( "lSet1_2" ).value != '') ? document.getElementById( "lSet1_2" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:blueSeriesArefIII" ).value =  (document.getElementById( "lSet1_3" ).value != '')? document.getElementById( "lSet1_3" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:blueSeriesArefIV" ).value =  (document.getElementById( "lSet1_4" ).value != '') ? document.getElementById( "lSet1_4" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:blueSeriesArefV" ).value =  (document.getElementById( "lSet1_5" ).value != '')  ? document.getElementById( "lSet1_5" ).value : 0.0;

        opener.document.getElementById( "main:adminDuoFightAction:redSeriesBrefI" ).value =  (document.getElementById( "rSet2_1" ).value != '')   ? document.getElementById( "rSet2_1" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:redSeriesBrefII" ).value =  (document.getElementById( "rSet2_2" ).value != '')  ? document.getElementById( "rSet2_2" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:redSeriesBrefIII" ).value =  (document.getElementById( "rSet2_3" ).value != '') ? document.getElementById( "rSet2_3" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:redSeriesBrefIV" ).value =  (document.getElementById( "rSet2_4" ).value != '')  ? document.getElementById( "rSet2_4" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:redSeriesBrefV" ).value =  (document.getElementById( "rSet2_5" ).value != '')   ? document.getElementById( "rSet2_5" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:blueSeriesBrefI" ).value =  (document.getElementById( "lSet2_1" ).value != '')  ? document.getElementById( "lSet2_1" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:blueSeriesBrefII" ).value =  (document.getElementById( "lSet2_2" ).value != '') ? document.getElementById( "lSet2_2" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:blueSeriesBrefIII" ).value =  (document.getElementById( "lSet2_3" ).value != '')? document.getElementById( "lSet2_3" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:blueSeriesBrefIV" ).value =  (document.getElementById( "lSet2_4" ).value != '') ? document.getElementById( "lSet2_4" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:blueSeriesBrefV" ).value =  (document.getElementById( "lSet2_5" ).value != '')  ? document.getElementById( "lSet2_5" ).value : 0.0;

        opener.document.getElementById( "main:adminDuoFightAction:redSeriesCrefI" ).value =  (document.getElementById( "rSet3_1" ).value != '')   ? document.getElementById( "rSet3_1" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:redSeriesCrefII" ).value =  (document.getElementById( "rSet3_2" ).value != '')  ? document.getElementById( "rSet3_2" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:redSeriesCrefIII" ).value =  (document.getElementById( "rSet3_3" ).value != '') ? document.getElementById( "rSet3_3" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:redSeriesCrefIV" ).value =  (document.getElementById( "rSet3_4" ).value != '')  ? document.getElementById( "rSet3_4" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:redSeriesCrefV" ).value =  (document.getElementById( "rSet3_5" ).value != '')   ? document.getElementById( "rSet3_5" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:blueSeriesCrefI" ).value =  (document.getElementById( "lSet3_1" ).value != '')  ? document.getElementById( "lSet3_1" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:blueSeriesCrefII" ).value =  (document.getElementById( "lSet3_2" ).value != '') ? document.getElementById( "lSet3_2" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:blueSeriesCrefIII" ).value =  (document.getElementById( "lSet3_3" ).value != '')? document.getElementById( "lSet3_3" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:blueSeriesCrefIV" ).value =  (document.getElementById( "lSet3_4" ).value != '') ? document.getElementById( "lSet3_4" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:blueSeriesCrefV" ).value =  (document.getElementById( "lSet3_5" ).value != '')  ? document.getElementById( "lSet3_5" ).value : 0.0;

        opener.document.getElementById( "main:adminDuoFightAction:redSeriesDrefI" ).value =  (document.getElementById( "rSet4_1" ).value != '')   ? document.getElementById( "rSet4_1" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:redSeriesDrefII" ).value =  (document.getElementById( "rSet4_2" ).value != '')  ? document.getElementById( "rSet4_2" ).value : 0.0; 
        opener.document.getElementById( "main:adminDuoFightAction:redSeriesDrefIII" ).value =  (document.getElementById( "rSet4_3" ).value != '') ? document.getElementById( "rSet4_3" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:redSeriesDrefIV" ).value =  (document.getElementById( "rSet4_4" ).value != '')  ? document.getElementById( "rSet4_4" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:redSeriesDrefV" ).value =  (document.getElementById( "rSet4_5" ).value != '')   ? document.getElementById( "rSet4_5" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:blueSeriesDrefI" ).value =  (document.getElementById( "lSet4_1" ).value != '')  ? document.getElementById( "lSet4_1" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:blueSeriesDrefII" ).value =  (document.getElementById( "lSet4_2" ).value != '') ? document.getElementById( "lSet4_2" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:blueSeriesDrefIII" ).value =  (document.getElementById( "lSet4_3" ).value != '')? document.getElementById( "lSet4_3" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:blueSeriesDrefIV" ).value =  (document.getElementById( "lSet4_4" ).value != '') ? document.getElementById( "lSet4_4" ).value : 0.0;
        opener.document.getElementById( "main:adminDuoFightAction:blueSeriesDrefV" ).value =  (document.getElementById( "lSet4_5" ).value != '')  ? document.getElementById( "lSet4_5" ).value : 0.0;
        // zeit von oeffnen bis schlie�en der Uhr
        opener.document.getElementById( "main:adminDuoFightAction:overallFightTime" ).value = 
                Math.floor( (new Date().getTime() - pageCallTime) / 1000 );
        opener.document.getElementById( "main:adminDuoFightAction:fusengachi" ).value =  fusengachi;
        opener.document.getElementById( "main:adminDuoFightAction:kikengachi" ).value =  kikengachi;

        if ( retWinner == 1 )
        {
            opener.document.getElementById( "main:adminDuoFightAction:winner" ).value =  1;
            opener.document.getElementById( "main:adminDuoFightAction:winner" ).selectedIndex = 1;
        }
        if ( retWinner == 0 )
        {
            opener.document.getElementById( "main:adminDuoFightAction:winner" ).value =  2;
            opener.document.getElementById( "main:adminDuoFightAction:winner" ).selectedIndex = 2;
        }
        if ( retWinner == -1 )
        {
            opener.document.getElementById( "main:adminDuoFightAction:winner" ).value =  0;
            opener.document.getElementById( "main:adminDuoFightAction:winner" ).selectedIndex = 0;
        }
    }
    catch( event )
    {
    	opener.document.getElementById( "main:offDuoFightAction:pointsRed" ).value =  retRightPoints;
        opener.document.getElementById( "main:offDuoFightAction:pointsBlue" ).value =  retLeftPoints;
        opener.document.getElementById( "main:offDuoFightAction:overtime" ).checked = overtime;
        opener.document.getElementById( "main:offDuoFightAction:pointsRedMax" ).disabled = !overtime;
        opener.document.getElementById( "main:offDuoFightAction:pointsBlueMax" ).disabled = !overtime;
        opener.document.getElementById( "main:offDuoFightAction:pointsRedMax" ).value =  retRightPointsMax;
        opener.document.getElementById( "main:offDuoFightAction:pointsBlueMax" ).value =  retLeftPointsMax;

        //KaRi Bewertungen speichern

        opener.document.getElementById( "main:offDuoFightAction:redSeriesArefI" ).value =  (document.getElementById( "rSet1_1" ).value != '')   ? document.getElementById( "rSet1_1" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:redSeriesArefII" ).value =  (document.getElementById( "rSet1_2" ).value != '')  ? document.getElementById( "rSet1_2" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:redSeriesArefIII" ).value =  (document.getElementById( "rSet1_3" ).value != '') ? document.getElementById( "rSet1_3" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:redSeriesArefIV" ).value =  (document.getElementById( "rSet1_4" ).value != '')  ? document.getElementById( "rSet1_4" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:redSeriesArefV" ).value =  (document.getElementById( "rSet1_5" ).value != '')   ? document.getElementById( "rSet1_5" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:blueSeriesArefI" ).value =  (document.getElementById( "lSet1_1" ).value != '')  ? document.getElementById( "lSet1_1" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:blueSeriesArefII" ).value =  (document.getElementById( "lSet1_2" ).value != '') ? document.getElementById( "lSet1_2" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:blueSeriesArefIII" ).value =  (document.getElementById( "lSet1_3" ).value != '')? document.getElementById( "lSet1_3" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:blueSeriesArefIV" ).value =  (document.getElementById( "lSet1_4" ).value != '') ? document.getElementById( "lSet1_4" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:blueSeriesArefV" ).value =  (document.getElementById( "lSet1_5" ).value != '')  ? document.getElementById( "lSet1_5" ).value : 0.0;

        opener.document.getElementById( "main:offDuoFightAction:redSeriesBrefI" ).value =  (document.getElementById( "rSet2_1" ).value != '')   ? document.getElementById( "rSet2_1" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:redSeriesBrefII" ).value =  (document.getElementById( "rSet2_2" ).value != '')  ? document.getElementById( "rSet2_2" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:redSeriesBrefIII" ).value =  (document.getElementById( "rSet2_3" ).value != '') ? document.getElementById( "rSet2_3" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:redSeriesBrefIV" ).value =  (document.getElementById( "rSet2_4" ).value != '')  ? document.getElementById( "rSet2_4" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:redSeriesBrefV" ).value =  (document.getElementById( "rSet2_5" ).value != '')   ? document.getElementById( "rSet2_5" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:blueSeriesBrefI" ).value =  (document.getElementById( "lSet2_1" ).value != '')  ? document.getElementById( "lSet2_1" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:blueSeriesBrefII" ).value =  (document.getElementById( "lSet2_2" ).value != '') ? document.getElementById( "lSet2_2" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:blueSeriesBrefIII" ).value =  (document.getElementById( "lSet2_3" ).value != '')? document.getElementById( "lSet2_3" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:blueSeriesBrefIV" ).value =  (document.getElementById( "lSet2_4" ).value != '') ? document.getElementById( "lSet2_4" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:blueSeriesBrefV" ).value =  (document.getElementById( "lSet2_5" ).value != '')  ? document.getElementById( "lSet2_5" ).value : 0.0;

        opener.document.getElementById( "main:offDuoFightAction:redSeriesCrefI" ).value =  (document.getElementById( "rSet3_1" ).value != '')   ? document.getElementById( "rSet3_1" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:redSeriesCrefII" ).value =  (document.getElementById( "rSet3_2" ).value != '')  ? document.getElementById( "rSet3_2" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:redSeriesCrefIII" ).value =  (document.getElementById( "rSet3_3" ).value != '') ? document.getElementById( "rSet3_3" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:redSeriesCrefIV" ).value =  (document.getElementById( "rSet3_4" ).value != '')  ? document.getElementById( "rSet3_4" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:redSeriesCrefV" ).value =  (document.getElementById( "rSet3_5" ).value != '')   ? document.getElementById( "rSet3_5" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:blueSeriesCrefI" ).value =  (document.getElementById( "lSet3_1" ).value != '')  ? document.getElementById( "lSet3_1" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:blueSeriesCrefII" ).value =  (document.getElementById( "lSet3_2" ).value != '') ? document.getElementById( "lSet3_2" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:blueSeriesCrefIII" ).value =  (document.getElementById( "lSet3_3" ).value != '')? document.getElementById( "lSet3_3" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:blueSeriesCrefIV" ).value =  (document.getElementById( "lSet3_4" ).value != '') ? document.getElementById( "lSet3_4" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:blueSeriesCrefV" ).value =  (document.getElementById( "lSet3_5" ).value != '')  ? document.getElementById( "lSet3_5" ).value : 0.0;

        opener.document.getElementById( "main:offDuoFightAction:redSeriesDrefI" ).value =  (document.getElementById( "rSet4_1" ).value != '')   ? document.getElementById( "rSet4_1" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:redSeriesDrefII" ).value =  (document.getElementById( "rSet4_2" ).value != '')  ? document.getElementById( "rSet4_2" ).value : 0.0; 
        opener.document.getElementById( "main:offDuoFightAction:redSeriesDrefIII" ).value =  (document.getElementById( "rSet4_3" ).value != '') ? document.getElementById( "rSet4_3" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:redSeriesDrefIV" ).value =  (document.getElementById( "rSet4_4" ).value != '')  ? document.getElementById( "rSet4_4" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:redSeriesDrefV" ).value =  (document.getElementById( "rSet4_5" ).value != '')   ? document.getElementById( "rSet4_5" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:blueSeriesDrefI" ).value =  (document.getElementById( "lSet4_1" ).value != '')  ? document.getElementById( "lSet4_1" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:blueSeriesDrefII" ).value =  (document.getElementById( "lSet4_2" ).value != '') ? document.getElementById( "lSet4_2" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:blueSeriesDrefIII" ).value =  (document.getElementById( "lSet4_3" ).value != '')? document.getElementById( "lSet4_3" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:blueSeriesDrefIV" ).value =  (document.getElementById( "lSet4_4" ).value != '') ? document.getElementById( "lSet4_4" ).value : 0.0;
        opener.document.getElementById( "main:offDuoFightAction:blueSeriesDrefV" ).value =  (document.getElementById( "lSet4_5" ).value != '')  ? document.getElementById( "lSet4_5" ).value : 0.0;

        // zeit von oeffnen bis schlie�en der Uhr
        opener.document.getElementById( "main:offDuoFightAction:overallFightTime" ).value = 
                Math.floor( (new Date().getTime() - pageCallTime) / 1000 );
        opener.document.getElementById( "main:offDuoFightAction:fusengachi" ).value =  fusengachi;
        opener.document.getElementById( "main:offDuoFightAction:kikengachi" ).value =  kikengachi;

        if ( retWinner == 1 )
        {
            opener.document.getElementById( "main:offDuoFightAction:winner" ).value =  1;
            opener.document.getElementById( "main:offDuoFightAction:winner" ).selectedIndex = 1;
        }
        if ( retWinner == 0 )
        {
            opener.document.getElementById( "main:offDuoFightAction:winner" ).value =  2;
            opener.document.getElementById( "main:offDuoFightAction:winner" ).selectedIndex = 2;
        }
        if ( retWinner == -1 )
        {
            opener.document.getElementById( "main:offDuoFightAction:winner" ).value =  0;
            opener.document.getElementById( "main:offDuoFightAction:winner" ).selectedIndex = 0;
        }
    }
}


function giveBackFriendlyResult()
{
    try
    {
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:pointsRed" ).value =  retRightPoints;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:pointsBlue" ).value =  retLeftPoints;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:overtime" ).checked = overtime;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:pointsRedMax" ).disabled = !overtime;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:pointsBlueMax" ).disabled = !overtime;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:pointsRedMax" ).value =  retRightPointsMax;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:pointsBlueMax" ).value =  retLeftPointsMax;

        //KaRi Bewertungen speichern

        opener.document.getElementById( "main:adminFriendlyDuoFightAction:redSeriesArefI" ).value =  (document.getElementById( "rSet1_1" ).value != '')   ? document.getElementById( "rSet1_1" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:redSeriesArefII" ).value =  (document.getElementById( "rSet1_2" ).value != '')  ? document.getElementById( "rSet1_2" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:redSeriesArefIII" ).value =  (document.getElementById( "rSet1_3" ).value != '') ? document.getElementById( "rSet1_3" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:redSeriesArefIV" ).value =  (document.getElementById( "rSet1_4" ).value != '')  ? document.getElementById( "rSet1_4" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:redSeriesArefV" ).value =  (document.getElementById( "rSet1_5" ).value != '')   ? document.getElementById( "rSet1_5" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:blueSeriesArefI" ).value =  (document.getElementById( "lSet1_1" ).value != '')  ? document.getElementById( "lSet1_1" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:blueSeriesArefII" ).value =  (document.getElementById( "lSet1_2" ).value != '') ? document.getElementById( "lSet1_2" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:blueSeriesArefIII" ).value =  (document.getElementById( "lSet1_3" ).value != '')? document.getElementById( "lSet1_3" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:blueSeriesArefIV" ).value =  (document.getElementById( "lSet1_4" ).value != '') ? document.getElementById( "lSet1_4" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:blueSeriesArefV" ).value =  (document.getElementById( "lSet1_5" ).value != '')  ? document.getElementById( "lSet1_5" ).value : 0.0;

        opener.document.getElementById( "main:adminFriendlyDuoFightAction:redSeriesBrefI" ).value =  (document.getElementById( "rSet2_1" ).value != '')   ? document.getElementById( "rSet2_1" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:redSeriesBrefII" ).value =  (document.getElementById( "rSet2_2" ).value != '')  ? document.getElementById( "rSet2_2" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:redSeriesBrefIII" ).value =  (document.getElementById( "rSet2_3" ).value != '') ? document.getElementById( "rSet2_3" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:redSeriesBrefIV" ).value =  (document.getElementById( "rSet2_4" ).value != '')  ? document.getElementById( "rSet2_4" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:redSeriesBrefV" ).value =  (document.getElementById( "rSet2_5" ).value != '')   ? document.getElementById( "rSet2_5" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:blueSeriesBrefI" ).value =  (document.getElementById( "lSet2_1" ).value != '')  ? document.getElementById( "lSet2_1" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:blueSeriesBrefII" ).value =  (document.getElementById( "lSet2_2" ).value != '') ? document.getElementById( "lSet2_2" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:blueSeriesBrefIII" ).value =  (document.getElementById( "lSet2_3" ).value != '')? document.getElementById( "lSet2_3" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:blueSeriesBrefIV" ).value =  (document.getElementById( "lSet2_4" ).value != '') ? document.getElementById( "lSet2_4" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:blueSeriesBrefV" ).value =  (document.getElementById( "lSet2_5" ).value != '')  ? document.getElementById( "lSet2_5" ).value : 0.0;

        opener.document.getElementById( "main:adminFriendlyDuoFightAction:redSeriesCrefI" ).value =  (document.getElementById( "rSet3_1" ).value != '')   ? document.getElementById( "rSet3_1" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:redSeriesCrefII" ).value =  (document.getElementById( "rSet3_2" ).value != '')  ? document.getElementById( "rSet3_2" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:redSeriesCrefIII" ).value =  (document.getElementById( "rSet3_3" ).value != '') ? document.getElementById( "rSet3_3" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:redSeriesCrefIV" ).value =  (document.getElementById( "rSet3_4" ).value != '')  ? document.getElementById( "rSet3_4" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:redSeriesCrefV" ).value =  (document.getElementById( "rSet3_5" ).value != '')   ? document.getElementById( "rSet3_5" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:blueSeriesCrefI" ).value =  (document.getElementById( "lSet3_1" ).value != '')  ? document.getElementById( "lSet3_1" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:blueSeriesCrefII" ).value =  (document.getElementById( "lSet3_2" ).value != '') ? document.getElementById( "lSet3_2" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:blueSeriesCrefIII" ).value =  (document.getElementById( "lSet3_3" ).value != '')? document.getElementById( "lSet3_3" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:blueSeriesCrefIV" ).value =  (document.getElementById( "lSet3_4" ).value != '') ? document.getElementById( "lSet3_4" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:blueSeriesCrefV" ).value =  (document.getElementById( "lSet3_5" ).value != '')  ? document.getElementById( "lSet3_5" ).value : 0.0;

        opener.document.getElementById( "main:adminFriendlyDuoFightAction:redSeriesDrefI" ).value =  (document.getElementById( "rSet4_1" ).value != '')   ? document.getElementById( "rSet4_1" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:redSeriesDrefII" ).value =  (document.getElementById( "rSet4_2" ).value != '')  ? document.getElementById( "rSet4_2" ).value : 0.0; 
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:redSeriesDrefIII" ).value =  (document.getElementById( "rSet4_3" ).value != '') ? document.getElementById( "rSet4_3" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:redSeriesDrefIV" ).value =  (document.getElementById( "rSet4_4" ).value != '')  ? document.getElementById( "rSet4_4" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:redSeriesDrefV" ).value =  (document.getElementById( "rSet4_5" ).value != '')   ? document.getElementById( "rSet4_5" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:blueSeriesDrefI" ).value =  (document.getElementById( "lSet4_1" ).value != '')  ? document.getElementById( "lSet4_1" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:blueSeriesDrefII" ).value =  (document.getElementById( "lSet4_2" ).value != '') ? document.getElementById( "lSet4_2" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:blueSeriesDrefIII" ).value =  (document.getElementById( "lSet4_3" ).value != '')? document.getElementById( "lSet4_3" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:blueSeriesDrefIV" ).value =  (document.getElementById( "lSet4_4" ).value != '') ? document.getElementById( "lSet4_4" ).value : 0.0;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:blueSeriesDrefV" ).value =  (document.getElementById( "lSet4_5" ).value != '')  ? document.getElementById( "lSet4_5" ).value : 0.0;
        // zeit von oeffnen bis schlie�en der Uhr
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:overallFightTime" ).value = 
                Math.floor( (new Date().getTime() - pageCallTime) / 1000 );
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:fusengachi" ).value =  fusengachi;
        opener.document.getElementById( "main:adminFriendlyDuoFightAction:kikengachi" ).value =  kikengachi;

        if ( retWinner == 1 )
        {
            opener.document.getElementById( "main:adminFriendlyDuoFightAction:winner" ).value =  1;
            opener.document.getElementById( "main:adminFriendlyDuoFightAction:winner" ).selectedIndex = 1;
        }
        if ( retWinner == 0 )
        {
            opener.document.getElementById( "main:adminFriendlyDuoFightAction:winner" ).value =  2;
            opener.document.getElementById( "main:adminFriendlyDuoFightAction:winner" ).selectedIndex = 2;
        }
        if ( retWinner == -1 )
        {
            opener.document.getElementById( "main:adminFriendlyDuoFightAction:winner" ).value =  0;
            opener.document.getElementById( "main:adminFriendlyDuoFightAction:winner" ).selectedIndex = 0;
        }
    }
    catch( event )
    {
    	opener.document.getElementById( "main:offFriendlyDuoFightAction:pointsRed" ).value =  retRightPoints;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:pointsBlue" ).value =  retLeftPoints;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:overtime" ).checked = overtime;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:pointsRedMax" ).disabled = !overtime;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:pointsBlueMax" ).disabled = !overtime;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:pointsRedMax" ).value =  retRightPointsMax;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:pointsBlueMax" ).value =  retLeftPointsMax;

        //KaRi Bewertungen speichern

        opener.document.getElementById( "main:offFriendlyDuoFightAction:redSeriesArefI" ).value =  (document.getElementById( "rSet1_1" ).value != '')   ? document.getElementById( "rSet1_1" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:redSeriesArefII" ).value =  (document.getElementById( "rSet1_2" ).value != '')  ? document.getElementById( "rSet1_2" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:redSeriesArefIII" ).value =  (document.getElementById( "rSet1_3" ).value != '') ? document.getElementById( "rSet1_3" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:redSeriesArefIV" ).value =  (document.getElementById( "rSet1_4" ).value != '')  ? document.getElementById( "rSet1_4" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:redSeriesArefV" ).value =  (document.getElementById( "rSet1_5" ).value != '')   ? document.getElementById( "rSet1_5" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:blueSeriesArefI" ).value =  (document.getElementById( "lSet1_1" ).value != '')  ? document.getElementById( "lSet1_1" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:blueSeriesArefII" ).value =  (document.getElementById( "lSet1_2" ).value != '') ? document.getElementById( "lSet1_2" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:blueSeriesArefIII" ).value =  (document.getElementById( "lSet1_3" ).value != '')? document.getElementById( "lSet1_3" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:blueSeriesArefIV" ).value =  (document.getElementById( "lSet1_4" ).value != '') ? document.getElementById( "lSet1_4" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:blueSeriesArefV" ).value =  (document.getElementById( "lSet1_5" ).value != '')  ? document.getElementById( "lSet1_5" ).value : 0.0;

        opener.document.getElementById( "main:offFriendlyDuoFightAction:redSeriesBrefI" ).value =  (document.getElementById( "rSet2_1" ).value != '')   ? document.getElementById( "rSet2_1" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:redSeriesBrefII" ).value =  (document.getElementById( "rSet2_2" ).value != '')  ? document.getElementById( "rSet2_2" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:redSeriesBrefIII" ).value =  (document.getElementById( "rSet2_3" ).value != '') ? document.getElementById( "rSet2_3" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:redSeriesBrefIV" ).value =  (document.getElementById( "rSet2_4" ).value != '')  ? document.getElementById( "rSet2_4" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:redSeriesBrefV" ).value =  (document.getElementById( "rSet2_5" ).value != '')   ? document.getElementById( "rSet2_5" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:blueSeriesBrefI" ).value =  (document.getElementById( "lSet2_1" ).value != '')  ? document.getElementById( "lSet2_1" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:blueSeriesBrefII" ).value =  (document.getElementById( "lSet2_2" ).value != '') ? document.getElementById( "lSet2_2" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:blueSeriesBrefIII" ).value =  (document.getElementById( "lSet2_3" ).value != '')? document.getElementById( "lSet2_3" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:blueSeriesBrefIV" ).value =  (document.getElementById( "lSet2_4" ).value != '') ? document.getElementById( "lSet2_4" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:blueSeriesBrefV" ).value =  (document.getElementById( "lSet2_5" ).value != '')  ? document.getElementById( "lSet2_5" ).value : 0.0;

        opener.document.getElementById( "main:offFriendlyDuoFightAction:redSeriesCrefI" ).value =  (document.getElementById( "rSet3_1" ).value != '')   ? document.getElementById( "rSet3_1" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:redSeriesCrefII" ).value =  (document.getElementById( "rSet3_2" ).value != '')  ? document.getElementById( "rSet3_2" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:redSeriesCrefIII" ).value =  (document.getElementById( "rSet3_3" ).value != '') ? document.getElementById( "rSet3_3" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:redSeriesCrefIV" ).value =  (document.getElementById( "rSet3_4" ).value != '')  ? document.getElementById( "rSet3_4" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:redSeriesCrefV" ).value =  (document.getElementById( "rSet3_5" ).value != '')   ? document.getElementById( "rSet3_5" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:blueSeriesCrefI" ).value =  (document.getElementById( "lSet3_1" ).value != '')  ? document.getElementById( "lSet3_1" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:blueSeriesCrefII" ).value =  (document.getElementById( "lSet3_2" ).value != '') ? document.getElementById( "lSet3_2" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:blueSeriesCrefIII" ).value =  (document.getElementById( "lSet3_3" ).value != '')? document.getElementById( "lSet3_3" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:blueSeriesCrefIV" ).value =  (document.getElementById( "lSet3_4" ).value != '') ? document.getElementById( "lSet3_4" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:blueSeriesCrefV" ).value =  (document.getElementById( "lSet3_5" ).value != '')  ? document.getElementById( "lSet3_5" ).value : 0.0;

        opener.document.getElementById( "main:offFriendlyDuoFightAction:redSeriesDrefI" ).value =  (document.getElementById( "rSet4_1" ).value != '')   ? document.getElementById( "rSet4_1" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:redSeriesDrefII" ).value =  (document.getElementById( "rSet4_2" ).value != '')  ? document.getElementById( "rSet4_2" ).value : 0.0; 
        opener.document.getElementById( "main:offFriendlyDuoFightAction:redSeriesDrefIII" ).value =  (document.getElementById( "rSet4_3" ).value != '') ? document.getElementById( "rSet4_3" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:redSeriesDrefIV" ).value =  (document.getElementById( "rSet4_4" ).value != '')  ? document.getElementById( "rSet4_4" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:redSeriesDrefV" ).value =  (document.getElementById( "rSet4_5" ).value != '')   ? document.getElementById( "rSet4_5" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:blueSeriesDrefI" ).value =  (document.getElementById( "lSet4_1" ).value != '')  ? document.getElementById( "lSet4_1" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:blueSeriesDrefII" ).value =  (document.getElementById( "lSet4_2" ).value != '') ? document.getElementById( "lSet4_2" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:blueSeriesDrefIII" ).value =  (document.getElementById( "lSet4_3" ).value != '')? document.getElementById( "lSet4_3" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:blueSeriesDrefIV" ).value =  (document.getElementById( "lSet4_4" ).value != '') ? document.getElementById( "lSet4_4" ).value : 0.0;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:blueSeriesDrefV" ).value =  (document.getElementById( "lSet4_5" ).value != '')  ? document.getElementById( "lSet4_5" ).value : 0.0;

        // zeit von oeffnen bis schlie�en der Uhr
        opener.document.getElementById( "main:offFriendlyDuoFightAction:overallFightTime" ).value = 
                Math.floor( (new Date().getTime() - pageCallTime) / 1000 );
        opener.document.getElementById( "main:offFriendlyDuoFightAction:fusengachi" ).value =  fusengachi;
        opener.document.getElementById( "main:offFriendlyDuoFightAction:kikengachi" ).value =  kikengachi;

        if ( retWinner == 1 )
        {
            opener.document.getElementById( "main:offFriendlyDuoFightAction:winner" ).value =  1;
            opener.document.getElementById( "main:offFriendlyDuoFightAction:winner" ).selectedIndex = 1;
        }
        if ( retWinner == 0 )
        {
            opener.document.getElementById( "main:offFriendlyDuoFightAction:winner" ).value =  2;
            opener.document.getElementById( "main:offFriendlyDuoFightAction:winner" ).selectedIndex = 2;
        }
        if ( retWinner == -1 )
        {
            opener.document.getElementById( "main:offFriendlyDuoFightAction:winner" ).value =  0;
            opener.document.getElementById( "main:offFriendlyDuoFightAction:winner" ).selectedIndex = 0;
        }
    }
}

function doResult()
{
    retLeftPoints = getSetPoints( 'left' );
    retRightPoints = getSetPoints( 'right' );

    if ( retLeftPoints == retRightPoints && retRightPoints != 0 )
    {
        retLeftPointsMax = leftPoints + retLeftPoints;
        retRightPointsMax = rightPoints + retRightPoints;
        overtime = true;
        if ( retLeftPointsMax < retRightPointsMax )
        {
            retWinner = 1;
        }
        if ( retLeftPointsMax > retRightPointsMax )
        {
            retWinner = 0;
        }
    }
    else
    {
        if ( retLeftPoints < retRightPoints )
        {
            retWinner = 1;
        }
        if ( retLeftPoints > retRightPoints )
        {
            retWinner = 0;
        }
    }
    checkFusenKikenResult();
}

function checkFusenKikenResult()
{
    if ( (isActiveKiken["left"]) && (isActiveKiken["right"]) )
    {
        kikengachi = -1;
    }
    if ( (isActiveKiken["left"]) && (!isActiveKiken["right"]) )
    {
        kikengachi = 2;
    }
    if ( (!isActiveKiken["left"]) && (isActiveKiken["right"]) )
    {
        kikengachi = 1;
    }
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
            retRightPoints = 12;
            retWinner = 1;
            return;

        }
        if ( (!isActiveFusen["left"]) && (isActiveFusen["right"]) )
        {
            fusengachi = 1;	// Rot hat Fusen/Kiken
            fusengachi_winner = 2;
            retLeftPoints = 12;
            retRightPoints = 0;
            retWinner = 0;
            return;
        }
    }
    if ( (isActiveKiken["left"]) || (isActiveKiken["right"]) )
    {
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
            retRightPoints = 12;
            retWinner = 1;
            return;
        }
        if ( fusengachi_winner == 2 )
        {
            retLeftPoints = 12;
            retRightPoints = 0;
            retWinner = 0;
            return;
        }
    }
}

//========================================================================================
// clock controls
//========================================================================================
function switchClock( clockType )
{
	if( document.getElementById( clockType ).style.display == "none")
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
    if ( !isActiveClock[clockType] && (milliseconds[clockType] + (value * 1000)) < maxTimeinMilliseconds )
    {
        milliseconds[clockType] = milliseconds[clockType] + (value * 1000);
        updateClock( clockType );
    }
}

function decreaseClock( value, clockType )
{
    if ( !isActiveClock[clockType] && (milliseconds[clockType] - value * 1000) >= 0 )
    {
        milliseconds[clockType] = milliseconds[clockType] - (value * 1000);
        updateClock( clockType );
    }
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
    document.getElementById( clockType + '.minutes.1' ).src =
            "../images/clock/" + Math.floor( getMinutes( clockType ) / 10 ) +
                    ((clockType == "mainClock") ? "_large.png" : ".png");
    document.getElementById( clockType + '.minutes.2' ).src =
            "../images/clock/" + getMinutes( clockType ) % 10 + ((clockType == "mainClock") ? "_large.png" : ".png");
    document.getElementById( clockType + '.seconds.1' ).src =
            "../images/clock/" + Math.floor( getSeconds( clockType ) / 10 ) +
                    ((clockType == "mainClock") ? "_large.png" : ".png");
    document.getElementById( clockType + '.seconds.2' ).src =
            "../images/clock/" + getSeconds( clockType ) % 10 + ((clockType == "mainClock") ? "_large.png" : ".png");
    if ( getWholeSeconds( clockType ) >= injury_time )
    {
        startStopClock( clockType );
    }
    if ( isActiveClock[clockType] )
    {
        activeClock[clockType] = window.setTimeout( ("updateClock('" + clockType + "')"), clockUpdateIntervall );
    }
}

function startStopClock( clockType )
{
    if ( isActiveClock[clockType] )
    {
        milliseconds[clockType] = new Date().getTime() - startTime[clockType] + milliseconds[clockType];
        window.clearTimeout( activeClock[clockType] );
    }
    else
    {
        if ( (clockType == "leftInjuryClock") && (getWholeSeconds( clockType ) >= injury_time ) )
        {
            return;
        }
        if ( (clockType == "rightInjuryClock") && (getWholeSeconds( clockType ) >= injury_time ) )
        {
            return;
        }
        startTime[clockType] = new Date().getTime();
        activeClock[clockType] = window.setTimeout( ("updateClock('" + clockType + "')"), clockUpdateIntervall );
    }

    if (isActiveClock[clockType])
	{
		color = hiLightColorStop;
	} else
	{
		color = hiLightColor;
	}
    
    isActiveClock[clockType] = !isActiveClock[clockType];
    document.getElementById( clockType + "Border" ).style.backgroundColor = color;
}

function resetClock( side )
{
    if ( side == "mainClock" )
    {
        if ( !isActiveClock["mainClock"] )
        {
            milliseconds["mainClock"] = 0;
            updateClock( "mainClock" );
        }
    }
    else
    {
        if ( !isActiveClock[clockDisplayed [side]] )
        {
            milliseconds[clockDisplayed [side]] = 0;
            updateClock( clockDisplayed [side] );
        }
    }
}

var clockDisplayed = new Array();
clockDisplayed ["left"] = "leftInjuryClock";
clockDisplayed ["right"] = "rightInjuryClock";

var activeClock = new Array();
activeClock["mainClock"] = 0;
activeClock["leftInjuryClock"] = 0;
activeClock["rightInjuryClock"] = 0;

var milliseconds = new Array();
milliseconds["mainClock"] = 0;
milliseconds["leftInjuryClock"] = 0;
milliseconds["rightInjuryClock"] = 0;

var startTime = new Array();
startTime["mainClock"] = 0;
startTime["leftInjuryClock"] = 0;
startTime["rightInjuryClock"] = 0;

var isActiveClock = new Array();
isActiveClock["mainClock"] = false;
isActiveClock["leftInjuryClock"] = false;
isActiveClock["rightInjuryClock"] = false;

//========================================================================================
// Point controls
//========================================================================================

var leftPoints = 0;
var rightPoints = 0;

function getPoints( side )
{
    if ( side == "left" )
    {
        return (leftPoints + getSetPoints( side ));
    }
    else
    {
        return (rightPoints + getSetPoints( side ));
    }
}

function increasePoints( value, side )
{
    if ( side == "left" )
    {
        if ( leftPoints + parseFloat( value ) < 999 )
        {
            leftPoints = (leftPoints + parseFloat( value ));
        }
    }
    else
    {
        if ( rightPoints + parseFloat( value ) < 999 )
        {
            rightPoints = (rightPoints + parseFloat( value ));
        }
    }
    updatePoints( side );
}

function decreasePoints( value, side )
{
    if ( side == "left" )
    {
        leftPoints -= value;
        if ( leftPoints < 0 )
        {
            leftPoints = 0;
        }
    }
    else
    {
        rightPoints -= value;
        if ( rightPoints < 0 )
        {
            rightPoints = 0;
        }
    }
    updatePoints( side );
}


var pointFieldRight = new Array();
pointFieldRight["rSet1_1"] = "";
pointFieldRight["rSet1_2"] = "";
pointFieldRight["rSet1_3"] = "";
pointFieldRight["rSet1_4"] = "";
pointFieldRight["rSet1_5"] = "";

pointFieldRight["rSet2_1"] = "";
pointFieldRight["rSet2_2"] = "";
pointFieldRight["rSet2_3"] = "";
pointFieldRight["rSet2_4"] = "";
pointFieldRight["rSet2_5"] = "";

pointFieldRight["rSet3_1"] = "";
pointFieldRight["rSet3_2"] = "";
pointFieldRight["rSet3_3"] = "";
pointFieldRight["rSet3_4"] = "";
pointFieldRight["rSet3_5"] = "";

pointFieldRight["rSet4_1"] = "";
pointFieldRight["rSet4_2"] = "";
pointFieldRight["rSet4_3"] = "";
pointFieldRight["rSet4_4"] = "";
pointFieldRight["rSet4_5"] = "";

var pointFieldLeft = new Array();
pointFieldLeft["lSet1_1"] = "";
pointFieldLeft["lSet1_2"] = "";
pointFieldLeft["lSet1_3"] = "";
pointFieldLeft["lSet1_4"] = "";
pointFieldLeft["lSet1_5"] = "";

pointFieldLeft["lSet2_1"] = "";
pointFieldLeft["lSet2_2"] = "";
pointFieldLeft["lSet2_3"] = "";
pointFieldLeft["lSet2_4"] = "";
pointFieldLeft["lSet2_5"] = "";

pointFieldLeft["lSet3_1"] = "";
pointFieldLeft["lSet3_2"] = "";
pointFieldLeft["lSet3_3"] = "";
pointFieldLeft["lSet3_4"] = "";
pointFieldLeft["lSet3_5"] = "";

pointFieldLeft["lSet4_1"] = "";
pointFieldLeft["lSet4_2"] = "";
pointFieldLeft["lSet4_3"] = "";
pointFieldLeft["lSet4_4"] = "";
pointFieldLeft["lSet4_5"] = "";

/*
* sets point to array and let calculate
*
*
*/
function setDuoPoints( side,id ){
	
	//hanle input for curent field before calculation
	document.getElementById(id).value = document.getElementById(id).value.replace( /,/, "." );
		
	if(isNaN(  parseFloat(document.getElementById(id).value) ) )
	{
		document.getElementById(id).value = '';
		(side.substr( 0, 1 )=="l")? pointFieldLeft[id]="":pointFieldRight[id]="";
		clearHighlightPoints(id);
	}
	else
	{
		// not .0  or .5
		if ( parseFloat( document.getElementById(id).value  * 10) % 5 != 0 )
		{
			document.getElementById(id).value = '';
			(side.substr( 0, 1 )=="l")? pointFieldLeft[id]="":pointFieldRight[id]="";
			clearHighlightPoints(id);
		}   
		//handle short input for .5 entries
		if(document.getElementById(id).value >  10 && 
			document.getElementById(id).value < 100 && 
			document.getElementById(id).value % 11 ==0	)
		{
			document.getElementById(id).value = document.getElementById(id).value / 11 + ".5";
		}	
		
		if ( document.getElementById(id).value < 1 || document.getElementById(id).value > 10 )
		{
			document.getElementById(id).value = '';
			(side.substr( 0, 1 )=="l")? pointFieldLeft[id]="":pointFieldRight[id]="";
			clearHighlightPoints(id);
		}		
		(side.substr( 0, 1 )=="l")? pointFieldLeft[id] =document.getElementById(id).value : 
									pointFieldRight[id]=document.getElementById(id).value ;
	}	
}


function getSetPoints( side )
{	
    setPoints = 0;
	for ( var j = 1; j < 5; j++ )
	{
		count = 0;
		SeriesPoints = 0;
		biggestPoints = 0;
		smallesPoints = 10;
		singlePointsInSeries=0;
		for ( var i = 1; i < 6; i++ )
		{			
			if("" ==((side.substr( 0, 1 )=="l")? pointFieldLeft["lSet"+j+"_"+i]:pointFieldRight["rSet"+j+"_"+i]))
			{
				continue;
			}
			else
			{
				singlePointsInSeries=parseFloat((side.substr( 0, 1 )=="l")? pointFieldLeft["lSet"+j+"_"+i]:pointFieldRight["rSet"+j+"_"+i]);
				singlePointsInSeries= parseInt(singlePointsInSeries*10)/10.0;
				SeriesPoints +=singlePointsInSeries;
				if ( biggestPoints <singlePointsInSeries )
				{
					biggestPoints =singlePointsInSeries;
				}
				if ( smallesPoints > singlePointsInSeries )
				{
					smallesPoints = singlePointsInSeries;
				}
				count++;
			}
			
			if ( count == 5 )
			{
				setPoints += (SeriesPoints - biggestPoints - smallesPoints);
				highlightPoints( side, j, biggestPoints, smallesPoints );
			}
		}
	}
	return setPoints;
}

function highlightPoints( side, Series, biggestPoint, smallestPoint )
{
    biggestFound = false;
    smallestFound = false;
    
    for ( var i = 1; i < 6; i++ )
    {		
        var e = document.getElementById(side.substr( 0, 1 )+"Set" + Series +"_"+i);
        e.className = 'setsHigh_'+side;
        if ( e.value != biggestPoint && e.value != smallestPoint )
        {
            e.className = 'sets';
        }
        if ( e.value == biggestPoint && e.value != smallestPoint )
        {
            if ( biggestFound )
            {
                e.className = 'sets';
            }
            else
            {
                biggestFound = true;
            }
            continue;
        }

        if ( e.value != biggestPoint && e.value == smallestPoint )
        {
            if ( smallestFound )
            {
                e.className = 'sets';
            }
            else
            {
                smallestFound = true;
            }
            continue;
        }

        if ( e.value == biggestPoint && e.value == smallestPoint )
        {
            if ( biggestFound && smallestFound )
            {
                e.className = 'sets';
            }
            if ( biggestFound && !smallestFound )
            {
                smallestFound = true;
            }
            if ( !biggestFound && smallestFound )
            {
                biggestFound = true;
            }
            if ( !biggestFound && !smallestFound )
            {
                biggestFound = true;
            }
        }

    }
    resizeSeriesPoints();
}
		
function clearHighlightPoints(id)
{
	for ( var i = 1; i < 6; i++ )
    {		
        var e = document.getElementById(id.substr( 0, 1 )+"Set" + id.substr( 4, 1 ) +"_"+i);
        e.className = 'sets';        
	}
}


function updatePoints( side )
{
	try{
		setDuoPoints( side,arguments.callee.caller.arguments[0].currentTarget.id );
	}
	catch(event)
	{
		;
	}
    DuoPoints = getPoints( side );

	if (DuoPoints >= 100)
		document.getElementById( side + "Points.2" ).src =  "../images/clock/" + Math.floor( DuoPoints / 100 ) + "_x_large.png";
	if (DuoPoints < 100)
		document.getElementById( side + "Points.2" ).src =  "../images/clock/bl_x_large.png";
	
	if (DuoPoints >= 10)
		document.getElementById( side + "Points.1" ).src = "../images/clock/" + Math.floor( (DuoPoints % 100) / 10 ) + "_x_large.png";
	if (DuoPoints < 10)
		document.getElementById( side + "Points.1" ).src = "../images/clock/bl_x_large.png";
		
    document.getElementById( side + "Points.0" ).src = "../images/clock/" + Math.floor( (DuoPoints % 100) % 10 ) + "_x_large.png";
    document.getElementById( side + "Points.S" ).src = "../images/clock/kommaseparator_x_large.png";
    document.getElementById( side + "Points.-1" ).src = "../images/clock/" + ((DuoPoints * 10) % 10) + "_x_large.png";

    if ( isActiveFusen["left"] )
    {
        document.getElementById( "leftPoints.2" ).src = "../images/clock/bl_x_large.png";
        document.getElementById( "leftPoints.1" ).src = "../images/clock/bl_x_large.png";
        document.getElementById( "leftPoints.0" ).src = "../images/clock/F_x_large.png";
        document.getElementById( "leftPoints.S" ).src = "../images/clock/bl_sep_x_large.png";
        document.getElementById( "leftPoints.-1" ).src = "../images/clock/bl_x_large.png";
    }
    if ( isActiveFusen["right"] )
    {
        document.getElementById( "rightPoints.2" ).src = "../images/clock/bl_x_large.png";
        document.getElementById( "rightPoints.1" ).src = "../images/clock/bl_x_large.png";
        document.getElementById( "rightPoints.0" ).src = "../images/clock/F_x_large.png";
        document.getElementById( "rightPoints.S" ).src = "../images/clock/bl_sep_x_large.png";
        document.getElementById( "rightPoints.-1" ).src = "../images/clock/bl_x_large.png";
    }
    if ( isActiveKiken["left"] )
    {
        document.getElementById( "leftPoints.2" ).src = "../images/clock/bl_x_large.png";
        document.getElementById( "leftPoints.1" ).src = "../images/clock/bl_x_large.png";
        document.getElementById( "leftPoints.0" ).src = "../images/clock/injury_x_large.png";
        document.getElementById( "leftPoints.S" ).src = "../images/clock/bl_sep_x_large.png";
        document.getElementById( "leftPoints.-1" ).src = "../images/clock/bl_x_large.png";
    }
    if ( isActiveKiken["right"] )
    {
        document.getElementById( "rightPoints.2" ).src = "../images/clock/bl_x_large.png";
        document.getElementById( "rightPoints.1" ).src = "../images/clock/bl_x_large.png";
        document.getElementById( "rightPoints.0" ).src = "../images/clock/injury_x_large.png";
        document.getElementById( "rightPoints.S" ).src = "../images/clock/bl_sep_x_large.png";
        document.getElementById( "rightPoints.-1" ).src = "../images/clock/bl_x_large.png";
    }

    document.getElementById( side + "PointsBorderTable" ).style.backgroundColor = hiLightColor;
}
//========================================================================================


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

function activateDeactivateFusenKiken( side )
{
    if ( (getSetPoints( "left" ) == 0) && (getSetPoints( "right" ) == 0) || (isActiveFusen[side]) )
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
            document.getElementById( "fusen_kiken_" + side ).src = "../images/clock/fusen_kiken.png";
            blinkFusen[side] = false;
        }
        else
        {
            if ( isActiveClock["mainClock"] )
            {
                return;
            }			// Aufgabe nur moeglich wenn HKZ nicht laeuft
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
        if ( isActiveKiken[side] )
        {
            if ( isActiveFusen[side] )
            {
                return;
            }
            if ( side == "left" )
            {
                window.clearInterval( blinkerFusenLeft );
            }
            else
            {
                window.clearInterval( blinkerFusenRight );
            }
            document.getElementById( "fusen_kiken_" + side ).src = "../images/clock/fusen_kiken.png";
            blinkFusen[side] = false;
        }
        else
        {
            if ( isActiveClock["mainClock"] )
            {
                return;
            }			// Aufgabe nur moeglich wenn HKZ nicht laeuft
            document.getElementById( "fusen_kiken_" + side ).src ="../images/clock/fusen_kiken_large.png";
            if ( side == "left" )
            {
                blinkerFusenLeft = window.setInterval( "blinkFusenKiken('left')", 1000 );
            }
            else
            {
                blinkerFusenRight = window.setInterval( "blinkFusenKiken('right')", 1000 );
            }
            if ( !isActiveDuoClocks )
            {
                // handleDuoClocks(); Ausblenden weil sonst Grafikfehler
            }
        }
        isActiveKiken[side] = !isActiveKiken[side];
        if ( isActiveKiken["left"] && isActiveKiken["right"] )
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
            return
        }
        if ( fusengachi_winner == 1 )
        {
            setKikenWinner( "left" );
            return
        }
        if ( fusengachi_winner == 2 )
        {
            setKikenWinner( "none" );
            return
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
    document.getElementById( "div_mainClock" ).style.display = "none";
    document.getElementById( "div_KikenWinner" ).style.display = "block";
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
    document.getElementById( "div_mainClock" ).style.display = "block";
    document.getElementById( "div_KikenWinner" ).style.display = "none";
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

//========================================================================================

//========================================================================================
//CookieSetup fuer Verletzungszeit, Haltegriffzeit, Kampfzeit - 2007 by Sebastian Dressel
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


//========================================================================================
// Anzeigenwechsel - 2007 by Sebastian Dressel
//========================================================================================
var isActiveDuoClocks = false;
var isActiveAnzeige = false;

function handleAnzeige( displayForm )
{
	if (videoOn== true){		
		try{
			jjw_startRecording();
		}
		catch (e){}
	}
	
    // if (isActiveAnzeige)
    // nur in FightingAnzeige
    // user_input = user_input + "DSP" + displayForm + ";";
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
        isActiveAnzeige = !isActiveAnzeige;
    }
}

function handleDuoClocks()
{
    if ( isActiveDuoClocks )
    {
        if ( isActiveClock["leftInjuryClock"] || isActiveClock["rightInjuryClock"] || isActiveFusen["left"] ||
                isActiveFusen["right"] || isActiveKiken["left"] || isActiveKiken["right"] )
        {
            return;
        }
        document.getElementById( "duoClocks" ).style.visibility = "hidden";
        document.getElementById( "duoClocks" ).style.display = "none";
    }
    else
    {
        if ( !isActiveAnzeige )
        {
            return;
        }
        document.getElementById( "duoClocks" ).style.visibility = "visible";
        document.getElementById( "duoClocks" ).style.display = "block";
    }
    isActiveDuoClocks = !isActiveDuoClocks;
}

//========================================================================================

//========================================================================================
// Key Events
//========================================================================================
function myKeyDownFunction( event )
{
    if ( event.keyCode == 32 && !event.shiftKey )
    {
        startStopClock( "mainClock" );
    } 					//leer			start / stopp Hauptuhr
    if ( event.keyCode == 32 && event.shiftKey && event.ctrlKey )
    {
        resetClock( "mainClock" );
    } 		//SHIFT + CTRL + leer	Hauptuhr resetten
    if ( event.keyCode == 70 && !event.shiftKey )
    {
        startStopClock( clockDisplayed ["left"] );
    }  		// f			start/stopp linke Uhr
    if ( event.keyCode == 74 && !event.shiftKey )
    {
        startStopClock( clockDisplayed ["right"] );
    }  		// j			start/stopp rechte Uhr

    if ( event.keyCode == 70 && event.shiftKey )
    {
        resetClock( "left" );
    }  								// SHIFT + f	RESET linke (BLAU) Uhr
    if ( event.keyCode == 74 && event.shiftKey )
    {
        resetClock( "right" );
    }  								// SHIFT + j	RESET rechte (ROT) Uhr

    if ( event.keyCode == 86 && event.shiftKey )
    {
        activateDeactivateFusenKiken( "left" );
    }				// SHIFT + v	Fusengachi / Kikengachi BLAU
    if ( event.keyCode == 78 && event.shiftKey )
    {
        activateDeactivateFusenKiken( "right" );
    }				// SHIFT + n	Fusengachi / Kikengachi ROT

    if ( event.keyCode == 81 && !event.shiftKey )
    {
        increasePoints( 0.5, "left" );
    }  					// q			Punkte links + 0.5
    if ( event.keyCode == 81 && event.shiftKey )
    {
        decreasePoints( 0.5, "left" );
    }  					// SHIFT + q		Punkte links - 0.5
    if ( event.keyCode == 87 && !event.shiftKey )
    {
        increasePoints( 1, "left" );
    }  						// w			Punkte links + 1
    if ( event.keyCode == 87 && event.shiftKey )
    {
        decreasePoints( 1, "left" );
    }  					// SHIFT + w		Punkte links - 1
    if ( event.keyCode == 69 && !event.shiftKey )
    {
        increasePoints( 10, "left" );
    }   					// e			Punkte links + 10
    if ( event.keyCode == 69 && event.shiftKey )
    {
        decreasePoints( 10, "left" );
    }   					// SHIFT + e		Punkte links - 10

    if ( event.keyCode == 80 && !event.shiftKey )
    {
        increasePoints( 0.5, "right" );
    }  					// p			Punkte rechts + 0.5
    if ( event.keyCode == 80 && event.shiftKey )
    {
        decreasePoints( 0.5, "right" );
    }  					// SHIFT + p		Punkte rechts - 0.5
    if ( event.keyCode == 79 && !event.shiftKey )
    {
        increasePoints( 1, "right" );
    }  						// o			Punkte rechts + 1
    if ( event.keyCode == 79 && event.shiftKey )
    {
        decreasePoints( 1, "right" );
    }  						// SHIFT + o		Punkte rechts - 1
    if ( event.keyCode == 73 && !event.shiftKey )
    {
        increasePoints( 10, "right" );
    }  					// i			Punkte rechts + 10
    if ( event.keyCode == 73 && event.shiftKey )
    {
        decreasePoints( 10, "right" );
    }  					// SHIFT + i		Punkte rechts - 10

    if ( event.keyCode == 191 && !event.shiftKey )
    {
        handleAnzeige( "0" );
    }  						// #			WettkampfAnzeige / Aufrufanzeige wird angezeigt
    if ( event.keyCode == 52 && event.shiftKey )
    {
        handleAnzeige( "1" );
    }							// SHIFT + 4		Wettkampfanzeige angezeigt
    if ( event.keyCode == 53 && event.shiftKey )
    {
        handleAnzeige( "2" );
    }							// SHIFT + 5		PreparationAnzeige anzeigen

    if ( (event.keyCode == 61 || event.keyCode == 107) && !event.shiftKey )
    {
        handleDuoClocks();
    }  								// +			DuoClocks werden ein-/ausgeblendet

    if ( event.keyCode == 39 && !event.shiftKey )
    {
        handleKikenWinner( "right" );
    }						// -->			Gewinner Kikengachi weiterschalten
    if ( event.keyCode == 37 && !event.shiftKey )
    {
        handleKikenWinner( "left" );
    }					// <--			Gewinner Kikengachi weiterschalten

    if ( event.keyCode == 113 && event.shiftKey && event.ctrlKey )
    {
        shutClock();
    } 					//SHIFT + CTRL + F2			Anzeige schliessen und Punkte uebergeben
    if ( event.keyCode == 115 && event.altKey )
    {
        shutClock();
    }
}

function setOriginalBackgroundColor( event )
{
    document.getElementById( "mainClockBorder" ).style.backgroundColor = "#FFFFFF";
    document.getElementById( "rightInjuryClockBorder" ).style.backgroundColor = "#FFFFFF";
    document.getElementById( "leftInjuryClockBorder" ).style.backgroundColor = "#FFFFFF";

    document.getElementById( "leftPointsBorderTable" ).style.backgroundColor = "#FFFFFF";
    document.getElementById( "rightPointsBorderTable" ).style.backgroundColor = "#FFFFFF";
}

document.onkeydown = myKeyDownFunction;
document.onkeyup = setOriginalBackgroundColor;
document.onmouseup = setOriginalBackgroundColor;

<!-- Die maximale Kampfzeit, Haltegriffzeit und Verletzungszeit werden ins Cookie geschrieben -->
function startfight( _altersklasse, _fightingtime, _fightingOverTime, _holdingtime, _injurytime )
{
    var altersklasse = _altersklasse;
    init();   
    injury_time =  _injurytime;
   
    <!-- dringend erforderlich!! -->
    //handleAnzeige();
    //document.rightSets.rSet1_1.focus();
}

<!-- Altersklasse, Geschlecht und Gewichtsklasse werden im Aufruf angezeigt -->
function showAK( _gwklasse )
{

    gwklasse = _gwklasse;
    document.getElementById( "loadfield_weight_1" ).innerHTML = gwklasse;
}
