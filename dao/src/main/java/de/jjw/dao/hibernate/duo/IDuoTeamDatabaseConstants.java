/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : IDuoTeamDatabaseConstants.java
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

package de.jjw.dao.hibernate.duo;

import de.jjw.dao.hibernate.IBaseDatabaseConstants;

public interface IDuoTeamDatabaseConstants
    extends IBaseDatabaseConstants
{
    String TABLE_DUOTEAM = "DuoTeam";
    String SQL_TABLE_DUOTEAM = "duoteam";
    String NAME = "name";
    String FIRSTNAME = "firstname";
    String NAME_2 = "name2";
    String FIRSTNAME_2 = "firstname2";
    String SEX = "sex";
    String TEAM = "team";
    String AGE = "age";
    String REGION = "region";
    String COUNTRY = "country";
    String PLACE = "place";
    String READY = "ready";

    String DUOCLASS = "duoclass";
    String DELETE_FLAG = "delete_flag";

    String DUOCLASS_ID = "duoclassId";

    String ORIGINAL_DUOCLASS = "originalDuoclassId";
    String MARK_FOR_EXPORT = "markForExport";
}
