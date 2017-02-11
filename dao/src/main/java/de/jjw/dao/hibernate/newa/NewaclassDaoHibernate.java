/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaclassDaoHibernate.java
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

package de.jjw.dao.hibernate.newa;

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

import de.jjw.dao.hibernate.BaseDaoHibernate;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.dao.hibernate.admin.IAgeDatabaseConstants;
import de.jjw.dao.newa.NewaclassDao;
import de.jjw.model.admin.Age;
import de.jjw.model.admin.ConfigJJW;
import de.jjw.model.admin.Fightsystem;
import de.jjw.model.newa.NewaCertification;
import de.jjw.model.newa.NewaDoublePoolClass;
import de.jjw.model.newa.NewaDoublePoolItem;
import de.jjw.model.newa.NewaFight;
import de.jjw.model.newa.NewaFighter;
import de.jjw.model.newa.NewaKoClass;
import de.jjw.model.newa.NewaKoItem;
import de.jjw.model.newa.NewaSimplePoolClass;
import de.jjw.model.newa.NewaSimplePoolItem;
import de.jjw.model.newa.Newaclass;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;
import de.jjw.util.dto.SortDTO;

public class NewaclassDaoHibernate
    extends BaseDaoHibernate
    implements INewaWeightclassDatabaseConstants, INewaSimplePoolItemDatabaseConstants,
    INewaDoublePoolItemDatabaseConstants, NewaclassDao, INewaFightDatabaseConstants, INewaKoItemDatabaseConstants,
    IAgeDatabaseConstants
{

    private static final String COMMA = ",";

    private static String GET_DELETE_STOPPED_NEWACLASSES =
 SQL_FROM + TABLE_NEWACLASS + " w " + SQL_WHERE + "w."
        + DELETE_STOP + SQL_IS + SQL_NOT + SQL_NULL + SQL_AND
            + "w." + DELETE_STOP + SQL_EQUAL + "'Y'";

    private static String GET_INUSE_NEWACLASSES =
 SQL_FROM + TABLE_NEWACLASS + " w " + SQL_WHERE + "w." + INUSE
        + SQL_IS + SQL_NOT + SQL_NULL + SQL_AND + "w."
            + INUSE + SQL_EQUAL + "'Y'" + SQL_ORDER_BY + "w." + AGE + COMMA + "w." + SEX + COMMA + "w." + WEIGHT_LIMIT;



    /**
     * return : Newaclass, numberOfFighterInClass, numberOfFights, completedFights, fightsystem,averageFighttimeInClass
     */
    private static String GET_INUSE_NEWACLASSES_WITH_ORDER_BY_DEFAULT =
        SQL_SELECT
            + " new Newaclass(w, "
            + " (select count(distinct f.id) from NewaFighter f where f.newaclass = w.id and f.ready=1 ),"
            +


            " (SELECT COUNT(DISTINCT fl.id) FROM NewaFight fl WHERE fl.newaclass = w.id ),"
            +

            " (SELECT COUNT(DISTINCT fl.id) FROM NewaFight fl WHERE fl.newaclass = w.id AND fl.winnerId IS NOT NULL AND fl.winnerId >= 0 "
            + "    AND fl.fighterIdBlue not in ( ? )),"
            +

            "(select s.fightsystemType from Fightsystem s where s.minParticipant<="
            + "  (select count(distinct f.id) from NewaFighter f where f.newaclass = w.id and f.ready =1) AND s.maxParticipant>="
            + "  (select count(distinct f.id) from NewaFighter f where f.newaclass = w.id and f.ready =1) ),"
            +

            " (select min(fl.createDate) from NewaFight fl where fl.newaclass = w.id), "
            + " (select min(fl.modificationDate) from NewaFight fl where fl.newaclass = w.id and fl.modificationDate > fl.createDate)"
            +

            ")"
            +
            // " (select avg(fl2.overallFightTime)  from Fight fl2 where fl2.newaclass = w.id and fl2.winnerId is not null and fl2.winnerId < 0))"
            // +
            SQL_FROM + TABLE_NEWACLASS
            + " w "
            +
            // " LEFT JOIN FETCH w." + INewaFighterDatabaseConstants.AGE + " a " +
            SQL_WHERE + "w." + INUSE + SQL_IS + SQL_NOT + SQL_NULL + SQL_AND + "w." + INUSE + SQL_EQUAL + "'Y'"
            + SQL_ORDER_BY + "w.age." + ORDER_NUMBER + " , " + "w." + AGE + COMMA + "w." + SEX + COMMA + "w."
            + WEIGHT_LIMIT;

    private static String W_DOT = " w.";



    private static String UPDATE_UNUSED_NEWACLASS =
 SQL_UPDATE + TABLE_NEWACLASS + SQL_SET + INUSE + SQL_EQUAL
        + "'N'" + SQL_WHERE + DELETE_STOP + SQL_IS
            + SQL_NULL + SQL_OR + DELETE_STOP + SQL_EQUAL + "'N'";

    private static String DELETE_UNUSED_FIGHTING_SIMPLE_POOL_ITEM =
 SQL_DELETE + SQL_FROM + TABLE_NEWA_SIMPLE_POOL
        + " f " + SQL_WHERE + " f." + INewaSimplePoolItemDatabaseConstants.NEWACLASS + SQL_IN + SQL_BRACE_OPEN
        + SQL_FROM
 + TABLE_NEWACLASS + " fc " + SQL_WHERE + "fc." + DELETE_STOP + SQL_IS + SQL_NULL + SQL_OR + "fc."
            + DELETE_STOP + SQL_EQUAL + "'N'" + SQL_BRACE_CLOSE;

    private static String DELETE_UNUSED_FIGHTING_DOUBLE_POOL_ITEM =
 SQL_DELETE + SQL_FROM + TABLE_NEWA_DOUBLE_POOL
        + " f " + SQL_WHERE + " f."
 + INewaDoublePoolItemDatabaseConstants.NEWACLASS + SQL_IN + SQL_BRACE_OPEN
        + SQL_FROM
 + TABLE_NEWACLASS + " fc " + SQL_WHERE + "fc." + DELETE_STOP + SQL_IS + SQL_NULL + SQL_OR + "fc."
            + DELETE_STOP + SQL_EQUAL + "'N'" + SQL_BRACE_CLOSE;

    private static String DELETE_UNUSED_FIGHTING_KO_ITEM =
 SQL_DELETE + SQL_FROM + TABLE_NEWA_KO + " f " + SQL_WHERE
        + " f." + INewaKoItemDatabaseConstants.NEWACLASS + SQL_IN + SQL_BRACE_OPEN + SQL_FROM
        + TABLE_NEWACLASS
            + " fc " + SQL_WHERE + "fc." + DELETE_STOP + SQL_IS + SQL_NULL + SQL_OR + "fc." + DELETE_STOP + SQL_EQUAL
            + "'N'" + SQL_BRACE_CLOSE;

    private static String DELETE_UNUSED_FIGHTS =
 "DELETE FROM " + TABLE_FIGHT + " f WHERE f."
        + INewaFightDatabaseConstants.NEWACLASS_ID + " in (SELECT " + ID + SQL_FROM + TABLE_NEWACLASS
        + " fc WHERE " + INewaWeightclassDatabaseConstants.DELETE_STOP + " is null or "
        + INewaWeightclassDatabaseConstants.DELETE_STOP + " <> 'Y')";


    private static String IS_NEWACLASS_A_SIMPLE_POOL_TYPE =
 SQL_FROM + TABLE_NEWA_SIMPLE_POOL + " f" + SQL_WHERE
        + "f." + INewaSimplePoolItemDatabaseConstants.NEWACLASS_ID + SQL_EQUAL + SQL_QUESTION_MARK;

    private static String IS_NEWACLASS_A_DOUBLE_POOL_TYPE =
 SQL_FROM + TABLE_NEWA_DOUBLE_POOL + " f" + SQL_WHERE + "f."
 + INewaDoublePoolItemDatabaseConstants.NEWACLASS_ID + SQL_EQUAL + SQL_QUESTION_MARK;

    private static String IS_NEWACLASS_A_DOUBLE_KO_TYPE =
 SQL_FROM + TABLE_NEWA_KO + " f" + SQL_WHERE + "f."
        + INewaKoItemDatabaseConstants.NEWACLASS_ID
            + SQL_EQUAL + SQL_QUESTION_MARK;

    public boolean existFightingClass( Newaclass fightingclass )
    {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * get all classes existing classes these are marked "inuse" in the weightclass table
     */
    public List<Newaclass> getDeleteStoppedNewaclasses()
    {
        try
        {
            Query q = getSession().createQuery( GET_DELETE_STOPPED_NEWACLASSES );

            // List<Newaclass> classesList =
            return q.list();

        }
        catch ( Exception e )
        {
            log.error( "can not getExistingNewaclasses", e );
            return null;
        }
    }

    public void removeNewaclass( Newaclass fightingclass )
    {

    }

    public void removeNewaclasses()
    {

    }

    /**
     * delete of all fighting classes, poolentries and fights, which are not in use
     */
    public void removeUnusedNewaclasses()
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
            Query q3 = getSession().createQuery( UPDATE_UNUSED_NEWACLASS );
            q3.executeUpdate();
        }
        catch ( Exception e )
        {
            log.error( "can not removeUnusedNewaclasses", e );
            throw e;
        }

    }

    private static String UPDATE_UNUSED_NEWACLASS_PER_NEWACLASS_ID = SQL_UPDATE + TABLE_NEWACLASS + SQL_SET
        + INUSE + SQL_EQUAL + "'N'" + SQL_WHERE + ID + "=?";

    private static String DELETE_UNUSED_NEWA_SIMPLE_POOL_ITEM_PER_NEWACLASS_ID = SQL_DELETE + SQL_FROM
        + TABLE_NEWA_SIMPLE_POOL + " f " + SQL_WHERE + " f." + INewaSimplePoolItemDatabaseConstants.NEWACLASS_ID + "=?";

    private static String DELETE_UNUSED_NEWA_DOUBLE_POOL_ITEM_PER_NEWACLASS_ID = SQL_DELETE + SQL_FROM
        + TABLE_NEWA_DOUBLE_POOL + " f " + SQL_WHERE + " f."
 + INewaDoublePoolItemDatabaseConstants.NEWACLASS_ID
        + "=?";

    private static String DELETE_UNUSED_NEWA_KO_ITEM_PER_NEWACLASS_ID = SQL_DELETE + SQL_FROM
 + TABLE_NEWA_KO
        + " f " + SQL_WHERE + " f." + INewaKoItemDatabaseConstants.NEWACLASS_ID + "=?";

    private static String DELETE_UNUSED_FIGHTS_PER_NEWACLASS_ID = "DELETE FROM " + TABLE_FIGHT + " f WHERE f."
        + INewaFightDatabaseConstants.NEWACLASS_ID + "=?";

    /**
     * delete of fighting class, poolentries and fights, which are not in use
     */
    public void removeUnusedNewaclass( long newaclass_id )
        throws JJWDataLayerException
    {
        try
        {
            // delete Fights
            Query q1 = getSession().createQuery( DELETE_UNUSED_FIGHTS_PER_NEWACLASS_ID );
            q1.setLong( 0, newaclass_id );
            q1.executeUpdate();

            // delete poolentries
            Query q2 = getSession().createQuery( DELETE_UNUSED_NEWA_SIMPLE_POOL_ITEM_PER_NEWACLASS_ID );
            q2.setLong( 0, newaclass_id );
            q2.executeUpdate();

            Query q4 = getSession().createQuery( DELETE_UNUSED_NEWA_DOUBLE_POOL_ITEM_PER_NEWACLASS_ID );
            q4.setLong( 0, newaclass_id );
            q4.executeUpdate();

            Query q5 = getSession().createQuery( DELETE_UNUSED_NEWA_KO_ITEM_PER_NEWACLASS_ID );
            q5.setLong( 0, newaclass_id );
            q5.executeUpdate();
            // delete pools
            Query q3 = getSession().createQuery( UPDATE_UNUSED_NEWACLASS_PER_NEWACLASS_ID );
            q3.setLong( 0, newaclass_id );
            q3.executeUpdate();
        }
        catch ( Exception e )
        {
            log.error( "can not removeUnusedNewaclass", e );
            throw new JJWDataLayerException( e );
        }
    }

    /**
     * save fights, poolentries and pool for the fightingclass
     * 
     * @throws JJWDataLayerException
     */
    public void saveNewaclassAfterCreate( Newaclass newaclass )
        throws JJWDataLayerException
    {
        if ( newaclass != null )
        {
            try
            {
                switch ( newaclass.getFightsystem() )
                {
                    case Fightsystem.SIMPLE_POOL:

                        for ( NewaSimplePoolItem item : ( (NewaSimplePoolClass) newaclass ).getFighterList() )
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
                        for ( NewaDoublePoolItem item : ( (NewaDoublePoolClass) newaclass ).getFighterListPoolA() )
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
                        for ( NewaDoublePoolItem item : ( (NewaDoublePoolClass) newaclass ).getFighterListPoolB() )
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
                        
                        getHibernateTemplate().save( ( (NewaDoublePoolClass) newaclass ).getHalfFinalFight1());
                        getHibernateTemplate().save( ( (NewaDoublePoolClass) newaclass ).getHalfFinalFight2());

                        getHibernateTemplate().save( ( (NewaDoublePoolClass) newaclass ).getFinalFight() );
                        break;

                    case Fightsystem.DOUBLE_KO:
                        for ( NewaKoItem item : ( (NewaKoClass) newaclass ).getFighterListPoolA() )
                        {
                            getHibernateTemplate().save( item );
                        }

                        for ( NewaKoItem item : ( (NewaKoClass) newaclass ).getFighterListPoolB() )
                        {
                            getHibernateTemplate().save( item );
                        }

                        for ( NewaFight item : ( (NewaKoClass) newaclass ).getFightListMapPoolA().values() )
                        {
                            if ( item.isWriteFlag() )
                            {
                                getHibernateTemplate().save( item );
                            }
                        }

                        for ( NewaFight item : ( (NewaKoClass) newaclass ).getFightListMapPoolB().values() )
                        {
                            if ( item.isWriteFlag() )
                            {
                                getHibernateTemplate().save( item );
                            }
                        }

                        for ( NewaFight item : ( (NewaKoClass) newaclass ).getFightListLooserMapPoolA().values() )
                        {
                            getHibernateTemplate().save( item );
                        }

                        for ( NewaFight item : ( (NewaKoClass) newaclass ).getFightListLooserMapPoolB().values() )
                        {
                            getHibernateTemplate().save( item );
                        }

                        getHibernateTemplate().save( ( (NewaKoClass) newaclass ).getFinalFight() );
                        break;

                }
            }
            catch ( Exception e )
            {
                log.error( "can not create newaclasses", e );
                throw new JJWDataLayerException( e );
            }
        }
    }

    public void saveNewaclass( Newaclass newaclass )
        throws JJWDataLayerException
    {
        if ( newaclass != null )
        {
            // beachte conurrent zugriffe
            try
            {
                getHibernateTemplate().saveOrUpdate( newaclass );
            }
            catch ( Exception e )
            {
                log.error( "can not update newaclass", e );
                throw new JJWDataLayerException( e );
            }
        }
    }

    public Newaclass getNewaclass( Newaclass newaclass )
    {
        // TODO Auto-generated method stub
        return null;
    }

    private static String GET_NEWACLASS_SIMPLEPOOL_POOL =
 SQL_FROM + TABLE_NEWA_SIMPLE_POOL + " p " + SQL_LEFT
        + SQL_JOIN + SQL_FETCH + "p." + INewaSimplePoolItemDatabaseConstants.FIGHTER + SQL_LEFT + SQL_JOIN + SQL_FETCH
        + "p." + INewaSimplePoolItemDatabaseConstants.NEWACLASS + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
        + INewaSimplePoolItemDatabaseConstants.FIGHT1 + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
        + INewaSimplePoolItemDatabaseConstants.FIGHT2 + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
        + INewaSimplePoolItemDatabaseConstants.FIGHT3 + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
        + INewaSimplePoolItemDatabaseConstants.FIGHT4 + SQL_WHERE + "p."
        + INewaSimplePoolItemDatabaseConstants.NEWACLASS + "=?" + SQL_ORDER_BY + "p."
        + INewaSimplePoolItemDatabaseConstants.NUMBER_IN_POOL;

    public NewaSimplePoolClass getNewaclassSimplePool( Long newaclassId )
        throws JJWDataLayerException
    {
        try
        {

            // getHibernateTemplate().find(GET_FIGHTINGCLASS_SIMPLEPOOL_POOL);
            Query q = getSession().createQuery( GET_NEWACLASS_SIMPLEPOOL_POOL );
            q.setLong( 0, newaclassId );

            List<NewaSimplePoolItem> retListPool = q.list();
            if ( retListPool == null || retListPool.size() == 0 )
            {
                return null;
            }
            else
            {
                NewaSimplePoolClass newaclass = new NewaSimplePoolClass();

                for ( NewaSimplePoolItem item : retListPool )
                {
                    newaclass.addFighterList( item );
                }
                return newaclass;
            }
        }
        catch ( Exception e )
        {
            log.error( "can not load newaclass", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String GET_NEWACLASS_DOUBLEPOOL_POOL = SQL_FROM + TABLE_NEWA_DOUBLE_POOL + " p " + SQL_LEFT
        + SQL_JOIN + SQL_FETCH + "p."
 + INewaDoublePoolItemDatabaseConstants.FIGHTER + SQL_LEFT + SQL_JOIN + SQL_FETCH
        + "p." + INewaDoublePoolItemDatabaseConstants.NEWACLASS + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
        + INewaDoublePoolItemDatabaseConstants.FIGHT1 + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
        + INewaDoublePoolItemDatabaseConstants.FIGHT2 + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
        + INewaDoublePoolItemDatabaseConstants.FIGHT3 + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
        + INewaDoublePoolItemDatabaseConstants.FIGHT4 + SQL_WHERE + "p."
        + INewaDoublePoolItemDatabaseConstants.NEWACLASS + "=?" + SQL_ORDER_BY + "p."
        + INewaDoublePoolItemDatabaseConstants.POOL_PART + ",p." + INewaDoublePoolItemDatabaseConstants.NUMBER_IN_POOL;

    private static String GET_NEWACLASS_DOUBLEPOOL_POOL_FINAL_FIGHT =
 SQL_FROM + TABLE_FIGHT + " f " + SQL_WHERE
        + "f." + INewaFightDatabaseConstants.NEWACLASS_ID + SQL_EQUAL
            + SQL_QUESTION_MARK + SQL_AND + "f." + FLAG + SQL_EQUAL + SQL_QUESTION_MARK;
    
    
    private static String GET_FIGHTINGCLASS_DOUBLEPOOL_POOL_HALF_FINAL_FIGHT_1 =
                    SQL_FROM + TABLE_FIGHT + " f " + SQL_WHERE + "f." + INewaFightDatabaseConstants.NEWACLASS_ID + SQL_EQUAL
                        + SQL_QUESTION_MARK + SQL_AND + "f." + FLAG + SQL_EQUAL + SQL_QUESTION_MARK;
    
    private static String GET_FIGHTINGCLASS_DOUBLEPOOL_POOL_HALF_FINAL_FIGHT_2 =
                    SQL_FROM + TABLE_FIGHT + " f " + SQL_WHERE + "f." + INewaFightDatabaseConstants.NEWACLASS_ID + SQL_EQUAL
                        + SQL_QUESTION_MARK + SQL_AND + "f." + FLAG + SQL_EQUAL + SQL_QUESTION_MARK;


    private static String GET_NEWACLASS_KO =
 SQL_FROM + TABLE_NEWA_KO + " p " + SQL_LEFT + SQL_JOIN + SQL_FETCH
        + "p." + INewaKoItemDatabaseConstants.FIGHTER + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
        + INewaKoItemDatabaseConstants.NEWACLASS + SQL_WHERE + "p." + INewaKoItemDatabaseConstants.NEWACLASS + "=?"
        + SQL_ORDER_BY + "p." + INewaKoItemDatabaseConstants.POOL_PART + ",p."
        + INewaKoItemDatabaseConstants.NUMBER_IN_POOL;

    private static String GET_NEWACLASS_KO_FIGHTS =
 SQL_FROM + TABLE_FIGHT + " f " + SQL_WHERE + "f."
        + INewaFightDatabaseConstants.NEWACLASS_ID + SQL_EQUAL
            + SQL_QUESTION_MARK;

    public NewaDoublePoolClass getNewaclassDoublePool( Long newaclassId )
        throws JJWDataLayerException
    {
        try
        {

            Query q = getSession().createQuery( GET_NEWACLASS_DOUBLEPOOL_POOL );
            q.setLong( 0, newaclassId );

            List<NewaDoublePoolItem> retListPool = q.list();
            if ( retListPool == null || retListPool.size() == 0 )
            {
                return null;
            }
            else
            {
                NewaDoublePoolClass newaclass = new NewaDoublePoolClass();

                for ( NewaDoublePoolItem item : retListPool )
                {
                    if ( IValueConstants.POOL_A.equals( item.getPoolPart() ) )
                    {
                        newaclass.addFighterListPoolA( item );
                    }
                    if ( IValueConstants.POOL_B.equals( item.getPoolPart() ) )
                    {
                        newaclass.addFighterListPoolB( item );
                    }
                }

                Query q2 = getSession().createQuery( GET_FIGHTINGCLASS_DOUBLEPOOL_POOL_HALF_FINAL_FIGHT_1 );
                q2.setLong( 0, newaclassId );
                q2.setString( 1, IValueConstants.HALF_FINAL_FIGHT_1 );
                NewaFight halfFinalFight1 = (NewaFight) q2.list().get( 0 );
                if ( halfFinalFight1 != null && halfFinalFight1.getFighterIdRed() > TypeUtil.LONG_DEFAULT )
                {
                    Query q3 = getSession().createQuery( "FROM NewaFighter f WHERE f.id=?" );
                    q3.setLong( 0, halfFinalFight1.getFighterIdRed() );
                    halfFinalFight1.setFighterRed( (NewaFighter) q3.uniqueResult() );
                }

                if ( halfFinalFight1 != null && halfFinalFight1.getFighterIdBlue() > TypeUtil.LONG_DEFAULT )
                {
                    Query q3 = getSession().createQuery( "FROM NewaFighter f WHERE f.id=?" );
                    q3.setLong( 0, halfFinalFight1.getFighterIdBlue() );
                    halfFinalFight1.setFighterBlue( (NewaFighter) q3.uniqueResult() );
                }
                newaclass.setHalfFinalFight1(  halfFinalFight1 );

                
                q2 = getSession().createQuery( GET_FIGHTINGCLASS_DOUBLEPOOL_POOL_HALF_FINAL_FIGHT_2 );
                q2.setLong( 0, newaclassId );
                q2.setString( 1, IValueConstants.HALF_FINAL_FIGHT_2 );
                NewaFight halfFinalFight2 = (NewaFight) q2.list().get( 0 );
                if ( halfFinalFight2 != null && halfFinalFight2.getFighterIdRed() > TypeUtil.LONG_DEFAULT )
                {
                    Query q3 = getSession().createQuery( "FROM NewaFighter f WHERE f.id=?" );
                    q3.setLong( 0, halfFinalFight2.getFighterIdRed() );
                    halfFinalFight2.setFighterRed( (NewaFighter) q3.uniqueResult() );
                }

                if ( halfFinalFight2 != null && halfFinalFight2.getFighterIdBlue() > TypeUtil.LONG_DEFAULT )
                {
                    Query q3 = getSession().createQuery( "FROM NewaFighter f WHERE f.id=?" );
                    q3.setLong( 0, halfFinalFight2.getFighterIdBlue() );
                    halfFinalFight2.setFighterBlue( (NewaFighter) q3.uniqueResult() );
                }
                newaclass.setHalfFinalFight2(  halfFinalFight2 );


                q2 = getSession().createQuery( GET_NEWACLASS_DOUBLEPOOL_POOL_FINAL_FIGHT );
                q2.setLong( 0, newaclassId );
                q2.setString( 1, IValueConstants.FINAL_FIGHT );
                NewaFight finalFight = (NewaFight) q2.list().get( 0 );
                if ( finalFight != null && finalFight.getFighterIdRed() > TypeUtil.LONG_DEFAULT )
                {
                    Query q3 = getSession().createQuery( "FROM NewaFighter f WHERE f.id=?" );
                    q3.setLong( 0, finalFight.getFighterIdRed() );
                    finalFight.setFighterRed( (NewaFighter) q3.uniqueResult() );
                }

                if ( finalFight != null && finalFight.getFighterIdBlue() > TypeUtil.LONG_DEFAULT )
                {
                    Query q3 = getSession().createQuery( "FROM NewaFighter f WHERE f.id=?" );
                    q3.setLong( 0, finalFight.getFighterIdBlue() );
                    finalFight.setFighterBlue( (NewaFighter) q3.uniqueResult() );
                }
                newaclass.setFinalFight( finalFight );
                return newaclass;
            }
        }
        catch ( Exception e )
        {
            log.error( "can not load newaclass", e );
            throw new JJWDataLayerException( e );
        }
    }

    public NewaKoClass getNewaclassKo( Long newaclassId )
        throws JJWDataLayerException
    {
        try
        {

            Query q = getSession().createQuery( GET_NEWACLASS_KO );
            q.setLong( 0, newaclassId );

            List<NewaKoItem> retListPool = q.list();
            if ( retListPool == null || retListPool.size() == 0 )
            {
                return null;
            }
            else
            {
                NewaKoClass newaclass = new NewaKoClass();
                Map<Long, NewaFighter> fighterMap = new HashMap<Long, NewaFighter>();

                for ( NewaKoItem item : retListPool )
                {
                    fighterMap.put( item.getFighter().getId(), item.getFighter() );
                    if ( IValueConstants.POOL_A.equals( item.getPoolPart() ) )
                    {
                        newaclass.addFighterListPoolA( item );
                    }
                    if ( IValueConstants.POOL_B.equals( item.getPoolPart() ) )
                    {
                        newaclass.addFighterListPoolB( item );
                    }
                }

                Query q2 = getSession().createQuery( GET_NEWACLASS_KO_FIGHTS );
                q2.setLong( 0, newaclassId );

                List<NewaFight> fightList = q2.list();
                // fightingclass.setFinalFight(finalFight);

                for ( NewaFight item : fightList )
                {
                    item.setFighterRed( fighterMap.get( item.getFighterIdRed() ) );
                    item.setFighterBlue( fighterMap.get( item.getFighterIdBlue() ) );
                    if ( item.isMainRound() )
                    {
                        // mainround and final fight
                        if ( IValueConstants.POOL_A.equals( item.getPoolPart() ) )
                        {
                            newaclass.putFightListMapPoolA( Integer.valueOf( item.getFightNumberInPart() ), item );
                        }

                        if ( IValueConstants.POOL_B.equals( item.getPoolPart() ) )
                        {
                            newaclass.putFightListMapPoolB( Integer.valueOf( item.getFightNumberInPart() ), item );
                        }

                        if ( IValueConstants.FINAL_FIGHT.equals( item.getFlag() ) )
                        {
                            newaclass.setFinalFight( item );
                        }

                    }
                    else
                    {
                        // loserround fights
                        if ( IValueConstants.POOL_A.equals( item.getPoolPart() ) )
                        {
                            newaclass.putFightListLoserMapPoolA( Integer.valueOf( item.getFightNumberInPart() ),
                                                                     item );
                            if (item.getFightNumberInPart()==0) newaclass.setOriginal3rdPlaceFightA( (NewaFight)item.clone() );
                        }

                        if ( IValueConstants.POOL_B.equals( item.getPoolPart() ) )
                        {
                            newaclass.putFightListLoserMapPoolB( Integer.valueOf( item.getFightNumberInPart() ),
                                                                     item );
                            if (item.getFightNumberInPart()==0) newaclass.setOriginal3rdPlaceFightB( (NewaFight)item.clone() );
                        }
                    }
                }

                return newaclass;
            }
        }
        catch ( Exception e )
        {
            log.error( "can not load newaclass", e );
            throw new JJWDataLayerException( e );
        }
    }

    // private static String GET_FIGHTINGCLASS = SQL_FROM + TABLE_FIGHTINGCLASS+ " p " +SQL_WHERE +"p." + ID + "=?";

    public Newaclass getNewaclass( Long newaclassId )
        throws JJWDataLayerException
    {
        try
        {
            return (Newaclass) getHibernateTemplate().get( Newaclass.class, newaclassId );

        }
        catch ( Exception e )
        {
            log.error( "can not load newaclass", e );
            throw new JJWDataLayerException( e );
        }
    }

    /**
     * get all fightingclasses, that are in Use all parameter in the list, which correspond to a column will be use for
     * order by. Beginning with die first element (0) by Integer and than the second ....
     * <p/>
     * e.g. (0,1, ..
     * 
     * @return Newaclasses
     */
    public List<Newaclass> getInUseNewaclasses( Map<Integer, SortDTO> sortParameter )
        throws JJWDataLayerException
    {
        try
        {
            Query q = null;
            if ( sortParameter == null || sortParameter.size() == 0 )
            {
                q = getSession().createQuery( GET_INUSE_NEWACLASSES_WITH_ORDER_BY_DEFAULT );
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
                    if ( Newaclass.PROPERTY_MAP.get( dto.getDatabaseField() ) != null )
                    {
                        if ( sb == null )
                        {
                            sb = new StringBuffer( SQL_ORDER_BY );
                            sb.append( W_DOT ).append( Newaclass.PROPERTY_MAP.get( dto.getDatabaseField() ) );
                        }
                        else
                        {
                            sb.append( COMMA );
                            sb.append( W_DOT ).append( Newaclass.PROPERTY_MAP.get( dto.getDatabaseField() ) );
                        }

                        // asc or desc
                        if ( !dto.isAcending() )
                        {
                            sb.append( SQL_DESC );
                        }
                    }

                    i++;
                }
                q = getSession().createQuery( GET_INUSE_NEWACLASSES + sb.toString() );
            }

            return q.list();

        }
        catch ( Exception e )
        {
            log.error( "can not InuseNewaclasses", e );
            throw new JJWDataLayerException( e );
        }
    }

    public int getFightsystemOfNewaclass( long newaclassId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = null;
            q = getSession().createQuery( IS_NEWACLASS_A_SIMPLE_POOL_TYPE );
            q.setLong( 0, newaclassId );
            if ( !q.list().isEmpty() )
            {
                return Fightsystem.SIMPLE_POOL;
            }
            q = getSession().createQuery( IS_NEWACLASS_A_DOUBLE_POOL_TYPE );
            q.setLong( 0, newaclassId );
            if ( !q.list().isEmpty() )
            {
                return Fightsystem.DOUBLE_POOL;
            }

            q = getSession().createQuery( IS_NEWACLASS_A_DOUBLE_KO_TYPE );
            q.setLong( 0, newaclassId );
            if ( !q.list().isEmpty() )
            {
                return Fightsystem.DOUBLE_KO;
            }

            return TypeUtil.INT_DEFAULT;
        }
        catch ( Exception e )
        {
            log.error( "can not getFightsystemOfNewaclass", e );
            throw new JJWDataLayerException( e );
        }
    }

    public void setNewaclassResults( Map<Long, Integer> resultMap, Long newaclassId )
        throws JJWDataLayerException
    {
        try
        {

            Query q = getSession().createQuery( "from NewaFighter f where newaclass = ? AND f.ready = '1'" );
            q.setLong( 0, newaclassId );
            List<NewaFighter> list = q.list();
            for ( NewaFighter fighter : list )
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
            log.error( "can not setNewaclassResults", e );
            throw new JJWDataLayerException( e );
        }

    }

    public void resetNewaclassResults( Long newaclassId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( "update NewaFighter set place = 0 where newaclass = ? " );
            q.setLong( 0, newaclassId );
            q.executeUpdate();
        }
        catch ( Exception e )
        {
            log.error( "can not setResetNewaclassResults", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String NEWA_CERTIFICATION_SQL =
        "from NewaFighter f where f.newaclass = ?  and f.place is not null and f.place > 0 and f.place <= ? AND f.ready = '1'"
            + " order by f.place";

    private static String NEWA_CERTIFICATION_SQL_2 = "from Newaclass f left join fetch f.age a where f.id = ? ";

    public NewaCertification getNewaCertification( Long newaclassId )
        throws JJWDataLayerException
    {
        try
        {
            NewaCertification ret = new NewaCertification();

            ConfigJJW config = (ConfigJJW) getHibernateTemplate().get( ConfigJJW.class, TypeUtil.LONG_1 );
            Query q = getSession().createQuery( NEWA_CERTIFICATION_SQL );
            q.setLong( 0, newaclassId );
            q.setInteger( 1, config.getCertificationPlaces() );
            List<NewaFighter> fighterList = q.list();

            ret.setFighterList( fighterList );

            Query q1 = getSession().createQuery( NEWA_CERTIFICATION_SQL_2 );
            q1.setLong( 0, newaclassId );
            List<Newaclass> newaclassList = q1.list();
            if ( newaclassList != null && newaclassList.size() > TypeUtil.INT_DEFAULT )
            {
                ret.setNewaclassCaption( newaclassList.get( 0 ).getWeightclass() );
                ret.setSex( newaclassList.get( 0 ).getSex() );
                ret.setAge( newaclassList.get( 0 ).getAge().getDescription() );
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
 " From " + TABLE_FIGHT + SQL_WHERE
        + INewaFightDatabaseConstants.FIGHTER_ID_RED + "=?";

    private static String SQL_CHANGE_FIGHTER_IN_FIGHTS_2 =
 " From " + TABLE_FIGHT + SQL_WHERE
        + INewaFightDatabaseConstants.FIGHTER_ID_BLUE + "=?";

    private static String SQL_CHANGE_FIGHTER_IN_FIGHTS_UPDATE_RED =
 " UPDATE " + SQL_TABLE_FIGHT + SQL_SET
        + INewaFightDatabaseConstants.FIGHTER_ID_RED + "=? , " + INewaFightDatabaseConstants.MODIFICATION_DATE + "=? "
        + " WHERE " + ID + " in (";

    private static String SQL_CHANGE_FIGHTER_IN_FIGHTS_UPDATE_BLUE =
 " UPDATE " + SQL_TABLE_FIGHT + SQL_SET
        + INewaFightDatabaseConstants.FIGHTER_ID_BLUE + "=? , " + INewaFightDatabaseConstants.MODIFICATION_DATE + "=? "
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
            List<NewaFight> list1 = q.list();
            StringBuffer inClause1 = new StringBuffer( 32 );

            Query q2 = getSession().createQuery( SQL_CHANGE_FIGHTER_IN_FIGHTS_2 );
            q2.setLong( 0, fighter1Id );
            List<NewaFight> list2 = q2.list();
            StringBuffer inClause2 = new StringBuffer( 32 );

            for ( NewaFight fight : list1 )
            {
                if ( inClause1.length() > 1 )
                {
                    inClause1.append( COMMA );
                }
                inClause1.append( fight.getId() );
            }

            for ( NewaFight fight : list2 )
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

            for ( NewaFight fight : list1 )
            {
                if ( inClause3.length() > 1 )
                {
                    inClause3.append( COMMA );
                }
                inClause3.append( fight.getId() );
            }

            for ( NewaFight fight : list2 )
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
 SQL_UPDATE + SQL_TABLE_NEWA_SIMPLE_POOL + SQL_SET
        + INewaSimplePoolItemDatabaseConstants.FIGHTER_ID
 + " =? , "
        + INewaFighterDatabaseConstants.MODIFICATION_DATE
        + "=? " + SQL_WHERE + ID + " =? ";

    public void changeFighterInPool( int fighter1, int fighter2, Newaclass newaclass )
        throws JJWDataLayerException
    {
        try
        {
            NewaSimplePoolClass fc = getNewaclassSimplePool( newaclass.getId() );
            long poolId1 = TypeUtil.LONG_EMPTY;
            long poolId2 = TypeUtil.LONG_EMPTY;
            long fighterId1 = TypeUtil.LONG_EMPTY;
            long fighterId2 = TypeUtil.LONG_EMPTY;

            for ( NewaSimplePoolItem item : fc.getFighterList() )
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
 SQL_UPDATE + SQL_TABLE_NEWA_DOUBLE_POOL + SQL_SET
        + INewaDoublePoolItemDatabaseConstants.FIGHTER_ID
 + " =? , "
        + INewaFighterDatabaseConstants.MODIFICATION_DATE
        + "=? " + SQL_WHERE + ID + " =? ";

    public void changeFighterInDPool( int fighter1, int fighter2, Newaclass newaclass )
        throws JJWDataLayerException
    {
        try
        {
            int[] helpArray = { 0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4 };
            NewaDoublePoolClass fc = getNewaclassDoublePool( newaclass.getId() );
            long poolId1 = TypeUtil.LONG_EMPTY;
            long poolId2 = TypeUtil.LONG_EMPTY;
            long fighterId1 = TypeUtil.LONG_EMPTY;
            long fighterId2 = TypeUtil.LONG_EMPTY;

            if ( fighter1 % 2 == 1 )
            {
                for ( NewaDoublePoolItem item : fc.getFighterListPoolA() )
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
                for ( NewaDoublePoolItem item : fc.getFighterListPoolA() )
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
                for ( NewaDoublePoolItem item : fc.getFighterListPoolB() )
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
                for ( NewaDoublePoolItem item : fc.getFighterListPoolB() )
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
 SQL_UPDATE + SQL_TABLE_NEWA_KO + SQL_SET
        + INewaKoItemDatabaseConstants.FIGHTER_ID + " =? , " + INewaFighterDatabaseConstants.MODIFICATION_DATE
        + "=? "
        + SQL_WHERE + ID + " =? ";

    public void changeFighterInKo( int fighter1, int fighter2, Newaclass newaclass )
        throws JJWDataLayerException
    {
        NewaKoClass fc = getNewaclassKo( newaclass.getId() );
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
                for ( NewaKoItem item : fc.getFighterListPoolA() )
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
                for ( NewaKoItem item : fc.getFighterListPoolA() )
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
                for ( NewaKoItem item : fc.getFighterListPoolB() )
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
                for ( NewaKoItem item : fc.getFighterListPoolB() )
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

    private static String GET_NUMBER_OF_FIGHTER_IN_NEWACLASS =
 SQL_SELECT + SQL_COUNT_1 + SQL_FROM
        + INewaFighterDatabaseConstants.SQL_TABLE_FIGHTER + SQL_WHERE + INewaFighterDatabaseConstants.NEWACLASS
        + "=? AND " + INewaFighterDatabaseConstants.READY + "=1";

    public int getNumberOfFighterInNewaclass( long newaclassId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createSQLQuery( GET_NUMBER_OF_FIGHTER_IN_NEWACLASS );
            q.setLong( 0, newaclassId );
            return ( (BigInteger) q.uniqueResult() ).intValue();
        }
        catch ( Exception e )
        {
            log.error( "can not getNumberOfFighterInNewaclass", e );
            throw new JJWDataLayerException( e );
        }
    }

    /**
     * return : Newaclass, numberOfFighterInClass, numberOfFights, completedFights, fightsystem,averageFighttimeInClass
     */
    private static String GET_INUSE_NEWACLASSES_WITH_ORDER_BY_DEFAULT_FOR_TATAMI =
        SQL_SELECT
            + " new Newaclass(w, "
            + " (select count(distinct f.id) from NewaFighter f where f.newaclass = w.id and f.ready=1 ),"
            +
            // number of fighter in class

            " (SELECT COUNT(DISTINCT fl.id) FROM NewaFight fl WHERE fl.newaclass = w.id ),"
            +

            " (SELECT COUNT(DISTINCT fl.id) FROM NewaFight fl WHERE fl.newaclass = w.id AND fl.winnerId IS NOT NULL AND fl.winnerId >= 0 "
            + "    AND fl.fighterIdBlue not in ( ? )),"
            +

            "(select s.fightsystemType from Fightsystem s where s.minParticipant<="
            + "  (select count(distinct f.id) from NewaFighter f where f.newaclass = w.id ) AND s.maxParticipant>="
            + "  (select count(distinct f.id) from NewaFighter f where f.newaclass = w.id ) ),"
            +

            " (select min(fl.createDate) from NewaFight fl where fl.newaclass = w.id), "
            + " (select min(fl.modificationDate) from NewaFight fl where fl.newaclass = w.id and fl.modificationDate > fl.createDate)"
            +

            ")" + SQL_FROM + TABLE_NEWACLASS + " w " + SQL_WHERE + "w." + INUSE + SQL_IS + SQL_NOT + SQL_NULL
            + SQL_AND + "w." + INUSE + SQL_EQUAL + "'Y'" + " AND w." + DELETE_STOP + "='Y' AND w." + COMPLETE + "='N' "
            + SQL_ORDER_BY + "w.age." + ORDER_NUMBER + " , " + "w." + AGE + COMMA + "w." + SEX + COMMA + "w."
            + WEIGHT_LIMIT;

    private static String GET_INUSE_NEWACLASSES_WITH_USER_ACCESS =
 "SELECT u."
        + IUserNewaclassDatabaseConstants.NEWACLASS_ID + " from UserNewaclass u WHERE u."
        + IUserNewaclassDatabaseConstants.USER_ID + "=?";

    /**
     * get all newaclasses, that are in Use all parameter in the list, which correspond to a column will be use for
     * order by. Beginning with die first element (0) by Integer and than the second ....
     * <p/>
     * e.g. (0,1, ..
     * 
     * @return Newaclasses
     */
    public List<Newaclass> getInUseNewaclassesForTatami( Map<Integer, SortDTO> sortParameter, Long accountId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = null;
            if ( sortParameter == null || sortParameter.size() == 0 )
            {
                q = getSession().createQuery( GET_INUSE_NEWACLASSES_WITH_ORDER_BY_DEFAULT_FOR_TATAMI );
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
                    if ( Newaclass.PROPERTY_MAP.get( dto.getDatabaseField() ) != null )
                    {
                        if ( sb == null )
                        {
                            sb = new StringBuffer( SQL_ORDER_BY );
                            sb.append( W_DOT ).append( Newaclass.PROPERTY_MAP.get( dto.getDatabaseField() ) );
                        }
                        else
                        {
                            sb.append( COMMA );
                            sb.append( W_DOT ).append( Newaclass.PROPERTY_MAP.get( dto.getDatabaseField() ) );
                        }

                        // asc or desc
                        if ( !dto.isAcending() )
                        {
                            sb.append( SQL_DESC );
                        }
                    }

                    i++;
                }
                q = getSession().createQuery( GET_INUSE_NEWACLASSES + sb.toString() );
            }

            List<Newaclass> fcList = q.list();
            Query q2 = getSession().createQuery( GET_INUSE_NEWACLASSES_WITH_USER_ACCESS );
            q2.setLong( 0, accountId );
            List<Long> allowedList = q2.list();
            List<Newaclass> retList = new ArrayList<Newaclass>();
            for ( Newaclass fc : fcList )
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
            log.error( "can not InuseNewaclasses", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String SQL_CREATE_AUTOMATIC_NEWACLASSES_GET_WEIGHTS =
 "SELECT "
        + INewaFighterDatabaseConstants.WEIGHT + " FROM " + INewaFighterDatabaseConstants.TABLE_FIGHTER + " WHERE "
        + INewaFighterDatabaseConstants.AGE + " =? AND " + INewaFighterDatabaseConstants.SEX + " =? " + " GROUP BY "
        + INewaFighterDatabaseConstants.WEIGHT + " ORDER BY " + INewaFighterDatabaseConstants.WEIGHT;

    public void createAutomaticNewaclasses( Long ageId, Long userId )
        throws JJWDataLayerException
    {
        try
        {
            Age age = (Age) getHibernateTemplate().get( Age.class, ageId );
            doCreateAutomaticNewaclass( userId, age, "1" ); // male
            doCreateAutomaticNewaclass( userId, age, "2" ); // female
        }
        catch ( Exception e )
        {
            log.error( "can not createAutomaticNewaclasses", e );
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
    private void doCreateAutomaticNewaclass( Long userId, Age age, String sex )
        throws HibernateException, DataAccessResourceFailureException, IllegalStateException
    {
        Query q = getSession().createSQLQuery( SQL_CREATE_AUTOMATIC_NEWACLASSES_GET_WEIGHTS );
        q.setLong( 0, age.getId() );
        q.setString( 1, sex );
        List<BigDecimal> weightList = q.list();
        double lastStart = 0.0;
        double weight = 0.0;
        double weightDiff = 5.0;
        Newaclass fc = null;

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
                fc = new Newaclass();
                fc.setAge( age );
                fc.setCreateDate( new Timestamp( System.currentTimeMillis() ) );
                fc.setCreateId( userId );
                fc.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
                fc.setModificationId( userId );
                fc.setSex( sex );
                fc.setStartWeight( weight );
                fc.setWeightclass( "-" + ( weight + weightDiff ) );
                fc.setWeightLimit( weight + weightDiff );
                if ( !existsNewaclass( fc ) )
                {
                    getHibernateTemplate().saveOrUpdate( fc );
                }

            }
        }
    }

    private static String SQL_EXIST_NEWACLASS =
 " FROM " + INewaWeightclassDatabaseConstants.TABLE_NEWACLASS
        + " f WHERE f." + INewaWeightclassDatabaseConstants.SEX + "=? and f."
        + INewaWeightclassDatabaseConstants.START_WEIGHT + " =? and f."
        + INewaWeightclassDatabaseConstants.WEIGHT_LIMIT + "=? and f." + INewaWeightclassDatabaseConstants.AGE + "=?";

    private boolean existsNewaclass( Newaclass fc )
        throws HibernateException
    {
        Query q = getSession().createQuery( SQL_EXIST_NEWACLASS );
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
 "update " + INewaFighterDatabaseConstants.SQL_TABLE_FIGHTER
        + " f set f." + INewaFighterDatabaseConstants.NEWACLASS
 + " = " + "if( " + " (Select " + ID + " from "
        + INewaWeightclassDatabaseConstants.SQL_TABLE_NEWACLASS
        + " w where w."
 + INewaWeightclassDatabaseConstants.AGE
        + " = f." + INewaFighterDatabaseConstants.AGE + " AND w." + INewaWeightclassDatabaseConstants.SEX
        + " = f."
        + INewaFighterDatabaseConstants.SEX
        + " AND w."
        + INewaWeightclassDatabaseConstants.START_WEIGHT
        + " < f."
        + INewaFighterDatabaseConstants.WEIGHT
        + " AND w."
        + INewaWeightclassDatabaseConstants.WEIGHT_LIMIT
        + " >= f."
        + INewaFighterDatabaseConstants.WEIGHT
        + ") "
        // if true minValue
        + " is null,"
        + TypeUtil.LONG_EMPTY
        + " , "
        // if false
        + " (Select " + ID + " from " + INewaWeightclassDatabaseConstants.SQL_TABLE_NEWACLASS + " w where w."
        + INewaWeightclassDatabaseConstants.AGE + " = f." + INewaFighterDatabaseConstants.AGE + " AND w."
        + INewaWeightclassDatabaseConstants.SEX + " = f." + INewaFighterDatabaseConstants.SEX + " AND w."
        + INewaWeightclassDatabaseConstants.START_WEIGHT + " < f." + INewaFighterDatabaseConstants.WEIGHT + " AND w."
        + INewaWeightclassDatabaseConstants.WEIGHT_LIMIT + " >= f." + INewaFighterDatabaseConstants.WEIGHT
        + ") "
        // end if
        + " ), "
            + MODIFICATION_ID + " = ? ," + MODIFICATION_DATE + " = now() " + " where "
        + INewaFighterDatabaseConstants.READY + " = '1' and (f." + INewaFighterDatabaseConstants.NEWACLASS
 + " is null or f."
 + INewaFighterDatabaseConstants.NEWACLASS + " < 1)  ";

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
 "SELECT distinct f."
        + INewaFighterDatabaseConstants.AGE + " from " + INewaFighterDatabaseConstants.TABLE_FIGHTER + " f WHERE "
        + INewaFighterDatabaseConstants.NEWACLASS + " is null or f." + INewaFighterDatabaseConstants.NEWACLASS
        + " < 1)";

    public List<Age> getAgesForAutomaticNewaclassCreation()
        throws JJWDataLayerException
    {
        try
        {
            return getHibernateTemplate().find( SQL_AGE_FOR_AUTOMATIC_FIGHTINGTINGCLASS_CREATION );
        }
        catch ( Exception e )
        {
            log.error( "can not getAgesForAutmaticNewaclassCreation", e );
            throw new JJWDataLayerException( e );
        }
    }

	
    private static String SQL_GET_CHILD_NEWACLASSES =
 " from "
        + INewaWeightclassDatabaseConstants.TABLE_NEWACLASS + " f WHERE f." + INewaWeightclassDatabaseConstants.PARENT
        + " = ?";

    public List<Newaclass> getChildNewaclasses( Long newaclassId )
        throws JJWDataLayerException
    {
		 try
	        {
            Query q = getSession().createQuery( SQL_GET_CHILD_NEWACLASSES );
            q.setLong( 0, newaclassId );
	         return q.list();            
	        }
	        catch ( Exception e )
	        {
            log.error( "can not getChildNewaclasses", e );
	            throw new JJWDataLayerException( e );
	        }
	}

    private static String SQL_GET_COMBINABLE_NEWACLASSES = "SELECT new Newaclass(f, "
        + " (select count(distinct fi.id) from NewaFighter fi where fi.newaclass = f.id )," // number of fighter in
                                                                                            // class
        + " (select count(distinct fi.id) from NewaFighter fi where fi.newaclass = f.id and fi.ready=1)"
        // number of fighter in class which are ready
        + " ) " + " from "
        + INewaWeightclassDatabaseConstants.TABLE_NEWACLASS
        + " f left join  f."
        + INewaWeightclassDatabaseConstants.AGE
        + "  a WHERE (f."
        + INewaWeightclassDatabaseConstants.PARENT
        + " is null or "
        + "f."
        + INewaWeightclassDatabaseConstants.PARENT
        + " < 0) and f."
        + INewaWeightclassDatabaseConstants.ID
        + " <> ? "
        + " and (f."
        + INewaWeightclassDatabaseConstants.DELETE_STOP
        + " is null or f."
        + INewaWeightclassDatabaseConstants.DELETE_STOP
        + " ='N') "
        // // and f.id not in (select ff.parent from Newaclass ff)
        + " and f."
        + INewaWeightclassDatabaseConstants.ID
        + " not in (select ff."
        + INewaWeightclassDatabaseConstants.PARENT
        + " from "
        + INewaWeightclassDatabaseConstants.TABLE_NEWACLASS
        + " ff)"
        //
        + " order by a."
        + START_AGE
        + " desc, f."
 + INewaWeightclassDatabaseConstants.SEX + ", f." + START_WEIGHT;

    public List<Newaclass> getCombinableNewaclasses( Long newaclassId )
        throws JJWDataLayerException
    {
		 try
	        {
            Query q = getSession().createQuery( SQL_GET_COMBINABLE_NEWACLASSES );
            q.setLong( 0, newaclassId );
	         return q.list();    
	        }
	        catch ( Exception e )
	        {
            log.error( "can not getCombinaleNewaclasses", e );
	            throw new JJWDataLayerException( e );
	        }
	}

	
    private static String SQL_GET_FIGHTER_FOR_NEW_CLASS = " from " + INewaFighterDatabaseConstants.TABLE_FIGHTER
        + " f where f." + INewaFighterDatabaseConstants.NEWACLASS_ID + "= :newaclassId";

    /**
     * adds a fightingclass to a parentclass and moves all Fighter of the child class to the parent class and sets the
     * childclass into original class column. Exception: when one of the classes are isDeleteStop
     */
    public void addNewaclassToClass( long parentClass, long childClass )
        throws JJWDataLayerException
    {
        try
        {
            Newaclass parent = (Newaclass) getHibernateTemplate().get( Newaclass.class, parentClass );
            if ( parent.isDeleteStop() )
                throw new Exception( "parent is deleteStop" );

            Newaclass child = (Newaclass) getHibernateTemplate().get( Newaclass.class, childClass );
            if ( child.isDeleteStop() )
                throw new Exception( "child is deleteStop" );

            child.setParentId( parentClass );

            // Query q = getSession().createQuery( SQL_GET_FIGHTER_FOR_NEW_CLASS );
            // q.setLong( 0, childClass );
            List<NewaFighter> fighterList =
                (List<NewaFighter>) getHibernateTemplate().findByNamedParam( SQL_GET_FIGHTER_FOR_NEW_CLASS,
                                                                             "newaclassId", childClass );

            // set fighter to new class
            for ( NewaFighter fighter : fighterList )
            {
                fighter.setOriginalNewaclassId( fighter.getNewaclassId() );
                fighter.setNewaclassId( parentClass );
            }

        }
        catch ( Exception e )
        {
            log.error( "can not addNewaclassToClass", e );
            throw new JJWDataLayerException( e );
        }
		
	}

    private static String SQL_GET_FIGHTER_FOR_ORIGINAL_CLASS = " from " + INewaFighterDatabaseConstants.TABLE_FIGHTER
        + " f where f." + INewaFighterDatabaseConstants.ORIGINAL_NEWACLASS + "=?";
	/**
	 * removes a fightingclass from its parent and sets for all corresponding fighter the original fighting class
	 * 
	 * Exception: when the classes are isDelteStop
	 */
    public void removeNewaclassFromParent( long parentClass, long childClass )
        throws JJWDataLayerException
    {
        try
        {

            Newaclass parent = (Newaclass) getHibernateTemplate().get( Newaclass.class, parentClass );
            if ( parent.isDeleteStop() )
                throw new Exception( "parent is deleteStop" );

            Newaclass child = (Newaclass) getHibernateTemplate().get( Newaclass.class, childClass );
            if ( child.isDeleteStop() )
                throw new Exception( "child is deleteStop" );

            child.setParentId( null );

            Query q = getSession().createQuery( SQL_GET_FIGHTER_FOR_ORIGINAL_CLASS );
            q.setLong( 0, childClass );
            List<NewaFighter> fighterList = (List<NewaFighter>) q.list();

            // set fighter to new class
            for ( NewaFighter fighter : fighterList )
            {
                fighter.setNewaclassId( fighter.getOriginalNewaclassId() );
                fighter.setOriginalNewaclassId( TypeUtil.LONG_MIN );
            }

        }
        catch ( Exception e )
        {
            log.error( "can not removeNewaclassFromParent", e );
            throw new JJWDataLayerException( e );
        }

    }

    private static String SQL_GET_TIMES_OF_CURRENT_TATAMI_USE =

        "select newaclass_id, if(total is null,0,total) from "
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

            + " FROM user_duoclass u2 union "

            + " select user_id,newaclass_id,(select count(1)* a.estimatedTimeDuo from  newaclass w left join age a on w.age=a.id"
            + " left join newa_fight_list f on w.id=f.newaclass"
            + " where w.id=u3.newaclass_id and winnerid<0 and deleteStop='Y' and complete='N' and startTime is  not  null) as mytime"

            + " FROM user_newaclass u3 "

            + ") tt group by user_id) t left join user_newaclass u3 on u3.user_id=t.user_Id "
            + " left join newaclass w on u3.newaclass_id=w.id where newaclass_id is not null and startTime is null";

    /**
     * gets the time for each tatami which is need for all started classes Exception: Return: <weightclassId,time(in
     * sec)>
     */
    public Map<Long, Long> getTimesOfCurrentTatamiUse()
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createSQLQuery( SQL_GET_TIMES_OF_CURRENT_TATAMI_USE );

            Map<Long, Long> retMap = new HashMap<Long, Long>();

            // set fighter to new class
            for ( Object item : q.list() )
            {
                retMap.put( ( (BigInteger) ( (Object[]) item )[0] ).longValue(),
                            ( (BigDecimal) ( (Object[]) item )[1] ).longValue() );
            }
            return retMap;
        }
        catch ( Exception e )
        {
            log.error( "can not getTimesOfCurrentTatamiUse", e );
            throw new JJWDataLayerException( e );
        }
    }
}