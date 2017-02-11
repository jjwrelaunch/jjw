/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2011.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : PreviewDao.java
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

package de.jjw.dao;

import java.util.List;

import de.jjw.dao.hibernate.JJWDataLayerException;
import de.jjw.model.Preview;

public interface PreviewDao
    extends Dao
{
    public List<List<Preview>> getAllPreviews()
        throws JJWDataLayerException;

    public List<Preview> getPreviewForTatami( long userId )
        throws JJWDataLayerException;

    public void savePreview( Preview preview )
        throws JJWDataLayerException;

    public void removePreview( long previewId )
        throws JJWDataLayerException;

    public void movePreviewUp( long previewId )
        throws JJWDataLayerException;

    public List<Preview> getPreviewForTatamiClock( long fightId, String discepline, long userId )
        throws JJWDataLayerException;

    public List<Preview> getPreviewByFightId( long fightId, String dicepline )
        throws JJWDataLayerException;

    public void removePreview( long fightId, String dicepline )
        throws JJWDataLayerException;

    public List<Long> getUserWithAssignedClasses()
        throws JJWDataLayerException;

}
