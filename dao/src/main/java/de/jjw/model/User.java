/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : User.java
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

package de.jjw.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;

/**
 * User class - also used to generate the Hibernate mapping file.
 * <p/>
 * <p><a href="User.java.html"><i>View Source</i></a>
 *
 * @hibernate.class table="account"
 */
public class User
    extends BaseObject
    implements Serializable, UserDetails, IValueConstants, Cloneable
{
    private static final long serialVersionUID = 3832626162173359411L;

    public static long CONTEXT_ADMIN = 4l;

    public static long CONTEXT_TATAMI = 3l;

    protected Long id;

    protected String username;                    // required

    protected String password;                    // required

    protected String confirmPassword;

    protected String firstName;

    protected String lastName;
    //protected Address address = new Address();

    protected Integer context;

    protected Timestamp lastLogInDate;

    protected Long logInErrorsAll;                  //gesamtzahl aller LogIn fehler

    protected Integer logInErrors;

    protected Set roles = new HashSet();

    protected boolean enabled;

    protected boolean accountExpired;

    protected boolean accountLocked;

    protected Timestamp accountLockedUntilDate;

    protected boolean credentialsExpired;

    protected Timestamp accountExpireDate;

    protected Long tatamiTime;

    private Set<GrantedAuthority> authorities;

    public User( User user, Set<GrantedAuthority> authorities )
    {
        super();
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.confirmPassword = user.getConfirmPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.context = user.getContext();
        this.lastLogInDate = user.getLastLogInDate();
        this.logInErrorsAll = user.getLogInErrorsAll();
        this.logInErrors = user.getLogInErrors();
        this.roles = user.getRoles();
        this.enabled = user.isEnabled();
        this.accountExpired = user.isAccountExpired();
        this.accountLocked = user.isAccountLocked();
        this.accountLockedUntilDate = user.getAccountLockedUntilDate();
        this.credentialsExpired = user.isCredentialsExpired();
        this.accountExpireDate = user.getAccountExpireDate();
        this.authorities = Collections.unmodifiableSet( authorities );
    }

    public User( User user, Long tatamiTime )
    {
        super();
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.confirmPassword = user.getConfirmPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.context = user.getContext();
        this.lastLogInDate = user.getLastLogInDate();
        this.logInErrorsAll = user.getLogInErrorsAll();
        this.logInErrors = user.getLogInErrors();
        this.roles = user.getRoles();
        this.enabled = user.isEnabled();
        this.accountExpired = user.isAccountExpired();
        this.accountLocked = user.isAccountLocked();
        this.accountLockedUntilDate = user.getAccountLockedUntilDate();
        this.credentialsExpired = user.isCredentialsExpired();
        this.accountExpireDate = user.getAccountExpireDate();
        this.tatamiTime = tatamiTime;
    }


    public User()
    {
    }

    public User( String username )
    {
        this.username = username;
    }

    /**
     * @hibernate.id column="id" generator-class="native"
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @hibernate.property length="50" not-null="true" unique="true"
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * @hibernate.property column="password" not-null="true"
     */
    public String getPassword()
    {
        return password;
    }

    public String getConfirmPassword()
    {
        return confirmPassword;
    }

    /**
     * @hibernate.property column="firstname"  length="50"
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * @hibernate.property column="lastname"  length="50"
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * Returns the full name.
     */
    public String getFullName()
    {
        return firstName + ' ' + lastName;
    }

    /**
     * @hibernate.component
     */
    // public Address getAddress() {
    //    return address;
    //}

    /**
     * @hibernate.set table="user_role" cascade="save-update" lazy="false"
     * @hibernate.collection-key column="user_id"
     * @hibernate.collection-many-to-many class="de.jjw.model.Role" column="role_id"
     */
    public Set getRoles()
    {
        return roles;
    }

    /**
     * Adds a role for the user
     *
     * @param role
     */
    public void addRole( Role role )
    {
        getRoles().add( role );
    }


    public Collection<GrantedAuthority> getAuthorities()
    {
        return authorities;
    }

    /**
     * @hibernate.property column="accountEnabled" type="yes_no"
     */
    public boolean isEnabled()
    {
        return enabled;
    }

//    public int getEnabled() {
//      return enabled == true ? 1 : 0;
//  }

    /**
     * @hibernate.property column="accountExpired" not-null="true" type="yes_no"
     */
    public boolean isAccountExpired()
    {
        return accountExpired;
    }

    public boolean isAccountNonExpired()
    {
        return !isAccountExpired();
    }

    /**
     * @hibernate.property column="accountLocked" not-null="true" type="yes_no"
     */
    public boolean isAccountLocked()
    {
        return accountLocked;
    }


    public boolean isAccountNonLocked()
    {
        return !isAccountLocked();
    }

    /**
     * @hibernate.property column="credentialsExpired" not-null="true"  type="yes_no"
     */
    public boolean isCredentialsExpired()
    {
        return credentialsExpired;
    }


    public boolean isCredentialsNonExpired()
    {
        return !credentialsExpired;
    }

    public void setId( Long id )
    {
        this.id = id;
    }

    public void setUsername( String username )
    {
        this.username = username;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public void setConfirmPassword( String confirmPassword )
    {
        this.confirmPassword = confirmPassword;
    }

    public void setFirstName( String firstName )
    {
        this.firstName = firstName;
    }

    public void setLastName( String lastName )
    {
        this.lastName = lastName;
    }

    public void setRoles( Set roles )
    {
        this.roles = roles;
    }

    public void setAccountEnabled( boolean enabled )
    {
        this.enabled = enabled;
    }

    /**
     * Convert user roles to LabelValue objects for convenience.
     */
    public List getRoleList()
    {
        List userRoles = new ArrayList();

        if ( this.roles != null )
        {
            for ( Iterator it = roles.iterator(); it.hasNext(); )
            {
                Role role = (Role) it.next();

                // convert the user's roles to LabelValue Objects
                userRoles.add( new LabelValue( role.getName(), role.getName() ) );
            }
        }

        return userRoles;
    }

    public void setAccountExpired( boolean accountExpired )
    {
        this.accountExpired = accountExpired;
    }

    public void setAccountLocked( boolean accountLocked )
    {
        this.accountLocked = accountLocked;
    }

    public void setCredentialsExpired( boolean credentialsExpired )
    {
        this.credentialsExpired = credentialsExpired;
    }


    /**
     * @hibernate.property column="AccountLockedUntilDate"
     */
    public Timestamp getAccountLockedUntilDate()
    {
        return accountLockedUntilDate;
    }

    public void setAccountLockedUntilDate( Timestamp accountLockedUntilDate )
    {
        this.accountLockedUntilDate = accountLockedUntilDate;
    }

//    public void setAccountLockedUntilDate(Date _accountLockedUntilDate) {
//      this.accountLockedUntilDate = new Timestamp(_accountLockedUntilDate.getTime());
//  }

    /**
     * @hibernate.property column="Context" not-null="true"
     */
    public Integer getContext()
    {
        return context;
    }

    public void setContext( Integer context )
    {
        this.context = context;
    }

    /**
     * @hibernate.property column="LastLogInDate"
     */
    public Timestamp getLastLogInDate()
    {
        return lastLogInDate;
    }

    public void setLastLogInDate( Timestamp lastLogInDate )
    {
        this.lastLogInDate = lastLogInDate;
    }

    /**
     * @hibernate.property column="LogInErrors"
     */
    public Integer getLogInErrors()
    {
        return logInErrors;
    }

    public void setLogInErrors( Integer logInErrors )
    {
        this.logInErrors = logInErrors;
    }

    public void incerementLogInErrors()
    {
        if ( this.logInErrors < MAX_LOGIN_FAILURE )
        {
            this.logInErrors++;
        }
    }

    /**
     * @hibernate.property column="LogInErrorsAll" not-null="true"
     */
    public Long getLogInErrorsAll()
    {
        return logInErrorsAll;
    }

    public void setLogInErrorsAll( Long logInErrorsAll )
    {
        this.logInErrorsAll = logInErrorsAll;
    }

    public void incerementLogInErrorsAll()
    {
        if ( this.logInErrorsAll < TypeUtil.LONG_MAX )
        {
            this.logInErrorsAll++;
        }
    }

    public void setEnabled( boolean enabled )
    {
        this.enabled = enabled;
    }

//    public void setEnabled(int _enabled) {
//     if (_enabled < 1) this.enabled = false;
//     else this.enabled = true;
//  }

    public boolean equals( Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof User ) )
        {
            return false;
        }

        final User user = (User) o;

        if ( username != null ? !username.equals( user.getUsername() ) : user.getUsername() != null )
        {
            return false;
        }

        return true;
    }

    public int hashCode()
    {
        return ( username != null ? username.hashCode() : 0 );
    }

    public String toString()
    {
        // StringBuffer sb =
        // new ToStringBuilder( this, ToStringStyle.DEFAULT_STYLE ).append( "username", this.username ).append(
        // "accountEnabled", this.enabled ).append( "accountExpired", this.accountExpired ).append(
        // "credentialsExpired", this.credentialsExpired ).append( "accountLocked", this.accountLocked );
        //
        // // GrantedAuthority[] auths = this.getAuthorities();
        // // if ( auths != null )
        // // {
        // // sb.append( "Granted Authorities: " );
        // //
        // // for ( int i = 0; i < auths.length; i++ )
        // // {
        // // if ( i > 0 )
        // // {
        // // sb.append( ", " );
        // // }
        // // sb.append( auths[i].toString() );
        // // }
        // // }
        // // else
        // // {
        // // sb.append( "No Granted Authorities" );
        // }
        return "not impl";
    }

    /**
     * @hibernate.property column="AccountExpireDate"
     */
    public Timestamp getAccountExpireDate()
    {
        return accountExpireDate;
    }

    public void setAccountExpireDate( Timestamp accountExpireDate )
    {
        this.accountExpireDate = accountExpireDate;
    }

//    public void setAccountExpireDate(Date _accountExpireDate) {
//      this.accountExpireDate = new Timestamp (_accountExpireDate.getTime());
//  }

    public User clone()
    {
        User user = new User();

        user.setId( id == null ? null : Long.valueOf( id ) );
        user.setUsername( username == null ? null : new String( username ) );
        user.setPassword( password == null ? null : new String( password ) );
        user.setConfirmPassword( confirmPassword == null ? null : new String( confirmPassword ) );
        user.setFirstName( firstName == null ? null : new String( firstName ) );
        user.setLastName( lastName == null ? null : new String( lastName ) );
        //protected Address address = new Address();

        user.setContext( context == null ? null : Integer.valueOf( context ) );
        user.setCreateDate( createDate == null ? null : (Timestamp) createDate.clone() );
        user.setModificationDate( modificationDate == null ? null : (Timestamp) modificationDate.clone() );
        user.setCreateId( createId == null ? null : Long.valueOf( createId ) );
        user.setModificationId( modificationId == null ? null : Long.valueOf( modificationId ) );
        user.setLastLogInDate( lastLogInDate == null ? null : (Timestamp) lastLogInDate.clone() );
        user.setLogInErrorsAll( logInErrorsAll == null ? null : Long.valueOf( logInErrorsAll ) );
        user.setLogInErrors( logInErrors == null ? null : Integer.valueOf( logInErrors ) );
        user.setRoles( roles == null ? null : new HashSet( roles ) );
        user.setEnabled( enabled );
        user.setAccountExpired( accountExpired );
        user.setAccountLocked( accountLocked );
//        user.setAccountLockedUntilDate(accountLockedUntilDate == null ? null : (Timestamp)accountLockedUntilDate.clone());
        user.setCredentialsExpired( credentialsExpired );
//        user.setAccountExpireDate(accountExpireDate == null ? null : (Timestamp)accountExpireDate.clone())
        ;
        return user;
    }


    public Long getTatamiTime()
    {
        return tatamiTime;
    }


    public void setTatamiTime( Long tatamiTime )
    {
        this.tatamiTime = tatamiTime;
    }


}
