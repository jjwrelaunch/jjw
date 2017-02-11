/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : INewaWeightclassDatabaseConstants.java
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

package de.jjw.dao.hibernate.newa;

import de.jjw.dao.hibernate.IBaseDatabaseConstants;

public interface INewaWeightclassDatabaseConstants
    extends IBaseDatabaseConstants
{

    String NEWACLASS = "newaclass";

    String SQL_TABLE_NEWACLASS = "newaclass";
    String TABLE_NEWACLASS = "Newaclass";
    String NEWACLASS_ID = "newaclassId";
    String AGE = "age";
    String SEX = "sex";

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
