/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : AgeManager.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:41
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

package de.jjw.service.admin;

import java.util.List;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.admin.Age;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.exception.AgeInUseException;
import de.jjw.service.exception.JJWManagerException;

public interface AgeManager
{

    public Age getAge( Long ageId );

    public List<Age> getAllAges()
        throws JJWManagerException;

    public void saveAge( Age age )
        throws OptimisticLockingException, JJWManagerException;

    public void removeAge( Age age )
        throws AgeInUseException;

    public void removeAge( Long ageId )
        throws AgeInUseException;

    public void moveAgeOrderUp( Age ageId, ServiceExchangeContext context )
        throws JJWManagerException;
}
