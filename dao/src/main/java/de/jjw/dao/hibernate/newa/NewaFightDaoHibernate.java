/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaFightDaoHibernate.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import de.jjw.dao.hibernate.BaseDaoHibernate;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.dao.hibernate.admin.IAgeDatabaseConstants;
import de.jjw.dao.hibernate.admin.IUserDatabaseConstants;
import de.jjw.dao.newa.NewaFightDao;
import de.jjw.model.FastFight;
import de.jjw.model.User;
import de.jjw.model.admin.Age;
import de.jjw.model.admin.FightTimes;
import de.jjw.model.newa.NewaFight;
import de.jjw.model.newa.NewaFighter;
import de.jjw.model.newa.Newaclass;

public class NewaFightDaoHibernate
    extends BaseDaoHibernate
    implements NewaFightDao, INewaFightDatabaseConstants, INewaFighterDatabaseConstants, IAgeDatabaseConstants
{

    private static String FIGHT_BY_ID = "from " + TABLE_FIGHT + " where " + ID + "=?";

       private static String EXIST_FIGHT_IN_MAIN_ROUND =
                    // "from " + TABLE_FIGHT + " where " + FIGHTER_ID_RED + "=? AND " + FIGHTER_ID_BLUE + "=? AND " + IFightDatabaseConstants.FIGHTINGCLASS_ID +"=?";
                                 "Select id from " + SQL_TABLE_FIGHT + " where " + FIGHTER_ID_RED + "=? AND " + FIGHTER_ID_BLUE + "=? AND " 
                                    + INewaFightDatabaseConstants.NEWACLASS +"=? AND " + MAIN_ROUND +"='Y'";
    
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

    public NewaFight getFight( Long fightId )
        throws JJWDataLayerException
    {
        try
        {
            NewaFight fight = (NewaFight) getHibernateTemplate().get( NewaFight.class, fightId );

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

    public List<NewaFight> getFightsFromFighter( NewaFighter fighter )
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

    public void removeFight( NewaFight fight )
    {
        // TODO Auto-generated method stub

    }

    public void removeFight( Long fightId )
    {
        // TODO Auto-generated method stub

    }

    public void saveFight( NewaFight fight )
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


    public boolean isDoneFightRegardlessRedBlue( Long newaclassId, Long fighterIdRed, Long fighterIdBlue ) throws  JJWDataLayerException
    {
        try
        {
            Query q = getSession().createSQLQuery( EXIST_FIGHT_IN_MAIN_ROUND );
            q.setLong( 0, fighterIdRed );
            q.setLong( 1, fighterIdBlue );
            q.setLong( 2, newaclassId );

            List list = q.list();
            if ( list != null && list.size() > 0 )
            {
                return true;
            }
            
            q = getSession().createSQLQuery( EXIST_FIGHT_IN_MAIN_ROUND );
            q.setLong( 0, fighterIdBlue );
            q.setLong( 1, fighterIdRed );
            q.setLong( 2, newaclassId );

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

    public List<NewaFight> getAllFightsForTheClass( Newaclass fightingclass )
    {
        // TODO Auto-generated method stub
        return null;
    }

    public void removeAllFightsForTheClass( Newaclass fightingclass )
    {
        // TODO Auto-generated method stub

    }

    /*  
     *        "select count(1) as fights,ac.username as Matte, age." +IAgeDatabaseConstants.AGE_SHORT+", "
            + " avg(if (a."+ MODIFICATION_ID+" = b."+ MODIFICATION_ID+",TIMESTAMPDIFF(SECOND,a."+ MODIFICATION_DATE+", b."+ MODIFICATION_DATE+"),null)) as totalFight, "
            + " avg(if (a."+ MODIFICATION_ID+" = b."+ MODIFICATION_ID+",TIMESTAMPDIFF(SECOND,a."+ MODIFICATION_DATE+", b."+ MODIFICATION_DATE+"),null)-b."+FIGHT_TIME_WITH_BREAKS+") as Pause, "
            + " avg(b."+FIGHT_TIME+") as FightTime,avg(b."+FIGHT_TIME_WITH_BREAKS+"-b."+FIGHT_TIME+") as DiffTimeBreak "
            + " from "
            + " (SELECT @r:=@r+1 rownum, f.* FROM (SELECT @r:=0) r, "+INewaFightDatabaseConstants.SQL_TABLE_FIGHT +" f where "+ WINNER_ID +" >=0 order by day(f."+ MODIFICATION_DATE+"),"+ MODIFICATION_ID+","+ MODIFICATION_DATE+" desc) a, "
            + " (SELECT @s:=@s+1 rownum2, f.* FROM (SELECT @s:=1) r, "+INewaFightDatabaseConstants.SQL_TABLE_FIGHT +" f where "+ WINNER_ID +" >=0 order by day(f."+ MODIFICATION_DATE+"),"+ MODIFICATION_ID+","+ MODIFICATION_DATE+" desc) b, "
            + INewaFighterDatabaseConstants.SQL_TABLE_FIGHTER +" f left join "
            + IUserDatabaseConstants.SQL_TABLE_ACCOUNT +" ac on f."+ MODIFICATION_ID +" = ac.id left join "
            + IAgeDatabaseConstants.SQL_TABLE_AGE +" on f."+ INewaFighterDatabaseConstants.AGE +" = age." +ID 
            + " where a.rownum = b.rownum2 and b."+ INewaFightDatabaseConstants.FIGHTER_ID_RED +" = f." + ID 
            + " group by b."+ MODIFICATION_ID+", f."+ INewaFighterDatabaseConstants.AGE 
            + " order by b."+ MODIFICATION_ID+", f."+ INewaFighterDatabaseConstants.AGE;
     */
    private static String SQL_FIGHT_TIMES =
        "select count(1) as fights,ac.username as Matte, age." + IAgeDatabaseConstants.AGE_SHORT + ", " + " avg(if (a."
            + MODIFICATION_ID + " = b." + MODIFICATION_ID + ",TIMESTAMPDIFF(SECOND,a." + MODIFICATION_DATE + ", b."
            + MODIFICATION_DATE + "),null)) as totalFight, " + " avg(if (a." + MODIFICATION_ID + " = b."
            + MODIFICATION_ID + ",TIMESTAMPDIFF(SECOND,a." + MODIFICATION_DATE + ", b." + MODIFICATION_DATE
            + "),null)-b." + FIGHT_TIME_WITH_BREAKS + ") as Pause, " + " avg(b." + FIGHT_TIME + ") as FightTime,avg(b."
            + FIGHT_TIME_WITH_BREAKS + "-b." + FIGHT_TIME + ") as DiffTimeBreak " + " from "
        + " (SELECT @r:=@r+1 rownum, f.* FROM (SELECT @r:=0) r, " + INewaFightDatabaseConstants.SQL_TABLE_FIGHT
            + " f where " + WINNER_ID + " >=0 order by day(f." + MODIFICATION_DATE + ")," + MODIFICATION_ID + ","
            + MODIFICATION_DATE + " desc) a, " + " (SELECT @s:=@s+1 rownum2, f.* FROM (SELECT @s:=1) r, "
        + INewaFightDatabaseConstants.SQL_TABLE_FIGHT + " f where " + WINNER_ID + " >=0 order by day(f."
            + MODIFICATION_DATE + ")," + MODIFICATION_ID + "," + MODIFICATION_DATE + " desc) b, "
        + INewaFighterDatabaseConstants.SQL_TABLE_FIGHTER + " f left join " + IUserDatabaseConstants.SQL_TABLE_ACCOUNT
            + " ac on f." + MODIFICATION_ID + " = ac.id left join " + IAgeDatabaseConstants.SQL_TABLE_AGE + " on f."
        + INewaFighterDatabaseConstants.AGE + " = age." + ID + " where a.rownum = b.rownum2 and b."
        + INewaFightDatabaseConstants.FIGHTER_ID_RED + " = f." + ID + " group by b." + MODIFICATION_ID + ", f."
        + INewaFighterDatabaseConstants.AGE + " order by b." + MODIFICATION_ID + ", f."
        + INewaFighterDatabaseConstants.AGE;

    private static String SQL_FIGHT_TIMES_2 = "from " + INewaFightDatabaseConstants.TABLE_FIGHT + " f Where f."
        + INewaFightDatabaseConstants.WINNER_ID + " >=0 order by f." + INewaFightDatabaseConstants.SAVE_TIME;



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

            List<NewaFight> fl = (List<NewaFight>) q.list();
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
                                                    fightTimeWithBreaks / fightCount, tatamiAsId ) );
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

    private Map<Long, FightTimes> getTatamiPauseTime( List<NewaFight> fl )
    {

        Map<Long, FightTimes> pauseMap = new HashMap<Long, FightTimes>();

        for ( NewaFight fight : fl )
        {

            if ( pauseMap.containsKey( fight.getModificationId() ) )
            {

                int pause =
                    Math.abs( (int) ( ( pauseMap.get( fight.getModificationId() ).getSaveTime().getTime() - fight.getSaveTime().getTime() ) / 1000 ) )
                        - fight.getFightTimeWithBreaks();

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
    private HashMap<Long, Map<Long, List<FightTimes>>> putFightTimesInDatastructure( List<NewaFight> fl,
                                                                                     Map<Long, User> acountMap )
    {
        HashMap<Long, Map<Long, List<FightTimes>>> bigMap = new HashMap<Long, Map<Long, List<FightTimes>>>();
        NewaFight item;
        List list = new ArrayList<FightTimes>();
        for ( int i = 0; i < fl.size(); i++ )
        {
            item = fl.get( i );
            if ( bigMap.containsKey( item.getModificationId() ) )
            {
                // refers to tatami
                if ( bigMap.get( item.getModificationId() ).containsKey( item.getNewaclass().getAge().getId() ) )
                {
                    // refers to age on tatami

                    list = bigMap.get( item.getModificationId() ).get( item.getNewaclass().getAge().getId() );
                    list.add( new FightTimes( 0, acountMap.get( item.getModificationId() ).getUsername(),
                                              item.getNewaclass().getAge().getDescription(),
                                              item.getOverallFightTime(), 0, item.getFightTime(),
                                              item.getFightTimeWithBreaks() - item.getFightTime(),
                                              item.getModificationDate(), item.getFightTimeWithBreaks(),
                                              item.getModificationId() ) );
                    bigMap.get( item.getModificationId() ).put( item.getNewaclass().getAge().getId(), list );
                }
                else
                {
                    list = new ArrayList<FightTimes>();
                    list.add( new FightTimes( 0, acountMap.get( item.getModificationId() ).getUsername(),
                                              item.getNewaclass().getAge().getDescription(),
                                              item.getOverallFightTime(), 0, item.getFightTime(),
                                              item.getFightTimeWithBreaks() - item.getFightTime(),
                                              item.getModificationDate(), item.getFightTimeWithBreaks(),
                                              item.getModificationId() ) );
                    bigMap.get( item.getModificationId() ).put( item.getNewaclass().getAge().getId(), list );

                }
            }
            else
            {
                bigMap.put( item.getModificationId(), new HashMap() );
                list = new ArrayList<FightTimes>();
                list.add( new FightTimes( 0, acountMap.get( item.getModificationId() ).getUsername(),
                                          item.getNewaclass().getAge().getDescription(),
                                          item.getOverallFightTime(), 0, item.getFightTime(),
                                          item.getFightTimeWithBreaks() - item.getFightTime(),
                                          item.getModificationDate(), item.getFightTimeWithBreaks(),
                                          item.getModificationId() ) );
                bigMap.get( item.getModificationId() ).put( item.getNewaclass().getAge().getId(), list );

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
            NewaFighter fighter = null;
            NewaFight fight = null;

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
                    fighter = (NewaFighter) ( (Object[]) o )[0];
                    fight = (NewaFight) ( (Object[]) o )[1];
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
                    fighter = (NewaFighter) ( (Object[]) o )[0];
                    fight = (NewaFight) ( (Object[]) o )[1];
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
    
    public NewaFight getFightByQuery( Long fightId )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createQuery( FIGHT_BY_ID );
            q.setLong( 0, fightId );

            NewaFight fight = (NewaFight) q.list().get( 0 );
            NewaFighter fighterIdRed = (NewaFighter) getHibernateTemplate().get( NewaFighter.class, fight.getFighterIdRed() );
            NewaFighter fighterIdBlue = (NewaFighter) getHibernateTemplate().get( NewaFighter.class, fight.getFighterIdBlue() );
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
}
