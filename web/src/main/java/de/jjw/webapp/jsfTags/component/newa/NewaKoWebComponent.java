/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaKoWebComponent.java
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

import org.apache.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import de.jjw.model.LabelValue;
import de.jjw.model.User;
import de.jjw.model.newa.NewaKoClass;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.taglib.HtmlConstants;

public class NewaKoWebComponent
    extends UIOutput
    implements HtmlConstants, IGlobalWebConstants
{

    protected final Logger log = Logger.getLogger( getClass() );

    NewaKoClass newaclass;

    /**
     * The component type for this component.
     */
    // public static final String COMPONENT_TYPE =
    // "de.jjw.webapp.jsfTags.component.FightingKoWebComponent";

    /**
     * The renderer type for this component.
     */
    // public static final String RENDERER_TYPE = null;
    public String getComponentType()
    {
        return "de.jjw.webapp.jsfTags.component.newa.NewaKoWebComponent";
    }

    @Override
    public String getRendererType()
    {
        return null;
    }

    public NewaKoWebComponent()
    {
        // setRendererType(RENDERER_TYPE);
    }

    public NewaKoClass getNewaclass()
    {
        return newaclass;
    }

    public void setNewaclass( NewaKoClass newaclass )
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
        try
        {

            Map attrs = this.getAttributes();
            newaclass = (NewaKoClass) attrs.get( NEWACLASS );
            boolean readOnly =
                ( !( attrs.get( READ_ONLY ) == null || !IValueConstants.STRING_TRUE.equals( attrs.get( READ_ONLY ) ) ) );
            if ( !readOnly && newaclass.isDeleteStop() && !newaclass.isComplete() )
            {
                editAccess = true;
            }
            StringBuffer sb = new StringBuffer( 50 );
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
                    StringBuffer sb2 = new StringBuffer();
                    sb2.append( contextPath ).append( CHANGE_NEWA_FIGHTER_URL ).append( HTML_QUESTION_MARK ).append( HTML_PARAM_NEWACLASS_ID ).append( HTML_EQUAL );
                    sb2.append( newaclass.getId() );

                    out.startElement( JSF_A, this );

                    out.writeAttribute( ATTR_HREF, sb2.toString(), null );
                    out.write( rb.getString( "changeFighterLink" ) );
                    out.endElement( JSF_A );
                    out.startElement( JSF_BR, this );
                    out.write( NBSP );
                    out.endElement( JSF_BR );
                }
            }

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

            if ( newaclass.getFighterListPoolA().size() <= 8 )
            {
                NewaKo16WebComponentHelper.doMainround( this, context, out, FANCY_TABLE, rb,
                                                        newaclass.getFightListMapPoolA(),
                                                        newaclass.getFightListMapPoolB(), newaclass.getFinalFight(),
                                                        editAccess, sb.toString() );
            }
            else if ( newaclass.getFighterListPoolA().size() <= 16 && newaclass.getFighterListPoolA().size() > 8 )
            {
                NewaKo32WebComponentHelper.doMainround( this, context, out, FANCY_TABLE, rb,
                                                        newaclass.getFightListMapPoolA(),
                                                        newaclass.getFightListMapPoolB(), newaclass.getFinalFight(),
                                                        editAccess, sb.toString() );
            }
            else if ( newaclass.getFighterListPoolA().size() <= 32 && newaclass.getFighterListPoolA().size() > 16 )
            {
                NewaKo64WebComponentHelper.doMainround( this, context, out, FANCY_TABLE, rb,
                                                        newaclass.getFightListMapPoolA(),
                                                        newaclass.getFightListMapPoolB(), newaclass.getFinalFight(),
                                                        editAccess, sb.toString() );
            }
            else
            {
                throw new RuntimeException( "No main round display component for more than 64 fighters." );
            }

            out.startElement( "h3", this );
            writeText( out, rb.getString( "looserRoundHeading" ) );
            out.endElement( "h3" );

            if ( newaclass.getFighterListPoolA().size() <= 8 )
            {
                NewaKo16WebComponentHelper.doLooserRound( this, context, out, FANCY_TABLE, rb,
                                                          newaclass.getFightListLooserMapPoolA(),
                                                          newaclass.getFightListLooserMapPoolB(), editAccess,
                                                      sb.toString() );
            }
            else if ( newaclass.getFighterListPoolA().size() <= 16 && newaclass.getFighterListPoolA().size() > 8 )
            {
                NewaKo32WebComponentHelper.doLooserRound( this, context, out, FANCY_TABLE, rb,
                                                          newaclass.getFightListLooserMapPoolA(),
                                                          newaclass.getFightListLooserMapPoolB(), editAccess,
                                                      sb.toString() );
            }
            else if ( newaclass.getFighterListPoolA().size() <= 32 && newaclass.getFighterListPoolA().size() > 16 )
            {
                NewaKo64WebComponentHelper.doLooserRound( this, context, out, FANCY_TABLE, rb,
                                                          newaclass.getFightListLooserMapPoolA(),
                                                          newaclass.getFightListLooserMapPoolB(), editAccess,
                                                      sb.toString() );
            }
            else
            {
                throw new RuntimeException( "No looser round display component for more than 64 fighters." );
            }
        }
        catch ( Exception e )
        {
            log.error( "Error rendering page.", e );
        }
    }

    /**
     * writes the text or &nbsp; if test is ""
     * 
     * @param out
     * @param text
     * @throws IOException
     */
    public static void writeText( ResponseWriter out, String text )
        throws IOException
    {
        if ( TypeUtil.isDefault( text ) )
        {
            out.write( NBSP );
        }
        else
        {
            if ( TypeUtil.INT_MIN_STRING.equals( text ) )
            {
                out.write( NBSP );
            }
            else
            {
                if ( !TypeUtil.isEmpty( text ) )
                {
                    out.writeText( text, null );
                }
            }
        }
    }

}
