/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : UserDaoHibernate.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:41
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

package de.jjw.dao.hibernate;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import de.jjw.dao.UserDao;
import de.jjw.dao.hibernate.admin.IAgeDatabaseConstants;
import de.jjw.dao.hibernate.admin.IUserRoleDatabaseConstants;
import de.jjw.dao.hibernate.duo.IDuoFightDatabaseConstants;
import de.jjw.dao.hibernate.duo.IDuoTeamDatabaseConstants;
import de.jjw.dao.hibernate.duo.IDuoclassDatabaseConstants;
import de.jjw.dao.hibernate.duo.IUserDuoclassDatabaseConstants;
import de.jjw.dao.hibernate.fighting.IFightDatabaseConstants;
import de.jjw.dao.hibernate.fighting.IFighterDatabaseConstants;
import de.jjw.dao.hibernate.fighting.IUserFightingclassDatabaseConstants;
import de.jjw.dao.hibernate.fighting.IWeightclassDatabaseConstants;
import de.jjw.dao.hibernate.newa.INewaFightDatabaseConstants;
import de.jjw.dao.hibernate.newa.INewaWeightclassDatabaseConstants;
import de.jjw.dao.hibernate.newa.IUserNewaclassDatabaseConstants;
import de.jjw.model.Account;
import de.jjw.model.LabelValue;
import de.jjw.model.User;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;

/**
 * This class interacts with Spring's HibernateTemplate to save/delete and
 * retrieve User objects.
 * <p/>
 * <p><a href="UserDaoHibernate.java.html"><i>View Source</i></a></p>
 */
public class UserDaoHibernate
    extends BaseDaoHibernate
    implements UserDao, UserDetailsService, IValueConstants
{



    private static final int USER_ROLE = 2;

    private static final int ADMIN_ROLE = 1;

    private static final String CHECK_REMOVE_USER_FIGHTING = "From " + IFighterDatabaseConstants.TABLE_FIGHTER
        + " where " + IFighterDatabaseConstants.MODIFICATION_ID + "=?";

    private static final String CHECK_REMOVE_USER_DUO = "From " + IDuoTeamDatabaseConstants.TABLE_DUOTEAM + " where "
        + IDuoTeamDatabaseConstants.MODIFICATION_ID + "=?";


    private static String USER_ALL_ORDER_BY_USERNAME = "from User u order by u.username";

    private static String TATAMI_USER_ALL_ORDER_BY_USERNAME2 =
        //"select new User (u, " +
//    " select sum(xx.myTime) " +
//      " from( "+
        " SELECT count(w.age)*a.estimatedTime as myTime,a.estimatedTime,w.age" +
            " FROM Fight f left join fetch f.fightingclass w left join fetch w.age a " +
            " where w.id in (SELECT u.fightingclassId FROM UserFightingclass u where u.userId=2) and f.winnerId < 0 " +
            " group by w.age "
//      ") xx"
        ;

    private static String TATAMI_USER_ALL_ORDER_BY_USERNAME = " from User u where u.context=3 order by u.username";


    private static String TATAMI_PLANNED_USE_TIME_FIGHTING =
        "select ac.id,  " + " (SELECT sum(a.estimatedTime) " + " FROM " +
            IWeightclassDatabaseConstants.SQL_TABLE_WEIGHTCLASS + " w left join " +
            IFightDatabaseConstants.SQL_TABLE_FIGHT + " f on w." + IWeightclassDatabaseConstants.ID + " = f." +
            IFightDatabaseConstants.FIGHTINGCLASS + " left join " + IAgeDatabaseConstants.SQL_TABLE_AGE +
            " a on a.id = " + IWeightclassDatabaseConstants.AGE + " where " + " w." +
            IWeightclassDatabaseConstants.COMPLETE + " <> 'Y' and " + " w." + IWeightclassDatabaseConstants.ID +
            " in (SELECT u." + IUserFightingclassDatabaseConstants.SQL_FIGHTINGCLASS_ID + " FROM " +
            IUserFightingclassDatabaseConstants.SQL_TABLE_USERFIGHTINGCLASS + " u where u." +
            IUserFightingclassDatabaseConstants.SQL_USER_ID + "=ac.id)" + " and f." +
            IFightDatabaseConstants.WINNER_ID + " < 0 ) as myTime from account ac";

    private static String TATAMI_PLANNED_USE_TIME_DUO =
        "select ac.id,  " + " (SELECT sum(a.estimatedTimeDuo) " + " FROM " +
            IDuoclassDatabaseConstants.SQL_TABLE_DUOCLASS + " w left join " +
            IDuoFightDatabaseConstants.SQL_TABLE_DUO_FIGHT + " f on w." + IDuoclassDatabaseConstants.ID + " = f." +
            IDuoFightDatabaseConstants.DUOCLASS + " left join " + IAgeDatabaseConstants.SQL_TABLE_AGE +
            " a on a.id = " + IDuoclassDatabaseConstants.AGE + " where " + " w." + IDuoclassDatabaseConstants.COMPLETE +
            " <> 'Y' and " + " w." + IDuoclassDatabaseConstants.ID + " in (SELECT u." +
            IUserDuoclassDatabaseConstants.SQL_DUOCLASS_ID + " FROM " +
            IUserDuoclassDatabaseConstants.SQL_TABLE_USERDUOCLASS + " u where u." +
            IUserDuoclassDatabaseConstants.SQL_USER_ID + "=ac.id)" + " and f." + IDuoFightDatabaseConstants.WINNER_ID +
            " < 0 ) as myTime from account ac";
    
    private static String TATAMI_PLANNED_USE_TIME_NEWA =
 "select ac.id,  " + " (SELECT sum(a.estimatedTimeNewa) "
        + " FROM "
        +
                                    INewaWeightclassDatabaseConstants.SQL_TABLE_NEWACLASS + " w left join " +
                                    INewaFightDatabaseConstants.SQL_TABLE_FIGHT + " f on w." + INewaWeightclassDatabaseConstants.ID + " = f." +
                                    INewaFightDatabaseConstants.NEWACLASS + " left join " + IAgeDatabaseConstants.SQL_TABLE_AGE +
                                    " a on a.id = " + INewaWeightclassDatabaseConstants.AGE + " where " + " w." +
                                    INewaWeightclassDatabaseConstants.COMPLETE + " <> 'Y' and " + " w." + INewaWeightclassDatabaseConstants.ID +
                                    " in (SELECT u." + IUserNewaclassDatabaseConstants.SQL_NEWACLASS_ID + " FROM " +
                                    IUserNewaclassDatabaseConstants.SQL_TABLE_USERNEWACLASS + " u where u." +
                                    IUserNewaclassDatabaseConstants.SQL_USER_ID + "=ac.id)" + " and f." +
                                    INewaFightDatabaseConstants.WINNER_ID + " < 0 ) as myTime from account ac";

    private static String TATAMI_PLANNED_USE_TIME =
 "select  id, sum(myTime) " + " From( "
        + TATAMI_PLANNED_USE_TIME_FIGHTING + " union all " + TATAMI_PLANNED_USE_TIME_DUO + " union all "
        + TATAMI_PLANNED_USE_TIME_NEWA + ") xx group by id";

    //" from User u where u.context=3 order by u.username";

    private static String USER_BY_USERNAME = "from User where username=?";

    /**
     * @see de.jjw.dao.UserDao#getUser(Long)
     */
    public User getUser( Long userId )
    {
        User user = (User) getHibernateTemplate().get( User.class, userId );

        if ( user == null )
        {
            log.warn( "uh oh, user '" + userId + "' not found..." );
            throw new ObjectRetrievalFailureException( User.class, userId );
        }

        return user;
    }

    /**
     * @see de.jjw.dao.UserDao#getUsers(de.jjw.model.User)
     */
    public List<User> getUsers()
    {
        return getHibernateTemplate().find( USER_ALL_ORDER_BY_USERNAME );
    }

    /**
     * @throws JJWDataLayerException
     * @see de.jjw.dao.UserDao#getUsers(de.jjw.model.User)
     */
    public List<User> getTatamiUsersFighting()
        throws JJWDataLayerException
    {
        try
        {
            Query q1 = getSession().createQuery( TATAMI_USER_ALL_ORDER_BY_USERNAME );
            Query q2 = getSession().createSQLQuery( TATAMI_PLANNED_USE_TIME );

            List<User> userList = (List<User>) q1.list();
            List list = q2.list();

            Object[] hlp;
            for ( User user : userList )
            {
                for ( Object item : list )
                {
                    hlp = (Object[]) item;
                    if ( hlp[1] != null && user.getId().equals( ( (BigInteger) hlp[0] ).longValue() ) )
                    {
                        user.setTatamiTime( ( (BigDecimal) hlp[1] ).longValue() );
                        break;
                    }
                }
            }

            return userList;
        }
        catch ( Exception e )
        {
            log.error( "can not getTatamiUsers", e );
            throw new JJWDataLayerException( e );
        }
    }


    /**
     * @throws JJWDataLayerException
     * @see de.jjw.dao.UserDao#getUsers(de.jjw.model.User)
     */
    public List<User> getTatamiUsersDuo()
        throws JJWDataLayerException
    {
        try
        {
            Query q1 = getSession().createQuery( TATAMI_USER_ALL_ORDER_BY_USERNAME );
            Query q2 = getSession().createSQLQuery( TATAMI_PLANNED_USE_TIME );

            List<User> userList = (List<User>) q1.list();
            List list = q2.list();

            Object[] hlp;
            for ( User user : userList )
            {
                for ( Object item : list )
                {
                    hlp = (Object[]) item;
                    if ( hlp[1] != null && user.getId().equals( ( (BigInteger) hlp[0] ).longValue() ) )
                    {
                        user.setTatamiTime( ( (BigDecimal) hlp[1] ).longValue() );
                        break;
                    }
                }
            }

            return userList;
        }
        catch ( Exception e )
        {
            log.error( "can not getTatamiUsers", e );
            throw new JJWDataLayerException( e );
        }
    }
    
    
    /**
     * @throws JJWDataLayerException
     * @see de.jjw.dao.UserDao#getUsers(de.jjw.model.User)
     */
    public List<User> getTatamiUsersNewa()
        throws JJWDataLayerException
    {
        try
        {
            Query q1 = getSession().createQuery( TATAMI_USER_ALL_ORDER_BY_USERNAME );
            Query q2 = getSession().createSQLQuery( TATAMI_PLANNED_USE_TIME );

            List<User> userList = (List<User>) q1.list();
            List list = q2.list();

            Object[] hlp;
            for ( User user : userList )
            {
                for ( Object item : list )
                {
                    hlp = (Object[]) item;
                    if ( hlp[1] != null && user.getId().equals( ( (BigInteger) hlp[0] ).longValue() ) )
                    {
                        user.setTatamiTime( ( (BigDecimal) hlp[1] ).longValue() );
                        break;
                    }
                }
            }

            return userList;
        }
        catch ( Exception e )
        {
            log.error( "can not getTatamiUsers", e );
            throw new JJWDataLayerException( e );
        }
    }
    

    /**
     * @see de.jjw.dao.UserDao#saveUser(de.jjw.model.User)
     */
    public void saveUser( final User user )
    {
        if ( log.isDebugEnabled() )
        {
            log.debug( "user's id: " + user.getId() );
        }
        getHibernateTemplate().saveOrUpdate( user );
        // necessary to throw a DataIntegrityViolation and catch it in UserManager
        getHibernateTemplate().flush();
    }

    /**
     * @see de.jjw.dao.UserDao#removeUser(Long) only removing, when no fights were saved
     */
    public int removeUser( Long userId )
    {
        Query q1 = getSession().createQuery( CHECK_REMOVE_USER_FIGHTING );
        q1.setLong( 0, userId );
        Query q2 = getSession().createQuery( CHECK_REMOVE_USER_DUO );
        q2.setLong( 0, userId );
        if ( q1.list().isEmpty() && q2.list().isEmpty() )
        {
            getHibernateTemplate().delete( getUser( userId ) );
            return 0;
        }
        else
            return 1;
    }


    public UserDetails loadUserByUsername( String username )
        throws UsernameNotFoundException
    {
        List users = getHibernateTemplate().find( USER_BY_USERNAME, username );

        if ( users == null || users.isEmpty() )
        {
            throw new UsernameNotFoundException( "user '" + username + "' not found..." );
        }
        else
        {
            User user = (User) users.get( 0 );
            Set<GrantedAuthority> dbAuthsSet = new HashSet<GrantedAuthority>();
            dbAuthsSet.add( new SimpleGrantedAuthority( "ROLE_"
                +
                                                        ( (LabelValue) user.getRoleList().get( 0 ) ).getValue().toUpperCase() ) );
            return new User( user, dbAuthsSet );
        }
    }


    public User getUserByUsername( String username )
        throws UsernameNotFoundException
    {
        List users = getHibernateTemplate().find( USER_BY_USERNAME, username );
        if ( users == null || users.isEmpty() )
        {
            throw new UsernameNotFoundException( "user '" + username + "' not found..." );
        }
        else
        {
            return (User) users.get( 0 );
        }
    }

    /**
     * @see UserDoa.testLoginAllowed
     */
    public Account testLoginAllowed( String username, String password )
    {
        User user;
        Account account = new Account();
        try
        {
            user = getUserByUsername( username );

            // test if password allowed
            if ( TypeUtil.isEmptyOrDefault( password ) )
            {
                throw new Exception();
            }
            else
            {
                if ( user.isEnabled() && password.equals( user.getPassword() ) && !user.isAccountExpired() )
                {
                    // user and password correct
                    if ( user.isAccountLocked() )
                    {
                        if ( TypeUtil.isTimestampOlderThenCurrent( user.getAccountLockedUntilDate() ) )
                        {
                            // account is no loner locked
                            user.setAccountLocked( false );
                            user.setLogInErrors( TypeUtil.INT_DEFAULT );
                            user.setModificationDate( TypeUtil.getCurrentTimestamp() );
                            user.setModificationId( MODIFICATION_ID_LOCKED_TIME_FREE );

                            account.setLoginAllowed( true );
                            account.setUserId( user.getId() );
                        }
                        else
                        {
                            account.setLoginLocked( true );
                        }
                    }
                    else
                    {
                        account.setUserId( user.getId() );
                        account.setLoginAllowed( true );
                    }
                }
                else
                {
                    // user and password not correct
                    handleWrongLogin( user );
                    if ( user.getLogInErrors() == MAX_LOGIN_FAILURE )
                    {
                        account.setTooMuchWrongTry( true );
                    }
                }
            }
        }
        catch ( UsernameNotFoundException e )
        {
            ;
        }
        catch ( Exception e )
        {
            ;
        }
        return account;
    }

    /**
     * increments Errors and set Lock, if nessesary
     * Save user
     *
     * @param user
     */
    private void handleWrongLogin( User user )
    {
        user.incerementLogInErrors();
        user.incerementLogInErrorsAll();

        if ( user.getLogInErrors() == User.MAX_LOGIN_FAILURE )
        {
            user.setAccountLocked( true );
            //     user.setAccountLockedUntilDate(new Timestamp(System.currentTimeMillis()+User.LOCKED_TIME_AFTER_MAX_FAILURE));
        }
        user.setModificationDate( TypeUtil.getCurrentTimestamp() );
        user.setModificationId( MODIFICATION_ID_WRONG_LOGIN );

        saveUser( user );
    }

    /**
     * @see UserDoa.logInUser
     */
    public void logInUser( String username )
    {
        User user = getUserByUsername( username ).clone();
        user.setLastLogInDate( TypeUtil.getCurrentTimestamp() );
        user.setModificationDate( TypeUtil.getCurrentTimestamp() );
        user.setModificationId( MODIFICATION_ID_LOGIN );
        //getHibernateTemplate().saveOrUpdate(user);getHibernateTemplate().setFlushMode(getHibernateTemplate().FLUSH_AUTO)
        // necessary to throw a DataIntegrityViolation and catch it in UserManager
        //getHibernateTemplate().flush();
        //speichern(user);
    }

    private static String GET_USER_ROLE_ASSIGNMENT_BY_USER = "Select * from "
        + IUserRoleDatabaseConstants.SQL_TABLE_USER_ROLE + " where " + IUserRoleDatabaseConstants.USER_ID + "=?";

    private static String INSERT_USER_ROLE_ASSIGNMENT = "insert into " + IUserRoleDatabaseConstants.SQL_TABLE_USER_ROLE
        + " values (?,?)";

    private static String DELETE_USER_ROLE_ASSIGNMENT = "delete from " + IUserRoleDatabaseConstants.SQL_TABLE_USER_ROLE
        + " where " + IUserRoleDatabaseConstants.USER_ID + "=?";

    /**
     * in some cases the reference between role and account is delete. This methode restores it.
     */
    public void handleDeleteRoleAssignment()
    {
        List<User> users = getUsers();
        try
        {
            Query q, q2;
            for ( User user : users )
            {
                q = getSession().createSQLQuery( GET_USER_ROLE_ASSIGNMENT_BY_USER );
                try
                {
                    q.setLong( 0, user.getId() );
                    if ( null == q.uniqueResult() )
                    {
                        q2 = getSession().createSQLQuery( INSERT_USER_ROLE_ASSIGNMENT );
                        q2.setLong( 0, user.getId() );
                        if ( User.CONTEXT_ADMIN == user.getContext() )
                            q2.setLong( 1, ADMIN_ROLE );
                        if ( User.CONTEXT_TATAMI == user.getContext() )
                            q2.setLong( 1, USER_ROLE );
                        q2.executeUpdate();
                    }
                }
                catch ( NonUniqueResultException e )
                {
                    q2 = getSession().createSQLQuery( DELETE_USER_ROLE_ASSIGNMENT );
                    q2.setLong( 0, user.getId() );
                    q2.executeUpdate();
                }
            }
        }
        catch ( Exception e )
        {
            log.error( "handleDeleteRoleAssignment: ", e );
        }
    }
}
