/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2011.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : VideoManager.java
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

package de.jjw.service;

import java.util.List;



import de.jjw.service.exception.JJWManagerException;
import de.jjw.model.VideoItem;

public interface VideoManager
{
    public VideoItem getVideo( Long id )
        throws JJWManagerException;

    public List<VideoItem> getVideoList()
        throws JJWManagerException;

    public List<VideoItem> getVideoListByParticipant( long participantId, String discepline )
        throws JJWManagerException;

    public void saveVideo( VideoItem video )
        throws JJWManagerException;

    public void removeVideo( Long videoId )
        throws JJWManagerException;

}
