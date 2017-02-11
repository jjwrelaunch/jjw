/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FriendlyDuoFightAction.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:42
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
import de.jjw.model.duo.FriendlyDuoFight;
import de.jjw.model.generalhelper.Codestable;
import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.duo.FriendlyDuoFightManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.action.validation.duo.DuoFightValidator;

public class FriendlyDuoFightAction
    extends BasePage
    implements IGlobalWebConstants
{


    private static String PARA_FIGHTID = "main:adminFriendlyDuoFightAction:" + HTML_PARAM_FIGHT;

    private FriendlyDuoFightManager duoFightManager;

    private FriendlyDuoFight fight;

    private double pointsRed;

    private double pointsBlue;

    private double pointsRedMax;

    private double pointsBlueMax;

    private boolean overtime;

    private long fightId;

    private int winner = TypeUtil.INT_MIN;


    private static String FIGHT_DTO = "fight_dto";

    public FriendlyDuoFight getFight()
    {
        Vector<ErrorElement> errors = new Vector<ErrorElement>();
        if ( duoFightManager == null )
        {
            log.warn( "keine fightManager Injection" );
        }
        if ( getRequest().getParameter( PARA_FIGHTID ) == null &&
            getRequest().getParameter( HTML_PARAM_FIGHT ) == null )
        {
            //error missing parameter
            return null;
        }
        else
        {
            if ( getRequest().getParameter( HTML_PARAM_FIGHT ) != null )
            {
                setFightId( Long.valueOf( getRequest().getParameter( HTML_PARAM_FIGHT ) ) );
            }

            if ( getRequest().getParameter( PARA_FIGHTID ) != null )
            {
                setFightId( Long.valueOf( getRequest().getParameter( PARA_FIGHTID ) ) );
            }
        }
        if ( getRequest().getAttribute( FIGHT_DTO ) == null )
        {
            try
            {
                fight = duoFightManager.getDuoFight( getFightId() );
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
        if ( !DuoFightValidator.isRequiredFieldsValid( this, errors ) )
        {
            setErrorMessageVector( errors );
            return null;
        }
        if ( !DuoFightValidator.isBusinessLogicValid( this, errors ) )
        {
            setErrorMessageVector( errors );
            return null;
        }
        getFight().setPointsRed( getPointsRed() );
        getFight().setPointsBlue( getPointsBlue() );
        if ( isOvertime() )
        {
            getFight().setPointsRedMax( getPointsRedMax() );
            getFight().setPointsBlueMax( getPointsBlueMax() );
        }
        else
        {
            getFight().setPointsRedMax( getPointsRed() );
            getFight().setPointsBlueMax( getPointsBlue() );
        }

        if ( TypeUtil.INT_DEFAULT == getWinner() )
        {
            getFight().setWinnerId( TypeUtil.LONG_DEFAULT );
            getFight().setPointsBlue( TypeUtil.INT_DEFAULT );
            getFight().setPointsRed( TypeUtil.INT_DEFAULT );
            getFight().setPointsBlueMax( TypeUtil.INT_DEFAULT );
            getFight().setPointsRedMax( TypeUtil.INT_DEFAULT );
        }
        if ( IValueConstants.FIGHT_WINNER_RED == getWinner() )
        {
            getFight().setWinnerId( getFight().getTeamIdRed() );
        }
        if ( IValueConstants.FIGHT_WINNER_BLUE == getWinner() )
        {
            getFight().setWinnerId( getFight().getTeamIdBlue() );
        }

        try
        {
            duoFightManager.saveDuoFight( new ServiceExchangeContext(
                WebExchangeContextHelper.getWebExchangeContext( getSession() ).getUserId() ), fight );

            return "friendlies";
        }
        catch ( OptimisticLockingException e )
        {
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
        }
        catch ( JJWManagerException e )
        {
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
        }
        return null;
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

    public void setDuoFightManager( FriendlyDuoFightManager duoFightManager )
    {
        this.duoFightManager = duoFightManager;
    }


    public double getPointsRed()
    {
        return pointsRed;
    }


    public void setPointsRed( double pointsRed )
    {
        this.pointsRed = pointsRed;
    }


    public double getPointsBlue()
    {
        return pointsBlue;
    }


    public void setPointsBlue( double pointsBlue )
    {
        this.pointsBlue = pointsBlue;
    }

    public long getFightId()
    {
        return fightId;
    }

    public void setFightId( long fightId )
    {
        this.fightId = fightId;
    }

    public double getPointsRedMax()
    {
        return pointsRedMax;
    }

    public void setPointsRedMax( double pointsRedMax )
    {
        this.pointsRedMax = pointsRedMax;
    }

    public double getPointsBlueMax()
    {
        return pointsBlueMax;
    }

    public void setPointsBlueMax( double pointsBlueMax )
    {
        this.pointsBlueMax = pointsBlueMax;
    }

    public boolean isOvertime()
    {
        return overtime;
    }

    public void setOvertime( boolean overtime )
    {
        this.overtime = overtime;
    }

    public void setFight( FriendlyDuoFight fight )
    {
        this.fight = fight;
    }

    public int getWinner()
    {

        return ( winner == TypeUtil.INT_MIN ) ? IValueConstants.FIGHT_WINNER_RED : winner;
    }

    public void setWinner( int winner )
    {
        this.winner = winner;
    }

}
