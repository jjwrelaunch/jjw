/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : IPLogManagerImpl.java
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

import de.jjw.dao.IDatabaseTableConstants;
import de.jjw.dao.admin.IPLogDao;
import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.admin.IPLog;
import de.jjw.service.admin.IPLogManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.impl.BaseManager;

public class IPLogManagerImpl
    extends BaseManager
    implements IPLogManager, IDatabaseTableConstants
{

    private IPLogDao ipLogDao;





    public void saveIPLog( IPLog ipLog )
        throws OptimisticLockingException, JJWManagerException
    {

        ipLog.setId( null );
        ipLog.setCreateDate( new Timestamp( System.currentTimeMillis() ) );

        try
        {
            ipLogDao.saveIPLog( ipLog );
        }
        catch ( JJWDataLayerException e )
        {
            log.error( "can not log ip" );
        }

    }


    /**
     * @param ipLogDao the ipLogDao to set
     */
    public void setIpLogDao( IPLogDao ipLogDao )
    {
        this.ipLogDao = ipLogDao;
    }

}
