/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaFighterManagerImpl.java
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

package de.jjw.service.impl.newa;

import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

import de.jjw.dao.IDatabaseTableConstants;
import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.dao.newa.NewaFighterDao;
import de.jjw.dao.newa.NewaWeightclassDao;
import de.jjw.model.fighting.Fighter;
import de.jjw.model.newa.NewaFighter;
import de.jjw.model.newa.Newaclass;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.exception.DuoTeamIllegalReadyException;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.exception.JJWPoolBlockedException;
import de.jjw.service.exception.NewaFighterIllegalReadyException;
import de.jjw.service.exception.NewaFighterInUseException;
import de.jjw.service.impl.BaseManager;
import de.jjw.service.mapper.fighting.FighterMapper;
import de.jjw.service.mapper.newa.NewaFighterMapper;
import de.jjw.service.modelWeb.DuoTeamWeb;
import de.jjw.service.modelWeb.FighterWeb;
import de.jjw.service.modelWeb.NewaFighterWeb;
import de.jjw.service.newa.NewaFighterManager;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;

public class NewaFighterManagerImpl
    extends BaseManager
    implements NewaFighterManager, IDatabaseTableConstants
{

    private NewaFighterDao newaFighterDao;

    private NewaWeightclassDao newaWeightclassDao;

    public void setNewaFighterDao( NewaFighterDao fighterDao )
    {
        this.newaFighterDao = fighterDao;
    }


    public NewaFighter getFighter( Long fighterId )
    {
        // TODO Auto-generated method stub
        return null;
    }

    public List<NewaFighterWeb> getAllNewaFighters( Locale local )
        throws JJWManagerException
    {
        try
        {
            return NewaFighterMapper.mapFighterListFromDB( newaFighterDao.getAllFighters(), local );
        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }

    public List<NewaFighterWeb> getAllFightersInOrder( int order, boolean isAscending, Locale local )
        throws JJWManagerException
    {
        try
        {
            return NewaFighterMapper.mapFighterListFromDB( newaFighterDao.getAllFightersInOrder( order, isAscending ),
                                                           local );
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

    public List<NewaFighterWeb> getFighterByAge( long ageId, Locale local, boolean notReg )
        throws JJWManagerException
    {
        try
        {
            return NewaFighterMapper.mapFighterListFromDB( newaFighterDao.getFighterByAge( ageId, notReg ), local );
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

    public List<NewaFighterWeb> getFighterByRegion( long regionId, Locale local, boolean notReg )
        throws JJWManagerException
    {
        try
        {
            return NewaFighterMapper.mapFighterListFromDB( newaFighterDao.getFighterByRegion( regionId, notReg ), local );
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

    public List<NewaFighterWeb> getFighterByCountry( long countryId, Locale local, boolean notReg )
        throws JJWManagerException
    {
        try
        {
            return NewaFighterMapper.mapFighterListFromDB( newaFighterDao.getFighterByCountry( countryId, notReg ),
                                                           local );
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

    public List<NewaFighterWeb> getFighterByTeam( long teamId, Locale local, boolean notReg )
        throws JJWManagerException
    {
        try
        {
            return NewaFighterMapper.mapFighterListFromDB( newaFighterDao.getFighterByTeam( teamId, notReg ), local );
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

    public List<NewaFighterWeb> getFighterByNewaclass( long newaclassId, Locale local, boolean notReg )
        throws JJWManagerException
    {
        try
        {
            return NewaFighterMapper.mapFighterListFromDB( newaFighterDao.getFighterByNewaclass( newaclassId, notReg ),
                                                       local );
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

    public void saveFighterList( List<NewaFighterWeb> newaFighterList )
        throws OptimisticLockingException, JJWManagerException, JJWPoolBlockedException,
        NewaFighterIllegalReadyException
    {
        NewaFighter newaFighterDB;
        for ( NewaFighterWeb item : newaFighterList )
        {
            if ( item.isCheck() ) // only items which are marked should be saved
            {
                newaFighterDB = new NewaFighter();
                item.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                item.setModificationId( 1l );
                NewaFighterMapper.mapFighterToDB( item, newaFighterDB, true );

                newaFighterDB.setCreateDate( new Timestamp( System.currentTimeMillis() ) );
                newaFighterDB.setCreateId( 1l );
                saveFighter( newaFighterDB );
            }
        }
    }

    public void saveFighter( NewaFighter fighter )
        throws OptimisticLockingException, JJWManagerException, JJWPoolBlockedException,
        NewaFighterIllegalReadyException
    {
        try
        {

            Newaclass newaclass =
                newaWeightclassDao.getWeightclassByAgeSexWeight( fighter.getAge().getId(), fighter.getSex(),
                                                             fighter.getWeight() );

            if ( newaclass != null )
            {
                if ( null != newaclass.getParentId() && !TypeUtil.isEmptyOrDefault( newaclass.getParentId() ) )
                {
                    fighter.setOriginalNewaclassId( newaclass.getId() );
                    newaclass = newaclass.getParent();
                }
                fighter.setNewaclassId( newaclass.getId() );
                fighter.setNewaclass( newaclass );
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
                if ( IValueConstants.STRING_1.equals( fighter.getReady() ) && null != newaclass
                    && newaclass.isDeleteStop() )
                {
                    throw new JJWPoolBlockedException();
                }
                newaFighterDao.saveFighter( fighter );
            }
            else
            { // test if new pool is blocked
                if ( newaclass != null )
                {
                    NewaFighter dbFighter = newaFighterDao.getFighter( fighter.getId() );

                    if // when fighter is ready && dbfighter has fightingclass &&( old or new fighting
                       // isdeletestop=true)
                    ( IValueConstants.STRING_1.equals( fighter.getReady() )
                        && dbFighter.getNewaclassId() > TypeUtil.LONG_DEFAULT
                        && ( ( dbFighter.getNewaclass().isDeleteStop() && IValueConstants.STRING_1.equals( dbFighter.getReady() ) ) || newaclass.isDeleteStop() ) )
                    {
                        if ( !fighter.equalsIgnoreFighterName( dbFighter ) )
                            throw new JJWPoolBlockedException();
                    }

                    // when old fightingclass not exists then check new pool
                    if ( IValueConstants.STRING_1.equals( fighter.getReady() )
                        && dbFighter.getNewaclassId() == TypeUtil.LONG_EMPTY && newaclass.isDeleteStop() )
                    {
                        if ( !fighter.equalsIgnoreFighterName( dbFighter ) )
                            throw new JJWPoolBlockedException();
                    }

                    // fighter is not ready and dbfightingclass exists and dbfighter is ready and dbfightingclass is
                    // deleteStop=true
                    if ( ( IValueConstants.STRING_0.equals( fighter.getReady() )
                        && dbFighter.getNewaclassId() > TypeUtil.LONG_DEFAULT
                        && IValueConstants.STRING_1.equals( dbFighter.getReady() ) && dbFighter.getNewaclass().isDeleteStop() ) )
                    {
                        if ( !fighter.equalsIgnoreFighterName( dbFighter ) )
                            throw new JJWPoolBlockedException();
                    }
                }
                fighter.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                optimisticDao.testLocking( TABLE_NEWA_FIGHTER, fighter.getId(), fighter.getModificationDate() );
                NewaFighterMapper.mapFighterToDB( fighter, newaFighterDao.getFighter( fighter.getId() ), false );
            }
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }

    }

    public void registerFast( long fighterId, ServiceExchangeContext ctx )
        throws JJWManagerException, JJWPoolBlockedException
    {
        try
        {
            NewaFighter fighter = newaFighterDao.getFighter( fighterId );

            try
            {
                fighter.getNewaclass().isDeleteStop();
            }
            catch ( Exception e )
            {
                log.error( e.getMessage() );
                throw new JJWManagerException( e );
            }

            if ( fighter.getNewaclass().isDeleteStop() )
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
        throws JJWManagerException, NewaFighterInUseException
    {
        try
        {
            if ( !newaFighterDao.isFighterInUse( fighterId ) )
            {
                newaFighterDao.removeFighter( fighterId );
            }
            else
            {
                throw new NewaFighterInUseException();
            }
            // dao.removeFighter(fighter);
        }
        catch ( NewaFighterInUseException e )
        {
            throw e;
        }
        catch ( Exception e )
        {
            log.error( e.getMessage() );
            throw new JJWManagerException( e );
        }
    }

    public void removeFighter( NewaFighter fighter )
        throws JJWManagerException, NewaFighterInUseException
    {
        try
        {
            if ( !newaFighterDao.isFighterInUse( fighter.getId() ) )
            {
                newaFighterDao.removeFighter( fighter );
            }
            else
            {
                throw new NewaFighterInUseException();
            }
            // dao.removeFighter(fighter);
        }
        catch ( NewaFighterInUseException e )
        {
            throw e;
        }
        catch ( Exception e )
        {
            log.error( e.getMessage() );
            throw new JJWManagerException( e );
        }
    }

    public List<NewaFighterWeb> getAllNotRegistratedFighters( Locale local )
    {
        return NewaFighterMapper.mapFighterListFromDB( newaFighterDao.getNotRegistratedFighters(), local );
    }

    public List<NewaFighterWeb> getAllRegistratedFighters( Locale local )
        throws JJWManagerException
    {
        try
        {
            return NewaFighterMapper.mapFighterListFromDB( newaFighterDao.getRegistratedFighters(), local );
        }
        catch ( Exception e )
        {
            log.error( e.getMessage() );
            throw new JJWManagerException( e );
        }
    }

    public boolean existFighter( NewaFighter fighter )
    {
        return newaFighterDao.existFighter( fighter );
    }

    public List<NewaFighterWeb> getPossibleDublicateFighters( NewaFighter fighter, Locale local )
    {
        return NewaFighterMapper.mapFighterListFromDB( newaFighterDao.getPossibleDublicateFighters( fighter ), local );
    }

    public List<NewaFighterWeb> getFighterResultList( Locale local )
        throws JJWManagerException
    {
        try
        {
            return NewaFighterMapper.mapFighterListFromDB( newaFighterDao.getFighterResultList(), local );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }
    
    public void toggleMarkForExport( NewaFighter fighter, ServiceExchangeContext ctx )
                    throws JJWManagerException, OptimisticLockingException
    {
        try
        {
            NewaFighter fighterDB = newaFighterDao.getFighter( fighter.getId() );
            optimisticDao.testLocking( TABLE_NEWA_FIGHTER, fighter.getId(), fighter.getModificationDate() );

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
    
    public List<NewaFighterWeb> getFightersForJSONExport( Locale local )
                    throws JJWManagerException
    {
        try
        {
            return NewaFighterMapper.mapFighterListFromDB( newaFighterDao.getFightersForJSONExport(), local );
        }
        catch ( Exception e )
        {
            log.error( e.getMessage() );
            throw new JJWManagerException( e );
        }
    }

    public NewaWeightclassDao getNewaWeightclassDao()
    {
        return newaWeightclassDao;
    }


    public void setNewaWeightclassDao( NewaWeightclassDao weightclassDao )
    {
        this.newaWeightclassDao = weightclassDao;
    }


}
