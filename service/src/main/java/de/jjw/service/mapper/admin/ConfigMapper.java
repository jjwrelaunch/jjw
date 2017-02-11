/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : ConfigMapper.java
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

package de.jjw.service.mapper.admin;

import java.sql.Timestamp;

import de.jjw.model.admin.ConfigJJW;

public class ConfigMapper
{

    public static ConfigJJW mapConfigFromDB( ConfigJJW config )
    {
        if ( config == null )
        {
            return null;
        }
        ConfigJJW ret = new ConfigJJW();

        ret.setCertificationType( config.getCertificationType() );
        ret.setEventDate( new String( config.getEventDate() ) );
        ret.setEventLocation( new String( config.getEventLocation() ) );
        ret.setEventName( new String( config.getEventName() ) );

        ret.setFighterDeleteable( config.isFighterDeleteable() );
        ret.setLogo( config.getLogo() );
        ret.setFightRevenge( config.getFightRevenge() );
        ret.setPdfHeadLine1( new String( config.getPdfHeadLine1() ) );
        ret.setPdfHeadLine2( new String( config.getPdfHeadLine2() ) );
        ret.setWebsiteHeadLine1( new String( config.getWebsiteHeadLine1() ) );
        ret.setWebsiteHeadLine2( new String( config.getWebsiteHeadLine2() ) );
        ret.setCertificationPlaces( config.getCertificationPlaces() );
        ret.setDumpSend( new Timestamp( config.getDumpSend().getTime() ) );
        ret.setVideoOn( config.isVideoOn() );

        ret.setCreateDate( new Timestamp( config.getCreateDate().getTime() ) );
        ret.setCreateId( Long.valueOf( config.getCreateId() ) );
        ret.setModificationDate( new Timestamp( config.getModificationDate().getTime() ) );
        ret.setModificationId( Long.valueOf( config.getModificationId() ) );
        ret.setId( Long.valueOf( config.getId() ) );
        return ret;
    }

    public static void mapConfigToDB( ConfigJJW config, ConfigJJW configDB )
    {
        if ( config != null && configDB == null )
        {
            config = new ConfigJJW();
        }

        configDB.setCertificationType( config.getCertificationType() );
        configDB.setEventDate( new String( config.getEventDate() ) );
        configDB.setEventLocation( new String( config.getEventLocation() ) );
        configDB.setEventName( new String( config.getEventName() ) );

        configDB.setFighterDeleteable( config.isFighterDeleteable() );
        configDB.setLogo( config.getLogo() );
        configDB.setFightRevenge( config.getFightRevenge() );
        configDB.setPdfHeadLine1( new String( config.getPdfHeadLine1() ) );
        configDB.setPdfHeadLine2( new String( config.getPdfHeadLine2() ) );
        configDB.setWebsiteHeadLine1( new String( config.getWebsiteHeadLine1() ) );
        configDB.setWebsiteHeadLine2( new String( config.getWebsiteHeadLine2() ) );
        configDB.setCertificationPlaces( config.getCertificationPlaces() );
        configDB.setDumpSend( new Timestamp( config.getDumpSend().getTime() ) );
        configDB.setVideoOn( config.isVideoOn() );

        configDB.setModificationDate( new Timestamp( config.getModificationDate().getTime() ) );
        configDB.setModificationId( new Long( config.getModificationId() ) );
    }
}
