/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : ProviderManager.java
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

package de.jjw.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.Assert;


public class ProviderManager
    extends DaoAuthenticationProvider

{
    // ~ Instance fields
    // ================================================================================================

    private PasswordEncoder passwordEncoder = new ShaPasswordEncoder();

    private SaltSource saltSource;

    private UserDetailsService userDetailsService;

    private boolean includeDetailsObject = true;

    // ~ Methods
    // ========================================================================================================

    protected void additionalAuthenticationChecks( UserDetails userDetails,
                                                   UsernamePasswordAuthenticationToken authentication )
        throws AuthenticationException
    {
        Object salt = null;
        if ( this.saltSource != null )
        {
            salt = this.saltSource.getSalt( userDetails );
        }

        if ( authentication.getCredentials() == null )
        {
            logger.debug( "Authentication failed: no credentials provided" );
            throw new BadCredentialsException(
                                               messages.getMessage( "AbstractUserDetailsAuthenticationProvider.badCredentials",
                                                                    "Bad credentials" ),
                                               includeDetailsObject ? userDetails : null );
        }

        String presentedPassword = authentication.getCredentials().toString();
        if ( !passwordEncoder.isPasswordValid( userDetails.getPassword(), presentedPassword, salt ) )
        {
            logger.debug( "Authentication failed: password does not match stored value" );
            throw new BadCredentialsException(
                                               messages.getMessage( "AbstractUserDetailsAuthenticationProvider.badCredentials",
                                                                    "Bad credentials" ),
                                               includeDetailsObject ? userDetails : null );
        }
    }

    protected void doAfterPropertiesSet()
        throws Exception
    {
        Assert.notNull( this.userDetailsService, "A UserDetailsService must be set" );
    }

    /**
     * Sets the PasswordEncoder instance to be used to encode and validate passwords. If not set, the password will be
     * compared as plain text.
     * <p>
     * For systems which are already using salted password which are encoded with a previous release, the encoder should
     * be of type {@code org.springframework.security.authentication.encoding.PasswordEncoder}. Otherwise, the
     * recommended approach is to use {@code org.springframework.security.crypto.password.PasswordEncoder}.
     * 
     * @param passwordEncoder must be an instance of one of the {@code PasswordEncoder} types.
     */

    public void setPasswordEncoder( Object passwordEncoder )
    {
        Assert.notNull( passwordEncoder, "passwordEncoder cannot be null" );
        if ( passwordEncoder instanceof PasswordEncoder )
        {
            this.passwordEncoder = (PasswordEncoder) passwordEncoder;
            return;
        }

        if ( passwordEncoder instanceof org.springframework.security.crypto.password.PasswordEncoder )
        {
            final org.springframework.security.crypto.password.PasswordEncoder delegate =
                (org.springframework.security.crypto.password.PasswordEncoder) passwordEncoder;
            this.passwordEncoder = new PasswordEncoder()
            {
                public String encodePassword( String rawPass, Object salt )
                {
                    checkSalt( salt );
                    return delegate.encode( rawPass );
                }

                public boolean isPasswordValid( String encPass, String rawPass, Object salt )
                {
                    checkSalt( salt );
                    return delegate.matches( rawPass, encPass );
                }

                private void checkSalt( Object salt )
                {
                    Assert.isNull( salt, "Salt value must be null when used with crypto module PasswordEncoder" );
                }
            };
            return;
        }
        throw new IllegalArgumentException( "passwordEncoder must be a PasswordEncoder instance" );
    }

    protected PasswordEncoder getPasswordEncoder()
    {
        return passwordEncoder;
    }

    /**
     * The source of salts to use when decoding passwords. <code>null</code> is a valid value, meaning the
     * <code>DaoAuthenticationProvider</code> will present <code>null</code> to the relevant
     * <code>PasswordEncoder</code>.
     * <p>
     * Instead, it is recommended that you use an encoder which uses a random salt and combines it with the password
     * field. This is the default approach taken in the {@code org.springframework.security.crypto.password} package.
     * 
     * @param saltSource to use when attempting to decode passwords via the <code>PasswordEncoder</code>
     */

    public void setSaltSource( SaltSource saltSource )
    {
        this.saltSource = saltSource;
    }

    protected SaltSource getSaltSource()
    {
        return saltSource;
    }

    public void setUserDetailsService( UserDetailsService userDetailsService )
    {
        this.userDetailsService = userDetailsService;
    }

    protected UserDetailsService getUserDetailsService()
    {
        return userDetailsService;
    }

    protected boolean isIncludeDetailsObject()
    {
        return includeDetailsObject;
    }

    /**
     * Determines whether the UserDetails will be included in the <tt>extraInformation</tt> field of a thrown
     * BadCredentialsException. Defaults to true, but can be set to false if the exception will be used with a remoting
     * protocol, for example.
     * 
     * @deprecated use
     *             {@link org.springframework.security.authentication.ProviderManager#setClearExtraInformation(boolean)}
     */

    public void setIncludeDetailsObject( boolean includeDetailsObject )
    {
        this.includeDetailsObject = includeDetailsObject;
    }
}