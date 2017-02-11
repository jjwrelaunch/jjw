/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : MedalRankingDaoHibernate.java
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

package de.jjw.dao.hibernate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import de.jjw.dao.MedalRankingDao;
import de.jjw.dao.hibernate.admin.ICountryDatabaseConstants;
import de.jjw.dao.hibernate.admin.IRegionDatabaseConstants;
import de.jjw.dao.hibernate.duo.IDuoTeamDatabaseConstants;
import de.jjw.dao.hibernate.fighting.IFighterDatabaseConstants;
import de.jjw.dao.hibernate.newa.INewaFighterDatabaseConstants;
import de.jjw.model.MedalRanking;

public class MedalRankingDaoHibernate
    extends BaseDaoHibernate
    implements IFighterDatabaseConstants, MedalRankingDao
{

    private static String SQL_MEDAL_RANKING_REGION =
 "Select distinct(r." + IRegionDatabaseConstants.DESCRIPTION
        + ") ," + "(SELECT Count(t2." + REGION +
            ") FROM " + SQL_TABLE_FIGHTER + " f2  " + SQL_LEFT + SQL_JOIN + ITeamDatabaseConstants.SQL_TABLE_TEAM +
            " t2 on f2." + TEAM + " = t2." + ID + " WHERE t2." + REGION + "=r." + ID + " AND f2." + PLACE +
            "=1) AS first_place, " +

            "(SELECT Count(t2." + REGION + ") FROM " + SQL_TABLE_FIGHTER + " f2  " + SQL_LEFT + SQL_JOIN +
            ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID + " WHERE t2." + REGION +
            "=r." + ID + " AND f2." + PLACE + "=2) AS second_place, " +

            "(SELECT Count(t2." + REGION + ") FROM " + SQL_TABLE_FIGHTER + " f2  " + SQL_LEFT + SQL_JOIN +
            ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID + " WHERE t2." + REGION +
            "=r." + ID + " AND f2." + PLACE + "=3) AS third_place " +
 " ,r.id " +

            " FROM " + SQL_TABLE_FIGHTER + " f  " + SQL_LEFT + SQL_JOIN + ITeamDatabaseConstants.SQL_TABLE_TEAM +
            " t on f." + TEAM + " = t." + ID + SQL_LEFT + SQL_JOIN + IRegionDatabaseConstants.SQL_TABLE_REGION +
            " r on t." + ITeamDatabaseConstants.TEAM_REGION + " = r." + ID + " WHERE f." + PLACE + " > 0 AND f." +
 PLACE
        + " < 4 ";

    private static String SQL_MEDAL_RANKING_REGION_NEWA = "Select distinct(r." + IRegionDatabaseConstants.DESCRIPTION
        + ") ," + "(SELECT Count(t2." + REGION + ") FROM " + INewaFighterDatabaseConstants.SQL_TABLE_FIGHTER + " f2  "
        + SQL_LEFT + SQL_JOIN + ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID
        + " WHERE t2." + REGION + "=r." + ID + " AND f2." + PLACE + "=1) AS first_place, " +

        "(SELECT Count(t2." + REGION + ") FROM " + INewaFighterDatabaseConstants.SQL_TABLE_FIGHTER + " f2  " + SQL_LEFT
        + SQL_JOIN + ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID + " WHERE t2."
        + REGION + "=r." + ID + " AND f2." + PLACE + "=2) AS second_place, " +

        "(SELECT Count(t2." + REGION + ") FROM " + INewaFighterDatabaseConstants.SQL_TABLE_FIGHTER + " f2  " + SQL_LEFT
        + SQL_JOIN + ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID + " WHERE t2."
        + REGION + "=r." + ID + " AND f2." + PLACE + "=3) AS third_place " + " ,r.id " +

        " FROM " + INewaFighterDatabaseConstants.SQL_TABLE_FIGHTER + " f  " + SQL_LEFT + SQL_JOIN
        + ITeamDatabaseConstants.SQL_TABLE_TEAM + " t on f." + TEAM + " = t." + ID + SQL_LEFT + SQL_JOIN
        + IRegionDatabaseConstants.SQL_TABLE_REGION + " r on t." + ITeamDatabaseConstants.TEAM_REGION + " = r." + ID
        + " WHERE f." + PLACE + " > 0 AND f." + PLACE + " < 4 ";

    private static String SQL_MEDAL_RANKING_REGION_DUO =
 "Select distinct(r." + IRegionDatabaseConstants.DESCRIPTION
        + ") ," + "(SELECT Count(t2." + REGION +
            ") FROM " + IDuoTeamDatabaseConstants.SQL_TABLE_DUOTEAM + " f2  " + SQL_LEFT + SQL_JOIN +
            ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID + " WHERE t2." + REGION +
            "=r." + ID + " AND f2." + PLACE + "=1) AS first_place, " +

            "(SELECT Count(t2." + REGION + ") FROM " + IDuoTeamDatabaseConstants.SQL_TABLE_DUOTEAM + " f2  " +
            SQL_LEFT + SQL_JOIN + ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID +
            " WHERE t2." + REGION + "=r." + ID + " AND f2." + PLACE + "=2) AS second_place, " +

            "(SELECT Count(t2." + REGION + ") FROM " + IDuoTeamDatabaseConstants.SQL_TABLE_DUOTEAM + " f2  " +
            SQL_LEFT + SQL_JOIN + ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID +
            " WHERE t2." + REGION + "=r." + ID + " AND f2." + PLACE + "=3) AS third_place " +
 " ,r.id " +
            " FROM " + IDuoTeamDatabaseConstants.SQL_TABLE_DUOTEAM + " f  " + SQL_LEFT + SQL_JOIN +
            ITeamDatabaseConstants.SQL_TABLE_TEAM + " t on f." + TEAM + " = t." + ID + SQL_LEFT + SQL_JOIN +
            IRegionDatabaseConstants.SQL_TABLE_REGION + " r on t." + ITeamDatabaseConstants.TEAM_REGION + " = r." + ID +
 " WHERE f." + PLACE + " > 0 AND f." + PLACE + " < 4 ";

    public List<MedalRanking> getMedalRankingByRegion()
        throws JJWDataLayerException
    {
        try
        {
            Map<Long, MedalRanking> regionMap = new HashMap<Long, MedalRanking>();
            Query q = getSession().createSQLQuery( SQL_MEDAL_RANKING_REGION );
            List list = q.list();
            List<MedalRanking> retList = new ArrayList<MedalRanking>( list.size() );
            Object[] hlp;
            for ( Object item : list )
            {
                hlp = (Object[]) item;
                regionMap.put( ( (BigInteger) hlp[4] ).longValue(),
                               new MedalRanking( (String) hlp[0], ( (BigInteger) hlp[1] ).intValue(),
                                                 ( (BigInteger) hlp[2] ).intValue(), ( (BigInteger) hlp[3] ).intValue() ) );
            }

            q = getSession().createSQLQuery( SQL_MEDAL_RANKING_REGION_DUO );
            list = q.list();
            for ( Object item : list )
            {
                hlp = (Object[]) item;

                if ( regionMap.containsKey( ( (BigInteger) hlp[4] ).longValue() ) )
                {
                    MedalRanking medalRanking = regionMap.get( ( (BigInteger) hlp[4] ).longValue() );

                    regionMap.put( ( (BigInteger) hlp[4] ).longValue(),
                                   new MedalRanking( (String) hlp[0], ( (BigInteger) hlp[1] ).intValue()
                                       + medalRanking.getFirstPlace(), ( (BigInteger) hlp[2] ).intValue()
                                       + medalRanking.getSecondPlace(), ( (BigInteger) hlp[3] ).intValue()
                                       + +medalRanking.getThirdPlace() ) );

                }
                else
                {
                    regionMap.put( ( (BigInteger) hlp[4] ).longValue(),
                                   new MedalRanking( (String) hlp[0], ( (BigInteger) hlp[1] ).intValue(),
                                                     ( (BigInteger) hlp[2] ).intValue(),
                                                     ( (BigInteger) hlp[3] ).intValue() ) );
                }
            }

            // newa
            q = getSession().createSQLQuery( SQL_MEDAL_RANKING_REGION_NEWA );
            list = q.list();
            for ( Object item : list )
            {
                hlp = (Object[]) item;

                if ( regionMap.containsKey( ( (BigInteger) hlp[4] ).longValue() ) )
                {
                    MedalRanking medalRanking = regionMap.get( ( (BigInteger) hlp[4] ).longValue() );

                    regionMap.put( ( (BigInteger) hlp[4] ).longValue(),
                                   new MedalRanking( (String) hlp[0], ( (BigInteger) hlp[1] ).intValue()
                                       + medalRanking.getFirstPlace(), ( (BigInteger) hlp[2] ).intValue()
                                       + medalRanking.getSecondPlace(), ( (BigInteger) hlp[3] ).intValue()
                                       + +medalRanking.getThirdPlace() ) );

                }
                else
                {
                    regionMap.put( ( (BigInteger) hlp[4] ).longValue(),
                                   new MedalRanking( (String) hlp[0], ( (BigInteger) hlp[1] ).intValue(),
                                                     ( (BigInteger) hlp[2] ).intValue(),
                                                     ( (BigInteger) hlp[3] ).intValue() ) );
                }
            }
            retList.addAll( regionMap.values() );
            Collections.sort( retList );
            return retList;
        }
        catch ( Exception e )
        {
            log.error( "can not getMedalRankingByRegion", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String SQL_MEDAL_RANKING_COUNTRY =
 "Select distinct(r." + IRegionDatabaseConstants.DESCRIPTION
        + ") ," + "(SELECT Count(t2." + COUNTRY +
            ") FROM " + SQL_TABLE_FIGHTER + " f2  " + SQL_LEFT + SQL_JOIN + ITeamDatabaseConstants.SQL_TABLE_TEAM +
            " t2 on f2." + TEAM + " = t2." + ID + " WHERE t2." + COUNTRY + "=r." + ID + " AND f2." + PLACE +
            "=1) AS first_place, " +

            "(SELECT Count(t2." + COUNTRY + ") FROM " + SQL_TABLE_FIGHTER + " f2  " + SQL_LEFT + SQL_JOIN +
            ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID + " WHERE t2." + COUNTRY +
            "=r." + ID + " AND f2." + PLACE + "=2) AS second_place, " +

            "(SELECT Count(t2." + COUNTRY + ") FROM " + SQL_TABLE_FIGHTER + " f2  " + SQL_LEFT + SQL_JOIN +
            ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID + " WHERE t2." + COUNTRY +
            "=r." + ID + " AND f2." + PLACE + "=3) AS third_place " +

        " ,r.id " +
            " FROM " + SQL_TABLE_FIGHTER + " f  " + SQL_LEFT + SQL_JOIN + ITeamDatabaseConstants.SQL_TABLE_TEAM +
            " t on f." + TEAM + " = t." + ID + SQL_LEFT + SQL_JOIN + ICountryDatabaseConstants.SQL_TABLE_COUNTRY +
            " r on t." + ITeamDatabaseConstants.TEAM_COUNTRY + " = r." + ID + " WHERE f." + PLACE + " > 0 AND f." +
 PLACE
        + " < 4";

    private static String SQL_MEDAL_RANKING_COUNTRY_NEWA = "Select distinct(r." + IRegionDatabaseConstants.DESCRIPTION
        + ") ," + "(SELECT Count(t2." + COUNTRY + ") FROM " + INewaFighterDatabaseConstants.SQL_TABLE_FIGHTER + " f2  "
        + SQL_LEFT + SQL_JOIN + ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID
        + " WHERE t2." + COUNTRY + "=r." + ID + " AND f2." + PLACE + "=1) AS first_place, " +

        "(SELECT Count(t2." + COUNTRY + ") FROM " + INewaFighterDatabaseConstants.SQL_TABLE_FIGHTER + " f2  "
        + SQL_LEFT + SQL_JOIN + ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID
        + " WHERE t2." + COUNTRY + "=r." + ID + " AND f2." + PLACE + "=2) AS second_place, " +

        "(SELECT Count(t2." + COUNTRY + ") FROM " + INewaFighterDatabaseConstants.SQL_TABLE_FIGHTER + " f2  "
        + SQL_LEFT + SQL_JOIN + ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID
        + " WHERE t2." + COUNTRY + "=r." + ID + " AND f2." + PLACE + "=3) AS third_place " +

        " ,r.id " + " FROM " + INewaFighterDatabaseConstants.SQL_TABLE_FIGHTER + " f  " + SQL_LEFT + SQL_JOIN
        + ITeamDatabaseConstants.SQL_TABLE_TEAM + " t on f." + TEAM + " = t." + ID + SQL_LEFT + SQL_JOIN
        + ICountryDatabaseConstants.SQL_TABLE_COUNTRY + " r on t." + ITeamDatabaseConstants.TEAM_COUNTRY + " = r." + ID
        + " WHERE f." + PLACE + " > 0 AND f." + PLACE + " < 4";

    private static String SQL_MEDAL_RANKING_COUNTRY_DUO =
 "Select distinct(r." + IRegionDatabaseConstants.DESCRIPTION
        + ") ," + "(SELECT Count(t2." + COUNTRY +
            ") FROM " + IDuoTeamDatabaseConstants.SQL_TABLE_DUOTEAM + " f2  " + SQL_LEFT + SQL_JOIN +
            ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID + " WHERE t2." + COUNTRY +
            "=r." + ID + " AND f2." + PLACE + "=1) AS first_place, " +

            "(SELECT Count(t2." + COUNTRY + ") FROM " + IDuoTeamDatabaseConstants.SQL_TABLE_DUOTEAM + " f2  " +
            SQL_LEFT + SQL_JOIN + ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID +
            " WHERE t2." + COUNTRY + "=r." + ID + " AND f2." + PLACE + "=2) AS second_place, " +

            "(SELECT Count(t2." + COUNTRY + ") FROM " + IDuoTeamDatabaseConstants.SQL_TABLE_DUOTEAM + " f2  " +
            SQL_LEFT + SQL_JOIN + ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID +
            " WHERE t2." + COUNTRY + "=r." + ID + " AND f2." + PLACE + "=3) AS third_place " +
 " ,r.id " +

            " FROM " + IDuoTeamDatabaseConstants.SQL_TABLE_DUOTEAM + " f  " + SQL_LEFT + SQL_JOIN +
            ITeamDatabaseConstants.SQL_TABLE_TEAM + " t on f." + TEAM + " = t." + ID + SQL_LEFT + SQL_JOIN +
            ICountryDatabaseConstants.SQL_TABLE_COUNTRY + " r on t." + ITeamDatabaseConstants.TEAM_COUNTRY + " = r." +
 ID
        + " WHERE f." + PLACE + " > 0 AND f." + PLACE + " < 4 ";

    public List<MedalRanking> getMedalRankingByCountry()
        throws JJWDataLayerException
    {
        try
        {
            Map<Long, MedalRanking> regionMap = new HashMap<Long, MedalRanking>();
            Query q = getSession().createSQLQuery( SQL_MEDAL_RANKING_COUNTRY );
            List list = q.list();
            List<MedalRanking> retList = new ArrayList<MedalRanking>( list.size() );
            Object[] hlp;
            for ( Object item : list )
            {
                hlp = (Object[]) item;
                regionMap.put( ( (BigInteger) hlp[4] ).longValue(),
                               new MedalRanking( (String) hlp[0], ( (BigInteger) hlp[1] ).intValue(),
                                                 ( (BigInteger) hlp[2] ).intValue(), ( (BigInteger) hlp[3] ).intValue() ) );
            }

            q = getSession().createSQLQuery( SQL_MEDAL_RANKING_COUNTRY_DUO );
            list = q.list();
            for ( Object item : list )
            {
                hlp = (Object[]) item;

                if ( regionMap.containsKey( ( (BigInteger) hlp[4] ).longValue() ) )
                {
                    MedalRanking medalRanking = regionMap.get( ( (BigInteger) hlp[4] ).longValue() );

                    regionMap.put( ( (BigInteger) hlp[4] ).longValue(),
                                   new MedalRanking( (String) hlp[0], ( (BigInteger) hlp[1] ).intValue()
                                       + medalRanking.getFirstPlace(), ( (BigInteger) hlp[2] ).intValue()
                                       + medalRanking.getSecondPlace(), ( (BigInteger) hlp[3] ).intValue()
                                       + +medalRanking.getThirdPlace() ) );

                }
                else
                {
                    regionMap.put( ( (BigInteger) hlp[4] ).longValue(),
                                   new MedalRanking( (String) hlp[0], ( (BigInteger) hlp[1] ).intValue(),
                                                     ( (BigInteger) hlp[2] ).intValue(),
                                                     ( (BigInteger) hlp[3] ).intValue() ) );
                }
            }
            // newa
            q = getSession().createSQLQuery( SQL_MEDAL_RANKING_COUNTRY_NEWA );
            list = q.list();
            for ( Object item : list )
            {
                hlp = (Object[]) item;

                if ( regionMap.containsKey( ( (BigInteger) hlp[4] ).longValue() ) )
                {
                    MedalRanking medalRanking = regionMap.get( ( (BigInteger) hlp[4] ).longValue() );

                    regionMap.put( ( (BigInteger) hlp[4] ).longValue(),
                                   new MedalRanking( (String) hlp[0], ( (BigInteger) hlp[1] ).intValue()
                                       + medalRanking.getFirstPlace(), ( (BigInteger) hlp[2] ).intValue()
                                       + medalRanking.getSecondPlace(), ( (BigInteger) hlp[3] ).intValue()
                                       + +medalRanking.getThirdPlace() ) );

                }
                else
                {
                    regionMap.put( ( (BigInteger) hlp[4] ).longValue(),
                                   new MedalRanking( (String) hlp[0], ( (BigInteger) hlp[1] ).intValue(),
                                                     ( (BigInteger) hlp[2] ).intValue(),
                                                     ( (BigInteger) hlp[3] ).intValue() ) );
                }
            }
            retList.addAll( regionMap.values() );
            Collections.sort( retList );
            return retList;
        }
        catch ( Exception e )
        {
            log.error( "can not getMedalRankingByCountry", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String SQL_MEDAL_RANKING_TEAM =
        "Select  distinct(r." + ITeamDatabaseConstants.TEAM_NAME + ") as name," + "(SELECT Count(q." + TEAM +
            ")  AS first_place FROM " + TABLE_FIGHTER + " as q WHERE f." + TEAM + "=q." + TEAM + " AND q." + PLACE +
            "=1), " + "(SELECT Count(q." + TEAM + ") AS second_place FROM " + TABLE_FIGHTER + " as q WHERE f." + TEAM +
            "=q." + TEAM + " AND q." + PLACE + "=2) , " + "(SELECT Count(q." + TEAM + ") AS third_place FROM " +
            TABLE_FIGHTER + " as q WHERE f." + TEAM + "=q." + TEAM + " AND q." + PLACE + "=3)  " +

        " ,r.id " +
            " FROM " + TABLE_FIGHTER + " f  " + SQL_LEFT + SQL_JOIN + " f." + IFighterDatabaseConstants.TEAM + " r " +
            " WHERE f." + PLACE + " >0 AND f." + PLACE + " <4  GROUP BY " + ITeamDatabaseConstants.TEAM_NAME;

    private static String SQL_MEDAL_RANKING_TEAM_NEWA = "Select  distinct(r." + ITeamDatabaseConstants.TEAM_NAME
        + ") as name," + "(SELECT Count(q." + TEAM + ")  AS first_place FROM "
        + INewaFighterDatabaseConstants.TABLE_FIGHTER + " as q WHERE f." + TEAM + "=q." + TEAM + " AND q." + PLACE
        + "=1), " + "(SELECT Count(q." + TEAM + ") AS second_place FROM " + INewaFighterDatabaseConstants.TABLE_FIGHTER
        + " as q WHERE f." + TEAM + "=q." + TEAM + " AND q." + PLACE + "=2) , " + "(SELECT Count(q." + TEAM
        + ") AS third_place FROM " + INewaFighterDatabaseConstants.TABLE_FIGHTER + " as q WHERE f." + TEAM + "=q."
        + TEAM + " AND q." + PLACE + "=3)  " +

        " ,r.id " + " FROM " + INewaFighterDatabaseConstants.TABLE_FIGHTER + " f  " + SQL_LEFT + SQL_JOIN + " f."
        + INewaFighterDatabaseConstants.TEAM + " r " + " WHERE f." + PLACE + " >0 AND f." + PLACE + " <4  GROUP BY "
        + ITeamDatabaseConstants.TEAM_NAME;

    private static String SQL_MEDAL_RANKING_TEAM_DUO =
        "Select  distinct(r." + ITeamDatabaseConstants.TEAM_NAME + ") as name," + "(SELECT Count(q." + TEAM +
            ")  AS first_place FROM " + IDuoTeamDatabaseConstants.TABLE_DUOTEAM + " as q WHERE f." + TEAM + "=q." +
            TEAM + " AND q." + PLACE + "=1), " + "(SELECT Count(q." + TEAM + ") AS second_place FROM " +
            IDuoTeamDatabaseConstants.TABLE_DUOTEAM + " as q WHERE f." + TEAM + "=q." + TEAM + " AND q." + PLACE +
            "=2) , " + "(SELECT Count(q." + TEAM + ") AS third_place FROM " + IDuoTeamDatabaseConstants.TABLE_DUOTEAM +
            " as q WHERE f." + TEAM + "=q." + TEAM + " AND q." + PLACE + "=3)  " +

        " ,r.id " +
            " FROM " + IDuoTeamDatabaseConstants.TABLE_DUOTEAM + " f  " + SQL_LEFT + SQL_JOIN + " f." +
            IFighterDatabaseConstants.TEAM + " r " + " WHERE f." + PLACE + " >0 AND f." + PLACE + " <4  GROUP BY " +
            ITeamDatabaseConstants.TEAM_NAME;


    public List<MedalRanking> getMedalRankingByTeam()
        throws JJWDataLayerException
    {
        try
        {
            Map<Long, MedalRanking> regionMap = new HashMap<Long, MedalRanking>();
            List list = getHibernateTemplate().find( SQL_MEDAL_RANKING_TEAM );
            List<MedalRanking> retList = new ArrayList<MedalRanking>( list.size() + 10 );
            Object[] hlp;
            for ( Object item : list )
            {
                hlp = (Object[]) item;
                regionMap.put( ( (Long) hlp[4] ).longValue(),
                               new MedalRanking( (String) hlp[0], ( (Long) hlp[1] ).intValue(),
                                                 ( (Long) hlp[2] ).intValue(), ( (Long) hlp[3] ).intValue() ) );
            }

            list = getHibernateTemplate().find( SQL_MEDAL_RANKING_TEAM_DUO );
            for ( Object item : list )
            {
                hlp = (Object[]) item;

                if ( regionMap.containsKey( ( (Long) hlp[4] ).longValue() ) )
                {
                    MedalRanking medalRanking = regionMap.get( ( (Long) hlp[4] ).longValue() );

                    regionMap.put( ( (Long) hlp[4] ).longValue(),
                                   new MedalRanking( (String) hlp[0], ( (Long) hlp[1] ).intValue()
                                       + medalRanking.getFirstPlace(), ( (Long) hlp[2] ).intValue()
                                       + medalRanking.getSecondPlace(), ( (Long) hlp[3] ).intValue()
                                       + +medalRanking.getThirdPlace() ) );

                }
                else
                {
                    regionMap.put( ( (Long) hlp[4] ).longValue(),
                                   new MedalRanking( (String) hlp[0], ( (Long) hlp[1] ).intValue(),
                                                     ( (Long) hlp[2] ).intValue(), ( (Long) hlp[3] ).intValue() ) );
                }
            }

            // newa

            list = getHibernateTemplate().find( SQL_MEDAL_RANKING_TEAM_NEWA );
            for ( Object item : list )
            {
                hlp = (Object[]) item;

                if ( regionMap.containsKey( ( (Long) hlp[4] ).longValue() ) )
                {
                    MedalRanking medalRanking = regionMap.get( ( (Long) hlp[4] ).longValue() );

                    regionMap.put( ( (Long) hlp[4] ).longValue(),
                                   new MedalRanking( (String) hlp[0], ( (Long) hlp[1] ).intValue()
                                       + medalRanking.getFirstPlace(), ( (Long) hlp[2] ).intValue()
                                       + medalRanking.getSecondPlace(), ( (Long) hlp[3] ).intValue()
                                       + +medalRanking.getThirdPlace() ) );

                }
                else
                {
                    regionMap.put( ( (Long) hlp[4] ).longValue(),
                                   new MedalRanking( (String) hlp[0], ( (Long) hlp[1] ).intValue(),
                                                     ( (Long) hlp[2] ).intValue(), ( (Long) hlp[3] ).intValue() ) );
                }
            }
            retList.addAll( regionMap.values() );
            Collections.sort( retList );
            return retList;
        }
        catch ( Exception e )
        {
            log.error( "can not getMedalRankingByTeam", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String SQL_MEDAL_RANKING_TEAM_BY_AGE =
 "Select  distinct(r." + ITeamDatabaseConstants.TEAM_NAME
        + ") ," + "(SELECT Count(q." + TEAM +
            ")  AS first_place FROM " + TABLE_FIGHTER + " as q WHERE f." + TEAM + "=q." + TEAM + " AND q." + PLACE +
 "=1 and q." + IFighterDatabaseConstants.AGE + "=? ), "
        + "(SELECT Count(q." + TEAM + ") AS second_place FROM " + TABLE_FIGHTER + " as q WHERE f." + TEAM + "=q."
        + TEAM + " AND q." + PLACE + "=2 and q." + IFighterDatabaseConstants.AGE + "=? ) , " + "(SELECT Count(q."
        + TEAM + ") AS third_place FROM " + TABLE_FIGHTER + " as q WHERE f." + TEAM + "=q." + TEAM + " AND q." + PLACE
        + "=3 and q." + IFighterDatabaseConstants.AGE + "=? )  " +

        " ,r.id " +
 " FROM " + TABLE_FIGHTER + " f  " + SQL_LEFT + SQL_JOIN + ITeamDatabaseConstants.SQL_TABLE_TEAM
        + " r on f." + IFighterDatabaseConstants.TEAM + " = r." + ID +
 " WHERE f." + PLACE + " >0 AND f." + PLACE + " <4 and f." + IFighterDatabaseConstants.AGE + "=?  ";

    private static String SQL_MEDAL_RANKING_TEAM_BY_AGE_NEWA = "Select  distinct(r." + ITeamDatabaseConstants.TEAM_NAME
        + ") ," + "(SELECT Count(q." + TEAM + ")  AS first_place FROM " + INewaFighterDatabaseConstants.TABLE_FIGHTER
        + " as q WHERE f." + TEAM + "=q." + TEAM + " AND q." + PLACE + "=1 and q." + INewaFighterDatabaseConstants.AGE
        + "=? ), " + "(SELECT Count(q." + TEAM + ") AS second_place FROM "
        + INewaFighterDatabaseConstants.TABLE_FIGHTER + " as q WHERE f." + TEAM + "=q." + TEAM + " AND q." + PLACE
        + "=2 and q." + INewaFighterDatabaseConstants.AGE + "=? ) , " + "(SELECT Count(q." + TEAM
        + ") AS third_place FROM " + INewaFighterDatabaseConstants.TABLE_FIGHTER + " as q WHERE f." + TEAM + "=q."
        + TEAM + " AND q." + PLACE + "=3 and q." + IFighterDatabaseConstants.AGE + "=? )  " +

        " ,r.id " + " FROM " + INewaFighterDatabaseConstants.TABLE_FIGHTER + " f  " + SQL_LEFT + SQL_JOIN
        + ITeamDatabaseConstants.SQL_TABLE_TEAM + " r on f." + INewaFighterDatabaseConstants.TEAM + " = r." + ID
        + " WHERE f." + PLACE + " >0 AND f." + PLACE + " <4 and f." + INewaFighterDatabaseConstants.AGE + "=?  ";

    private static String SQL_MEDAL_RANKING_TEAMBY_AGE_DUO =
 "Select  distinct(r." + ITeamDatabaseConstants.TEAM_NAME
        + ")," + "(SELECT Count(q." + TEAM +
            ")  AS first_place FROM " + IDuoTeamDatabaseConstants.TABLE_DUOTEAM + " as q WHERE f." + TEAM + "=q." +
 TEAM + " AND q." + PLACE + "=1 and q." + IFighterDatabaseConstants.AGE
        + "=? ), " + "(SELECT Count(q." + TEAM + ") AS second_place FROM " +
            IDuoTeamDatabaseConstants.TABLE_DUOTEAM + " as q WHERE f." + TEAM + "=q." + TEAM + " AND q." + PLACE +
 "=2 and q." + IFighterDatabaseConstants.AGE
        + "=? ) , " + "(SELECT Count(q." + TEAM + ") AS third_place FROM " + IDuoTeamDatabaseConstants.TABLE_DUOTEAM
        + " as q WHERE f." + TEAM + "=q." + TEAM + " AND q." + PLACE + "=3 and q." + IFighterDatabaseConstants.AGE
        + "=? )  " +

        " ,r.id " +
 " FROM " + IDuoTeamDatabaseConstants.TABLE_DUOTEAM + " f  " + SQL_LEFT + SQL_JOIN
        + ITeamDatabaseConstants.SQL_TABLE_TEAM + " r on f." + IFighterDatabaseConstants.TEAM + " = r." + ID
        + " WHERE f." + PLACE + " >0 AND f." + PLACE + " <4 and f."
        +
 IDuoTeamDatabaseConstants.AGE + "=? ";

    public List<MedalRanking> getMedalRankingByTeamAndAge( long ageId )
        throws JJWDataLayerException
    {
        try
        {
            Map<Long, MedalRanking> regionMap = new HashMap<Long, MedalRanking>();
            Query q = getSession().createSQLQuery( SQL_MEDAL_RANKING_TEAM_BY_AGE );
            q.setLong( 0, ageId );
            q.setLong( 1, ageId );
            q.setLong( 2, ageId );
            q.setLong( 3, ageId );
            List list = q.list();
            List<MedalRanking> retList = new ArrayList<MedalRanking>( list.size() + 10 );
            Object[] hlp;
            for ( Object item : list )
            {
                hlp = (Object[]) item;
                regionMap.put( ( (BigInteger) hlp[4] ).longValue(),
                               new MedalRanking( (String) hlp[0], ( (BigInteger) hlp[1] ).intValue(),
                                                 ( (BigInteger) hlp[2] ).intValue(), ( (BigInteger) hlp[3] ).intValue() ) );
            }

            q = getSession().createSQLQuery( SQL_MEDAL_RANKING_TEAMBY_AGE_DUO );
            q.setLong( 0, ageId );
            q.setLong( 1, ageId );
            q.setLong( 2, ageId );
            q.setLong( 3, ageId );
            list = q.list();
            for ( Object item : list )
            {
                hlp = (Object[]) item;

                if ( regionMap.containsKey( ( (BigInteger) hlp[4] ).longValue() ) )
                {
                    MedalRanking medalRanking = regionMap.get( ( (BigInteger) hlp[4] ).longValue() );

                    regionMap.put( ( (BigInteger) hlp[4] ).longValue(),
                                   new MedalRanking( (String) hlp[0], ( (BigInteger) hlp[1] ).intValue()
                                       + medalRanking.getFirstPlace(), ( (BigInteger) hlp[2] ).intValue()
                                       + medalRanking.getSecondPlace(), ( (BigInteger) hlp[3] ).intValue()
                                       + +medalRanking.getThirdPlace() ) );

                }
                else
                {
                    regionMap.put( ( (BigInteger) hlp[4] ).longValue(),
                                   new MedalRanking( (String) hlp[0], ( (BigInteger) hlp[1] ).intValue(),
                                                     ( (BigInteger) hlp[2] ).intValue(),
                                                     ( (BigInteger) hlp[3] ).intValue() ) );
                }
            }

            // newa
            q = getSession().createSQLQuery( SQL_MEDAL_RANKING_TEAM_BY_AGE_NEWA );
            q.setLong( 0, ageId );
            q.setLong( 1, ageId );
            q.setLong( 2, ageId );
            q.setLong( 3, ageId );
            list = q.list();
            for ( Object item : list )
            {
                hlp = (Object[]) item;

                if ( regionMap.containsKey( ( (BigInteger) hlp[4] ).longValue() ) )
                {
                    MedalRanking medalRanking = regionMap.get( ( (BigInteger) hlp[4] ).longValue() );

                    regionMap.put( ( (BigInteger) hlp[4] ).longValue(),
                                   new MedalRanking( (String) hlp[0], ( (BigInteger) hlp[1] ).intValue()
                                       + medalRanking.getFirstPlace(), ( (BigInteger) hlp[2] ).intValue()
                                       + medalRanking.getSecondPlace(), ( (BigInteger) hlp[3] ).intValue()
                                       + +medalRanking.getThirdPlace() ) );

                }
                else
                {
                    regionMap.put( ( (BigInteger) hlp[4] ).longValue(),
                                   new MedalRanking( (String) hlp[0], ( (BigInteger) hlp[1] ).intValue(),
                                                     ( (BigInteger) hlp[2] ).intValue(),
                                                     ( (BigInteger) hlp[3] ).intValue() ) );
                }
            }
            retList.addAll( regionMap.values() );
            Collections.sort( retList );
            return retList;
        }
        catch ( Exception e )
        {
            log.error( "can not getMedalRankingByTeamAndAge", e );
            throw new JJWDataLayerException( e );
        }
    }


    private static String SQL_MEDAL_RANKING_REGION_BY_AGE =
 "Select distinct(r." + IRegionDatabaseConstants.DESCRIPTION
        + ") ," + "(SELECT Count(t2." + REGION +
            ") FROM " + SQL_TABLE_FIGHTER + " f2  " + SQL_LEFT + SQL_JOIN + ITeamDatabaseConstants.SQL_TABLE_TEAM +
            " t2 on f2." + TEAM + " = t2." + ID + " WHERE t2." + REGION + "=r." + ID + " AND f2." + PLACE +
 "=1 and f2." + IFighterDatabaseConstants.AGE + "=? ) AS first_place, " +

            "(SELECT Count(t2." + REGION + ") FROM " + SQL_TABLE_FIGHTER + " f2  " + SQL_LEFT + SQL_JOIN +
            ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID + " WHERE t2." + REGION +
 "=r."
        + ID + " AND f2." + PLACE + "=2 and f2." + IFighterDatabaseConstants.AGE + "=? ) AS second_place, " +

            "(SELECT Count(t2." + REGION + ") FROM " + SQL_TABLE_FIGHTER + " f2  " + SQL_LEFT + SQL_JOIN +
            ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID + " WHERE t2." + REGION +
 "=r."
        + ID + " AND f2." + PLACE + "=3 and f2." + IFighterDatabaseConstants.AGE + "=? ) AS third_place " +

        " ,r.id " +
            " FROM " + SQL_TABLE_FIGHTER + " f  " + SQL_LEFT + SQL_JOIN + ITeamDatabaseConstants.SQL_TABLE_TEAM +
            " t on f." + TEAM + " = t." + ID + SQL_LEFT + SQL_JOIN + IRegionDatabaseConstants.SQL_TABLE_REGION +
            " r on t." + ITeamDatabaseConstants.TEAM_REGION + " = r." + ID + " WHERE f." + PLACE + " > 0 AND f." +
 PLACE
        + " < 4 and f." + IFighterDatabaseConstants.AGE + "=? ";

    private static String SQL_MEDAL_RANKING_REGION_BY_AGE_NEWA = "Select distinct(r."
        + IRegionDatabaseConstants.DESCRIPTION + ") ," + "(SELECT Count(t2." + REGION + ") FROM "
        + INewaFighterDatabaseConstants.SQL_TABLE_FIGHTER + " f2  " + SQL_LEFT + SQL_JOIN
        + ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID + " WHERE t2." + REGION + "=r."
        + ID + " AND f2." + PLACE + "=1 and f2." + INewaFighterDatabaseConstants.AGE + "=? ) AS first_place, " +

        "(SELECT Count(t2." + REGION + ") FROM " + INewaFighterDatabaseConstants.SQL_TABLE_FIGHTER + " f2  " + SQL_LEFT
        + SQL_JOIN + ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID + " WHERE t2."
        + REGION + "=r." + ID + " AND f2." + PLACE + "=2 and f2." + INewaFighterDatabaseConstants.AGE
        + "=? ) AS second_place, " +

        "(SELECT Count(t2." + REGION + ") FROM " + INewaFighterDatabaseConstants.SQL_TABLE_FIGHTER + " f2  " + SQL_LEFT
        + SQL_JOIN + ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID + " WHERE t2."
        + REGION + "=r." + ID + " AND f2." + PLACE + "=3 and f2." + INewaFighterDatabaseConstants.AGE
        + "=? ) AS third_place " +

        " ,r.id " + " FROM " + INewaFighterDatabaseConstants.SQL_TABLE_FIGHTER + " f  " + SQL_LEFT + SQL_JOIN
        + ITeamDatabaseConstants.SQL_TABLE_TEAM + " t on f." + TEAM + " = t." + ID + SQL_LEFT + SQL_JOIN
        + IRegionDatabaseConstants.SQL_TABLE_REGION + " r on t." + ITeamDatabaseConstants.TEAM_REGION + " = r." + ID
        + " WHERE f." + PLACE + " > 0 AND f." + PLACE + " < 4 and f." + INewaFighterDatabaseConstants.AGE + "=? ";

    private static String SQL_MEDAL_RANKING_REGION_BY_AGE_DUO =
 "Select distinct(r."
        + IRegionDatabaseConstants.DESCRIPTION + ")," + "(SELECT Count(t2." + REGION +
            ") FROM " + IDuoTeamDatabaseConstants.SQL_TABLE_DUOTEAM + " f2  " + SQL_LEFT + SQL_JOIN +
            ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID + " WHERE t2." + REGION +
 "=r."
        + ID + " AND f2." + PLACE + "=1 and f2." + IFighterDatabaseConstants.AGE + "=? ) AS first_place, " +

            "(SELECT Count(t2." + REGION + ") FROM " + IDuoTeamDatabaseConstants.SQL_TABLE_DUOTEAM + " f2  " +
            SQL_LEFT + SQL_JOIN + ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID +
 " WHERE t2."
        + REGION + "=r." + ID + " AND f2." + PLACE + "=2 and f2." + IFighterDatabaseConstants.AGE
        + "=? ) AS second_place, " +

            "(SELECT Count(t2." + REGION + ") FROM " + IDuoTeamDatabaseConstants.SQL_TABLE_DUOTEAM + " f2  " +
            SQL_LEFT + SQL_JOIN + ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID +
 " WHERE t2."
        + REGION + "=r." + ID + " AND f2." + PLACE + "=3 and f2." + IFighterDatabaseConstants.AGE
        + "=? ) AS third_place " +

        " ,r.id " +
            " FROM " + IDuoTeamDatabaseConstants.SQL_TABLE_DUOTEAM + " f  " + SQL_LEFT + SQL_JOIN +
            ITeamDatabaseConstants.SQL_TABLE_TEAM + " t on f." + TEAM + " = t." + ID + SQL_LEFT + SQL_JOIN +
            IRegionDatabaseConstants.SQL_TABLE_REGION + " r on t." + ITeamDatabaseConstants.TEAM_REGION + " = r." + ID +
            " WHERE f." + PLACE + " > 0 AND f." + PLACE + " < 4 and f." + IFighterDatabaseConstants.AGE +
 "=? ";


    public List<MedalRanking> getMedalRankingByRegionAndAge( long ageId )
        throws JJWDataLayerException
    {
        try
        {
            Map<Long, MedalRanking> regionMap = new HashMap<Long, MedalRanking>();
            Query q = getSession().createSQLQuery( SQL_MEDAL_RANKING_REGION_BY_AGE );
            q.setLong( 0, ageId );
            q.setLong( 1, ageId );
            q.setLong( 2, ageId );
            q.setLong( 3, ageId );
            List list = q.list();
            List<MedalRanking> retList = new ArrayList<MedalRanking>( list.size() + 10 );
            Object[] hlp;
            for ( Object item : list )
            {
                hlp = (Object[]) item;
                regionMap.put( ( (BigInteger) hlp[4] ).longValue(),
                               new MedalRanking( (String) hlp[0], ( (BigInteger) hlp[1] ).intValue(),
                                                 ( (BigInteger) hlp[2] ).intValue(), ( (BigInteger) hlp[3] ).intValue() ) );
            }

            q = getSession().createSQLQuery( SQL_MEDAL_RANKING_REGION_BY_AGE_DUO );
            q.setLong( 0, ageId );
            q.setLong( 1, ageId );
            q.setLong( 2, ageId );
            q.setLong( 3, ageId );
            list = q.list();
            for ( Object item : list )
            {
                hlp = (Object[]) item;

                if ( regionMap.containsKey( ( (BigInteger) hlp[4] ).longValue() ) )
                {
                    MedalRanking medalRanking = regionMap.get( ( (BigInteger) hlp[4] ).longValue() );

                    regionMap.put( ( (BigInteger) hlp[4] ).longValue(),
                                   new MedalRanking( (String) hlp[0], ( (BigInteger) hlp[1] ).intValue()
                                       + medalRanking.getFirstPlace(), ( (BigInteger) hlp[2] ).intValue()
                                       + medalRanking.getSecondPlace(), ( (BigInteger) hlp[3] ).intValue()
                                       + +medalRanking.getThirdPlace() ) );

                }
                else
                {
                    regionMap.put( ( (BigInteger) hlp[4] ).longValue(),
                                   new MedalRanking( (String) hlp[0], ( (BigInteger) hlp[1] ).intValue(),
                                                     ( (BigInteger) hlp[2] ).intValue(),
                                                     ( (BigInteger) hlp[3] ).intValue() ) );
                }
            }

            // newa
            q = getSession().createSQLQuery( SQL_MEDAL_RANKING_REGION_BY_AGE_NEWA );
            q.setLong( 0, ageId );
            q.setLong( 1, ageId );
            q.setLong( 2, ageId );
            q.setLong( 3, ageId );
            list = q.list();
            for ( Object item : list )
            {
                hlp = (Object[]) item;

                if ( regionMap.containsKey( ( (BigInteger) hlp[4] ).longValue() ) )
                {
                    MedalRanking medalRanking = regionMap.get( ( (BigInteger) hlp[4] ).longValue() );

                    regionMap.put( ( (BigInteger) hlp[4] ).longValue(),
                                   new MedalRanking( (String) hlp[0], ( (BigInteger) hlp[1] ).intValue()
                                       + medalRanking.getFirstPlace(), ( (BigInteger) hlp[2] ).intValue()
                                       + medalRanking.getSecondPlace(), ( (BigInteger) hlp[3] ).intValue()
                                       + +medalRanking.getThirdPlace() ) );

                }
                else
                {
                    regionMap.put( ( (BigInteger) hlp[4] ).longValue(),
                                   new MedalRanking( (String) hlp[0], ( (BigInteger) hlp[1] ).intValue(),
                                                     ( (BigInteger) hlp[2] ).intValue(),
                                                     ( (BigInteger) hlp[3] ).intValue() ) );
                }
            }
            retList.addAll( regionMap.values() );
            Collections.sort( retList );
            return retList;
        }
        catch ( Exception e )
        {
            log.error( "can not getMedalRankingByRegionAndAge", e );
            throw new JJWDataLayerException( e );
        }
    }

    private static String SQL_MEDAL_RANKING_COUNTRY_BY_AGE =
 "Select distinct(r."
        + IRegionDatabaseConstants.DESCRIPTION + ") ," + "(SELECT Count(t2." + COUNTRY +
            ") FROM " + SQL_TABLE_FIGHTER + " f2  " + SQL_LEFT + SQL_JOIN + ITeamDatabaseConstants.SQL_TABLE_TEAM +
            " t2 on f2." + TEAM + " = t2." + ID + " WHERE t2." + COUNTRY + "=r." + ID + " AND f2." + PLACE +
 "=1 and f2." + IFighterDatabaseConstants.AGE
        + "=? ) AS first_place, " +

            "(SELECT Count(t2." + COUNTRY + ") FROM " + SQL_TABLE_FIGHTER + " f2  " + SQL_LEFT + SQL_JOIN +
            ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID + " WHERE t2." + COUNTRY +
 "=r."
        + ID + " AND f2." + PLACE + "=2 and f2." + IFighterDatabaseConstants.AGE + "=? ) AS second_place, " +

            "(SELECT Count(t2." + COUNTRY + ") FROM " + SQL_TABLE_FIGHTER + " f2  " + SQL_LEFT + SQL_JOIN +
            ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID + " WHERE t2." + COUNTRY +
 "=r."
        + ID + " AND f2." + PLACE + "=3 and f2." + IFighterDatabaseConstants.AGE + "=? ) AS third_place " +

        " ,r.id " +
            " FROM " + SQL_TABLE_FIGHTER + " f  " + SQL_LEFT + SQL_JOIN + ITeamDatabaseConstants.SQL_TABLE_TEAM +
            " t on f." + TEAM + " = t." + ID + SQL_LEFT + SQL_JOIN + ICountryDatabaseConstants.SQL_TABLE_COUNTRY +
            " r on t." + ITeamDatabaseConstants.TEAM_COUNTRY + " = r." + ID + " WHERE f." + PLACE + " > 0 AND f." +
 PLACE
        + " < 4  and f." + IFighterDatabaseConstants.AGE + "=? ";

    private static String SQL_MEDAL_RANKING_COUNTRY_BY_AGE_NEWA = "Select distinct(r."
        + IRegionDatabaseConstants.DESCRIPTION + ") ," + "(SELECT Count(t2." + COUNTRY + ") FROM "
        + INewaFighterDatabaseConstants.SQL_TABLE_FIGHTER + " f2  " + SQL_LEFT + SQL_JOIN
        + ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID + " WHERE t2." + COUNTRY + "=r."
        + ID + " AND f2." + PLACE + "=1 and f2." + INewaFighterDatabaseConstants.AGE + "=? ) AS first_place, " +

        "(SELECT Count(t2." + COUNTRY + ") FROM " + INewaFighterDatabaseConstants.SQL_TABLE_FIGHTER + " f2  "
        + SQL_LEFT + SQL_JOIN + ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID
        + " WHERE t2." + COUNTRY + "=r." + ID + " AND f2." + PLACE + "=2 and f2." + INewaFighterDatabaseConstants.AGE
        + "=? ) AS second_place, " +

        "(SELECT Count(t2." + COUNTRY + ") FROM " + INewaFighterDatabaseConstants.SQL_TABLE_FIGHTER + " f2  "
        + SQL_LEFT + SQL_JOIN + ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID
        + " WHERE t2." + COUNTRY + "=r." + ID + " AND f2." + PLACE + "=3 and f2." + INewaFighterDatabaseConstants.AGE
        + "=? ) AS third_place " +

        " ,r.id " + " FROM " + INewaFighterDatabaseConstants.SQL_TABLE_FIGHTER + " f  " + SQL_LEFT + SQL_JOIN
        + ITeamDatabaseConstants.SQL_TABLE_TEAM + " t on f." + TEAM + " = t." + ID + SQL_LEFT + SQL_JOIN
        + ICountryDatabaseConstants.SQL_TABLE_COUNTRY + " r on t." + ITeamDatabaseConstants.TEAM_COUNTRY + " = r." + ID
        + " WHERE f." + PLACE + " > 0 AND f." + PLACE + " < 4  and f." + INewaFighterDatabaseConstants.AGE + "=? ";

    private static String SQL_MEDAL_RANKING_COUNTRY_BY_AGE_DUO =
 "Select distinct(r."
        + IRegionDatabaseConstants.DESCRIPTION + ") ," + "(SELECT Count(t2." + COUNTRY +
            ") FROM " + IDuoTeamDatabaseConstants.SQL_TABLE_DUOTEAM + " f2  " + SQL_LEFT + SQL_JOIN +
            ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID + " WHERE t2." + COUNTRY +
 "=r."
        + ID + " AND f2." + PLACE + "=1 and f2." + IFighterDatabaseConstants.AGE + "=? ) AS first_place, " +

            "(SELECT Count(t2." + COUNTRY + ") FROM " + IDuoTeamDatabaseConstants.SQL_TABLE_DUOTEAM + " f2  " +
            SQL_LEFT + SQL_JOIN + ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID +
 " WHERE t2."
        + COUNTRY + "=r." + ID + " AND f2." + PLACE + "=2 and f2." + IFighterDatabaseConstants.AGE
        + "=? ) AS second_place, " +

            "(SELECT Count(t2." + COUNTRY + ") FROM " + IDuoTeamDatabaseConstants.SQL_TABLE_DUOTEAM + " f2  " +
            SQL_LEFT + SQL_JOIN + ITeamDatabaseConstants.SQL_TABLE_TEAM + " t2 on f2." + TEAM + " = t2." + ID +
 " WHERE t2."
        + COUNTRY + "=r." + ID + " AND f2." + PLACE + "=3 and f2." + IFighterDatabaseConstants.AGE
        + "=? ) AS third_place " +

        " ,r.id " +
            " FROM " + IDuoTeamDatabaseConstants.SQL_TABLE_DUOTEAM + " f  " + SQL_LEFT + SQL_JOIN +
            ITeamDatabaseConstants.SQL_TABLE_TEAM + " t on f." + TEAM + " = t." + ID + SQL_LEFT + SQL_JOIN +
            ICountryDatabaseConstants.SQL_TABLE_COUNTRY + " r on t." + ITeamDatabaseConstants.TEAM_COUNTRY + " = r." +
            ID + " WHERE f." + PLACE + " > 0 AND f." + PLACE + " < 4  and f." + IFighterDatabaseConstants.AGE +
 "=? ";


    public List<MedalRanking> getMedalRankingByCountryAndAge( long ageId )
        throws JJWDataLayerException
    {
        try
        {
            Map<Long, MedalRanking> regionMap = new HashMap<Long, MedalRanking>();
            Query q = getSession().createSQLQuery( SQL_MEDAL_RANKING_COUNTRY_BY_AGE );
            q.setLong( 0, ageId );
            q.setLong( 1, ageId );
            q.setLong( 2, ageId );
            q.setLong( 3, ageId );
            List list = q.list();
            List<MedalRanking> retList = new ArrayList<MedalRanking>( list.size() + 10 );
            Object[] hlp;
            for ( Object item : list )
            {
                hlp = (Object[]) item;
                regionMap.put( ( (BigInteger) hlp[4] ).longValue(),
                               new MedalRanking( (String) hlp[0], ( (BigInteger) hlp[1] ).intValue(),
                                                 ( (BigInteger) hlp[2] ).intValue(), ( (BigInteger) hlp[3] ).intValue() ) );
            }

            q = getSession().createSQLQuery( SQL_MEDAL_RANKING_COUNTRY_BY_AGE_DUO );
            q.setLong( 0, ageId );
            q.setLong( 1, ageId );
            q.setLong( 2, ageId );
            q.setLong( 3, ageId );
            list = q.list();
            for ( Object item : list )
            {
                hlp = (Object[]) item;

                if ( regionMap.containsKey( ( (BigInteger) hlp[4] ).longValue() ) )
                {
                    MedalRanking medalRanking = regionMap.get( ( (BigInteger) hlp[4] ).longValue() );

                    regionMap.put( ( (BigInteger) hlp[4] ).longValue(),
                                   new MedalRanking( (String) hlp[0], ( (BigInteger) hlp[1] ).intValue()
                                       + medalRanking.getFirstPlace(), ( (BigInteger) hlp[2] ).intValue()
                                       + medalRanking.getSecondPlace(), ( (BigInteger) hlp[3] ).intValue()
                                       + +medalRanking.getThirdPlace() ) );

                }
                else
                {
                    regionMap.put( ( (BigInteger) hlp[4] ).longValue(),
                                   new MedalRanking( (String) hlp[0], ( (BigInteger) hlp[1] ).intValue(),
                                                     ( (BigInteger) hlp[2] ).intValue(),
                                                     ( (BigInteger) hlp[3] ).intValue() ) );
                }
            }

            // newa
            q = getSession().createSQLQuery( SQL_MEDAL_RANKING_COUNTRY_BY_AGE_NEWA );
            q.setLong( 0, ageId );
            q.setLong( 1, ageId );
            q.setLong( 2, ageId );
            q.setLong( 3, ageId );
            list = q.list();
            for ( Object item : list )
            {
                hlp = (Object[]) item;

                if ( regionMap.containsKey( ( (BigInteger) hlp[4] ).longValue() ) )
                {
                    MedalRanking medalRanking = regionMap.get( ( (BigInteger) hlp[4] ).longValue() );

                    regionMap.put( ( (BigInteger) hlp[4] ).longValue(),
                                   new MedalRanking( (String) hlp[0], ( (BigInteger) hlp[1] ).intValue()
                                       + medalRanking.getFirstPlace(), ( (BigInteger) hlp[2] ).intValue()
                                       + medalRanking.getSecondPlace(), ( (BigInteger) hlp[3] ).intValue()
                                       + +medalRanking.getThirdPlace() ) );

                }
                else
                {
                    regionMap.put( ( (BigInteger) hlp[4] ).longValue(),
                                   new MedalRanking( (String) hlp[0], ( (BigInteger) hlp[1] ).intValue(),
                                                     ( (BigInteger) hlp[2] ).intValue(),
                                                     ( (BigInteger) hlp[3] ).intValue() ) );
                }
            }
            retList.addAll( regionMap.values() );
            Collections.sort( retList );
            return retList;
        }
        catch ( Exception e )
        {
            log.error( "can not getMedalRankingByCountryAndAge", e );
            throw new JJWDataLayerException( e );
        }
    }
}
