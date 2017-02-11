/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : ajax-dialog-forms.js
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

$( document ).ready( dialogForms );

function dialogForms()
{
    $( 'a.dialog-form' ).click( function()
    {
        var a = $( this );
        $.get( a.attr( 'href' ), function( resp )
        {
            var dialog = $( '<div>' ).attr( 'id', 'formDialog' ).html( resp );
            $( 'body' ).append( dialog );
            dialog.find( ':submit' ).hide();
            dialog.dialog( {
                title: a.attr( 'title' ) ? a.attr( 'title' ) : '',
                modal: true,
                buttons: {
                    'Save': function()
                    {
                        submitFormWithAjax( $( this ).find( 'form' ) );
                    },
                    'Cancel': function()
                    {
                        $( this ).dialog( 'close' );
                    }
                },
                close: function()
                {
                    $( this ).remove();
                },
                width: 'auto'
            } );
        }, 'html' );
        return false;
    } );
}

function submitFormWithAjax( form )
{
    form = $( form );
    $.ajax( {
        url: form.attr( 'action' ),
        data: form.serialize(),
        type: (form.attr( 'method' )),
        dataType: 'script'
    } );
    return false;
}
