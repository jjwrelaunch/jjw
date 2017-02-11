/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : VideoDaoHibernate.java
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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import de.jjw.dao.IDatabaseTableConstants;
import de.jjw.dao.VideoDao;
import de.jjw.dao.hibernate.fighting.IFighterDatabaseConstants;
import de.jjw.model.Team;
import de.jjw.model.VideoItem;

public class VideoDaoHibernate
    extends BaseDaoHibernate
    implements VideoDao, IDatabaseTableConstants
{

    public Team getTeam( Long id )
    {
        return (Team) getHibernateTemplate().get( Team.class, id );
    }

    @SuppressWarnings( "unchecked" )
    public List<Team> getTeamsByTeamName( String teamName )
    {
        Query q = getSession().createQuery( "TEAMS_BY_TEAM_NAME" );
        q.setString( 0, teamName );
        return q.list();
    }

    public void saveVideo( VideoItem video)
    {
        try
        {
            getHibernateTemplate().saveOrUpdate( video );
        }
        catch ( Exception e )
        {
            log.fatal( "Can't save video: " + e.getMessage() );
        }
    }

    public void removeVideo( Long videoId )throws JJWDataLayerException
    {
        try
        {
            getHibernateTemplate().delete( getTeam( videoId ) );
        }
        catch ( Exception e )
        {
            log.error( "can't get removeVideo", e );
            throw new JJWDataLayerException( e );
        }

    }

    
    public VideoItem getVideo( Long id )
        throws JJWDataLayerException
    {
        try
        {
            return (VideoItem) getHibernateTemplate().get( VideoItem.class, id );
        }
        catch ( Exception e )
        {
            log.error( "can't get getVideo", e );
            throw new JJWDataLayerException( e );
        }
    }

    
    private static String SQL_GET_VIDEO_LIST =

                    "select id,text,description, fightId,discepline from " + IDatabaseTableConstants.TABLE_VIDEO;
                        
    
    
    public List<VideoItem> getVideoList()
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createSQLQuery(SQL_GET_VIDEO_LIST);
            
            List<VideoItem> retList =  new ArrayList<VideoItem>();
//            for ( Object item : q.list() )
//                
//            {
//                retList.add( new VideoItem(((BigInteger) ( (Object[]) item )[0] ).longValue(),
//                                           (String) ( (Object[]) item )[1] ,
//                                           (String) ( (Object[]) item )[2] ,
//                                           ((BigInteger) ( (Object[]) item )[3] ).longValue(),
//                                           (String) ( (Object[]) item )[4] );                                            text, description, fightId, discepline ) )
//               
//            }
         return null;   
        }
        catch ( Exception e )
        {
            log.error( "can't get getVideoList", e );
            throw new JJWDataLayerException( e );
        }
    }

   
    public List<VideoItem> getVideoListByParticipant( long participantId, String discepline )
        throws JJWDataLayerException
    {
        // TODO Auto-generated method stub
        return null;
    }

  
    public void saveTeam( VideoItem video )
        throws JJWDataLayerException
    {
        try
        {
            Query q = getSession().createSQLQuery(SQL_GET_VIDEO_LIST);
            
            List<VideoItem> retList =  new ArrayList<VideoItem>();
//            for ( Object item : q.list() )
//                
//            {
//                retList.add( new VideoItem(((BigInteger) ( (Object[]) item )[0] ).longValue(),
//                                           (String) ( (Object[]) item )[1] ,
//                                           (String) ( (Object[]) item )[2] ,
//                                           ((BigInteger) ( (Object[]) item )[3] ).longValue(),
//                                           (String) ( (Object[]) item )[4] );                                            text, description, fightId, discepline ) )
////               
//            }
            
        }
        catch ( Exception e )
        {
            log.error( "can't get saveTeam", e );
            throw new JJWDataLayerException( e );
        }

    }
}
