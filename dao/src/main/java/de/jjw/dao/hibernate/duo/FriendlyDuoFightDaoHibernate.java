/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FriendlyDuoFightDaoHibernate.java
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

package de.jjw.dao.hibernate.duo;

import java.util.List;

import org.hibernate.Query;

import de.jjw.dao.duo.FriendlyDuoFightDao;
import de.jjw.dao.hibernate.BaseDaoHibernate;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.dao.hibernate.admin.IAgeDatabaseConstants;
import de.jjw.dao.hibernate.fighting.IFighterDatabaseConstants;
import de.jjw.model.duo.DuoTeam;
import de.jjw.model.duo.FriendlyDuoFight;

public class FriendlyDuoFightDaoHibernate
    extends BaseDaoHibernate
    implements FriendlyDuoFightDao, IDuoFightDatabaseConstants, IFighterDatabaseConstants, IAgeDatabaseConstants
{

    private static String ALL_FRIENDLY_FIGHTS = "from " + TABLE_FRIENDLY_DUO_FIGHT;

    public List<FriendlyDuoFight> getAllFriendlyDuoFights()
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( ALL_FRIENDLY_FIGHTS );
            List<FriendlyDuoFight> list = (List<FriendlyDuoFight>) q.list();
            for ( FriendlyDuoFight item : list )
            {
                item.setDuoTeamRed( (DuoTeam) getHibernateTemplate().get( DuoTeam.class, item.getTeamIdRed() ) );
                item.setDuoTeamBlue( (DuoTeam) getHibernateTemplate().get( DuoTeam.class, item.getTeamIdBlue() ) );
            }

            return list;
        }
        catch ( Exception e )
        {
            log.fatal( "Can't getAllFriendlyDuoFights: " + e.toString() );
            throw new JJWDataLayerException( e );
        }
    }

    private static String FIGHT_BY_ID = "from " + TABLE_FRIENDLY_DUO_FIGHT + " where " + ID + "=?";

    private static String EXIST_FIGHT = "from " + TABLE_FRIENDLY_DUO_FIGHT + " where " + TEAM_ID_RED + "=? AND "
        + TEAM_ID_BLUE + "=?";

    private static String FIGHTS_BY_FIGHTER = "from " + TABLE_FRIENDLY_DUO_FIGHT + " where " + TEAM_ID_RED + "=? OR "
        + TEAM_ID_BLUE + "=?";

    public boolean existDuoFight( Long fightId )
    {
        Query q = getSession().createQuery( FIGHT_BY_ID );
        q.setLong( 0, fightId );

        List list = q.list();
        if ( list == null || list.size() == 0 )
        {
            return false;
        }
        return true;
    }

    public FriendlyDuoFight getDuoFight( Long fightId )
        throws JJWDataLayerException
    {
        try
        {
            FriendlyDuoFight fight = (FriendlyDuoFight) getHibernateTemplate().get( FriendlyDuoFight.class, fightId );

            DuoTeam teamIdRed = (DuoTeam) getHibernateTemplate().get( DuoTeam.class, fight.getTeamIdRed() );
            DuoTeam teamIdBlue = (DuoTeam) getHibernateTemplate().get( DuoTeam.class, fight.getTeamIdBlue() );
            fight.setDuoTeamRed( teamIdRed );
            fight.setDuoTeamBlue( teamIdBlue );
            return fight;
        }
        catch ( Exception e )
        {
            log.error( e.getMessage() );
            throw new JJWDataLayerException( e );
        }
    }

    public List<FriendlyDuoFight> getFightsFromDuoTeam( DuoTeam duoteam )
    {
        Query q = getSession().createQuery( FIGHTS_BY_FIGHTER );
        q.setLong( 0, duoteam.getId() );
        q.setLong( 1, duoteam.getId() );

        return q.list();
    }

    public void removeDuoFriendlyFight( FriendlyDuoFight fight )
        throws JJWDataLayerException
    {
        try
        {
            getHibernateTemplate().delete( fight );
        }
        catch ( Exception e )
        {
            log.fatal( "Can't remove removeDuoFriendlyFight: " + e.toString() );
            throw new JJWDataLayerException( e );
        }

    }

    public void removeDuoFriendlyFight( Long fightId )
        throws JJWDataLayerException
    {
        try
        {
            removeDuoFriendlyFight( getDuoFight( fightId ) );
        }
        catch ( Exception e )
        {
            log.fatal( "Can't remove removeDuoFriendlyFight: " + e.toString() );
            throw new JJWDataLayerException( e );
        }
    }

    public void saveFriendlyDuoFight( FriendlyDuoFight fight )
        throws JJWDataLayerException
    {
        try
        {
            getHibernateTemplate().saveOrUpdate( fight );
        }
        catch ( Exception e )
        {
            log.fatal( "Can't save saveFriendlyDuoFight: " + e.toString() );
            throw new JJWDataLayerException( e );
        }
    }

    public boolean existDuoFight( Long fightIdRed, Long fightIdBlue )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( FIGHT_BY_ID );
            q.setLong( 0, fightIdRed );
            q.setLong( 1, fightIdBlue );

            List list = q.list();
            if ( list == null || list.size() == 0 )
            {
                return false;
            }
            return true;
        }
        catch ( Exception e )
        {
            log.fatal( "Can't save fight: " + e.toString() );
            throw new JJWDataLayerException( e );
        }
    }
}
