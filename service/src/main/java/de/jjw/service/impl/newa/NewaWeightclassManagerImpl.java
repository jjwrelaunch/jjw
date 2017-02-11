/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaWeightclassManagerImpl.java
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

package de.jjw.service.impl.newa;

import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

import de.jjw.dao.IDatabaseTableConstants;
import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.dao.newa.NewaWeightclassDao;
import de.jjw.model.admin.Age;
import de.jjw.model.newa.Newaclass;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.exception.NewaWeightclassChildParentDeleteException;
import de.jjw.service.exception.NewaWeightclassInUseException;
import de.jjw.service.impl.BaseManager;
import de.jjw.service.mapper.newa.NewaWeightclassMapper;
import de.jjw.service.modelWeb.NewaclassWeb;
import de.jjw.service.newa.NewaWeightclassManager;
import de.jjw.util.TypeUtil;

public class NewaWeightclassManagerImpl
    extends BaseManager
    implements NewaWeightclassManager, IDatabaseTableConstants
{

    private NewaWeightclassDao newaWeightclassDao;

    public void setNewaWeightclassDao( NewaWeightclassDao weightclassDao )
    {
        this.newaWeightclassDao = weightclassDao;
    }

    public de.jjw.service.modelWeb.NewaclassWeb getWeightclass( Long weightclassId )
    {
        return NewaWeightclassMapper.mapWeightclassFromDB( newaWeightclassDao.getWeightclass( weightclassId ) );
    }

    public List<NewaclassWeb> getAllWeightclasses( Locale locale )
    {
        return NewaWeightclassMapper.mapWeightclassListFromDB( newaWeightclassDao.getAllWeightclasses(), locale );
    }

    public List<Newaclass> getWeightclassByAgeNameShort( String ageNameShort )
    {
        // TODO Auto-generated method stub
        return null;
    }

    public List<NewaclassWeb> getWeightclassByAge( Age age, Locale locale )
    {
        return NewaWeightclassMapper.mapWeightclassListFromDB( newaWeightclassDao.getWeightclassByAge( age ), locale );
    }

    public void saveWeightclass( Newaclass fightingclass )
        throws OptimisticLockingException
    {
        if ( null == fightingclass.getId() || fightingclass.getId().longValue() == TypeUtil.LONG_DEFAULT )
        {
            fightingclass.setId( null );
            newaWeightclassDao.saveWeightclass( fightingclass );
        }
        else
        {
            fightingclass.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
            optimisticDao.testLocking( TABLE_NEWACLASS, fightingclass.getId(), fightingclass.getModificationDate() );
            NewaWeightclassMapper.mapWeightclassToDB( fightingclass,
                                                      newaWeightclassDao.getWeightclass( fightingclass.getId() ) );
        }
    }

    public void setFightingclassAsPrinted( Newaclass fightingclass, ServiceExchangeContext ctx )
        throws JJWManagerException
    {
        try
        {
            Newaclass fc = newaWeightclassDao.getWeightclass( fightingclass.getId() );
            fc.setCertificationPrint( true );
            fc.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
            fc.setModificationId( ctx.getUserId() );
        }
        catch ( Exception e )
        {
            throw new JJWManagerException();
        }
    }

    public void removeWeightclass( Long weightclassId )
        throws JJWManagerException, NewaWeightclassInUseException, NewaWeightclassChildParentDeleteException
    {
        try
        {
            if ( newaWeightclassDao.isWeightclassInUse( weightclassId ) )
            {
                throw new NewaWeightclassInUseException();
            }

            if ( newaWeightclassDao.isWeightclassChildOrParent( weightclassId ) )
            {
                throw new NewaWeightclassChildParentDeleteException();
            }
            newaWeightclassDao.removeWeightclass( weightclassId );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException();
        }
    }

    public void removeWeightclass( Newaclass fightingclass )
        throws JJWManagerException, NewaWeightclassInUseException, NewaWeightclassChildParentDeleteException
    {
        try
        {
            if ( newaWeightclassDao.isWeightclassInUse( fightingclass ) )
            {
                throw new NewaWeightclassInUseException();
            }
            if ( newaWeightclassDao.isWeightclassChildOrParent( fightingclass.getId() ) )
            {
                throw new NewaWeightclassChildParentDeleteException();
            }
            newaWeightclassDao.removeWeightclass( fightingclass );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException();
        }
    }

}
