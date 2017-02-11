/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : INewaDoublePoolItemDatabaseConstants.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:50
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

public interface INewaDoublePoolItemDatabaseConstants
{

    String TABLE_NEWA_DOUBLE_POOL = "NewaDoublePoolItem";

    String SQL_TABLE_NEWA_DOUBLE_POOL = "newa_d_poolentries";

    String NEWACLASS_ID = "newaclassId";

    String NEWACLASS = "newaclass";
    String FIGHTER = "fighter";
    String NUMBER_IN_POOL = "numberInPool";
    String FIGHT1 = "fight1";
    String FIGHT2 = "fight2";
    String FIGHT3 = "fight3";
    String FIGHT4 = "fight4";
    String FINAL_FIGHT = "finalFight";
    String POOL_PART = "poolPart";
    String FIGHTER_ID = "fighterId";
}
