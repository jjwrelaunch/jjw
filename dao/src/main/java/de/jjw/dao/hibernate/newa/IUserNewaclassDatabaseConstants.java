/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : IUserNewaclassDatabaseConstants.java
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

package de.jjw.dao.hibernate.newa;

import de.jjw.dao.hibernate.IBaseDatabaseConstants;

public interface IUserNewaclassDatabaseConstants
    extends IBaseDatabaseConstants
{

    String TABLE_USERNEWACLASS = "UserNewaclass";

    String SQL_TABLE_USERNEWACLASS = "user_newaclass";
    String USER = "user";

    String NEWACLASS = "newaclass";

    String NEWACLASS_ID = "newaclassId";

    String USER_ID = "userId";

    String SQL_USER_ID = "user_id";

    String SQL_NEWACLASS_ID = "newaclass_id";
    String SQL_ORDER_NUMBER ="orderNumber";

}
