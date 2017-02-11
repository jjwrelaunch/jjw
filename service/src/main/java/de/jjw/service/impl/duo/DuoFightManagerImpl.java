/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoFightManagerImpl.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 21:54:41
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
import java.util.Map;

import de.jjw.dao.PreviewDao;
import de.jjw.dao.duo.DuoFightDao;
import de.jjw.dao.duo.DuoclassDao;
import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.Preview;
import de.jjw.model.admin.Fightsystem;
import de.jjw.model.duo.DuoFight;
import de.jjw.model.duo.HighestDuoPoints;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.duo.DuoFightManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.impl.BaseManager;
import de.jjw.service.mapper.duo.DuoFightMapper;
import de.jjw.service.mapper.duo.HighestDuoPointsMapper;
import de.jjw.service.modelWeb.DuoFightWeb;
import de.jjw.util.IValueConstants;

public class DuoFightManagerImpl
    extends BaseManager
    implements DuoFightManager
{

    private PreviewDao previewDao;

    private DuoFightDao duoFightDao;

    private DuoclassDao duoclassDao;

    public void setDuoFightDao( DuoFightDao duoFightDao )
    {
        this.duoFightDao = duoFightDao;
    }

    public DuoFight getDuoFight( Long fightId )
        throws JJWManagerException
    {

        try
        {
            return DuoFightMapper.mapFightFromDB( duoFightDao.getDuoFight( fightId ) );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }

    }

    public DuoFight getDuoFight( DuoFight fight )
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
                return DuoFightMapper.mapFightFromDB( duoFightDao.getDuoFight( fight.getId() ) );
            }
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    @Override
    public DuoFightWeb getDuoFightForClock( Long fightId )
        throws JJWManagerException
    {
        try
        {
            return DuoFightMapper.mapFightForClockFromDB( duoFightDao.getDuoFight( fightId ) );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public void saveDuoFight( ServiceExchangeContext ctx, DuoFight fight )
        throws OptimisticLockingException, JJWManagerException
    {
        try
        {
            fight.setModificationId( ctx.getUserId() );
            fight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
            optimisticDao.testLocking( TABLE_DUO_FIGHT, fight.getId(), fight.getModificationDate() );
            DuoFightMapper.mapFightToDB( fight, duoFightDao.getDuoFight( fight.getId() ), false );

            switch ( getDuoclassDao().getFightsystemOfDuoclass( fight.getDuoclassId() ) )
            {
                case Fightsystem.DOUBLE_POOL:
                    DuoclassPoolsImpl dpool = new DuoclassPoolsImpl();
                    if ( IValueConstants.FINAL_FIGHT.equals( fight.getFlag() ) )
                    {
                        dpool.getDuoclassDoublePool( fight.getDuoclassId(), getDuoclassDao(), duoFightDao, false );
                    }
                    else
                    {
                        dpool.getDuoclassDoublePool( fight.getDuoclassId(), getDuoclassDao(), duoFightDao, true );
                    }
                    break;
            }
            previewDao.removePreview( fight.getId(), Preview.DISCEPLINE_DUO );

        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }

    public Map<Integer, List<HighestDuoPoints>> getHighestDuoPoints()
        throws JJWManagerException
    {
        try
        {
            return HighestDuoPointsMapper.mapHighestDuoPointsFromDB( duoFightDao.getHighestDuoPoints() );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public DuoclassDao getDuoclassDao()
    {
        return duoclassDao;
    }

    public void setDuoclassDao( DuoclassDao duoclassDao )
    {
        this.duoclassDao = duoclassDao;
    }

    /**
     * @param previewDao the previewDao to set
     */
    public void setPreviewDao( PreviewDao previewDao )
    {
        this.previewDao = previewDao;
    }

}
