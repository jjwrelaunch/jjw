/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoclassManager.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:46
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

package de.jjw.service.duo;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.admin.Age;
import de.jjw.model.duo.DuoCertification;
import de.jjw.model.duo.DuoDoublePoolClass;
import de.jjw.model.duo.DuoKoClass;
import de.jjw.model.duo.DuoSimplePoolClass;
import de.jjw.model.duo.Duoclass;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.exception.DuoclassChildParentDeleteException;
import de.jjw.service.exception.DuoclassCreateException;
import de.jjw.service.exception.DuoclassInUseException;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.modelWeb.DuoclassWeb;
import de.jjw.util.dto.SortDTO;


public interface DuoclassManager
{

    public List<DuoclassWeb> getAllDuoclasses( Locale locale )
        throws JJWManagerException;

    public DuoclassWeb getDuoclass( long duoclassId )
        throws JJWManagerException;

    public List<DuoclassWeb> getDuoclassByAge( Age age, Locale locale );

    public void saveDuoclass( Duoclass duoclass )
        throws OptimisticLockingException, JJWManagerException;

    public void removeDuoclass( Duoclass duoclass )
        throws JJWManagerException, DuoclassInUseException, DuoclassChildParentDeleteException;

    public DuoSimplePoolClass getDuoclassSimplePool( Long duoclassId )
        throws JJWManagerException;

    public DuoDoublePoolClass getDuoclassDoublePool( Long duoclassId )
        throws JJWManagerException;

    public DuoKoClass getDuoclassKo( Long duoclassId )
        throws JJWManagerException;

    public int getFightsystemOfDuoclass( long duoclassId )
        throws JJWManagerException;

    public void createDuoclasses( ServiceExchangeContext ctx )
        throws DuoclassCreateException;

    public DuoCertification getDuoCertification( Long duoclassId )
        throws JJWManagerException;

    public void toggleDeleteStop( Duoclass duoclass, ServiceExchangeContext ctx )
        throws JJWManagerException, OptimisticLockingException;

    public void toggleComplete( Duoclass duoclass, ServiceExchangeContext ctx )
        throws JJWManagerException, OptimisticLockingException;

    public List<DuoclassWeb> getInUseDuoclasses( Map<Integer, SortDTO> sortParameter, Locale locale )
        throws JJWManagerException;

    public Map<Integer, Duoclass> getAllDuoclasses( boolean onlyCompleted )
        throws JJWManagerException;

    public void changeDuoTeamsInClass( int duoTeam1, int duoTeam2, long duoclassId )
        throws JJWManagerException;

    public int getNumberOfDuoTeamInDuoclass( long duoclassId )
        throws JJWManagerException;

    public void setDuoclassAsPrinted( Duoclass duoclass, ServiceExchangeContext ctx )
        throws JJWManagerException;

    public List<DuoclassWeb> getInUseDuoclassesForTatami( Map<Integer, SortDTO> sortParameter, Locale locale,
                                                          ServiceExchangeContext ctx )
        throws JJWManagerException;

    public List<DuoclassWeb> getChildDuoclasses( Long duoclassId, Locale locale )
        throws JJWManagerException;

    public List<DuoclassWeb> getCombinableDuoclasses( Long duoclassId, Locale locale )
        throws JJWManagerException;

    public void addDuoclassToClass( long parentClass, long childClass )
        throws JJWManagerException;

    public void removeDuoclassFromParent( long parentClass, long childClass )
        throws JJWManagerException;

    public void startClass( Duoclass duoclass, ServiceExchangeContext createServiceExchangeContext )
        throws JJWManagerException, OptimisticLockingException;
}
