/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaclassManagerImpl.java
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

package de.jjw.service.impl.newa;

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
import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.dao.newa.NewaFightDao;
import de.jjw.dao.newa.NewaFighterDao;
import de.jjw.dao.newa.NewaclassDao;
import de.jjw.model.ITechnicalDBFields;
import de.jjw.model.admin.Age;
import de.jjw.model.admin.Fightsystem;
import de.jjw.model.newa.NewaCertification;
import de.jjw.model.newa.NewaDoublePoolClass;
import de.jjw.model.newa.NewaDoublePoolItem;
import de.jjw.model.newa.NewaFight;
import de.jjw.model.newa.NewaFighter;
import de.jjw.model.newa.NewaKoClass;
import de.jjw.model.newa.NewaSimplePoolClass;
import de.jjw.model.newa.NewaSimplePoolItem;
import de.jjw.model.newa.Newaclass;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.exception.NewaclassCreateException;
import de.jjw.service.impl.BaseManager;
import de.jjw.service.mapper.newa.NewaKoMapper;
import de.jjw.service.mapper.newa.NewaWeightclassMapper;
import de.jjw.service.modelWeb.NewaclassWeb;
import de.jjw.service.newa.NewaclassManager;
import de.jjw.service.util.helper.FightsystemHelper;
import de.jjw.service.util.helper.NewaclassHelper;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;
import de.jjw.util.dto.SortDTO;



public class NewaclassManagerImpl
    extends BaseManager
    implements NewaclassManager
{

    private NewaFighterDao newaFighterDao;

    private NewaFightDao newaFightDao;

    private NewaclassDao newaclassDao;

    private FightsystemDao fightsystemDao;

    private ConfigDao configDao;


    public void setNewaFighterDao( NewaFighterDao fighterDao )
    {
        this.newaFighterDao = fighterDao;
    }

    /**
     * @param newaclassDao the fightingclassDao to set
     */
    public void setNewaclassDao( NewaclassDao newaclassDao )
    {
        this.newaclassDao = newaclassDao;
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


    public void createNewaclasses( ServiceExchangeContext ctx )
        throws NewaclassCreateException
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
            newaclassDao.removeUnusedNewaclasses();
            // classes with at least one fighter
            List<Newaclass> usedNewaclasses = newaFighterDao.getUsedWeightclasses();
            List<Fightsystem> fightsystems = fightsystemDao.getAllFightsystems();
            Fightsystem fightsystem = null;
            List<Newaclass> newaclasses = newaclassDao.getDeleteStoppedNewaclasses();

            if ( newaclasses == null )
            {
                newaclasses = new ArrayList<Newaclass>( 1 );
            }

            Map<Long, Newaclass> newaclassesMap = NewaclassHelper.getNewaclassesMap( newaclasses );
            Newaclass newaclass = null;
            // 2.
            for ( Newaclass usesNewaclassItem : usedNewaclasses )
            {
                // check if fightingclass exists
                if ( newaclassesMap.get( usesNewaclassItem.getId() ) != null )
                {
                    continue;
                }

                // 2a.
                List<NewaFighter> fighters = newaFighterDao.getFighterByNewaclassForDraw( usesNewaclassItem );
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
                            newaclass = doSimplePool( fighters, usesNewaclassItem, ctx );
                            newaclassDao.saveNewaclassAfterCreate( newaclass );
                            break;

                        case Fightsystem.DOUBLE_POOL:
                            newaclass = doDoublePool( fighters, usesNewaclassItem, ctx );
                            newaclassDao.saveNewaclassAfterCreate( newaclass );
                            break;

                        case Fightsystem.DOUBLE_KO:
                            newaclass = doKo( fighters, usesNewaclassItem, ctx );
                            newaclassDao.saveNewaclassAfterCreate( newaclass );
                            break;
                    }
                    // saveFightingclass(fightingclass);

                }

            }
        }
        catch ( Exception e )
        {
            throw new NewaclassCreateException( e );
        }

    }

    private Newaclass doDoublePool( List<NewaFighter> fighters, Newaclass newaclass, ServiceExchangeContext ctx )
    {
        NewaclassPoolCreateImpl impl = new NewaclassPoolCreateImpl();
        return impl.doDoublePool( fighters, newaclass, ctx );
    }

    /**
     * @param fighters
     * @param weightclass
     * @return
     */
    private Newaclass doSimplePool( List<NewaFighter> fighters, Newaclass newaclass, ServiceExchangeContext ctx )
        throws Exception
    {

        NewaclassPoolCreateImpl impl = new NewaclassPoolCreateImpl();
        return impl.doSimplePool( fighters, newaclass, ctx, configDao.getConfig().getFightRevenge() );
    }

    /**
     * @param fighters
     * @param weightclass
     * @return
     */
    private Newaclass doKo( List<NewaFighter> fighters, Newaclass newaclass, ServiceExchangeContext ctx )
    {
        NewaclassKoCreateImpl impl = new NewaclassKoCreateImpl();
        return impl.doKo( fighters, newaclass, ctx );
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

    public List<NewaclassWeb> getInUseNewaclasses( Map<Integer, SortDTO> sortParameter, Locale locale )
        throws JJWManagerException
    {
        try
        {
            List<NewaclassWeb> retList =
                NewaWeightclassMapper.mapWeightclassListFromDB( newaclassDao.getInUseNewaclasses( sortParameter ),
                                                            locale );

            long current = System.currentTimeMillis();
            Map<Long, Long> timesMap = newaclassDao.getTimesOfCurrentTatamiUse();
            for ( NewaclassWeb item : retList )
            {
                if (timesMap.get( item.getId()) != null)
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
    public Map<Integer, Newaclass> getAllNewaclasses( boolean onlyCompleted )
        throws JJWManagerException
    {

        try
        {
            List<Newaclass> fc = newaclassDao.getInUseNewaclasses( null );
            Map<Integer, Newaclass> retMap = new HashMap<Integer, Newaclass>( fc.size() );
            int counter = 0;
            for ( Newaclass item : fc )
            {
                if ( onlyCompleted && !item.isComplete() )
                    continue;

                if ( item.getFightsystem() == Fightsystem.SIMPLE_POOL )

                {
                    counter++;
                    retMap.put( counter, getNewaclassSimplePool( item.getId() ) );
                }

                if ( item.getFightsystem() == Fightsystem.DOUBLE_POOL )

                {
                    counter++;
                    retMap.put( counter, getNewaclassDoublePool( item.getId() ) );
                }

                if ( item.getFightsystem() == Fightsystem.DOUBLE_KO )
                {
                    counter++;
                    retMap.put( counter, getNewaclassKo( item.getId() ) );

                }
            }
            return retMap;
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public List<NewaclassWeb> getInUseNewaclassesForTatami( Map<Integer, SortDTO> sortParameter, Locale locale,
                                                                    ServiceExchangeContext ctx )
        throws JJWManagerException
    {

        try
        {
            List<NewaclassWeb> retList =
                NewaWeightclassMapper.mapWeightclassListFromDB( newaclassDao.getInUseNewaclassesForTatami(
                                                                                                                  sortParameter,
                                                                                                                  ctx.getUserId() ),
                                                               locale );
            long current = System.currentTimeMillis();
            Map<Long, Long> timesMap = newaclassDao.getTimesOfCurrentTatamiUse();
            for ( NewaclassWeb item : retList )
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

    public NewaSimplePoolClass getNewaclassSimplePool( Long newaclassId )
        throws JJWManagerException
    {
        NewaclassPoolsImpl pool = new NewaclassPoolsImpl();
        try
        {
            return pool.getNewaclassSimplePool( newaclassId, getNewaclassDao(),
                                                    configDao.getConfig().getFightRevenge() );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    /**
     * @param newaclassId
     * @return
     * @throws JJWManagerException
     */
    public NewaDoublePoolClass getNewaclassDoublePool( Long newaclassId )
        throws JJWManagerException
    {
        NewaclassPoolsImpl dpool = new NewaclassPoolsImpl();
        return dpool.getNewaclassDoublePool( newaclassId, getNewaclassDao(), getFightDao(), true );
    }

    public NewaKoClass getNewaclassKo( Long newaclassId )
        throws JJWManagerException
    {

        try
        {

            NewaKoClass fc = newaclassDao.getNewaclassKo( newaclassId );

            fc.setId( newaclassId );
            // add not persisted fights only for calculating
            if ( fc.getFighterListPoolA().size() < 9 )
            {
                // KO 16
                for ( int i = fc.getFightListLooserMapPoolA().size(); i < 7; i++ )
                {
                    if ( fc.getFightListLooserMapPoolA().get( Integer.valueOf( i ) ) == null )
                    {
                        fc.putFightListLoserMapPoolA( Integer.valueOf( i ), new NewaFight() );
                    }
                }

                for ( int i = fc.getFightListLooserMapPoolB().size(); i < 7; i++ )
                {
                    if ( fc.getFightListLooserMapPoolB().get( Integer.valueOf( i ) ) == null )
                    {
                        fc.putFightListLoserMapPoolB( Integer.valueOf( i ), new NewaFight() );
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
                        fc.putFightListLoserMapPoolA( Integer.valueOf( i ), new NewaFight() );
                    }
                }

                for ( int i = fc.getFightListLooserMapPoolB().size(); i < 14; i++ )
                {
                    if ( fc.getFightListLooserMapPoolB().get( Integer.valueOf( i ) ) == null )
                    {
                        fc.putFightListLoserMapPoolB( Integer.valueOf( i ), new NewaFight() );
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
                        fc.putFightListLoserMapPoolA( Integer.valueOf( i ), new NewaFight() );
                    }
                }

                for ( int i = fc.getFightListLooserMapPoolB().size(); i < 30; i++ )
                {
                    if ( fc.getFightListLooserMapPoolB().get( Integer.valueOf( i ) ) == null )
                    {
                        fc.putFightListLoserMapPoolB( Integer.valueOf( i ), new NewaFight() );
                    }
                }
            }

            NewaclassKoImpl impl = new NewaclassKoImpl();
            fc = impl.doKo( fc, newaFightDao );

            return NewaKoMapper.mapFightingKoClassFromDB( fc );

        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    /**
     * toggles the status of completed newaclass and sets or resets the place in the fighter
     */
    public void toggleComplete( Newaclass newaclass, ServiceExchangeContext ctx )
        throws JJWManagerException, OptimisticLockingException
    {
        try
        {
            Newaclass fightingclassDB = newaclassDao.getNewaclass( newaclass.getId() );
            setTechnicalDBFieldsForUpdate( newaclass, ctx );
            optimisticDao.testLocking( TABLE_NEWACLASS, newaclass.getId(), newaclass.getModificationDate() );

            if ( newaclass.isComplete() )
            {
                // reset places
                resetResults( newaclass.getId() );
                newaclass.setComplete( false );
                newaclass.setCertificationPrint( false );
                configDao.getConfig().setDumpSend( new Timestamp( IValueConstants.YEAR_2000_IN_MS ) );
            }
            else
            {
                // set places
                newaclass.setComplete( true );
                if ( newaclass.getFightsystem() == Fightsystem.SIMPLE_POOL )
                {
                    doResultForSimplePool( newaclass.getId() );
                }
                if ( newaclass.getFightsystem() == Fightsystem.DOUBLE_POOL )
                {
                    doResultForDoublePool( newaclass.getId() );
                }
                if ( newaclass.getFightsystem() == Fightsystem.DOUBLE_KO )
                {
                    doResultForDoubleKO( newaclass.getId() );
                }
            }
            NewaWeightclassMapper.mapWeightclassToDB( newaclass, fightingclassDB );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    private void doResultForSimplePool( Long newaclassId )
        throws JJWManagerException, JJWDataLayerException
    {
        Map<Long, Integer> resultMap = new HashMap<Long, Integer>( 5 );

        NewaSimplePoolClass fc = getNewaclassSimplePool( newaclassId );
        for ( NewaSimplePoolItem item : fc.getFighterList() )
        {

            resultMap.put( item.getFighter().getId(), item.getPlace() );
        }
        newaclassDao.setNewaclassResults( resultMap, newaclassId );
    }

    private void doResultForDoublePool( Long newaclassId )
        throws JJWManagerException, JJWDataLayerException
    {
        Map<Long, Integer> resultMap = new HashMap<Long, Integer>( 10 );

        NewaDoublePoolClass fc = getNewaclassDoublePool( newaclassId );

        for ( NewaDoublePoolItem item : fc.getFighterListPoolA() )
        {

            resultMap.put( item.getFighter().getId(), item.getPlace() );
        }

        for ( NewaDoublePoolItem item : fc.getFighterListPoolB() )
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

        newaclassDao.setNewaclassResults( resultMap, newaclassId );
    }

    private void doResultForDoubleKO( Long fightingclassId )
        throws JJWManagerException, JJWDataLayerException
    {
        Map<Long, Integer> topList = new HashMap<Long, Integer>( 5 );
        NewaKoClass fc = getNewaclassKo( fightingclassId );

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

        newaclassDao.setNewaclassResults( topList, fightingclassId );
    }

    private void doLoserRoundResult( NewaFight fight, Integer place, Integer dqPlace, Map<Long, Integer> topList )
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
        newaclassDao.resetNewaclassResults( fightingclassId );
    }

    public void toggleDeleteStop( Newaclass newaclass, ServiceExchangeContext ctx )
        throws JJWManagerException, OptimisticLockingException
    {
        try
        {
            Newaclass fightingclassDB = newaclassDao.getNewaclass( newaclass.getId() );
            setTechnicalDBFieldsForUpdate( newaclass, ctx );
            optimisticDao.testLocking( TABLE_NEWACLASS, newaclass.getId(), newaclass.getModificationDate() );
            if ( newaclass.isDeleteStop() )
            {
                newaclass.setDeleteStop( false );
            }
            else
            {
                newaclass.setDeleteStop( true );
            }
            NewaWeightclassMapper.mapWeightclassToDB( newaclass, fightingclassDB );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    /**
     * get the type of the fightsystem for this fightingclass
     * 
     * @param newaclassId
     * @return
     * @throws JJWManagerException
     */
    public int getFightsystemOfNewaclass( long newaclassId )
        throws JJWManagerException
    {
        try
        {
            return getNewaclassDao().getFightsystemOfNewaclass( newaclassId );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public NewaCertification getNewaCertification( Long newaclassId )
        throws JJWManagerException
    {
        try
        {
            return getNewaclassDao().getNewaCertification( newaclassId );
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

    public NewaFightDao getFightDao()
    {
        return newaFightDao;
    }

    public void setNewaFightDao( NewaFightDao fightDao )
    {
        this.newaFightDao = fightDao;
    }

    public void changeFighterInClass( int fighter1, int fighter2, long newaclassId )
        throws JJWManagerException
    {

        // get pool an pooltype
        // get fighters
        // change Fighters in class
        // change Fights
        try
        {
            Newaclass fightingclass = newaclassDao.getNewaclass( newaclassId );
            switch ( getFightsystemOfNewaclass( newaclassId ) )
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

    private void changeFighterInPool( int fighter1, int fighter2, Newaclass newaclass )
        throws JJWDataLayerException
    {
        newaclassDao.changeFighterInPool( fighter1, fighter2, newaclass );
    }

    private void changeFighterInDoublePool( int fighter1, int fighter2, Newaclass newaclass )
        throws JJWDataLayerException
    {
        newaclassDao.changeFighterInDPool( fighter1, fighter2, newaclass );
    }

    private void changeFighterInKo( int fighter1, int fighter2, Newaclass newaclass )
        throws JJWDataLayerException
    {
        newaclassDao.changeFighterInKo( fighter1, fighter2, newaclass );
    }

    public int getNumberOfFighterInNewaclass( long newaclassId )
        throws JJWManagerException
    {
        try
        {
            return newaclassDao.getNumberOfFighterInNewaclass( newaclassId );

        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }

    public void createAutomaticNewaclasses( Long ageId, Long userId )
        throws JJWManagerException
    {
        try
        {
            newaclassDao.createAutomaticNewaclasses( ageId, userId );

        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }

    public void sortNewaFighterIntoClasses( Long userId )
        throws JJWManagerException
    {
        try
        {
            newaclassDao.sortFighterIntoClasses( userId );

        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }

    public List<Age> getAgesForAutomaticNewaclassCreation()
        throws JJWManagerException
    {
        try
        {
            return newaclassDao.getAgesForAutomaticNewaclassCreation();

        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }

	
    public List<NewaclassWeb> getChildNewaclasses( Long newaclassId,
			Locale locale) throws JJWManagerException {
		try
        {
            return NewaWeightclassMapper.mapWeightclassListFromDB( newaclassDao.getChildNewaclasses( newaclassId ),
                                                                   locale );
        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
	}

	
    public List<NewaclassWeb> getCombinableNewaclasses( Long newaclassId, Locale locale )
        throws JJWManagerException
    {
		try
        {
            return NewaWeightclassMapper.mapWeightclassListFromDB( newaclassDao.getCombinableNewaclasses( newaclassId ),
                                                                   locale );
        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
	}

    @Override
    public void addNewaclassToClass( long parentClass, long childClass )
        throws JJWManagerException
    {
        try
        {
            newaclassDao.addNewaclassToClass( parentClass, childClass );
            // delete child class from pool entries and corresponding fights
            newaclassDao.removeUnusedNewaclass( childClass );

            // delete child class from pool entries and corresponding fights
            newaclassDao.removeUnusedNewaclass( parentClass );
        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }

    @Override
    public void removeNewaclassFromParent( long parentClass, long childClass )
        throws JJWManagerException
    {
        try
        {
            newaclassDao.removeNewaclassFromParent( parentClass, childClass );
            // delete child class from pool entries and corresponding fights
            newaclassDao.removeUnusedNewaclass( childClass );

            // delete child class from pool entries and corresponding fights
            newaclassDao.removeUnusedNewaclass( parentClass );
        }
        catch ( Exception e )
        {
            throw new JJWManagerException( e );
        }
    }

    public void startClass( Newaclass newaclass, ServiceExchangeContext ctx )
        throws JJWManagerException, OptimisticLockingException
    {
        try
        {
            Newaclass newaclassDB = newaclassDao.getNewaclass( newaclass.getId() );


            setTechnicalDBFieldsForUpdate( newaclassDB, ctx );
            newaclassDB.setStartTime( new Timestamp( System.currentTimeMillis() ) );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }


}
