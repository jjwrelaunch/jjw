/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaFighterDaoHibernate.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:48
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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import de.jjw.dao.IDatabaseTableConstants;
import de.jjw.dao.hibernate.BaseDaoHibernate;
import de.jjw.dao.hibernate.ITeamDatabaseConstants;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.dao.hibernate.admin.IAgeDatabaseConstants;
import de.jjw.dao.hibernate.fighting.IFighterDatabaseConstants;
import de.jjw.dao.newa.NewaFighterDao;
import de.jjw.model.fighting.Fighter;
import de.jjw.model.newa.NewaFighter;
import de.jjw.model.newa.Newaclass;
import de.jjw.util.TypeUtil;

public class NewaFighterDaoHibernate
    extends BaseDaoHibernate
    implements NewaFighterDao, IAgeDatabaseConstants, INewaFighterDatabaseConstants, INewaWeightclassDatabaseConstants
{
    // left join fetch f.country left join fetch f.region

    private static String FIGHTERS_ALL = SQL_FROM + TABLE_FIGHTER + " f " + SQL_LEFT + SQL_JOIN + SQL_FETCH + "f."
        + INewaFighterDatabaseConstants.AGE + " a " + SQL_LEFT + SQL_JOIN + SQL_FETCH + "f."
        + INewaFighterDatabaseConstants.NEWACLASS + SQL_LEFT + SQL_JOIN + SQL_FETCH + "f."
        + INewaFighterDatabaseConstants.TEAM + " t " + SQL_LEFT + SQL_JOIN + SQL_FETCH + "t."
        + INewaFighterDatabaseConstants.REGION + SQL_LEFT + SQL_JOIN + SQL_FETCH + "t."
        + INewaFighterDatabaseConstants.COUNTRY + " ";

    private static String FIGHTERS_ALL_ORDER_BY = " order by f." + INewaFighterDatabaseConstants.AGE + " , f."
        + INewaFighterDatabaseConstants.SEX + " , f." + INewaFighterDatabaseConstants.WEIGHTCLASS + " , t."
        + ITeamDatabaseConstants.TEAM_NAME + " , f." + INewaFighterDatabaseConstants.NAME + " , f."
        + INewaFighterDatabaseConstants.FIRSTNAME;

    private static String FIGHTERS_ALL_NOT_REGISTRATED = SQL_FROM + TABLE_FIGHTER + " f " + SQL_LEFT + SQL_JOIN
        + SQL_FETCH + "f." + INewaFighterDatabaseConstants.AGE + " a " + SQL_LEFT + SQL_JOIN + SQL_FETCH + "f."
        + INewaFighterDatabaseConstants.NEWACLASS + SQL_LEFT + SQL_JOIN + SQL_FETCH + "f."
        + INewaFighterDatabaseConstants.TEAM + " t " + SQL_LEFT + SQL_JOIN + SQL_FETCH + "t."
        + INewaFighterDatabaseConstants.REGION + SQL_LEFT + SQL_JOIN + SQL_FETCH + "t."
        + INewaFighterDatabaseConstants.COUNTRY + SQL_WHERE + "f." + INewaFighterDatabaseConstants.READY + SQL_EQUAL
        + "'0'" + SQL_OR + "f." + INewaFighterDatabaseConstants.READY + SQL_IS + SQL_NULL + " order by f."
        + INewaFighterDatabaseConstants.AGE + " , f." + INewaFighterDatabaseConstants.SEX + " , f."
        + INewaFighterDatabaseConstants.WEIGHTCLASS + " , t." + ITeamDatabaseConstants.TEAM_NAME + " , f."
        + INewaFighterDatabaseConstants.NAME + " , f." + INewaFighterDatabaseConstants.FIRSTNAME;

    private static String FIGHTERS_ALL_REGISTRATED = SQL_FROM + TABLE_FIGHTER + " f " + SQL_LEFT + SQL_JOIN + SQL_FETCH
        + "f." + INewaFighterDatabaseConstants.AGE + " a " + SQL_LEFT + SQL_JOIN + SQL_FETCH + "f."
        + INewaFighterDatabaseConstants.NEWACLASS + SQL_LEFT + SQL_JOIN + SQL_FETCH + "f."
        + INewaFighterDatabaseConstants.TEAM + " t " + SQL_LEFT + SQL_JOIN + SQL_FETCH + "t."
        + INewaFighterDatabaseConstants.REGION + SQL_LEFT + SQL_JOIN + SQL_FETCH + "t."
        + INewaFighterDatabaseConstants.COUNTRY + SQL_WHERE + "f." + INewaFighterDatabaseConstants.READY + SQL_EQUAL
        + "'1'" + SQL_OR + "f." + INewaFighterDatabaseConstants.READY + SQL_IS + SQL_NULL + " order by f."
        + INewaFighterDatabaseConstants.AGE + " , f." + INewaFighterDatabaseConstants.SEX + " , f."
        + INewaFighterDatabaseConstants.WEIGHTCLASS + " , t." + ITeamDatabaseConstants.TEAM_NAME + " , f."
        + INewaFighterDatabaseConstants.NAME + " , f." + INewaFighterDatabaseConstants.FIRSTNAME;

    private static String EXIST_FIGHTER = "from NewaFighter f where f.name =? and f.firstname = ? and f.team =?";  // and f.id<>?";

    private static String POSSIBLE_DUBLICATE_FIGHTER = "from NewaFighter f where f.name =? and f.firstname = ?";

    private static String FIGHTERS_BY_WEIGHTCLASS_FOR_DRAW = SQL_FROM + TABLE_FIGHTER + " f " + SQL_WHERE + "f."
        + INewaFighterDatabaseConstants.WEIGHTCLASS + SQL_EQUAL + SQL_QUESTION_MARK + SQL_AND
        + INewaFighterDatabaseConstants.READY + "= 1";

    private static String FIGHTERS_BY_WEIGHTCLASS_DRAWED = SQL_SELECT + "a.id " + SQL_FROM + SQL_TABLE_FIGHTER + " a "
        + " left join team t on a.team=t.id "
        + " left join (select rand() as rand3, f.team from newa_fighter f group by f.team) d on a.team=d.team "
        + "	left join (select rand() as rand2, f.id from region f group by f.id) c on t.region=c.id "
        + "	left join (select rand() as rand1, f.id from country f group by f.id) b on t.country=b.id "
        + "	left join (select rand() as rand4, f.name from newa_fighter f group by f.name) e on a.name=e.name "
        + SQL_WHERE + " a." + INewaFighterDatabaseConstants.WEIGHTCLASS + SQL_EQUAL + SQL_QUESTION_MARK + SQL_AND
        + INewaFighterDatabaseConstants.READY + "= 1" + "	order by rand1,rand2,rand3, rand4 ";

    public NewaFighter getFighter( Long fighterId )
        throws JJWDataLayerException
    {
        try
        {
            return (NewaFighter) getHibernateTemplate().get( NewaFighter.class, fighterId );
        }
        catch ( Exception e )
        {
            throw new JJWDataLayerException( e );
        }
    }

    public List<NewaFighter> getAllFighters()
        throws JJWDataLayerException
    {
        try
        {
            return getHibernateTemplate().find( FIGHTERS_ALL + FIGHTERS_ALL_ORDER_BY );
        }
        catch ( Exception e )
        {
            return null;
        }
    }

    public List<NewaFighter> getAllFightersInOrder( int order, boolean isAscending )
        throws JJWDataLayerException
    {
        try
        {
            StringBuffer sb = new StringBuffer( FIGHTERS_ALL + FIGHTERS_ALL_ORDER_BY );
            sb.append( SQL_ORDER_BY );
            if ( IDatabaseTableConstants.ORDER_BY_NAME == order )
            {
                sb.append( "f." );
                sb.append( INewaFighterDatabaseConstants.NAME );
                if ( !isAscending )
                {
                    sb.append( SQL_DESC );
                }
            }

            if ( IDatabaseTableConstants.ORDER_BY_FIRSTNAME == order )
            {
                sb.append( "f." );
                sb.append( INewaFighterDatabaseConstants.FIRSTNAME );
                if ( !isAscending )
                {
                    sb.append( SQL_DESC );
                }
            }

            if ( IDatabaseTableConstants.ORDER_BY_SEX == order )
            {
                sb.append( "f." );
                sb.append( INewaFighterDatabaseConstants.SEX );
                if ( !isAscending )
                {
                    sb.append( SQL_DESC );
                }
            }

            if ( IDatabaseTableConstants.ORDER_BY_READY == order )
            {
                sb.append( "f." );
                sb.append( INewaFighterDatabaseConstants.READY );
                if ( !isAscending )
                {
                    sb.append( SQL_DESC );
                }
            }

            if ( IDatabaseTableConstants.ORDER_BY_AGE == order )
            {
                sb.append( "a." );
                sb.append( IAgeDatabaseConstants.AGE_SHORT );
                if ( !isAscending )
                {
                    sb.append( SQL_DESC );
                }
            }

            if ( IDatabaseTableConstants.ORDER_BY_TEAM == order )
            {
                sb.append( "t." );
                sb.append( ITeamDatabaseConstants.TEAM_NAME );
                if ( !isAscending )
                {
                    sb.append( SQL_DESC );
                }
            }

            return getHibernateTemplate().find( sb.toString() );
        }
        catch ( Exception e )
        {
            throw new JJWDataLayerException( e );
        }
    }

    private static String FIGHTER_BY_AGE = FIGHTERS_ALL + SQL_WHERE + "f." + INewaFighterDatabaseConstants.AGE + " =?";

    private static String NOT_REG_SUPPORT = SQL_AND + "f." + INewaFighterDatabaseConstants.READY + SQL_EQUAL + "0";

    public List<NewaFighter> getFighterByAge( long ageId, boolean notReg )
        throws JJWDataLayerException
    {
        try
        {
            Query q;
            if ( notReg )
            {
                q = getSession().createQuery( FIGHTER_BY_AGE + NOT_REG_SUPPORT + FIGHTERS_ALL_ORDER_BY );
            }
            else
            {
                q = getSession().createQuery( FIGHTER_BY_AGE + FIGHTERS_ALL_ORDER_BY );
            }
            q.setLong( 0, ageId );

            return q.list();
        }
        catch ( Exception e )
        {
            log.error( "can not getFighterByAge", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String FIGHTER_BY_REGION = FIGHTERS_ALL + SQL_WHERE + "t." + INewaFighterDatabaseConstants.REGION
        + " =?";

    public List<NewaFighter> getFighterByRegion( long regionId, boolean notReg )
        throws JJWDataLayerException
    {
        try
        {
            Query q;
            if ( notReg )
            {
                q = getSession().createQuery( FIGHTER_BY_REGION + NOT_REG_SUPPORT + FIGHTERS_ALL_ORDER_BY );
            }
            else
            {
                q = getSession().createQuery( FIGHTER_BY_REGION + FIGHTERS_ALL_ORDER_BY );
            }
            q.setLong( 0, regionId );

            return q.list();
        }
        catch ( Exception e )
        {
            log.error( "can not getFighterByRegion", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String FIGHTER_BY_COUNTRY = FIGHTERS_ALL + SQL_WHERE + "t." + INewaFighterDatabaseConstants.COUNTRY
        + " =?";

    public List<NewaFighter> getFighterByCountry( long countryId, boolean notReg )
        throws JJWDataLayerException
    {
        try
        {
            Query q;
            if ( notReg )
            {
                q = getSession().createQuery( FIGHTER_BY_COUNTRY + NOT_REG_SUPPORT + FIGHTERS_ALL_ORDER_BY );
            }
            else
            {
                q = getSession().createQuery( FIGHTER_BY_COUNTRY + FIGHTERS_ALL_ORDER_BY );
            }
            q.setLong( 0, countryId );

            return q.list();
        }
        catch ( Exception e )
        {
            log.error( "can not getFighterByCountry", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String FIGHTER_BY_TEAM = FIGHTERS_ALL + SQL_WHERE + " f." + INewaFighterDatabaseConstants.TEAM
        + " =?";

    public List<NewaFighter> getFighterByTeam( long teamId, boolean notReg )
        throws JJWDataLayerException
    {
        try
        {
            Query q;
            if ( notReg )
            {
                q = getSession().createQuery( FIGHTER_BY_TEAM + NOT_REG_SUPPORT + FIGHTERS_ALL_ORDER_BY );
            }
            else
            {
                q = getSession().createQuery( FIGHTER_BY_TEAM + FIGHTERS_ALL_ORDER_BY );
            }
            q.setLong( 0, teamId );

            return q.list();
        }
        catch ( Exception e )
        {
            log.error( "can not getFighterByTeam", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String FIGHTERS_BY_WEIGHTCLASS = SQL_FROM + TABLE_FIGHTER + " f " + SQL_JOIN + SQL_FETCH + "f."
        + INewaFighterDatabaseConstants.TEAM + " t " + SQL_WHERE + "f." + INewaFighterDatabaseConstants.WEIGHTCLASS
        + SQL_EQUAL + SQL_QUESTION_MARK;

    public List<NewaFighter> getFighterByNewaclass( long newaclassId, boolean notReg )
        throws JJWDataLayerException
    {
        try
        {
            Query q;
            if ( notReg )
            {
                q = getSession().createQuery( FIGHTERS_BY_WEIGHTCLASS + NOT_REG_SUPPORT + FIGHTERS_ALL_ORDER_BY );
            }
            else
            {
                q = getSession().createQuery( FIGHTERS_BY_WEIGHTCLASS + FIGHTERS_ALL_ORDER_BY );
            }
            q.setLong( 0, newaclassId );

            List<NewaFighter> fighterList = q.list();

            return fighterList;
        }
        catch ( Exception e )
        {
            log.error( "can not getFighterByWeightclass", e );
            throw new JJWDataLayerException( e );
        }
    }

    /**
     * this function should only be uses for creating classes, because its draws the fighter, so the result is not
     * predictable and may be not repeateable
     */
    public List<NewaFighter> getFighterByNewaclassForDraw( Newaclass newaclass )
    {
        try
        {
            Query q = getSession().createQuery( FIGHTERS_BY_WEIGHTCLASS_FOR_DRAW );
            q.setLong( 0, newaclass.getId() );

            Query q2 = getSession().createSQLQuery( FIGHTERS_BY_WEIGHTCLASS_DRAWED );
            q2.setLong( 0, newaclass.getId() );

            List<NewaFighter> fighterList = q.list();
            Map<Long, NewaFighter> fighterMap = new HashMap<Long, NewaFighter>();
            for ( NewaFighter fighter : fighterList )
            {
                fighterMap.put( fighter.getId(), fighter );
            }

            List<BigInteger> randomList = q2.list();

            fighterList = new ArrayList<NewaFighter>();
            for ( BigInteger id : randomList )
            {
                fighterList.add( fighterMap.get( id.longValue() ) );
            }

            return fighterList;
        }
        catch ( Exception e )
        {
            log.error( "can not getFighterByWeightclass", e );
            return null;
        }
    }

    public void saveFighter( NewaFighter fighter )
        throws JJWDataLayerException
    {
        try
        {
            getHibernateTemplate().saveOrUpdate( fighter );
        }
        catch ( Exception e )
        {
            log.fatal( "Can't save fighter: " + fighter.toString() );
            throw new JJWDataLayerException( e );
        }

    }

    public void removeFighter( Long fighterId )
        throws JJWDataLayerException
    {
        try
        {
            getHibernateTemplate().delete( getFighter( fighterId ) );
        }
        catch ( Exception e )
        {
            log.error( "can not removeFighter", e );
            throw new JJWDataLayerException( e );
        }
    }

    public void removeFighter( NewaFighter fighter )
        throws JJWDataLayerException
    {
        try
        {
            getHibernateTemplate().delete( getFighter( fighter.getId() ) );
        }
        catch ( Exception e )
        {
            log.error( "can not removeFighter", e );
            throw new JJWDataLayerException( e );
        }
    }

    // private static String FIGHTER_USE = "select f." + ID + " from " + TABLE_FIGHTINGCLASS + " f where f." + ID
    // +" = "+
    // "(select ff." + FIGHTINGCLASS + " from " + TABLE_FIGHTER + " ff WHERE ff." + ID +" =?) AND f." + DELETE_STOP +
    // " = 'Y'";

    private static String FIGHTER_REGISTRATED = "select f." + ID + " from " + TABLE_FIGHTER + " f WHERE f." + ID
        + " =?  AND f." + READY + " = '1'";

    public boolean isFighterInUse( Long fighterId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( FIGHTER_REGISTRATED );
            q.setLong( 0, fighterId );
            return !q.list().isEmpty();
        }
        catch ( Exception e )
        {
            log.error( "can not isFighterInUse", e );
            throw new JJWDataLayerException( e );
        }
    }

    public List<NewaFighter> getNotRegistratedFighters()
    {
        try
        {
            return getHibernateTemplate().find( FIGHTERS_ALL_NOT_REGISTRATED );
        }
        catch ( Exception e )
        {
            log.error( "can not getNotRegistratedFighters", e );
            return null;
        }
    }

    public List<NewaFighter> getRegistratedFighters()
    {
        try
        {
            return getHibernateTemplate().find( FIGHTERS_ALL_REGISTRATED );
        }
        catch ( Exception e )
        {
            log.error( "can not getRegistratedFighters", e );
            return null;
        }
    }

    public boolean existFighter( NewaFighter fighter )
    {
        Query q = getSession().createQuery( EXIST_FIGHTER );
        q.setString( 0, fighter.getName() );
        q.setString( 1, fighter.getFirstname() );
        q.setLong( 2, fighter.getTeam().getId() );
        List<NewaFighter> list = (List<NewaFighter>) q.list();

        if ( list != null && !list.isEmpty() )
        { // there is at least one team
            if ( null == fighter.getId() )
                // when a team exists then no new team allowed
                return true;
            else
            {
                for ( NewaFighter item : list )
                {
                    if ( item.getId().equals( fighter.getId() ) )
                        // return true, because it is an update for the current fighter eg. change of age
                        return false;
                }
                return true;
            }
        }

        return false;      
    }

    public List<NewaFighter> getPossibleDublicateFighters( NewaFighter fighter )
    {
        Query q = getSession().createQuery( POSSIBLE_DUBLICATE_FIGHTER );
        q.setString( 0, fighter.getName() );
        q.setString( 1, fighter.getFirstname() );

        return q.list();
    }

    public List<NewaFighter> getFilteredFightersInSpezialOrder()
    {

        return null;
    }

    public List<NewaFighter> getFilteredNotRegistratedFightersInSpezialOrder()
    {

        return null;
    }

    private static String SQL_GET_USED_WEIGHTCLASSES = "FROM " + TABLE_NEWACLASS + " w " + " WHERE w." + ID + " in ( "
        + " SELECT DISTINCT f." + INewaFighterDatabaseConstants.NEWACLASS + " FROM " + TABLE_FIGHTER + " f "
        + " WHERE f." + READY + " =1) " + " GROUP BY w." + ID;

    /**
     * get all weightclasses that contains fighter
     */
    public List<Newaclass> getUsedWeightclasses()
        throws JJWDataLayerException
    {

        try
        {
            Query q = getSession().createQuery( SQL_GET_USED_WEIGHTCLASSES );
            return q.list();
        }
        catch ( Exception e )
        {
            log.fatal( getClass() + ".getUsedWeightclasses() :" + e.getMessage() );
            throw new JJWDataLayerException( e );
        }
    }

    private static String FIGHTER_RESULT_LIST_SQL = "FROM NewaFighter f  left join fetch f."
        + INewaFighterDatabaseConstants.NEWACLASS + " w   left join fetch w." + INewaFighterDatabaseConstants.AGE
        + " a " + "WHERE f." + INewaFighterDatabaseConstants.READY + "=1 AND f." + INewaFighterDatabaseConstants.PLACE
        + " > 0 " + " ORDER BY a." + ORDER_NUMBER + " , " + "w." + INewaWeightclassDatabaseConstants.AGE + "," + " w."
        + INewaWeightclassDatabaseConstants.SEX + "," + "w." + WEIGHT_LIMIT + ", f."
        + INewaFighterDatabaseConstants.PLACE;

    public List<NewaFighter> getFighterResultList()
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( FIGHTER_RESULT_LIST_SQL );
            return q.list();
        }
        catch ( Exception e )
        {
            log.fatal( getClass() + ".getFighterResultList() :" + e.getMessage() );
            throw new JJWDataLayerException( e );
        }
    }
    
    
    private static String FIGHTERS_FOR_JSON_EXPORT = SQL_FROM + TABLE_FIGHTER + " f " + SQL_LEFT + SQL_JOIN + SQL_FETCH + "f."
                    + INewaFighterDatabaseConstants.AGE + " a " + SQL_LEFT + SQL_JOIN + SQL_FETCH + "f."
                    + INewaFighterDatabaseConstants.NEWACLASS + SQL_LEFT + SQL_JOIN + SQL_FETCH + "f."
                    + INewaFighterDatabaseConstants.TEAM + " t " + SQL_LEFT + SQL_JOIN + SQL_FETCH + "t."
                    + INewaFighterDatabaseConstants.REGION + SQL_LEFT + SQL_JOIN + SQL_FETCH + "t."
                    + INewaFighterDatabaseConstants.COUNTRY + " "
                      + " where "+INewaFighterDatabaseConstants.MARK_FOR_EXPORT+"=?";


    public List<NewaFighter> getFightersForJSONExport()
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( FIGHTERS_FOR_JSON_EXPORT );
            q.setString( 0, "Y" );
            return q.list();

        }
        catch ( Exception e )
        {
            log.fatal( getClass() + ".getFightersForJSONExport() :" + e.getMessage() );
            throw new JJWDataLayerException( e );
        }
    }

}
