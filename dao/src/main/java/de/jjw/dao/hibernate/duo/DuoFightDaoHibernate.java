/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoFightDaoHibernate.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:48
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import de.jjw.dao.duo.DuoFightDao;
import de.jjw.dao.hibernate.BaseDaoHibernate;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.dao.hibernate.admin.IAgeDatabaseConstants;
import de.jjw.dao.hibernate.newa.INewaFightDatabaseConstants;
import de.jjw.model.admin.Age;
import de.jjw.model.duo.DuoFight;
import de.jjw.model.duo.DuoTeam;
import de.jjw.model.duo.HighestDuoPoints;

public class DuoFightDaoHibernate
    extends BaseDaoHibernate
    implements DuoFightDao, IDuoFightDatabaseConstants, IDuoTeamDatabaseConstants
{

    public DuoFight getDuoFight( Long fightId )
        throws JJWDataLayerException
    {
        try
        {
            DuoFight fight = (DuoFight) getHibernateTemplate().get( DuoFight.class, fightId );

            DuoTeam fighterIdRed = (DuoTeam) getHibernateTemplate().get( DuoTeam.class, fight.getTeamIdRed() );
            DuoTeam fighterIdBlue = (DuoTeam) getHibernateTemplate().get( DuoTeam.class, fight.getTeamIdBlue() );
            fight.setDuoTeamRed( fighterIdRed );
            fight.setDuoTeamBlue( fighterIdBlue );
            return fight;
        }
        catch ( Exception e )
        {
            log.error( e.getMessage() );
            throw new JJWDataLayerException( e );

        }
    }

    public void saveFight( DuoFight fight )
        throws JJWDataLayerException
    {
        try
        {
            getHibernateTemplate().saveOrUpdate( fight );
        }
        catch ( Exception e )
        {
            log.fatal( "Can't save fight: " + e.getMessage() );
            throw new JJWDataLayerException( e );
        }
    }

    private static String AGE_ALL = "from " + IAgeDatabaseConstants.TABLE_AGE + " a order by a."
        + IAgeDatabaseConstants.ORDER_NUMBER;

    private static String HIGHEST_POINTS_RED = "Select f,fl from " + TABLE_DUOTEAM + " f " + "left join fetch f." + AGE
        + " left join fetch f." + TEAM + " t left join fetch t." + REGION + " left join fetch t." + COUNTRY
 + " , "
        + TABLE_DUO_FIGHT + " fl where f.id =  fl." + TEAM_ID_RED + " and fl." + WINNER_ID + ">0 and fl." + POINTS_RED
        + ">0 and fl." + FUSENGACHI + " =0 and fl." + KIKENGACHI + "=0 and f." + SEX + "=? and f." + AGE + "=?"
        + " order by fl." + POINTS_RED;

    private static String HIGHEST_POINTS_BLUE = "Select f,fl from " + TABLE_DUOTEAM + " f " + "left join fetch f."
        + AGE + " left join fetch f." + TEAM + " t left join fetch t." + REGION + " left join fetch t." + COUNTRY
        + " , " + TABLE_DUO_FIGHT + " fl where f.id =  fl." + TEAM_ID_BLUE + " and fl." + WINNER_ID + ">0 and fl."
        + POINTS_BLUE + ">0 and fl." + FUSENGACHI + " =0 and fl." + KIKENGACHI + "=0 and f." + SEX + "=? and f." + AGE
        + "=?" + " order by fl." + POINTS_BLUE;

    public Map<Integer, List<HighestDuoPoints>> getHighestDuoPoints()
        throws JJWDataLayerException
    {
        try
        {
            Map<Integer, List<HighestDuoPoints>> ret = new HashMap<Integer, List<HighestDuoPoints>>();
            List<Age> ageList = (List<Age>) getSession().createQuery( AGE_ALL ).list();
            DuoTeam duoTeam = null;
            DuoFight fight = null;

            Query q = null;
            List<HighestDuoPoints> fightList = null;
            List<HighestDuoPoints> fightListForSex = null;
            HighestDuoPoints highestDuoPoints = null;
           
            for ( Age item : ageList )
            {
                fightList = new ArrayList<HighestDuoPoints>( 21 );
                fightListForSex = new ArrayList<HighestDuoPoints>( 21 );
                // male
                int sexMale=1;
                q = getSession().createQuery( HIGHEST_POINTS_RED );
                q.setInteger( 0, sexMale );
                q.setLong( 1, item.getId() );
                q.setMaxResults( 10 );
                List<Object> resultList = q.list();
                fightListForSex.addAll( buildResultlist( resultList, sexMale, true ) );

                q = getSession().createQuery( HIGHEST_POINTS_BLUE );
                q.setInteger( 0, sexMale );
                q.setLong( 1, item.getId() );
                q.setMaxResults( 10 );
                resultList = q.list();
                fightListForSex.addAll( buildResultlist( resultList, sexMale, false ) );

                Collections.sort( fightListForSex );
                fightList.addAll( fightListForSex );
                // female
                fightListForSex = new ArrayList<HighestDuoPoints>( 21 );
                int sexFemale=3;
                q = getSession().createQuery( HIGHEST_POINTS_RED );
                q.setInteger( 0, sexFemale );
                q.setLong( 1, item.getId() );
                q.setMaxResults( 10 );
                resultList = q.list();
                fightList.addAll( buildResultlist( resultList, sexFemale, true ) );

                q = getSession().createQuery( HIGHEST_POINTS_BLUE );
                q.setInteger( 0, sexFemale );
                q.setLong( 1, item.getId() );
                q.setMaxResults( 10 );
                resultList = q.list();
                fightList.addAll( buildResultlist( resultList, sexFemale, false ) );

                Collections.sort( fightListForSex );
                fightList.addAll( fightListForSex );

                // mixed
                fightListForSex = new ArrayList<HighestDuoPoints>( 21 );
                int sexMix=3;
                q = getSession().createQuery( HIGHEST_POINTS_RED );
                q.setInteger( 0,sexMix );
                q.setLong( 1, item.getId() );
                q.setMaxResults( 10 );
                resultList = q.list();
                fightList.addAll( buildResultlist( resultList, sexMix, true ) );

                q = getSession().createQuery( HIGHEST_POINTS_BLUE );
                q.setInteger( 0, sexMix );
                q.setLong( 1, item.getId() );
                q.setMaxResults( 10 );
                resultList = q.list();
                fightList.addAll( buildResultlist( resultList, sexMix, false ) );

                Collections.sort( fightListForSex );
                fightList.addAll( fightListForSex );

                if ( fightList.size() > 0 )
                    ret.put( ret.size() + 1, fightList );
            }
            return ret;
        }
        catch ( Exception e )
        {
            log.error( e.getMessage() );
            throw new JJWDataLayerException( e );
        }
    }

    /**
     * @param fightList
     * @param resultList
     */
    private List<HighestDuoPoints> buildResultlist( List<Object> resultList, int sex, boolean isRed )
    {
        List<HighestDuoPoints> fightList = new ArrayList<HighestDuoPoints>( resultList.size() );
        DuoTeam duoTeam;
        DuoFight fight;
        HighestDuoPoints highestDuoPoints;
        for ( Object o : resultList )
        {
            duoTeam = (DuoTeam) ( (Object[]) o )[0];
            fight = (DuoFight) ( (Object[]) o )[1];
            highestDuoPoints = new HighestDuoPoints();
            highestDuoPoints.setName( duoTeam.getName() );
            highestDuoPoints.setFirstname( duoTeam.getFirstname() );
            highestDuoPoints.setName2( duoTeam.getName2() );
            highestDuoPoints.setFirstname2( duoTeam.getFirstname2() );
            highestDuoPoints.setAge( duoTeam.getAge().getDescription() );
            highestDuoPoints.setCountry( duoTeam.getTeam().getCountry().getCountryShort() );
            highestDuoPoints.setRegion( duoTeam.getTeam().getRegion().getRegionShort() );
            highestDuoPoints.setTeam( duoTeam.getTeam().getTeamName() );
            highestDuoPoints.setSex( sex );
            if ( isRed )
            {
                highestDuoPoints.setPoints( fight.getPointsRed() );
                highestDuoPoints.setPointsMax( fight.getPointsRedMax() );
            }
            else
            {
                highestDuoPoints.setPoints( fight.getPointsBlue() );
                highestDuoPoints.setPointsMax( fight.getPointsBlueMax() );
            }

            fightList.add( highestDuoPoints );
        }

        return fightList;
    }
      
    private static String EXIST_FIGHT_IN_MAIN_ROUND =
                    // "from " + TABLE_FIGHT + " where " + FIGHTER_ID_RED + "=? AND " + FIGHTER_ID_BLUE + "=? AND " + IFightDatabaseConstants.FIGHTINGCLASS_ID +"=?";
                                 "Select id from " + SQL_TABLE_DUO_FIGHT + " where " + TEAM_ID_RED + "=? AND " + TEAM_ID_BLUE + "=? AND " 
                                    + IDuoFightDatabaseConstants.DUOCLASS +"=? AND " + MAIN_ROUND +"='Y'";
   
    
    public boolean isDoneFightRegardlessRedBlue( Long duoclassId, Long teamIdRed, Long teamIdBlue ) throws  JJWDataLayerException
    {
        try
        {
            Query q = getSession().createSQLQuery( EXIST_FIGHT_IN_MAIN_ROUND );
            q.setLong( 0, teamIdRed );
            q.setLong( 1, teamIdBlue );
            q.setLong( 2, duoclassId );

            List list = q.list();
            if ( list != null && list.size() > 0 )
            {
                return true;
            }
            
            q = getSession().createSQLQuery( EXIST_FIGHT_IN_MAIN_ROUND );
            q.setLong( 0, teamIdBlue );
            q.setLong( 1, teamIdRed );
            q.setLong( 2, duoclassId );

            List list2 = q.list();
            if ( list2 != null && list2.size() > 0 )
            {
                return true;
            }

        return false;
        }
        catch ( Exception e )
        {
            log.fatal( "Can't isDoneFightRegardlessRedBlue: " + e.toString() );
            throw new JJWDataLayerException( e );
        }
    }

    private static String FIGHT_BY_ID = "from " + TABLE_DUO_FIGHT + " where " + ID + "=?";

    public DuoFight getFightByQuery( Long fightId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( FIGHT_BY_ID );
            q.setLong( 0, fightId );

            DuoFight fight = (DuoFight) q.list().get( 0 );
            DuoTeam fighterIdRed = (DuoTeam) getHibernateTemplate().get( DuoTeam.class, fight.getTeamIdRed() );
            DuoTeam fighterIdBlue = (DuoTeam) getHibernateTemplate().get( DuoTeam.class, fight.getTeamIdBlue() );
            fight.setDuoTeamRed( fighterIdRed );
            fight.setDuoTeamBlue( fighterIdBlue );
            return fight;
        }
        catch ( Exception e )
        {
            log.error( e.getMessage() );
            throw new JJWDataLayerException( e );

        }
    }
}
