/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaFightManagerImpl.java
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
import java.util.Map;

import de.jjw.dao.IDatabaseTableConstants;
import de.jjw.dao.PreviewDao;
import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.dao.newa.NewaFightDao;
import de.jjw.dao.newa.NewaclassDao;
import de.jjw.model.FastFight;
import de.jjw.model.Preview;
import de.jjw.model.admin.FightTimes;
import de.jjw.model.admin.Fightsystem;
import de.jjw.model.newa.NewaFight;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.impl.BaseManager;
import de.jjw.service.mapper.newa.FastFightMapper;
import de.jjw.service.mapper.newa.NewaFightMapper;
import de.jjw.service.modelWeb.NewaFightWeb;
import de.jjw.service.newa.NewaFightManager;
import de.jjw.util.IValueConstants;

public class NewaFightManagerImpl
    extends BaseManager
    implements NewaFightManager, IDatabaseTableConstants
{

    private PreviewDao previewDao;

    private NewaFightDao newaFightDao;

    private NewaclassDao newaclassDao;

    public void setNewaFightDao( NewaFightDao fightDao )
    {
        this.newaFightDao = fightDao;
    }

    public NewaFight getFight( Long fightId )
        throws JJWManagerException
    {

        try
        {
            return NewaFightMapper.mapFightFromDB( newaFightDao.getFight( fightId ) );
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
            return NewaFightMapper.mapFightForClockFromDB( newaFightDao.getFight( fightId ), local );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }

    }

    public NewaFight getFight( NewaFight fight )
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
                return NewaFightMapper.mapFightFromDB( newaFightDao.getFight( fight.getId() ) );
            }
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public void saveFight( ServiceExchangeContext ctx, NewaFight fight )
        throws OptimisticLockingException, JJWManagerException
    {
        try
        {

            fight.setModificationId( ctx.getUserId() );
            fight.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
            optimisticDao.testLocking( IDatabaseTableConstants.TABLE_NEWA_FIGHT, fight.getId(),
                                       fight.getModificationDate() );
            
            //test if there was a correction
            if ( newaFightDao.getFight( fight.getId() ).getWinnerId() > 0 )
            {
                fight.setModifiedWith2Call( true );
            }else{
                fight.setSaveTime( new Timestamp( System.currentTimeMillis() ) );
            }
            
            NewaFightMapper.mapFightToDB( fight, newaFightDao.getFight( fight.getId() ), false );

            switch ( getNewaclassDao().getFightsystemOfNewaclass( fight.getNewaclassId() ) )
            {
                case Fightsystem.DOUBLE_POOL:
                    NewaclassPoolsImpl dpool = new NewaclassPoolsImpl();
                    if ( IValueConstants.FINAL_FIGHT.equals( fight.getFlag() ) )
                    {
                        dpool.getNewaclassDoublePool( fight.getNewaclassId(), getNewaclassDao(), newaFightDao,
                                                          false );
                    }
                    else
                    {
                        dpool.getNewaclassDoublePool( fight.getNewaclassId(), getNewaclassDao(), newaFightDao,
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
            return newaFightDao.getFightTimes();
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
            return FastFightMapper.mapFighterListFastFightFromDB( newaFightDao.getFastestFights() );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public NewaclassDao getNewaclassDao()
    {
        return newaclassDao;
    }

    public void setNewaclassDao( NewaclassDao newaclassDao )
    {
        this.newaclassDao = newaclassDao;
    }

    /**
     * @param previewDao the previewDao to set
     */
    public void setPreviewDao( PreviewDao previewDao )
    {
        this.previewDao = previewDao;
    }

}
