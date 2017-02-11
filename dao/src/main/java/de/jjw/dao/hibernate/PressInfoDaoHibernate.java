/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : PressInfoDaoHibernate.java
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
import java.util.List;

import org.hibernate.Query;

import de.jjw.dao.PressInfoDao;
import de.jjw.dao.hibernate.admin.IAgeDatabaseConstants;
import de.jjw.dao.hibernate.duo.IDuoFightDatabaseConstants;
import de.jjw.dao.hibernate.duo.IDuoTeamDatabaseConstants;
import de.jjw.dao.hibernate.duo.IDuoclassDatabaseConstants;
import de.jjw.dao.hibernate.fighting.IFightDatabaseConstants;
import de.jjw.dao.hibernate.fighting.IFighterDatabaseConstants;
import de.jjw.dao.hibernate.fighting.IWeightclassDatabaseConstants;
import de.jjw.dao.hibernate.newa.INewaFightDatabaseConstants;
import de.jjw.dao.hibernate.newa.INewaFighterDatabaseConstants;
import de.jjw.dao.hibernate.newa.INewaWeightclassDatabaseConstants;
import de.jjw.model.LabelValue;
import de.jjw.model.LabelValueList;
import de.jjw.model.PressInfo;

public class PressInfoDaoHibernate
    extends BaseDaoHibernate
    implements PressInfoDao
{

    private static String SQL_PARTICIPANTS_AND_FIGHTS =
        "Select (Select count(1) from " + IFighterDatabaseConstants.SQL_TABLE_FIGHTER + " where "
            + IFighterDatabaseConstants.READY + " = '1' " + "), (Select count(1) from "
            + IDuoTeamDatabaseConstants.SQL_TABLE_DUOTEAM + " where " + IDuoTeamDatabaseConstants.READY + " = '1' "
            + "), (Select count(1) from " + IFightDatabaseConstants.SQL_TABLE_FIGHT + "), (Select count(1) from "
            + IDuoFightDatabaseConstants.SQL_TABLE_DUO_FIGHT + "), "
            
            + "(Select count(1) from " + INewaFighterDatabaseConstants.SQL_TABLE_FIGHTER + " where " + INewaFighterDatabaseConstants.READY + " = '1' " + ") ," 
            + "(Select count(1) from " + INewaFightDatabaseConstants.SQL_TABLE_FIGHT + "),"
            
            + "(Select count(1) from " + IFightDatabaseConstants.SQL_TABLE_FIGHT + " where " + IFightDatabaseConstants.WINNER_ID + "< 0),"
            + "(Select count(1) from " + IDuoFightDatabaseConstants.SQL_TABLE_DUO_FIGHT + " where " + IDuoFightDatabaseConstants.WINNER_ID + "< 0),"
            + "(Select count(1) from " + INewaFightDatabaseConstants.SQL_TABLE_FIGHT + " where " + INewaFightDatabaseConstants.WINNER_ID + "< 0)"
            ;
//(Select count(1) from  fight_list where winnerId < 0 and fightingclass in (select id from weightclass w where w.age=a.id))
    
    private static String SQL_PARTICIPANT_PER_AGE_FIGHTER =
        "select a." + IAgeDatabaseConstants.DESCRIPTION + " , count(1) as value, "
            + " (Select count(1) from " + IFightDatabaseConstants.SQL_TABLE_FIGHT + " fl  where " + IFightDatabaseConstants.WINNER_ID + "< 0 AND "
                + IFightDatabaseConstants.FIGHTINGCLASS + " in"
                    + " (select "+ IFighterDatabaseConstants.ID+" From "+IWeightclassDatabaseConstants.SQL_TABLE_WEIGHTCLASS+" w where w."
                        + IWeightclassDatabaseConstants.AGE +" = a."+IAgeDatabaseConstants.ID
                        +")"                                            
                + ") as openFights"
            + " from "
            + IFighterDatabaseConstants.SQL_TABLE_FIGHTER + " f left join " + IAgeDatabaseConstants.SQL_TABLE_AGE
            + " a on f." + IFighterDatabaseConstants.AGE + " = a." + IAgeDatabaseConstants.ID + " where f."
            + IFighterDatabaseConstants.READY + " = '1' " + " group by a." + IAgeDatabaseConstants.ID;

    private static String SQL_PARTICIPANT_PER_AGE_DUO =
        "select a." + IAgeDatabaseConstants.DESCRIPTION + " , count(1) as value, "
            + " (Select count(1) from " + IDuoFightDatabaseConstants.SQL_TABLE_DUO_FIGHT+ " fl  where " + IDuoFightDatabaseConstants.WINNER_ID + "< 0 AND "
                + IDuoFightDatabaseConstants.DUOCLASS + " in"
                    + " (select "+ IDuoTeamDatabaseConstants.ID+" From "+IDuoclassDatabaseConstants.SQL_TABLE_DUOCLASS+" w where w."
                        + IDuoclassDatabaseConstants.AGE +" = a."+IAgeDatabaseConstants.ID
                        +")"                                            
                + ") as openFights"
        
            + " from "
            + IDuoTeamDatabaseConstants.SQL_TABLE_DUOTEAM + " f left join " + IAgeDatabaseConstants.SQL_TABLE_AGE
            + " a on f." + IDuoTeamDatabaseConstants.AGE + " = a." + IAgeDatabaseConstants.ID + " where f."
            + IDuoTeamDatabaseConstants.READY + " = '1' " + " group by a." + IAgeDatabaseConstants.ID;
    
    private static String SQL_PARTICIPANT_PER_AGE_NEWAFIGHTER =
         "select a." + IAgeDatabaseConstants.DESCRIPTION + " , count(1) as value, "
            + " (Select count(1) from " + INewaFightDatabaseConstants.SQL_TABLE_FIGHT + " fl  where " + INewaFightDatabaseConstants.WINNER_ID + "< 0 AND "
                + INewaFightDatabaseConstants.NEWACLASS + " in"
                    + " (select "+ INewaFighterDatabaseConstants.ID+" From "+INewaWeightclassDatabaseConstants.SQL_TABLE_NEWACLASS+" w where w."
                        + INewaWeightclassDatabaseConstants.AGE +" = a."+IAgeDatabaseConstants.ID
                        +")"                                            
                + ") as openFights"
         
         
             + " from "
            + INewaFighterDatabaseConstants.SQL_TABLE_FIGHTER + " f left join " + IAgeDatabaseConstants.SQL_TABLE_AGE
            + " a on f." + INewaFighterDatabaseConstants.AGE + " = a." + IAgeDatabaseConstants.ID + " where f."
            + INewaFighterDatabaseConstants.READY + " = '1' " + " group by a." + IAgeDatabaseConstants.ID;

    private static String SQL_PARTICIPANTS_TEAM_REGION_COUNTRY =
        "select count(distinct(t."
            + ITeamDatabaseConstants.TEAM_REGION
            + ")) as regionValue, count(distinct(t."
            + ITeamDatabaseConstants.ID
            + ")) as teamValue,count(distinct(t."
            + ITeamDatabaseConstants.TEAM_COUNTRY
            + ")) as countryValue from(" //
            + "select t." + ITeamDatabaseConstants.ID + " , t." + ITeamDatabaseConstants.TEAM_REGION + " , t."
            + ITeamDatabaseConstants.TEAM_COUNTRY + " from " + IFighterDatabaseConstants.SQL_TABLE_FIGHTER
            + " f left join " + ITeamDatabaseConstants.SQL_TABLE_TEAM + " t on f."
            + IFighterDatabaseConstants.TEAM
            + " = t."
            + ITeamDatabaseConstants.ID
            + " where f." //
            + IFighterDatabaseConstants.READY
            + " = '1' "

            + " UNION " //
            + "select t." + ITeamDatabaseConstants.ID + " , t." + ITeamDatabaseConstants.TEAM_REGION + " , t."
            + ITeamDatabaseConstants.TEAM_COUNTRY + " from " + IDuoTeamDatabaseConstants.SQL_TABLE_DUOTEAM
            + " f left join " + ITeamDatabaseConstants.SQL_TABLE_TEAM + " t on f." + IDuoTeamDatabaseConstants.TEAM
            + " = t." + ITeamDatabaseConstants.ID //
            + " where f." + IDuoTeamDatabaseConstants.READY + " = '1' " //
        // newa
        + " UNION " //
        + "select t." + ITeamDatabaseConstants.ID + " , t." + ITeamDatabaseConstants.TEAM_REGION + " , t."
        + ITeamDatabaseConstants.TEAM_COUNTRY + " from " + INewaFighterDatabaseConstants.SQL_TABLE_FIGHTER
        + " f left join " + ITeamDatabaseConstants.SQL_TABLE_TEAM + " t on f." + INewaFighterDatabaseConstants.TEAM
        + " = t." + ITeamDatabaseConstants.ID //
        + " where f." + INewaFighterDatabaseConstants.READY + " = '1' " //

            + ") as t";

    private static String SQL_EVENT_INFOS =
        "select " + IConfigDatabaseConstants.EVENT_NAME + " , " + IConfigDatabaseConstants.EVENT_LOCATION + ", "
            + IConfigDatabaseConstants.EVENT_DATE + " from " + IConfigDatabaseConstants.SQL_TABLE_CONFIG;

    @Override
    public PressInfo getPressInfo()
        throws JJWDataLayerException
    {
        try
        {
            PressInfo ret = new PressInfo();
            Query q = getSession().createSQLQuery( SQL_PARTICIPANTS_TEAM_REGION_COUNTRY );
            List list = q.list();

            ret.setEventParticipansRegions( ( (BigInteger) ( (Object[]) list.get( 0 ) )[0] ).intValue() );
            ret.setEventParticipansTeams( ( (BigInteger) ( (Object[]) list.get( 0 ) )[1] ).intValue() );
            ret.setEventParticipansCountries( ( (BigInteger) ( (Object[]) list.get( 0 ) )[2] ).intValue() );

            q = getSession().createSQLQuery( SQL_PARTICIPANTS_AND_FIGHTS );
            list = q.list();

            ret.setEventParticipansFighter( ( (BigInteger) ( (Object[]) list.get( 0 ) )[0] ).intValue() );
            ret.setEventParticipansDuoTeams( ( (BigInteger) ( (Object[]) list.get( 0 ) )[1] ).intValue() );
            ret.setEventParticipansNewaFighter( ( (BigInteger) ( (Object[]) list.get( 0 ) )[4] ).intValue() );
            ret.setEventParticipans( ret.getEventParticipansFighter() + 2 * ret.getEventParticipansDuoTeams()
                + ret.getEventParticipansNewaFighter() );

            ret.setEventFightsFighting( ( (BigInteger) ( (Object[]) list.get( 0 ) )[2] ).intValue() );
            ret.setEventFightsDuo( ( (BigInteger) ( (Object[]) list.get( 0 ) )[3] ).intValue() );
            ret.setEventFightsNewa( ( (BigInteger) ( (Object[]) list.get( 0 ) )[5] ).intValue() );
            ret.setEventFights( ret.getEventFightsFighting() + ret.getEventFightsDuo() + ret.getEventFightsNewa() );

            ret.setEventFightsFightingOpen( ( (BigInteger) ( (Object[]) list.get( 0 ) )[6] ).intValue() );
            ret.setEventFightsDuoOpen( ( (BigInteger) ( (Object[]) list.get( 0 ) )[7] ).intValue() );
            ret.setEventFightsNewaOpen( ( (BigInteger) ( (Object[]) list.get( 0 ) )[8] ).intValue() );

            q = getSession().createSQLQuery( SQL_PARTICIPANT_PER_AGE_FIGHTER );
            list = q.list();
            Object[] hlp;

            List<LabelValueList> participantslist = new ArrayList<LabelValueList>();

            List<String> listValues= null;
            
            for ( Object item : list )
            {
                hlp = (Object[]) item;
                listValues= new ArrayList<String>();
                listValues.add(String.valueOf( ( (BigInteger) hlp[1] ).intValue() ));
                listValues.add(String.valueOf( ( (BigInteger) hlp[2] ).intValue() ));
                participantslist.add( new LabelValueList( (String) hlp[0],listValues ));
            }
            ret.setEventParticipansPerAgeFighter( participantslist );

            //duo
            q = getSession().createSQLQuery( SQL_PARTICIPANT_PER_AGE_DUO );
            list = q.list();

            participantslist = new ArrayList<LabelValueList>();

            for ( Object item : list )
            {
                hlp = (Object[]) item;
                listValues= new ArrayList<String>();
                listValues.add(String.valueOf( ( (BigInteger) hlp[1] ).intValue() ));
                listValues.add(String.valueOf( ( (BigInteger) hlp[2] ).intValue() ));
                participantslist.add( new LabelValueList( (String) hlp[0],listValues ));
            }
            ret.setEventParticipansPerAgeDuoteams( participantslist );
            
            //newa
            q = getSession().createSQLQuery( SQL_PARTICIPANT_PER_AGE_NEWAFIGHTER );
            list = q.list();

            participantslist = new ArrayList<LabelValueList>();

            for ( Object item : list )
            {
                hlp = (Object[]) item;
                listValues= new ArrayList<String>();
                listValues.add(String.valueOf( ( (BigInteger) hlp[1] ).intValue() ));
                listValues.add(String.valueOf( ( (BigInteger) hlp[2] ).intValue() ));
                participantslist.add( new LabelValueList( (String) hlp[0],listValues ));
            }
            ret.setEventParticipansPerAgeNewaFighter( participantslist );
            
            

            q = getSession().createSQLQuery( SQL_EVENT_INFOS );
            list = q.list();

            ret.setEventName( ( (String) ( (Object[]) list.get( 0 ) )[0] ) );
            ret.setEventLocation( ( (String) ( (Object[]) list.get( 0 ) )[1] ) );
            ret.setEventDate( ( (String) ( (Object[]) list.get( 0 ) )[2] ) );

            return ret;
        }
        catch ( Exception e )
        {
            log.error( "can not getPressInfo", e );
            throw new JJWDataLayerException( e );
        }
    }
}
