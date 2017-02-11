/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : UserFightingclassManagerImpl.java
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

package de.jjw.service.impl.fighting;

import java.sql.Timestamp;
import java.util.Locale;
import java.util.Map;

import de.jjw.dao.fighting.UserFightingclassDao;
import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.fighting.UserFightingclass;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.fighting.UserFightingclassManager;
import de.jjw.service.impl.BaseManager;
import de.jjw.util.JJWTwoLongDTO;

public class UserFightingclassManagerImpl
    extends BaseManager
    implements UserFightingclassManager
{

    private UserFightingclassDao userFightingclassDao;

    @Override
    public Map<JJWTwoLongDTO, UserFightingclass> getAssignMap( Locale locale )
        throws JJWManagerException
    {
        try
        {
            return userFightingclassDao.getAssignMap();
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    @Override
    public void saveUserFightingclass( UserFightingclass uf )
        throws JJWManagerException, OptimisticLockingException
    {
        // TODO Auto-generated method stub

    }

    public void setUserFightingclassDao( UserFightingclassDao userFightingclassDao )
    {
        this.userFightingclassDao = userFightingclassDao;
    }

    @Override
    public void toggleAccess( Long fightingclassId, Long userId, ServiceExchangeContext serviceExchangeContext )
        throws JJWManagerException
    {
        try
        {          
            UserFightingclass uf = new UserFightingclass();
            uf.setFightingclassId( fightingclassId );
            uf.setUserId( userId );
            uf.setCreateDate( new Timestamp( System.currentTimeMillis() ) );
            uf.setCreateId( serviceExchangeContext.getUserId() );
            uf.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
            uf.setModificationId( serviceExchangeContext.getUserId() );
            userFightingclassDao.toggleAccess( uf );
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
           userFightingclassDao.resetOrderNumbers( userId );
            
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
            return userFightingclassDao.isAccessForTatamiUser( userId, fightingclassId );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    @Override
    public void moveUp( Long fightingclassId, Long userId, ServiceExchangeContext serviceExchangeContext )
        throws JJWManagerException
    {
        try
        {
            userFightingclassDao.moveUp(  fightingclassId,  userId );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }

        
    }

    @Override
    public void moveDown( Long fightingclassId, Long userId, ServiceExchangeContext serviceExchangeContext )
        throws JJWManagerException
    {
        try
        {
            userFightingclassDao.moveDown( fightingclassId, userId );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }

        
    }
}
