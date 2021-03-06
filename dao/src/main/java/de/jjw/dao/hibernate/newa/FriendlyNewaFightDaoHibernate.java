/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FriendlyNewaFightDaoHibernate.java
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

package de.jjw.dao.hibernate.newa;

import java.util.List;

import org.hibernate.Query;

import de.jjw.dao.hibernate.BaseDaoHibernate;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.dao.hibernate.admin.IAgeDatabaseConstants;
import de.jjw.dao.newa.FriendlyNewaFightDao;
import de.jjw.model.newa.FriendlyNewaFight;
import de.jjw.model.newa.NewaFighter;

public class FriendlyNewaFightDaoHibernate
    extends BaseDaoHibernate
    implements FriendlyNewaFightDao, INewaFightDatabaseConstants, INewaFighterDatabaseConstants, IAgeDatabaseConstants
{

    private static String ALL_FRIENDLY_FIGHTS = "from " + TABLE_FRIENDLY_FIGHT;

    public List<FriendlyNewaFight> getAllFriendlyFights()
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( ALL_FRIENDLY_FIGHTS );
            List<FriendlyNewaFight> list = (List<FriendlyNewaFight>) q.list();
            for ( FriendlyNewaFight item : list )
            {
                item.setFighterRed( (NewaFighter) getHibernateTemplate().get( NewaFighter.class, item.getFighterIdRed() ) );
                item.setFighterBlue( (NewaFighter) getHibernateTemplate().get( NewaFighter.class,
                                                                               item.getFighterIdBlue() ) );
            }

            return list;
        }
        catch ( Exception e )
        {
            log.fatal( "Can't getAllFriendlyFights: " + e.toString() );
            throw new JJWDataLayerException( e );
        }
    }
    private static String FIGHT_BY_ID = "from " + TABLE_FRIENDLY_FIGHT + " where " + ID + "=?";

    private static String EXIST_FIGHT = "from " + TABLE_FRIENDLY_FIGHT + " where " + FIGHTER_ID_RED + "=? AND "
        + FIGHTER_ID_BLUE + "=?";

    private static String FIGHTS_BY_FIGHTER = "from " + TABLE_FRIENDLY_FIGHT + " where " + FIGHTER_ID_RED + "=? OR "
        + FIGHTER_ID_BLUE + "=?";

    public boolean existFight( Long fightId )
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

    public FriendlyNewaFight getFight( Long fightId )
        throws JJWDataLayerException
    {
        try
        {
            FriendlyNewaFight fight = (FriendlyNewaFight) getHibernateTemplate().get( FriendlyNewaFight.class, fightId );

            NewaFighter fighterIdRed =
                (NewaFighter) getHibernateTemplate().get( NewaFighter.class, fight.getFighterIdRed() );
            NewaFighter fighterIdBlue =
                (NewaFighter) getHibernateTemplate().get( NewaFighter.class, fight.getFighterIdBlue() );
            fight.setFighterRed( fighterIdRed );
            fight.setFighterBlue( fighterIdBlue );
            return fight;
        }
        catch ( Exception e )
        {
            log.error( e.getMessage() );
            throw new JJWDataLayerException( e );

        }
    }

    public List<FriendlyNewaFight> getFightsFromFighter( NewaFighter fighter )
    {
        Query q = getSession().createQuery( FIGHTS_BY_FIGHTER );
        q.setLong( 0, fighter.getId() );
        q.setLong( 1, fighter.getId() );

        return q.list();
    }

    public void removeFriendlyFight( FriendlyNewaFight fight )
        throws JJWDataLayerException
    {
        try
        {
            getHibernateTemplate().delete( fight );
        }
        catch ( Exception e )
        {
            log.fatal( "Can't remove fight: " + e.toString() );
            throw new JJWDataLayerException( e );
        }

    }

    public void removeFriendlyFight( Long fightId )
        throws JJWDataLayerException
    {
        try
        {
            removeFriendlyFight( getFight( fightId ) );
        }
        catch ( Exception e )
        {
            log.fatal( "Can't remove fight: " + e.toString() );
            throw new JJWDataLayerException( e );
        }
    }

    public void saveFriendlyFight( FriendlyNewaFight fight )
        throws JJWDataLayerException
    {
        try
        {
            getHibernateTemplate().saveOrUpdate( fight );
        }
        catch ( Exception e )
        {
            log.fatal( "Can't save fight: " + e.toString() );
            throw new JJWDataLayerException( e );
        }
    }

    public boolean existFight( Long fightIdRed, Long fightIdBlue )
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
