/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : IUserDuoclassDatabaseConstants.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:42
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

package de.jjw.dao.hibernate.duo;

import de.jjw.dao.hibernate.IBaseDatabaseConstants;

public interface IUserDuoclassDatabaseConstants
    extends IBaseDatabaseConstants
{

    String TABLE_USERDUOGCLASS = "UserDuoclass";
    String SQL_TABLE_USERDUOCLASS = "user_duoclass";
    String USER = "user";
    String DUOCLASS = "duoclass";
    String USER_ID = "userId";
    String DUOCLASS_ID = "duoclassId";
    String SQL_USER_ID = "user_id";
    String SQL_DUOCLASS_ID = "duoclass_id";
    String SQL_ORDER_NUMBER ="orderNumber";

}
