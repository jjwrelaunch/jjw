/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : jjw_generals.js
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 21:54:41
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

function jjw_setFocus( id )
{
    var element = document.getElementById( id );
    if ( element && element.focus )
    {
        element.focus();
    }
}

function jjw_resetValue( id )
{
    var element = document.getElementById( id );
    element.value = '';
}

function openFightingClockWindow( fightId )
{
    if (screen.width < 1200)
    	win = window.open( "", "", 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=1,width=1020,height=768,left=0,top=0' );
    else
    	win = window.open( "", "", 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=1,width='+(screen.width-20)+',height='+(screen.height-20)+',left=0,top=0' );
    win.moveTo(0,0);
    win.resizeTo(screen.width,screen.height);
    win.document.open();
    win.document.writeln( "<html>" );
    win.document.writeln( "<script LANGUAGE=\"JavaScript\"> function popUp(URL) { location=URL;}" );
    win.document.writeln( "<\/script>" );
    win.document.write( "<body onLoad=\"popUp('./showFightingClock.html?fightId=" );
    win.document.write( fightId );
    win.document.write( "') \" ></body></html>" );
    win.document.close();
   
}

function openDuoClockWindow( fightId )
{

	 if (screen.width < 1200)
	    	win = window.open( "", "", 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=1,width=1020,height=768,left=0,top=0' );
	 else
	    	win = window.open( "", "", 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=1,width='+(screen.width-20)+',height='+(screen.height-20)+',left=0,top=0' );
	win.moveTo(0,0);
	win.resizeTo(screen.width,screen.height);
    win.document.open();
    win.document.writeln( "<html>" );
    win.document.writeln( "<script LANGUAGE=\"JavaScript\"> function popUp(URL) { location=URL;}" );
    win.document.writeln( "<\/script>" );
    win.document.write( "<body onLoad=\"popUp('./showDuoClock.html?fightId=" );
    win.document.write( fightId );
    win.document.write( "') \" ></body></html>" );
    win.document.close();
   
}

function openNewaClockWindow( fightId )
{
    if (screen.width < 1200)
    	win = window.open( "", "", 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=1,width=1020,height=768,left=0,top=0' );
    else
    	win = window.open( "", "", 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=1,width='+(screen.width-20)+',height='+(screen.height-20)+',left=0,top=0' );
    win.moveTo(0,0);
    win.resizeTo(screen.width,screen.height);
    win.document.open();
    win.document.writeln( "<html>" );
    win.document.writeln( "<script LANGUAGE=\"JavaScript\"> function popUp(URL) { location=URL;}" );
    win.document.writeln( "<\/script>" );
    win.document.write( "<body onLoad=\"popUp('./showNewaClock.html?fightId=" );
    win.document.write( fightId );
    win.document.write( "') \" ></body></html>" );
    win.document.close();
   
}

function openFriendlyFightingClockWindow( fightId )
{
    if (screen.width < 1200)
    	win = window.open( "", "", 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=1,width=1020,height=768,left=0,top=0' );
    else
    	win = window.open( "", "", 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=1,width='+(screen.width-20)+',height='+(screen.height-20)+',left=0,top=0' );
    win.moveTo(0,0);
    win.resizeTo(screen.width,screen.height);
    win.document.open();
    win.document.writeln( "<html>" );
    win.document.writeln( "<script LANGUAGE=\"JavaScript\"> function popUp(URL) { location=URL;}" );
    win.document.writeln( "<\/script>" );
    win.document.write( "<body onLoad=\"popUp('./showFriendlyFightingClock.html?fightId=" );
    win.document.write( fightId );
    win.document.write( "') \" ></body></html>" );
    win.document.close();
   
}

function openFriendlyDuoClockWindow( fightId )
{

	 if (screen.width < 1200)
	    	win = window.open( "", "", 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=1,width=1020,height=768,left=0,top=0' );
	 else
	    	win = window.open( "", "", 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=1,width='+(screen.width-20)+',height='+(screen.height-20)+',left=0,top=0' );
	win.moveTo(0,0);
	win.resizeTo(screen.width,screen.height);
	win.document.open();    
    win.document.writeln( "<html>" );
    win.document.writeln( "<script LANGUAGE=\"JavaScript\"> function popUp(URL) { location=URL;}" );
    win.document.writeln( "<\/script>" );
    win.document.write( "<body onLoad=\"popUp('./showFriendlyDuoClock.html?fightId=" );
    win.document.write( fightId );
    win.document.write( "') \" ></body></html>" );
    win.document.close();
   
}

function openFriendlyNewaClockWindow( fightId )
{
    if (screen.width < 1200)
    	win = window.open( "", "", 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=1,width=1020,height=768,left=0,top=0' );
    else
    	win = window.open( "", "", 'toolbar=0,scrollbars=0,location=0,statusbar=0,menubar=0,resizable=1,width='+(screen.width-20)+',height='+(screen.height-20)+',left=0,top=0' );
    win.moveTo(0,0);
    win.resizeTo(screen.width,screen.height);
    win.document.open();
    win.document.writeln( "<html>" );
    win.document.writeln( "<script LANGUAGE=\"JavaScript\"> function popUp(URL) { location=URL;}" );
    win.document.writeln( "<\/script>" );
    win.document.write( "<body onLoad=\"popUp('./showFriendlyNewaClock.html?fightId=" );
    win.document.write( fightId );
    win.document.write( "') \" ></body></html>" );
    win.document.close();
   
}

function hiddenCall(url){
var http;
if (window.XMLHttpRequest) {
   http = new XMLHttpRequest();
} else if (window.ActiveXObject) {
   http = new ActiveXObject("Microsoft.XMLHTTP");
}
        http.open('get','http://' +location.host  +url,true);
    http.send(null);

}

function handleLinkClick(event,urlStandard, urlSpecial)
{
	if (event.button==2) hiddenCall(urlSpecial);
	else window.location.href ='http://' +location.host  +urlStandard;
}
