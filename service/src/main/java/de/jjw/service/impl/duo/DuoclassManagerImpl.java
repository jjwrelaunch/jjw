/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoclassManagerImpl.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:46
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.time.FastDateFormat;

import de.jjw.dao.IDatabaseTableConstants;
import de.jjw.dao.admin.ConfigDao;
import de.jjw.dao.admin.FightsystemDao;
import de.jjw.dao.duo.DuoFightDao;
import de.jjw.dao.duo.DuoTeamDao;
import de.jjw.dao.duo.DuoclassDao;
import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.ITechnicalDBFields;
import de.jjw.model.admin.Age;
import de.jjw.model.admin.Fightsystem;
import de.jjw.model.duo.DuoCertification;
import de.jjw.model.duo.DuoDoublePoolClass;
import de.jjw.model.duo.DuoDoublePoolItem;
import de.jjw.model.duo.DuoFight;
import de.jjw.model.duo.DuoKoClass;
import de.jjw.model.duo.DuoSimplePoolClass;
import de.jjw.model.duo.DuoSimplePoolItem;
import de.jjw.model.duo.DuoTeam;
import de.jjw.model.duo.Duoclass;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.duo.DuoclassManager;
import de.jjw.service.exception.DuoclassChildParentDeleteException;
import de.jjw.service.exception.DuoclassCreateException;
import de.jjw.service.exception.DuoclassInUseException;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.impl.BaseManager;
import de.jjw.service.mapper.duo.DuoKoMapper;
import de.jjw.service.mapper.duo.DuoclassMapper;
import de.jjw.service.modelWeb.DuoclassWeb;
import de.jjw.service.util.helper.DuoclassHelper;
import de.jjw.service.util.helper.FightsystemHelper;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;
import de.jjw.util.dto.SortDTO;


public class DuoclassManagerImpl
    extends BaseManager
    implements DuoclassManager
{

    private DuoclassDao duoclassDao;

    private DuoTeamDao duoTeamDao;

    private DuoFightDao duoFightDao;

    private FightsystemDao fightsystemDao;

    private ConfigDao configDao;

    public List<DuoclassWeb> getAllDuoclasses( Locale locale )
        throws JJWManagerException
    {
        try
        {
            return DuoclassMapper.mapDuoclassListFromDB( duoclassDao.getAllDuoclasses(), locale );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public DuoclassWeb getDuoclass( long duoclassId )
        throws JJWManagerException
    {
        try
        {
            return DuoclassMapper.mapDuoclassFromDB( duoclassDao.getDuoclass( duoclassId ) );
        }
        catch ( JJWDataLayerException e )
        {
            log.error( "getDuoclass", e );
            throw new JJWManagerException( e );
        }
    }

    public List<DuoclassWeb> getDuoclassByAge( Age age, Locale locale )
    {
        return DuoclassMapper.mapDuoclassListFromDB( duoclassDao.getDuoclassByAge( age ), locale );
    }

    public void saveDuoclass( Duoclass duoclass )
        throws OptimisticLockingException, JJWManagerException
    {
        try
        {
            if ( null == duoclass.getId() || duoclass.getId().longValue() == TypeUtil.LONG_DEFAULT )
            {
                duoclass.setId( null );
                duoclassDao.saveDuoclass( duoclass );
            }
            else
            {
                duoclass.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                optimisticDao.testLocking( TABLE_DUOCLASS, duoclass.getId(), duoclass.getModificationDate() );
                DuoclassMapper.mapDuoclassToDB( duoclass, duoclassDao.getDuoclass( duoclass.getId() ) );
            }
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public void createDuoclasses( ServiceExchangeContext ctx )
        throws DuoclassCreateException
    {

        /*
        * 1. get all Weightclasses with registrated Fighter
        * 2. for every weightclass
        * 2a. read registrated fighter for this weightclass and get the draw list
        * 2b. get fightsystem for fighter
        * 2d. create right Fighclass-entries and Fights
        *
        * if there is an exception: new Exception type and throw
        */

        try
        {
            // 1.
            duoclassDao.removeUnusedDuoclasses();
            // classes with at least one fighter
            List<Duoclass> usedDuoclasses = duoTeamDao.getUsedDuoclasses();
            List<Fightsystem> fightsystems = fightsystemDao.getAllFightsystems();
            Fightsystem fightsystem = null;
            List<Duoclass> duoclasses = duoclassDao.getDeleteStoppedDuoclasses();

            if ( duoclasses == null )
            {
                duoclasses = new ArrayList<Duoclass>( 1 );
            }

            Map<Long, Duoclass> duoclassesMap = DuoclassHelper.getDuoclassesMap( duoclasses );
            Duoclass duoclass = null;
            // 2.
            for ( Duoclass usesDuoclassItem : usedDuoclasses )
            {
                // check if duoclass exists
                if ( duoclassesMap.get( usesDuoclassItem.getId() ) != null )
                {
                    continue;
                }

                // 2a.
                List<DuoTeam> duoteams = duoTeamDao.getDuoTeamsByDuoclassForDraw( usesDuoclassItem );
                int fighterCount = duoteams.size();
                // 2b.
                fightsystem = FightsystemHelper.getFightsystem( fightsystems, fighterCount );

                // 2c. draw fighter
                // fighters = drawFighter(fighters,fighterCount);a

                if ( null != fightsystem )
                {

                    switch ( fightsystem.getFightsystemType() )
                    {

                        case Fightsystem.SIMPLE_POOL:
                            //
                            duoclass = doSimplePool( duoteams, usesDuoclassItem, ctx );
                            duoclassDao.saveDuoclassAfterCreate( duoclass );
                            break;

                        case Fightsystem.DOUBLE_POOL:
                            duoclass = doDoublePool( duoteams, usesDuoclassItem, ctx );
                            duoclassDao.saveDuoclassAfterCreate( duoclass );
                            break;

                        case Fightsystem.DOUBLE_KO:
                            duoclass = doKo( duoteams, usesDuoclassItem, ctx );
                            duoclassDao.saveDuoclassAfterCreate( duoclass );
                            break;
                    }

                }

            }
        }
        catch ( Exception e )
        {
            throw new DuoclassCreateException( e );
        }

    }

    /**
     * @param duoTeams
     * @param weightclass
     * @return
     */
    private Duoclass doSimplePool( List<DuoTeam> duoTeams, Duoclass duoclass, ServiceExchangeContext ctx )
        throws Exception
    {

        DuoclassPoolCreateImpl impl = new DuoclassPoolCreateImpl();
        return impl.doSimplePool( duoTeams, duoclass, ctx, configDao.getConfig().getFightRevenge() );
    }

    /**
     * @param duoteams
     * @param weightclass
     * @return
     */
    private Duoclass doKo( List<DuoTeam> duoteams, Duoclass duoclass, ServiceExchangeContext ctx )
    {
        DuoclassKoCreateImpl impl = new DuoclassKoCreateImpl();
        return impl.doKo( duoteams, duoclass, ctx );
    }

    public List<DuoclassWeb> getInUseDuoclasses( Map<Integer, SortDTO> sortParameter, Locale locale )
        throws JJWManagerException
    {

        try
        {
            List<DuoclassWeb> retList =
                DuoclassMapper.mapDuoclassListFromDB( duoclassDao.getInUseDuoclasses( sortParameter ), locale );

            long current = System.currentTimeMillis();
            Map<Long, Long> timesMap = duoclassDao.getTimesOfCurrentTatamiUse();
            for ( DuoclassWeb item : retList )
            {
                if ( timesMap.get( item.getId() ) != null )
                    item.setEstimateBeginTimeWeb( FastDateFormat.getInstance( "HH:mm" ).format( current
                                                                                                    + 1000
                                                                                                    * timesMap.get( item.getId() ) ) );
            }

            return retList;
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    /**
     * get all Pools and KOs of all Classes
     * 
     * @param complete only completed classes
     * @param locale
     * @return
     * @throws JJWManagerException
     */
    public Map<Integer, Duoclass> getAllDuoclasses( boolean onlyCompleted )
        throws JJWManagerException
    {

        try
        {
            List<Duoclass> fc = duoclassDao.getInUseDuoclasses( null );
            Map<Integer, Duoclass> retMap = new HashMap<Integer, Duoclass>( fc.size() );
            int counter = 0;
            for ( Duoclass item : fc )
            {
                if ( onlyCompleted && !item.isComplete() )
                    continue;

                if ( item.getFightsystem() == Fightsystem.SIMPLE_POOL )

                {
                    counter++;
                    retMap.put( counter, getDuoclassSimplePool( item.getId() ) );
                }

                if ( item.getFightsystem() == Fightsystem.DOUBLE_POOL )

                {
                    counter++;
                    retMap.put( counter, getDuoclassDoublePool( item.getId() ) );
                }

                if ( item.getFightsystem() == Fightsystem.DOUBLE_KO )
                {
                    counter++;
                    retMap.put( counter, getDuoclassKo( item.getId() ) );

                }
            }
            return retMap;
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public DuoSimplePoolClass getDuoclassSimplePool( Long duoclassId )
        throws JJWManagerException
    {
        DuoclassPoolsImpl pool = new DuoclassPoolsImpl();
        try
        {
            return pool.getDuoclassSimplePool( duoclassId, getDuoclassDao(), configDao.getConfig().getFightRevenge() );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    /**
     * @param duoclassId
     * @return
     * @throws JJWManagerException
     */
    public DuoDoublePoolClass getDuoclassDoublePool( Long duoclassId )
        throws JJWManagerException
    {
        DuoclassPoolsImpl dpool = new DuoclassPoolsImpl();
        return dpool.getDuoclassDoublePool( duoclassId, getDuoclassDao(), getDuoFightDao(), true );
    }

    public DuoKoClass getDuoclassKo( Long duoclassId )
        throws JJWManagerException
    {

        try
        {

            DuoKoClass dc = duoclassDao.getDuoclassKo( duoclassId );
            // add not persisted fights only for calculating
            dc.setId( duoclassId );
            if ( dc.getTeamListPoolA().size() < 9 )
            {
                // KO 16
                for ( int i = dc.getFightListLooserMapPoolA().size(); i < 7; i++ )
                {
                    if ( dc.getFightListLooserMapPoolA().get( Integer.valueOf( i ) ) == null )
                    {
                        dc.putFightListLoserMapPoolA( Integer.valueOf( i ), new DuoFight() );
                    }
                }

                for ( int i = dc.getFightListLooserMapPoolB().size(); i < 7; i++ )
                {
                    if ( dc.getFightListLooserMapPoolB().get( Integer.valueOf( i ) ) == null )
                    {
                        dc.putFightListLoserMapPoolB( Integer.valueOf( i ), new DuoFight() );
                    }
                }
            }

            if ( dc.getTeamListPoolA().size() > 8 && dc.getTeamListPoolA().size() < 17 )
            {
                // KO 32
                for ( int i = dc.getFightListLooserMapPoolA().size(); i < 14; i++ )
                {
                    if ( dc.getFightListLooserMapPoolA().get( Integer.valueOf( i ) ) == null )
                    {
                        dc.putFightListLoserMapPoolA( Integer.valueOf( i ), new DuoFight() );
                    }
                }

                for ( int i = dc.getFightListLooserMapPoolB().size(); i < 14; i++ )
                {
                    if ( dc.getFightListLooserMapPoolB().get( Integer.valueOf( i ) ) == null )
                    {
                        dc.putFightListLoserMapPoolB( Integer.valueOf( i ), new DuoFight() );
                    }
                }
            }

            if ( dc.getTeamListPoolA().size() > 16 && dc.getTeamListPoolA().size() < 33 )
            {
                // KO 64
                for ( int i = dc.getFightListLooserMapPoolA().size(); i < 30; i++ )
                {
                    if ( dc.getFightListLooserMapPoolA().get( Integer.valueOf( i ) ) == null )
                    {
                        dc.putFightListLoserMapPoolA( Integer.valueOf( i ), new DuoFight() );
                    }
                }

                for ( int i = dc.getFightListLooserMapPoolB().size(); i < 30; i++ )
                {
                    if ( dc.getFightListLooserMapPoolB().get( Integer.valueOf( i ) ) == null )
                    {
                        dc.putFightListLoserMapPoolB( Integer.valueOf( i ), new DuoFight() );
                    }
                }
            }

            DuoclassKoImpl impl = new DuoclassKoImpl();
            dc = impl.doKo( dc, duoFightDao );

            return DuoKoMapper.mapDuoKoClassFromDB( dc );

        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    /**
     * toggles the status of completed duoclass and sets or resets the place in the fighter
     */
    public void toggleComplete( Duoclass duoclass, ServiceExchangeContext ctx )
        throws JJWManagerException, OptimisticLockingException
    {
        try
        {
            Duoclass duoclassDB = duoclassDao.getDuoclass( duoclass.getId() );
            setTechnicalDBFieldsForUpdate( duoclass, ctx );
            optimisticDao.testLocking( TABLE_DUOCLASS, duoclass.getId(), duoclass.getModificationDate() );

            if ( duoclass.isComplete() )
            {
                // reset places
                resetResults( duoclass.getId() );
                duoclass.setComplete( false );
                duoclass.setCertificationPrint( false );
                configDao.getConfig().setDumpSend( new Timestamp( IValueConstants.YEAR_2000_IN_MS ) );
            }
            else
            {
                // set places
                duoclass.setComplete( true );
                if ( duoclass.getFightsystem() == Fightsystem.SIMPLE_POOL )
                {
                    doResultForSimplePool( duoclass.getId() );
                }
                if ( duoclass.getFightsystem() == Fightsystem.DOUBLE_POOL )
                {
                    doResultForDoublePool( duoclass.getId() );
                }
                if ( duoclass.getFightsystem() == Fightsystem.DOUBLE_KO )
                {
                    doResultForDoubleKO( duoclass.getId() );
                }
            }
            DuoclassMapper.mapDuoclassToDB( duoclass, duoclassDB );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    private void doResultForSimplePool( Long duoclassId )
        throws JJWManagerException, JJWDataLayerException
    {
        Map<Long, Integer> resultMap = new HashMap<Long, Integer>( 5 );

        DuoSimplePoolClass fc = getDuoclassSimplePool( duoclassId );
        for ( DuoSimplePoolItem item : fc.getDuoTeamList() )
        {

            resultMap.put( item.getDuoTeam().getId(), item.getPlace() );
        }
        duoclassDao.setDuoclassResults( resultMap, duoclassId );
    }

    private void doResultForDoublePool( Long duoclassId )
        throws JJWManagerException, JJWDataLayerException
    {
        Map<Long, Integer> resultMap = new HashMap<Long, Integer>( 10 );

        DuoDoublePoolClass fc = getDuoclassDoublePool( duoclassId );

        for ( DuoDoublePoolItem item : fc.getDuoListPoolA() )
        {

            resultMap.put( item.getDuoTeam().getId(), item.getPlace() );
        }

        for ( DuoDoublePoolItem item : fc.getDuoListPoolB() )
        {

            resultMap.put( item.getDuoTeam().getId(), item.getPlace() );
        }
        
        resultMap.put( fc.getHalfFinalFight1().getTeamIdRed(), fc.getHalfFinalFight1().getDuoTeamRed().getPlace() );
        resultMap.put( fc.getHalfFinalFight1().getTeamIdBlue(), fc.getHalfFinalFight1().getDuoTeamBlue().getPlace() );
        
        resultMap.put( fc.getHalfFinalFight2().getTeamIdRed(), fc.getHalfFinalFight2().getDuoTeamRed().getPlace() );
        resultMap.put( fc.getHalfFinalFight2().getTeamIdBlue(), fc.getHalfFinalFight2().getDuoTeamBlue().getPlace() );
        
        resultMap.put( fc.getFinalFight().getTeamIdRed(), fc.getFinalFight().getDuoTeamRed().getPlace() );
        resultMap.put( fc.getFinalFight().getTeamIdBlue(), fc.getFinalFight().getDuoTeamBlue().getPlace() );

        
//        if ( fc.getFinalFight().getWinnerId().longValue() >= TypeUtil.LONG_DEFAULT )
//        {
//            if ( fc.getFinalFight().getWinnerId().longValue() == TypeUtil.LONG_DEFAULT )
//            {
//                resultMap.put( fc.getFinalFight().getTeamIdRed(), 2 );
//                resultMap.put( fc.getFinalFight().getTeamIdBlue(), 2 );
//            }
//
//            if ( fc.getFinalFight().getWinnerId().longValue() == fc.getFinalFight().getTeamIdRed() )
//            {
//                resultMap.put( fc.getFinalFight().getTeamIdRed(), 1 );
//                resultMap.put( fc.getFinalFight().getTeamIdBlue(), 2 );
//            }
//
//            if ( fc.getFinalFight().getWinnerId().longValue() == fc.getFinalFight().getTeamIdBlue() )
//            {
//                resultMap.put( fc.getFinalFight().getTeamIdRed(), 2 );
//                resultMap.put( fc.getFinalFight().getTeamIdBlue(), 1 );
//            }
//        }

        duoclassDao.setDuoclassResults( resultMap, duoclassId );
    }

    private void doResultForDoubleKO( Long duoclassId )
        throws JJWManagerException, JJWDataLayerException
    {
        Map<Long, Integer> topList = new HashMap<Long, Integer>( 5 );
        DuoKoClass fc = getDuoclassKo( duoclassId );

        if ( fc.getFinalFight().getWinnerId().longValue() == fc.getFinalFight().getTeamIdRed() )
        {
            if ( !topList.containsKey( Long.valueOf( fc.getFinalFight().getTeamIdBlue() ) ) )
            {
                topList.put( Long.valueOf( fc.getFinalFight().getTeamIdBlue() ), TypeUtil.INTEGER_2 );
            }
            if ( !topList.containsKey( Long.valueOf( fc.getFinalFight().getTeamIdRed() ) ) )
            {
                topList.put( Long.valueOf( fc.getFinalFight().getTeamIdRed() ), TypeUtil.INTEGER_1 );
            }
        }
        if ( fc.getFinalFight().getWinnerId().longValue() == fc.getFinalFight().getTeamIdBlue() )
        {
            if ( !topList.containsKey( Long.valueOf( fc.getFinalFight().getTeamIdBlue() ) ) )
            {
                topList.put( Long.valueOf( fc.getFinalFight().getTeamIdBlue() ), TypeUtil.INTEGER_1 );
            }
            if ( !topList.containsKey( Long.valueOf( fc.getFinalFight().getTeamIdRed() ) ) )
            {
                topList.put( Long.valueOf( fc.getFinalFight().getTeamIdRed() ), TypeUtil.INTEGER_2 );
            }
        }
        if ( fc.getFinalFight().getWinnerId() == TypeUtil.LONG_DEFAULT && fc.getFinalFight().getPointsRed() == 0
            && fc.getFinalFight().getPointsBlue() == 0 )
        { // double DQ

            if ( !topList.containsKey( Long.valueOf( fc.getFinalFight().getTeamIdBlue() ) ) )
            {
                topList.put( Long.valueOf( fc.getFinalFight().getTeamIdBlue() ), TypeUtil.INTEGER_2 );
            }
            if ( !topList.containsKey( Long.valueOf( fc.getFinalFight().getTeamIdRed() ) ) )
            {
                topList.put( Long.valueOf( fc.getFinalFight().getTeamIdRed() ), TypeUtil.INTEGER_2 );
            }
        }

        // 3rd
        doLoserRoundResult( fc.getFightListLooserMapPoolA().get( TypeUtil.INTEGER_0 ), TypeUtil.INTEGER_3,
                            TypeUtil.INTEGER_5, topList );
        doLoserRoundResult( fc.getFightListLooserMapPoolB().get( TypeUtil.INTEGER_0 ), TypeUtil.INTEGER_3,
                            TypeUtil.INTEGER_5, topList );

        // 5th
        doLoserRoundResult( fc.getFightListLooserMapPoolA().get( TypeUtil.INTEGER_1 ), TypeUtil.INTEGER_5,
                            TypeUtil.INTEGER_7, topList );
        doLoserRoundResult( fc.getFightListLooserMapPoolB().get( TypeUtil.INTEGER_1 ), TypeUtil.INTEGER_5,
                            TypeUtil.INTEGER_7, topList );
        // 7th
        doLoserRoundResult( fc.getFightListLooserMapPoolA().get( TypeUtil.INTEGER_2 ), TypeUtil.INTEGER_7,
                            TypeUtil.INTEGER_9, topList );
        doLoserRoundResult( fc.getFightListLooserMapPoolB().get( TypeUtil.INTEGER_2 ), TypeUtil.INTEGER_7,
                            TypeUtil.INTEGER_9, topList );
        doLoserRoundResult( fc.getFightListLooserMapPoolA().get( TypeUtil.INTEGER_3 ), TypeUtil.INTEGER_7,
                            TypeUtil.INTEGER_9, topList );
        doLoserRoundResult( fc.getFightListLooserMapPoolB().get( TypeUtil.INTEGER_3 ), TypeUtil.INTEGER_7,
                            TypeUtil.INTEGER_9, topList );
        // 9th
        doLoserRoundResult( fc.getFightListLooserMapPoolA().get( TypeUtil.INTEGER_4 ), TypeUtil.INTEGER_9,
                            TypeUtil.INTEGER_13, topList );
        doLoserRoundResult( fc.getFightListLooserMapPoolB().get( TypeUtil.INTEGER_4 ), TypeUtil.INTEGER_9,
                            TypeUtil.INTEGER_13, topList );
        doLoserRoundResult( fc.getFightListLooserMapPoolA().get( TypeUtil.INTEGER_5 ), TypeUtil.INTEGER_9,
                            TypeUtil.INTEGER_13, topList );
        doLoserRoundResult( fc.getFightListLooserMapPoolB().get( TypeUtil.INTEGER_5 ), TypeUtil.INTEGER_9,
                            TypeUtil.INTEGER_13, topList );

        // 13th
        doLoserRoundResult( fc.getFightListLooserMapPoolA().get( TypeUtil.INTEGER_6 ), TypeUtil.INTEGER_13,
                            TypeUtil.INTEGER_17, topList );
        doLoserRoundResult( fc.getFightListLooserMapPoolB().get( TypeUtil.INTEGER_6 ), TypeUtil.INTEGER_13,
                            TypeUtil.INTEGER_17, topList );
        doLoserRoundResult( fc.getFightListLooserMapPoolA().get( TypeUtil.INTEGER_7 ), TypeUtil.INTEGER_13,
                            TypeUtil.INTEGER_17, topList );
        doLoserRoundResult( fc.getFightListLooserMapPoolB().get( TypeUtil.INTEGER_7 ), TypeUtil.INTEGER_13,
                            TypeUtil.INTEGER_17, topList );
        doLoserRoundResult( fc.getFightListLooserMapPoolA().get( TypeUtil.INTEGER_8 ), TypeUtil.INTEGER_13,
                            TypeUtil.INTEGER_17, topList );
        doLoserRoundResult( fc.getFightListLooserMapPoolB().get( TypeUtil.INTEGER_8 ), TypeUtil.INTEGER_13,
                            TypeUtil.INTEGER_17, topList );
        doLoserRoundResult( fc.getFightListLooserMapPoolA().get( TypeUtil.INTEGER_9 ), TypeUtil.INTEGER_13,
                            TypeUtil.INTEGER_17, topList );
        doLoserRoundResult( fc.getFightListLooserMapPoolB().get( TypeUtil.INTEGER_9 ), TypeUtil.INTEGER_13,
                            TypeUtil.INTEGER_17, topList );
        // 17th
        doLoserRoundResult( fc.getFightListLooserMapPoolA().get( TypeUtil.INTEGER_10 ), TypeUtil.INTEGER_17,
                            TypeUtil.INTEGER_25, topList );
        doLoserRoundResult( fc.getFightListLooserMapPoolB().get( TypeUtil.INTEGER_10 ), TypeUtil.INTEGER_17,
                            TypeUtil.INTEGER_25, topList );
        doLoserRoundResult( fc.getFightListLooserMapPoolA().get( TypeUtil.INTEGER_11 ), TypeUtil.INTEGER_17,
                            TypeUtil.INTEGER_25, topList );
        doLoserRoundResult( fc.getFightListLooserMapPoolB().get( TypeUtil.INTEGER_11 ), TypeUtil.INTEGER_17,
                            TypeUtil.INTEGER_25, topList );
        doLoserRoundResult( fc.getFightListLooserMapPoolA().get( TypeUtil.INTEGER_12 ), TypeUtil.INTEGER_17,
                            TypeUtil.INTEGER_25, topList );
        doLoserRoundResult( fc.getFightListLooserMapPoolB().get( TypeUtil.INTEGER_12 ), TypeUtil.INTEGER_17,
                            TypeUtil.INTEGER_25, topList );
        doLoserRoundResult( fc.getFightListLooserMapPoolA().get( TypeUtil.INTEGER_13 ), TypeUtil.INTEGER_17,
                            TypeUtil.INTEGER_25, topList );
        doLoserRoundResult( fc.getFightListLooserMapPoolB().get( TypeUtil.INTEGER_13 ), TypeUtil.INTEGER_17,
                            TypeUtil.INTEGER_25, topList );

        duoclassDao.setDuoclassResults( topList, duoclassId );
    }

    private void doLoserRoundResult( DuoFight fight, Integer place, Integer dqPlace, Map<Long, Integer> topList )
    {
        if ( fight == null )
        {
            return;
        }
        if ( fight.getWinnerId() == TypeUtil.LONG_DEFAULT && fight.getPointsRed() == 0 && fight.getPointsBlue() == 0 )
        { // double DQ

            if ( !topList.containsKey( Long.valueOf( fight.getTeamIdBlue() ) ) )
            {
                topList.put( Long.valueOf( fight.getTeamIdBlue() ), dqPlace );
            }
            if ( !topList.containsKey( Long.valueOf( fight.getTeamIdRed() ) ) )
            {
                topList.put( Long.valueOf( fight.getTeamIdRed() ), dqPlace );
            }
        }
        // if(fight.getWinner () == -1 && fight.getIdWhite() == 0) setResult (fight.getIdRed (), place,topList);
        // if(fight.getWinner () == -1 && fight.getIdRed() == 0) setResult (fight.getIdWhite (), place,topList);

        if ( fight.getWinnerId() == fight.getTeamIdBlue() )
        {
            if ( !topList.containsKey( Long.valueOf( fight.getTeamIdBlue() ) ) )
            {
                topList.put( Long.valueOf( fight.getTeamIdBlue() ), place );
            }
            if ( !topList.containsKey( Long.valueOf( fight.getTeamIdRed() ) ) )
            {
                topList.put( Long.valueOf( fight.getTeamIdRed() ), dqPlace );
            }
        }
        if ( fight.getWinnerId() == fight.getTeamIdRed() )
        {
            if ( !topList.containsKey( Long.valueOf( fight.getTeamIdBlue() ) ) )
            {
                topList.put( Long.valueOf( fight.getTeamIdBlue() ), dqPlace );
            }
            if ( !topList.containsKey( Long.valueOf( fight.getTeamIdRed() ) ) )
            {
                topList.put( Long.valueOf( fight.getTeamIdRed() ), place );
            }
        }
    }

    private void resetResults( Long duoclassId )
        throws JJWDataLayerException
    {
        duoclassDao.resetDuoclassResults( duoclassId );
    }

    public void toggleDeleteStop( Duoclass duoclass, ServiceExchangeContext ctx )
        throws JJWManagerException, OptimisticLockingException
    {
        try
        {
            Duoclass duoclassDB = duoclassDao.getDuoclass( duoclass.getId() );
            setTechnicalDBFieldsForUpdate( duoclass, ctx );
            optimisticDao.testLocking( TABLE_DUOCLASS, duoclass.getId(), duoclass.getModificationDate() );
            if ( duoclass.isDeleteStop() )
            {
                duoclass.setDeleteStop( false );
            }
            else
            {
                duoclass.setDeleteStop( true );
            }
            DuoclassMapper.mapDuoclassToDB( duoclass, duoclassDB );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    /**
     * get the type of the fightsystem for this duoclassId
     * 
     * @param duoclassId
     * @return
     * @throws JJWManagerException
     */
    public int getFightsystemOfDuoclass( long duoclassId )
        throws JJWManagerException
    {
        try
        {
            return getDuoclassDao().getFightsystemOfDuoclass( duoclassId );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public DuoCertification getDuoCertification( Long duoclassId )
        throws JJWManagerException
    {
        try
        {
            return getDuoclassDao().getDuoCertification( duoclassId );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    private Duoclass doDoublePool( List<DuoTeam> duoTeams, Duoclass duoclass, ServiceExchangeContext ctx )
    {
        DuoclassPoolCreateImpl impl = new DuoclassPoolCreateImpl();
        return impl.doDoublePool( duoTeams, duoclass, ctx );
    }

    /**
     * set technical DB Fields
     * 
     * @param obj
     */
    protected void setTechnicalDBFieldsForCreateBySystemId( ITechnicalDBFields obj )
    {
        obj.setCreateDate( new Timestamp( System.currentTimeMillis() ) );
        obj.setCreateId( IDatabaseTableConstants.SYSTEM );
        obj.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
        obj.setModificationId( IDatabaseTableConstants.SYSTEM );
    }

    /**
     * set technical DB Fields
     * 
     * @param obj
     */
    protected void setTechnicalDBFieldsForCreate( ITechnicalDBFields obj, ServiceExchangeContext ctx )
    {
        obj.setCreateDate( new Timestamp( System.currentTimeMillis() ) );
        obj.setCreateId( ctx.getUserId() );
        obj.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
        obj.setModificationId( ctx.getUserId() );
    }

    /**
     * set technical DB Fields
     * 
     * @param obj
     */
    protected void setTechnicalDBFieldsForUpdate( ITechnicalDBFields obj, ServiceExchangeContext ctx )
    {
        obj.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
        obj.setModificationId( ctx.getUserId() );
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
    public void removeDuoclass( Duoclass duoclass )
        throws JJWManagerException, DuoclassInUseException, DuoclassChildParentDeleteException
    {
        try
        {
            if ( duoclassDao.isDuoclassInUse( duoclass ) )
            {
                throw new DuoclassInUseException();
            }
            if ( duoclassDao.isDuoclassChildOrParent( duoclass.getId() ) )
            {
                throw new DuoclassChildParentDeleteException();
            }
            duoclassDao.removeDuoclass( duoclass );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException();
        }
    }

    public void changeDuoTeamsInClass( int duoTeam1, int duoTeam2, long duoclassId )
        throws JJWManagerException
    {

        // get pool an pooltype
        // get fighters
        // change Fighters in class
        // change Fights
        try
        {
            Duoclass duoclass = duoclassDao.getDuoclass( duoclassId );
            switch ( getFightsystemOfDuoclass( duoclassId ) )
            {

                case Fightsystem.SIMPLE_POOL:
                    changeFighterInPool( duoTeam1, duoTeam2, duoclass );
                    break;

                case Fightsystem.DOUBLE_KO:
                    changeDuoTeamsInKo( duoTeam1, duoTeam2, duoclass );
                    break;

                case Fightsystem.DOUBLE_POOL:
                    changeDuoTeamsInDoublePool( duoTeam1, duoTeam2, duoclass );
                    break;
            }
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    private void changeFighterInPool( int duoTeam1, int duoTeam2, Duoclass duoclass )
        throws JJWDataLayerException
    {
        duoclassDao.changeDuoTeamsInPool( duoTeam1, duoTeam2, duoclass );
    }

    private void changeDuoTeamsInDoublePool( int duoTeam1, int duoTeam2, Duoclass duolass )
        throws JJWDataLayerException
    {
        duoclassDao.changeDuoTeamsInDPool( duoTeam1, duoTeam2, duolass );
    }

    private void changeDuoTeamsInKo( int duoTeam1, int duoTeam2, Duoclass duoclass )
        throws JJWDataLayerException
    {
        duoclassDao.changeDuoTeamsInKo( duoTeam1, duoTeam2, duoclass );
    }

    public int getNumberOfDuoTeamInDuoclass( long duoclassId )
        throws JJWManagerException
    {
        try
        {
            return duoclassDao.getNumberOfDuoTeamsInDuoclass( duoclassId );

        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }

    public void setDuoclassAsPrinted( Duoclass duoclass, ServiceExchangeContext ctx )
        throws JJWManagerException
    {
        try
        {
            Duoclass fc = duoclassDao.getDuoclass( duoclass.getId() );
            fc.setCertificationPrint( true );
            fc.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
            fc.setModificationId( ctx.getUserId() );
        }
        catch ( Exception e )
        {
            throw new JJWManagerException();
        }
    }

    public List<DuoclassWeb> getInUseDuoclassesForTatami( Map<Integer, SortDTO> sortParameter, Locale locale,
                                                          ServiceExchangeContext ctx )
        throws JJWManagerException
    {
        try
        {
            List<DuoclassWeb> retList =
                DuoclassMapper.mapDuoclassListFromDB( duoclassDao.getInUseDuoclassesForTatami( sortParameter,
                                                                                                  ctx.getUserId() ),
                                                         locale );

            long current = System.currentTimeMillis();
            Map<Long, Long> timesMap = duoclassDao.getTimesOfCurrentTatamiUse();
            for ( DuoclassWeb item : retList )
            {
                if ( timesMap.get( item.getId() ) != null )
                    item.setEstimateBeginTimeWeb( FastDateFormat.getInstance( "HH:mm" ).format( current
                                                                                                    + 1000
                                                                                                    * timesMap.get( item.getId() ) ) );
            }

            return retList;
        }
        catch ( Exception e )
        {
            throw new JJWManagerException();
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

    public DuoFightDao getDuoFightDao()
    {
        return duoFightDao;
    }

    public void setDuoFightDao( DuoFightDao duoFightDao )
    {
        this.duoFightDao = duoFightDao;
    }

    public FightsystemDao getFightsystemDao()
    {
        return fightsystemDao;
    }

    public void setFightsystemDao( FightsystemDao fightsystemDao )
    {
        this.fightsystemDao = fightsystemDao;
    }

    public void setConfigDao( ConfigDao configDao )
    {
        this.configDao = configDao;
    }

    @Override
    public List<DuoclassWeb> getChildDuoclasses( Long duoclassId, Locale locale )
        throws JJWManagerException
    {
        try
        {
            return DuoclassMapper.mapDuoclassListFromDB( duoclassDao.getChildDuoclasses( duoclassId ), locale );
        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }

    @Override
    public List<DuoclassWeb> getCombinableDuoclasses( Long duoclassId, Locale locale )
        throws JJWManagerException
    {
        try
        {
            return DuoclassMapper.mapDuoclassListFromDB( duoclassDao.getCombinableDuoclasses( duoclassId ), locale );
        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }

    @Override
    public void addDuoclassToClass( long parentClass, long childClass )
        throws JJWManagerException
    {
        try
        {
            duoclassDao.addDuoclassToClass( parentClass, childClass );

            duoclassDao.removeUnusedDuoclass( childClass );
            duoclassDao.removeUnusedDuoclass( parentClass );
        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }

    @Override
    public void removeDuoclassFromParent( long parentClass, long childClass )
        throws JJWManagerException
    {
        try
        {
            duoclassDao.removeDuoclassFromParent( parentClass, childClass );

            duoclassDao.removeUnusedDuoclass( childClass );
            duoclassDao.removeUnusedDuoclass( parentClass );
        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }

    @Override
    public void startClass( Duoclass duoclass, ServiceExchangeContext ctx )
        throws JJWManagerException, OptimisticLockingException
    {
        {
            try
            {
                Duoclass duoclassDB = duoclassDao.getDuoclass( duoclass.getId() );


                setTechnicalDBFieldsForUpdate( duoclassDB, ctx );
                duoclassDB.setStartTime( new Timestamp( System.currentTimeMillis() ) );
            }
            catch ( JJWDataLayerException e )
            {
                throw new JJWManagerException( e );
            }
        }
    }
}
