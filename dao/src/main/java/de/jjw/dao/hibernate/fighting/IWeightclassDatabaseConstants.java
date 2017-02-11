/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : IWeightclassDatabaseConstants.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:45
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

public interface IWeightclassDatabaseConstants
    extends IBaseDatabaseConstants
{

    String TABLE_WEIGHTCLASS = "Fightingclass";
    String SQL_TABLE_WEIGHTCLASS = "weightclass";
    String TABLE_FIGHTINGCLASS = "Fightingclass";
    String AGE = "age";
    String SEX = "sex";
    String WEIGHTCLASS = "weightclass";
    String START_WEIGHT = "startWeight";
    String WEIGHT_LIMIT = "weightLimit";
    String DELETE_STOP = "deleteStop";
    String COMPLETE = "complete";
    String CERTIFICATION_PRINT = "certificationPrint";
    String PRINT = "print";
    String FINISH = "finish";
    String START_TIME = "startTime";
    String END_TIME = "endTime";
    String OFFICIALS_LOG = "officialsLog";
    String INUSE = "inuse";
    String PARENT = "parent";

}
