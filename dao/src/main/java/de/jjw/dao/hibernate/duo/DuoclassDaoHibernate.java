/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoclassDaoHibernate.java
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

package de.jjw.dao.hibernate.duo;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import de.jjw.dao.IDatabaseTableConstants;
import de.jjw.dao.duo.DuoclassDao;
import de.jjw.dao.hibernate.BaseDaoHibernate;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.dao.hibernate.admin.IAgeDatabaseConstants;
import de.jjw.model.admin.Age;
import de.jjw.model.admin.ConfigJJW;
import de.jjw.model.admin.Fightsystem;
import de.jjw.model.duo.DuoCertification;
import de.jjw.model.duo.DuoDoublePoolClass;
import de.jjw.model.duo.DuoDoublePoolItem;
import de.jjw.model.duo.DuoFight;
import de.jjw.model.duo.DuoKoClass;
import de.jjw.model.duo.DuoKoItem;
import de.jjw.model.duo.DuoSimplePoolClass;
import de.jjw.model.duo.DuoSimplePoolItem;
import de.jjw.model.duo.DuoTeam;
import de.jjw.model.duo.Duoclass;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;
import de.jjw.util.dto.SortDTO;

public class DuoclassDaoHibernate
    extends BaseDaoHibernate
    implements DuoclassDao, IDatabaseTableConstants, IAgeDatabaseConstants
{

    private static final String COMMA = ",";

    private static String DUOCLASS_ALL =
        "SELECT new Duoclass(w, " + " (select count(distinct f.id) from DuoTeam f where f.duoclass = w.id ),"
            +
            // number of fighter in class
            " (select count(distinct f.id) from DuoTeam f where f.duoclass = w.id and f.ready=1)"
            +
            // number of fighter in class which are ready
            " ) " + " from " + TABLE_DUOCLASS + " w left join  w." + IDuoclassDatabaseConstants.AGE + " a order by a."
            + START_AGE + " desc, w." + IDuoclassDatabaseConstants.SEX;

    private static String DUOCLASS_BY_AGE =
        "from " + TABLE_DUOCLASS + " w left join fetch w." + IDuoclassDatabaseConstants.AGE + " a where w."
            + IDuoclassDatabaseConstants.AGE + "=? order by a." + START_AGE + " desc, w."
            + IDuoclassDatabaseConstants.SEX;

    private static String DUOCLASS_IN_DUOTEAM_USE =
        "select f.* from " + TABLE_DUOTEAM + " f where f." + IDuoTeamDatabaseConstants.DUOCLASS + "= ?";

    private static String DUOCLASS_AGE_SHORT =
        "from" + TABLE_DUOCLASS + " w left join fetch w.age a where a.ageShort =? order by w.sex";

    private static String DUOCLASS_AGE_ID = "from Duoclass w left join fetch w.age a where a.id =? order by w.sex";

    private static String DUOCLASS_BY_AGE_SEX =
        SQL_FROM + TABLE_DUOCLASS + " w " + SQL_LEFT + SQL_JOIN + SQL_FETCH + " w." + IDuoclassDatabaseConstants.AGE
            + " a " + SQL_WHERE + "a." + ID + SQL_EQUAL + SQL_QUESTION_MARK + SQL_AND + " w."
            + IDuoclassDatabaseConstants.SEX + SQL_EQUAL + SQL_QUESTION_MARK;

    public Duoclass getDuoclass( Long id )
        throws JJWDataLayerException
    {
        try
        {
            return (Duoclass) getHibernateTemplate().get( Duoclass.class, id );
        }
        catch ( Exception e )
        {
            log.fatal( e.getMessage() );
            throw new JJWDataLayerException( e );
        }
    }

    public List<Duoclass> getAllDuoclasses()
        throws JJWDataLayerException
    {
        try
        {
            return getHibernateTemplate().find( DUOCLASS_ALL );
        }
        catch ( Exception e )
        {
            e.getMessage();
            throw new JJWDataLayerException( e );
        }
    }

    public List<Duoclass> getDuoclassByAgeNameShort( String ageShort )
    {
        Query q = getSession().createQuery( DUOCLASS_AGE_SHORT );
        q.setString( 0, ageShort );
        return q.list();
    }

    public List<Duoclass> getDuoclassByAge( Age age )
    {
        Query q = getSession().createQuery( DUOCLASS_BY_AGE );
        q.setLong( 0, age.getId() );
        return q.list();
    }

    /**
     * save fights, poolentries and pool for the duoclass
     * 
     * @throws JJWDataLayerException
     */
    public void saveDuoclassAfterCreate( Duoclass duoclass )
        throws JJWDataLayerException
    {
        if ( duoclass != null )
        {
            try
            {
                switch ( duoclass.getFightsystem() )
                {
                    case Fightsystem.SIMPLE_POOL:

                        for ( DuoSimplePoolItem item : ( (DuoSimplePoolClass) duoclass ).getDuoTeamList() )
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
                        for ( DuoDoublePoolItem item : ( (DuoDoublePoolClass) duoclass ).getDuoListPoolA() )
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
                        for ( DuoDoublePoolItem item : ( (DuoDoublePoolClass) duoclass ).getDuoListPoolB() )
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
                        
                        getHibernateTemplate().save( ( (DuoDoublePoolClass) duoclass ).getHalfFinalFight1());
                        getHibernateTemplate().save( ( (DuoDoublePoolClass) duoclass ).getHalfFinalFight2());

                        getHibernateTemplate().save( ( (DuoDoublePoolClass) duoclass ).getFinalFight() );
                        break;

                    case Fightsystem.DOUBLE_KO:
                        for ( DuoKoItem item : ( (DuoKoClass) duoclass ).getTeamListPoolA() )
                        {
                            getHibernateTemplate().save( item );
                        }

                        for ( DuoKoItem item : ( (DuoKoClass) duoclass ).getTeamListPoolB() )
                        {
                            getHibernateTemplate().save( item );
                        }

                        for ( DuoFight item : ( (DuoKoClass) duoclass ).getFightListMapPoolA().values() )
                        {
                            if ( item.isWriteFlag() )
                            {
                                getHibernateTemplate().save( item );
                            }
                        }

                        for ( DuoFight item : ( (DuoKoClass) duoclass ).getFightListMapPoolB().values() )
                        {
                            if ( item.isWriteFlag() )
                            {
                                getHibernateTemplate().save( item );
                            }
                        }

                        for ( DuoFight item : ( (DuoKoClass) duoclass ).getFightListLooserMapPoolA().values() )
                        {
                            getHibernateTemplate().save( item );
                        }

                        for ( DuoFight item : ( (DuoKoClass) duoclass ).getFightListLooserMapPoolB().values() )
                        {
                            getHibernateTemplate().save( item );
                        }

                        getHibernateTemplate().save( ( (DuoKoClass) duoclass ).getFinalFight() );
                        break;

                }
            }
            catch ( Exception e )
            {
                log.error( "can not create duoclasses", e );
                throw new JJWDataLayerException( e );
            }
        }
    }

    public void saveDuoclass( Duoclass duoclass )
        throws JJWDataLayerException
    {
        try
        {
            getHibernateTemplate().saveOrUpdate( duoclass );
        }
        catch ( Exception e )
        {
            log.fatal( "Can't save duotclass: " + duoclass.toString() );
            throw new JJWDataLayerException( e );
        }
    }

    /**
     * get the right duoclass for a duoteam who is specified by age and sex
     * 
     * @param ageId
     * @param sex
     * @return
     * @throws JJWDataLayerException
     */
    public Duoclass getDuoclassByAgeSex( Long ageId, String sex )
        throws JJWDataLayerException
    {
        Duoclass ret = null;
        try
        {
            Query q = getSession().createQuery( DUOCLASS_BY_AGE_SEX );
            q.setLong( 0, ageId );
            q.setString( 1, sex );
            List list = q.list();

            if ( list != null && list.size() > 0 )
            {
                return (Duoclass) list.get( 0 );
            }
        }
        catch ( Exception e )
        {
            log.error( "can not get duoclass ba age,sex ", e );
            throw new JJWDataLayerException( e );
        }

        return ret;
    }

    private static String GET_DUOCLASS_SIMPLEPOOL_POOL =
        SQL_FROM + IDuoSimplePoolItemDatabaseConstants.TABLE_DUO_SIMPLE_POOL + " p " + SQL_LEFT + SQL_JOIN + SQL_FETCH
            + "p." + IDuoSimplePoolItemDatabaseConstants.DUOTEAM + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
            + IDuoSimplePoolItemDatabaseConstants.DUOCLASS + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
            + IDuoSimplePoolItemDatabaseConstants.FIGHT1 + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
            + IDuoSimplePoolItemDatabaseConstants.FIGHT2 + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
            + IDuoSimplePoolItemDatabaseConstants.FIGHT3 + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
            + IDuoSimplePoolItemDatabaseConstants.FIGHT4 + SQL_WHERE + "p."
            + IDuoSimplePoolItemDatabaseConstants.DUOCLASS + "=?" + SQL_ORDER_BY + "p."
            + IDuoSimplePoolItemDatabaseConstants.NUMBER_IN_POOL;

    public DuoSimplePoolClass getDuoclassSimplePool( Long duoclassId )
        throws JJWDataLayerException
    {
        try
        {

            Query q = getSession().createQuery( GET_DUOCLASS_SIMPLEPOOL_POOL );
            q.setLong( 0, duoclassId );

            List<DuoSimplePoolItem> retListPool = q.list();
            if ( retListPool == null || retListPool.size() == 0 )
            {
                return null;
            }
            else
            {
                DuoSimplePoolClass duoclass = new DuoSimplePoolClass();

                for ( DuoSimplePoolItem item : retListPool )
                {
                    duoclass.addDuoTeamList( item );
                }
                return duoclass;
            }
        }
        catch ( Exception e )
        {
            log.error( "can not load duoclass", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String GET_DUOCLASS_DOUBLEPOOL_POOL =
        SQL_FROM + IDuoDoublePoolItemDatabaseConstants.TABLE_DUO_DOUBLE_POOL + " p " + SQL_LEFT + SQL_JOIN + SQL_FETCH
            + "p." + IDuoDoublePoolItemDatabaseConstants.DUOTEAM + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
            + IDuoDoublePoolItemDatabaseConstants.DUOCLASS + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
            + IDuoDoublePoolItemDatabaseConstants.FIGHT1 + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
            + IDuoDoublePoolItemDatabaseConstants.FIGHT2 + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
            + IDuoDoublePoolItemDatabaseConstants.FIGHT3 + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
            + IDuoDoublePoolItemDatabaseConstants.FIGHT4 + SQL_WHERE + "p."
            + IDuoDoublePoolItemDatabaseConstants.DUOCLASS + "=?" + SQL_ORDER_BY + "p."
            + IDuoDoublePoolItemDatabaseConstants.POOL_PART + ",p."
            + IDuoDoublePoolItemDatabaseConstants.NUMBER_IN_POOL;

    private static String GET_DUOCLASS_DOUBLEPOOL_POOL_FINAL_FIGHT =
        SQL_FROM + TABLE_DUO_FIGHT + " f " + SQL_WHERE + "f." + IDuoFightDatabaseConstants.DUOCLASS_ID + SQL_EQUAL
            + SQL_QUESTION_MARK + SQL_AND + "f." + IDuoFightDatabaseConstants.FLAG + SQL_EQUAL + SQL_QUESTION_MARK;
    
    private static String GET_DUOCLASS_DOUBLEPOOL_POOL_HALF_FINAL_FIGHT_1 =
                    SQL_FROM + TABLE_DUO_FIGHT + " f " + SQL_WHERE + "f." + IDuoFightDatabaseConstants.DUOCLASS_ID + SQL_EQUAL
                        + SQL_QUESTION_MARK + SQL_AND + "f." + IDuoFightDatabaseConstants.FLAG + SQL_EQUAL + SQL_QUESTION_MARK;
    
    private static String GET_DUOCLASS_DOUBLEPOOL_POOL_HALF_FINAL_FIGHT_2 =
                    SQL_FROM + TABLE_DUO_FIGHT + " f " + SQL_WHERE + "f." + IDuoFightDatabaseConstants.DUOCLASS_ID + SQL_EQUAL
                        + SQL_QUESTION_MARK + SQL_AND + "f." + IDuoFightDatabaseConstants.FLAG + SQL_EQUAL + SQL_QUESTION_MARK;


    private static String GET_DUOCLASS_KO =
        SQL_FROM + IDuoKoItemDatabaseConstants.TABLE_DUO_KO + " p " + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
            + IDuoKoItemDatabaseConstants.DUOTEAM + SQL_LEFT + SQL_JOIN + SQL_FETCH + "p."
            + IDuoKoItemDatabaseConstants.DUOCLASS + SQL_WHERE + "p." + IDuoKoItemDatabaseConstants.DUOCLASS + "=?"
            + SQL_ORDER_BY + "p." + IDuoKoItemDatabaseConstants.POOL_PART + ",p."
            + IDuoKoItemDatabaseConstants.NUMBER_IN_POOL;

    private static String GET_DUOCLASS_KO_FIGHTS =
        SQL_FROM + TABLE_DUO_FIGHT + " f " + SQL_WHERE + "f." + IDuoFightDatabaseConstants.DUOCLASS_ID + SQL_EQUAL
            + SQL_QUESTION_MARK;

    public DuoDoublePoolClass getDuoclassDoublePool( Long duoclassId )
        throws JJWDataLayerException
    {
        try
        {

            Query q = getSession().createQuery( GET_DUOCLASS_DOUBLEPOOL_POOL );
            q.setLong( 0, duoclassId );

            List<DuoDoublePoolItem> retListPool = q.list();
            if ( retListPool == null || retListPool.size() == 0 )
            {
                return null;
            }
            else
            {
                DuoDoublePoolClass duoclass = new DuoDoublePoolClass();

                for ( DuoDoublePoolItem item : retListPool )
                {
                    if ( IValueConstants.POOL_A.equals( item.getPoolPart() ) )
                    {
                        duoclass.addDuoListPoolA( item );
                    }
                    if ( IValueConstants.POOL_B.equals( item.getPoolPart() ) )
                    {
                        duoclass.addDuoListPoolB( item );
                    }
                }

                Query q2 = getSession().createQuery( GET_DUOCLASS_DOUBLEPOOL_POOL_HALF_FINAL_FIGHT_1 );
                q2.setLong( 0, duoclassId );
                q2.setString( 1, IValueConstants.HALF_FINAL_FIGHT_1 );
                DuoFight halfFinalFight1 = (DuoFight) q2.list().get( 0 );
                if ( halfFinalFight1 != null && halfFinalFight1.getTeamIdRed() > TypeUtil.LONG_DEFAULT )
                {
                    Query q3 = getSession().createQuery( "FROM DuoTeam f WHERE f.id=?" );
                    q3.setLong( 0, halfFinalFight1.getTeamIdRed() );
                    halfFinalFight1.setDuoTeamRed( (DuoTeam) q3.uniqueResult() );
                }

                if ( halfFinalFight1 != null && halfFinalFight1.getTeamIdBlue() > TypeUtil.LONG_DEFAULT )
                {
                    Query q3 = getSession().createQuery( "FROM DuoTeam f WHERE f.id=?" );
                    q3.setLong( 0, halfFinalFight1.getTeamIdBlue() );
                    halfFinalFight1.setDuoTeamBlue( (DuoTeam) q3.uniqueResult() );
                }
                duoclass.setHalfFinalFight1(  halfFinalFight1 );

                
                q2 = getSession().createQuery( GET_DUOCLASS_DOUBLEPOOL_POOL_HALF_FINAL_FIGHT_2 );
                q2.setLong( 0, duoclassId );
                q2.setString( 1, IValueConstants.HALF_FINAL_FIGHT_2 );
                DuoFight halfFinalFight2 = (DuoFight) q2.list().get( 0 );
                if ( halfFinalFight2 != null && halfFinalFight2.getTeamIdRed() > TypeUtil.LONG_DEFAULT )
                {
                    Query q3 = getSession().createQuery( "FROM DuoTeam f WHERE f.id=?" );
                    q3.setLong( 0, halfFinalFight2.getTeamIdRed() );
                    halfFinalFight2.setDuoTeamRed( (DuoTeam) q3.uniqueResult() );
                }

                if ( halfFinalFight2 != null && halfFinalFight2.getTeamIdBlue() > TypeUtil.LONG_DEFAULT )
                {
                    Query q3 = getSession().createQuery( "FROM DuoTeam f WHERE f.id=?" );
                    q3.setLong( 0, halfFinalFight2.getTeamIdBlue() );
                    halfFinalFight2.setDuoTeamBlue( (DuoTeam) q3.uniqueResult() );
                }
                duoclass.setHalfFinalFight2(  halfFinalFight2 );


                 q2 = getSession().createQuery( GET_DUOCLASS_DOUBLEPOOL_POOL_FINAL_FIGHT );
                q2.setLong( 0, duoclassId );
                q2.setString( 1, IValueConstants.FINAL_FIGHT );
                DuoFight finalFight = (DuoFight) q2.list().get( 0 );
                if ( finalFight != null && finalFight.getTeamIdRed() > TypeUtil.LONG_DEFAULT )
                {
                    Query q3 = getSession().createQuery( "FROM DuoTeam f WHERE f.id=?" );
                    q3.setLong( 0, finalFight.getTeamIdRed() );
                    finalFight.setDuoTeamRed( (DuoTeam) q3.uniqueResult() );
                }

                if ( finalFight != null && finalFight.getTeamIdBlue() > TypeUtil.LONG_DEFAULT )
                {
                    Query q3 = getSession().createQuery( "FROM DuoTeam f WHERE f.id=?" );
                    q3.setLong( 0, finalFight.getTeamIdBlue() );
                    finalFight.setDuoTeamBlue( (DuoTeam) q3.uniqueResult() );
                }
                duoclass.setFinalFight( finalFight );
                return duoclass;
            }
        }
        catch ( Exception e )
        {
            log.error( "can not load duoclass", e );
            throw new JJWDataLayerException( e );
        }
    }

    public DuoKoClass getDuoclassKo( Long duoclassId )
        throws JJWDataLayerException
    {
        try
        {

            Query q = getSession().createQuery( GET_DUOCLASS_KO );
            q.setLong( 0, duoclassId );

            List<DuoKoItem> retListPool = q.list();
            if ( retListPool == null || retListPool.size() == 0 )
            {
                return null;
            }
            else
            {
                DuoKoClass duoclass = new DuoKoClass();
                Map<Long, DuoTeam> duoTeamMap = new HashMap<Long, DuoTeam>();

                for ( DuoKoItem item : retListPool )
                {
                    duoTeamMap.put( item.getDuoTeam().getId(), item.getDuoTeam() );
                    if ( IValueConstants.POOL_A.equals( item.getPoolPart() ) )
                    {
                        duoclass.addTeamListPoolA( item );
                    }
                    if ( IValueConstants.POOL_B.equals( item.getPoolPart() ) )
                    {
                        duoclass.addTeamListPoolB( item );
                    }
                }

                Query q2 = getSession().createQuery( GET_DUOCLASS_KO_FIGHTS );
                q2.setLong( 0, duoclassId );

                List<DuoFight> fightList = q2.list();
                // fightingclass.setFinalFight(finalFight);

                for ( DuoFight item : fightList )
                {
                    item.setDuoTeamRed( duoTeamMap.get( item.getTeamIdRed() ) );
                    item.setDuoTeamBlue( duoTeamMap.get( item.getTeamIdBlue() ) );
                    if ( item.isMainRound() )
                    {
                        // mainround and final fight
                        if ( IValueConstants.POOL_A.equals( item.getPoolPart() ) )
                        {
                            duoclass.putFightListMapPoolA( Integer.valueOf( item.getFightNumberInPart() ), item );
                        }

                        if ( IValueConstants.POOL_B.equals( item.getPoolPart() ) )
                        {
                            duoclass.putFightListMapPoolB( Integer.valueOf( item.getFightNumberInPart() ), item );
                        }

                        if ( IValueConstants.FINAL_FIGHT.equals( item.getFlag() ) )
                        {
                            duoclass.setFinalFight( item );
                        }

                    }
                    else
                    {
                        // loserround fights
                        if ( IValueConstants.POOL_A.equals( item.getPoolPart() ) )
                        {
                            duoclass.putFightListLoserMapPoolA( Integer.valueOf( item.getFightNumberInPart() ), item );
                            if (item.getFightNumberInPart()==0) duoclass.setOriginal3rdPlaceFightA( (DuoFight)item.clone() );
                        }

                        if ( IValueConstants.POOL_B.equals( item.getPoolPart() ) )
                        {
                            duoclass.putFightListLoserMapPoolB( Integer.valueOf( item.getFightNumberInPart() ), item );
                            if (item.getFightNumberInPart()==0) duoclass.setOriginal3rdPlaceFightB( (DuoFight)item.clone() );
                        }
                    }
                }

                return duoclass;
            }
        }
        catch ( Exception e )
        {
            log.error( "can not load duoclass", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String DELETE_UNUSED_FIGHTS =
        "DELETE FROM " + TABLE_DUO_FIGHT + " f WHERE f." + IDuoFightDatabaseConstants.DUOCLASS_ID + " in (SELECT " + ID
            + SQL_FROM + TABLE_DUOCLASS + " fc WHERE " + IDuoclassDatabaseConstants.DELETE_STOP + " is null or "
            + IDuoclassDatabaseConstants.DELETE_STOP + " <> 'Y')";

    private static String DELETE_UNUSED_DUO_SIMPLE_POOL_ITEM =
        SQL_DELETE + SQL_FROM + IDuoSimplePoolItemDatabaseConstants.TABLE_DUO_SIMPLE_POOL + " f " + SQL_WHERE + " f."
            + IDuoSimplePoolItemDatabaseConstants.DUOCLASS + SQL_IN + SQL_BRACE_OPEN + SQL_FROM + TABLE_DUOCLASS
            + " fc " + SQL_WHERE + "fc." + IDuoclassDatabaseConstants.DELETE_STOP + SQL_IS + SQL_NULL + SQL_OR + "fc."
            + IDuoclassDatabaseConstants.DELETE_STOP + SQL_EQUAL + "'N'" + SQL_BRACE_CLOSE;

    private static String DELETE_UNUSED_DUO_DOUBLE_POOL_ITEM =
        SQL_DELETE + SQL_FROM + IDuoDoublePoolItemDatabaseConstants.TABLE_DUO_DOUBLE_POOL + " f " + SQL_WHERE + " f."
            + IDuoDoublePoolItemDatabaseConstants.DUOCLASS + SQL_IN + SQL_BRACE_OPEN + SQL_FROM + TABLE_DUOCLASS
            + " fc " + SQL_WHERE + "fc." + IDuoclassDatabaseConstants.DELETE_STOP + SQL_IS + SQL_NULL + SQL_OR + "fc."
            + IDuoclassDatabaseConstants.DELETE_STOP + SQL_EQUAL + "'N'" + SQL_BRACE_CLOSE;

    private static String DELETE_UNUSED_DUO_KO_ITEM =
        SQL_DELETE + SQL_FROM + IDuoKoItemDatabaseConstants.TABLE_DUO_KO + " f " + SQL_WHERE + " f."
            + IDuoKoItemDatabaseConstants.DUOCLASS + SQL_IN + SQL_BRACE_OPEN + SQL_FROM + TABLE_DUOCLASS + " fc "
            + SQL_WHERE + "fc." + IDuoclassDatabaseConstants.DELETE_STOP + SQL_IS + SQL_NULL + SQL_OR + "fc."
            + IDuoclassDatabaseConstants.DELETE_STOP + SQL_EQUAL + "'N'" + SQL_BRACE_CLOSE;

    private static String UPDATE_UNUSED_DUOCLASS =
        SQL_UPDATE + TABLE_DUOCLASS + SQL_SET + IDuoclassDatabaseConstants.INUSE + SQL_EQUAL + "'N'" + SQL_WHERE
            + IDuoclassDatabaseConstants.DELETE_STOP + SQL_IS + SQL_NULL + SQL_OR
            + IDuoclassDatabaseConstants.DELETE_STOP + SQL_EQUAL + "'N'";

    /**
     * delete of all duoclasses, poolentries and fights, which are not in use
     */
    public void removeUnusedDuoclasses()
        throws JJWDataLayerException
    {

        try
        {
            // delete Fights
            Query q1 = getSession().createQuery( DELETE_UNUSED_FIGHTS );
            q1.executeUpdate();

            // delete poolentries
            Query q2 = getSession().createQuery( DELETE_UNUSED_DUO_SIMPLE_POOL_ITEM );
            q2.executeUpdate();

            Query q4 = getSession().createQuery( DELETE_UNUSED_DUO_DOUBLE_POOL_ITEM );
            q4.executeUpdate();

            Query q5 = getSession().createQuery( DELETE_UNUSED_DUO_KO_ITEM );
            q5.executeUpdate();
            // delete pools
            Query q3 = getSession().createQuery( UPDATE_UNUSED_DUOCLASS );
            q3.executeUpdate();
        }
        catch ( Exception e )
        {
            log.error( "can not removeUnusedDuoclasses", e );
            throw new JJWDataLayerException( e );
        }

    }

    private static String DELETE_UNUSED_FIGHTS_PER_DUOCLASS_ID = "DELETE FROM " + TABLE_DUO_FIGHT + " f WHERE f."
        + IDuoFightDatabaseConstants.DUOCLASS_ID + " =?";

    private static String DELETE_UNUSED_DUO_SIMPLE_POOL_ITEM_PER_DUOCLASS_ID = SQL_DELETE + SQL_FROM
        + IDuoSimplePoolItemDatabaseConstants.TABLE_DUO_SIMPLE_POOL + " f " + SQL_WHERE + " f."
        + IDuoSimplePoolItemDatabaseConstants.DUOCLASS_ID + "=?";

    private static String DELETE_UNUSED_DUO_DOUBLE_POOL_ITEM_PER_DUOCLASS_ID = SQL_DELETE + SQL_FROM
        + IDuoDoublePoolItemDatabaseConstants.TABLE_DUO_DOUBLE_POOL + " f " + SQL_WHERE + " f."
        + IDuoDoublePoolItemDatabaseConstants.DUOCLASS_ID + "=?";

    private static String DELETE_UNUSED_DUO_KO_ITEM_PER_DUOCLASS_ID = SQL_DELETE + SQL_FROM
        + IDuoKoItemDatabaseConstants.TABLE_DUO_KO + " f " + SQL_WHERE + " f."
        + IDuoKoItemDatabaseConstants.DUOCLASS_ID + "=?";

    private static String UPDATE_UNUSED_DUOCLASS_PER_DUOCLASS_ID = SQL_UPDATE + TABLE_DUOCLASS + SQL_SET
        + IDuoclassDatabaseConstants.INUSE + SQL_EQUAL + "'N'" + SQL_WHERE + IDuoclassDatabaseConstants.ID + "=?";

    /**
     * delete of duoclass, poolentries and fights
     */
    public void removeUnusedDuoclass(long duoclass_id)
        throws JJWDataLayerException
    {
        try
        {
            // delete Fights
            Query q1 = getSession().createQuery( DELETE_UNUSED_FIGHTS_PER_DUOCLASS_ID );
            q1.setLong( 0, duoclass_id );
            q1.executeUpdate();

            // delete poolentries
            Query q2 = getSession().createQuery( DELETE_UNUSED_DUO_SIMPLE_POOL_ITEM_PER_DUOCLASS_ID );
            q2.setLong( 0, duoclass_id );
            q2.executeUpdate();

            Query q4 = getSession().createQuery( DELETE_UNUSED_DUO_DOUBLE_POOL_ITEM_PER_DUOCLASS_ID );
            q4.setLong( 0, duoclass_id );
            q4.executeUpdate();

            Query q5 = getSession().createQuery( DELETE_UNUSED_DUO_KO_ITEM_PER_DUOCLASS_ID );
            q5.setLong( 0, duoclass_id );
            q5.executeUpdate();
            // delete pools
            Query q3 = getSession().createQuery( UPDATE_UNUSED_DUOCLASS_PER_DUOCLASS_ID );
            q3.setLong( 0, duoclass_id );
            q3.executeUpdate();
        }
        catch ( Exception e )
        {
            log.error( "can not removeUnusedDuoclass", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String GET_DELETE_STOPPED_DUOCLASSES =
        SQL_FROM + TABLE_DUOCLASS + " w " + SQL_WHERE + "w." + IDuoclassDatabaseConstants.DELETE_STOP + SQL_IS
            + SQL_NOT + SQL_NULL + SQL_AND + "w." + IDuoclassDatabaseConstants.DELETE_STOP + SQL_EQUAL + "'Y'";

    /**
     * get all classes existing classes these are marked "inuse" in the duoclass table
     */
    public List<Duoclass> getDeleteStoppedDuoclasses()
    {
        try
        {
            Query q = getSession().createQuery( GET_DELETE_STOPPED_DUOCLASSES );

            return q.list();

        }
        catch ( Exception e )
        {
            log.error( "can not getExistingDuoclasses", e );
            return null;
        }
    }

    private static String GET_INUSE_DUOCLASSES_WITH_ORDER_BY_DEFAULT =
        SQL_SELECT
            + " new Duoclass(w, "
            + " (select count(distinct f.id) from DuoTeam f where f.duoclass = w.id and f.ready=1 ),"
            +
            // number of teams in class
            " (SELECT COUNT(DISTINCT fl.id) FROM DuoFight fl WHERE fl.duoclass = w.id ),"
            +

            " (SELECT COUNT(DISTINCT fl.id) FROM DuoFight fl WHERE fl.duoclass = w.id AND fl.winnerId IS NOT NULL AND fl.winnerId >= 0 "
            + "    AND fl.teamIdBlue not in ( ? )),"
            +

            "(select s.fightsystemType from Fightsystem s where s.minParticipant<="
            + "  (select count(distinct f.id) from DuoTeam f where f.duoclass = w.id and f.ready =1) AND s.maxParticipant>="
            + "  (select count(distinct f.id) from DuoTeam f where f.duoclass = w.id and f.ready =1) ),"
            + " (select min(fl.createDate) from DuoFight fl where fl.duoclass = w.id), "
            + " (select min(fl.modificationDate) from DuoFight fl where fl.duoclass = w.id and fl.modificationDate > fl.createDate) "
            + ")" + SQL_FROM + TABLE_DUOCLASS + " w " + SQL_WHERE + "w." + IDuoclassDatabaseConstants.INUSE + SQL_IS
            + SQL_NOT + SQL_NULL + SQL_AND + "w." + IDuoclassDatabaseConstants.INUSE + SQL_EQUAL + "'Y'" + SQL_ORDER_BY
            + "w.age." + ORDER_NUMBER + " , " + "w." + IDuoclassDatabaseConstants.AGE + "," + "w."
            + IDuoclassDatabaseConstants.SEX;

    private static String W_DOT = " w.";

    private static String GET_INUSE_DUOCLASSES =
        SQL_FROM + TABLE_DUOCLASS + " w " + SQL_WHERE + "w." + IDuoclassDatabaseConstants.INUSE + SQL_IS + SQL_NOT
            + SQL_NULL + SQL_AND + "w." + IDuoclassDatabaseConstants.INUSE + SQL_EQUAL + "'Y'" + SQL_ORDER_BY + "w."
            + IDuoclassDatabaseConstants.AGE + "," + "w." + IDuoclassDatabaseConstants.SEX;

    /**
     * get all duoclasses, that are in Use all parameter in the list, which correspond to a column will be use for order
     * by. Beginning with die first element (0) by Integer and than the second ....
     * <p/>
     * e.g. (0,1, ..
     * 
     * @return Duoclasses
     */
    public List<Duoclass> getInUseDuoclasses( Map<Integer, SortDTO> sortParameter )
        throws JJWDataLayerException
    {
        try
        {
            Query q = null;
            if ( sortParameter == null || sortParameter.size() == 0 )
            {
                q = getSession().createQuery( GET_INUSE_DUOCLASSES_WITH_ORDER_BY_DEFAULT );
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
                    if ( Duoclass.PROPERTY_MAP.get( dto.getDatabaseField() ) != null )
                    {
                        if ( sb == null )
                        {
                            sb = new StringBuffer( SQL_ORDER_BY );
                            sb.append( W_DOT ).append( Duoclass.PROPERTY_MAP.get( dto.getDatabaseField() ) );
                        }
                        else
                        {
                            sb.append( "," );
                            sb.append( W_DOT ).append( Duoclass.PROPERTY_MAP.get( dto.getDatabaseField() ) );
                        }

                        // asc or desc
                        if ( !dto.isAcending() )
                        {
                            sb.append( SQL_DESC );
                        }
                    }

                    i++;
                }
                q = getSession().createQuery( GET_INUSE_DUOCLASSES + sb.toString() );
            }

            return q.list();

        }
        catch ( Exception e )
        {
            log.error( "can not InuseDuoclasses", e );
            throw new JJWDataLayerException( e );
        }
    }

    public void setDuoclassResults( Map<Long, Integer> resultMap, Long duoclassId )
        throws JJWDataLayerException
    {
        try
        {

            Query q = getSession().createQuery( "from DuoTeam f where duoclass = ? AND f.ready = '1'" );
            q.setLong( 0, duoclassId );
            List<DuoTeam> list = q.list();
            for ( DuoTeam duoTeam : list )
            {
                if ( resultMap.containsKey( duoTeam.getId() ) )
                {
                    duoTeam.setPlace( resultMap.get( duoTeam.getId() ) );
                }
            }

            getHibernateTemplate().saveOrUpdateAll( list );

        }
        catch ( Exception e )
        {
            log.error( "can not setDuoclassResults", e );
            throw new JJWDataLayerException( e );
        }

    }

    public void resetDuoclassResults( Long duoclassId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( "update DuoTeam set place = 0 where duoclass = ? " );
            q.setLong( 0, duoclassId );
            q.executeUpdate();
        }
        catch ( Exception e )
        {
            log.error( "can not setResetDuoclassResults", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String IS_DUOCLASS_A_SIMPLE_POOL_TYPE =
        SQL_FROM + IDuoSimplePoolItemDatabaseConstants.TABLE_DUO_SIMPLE_POOL + " f" + SQL_WHERE + "f."
            + IDuoSimplePoolItemDatabaseConstants.DUOCLASS_ID + SQL_EQUAL + SQL_QUESTION_MARK;

    private static String IS_DUOCLASS_A_DOUBLE_POOL_TYPE =
        SQL_FROM + IDuoDoublePoolItemDatabaseConstants.TABLE_DUO_DOUBLE_POOL + " f" + SQL_WHERE + "f."
            + IDuoDoublePoolItemDatabaseConstants.DUOCLASS_ID + SQL_EQUAL + SQL_QUESTION_MARK;

    private static String IS_DUOCLASS_A_DOUBLE_KO_TYPE =
        SQL_FROM + IDuoKoItemDatabaseConstants.TABLE_DUO_KO + " f" + SQL_WHERE + "f."
            + IDuoKoItemDatabaseConstants.DUOCLASS_ID + SQL_EQUAL + SQL_QUESTION_MARK;

    public int getFightsystemOfDuoclass( long duoclassId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = null;
            q = getSession().createQuery( IS_DUOCLASS_A_SIMPLE_POOL_TYPE );
            q.setLong( 0, duoclassId );
            if ( !q.list().isEmpty() )
            {
                return Fightsystem.SIMPLE_POOL;
            }
            q = getSession().createQuery( IS_DUOCLASS_A_DOUBLE_POOL_TYPE );
            q.setLong( 0, duoclassId );
            if ( !q.list().isEmpty() )
            {
                return Fightsystem.DOUBLE_POOL;
            }

            q = getSession().createQuery( IS_DUOCLASS_A_DOUBLE_KO_TYPE );
            q.setLong( 0, duoclassId );
            if ( !q.list().isEmpty() )
            {
                return Fightsystem.DOUBLE_KO;
            }

            // TODO andere Poolarten

            return TypeUtil.INT_DEFAULT;
        }
        catch ( Exception e )
        {
            log.error( "can not getFightsystemOfDuoclass", e );
            throw new JJWDataLayerException( e );
        }

    }

    private static String DUO_CERTIFICATION_I =
        "from " + IDuoTeamDatabaseConstants.TABLE_DUOTEAM + " f where f." + IDuoTeamDatabaseConstants.DUOCLASS
 + " = ?  and f." + IDuoTeamDatabaseConstants.PLACE + " is not null and f."
        + IDuoTeamDatabaseConstants.PLACE + " > 0 and f." + IDuoTeamDatabaseConstants.PLACE + " <=? AND f."
        + IDuoTeamDatabaseConstants.READY + " = '1' order by f." + IDuoTeamDatabaseConstants.PLACE;

    private static String DUO_CERTIFICATION_II =
 "from " + IDuoclassDatabaseConstants.TABLE_DUOCLASS
        + " f left join fetch f.age a where f.id = ? ";

    public DuoCertification getDuoCertification( Long duoclassId )
        throws JJWDataLayerException
    {
        try
        {
            DuoCertification ret = new DuoCertification();
            ConfigJJW config = (ConfigJJW) getHibernateTemplate().get( ConfigJJW.class, TypeUtil.LONG_1 );
            Query q = getSession().createQuery( DUO_CERTIFICATION_I );
            q.setLong( 0, duoclassId );
            q.setInteger( 1, config.getCertificationPlaces() );
            List<DuoTeam> duoTeamList = q.list();

            ret.setDuoTeamList( duoTeamList );

            Query q1 = getSession().createQuery( DUO_CERTIFICATION_II );
            q1.setLong( 0, duoclassId );
            List<Duoclass> duoclassList = q1.list();
            if ( duoclassList != null && duoclassList.size() > TypeUtil.INT_DEFAULT )
            {
                ret.setDuoclassCaption( duoclassList.get( 0 ).getDuoclassName() );
                ret.setSex( duoclassList.get( 0 ).getSex() );
                ret.setAge( duoclassList.get( 0 ).getAge().getAgeShort() );
            }

            return ret;
        }
        catch ( Exception e )
        {
            log.error( "can not getDuoCertification", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String SQL_CHANGE_DUOTEAM_IN_FIGHTS_1 =
        " From " + TABLE_DUO_FIGHT + SQL_WHERE + IDuoFightDatabaseConstants.TEAM_ID_RED + "=?";

    private static String SQL_CHANGE_DUOTEAM_IN_FIGHTS_2 =
        " From " + TABLE_DUO_FIGHT + SQL_WHERE + IDuoFightDatabaseConstants.TEAM_ID_BLUE + "=?";

    private static String SQL_CHANGE_DUOTEAMS_IN_FIGHTS_UPDATE_RED =
        " UPDATE " + IDuoFightDatabaseConstants.SQL_TABLE_DUO_FIGHT + SQL_SET + IDuoFightDatabaseConstants.TEAM_ID_RED
 + "=? , "
        + IDuoFightDatabaseConstants.MODIFICATION_DATE + "=?  WHERE " + ID + " in (";

    private static String SQL_CHANGE_DUOTEAMS_IN_FIGHTS_UPDATE_BLUE =
        " UPDATE " + IDuoFightDatabaseConstants.SQL_TABLE_DUO_FIGHT + SQL_SET + IDuoFightDatabaseConstants.TEAM_ID_BLUE
 + "=? , "
        + IDuoFightDatabaseConstants.MODIFICATION_DATE + "=?  WHERE " + ID + " in (";

    /**
     * change the teamIds in the fights
     * 
     * @param duoTeam1Id
     * @param duoTeamId
     * @throws JJWDataLayerException
     */
    private void changeDuoTeamsInFights( long duoTeam1Id, long duoTeamId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( SQL_CHANGE_DUOTEAM_IN_FIGHTS_1 );
            q.setLong( 0, duoTeam1Id );
            List<DuoFight> list1 = q.list();
            StringBuffer inClause1 = new StringBuffer( 32 );

            Query q2 = getSession().createQuery( SQL_CHANGE_DUOTEAM_IN_FIGHTS_2 );
            q2.setLong( 0, duoTeam1Id );
            List<DuoFight> list2 = q2.list();
            StringBuffer inClause2 = new StringBuffer( 32 );

            for ( DuoFight fight : list1 )
            {
                if ( inClause1.length() > 1 )
                {
                    inClause1.append( COMMA );
                }
                inClause1.append( fight.getId() );
            }

            for ( DuoFight fight : list2 )
            {
                if ( inClause2.length() > 1 )
                {
                    inClause2.append( COMMA );
                }
                inClause2.append( fight.getId() );
            }

            // fighter 2 lists
            q = getSession().createQuery( SQL_CHANGE_DUOTEAM_IN_FIGHTS_1 );
            q.setLong( 0, duoTeamId );
            list1 = q.list();
            StringBuffer inClause3 = new StringBuffer( 32 );

            q2 = getSession().createQuery( SQL_CHANGE_DUOTEAM_IN_FIGHTS_2 );
            q2.setLong( 0, duoTeamId );
            list2 = q2.list();
            StringBuffer inClause4 = new StringBuffer( 32 );

            for ( DuoFight fight : list1 )
            {
                if ( inClause3.length() > 1 )
                {
                    inClause3.append( COMMA );
                }
                inClause3.append( fight.getId() );
            }

            for ( DuoFight fight : list2 )
            {
                if ( inClause4.length() > 1 )
                {
                    inClause4.append( COMMA );
                }
                inClause4.append( fight.getId() );
            }

            // update the fights
            // set duoteam 2
            if ( inClause1.length() > 0 )
            {
                StringBuffer sb = new StringBuffer( 100 );
                sb.append( SQL_CHANGE_DUOTEAMS_IN_FIGHTS_UPDATE_RED );
                sb.append( inClause1 );
                sb.append( ")" );

                q = getSession().createSQLQuery( sb.toString() );
                q.setLong( 0, duoTeamId );
                q.setTimestamp( 1, new Timestamp( System.currentTimeMillis() ) );
                q.executeUpdate();
            }

            if ( inClause2.length() > 0 )
            {
                StringBuffer sb = new StringBuffer( 100 );
                sb.append( SQL_CHANGE_DUOTEAMS_IN_FIGHTS_UPDATE_BLUE );
                sb.append( inClause2 );
                sb.append( ")" );

                q = getSession().createSQLQuery( sb.toString() );
                q.setLong( 0, duoTeamId );
                q.setTimestamp( 1, new Timestamp( System.currentTimeMillis() ) );
                q.executeUpdate();
            }

            // set duoteam 1
            if ( inClause3.length() > 0 )
            {
                StringBuffer sb = new StringBuffer( 100 );
                sb.append( SQL_CHANGE_DUOTEAMS_IN_FIGHTS_UPDATE_RED );
                sb.append( inClause3 );
                sb.append( ")" );

                q = getSession().createSQLQuery( sb.toString() );
                q.setLong( 0, duoTeam1Id );
                q.setTimestamp( 1, new Timestamp( System.currentTimeMillis() ) );
                q.executeUpdate();
            }

            if ( inClause4.length() > 0 )
            {
                StringBuffer sb = new StringBuffer( 100 );
                sb.append( SQL_CHANGE_DUOTEAMS_IN_FIGHTS_UPDATE_BLUE );
                sb.append( inClause4 );
                sb.append( ")" );

                q = getSession().createSQLQuery( sb.toString() );
                q.setLong( 0, duoTeam1Id );
                q.setTimestamp( 1, new Timestamp( System.currentTimeMillis() ) );
                q.executeUpdate();
            }

        }
        catch ( Exception e )
        {
            log.error( "can not changeDuoTeamsInFights", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String SQL_CHANGE_DUOTEAMS_IN_POOL =
        SQL_UPDATE + IDuoSimplePoolItemDatabaseConstants.SQL_TABLE_DUO_SIMPLE_POOL + SQL_SET
        + IDuoSimplePoolItemDatabaseConstants.DUOTEAM_ID + " =? , " + IDuoTeamDatabaseConstants.MODIFICATION_DATE
        + "=? " + SQL_WHERE + ID + " =? ";

    public void changeDuoTeamsInPool( int duoTeam1, int duoteam2, Duoclass duoclass )
        throws JJWDataLayerException
    {
        try
        {
            DuoSimplePoolClass fc = getDuoclassSimplePool( duoclass.getId() );
            long poolId1 = TypeUtil.LONG_EMPTY;
            long poolId2 = TypeUtil.LONG_EMPTY;
            long duoTeamId1 = TypeUtil.LONG_EMPTY;
            long duoTeamId2 = TypeUtil.LONG_EMPTY;

            for ( DuoSimplePoolItem item : fc.getDuoTeamList() )
            {
                if ( item.getNumberInPool() == duoTeam1 - 1 )
                {
                    duoTeamId1 = item.getDuoTeamId();
                    poolId1 = item.getId();
                }

                if ( item.getNumberInPool() == duoteam2 - 1 )
                {
                    duoTeamId2 = item.getDuoTeamId();
                    poolId2 = item.getId();
                }
            }

            Query q = getSession().createSQLQuery( SQL_CHANGE_DUOTEAMS_IN_POOL );
            q.setLong( 0, duoTeamId1 );
            q.setTimestamp( 1, new Timestamp( System.currentTimeMillis() ) );
            q.setLong( 2, poolId2 );
            q.executeUpdate();

            q = getSession().createSQLQuery( SQL_CHANGE_DUOTEAMS_IN_POOL );
            q.setLong( 0, duoTeamId2 );
            q.setTimestamp( 1, new Timestamp( System.currentTimeMillis() ) );
            q.setLong( 2, poolId1 );
            q.executeUpdate();

            changeDuoTeamsInFights( duoTeamId1, duoTeamId2 );
        }
        catch ( Exception e )
        {
            log.error( "can not changeDuoTeamInPool", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String SQL_CHANGE_DUOTEAM_IN_D_POOL =
        SQL_UPDATE + IDuoDoublePoolItemDatabaseConstants.SQL_TABLE_DUO_DOUBLE_POOL + SQL_SET
        + IDuoDoublePoolItemDatabaseConstants.DUOTEAM_ID + " =? , " + IDuoTeamDatabaseConstants.MODIFICATION_DATE
        + "=? " + SQL_WHERE + ID + " =? ";

    public void changeDuoTeamsInDPool( int duoTeam1, int duoTeam2, Duoclass duoclass )
        throws JJWDataLayerException
    {
        try
        {
            int[] helpArray = { 0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4 };
            DuoDoublePoolClass fc = getDuoclassDoublePool( duoclass.getId() );
            long poolId1 = TypeUtil.LONG_EMPTY;
            long poolId2 = TypeUtil.LONG_EMPTY;
            long duoTeamId1 = TypeUtil.LONG_EMPTY;
            long duoTeamId2 = TypeUtil.LONG_EMPTY;

            if ( duoTeam1 % 2 == 1 )
            {
                for ( DuoDoublePoolItem item : fc.getDuoListPoolA() )
                {
                    if ( item.getNumberInPool() == helpArray[duoTeam1] )
                    {
                        duoTeamId1 = item.getDuoTeamId();
                        poolId1 = item.getId();
                        break;
                    }
                }
            }
            if ( duoTeam2 % 2 == 1 )
            {
                for ( DuoDoublePoolItem item : fc.getDuoListPoolA() )
                {
                    if ( item.getNumberInPool() == helpArray[duoTeam2] )
                    {
                        duoTeamId2 = item.getDuoTeamId();
                        poolId2 = item.getId();
                        break;
                    }
                }
            }

            if ( duoTeam1 % 2 == 0 )
            {
                for ( DuoDoublePoolItem item : fc.getDuoListPoolB() )
                {
                    if ( item.getNumberInPool() == helpArray[duoTeam1] )
                    {
                        duoTeamId1 = item.getDuoTeamId();
                        poolId1 = item.getId();
                        break;
                    }
                }
            }
            if ( duoTeam2 % 2 == 0 )
            {
                for ( DuoDoublePoolItem item : fc.getDuoListPoolB() )
                {
                    if ( item.getNumberInPool() == helpArray[duoTeam2] )
                    {
                        duoTeamId2 = item.getDuoTeamId();
                        poolId2 = item.getId();
                        break;
                    }
                }
            }

            Query q = getSession().createSQLQuery( SQL_CHANGE_DUOTEAM_IN_D_POOL );
            q.setLong( 0, duoTeamId1 );
            q.setTimestamp( 1, new Timestamp( System.currentTimeMillis() ) );
            q.setLong( 2, poolId2 );
            q.executeUpdate();

            q = getSession().createSQLQuery( SQL_CHANGE_DUOTEAM_IN_D_POOL );
            q.setLong( 0, duoTeamId2 );
            q.setTimestamp( 1, new Timestamp( System.currentTimeMillis() ) );
            q.setLong( 2, poolId1 );
            q.executeUpdate();

            changeDuoTeamsInFights( duoTeamId1, duoTeamId2 );
        }
        catch ( Exception e )
        {
            log.error( "can not changeDuoTeamInDPool", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String SQL_CHANGE_DUOTEAMS_IN_KO =
        SQL_UPDATE + IDuoKoItemDatabaseConstants.SQL_TABLE_DUO_KO + SQL_SET + IDuoKoItemDatabaseConstants.DUOTEAM_ID
 + " =? , " + IDuoTeamDatabaseConstants.MODIFICATION_DATE
        + "=? " + SQL_WHERE + ID + " =? ";

    public void changeDuoTeamsInKo( int duoTeam1, int duoTeam2, Duoclass duoclass )
        throws JJWDataLayerException
    {
        DuoKoClass fc = getDuoclassKo( duoclass.getId() );
        int[] helpArray =
            { 0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 11, 11, 12, 12, 13, 13, 14, 14,
                15, 15, 16, 16 };
        long poolId1 = TypeUtil.LONG_EMPTY;
        long poolId2 = TypeUtil.LONG_EMPTY;
        long duoTeamId1 = TypeUtil.LONG_EMPTY;
        long duoTeamId2 = TypeUtil.LONG_EMPTY;
        try
        {
            if ( ( duoTeam1 % 2 ) == 1 )
            {
                for ( DuoKoItem item : fc.getTeamListPoolA() )
                {
                    if ( item.getNumberInPool() == helpArray[duoTeam1] )
                    {
                        duoTeamId1 = item.getDuoTeamId();
                        poolId1 = item.getId();
                        break;
                    }
                }
            }

            if ( ( duoTeam2 % 2 ) == 1 )
            {
                for ( DuoKoItem item : fc.getTeamListPoolA() )
                {
                    if ( item.getNumberInPool() == helpArray[duoTeam2] )
                    {
                        duoTeamId2 = item.getDuoTeamId();
                        poolId2 = item.getId();
                        break;
                    }
                }
            }

            if ( ( duoTeam1 % 2 ) == 0 )
            {
                for ( DuoKoItem item : fc.getTeamListPoolB() )
                {
                    if ( item.getNumberInPool() == helpArray[duoTeam1] )
                    {
                        duoTeamId1 = item.getDuoTeamId();
                        poolId1 = item.getId();
                        break;
                    }
                }
            }

            if ( ( duoTeam2 % 2 ) == 0 )
            {
                for ( DuoKoItem item : fc.getTeamListPoolB() )
                {
                    if ( item.getNumberInPool() == helpArray[duoTeam2] )
                    {
                        duoTeamId2 = item.getDuoTeamId();
                        poolId2 = item.getId();
                        break;
                    }
                }
            }
            Query q = getSession().createSQLQuery( SQL_CHANGE_DUOTEAMS_IN_KO );
            q.setLong( 0, duoTeamId1 );
            q.setTimestamp( 1, new Timestamp( System.currentTimeMillis() ) );
            q.setLong( 2, poolId2 );
            q.executeUpdate();

            q = getSession().createSQLQuery( SQL_CHANGE_DUOTEAMS_IN_KO );
            q.setLong( 0, duoTeamId2 );
            q.setTimestamp( 1, new Timestamp( System.currentTimeMillis() ) );
            q.setLong( 2, poolId1 );
            q.executeUpdate();

            changeDuoTeamsInFights( duoTeamId1, duoTeamId2 );
        }
        catch ( Exception e )
        {
            log.error( "can not changeDuoTeamInKo", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String GET_NUMBER_OF_DUOTEAMS_IN_DUOCLASS =
        SQL_SELECT + SQL_COUNT_1 + SQL_FROM + IDuoTeamDatabaseConstants.SQL_TABLE_DUOTEAM + SQL_WHERE
 + IDuoTeamDatabaseConstants.DUOCLASS + "=? AND "
        + IDuoTeamDatabaseConstants.READY + "=1";

    public int getNumberOfDuoTeamsInDuoclass( long duoclassId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createSQLQuery( GET_NUMBER_OF_DUOTEAMS_IN_DUOCLASS );
            q.setLong( 0, duoclassId );
            return ( (BigInteger) q.uniqueResult() ).intValue();
        }
        catch ( Exception e )
        {
            log.error( "can not getNumberOfDuoTeamsInDuoclass", e );
            throw new JJWDataLayerException( e );
        }
    }

    public void removeDuoclass( Duoclass duoclass )
        throws JJWDataLayerException
    {
        getHibernateTemplate().delete( getDuoclass( duoclass.getId() ) );

    }

    private static String DUOCLASS_IN_TEAM_USE =
        "from " + TABLE_DUOTEAM + " f where f." + IDuoTeamDatabaseConstants.DUOCLASS + " = ?";

    public boolean isDuoclassInUse( Long duoclassId )
    {
        Query q = getSession().createQuery( DUOCLASS_IN_TEAM_USE );
        q.setLong( 0, duoclassId );

        return !q.list().isEmpty();

    }

    public boolean isDuoclassInUse( Duoclass duoclassId )
    {
        return isDuoclassInUse( duoclassId.getId() );
    }

    /**
     * return : Duoclass, numberOfFighterInClass, numberOfFights, completedFights, fightsystem,averageFighttimeInClass
     */
    private static String GET_INUSE_DUOCLASSES_WITH_ORDER_BY_DEFAULT_FOR_TATAMI =
        SQL_SELECT
            + " new Duoclass(w, "
            + " (select count(distinct f.id) from DuoTeam f where f.duoclass = w.id and f.ready=1 ),"
            +
            // number of teams in class
            " (SELECT COUNT(DISTINCT fl.id) FROM DuoFight fl WHERE fl.duoclass = w.id ),"
            +

            " (SELECT COUNT(DISTINCT fl.id) FROM DuoFight fl WHERE fl.duoclass = w.id AND fl.winnerId IS NOT NULL AND fl.winnerId >= 0 "
            + "    AND fl.teamIdBlue not in ( ? )),"
            +

            "(select s.fightsystemType from Fightsystem s where s.minParticipant<="
            + "  (select count(distinct f.id) from DuoTeam f where f.duoclass = w.id and f.ready =1) AND s.maxParticipant>="
            + "  (select count(distinct f.id) from DuoTeam f where f.duoclass = w.id and f.ready =1) ),"
            + " (select min(fl.createDate) from DuoFight fl where fl.duoclass = w.id), "
            + " (select min(fl.modificationDate) from DuoFight fl where fl.duoclass = w.id and fl.modificationDate > fl.createDate) "
            + ")" + SQL_FROM + TABLE_DUOCLASS + " w " + SQL_WHERE + "w." + IDuoclassDatabaseConstants.INUSE + SQL_IS
            + SQL_NOT + SQL_NULL + SQL_AND + "w." + IDuoclassDatabaseConstants.INUSE + SQL_EQUAL + "'Y'" + SQL_ORDER_BY
            + "w.age." + ORDER_NUMBER + " , " + "w." + IDuoclassDatabaseConstants.AGE + "," + "w."
            + IDuoclassDatabaseConstants.SEX;

    private static String GET_INUSE_DUOCLASSES_WITH_USER_ACCESS =
        "SELECT u." + IUserDuoclassDatabaseConstants.DUOCLASS_ID + " from UserDuoclass u WHERE u."
            + IUserDuoclassDatabaseConstants.USER_ID + "=?";

    /**
     * get all duoclasses, that are in Use all parameter in the list, which correspond to a column will be use for order
     * by. Beginning with die first element (0) by Integer and than the second ....
     * <p/>
     * e.g. (0,1, ..
     * 
     * @return Duoclasses
     */
    public List<Duoclass> getInUseDuoclassesForTatami( Map<Integer, SortDTO> sortParameter, Long accountId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = null;
            if ( sortParameter == null || sortParameter.size() == 0 )
            {
                q = getSession().createQuery( GET_INUSE_DUOCLASSES_WITH_ORDER_BY_DEFAULT_FOR_TATAMI );
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
                    if ( Duoclass.PROPERTY_MAP.get( dto.getDatabaseField() ) != null )
                    {
                        if ( sb == null )
                        {
                            sb = new StringBuffer( SQL_ORDER_BY );
                            sb.append( W_DOT ).append( Duoclass.PROPERTY_MAP.get( dto.getDatabaseField() ) );
                        }
                        else
                        {
                            sb.append( COMMA );
                            sb.append( W_DOT ).append( Duoclass.PROPERTY_MAP.get( dto.getDatabaseField() ) );
                        }

                        // asc or desc
                        if ( !dto.isAcending() )
                        {
                            sb.append( SQL_DESC );
                        }
                    }

                    i++;
                }
                q = getSession().createQuery( GET_INUSE_DUOCLASSES + sb.toString() );
            }

            List<Duoclass> fcList = q.list();
            Query q2 = getSession().createQuery( GET_INUSE_DUOCLASSES_WITH_USER_ACCESS );
            q2.setLong( 0, accountId );
            List<Long> allowedList = q2.list();
            List<Duoclass> retList = new ArrayList<Duoclass>();
            for ( Duoclass fc : fcList )
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
            log.error( "can not InuseDuoclasses", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String SQL_GET_CHILD_DUOCLASSES = " from " + IDuoclassDatabaseConstants.TABLE_DUOCLASS
        + " f WHERE f." + IDuoclassDatabaseConstants.PARENT + " = ?";

    @Override
    public List<Duoclass> getChildDuoclasses( Long duoclassId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( SQL_GET_CHILD_DUOCLASSES );
            q.setLong( 0, duoclassId );
            return q.list();
        }
        catch ( Exception e )
        {
            log.error( "can not getChildDuoclassclasses", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String SQL_GET_COMBINABLE_DUOCLASSES = "SELECT new Duoclass(f, "
        + " (select count(distinct fi.id) from DuoTeam fi where fi.duoclass = f.id ),"
        +
        // number of fighter in class
        " (select count(distinct fi.id) from DuoTeam fi where fi.duoclass = f.id and fi.ready=1)"
        +
        // number of fighter in class which are ready
        " ) " + " from " + IDuoclassDatabaseConstants.TABLE_DUOCLASS + " f left join  f."
        + IDuoclassDatabaseConstants.AGE + "  a " + " WHERE (f." + IDuoclassDatabaseConstants.PARENT + " is null or "
        + "f." + IDuoclassDatabaseConstants.PARENT + "<0) and f." + IDuoclassDatabaseConstants.ID + " <> ? "
        + " and (f." + IDuoclassDatabaseConstants.DELETE_STOP + " is null or f."
        + IDuoclassDatabaseConstants.DELETE_STOP
        + "='N') "
        // // and f.id not in (select ff.parent from Duoclass ff)
        + " and f."
        + IDuoclassDatabaseConstants.ID
        + " not in (select ff."
        + IDuoclassDatabaseConstants.PARENT
        + " from " + IDuoclassDatabaseConstants.TABLE_DUOCLASS + " ff)"
        //
        + " order by a." + START_AGE + " desc, f."
        + IDuoclassDatabaseConstants.SEX;

    @Override
    public List<Duoclass> getCombinableDuoclasses( Long duoclassId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( SQL_GET_COMBINABLE_DUOCLASSES );
            q.setLong( 0, duoclassId );
            return q.list();
        }
        catch ( Exception e )
        {
            log.error( "can not getCombinableDuoclasses", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String SQL_GET_DUOTEAM_FOR_NEW_CLASS = " from " + IDuoTeamDatabaseConstants.TABLE_DUOTEAM
        + " f where f." + IDuoTeamDatabaseConstants.DUOCLASS_ID + "= :duoclassId";

    /**
     * adds a duoclass to a parentclass and moves all duoTeams of the child class to the parent class and sets the
     * childclass into original class column. Exception: when one of the classes are isDeleteStop
     */
    @Override
    public void addDuoclassToClass( long parentClass, long childClass )
        throws JJWDataLayerException
    {
        try
        {
            Duoclass parent = (Duoclass) getHibernateTemplate().get( Duoclass.class, parentClass );
            if ( parent.isDeleteStop() )
                throw new Exception( "parent is deleteStop" );

            Duoclass child = (Duoclass) getHibernateTemplate().get( Duoclass.class, childClass );
            if ( child.isDeleteStop() )
                throw new Exception( "child is deleteStop" );

            child.setParentId( parentClass );

            List<DuoTeam> duoList =
                (List<DuoTeam>) getHibernateTemplate().findByNamedParam( SQL_GET_DUOTEAM_FOR_NEW_CLASS, "duoclassId",
                                                                         childClass );

            // set duo to new class
            for ( DuoTeam duoTeam : duoList )
            {
                duoTeam.setOriginalDuoclassId( duoTeam.getDuoclassId() );
                duoTeam.setDuoclassId( parentClass );
            }

        }
        catch ( Exception e )
        {
            log.error( "can not addDuoclassToClass", e );
            throw new JJWDataLayerException( e );
        }

    }

    private static String SQL_GET_DUOTEAM_FOR_ORIGINAL_CLASS = " from " + IDuoTeamDatabaseConstants.TABLE_DUOTEAM
        + " f where f." + IDuoTeamDatabaseConstants.ORIGINAL_DUOCLASS + "=?";

    /**
     * removes a duoclass from its parent and sets for all corresponding duoteams the original duo class Exception: when
     * the classes are isDelteStop
     */
    @Override
    public void removeDuoclassFromParent( long parentClass, long childClass )
        throws JJWDataLayerException
    {
        try
        {
            Duoclass parent = (Duoclass) getHibernateTemplate().get( Duoclass.class, parentClass );
            if ( parent.isDeleteStop() )
                throw new Exception( "parent is deleteStop" );

            Duoclass child = (Duoclass) getHibernateTemplate().get( Duoclass.class, childClass );
            if ( child.isDeleteStop() )
                throw new Exception( "child is deleteStop" );

            child.setParentId( null );

            Query q = getSession().createQuery( SQL_GET_DUOTEAM_FOR_ORIGINAL_CLASS );
            q.setLong( 0, childClass );
            List<DuoTeam> teamList = (List<DuoTeam>) q.list();

            // set duoteam to new class
            for ( DuoTeam duoteam : teamList )
            {
                duoteam.setDuoclassId( duoteam.getOriginalDuoclassId() );
                duoteam.setOriginalDuoclassId( TypeUtil.LONG_MIN );
            }

        }
        catch ( Exception e )
        {
            log.error( "can not removeDuoclassFromParent", e );
            throw new JJWDataLayerException( e );
        }

    }

    private static String DUOCLASS_CHILD_OR_PARENT = "from " + TABLE_DUOCLASS + " f where f."
        + IDuoclassDatabaseConstants.PARENT + " = ?";

    @Override
    public boolean isDuoclassChildOrParent( Long duoclassId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( DUOCLASS_CHILD_OR_PARENT );
            q.setLong( 0, duoclassId );

            if ( q.list().isEmpty() )
            {
                Duoclass fc = getDuoclass( duoclassId );
                if ( null == fc.getParentId() )
                    return false;
                return !TypeUtil.isEmptyOrDefault( fc.getParentId() );
            }
            else
            {
                return true;
            }
        }
        catch ( Exception e )
        {
            log.error( "can not isDuoclassChildOrParent", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String SQL_GET_TIMES_OF_CURRENT_TATAMI_USE =

        "select duoclass_id,if(total is null,0,total) from "
            +

            " (select user_id,sum(mytime) as total from"
            +

            " (select user_id,weightclass_id,(select count(1)* a.estimatedTime from  weightclass w left join age a on w.age=a.id"
            + " left join fight_list f on w.id=f.fightingclass"
            + " where w.id=u.weightclass_id and winnerid<0 and deleteStop='Y' and complete='N' and startTime is  not  null) as mytime"
            +

            " FROM user_weightclass u union"
            +

            " select user_id,duoclass_id,(select count(1)* a.estimatedTimeDuo from duoclass w left join age a on w.age=a.id"
            + " left join duo_fight_list f on w.id=f.duoclass"
            + " where w.id=u2.duoclass_id and winnerid<0 and deleteStop='Y' and complete='N' and startTime is  not  null) as mytime"
            +

            " FROM user_duoclass u2 ) tt group by user_id) t left join user_duoclass u on u.user_id=t.user_Id "
            + " left join duoclass w on duoclass_id=w.id where duoclass_id is not null and startTime is null";

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
