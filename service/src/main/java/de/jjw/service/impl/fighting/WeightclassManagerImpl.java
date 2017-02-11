/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : WeightclassManagerImpl.java
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

package de.jjw.service.impl.fighting;

import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

import de.jjw.dao.IDatabaseTableConstants;
import de.jjw.dao.fighting.WeightclassDao;
import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.admin.Age;
import de.jjw.model.fighting.Fightingclass;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.exception.WeightclassChildParentDeleteException;
import de.jjw.service.exception.WeightclassInUseException;
import de.jjw.service.fighting.WeightclassManager;
import de.jjw.service.impl.BaseManager;
import de.jjw.service.mapper.fighting.WeightclassMapper;
import de.jjw.service.modelWeb.FightingclassWeb;
import de.jjw.util.TypeUtil;

public class WeightclassManagerImpl
    extends BaseManager
    implements WeightclassManager, IDatabaseTableConstants
{

    private WeightclassDao weightclassDao;

    public void setWeightclassDao( WeightclassDao weightclassDao )
    {
        this.weightclassDao = weightclassDao;
    }

    public de.jjw.service.modelWeb.FightingclassWeb getWeightclass( Long weightclassId )
    {
        return WeightclassMapper.mapWeightclassFromDB( weightclassDao.getWeightclass( weightclassId ) );
    }

    public List<FightingclassWeb> getAllWeightclasses( Locale locale )
    {
        return WeightclassMapper.mapWeightclassListFromDB( weightclassDao.getAllWeightclasses(), locale );
    }

    public List<Fightingclass> getWeightclassByAgeNameShort( String ageNameShort )
    {
        // TODO Auto-generated method stub
        return null;
    }

    public List<FightingclassWeb> getWeightclassByAge( Age age, Locale locale )
    {
        return WeightclassMapper.mapWeightclassListFromDB( weightclassDao.getWeightclassByAge( age ), locale );
    }

    public void saveWeightclass( Fightingclass fightingclass )
        throws OptimisticLockingException
    {
        if ( null == fightingclass.getId() || fightingclass.getId().longValue() == TypeUtil.LONG_DEFAULT )
        {
            fightingclass.setId( null );
            weightclassDao.saveWeightclass( fightingclass );
        }
        else
        {
            fightingclass.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
            optimisticDao.testLocking( TABLE_WEIGHTCLASS, fightingclass.getId(), fightingclass.getModificationDate() );
            WeightclassMapper.mapWeightclassToDB( fightingclass, weightclassDao.getWeightclass( fightingclass.getId() ) );
        }
    }

    public void setFightingclassAsPrinted( Fightingclass fightingclass, ServiceExchangeContext ctx )
        throws JJWManagerException
    {
        try
        {
            Fightingclass fc = weightclassDao.getWeightclass( fightingclass.getId() );
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
        throws JJWManagerException, WeightclassInUseException, WeightclassChildParentDeleteException
    {
        try
        {
            if ( weightclassDao.isWeightclassInUse( weightclassId ) )
            {
                throw new WeightclassInUseException();
            }

            if ( weightclassDao.isWeightclassChildOrParent( weightclassId ) )
            {
                throw new WeightclassChildParentDeleteException();
            }
            weightclassDao.removeWeightclass( weightclassId );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException();
        }
    }

    public void removeWeightclass( Fightingclass fightingclass )
        throws JJWManagerException, WeightclassInUseException, WeightclassChildParentDeleteException
    {
        try
        {
            if ( weightclassDao.isWeightclassInUse( fightingclass ) )
            {
                throw new WeightclassInUseException();
            }
            if ( weightclassDao.isWeightclassChildOrParent( fightingclass.getId() ) )
            {
                throw new WeightclassChildParentDeleteException();
            }
            weightclassDao.removeWeightclass( fightingclass );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException();
        }
    }

}
