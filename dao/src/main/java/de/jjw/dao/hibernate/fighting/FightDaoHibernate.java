/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightDaoHibernate.java
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

package de.jjw.dao.hibernate.fighting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import de.jjw.dao.fighting.FightDao;
import de.jjw.dao.hibernate.BaseDaoHibernate;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.dao.hibernate.admin.IAgeDatabaseConstants;
import de.jjw.dao.hibernate.admin.IUserDatabaseConstants;
import de.jjw.model.FastFight;
import de.jjw.model.User;
import de.jjw.model.admin.Age;
import de.jjw.model.admin.FightTimes;
import de.jjw.model.fighting.Fight;
import de.jjw.model.fighting.Fighter;
import de.jjw.model.fighting.Fightingclass;

public class FightDaoHibernate
    extends BaseDaoHibernate
    implements FightDao, IFightDatabaseConstants, IFighterDatabaseConstants, IAgeDatabaseConstants
{

    private static String FIGHT_BY_ID = "from " + TABLE_FIGHT + " where " + ID + "=?";

    private static String EXIST_FIGHT_IN_MAIN_ROUND =
       // "from " + TABLE_FIGHT + " where " + FIGHTER_ID_RED + "=? AND " + FIGHTER_ID_BLUE + "=? AND " + IFightDatabaseConstants.FIGHTINGCLASS_ID +"=?";
                    "Select id from " + SQL_TABLE_FIGHT + " where " + FIGHTER_ID_RED + "=? AND " + FIGHTER_ID_BLUE + "=? AND " 
                       + IFightDatabaseConstants.FIGHTINGCLASS +"=? AND " + MAIN_ROUND +"='Y'";

    private static String FIGHTS_BY_FIGHTER =
        "from " + TABLE_FIGHT + " where " + FIGHTER_ID_RED + "=? OR " + FIGHTER_ID_BLUE + "=?";

   
    
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

    public Fight getFight( Long fightId )
        throws JJWDataLayerException
    {
        try
        {
            Fight fight = (Fight) getHibernateTemplate().get( Fight.class, fightId );

            Fighter fighterIdRed = (Fighter) getHibernateTemplate().get( Fighter.class, fight.getFighterIdRed() );
            Fighter fighterIdBlue = (Fighter) getHibernateTemplate().get( Fighter.class, fight.getFighterIdBlue() );
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
    
    
   
    public Fight getFightByQuery( Long fightId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( FIGHT_BY_ID );
            q.setLong( 0, fightId);

            Fight fight = (Fight) q.list().get( 0 );
            Fighter fighterIdRed = (Fighter) getHibernateTemplate().get( Fighter.class, fight.getFighterIdRed() );
            Fighter fighterIdBlue = (Fighter) getHibernateTemplate().get( Fighter.class, fight.getFighterIdBlue() );
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

    public List<Fight> getFightsFromFighter( Fighter fighter )
    {
        Query q = getSession().createQuery( FIGHTS_BY_FIGHTER );
        q.setLong( 0, fighter.getId() );
        q.setLong( 1, fighter.getId() );

        return q.list();
    }

    // public void removeAllFightsForTheClass(Fightingclass fightingclass) {
    // // TODO Auto-generated method stub
    //        
    // }

    public void removeFight( Fight fight )
    {
        // TODO Auto-generated method stub

    }

    public void removeFight( Long fightId )
    {
        // TODO Auto-generated method stub

    }

    public void saveFight( Fight fight )
    {
        try
        {
            getHibernateTemplate().saveOrUpdate( fight );
        }
        catch ( Exception e )
        {
            log.fatal( "Can't save fight: " + e.toString() );
        }
    }

    public boolean isDoneFightRegardlessRedBlue( Long fightingclassId, Long fighterIdRed, Long fighterIdBlue ) throws  JJWDataLayerException
    {
        try
        {
            Query q = getSession().createSQLQuery( EXIST_FIGHT_IN_MAIN_ROUND );
            q.setLong( 0, fighterIdRed );
            q.setLong( 1, fighterIdBlue );
            q.setLong( 2, fightingclassId );

            List list = q.list();
            if ( list != null && list.size() > 0 )
            {
                return true;
            }
            
            q = getSession().createSQLQuery( EXIST_FIGHT_IN_MAIN_ROUND );
            q.setLong( 0, fighterIdBlue );
            q.setLong( 1, fighterIdRed );
            q.setLong( 2, fightingclassId );

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

    public List<Fight> getAllFightsForTheClass( Fightingclass fightingclass )
    {
        // TODO Auto-generated method stub
        return null;
    }

    public void removeAllFightsForTheClass( Fightingclass fightingclass )
    {
        // TODO Auto-generated method stub

    }

    /*  
     *        "select count(1) as fights,ac.username as Matte, age." +IAgeDatabaseConstants.AGE_SHORT+", "
            + " avg(if (a."+ MODIFICATION_ID+" = b."+ MODIFICATION_ID+",TIMESTAMPDIFF(SECOND,a."+ MODIFICATION_DATE+", b."+ MODIFICATION_DATE+"),null)) as totalFight, "
            + " avg(if (a."+ MODIFICATION_ID+" = b."+ MODIFICATION_ID+",TIMESTAMPDIFF(SECOND,a."+ MODIFICATION_DATE+", b."+ MODIFICATION_DATE+"),null)-b."+FIGHT_TIME_WITH_BREAKS+") as Pause, "
            + " avg(b."+FIGHT_TIME+") as FightTime,avg(b."+FIGHT_TIME_WITH_BREAKS+"-b."+FIGHT_TIME+") as DiffTimeBreak "
            + " from "
            + " (SELECT @r:=@r+1 rownum, f.* FROM (SELECT @r:=0) r, "+IFightDatabaseConstants.SQL_TABLE_FIGHT +" f where "+ WINNER_ID +" >=0 order by day(f."+ MODIFICATION_DATE+"),"+ MODIFICATION_ID+","+ MODIFICATION_DATE+" desc) a, "
            + " (SELECT @s:=@s+1 rownum2, f.* FROM (SELECT @s:=1) r, "+IFightDatabaseConstants.SQL_TABLE_FIGHT +" f where "+ WINNER_ID +" >=0 order by day(f."+ MODIFICATION_DATE+"),"+ MODIFICATION_ID+","+ MODIFICATION_DATE+" desc) b, "
            + IFighterDatabaseConstants.SQL_TABLE_FIGHTER +" f left join "
            + IUserDatabaseConstants.SQL_TABLE_ACCOUNT +" ac on f."+ MODIFICATION_ID +" = ac.id left join "
            + IAgeDatabaseConstants.SQL_TABLE_AGE +" on f."+ IFighterDatabaseConstants.AGE +" = age." +ID 
            + " where a.rownum = b.rownum2 and b."+ IFightDatabaseConstants.FIGHTER_ID_RED +" = f." + ID 
            + " group by b."+ MODIFICATION_ID+", f."+ IFighterDatabaseConstants.AGE 
            + " order by b."+ MODIFICATION_ID+", f."+ IFighterDatabaseConstants.AGE;
     */
    private static String SQL_FIGHT_TIMES =
        "select count(1) as fights,ac.username as Matte, age." + IAgeDatabaseConstants.AGE_SHORT + ", " + " avg(if (a."
            + MODIFICATION_ID + " = b." + MODIFICATION_ID + ",TIMESTAMPDIFF(SECOND,a." + MODIFICATION_DATE + ", b."
            + MODIFICATION_DATE + "),null)) as totalFight, " + " avg(if (a." + MODIFICATION_ID + " = b."
            + MODIFICATION_ID + ",TIMESTAMPDIFF(SECOND,a." + MODIFICATION_DATE + ", b." + MODIFICATION_DATE
            + "),null)-b." + FIGHT_TIME_WITH_BREAKS + ") as Pause, " + " avg(b." + FIGHT_TIME + ") as FightTime,avg(b."
            + FIGHT_TIME_WITH_BREAKS + "-b." + FIGHT_TIME + ") as DiffTimeBreak " + " from "
            + " (SELECT @r:=@r+1 rownum, f.* FROM (SELECT @r:=0) r, " + IFightDatabaseConstants.SQL_TABLE_FIGHT
            + " f where " + WINNER_ID + " >=0 order by day(f." + MODIFICATION_DATE + ")," + MODIFICATION_ID + ","
            + MODIFICATION_DATE + " desc) a, " + " (SELECT @s:=@s+1 rownum2, f.* FROM (SELECT @s:=1) r, "
            + IFightDatabaseConstants.SQL_TABLE_FIGHT + " f where " + WINNER_ID + " >=0 order by day(f."
            + MODIFICATION_DATE + ")," + MODIFICATION_ID + "," + MODIFICATION_DATE + " desc) b, "
            + IFighterDatabaseConstants.SQL_TABLE_FIGHTER + " f left join " + IUserDatabaseConstants.SQL_TABLE_ACCOUNT
            + " ac on f." + MODIFICATION_ID + " = ac.id left join " + IAgeDatabaseConstants.SQL_TABLE_AGE + " on f."
            + IFighterDatabaseConstants.AGE + " = age." + ID + " where a.rownum = b.rownum2 and b."
            + IFightDatabaseConstants.FIGHTER_ID_RED + " = f." + ID + " group by b." + MODIFICATION_ID + ", f."
            + IFighterDatabaseConstants.AGE + " order by b." + MODIFICATION_ID + ", f." + IFighterDatabaseConstants.AGE;

    private static String SQL_FIGHT_TIMES_2 = "from " + IFightDatabaseConstants.TABLE_FIGHT + " f Where f."
        + IFightDatabaseConstants.WINNER_ID + " >=0 order by f." + IFightDatabaseConstants.SAVE_TIME;

    private static String SQL_FIGHT_TIMES_3 =
        "Select ac.username as Matte, age.ageShort, b.fightTimeWithBreaks-b.fightTime) as DiffTimeBreak"
            + "fighter f left join account ac on f.modificationId = ac.id left join age on f.age = age.id";

    private static String SQL_FIGHT_TIMES_4 = " From User";

    // Select ac.username as Matte, age.ageShort,b.modificationDate,b.modificationid,
    // (b.fightTimeWithBreaks-b.fightTime) as DiffTimeBreak
    // from
    // fight_list b
    // left join fighter f on b.fighterIdRed = f.id
    // left join account ac on b.modificationId = ac.id
    // left join age on f.age = age.id

    public List<FightTimes> getFightTimes()
        throws JJWDataLayerException
    {
        List<FightTimes> ret = new ArrayList<FightTimes>();
        try
        {
            Query q = getSession().createQuery( SQL_FIGHT_TIMES_2 );
            Query q2 = getSession().createQuery( SQL_FIGHT_TIMES_4 );

            Map<Long, User> acountMap = new HashMap<Long, User>();
            for ( User item : (List<User>) q2.list() )
            {
                acountMap.put( item.getId(), item );
            }

            List<Fight> fl = (List<Fight>) q.list();
            HashMap<Long, Map<Long, List<FightTimes>>> bigMap = new HashMap<Long, Map<Long, List<FightTimes>>>();// long1
                                                                                                                 // =
                                                                                                                 // tatami,
                                                                                                                 // long2=age
            bigMap = putFightTimesInDatastructure( fl, acountMap );
            Map<Long, FightTimes> pauseMap = getTatamiPauseTime( fl );

            for ( Map<Long, List<FightTimes>> tatamiItem : bigMap.values() )
            {// tatami level

                List<FightTimes> tatamiList = new ArrayList<FightTimes>();

                for ( List<FightTimes> fightTimesItem : tatamiItem.values() )
                {// age Level
                    int fightTimeWithBreaks = 0;
                    int fightCount = 0;
                    String tatami = null;
                    String ageDescription = "";
                    int totalFightTime = 0;
                    int fightTime = 0;
                    int diffTimeBreak = 0;
                    tatami = fightTimesItem.get( 0 ).getTatami();
                    ageDescription = fightTimesItem.get( 0 ).getAgeDescription();
                    long tatamiAsId = fightTimesItem.get( 0 ).getTatamiAsId();

                    for ( FightTimes item : fightTimesItem )
                    {// fight level
                        fightCount++;
                        totalFightTime += item.getTotalFightTime();
                        fightTime += item.getFightTime();
                        diffTimeBreak += item.getDiffTimeBreak();
                        fightTimeWithBreaks = item.getFightTimeWithBreaks();
                    }
                    tatamiList.add( new FightTimes( fightCount, tatami, ageDescription, totalFightTime / fightCount, 0,
                                             fightTime / fightCount, diffTimeBreak / fightCount,
                                             fightTimeWithBreaks   / fightCount, tatamiAsId ) );
                }
                for ( FightTimes item : tatamiList )
                {
                    // set pause time and add to returnList
                    item.setPause( pauseMap.get( item.getTatamiAsId() ).getPause()
                        / pauseMap.get( item.getTatamiAsId() ).getFightCount() );
                    ret.add( item );
                }

            }
            return ret; // retList;
        }
        catch ( Exception e )
        {
            log.error( "can not getFightTimes", e );
            throw new JJWDataLayerException( e );
        }
    }

    private Map<Long, FightTimes> getTatamiPauseTime( List<Fight> fl )
    {

        Map<Long, FightTimes> pauseMap = new HashMap<Long, FightTimes>();

        for ( Fight fight : fl )
        {

            if ( pauseMap.containsKey( fight.getModificationId() ) )
            {

                int pause =
                    Math.abs( (int) ( ( pauseMap.get( fight.getModificationId() ).getSaveTime().getTime() - fight.getSaveTime().getTime() ) / 1000 ) )
                        - fight.getFightTimeWithBreaks();

                if ( pause > 3600 )
                    continue;
                pauseMap.put( fight.getModificationId(),
                              new FightTimes( pauseMap.get( fight.getModificationId() ).getFightCount() + 1,
                                              pauseMap.get( fight.getModificationId() ).getPause() + pause,
                                              fight.getSaveTime() ) );
            }
            else
            {
                pauseMap.put( fight.getModificationId(), new FightTimes( 1, 0, fight.getSaveTime() ) );

            }
        }
        return pauseMap;
    }

    /**
     * @param fl
     * @param acountMap
     * @param bigMap
     */
    private HashMap<Long, Map<Long, List<FightTimes>>> putFightTimesInDatastructure( List<Fight> fl,
                                                                                     Map<Long, User> acountMap )
    {
        HashMap<Long, Map<Long, List<FightTimes>>> bigMap = new HashMap<Long, Map<Long, List<FightTimes>>>();
        Fight item;
        List list = new ArrayList<FightTimes>();
        for ( int i = 0; i < fl.size(); i++ )
        {
            item = fl.get( i );
            if ( bigMap.containsKey( item.getModificationId() ) )
            {
                // refers to tatami
                if ( bigMap.get( item.getModificationId() ).containsKey( item.getFightingclass().getAge().getId() ) )
                {
                    // refers to age on tatami

                    list = bigMap.get( item.getModificationId() ).get( item.getFightingclass().getAge().getId() );
                    list.add( new FightTimes( 0, acountMap.get( item.getModificationId() ).getUsername(),
                                              item.getFightingclass().getAge().getDescription(),
                                              item.getOverallFightTime(), 0, item.getFightTime(),
                                              item.getFightTimeWithBreaks() - item.getFightTime(),
                                              item.getModificationDate(), item.getFightTimeWithBreaks(),
                                              item.getModificationId() ) );
                    bigMap.get( item.getModificationId() ).put( item.getFightingclass().getAge().getId(), list );
                }
                else
                {
                    list = new ArrayList<FightTimes>();
                    list.add( new FightTimes( 0, acountMap.get( item.getModificationId() ).getUsername(),
                                              item.getFightingclass().getAge().getDescription(),
                                              item.getOverallFightTime(), 0, item.getFightTime(),
                                              item.getFightTimeWithBreaks() - item.getFightTime(),
                                              item.getModificationDate(), item.getFightTimeWithBreaks(),
                                              item.getModificationId() ) );
                    bigMap.get( item.getModificationId() ).put( item.getFightingclass().getAge().getId(), list );

                }
            }
            else
            {
                bigMap.put( item.getModificationId(), new HashMap() );
                list = new ArrayList<FightTimes>();
                list.add( new FightTimes( 0, acountMap.get( item.getModificationId() ).getUsername(),
                                          item.getFightingclass().getAge().getDescription(),
                                          item.getOverallFightTime(), 0, item.getFightTime(),
                                          item.getFightTimeWithBreaks() - item.getFightTime(),
                                          item.getModificationDate(), item.getFightTimeWithBreaks(),
                                          item.getModificationId() ) );
                bigMap.get( item.getModificationId() ).put( item.getFightingclass().getAge().getId(), list );

            }
        }
        return bigMap;
    }

    private static String AGE_ALL = "from " + TABLE_AGE + " a order by a." + ORDER_NUMBER;

    private static String FAST_FIGHTER = "Select f,fl from " + TABLE_FIGHTER + " f " + "left join fetch f." + AGE
        + " left join fetch f." + TEAM + " t left join fetch t." + REGION + " left join fetch t." + COUNTRY
        + " , "
        //
        + TABLE_FIGHT
        + " fl where f.id =  fl." + WINNER_ID + " and fl." + WINNER_ID + ">0 and fl." + FIGHT_TIME + ">0 and fl."
        + FUSENGACHI + " =0 and fl." + KIKENGACHI + "=0 and f." + SEX + "=? and f." + AGE + "=?" + " order by fl."
 + FIGHT_TIME;

    public Map<Integer, List<FastFight>> getFastestFights()
        throws JJWDataLayerException
    {
        try
        {
            Map<Integer, List<FastFight>> ret = new HashMap<Integer, List<FastFight>>();
            List<Age> ageList = (List<Age>) getSession().createQuery( AGE_ALL ).list();
            Fighter fighter = null;
            Fight fight = null;

            Query q = null;
            List<FastFight> fightList = null;
            FastFight fastFight = null;
            for ( Age item : ageList )
            {
                fightList = new ArrayList<FastFight>( 21 );
                // male
                q = getSession().createQuery( FAST_FIGHTER );
                q.setInteger( 0, 1 );
                q.setLong( 1, item.getId() );
                q.setMaxResults( 10 );
                List<Object> resultList = q.list();
                for ( Object o : resultList )
                {
                    fighter = (Fighter) ( (Object[]) o )[0];
                    fight = (Fight) ( (Object[]) o )[1];
                    fastFight = new FastFight();
                    fastFight.setName( fighter.getName() );
                    fastFight.setFirstname( fighter.getFirstname() );
                    fastFight.setAge( fighter.getAge().getDescription() );
                    fastFight.setCountry( fighter.getTeam().getCountry().getCountryShort() );
                    fastFight.setRegion( fighter.getTeam().getRegion().getRegionShort() );
                    fastFight.setTeam( fighter.getTeam().getTeamName() );
                    fastFight.setSex( 1 );
                    fastFight.setFightTime( fight.getFightTime() );
                    fightList.add( fastFight );
                }

                // female
                q = getSession().createQuery( FAST_FIGHTER );
                q.setInteger( 0, 2 );
                q.setLong( 1, item.getId() );
                q.setMaxResults( 10 );
                resultList = q.list();
                for ( Object o : resultList )
                {
                    fighter = (Fighter) ( (Object[]) o )[0];
                    fight = (Fight) ( (Object[]) o )[1];
                    fastFight = new FastFight();
                    fastFight.setName( fighter.getName() );
                    fastFight.setFirstname( fighter.getFirstname() );
                    fastFight.setAge( fighter.getAge().getDescription() );
                    fastFight.setCountry( fighter.getTeam().getCountry().getCountryShort() );
                    fastFight.setRegion( fighter.getTeam().getRegion().getRegionShort() );
                    fastFight.setTeam( fighter.getTeam().getTeamName() );
                    fastFight.setSex( 2 );
                    fastFight.setFightTime( fight.getFightTime() );
                    fightList.add( fastFight );
                }
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
    
    
    private static String ALL_FINISHED_FIGHTS =
                    "from " + TABLE_FIGHT + " where " + FIGHTER_ID_RED + ">0 and " + FIGHTER_ID_BLUE + ">0 and " + WINNER_ID +" >0";
    private static String ALL_FIGHTER =
                    "from " + TABLE_FIGHTER;
    
    public Map<Fighter,List<Fight>> getFightsFromFighter() throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( ALL_FINISHED_FIGHTS );
            List<Fight> fightList = (List<Fight>) q.list();
            Map<Long, List<Fight>> myMap = new HashMap<Long, List<Fight>>();
            List<Fight> newList = new ArrayList<Fight>();
            Map<Fighter, List<Fight>> retMap = new HashMap<Fighter, List<Fight>>();
            Fighter fighterRed;
            Fighter fighterBlue;

            for ( Fight item : fightList )
            {                
                fighterRed = (Fighter) getHibernateTemplate().get( Fighter.class, item.getFighterIdRed() );
                fighterBlue = (Fighter) getHibernateTemplate().get( Fighter.class, item.getFighterIdBlue() );
                item.setFighterRed( fighterRed );
                item.setFighterBlue( fighterBlue );
                
                if ( myMap.containsKey( item.getFighterIdRed() ) )
                    newList = myMap.get( item.getFighterIdRed() );
                else
                    newList = new ArrayList<Fight>();
                newList.add( item );
                myMap.put( item.getFighterIdRed(), newList );

                if ( myMap.containsKey( item.getFighterIdBlue() ) )
                    newList = myMap.get( item.getFighterIdBlue() );
                else
                    newList = new ArrayList<Fight>();
                newList.add( item );
                myMap.put( item.getFighterIdRed(), newList );
            }

            Query q2 = getSession().createQuery( ALL_FIGHTER );
            List<Fighter> fighterList = (List<Fighter>) q2.list();
            for ( Fighter item : fighterList )
            {
                if ( myMap.containsKey( item.getId() ) )
                    retMap.put( item, myMap.get( item.getId() ) );
            }

            return retMap;
        }
        catch ( Exception e )
        {
            log.error( e.getMessage() );
            throw new JJWDataLayerException( e );
        }
    }
    
    private static String FINISHED_FIGHTS_BY_FIGHTER =
                    "from " + TABLE_FIGHT + " where " + FIGHTER_ID_RED + "=? OR " + FIGHTER_ID_BLUE + "=? and " + WINNER_ID +" >0";
    
    public Map<Fighter,List<Fight>>  getFightsFromOneFighter( Fighter fighter ) throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( FINISHED_FIGHTS_BY_FIGHTER );
            q.setLong( 0, fighter.getId() );
            q.setLong( 1, fighter.getId() );
            List<Fight> fightList = (List<Fight>) q.list();
            Map<Fighter, List<Fight>> retMap = new HashMap<Fighter, List<Fight>>();
            Fighter myFighter = (Fighter) getHibernateTemplate().get( Fighter.class, fighter.getId() );
            Fighter fighterRed;
            Fighter fighterBlue;
            
            for ( Fight item : fightList )
            {                
                fighterRed = (Fighter) getHibernateTemplate().get( Fighter.class, item.getFighterIdRed() );
                fighterBlue = (Fighter) getHibernateTemplate().get( Fighter.class, item.getFighterIdBlue() );
                item.setFighterRed( fighterRed );
                item.setFighterBlue( fighterBlue );                
            }

            retMap.put( myFighter, fightList );

            return retMap;
        }
        catch ( Exception e )
        {
            log.error( e.getMessage() );
            throw new JJWDataLayerException( e );
        }
    }
    
    public Map<Fighter,List<Fight>>  getFightForVisualize( long fightId ) throws JJWDataLayerException
    {
        try
        {

            Fight myFight = (Fight) getHibernateTemplate().get( Fight.class, fightId );
            List<Fight> fightList = new ArrayList<Fight>();
            
            Map<Fighter, List<Fight>> retMap = new HashMap<Fighter, List<Fight>>();
            
            Fighter fighterRed;
            Fighter fighterBlue;

            fighterRed = (Fighter) getHibernateTemplate().get( Fighter.class, myFight.getFighterIdRed() );
            fighterBlue = (Fighter) getHibernateTemplate().get( Fighter.class, myFight.getFighterIdBlue() );
            myFight.setFighterRed( fighterRed );
            myFight.setFighterBlue( fighterBlue );
            fightList.add( myFight );

            retMap.put( fighterRed, fightList );

            return retMap;
        }
        catch ( Exception e )
        {
            log.error( e.getMessage() );
            throw new JJWDataLayerException( e );
        }
    }
    
}
