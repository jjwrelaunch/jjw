/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2011.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : VideoManagerImpl.java
 * Created : 08 Feb 2011
 * Last Modified: Tue, 08 Feb 2011 20:55:48
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

import de.jjw.dao.VideoDao;
import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.VideoItem;
import de.jjw.service.VideoManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.mapper.VideoMapper;



public class VideoManagerImpl
    extends BaseManager
    implements VideoManager
{

    private VideoDao videoDao;

    public VideoItem getVideo( Long id )
        throws JJWManagerException
    {
        try
        {
        return VideoMapper.mapVideoFromDB( videoDao.getVideo( id ), null );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public List<VideoItem> getVideoList()
        throws JJWManagerException
    {
        try
        {
        return VideoMapper.mapVideoListFromDB( videoDao.getVideoList(), null );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public List<VideoItem> getVideoListByParticipant( long participantId, String discepline )
        throws JJWManagerException
    {
        try
        {
            return VideoMapper.mapVideoListFromDB( videoDao.getVideoListByParticipant( participantId, discepline ), null );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }        
    }

    public void saveVideo( VideoItem video )
        throws JJWManagerException
    {
        try
        {
            video.setCreateDate( new Timestamp( System.currentTimeMillis() ));
            video.setModificationDate( new Timestamp( System.currentTimeMillis() ));
            video.setModificationId( 0l );
            video.setCreateId( 0l );
            if ( null == video.getId() )
            {
                videoDao.saveVideo( video );
            }
            else
            {
            	VideoMapper.mapVideoToDB( video, videoDao.getVideo(video.getId() ), true );
            }
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public void removeVideo( Long videoId )
        throws JJWManagerException
    {
        try
        {
           videoDao.removeVideo( videoId );
        }
        catch ( JJWDataLayerException e )
        {
            throw new JJWManagerException( e );
        }
    }

    public VideoDao getVideoDao()
    {
        return videoDao;
    }

    public void setVideoDao( VideoDao videoDao )
    {
        this.videoDao = videoDao;
    }
    
  
}
