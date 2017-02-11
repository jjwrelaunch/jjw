/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaWeightclassManager.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:49
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

package de.jjw.service.newa;

import java.util.List;
import java.util.Locale;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.admin.Age;
import de.jjw.model.newa.Newaclass;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.exception.NewaWeightclassChildParentDeleteException;
import de.jjw.service.exception.NewaWeightclassInUseException;
import de.jjw.service.modelWeb.NewaclassWeb;

public interface NewaWeightclassManager
{

    public NewaclassWeb getWeightclass( Long weightclassId );

    public List<NewaclassWeb> getAllWeightclasses( Locale locale );

    public List<Newaclass> getWeightclassByAgeNameShort( String ageNameShort );

    public List<NewaclassWeb> getWeightclassByAge( Age age, Locale locale );

    public void saveWeightclass( Newaclass Newaclass )
        throws OptimisticLockingException;

    public void setFightingclassAsPrinted( Newaclass newaclass, ServiceExchangeContext ctx )
        throws JJWManagerException;

    public void removeWeightclass( Long weightclassId )
        throws JJWManagerException, NewaWeightclassInUseException, NewaWeightclassChildParentDeleteException;

    public void removeWeightclass( Newaclass newaclass )
        throws JJWManagerException, NewaWeightclassInUseException, NewaWeightclassChildParentDeleteException;
}
