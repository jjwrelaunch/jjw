/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightingclassDaoHibernate.java
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

package de.jjw.dao.hibernate.fighting;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.dao.DataAccessResourceFailureException;

import de.jjw.dao.fighting.FightingclassDao;
import de.jjw.dao.hibernate.BaseDaoHibernate;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.dao.hibernate.admin.IAgeDatabaseConstants;
import de.jjw.model.admin.Age;
import de.jjw.model.admin.ConfigJJW;
import de.jjw.model.admin.Fightsystem;
import de.jjw.model.fighting.Fight;
import de.jjw.model.fighting.Fighter;
import de.jjw.model.fighting.FightingCertification;
import de.jjw.model.fighting.FightingDoublePoolClass;
import de.jjw.model.fighting.FightingDoublePoolItem;
import de.jjw.model.fighting.FightingKoClass;
import de.jjw.model.fighting.FightingKoItem;
import de.jjw.model.fighting.FightingSimplePoolClass;
import de.jjw.model.fighting.FightingSimplePoolItem;
import de.jjw.model.fighting.Fightingclass;
import de.jjw.model.EstimatedTimes;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;
import de.jjw.util.dto.SortDTO;

public class FightingclassDaoHibernate
    extends BaseDaoHibernate
    implements IWeightclassDatabaseConstants, IFightingSimplePoolItemDatabaseConstants,
    IFightingDoublePoolItemDatabaseConstants, FightingclassDao, IFightDatabaseConstants,
    IFightingKoItemDatabaseConstants, IAgeDatabaseConstants
{

    private static final String COMMA = ",";

    private static String GET_DELETE_STOPPED_FIGHTINGCLASSES =
        SQL_FROM + TABLE_WEIGHTCLASS + " w " + SQL_WHERE + "w." + DELETE_STOP + SQL_IS + SQL_NOT + SQL_NULL + SQL_AND
            + "w." + DELETE_STOP + SQL_EQUAL + "'Y'";

    private static String GET_INUSE_FIGHTINGCLASSES =
        SQL_FROM + TABLE_WEIGHTCLASS + " w " + SQL_WHERE + "w." + INUSE + SQL_IS + SQL_NOT + SQL_NULL + SQL_AND + "w."
            + INUSE + SQL_EQUAL + "'Y'" + SQL_ORDER_BY + "w." + AGE + COMMA + "w." + SEX + COMMA + "w." + WEIGHT_LIMIT;

    // private static String GET_INUSE_FIGHTINGCLASSES_WITH_ORDER_BY_DEFAULT = SQL_SELECT + "w.*" +SQL_FROM +
    // TABLE_WEIGHTCLASS + " w "+ SQL_WHERE + "w." +
    // INUSE + SQL_IS+ SQL_NOT + SQL_NULL + SQL_AND + "w." + INUSE + SQL_EQUAL + "'Y'" +
    // SQL_ORDER_BY + "w." + AGE + "," + "w." + SEX + "," + "w." + WEIGHT_LIMIT;

    /**
     * return : Fightingclass, numberOfFighterInClass, numberOfFights, completedFights,
     * fightsystem,averageFighttimeInClass
     */
    private static String GET_INUSE_FIGHTINGCLASSES_WITH_ORDER_BY_DEFAULT =
        SQL_SELECT
            + " new Fightingclass(w, "
            + " (select count(distinct f.id) from Fighter f where f.fightingclass = w.id and f.ready=1 ),"
            +
            // number of fighter in class

            // " (select count(distinct fl.id) from Fight fl where fl.fightingclass = w.id ) -" +
            // " (select count(distinct fl.id) from Fight fl where fl.fightingclass = w.id AND fl.fighterIdBlue not in ( ? )),"
            // +

            " (SELECT COUNT(DISTINCT fl.id) FROM Fight fl WHERE fl.fightingclass = w.id ),"
            +

            " (SELECT COUNT(DISTINCT fl.id) FROM Fight fl WHERE fl.fightingclass = w.id AND fl.winnerId IS NOT NULL AND fl.winnerId >= 0 "
            + "    AND fl.fighterIdBlue not in ( ? )),"
            +

            "(select s.fightsystemType from Fightsystem s where s.minParticipant<="
            + "  (select count(distinct f.id) from Fighter f where f.fightingclass = w.id and f.ready =1) AND s.maxParticipant>="
            + "  (select count(distinct f.id) from Fighter f where f.fightingclass = w.id and f.ready =1) ),"
            +

            " (select min(fl.createDate) from Fight fl where fl.fightingclass = w.id), "
            + " (select min(fl.modificationDate) from Fight fl where fl.fightingclass = w.id and fl.modificationDate > fl.createDate)"
            +

            ")"
            +
            // " (select avg(fl2.overallFightTime)  from Fight fl2 where fl2.fightingclass = w.id and fl2.winnerId is not null and fl2.winnerId < 0))"
            // +
            SQL_FROM + TABLE_WEIGHTCLASS
            + " w "
            +
            // " LEFT JOIN FETCH w." + IFighterDatabaseConstants.AGE + " a " +
            SQL_WHERE + "w." + INUSE + SQL_IS + SQL_NOT + SQL_NULL + SQL_AND + "w." + INUSE + SQL_EQUAL + "'Y'"
            + SQL_ORDER_BY + "w.age." + ORDER_NUMBER + " , " + "w." + AGE + COMMA + "w." + SEX + COMMA + "w."
            + WEIGHT_LIMIT;

    private static String W_DOT = " w.";

    // private static String GET_ALL_FIGHTINGCLASSES_WITH_SIMPLE_POOL="";

    // private static String GET_ALL_UNUSES_FIGHTINGCLASSES = SQL_FROM + TABLE_WEIGHTCLASS + " f " +
    // SQL_WHERE + "f." + DELETE_STOP+ SQL_IS + SQL_NULL + SQL_OR + "f." + DELETE_STOP + SQL_LOWER + "1";

    private static String UPDATE_UNUSED_FIGHTINGCLASS =
        SQL_UPDATE + TABLE_WEIGHTCLASS + SQL_SET + INUSE + SQL_EQUAL + "'N'" + SQL_WHERE + DELETE_STOP + SQL_IS
            + SQL_NULL + SQL_OR + DELETE_STOP + SQL_EQUAL + "'N'";

    private static String DELETE_UNUSED_FIGHTING_SIMPLE_POOL_ITEM =
        SQL_DELETE + SQL_FROM + TABLE_FIGHTING_SIMPLE_POOL + " f " + SQL_WHERE + " f."
            + IFightingSimplePoolItemDatabaseConstants.FIGHTINGCLASS + SQL_IN + SQL_BRACE_OPEN + SQL_FROM
            + TABLE_WEIGHTCLASS + " fc " + SQL_WHERE + "fc." + DELETE_STOP + SQL_IS + SQL_NULL + SQL_OR + "fc."
            + DELETE_STOP + SQL_EQUAL + "'N'" + SQL_BRACE_CLOSE;

    private static String DELETE_UNUSED_FIGHTING_DOUBLE_POOL_ITEM =
        SQL_DELETE + SQL_FROM + TABLE_FIGHTING_DOUBLE_POOL + " f " + SQL_WHERE + " f."
            + IFightingDoublePoolItemDatabaseConstants.FIGHTINGCLASS + SQL_IN + SQL_BRACE_OPEN + SQL_FROM
            + TABLE_WEIGHTCLASS + " fc " + SQL_WHERE + "fc." + DELETE_STOP + SQL_IS + SQL_NULL + SQL_OR + "fc."
            + DELETE_STOP + SQL_EQUAL + "'N'" + SQL_BRACE_CLOSE;

    private static String DELETE_UNUSED_FIGHTING_KO_ITEM =
        SQL_DELETE + SQL_FROM + TABLE_FIGHTING_KO + " f " + SQL_WHERE + " f."
            + IFightingKoItemDatabaseConstants.FIGHTINGCLASS + SQL_IN + SQL_BRACE_OPEN + SQL_FROM + TABLE_WEIGHTCLASS
            + " fc " + SQL_WHERE + "fc." + DELETE_STOP + SQL_IS + SQL_NULL + SQL_OR + "fc." + DELETE_STOP + SQL_EQUAL
            + "'N'" + SQL_BRACE_CLOSE;

    private static String DELETE_UNUSED_FIGHTS =
        "DELETE FROM " + TABLE_FIGHT + " f WHERE f." + IFightDatabaseConstants.FIGHTINGCLASS_ID + " in (SELECT " + ID
            + SQL_FROM + TABLE_FIGHTINGCLASS + " fc WHERE " + IWeightclassDatabaseConstants.DELETE_STOP
            + " is null or " + IWeightclassDatabaseConstants.DELETE_STOP + " <> 'Y')";


    private static String IS_FIGHTINGCLASS_A_SIMPLE_POOL_TYPE =
        SQL_FROM + TABLE_FIGHTING_SIMPLE_POOL + " f" + SQL_WHERE + "f."
            + IFightingSimplePoolItemDatabaseConstants.FIGHTINGCLASS_ID + SQL_EQUAL + SQL_QUESTION_MARK;

    private static String IS_FIGHTINGCLASS_A_DOUBLE_POOL_TYPE =
        SQL_FROM + TABLE_FIGHTING_DOUBLE_POOL + " f" + SQL_WHERE + "f."
            + IFightingDoublePoolItemDatabaseConstants.FIGHTINGCLASS_ID + SQL_EQUAL + SQL_QUESTION_MARK;

    private static String IS_FIGHTINGCLASS_A_DOUBLE_KO_TYPE =
        SQL_FROM + TABLE_FIGHTING_KO + " f" + SQL_WHERE + "f." + IFightingKoItemDatabaseConstants.FIGHTINGCLASS_ID
            + SQL_EQUAL + SQL_QUESTION_MARK;

    public boolean existFightingClass( Fightingclass fightingclass )
    {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * get all classes existing classes these are marked "inuse" in the weightclass table
     */
    public List<Fightingclass> getDeleteStoppedFightingclasses()
    {
        try
        {
            Query q = getSession().createQuery( GET_DELETE_STOPPED_FIGHTINGCLASSES );

            // List<Fightingclass> classesList =
            return q.list();

        }
        catch ( Exception e )
        {
            log.error( "can not getExistingFightingclasses", e );
            return null;
        }
    }

    public void removeFightingclass( Fightingclass fightingclass )
    {

    }

    public void removeFightingclasses()
    {

    }

    /**
     * delete of all fighting classes, poolentries and fights, which are not in use
     */
    public void removeUnusedFightingclasses()
        throws Exception
    {

        try
        {
            // delete Fights
            Query q1 = getSession().createQuery( DELETE_UNUSED_FIGHTS );
            q1.executeUpdate();

            // delete poolentries
            Query q2 = getSession().createQuery( DELETE_UNUSED_FIGHTING_SIMPLE_POOL_ITEM );
            q2.executeUpdate();

            Query q4 = getSession().createQuery( DELETE_UNUSED_FIGHTING_DOUBLE_POOL_ITEM );
            q4.executeUpdate();

            Query q5 = getSession().createQuery( DELETE_UNUSED_FIGHTING_KO_ITEM );
            q5.executeUpdate();
            // delete pools
            Query q3 = getSession().createQuery( UPDATE_UNUSED_FIGHTINGCLASS );
            q3.executeUpdate();
        }
        catch ( Exception e )
        {
            log.error( "can not removeUnusedFightingclasses", e );
            throw e;
        }

    }

    private static String UPDATE_UNUSED_FIGHTINGCLASS_PER_FIGHTINGCLASS_ID = SQL_UPDATE + TABLE_WEIGHTCLASS + SQL_SET
        + INUSE + SQL_EQUAL + "'N'" + SQL_WHERE + ID + "=?";

    private static String DELETE_UNUSED_FIGHTING_SIMPLE_POOL_ITEM_PER_FIGHTINGCLASS_ID = SQL_DELETE + SQL_FROM
        + TABLE_FIGHTING_SIMPLE_POOL + " f " + SQL_WHERE + " f."
        + IFightingSimplePoolItemDatabaseConstants.FIGHTINGCLASS_ID + "=?";

    private static String DELETE_UNUSED_FIGHTING_DOUBLE_POOL_ITEM_PER_FIGHTINGCLASS_ID = SQL_DELETE + SQL_FROM
        + TABLE_FIGHTING_DOUBLE_POOL + " f " + SQL_WHERE + " f."
        + IFightingDoublePoolItemDatabaseConstants.FIGHTINGCLASS_ID + "=?";

    private static String DELETE_UNUSED_FIGHTING_KO_ITEM_PER_FIGHTINGCLASS_ID = SQL_DELETE + SQL_FROM
        + TABLE_FIGHTING_KO + " f " + SQL_WHERE + " f." + IFightingKoItemDatabaseConstants.FIGHTINGCLASS_ID + "=?";

    private static String DELETE_UNUSED_FIGHTS_PER_FIGHTINGCLASS_ID = "DELETE FROM " + TABLE_FIGHT + " f WHERE f."
        + IFightDatabaseConstants.FIGHTINGCLASS_ID + "=?";

    /**
     * delete of fighting class, poolentries and fights, which are not in use
     */
    public void removeUnusedFightingclass(long fightingclass_id)
        throws JJWDataLayerException
    {
        try
        {
            // delete Fights
            Query q1 = getSession().createQuery( DELETE_UNUSED_FIGHTS_PER_FIGHTINGCLASS_ID );
            q1.setLong( 0, fightingclass_id );
            q1.executeUpdate();

            // delete poolentries
            Query q2 = getSession().createQuery( DELETE_UNUSED_FIGHTING_SIMPLE_POOL_ITEM_PER_FIGHTINGCLASS_ID );
            q2.setLong( 0, fightingclass_id );
            q2.executeUpdate();

            Query q4 = getSession().createQuery( DELETE_UNUSED_FIGHTING_DOUBLE_POOL_ITEM_PER_FIGHTINGCLASS_ID );
            q4.setLong( 0, fightingclass_id );
            q4.executeUpdate();

            Query q5 = getSession().createQuery( DELETE_UNUSED_FIGHTING_KO_ITEM_PER_FIGHTINGCLASS_ID );
            q5.setLong( 0, fightingclass_id );
            q5.executeUpdate();
            // delete pools
            Query q3 = getSession().createQuery( UPDATE_UNUSED_FIGHTINGCLASS_PER_FIGHTINGCLASS_ID );
            q3.setLong( 0, fightingclass_id );
            q3.executeUpdate();
        }
        catch ( Exception e )
        {
            log.error( "can not removeUnusedFightingclass", e );
            throw new JJWDataLayerException( e );
        }
    }

    /**
     * save fights, poolentries and pool for the fightingclass
     * 
     * @throws JJWDataLayerException
     */
    public void saveFightingclassAfterCreate( Fightingclass fightingclass )
        throws JJWDataLayerException
    {
        if ( fightingclass != null )
        {
            try
            {
                switch ( fightingclass.getFightsystem() )
                {
                    case Fightsystem.SIMPLE_POOL:

                        for ( FightingSimplePoolItem item : ( (FightingSimplePoolClass) fightingclass ).getFighterList() )
                        {

                            if ( item.getFight1() != null )
                            {
                                getHibernateTemplate().save( item.getFight1() );
                            }
                            if ( item.getFight2() != null )
                            {
                                getHibernateTemplate().save( item.getFight2() );
                            }
                            if ( item.getFight3() != null )
                            {
                                getHibernateTemplate().save( item.getFight3() );
                            }
                            if ( item.getFight4() != null )
                            {
                                getHibernateTemplate().save( item.getFight4() );
                            }
                            getHibernateTemplate().save( item );
                        }
                        break;

                    case Fightsystem.DOUBLE_POOL:
                        for ( FightingDoublePoolItem item : ( (FightingDoublePoolClass) fightingclass ).getFighterListPoolA() )
                        {

                            if ( item.getFight1() != null )
                            {
                                getHibernateTemplate().save( item.getFight1() );
                            }
                            if ( item.getFight2() != null )
                            {
                                getHibernateTemplate().save( item.getFight2() );
                            }
                            if ( item.getFight3() != null )
                            {
                                getHibernateTemplate().save( item.getFight3() );
                            }
                            if ( item.getFight4() != null )
                            {
                                getHibernateTemplate().save( item.getFight4() );
                            }
                            getHibernateTemplate().save( item );
                        }
                        for ( FightingDoublePoolItem item : ( (FightingDoublePoolClass) fightingclass ).getFighterListPoolB() )
                        {

                            if ( item.getFight1() != null )
                            {
                                getHibernateTemplate().save( item.getFight1() );
                            }
                            if ( item.getFight2() != null )
                            {
                                getHibernateTemplate().save( item.getFight2() );
                            }
                            if ( item.getFight3() != null )
                            {
                                getHibernateTemplate().save( item.getFight3() );
                            }
                            if ( item.getFight4() != null )
                            {
                                getHibernateTemplate().save( item.getFight4() );
                            }
                            getHibernateTemplate().save( item );
                        }
                        // final fight
                        
                        getHibernateTemplate().save( ( (FightingDoublePoolClass) fightingclass ).getHalfFinalFight1());
                        getHibernateTemplate().save( ( (FightingDoublePoolClass) fightingclass ).getHalfFinalFight2());
                        getHibernateTemplate().save( ( (FightingDoublePoolClass) fightingclass ).getFinalFight() );
                        break;

                    case Fightsystem.DOUBLE_KO:
                        for ( FightingKoItem item : ( (FightingKoClass) fightingclass ).getFighterListPoolA() )
                        {
                            getHibernateTemplate().save( item );
                        }

                        for ( FightingKoItem item : ( (FightingKoClass) fightingclass ).getFighterListPoolB() )
                        {
                            getHibernateTemplate().save( item );
                        }

                        for ( Fight item : ( (FightingKoClass) fightingclass ).getFightListMapPoolA().values() )
                        {
                            if ( item.isWriteFlag() )
                            {
                                getHibernateTemplate().save( item );
                            }
                        }

                        for ( Fight item : ( (FightingKoClass) fightingclass ).getFightListMapPoolB().values() )
                        {
                            if ( item.isWriteFlag() )
                            {
                                getHibernateTemplate().save( item );
                            }
                        }

                        for ( Fight item : ( (FightingKoClass) fightingclass ).getFightListLooserMapPoolA().values() )
                        {
                            getHibernateTemplate().save( item );
                        }

                        for ( Fight item : ( (FightingKoClass) fightingclass ).getFightListLooserMapPoolB().values() )
                        {
                            getHibernateTemplate().save( item );
                        }

                        getHibernateTemplate().save( ( (FightingKoClass) fightingclass ).getFinalFight() );
                        break;

                }
            }
            catch ( Exception e )
            {
                log.error( "can not create fightingclasses", e );
                throw new JJWDataLayerException( e );
            }
        }
    }

    public void saveFightingclass( Fightingclass fightingclass )
        throws JJWDataLayerException
    {
        if ( fightingclass != null )
        {
            // beachte conurrent zugriffe
            try
            {
                getHibernateTemplate().saveOrUpdate( fightingclass );
            }
            catch ( Exception e )
            {
                log.error( "can not update fightingclass", e );
                throw new JJWDataLayerException( e );
            }
        }
    }

    public Fightingclass getFightingclass( Fightingclass fightingclass )
    {
        // TODO Auto-generated method stub
        return null;
    }

    private static String GET_FIGHTINGCLASS_SIMPLEPOOL_POOL =
        SQL_FROM + TABLE_FIGHTING_SIMPLE_POOL + " p " + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
            + IFightingSimplePoolItemDatabaseConstants.FIGHTER + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
            + IFightingSimplePoolItemDatabaseConstants.FIGHTINGCLASS + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
            + IFightingSimplePoolItemDatabaseConstants.FIGHT1 + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
            + IFightingSimplePoolItemDatabaseConstants.FIGHT2 + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
            + IFightingSimplePoolItemDatabaseConstants.FIGHT3 + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
            + IFightingSimplePoolItemDatabaseConstants.FIGHT4 + SQL_WHERE + "p."
            + IFightingSimplePoolItemDatabaseConstants.FIGHTINGCLASS + "=?" + SQL_ORDER_BY + "p."
            + IFightingSimplePoolItemDatabaseConstants.NUMBER_IN_POOL;

    public FightingSimplePoolClass getFightingclassSimplePool( Long fightingclassId )
        throws JJWDataLayerException
    {
        try
        {

            // getHibernateTemplate().find(GET_FIGHTINGCLASS_SIMPLEPOOL_POOL);
            Query q = getSession().createQuery( GET_FIGHTINGCLASS_SIMPLEPOOL_POOL );
            q.setLong( 0, fightingclassId );

            List<FightingSimplePoolItem> retListPool = q.list();
            if ( retListPool == null || retListPool.size() == 0 )
            {
                return null;
            }
            else
            {
                FightingSimplePoolClass fightingclass = new FightingSimplePoolClass();

                for ( FightingSimplePoolItem item : retListPool )
                {
                    fightingclass.addFighterList( item );
                }
                return fightingclass;
            }
        }
        catch ( Exception e )
        {
            log.error( "can not load fightingclass", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String GET_FIGHTINGCLASS_DOUBLEPOOL_POOL =
        SQL_FROM + TABLE_FIGHTING_DOUBLE_POOL + " p " + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
            + IFightingDoublePoolItemDatabaseConstants.FIGHTER + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
            + IFightingDoublePoolItemDatabaseConstants.FIGHTINGCLASS + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
            + IFightingDoublePoolItemDatabaseConstants.FIGHT1 + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
            + IFightingDoublePoolItemDatabaseConstants.FIGHT2 + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
            + IFightingDoublePoolItemDatabaseConstants.FIGHT3 + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
            + IFightingDoublePoolItemDatabaseConstants.FIGHT4 + SQL_WHERE + "p."
            + IFightingDoublePoolItemDatabaseConstants.FIGHTINGCLASS + "=?" + SQL_ORDER_BY + "p."
            + IFightingDoublePoolItemDatabaseConstants.POOL_PART + ",p."
            + IFightingDoublePoolItemDatabaseConstants.NUMBER_IN_POOL;

    private static String GET_FIGHTINGCLASS_DOUBLEPOOL_POOL_FINAL_FIGHT =
        SQL_FROM + TABLE_FIGHT + " f " + SQL_WHERE + "f." + IFightDatabaseConstants.FIGHTINGCLASS_ID + SQL_EQUAL
            + SQL_QUESTION_MARK + SQL_AND + "f." + FLAG + SQL_EQUAL + SQL_QUESTION_MARK;
    
    private static String GET_FIGHTINGCLASS_DOUBLEPOOL_POOL_HALF_FINAL_FIGHT_1 =
                    SQL_FROM + TABLE_FIGHT + " f " + SQL_WHERE + "f." + IFightDatabaseConstants.FIGHTINGCLASS_ID + SQL_EQUAL
                        + SQL_QUESTION_MARK + SQL_AND + "f." + FLAG + SQL_EQUAL + SQL_QUESTION_MARK;
    
    private static String GET_FIGHTINGCLASS_DOUBLEPOOL_POOL_HALF_FINAL_FIGHT_2 =
                    SQL_FROM + TABLE_FIGHT + " f " + SQL_WHERE + "f." + IFightDatabaseConstants.FIGHTINGCLASS_ID + SQL_EQUAL
                        + SQL_QUESTION_MARK + SQL_AND + "f." + FLAG + SQL_EQUAL + SQL_QUESTION_MARK;

    private static String GET_FIGHTINGCLASS_KO =
        SQL_FROM + TABLE_FIGHTING_KO + " p " + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
            + IFightingKoItemDatabaseConstants.FIGHTER + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
            + IFightingKoItemDatabaseConstants.FIGHTINGCLASS + SQL_WHERE + "p."
            + IFightingKoItemDatabaseConstants.FIGHTINGCLASS + "=?" + SQL_ORDER_BY + "p."
            + IFightingKoItemDatabaseConstants.POOL_PART + ",p." + IFightingKoItemDatabaseConstants.NUMBER_IN_POOL;

    private static String GET_FIGHTINGCLASS_KO_FIGHTS =
        SQL_FROM + TABLE_FIGHT + " f " + SQL_WHERE + "f." + IFightDatabaseConstants.FIGHTINGCLASS_ID + SQL_EQUAL
            + SQL_QUESTION_MARK;

    public FightingDoublePoolClass getFightingclassDoublePool( Long fightingclassId )
        throws JJWDataLayerException
    {
        try
        {

            Query q = getSession().createQuery( GET_FIGHTINGCLASS_DOUBLEPOOL_POOL );
            q.setLong( 0, fightingclassId );

            List<FightingDoublePoolItem> retListPool = q.list();
            if ( retListPool == null || retListPool.size() == 0 )
            {
                return null;
            }
            else
            {
                FightingDoublePoolClass fightingclass = new FightingDoublePoolClass();

                for ( FightingDoublePoolItem item : retListPool )
                {
                    if ( IValueConstants.POOL_A.equals( item.getPoolPart() ) )
                    {
                        fightingclass.addFighterListPoolA( item );
                    }
                    if ( IValueConstants.POOL_B.equals( item.getPoolPart() ) )
                    {
                        fightingclass.addFighterListPoolB( item );
                    }
                }
                
                
                
                Query q2 = getSession().createQuery( GET_FIGHTINGCLASS_DOUBLEPOOL_POOL_HALF_FINAL_FIGHT_1 );
                q2.setLong( 0, fightingclassId );
                q2.setString( 1, IValueConstants.HALF_FINAL_FIGHT_1 );
                Fight halfFinalFight1 = (Fight) q2.list().get( 0 );
                if ( halfFinalFight1 != null && halfFinalFight1.getFighterIdRed() > TypeUtil.LONG_DEFAULT )
                {
                    Query q3 = getSession().createQuery( "FROM Fighter f WHERE f.id=?" );
                    q3.setLong( 0, halfFinalFight1.getFighterIdRed() );
                    halfFinalFight1.setFighterRed( (Fighter) q3.uniqueResult() );
                }

                if ( halfFinalFight1 != null && halfFinalFight1.getFighterIdBlue() > TypeUtil.LONG_DEFAULT )
                {
                    Query q3 = getSession().createQuery( "FROM Fighter f WHERE f.id=?" );
                    q3.setLong( 0, halfFinalFight1.getFighterIdBlue() );
                    halfFinalFight1.setFighterBlue( (Fighter) q3.uniqueResult() );
                }
                fightingclass.setHalfFinalFight1(  halfFinalFight1 );

                
                q2 = getSession().createQuery( GET_FIGHTINGCLASS_DOUBLEPOOL_POOL_HALF_FINAL_FIGHT_2 );
                q2.setLong( 0, fightingclassId );
                q2.setString( 1, IValueConstants.HALF_FINAL_FIGHT_2 );
                Fight halfFinalFight2 = (Fight) q2.list().get( 0 );
                if ( halfFinalFight2 != null && halfFinalFight2.getFighterIdRed() > TypeUtil.LONG_DEFAULT )
                {
                    Query q3 = getSession().createQuery( "FROM Fighter f WHERE f.id=?" );
                    q3.setLong( 0, halfFinalFight2.getFighterIdRed() );
                    halfFinalFight2.setFighterRed( (Fighter) q3.uniqueResult() );
                }

                if ( halfFinalFight2 != null && halfFinalFight2.getFighterIdBlue() > TypeUtil.LONG_DEFAULT )
                {
                    Query q3 = getSession().createQuery( "FROM Fighter f WHERE f.id=?" );
                    q3.setLong( 0, halfFinalFight2.getFighterIdBlue() );
                    halfFinalFight2.setFighterBlue( (Fighter) q3.uniqueResult() );
                }
                fightingclass.setHalfFinalFight2(  halfFinalFight2 );


                q2 = getSession().createQuery( GET_FIGHTINGCLASS_DOUBLEPOOL_POOL_FINAL_FIGHT );
                q2.setLong( 0, fightingclassId );
                q2.setString( 1, IValueConstants.FINAL_FIGHT );
                Fight finalFight = (Fight) q2.list().get( 0 );
                if ( finalFight != null && finalFight.getFighterIdRed() > TypeUtil.LONG_DEFAULT )
                {
                    Query q3 = getSession().createQuery( "FROM Fighter f WHERE f.id=?" );
                    q3.setLong( 0, finalFight.getFighterIdRed() );
                    finalFight.setFighterRed( (Fighter) q3.uniqueResult() );
                }

                if ( finalFight != null && finalFight.getFighterIdBlue() > TypeUtil.LONG_DEFAULT )
                {
                    Query q3 = getSession().createQuery( "FROM Fighter f WHERE f.id=?" );
                    q3.setLong( 0, finalFight.getFighterIdBlue() );
                    finalFight.setFighterBlue( (Fighter) q3.uniqueResult() );
                }
                fightingclass.setFinalFight( finalFight );
                return fightingclass;
            }
        }
        catch ( Exception e )
        {
            log.error( "can not load fightingclass", e );
            throw new JJWDataLayerException( e );
        }
    }

    public FightingKoClass getFightingclassKo( Long fightingclassId )
        throws JJWDataLayerException
    {
        try
        {

            Query q = getSession().createQuery( GET_FIGHTINGCLASS_KO );
            q.setLong( 0, fightingclassId );

            List<FightingKoItem> retListPool = q.list();
            if ( retListPool == null || retListPool.size() == 0 )
            {
                return null;
            }
            else
            {
                FightingKoClass fightingclass = new FightingKoClass();
                Map<Long, Fighter> fighterMap = new HashMap<Long, Fighter>();

                for ( FightingKoItem item : retListPool )
                {
                    fighterMap.put( item.getFighter().getId(), item.getFighter() );
                    if ( IValueConstants.POOL_A.equals( item.getPoolPart() ) )
                    {
                        fightingclass.addFighterListPoolA( item );
                    }
                    if ( IValueConstants.POOL_B.equals( item.getPoolPart() ) )
                    {
                        fightingclass.addFighterListPoolB( item );
                    }
                }

                Query q2 = getSession().createQuery( GET_FIGHTINGCLASS_KO_FIGHTS );
                q2.setLong( 0, fightingclassId );

                List<Fight> fightList = q2.list();
                // fightingclass.setFinalFight(finalFight);

                for ( Fight item : fightList )
                {
                    item.setFighterRed( fighterMap.get( item.getFighterIdRed() ) );
                    item.setFighterBlue( fighterMap.get( item.getFighterIdBlue() ) );
                    if ( item.isMainRound() )
                    {
                        // mainround and final fight
                        if ( IValueConstants.POOL_A.equals( item.getPoolPart() ) )
                        {
                            fightingclass.putFightListMapPoolA( Integer.valueOf( item.getFightNumberInPart() ), item );
                        }

                        if ( IValueConstants.POOL_B.equals( item.getPoolPart() ) )
                        {
                            fightingclass.putFightListMapPoolB( Integer.valueOf( item.getFightNumberInPart() ), item );
                        }

                        if ( IValueConstants.FINAL_FIGHT.equals( item.getFlag() ) )
                        {
                            fightingclass.setFinalFight( item );
                        }

                    }
                    else
                    {
                        // loserround fights
                        if ( IValueConstants.POOL_A.equals( item.getPoolPart() ) )
                        {
                            fightingclass.putFightListLoserMapPoolA( Integer.valueOf( item.getFightNumberInPart() ),
                                                                     item );
                            if (item.getFightNumberInPart()==0) fightingclass.setOriginal3rdPlaceFightA( (Fight)item.clone() );
                        }

                        if ( IValueConstants.POOL_B.equals( item.getPoolPart() ) )
                        {
                            fightingclass.putFightListLoserMapPoolB( Integer.valueOf( item.getFightNumberInPart() ),
                                                                     item );
                            if (item.getFightNumberInPart()==0) fightingclass.setOriginal3rdPlaceFightB( (Fight)item.clone() );
                        }
                    }
                }

                return fightingclass;
            }
        }
        catch ( Exception e )
        {
            log.error( "can not load fightingclass", e );
            throw new JJWDataLayerException( e );
        }
    }

    // private static String GET_FIGHTINGCLASS = SQL_FROM + TABLE_FIGHTINGCLASS+ " p " +SQL_WHERE +"p." + ID + "=?";

    public Fightingclass getFightingclass( Long fightingclassId )
        throws JJWDataLayerException
    {
        try
        {
            return (Fightingclass) getHibernateTemplate().get( Fightingclass.class, fightingclassId );

        }
        catch ( Exception e )
        {
            log.error( "can not load fightingclass", e );
            throw new JJWDataLayerException( e );
        }
    }

    /**
     * get all fightingclasses, that are in Use all parameter in the list, which correspond to a column will be use for
     * order by. Beginning with die first element (0) by Integer and than the second ....
     * <p/>
     * e.g. (0,1, ..
     * 
     * @return Fightingclasses
     */
    public List<Fightingclass> getInUseFightingclasses( Map<Integer, SortDTO> sortParameter )
        throws JJWDataLayerException
    {
        try
        {
            Query q = null;
            if ( sortParameter == null || sortParameter.size() == 0 )
            {
                q = getSession().createQuery( GET_INUSE_FIGHTINGCLASSES_WITH_ORDER_BY_DEFAULT );
                q.setLong( 0, TypeUtil.LONG_EMPTY );
                // q.setLong(1, TypeUtil.LONG_EMPTY);
            }
            else
            {
                StringBuffer sb = null;
                SortDTO dto = null;
                int i = 0;
                // go through all sortparameter
                while ( sortParameter.get( i ) != null )
                {
                    dto = sortParameter.get( i );

                    // check if the parameter really exists
                    if ( Fightingclass.PROPERTY_MAP.get( dto.getDatabaseField() ) != null )
                    {
                        if ( sb == null )
                        {
                            sb = new StringBuffer( SQL_ORDER_BY );
                            sb.append( W_DOT ).append( Fightingclass.PROPERTY_MAP.get( dto.getDatabaseField() ) );
                        }
                        else
                        {
                            sb.append( COMMA );
                            sb.append( W_DOT ).append( Fightingclass.PROPERTY_MAP.get( dto.getDatabaseField() ) );
                        }

                        // asc or desc
                        if ( !dto.isAcending() )
                        {
                            sb.append( SQL_DESC );
                        }
                    }

                    i++;
                }
                q = getSession().createQuery( GET_INUSE_FIGHTINGCLASSES + sb.toString() );
            }

            return q.list();

        }
        catch ( Exception e )
        {
            log.error( "can not InuseFightingclasses", e );
            throw new JJWDataLayerException( e );
        }
    }

    public int getFightsystemOfFightingclass( long fightingclassId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = null;
            q = getSession().createQuery( IS_FIGHTINGCLASS_A_SIMPLE_POOL_TYPE );
            q.setLong( 0, fightingclassId );
            if ( !q.list().isEmpty() )
            {
                return Fightsystem.SIMPLE_POOL;
            }
            q = getSession().createQuery( IS_FIGHTINGCLASS_A_DOUBLE_POOL_TYPE );
            q.setLong( 0, fightingclassId );
            if ( !q.list().isEmpty() )
            {
                return Fightsystem.DOUBLE_POOL;
            }

            q = getSession().createQuery( IS_FIGHTINGCLASS_A_DOUBLE_KO_TYPE );
            q.setLong( 0, fightingclassId );
            if ( !q.list().isEmpty() )
            {
                return Fightsystem.DOUBLE_KO;
            }

            return TypeUtil.INT_DEFAULT;
        }
        catch ( Exception e )
        {
            log.error( "can not getFightsystemOfFightingclass", e );
            throw new JJWDataLayerException( e );
        }
    }

    public void setFightingclassResults( Map<Long, Integer> resultMap, Long fightingclassId )
        throws JJWDataLayerException
    {
        try
        {

            Query q = getSession().createQuery( "from Fighter f where fightingclass = ? AND f.ready = '1'" );
            q.setLong( 0, fightingclassId );
            List<Fighter> list = q.list();
            for ( Fighter fighter : list )
            {
                if ( resultMap.containsKey( fighter.getId() ) )
                {
                    fighter.setPlace( resultMap.get( fighter.getId() ) );
                }
            }

            getHibernateTemplate().saveOrUpdateAll( list );

        }
        catch ( Exception e )
        {
            log.error( "can not setFightingclassResults", e );
            throw new JJWDataLayerException( e );
        }

    }

    public void resetFightingclassResults( Long fightingclassId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( "update Fighter set place = 0 where fightingclass = ? " );
            q.setLong( 0, fightingclassId );
            q.executeUpdate();
        }
        catch ( Exception e )
        {
            log.error( "can not setResetFightingclassResults", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String FIGHTING_CERTIFICATION_SQL =
        "from Fighter f where f.fightingclass = ?  and f.place is not null and f.place > 0 and f.place <= ? AND f.ready = '1'"
            + " order by f.place";

    private static String FIGHTING_CERTIFICATION_SQL_2 = "from Fightingclass f left join fetch f.age a where f.id = ? ";

    public FightingCertification getFightingCertification( Long fightingclassId )
        throws JJWDataLayerException
    {
        try
        {
            FightingCertification ret = new FightingCertification();

            ConfigJJW config = (ConfigJJW) getHibernateTemplate().get( ConfigJJW.class, TypeUtil.LONG_1 );
            Query q = getSession().createQuery( FIGHTING_CERTIFICATION_SQL );
            q.setLong( 0, fightingclassId );
            q.setInteger( 1, config.getCertificationPlaces() );
            List<Fighter> fighterList = q.list();

            ret.setFighterList( fighterList );

            Query q1 = getSession().createQuery( FIGHTING_CERTIFICATION_SQL_2 );
            q1.setLong( 0, fightingclassId );
            List<Fightingclass> fightingclassList = q1.list();
            if ( fightingclassList != null && fightingclassList.size() > TypeUtil.INT_DEFAULT )
            {
                ret.setFightingclassCaption( fightingclassList.get( 0 ).getWeightclass() );
                ret.setSex( fightingclassList.get( 0 ).getSex() );
                ret.setAge( fightingclassList.get( 0 ).getAge().getDescription() );
            }

            return ret;
        }
        catch ( Exception e )
        {
            log.error( "can not getFightingCertification", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String SQL_CHANGE_FIGHTER_IN_FIGHTS_1 =
        " From " + TABLE_FIGHT + SQL_WHERE + IFightDatabaseConstants.FIGHTER_ID_RED + "=?";

    private static String SQL_CHANGE_FIGHTER_IN_FIGHTS_2 =
        " From " + TABLE_FIGHT + SQL_WHERE + IFightDatabaseConstants.FIGHTER_ID_BLUE + "=?";

    private static String SQL_CHANGE_FIGHTER_IN_FIGHTS_UPDATE_RED =
 " UPDATE " + SQL_TABLE_FIGHT + SQL_SET
        + IFightDatabaseConstants.FIGHTER_ID_RED + "=? , " + IFightDatabaseConstants.MODIFICATION_DATE + "=? "
        + " WHERE " + ID + " in (";

    private static String SQL_CHANGE_FIGHTER_IN_FIGHTS_UPDATE_BLUE =
 " UPDATE " + SQL_TABLE_FIGHT + SQL_SET
        + IFightDatabaseConstants.FIGHTER_ID_BLUE + "=? , " + IFightDatabaseConstants.MODIFICATION_DATE + "=? "
        + " WHERE " + ID + " in (";

    /**
     * change the fighterIds in the fights
     * 
     * @param fighter1Id
     * @param fighter2Id
     * @throws JJWDataLayerException
     */
    private void changeFighterInFights( long fighter1Id, long fighter2Id )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( SQL_CHANGE_FIGHTER_IN_FIGHTS_1 );
            q.setLong( 0, fighter1Id );
            List<Fight> list1 = q.list();
            StringBuffer inClause1 = new StringBuffer( 32 );

            Query q2 = getSession().createQuery( SQL_CHANGE_FIGHTER_IN_FIGHTS_2 );
            q2.setLong( 0, fighter1Id );
            List<Fight> list2 = q2.list();
            StringBuffer inClause2 = new StringBuffer( 32 );

            for ( Fight fight : list1 )
            {
                if ( inClause1.length() > 1 )
                {
                    inClause1.append( COMMA );
                }
                inClause1.append( fight.getId() );
            }

            for ( Fight fight : list2 )
            {
                if ( inClause2.length() > 1 )
                {
                    inClause2.append( COMMA );
                }
                inClause2.append( fight.getId() );
            }

            // fighter 2 lists
            q = getSession().createQuery( SQL_CHANGE_FIGHTER_IN_FIGHTS_1 );
            q.setLong( 0, fighter2Id );
            list1 = q.list();
            StringBuffer inClause3 = new StringBuffer( 32 );

            q2 = getSession().createQuery( SQL_CHANGE_FIGHTER_IN_FIGHTS_2 );
            q2.setLong( 0, fighter2Id );
            list2 = q2.list();
            StringBuffer inClause4 = new StringBuffer( 32 );

            for ( Fight fight : list1 )
            {
                if ( inClause3.length() > 1 )
                {
                    inClause3.append( COMMA );
                }
                inClause3.append( fight.getId() );
            }

            for ( Fight fight : list2 )
            {
                if ( inClause4.length() > 1 )
                {
                    inClause4.append( COMMA );
                }
                inClause4.append( fight.getId() );
            }

            // update the fights
            // set fighter 2
            if ( inClause1.length() > 0 )
            {
                StringBuffer sb = new StringBuffer( 100 );
                sb.append( SQL_CHANGE_FIGHTER_IN_FIGHTS_UPDATE_RED );
                sb.append( inClause1 );
                sb.append( ")" );

                q = getSession().createSQLQuery( sb.toString() );
                q.setLong( 0, fighter2Id );
                q.setTimestamp( 1, new Timestamp( System.currentTimeMillis() ) );
                q.executeUpdate();
            }

            if ( inClause2.length() > 0 )
            {
                StringBuffer sb = new StringBuffer( 100 );
                sb.append( SQL_CHANGE_FIGHTER_IN_FIGHTS_UPDATE_BLUE );
                sb.append( inClause2 );
                sb.append( ")" );

                q = getSession().createSQLQuery( sb.toString() );
                q.setLong( 0, fighter2Id );
                q.setTimestamp( 1, new Timestamp( System.currentTimeMillis() ) );
                q.executeUpdate();
            }

            // set fighter 1
            if ( inClause3.length() > 0 )
            {
                StringBuffer sb = new StringBuffer( 100 );
                sb.append( SQL_CHANGE_FIGHTER_IN_FIGHTS_UPDATE_RED );
                sb.append( inClause3 );
                sb.append( ")" );

                q = getSession().createSQLQuery( sb.toString() );
                q.setLong( 0, fighter1Id );
                q.setTimestamp( 1, new Timestamp( System.currentTimeMillis() ) );
                q.executeUpdate();
            }

            if ( inClause4.length() > 0 )
            {
                StringBuffer sb = new StringBuffer( 100 );
                sb.append( SQL_CHANGE_FIGHTER_IN_FIGHTS_UPDATE_BLUE );
                sb.append( inClause4 );
                sb.append( ")" );

                q = getSession().createSQLQuery( sb.toString() );
                q.setLong( 0, fighter1Id );
                q.setTimestamp( 1, new Timestamp( System.currentTimeMillis() ) );
                q.executeUpdate();
            }

        }
        catch ( Exception e )
        {
            log.error( "can not changeFighterInFights", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String SQL_CHANGE_FIGHTER_IN_POOL =
        SQL_UPDATE + SQL_TABLE_FIGHTING_SIMPLE_POOL + SQL_SET + IFightingSimplePoolItemDatabaseConstants.FIGHTER_ID
 + " =? , " + IFighterDatabaseConstants.MODIFICATION_DATE
        + "=? " + SQL_WHERE + ID + " =? ";

    public void changeFighterInPool( int fighter1, int fighter2, Fightingclass fightingclass )
        throws JJWDataLayerException
    {
        try
        {
            FightingSimplePoolClass fc = getFightingclassSimplePool( fightingclass.getId() );
            long poolId1 = TypeUtil.LONG_EMPTY;
            long poolId2 = TypeUtil.LONG_EMPTY;
            long fighterId1 = TypeUtil.LONG_EMPTY;
            long fighterId2 = TypeUtil.LONG_EMPTY;

            for ( FightingSimplePoolItem item : fc.getFighterList() )
            {
                if ( item.getNumberInPool() == fighter1 - 1 )
                {
                    fighterId1 = item.getFighterId();
                    poolId1 = item.getId();
                }

                if ( item.getNumberInPool() == fighter2 - 1 )
                {
                    fighterId2 = item.getFighterId();
                    poolId2 = item.getId();
                }
            }

            Query q = getSession().createSQLQuery( SQL_CHANGE_FIGHTER_IN_POOL );
            q.setLong( 0, fighterId1 );
            q.setTimestamp( 1, new Timestamp( System.currentTimeMillis() ) );
            q.setLong( 2, poolId2 );
            q.executeUpdate();

            q = getSession().createSQLQuery( SQL_CHANGE_FIGHTER_IN_POOL );
            q.setLong( 0, fighterId2 );
            q.setTimestamp( 1, new Timestamp( System.currentTimeMillis() ) );
            q.setLong( 2, poolId1 );
            q.executeUpdate();

            changeFighterInFights( fighterId1, fighterId2 );
        }
        catch ( Exception e )
        {
            log.error( "can not changeFighterInPool", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String SQL_CHANGE_FIGHTER_IN_D_POOL =
        SQL_UPDATE + SQL_TABLE_FIGHTING_DOUBLE_POOL + SQL_SET + IFightingDoublePoolItemDatabaseConstants.FIGHTER_ID
 + " =? , " + IFighterDatabaseConstants.MODIFICATION_DATE
        + "=? " + SQL_WHERE + ID + " =? ";

    public void changeFighterInDPool( int fighter1, int fighter2, Fightingclass fightingclass )
        throws JJWDataLayerException
    {
        try
        {
            int[] helpArray = { 0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4 };
            FightingDoublePoolClass fc = getFightingclassDoublePool( fightingclass.getId() );
            long poolId1 = TypeUtil.LONG_EMPTY;
            long poolId2 = TypeUtil.LONG_EMPTY;
            long fighterId1 = TypeUtil.LONG_EMPTY;
            long fighterId2 = TypeUtil.LONG_EMPTY;

            if ( fighter1 % 2 == 1 )
            {
                for ( FightingDoublePoolItem item : fc.getFighterListPoolA() )
                {
                    if ( item.getNumberInPool() == helpArray[fighter1] )
                    {
                        fighterId1 = item.getFighterId();
                        poolId1 = item.getId();
                        break;
                    }
                }
            }
            if ( fighter2 % 2 == 1 )
            {
                for ( FightingDoublePoolItem item : fc.getFighterListPoolA() )
                {
                    if ( item.getNumberInPool() == helpArray[fighter2] )
                    {
                        fighterId2 = item.getFighterId();
                        poolId2 = item.getId();
                        break;
                    }
                }
            }

            if ( fighter1 % 2 == 0 )
            {
                for ( FightingDoublePoolItem item : fc.getFighterListPoolB() )
                {
                    if ( item.getNumberInPool() == helpArray[fighter1] )
                    {
                        fighterId1 = item.getFighterId();
                        poolId1 = item.getId();
                        break;
                    }
                }
            }
            if ( fighter2 % 2 == 0 )
            {
                for ( FightingDoublePoolItem item : fc.getFighterListPoolB() )
                {
                    if ( item.getNumberInPool() == helpArray[fighter2] )
                    {
                        fighterId2 = item.getFighterId();
                        poolId2 = item.getId();
                        break;
                    }
                }
            }

            Query q = getSession().createSQLQuery( SQL_CHANGE_FIGHTER_IN_D_POOL );
            q.setLong( 0, fighterId1 );
            q.setTimestamp( 1, new Timestamp( System.currentTimeMillis() ) );
            q.setLong( 2, poolId2 );
            q.executeUpdate();

            q = getSession().createSQLQuery( SQL_CHANGE_FIGHTER_IN_D_POOL );
            q.setLong( 0, fighterId2 );
            q.setTimestamp( 1, new Timestamp( System.currentTimeMillis() ) );
            q.setLong( 2, poolId1 );
            q.executeUpdate();

            changeFighterInFights( fighterId1, fighterId2 );
        }
        catch ( Exception e )
        {
            log.error( "can not changeFighterInDPool", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String SQL_CHANGE_FIGHTER_IN_KO =
 SQL_UPDATE + SQL_TABLE_FIGHTING_KO + SQL_SET
        + IFightingKoItemDatabaseConstants.FIGHTER_ID + " =? , " + IFighterDatabaseConstants.MODIFICATION_DATE + "=? "
        + SQL_WHERE + ID + " =? ";

    public void changeFighterInKo( int fighter1, int fighter2, Fightingclass fightingclass )
        throws JJWDataLayerException
    {
        FightingKoClass fc = getFightingclassKo( fightingclass.getId() );
        int[] helpArray =
            { 0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12, 13, 13, 14, 14,
                15, 15, 16, 16 };
        long poolId1 = TypeUtil.LONG_EMPTY;
        long poolId2 = TypeUtil.LONG_EMPTY;
        long fighterId1 = TypeUtil.LONG_EMPTY;
        long fighterId2 = TypeUtil.LONG_EMPTY;
        try
        {
            if ( ( fighter1 % 2 ) == 1 )
            {
                for ( FightingKoItem item : fc.getFighterListPoolA() )
                {
                    if ( item.getNumberInPool() == helpArray[fighter1] )
                    {
                        fighterId1 = item.getFighterId();
                        poolId1 = item.getId();
                        break;
                    }
                }
            }

            if ( ( fighter2 % 2 ) == 1 )
            {
                for ( FightingKoItem item : fc.getFighterListPoolA() )
                {
                    if ( item.getNumberInPool() == helpArray[fighter2] )
                    {
                        fighterId2 = item.getFighterId();
                        poolId2 = item.getId();
                        break;
                    }
                }
            }

            if ( ( fighter1 % 2 ) == 0 )
            {
                for ( FightingKoItem item : fc.getFighterListPoolB() )
                {
                    if ( item.getNumberInPool() == helpArray[fighter1] )
                    {
                        fighterId1 = item.getFighterId();
                        poolId1 = item.getId();
                        break;
                    }
                }
            }

            if ( ( fighter2 % 2 ) == 0 )
            {
                for ( FightingKoItem item : fc.getFighterListPoolB() )
                {
                    if ( item.getNumberInPool() == helpArray[fighter2] )
                    {
                        fighterId2 = item.getFighterId();
                        poolId2 = item.getId();
                        break;
                    }
                }
            }
            Query q = getSession().createSQLQuery( SQL_CHANGE_FIGHTER_IN_KO );
            q.setLong( 0, fighterId1 );
            q.setTimestamp( 1, new Timestamp( System.currentTimeMillis() ) );
            q.setLong( 2, poolId2 );
            q.executeUpdate();

            q = getSession().createSQLQuery( SQL_CHANGE_FIGHTER_IN_KO );
            q.setLong( 0, fighterId2 );
            q.setTimestamp( 1, new Timestamp( System.currentTimeMillis() ) );
            q.setLong( 2, poolId1 );
            q.executeUpdate();

            changeFighterInFights( fighterId1, fighterId2 );
        }
        catch ( Exception e )
        {
            log.error( "can not changeFighterInKo", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String GET_NUMBER_OF_FIGHTER_IN_FIGHTINGCLASS =
 SQL_SELECT + SQL_COUNT_1 + SQL_FROM
        + IFighterDatabaseConstants.TABLE_FIGHTER + SQL_WHERE + IFighterDatabaseConstants.FIGHTINGCLASS + "=? AND "
        + IFighterDatabaseConstants.READY + "=1";

    public int getNumberOfFighterInFightingclass( long fightingclassId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createSQLQuery( GET_NUMBER_OF_FIGHTER_IN_FIGHTINGCLASS );
            q.setLong( 0, fightingclassId );
            return ( (BigInteger) q.uniqueResult() ).intValue();
        }
        catch ( Exception e )
        {
            log.error( "can not getNumberOfFighterInFightingclass", e );
            throw new JJWDataLayerException( e );
        }
    }

    /**
     * return : Fightingclass, numberOfFighterInClass, numberOfFights, completedFights,
     * fightsystem,averageFighttimeInClass
     */
    private static String GET_INUSE_FIGHTINGCLASSES_WITH_ORDER_BY_DEFAULT_FOR_TATAMI =
        SQL_SELECT
            + " new Fightingclass(w, "
            + " (select count(distinct f.id) from Fighter f where f.fightingclass = w.id and f.ready=1 ),"
            +
            // number of fighter in class

            " (SELECT COUNT(DISTINCT fl.id) FROM Fight fl WHERE fl.fightingclass = w.id ),"
            +

            " (SELECT COUNT(DISTINCT fl.id) FROM Fight fl WHERE fl.fightingclass = w.id AND fl.winnerId IS NOT NULL AND fl.winnerId >= 0 "
            + "    AND fl.fighterIdBlue not in ( ? )),"
            +

            "(select s.fightsystemType from Fightsystem s where s.minParticipant<="
            + "  (select count(distinct f.id) from Fighter f where f.fightingclass = w.id ) AND s.maxParticipant>="
            + "  (select count(distinct f.id) from Fighter f where f.fightingclass = w.id ) ),"
            +

            " (select min(fl.createDate) from Fight fl where fl.fightingclass = w.id), "
            + " (select min(fl.modificationDate) from Fight fl where fl.fightingclass = w.id and fl.modificationDate > fl.createDate)"
            +

            ")" + SQL_FROM + TABLE_WEIGHTCLASS + " w " + SQL_WHERE + "w." + INUSE + SQL_IS + SQL_NOT + SQL_NULL
            + SQL_AND + "w." + INUSE + SQL_EQUAL + "'Y'" + " AND w." + DELETE_STOP + "='Y' AND w." + COMPLETE + "='N' "
            + SQL_ORDER_BY + "w.age." + ORDER_NUMBER + " , " + "w." + AGE + COMMA + "w." + SEX + COMMA + "w."
            + WEIGHT_LIMIT;

    private static String GET_INUSE_FIGHTINGCLASSES_WITH_USER_ACCESS =
        "SELECT u." + IUserFightingclassDatabaseConstants.FIGHTINGCLASS_ID + " from UserFightingclass u WHERE u."
            + IUserFightingclassDatabaseConstants.USER_ID + "=?";

    /**
     * get all fightingclasses, that are in Use all parameter in the list, which correspond to a column will be use for
     * order by. Beginning with die first element (0) by Integer and than the second ....
     * <p/>
     * e.g. (0,1, ..
     * 
     * @return Fightingclasses
     */
    public List<Fightingclass> getInUseFightingclassesForTatami( Map<Integer, SortDTO> sortParameter, Long accountId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = null;
            if ( sortParameter == null || sortParameter.size() == 0 )
            {
                q = getSession().createQuery( GET_INUSE_FIGHTINGCLASSES_WITH_ORDER_BY_DEFAULT_FOR_TATAMI );
                q.setLong( 0, TypeUtil.LONG_EMPTY );
                // q.setLong(1, TypeUtil.LONG_EMPTY);
            }
            else
            {
                StringBuffer sb = null;
                SortDTO dto = null;
                int i = 0;
                // go through all sortparameter
                while ( sortParameter.get( i ) != null )
                {
                    dto = sortParameter.get( i );

                    // check if the parameter really exists
                    if ( Fightingclass.PROPERTY_MAP.get( dto.getDatabaseField() ) != null )
                    {
                        if ( sb == null )
                        {
                            sb = new StringBuffer( SQL_ORDER_BY );
                            sb.append( W_DOT ).append( Fightingclass.PROPERTY_MAP.get( dto.getDatabaseField() ) );
                        }
                        else
                        {
                            sb.append( COMMA );
                            sb.append( W_DOT ).append( Fightingclass.PROPERTY_MAP.get( dto.getDatabaseField() ) );
                        }

                        // asc or desc
                        if ( !dto.isAcending() )
                        {
                            sb.append( SQL_DESC );
                        }
                    }

                    i++;
                }
                q = getSession().createQuery( GET_INUSE_FIGHTINGCLASSES + sb.toString() );
            }

            List<Fightingclass> fcList = q.list();
            Query q2 = getSession().createQuery( GET_INUSE_FIGHTINGCLASSES_WITH_USER_ACCESS );
            q2.setLong( 0, accountId );
            List<Long> allowedList = q2.list();
            List<Fightingclass> retList = new ArrayList<Fightingclass>();
            for ( Fightingclass fc : fcList )
            {
                for ( Long item : allowedList )
                {
                    if ( fc.getId().equals( item ) )
                    {
                        retList.add( fc );
                        break;
                    }
                }
            }

            return retList;

        }
        catch ( Exception e )
        {
            log.error( "can not InuseFightingclasses", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String SQL_CREATE_AUTOMATIC_FIGHTINGCLASSES_GET_WEIGHTS =
        "SELECT " + IFighterDatabaseConstants.WEIGHT + " FROM " + IFighterDatabaseConstants.TABLE_FIGHTER + " WHERE "
            + IFighterDatabaseConstants.AGE + " =? AND " + IFighterDatabaseConstants.SEX + " =? " + " GROUP BY "
            + IFighterDatabaseConstants.WEIGHT + " ORDER BY " + IFighterDatabaseConstants.WEIGHT;

    public void createAutomaticFightingclasses( Long ageId, Long userId )
        throws JJWDataLayerException
    {
        try
        {
            Age age = (Age) getHibernateTemplate().get( Age.class, ageId );
            doCreateAutomaticFightingclass( userId, age, "1" ); // male
            doCreateAutomaticFightingclass( userId, age, "2" ); // female
        }
        catch ( Exception e )
        {
            log.error( "can not createAutomaticFightingclasses", e );
            throw new JJWDataLayerException( e );
        }
    }

    /**
     * @param ageId
     * @param userId
     * @param age
     * @throws HibernateException
     * @throws DataAccessResourceFailureException
     * @throws IllegalStateException
     */
    private void doCreateAutomaticFightingclass( Long userId, Age age, String sex )
        throws HibernateException, DataAccessResourceFailureException, IllegalStateException
    {
        Query q = getSession().createSQLQuery( SQL_CREATE_AUTOMATIC_FIGHTINGCLASSES_GET_WEIGHTS );
        q.setLong( 0, age.getId() );
        q.setString( 1, sex );
        List<BigDecimal> weightList = q.list();
        double lastStart = 0.0;
        double weight = 0.0;
        double weightDiff = 5.0;
        Fightingclass fc = null;

        for ( BigDecimal item : weightList )
        {
            weight = ( (int) item.doubleValue() ) - 1; // weight = ( ( (int) ( item.doubleValue() * 10 ) - 1 ) / 10.0 );
            if ( ( lastStart + weightDiff ) > weight )
            {
                continue;
            }
            else
            {
                lastStart = weight;
                fc = new Fightingclass();
                fc.setAge( age );
                fc.setCreateDate( new Timestamp( System.currentTimeMillis() ) );
                fc.setCreateId( userId );
                fc.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                fc.setModificationId( userId );
                fc.setSex( sex );
                fc.setStartWeight( weight );
                fc.setWeightclass( "-" + ( weight + weightDiff ) );
                fc.setWeightLimit( weight + weightDiff );
                if ( !existsFightingclass( fc ) )
                {
                    getHibernateTemplate().saveOrUpdate( fc );
                }

            }
        }
    }

    private static String SQL_EXIST_FIGHTINGCLASS =
        " FROM " + IWeightclassDatabaseConstants.TABLE_FIGHTINGCLASS + " f WHERE f."
            + IWeightclassDatabaseConstants.SEX + "=? and f." + IWeightclassDatabaseConstants.START_WEIGHT
            + " =? and f." + IWeightclassDatabaseConstants.WEIGHT_LIMIT + "=? and f."
            + IWeightclassDatabaseConstants.AGE + "=?";

    private boolean existsFightingclass( Fightingclass fc )
        throws HibernateException
    {
        Query q = getSession().createQuery( SQL_EXIST_FIGHTINGCLASS );
        q.setString( 0, fc.getSex() );
        q.setDouble( 1, fc.getStartWeight() );
        q.setDouble( 2, fc.getWeightLimit() );
        q.setLong( 3, fc.getAge().getId() );
        if ( q.uniqueResult() != null )
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    private static String SQL_SORT_FIGHTER_INTO_CLASSES =
        "update " + IFighterDatabaseConstants.SQL_TABLE_FIGHTER + " f set f." + IFighterDatabaseConstants.FIGHTINGCLASS
 + " = " + "if( " + " (Select " + ID + " from "
        + IWeightclassDatabaseConstants.SQL_TABLE_WEIGHTCLASS
        + " w where w."
            + IWeightclassDatabaseConstants.AGE + " = f." + IFighterDatabaseConstants.AGE + " AND w."
            + IWeightclassDatabaseConstants.SEX + " = f." + IFighterDatabaseConstants.SEX + " AND w."
            + IWeightclassDatabaseConstants.START_WEIGHT + " < f." + IFighterDatabaseConstants.WEIGHT + " AND w."
        + IWeightclassDatabaseConstants.WEIGHT_LIMIT
        + " >= f."
        + IFighterDatabaseConstants.WEIGHT
        + ") "
        // if true minValue
        + " is null,"
        + TypeUtil.LONG_EMPTY
        + " , "
        // if false
        + " (Select " + ID + " from " + IWeightclassDatabaseConstants.SQL_TABLE_WEIGHTCLASS + " w where w."
        + IWeightclassDatabaseConstants.AGE + " = f." + IFighterDatabaseConstants.AGE + " AND w."
        + IWeightclassDatabaseConstants.SEX + " = f." + IFighterDatabaseConstants.SEX + " AND w."
        + IWeightclassDatabaseConstants.START_WEIGHT + " < f." + IFighterDatabaseConstants.WEIGHT + " AND w."
        + IWeightclassDatabaseConstants.WEIGHT_LIMIT + " >= f." + IFighterDatabaseConstants.WEIGHT
        + ") "
        // end if
        + " ), "
            + MODIFICATION_ID + " = ? ," + MODIFICATION_DATE + " = now() " + " where "
            + IFighterDatabaseConstants.READY + " = '1' and (f." + IFighterDatabaseConstants.FIGHTINGCLASS
 + " is null or f."
 + IFighterDatabaseConstants.FIGHTINGCLASS + " < 1)  ";

    public void sortFighterIntoClasses( Long userId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createSQLQuery( SQL_SORT_FIGHTER_INTO_CLASSES );
            q.setLong( 0, userId );
            q.executeUpdate();
        }
        catch ( Exception e )
        {
            log.error( "can not sortFighterIntoClasses", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String SQL_AGE_FOR_AUTOMATIC_FIGHTINGTINGCLASS_CREATION =
        "SELECT distinct f." + IFighterDatabaseConstants.AGE + " from " + IFighterDatabaseConstants.TABLE_FIGHTER
            + " f WHERE " + IFighterDatabaseConstants.FIGHTINGCLASS + " is null or f."
            + IFighterDatabaseConstants.FIGHTINGCLASS + " < 1)";

    public List<Age> getAgesForAutomaticFightingclassCreation()
        throws JJWDataLayerException
    {
        try
        {
            return getHibernateTemplate().find( SQL_AGE_FOR_AUTOMATIC_FIGHTINGTINGCLASS_CREATION );
        }
        catch ( Exception e )
        {
            log.error( "can not getAgesForAutmaticFightingclassCreation", e );
            throw new JJWDataLayerException( e );
        }
    }

	
    private static String SQL_GET_CHILD_FIGHTINGTINGCLASSES =
        " from " + IWeightclassDatabaseConstants.TABLE_FIGHTINGCLASS
            + " f WHERE f." + IWeightclassDatabaseConstants.PARENT + " = ?";
	public List<Fightingclass> getChildFightingclasses(Long fightingclassId) throws JJWDataLayerException {
		 try
	        {
			 Query q = getSession().createQuery( SQL_GET_CHILD_FIGHTINGTINGCLASSES );
			 q.setLong(0, fightingclassId);
	         return q.list();            
	        }
	        catch ( Exception e )
	        {
	            log.error( "can not getChildFightingclasses", e );
	            throw new JJWDataLayerException( e );
	        }
	}

    private static String SQL_GET_COMBINABLE_FIGHTINGTINGCLASSES = "SELECT new Fightingclass(f, "
        + " (select count(distinct fi.id) from Fighter fi where fi.fightingclass = f.id )," // number of fighter in
                                                                                            // class
        + " (select count(distinct fi.id) from Fighter fi where fi.fightingclass = f.id and fi.ready=1)"
        // number of fighter in class which are ready
        + " ) " + " from "
        + IWeightclassDatabaseConstants.TABLE_FIGHTINGCLASS
        + " f left join  f."
        + IWeightclassDatabaseConstants.AGE
        + "  a WHERE (f."
        + IWeightclassDatabaseConstants.PARENT
        + " is null or "
        + "f."
        + IWeightclassDatabaseConstants.PARENT
        + " < 0) and f."
        + IWeightclassDatabaseConstants.ID
        + " <> ? "
        + " and (f."
        + IWeightclassDatabaseConstants.DELETE_STOP
        + " is null or f."
        + IWeightclassDatabaseConstants.DELETE_STOP
        + " ='N') "
        // // and f.id not in (select ff.parent from Fightingclass ff)
        + " and f."
        + IWeightclassDatabaseConstants.ID
        + " not in (select ff."
        + IWeightclassDatabaseConstants.PARENT
        + " from " + IWeightclassDatabaseConstants.TABLE_FIGHTINGCLASS + " ff)"
        //
        + " order by a."
        + START_AGE
        + " desc, f."
        + IWeightclassDatabaseConstants.SEX + ", f." + START_WEIGHT;

	public List<Fightingclass> getCombinableFightingclasses(Long fightingclassId) throws JJWDataLayerException {
		 try
	        {
			 Query q = getSession().createQuery( SQL_GET_COMBINABLE_FIGHTINGTINGCLASSES );
            q.setLong( 0, fightingclassId );
	         return q.list();    
	        }
	        catch ( Exception e )
	        {
	            log.error( "can not getCombinaleFightingclasses", e );
	            throw new JJWDataLayerException( e );
	        }
	}

	
    private static String SQL_GET_FIGHTER_FOR_NEW_CLASS = " from " + IFighterDatabaseConstants.TABLE_FIGHTER
        + " f where f." + IFighterDatabaseConstants.FIGHTINGCLASS_ID + "= :fightingclassId";

    /**
     * adds a fightingclass to a parentclass and moves all Fighter of the child class to the parent class and sets the
     * childclass into original class column. Exception: when one of the classes are isDeleteStop
     */
	public void addFightingclassToClass(long parentClass, long childClass)
        throws JJWDataLayerException
    {
        try
        {
            Fightingclass parent = (Fightingclass) getHibernateTemplate().get( Fightingclass.class, parentClass );
            if ( parent.isDeleteStop() )
                throw new Exception( "parent is deleteStop" );

            Fightingclass child = (Fightingclass) getHibernateTemplate().get( Fightingclass.class, childClass );
            if ( child.isDeleteStop() )
                throw new Exception( "child is deleteStop" );

            child.setParentId( parentClass );

            // Query q = getSession().createQuery( SQL_GET_FIGHTER_FOR_NEW_CLASS );
            // q.setLong( 0, childClass );
            List<Fighter> fighterList =
                (List<Fighter>) getHibernateTemplate().findByNamedParam( SQL_GET_FIGHTER_FOR_NEW_CLASS,
                                                                         "fightingclassId", childClass );

            // set fighter to new class
            for ( Fighter fighter : fighterList )
            {
                fighter.setOriginalFightingclassId( fighter.getFightingclassId() );
                fighter.setFightingclassId( parentClass );
            }

        }
        catch ( Exception e )
        {
            log.error( "can not addFightingclassToClass", e );
            throw new JJWDataLayerException( e );
        }
		
	}

	private static String SQL_GET_FIGHTER_FOR_ORIGINAL_CLASS = " from " + IFighterDatabaseConstants.TABLE_FIGHTER
    + " f where f." + IFighterDatabaseConstants.ORIGINAL_FIGHTINGCLASS + "=?";
	/**
	 * removes a fightingclass from its parent and sets for all corresponding fighter the original fighting class
	 * 
	 * Exception: when the classes are isDelteStop
	 */
	public void removeFightingclassFromParent(long parentClass, long childClass)
        throws JJWDataLayerException
    {
        try
        {

            Fightingclass parent = (Fightingclass) getHibernateTemplate().get( Fightingclass.class, parentClass );
            if ( parent.isDeleteStop() )
                throw new Exception( "parent is deleteStop" );

            Fightingclass child = (Fightingclass) getHibernateTemplate().get( Fightingclass.class, childClass );
            if ( child.isDeleteStop() )
                throw new Exception( "child is deleteStop" );

            child.setParentId( null );

            Query q = getSession().createQuery( SQL_GET_FIGHTER_FOR_ORIGINAL_CLASS );
            q.setLong( 0, childClass );
            List<Fighter> fighterList = (List<Fighter>) q.list();

            // set fighter to new class
            for ( Fighter fighter : fighterList )
            {
                fighter.setFightingclassId( fighter.getOriginalFightingclassId() );
                fighter.setOriginalFightingclassId( TypeUtil.LONG_MIN );
            }

        }
        catch ( Exception e )
        {
            log.error( "can not removeFightingclassFromParent", e );
            throw new JJWDataLayerException( e );
        }

    }

    private static String SQL_GET_TIMES_OF_CURRENT_TATAMI_USE =

        "select weightclass_id, if(total is null,0,total) from "
            + " (select user_id,sum(mytime) as total from "
            +

            " (select user_id,weightclass_id,(select count(1)* a.estimatedTime from weightclass w left join age a on w.age=a.id "
            + " left join fight_list f on w.id=f.fightingclass "
            + " where w.id=u.weightclass_id and winnerid<0 and deleteStop='Y' and complete='N' and startTime is  not   null) as mytime "
            +

            " FROM user_weightclass u union"
            +

            " select user_id,duoclass_id,(select count(1)* a.estimatedTimeDuo from  duoclass w left join age a on w.age=a.id"
            + " left join duo_fight_list f on w.id=f.duoclass"
            + " where w.id=u2.duoclass_id and winnerid<0 and deleteStop='Y' and complete='N' and startTime is  not  null) as mytime"
            + " FROM user_duoclass u2 ) tt group by user_id) t left join user_weightclass u on u.user_id=t.user_Id "
            + " left join weightclass w on u.weightclass_id=w.id where weightclass_id is not null and startTime is null";
    
    
    private static String SQL_GET_TIMES_OF_CURRENT_TATAMI_USE_2 = 
    "select * from ( "+

    "SELECT w.id,ufc.user_id,ufc.orderNumber,w.starttime, count(f.id) * a.estimatedTime as addTime, 'F' "+
     "FROM weightclass w left "+
     "join fight_list f on w.id = f.fightingclass left "+
     "join age a on a.id = age left "+
     "join user_weightclass ufc on w.id = ufc.weightclass_id "+

    "where ufc.user_id is not null and f.winnerId <0 "+
    "group by w.id,w.age, ufc.user_id "+

    "union all "+

    "SELECT w.id,ufc.user_id,ufc.orderNumber,w.starttime, count(f.id) * a.estimatedTime as addTime, 'D' "+
     "FROM duoclass w left "+
     "join duo_fight_list f on w.id = f.duoclass left "+
     "join age a on a.id = age left "+
     "join user_duoclass ufc on w.id = ufc.duoclass_id "+

    "where ufc.user_id is not null and f.winnerId <0 "+
    "group by w.id,w.age, ufc.user_id "+

    "union all "+

    "SELECT w.id,ufc.user_id,ufc.orderNumber,w.starttime, count(f.id) * a.estimatedTime as addTime, 'N' "+
     "FROM newaclass w left "+
     "join newa_fight_list f on w.id = f.newaclass left "+
     "join age a on a.id = age left "+
     "join user_newaclass ufc on w.id = ufc.newaclass_id "+

    "where ufc.user_id is not null and f.winnerId <0 "+
    "group by w.id,w.age, ufc.user_id "+
    ") tt "+
    "order by orderNumber";
    
    
    
    
    
    
    
    
    
    

    /**
     * gets the time for each tatami which is need for all started classes Exception: Return: <weightclassId,time(in
     * sec)>
     */
    public  List<EstimatedTimes> getTimesOfCurrentTatamiUse()
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createSQLQuery( SQL_GET_TIMES_OF_CURRENT_TATAMI_USE_2 );

            Map<Long, Long> retMap = new HashMap<Long, Long>();
            List<EstimatedTimes> list = new ArrayList<EstimatedTimes>();
            EstimatedTimes estimatedTimes;
            long scheduledTime=0;
            int lastOrderNumber=0;
            for ( Object item : q.list() )
            {

//                if ( ( (Object[]) item )[3] == null )
//                {// class not started

                    estimatedTimes = new EstimatedTimes();
                    
                    estimatedTimes.setWeightclassId( ( (BigInteger) ( (Object[]) item )[0] ).longValue()); // weightclassId
                    estimatedTimes.setUserId( ( (BigInteger) ( (Object[]) item )[1] ).longValue()); // userId
                    estimatedTimes.setOrderNumber ( ( (Integer) ( (Object[]) item )[2] ).intValue()); // orderNumber^
                    if(null!= ((Object[]) item )[3]) estimatedTimes.setStartTime(( (Timestamp) ( (Object[]) item )[3] ).getTime()); //starttime
                    estimatedTimes.setAddTime ( ( (BigInteger) ( (Object[]) item )[4] ).longValue()); // addTime
                    estimatedTimes.setDiscepline ( (String) ( (Object[]) item )[5] );
                    
//                    if ( lastOrderNumber == estimatedTimes.orderNumber )
//                    {
//                        if(list.isEmpty()) estimatedTimes.startTime = scheduledTime;
//                        else estimatedTimes.startTime= list.get( list.size()-1 ).startTime;                                                
//                    }
//                    else
//                    {
//                        estimatedTimes.startTime = scheduledTime;
//                    }
//                    
//                    if(    "F".equals( (String) ( (Object[]) item )[5] )     )
                    list.add( estimatedTimes );
//                }

//                scheduledTime += ( (BigInteger) ( (Object[]) item )[4] ).longValue();
//
//                lastOrderNumber = ( (Integer) ( (Object[]) item )[2] ).intValue();

                // retMap.put( ( (BigInteger) ( (Object[]) item )[0] ).longValue(),
                // ( (BigDecimal) ( (Object[]) item )[1] ).longValue() );
            }
            
//            for (EstimatedTimes item: list)
//            {
//                retMap.put( item.weightclassId, item.startTime );
//            }
            return list;
        }
        catch ( Exception e )
        {
            log.error( "can not getTimesOfCurrentTatamiUse", e );
            throw new JJWDataLayerException( e );
        }
    }    
}