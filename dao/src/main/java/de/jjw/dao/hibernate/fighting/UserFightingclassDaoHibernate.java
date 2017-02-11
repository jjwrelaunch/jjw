/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : UserFightingclassDaoHibernate.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:45
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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import de.jjw.dao.fighting.UserFightingclassDao;
import de.jjw.dao.hibernate.BaseDaoHibernate;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.dao.hibernate.admin.IAgeDatabaseConstants;
import de.jjw.dao.hibernate.duo.IDuoclassDatabaseConstants;
import de.jjw.dao.hibernate.duo.IUserDuoclassDatabaseConstants;
import de.jjw.dao.hibernate.newa.IUserNewaclassDatabaseConstants;
import de.jjw.dao.hibernate.newa.INewaWeightclassDatabaseConstants;
import de.jjw.model.admin.UserToClass;
import de.jjw.model.duo.Duoclass;
import de.jjw.model.duo.UserDuoclass;
import de.jjw.model.fighting.Fightingclass;
import de.jjw.model.fighting.UserFightingclass;
import de.jjw.model.newa.Newaclass;
import de.jjw.model.newa.UserNewaclass;
import de.jjw.util.JJWTwoLongDTO;
import de.jjw.util.TypeUtil;

public class UserFightingclassDaoHibernate
    extends BaseDaoHibernate
    implements IUserFightingclassDatabaseConstants, UserFightingclassDao, IFightDatabaseConstants
{

    private static String SQL_GET_ASSIGN_LIST =
        SQL_FROM + TABLE_USERFIGHTINGCLASS + " f " + SQL_LEFT + SQL_JOIN + SQL_FETCH + "f."
            + IUserFightingclassDatabaseConstants.FIGHTINGCLASS + " fc " + SQL_LEFT + SQL_JOIN + SQL_FETCH + "f."
            + USER + " u ";

    @Override
    public Map<JJWTwoLongDTO, UserFightingclass> getAssignMap()
        throws JJWDataLayerException
    {

        try
        {
            Query q = getSession().createQuery( SQL_GET_ASSIGN_LIST );
            JJWTwoLongDTO dto = null;
            List<UserFightingclass> list = q.list();
            Map<JJWTwoLongDTO, UserFightingclass> assignMap = new HashMap<JJWTwoLongDTO, UserFightingclass>( list.size() );
            for ( UserFightingclass item : list )
            {
                dto = new JJWTwoLongDTO( item.getFightingclassId(), item.getUserId() );
                assignMap.put( dto, item);
            }
            return assignMap;
        }
        catch ( Exception e )
        {
            log.error( "can not getAssignList", e );
            return null;
        }
    }

    public void saveUserFightingclass( UserFightingclass uf )
        throws JJWDataLayerException
    {
        try
        {
            getHibernateTemplate().saveOrUpdate( uf );
        }
        catch ( Exception e )
        {
            log.fatal( "Can't save fighter: " + uf.toString() );
            throw new JJWDataLayerException( e );
        }
    }

    private static String SQL_TOGGLE_ACCESS =
        SQL_FROM + TABLE_USERFIGHTINGCLASS + " f " + SQL_WHERE + "f."
            + IUserFightingclassDatabaseConstants.FIGHTINGCLASS_ID + "=? and f." + USER_ID + "=?";

    private static String SQL_TOGGLE_ACCESS_MAX_ORDER_NUMBER =
                    SQL_SELECT+ "max(a) from ( " + 
                    SQL_SELECT+ "max("+ IUserFightingclassDatabaseConstants.SQL_ORDER_NUMBER + ") as a " + SQL_FROM + SQL_TABLE_USERFIGHTINGCLASS + " f " + SQL_WHERE + "f."  + SQL_USER_ID + "=? union " +
                    SQL_SELECT+ "max("+ IUserDuoclassDatabaseConstants.SQL_ORDER_NUMBER + ")  as a " + SQL_FROM + IUserDuoclassDatabaseConstants.SQL_TABLE_USERDUOCLASS + " f " + SQL_WHERE + "f."  + IUserDuoclassDatabaseConstants.SQL_USER_ID + "=? union " +
                    SQL_SELECT+ "max("+ IUserNewaclassDatabaseConstants.SQL_ORDER_NUMBER + ")  as a " + SQL_FROM + IUserNewaclassDatabaseConstants.SQL_TABLE_USERNEWACLASS + " f " + SQL_WHERE + "f."  + IUserNewaclassDatabaseConstants.SQL_USER_ID + "=? ) as tt "                    
                    ;
    
    @Override
    public void toggleAccess( UserFightingclass uf )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( SQL_TOGGLE_ACCESS );
            q.setLong( 0, uf.getFightingclassId() );
            q.setLong( 1, uf.getUserId() );            
            List<UserFightingclass> list = q.list();
            if ( list.isEmpty() )
            {
                Query q2 = getSession().createSQLQuery( SQL_TOGGLE_ACCESS_MAX_ORDER_NUMBER );                
                q2.setLong( 0, uf.getUserId() );
                q2.setLong( 1, uf.getUserId() );
                q2.setLong( 2, uf.getUserId() );
                
                Object o = q2.uniqueResult();
                if (o !=null)
                    uf.setOrderNumber(1+ ( (Integer)  o).longValue());
                else
                    uf.setOrderNumber(0l);
                
                getHibernateTemplate().saveOrUpdate( uf );
            }
            else
            {
                getHibernateTemplate().delete( list.get( 0 ) );
            }

        }
        catch ( Exception e )
        {
            log.fatal( "Can't toggleAccess: " + e.toString() );
            throw new JJWDataLayerException( e );
        }

    }
  
    
    private static String SQL_RESET_ORDER_NUMBER_GET_LOWEST_NUMBER =
                    SQL_SELECT+ "min(a) from ( " + 
                                    SQL_SELECT+ "min("+ IUserFightingclassDatabaseConstants.SQL_ORDER_NUMBER + ") as a " + SQL_FROM + SQL_TABLE_USERFIGHTINGCLASS + " f " + SQL_WHERE + "f."  + SQL_USER_ID + "=? union " +
                                    SQL_SELECT+ "min("+ IUserDuoclassDatabaseConstants.SQL_ORDER_NUMBER + ")  as a " + SQL_FROM + IUserDuoclassDatabaseConstants.SQL_TABLE_USERDUOCLASS + " f " + SQL_WHERE + "f."  + IUserDuoclassDatabaseConstants.SQL_USER_ID + "=? union " +
                                    SQL_SELECT+ "min("+ IUserNewaclassDatabaseConstants.SQL_ORDER_NUMBER + ")  as a " + SQL_FROM + IUserNewaclassDatabaseConstants.SQL_TABLE_USERNEWACLASS + " f " + SQL_WHERE + "f."  + IUserNewaclassDatabaseConstants.SQL_USER_ID + "=? ) as tt "                    
                                    ;
    
    private static String SQL_RESET_ORDER_NUMBER_SET_NEW_ORDER_PART_I =
                    "update UserFightingclass ufc set ufc.orderNumber=ufc.orderNumber- ";// ?   where  user_id=?";
    
    /** 
   * @param fightingclassId
   * @param userId
   * @throws JJWDataLayerException
   */
    public void resetOrderNumbers(  Long userId )
        throws JJWDataLayerException
    {
        try
        {            
            Query q = getSession().createSQLQuery( SQL_RESET_ORDER_NUMBER_GET_LOWEST_NUMBER );
            q.setLong( 0, userId );
            q.setLong( 1, userId );
            q.setLong( 2, userId );
            Object o = q.uniqueResult(); 
            if ( o != null )
            {
                StringBuffer sb = new StringBuffer( SQL_RESET_ORDER_NUMBER_SET_NEW_ORDER_PART_I );
                sb.append( ( (Integer) o ).intValue() ).append( " where  ufc.userId= :user_id" );
                                
                Query q2 = getSession().createQuery( sb.toString() );
                // q2.setLong( 0, ((Integer)o).longValue() );
                q2.setParameter( "user_id", userId );// setLong( 0, userId );
                q2.executeUpdate();                
            }
        }
        catch ( Exception e )
        {
            log.fatal( "Can't resetOrderNumbers: " + e.toString() );
            throw new JJWDataLayerException( e );
        }

    }

    
    
    private static String SQL_MOVE_UP =
                    SQL_UPDATE + SQL_TABLE_USERFIGHTINGCLASS + SQL_SET + IUserFightingclassDatabaseConstants.SQL_ORDER_NUMBER + " = " +IUserFightingclassDatabaseConstants.SQL_ORDER_NUMBER +" +1 "
                   + SQL_WHERE + IUserFightingclassDatabaseConstants.SQL_FIGHTINGCLASS_ID + "=? and " + SQL_USER_ID + "=?" + SQL_AND + IUserFightingclassDatabaseConstants.SQL_ORDER_NUMBER +"< 999";
    @Override
    public void moveUp(  Long fightingclassId, Long userId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createSQLQuery( SQL_MOVE_UP );
            q.setLong( 0, fightingclassId );
            q.setLong( 1,userId );
            q.executeUpdate();            
        }
        catch ( Exception e )
        {
            log.fatal( "Can't moveUp: " + e.toString() );
            throw new JJWDataLayerException( e );
        }

    }
    
//    private static String SQL_MOVE_DOWN =
//                    SQL_UPDATE + SQL_TABLE_USERFIGHTINGCLASS + SQL_SET + IUserFightingclassDatabaseConstants.SQL_ORDER_NUMBER + " = " +IUserFightingclassDatabaseConstants.SQL_ORDER_NUMBER +" -1 "
//                   + SQL_WHERE + IUserFightingclassDatabaseConstants.SQL_FIGHTINGCLASS_ID + "=? and " + USER_ID + "=?"+ SQL_AND + IUserFightingclassDatabaseConstants.SQL_ORDER_NUMBER +"> (" +
//                    "Select min(" + IUserFightingclassDatabaseConstants.SQL_ORDER_NUMBER +")" + SQL_FROM + SQL_TABLE_USERFIGHTINGCLASS + SQL_WHERE + IUserFightingclassDatabaseConstants.SQL_FIGHTINGCLASS_ID + "=? and cccc" + USER_ID + "=?=" ;
    
    private static String SQL_MOVE_DOWN =
                    SQL_UPDATE + SQL_TABLE_USERFIGHTINGCLASS + SQL_SET + IUserFightingclassDatabaseConstants.SQL_ORDER_NUMBER + " = " +IUserFightingclassDatabaseConstants.SQL_ORDER_NUMBER +" -1 "
                                    + SQL_WHERE + IUserFightingclassDatabaseConstants.SQL_FIGHTINGCLASS_ID + "=? and " + SQL_USER_ID + "=?" + SQL_AND + IUserFightingclassDatabaseConstants.SQL_ORDER_NUMBER +"> 1";
                    
    @Override
    public void moveDown( Long fightingclassId, Long userId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createSQLQuery( SQL_MOVE_DOWN );
            q.setLong( 0, fightingclassId );
            q.setLong( 1, userId );            
            q.executeUpdate();        
        }
        catch ( Exception e )
        {
            log.fatal( "Can't moveDown: " + e.toString() );
            throw new JJWDataLayerException( e );
        }

    }

    private static String SQL_IS_ACCESS_FOR_TATMI_USER =
        SQL_FROM + TABLE_USERFIGHTINGCLASS + " f " + SQL_WHERE + "f."
            + IUserFightingclassDatabaseConstants.FIGHTINGCLASS_ID + "=? and f." + USER_ID + "=?";

    @Override
    public boolean isAccessForTatamiUser( long userId, long fightingclassId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( SQL_IS_ACCESS_FOR_TATMI_USER );
            q.setLong( 0, fightingclassId );
            q.setLong( 1, userId );

            List<UserFightingclass> list = q.list();
            if ( list.isEmpty() )
            {
                return false;
            }
            else
            {
                return true;
            }

        }
        catch ( Exception e )
        {
            log.fatal( "Can't isAccessForTatamiUser: " + e.toString() );
            throw new JJWDataLayerException( e );
        }
    }
    
    private static String SQL_WEIGHTCLASSES_FOR_USER_PART_1 =
        SQL_SELECT
            + " new Fightingclass(w, "
            + " (select count(distinct f.id) from Fighter f where f.fightingclass = w.id and f.ready=1 ),"
            +
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

            ")" +

            SQL_FROM + IWeightclassDatabaseConstants.TABLE_FIGHTINGCLASS
            + " w "
           + 
           
            // " LEFT JOIN FETCH w." + IFighterDatabaseConstants.AGE + " a " +
            SQL_WHERE + " w." + IWeightclassDatabaseConstants.COMPLETE + "='N' and id in ( ";

    private static String SQL_WEIGHTCLASSES_FOR_USER_PART_2 = SQL_ORDER_BY + "w.age."
        + IAgeDatabaseConstants.ORDER_NUMBER + " , " + "w." + IWeightclassDatabaseConstants.AGE + ", w."
        + IWeightclassDatabaseConstants.SEX + ", w."
            + IWeightclassDatabaseConstants.WEIGHT_LIMIT;                 
    
    private static String SQL_USER_WEIGHTCLASSES_FOR_USER = SQL_FROM + TABLE_USERFIGHTINGCLASS + " uf " + SQL_WHERE
        + "uf." + IUserFightingclassDatabaseConstants.USER_ID + "=?";

    @SuppressWarnings( "unchecked" )
    public List<Fightingclass> getCurrentAndPlanedFightingClassesForUser( Long userId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( SQL_USER_WEIGHTCLASSES_FOR_USER );
            q.setLong( 0, userId );
            List<UserFightingclass> list = q.list();
            StringBuffer sb = new StringBuffer( 1024 );
            for ( UserFightingclass item : list )
            {
                sb.append( item.getFightingclassId() );
                sb.append( "," );
            }
            if ( sb.length() > 0 )
            {
                sb.deleteCharAt( sb.length() - 1 );
                sb.append( " ) " );

                q =
                    getSession().createQuery( SQL_WEIGHTCLASSES_FOR_USER_PART_1 + sb.toString()
                                                  + SQL_WEIGHTCLASSES_FOR_USER_PART_2 );
                q.setLong( 0, TypeUtil.LONG_EMPTY );
                return (List<Fightingclass>) q.list();
            }
            return new ArrayList<Fightingclass>();
        }
        catch ( Exception e )
        {
            log.fatal( "Can't getCurrentAndPlanedClassesForUser: " + e.toString() );
            throw new JJWDataLayerException( e );
        }
    }
      
    private static String SQL_DUOCLASSES_FOR_USER_PART_1 = SQL_SELECT + " new Duoclass(w, "
        + " (select count(distinct f.id) from DuoTeam f where f.duoclass = w.id and f.ready=1 ),"
        + " (SELECT COUNT(DISTINCT fl.id) FROM DuoFight fl WHERE fl.duoclass = w.id ),"
        + " (SELECT COUNT(DISTINCT fl.id) FROM DuoFight fl WHERE fl.duoclass = w.id AND fl.winnerId IS NOT NULL AND fl.winnerId >= 0 "
        + "    AND fl.teamIdBlue not in ( ? )),"
        + "(select s.fightsystemType from Fightsystem s where s.minParticipant<="
        + "  (select count(distinct f.id) from DuoTeam f where f.duoclass = w.id and f.ready =1) AND s.maxParticipant>="
        + "  (select count(distinct f.id) from DuoTeam f where f.duoclass = w.id and f.ready =1) )," +

        " (select min(fl.createDate) from DuoFight fl where fl.duoclass = w.id), "
        + " (select min(fl.modificationDate) from DuoFight fl where fl.duoclass = w.id and fl.modificationDate > fl.createDate)"
        +

        ")" +

        SQL_FROM + IDuoclassDatabaseConstants.TABLE_DUOCLASS + " w " +

        // " LEFT JOIN FETCH w." + IFighterDatabaseConstants.AGE + " a " +
        SQL_WHERE + " w." + IDuoclassDatabaseConstants.COMPLETE + "='N' and id in ( ";

    private static String SQL_DUOCLASSES_FOR_USER_PART_2 =
        SQL_ORDER_BY + "w.age." + IAgeDatabaseConstants.ORDER_NUMBER + " , " + "w." + IDuoclassDatabaseConstants.AGE
            + ", w." + IDuoclassDatabaseConstants.SEX;

    private static String SQL_USER_DUOCLASSES_FOR_USER = SQL_FROM + IUserDuoclassDatabaseConstants.TABLE_USERDUOGCLASS + " uf " + SQL_WHERE
        + "uf." + IUserDuoclassDatabaseConstants.USER_ID + "=?";

    @SuppressWarnings( "unchecked" )
    public List<Duoclass> getCurrentAndPlanedDuoClassesForUser( Long userId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( SQL_USER_DUOCLASSES_FOR_USER );
            q.setLong( 0, userId );
            List<UserDuoclass> list = q.list();
            StringBuffer sb = new StringBuffer( 1024 );
            for ( UserDuoclass item : list )
            {
                sb.append( item.getDuoclassId() );
                sb.append( "," );
            }

            if ( sb.length() > 0 )
            {
                sb.deleteCharAt( sb.length() - 1 );
                sb.append( " ) " );

                q = getSession().createQuery( SQL_DUOCLASSES_FOR_USER_PART_1 + sb.toString()
                    + SQL_DUOCLASSES_FOR_USER_PART_2 );
                q.setLong( 0, TypeUtil.LONG_EMPTY );
                return (List<Duoclass>) q.list();
            }
            return new ArrayList<Duoclass>();
        }
        catch ( Exception e )
        {
            log.fatal( "Can't getCurrentAndPlanedClassesForUser: " + e.toString() );
            throw new JJWDataLayerException( e );
        }

    }              
                
                
    private static String SQL_NEWACLASSES_FOR_USER_PART_1 = SQL_SELECT + " new Newaclass(w, "
        + " (select count(distinct f.id) from NewaFighter f where f.newaclass = w.id and f.ready=1 ),"
        + " (SELECT COUNT(DISTINCT fl.id) FROM NewaFight fl WHERE fl.newaclass = w.id ),"
        + " (SELECT COUNT(DISTINCT fl.id) FROM NewaFight fl WHERE fl.newaclass = w.id AND fl.winnerId IS NOT NULL AND fl.winnerId >= 0 "
        + "    AND fl.fighterIdBlue not in ( ? )),"
        + "(select s.fightsystemType from Fightsystem s where s.minParticipant<="
        + "  (select count(distinct f.id) from NewaFighter f where f.newaclass = w.id and f.ready =1) AND s.maxParticipant>="
        + "  (select count(distinct f.id) from NewaFighter f where f.newaclass = w.id and f.ready =1) )," +

        " (select min(fl.createDate) from NewaFight fl where fl.newaclass = w.id), "
        + " (select min(fl.modificationDate) from NewaFight fl where fl.newaclass = w.id and fl.modificationDate > fl.createDate)"
        +

        ")" +

        SQL_FROM + INewaWeightclassDatabaseConstants.TABLE_NEWACLASS + " w " +

        // " LEFT JOIN FETCH w." + IFighterDatabaseConstants.AGE + " a " +
        SQL_WHERE + " w." + INewaWeightclassDatabaseConstants.COMPLETE + "='N' and id in ( ";

    private static String SQL_NEWACLASSES_FOR_USER_PART_2 =
        SQL_ORDER_BY + "w.age." + IAgeDatabaseConstants.ORDER_NUMBER + " , " + "w." + INewaWeightclassDatabaseConstants.AGE
            + ", w." + INewaWeightclassDatabaseConstants.SEX + ", w." + INewaWeightclassDatabaseConstants.WEIGHT_LIMIT;

    private static String SQL_USER_NEWACLASSES_FOR_USER = SQL_FROM + IUserNewaclassDatabaseConstants.TABLE_USERNEWACLASS + " uf " + SQL_WHERE
        + "uf." + IUserNewaclassDatabaseConstants.USER_ID + "=?";

    @SuppressWarnings( "unchecked" )
    public List<Newaclass> getCurrentAndPlanedNewaClassesForUser( Long userId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( SQL_USER_NEWACLASSES_FOR_USER );
            q.setLong( 0, userId );
            List<UserNewaclass> list = q.list();
            StringBuffer sb = new StringBuffer( 1024 );
            for ( UserNewaclass item : list )
            {
                sb.append( item.getNewaclassId() );
                sb.append( "," );
            }

            if ( sb.length() > 0 )
            {

                sb.deleteCharAt( sb.length() - 1 );
                sb.append( " ) " );

                q = getSession().createQuery( SQL_NEWACLASSES_FOR_USER_PART_1 + sb.toString()
                    + SQL_NEWACLASSES_FOR_USER_PART_2 );
                q.setLong( 0, TypeUtil.LONG_EMPTY );
                return (List<Newaclass>) q.list();
            }
            return new ArrayList<Newaclass>();
        }
        catch ( Exception e )
        {
            log.fatal( "Can't getCurrentAndPlanedClassesForUser: " + e.toString() );
            throw new JJWDataLayerException( e );
        }
    }
    
    
    private static String SQL_USERS_TO_CLASSES =
      SQL_SELECT + "'F'," + IUserFightingclassDatabaseConstants.SQL_FIGHTINGCLASS_ID + ","+ IUserFightingclassDatabaseConstants.SQL_USER_ID + ","+ IUserFightingclassDatabaseConstants.SQL_ORDER_NUMBER +  SQL_FROM + SQL_TABLE_USERFIGHTINGCLASS + " union " +
      SQL_SELECT + "'D'," + IUserDuoclassDatabaseConstants.SQL_DUOCLASS_ID           + ","+ IUserDuoclassDatabaseConstants.SQL_USER_ID +      "," + IUserDuoclassDatabaseConstants.SQL_ORDER_NUMBER +   SQL_FROM + IUserDuoclassDatabaseConstants.SQL_TABLE_USERDUOCLASS + " union " +
      SQL_SELECT + "'N'," + IUserNewaclassDatabaseConstants.SQL_NEWACLASS_ID         + ","+ IUserNewaclassDatabaseConstants.SQL_USER_ID +     "," + IUserNewaclassDatabaseConstants.SQL_ORDER_NUMBER +  SQL_FROM + IUserNewaclassDatabaseConstants.SQL_TABLE_USERNEWACLASS                     
                                    ;
    
    public Map<String, UserToClass>getallUsersToClasses() throws JJWDataLayerException
    {
        try
        {
            Map<String, UserToClass> ret = new HashMap<String, UserToClass>();
            Query q = getSession().createSQLQuery( SQL_USERS_TO_CLASSES );
            UserToClass utc;
            List<Object> list = q.list();
            Object[] hlp;
            for ( Object item : list )
            {
                hlp = (Object[]) item;
                utc = new UserToClass();
                utc.setDicipline( (String) hlp[0] );
                utc.setClassId( ( (BigInteger) hlp[1] ).longValue() );
                utc.setUserId( ( (BigInteger) hlp[2] ).longValue() );
                utc.setOrder( ( (Integer) hlp[3] ).intValue() );
                ret.put(utc.getDicipline()+utc.getUserId()+utc.getClassId(), utc );
            }
            return ret;
        }
        catch ( Exception e )
        {
            log.fatal( "Can't getallUsersToClasses: " + e.toString() );
            throw new JJWDataLayerException( e );
        }
    }
    

}
