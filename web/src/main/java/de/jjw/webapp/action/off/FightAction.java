/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightAction.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:43
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

package de.jjw.webapp.action.off;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.faces.model.SelectItem;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.fighting.Fight;
import de.jjw.model.generalhelper.Codestable;
import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.fighting.FightManager;
import de.jjw.service.fighting.UserFightingclassManager;
import de.jjw.service.modelWeb.FightWeb;
import de.jjw.service.modelWeb.FighterWeb;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.action.validation.fighting.FightValidator;
import de.jjw.webapp.pdf.fighting.FightingVisualizelProtokollPDF;
import javax.servlet.http.HttpServletResponse;

public class FightAction
    extends BasePage
    implements IGlobalWebConstants
{

    private static String PARA_FIGHTID = "main:offFightAction:" + HTML_PARAM_FIGHT;

    private FightManager fightManager;

    private Fight fight;

    private int pointsRed;

    private int pointsBlue;

    private int winner = TypeUtil.INT_MIN;

    private long fightId;

    private static String FIGHT_DTO = "fight_dto";

    private UserFightingclassManager userFightingclassManager = null;

    public Fight getFight()
    {
        Vector<ErrorElement> errors = new Vector<ErrorElement>();
        if ( fightManager == null )
        {
            log.warn( "keine fightManager Injection" );
        }

        if ( getRequest().getParameter( PARA_FIGHTID ) == null || getRequest().getParameter( PARA_FIGHTID ).isEmpty() )
        {
            setFightId( Long.valueOf( getRequest().getParameter( HTML_PARAM_FIGHT ) ) );
        }
        else if ( getRequest().getParameter( HTML_PARAM_FIGHT ) == null
            || getRequest().getParameter( HTML_PARAM_FIGHT ).isEmpty() )
        {
            setFightId( Long.valueOf( getRequest().getParameter( PARA_FIGHTID ) ) );
        }
        else
        {
            // missing parameter
            return null;
        }
        if ( getRequest().getAttribute( FIGHT_DTO ) == null )
        {
            try
            {
                fight = fightManager.getFight( getFightId() );
            }
            catch ( NumberFormatException e )
            {
                errors.add( new ErrorElement( "", GEN_GERNERAL_ERROR ) );
                setErrorMessageVector( errors );
                return null;
            }
            catch ( JJWManagerException e )
            {
                errors.add( new ErrorElement( "", GEN_GERNERAL_ERROR ) );
                setErrorMessageVector( errors );
                return null;
            }
            getRequest().setAttribute( FIGHT_DTO, FIGHT_DTO );
        }
        return fight;
    }

    public String save()
    {
        Vector<ErrorElement> errors = new Vector<ErrorElement>();
        if ( !FightValidator.isRequiredFieldsValid( this, errors ) )
        {
            setErrorMessageVector( errors );
            return null;
        }
        if ( !FightValidator.isBusinessLogicValid( this, errors ) )
        {
            setErrorMessageVector( errors );
            return null;
        }
        try
        {
            if ( !userFightingclassManager.isAccessForTatamiUser( getCtx().getUserId(), getFight().getFightingclassId() ) )
            {
                getFacesContext().getExternalContext().redirect(
                                                                 getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                         getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                             + "/logout" ) );
                errors.add( new ErrorElement( GEN_ACCESS_VIOLATION ) );
                setErrorMessageVector( errors );
                return null;

            }
            getFight().setPointsRed( getPointsRed() );
            getFight().setPointsBlue( getPointsBlue() );

            // set points
            if ( TypeUtil.INT_DEFAULT == getWinner() )
            {
                getFight().setWinnerId( TypeUtil.LONG_DEFAULT );
                getFight().setPointsBlue( TypeUtil.INT_DEFAULT );
                getFight().setPointsRed( TypeUtil.INT_DEFAULT );
            }
            if ( IValueConstants.FIGHT_WINNER_RED == getWinner() )
            {
                getFight().setWinnerId( getFight().getFighterIdRed() );
            }
            if ( IValueConstants.FIGHT_WINNER_BLUE == getWinner() )
            {
                getFight().setWinnerId( getFight().getFighterIdBlue() );
            }

            fightManager.saveFight(
                                    new ServiceExchangeContext(
                                                                WebExchangeContextHelper.getWebExchangeContext(
                                                                                                                getSession() ).getUserId() ),
                                    fight );

            // navigate to class
            String contextPath = getFacesContext().getExternalContext().getRequestContextPath();

            StringBuffer sb = new StringBuffer( 120 );
            sb.append( contextPath ).append( SHOW_FIGHTINGCLASS_OFFICIAL ).append( HTML_QUESTION_MARK ).append(
                                                                                                                HTML_PARAM_FIGHTINGCLASS_ID ).append(
                                                                                                                                                      HTML_EQUAL ).append(
                                                                                                                                                                           String.valueOf( getFight().getFightingclassId() ) );

            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     sb.toString() ) );

        }
        catch ( OptimisticLockingException e )
        {
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
            return null;
        }
        catch ( JJWManagerException e )
        {
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
            return null;
        }
        catch ( IOException e1 )
        {
            log.error( "FightAction", e1 );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }

        return null;
    }

    public String abort()
    {
        try
        {
            String contextPath = getFacesContext().getExternalContext().getRequestContextPath();

            StringBuffer sb = new StringBuffer( 120 );
            sb.append( contextPath ).append( SHOW_FIGHTINGCLASS_OFFICIAL ).append( HTML_QUESTION_MARK ).append(
                                                                                                                HTML_PARAM_FIGHTINGCLASS_ID ).append(
                                                                                                                                                      HTML_EQUAL ).append(
                                                                                                                                                                           String.valueOf( getFight().getFightingclassId() ) );

            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     sb.toString() ) );
        }
        catch ( IOException e1 )
        {
            log.error( "FightAction", e1 );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;

    }
    
    
    public String visualizeAllFights()
    {
        HttpServletResponse response = getResponseWithPDFHeader();
        try
        {  
            Map<FighterWeb, List<FightWeb>> map =fightManager.getFightForVisualize(getFight().getId());
            if (map.size()<1) return null;  // do nothing when no data 
            new FightingVisualizelProtokollPDF( "de.jjw.webapp.messages.fighter", response, getLocale() ).visualizeAllProtokoll( map );            
            getFacesContext().responseComplete();
        }
        catch ( Exception e )
        {
            log.error( "FightAction", e );
            addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
        }
        return null;
    }
    
    private HttpServletResponse getResponseWithPDFHeader()
    {
        HttpServletResponse response = (HttpServletResponse) getFacesContext().getExternalContext().getResponse();

        response.setHeader( "Expires", "0" );
        response.setHeader( "Cache-Control", "must-revalidate, post-check=0, pre-check=0" );
        response.setHeader( "Pragma", "public" );
        response.setContentType( "application/pdf" );
        return response;
    }
    

    public List getWinList()
    {
        List ret = new ArrayList();

        List<Codestable> list =
            CodestableMain.getInstance().getCodestableCodeByType( TYPE_WINNER, getRequest().getLocale() );
        for ( Codestable entry : list )
        {
            ret.add( new SelectItem( Integer.valueOf( entry.getId() ), entry.getValue() ) );
        }
        return ret;
    }

    
    public void setFightManager( FightManager fightManager )
    {
        this.fightManager = fightManager;
    }

    public int getPointsRed()
    {
        return pointsRed;
    }

    public void setPointsRed( int pointsRed )
    {
        this.pointsRed = pointsRed;
    }

    public int getPointsBlue()
    {
        return pointsBlue;
    }

    public void setPointsBlue( int pointsBlue )
    {
        this.pointsBlue = pointsBlue;
    }

    public int getWinner()
    {

        return ( winner == TypeUtil.INT_MIN ) ? IValueConstants.FIGHT_WINNER_RED : winner;
    }

    public void setWinner( int winner )
    {
        this.winner = winner;
    }

    public long getFightId()
    {
        return fightId;
    }

    public void setFightId( long fightId )
    {
        this.fightId = fightId;
    }

    public UserFightingclassManager getUserFightingclassManager()
    {
        return userFightingclassManager;
    }

    public void setUserFightingclassManager( UserFightingclassManager userFightingclassManager )
    {
        this.userFightingclassManager = userFightingclassManager;
    }
}
