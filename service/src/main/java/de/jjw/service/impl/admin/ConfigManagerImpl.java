/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : ConfigManagerImpl.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:43
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

import de.jjw.dao.IDatabaseTableConstants;
import de.jjw.dao.admin.ConfigDao;
import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.admin.ConfigJJW;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.admin.ConfigManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.impl.BaseManager;
import de.jjw.service.mapper.admin.ConfigMapper;

public class ConfigManagerImpl
    extends BaseManager
    implements IDatabaseTableConstants, ConfigManager
{

    private ConfigDao configDao;

    public ConfigJJW getConfig()
        throws JJWManagerException
    {
        try
        {
            return ConfigMapper.mapConfigFromDB( configDao.getConfig() );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public void saveConfig( ServiceExchangeContext ctx, ConfigJJW config )
        throws JJWManagerException, OptimisticLockingException
    {
        try
        {
            config.setModificationId( ctx.getUserId() );
            config.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
            optimisticDao.testLocking( TABLE_CONFIG, config.getId(), config.getModificationDate() );
            ConfigMapper.mapConfigToDB( config, configDao.getConfig() );
        }
        catch ( OptimisticLockingException e )
        {
            throw new OptimisticLockingException( e.getMessage() );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }


    }

    public ConfigDao getConfigDao()
    {
        return configDao;
    }

    public void setConfigDao( ConfigDao configDao )
    {
        this.configDao = configDao;
    }


}
