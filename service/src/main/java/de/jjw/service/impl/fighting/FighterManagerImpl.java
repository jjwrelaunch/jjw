/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FighterManagerImpl.java
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

package de.jjw.service.impl.fighting;

import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

import de.jjw.dao.IDatabaseTableConstants;
import de.jjw.dao.fighting.FighterDao;
import de.jjw.dao.fighting.WeightclassDao;
import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.admin.Fightsystem;
import de.jjw.model.fighting.Fighter;
import de.jjw.model.fighting.Fightingclass;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.exception.FighterIllegalReadyException;
import de.jjw.service.exception.FighterInUseException;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.exception.JJWPoolBlockedException;
import de.jjw.service.fighting.FighterManager;
import de.jjw.service.impl.BaseManager;
import de.jjw.service.mapper.fighting.FighterMapper;
import de.jjw.service.mapper.fighting.WeightclassMapper;
import de.jjw.service.modelWeb.FighterWeb;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;

public class FighterManagerImpl
    extends BaseManager
    implements FighterManager, IDatabaseTableConstants
{

    private FighterDao fighterDao;

    private WeightclassDao weightclassDao;

    public void setFighterDao( FighterDao fighterDao )
    {
        this.fighterDao = fighterDao;
    }


    public Fighter getFighter( Long fighterId )
    {
        // TODO Auto-generated method stub
        return null;
    }

    public List<FighterWeb> getAllFighters( Locale local )
        throws JJWManagerException
    {
        try
        {
            return FighterMapper.mapFighterListFromDB( fighterDao.getAllFighters(), local );
        }
        catch ( Exception e )
        {
            log.error( e.getMessage() );
            throw new JJWManagerException( e );
        }
    }

    public List<FighterWeb> getAllFightersInOrder( int order, boolean isAscending, Locale local )
        throws JJWManagerException
    {
        try
        {
            return FighterMapper.mapFighterListFromDB( fighterDao.getAllFightersInOrder( order, isAscending ), local );
        }
        catch ( JJWDataLayerException e )
        {
            log.error( e.getMessage() );
            throw new JJWManagerException( e );
        }
        catch ( Exception e )
        {
            log.error( e.getMessage() );
            throw new JJWManagerException( e );
        }
    }

    public List<FighterWeb> getFighterByAge( long ageId, Locale local, boolean notReg )
        throws JJWManagerException
    {
        try
        {
            return FighterMapper.mapFighterListFromDB( fighterDao.getFighterByAge( ageId, notReg ), local );
        }
        catch ( JJWDataLayerException e )
        {
            log.error( e.getMessage() );
            throw new JJWManagerException( e );
        }
        catch ( Exception e )
        {
            log.error( e.getMessage() );
            throw new JJWManagerException( e );
        }
    }

    public List<FighterWeb> getFighterByRegion( long regionId, Locale local, boolean notReg )
        throws JJWManagerException
    {
        try
        {
            return FighterMapper.mapFighterListFromDB( fighterDao.getFighterByRegion( regionId, notReg ), local );
        }
        catch ( JJWDataLayerException e )
        {
            log.error( e.getMessage() );
            throw new JJWManagerException( e );
        }
        catch ( Exception e )
        {
            log.error( e.getMessage() );
            throw new JJWManagerException( e );
        }
    }

    public List<FighterWeb> getFighterByCountry( long countryId, Locale local, boolean notReg )
        throws JJWManagerException
    {
        try
        {
            return FighterMapper.mapFighterListFromDB( fighterDao.getFighterByCountry( countryId, notReg ), local );
        }
        catch ( JJWDataLayerException e )
        {
            log.error( e.getMessage() );
            throw new JJWManagerException( e );
        }
        catch ( Exception e )
        {
            log.error( e.getMessage() );
            throw new JJWManagerException( e );
        }
    }

    public List<FighterWeb> getFighterByTeam( long teamId, Locale local, boolean notReg )
        throws JJWManagerException
    {
        try
        {
            return FighterMapper.mapFighterListFromDB( fighterDao.getFighterByTeam( teamId, notReg ), local );
        }
        catch ( JJWDataLayerException e )
        {
            log.error( e.getMessage() );
            throw new JJWManagerException( e );
        }
        catch ( Exception e )
        {
            log.error( e.getMessage() );
            throw new JJWManagerException( e );
        }
    }

    public List<FighterWeb> getFighterByFightingclass( long fightingclassId, Locale local, boolean notReg )
        throws JJWManagerException
    {
        try
        {
            return FighterMapper.mapFighterListFromDB( fighterDao.getFighterByFightingclass( fightingclassId, notReg ),
                                                       local );
        }
        catch ( JJWDataLayerException e )
        {
            log.error( e.getMessage() );
            throw new JJWManagerException( e );
        }
        catch ( Exception e )
        {
            log.error( e.getMessage() );
            throw new JJWManagerException( e );
        }
    }

    public void saveFighterList( List<FighterWeb> fighterList )
        throws OptimisticLockingException, JJWManagerException, JJWPoolBlockedException, FighterIllegalReadyException
    {
        Fighter fighterDB;
        for ( FighterWeb item : fighterList )
        {
            if ( item.isCheck() ) // only items which are marked should be saved
            {
                fighterDB = new Fighter();
                item.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                item.setModificationId( 1l );
                FighterMapper.mapFighterToDB( item, fighterDB, true );

                fighterDB.setCreateDate( new Timestamp( System.currentTimeMillis() ) );
                fighterDB.setCreateId( 1l );
                saveFighter( fighterDB );
            }
        }
    }

    public void saveFighter( Fighter fighter )
        throws OptimisticLockingException, JJWManagerException, JJWPoolBlockedException, FighterIllegalReadyException
    {
        try
        {

            Fightingclass fightingclass =
                weightclassDao.getWeightclassByAgeSexWeight( fighter.getAge().getId(), fighter.getSex(),
                                                             fighter.getWeight() );

            if ( fightingclass != null )
            {
                if ( null != fightingclass.getParentId() && !TypeUtil.isEmptyOrDefault( fightingclass.getParentId() ) )
                {
                    fighter.setOriginalFightingclassId( fightingclass.getId() );
                    fightingclass = fightingclass.getParent();
                }
                fighter.setFightingclassId( fightingclass.getId() );
                fighter.setFightingclass( fightingclass );
            }
            else
            {
                // fighter.setFightingclassId( TypeUtil.LONG_MIN );
                // // when no fighting class avaiable and ready==true than exception
                // if( IValueConstants.STRING_1.equals( fighter.getReady() ))
                // throw new FighterIllegalReadyException();
            }

            fighter.setAgeId( fighter.getAge().getId() );
            fighter.setTeamId( fighter.getTeam().getId() );
            if ( null == fighter.getId() )
            { // test if new pool is blocked
                if ( IValueConstants.STRING_1.equals( fighter.getReady() ) && null != fightingclass
                    && fightingclass.isDeleteStop() )
                {
                    throw new JJWPoolBlockedException();
                }
                fighterDao.saveFighter( fighter );
            }
            else
            { // test if new pool is blocked
                if ( fightingclass != null )
                {
                    Fighter dbFighter = fighterDao.getFighter( fighter.getId() );

                    if // when fighter is ready && dbfighter has fightingclass &&( old or new fighting
                       // isdeletestop=true)
                    ( IValueConstants.STRING_1.equals( fighter.getReady() )
                        && dbFighter.getFightingclassId() > TypeUtil.LONG_DEFAULT
                        && ( ( dbFighter.getFightingclass().isDeleteStop() && IValueConstants.STRING_1.equals( dbFighter.getReady() ) ) || fightingclass.isDeleteStop() ) )
                    {
                        if ( !fighter.equalsIgnoreFighterName( dbFighter ) )
                            throw new JJWPoolBlockedException();
                    }

                    // when old fightingclass not exists then check new pool
                    if ( IValueConstants.STRING_1.equals( fighter.getReady() )
                        && dbFighter.getFightingclassId() == TypeUtil.LONG_EMPTY && fightingclass.isDeleteStop() )
                    {
                        if ( !fighter.equalsIgnoreFighterName( dbFighter ) )
                            throw new JJWPoolBlockedException();
                    }

                    // fighter is not ready and dbfightingclass exists and dbfighter is ready and dbfightingclass is
                    // deleteStop=true
                    if ( ( IValueConstants.STRING_0.equals( fighter.getReady() )
                        && dbFighter.getFightingclassId() > TypeUtil.LONG_DEFAULT
                        && IValueConstants.STRING_1.equals( dbFighter.getReady() ) && dbFighter.getFightingclass().isDeleteStop() ) )
                    {
                        if ( !fighter.equalsIgnoreFighterName( dbFighter ) )
                            throw new JJWPoolBlockedException();
                    }
                }
                fighter.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                optimisticDao.testLocking( TABLE_FIGHTER, fighter.getId(), fighter.getModificationDate() );
                FighterMapper.mapFighterToDB( fighter, fighterDao.getFighter( fighter.getId() ), false );
            }
        }
        catch ( JJWDataLayerException e )
        {
            log.error( e.getMessage() );
            throw new JJWManagerException( e );
        }

    }

    public void registerFast( long fighterId, ServiceExchangeContext ctx )
        throws JJWManagerException, JJWPoolBlockedException
    {
        try
        {
            Fighter fighter = fighterDao.getFighter( fighterId );

            try
            {
                fighter.getFightingclass().isDeleteStop();
            }
            catch ( Exception e )
            {
                log.error( e.getMessage() );
                throw new JJWManagerException( e );
            }

            if ( fighter.getFightingclass().isDeleteStop() )
                throw new JJWPoolBlockedException();

            fighter.setModificationId( ctx.getUserId() );
            fighter.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
            fighter.setReady( "1" );

        }
        catch ( JJWDataLayerException e )
        {
            log.error( e.getMessage() );
            throw new JJWManagerException( e );
        }
    }

    public void removeFighter( Long fighterId )
        throws JJWManagerException, FighterInUseException
    {
        try
        {
            if ( !fighterDao.isFighterInUse( fighterId ) )
            {
                fighterDao.removeFighter( fighterId );
            }
            else
            {
                throw new FighterInUseException();
            }
            // dao.removeFighter(fighter);
        }
        catch ( FighterInUseException e )
        {
            throw e;
        }
        catch ( Exception e )
        {
            log.error( e.getMessage() );
            throw new JJWManagerException( e );
        }
    }

    public void removeFighter( Fighter fighter )
        throws JJWManagerException, FighterInUseException
    {
        try
        {
            if ( !fighterDao.isFighterInUse( fighter.getId() ) )
            {
                fighterDao.removeFighter( fighter );
            }
            else
            {
                throw new FighterInUseException();
            }
            // dao.removeFighter(fighter);
        }
        catch ( FighterInUseException e )
        {
            throw e;
        }
        catch ( Exception e )
        {
            log.error( e.getMessage() );
            throw new JJWManagerException( e );
        }
    }

    public List<FighterWeb> getAllNotRegistratedFighters( Locale local )
    {
        return FighterMapper.mapFighterListFromDB( fighterDao.getNotRegistratedFighters(), local );
    }

    public List<FighterWeb> getAllRegistratedFighters( Locale local )
        throws JJWManagerException
    {
        try
        {
            return FighterMapper.mapFighterListFromDB( fighterDao.getRegistratedFighters(), local );
        }
        catch ( Exception e )
        {
            log.error( e.getMessage() );
            throw new JJWManagerException( e );
        }
    }

    public boolean existFighter( Fighter fighter )
    {
        return fighterDao.existFighter( fighter );
    }

    public List<FighterWeb> getPossibleDublicateFighters( Fighter fighter, Locale local )
    {
        return FighterMapper.mapFighterListFromDB( fighterDao.getPossibleDublicateFighters( fighter ), local );
    }

    public List<FighterWeb> getFighterResultList( Locale local )
        throws JJWManagerException
    {
        try
        {
            return FighterMapper.mapFighterListFromDB( fighterDao.getFighterResultList(), local );
        }
        catch ( JJWDataLayerException e )
        {
            log.error( e.getMessage() );
            throw new JJWManagerException( e );
        }
    }

    public void toggleMarkForExport( Fighter fighter, ServiceExchangeContext ctx )
        throws JJWManagerException, OptimisticLockingException
    {
        try
        {
            Fighter fighterDB = fighterDao.getFighter( fighter.getId() );
            optimisticDao.testLocking( TABLE_FIGHTER, fighter.getId(), fighter.getModificationDate() );

            if ( fighter.isMarkForExport() )              
                fighterDB.setMarkForExport( false );
            else
                fighterDB.setMarkForExport( true );
            return;
        }
        catch ( JJWDataLayerException e )
        {
            log.error( e.getMessage() );
            throw new JJWManagerException( e );
        }        
    }
    
    public List<FighterWeb> getFightersForJSONExport( Locale local )
                    throws JJWManagerException
    {
        try
        {
            return FighterMapper.mapFighterListFromDB( fighterDao.getFightersForJSONExport(), local );
        }
        catch ( Exception e )
        {
            log.error( e.getMessage() );
            throw new JJWManagerException( e );
        }
    }
    
    
    public WeightclassDao getWeightclassDao()
    {
        return weightclassDao;
    }


    public void setWeightclassDao( WeightclassDao weightclassDao )
    {
        this.weightclassDao = weightclassDao;
    }


}
