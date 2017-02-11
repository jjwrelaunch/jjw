/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FriendlyFightNewaManagerImpl.java
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

package de.jjw.service.impl.newa;

import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

import de.jjw.dao.IDatabaseTableConstants;
import de.jjw.dao.PreviewDao;
import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.dao.newa.FriendlyNewaFightDao;
import de.jjw.model.Preview;
import de.jjw.model.newa.FriendlyNewaFight;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.impl.BaseManager;
import de.jjw.service.mapper.newa.NewaFightMapper;
import de.jjw.service.modelWeb.NewaFightWeb;
import de.jjw.service.newa.FriendlyNewaFightManager;

public class FriendlyNewaFightManagerImpl
    extends BaseManager
    implements FriendlyNewaFightManager, IDatabaseTableConstants
{

    private PreviewDao previewDao;

    private FriendlyNewaFightDao friendlyNewaFightDao;


    public void setFriendlyNewaFightDao( FriendlyNewaFightDao friendlyNewaFightDao )
    {
        this.friendlyNewaFightDao = friendlyNewaFightDao;
    }

    public List<NewaFightWeb> getAllFriendlyFights()
        throws JJWManagerException
    {
        try
        {
            return NewaFightMapper.mapFriendlyFightListFromDB( friendlyNewaFightDao.getAllFriendlyFights() );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public FriendlyNewaFight getFight( Long fightId )
        throws JJWManagerException
    {
        try
        {
            return (FriendlyNewaFight) NewaFightMapper.mapFriendlyFightFromDB( friendlyNewaFightDao.getFight( fightId ) );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public NewaFightWeb getFightForClock( Long fightId, Locale local )
        throws JJWManagerException
    {

        try
        {
            return NewaFightMapper.mapFriendlyFightForClockFromDB( friendlyNewaFightDao.getFight( fightId ), local );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }

    }

    public FriendlyNewaFight getFight( FriendlyNewaFight fight )
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
                return (FriendlyNewaFight) NewaFightMapper.mapFriendlyFightFromDB( friendlyNewaFightDao.getFight( fight.getId() ) );
            }
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public void saveFight( ServiceExchangeContext ctx, FriendlyNewaFight fight )
        throws OptimisticLockingException, JJWManagerException
    {
        try
        {

            fight.setModificationId( ctx.getUserId() );
            fight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
            optimisticDao.testLocking( IDatabaseTableConstants.TABLE_FRIENDLY_NEWA_FIGHT, fight.getId(),
                                       fight.getModificationDate() );

            // test if there was a correction
            if ( friendlyNewaFightDao.getFight( fight.getId() ).getWinnerId() > 0 )
            {
                fight.setModifiedWith2Call( true );
            }
            else
            {
                fight.setSaveTime( new Timestamp( System.currentTimeMillis() ) );
            }

            NewaFightMapper.mapFriendlyFightToDB( fight, friendlyNewaFightDao.getFight( fight.getId() ), false );

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
            FriendlyNewaFight fight = new FriendlyNewaFight();
            fight.setModificationId( ctx.getUserId() );
            fight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
            fight.setCreateId( ctx.getUserId() );
            fight.setCreateDate( new Timestamp( System.currentTimeMillis() ) );
            fight.setFighterIdRed( fighterIdRed );
            fight.setFighterIdBlue( fighterIdBlue );

            friendlyNewaFightDao.saveFriendlyFight( fight );
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
            friendlyNewaFightDao.removeFriendlyFight( fightId );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

}
