/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : UserDuoclassDao.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:46
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

package de.jjw.dao.duo;

import java.util.Map;

import de.jjw.dao.Dao;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.duo.UserDuoclass;
import de.jjw.util.JJWTwoLongDTO;

public interface UserDuoclassDao
    extends Dao
{

    public Map<JJWTwoLongDTO, UserDuoclass> getAssignMap()
        throws JJWDataLayerException;

    public void saveUserDuoclass( UserDuoclass uf )
        throws JJWDataLayerException;

    public void toggleAccess( UserDuoclass uf )
        throws JJWDataLayerException;

    /**
     * @param userId
     * @param duoclassId
     * @return
     * @throws JJWDataLayerException
     */
    boolean isAccessForTatamiUser( long userId, long duoclassId )
        throws JJWDataLayerException;
    public void resetOrderNumbers(  Long userId )
                    throws JJWDataLayerException;
    
    public void moveUp(  Long duoclassId, Long userId )
                    throws JJWDataLayerException;
    
    public void moveDown( Long duoclassId, Long userId )
                    throws JJWDataLayerException;

}
