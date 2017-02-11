/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaFighterManager.java
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

package de.jjw.service.newa;

import java.util.List;
import java.util.Locale;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.fighting.Fighter;
import de.jjw.model.newa.NewaFighter;
import de.jjw.service.ServiceExchangeContext;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.exception.JJWPoolBlockedException;
import de.jjw.service.exception.NewaFighterIllegalReadyException;
import de.jjw.service.exception.NewaFighterInUseException;
import de.jjw.service.modelWeb.FighterWeb;
import de.jjw.service.modelWeb.NewaFighterWeb;

public interface NewaFighterManager
{

    public NewaFighter getFighter( Long fighterId );

    public List<NewaFighterWeb> getAllNewaFighters( Locale local )
        throws JJWManagerException;

    public List<NewaFighterWeb> getAllFightersInOrder( int order, boolean isAscending, Locale local )
        throws JJWManagerException;

    public List<NewaFighterWeb> getAllNotRegistratedFighters( Locale local );

    public List<NewaFighterWeb> getAllRegistratedFighters( Locale local )
        throws JJWManagerException;

    public List<NewaFighterWeb> getFighterByAge( long ageId, Locale local, boolean notReg )
        throws JJWManagerException;

    public List<NewaFighterWeb> getFighterByRegion( long regionId, Locale local, boolean notReg )
        throws JJWManagerException;

    public List<NewaFighterWeb> getFighterByCountry( long countryId, Locale local, boolean notReg )
        throws JJWManagerException;

    public List<NewaFighterWeb> getFighterByTeam( long teamId, Locale local, boolean notReg )
        throws JJWManagerException;

    public List<NewaFighterWeb> getFighterByNewaclass( long newaclassId, Locale local, boolean notReg )
        throws JJWManagerException;

    public void saveFighterList( List<NewaFighterWeb> newaFighterList )
        throws OptimisticLockingException, JJWManagerException, JJWPoolBlockedException,
        NewaFighterIllegalReadyException;

    public void saveFighter( NewaFighter fighter )
        throws OptimisticLockingException, JJWManagerException, JJWPoolBlockedException,
        NewaFighterIllegalReadyException;

    public void removeFighter( Long fighterId )
        throws JJWManagerException, NewaFighterInUseException;

    public void registerFast( long fighterId, ServiceExchangeContext ctx )
        throws JJWManagerException, JJWPoolBlockedException;

    public void removeFighter( NewaFighter fighter )
        throws JJWManagerException, NewaFighterInUseException;

    /*
    * test  Name, firstname and Team of the given fighter
    *
    */
    public boolean existFighter( NewaFighter fighter );

    /*
    * test Name and firstname of the given fighter
    */
    public List<NewaFighterWeb> getPossibleDublicateFighters( NewaFighter fighter, Locale local );

    public List<NewaFighterWeb> getFighterResultList( Locale local )
        throws JJWManagerException;

    public void toggleMarkForExport( NewaFighter fighter, ServiceExchangeContext ctx )
        throws JJWManagerException, OptimisticLockingException;

    public List<NewaFighterWeb> getFightersForJSONExport( Locale local )
        throws JJWManagerException;
}
