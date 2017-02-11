/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FighterManager.java
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

package de.jjw.service.fighting;

import java.util.List;
import java.util.Locale;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.fighting.Fighter;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.exception.FighterIllegalReadyException;
import de.jjw.service.exception.FighterInUseException;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.exception.JJWPoolBlockedException;
import de.jjw.service.modelWeb.FighterWeb;


public interface FighterManager
{

    public Fighter getFighter( Long fighterId );

    public List<FighterWeb> getAllFighters( Locale local )
        throws JJWManagerException;

    public List<FighterWeb> getAllFightersInOrder( int order, boolean isAscending, Locale local )
        throws JJWManagerException;

    public List<FighterWeb> getAllNotRegistratedFighters( Locale local );

    public List<FighterWeb> getAllRegistratedFighters( Locale local )
        throws JJWManagerException;

    public List<FighterWeb> getFighterByAge( long ageId, Locale local, boolean notReg )
        throws JJWManagerException;

    public List<FighterWeb> getFighterByRegion( long regionId, Locale local, boolean notReg )
        throws JJWManagerException;

    public List<FighterWeb> getFighterByCountry( long countryId, Locale local, boolean notReg )
        throws JJWManagerException;

    public List<FighterWeb> getFighterByTeam( long teamId, Locale local, boolean notReg )
        throws JJWManagerException;

    public List<FighterWeb> getFighterByFightingclass( long fightingclassId, Locale local, boolean notReg )
        throws JJWManagerException;

    public void saveFighterList( List<FighterWeb> fighterList )
        throws OptimisticLockingException, JJWManagerException, JJWPoolBlockedException, FighterIllegalReadyException;

    public void saveFighter( Fighter fighter )
        throws OptimisticLockingException, JJWManagerException, JJWPoolBlockedException, FighterIllegalReadyException;

    public void removeFighter( Long fighterId )
        throws JJWManagerException, FighterInUseException;

    public void registerFast( long fighterId, ServiceExchangeContext ctx )
        throws JJWManagerException, JJWPoolBlockedException;

    public void removeFighter( Fighter fighter )
        throws JJWManagerException, FighterInUseException;

    /*
    * test  Name, firstname and Team of the given fighter
    *
    */
    public boolean existFighter( Fighter fighter );

    /*
    * test Name and firstname of the given fighter
    */
    public List<FighterWeb> getPossibleDublicateFighters( Fighter fighter, Locale local );

    public List<FighterWeb> getFighterResultList( Locale local )
        throws JJWManagerException;

    public void toggleMarkForExport( Fighter fighter, ServiceExchangeContext ctx )
        throws JJWManagerException,OptimisticLockingException;
    
    public List<FighterWeb> getFightersForJSONExport( Locale local )
                    throws JJWManagerException;
}
