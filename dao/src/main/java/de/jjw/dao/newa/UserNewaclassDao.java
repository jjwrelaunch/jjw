/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : UserFightingclassDao.java
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

package de.jjw.dao.newa;

import java.util.Map;

import de.jjw.dao.Dao;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.newa.UserNewaclass;
import de.jjw.util.JJWTwoLongDTO;

public interface UserNewaclassDao
    extends Dao
{

    public Map<JJWTwoLongDTO, UserNewaclass> getAssignMap()
        throws JJWDataLayerException;

    public void saveUserNewaclass( UserNewaclass uf )
        throws JJWDataLayerException;

    public void toggleAccess( UserNewaclass uf )
        throws JJWDataLayerException;

    public boolean isAccessForTatamiUser( long userId, long fightingclassId )
        throws JJWDataLayerException;
    
    public void resetOrderNumbers(  Long userId )
                    throws JJWDataLayerException;
    
    public void moveUp(  Long newaclassId, Long userId )
                    throws JJWDataLayerException;
    
    public void moveDown( Long newaclassId, Long userId )
                    throws JJWDataLayerException;
}
