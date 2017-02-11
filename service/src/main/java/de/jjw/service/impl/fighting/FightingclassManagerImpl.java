/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightingclassManagerImpl.java
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

package de.jjw.service.impl.fighting;

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
import de.jjw.dao.fighting.FightDao;
import de.jjw.dao.fighting.FighterDao;
import de.jjw.dao.fighting.FightingclassDao;
import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.EstimatedTimes;
import de.jjw.model.ITechnicalDBFields;
import de.jjw.model.admin.Age;
import de.jjw.model.admin.Fightsystem;
import de.jjw.model.fighting.Fight;
import de.jjw.model.fighting.Fighter;
import de.jjw.model.fighting.FightingCertification;
import de.jjw.model.fighting.FightingDoublePoolClass;
import de.jjw.model.fighting.FightingDoublePoolItem;
import de.jjw.model.fighting.FightingKoClass;
import de.jjw.model.fighting.FightingSimplePoolClass;
import de.jjw.model.fighting.FightingSimplePoolItem;
import de.jjw.model.fighting.Fightingclass;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.exception.FightingclassCreateException;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.fighting.FightingclassManager;
import de.jjw.service.impl.BaseManager;
import de.jjw.service.mapper.fighting.FightingKoMapper;
import de.jjw.service.mapper.fighting.WeightclassMapper;
import de.jjw.service.modelWeb.FightingclassWeb;
import de.jjw.service.util.helper.FightingclassHelper;
import de.jjw.service.util.helper.FightsystemHelper;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;
import de.jjw.util.dto.SortDTO;



public class FightingclassManagerImpl
    extends BaseManager
    implements FightingclassManager
{

    // private WeightclassDao weightclassDao;

    private FighterDao fighterDao;

    private FightDao fightDao;

    private FightingclassDao fightingclassDao;

    private FightsystemDao fightsystemDao;

    private ConfigDao configDao;

    // public void setWeightclassDao(WeightclassDao weightclassDao) {
    // this.weightclassDao=weightclassDao;
    // }

    public void setFighterDao( FighterDao fighterDao )
    {
        this.fighterDao = fighterDao;
    }

    /**
     * @param fightingclassDao the fightingclassDao to set
     */
    public void setFightingclassDao( FightingclassDao fightingclassDao )
    {
        this.fightingclassDao = fightingclassDao;
    }

    /**
     * @param fightsystemManager the fightsystemManager to set
     */
    public void setFightsystemDao( FightsystemDao fightsystemDao )
    {
        this.fightsystemDao = fightsystemDao;
    }

    public void setConfigDao( ConfigDao configDao )
    {
        this.configDao = configDao;
    }


    public void createFightingclasses( ServiceExchangeContext ctx )
        throws FightingclassCreateException
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
            fightingclassDao.removeUnusedFightingclasses();
            // classes with at least one fighter
            List<Fightingclass> usedFightingclasses = fighterDao.getUsedWeightclasses();
            List<Fightsystem> fightsystems = fightsystemDao.getAllFightsystems();
            Fightsystem fightsystem = null;
            List<Fightingclass> fightingclasses = fightingclassDao.getDeleteStoppedFightingclasses();

            if ( fightingclasses == null )
            {
                fightingclasses = new ArrayList<Fightingclass>( 1 );
            }

            Map<Long, Fightingclass> fightingclassesMap = FightingclassHelper.getFightingclassesMap( fightingclasses );
            Fightingclass fightingclass = null;
            // 2.
            for ( Fightingclass usesFightingclassItem : usedFightingclasses )
            {
                // check if fightingclass exists
                if ( fightingclassesMap.get( usesFightingclassItem.getId() ) != null )
                {
                    continue;
                }

                // 2a.
                List<Fighter> fighters = fighterDao.getFighterByFightingclassForDraw( usesFightingclassItem );
                int fighterCount = fighters.size();
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
                            fightingclass = doSimplePool( fighters, usesFightingclassItem, ctx );
                            fightingclassDao.saveFightingclassAfterCreate( fightingclass );
                            break;

                        case Fightsystem.DOUBLE_POOL:
                            fightingclass = doDoublePool( fighters, usesFightingclassItem, ctx );
                            fightingclassDao.saveFightingclassAfterCreate( fightingclass );
                            break;

                        case Fightsystem.DOUBLE_KO:
                            fightingclass = doKo( fighters, usesFightingclassItem, ctx );
                            fightingclassDao.saveFightingclassAfterCreate( fightingclass );
                            break;
                    }
                    // saveFightingclass(fightingclass);

                }

            }
        }
        catch ( Exception e )
        {
            throw new FightingclassCreateException( e );
        }

    }

    private Fightingclass doDoublePool( List<Fighter> fighters, Fightingclass fightingclass, ServiceExchangeContext ctx )
    {
        FightingclassPoolCreateImpl impl = new FightingclassPoolCreateImpl();
        return impl.doDoublePool( fighters, fightingclass, ctx );
    }

    /**
     * @param fighters
     * @param weightclass
     * @return
     */
    private Fightingclass doSimplePool( List<Fighter> fighters, Fightingclass fightingclass, ServiceExchangeContext ctx )
        throws Exception
    {

        FightingclassPoolCreateImpl impl = new FightingclassPoolCreateImpl();
        return impl.doSimplePool( fighters, fightingclass, ctx, configDao.getConfig().getFightRevenge() );
    }

    /**
     * @param fighters
     * @param weightclass
     * @return
     */
    private Fightingclass doKo( List<Fighter> fighters, Fightingclass fightingclass, ServiceExchangeContext ctx )
    {
        FightingclassKoCreateImpl impl = new FightingclassKoCreateImpl();
        return impl.doKo( fighters, fightingclass, ctx );
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

    public List<FightingclassWeb> getInUseFightingclasses( Map<Integer, SortDTO> sortParameter, Locale locale )
        throws JJWManagerException
    {
        long startTime=0;
        try
        {
            List<FightingclassWeb> retList =
                WeightclassMapper.mapWeightclassListFromDB( fightingclassDao.getInUseFightingclasses( sortParameter ),
                                                            locale );

            long current = System.currentTimeMillis();
            List<EstimatedTimes> timesList = fightingclassDao.getTimesOfCurrentTatamiUse();
            for ( FightingclassWeb item : retList )
            {
                startTime= getStartTime( timesList, item.getId() );
                if (startTime >0)
                 item.setEstimateBeginTimeWeb( FastDateFormat.getInstance( "HH:mm" ).format( current
                                                                                                    + 1000
                                                                                                    * startTime ) );
            
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
    public Map<Integer, Fightingclass> getAllFightingclasses( boolean onlyCompleted )
        throws JJWManagerException
    {

        try
        {
            List<Fightingclass> fc = fightingclassDao.getInUseFightingclasses( null );
            Map<Integer, Fightingclass> retMap = new HashMap<Integer, Fightingclass>( fc.size() );
            int counter = 0;
            for ( Fightingclass item : fc )
            {
                if ( onlyCompleted && !item.isComplete() )
                    continue;

                if ( item.getFightsystem() == Fightsystem.SIMPLE_POOL )

                {
                    counter++;
                    retMap.put( counter, getFightingclassSimplePool( item.getId() ) );
                }

                if ( item.getFightsystem() == Fightsystem.DOUBLE_POOL )

                {
                    counter++;
                    retMap.put( counter, getFightingclassDoublePool( item.getId() ) );
                }

                if ( item.getFightsystem() == Fightsystem.DOUBLE_KO )
                {
                    counter++;
                    retMap.put( counter, getFightingclassKo( item.getId() ) );

                }
            }
            return retMap;
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public List<FightingclassWeb> getInUseFightingclassesForTatami( Map<Integer, SortDTO> sortParameter, Locale locale,
                                                                    ServiceExchangeContext ctx )
        throws JJWManagerException
    {
        long startTime=0;
        try
        {
            List<FightingclassWeb> retList =
                WeightclassMapper.mapWeightclassListFromDB(
                                                               fightingclassDao.getInUseFightingclassesForTatami(
                                                                                                                  sortParameter,
                                                                                                                  ctx.getUserId() ),
                                                               locale );
            long current = System.currentTimeMillis();
            List<EstimatedTimes> timesList = fightingclassDao.getTimesOfCurrentTatamiUse();
            for ( FightingclassWeb item : retList )
            {
                startTime= getStartTime( timesList, item.getId() );
                if (startTime >0)
                 item.setEstimateBeginTimeWeb( FastDateFormat.getInstance( "HH:mm" ).format( current
                                                                                                    + 1000
                                                                                                    * startTime ) );
            
            }
            return retList;

        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public FightingSimplePoolClass getFightingclassSimplePool( Long fightingclassId )
        throws JJWManagerException
    {
        FightingclassPoolsImpl pool = new FightingclassPoolsImpl();
        try
        {
            return pool.getFightingclassSimplePool( fightingclassId, getFightingclassDao(),
                                                    configDao.getConfig().getFightRevenge() );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    /**
     * @param fightingclassId
     * @return
     * @throws JJWManagerException
     */
    public FightingDoublePoolClass getFightingclassDoublePool( Long fightingclassId )
        throws JJWManagerException
    {
        FightingclassPoolsImpl dpool = new FightingclassPoolsImpl();
        return dpool.getFightingclassDoublePool( fightingclassId, getFightingclassDao(), getFightDao(), true );
    }

    public FightingKoClass getFightingclassKo( Long fightingclassId )
        throws JJWManagerException
    {

        try
        {

            FightingKoClass fc = fightingclassDao.getFightingclassKo( fightingclassId );

            fc.setId( fightingclassId );
            // add not persisted fights only for calculating
            if ( fc.getFighterListPoolA().size() < 9 )
            {
                // KO 16
                for ( int i = fc.getFightListLooserMapPoolA().size(); i < 7; i++ )
                {
                    if ( fc.getFightListLooserMapPoolA().get( Integer.valueOf( i ) ) == null )
                    {
                        fc.putFightListLoserMapPoolA( Integer.valueOf( i ), new Fight() );
                    }
                }

                for ( int i = fc.getFightListLooserMapPoolB().size(); i < 7; i++ )
                {
                    if ( fc.getFightListLooserMapPoolB().get( Integer.valueOf( i ) ) == null )
                    {
                        fc.putFightListLoserMapPoolB( Integer.valueOf( i ), new Fight() );
                    }
                }
            }

            if ( fc.getFighterListPoolA().size() > 8 && fc.getFighterListPoolA().size() < 17 )
            {
                // KO 32
                for ( int i = fc.getFightListLooserMapPoolA().size(); i < 14; i++ )
                {
                    if ( fc.getFightListLooserMapPoolA().get( Integer.valueOf( i ) ) == null )
                    {
                        fc.putFightListLoserMapPoolA( Integer.valueOf( i ), new Fight() );
                    }
                }

                for ( int i = fc.getFightListLooserMapPoolB().size(); i < 14; i++ )
                {
                    if ( fc.getFightListLooserMapPoolB().get( Integer.valueOf( i ) ) == null )
                    {
                        fc.putFightListLoserMapPoolB( Integer.valueOf( i ), new Fight() );
                    }
                }
            }

            if ( fc.getFighterListPoolA().size() > 16 && fc.getFighterListPoolA().size() < 33 )
            {
                // KO 64
                for ( int i = fc.getFightListLooserMapPoolA().size(); i < 30; i++ )
                {
                    if ( fc.getFightListLooserMapPoolA().get( Integer.valueOf( i ) ) == null )
                    {
                        fc.putFightListLoserMapPoolA( Integer.valueOf( i ), new Fight() );
                    }
                }

                for ( int i = fc.getFightListLooserMapPoolB().size(); i < 30; i++ )
                {
                    if ( fc.getFightListLooserMapPoolB().get( Integer.valueOf( i ) ) == null )
                    {
                        fc.putFightListLoserMapPoolB( Integer.valueOf( i ), new Fight() );
                    }
                }
            }

            FightingclassKoImpl impl = new FightingclassKoImpl();
            fc = impl.doKo( fc, fightDao );

            return FightingKoMapper.mapFightingKoClassFromDB( fc );

        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    /**
     * toggles the status of completed fightingclass and sets or resets the place in the fighter
     */
    public void toggleComplete( Fightingclass fightingclass, ServiceExchangeContext ctx )
        throws JJWManagerException, OptimisticLockingException
    {
        try
        {
            Fightingclass fightingclassDB = fightingclassDao.getFightingclass( fightingclass.getId() );
            setTechnicalDBFieldsForUpdate( fightingclass, ctx );
            optimisticDao.testLocking( TABLE_FIGHTINGCLASS, fightingclass.getId(), fightingclass.getModificationDate() );

            if ( fightingclass.isComplete() )
            {
                // reset places
                resetResults( fightingclass.getId() );
                fightingclass.setComplete( false );
                fightingclass.setCertificationPrint( false );
                configDao.getConfig().setDumpSend( new Timestamp( IValueConstants.YEAR_2000_IN_MS ) );
            }
            else
            {
                // set places
                fightingclass.setComplete( true );
                if ( fightingclass.getFightsystem() == Fightsystem.SIMPLE_POOL )
                {
                    doResultForSimplePool( fightingclass.getId() );
                }
                if ( fightingclass.getFightsystem() == Fightsystem.DOUBLE_POOL )
                {
                    doResultForDoublePool( fightingclass.getId() );
                }
                if ( fightingclass.getFightsystem() == Fightsystem.DOUBLE_KO )
                {
                    doResultForDoubleKO( fightingclass.getId() );
                }
            }
            WeightclassMapper.mapWeightclassToDB( fightingclass, fightingclassDB );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    private void doResultForSimplePool( Long fightingclassId )
        throws JJWManagerException, JJWDataLayerException
    {
        Map<Long, Integer> resultMap = new HashMap<Long, Integer>( 5 );

        FightingSimplePoolClass fc = getFightingclassSimplePool( fightingclassId );
        for ( FightingSimplePoolItem item : fc.getFighterList() )
        {

            resultMap.put( item.getFighter().getId(), item.getPlace() );
        }
        fightingclassDao.setFightingclassResults( resultMap, fightingclassId );
    }

    private void doResultForDoublePool( Long fightingclassId )
        throws JJWManagerException, JJWDataLayerException
    {
        Map<Long, Integer> resultMap = new HashMap<Long, Integer>( 10 );

        FightingDoublePoolClass fc = getFightingclassDoublePool( fightingclassId );

        for ( FightingDoublePoolItem item : fc.getFighterListPoolA() )
        {

            resultMap.put( item.getFighter().getId(), item.getPlace() );
        }

        for ( FightingDoublePoolItem item : fc.getFighterListPoolB() )
        {

            resultMap.put( item.getFighter().getId(), item.getPlace() );
        }
        
               
        resultMap.put( fc.getHalfFinalFight1().getFighterIdRed(), fc.getHalfFinalFight1().getFighterRed().getPlace() );
        resultMap.put( fc.getHalfFinalFight1().getFighterIdBlue(), fc.getHalfFinalFight1().getFighterBlue().getPlace() );
        
        resultMap.put( fc.getHalfFinalFight2().getFighterIdRed(), fc.getHalfFinalFight2().getFighterRed().getPlace() );
        resultMap.put( fc.getHalfFinalFight2().getFighterIdBlue(), fc.getHalfFinalFight2().getFighterBlue().getPlace() );
        
        resultMap.put( fc.getFinalFight().getFighterIdRed(), fc.getFinalFight().getFighterRed().getPlace() );
        resultMap.put( fc.getFinalFight().getFighterIdBlue(), fc.getFinalFight().getFighterBlue().getPlace() );

//        if ( fc.getFinalFight().getWinnerId().longValue() >= TypeUtil.LONG_DEFAULT )
//        {
//            if ( fc.getFinalFight().getWinnerId().longValue() == TypeUtil.LONG_DEFAULT )
//            {
//                resultMap.put( fc.getFinalFight().getFighterIdRed(), 2 );
//                resultMap.put( fc.getFinalFight().getFighterIdBlue(), 2 );
//            }
//
//            if ( fc.getFinalFight().getWinnerId().longValue() == fc.getFinalFight().getFighterIdRed() )
//            {
//                resultMap.put( fc.getFinalFight().getFighterIdRed(), 1 );
//                resultMap.put( fc.getFinalFight().getFighterIdBlue(), 2 );
//            }
//
//            if ( fc.getFinalFight().getWinnerId().longValue() == fc.getFinalFight().getFighterIdBlue() )
//            {
//                resultMap.put( fc.getFinalFight().getFighterIdRed(), 2 );
//                resultMap.put( fc.getFinalFight().getFighterIdBlue(), 1 );
//            }
//        }

        fightingclassDao.setFightingclassResults( resultMap, fightingclassId );
    }

    private void doResultForDoubleKO( Long fightingclassId )
        throws JJWManagerException, JJWDataLayerException
    {
        Map<Long, Integer> topList = new HashMap<Long, Integer>( 5 );
        FightingKoClass fc = getFightingclassKo( fightingclassId );

        if ( fc.getFinalFight().getWinnerId().longValue() == fc.getFinalFight().getFighterIdRed() )
        {
            if ( !topList.containsKey( Long.valueOf( fc.getFinalFight().getFighterIdBlue() ) ) )
            {
                topList.put( Long.valueOf( fc.getFinalFight().getFighterIdBlue() ), TypeUtil.INTEGER_2 );
            }
            if ( !topList.containsKey( Long.valueOf( fc.getFinalFight().getFighterIdRed() ) ) )
            {
                topList.put( Long.valueOf( fc.getFinalFight().getFighterIdRed() ), TypeUtil.INTEGER_1 );
            }
        }
        if ( fc.getFinalFight().getWinnerId().longValue() == fc.getFinalFight().getFighterIdBlue() )
        {
            if ( !topList.containsKey( Long.valueOf( fc.getFinalFight().getFighterIdBlue() ) ) )
            {
                topList.put( Long.valueOf( fc.getFinalFight().getFighterIdBlue() ), TypeUtil.INTEGER_1 );
            }
            if ( !topList.containsKey( Long.valueOf( fc.getFinalFight().getFighterIdRed() ) ) )
            {
                topList.put( Long.valueOf( fc.getFinalFight().getFighterIdRed() ), TypeUtil.INTEGER_2 );
            }
        }
        if ( fc.getFinalFight().getWinnerId() == TypeUtil.LONG_DEFAULT && fc.getFinalFight().getPointsRed() == 0
            && fc.getFinalFight().getPointsBlue() == 0 )
        { // double DQ

            if ( !topList.containsKey( Long.valueOf( fc.getFinalFight().getFighterIdBlue() ) ) )
            {
                topList.put( Long.valueOf( fc.getFinalFight().getFighterIdBlue() ), TypeUtil.INTEGER_2 );
            }
            if ( !topList.containsKey( Long.valueOf( fc.getFinalFight().getFighterIdRed() ) ) )
            {
                topList.put( Long.valueOf( fc.getFinalFight().getFighterIdRed() ), TypeUtil.INTEGER_2 );
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

        fightingclassDao.setFightingclassResults( topList, fightingclassId );
    }

    private void doLoserRoundResult( Fight fight, Integer place, Integer dqPlace, Map<Long, Integer> topList )
    {
        if ( fight == null )
        {
            return;
        }
        if ( fight.getWinnerId() == TypeUtil.LONG_DEFAULT && fight.getPointsRed() == 0 && fight.getPointsBlue() == 0 )
        { // double DQ

            if ( !topList.containsKey( Long.valueOf( fight.getFighterIdBlue() ) ) )
            {
                topList.put( Long.valueOf( fight.getFighterIdBlue() ), dqPlace );
            }
            if ( !topList.containsKey( Long.valueOf( fight.getFighterIdRed() ) ) )
            {
                topList.put( Long.valueOf( fight.getFighterIdRed() ), dqPlace );
            }
        }
        // if(fight.getWinner () == -1 && fight.getIdWhite() == 0) setResult (fight.getIdRed (), place,topList);
        // if(fight.getWinner () == -1 && fight.getIdRed() == 0) setResult (fight.getIdWhite (), place,topList);

        if ( fight.getWinnerId() == fight.getFighterIdBlue() )
        {
            if ( !topList.containsKey( Long.valueOf( fight.getFighterIdBlue() ) ) )
            {
                topList.put( Long.valueOf( fight.getFighterIdBlue() ), place );
            }
            if ( !topList.containsKey( Long.valueOf( fight.getFighterIdRed() ) ) )
            {
                topList.put( Long.valueOf( fight.getFighterIdRed() ), dqPlace );
            }
        }
        if ( fight.getWinnerId() == fight.getFighterIdRed() )
        {
            if ( !topList.containsKey( Long.valueOf( fight.getFighterIdBlue() ) ) )
            {
                topList.put( Long.valueOf( fight.getFighterIdBlue() ), dqPlace );
            }
            if ( !topList.containsKey( Long.valueOf( fight.getFighterIdRed() ) ) )
            {
                topList.put( Long.valueOf( fight.getFighterIdRed() ), place );
            }
        }
    }

    private void resetResults( Long fightingclassId )
        throws JJWDataLayerException
    {
        fightingclassDao.resetFightingclassResults( fightingclassId );
    }

    public void toggleDeleteStop( Fightingclass fightingclass, ServiceExchangeContext ctx )
        throws JJWManagerException, OptimisticLockingException
    {
        try
        {
            Fightingclass fightingclassDB = fightingclassDao.getFightingclass( fightingclass.getId() );
            setTechnicalDBFieldsForUpdate( fightingclass, ctx );
            optimisticDao.testLocking( TABLE_FIGHTINGCLASS, fightingclass.getId(), fightingclass.getModificationDate() );
            if ( fightingclass.isDeleteStop() )
            {
                fightingclass.setDeleteStop( false );
            }
            else
            {
                fightingclass.setDeleteStop( true );
            }
            WeightclassMapper.mapWeightclassToDB( fightingclass, fightingclassDB );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    /**
     * get the type of the fightsystem for this fightingclass
     * 
     * @param fightingclassId
     * @return
     * @throws JJWManagerException
     */
    public int getFightsystemOfFightingclass( long fightingclassId )
        throws JJWManagerException
    {
        try
        {
            return getFightingclassDao().getFightsystemOfFightingclass( fightingclassId );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public FightingCertification getFightingCertification( Long fightingclassId )
        throws JJWManagerException
    {
        try
        {
            return getFightingclassDao().getFightingCertification( fightingclassId );
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

    public FightDao getFightDao()
    {
        return fightDao;
    }

    public void setFightDao( FightDao fightDao )
    {
        this.fightDao = fightDao;
    }

    public void changeFighterInClass( int fighter1, int fighter2, long fightingclassId )
        throws JJWManagerException
    {

        // get pool an pooltype
        // get fighters
        // change Fighters in class
        // change Fights
        try
        {
            Fightingclass fightingclass = fightingclassDao.getFightingclass( fightingclassId );
            switch ( getFightsystemOfFightingclass( fightingclassId ) )
            {

                case Fightsystem.SIMPLE_POOL:
                    changeFighterInPool( fighter1, fighter2, fightingclass );
                    break;

                case Fightsystem.DOUBLE_KO:
                    changeFighterInKo( fighter1, fighter2, fightingclass );
                    break;

                case Fightsystem.DOUBLE_POOL:
                    changeFighterInDoublePool( fighter1, fighter2, fightingclass );
                    break;
            }
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    private void changeFighterInPool( int fighter1, int fighter2, Fightingclass fightingclass )
        throws JJWDataLayerException
    {
        fightingclassDao.changeFighterInPool( fighter1, fighter2, fightingclass );
    }

    private void changeFighterInDoublePool( int fighter1, int fighter2, Fightingclass fightingclass )
        throws JJWDataLayerException
    {
        fightingclassDao.changeFighterInDPool( fighter1, fighter2, fightingclass );
    }

    private void changeFighterInKo( int fighter1, int fighter2, Fightingclass fightingclass )
        throws JJWDataLayerException
    {
        fightingclassDao.changeFighterInKo( fighter1, fighter2, fightingclass );
    }

    public int getNumberOfFighterInFightingclass( long fightingclassId )
        throws JJWManagerException
    {
        try
        {
            return fightingclassDao.getNumberOfFighterInFightingclass( fightingclassId );

        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }

    public void createAutomaticFightingclasses( Long ageId, Long userId )
        throws JJWManagerException
    {
        try
        {
            fightingclassDao.createAutomaticFightingclasses( ageId, userId );

        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }

    public void sortFighterIntoClasses( Long userId )
        throws JJWManagerException
    {
        try
        {
            fightingclassDao.sortFighterIntoClasses( userId );

        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }

    public List<Age> getAgesForAutomaticFightingclassCreation()
        throws JJWManagerException
    {
        try
        {
            return fightingclassDao.getAgesForAutomaticFightingclassCreation();

        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }

	
	public List<FightingclassWeb> getChildFightingclasses(Long fightingclassId,
			Locale locale) throws JJWManagerException {
		try
        {
			 return WeightclassMapper.mapWeightclassListFromDB(fightingclassDao.getChildFightingclasses(fightingclassId), locale);
        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
	}

	
	public List<FightingclassWeb> getCombinableFightingclasses(
			Long fightingclassId, Locale locale) throws JJWManagerException {
		try
        {
			 return WeightclassMapper.mapWeightclassListFromDB(fightingclassDao.getCombinableFightingclasses(fightingclassId), locale);
        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
	}

    @Override
    public void addFightingclassToClass( long parentClass, long childClass )
        throws JJWManagerException
    {
        try
        {
            fightingclassDao.addFightingclassToClass( parentClass, childClass );
            // delete child class from pool entries and corresponding fights
            fightingclassDao.removeUnusedFightingclass( childClass );

            // delete child class from pool entries and corresponding fights
            fightingclassDao.removeUnusedFightingclass( parentClass );
        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }

    @Override
    public void removeFightingclassFromParent( long parentClass, long childClass )
        throws JJWManagerException
    {
        try
        {
            fightingclassDao.removeFightingclassFromParent( parentClass, childClass );
            // delete child class from pool entries and corresponding fights
            fightingclassDao.removeUnusedFightingclass( childClass );

            // delete child class from pool entries and corresponding fights
            fightingclassDao.removeUnusedFightingclass( parentClass );
        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }

    public void startClass( Fightingclass fightingclass, ServiceExchangeContext ctx )
        throws JJWManagerException, OptimisticLockingException
    {
        try
        {
            Fightingclass fightingclassDB = fightingclassDao.getFightingclass( fightingclass.getId() );


            setTechnicalDBFieldsForUpdate( fightingclassDB, ctx );
            fightingclassDB.setStartTime( new Timestamp( System.currentTimeMillis() ) );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }


    private long getStartTime (List<EstimatedTimes> list, long weightclassId)
    {
        long userId=0;
        int lastOrderNumber=0;
        long retTime=0;
        long hlpTime=0;
        for ( EstimatedTimes item : list )
        {
            if ( weightclassId == item.getWeightclassId() &&  "F".equals( item.getDiscepline()))
            {
                userId = item.getUserId();
                break;
            }
        }

        for ( EstimatedTimes item : list )
        {
            if ( userId == item.getUserId() && "F".equals( item.getDiscepline()))
            {                               
                if ( weightclassId == item.getWeightclassId() )
                {
                    if ( lastOrderNumber == item.getOrderNumber() )
                    {
                        return retTime;
                    }
                    return retTime+hlpTime;
                }
                
                if ( lastOrderNumber == item.getOrderNumber() )
                  {
                    hlpTime+=item.getAddTime();                    
                  }
                  else
                  {
                      retTime+=item.getAddTime()+hlpTime;
                      hlpTime=0;
                  }                
                lastOrderNumber = item.getOrderNumber();
            }
        }                
        return retTime;
    }
    
}
