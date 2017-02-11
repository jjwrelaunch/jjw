/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoTeamManager.java
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

package de.jjw.service.duo;

import java.util.List;
import java.util.Locale;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.duo.DuoTeam;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.exception.DuoTeamIllegalReadyException;
import de.jjw.service.exception.DuoTeamInUseException;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.exception.JJWPoolBlockedException;
import de.jjw.service.modelWeb.DuoTeamWeb;

public interface DuoTeamManager
{

    public List<DuoTeamWeb> getAllDuoTeams( Locale local )
        throws JJWManagerException;

    public List<DuoTeamWeb> getAllDuoTeamsInOrder( int order, boolean isAscending, Locale local )
        throws JJWManagerException;

    public void saveDuoTeamList( List<DuoTeamWeb> duoTeamList )
        throws OptimisticLockingException, JJWManagerException, JJWPoolBlockedException, DuoTeamIllegalReadyException;

    public void saveDuoTeam( DuoTeam duoTeam )
        throws OptimisticLockingException, JJWManagerException, JJWPoolBlockedException, DuoTeamIllegalReadyException;

    public List<DuoTeamWeb> getDuoTeamResultList( Locale local )
        throws JJWManagerException;

    public boolean existDuoTeam( DuoTeam duoTeam )
        throws JJWManagerException;

    public List<DuoTeamWeb> getNotRegistratedDuoTeams( Locale local )
        throws JJWManagerException;

    public List<DuoTeamWeb> getAllRegistratedDuoTeams( Locale local )
        throws JJWManagerException;

    public List<DuoTeamWeb> getDuoTeamByAge( long ageId, Locale local, boolean notReg )
        throws JJWManagerException;

    public List<DuoTeamWeb> getDuoTeamByRegion( long regionId, Locale local, boolean notReg )
        throws JJWManagerException;

    public List<DuoTeamWeb> getDuoTeamByCountry( long countryId, Locale local, boolean notReg )
        throws JJWManagerException;

    public List<DuoTeamWeb> getDuoTeamByTeam( long teamId, Locale local, boolean notReg )
        throws JJWManagerException;

    public List<DuoTeamWeb> getDuoTeamByDuoclass( long fightingclassId, Locale local, boolean notReg )
        throws JJWManagerException;

    public void registerFast( Long duoTeamId, ServiceExchangeContext serviceExchangeContext )
        throws JJWManagerException, JJWPoolBlockedException;

    void removeDuoTeam( DuoTeam duoTeam )
        throws JJWManagerException, DuoTeamInUseException;

    void removeDuoTeam( Long teamId )
        throws JJWManagerException, DuoTeamInUseException;
    
    public void toggleMarkForExport(  DuoTeam duoTeam , ServiceExchangeContext ctx )
                    throws JJWManagerException,OptimisticLockingException;

    public List<DuoTeamWeb> getDuosForJSONExport( Locale locale )
                    throws JJWManagerException;

}
