/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightManagerImpl.java
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
import java.util.Map;

import de.jjw.dao.IDatabaseTableConstants;
import de.jjw.dao.PreviewDao;
import de.jjw.dao.fighting.FightDao;
import de.jjw.dao.fighting.FightingclassDao;
import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.FastFight;
import de.jjw.model.Preview;
import de.jjw.model.admin.FightTimes;
import de.jjw.model.admin.Fightsystem;
import de.jjw.model.fighting.Fight;
import de.jjw.model.fighting.Fighter;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.fighting.FightManager;
import de.jjw.service.impl.BaseManager;
import de.jjw.service.mapper.fighting.FastFightMapper;
import de.jjw.service.mapper.fighting.FightMapper;
import de.jjw.service.modelWeb.FightWeb;
import de.jjw.service.modelWeb.FighterWeb;
import de.jjw.util.IValueConstants;

public class FightManagerImpl
    extends BaseManager
    implements FightManager, IDatabaseTableConstants
{

    private PreviewDao previewDao;

    private FightDao fightDao;

    private FightingclassDao fightingclassDao;

    public void setFightDao( FightDao fightDao )
    {
        this.fightDao = fightDao;
    }

    public Fight getFight( Long fightId )
        throws JJWManagerException
    {

        try
        {
            return FightMapper.mapFightFromDB( fightDao.getFight( fightId ) );
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
            return FightMapper.mapFightForClockFromDB( fightDao.getFight( fightId ), local );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }

    }

    public Fight getFight( Fight fight )
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
                return FightMapper.mapFightFromDB( fightDao.getFight( fight.getId() ) );
            }
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public void saveFight( ServiceExchangeContext ctx, Fight fight )
        throws OptimisticLockingException, JJWManagerException
    {
        try
        {

            fight.setModificationId( ctx.getUserId() );
            fight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
            optimisticDao.testLocking( IDatabaseTableConstants.TABLE_FIGHT, fight.getId(), fight.getModificationDate() );
            
            //test if there was a correction
            if ( fightDao.getFight( fight.getId() ).getWinnerId() > 0 )
            {
                fight.setModifiedWith2Call( true );
            }else{
                fight.setSaveTime( new Timestamp( System.currentTimeMillis() ) );
            }
            
            FightMapper.mapFightToDB( fight, fightDao.getFight( fight.getId() ), false );

            switch ( getFightingclassDao().getFightsystemOfFightingclass( fight.getFightingclassId() ) )
            {
                case Fightsystem.DOUBLE_POOL:
                    FightingclassPoolsImpl dpool = new FightingclassPoolsImpl();
                    if ( IValueConstants.FINAL_FIGHT.equals( fight.getFlag() ) )
                    {
                        dpool.getFightingclassDoublePool( fight.getFightingclassId(), getFightingclassDao(), fightDao,
                                                          false );
                    }
                    else
                    {
                        dpool.getFightingclassDoublePool( fight.getFightingclassId(), getFightingclassDao(), fightDao,
                                                          true );
                    }
                    break;
            }
            previewDao.removePreview( fight.getId(), Preview.DISCEPLINE_FIGHTING );

        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }

    /**
     * gets the fight time for statistic (Leitstand), how quick are the tatamis work
     * 
     * @return
     * @throws JJWManagerException
     */
    public List<FightTimes> getFightTimes()
        throws JJWManagerException
    {
        try
        {
            return fightDao.getFightTimes();
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public Map<Integer, List<FastFight>> getFastestFights()
        throws JJWManagerException
    {
        try
        {
            return FastFightMapper.mapFighterListFastFightFromDB( fightDao.getFastestFights() );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public FightingclassDao getFightingclassDao()
    {
        return fightingclassDao;
    }

    public void setFightingclassDao( FightingclassDao fightingclassDao )
    {
        this.fightingclassDao = fightingclassDao;
    }

    /**
     * @param previewDao the previewDao to set
     */
    public void setPreviewDao( PreviewDao previewDao )
    {
        this.previewDao = previewDao;
    }

    @Override
    public Map<FighterWeb, List<FightWeb>> getFightsFromFighter()
        throws JJWManagerException
    {
        try
        {
            return FightMapper.mapFightMapFromDB( fightDao.getFightsFromFighter() );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    @Override
    public Map<FighterWeb, List<FightWeb>> getFightsFromOneFighter( Fighter fighter )
        throws JJWManagerException
    {
        try
        {
            return FightMapper.mapFightMapFromDB( fightDao.getFightsFromOneFighter(fighter) );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }
    
    @Override
    public Map<FighterWeb, List<FightWeb>> getFightForVisualize( long fightId )
        throws JJWManagerException
    {
        try
        {
            return FightMapper.mapFightMapFromDB( fightDao.getFightForVisualize(fightId) );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

}
