/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : TeamDaoHibernate.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:43
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

package de.jjw.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import de.jjw.dao.TeamDao;
import de.jjw.dao.hibernate.duo.IDuoTeamDatabaseConstants;
import de.jjw.dao.hibernate.fighting.IFighterDatabaseConstants;
import de.jjw.model.Team;
import de.jjw.model.duo.DuoTeam;
import de.jjw.model.fighting.Fighter;
import de.jjw.util.TypeUtil;
import de.jjw.util.dto.TeamParticipantDto;

public class TeamDaoHibernate
    extends BaseDaoHibernate
    implements TeamDao, ITeamDatabaseConstants, IFighterDatabaseConstants
{

    private static String TEAMS_ALL =
        "from Team t left join fetch t.country c left join fetch t.region r order by c.id, r.description, t.teamName";

    private static String TEAMS_BY_TEAM_NAME = "from Team t where t.teamName = ?";

    private static String TEAMS_BY_COUNTRY = "from Team t where t.country = ? order by t.teamName";

    private static String TEAMS_BY_REGION = "from Team t where t.region = ? order by t.teamName";

    private static String TEAMS_BY_COUNTRY_AND_REGION =
        "from Team t where t.country = ? AND t.region = ? order by t.teamName";


    private static String TEAM_IN_FIGHTER_USE =
        "select f." + ID + " from " + TABLE_FIGHTER + " f where f." + TEAM + " = ?";

    private static String TEAM_IN_DUO_USE = "select d." + ID + " from DuoTeam d where d.team = ?";

    public Team getTeam( Long id )
    {
        return (Team) getHibernateTemplate().get( Team.class, id );
    }


    @SuppressWarnings("unchecked")
    public List<Team> getAllTeams()
    {
        try
        {
            return (List<Team>) getHibernateTemplate().find( TEAMS_ALL );
        }
        catch ( Exception e )
        {
            return null;
        }
    }



    @SuppressWarnings("unchecked")
    public List<Team> getTeamsByTeamName( String teamName )
    {
        Query q = getSession().createQuery( TEAMS_BY_TEAM_NAME );
        q.setString( 0, teamName );
        return q.list();
    }



    public void saveTeam( Team team )
    {
        try
        {
            getHibernateTemplate().saveOrUpdate( team );
        }
        catch ( Exception e )
        {
            log.fatal( "Can't save team: " + team.toString() );
        }
    }

    @SuppressWarnings("unchecked")
    public List<Team> getTeamsByCountry( Long countryId )
    {
        Query q = getSession().createQuery( TEAMS_BY_COUNTRY );
        q.setLong( 0, countryId );
        return q.list();
    }

    @SuppressWarnings("unchecked")
    public List<Team> getTeamsByCountryAndRegion( Long countryId, Long regionId )
    {
        Query q = getSession().createQuery( TEAMS_BY_COUNTRY_AND_REGION );
        q.setLong( 0, countryId );
        q.setLong( 1, regionId );
        return q.list();
    }


    public List<Team> getTeamsByRegion( long regionId )
    {
        Query q = getSession().createQuery( TEAMS_BY_REGION );
        q.setLong( 0, regionId );
        return q.list();
    }

    private static final String NUMBER_OF_TEAM_MEMBER =
        "select t." + TEAM_NAME + " , " + " (select count(f.id) from " + IFighterDatabaseConstants.TABLE_FIGHTER +
            " f where f." + IFighterDatabaseConstants.TEAM + " = t.id and f." + IFighterDatabaseConstants.READY +
            "='1'), " + " (select count(f.id) from " + IDuoTeamDatabaseConstants.TABLE_DUOTEAM + " f where f." +
 IDuoTeamDatabaseConstants.TEAM + " = t.id and f."
        + IDuoTeamDatabaseConstants.READY + "='1'), " + "t.id from " + TABLE_TEAM + " t";

    private static final String TEAM_RESULTS_FIGHTING = "from " + IFighterDatabaseConstants.TABLE_FIGHTER
        + " f WHERE f." + IFighterDatabaseConstants.TEAM + " =? and f." + IFighterDatabaseConstants.PLACE
        + " is not null and f." + IFighterDatabaseConstants.PLACE + " > 0 order by f."
        + IFighterDatabaseConstants.PLACE
        + " , f." + IFighterDatabaseConstants.AGE + " , f." + IFighterDatabaseConstants.SEX + " , f."
        + IFighterDatabaseConstants.WEIGHT;

    private static final String TEAM_RESULTS_DUO = "from " + IDuoTeamDatabaseConstants.TABLE_DUOTEAM + " f WHERE f."
        + IDuoTeamDatabaseConstants.TEAM + " =? and f." + IDuoTeamDatabaseConstants.PLACE + " is not null and f."
        + IDuoTeamDatabaseConstants.PLACE + " > 0 order by f." + IFighterDatabaseConstants.PLACE + " , f."
        + IDuoTeamDatabaseConstants.AGE + " , f."
        + IDuoTeamDatabaseConstants.SEX;

    public List<TeamParticipantDto> getNumberOfTeamMember()
        throws JJWDataLayerException
    {
        try
        {
            List<TeamParticipantDto> retList = new ArrayList<TeamParticipantDto>( 30 );
            Query q = getSession().createQuery( NUMBER_OF_TEAM_MEMBER );
            List list = q.list();
            Object[] hlp;
            for ( Object item : list )
            {
                hlp = (Object[]) item;

                if ( ( (Long) hlp[1] ).intValue() > TypeUtil.INT_DEFAULT
                    || ( (Long) hlp[2] ).intValue() > TypeUtil.INT_DEFAULT )
                    // only add, when at least one fighter oder team exists
                    retList.add( new TeamParticipantDto( (String) hlp[0], ( (Long) hlp[1] ).intValue(),
                                                         ( (Long) hlp[2] ).intValue(), (Long) hlp[3] ) );
            }
            return retList;
        }
        catch ( Exception e )
        {
            log.error( "can't get getNumberOfTeamMember", e );
            throw new JJWDataLayerException( e );
        }
    }

    public void removeTeam( Long teamId )
    {
        getHibernateTemplate().delete( getTeam( teamId ) );

    }

    public void removeTeam( Team team )
    {
        getHibernateTemplate().delete( getTeam( team.getId() ) );

    }

    public boolean isTeamInUse( Team team )
        throws JJWDataLayerException
    {
        return isTeamInUse( team.getId() );
    }

    public boolean isTeamInUse( Long teamId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( TEAM_IN_FIGHTER_USE );
            q.setLong( 0, teamId );
            if ( q.list().size() == 0 )
            {
                q = getSession().createQuery( TEAM_IN_DUO_USE );
                q.setLong( 0, teamId );
                return q.list().size() != 0;
            }
            else
            {
                return true;
            }
        }
        catch ( Exception e )
        {
            log.error( "can't get isTeamInUse", e );
            throw new JJWDataLayerException( e );
        }
    }

    public List<Fighter> getTeamResultsFighting( long teamId )
    throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( TEAM_RESULTS_FIGHTING );
            q.setLong( 0, teamId );
            return (List<Fighter>) q.list();
        }
        catch ( Exception e )
        {
            log.error( "can't get getTeamResultsFighting", e );
            throw new JJWDataLayerException( e );
        }
    }

    public List<DuoTeam> getTeamResultsDuo( long teamId )
        throws JJWDataLayerException
    {
        try
        {
            Query q2 = getSession().createQuery( TEAM_RESULTS_DUO );
            q2.setLong( 0, teamId );
            return (List<DuoTeam>) q2.list();
        }
        catch ( Exception e )
        {
            log.error( "can't get getTeamResultsDuo", e );
            throw new JJWDataLayerException( e );
        }
}
}
