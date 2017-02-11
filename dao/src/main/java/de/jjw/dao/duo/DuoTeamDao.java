/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoTeamDao.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:44
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

package de.jjw.dao.duo;

import java.util.List;

import de.jjw.dao.Dao;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.duo.DuoTeam;
import de.jjw.model.duo.Duoclass;

public interface DuoTeamDao
    extends Dao
{

    public DuoTeam getDuoTeam( Long duoTeamId )
        throws JJWDataLayerException;

    public List<DuoTeam> getAllDuoTeams()
        throws JJWDataLayerException;

    public List<DuoTeam> getAllDuoTeamsInOrder( int order, boolean isAscending )
        throws JJWDataLayerException;

    public List<DuoTeam> getDuoTeamsByDuoclassForDraw( Duoclass duoclass )
        throws JJWDataLayerException;

    public void saveDuoTeam( DuoTeam duoTeam )
        throws JJWDataLayerException;

    public List<DuoTeam> getNotRegistratedDuoTeams()
        throws JJWDataLayerException;

    public List<DuoTeam> getRegistratedDuoTeams()
        throws JJWDataLayerException;

    public boolean existDuoTeam( DuoTeam duoTeam )
        throws JJWDataLayerException;

    public List<Duoclass> getUsedDuoclasses()
        throws JJWDataLayerException;

    public List<DuoTeam> getDuoTeamResultList()
        throws JJWDataLayerException;

    public List<DuoTeam> getDuoTeamByAge( long ageId, boolean notReg )
        throws JJWDataLayerException;

    public List<DuoTeam> getDuoTeamByRegion( long regionId, boolean notReg )
        throws JJWDataLayerException;

    public List<DuoTeam> getDuoTeamByCountry( long countryId, boolean notReg )
        throws JJWDataLayerException;

    public List<DuoTeam> getDuoTeamByTeam( long teamId, boolean notReg )
        throws JJWDataLayerException;

    public List<DuoTeam> getDuoTeamByDuoclass( long duoclassId, boolean notReg )
        throws JJWDataLayerException;

    boolean isDuoTeamInUse( Long teamId )
        throws JJWDataLayerException;

    void removeDuoTeam( DuoTeam duoteam )
        throws JJWDataLayerException;

    void removeDuoTeam( Long teamId )
        throws JJWDataLayerException;

    public List<DuoTeam> getDuosForJSONExport()
                    throws JJWDataLayerException;
}
