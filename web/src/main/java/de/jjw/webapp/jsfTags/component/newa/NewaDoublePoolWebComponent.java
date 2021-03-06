/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaDoublePoolWebComponent.java
 * Created : 16 Jun 2010
 * Last Modified: Wed, 16 Jun 2010 13:23:43
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
import java.util.ArrayList;
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
import de.jjw.model.fighting.Fight;
import de.jjw.model.newa.NewaFighter;
import de.jjw.model.newa.NewaDoublePoolClass;
import de.jjw.model.newa.NewaDoublePoolItem;
import de.jjw.model.newa.NewaFight;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.taglib.HtmlConstants;


public class NewaDoublePoolWebComponent
    extends UIOutput
    implements HtmlConstants, IGlobalWebConstants
{

    private static final String _36 = "36";

    private static final String _280 = "280";

    NewaDoublePoolClass newaclass;

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
        return "de.jjw.webapp.jsfTags.component.newa.NewaDoublePoolWebComponent";
    }

    @Override
    public String getRendererType()
    {
        return null;
    }

    public NewaDoublePoolWebComponent()
    {
        // setRendererType(RENDERER_TYPE);
    }

    public NewaDoublePoolClass getNewaclass()
    {
        return newaclass;
    }

    public void setNewaclass( NewaDoublePoolClass newaclass )
    {
        this.newaclass = newaclass;
    }

    private static String NEWA_CLASS = "newaclass";

    public static final String RESOURCE_BUNDLE = "messageResource";

    public static final String READ_ONLY = "readOnly";

    @Override
    public void encodeBegin( FacesContext context )
        throws IOException
    {
        Map attrs = this.getAttributes();
        newaclass = (NewaDoublePoolClass) attrs.get( NEWA_CLASS );
        boolean readOnly =
            ( !( attrs.get( READ_ONLY ) == null || !IValueConstants.STRING_TRUE.equals( attrs.get( READ_ONLY ) ) ) );
        boolean editAccess = !readOnly && newaclass.isDeleteStop() && !newaclass.isComplete();

        ResourceBundle rb = ResourceBundle.getBundle( (String) attrs.get( RESOURCE_BUNDLE ) );
        String contextPath = ( (HttpServletRequest) context.getExternalContext().getRequest() ).getContextPath();
        List<ResultTable> resultList = getResultTableList( newaclass );

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
                isChangeable = "admin".equals( item.getLabel() );
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
        NewaPoolWebComponentHelper.doPooltable( this, context, out, TABLE_WIDTH, TABLE_BORDER, rb,
                                            newaclass.getFighterListPoolA() );
        out.startElement( JSF_PARAGRAPH, null );
        NewaPoolWebComponentHelper.writeText( out, null );
        out.endElement( JSF_PARAGRAPH );
        StringBuffer sb = new StringBuffer( 50 );
        sb.append( contextPath );

        if ( editAccess )
        {
            if ( IGlobalWebConstants.CONTEXT_ADMIN == WebExchangeContextHelper.getUserAttributeFromSession( (HttpSession) context.getExternalContext().getSession( false ) ).getContext() )
            {
                sb.append( SHOW_NEWA_FIGHTMASK_URL );
            }
            if ( IGlobalWebConstants.CONTEXT_OFFICIAL == WebExchangeContextHelper.getUserAttributeFromSession( (HttpSession) context.getExternalContext().getSession( false ) ).getContext() )
            {
                sb.append( SHOW_NEWA_FIGHTMASK_URL_OFFICIAL );
            }

            sb.append( HTML_QUESTION_MARK ).append( HTML_PARAM_FIGHT ).append( HTML_EQUAL );
        }

        NewaPoolWebComponentHelper.doFighttable( this, context, out, TABLE_WIDTH, TABLE_BORDER, rb,
                                             newaclass.getFightListMapPoolA(), editAccess, sb.toString(), 1 );

        // 2nd Pool
        out.startElement( JSF_PARAGRAPH, null );
        NewaPoolWebComponentHelper.writeText( out, null );
        out.endElement( JSF_PARAGRAPH );
        out.startElement( JSF_PARAGRAPH, null );
        NewaPoolWebComponentHelper.writeText( out, null );
        out.endElement( JSF_PARAGRAPH );

        NewaPoolWebComponentHelper.doPooltable( this, context, out, TABLE_WIDTH, TABLE_BORDER, rb,
                                            newaclass.getFighterListPoolB() );
        out.startElement( JSF_PARAGRAPH, null );
        NewaPoolWebComponentHelper.writeText( out, null );
        out.endElement( JSF_PARAGRAPH );

        NewaPoolWebComponentHelper.doFighttable( this, context, out, TABLE_WIDTH, TABLE_BORDER, rb,
                                             newaclass.getFightListMapPoolB(), editAccess, sb.toString(), 11 );

        out.startElement( JSF_PARAGRAPH, null );
        NewaPoolWebComponentHelper.writeText( out, null );
        out.endElement( JSF_PARAGRAPH );
        out.startElement( JSF_PARAGRAPH, null );

        out.startElement( JSF_TABLE, null );// outer table for structure
        out.writeAttribute( ATTR_CLASS, FANCY_TABLE, null );
        out.writeAttribute( ATTR_BORDER, TABLE_BORDER, null );
        out.writeAttribute( ATTR_CELLPADDING, TABLE_CELLPADDING, null );
        out.writeAttribute( ATTR_CELLSPACING, TABLE_CELLSPACING, null );
        out.writeAttribute( ATTR_WIDTH, TABLE_WIDTH, null );



        // Final
        out.startElement( JSF_TABLE, null );// outer table for structure
        out.writeAttribute( ATTR_CLASS, FANCY_TABLE, null );
        out.writeAttribute( ATTR_BORDER, TABLE_BORDER, null );
        out.writeAttribute( ATTR_CELLPADDING, TABLE_CELLPADDING, null );
        out.writeAttribute( ATTR_CELLSPACING, TABLE_CELLSPACING, null );
        out.writeAttribute( ATTR_WIDTH, TABLE_WIDTH, null );

        out.startElement( JSF_THEAD, null );
        out.startElement( JSF_TR, null );
        out.writeAttribute( ATTR_CLASS, "finalHeader", null );
        out.startElement( JSF_TH, null );
        NewaPoolWebComponentHelper.writeText( out, rb.getString( "pool.halfFinal" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, null );
        NewaPoolWebComponentHelper.writeText( out, rb.getString( "pool.final" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, null );
        NewaPoolWebComponentHelper.writeText( out, rb.getString( "pool.result" ) );
        out.endElement( JSF_TH );
        out.endElement( JSF_TR );
        out.endElement( JSF_THEAD );

        // final
        out.startElement( JSF_TR, null );
        
        // half final
        out.startElement( JSF_TD, null );
        out.startElement( JSF_TABLE, null );

        out.startElement( JSF_TR, null );
        out.startElement( JSF_TD, null );
        NewaPoolWebComponentHelper.writeText( out, rb.getString( "pool.A1" ) );
        out.endElement( JSF_TD );

        out.startElement( JSF_TD, null );
        if ( newaclass.getHalfFinalFight1().getFighterRed() != null
            && newaclass.getHalfFinalFight1().getFighterRed().getId() != null )
        {
            if ( editAccess && newaclass.getHalfFinalFight1().getFighterBlue() != null
                && newaclass.getHalfFinalFight1().getFighterBlue().getId() != null )
            {
                doLink4Finals(out,newaclass.getHalfFinalFight1().getFighterRed().getName(),newaclass.getHalfFinalFight1(),context,sb.toString());
//                out.startElement( JSF_A, this );
//                out.writeAttribute( ATTR_HREF, sb.toString() + newaclass.getHalfFinalFight1().getId().longValue(), null );
//                NewaPoolWebComponentHelper.writeText( out, newaclass.getHalfFinalFight1().getFighterRed().getName() );
//                out.endElement( JSF_A );
            }
            else
            {
                NewaPoolWebComponentHelper.writeText( out, newaclass.getHalfFinalFight1().getFighterRed().getName() );
            }
        }
        else
        {
            NewaPoolWebComponentHelper.writeText( out, null );
        }
        out.endElement( JSF_TD );
        out.startElement( JSF_TD, null );
        if ( newaclass.getHalfFinalFight1().getFighterRed() != null )
        {
            NewaPoolWebComponentHelper.writeText( out, TypeUtil.toString( newaclass.getHalfFinalFight1().getPointsRed() ) );
        }
        else
        {
            NewaPoolWebComponentHelper.writeText( out, null );
        }
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );
        out.startElement( JSF_TR, null );
        out.startElement( JSF_TD, null );
        NewaPoolWebComponentHelper.writeText( out, rb.getString( "pool.B2" ) );
        out.endElement( JSF_TD );

        out.startElement( JSF_TD, null );
        if ( newaclass.getHalfFinalFight1().getFighterBlue() != null
            && newaclass.getHalfFinalFight1().getFighterBlue().getId() != null )
        {
            if ( editAccess && newaclass.getHalfFinalFight1().getFighterRed() != null
                && newaclass.getHalfFinalFight1().getFighterRed().getId() != null )
            {
                doLink4Finals(out,newaclass.getHalfFinalFight1().getFighterBlue().getName(),newaclass.getHalfFinalFight1(),context,sb.toString());
//                out.startElement( JSF_A, this );
//                out.writeAttribute( ATTR_HREF, sb.toString() + newaclass.getHalfFinalFight1().getId().longValue(), null );
//                NewaPoolWebComponentHelper.writeText( out, newaclass.getHalfFinalFight1().getFighterBlue().getName() );
//                out.endElement( JSF_A );
            }
            else
            {
                NewaPoolWebComponentHelper.writeText( out, newaclass.getHalfFinalFight1().getFighterBlue().getName() );
            }
        }
        else
        {
            NewaPoolWebComponentHelper.writeText( out, null );
        }
        out.endElement( JSF_TD );
        out.startElement( JSF_TD, null );
        if ( newaclass.getHalfFinalFight1().getFighterBlue() != null )
        {
            NewaPoolWebComponentHelper.writeText( out, TypeUtil.toString( newaclass.getHalfFinalFight1().getPointsBlue() ) );
        }
        else
        {
            NewaPoolWebComponentHelper.writeText( out, null );
        }
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );
        //----------------------------------------------------------------2nd half final ---------------------------------------------------
        out.startElement( JSF_TR, null );
        out.startElement( JSF_TD, null );
        NewaPoolWebComponentHelper.writeText( out, rb.getString( "pool.B1" ) );
        out.endElement( JSF_TD );

        out.startElement( JSF_TD, null );
        if ( newaclass.getHalfFinalFight2().getFighterRed() != null
            && newaclass.getHalfFinalFight2().getFighterRed().getId() != null )
        {
            if ( editAccess && newaclass.getHalfFinalFight2().getFighterBlue() != null
                && newaclass.getHalfFinalFight2().getFighterBlue().getId() != null )
            {
                doLink4Finals(out,newaclass.getHalfFinalFight2().getFighterRed().getName(),newaclass.getHalfFinalFight2(),context,sb.toString());
//                out.startElement( JSF_A, this );
//                out.writeAttribute( ATTR_HREF, sb.toString() + newaclass.getHalfFinalFight2().getId().longValue(), null );
//                NewaPoolWebComponentHelper.writeText( out, newaclass.getHalfFinalFight2().getFighterRed().getName() );
//                out.endElement( JSF_A );
            }
            else
            {
                NewaPoolWebComponentHelper.writeText( out, newaclass.getHalfFinalFight2().getFighterRed().getName() );
            }
        }
        else
        {
            NewaPoolWebComponentHelper.writeText( out, null );
        }
        out.endElement( JSF_TD );
        out.startElement( JSF_TD, null );
        if ( newaclass.getHalfFinalFight2().getFighterRed() != null )
        {
            NewaPoolWebComponentHelper.writeText( out, TypeUtil.toString( newaclass.getHalfFinalFight2().getPointsRed() ) );
        }
        else
        {
            NewaPoolWebComponentHelper.writeText( out, null );
        }
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );
        out.startElement( JSF_TR, null );
        out.startElement( JSF_TD, null );
        NewaPoolWebComponentHelper.writeText( out, rb.getString( "pool.A2" ) );
        out.endElement( JSF_TD );

        out.startElement( JSF_TD, null );
        if ( newaclass.getHalfFinalFight2().getFighterBlue() != null
            && newaclass.getHalfFinalFight2().getFighterBlue().getId() != null )
        {
            if ( editAccess && newaclass.getHalfFinalFight2().getFighterRed() != null
                && newaclass.getHalfFinalFight2().getFighterRed().getId() != null )
            {
                doLink4Finals(out,newaclass.getHalfFinalFight2().getFighterBlue().getName(),newaclass.getHalfFinalFight2(),context,sb.toString());
//                out.startElement( JSF_A, this );
//                out.writeAttribute( ATTR_HREF, sb.toString() + newaclass.getHalfFinalFight2().getId().longValue(), null );
//                NewaPoolWebComponentHelper.writeText( out, newaclass.getHalfFinalFight2().getFighterBlue().getName() );
//                out.endElement( JSF_A );
            }
            else
            {
                NewaPoolWebComponentHelper.writeText( out, newaclass.getHalfFinalFight2().getFighterBlue().getName() );
            }
        }
        else
        {
            NewaPoolWebComponentHelper.writeText( out, null );
        }
        out.endElement( JSF_TD );
        out.startElement( JSF_TD, null );
        if ( newaclass.getHalfFinalFight2().getFighterBlue() != null )
        {
            NewaPoolWebComponentHelper.writeText( out, TypeUtil.toString( newaclass.getHalfFinalFight2().getPointsBlue() ) );
        }
        else
        {
            NewaPoolWebComponentHelper.writeText( out, null );
        }
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.endElement( JSF_TABLE );
        out.endElement( JSF_TD );
        
        //real final
        out.startElement( JSF_TD, null );

        out.startElement( JSF_TABLE, null );

        out.startElement( JSF_TR, null );
        out.startElement( JSF_TD, null );
        NewaPoolWebComponentHelper.writeText( out, rb.getString( "pool.A1" ) );
        out.endElement( JSF_TD );

        out.startElement( JSF_TD, null );
        if ( newaclass.getFinalFight().getFighterRed() != null
            && newaclass.getFinalFight().getFighterRed().getId() != null )
        {
            if ( editAccess && newaclass.getFinalFight().getFighterBlue() != null
                && newaclass.getFinalFight().getFighterBlue().getId() != null )
            {
                doLink4Finals(out,newaclass.getFinalFight().getFighterRed().getName(),newaclass.getFinalFight(),context,sb.toString());
//                out.startElement( JSF_A, this );
//                out.writeAttribute( ATTR_HREF, sb.toString() + newaclass.getFinalFight().getId().longValue(), null );
//                NewaPoolWebComponentHelper.writeText( out, newaclass.getFinalFight().getFighterRed().getName() );
//                out.endElement( JSF_A );
            }
            else
            {
                NewaPoolWebComponentHelper.writeText( out, newaclass.getFinalFight().getFighterRed().getName() );
            }
        }
        else
        {
            NewaPoolWebComponentHelper.writeText( out, null );
        }
        out.endElement( JSF_TD );
        out.startElement( JSF_TD, null );
        if ( newaclass.getFinalFight().getFighterRed() != null )
        {
            NewaPoolWebComponentHelper.writeText( out, TypeUtil.toString( newaclass.getFinalFight().getPointsRed() ) );
        }
        else
        {
            NewaPoolWebComponentHelper.writeText( out, null );
        }
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );
        out.startElement( JSF_TR, null );
        out.startElement( JSF_TD, null );
        NewaPoolWebComponentHelper.writeText( out, rb.getString( "pool.B1" ) );
        out.endElement( JSF_TD );

        out.startElement( JSF_TD, null );
        if ( newaclass.getFinalFight().getFighterBlue() != null
            && newaclass.getFinalFight().getFighterBlue().getId() != null )
        {
            if ( editAccess && newaclass.getFinalFight().getFighterRed() != null
                && newaclass.getFinalFight().getFighterRed().getId() != null )
            {
                doLink4Finals(out,newaclass.getFinalFight().getFighterBlue().getName(),newaclass.getFinalFight(),context,sb.toString());
//                out.startElement( JSF_A, this );
//                out.writeAttribute( ATTR_HREF, sb.toString() + newaclass.getFinalFight().getId().longValue(), null );
//                NewaPoolWebComponentHelper.writeText( out, newaclass.getFinalFight().getFighterBlue().getName() );
//                out.endElement( JSF_A );
            }
            else
            {
                NewaPoolWebComponentHelper.writeText( out, newaclass.getFinalFight().getFighterBlue().getName() );
            }
        }
        else
        {
            NewaPoolWebComponentHelper.writeText( out, null );
        }
        out.endElement( JSF_TD );
        out.startElement( JSF_TD, null );
        if ( newaclass.getFinalFight().getFighterBlue() != null )
        {
            NewaPoolWebComponentHelper.writeText( out, TypeUtil.toString( newaclass.getFinalFight().getPointsBlue() ) );
        }
        else
        {
            NewaPoolWebComponentHelper.writeText( out, null );
        }
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.endElement( JSF_TABLE );
        out.endElement( JSF_TD );

        

        // resultTable
        out.startElement( JSF_TD, null );
        out.writeAttribute( ATTR_ALIGN, VAL_CENTER, null );

        out.startElement( JSF_TABLE, null );
        out.writeAttribute( ATTR_BORDER, TABLE_BORDER, null );

        out.startElement( JSF_TR, null );
        out.startElement( JSF_TD, null );
        NewaPoolWebComponentHelper.writeText( out, resultList.get( 0 ).place );
        out.endElement( JSF_TD );
        out.startElement( JSF_TD, null );
        NewaPoolWebComponentHelper.writeText( out, resultList.get( 0 ).name );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, null );
        out.startElement( JSF_TD, null );
        NewaPoolWebComponentHelper.writeText( out, resultList.get( 1 ).place );
        out.endElement( JSF_TD );
        out.startElement( JSF_TD, null );
        NewaPoolWebComponentHelper.writeText( out, resultList.get( 1 ).name );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, null );
        out.startElement( JSF_TD, null );
        NewaPoolWebComponentHelper.writeText( out, resultList.get( 2 ).place );
        out.endElement( JSF_TD );
        out.startElement( JSF_TD, null );
        NewaPoolWebComponentHelper.writeText( out, resultList.get( 2 ).name );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, null );
        out.startElement( JSF_TD, null );
        NewaPoolWebComponentHelper.writeText( out, resultList.get( 3 ).place );
        out.endElement( JSF_TD );
        out.startElement( JSF_TD, null );
        NewaPoolWebComponentHelper.writeText( out, resultList.get( 3 ).name );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.endElement( JSF_TABLE );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );
        out.endElement( JSF_TABLE );

        out.endElement( JSF_PARAGRAPH );
    }

    private  void doLink4Finals(ResponseWriter out, String text, NewaFight fight,
                                FacesContext context,  String linkToFight) throws IOException
        {
            out.startElement( JSF_A, this );
            out.writeAttribute( ATTR_HREF, linkToFight + fight.getId().longValue(), null );
            out.writeAttribute( ATTR_ONCONTEXTMENUE, "return false;", null );
            String contextPath = ( (HttpServletRequest) context.getExternalContext().getRequest() ).getContextPath();

            StringBuffer sb2 = new StringBuffer();
            sb2.append( HANDLE_LINK_CLICK );
            sb2.append( "(event,'" );
            sb2.append( linkToFight + fight.getId().longValue() );
            sb2.append( "','" );
            sb2.append( contextPath );
            sb2.append( PREVIEW_URL );
            sb2.append( "?system=F&id=" );
            sb2.append( fight.getId().longValue() );
            sb2.append( "');" );

            out.writeAttribute( ATTR_ONMOUSEDOWN, sb2.toString(), null );
            out.write( text );
            out.endElement( JSF_A );
        }
    
    private List<ResultTable> getResultTableList( NewaDoublePoolClass newaclass )
    {
        List<ResultTable> retList = new ArrayList<ResultTable>( 4 );
        ResultTable result;
        Map<Long,ArrayList<NewaFighter>> resultMap= newaclass.getResultMap();
                        
            if ( resultMap.get( Long.valueOf( 1 ) ) != null )
            {
                for ( int i = 0; i < resultMap.get( Long.valueOf( 1 ) ).size(); i++ )
                {
                    result = new ResultTable();
                    result.place = "1";
                    result.name =
                        resultMap.get( Long.valueOf( 1 ) ).get( i ).getFirstname() + IValueConstants.SPACE
                            + resultMap.get( Long.valueOf( 1 ) ).get( i ).getName();
                    retList.add( result );
                }
            }
            
            if ( resultMap.get( Long.valueOf( 2 ) ) != null )
            {
                for ( int i = 0; i < resultMap.get( Long.valueOf( 2 ) ).size(); i++ )
                {
                    result = new ResultTable();
                    result.place = "2";
                    result.name =
                        resultMap.get( Long.valueOf( 2 ) ).get( i ).getFirstname() + IValueConstants.SPACE
                            + resultMap.get( Long.valueOf( 2 ) ).get( i ).getName();
                    retList.add( result );
                }
            }
            
            if ( resultMap.get( Long.valueOf( 3 ) ) != null )
            {
                for ( int i = 0; i < resultMap.get( Long.valueOf( 3 ) ).size(); i++ )
                {
                    result = new ResultTable();
                    result.place = "3";
                    result.name =
                        resultMap.get( Long.valueOf( 3 ) ).get( i ).getFirstname() + IValueConstants.SPACE
                            + resultMap.get( Long.valueOf( 3 ) ).get( i ).getName();
                    retList.add( result );
                }
            }
            
            for (int i =retList.size(); i<5;i++){
                retList.add( new ResultTable() );
            }
        return retList;

    }

    private class ResultTable
    {
        public String place = TypeUtil.STRING_DEFAULT;

        public String name = TypeUtil.STRING_DEFAULT;
    }
}
