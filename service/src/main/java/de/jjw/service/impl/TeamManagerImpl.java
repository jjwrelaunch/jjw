/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : TeamManagerImpl.java
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

package de.jjw.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

import de.jjw.dao.IDatabaseTableConstants;
import de.jjw.dao.TeamDao;
import de.jjw.dao.admin.CountryDao;
import de.jjw.dao.duo.DuoTeamDao;
import de.jjw.dao.fighting.FighterDao;
import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.dao.newa.NewaFighterDao;
import de.jjw.model.Team;
import de.jjw.model.duo.DuoTeam;
import de.jjw.model.fighting.Fighter;
import de.jjw.model.newa.NewaFighter;
import de.jjw.service.TeamManager;
import de.jjw.service.TeamResultDTO;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.exception.TeamInUseException;
import de.jjw.service.mapper.admin.TeamMapper;
import de.jjw.service.mapper.duo.DuoTeamMapper;
import de.jjw.service.mapper.fighting.FighterMapper;
import de.jjw.util.TypeUtil;
import de.jjw.util.dto.TeamParticipantDto;

public class TeamManagerImpl
    extends BaseManager
    implements TeamManager, IDatabaseTableConstants
{

    private TeamDao dao;

    private CountryDao countryDao;

    private FighterDao fighterDao;

    private DuoTeamDao duoTeamDao;

    private NewaFighterDao newaFighterDao;


    public TeamDao getDao()
    {
        return dao;
    }

    public void setDao( TeamDao dao )
    {
        this.dao = dao;
    }


    public CountryDao getCountryDao()
    {
        return countryDao;
    }

    public void setCountryDao( CountryDao countryDao )
    {
        this.countryDao = countryDao;
    }

    public Team getTeam( long id )
    {
        return TeamMapper.mapTeamFromDB( dao.getTeam( id ) );
    }

    public List<Team> getAllTeams()
    {
        return TeamMapper.mapTeamListFromDB( dao.getAllTeams() );
    }

    public List<Team> getTeamsForEvent( long eventId )
    {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Team> getTeamsForEvents( List eventIdList )
    {
        // TODO Auto-generated method stub
        return null;
    }

    public List<Team> getTeamsByTeamName( String teamName )
    {
        return dao.getTeamsByTeamName( teamName );
    }

    public void saveTeam( Team team, int logoStatus )
        throws OptimisticLockingException
    {

        if ( null == team.getId() || TypeUtil.isEmptyOrDefault( team.getId().longValue() ) )
        {
            team.setId( null );
            team.setRegionId( team.getRegion().getId() );
            team.setCountryId( team.getCountry().getId() );
            dao.saveTeam( team );
        }
        else
        {
            team.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
            optimisticDao.testLocking( TABLE_TEAM, team.getId(), team.getModificationDate() );
            Team dbTeam = dao.getTeam(
                team.getId() ); //TeamMapper.mapTeamFromDB( dao.getTeam(team.getId()));  //getTeam(team.getId());
            if ( DELETE_LOGO == logoStatus )
            {
                dbTeam.setLogo( null );
            }

            TeamMapper.mapTeamToDB( team, dbTeam, false );
            // dao.saveTeam(team);

        }

    }

    public List<Team> getTeamsByCountry( long countryId )
    {
        return TeamMapper.mapTeamListFromDB( dao.getTeamsByCountry( countryId ) );
    }

    public List<Team> getTeamsByCountryAndRegion( long countryId, long regionId )
    {

        return TeamMapper.mapTeamListFromDB( dao.getTeamsByCountryAndRegion( countryId, regionId ) );
    }


    public void removeTeam( Team team )
        throws TeamInUseException, JJWManagerException
    {
        try
        {
            if ( dao.isTeamInUse( team ) )
            {
                throw new TeamInUseException();
            }
            dao.removeTeam( team );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public void removeTeam( Long teamId )
        throws TeamInUseException, JJWManagerException
    {
        try
        {
            if ( dao.isTeamInUse( teamId ) )
            {
                throw new TeamInUseException();
            }
            dao.removeTeam( teamId );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }

    }

    public List<Team> getTeamsByRegion( long regionId )
    {
        return TeamMapper.mapTeamListFromDB( dao.getTeamsByRegion( regionId ) );
    }

    public List<TeamParticipantDto> getNumberOfTeamMember()
        throws JJWManagerException
    {
        try
        {
            return dao.getNumberOfTeamMember();
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public TeamResultDTO getTeamResults( long teamId, Locale local )
        throws JJWManagerException
    {
        try
        {
            TeamResultDTO ret = new TeamResultDTO();
            ret.setFighterList( FighterMapper.mapFighterListFromDB( dao.getTeamResultsFighting( teamId ), local ) );
            ret.setDuoteamList( DuoTeamMapper.mapDuoTeamListFromDB( dao.getTeamResultsDuo( teamId ), local ) );

            return ret;
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public boolean isATeamMemberAlreadyInTheSystem( long teamId )
        throws JJWManagerException
    {
        try
        {
            List<Fighter> fighterList = fighterDao.getFighterByTeam( teamId, false );
            if ( fighterList != null && !fighterList.isEmpty() )
                return true;

            List<DuoTeam> duoTeamList = duoTeamDao.getDuoTeamByTeam( teamId, false );
            if ( duoTeamList != null && !duoTeamList.isEmpty() )
                return true;

            List<NewaFighter> newaFighterList = newaFighterDao.getFighterByTeam( teamId, false );
            if ( newaFighterList != null && !newaFighterList.isEmpty() )
                return true;

            return false;
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }


    /**
     * @return the fighterDao
     */
    public FighterDao getFighterDao()
    {
        return fighterDao;
    }

    /**
     * @param fighterDao the fighterDao to set
     */
    public void setFighterDao( FighterDao fighterDao )
    {
        this.fighterDao = fighterDao;
    }

    /**
     * @return the duoTeamDao
     */
    public DuoTeamDao getDuoTeamDao()
    {
        return duoTeamDao;
    }

    /**
     * @param duoTeamDao the duoTeamDao to set
     */
    public void setDuoTeamDao( DuoTeamDao duoTeamDao )
    {
        this.duoTeamDao = duoTeamDao;
    }

    /**
     * @return the newaFighterDao
     */
    public NewaFighterDao getNewaFighterDao()
    {
        return newaFighterDao;
    }

    /**
     * @param newaFighterDao the newaFighterDao to set
     */
    public void setNewaFighterDao( NewaFighterDao newaFighterDao )
    {
        this.newaFighterDao = newaFighterDao;
    }

}
