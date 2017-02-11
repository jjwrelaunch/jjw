/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FriendlyDuoFightManagerImpl.java
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

package de.jjw.service.impl.duo;

import java.sql.Timestamp;
import java.util.List;

import de.jjw.dao.IDatabaseTableConstants;
import de.jjw.dao.PreviewDao;
import de.jjw.dao.duo.FriendlyDuoFightDao;
import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.Preview;
import de.jjw.model.duo.FriendlyDuoFight;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.duo.FriendlyDuoFightManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.impl.BaseManager;
import de.jjw.service.mapper.duo.DuoFightMapper;
import de.jjw.service.modelWeb.DuoFightWeb;

public class FriendlyDuoFightManagerImpl
    extends BaseManager
    implements FriendlyDuoFightManager, IDatabaseTableConstants
{

    private PreviewDao previewDao;

    private FriendlyDuoFightDao friendlyDuoFightDao;


    public void setFriendlyDuoFightDao( FriendlyDuoFightDao fightDao )
    {
        this.friendlyDuoFightDao = fightDao;
    }

    public List<DuoFightWeb> getAllFriendlyDuoFights()
        throws JJWManagerException
    {
        try
        {
            return DuoFightMapper.mapFriendlyFightListFromDB( friendlyDuoFightDao.getAllFriendlyDuoFights() );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public FriendlyDuoFight getDuoFight( Long fightId )
        throws JJWManagerException
    {

        try
        {
            return (FriendlyDuoFight) DuoFightMapper.mapFriendlyFightFromDB( friendlyDuoFightDao.getDuoFight( fightId ) );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }

    }

    public DuoFightWeb getDuoFightForClock( Long fightId )
        throws JJWManagerException
    {

        try
        {
            return DuoFightMapper.mapFriendlyFightForClockFromDB( friendlyDuoFightDao.getDuoFight( fightId ) );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }

    }

    public FriendlyDuoFight getDuoFight( FriendlyDuoFight fight )
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
                return (FriendlyDuoFight) DuoFightMapper.mapFriendlyFightFromDB( friendlyDuoFightDao.getDuoFight( fight.getId() ) );
            }
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public void saveDuoFight( ServiceExchangeContext ctx, FriendlyDuoFight fight )
        throws OptimisticLockingException, JJWManagerException
    {
        try
        {
            fight.setModificationId( ctx.getUserId() );
            fight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
            optimisticDao.testLocking( IDatabaseTableConstants.TABLE_FRIENDLY_DUO_FIGHT, fight.getId(),
                                       fight.getModificationDate() );

            DuoFightMapper.mapFriendlyFightToDB( fight, friendlyDuoFightDao.getDuoFight( fight.getId() ), false );

            previewDao.removePreview( fight.getId(), Preview.DISCEPLINE_FIGHTING );
        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }

    public void createFriendlyDuoFight( ServiceExchangeContext ctx, long teamIdRed, long teamIdBlue )
        throws JJWManagerException
    {
        try
        {
            FriendlyDuoFight fight = new FriendlyDuoFight();
            fight.setModificationId( ctx.getUserId() );
            fight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
            fight.setCreateId( ctx.getUserId() );
            fight.setCreateDate( new Timestamp( System.currentTimeMillis() ) );
            fight.setTeamIdRed( teamIdRed );
            fight.setTeamIdBlue( teamIdBlue );

            friendlyDuoFightDao.saveFriendlyDuoFight( fight );
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
    public void removeFriendlyDuoFight( long fightId )
        throws JJWManagerException
    {
        try
        {
            friendlyDuoFightDao.removeDuoFriendlyFight( fightId );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

}
