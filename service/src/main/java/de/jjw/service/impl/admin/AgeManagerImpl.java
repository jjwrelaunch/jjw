/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : AgeManagerImpl.java
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

package de.jjw.service.impl.admin;

import java.sql.Timestamp;
import java.util.List;

import de.jjw.dao.IDatabaseTableConstants;
import de.jjw.dao.admin.AgeDao;
import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.admin.Age;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.admin.AgeManager;
import de.jjw.service.exception.AgeInUseException;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.impl.BaseManager;
import de.jjw.service.mapper.admin.AgeMapper;
import de.jjw.util.TypeUtil;

public class AgeManagerImpl
    extends BaseManager
    implements AgeManager, IDatabaseTableConstants
{

    private AgeDao ageDao;

    public void setAgeDao( AgeDao ageDao )
    {
        this.ageDao = ageDao;

    }

    public Age getAge( Long ageId )
    {
        return ageDao.getAge( ageId );
    }

    public List<Age> getAllAges()
        throws JJWManagerException
    {
        return AgeMapper.mapAgeListFromDB( ageDao.getAllAges() );
    }

    public void saveAge( Age age )
        throws OptimisticLockingException, JJWManagerException
    {
        if ( null == age.getId() || TypeUtil.isEmptyOrDefault( age.getId().longValue() ) )
        {
            age.setId( null );
            age.setCreateDate( new Timestamp( System.currentTimeMillis() ) );
            age.setCreateId( 0l );
            int max = ageDao.getMaxOrderNumber();
            age.setOrderNumber( ++max );
            ageDao.saveAge( age );            
        }
        else
        {
            age.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
            optimisticDao.testLocking( TABLE_AGE, age.getId(), age.getModificationDate() );
            AgeMapper.mapAgeToDB( age, ageDao.getAge( age.getId() ) );
        }
    }

    public void removeAge( Age age )
        throws AgeInUseException
    {
        if ( ageDao.isAgeInUse( age ) )
        {
            throw new AgeInUseException();
        }
        ageDao.removeAge( age );
    }

    public void removeAge( Long ageId )
        throws AgeInUseException
    {
        if ( ageDao.isAgeInUse( ageId ) )
        {
            throw new AgeInUseException();
        }
        ageDao.removeAge( ageId );

    }

    /**
     * move the age up. switch the orderNumbers of the age and the
     * previousAge
     */
    public void moveAgeOrderUp( Age age, ServiceExchangeContext context )
        throws JJWManagerException
    {

        try
        {
            Age previousAge = ageDao.getPreviousAge( age.getId() );
            Age originalAge = getAge( age.getId() );

            int previousAgeOrder = previousAge.getOrderNumber();
            int originalAgeOrder = originalAge.getOrderNumber();

            previousAge.setOrderNumber( originalAgeOrder );
            originalAge.setOrderNumber( previousAgeOrder );

            previousAge.setModificationId( context.getUserId() );
            originalAge.setModificationId( context.getUserId() );

            previousAge.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
            originalAge.setModificationDate( new Timestamp( System.currentTimeMillis() ) );

        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }

    }
}
