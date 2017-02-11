/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoDoublePoolWebComponent.java
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

package de.jjw.webapp.jsfTags.component;

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
import de.jjw.model.duo.DuoDoublePoolClass;
import de.jjw.model.duo.DuoDoublePoolItem;
import de.jjw.model.duo.DuoTeam;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.taglib.HtmlConstants;

public class DuoDoublePoolWebComponent
    extends UIOutput
    implements HtmlConstants, IGlobalWebConstants
{

    DuoDoublePoolClass duoclass;

    /**
     * The component type for this component.
     */
    // public static final String COMPONENT_TYPE = "de.jjw.webapp.jsfTags.component.DuoDoublePoolWebComponent";

    /**
     * The renderer type for this component.
     * 
     * @return the full name of the class
     */
    public String getComponentType()
    {
        return this.getClass().getName();
    }

    @Override
    public String getRendererType()
    {
        return null;
    }

    public DuoDoublePoolClass getDuoclass()
    {
        return duoclass;
    }

    public void setDuoclass( DuoDoublePoolClass duoclass )
    {
        this.duoclass = duoclass;
    }

    private static String DUO_CLASS = "duoclass";

    private static String RESOURCE_BUNDLE = "messageResource";

    private static String READ_ONLY = "readOnly";

    private boolean editAccess = false;

    @Override
    public void encodeBegin( FacesContext context )
        throws IOException
    {
        Map attrs = this.getAttributes();
        duoclass = (DuoDoublePoolClass) attrs.get( DUO_CLASS );
        boolean readOnly =
            ( !( attrs.get( READ_ONLY ) == null || !IValueConstants.STRING_TRUE.equals( attrs.get( READ_ONLY ) ) ) );
        if ( !readOnly && duoclass.isDeleteStop() && !duoclass.isComplete() )
        {
            editAccess = true;
        }

        ResourceBundle rb = ResourceBundle.getBundle( (String) attrs.get( RESOURCE_BUNDLE ) );
        String contextPath = ( (HttpServletRequest) context.getExternalContext().getRequest() ).getContextPath();
        List<ResultTable> resultList = getResultTableList( duoclass );

        ResponseWriter out = context.getResponseWriter();
        if ( DEBUG )
        {
            out.writeComment( "Source Class Name: " + this.getClass().getName() );
        }

        if ( context.getExternalContext().getUserPrincipal() != null
            && ( (UsernamePasswordAuthenticationToken) context.getExternalContext().getUserPrincipal() ).getPrincipal() != null
            && !duoclass.isDeleteStop() && !duoclass.isComplete() )
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
                StringBuffer sb2 = new StringBuffer();
                sb2.append( contextPath );
                if ( IGlobalWebConstants.CONTEXT_ADMIN == WebExchangeContextHelper.getUserAttributeFromSession(
                                                                                                                (HttpSession) context.getExternalContext().getSession(
                                                                                                                                                                       false ) ).getContext() )
                {
                    sb2.append( CHANGE_DUOTEAM_URL );
                }

                sb2.append( HTML_QUESTION_MARK ).append( HTML_PARAM_DUOCLASS_ID ).append( HTML_EQUAL );

                sb2.append( duoclass.getId() );

                out.startElement( JSF_A, this );

                out.writeAttribute( ATTR_HREF, sb2.toString(), null );
                out.write( rb.getString( "changeDuoTeamLink" ) );
                out.endElement( JSF_A );
                out.startElement( JSF_BR, this );
                out.write( NBSP );
                out.endElement( JSF_BR );
            }
        }

        PoolWebComponentHelper.doDuoPooltable( this, context, out, TABLE_WIDTH, TABLE_BORDER, rb,
                                               duoclass.getDuoListPoolA() );
        out.startElement( JSF_PARAGRAPH, null );
        PoolWebComponentHelper.writeText( out, null );
        out.endElement( JSF_PARAGRAPH );
        StringBuffer sb = new StringBuffer( 50 );

        sb.append( contextPath );
        if ( editAccess )
        {
            if ( IGlobalWebConstants.CONTEXT_ADMIN == WebExchangeContextHelper.getUserAttributeFromSession( (HttpSession) context.getExternalContext().getSession( false ) ).getContext() )
            {
                sb.append( SHOW_DUO_FIGHTMASK_URL );
            }
            if ( IGlobalWebConstants.CONTEXT_OFFICIAL == WebExchangeContextHelper.getUserAttributeFromSession( (HttpSession) context.getExternalContext().getSession( false ) ).getContext() )
            {
                sb.append( SHOW_DUO_FIGHTMASK_URL_OFFICIAL );
            }

            sb.append( HTML_QUESTION_MARK ).append( HTML_PARAM_FIGHT ).append( HTML_EQUAL );
        }

        PoolWebComponentHelper.doDuoFighttable( this, context, out, TABLE_WIDTH, TABLE_BORDER, rb,
                                                duoclass.getFightListMapPoolA(), editAccess, sb.toString(), 1 );

        // 2nd Pool
        out.startElement( JSF_PARAGRAPH, null );
        PoolWebComponentHelper.writeText( out, null );
        out.endElement( JSF_PARAGRAPH );
        out.startElement( JSF_PARAGRAPH, null );
        PoolWebComponentHelper.writeText( out, null );
        out.endElement( JSF_PARAGRAPH );

        PoolWebComponentHelper.doDuoPooltable( this, context, out, TABLE_WIDTH, TABLE_BORDER, rb,
                                               duoclass.getDuoListPoolB() );
        out.startElement( JSF_PARAGRAPH, null );
        PoolWebComponentHelper.writeText( out, null );
        out.endElement( JSF_PARAGRAPH );

        PoolWebComponentHelper.doDuoFighttable( this, context, out, TABLE_WIDTH, TABLE_BORDER, rb,
                                                duoclass.getFightListMapPoolB(), editAccess, sb.toString(), 11 );

        out.startElement( JSF_PARAGRAPH, null );
        PoolWebComponentHelper.writeText( out, null );
        out.endElement( JSF_PARAGRAPH );
        out.startElement( JSF_PARAGRAPH, null );

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
        PoolWebComponentHelper.writeText( out, rb.getString( "pool.halfFinal" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, null );
        PoolWebComponentHelper.writeText( out, rb.getString( "pool.final" ) );
        out.endElement( JSF_TH );

        out.startElement( JSF_TH, null );
        PoolWebComponentHelper.writeText( out, rb.getString( "pool.result" ) );
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
        PoolWebComponentHelper.writeText( out, rb.getString( "pool.A1" ) );
        out.endElement( JSF_TD );

        out.startElement( JSF_TD, null );
        if ( duoclass.getHalfFinalFight1().getDuoTeamRed() != null
            && duoclass.getHalfFinalFight1().getDuoTeamRed().getId() != null )
        {
            if ( editAccess && duoclass.getHalfFinalFight1().getDuoTeamBlue() != null
                && duoclass.getHalfFinalFight1().getDuoTeamBlue().getId() != null )
            {
                out.startElement( JSF_A, this );
                out.writeAttribute( ATTR_HREF, sb.toString() + duoclass.getHalfFinalFight1().getId().longValue(), null );
                PoolWebComponentHelper.writeText( out, duoclass.getHalfFinalFight1().getDuoTeamRed().getName() + " / " + duoclass.getHalfFinalFight1().getDuoTeamRed().getName2() );
                out.endElement( JSF_A );
            }
            else
            {
                PoolWebComponentHelper.writeText( out, duoclass.getHalfFinalFight1().getDuoTeamRed().getName()+ " / " + duoclass.getHalfFinalFight1().getDuoTeamRed().getName2() );
            }
        }
        else
        {
            PoolWebComponentHelper.writeText( out, null );
        }
        out.endElement( JSF_TD );
        out.startElement( JSF_TD, null );
        if ( duoclass.getHalfFinalFight1().getDuoTeamRed() != null )
        {
            PoolWebComponentHelper.writeText( out, TypeUtil.toString( duoclass.getHalfFinalFight1().getPointsRed() ) );
        }
        else
        {
            PoolWebComponentHelper.writeText( out, null );
        }
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );
        out.startElement( JSF_TR, null );
        out.startElement( JSF_TD, null );
        PoolWebComponentHelper.writeText( out, rb.getString( "pool.B2" ) );
        out.endElement( JSF_TD );

        out.startElement( JSF_TD, null );
        if ( duoclass.getHalfFinalFight1().getDuoTeamBlue() != null
            && duoclass.getHalfFinalFight1().getDuoTeamBlue().getId() != null )
        {
            if ( editAccess && duoclass.getHalfFinalFight1().getDuoTeamRed() != null
                && duoclass.getHalfFinalFight1().getDuoTeamRed().getId() != null )
            {
                out.startElement( JSF_A, this );
                out.writeAttribute( ATTR_HREF, sb.toString() + duoclass.getHalfFinalFight1().getId().longValue(), null );
                PoolWebComponentHelper.writeText( out, duoclass.getHalfFinalFight1().getDuoTeamBlue().getName()+ " / " + duoclass.getHalfFinalFight1().getDuoTeamBlue().getName2() );
                out.endElement( JSF_A );
            }
            else
            {
                PoolWebComponentHelper.writeText( out, duoclass.getHalfFinalFight1().getDuoTeamBlue().getName()+ " / " + duoclass.getHalfFinalFight1().getDuoTeamBlue().getName2() );
            }
        }
        else
        {
            PoolWebComponentHelper.writeText( out, null );
        }
        out.endElement( JSF_TD );
        out.startElement( JSF_TD, null );
        if ( duoclass.getHalfFinalFight1().getDuoTeamBlue() != null )
        {
            PoolWebComponentHelper.writeText( out, TypeUtil.toString( duoclass.getHalfFinalFight1().getPointsBlue() ) );
        }
        else
        {
            PoolWebComponentHelper.writeText( out, null );
        }
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );
        //----------------------------------------------------------------2nd half final ---------------------------------------------------
        out.startElement( JSF_TR, null );
        out.startElement( JSF_TD, null );
        PoolWebComponentHelper.writeText( out, rb.getString( "pool.B1" ) );
        out.endElement( JSF_TD );

        out.startElement( JSF_TD, null );
        if ( duoclass.getHalfFinalFight2().getDuoTeamRed() != null
            && duoclass.getHalfFinalFight2().getDuoTeamRed().getId() != null )
        {
            if ( editAccess && duoclass.getHalfFinalFight2().getDuoTeamBlue() != null
                && duoclass.getHalfFinalFight2().getDuoTeamBlue().getId() != null )
            {
                out.startElement( JSF_A, this );
                out.writeAttribute( ATTR_HREF, sb.toString() + duoclass.getHalfFinalFight2().getId().longValue(), null );
                PoolWebComponentHelper.writeText( out, duoclass.getHalfFinalFight2().getDuoTeamRed().getName() + " / " + duoclass.getHalfFinalFight2().getDuoTeamRed().getName2());
                out.endElement( JSF_A );
            }
            else
            {
                PoolWebComponentHelper.writeText( out, duoclass.getHalfFinalFight2().getDuoTeamRed().getName() + " / " + duoclass.getHalfFinalFight2().getDuoTeamRed().getName2());
            }
        }
        else
        {
            PoolWebComponentHelper.writeText( out, null );
        }
        out.endElement( JSF_TD );
        out.startElement( JSF_TD, null );
        if ( duoclass.getHalfFinalFight2().getDuoTeamRed() != null )
        {
            PoolWebComponentHelper.writeText( out, TypeUtil.toString( duoclass.getHalfFinalFight2().getPointsRed() ) );
        }
        else
        {
            PoolWebComponentHelper.writeText( out, null );
        }
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );
        out.startElement( JSF_TR, null );
        out.startElement( JSF_TD, null );
        PoolWebComponentHelper.writeText( out, rb.getString( "pool.A2" ) );
        out.endElement( JSF_TD );

        out.startElement( JSF_TD, null );
        if ( duoclass.getHalfFinalFight2().getDuoTeamBlue() != null
            && duoclass.getHalfFinalFight2().getDuoTeamBlue().getId() != null )
        {
            if ( editAccess && duoclass.getHalfFinalFight2().getDuoTeamRed() != null
                && duoclass.getHalfFinalFight2().getDuoTeamRed().getId() != null )
            {
                out.startElement( JSF_A, this );
                out.writeAttribute( ATTR_HREF, sb.toString() + duoclass.getHalfFinalFight2().getId().longValue(), null );
                PoolWebComponentHelper.writeText( out, duoclass.getHalfFinalFight2().getDuoTeamBlue().getName()+ " / " + duoclass.getHalfFinalFight2().getDuoTeamBlue().getName2() );
                out.endElement( JSF_A );
            }
            else
            {
                PoolWebComponentHelper.writeText( out, duoclass.getHalfFinalFight2().getDuoTeamBlue().getName() + " / " + duoclass.getHalfFinalFight2().getDuoTeamBlue().getName2());
            }
        }
        else
        {
            PoolWebComponentHelper.writeText( out, null );
        }
        out.endElement( JSF_TD );
        out.startElement( JSF_TD, null );
        if ( duoclass.getHalfFinalFight2().getDuoTeamBlue() != null )
        {
            PoolWebComponentHelper.writeText( out, TypeUtil.toString( duoclass.getHalfFinalFight2().getPointsBlue() ) );
        }
        else
        {
            PoolWebComponentHelper.writeText( out, null );
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
        PoolWebComponentHelper.writeText( out, rb.getString( "pool.A1" ) );
        out.endElement( JSF_TD );

        out.startElement( JSF_TD, null );
        if ( duoclass.getFinalFight().getDuoTeamRed() != null
            && duoclass.getFinalFight().getDuoTeamRed().getId() != null )
        {
            if ( editAccess && duoclass.getFinalFight().getDuoTeamBlue() != null
                && duoclass.getFinalFight().getDuoTeamBlue().getId() != null )
            {
                out.startElement( JSF_A, this );
                out.writeAttribute( ATTR_HREF, sb.toString() + duoclass.getFinalFight().getId(), null );
                PoolWebComponentHelper.writeText( out, duoclass.getFinalFight().getDuoTeamRed().getName() + " / " + duoclass.getFinalFight().getDuoTeamRed().getName2());
                out.endElement( JSF_A );
            }
            else
            {
                PoolWebComponentHelper.writeText( out, duoclass.getFinalFight().getDuoTeamRed().getName() + " / " + duoclass.getFinalFight().getDuoTeamRed().getName2());
            }
        }
        else
        {
            PoolWebComponentHelper.writeText( out, null );
        }
        out.endElement( JSF_TD );
        out.startElement( JSF_TD, null );
        if ( duoclass.getFinalFight().getDuoTeamRed() != null )
        {
            PoolWebComponentHelper.writeText( out, TypeUtil.toString( duoclass.getFinalFight().getPointsRed() ) );
        }
        else
        {
            PoolWebComponentHelper.writeText( out, null );
        }
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );
        out.startElement( JSF_TR, null );
        out.startElement( JSF_TD, null );
        PoolWebComponentHelper.writeText( out, rb.getString( "pool.B1" ) );
        out.endElement( JSF_TD );

        out.startElement( JSF_TD, null );
        if ( duoclass.getFinalFight().getDuoTeamBlue() != null
            && duoclass.getFinalFight().getDuoTeamBlue().getId() != null )
        {
            if ( editAccess && duoclass.getFinalFight().getDuoTeamRed() != null
                && duoclass.getFinalFight().getDuoTeamRed().getId() != null )
            {
                out.startElement( JSF_A, this );
                out.writeAttribute( ATTR_HREF, sb.toString() + duoclass.getFinalFight().getId(), null );
                PoolWebComponentHelper.writeText( out, duoclass.getFinalFight().getDuoTeamBlue().getName()+ " / " + duoclass.getFinalFight().getDuoTeamBlue().getName2() );
                out.endElement( JSF_A );
            }
            else
            {
                PoolWebComponentHelper.writeText( out, duoclass.getFinalFight().getDuoTeamBlue().getName()+ " / " + duoclass.getFinalFight().getDuoTeamBlue().getName2() );
            }
        }
        else
        {
            PoolWebComponentHelper.writeText( out, null );
        }
        out.endElement( JSF_TD );
        out.startElement( JSF_TD, null );
        if ( duoclass.getFinalFight().getDuoTeamBlue() != null )
        {
            PoolWebComponentHelper.writeText( out, TypeUtil.toString( duoclass.getFinalFight().getPointsBlue() ) );
        }
        else
        {
            PoolWebComponentHelper.writeText( out, null );
        }
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.endElement( JSF_TABLE );
        out.endElement( JSF_TD );

        
        // result table
        out.startElement( JSF_TD, null );
        out.writeAttribute( ATTR_ALIGN, VAL_CENTER, null );

        out.startElement( JSF_TABLE, null );
        out.writeAttribute( ATTR_BORDER, TABLE_BORDER, null );

        out.startElement( JSF_TR, null );
        out.startElement( JSF_TD, null );
        PoolWebComponentHelper.writeText( out, resultList.get( 0 ).place );
        out.endElement( JSF_TD );
        out.startElement( JSF_TD, null );
        PoolWebComponentHelper.writeText( out, resultList.get( 0 ).name );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, null );
        out.startElement( JSF_TD, null );
        PoolWebComponentHelper.writeText( out, resultList.get( 1 ).place );
        out.endElement( JSF_TD );
        out.startElement( JSF_TD, null );
        PoolWebComponentHelper.writeText( out, resultList.get( 1 ).name );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, null );
        out.startElement( JSF_TD, null );
        PoolWebComponentHelper.writeText( out, resultList.get( 2 ).place );
        out.endElement( JSF_TD );
        out.startElement( JSF_TD, null );
        PoolWebComponentHelper.writeText( out, resultList.get( 2 ).name );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.startElement( JSF_TR, null );
        out.startElement( JSF_TD, null );
        PoolWebComponentHelper.writeText( out, resultList.get( 3 ).place );
        out.endElement( JSF_TD );
        out.startElement( JSF_TD, null );
        PoolWebComponentHelper.writeText( out, resultList.get( 3 ).name );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );

        out.endElement( JSF_TABLE );
        out.endElement( JSF_TD );
        out.endElement( JSF_TR );
        out.endElement( JSF_TABLE );

        out.endElement( JSF_PARAGRAPH );
    }

    private List<ResultTable> getResultTableList( DuoDoublePoolClass duoclass )
    {
        List<ResultTable> retList = new ArrayList<ResultTable>( 4 );
        ResultTable result;
        Map<Long,ArrayList<DuoTeam>> resultMap= duoclass.getResultMap();
                        
            if ( resultMap.get( Long.valueOf( 1 ) ) != null )
            {
                for ( int i = 0; i < resultMap.get( Long.valueOf( 1 ) ).size(); i++ )
                {
                    result = new ResultTable();
                    result.place = "1";
                    result.name =
                        resultMap.get( Long.valueOf( 1 ) ).get( i ).getName() +  IValueConstants.SLASH_WITH_SPACE
                            + resultMap.get( Long.valueOf( 1 ) ).get( i ).getName2();
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
                        resultMap.get( Long.valueOf( 2 ) ).get( i ).getName() +  IValueConstants.SLASH_WITH_SPACE
                            + resultMap.get( Long.valueOf( 2 ) ).get( i ).getName2();
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
                        resultMap.get( Long.valueOf( 3 ) ).get( i ).getName() + IValueConstants.SLASH_WITH_SPACE
                            + resultMap.get( Long.valueOf( 3 ) ).get( i ).getName2();
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
