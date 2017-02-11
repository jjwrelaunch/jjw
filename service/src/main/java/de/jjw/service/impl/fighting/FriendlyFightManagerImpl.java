/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FriendlyFightManagerImpl.java
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

package de.jjw.service.impl.fighting;

import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

import de.jjw.dao.IDatabaseTableConstants;
import de.jjw.dao.PreviewDao;
import de.jjw.dao.fighting.FriendlyFightDao;
import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.Preview;
import de.jjw.model.fighting.FriendlyFight;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.fighting.FriendlyFightManager;
import de.jjw.service.impl.BaseManager;
import de.jjw.service.mapper.fighting.FightMapper;
import de.jjw.service.modelWeb.FightWeb;

public class FriendlyFightManagerImpl
    extends BaseManager
    implements FriendlyFightManager, IDatabaseTableConstants
{

    private PreviewDao previewDao;

    private FriendlyFightDao friendlyFightDao;


    public void setFriendlyFightDao( FriendlyFightDao fightDao )
    {
        this.friendlyFightDao = fightDao;
    }

    public List<FightWeb> getAllFriendlyFights()
        throws JJWManagerException
    {
        try
        {
            return FightMapper.mapFriendlyFightListFromDB( friendlyFightDao.getAllFriendlyFights() );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public FriendlyFight getFight( Long fightId )
        throws JJWManagerException
    {
        try
        {
            return (FriendlyFight) FightMapper.mapFriendlyFightFromDB( friendlyFightDao.getFight( fightId ) );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public FightWeb getFightForClock( Long fightId, Locale local )
        throws JJWManagerException
    {

        try
        {
            return FightMapper.mapFriendlyFightForClockFromDB( friendlyFightDao.getFight( fightId ), local );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }

    }

    public FriendlyFight getFight( FriendlyFight fight )
        throws JJWManagerException
    {
        try
        {
            if ( fight == null )
            {
                log.error( "FightManagerImpl.getFight  Fight is null" );
                throw new JJWManagerException();
            }
            else
            {
                return (FriendlyFight) FightMapper.mapFriendlyFightFromDB( friendlyFightDao.getFight( fight.getId() ) );
            }
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public void saveFight( ServiceExchangeContext ctx, FriendlyFight fight )
        throws OptimisticLockingException, JJWManagerException
    {
        try
        {

            fight.setModificationId( ctx.getUserId() );
            fight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
            optimisticDao.testLocking( IDatabaseTableConstants.TABLE_FRIENDLY_FIGHT, fight.getId(),
                                       fight.getModificationDate() );

            // test if there was a correction
            if ( friendlyFightDao.getFight( fight.getId() ).getWinnerId() > 0 )
            {
                fight.setModifiedWith2Call( true );
            }
            else
            {
                fight.setSaveTime( new Timestamp( System.currentTimeMillis() ) );
            }

            FightMapper.mapFriendlyFightToDB( fight, friendlyFightDao.getFight( fight.getId() ), false );

            previewDao.removePreview( fight.getId(), Preview.DISCEPLINE_FIGHTING );

        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }

    public void createFriendlyFight( ServiceExchangeContext ctx, long fighterIdRed, long fighterIdBlue )
        throws JJWManagerException
    {
        try
        {
            FriendlyFight fight = new FriendlyFight();
            fight.setModificationId( ctx.getUserId() );
            fight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
            fight.setCreateId( ctx.getUserId() );
            fight.setCreateDate( new Timestamp( System.currentTimeMillis() ) );
            fight.setFighterIdRed( fighterIdRed );
            fight.setFighterIdBlue( fighterIdBlue );

            friendlyFightDao.saveFriendlyFight( fight );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }



    /**
     * @param previewDao the previewDao to set
     */
    public void setPreviewDao( PreviewDao previewDao )
    {
        this.previewDao = previewDao;
    }

    @Override
    public void removeFriendlyFight( long fightId )
        throws JJWManagerException
    {
        try
        {
            friendlyFightDao.removeFriendlyFight( fightId );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

}
