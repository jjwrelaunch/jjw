/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoTeamManagerImpl.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:44
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
import java.util.Locale;

import de.jjw.dao.duo.DuoTeamDao;
import de.jjw.dao.duo.DuoclassDao;
import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.duo.DuoTeam;
import de.jjw.model.duo.Duoclass;
import de.jjw.model.fighting.Fighter;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.duo.DuoTeamManager;
import de.jjw.service.exception.DuoTeamIllegalReadyException;
import de.jjw.service.exception.DuoTeamInUseException;
import de.jjw.service.exception.FighterIllegalReadyException;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.exception.JJWPoolBlockedException;
import de.jjw.service.impl.BaseManager;
import de.jjw.service.mapper.duo.DuoTeamMapper;
import de.jjw.service.modelWeb.DuoTeamWeb;
import de.jjw.service.modelWeb.FighterWeb;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;

public class DuoTeamManagerImpl
    extends BaseManager
    implements DuoTeamManager
{

    private DuoTeamDao duoTeamDao;

    private DuoclassDao duoclassDao;


    public List<DuoTeamWeb> getAllDuoTeams( Locale local )
        throws JJWManagerException
    {
        try
        {
            return DuoTeamMapper.mapDuoTeamListFromDB( duoTeamDao.getAllDuoTeams(), local );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public List<DuoTeamWeb> getAllDuoTeamsInOrder( int order, boolean isAscending, Locale local )
        throws JJWManagerException
    {
        try
        {
            return DuoTeamMapper.mapDuoTeamListFromDB( duoTeamDao.getAllDuoTeamsInOrder( order, isAscending ), local );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }

    public void saveDuoTeamList( List<DuoTeamWeb> duoTeamList )
        throws OptimisticLockingException, JJWManagerException, JJWPoolBlockedException, DuoTeamIllegalReadyException
    {
        DuoTeam duoTeamDB;
        for ( DuoTeamWeb item : duoTeamList )
        {

            if ( item.isCheck() ) // only items which are marked should be saved
            {
                duoTeamDB = new DuoTeam();
                item.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                item.setModificationId( 1l );
                DuoTeamMapper.mapDuoTeamToDB( item, duoTeamDB, true );

                duoTeamDB.setCreateDate( new Timestamp( System.currentTimeMillis() ) );
                duoTeamDB.setCreateId( 1l );
                saveDuoTeam( duoTeamDB );
            }
        }
    }

    public void saveDuoTeam( DuoTeam duoTeam )
        throws OptimisticLockingException, JJWManagerException, JJWPoolBlockedException, DuoTeamIllegalReadyException
    {
        try
        {
            Duoclass duoclass = duoclassDao.getDuoclassByAgeSex( duoTeam.getAge().getId(), duoTeam.getSex() );

            if ( duoclass != null )
            {
                if ( null != duoclass.getParentId() && !TypeUtil.isEmptyOrDefault( duoclass.getParentId() ) )
                {
                    duoTeam.setOriginalDuoclassId( duoclass.getId() );
                    duoclass = duoclass.getParent();
                }
                duoTeam.setDuoclassId( duoclass.getId() );
                duoTeam.setDuoclass( duoclass );
            }
            else
            {
                duoTeam.setDuoclassId( TypeUtil.LONG_MIN );
                // when no duo class avaiable and ready==true than exception
                if ( IValueConstants.STRING_1.equals( duoTeam.getReady() ) )
                    throw new DuoTeamIllegalReadyException();
            }

            duoTeam.setAgeId( duoTeam.getAge().getId() );
            duoTeam.setTeamId( duoTeam.getTeam().getId() );

            if ( null == duoTeam.getId() )
            { // test if new pool is blocked
                if ( IValueConstants.STRING_1.equals( duoTeam.getReady() ) && null != duoclass
                    && duoclass.isDeleteStop() )
                {
                    throw new JJWPoolBlockedException();
                }
                duoTeamDao.saveDuoTeam( duoTeam );
            }
            else
            { // test if new pool is blocked
              // if ( ( IValueConstants.STRING_1.equals( duoTeam.getReady() ) && ( duoTeamDao.getDuoTeam(
              // duoTeam.getId()
              // ).getDuoclass().isDeleteStop() || duoclass.isDeleteStop() ) )
              // || ( IValueConstants.STRING_0.equals( duoTeam.getReady() )
              // && IValueConstants.STRING_1.equals( duoTeamDao.getDuoTeam( duoTeam.getId() ).getReady() ) &&
              // duoTeamDao.getDuoTeam( duoTeam.getId() ).getDuoclass().isDeleteStop() ) )
              // {
              // if ( !duoTeam.equalsIgnoreFighterName( duoTeamDao.getDuoTeam( duoTeam.getId() ) ) )
              // throw new JJWPoolBlockedException();
              // }

                DuoTeam dbDuoTeam = duoTeamDao.getDuoTeam( duoTeam.getId() );

                if // when fighter is ready && dbfighter has fightingclass &&( old or new fighting
                   // isdeletestop=true)
                ( IValueConstants.STRING_1.equals( duoTeam.getReady() )
                    && dbDuoTeam.getDuoclassId() > TypeUtil.LONG_DEFAULT
                    && ( ( dbDuoTeam.getDuoclass().isDeleteStop() && IValueConstants.STRING_1.equals( dbDuoTeam.getReady() ) ) || duoclass.isDeleteStop() ) )
                {
                    if ( !duoTeam.equalsIgnoreFighterName( dbDuoTeam ) )
                        throw new JJWPoolBlockedException();
                }

                // when old fightingclass not exists then check new pool
                if ( IValueConstants.STRING_1.equals( duoTeam.getReady() )
                    && dbDuoTeam.getDuoclassId() == TypeUtil.LONG_EMPTY && duoclass.isDeleteStop() )
                {
                    if ( !duoTeam.equalsIgnoreFighterName( dbDuoTeam ) )
                        throw new JJWPoolBlockedException();
                }

                // fighter is not ready and dbfightingclass exists and dbfighter is ready and dbfightingclass is
                // deleteStop=true
                if ( ( IValueConstants.STRING_0.equals( duoTeam.getReady() )
                    && dbDuoTeam.getDuoclassId() > TypeUtil.LONG_DEFAULT
                    && IValueConstants.STRING_1.equals( dbDuoTeam.getReady() ) && dbDuoTeam.getDuoclass().isDeleteStop() ) )
                {
                    if ( !duoTeam.equalsIgnoreFighterName( dbDuoTeam ) )
                        throw new JJWPoolBlockedException();
                }

                duoTeam.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                optimisticDao.testLocking( TABLE_DUOTEAM, duoTeam.getId(), duoTeam.getModificationDate() );
                DuoTeamMapper.mapDuoTeamToDB( duoTeam, duoTeamDao.getDuoTeam( duoTeam.getId() ), false );
            }
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }

    }

    public List<DuoTeamWeb> getDuoTeamResultList( Locale local )
        throws JJWManagerException
    {
        try
        {
            return DuoTeamMapper.mapDuoTeamListFromDB( duoTeamDao.getDuoTeamResultList(), local );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public boolean existDuoTeam( DuoTeam duoTeam )
        throws JJWManagerException
    {
        try
        {
            return duoTeamDao.existDuoTeam( duoTeam );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public List<DuoTeamWeb> getNotRegistratedDuoTeams( Locale local )
        throws JJWManagerException
    {
        try
        {
            return DuoTeamMapper.mapDuoTeamListFromDB( duoTeamDao.getNotRegistratedDuoTeams(), local );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }


    public List<DuoTeamWeb> getDuoTeamByAge( long ageId, Locale local, boolean notReg )
        throws JJWManagerException
    {
        try
        {
            return DuoTeamMapper.mapDuoTeamListFromDB( duoTeamDao.getDuoTeamByAge( ageId, notReg ), local );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }

    public List<DuoTeamWeb> getDuoTeamByRegion( long regionId, Locale local, boolean notReg )
        throws JJWManagerException
    {
        try
        {
            return DuoTeamMapper.mapDuoTeamListFromDB( duoTeamDao.getDuoTeamByRegion( regionId, notReg ), local );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }

    public List<DuoTeamWeb> getDuoTeamByCountry( long countryId, Locale local, boolean notReg )
        throws JJWManagerException
    {
        try
        {
            return DuoTeamMapper.mapDuoTeamListFromDB( duoTeamDao.getDuoTeamByCountry( countryId, notReg ), local );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }

    public List<DuoTeamWeb> getDuoTeamByTeam( long teamId, Locale local, boolean notReg )
        throws JJWManagerException
    {
        try
        {
            return DuoTeamMapper.mapDuoTeamListFromDB( duoTeamDao.getDuoTeamByTeam( teamId, notReg ), local );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }

    public List<DuoTeamWeb> getDuoTeamByDuoclass( long duoclassId, Locale local, boolean notReg )
        throws JJWManagerException
    {
        try
        {
            return DuoTeamMapper.mapDuoTeamListFromDB( duoTeamDao.getDuoTeamByDuoclass( duoclassId, notReg ), local );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }


    public DuoTeamDao getDuoTeamDao()
    {
        return duoTeamDao;
    }

    public void setDuoTeamDao( DuoTeamDao duoTeamDao )
    {
        this.duoTeamDao = duoTeamDao;
    }

    public DuoclassDao getDuoclassDao()
    {
        return duoclassDao;
    }

    public void setDuoclassDao( DuoclassDao duoclassDao )
    {
        this.duoclassDao = duoclassDao;
    }

    @Override
    public void registerFast( Long duoTeamId, ServiceExchangeContext serviceExchangeContext )
        throws JJWManagerException, JJWPoolBlockedException
    {
        try
        {
            DuoTeam duoTeam = duoTeamDao.getDuoTeam( duoTeamId );

            try
            {
                duoTeam.getDuoclass().isDeleteStop();
            }
            catch ( Exception e )
            {
                log.error( e.getMessage() );
                throw new JJWManagerException( e );
            }

            if ( duoTeam.getDuoclass().isDeleteStop() )
                throw new JJWPoolBlockedException();

            duoTeam.setModificationId( serviceExchangeContext.getUserId() );
            duoTeam.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
            duoTeam.setReady( "1" );

        }
        catch ( JJWDataLayerException e )
        {
            log.error( e.getMessage() );
            throw new JJWManagerException( e );
        }

    }

    public void removeDuoTeam( Long teamId )
        throws JJWManagerException, DuoTeamInUseException
    {
        try
        {
            if ( !duoTeamDao.isDuoTeamInUse( teamId ) )
            {
                duoTeamDao.removeDuoTeam( teamId );
            }
            else
            {
                throw new DuoTeamInUseException();
            }
        }
        catch ( DuoTeamInUseException e )
        {
            throw e;
        }
        catch ( Exception e )
        {
            log.error( e.getMessage() );
            throw new JJWManagerException( e );
        }
    }

    public void removeDuoTeam( DuoTeam duoTeam )
        throws JJWManagerException, DuoTeamInUseException
    {
        try
        {
            if ( !duoTeamDao.isDuoTeamInUse( duoTeam.getId() ) )
            {
                duoTeamDao.removeDuoTeam( duoTeam );
            }
            else
            {
                throw new DuoTeamInUseException();
            }

        }
        catch ( DuoTeamInUseException e )
        {
            throw e;
        }
        catch ( Exception e )
        {
            log.error( e.getMessage() );
            throw new JJWManagerException( e );
        }
    }

    @Override
    public List<DuoTeamWeb> getAllRegistratedDuoTeams( Locale local )
        throws JJWManagerException
    {
        try
        {
            return DuoTeamMapper.mapDuoTeamListFromDB( duoTeamDao.getRegistratedDuoTeams(), local );
        }
        catch ( Exception e )
        {
            log.error( e.getMessage() );
            throw new JJWManagerException( e );
        }
    }

    public void toggleMarkForExport( DuoTeam duoTeam, ServiceExchangeContext ctx )
                    throws JJWManagerException, OptimisticLockingException
    {
        try
        {
            DuoTeam duoTeamDB = duoTeamDao.getDuoTeam( duoTeam.getId() );
            optimisticDao.testLocking( TABLE_DUOTEAM, duoTeam.getId(), duoTeam.getModificationDate() );

            if ( duoTeam.isMarkForExport() )
                duoTeamDB.setMarkForExport( false );
            else
                duoTeamDB.setMarkForExport( true );
            return;
        }
        catch ( JJWDataLayerException e )
        {
            log.error( e.getMessage() );
            throw new JJWManagerException( e );
        }
    }
    
    public List<DuoTeamWeb> getDuosForJSONExport( Locale local )
                    throws JJWManagerException
    {
        try
        {
            return DuoTeamMapper.mapDuoTeamListFromDB( duoTeamDao.getDuosForJSONExport(), local );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }
}
