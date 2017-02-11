/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : UserDuoclassManagerImpl.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:47
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

package de.jjw.service.impl.duo;

import java.sql.Timestamp;
import java.util.Locale;
import java.util.Map;

import de.jjw.dao.duo.UserDuoclassDao;
import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.duo.UserDuoclass;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.duo.UserDuoclassManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.impl.BaseManager;
import de.jjw.util.JJWTwoLongDTO;

public class UserDuoclassManagerImpl
    extends BaseManager
    implements UserDuoclassManager
{

    private UserDuoclassDao userDuoclassDao;

    @Override
    public Map<JJWTwoLongDTO, UserDuoclass> getAssignMap( Locale locale )
        throws JJWManagerException
    {
        try
        {
            return userDuoclassDao.getAssignMap();
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    @Override
    public void saveUserDuoclass( UserDuoclass uf )
        throws JJWManagerException, OptimisticLockingException
    {
        // TODO Auto-generated method stub

    }

    public void setUserDuoclassDao( UserDuoclassDao userDuoclassDao )
    {
        this.userDuoclassDao = userDuoclassDao;
    }

    @Override
    public void toggleAccess( Long duoclassId, Long userId, ServiceExchangeContext serviceExchangeContext )
        throws JJWManagerException
    {
        try
        {

            UserDuoclass ud = new UserDuoclass();
            ud.setDuoclassId( duoclassId );
            ud.setUserId( userId );
            ud.setCreateDate( new Timestamp( System.currentTimeMillis() ) );
            ud.setCreateId( serviceExchangeContext.getUserId() );
            ud.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
            ud.setModificationId( serviceExchangeContext.getUserId() );
            userDuoclassDao.toggleAccess( ud );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }
    
    
    @Override
    public void resetOrderNumbers(  Long userId)
        throws JJWManagerException
    {
        try
        {
            userDuoclassDao.resetOrderNumbers( userId );            
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    @Override
    public boolean isAccessForTatamiUser( long userId, long duoclassId )
        throws JJWManagerException
    {
        try
        {
            return userDuoclassDao.isAccessForTatamiUser( userId, duoclassId );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }
    
    @Override
    public void moveUp( Long duoclassId, Long userId, ServiceExchangeContext serviceExchangeContext )
        throws JJWManagerException
    {
        try
        {
            userDuoclassDao.moveUp(  duoclassId,  userId );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }

        
    }

    @Override
    public void moveDown( Long duoclassId, Long userId, ServiceExchangeContext serviceExchangeContext )
        throws JJWManagerException
    {
        try
        {
            userDuoclassDao.moveDown( duoclassId, userId );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }

        
    }
}
