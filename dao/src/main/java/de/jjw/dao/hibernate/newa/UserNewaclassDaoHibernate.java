/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : UserNewaclassDaoHibernate.java
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

package de.jjw.dao.hibernate.newa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import de.jjw.dao.hibernate.BaseDaoHibernate;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.dao.hibernate.duo.IUserDuoclassDatabaseConstants;
import de.jjw.dao.hibernate.fighting.IUserFightingclassDatabaseConstants;
import de.jjw.dao.newa.UserNewaclassDao;
import de.jjw.model.newa.UserNewaclass;
import de.jjw.util.JJWTwoLongDTO;

public class UserNewaclassDaoHibernate
    extends BaseDaoHibernate
    implements IUserNewaclassDatabaseConstants, UserNewaclassDao, INewaFightDatabaseConstants
{

    private static String SQL_GET_ASSIGN_LIST =
 SQL_FROM + TABLE_USERNEWACLASS + " f " + SQL_LEFT + SQL_JOIN
        + SQL_FETCH + "f." + IUserNewaclassDatabaseConstants.NEWACLASS + " fc " + SQL_LEFT + SQL_JOIN + SQL_FETCH
        + "f."
            + USER + " u ";

    @Override
    public Map<JJWTwoLongDTO, UserNewaclass> getAssignMap()
        throws JJWDataLayerException
    {

        try
        {
            Query q = getSession().createQuery( SQL_GET_ASSIGN_LIST );
            JJWTwoLongDTO dto = null;
            List<UserNewaclass> list = q.list();
            Map<JJWTwoLongDTO, UserNewaclass> assignMap = new HashMap<JJWTwoLongDTO, UserNewaclass>( list.size() );
            for ( UserNewaclass item : list )
            {
                dto = new JJWTwoLongDTO( item.getNewaclassId(), item.getUserId() );
                assignMap.put( dto, item );
            }
            return assignMap;
        }
        catch ( Exception e )
        {
            log.error( "can not getAssignList", e );
            return null;
        }
    }

    public void saveUserNewaclass( UserNewaclass uf )
        throws JJWDataLayerException
    {
        try
        {
            getHibernateTemplate().saveOrUpdate( uf );
        }
        catch ( Exception e )
        {
            log.fatal( "Can't save saveUserNewaclass: " + uf.toString() );
            throw new JJWDataLayerException( e );
        }
    }

    private static String SQL_TOGGLE_ACCESS =
 SQL_FROM + TABLE_USERNEWACLASS + " f " + SQL_WHERE + "f."
        + IUserNewaclassDatabaseConstants.NEWACLASS_ID + "=? and f." + USER_ID + "=?";

    
    private static String SQL_TOGGLE_ACCESS_MAX_ORDER_NUMBER =
                    SQL_SELECT+ "max(a) from ( " + 
                    SQL_SELECT+ "max("+ IUserFightingclassDatabaseConstants.SQL_ORDER_NUMBER + ") as a " + SQL_FROM + IUserFightingclassDatabaseConstants.SQL_TABLE_USERFIGHTINGCLASS + " f " + SQL_WHERE + "f."  + SQL_USER_ID + "=? union " +
                    SQL_SELECT+ "max("+ IUserDuoclassDatabaseConstants.SQL_ORDER_NUMBER + ")  as a " + SQL_FROM + IUserDuoclassDatabaseConstants.SQL_TABLE_USERDUOCLASS + " f " + SQL_WHERE + "f."  + IUserDuoclassDatabaseConstants.SQL_USER_ID + "=? union " +
                    SQL_SELECT+ "max("+ IUserNewaclassDatabaseConstants.SQL_ORDER_NUMBER + ")  as a " + SQL_FROM + IUserNewaclassDatabaseConstants.SQL_TABLE_USERNEWACLASS + " f " + SQL_WHERE + "f."  + IUserNewaclassDatabaseConstants.SQL_USER_ID + "=? ) as tt "                    
                    ;
    
    @Override
    public void toggleAccess( UserNewaclass uf )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( SQL_TOGGLE_ACCESS );
            q.setLong( 0, uf.getNewaclassId() );
            q.setLong( 1, uf.getUserId() );
            List<UserNewaclass> list = q.list();
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
                                    SQL_SELECT+ "min("+ IUserFightingclassDatabaseConstants.SQL_ORDER_NUMBER + ") as a " + SQL_FROM + IUserFightingclassDatabaseConstants.SQL_TABLE_USERFIGHTINGCLASS + " f " + SQL_WHERE + "f."  + SQL_USER_ID + "=? union " +
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
                    SQL_UPDATE + SQL_TABLE_USERNEWACLASS + SQL_SET + IUserNewaclassDatabaseConstants.SQL_ORDER_NUMBER + " = " +IUserNewaclassDatabaseConstants.SQL_ORDER_NUMBER +" +1 "
                   + SQL_WHERE + IUserNewaclassDatabaseConstants.SQL_NEWACLASS_ID + "=? and " + SQL_USER_ID + "=?" + SQL_AND + IUserNewaclassDatabaseConstants.SQL_ORDER_NUMBER +"< 999";
    @Override
    public void moveUp(  Long newaclassId, Long userId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createSQLQuery( SQL_MOVE_UP );
            q.setLong( 0, newaclassId );
            q.setLong( 1,userId );
            q.executeUpdate();            
        }
        catch ( Exception e )
        {
            log.fatal( "Can't moveUp: " + e.toString() );
            throw new JJWDataLayerException( e );
        }

    }
    

    private static String SQL_MOVE_DOWN =
                    SQL_UPDATE + SQL_TABLE_USERNEWACLASS + SQL_SET + IUserNewaclassDatabaseConstants.SQL_ORDER_NUMBER + " = " +IUserNewaclassDatabaseConstants.SQL_ORDER_NUMBER +" -1 "
                                    + SQL_WHERE + IUserNewaclassDatabaseConstants.SQL_NEWACLASS_ID + "=? and " + SQL_USER_ID + "=?" + SQL_AND + IUserNewaclassDatabaseConstants.SQL_ORDER_NUMBER +"> 1";
                    
    @Override
    public void moveDown( Long newaclassId, Long userId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createSQLQuery( SQL_MOVE_DOWN );
            q.setLong( 0, newaclassId );
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
 SQL_FROM + TABLE_USERNEWACLASS + " f " + SQL_WHERE + "f."
        + IUserNewaclassDatabaseConstants.NEWACLASS_ID + "=? and f." + USER_ID + "=?";

    @Override
    public boolean isAccessForTatamiUser( long userId, long fightingclassId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( SQL_IS_ACCESS_FOR_TATMI_USER );
            q.setLong( 0, fightingclassId );
            q.setLong( 1, userId );

            List<UserNewaclass> list = q.list();
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
}
