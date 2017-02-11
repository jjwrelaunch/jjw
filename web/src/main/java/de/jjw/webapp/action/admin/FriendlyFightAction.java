/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FriendlyFightAction.java
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

package de.jjw.webapp.action.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.faces.model.SelectItem;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.fighting.FriendlyFight;
import de.jjw.model.generalhelper.Codestable;
import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.fighting.FriendlyFightManager;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.action.validation.fighting.FightValidator;

public class FriendlyFightAction
    extends BasePage
    implements IGlobalWebConstants
{

    private static String PARA_FIGHTID = "main:adminFriendlyFightAction:" + HTML_PARAM_FIGHT;

    private FriendlyFightManager fightManager;

    private FriendlyFight fight;

    private int pointsRed;

    private int pointsBlue;

    private int winner = TypeUtil.INT_MIN;

    private long fightId;

    private static String FIGHT_DTO = "fight_dto";

    public FriendlyFight getFight()
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

        try
        {
            fightManager.saveFight(
                                    new ServiceExchangeContext(
                                                                WebExchangeContextHelper.getWebExchangeContext(
                                                                                                                getSession() ).getUserId() ),
                                    fight );

            return "friendlies";
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

    }

    public String abort()
    {
        return "friendlies";
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


    public void setFightManager( FriendlyFightManager fightManager )
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

}
