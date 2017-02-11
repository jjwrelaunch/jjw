/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2011.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : VideoMapper.java
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
package de.jjw.service.mapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.jjw.model.VideoItem;
import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.service.modelWeb.PreviewWeb;
import de.jjw.util.ICodestableTypeConstants;
import de.jjw.util.TypeUtil;

public class VideoMapper
    implements ICodestableTypeConstants
{

    public static List<VideoItem> mapVideoListFromDB( List<VideoItem> videos, Locale local )
    {
        if ( videos == null )
        {
            return null;
        }

        List<VideoItem> ret = new ArrayList<VideoItem>( videos.size() + 1 );
        VideoItem item;
        for ( VideoItem video : videos )
        {
            item = mapVideoFromDB( video, local );

            ret.add( item );
        }

        return ret;
    }

    public static VideoItem mapVideoFromDB( VideoItem video, Locale local )
    {

        if ( video == null )
        {
            return null;
        }
        VideoItem ret = new VideoItem();

        ret.setFightId( Long.valueOf( video.getFightId() ) );
        ret.setParticipanIdRed( Long.valueOf( video.getParticipanIdRed() ) );
        ret.setParticipanIdBlue( Long.valueOf( video.getParticipanIdBlue() ) );
        ret.setText( new String( video.getText() ) );
        ret.setDescription( new String( video.getDescription() ) );
        ret.setDiscepline( new String( video.getDiscepline() ) );

        if ( null != video.getVideo() )
        {
            ret.setVideo( video.getVideo().clone() );
            ret.setSize( video.getVideo().length );
        }
        
        ret.setFilename( new String( video.getFilename() ) );

        if ( video.getCreateDate() != null )
        {
            ret.setCreateDate( new Timestamp( video.getCreateDate().getTime() ) );
        }
        if ( video.getCreateId() != null )
        {
            ret.setCreateId( Long.valueOf( video.getCreateId() ) );
        }
        if ( video.getId() != null )
        {
            ret.setId( Long.valueOf( video.getId() ) );
        }
        if ( video.getModificationDate() != null )
        {
            ret.setModificationDate( new Timestamp( video.getModificationDate().getTime() ) );
        }
        if ( video.getModificationId() != null )
        {
            ret.setModificationId( Long.valueOf( video.getModificationId() ) );
        }
        return ret;
    }

    public static void mapVideoToDB( VideoItem video, VideoItem videoDB, boolean deepMapping )
    {

        videoDB.setFightId( Long.valueOf( video.getFightId() ) );
        videoDB.setParticipanIdRed( Long.valueOf( video.getParticipanIdRed() ) );
        videoDB.setParticipanIdBlue( Long.valueOf( video.getParticipanIdBlue() ) );
        videoDB.setText( new String( video.getText() ) );
        videoDB.setDescription( new String( video.getDescription() ) );
        videoDB.setDiscepline( new String( video.getDiscepline() ) );

        if ( null != video.getVideo() )
        {
            videoDB.setVideo( video.getVideo().clone() );
            videoDB.setSize( video.getVideo().length );
        }
        videoDB.setFilename( new String( video.getFilename() ) );
        videoDB.setCreateDate( new Timestamp( video.getCreateDate().getTime() ) );
        videoDB.setCreateId( Long.valueOf( video.getCreateId() ) );
        videoDB.setId( Long.valueOf( video.getId() ) );
        videoDB.setModificationDate( new Timestamp( video.getModificationDate().getTime() ) );
        videoDB.setModificationId( Long.valueOf( video.getModificationId() ) );
    }

}
