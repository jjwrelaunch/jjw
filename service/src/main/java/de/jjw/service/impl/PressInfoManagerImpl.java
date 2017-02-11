/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : PressInfoManagerImpl.java
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

package de.jjw.service.impl;

import de.jjw.dao.PressInfoDao;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.PressInfo;
import de.jjw.service.PressInfoManager;
import de.jjw.service.exception.JJWManagerException;

public class PressInfoManagerImpl
    extends BaseManager
    implements PressInfoManager
{

    private PressInfoDao pressInfoDao;

    @Override
    public PressInfo getPressInfo()
        throws JJWManagerException
    {
        try
        {
            return pressInfoDao.getPressInfo();
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public PressInfoDao getPressInfoDao()
    {
        return pressInfoDao;
    }

    public void setPressInfoDao( PressInfoDao pressInfoDao )
    {
        this.pressInfoDao = pressInfoDao;
    }

}
