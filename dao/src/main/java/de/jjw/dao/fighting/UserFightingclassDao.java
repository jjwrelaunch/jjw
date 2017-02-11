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

package de.jjw.dao.fighting;

import java.util.List;
import java.util.Map;

import de.jjw.dao.Dao;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.admin.UserToClass;
import de.jjw.model.duo.Duoclass;
import de.jjw.model.fighting.Fightingclass;
import de.jjw.model.fighting.UserFightingclass;
import de.jjw.model.newa.Newaclass;
import de.jjw.util.JJWTwoLongDTO;

public interface UserFightingclassDao
    extends Dao
{

    public Map<JJWTwoLongDTO, UserFightingclass> getAssignMap()
        throws JJWDataLayerException;

    public void saveUserFightingclass( UserFightingclass uf )
        throws JJWDataLayerException;

    public void toggleAccess( UserFightingclass uf )
        throws JJWDataLayerException;
    public void resetOrderNumbers(  Long userId )
                    throws JJWDataLayerException;
    
    public void moveUp(  Long fightingclassId, Long userId )
                    throws JJWDataLayerException;
    
    public void moveDown( Long fightingclassId, Long userId )
                    throws JJWDataLayerException;

    public boolean isAccessForTatamiUser( long userId, long fightingclassId )
        throws JJWDataLayerException;

    public List<Fightingclass> getCurrentAndPlanedFightingClassesForUser( Long userId )
        throws JJWDataLayerException;
    public List<Duoclass> getCurrentAndPlanedDuoClassesForUser( Long userId )
                    throws JJWDataLayerException;
    public List<Newaclass> getCurrentAndPlanedNewaClassesForUser( Long userId )
                    throws JJWDataLayerException;
    
    public Map<String, UserToClass>getallUsersToClasses() throws JJWDataLayerException;
}
