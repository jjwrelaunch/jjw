/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : IUserFightingclassDatabaseConstants.java
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

package de.jjw.dao.hibernate.fighting;

import de.jjw.dao.hibernate.IBaseDatabaseConstants;

public interface IUserFightingclassDatabaseConstants
    extends IBaseDatabaseConstants
{

    String TABLE_USERFIGHTINGCLASS = "UserFightingclass";
    String SQL_TABLE_USERFIGHTINGCLASS = "user_weightclass";
    String USER = "user";
    String FIGHTINGCLASS = "fightingclass";
    String USER_ID = "userId";
    String FIGHTINGCLASS_ID = "fightingclassId";
    String SQL_USER_ID = "user_id";
    String SQL_FIGHTINGCLASS_ID = "weightclass_id";
    String SQL_ORDER_NUMBER ="orderNumber";

}
