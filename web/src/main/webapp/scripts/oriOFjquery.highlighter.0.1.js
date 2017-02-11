/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : jquery.highlighter.0.1.js
 * Created : 15 Jun 2010
 * Last Modified: Tue, 15 Jun 2010 21:11:11
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

jQuery.fn.separator = function( valueFunction, options )
{
    var settings = {elementFunction:function()
    {
        return jQuery( this )
    },elementStyle:'separator'};
    if ( options )
    {
        jQuery.extend( settings, options )
    }
    var lastVal = '';
    jQuery( this ).each( function()
    {
        var el = jQuery( this );
        var curval = valueFunction.apply( el );
        var elToStyle;
        if ( curval != lastVal )
        {
            lastVal = curval;
            elToStyle = settings.elementFunction.apply( el );
            elToStyle.addClass( settings.elementStyle )
        }
        else
        {
            elToStyle = settings.elementFunction.apply( el );
            elToStyle.removeClass( settings.elementStyle )
        }
    } );
    return(this)
};