/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : UserNewaclassManagerImpl.java
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

package de.jjw.service.impl.newa;

import java.sql.Timestamp;
import java.util.Locale;
import java.util.Map;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.dao.newa.UserNewaclassDao;
import de.jjw.model.newa.UserNewaclass;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.impl.BaseManager;
import de.jjw.service.newa.UserNewaclassManager;
import de.jjw.util.JJWTwoLongDTO;

public class UserNewaclassManagerImpl
    extends BaseManager
    implements UserNewaclassManager
{

    private UserNewaclassDao userNewaclassDao;

    @Override
    public Map<JJWTwoLongDTO, UserNewaclass> getAssignMap( Locale locale )
        throws JJWManagerException
    {
        try
        {
            return userNewaclassDao.getAssignMap();
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    @Override
    public void saveUserNewaclass( UserNewaclass uf )
        throws JJWManagerException, OptimisticLockingException
    {
        // TODO Auto-generated method stub

    }

    public void setUserNewaclassDao( UserNewaclassDao userNewaclassDao )
    {
        this.userNewaclassDao = userNewaclassDao;
    }

    
    @Override
    public void resetOrderNumbers(  Long userId)
        throws JJWManagerException
    {
        try
        {
            userNewaclassDao.resetOrderNumbers( userId );
            
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }

    }

    
    @Override
    public void toggleAccess( Long newaclassId, Long userId, ServiceExchangeContext serviceExchangeContext )
        throws JJWManagerException
    {
        try
        {

            UserNewaclass uf = new UserNewaclass();
            uf.setNewaclassId( newaclassId );
            uf.setUserId( userId );
            uf.setCreateDate( new Timestamp( System.currentTimeMillis() ) );
            uf.setCreateId( serviceExchangeContext.getUserId() );
            uf.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
            uf.setModificationId( serviceExchangeContext.getUserId() );
            userNewaclassDao.toggleAccess( uf );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }

    }

    @Override
    public boolean isAccessForTatamiUser( long userId, long fightingclassId )
        throws JJWManagerException
    {
        try
        {
            return userNewaclassDao.isAccessForTatamiUser( userId, fightingclassId );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }
    
    
    @Override
    public void moveUp( Long newaclassId, Long userId, ServiceExchangeContext serviceExchangeContext )
        throws JJWManagerException
    {
        try
        {
            userNewaclassDao.moveUp(  newaclassId,  userId );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    @Override
    public void moveDown( Long newaclassId, Long userId, ServiceExchangeContext serviceExchangeContext )
        throws JJWManagerException
    {
        try
        {
            userNewaclassDao.moveDown( newaclassId, userId );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }
}
