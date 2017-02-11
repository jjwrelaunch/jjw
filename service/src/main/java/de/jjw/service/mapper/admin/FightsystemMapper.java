/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightsystemMapper.java
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

package de.jjw.service.mapper.admin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import de.jjw.model.admin.Fightsystem;

public class FightsystemMapper
{

    public static List<Fightsystem> mapFightsystemListFromDB( List<Fightsystem> fightsystems )
    {

        if ( fightsystems == null )
        {
            return null;
        }
        List<Fightsystem> ret = new ArrayList<Fightsystem>( fightsystems.size() + 1 );

        for ( Fightsystem fightsystem : fightsystems )
        {
            ret.add( mapFightsystemFromDB( fightsystem ) );
        }

        return ret;
    }

    /**
     * maps a Fightsystem from DB and get a new Country object
     *
     * @param fightsystem
     * @return
     */
    public static Fightsystem mapFightsystemFromDB( Fightsystem fightsystem )
    {
        if ( fightsystem == null )
        {
            return null;
        }
        Fightsystem newFightsystem = new Fightsystem();

        newFightsystem.setFightsystemType( Integer.valueOf( fightsystem.getFightsystemType() ) );
        newFightsystem.setMinParticipant( Integer.valueOf( fightsystem.getMinParticipant() ) );
        newFightsystem.setMaxParticipant( Integer.valueOf( fightsystem.getMaxParticipant() ) );

        newFightsystem.setCreateDate( new Timestamp( fightsystem.getCreateDate().getTime() ) );
        newFightsystem.setCreateId( Long.valueOf( fightsystem.getCreateId() ) );
        newFightsystem.setId( new Long( fightsystem.getId() ) );
        newFightsystem.setModificationDate( new Timestamp( fightsystem.getModificationDate().getTime() ) );
        newFightsystem.setModificationId( new Long( fightsystem.getModificationId() ) );
        return newFightsystem;
    }


    public static List<Fightsystem> mapFightsystemListToDB( List<Fightsystem> fightsystems )
    {

        if ( fightsystems == null )
        {
            return null;
        }
        List<Fightsystem> ret = new ArrayList<Fightsystem>( fightsystems.size() + 1 );
        Fightsystem fightsystemDB = null;

        for ( Fightsystem fightsystem : fightsystems )
        {
            fightsystemDB = new Fightsystem();
            mapFightsystemToDB( fightsystem, fightsystemDB );
            ret.add( fightsystemDB );
        }

        return ret;
    }

    public static void mapFightsystemToDB( Fightsystem fightsystem, Fightsystem fightsystemDB )
    {
        fightsystemDB.setFightsystemType( Integer.valueOf( fightsystem.getFightsystemType() ) );
        fightsystemDB.setMinParticipant( Integer.valueOf( fightsystem.getMinParticipant() ) );
        fightsystemDB.setMaxParticipant( Integer.valueOf( fightsystem.getMaxParticipant() ) );

        fightsystemDB.setModificationDate( new Timestamp( System.currentTimeMillis() ) );
        fightsystemDB.setModificationId( Long.valueOf( fightsystem.getModificationId() ) );
        fightsystemDB.setCreateDate( fightsystem.getCreateDate() );
        fightsystemDB.setCreateId( fightsystem.getCreateId() );
        fightsystemDB.setId( fightsystem.getId() );

    }

}
