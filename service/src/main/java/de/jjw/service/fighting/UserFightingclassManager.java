/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : UserFightingclassManager.java
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

package de.jjw.service.fighting;

import java.util.Locale;
import java.util.Map;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.fighting.UserFightingclass;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.util.JJWTwoLongDTO;

public interface UserFightingclassManager
{

    public Map<JJWTwoLongDTO, UserFightingclass> getAssignMap( Locale locale )
        throws JJWManagerException;

    public void saveUserFightingclass( UserFightingclass uf )
        throws JJWManagerException, OptimisticLockingException;

    public void toggleAccess( Long fightingclassId, Long userId, ServiceExchangeContext serviceExchangeContext )
        throws JJWManagerException;
    
    public void moveUp( Long fightingclassId, Long userId, ServiceExchangeContext serviceExchangeContext )
                    throws JJWManagerException;
    
    public void moveDown( Long fightingclassId, Long userId, ServiceExchangeContext serviceExchangeContext )
                    throws JJWManagerException;

    public boolean isAccessForTatamiUser( long userId, long fightingclassId )
        throws JJWManagerException;

    void resetOrderNumbers( Long userId )
        throws JJWManagerException;

    // public void toggleDeleteStop (Fightingclass fightingclass,ServiceExchangeContext ctx)throws JJWManagerException,
    // OptimisticLockingException;
    //
    // public void toggleComplete (Fightingclass fightingclass,ServiceExchangeContext ctx)throws JJWManagerException,
    // OptimisticLockingException;
    //    
}
