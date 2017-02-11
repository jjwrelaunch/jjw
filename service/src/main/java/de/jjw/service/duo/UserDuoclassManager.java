/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : UserDuoclassManager.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:49
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

package de.jjw.service.duo;

import java.util.Locale;
import java.util.Map;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.duo.UserDuoclass;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.util.JJWTwoLongDTO;

public interface UserDuoclassManager
{

    public Map<JJWTwoLongDTO, UserDuoclass> getAssignMap( Locale locale )
        throws JJWManagerException;

    public void saveUserDuoclass( UserDuoclass uf )
        throws JJWManagerException, OptimisticLockingException;

    public void toggleAccess( Long duoclassId, Long userId, ServiceExchangeContext serviceExchangeContext )
        throws JJWManagerException;

    public void moveUp( Long duoclassId, Long userId, ServiceExchangeContext serviceExchangeContext )
                    throws JJWManagerException;
    
    public void moveDown( Long duoclassId, Long userId, ServiceExchangeContext serviceExchangeContext )
                    throws JJWManagerException;
    
    /**
     * @param userId
     * @param duoclassId
     * @return
     * @throws JJWManagerException
     */
    boolean isAccessForTatamiUser( long userId, long duoclassId )
        throws JJWManagerException;

    void resetOrderNumbers( Long userId )
        throws JJWManagerException;

}
