/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaSimplePoolWebComponent.java
 * Created : 09 Jun 2010
 * Last Modified: Wed, 09 Jun 2010 15:58:15
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

package de.jjw.webapp.jsfTags.component.newa;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import de.jjw.model.LabelValue;
import de.jjw.model.User;
import de.jjw.model.newa.NewaFight;
import de.jjw.model.newa.NewaSimplePoolClass;
import de.jjw.model.newa.NewaSimplePoolItem;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.taglib.HtmlConstants;

public class NewaSimplePoolWebComponent
    extends UIOutput
    implements HtmlConstants, IGlobalWebConstants
{

    NewaSimplePoolClass newaclass;

    /**
     * The component type for this component.
     */
    // public static final String COMPONENT_TYPE = "de.jjw.webapp.jsfTags.component.FightingSimplePoolWebComponent";

    /**
     * The renderer type for this component.
     */
    // public static final String RENDERER_TYPE = null;
    public String getComponentType()
    {
        return this.getClass().getName();
    }

    @Override
    public String getRendererType()
    {
        return null;
    }

    public NewaSimplePoolWebComponent()
    {
        // setRendererType(RENDERER_TYPE);
    }

    public NewaSimplePoolClass getNewaclass()
    {
        return newaclass;
    }

    public void setNewaclass( NewaSimplePoolClass newaclass )
    {
        this.newaclass = newaclass;
    }

    private static String NEWACLASS = "newaclass";

    private static String RESOURCEBUNDLE = "messageResource";

    private static String READ_ONLY = "readOnly";

    private ResourceBundle rb = null;

    private boolean editAccess = false;

    private String contextPath = null;

    @Override
    public void encodeBegin( FacesContext context )
        throws IOException
    {
        Map attrs = this.getAttributes();
        newaclass = (NewaSimplePoolClass) attrs.get( NEWACLASS );
        boolean readOnly =
            ( !( attrs.get( READ_ONLY ) == null || !IValueConstants.STRING_TRUE.equals( attrs.get( READ_ONLY ) ) ) );
        if ( !readOnly && newaclass.isDeleteStop() && !newaclass.isComplete() )
        {
            editAccess = true;
        }

        rb = ResourceBundle.getBundle( (String) attrs.get( RESOURCEBUNDLE ) );
        contextPath = ( (HttpServletRequest) context.getExternalContext().getRequest() ).getContextPath();

        ResponseWriter out = context.getResponseWriter();
        if ( DEBUG )
        {
            out.writeComment( "Source Class Name: " + this.getClass().getName() );
        }
        if ( context.getExternalContext().getUserPrincipal() != null
            && ( (UsernamePasswordAuthenticationToken) context.getExternalContext().getUserPrincipal() ).getPrincipal() != null
            && !newaclass.isDeleteStop() && !newaclass.isComplete() )
        {

            List<LabelValue> list =
                ( (User) ( (UsernamePasswordAuthenticationToken) context.getExternalContext().getUserPrincipal() ).getPrincipal() ).getRoleList();
            boolean isChangeable = false;
            for ( LabelValue item : list )
            {
                if ( "admin".equals( item.getLabel() ) )
                {
                    isChangeable = true;
                }
            }

            if ( isChangeable )
            {
                StringBuffer sb = new StringBuffer();
                sb.append( contextPath );
                if ( IGlobalWebConstants.CONTEXT_ADMIN == WebExchangeContextHelper.getUserAttributeFromSession(
                                                                                                                (HttpSession) context.getExternalContext().getSession(
                                                                                                                                                                       false ) ).getContext() )
                {
                    sb.append( CHANGE_NEWA_FIGHTER_URL );
                }               

                sb.append( HTML_QUESTION_MARK ).append( HTML_PARAM_NEWACLASS_ID ).append( HTML_EQUAL );

                sb.append( newaclass.getId() );

                out.startElement( JSF_A, this );

                out.writeAttribute( ATTR_HREF, sb.toString(), null );
                out.write( rb.getString( "changeFighterLink" ) );
                out.endElement( JSF_A );
                out.startElement( JSF_BR, this );
                out.write( NBSP );
                out.endElement( JSF_BR );
            }
        }

        doPooltable( context, out );
        out.startElement( JSF_PARAGRAPH, null );
        writeText( out, null );
        out.endElement( JSF_PARAGRAPH );
        doFighttable( context, out );

    }

    private void doFighttable( FacesContext context, ResponseWriter out )
        throws IOException
    {
        int i = 1;
        out.startElement( JSF_TABLE, this );
        out.writeAttribute( ATTR_CLASS, FANCY_TABLE, null );
        out.writeAttribute( ATTR_BORDER, TABLE_BORDER, null );
        out.writeAttribute( ATTR_CELLPADDING, TABLE_CELLPADDING, null );
        out.writeAttribute( ATTR_CELLSPACING, TABLE_CELLSPACING, null );
        out.writeAttribute( ATTR_WIDTH, TABLE_WIDTH, null );

        out.startElement( JSF_THEAD, this );
        out.startElement( JSF_TR, this );
        doTableHeadCell( out, "42", null, false, 2, null );
        doTableHeadCell( out, "139", VAL_CENTER, true, rb.getString( "fightTable.fighterRed" ) );
        doTableHeadCell( out, "139", VAL_CENTER, true, rb.getString( "fightTable.fighterBlue" ) );
        doTableHeadCell( out, "42", null, false, 2, null );

        // row span =6----------
        out.startElement( JSF_TH, this );
        // out.writeAttribute( ATTR_ROWSPAN, 6, null );
        out.writeAttribute( ATTR_STYLE,
                            "background:white;border-left:1px solid bbb;border-right:1px solid bbb;border-bottom:0",
                            null );
        out.writeAttribute( ATTR_WIDTH, 48, "SPACER" );
        writeText( out, null );
        out.endElement( JSF_TH );
        // ---------------------

        doTableHeadCell( out, "42", null, false, 2, null );
        doTableHeadCell( out, "139", VAL_CENTER, true, rb.getString( "fightTable.fighterRed" ) );
        doTableHeadCell( out, "139", VAL_CENTER, true, rb.getString( "fightTable.fighterBlue" ) );
        doTableHeadCell( out, "42", null, false, 2, null );
        out.endElement( JSF_TR );
        out.endElement( JSF_THEAD );

        i++;
        out.startElement( JSF_TR, this );
        out.writeAttribute( ATTR_CLASS, i % 2 == 0 ? "odd" : "even", null );
        doTableCell( out, "18", VAL_CENTER, true, TypeUtil.toString( i - 1 ) );
        doTableCellsLinkFight( out, rb.getString( "fightTable.1-2" ), newaclass.getFightListMap().get( 1 ), context );

        doTableCell( out, "48", null, false, "SPACER" );

        doTableCell( out, "18", VAL_CENTER, true, TypeUtil.toString( i + 4 ) );
        doTableCellsLinkFight( out, rb.getString( "fightTable.1-3" ), newaclass.getFightListMap().get( 6 ), context );
        out.endElement( JSF_TR );

        i++;
        out.startElement( JSF_TR, this );
        out.writeAttribute( ATTR_CLASS, i % 2 == 0 ? "odd" : "even", null );
        doTableCell( out, "18", VAL_CENTER, true, TypeUtil.toString( i - 1 ) );
        doTableCellsLinkFight( out, rb.getString( "fightTable.3-4" ), newaclass.getFightListMap().get( 2 ), context );

        doTableCell( out, "48", null, false, "SPACER" );

        doTableCell( out, "18", VAL_CENTER, true, TypeUtil.toString( i + 4 ) );
        doTableCellsLinkFight( out, rb.getString( "fightTable.2-4" ), newaclass.getFightListMap().get( 7 ), context );
        out.endElement( JSF_TR );

        i++;
        out.startElement( JSF_TR, this );
        out.writeAttribute( ATTR_CLASS, i % 2 == 0 ? "odd" : "even", null );
        doTableCell( out, "18", VAL_CENTER, true, TypeUtil.toString( i - 1 ) );
        doTableCellsLinkFight( out, rb.getString( "fightTable.1-5" ), newaclass.getFightListMap().get( 3 ), context );

        doTableCell( out, "48", null, false, "SPACER" );

        doTableCell( out, "18", VAL_CENTER, true, TypeUtil.toString( i + 4 ) );
        doTableCellsLinkFight( out, rb.getString( "fightTable.3-5" ), newaclass.getFightListMap().get( 8 ), context );
        out.endElement( JSF_TR );

        i++;
        out.startElement( JSF_TR, this );
        out.writeAttribute( ATTR_CLASS, i % 2 == 0 ? "odd" : "even", null );
        doTableCell( out, "18", VAL_CENTER, true, TypeUtil.toString( i - 1 ) );
        doTableCellsLinkFight( out, rb.getString( "fightTable.2-3" ), newaclass.getFightListMap().get( 4 ), context );

        doTableCell( out, "48", null, false, "SPACER" );

        doTableCell( out, "18", VAL_CENTER, true, TypeUtil.toString( i + 4 ) );
        doTableCellsLinkFight( out, rb.getString( "fightTable.1-4" ), newaclass.getFightListMap().get( 9 ), context );
        out.endElement( JSF_TR );

        i++;
        out.startElement( JSF_TR, this );
        out.writeAttribute( ATTR_CLASS, i % 2 == 0 ? "odd" : "even", null );
        doTableCell( out, "18", VAL_CENTER, true, TypeUtil.toString( i - 1 ) );
        doTableCellsLinkFight( out, rb.getString( "fightTable.4-5" ), newaclass.getFightListMap().get( 5 ), context );

        doTableCell( out, "48", null, false, "SPACER" );

        doTableCell( out, "18", VAL_CENTER, true, TypeUtil.toString( i + 4 ) );
        doTableCellsLinkFight( out, rb.getString( "fightTable.2-5" ), newaclass.getFightListMap().get( 10 ),
                               context );
        out.endElement( JSF_TR );

        out.endElement( JSF_TABLE );
    }

    private void doTableCellsLinkFight( ResponseWriter out, String text, NewaFight fight, FacesContext context )
        throws IOException
    {
        if ( editAccess && fight != null )
        {
            out.startElement( JSF_TD, this );
            out.writeAttribute( ATTR_ALIGN, VAL_CENTER, null );

            out.startElement( JSF_A, this );
            StringBuffer sb = new StringBuffer( 50 );
            sb.append( contextPath );
            if ( IGlobalWebConstants.CONTEXT_ADMIN == WebExchangeContextHelper.getUserAttributeFromSession(
                                                                                                            (HttpSession) context.getExternalContext().getSession(
                                                                                                                                                                   false ) ).getContext() )
            {
                sb.append( SHOW_NEWA_FIGHTMASK_URL );
            }
            if ( IGlobalWebConstants.CONTEXT_OFFICIAL == WebExchangeContextHelper.getUserAttributeFromSession(
                                                                                                               (HttpSession) context.getExternalContext().getSession(
                                                                                                                                                                      false ) ).getContext() )
            {
                sb.append( SHOW_NEWA_FIGHTMASK_URL_OFFICIAL );
            }

            sb.append( HTML_QUESTION_MARK ).append( HTML_PARAM_FIGHT ).append( HTML_EQUAL ).append(
                                                                                                    fight.getId().longValue() );

            out.writeAttribute( ATTR_HREF, sb.toString(), null );

            out.writeAttribute( ATTR_ONCONTEXTMENUE, "return false;", null );

            StringBuffer sb2 = new StringBuffer();
            sb2.append( HANDLE_LINK_CLICK );
            sb2.append( "(event,'" );
            sb2.append( sb );
            sb2.append( "','" );
            sb2.append( contextPath );
            sb2.append( PREVIEW_URL );
            sb2.append( "?system=N&id=" );
            sb2.append( fight.getId() );
            sb2.append( "');" );

            // out.writeAttribute( ATTR_ONMOUSEDOWN, "handleLinkClick(event,'/util/preview?id=0','/util/preview?id=0','"
            // + contextPath
            // + "')",
            // null );
            out.writeAttribute( ATTR_ONMOUSEDOWN, sb2.toString(), null );
            out.write( text );
            out.endElement( JSF_A );
            out.endElement( JSF_TD );

        }
        else
        {
            // pair
            doTableCell( out, TypeUtil.toString( 24 ), VAL_CENTER, false, text );
        }

        if ( fight == null )
        {
            // fighter red and blue
            doTableCell( out, TypeUtil.toString( 139 ), null, false, null );
            doTableCell( out, TypeUtil.toString( 139 ), null, false, null );
            // fighter points red and blue
            doTableCell( out, TypeUtil.toString( 24 ), null, false, null );
            doTableCell( out, TypeUtil.toString( 24 ), null, false, null );
        }
        else
        {
            // fighter red and blue
            if ( fight.getWinnerId() != null && fight.getWinnerId().equals( fight.getFighterRed().getId() ) )
            {
                doTableCell( out, TypeUtil.toString( 139 ), VAL_CENTER, true, fight.getFighterRed().getName() );
            }
            else
            {
                doTableCell( out, TypeUtil.toString( 139 ), VAL_CENTER, false, fight.getFighterRed().getName() );
            }

            if ( fight.getWinnerId() != null && fight.getWinnerId().equals( fight.getFighterBlue().getId() ) )
            {
                doTableCell( out, TypeUtil.toString( 139 ), VAL_CENTER, true, fight.getFighterBlue().getName() );
            }
            else
            {
                doTableCell( out, TypeUtil.toString( 139 ), VAL_CENTER, false, fight.getFighterBlue().getName() );
            }

            // fighter points red and blue
            doTableCell( out, TypeUtil.toString( 24 ), VAL_CENTER, false, TypeUtil.toString( fight.getPointsRed() ) );
            doTableCell( out, TypeUtil.toString( 24 ), VAL_CENTER, false, TypeUtil.toString( fight.getPointsBlue() ) );
        }
    }

    private void doPooltable( FacesContext context, ResponseWriter out )
        throws IOException
    {
        out.startElement( JSF_TABLE, this );
        out.writeAttribute( ATTR_CLASS, FANCY_TABLE, null );
        out.writeAttribute( ATTR_BORDER, TABLE_BORDER, null );
        out.writeAttribute( ATTR_CELLPADDING, TABLE_CELLPADDING, null );
        out.writeAttribute( ATTR_CELLSPACING, TABLE_CELLSPACING, null );
        out.writeAttribute( ATTR_WIDTH, TABLE_WIDTH, null );

        doPooltableHeadRow( context, out );
        doPooltableRow( context, out );
        out.endElement( JSF_TABLE );
    }

    private void doPooltableRow( FacesContext context, ResponseWriter out )
        throws IOException
    {
        NewaSimplePoolItem item;
        for ( int i = 1; i < 6; i++ )
        {
            if ( newaclass.getFighterList().size() >= i )
            {
                item = newaclass.getFighterList().get( i - 1 );
                out.startElement( JSF_TR, this );
                out.writeAttribute( ATTR_CLASS, i % 2 == 0 ? "odd" : "even", null );
                doTableCell( out, "15%", VAL_CENTER, false, item.getFighter().getName() );
                doTableCell( out, "15%", VAL_CENTER, false, item.getFighter().getFirstname() );
                doTableCell( out, "15%", VAL_CENTER, false, item.getFighter().getTeam().getTeamName() );
                doTableCell( out, "3%", VAL_CENTER, false, String.valueOf( i ) );
                if ( i == 1 )
                {
                    doTableCell( out, "6%", VAL_CENTER, false, 2, null, "invalid" );
                }
                doTableCell( out, "2%", VAL_CENTER, false, TypeUtil.toString( item.getResult().getResultFight1() ) );
                doTableCell( out, "4%", VAL_CENTER, false, TypeUtil.toString( item.getResult().getUbFight1() ) );
                if ( i == 2 )
                {
                    doTableCell( out, "6%", VAL_CENTER, false, 2, null, "invalid" );
                }
                doTableCell( out, "2%", VAL_CENTER, false, TypeUtil.toString( item.getResult().getResultFight2() ) );
                doTableCell( out, "4%", VAL_CENTER, false, TypeUtil.toString( item.getResult().getUbFight2() ) );
                if ( i == 3 )
                {
                    doTableCell( out, "6%", VAL_CENTER, false, 2, null, "invalid" );
                }
                doTableCell( out, "2%", VAL_CENTER, false, TypeUtil.toString( item.getResult().getResultFight3() ) );
                doTableCell( out, "4%", VAL_CENTER, false, TypeUtil.toString( item.getResult().getUbFight3() ) );
                if ( i == 4 )
                {
                    doTableCell( out, "6%", VAL_CENTER, false, 2, null, "invalid" );
                }
                doTableCell( out, "2%", VAL_CENTER, false, TypeUtil.toString( item.getResult().getResultFight4() ) );
                doTableCell( out, "4%", VAL_CENTER, false, TypeUtil.toString( item.getResult().getUbFight4() ) );
                if ( i == 5 )
                {
                    doTableCell( out, "6%", VAL_CENTER, false, 2, null, "invalid" );
                }
                doTableCell( out, "5%", VAL_CENTER, false, TypeUtil.toString( item.getResult().getWinCount() ) );
                doTableCell( out, "6%", VAL_CENTER, false, TypeUtil.toString( item.getResult().getUBCount() ) );
                doTableCell( out, "9%", VAL_CENTER, false, TypeUtil.toString( item.getPlace() ) );
                out.endElement( JSF_TR );
            }
            else
            {

                NewaPoolWebComponentHelper.doEmptyPooltableRow( this, out, i );
            }

        }

    }

    private void doTableHeadCell( ResponseWriter out, String width, String align, boolean bold, int colspan, String text )
        throws IOException
    {
        out.startElement( JSF_TH, this );
        if ( colspan > 0 )
        {
            out.writeAttribute( ATTR_COLSPAN, colspan, null );
        }

        // if ( width != null )
        // {
        // out.writeAttribute( ATTR_WIDTH, width, null );
        // }
        if ( align != null && !TypeUtil.isEmptyOrDefault( text ) )
        {
            out.writeAttribute( ATTR_ALIGN, align, null );
        }
        if ( bold )
        {
            out.startElement( JSF_BOLD, this );
        }
        writeText( out, text );
        if ( bold )
        {
            out.endElement( JSF_BOLD );
        }
        out.endElement( JSF_TH );
    }

    private void doTableHeadCell( ResponseWriter out, String width, String align, boolean bold, String text )
        throws IOException
    {
        doTableHeadCell( out, width, align, bold, 0, text );
    }

    private void doTableCell( ResponseWriter out, String width, String align, boolean bold, String text )
        throws IOException
    {
        doTableCell( out, width, align, bold, 0, text, null );
    }

    private void doPooltableHeadRow( FacesContext context, ResponseWriter out )
        throws IOException
    {
        out.startElement( JSF_THEAD, this );
        out.startElement( JSF_TR, this );

        out.startElement( JSF_TH, this );
        out.writeAttribute( ATTR_WIDTH, "100", null );
        writeText( out, rb.getString( "pool.name" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, this );
        out.writeAttribute( ATTR_WIDTH, "100", null );
        writeText( out, rb.getString( "pool.firstname" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, this );
        out.writeAttribute( ATTR_WIDTH, "100", null );
        writeText( out, rb.getString( "pool.team" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, this );
        out.writeAttribute( ATTR_WIDTH, "30", null );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, this );
        out.writeAttribute( ATTR_WIDTH, "50", null );
        out.writeAttribute( ATTR_COLSPAN, "2", null );
        writeText( out, rb.getString( "pool.1" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, this );
        out.writeAttribute( ATTR_WIDTH, "50", null );
        out.writeAttribute( ATTR_COLSPAN, "2", null );
        writeText( out, rb.getString( "pool.2" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, this );
        out.writeAttribute( ATTR_WIDTH, "50", null );
        out.writeAttribute( ATTR_COLSPAN, "2", null );
        writeText( out, rb.getString( "pool.3" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, this );
        out.writeAttribute( ATTR_WIDTH, "50", null );
        out.writeAttribute( ATTR_COLSPAN, "2", null );
        writeText( out, rb.getString( "pool.4" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, this );
        out.writeAttribute( ATTR_WIDTH, "50", null );
        out.writeAttribute( ATTR_COLSPAN, "2", null );
        writeText( out, rb.getString( "pool.5" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, this );
        out.writeAttribute( ATTR_WIDTH, "80", null );
        writeText( out, rb.getString( "pool.wins" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, this );
        out.writeAttribute( ATTR_WIDTH, "80", null );
        writeText( out, rb.getString( "pool.points" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, this );
        out.writeAttribute( ATTR_WIDTH, "80", null );
        writeText( out, rb.getString( "pool.place" ) );
        out.endElement( JSF_TH );

        out.endElement( JSF_TR );
        out.endElement( JSF_THEAD );
    }

    private void doTableCell( ResponseWriter out, String width, String align, boolean bold, int colspan, String text,
                              String styleClass )
        throws IOException
    {
        out.startElement( JSF_TD, this );
        if ( colspan > 0 )
        {
            out.writeAttribute( ATTR_COLSPAN, colspan, null );
        }
        if ( styleClass != null )
        {
            out.writeAttribute( ATTR_CLASS, styleClass, null );
        }
        if ( "SPACER".equals( text ) )
        {
            out.writeAttribute( ATTR_STYLE, "border: 0;background:white;", null );
        }
        else
        {
            out.writeAttribute( ATTR_STYLE, "border-left: 1px solid #BBBBBB;", null );
        }
        // if ( width != null )
        // {
        // out.writeAttribute( ATTR_WIDTH, width, null );
        // }
        if ( align != null && !TypeUtil.isEmptyOrDefault( text ) )
        {
            out.writeAttribute( ATTR_ALIGN, align, null );
        }
        if ( bold )
        {
            out.startElement( JSF_BOLD, this );
        }

        if ( !"SPACER".equals( text ) )
        {
            writeText( out, text );
        }

        if ( bold )
        {
            out.endElement( JSF_BOLD );
        }
        out.endElement( JSF_TD );
    }

    /**
     * wites the text or &nbsp; if test is null or ""
     * 
     * @param out
     * @param text
     * @throws IOException
     */
    private void writeText( ResponseWriter out, String text )
        throws IOException
    {
        if ( TypeUtil.isEmptyOrDefault( text ) )
        {
            out.write( NBSP );
        }
        else
        {
            out.writeText( text, null );
        }
    }
}
