/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoTeamDaoHibernate.java
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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import de.jjw.dao.IDatabaseTableConstants;
import de.jjw.dao.duo.DuoTeamDao;
import de.jjw.dao.hibernate.BaseDaoHibernate;
import de.jjw.dao.hibernate.ITeamDatabaseConstants;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.dao.hibernate.admin.IAgeDatabaseConstants;
import de.jjw.dao.hibernate.fighting.IFighterDatabaseConstants;
import de.jjw.model.duo.DuoTeam;
import de.jjw.model.duo.Duoclass;
import de.jjw.model.fighting.Fighter;
import de.jjw.util.TypeUtil;

public class DuoTeamDaoHibernate
    extends BaseDaoHibernate
    implements DuoTeamDao
{

    private static String DUO_TEAMS_ALL = SQL_FROM + IDuoTeamDatabaseConstants.TABLE_DUOTEAM + " f " + SQL_LEFT
        + SQL_JOIN + SQL_FETCH + "f." + IDuoTeamDatabaseConstants.AGE + " a " + SQL_LEFT + SQL_JOIN + SQL_FETCH + "f."
        + IDuoTeamDatabaseConstants.DUOCLASS + SQL_LEFT + SQL_JOIN + SQL_FETCH + "f." + IDuoTeamDatabaseConstants.TEAM
        + " t " + SQL_LEFT + SQL_JOIN + SQL_FETCH + "t." + IDuoTeamDatabaseConstants.REGION + SQL_LEFT + SQL_JOIN
        + SQL_FETCH + "t." + IDuoTeamDatabaseConstants.COUNTRY;

    private static String DUO_TEAMS_ALL_ORDER_BY = " order by f." + IDuoTeamDatabaseConstants.AGE + " , f."
        + IDuoTeamDatabaseConstants.SEX + " , f." + IDuoTeamDatabaseConstants.DUOCLASS_ID + " , t."
        + ITeamDatabaseConstants.TEAM_NAME + " , f." + IDuoTeamDatabaseConstants.NAME + " , f."
        + IDuoTeamDatabaseConstants.FIRSTNAME;

    private static String DUO_TEAMS_BY_WEIGHTCLASS = SQL_FROM + IDuoTeamDatabaseConstants.TABLE_DUOTEAM + " f "
        + SQL_WHERE + "f." + IDuoTeamDatabaseConstants.DUOCLASS + SQL_EQUAL + SQL_QUESTION_MARK + SQL_AND
        + IDuoTeamDatabaseConstants.READY + "= 1";

    private static String DUO_TEAMS_BY_WEIGHTCLASS_DRAWED = SQL_SELECT + "a.id " + SQL_FROM
        + IDuoTeamDatabaseConstants.TABLE_DUOTEAM + " a " + " left join team t on a.team=t.id "
        + " left join (select rand() as rand3, f.team from duoteam f group by f.team) d on a.team=d.team "
        + " left join (select rand() as rand2, f.id from region f group by f.id) c on t.region=c.id "
        + " left join (select rand() as rand1, f.id from country f group by f.id) b on t.country=b.id "
        + "	left join (select rand() as rand4, f.name from fighter f group by f.name) e on a.name=e.name " + SQL_WHERE
        + " a." + IDuoTeamDatabaseConstants.DUOCLASS + SQL_EQUAL + SQL_QUESTION_MARK + SQL_AND
        + IDuoTeamDatabaseConstants.READY + "= 1" + "	order by rand1,rand2,rand3, rand4 ";

    private static String DUO_TEAMS_ALL_NOT_REGISTRATED = SQL_FROM + IDuoTeamDatabaseConstants.TABLE_DUOTEAM + " f "
        + SQL_LEFT + SQL_JOIN + SQL_FETCH + "f." + IDuoTeamDatabaseConstants.AGE + " a " + SQL_LEFT + SQL_JOIN
        + SQL_FETCH + "f." + IDuoTeamDatabaseConstants.DUOCLASS + SQL_LEFT + SQL_JOIN + SQL_FETCH + "f."
        + IDuoTeamDatabaseConstants.TEAM + " t " + SQL_LEFT + SQL_JOIN + SQL_FETCH + "t."
        + IDuoTeamDatabaseConstants.REGION + SQL_LEFT + SQL_JOIN + SQL_FETCH + "t." + IDuoTeamDatabaseConstants.COUNTRY
        + SQL_WHERE + "f." + IDuoTeamDatabaseConstants.READY + SQL_EQUAL + "'0'" + SQL_OR + "f."
        + IDuoTeamDatabaseConstants.READY + SQL_IS + SQL_NULL + " order by f." + IDuoTeamDatabaseConstants.AGE
        + " , f." + IDuoTeamDatabaseConstants.SEX + " , f." + IDuoTeamDatabaseConstants.DUOCLASS_ID + " , t."
        + ITeamDatabaseConstants.TEAM_NAME + " , f." + IDuoTeamDatabaseConstants.NAME + " , f."
        + IDuoTeamDatabaseConstants.FIRSTNAME;

    private static String EXIST_DUO_TEAM = "from " + IDuoTeamDatabaseConstants.TABLE_DUOTEAM + " f where ( f."
        + IDuoTeamDatabaseConstants.NAME + "=? and f." + IDuoTeamDatabaseConstants.FIRSTNAME + "= ? and f."
        + IDuoTeamDatabaseConstants.NAME_2 + "=? and f." + IDuoTeamDatabaseConstants.FIRSTNAME_2 + "= ? and f."
        + IDuoTeamDatabaseConstants.TEAM + "=? ) OR (f. " +

        IDuoTeamDatabaseConstants.NAME + "=? and f." + IDuoTeamDatabaseConstants.FIRSTNAME + "= ? and f."
        + IDuoTeamDatabaseConstants.NAME_2 + "=? and f." + IDuoTeamDatabaseConstants.FIRSTNAME_2 + "= ? and f."
        + IDuoTeamDatabaseConstants.TEAM + "=? )";
       // +" and " + IDuoTeamDatabaseConstants.ID +"<>?";

    public DuoTeam getDuoTeam( Long duoTeamId )
        throws JJWDataLayerException
    {
        try
        {
            return (DuoTeam) getHibernateTemplate().get( DuoTeam.class, duoTeamId );
        }
        catch ( Exception e )
        {
            log.error( e.getMessage() );
            throw new JJWDataLayerException( e );
        }
    }

    public List<DuoTeam> getAllDuoTeams()
        throws JJWDataLayerException
    {
        try
        {
            return getHibernateTemplate().find( DUO_TEAMS_ALL );
        }
        catch ( Exception e )
        {
            log.error( e.getMessage() );
            throw new JJWDataLayerException( e );
        }
    }

    public List<DuoTeam> getAllDuoTeamsInOrder( int order, boolean isAscending )
        throws JJWDataLayerException
    {
        try
        {
            StringBuffer sb = new StringBuffer( DUO_TEAMS_ALL );
            sb.append( SQL_ORDER_BY );
            if ( IDatabaseTableConstants.ORDER_BY_NAME == order )
            {
                sb.append( "f." );
                sb.append( IDuoTeamDatabaseConstants.NAME );
                if ( !isAscending )
                {
                    sb.append( SQL_DESC );
                }
            }

            if ( IDatabaseTableConstants.ORDER_BY_FIRSTNAME == order )
            {
                sb.append( "f." );
                sb.append( IDuoTeamDatabaseConstants.FIRSTNAME );
                if ( !isAscending )
                {
                    sb.append( SQL_DESC );
                }
            }

            if ( IDatabaseTableConstants.ORDER_BY_SEX == order )
            {
                sb.append( "f." );
                sb.append( IDuoTeamDatabaseConstants.SEX );
                if ( !isAscending )
                {
                    sb.append( SQL_DESC );
                }
            }

            if ( IDatabaseTableConstants.ORDER_BY_READY == order )
            {
                sb.append( "f." );
                sb.append( IDuoTeamDatabaseConstants.READY );
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

    public List<DuoTeam> getDuoTeamsByDuoclassForDraw( Duoclass duoclass )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( DUO_TEAMS_BY_WEIGHTCLASS );
            q.setLong( 0, duoclass.getId() );

            Query q2 = getSession().createSQLQuery( DUO_TEAMS_BY_WEIGHTCLASS_DRAWED );
            q2.setLong( 0, duoclass.getId() );

            List<DuoTeam> duoTeamList = q.list();
            Map<Long, DuoTeam> duoTeamMap = new HashMap<Long, DuoTeam>();
            for ( DuoTeam duoTeam : duoTeamList )
            {
                duoTeamMap.put( duoTeam.getId(), duoTeam );
            }

            List<BigInteger> randomList = q2.list();

            duoTeamList = new ArrayList<DuoTeam>();
            for ( BigInteger id : randomList )
            {
                duoTeamList.add( duoTeamMap.get( id.longValue() ) );
            }

            return duoTeamList;
        }
        catch ( Exception e )
        {
            log.error( "can not getTeamsByDuoclass", e );
            throw new JJWDataLayerException( e );
        }
    }

    public void saveDuoTeam( DuoTeam duoTeam )
        throws JJWDataLayerException
    {
        try
        {
            getHibernateTemplate().saveOrUpdate( duoTeam );
        }
        catch ( Exception e )
        {
            log.fatal( "Can't save fighter: " + duoTeam.toString() );
            throw new JJWDataLayerException( e );
        }
    }

    public List<DuoTeam> getNotRegistratedDuoTeams()
        throws JJWDataLayerException
    {
        try
        {
            return getHibernateTemplate().find( DUO_TEAMS_ALL_NOT_REGISTRATED );
        }
        catch ( Exception e )
        {
            log.error( e );
            throw new JJWDataLayerException( e );
        }
    }

    public boolean existDuoTeam( DuoTeam duoTeam )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( EXIST_DUO_TEAM );
            q.setString( 0, duoTeam.getName() );
            q.setString( 1, duoTeam.getFirstname() );
            q.setString( 2, duoTeam.getName2() );
            q.setString( 3, duoTeam.getFirstname2() );
            q.setLong( 4, duoTeam.getTeam().getId() );

            q.setString( 5, duoTeam.getName2() );
            q.setString( 6, duoTeam.getFirstname2() );
            q.setString( 7, duoTeam.getName() );
            q.setString( 8, duoTeam.getFirstname() );
            q.setLong( 9, duoTeam.getTeam().getId() );

            List<DuoTeam> list = (List<DuoTeam>) q.list();

            if ( list != null && !list.isEmpty() )
            { // there is at least one team
                if ( null == duoTeam.getId() )
                    // when a team exists then no new team allowed
                    return true;
                else
                {
                    for ( DuoTeam item : list )
                    {
                        if ( item.getId().equals( duoTeam.getId() ) )
                            // return true, because it is an update for the current team eg. change of age
                            return false;
                    }
                    return true;
                }
            }

            return false;
        }
        catch ( Exception e )
        {
            log.error( e );
            throw new JJWDataLayerException( e );
        }

    }

    private static String SQL_GET_USED_DUOCLASSES = "FROM " + IDuoclassDatabaseConstants.TABLE_DUOCLASS + " w "
        + " WHERE w." + IDuoclassDatabaseConstants.ID + " in ( " + " SELECT DISTINCT f."
        + IDuoTeamDatabaseConstants.DUOCLASS + " FROM " + IDuoTeamDatabaseConstants.TABLE_DUOTEAM + " f " + " WHERE f."
        + IDuoTeamDatabaseConstants.READY + " =1) " + " GROUP BY w." + IDuoTeamDatabaseConstants.ID;

    public List<Duoclass> getUsedDuoclasses()
        throws JJWDataLayerException
    {

        try
        {
            Query q = getSession().createQuery( SQL_GET_USED_DUOCLASSES );
            return q.list();
        }
        catch ( Exception e )
        {
            log.fatal( getClass() + ".getUsedDuoclasses() :" + e.getMessage() );
            throw new JJWDataLayerException( e );
        }
    }

    private static String DUO_RESULT_LIST_SQL = "FROM DuoTeam f  left join fetch f."
        + IDuoTeamDatabaseConstants.DUOCLASS + " w " + " left join fetch w." + IDuoTeamDatabaseConstants.AGE + " a "
        + "WHERE f." + IDuoTeamDatabaseConstants.READY + "=1 AND f." + IDuoTeamDatabaseConstants.PLACE + " > 0 "
        + " ORDER BY a." + IAgeDatabaseConstants.ORDER_NUMBER + " , " + "w." + IDuoTeamDatabaseConstants.AGE + ","
        + " w." + IDuoTeamDatabaseConstants.SEX + ", f." + IDuoTeamDatabaseConstants.PLACE;

    public List<DuoTeam> getDuoTeamResultList()
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( DUO_RESULT_LIST_SQL );
            return q.list();
        }
        catch ( Exception e )
        {
            log.error( getClass() + ".getDuoTeamResultList() :" + e.getMessage() );
            throw new JJWDataLayerException( e );
        }
    }

    private static String DUOTEAM_BY_AGE = DUO_TEAMS_ALL + SQL_WHERE + "f." + IDuoTeamDatabaseConstants.AGE + " =?";

    private static String NOT_REG_SUPPORT = SQL_AND + "f." + IDuoTeamDatabaseConstants.READY + SQL_EQUAL + "0";

    public List<DuoTeam> getDuoTeamByAge( long ageId, boolean notReg )
        throws JJWDataLayerException
    {
        try
        {
            Query q;
            if ( notReg )
            {
                q = getSession().createQuery( DUOTEAM_BY_AGE + NOT_REG_SUPPORT );
            }
            else
            {
                q = getSession().createQuery( DUOTEAM_BY_AGE );
            }
            q.setLong( 0, ageId );

            return q.list();
        }
        catch ( Exception e )
        {
            log.error( "can not getDuoteamByAge", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String DUOTEAM_BY_REGION = DUO_TEAMS_ALL + SQL_WHERE + "t." + IDuoTeamDatabaseConstants.REGION
        + " =?";

    public List<DuoTeam> getDuoTeamByRegion( long regionId, boolean notReg )
        throws JJWDataLayerException
    {
        try
        {
            Query q;
            if ( notReg )
            {
                q = getSession().createQuery( DUOTEAM_BY_REGION + NOT_REG_SUPPORT );
            }
            else
            {
                q = getSession().createQuery( DUOTEAM_BY_REGION );
            }
            q.setLong( 0, regionId );

            return q.list();
        }
        catch ( Exception e )
        {
            log.error( "can not getDuoTeamByRegion", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String DUOTEAM_BY_COUNTRY = DUO_TEAMS_ALL + SQL_WHERE + "t." + IDuoTeamDatabaseConstants.COUNTRY
        + " =?";

    public List<DuoTeam> getDuoTeamByCountry( long countryId, boolean notReg )
        throws JJWDataLayerException
    {
        try
        {
            Query q;
            if ( notReg )
            {
                q = getSession().createQuery( DUOTEAM_BY_COUNTRY + NOT_REG_SUPPORT );
            }
            else
            {
                q = getSession().createQuery( DUOTEAM_BY_COUNTRY );
            }
            q.setLong( 0, countryId );

            return q.list();
        }
        catch ( Exception e )
        {
            log.error( "can not getDuoTeamByCountry", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String DUOTEAM_BY_TEAM = DUO_TEAMS_ALL + SQL_WHERE + " f." + IDuoTeamDatabaseConstants.TEAM + " =?";

    public List<DuoTeam> getDuoTeamByTeam( long teamId, boolean notReg )
        throws JJWDataLayerException
    {
        try
        {
            Query q;
            if ( notReg )
            {
                q = getSession().createQuery( DUOTEAM_BY_TEAM + NOT_REG_SUPPORT + DUO_TEAMS_ALL_ORDER_BY );
            }
            else
            {
                q = getSession().createQuery( DUOTEAM_BY_TEAM + DUO_TEAMS_ALL_ORDER_BY );
            }
            q.setLong( 0, teamId );

            return q.list();
        }
        catch ( Exception e )
        {
            log.error( "can not getDuoTeamByTeam", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String DUOTEAMS_BY_DUOCLASS = SQL_FROM + IDuoTeamDatabaseConstants.TABLE_DUOTEAM + " f " + SQL_WHERE
        + "f." + IDuoTeamDatabaseConstants.DUOCLASS + SQL_EQUAL + SQL_QUESTION_MARK;

    public List<DuoTeam> getDuoTeamByDuoclass( long duoclassId, boolean notReg )
        throws JJWDataLayerException
    {
        try
        {
            Query q;
            if ( notReg )
            {
                q = getSession().createQuery( DUOTEAMS_BY_DUOCLASS + NOT_REG_SUPPORT );
            }
            else
            {
                q = getSession().createQuery( DUOTEAMS_BY_DUOCLASS );
            }
            q.setLong( 0, duoclassId );

            List<DuoTeam> duoTeamList = q.list();

            return duoTeamList;
        }
        catch ( Exception e )
        {
            log.error( "can not getDuoTeamByDuoclass", e );
            throw new JJWDataLayerException( e );
        }
    }

    public void removeDuoTeam( Long teamId )
        throws JJWDataLayerException
    {
        try
        {
            getHibernateTemplate().delete( getDuoTeam( teamId ) );
        }
        catch ( Exception e )
        {
            log.error( "can not removeDuoTeam", e );
            throw new JJWDataLayerException( e );
        }
    }

    public void removeDuoTeam( DuoTeam duoteam )
        throws JJWDataLayerException
    {
        try
        {
            getHibernateTemplate().delete( getDuoTeam( duoteam.getId() ) );
        }
        catch ( Exception e )
        {
            log.error( "can not removeDuoTeam", e );
            throw new JJWDataLayerException( e );
        }
    }

    // private static String DUO_TEAM_IN_USE = "select f." + IDuoTeamDatabaseConstants.ID + " from " +
    // IDuoclassDatabaseConstants.TABLE_DUOCLASS + " f where f." + IDuoTeamDatabaseConstants.ID +" = "+
    // "(select ff." + IDuoTeamDatabaseConstants.DUOCLASS + " from " + IDuoTeamDatabaseConstants.TABLE_DUOTEAM +
    // " ff WHERE ff." +
    // IDuoTeamDatabaseConstants.ID + " =?) AND f." + IDuoclassDatabaseConstants.DELETE_STOP + " = 'Y'";

    private static String DUO_TEAM_REGISTRATED = "select f." + IDuoTeamDatabaseConstants.ID + " from "
        + IDuoTeamDatabaseConstants.TABLE_DUOTEAM + " f WHERE f." + IDuoTeamDatabaseConstants.ID + " =? AND f."
        + IDuoTeamDatabaseConstants.READY + " = '1'";

    public boolean isDuoTeamInUse( Long teamId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( DUO_TEAM_REGISTRATED );
            q.setLong( 0, teamId );
            return !q.list().isEmpty();
        }
        catch ( Exception e )
        {
            log.error( "can not isDuoTeamInUse", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String DUO_TEAMS_ALL_REGISTRATED = SQL_FROM + IDuoTeamDatabaseConstants.TABLE_DUOTEAM + " f "
        + SQL_LEFT + SQL_JOIN + SQL_FETCH + "f." + IDuoTeamDatabaseConstants.AGE + " a " + SQL_LEFT + SQL_JOIN
        + SQL_FETCH + "f." + IDuoTeamDatabaseConstants.DUOCLASS + SQL_LEFT + SQL_JOIN + SQL_FETCH + "f."
        + IDuoTeamDatabaseConstants.TEAM + " t " + SQL_LEFT + SQL_JOIN + SQL_FETCH + "t."
        + IDuoTeamDatabaseConstants.REGION + SQL_LEFT + SQL_JOIN + SQL_FETCH + "t." + IDuoTeamDatabaseConstants.COUNTRY
        + SQL_WHERE + "f." + IDuoTeamDatabaseConstants.READY + SQL_EQUAL + "'1'" + SQL_OR + "f."
        + IDuoTeamDatabaseConstants.READY + SQL_IS + SQL_NULL + " order by f." + IDuoTeamDatabaseConstants.AGE
        + " , f." + IDuoTeamDatabaseConstants.SEX + " , f." + IDuoTeamDatabaseConstants.DUOCLASS_ID + " , t."
        + ITeamDatabaseConstants.TEAM_NAME + " , f." + IDuoTeamDatabaseConstants.NAME + " , f."
        + IDuoTeamDatabaseConstants.FIRSTNAME;

    @Override
    public List<DuoTeam> getRegistratedDuoTeams()
        throws JJWDataLayerException
    {
        try
        {
            return getHibernateTemplate().find( DUO_TEAMS_ALL_REGISTRATED );
        }
        catch ( Exception e )
        {
            log.error( "can not getRegistratedFighters", e );
            throw new JJWDataLayerException( e );
        }
    }    
    
    private static String DUO_TEAMS_FOR_JSON_EXPORT  = SQL_FROM + IDuoTeamDatabaseConstants.TABLE_DUOTEAM + " f " + SQL_LEFT
                    + SQL_JOIN + SQL_FETCH + "f." + IDuoTeamDatabaseConstants.AGE + " a " + SQL_LEFT + SQL_JOIN + SQL_FETCH + "f."
                    + IDuoTeamDatabaseConstants.DUOCLASS + SQL_LEFT + SQL_JOIN + SQL_FETCH + "f." + IDuoTeamDatabaseConstants.TEAM
                    + " t " + SQL_LEFT + SQL_JOIN + SQL_FETCH + "t." + IDuoTeamDatabaseConstants.REGION + SQL_LEFT + SQL_JOIN
                    + SQL_FETCH + "t." + IDuoTeamDatabaseConstants.COUNTRY
                    + " where "+IDuoTeamDatabaseConstants.MARK_FOR_EXPORT+"=?";
    

    public List<DuoTeam> getDuosForJSONExport()
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( DUO_TEAMS_FOR_JSON_EXPORT );
            q.setString( 0, "Y" );
            return q.list();

        }
        catch ( Exception e )
        {
            log.fatal( getClass() + ".getDuosForJSONExport() :" + e.getMessage() );
            throw new JJWDataLayerException( e );
        }
    }
}
