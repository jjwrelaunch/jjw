/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : AgeMapper.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:45
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

import de.jjw.model.admin.Age;

public class AgeMapper
{

    public static List<Age> mapAgeListFromDB( List<Age> ages )
    {
        if ( ages == null )
        {
            return null;
        }
        List<Age> ret = new ArrayList<Age>( ages.size() + 1 );

        for ( Age age : ages )
        {
            ret.add( mapAgeFromDB( age ) );
        }

        return ret;
    }

    /**
     * maps a Age from DB and get a new Age object
     *
     * @param age
     * @return
     */
    public static Age mapAgeFromDB( Age age )
    {
        if ( age == null )
        {
            return null;
        }
        Age ret = new Age();

        ret.setAgeShort( new String( age.getAgeShort() ) );
        ret.setCreateDate( new Timestamp( age.getCreateDate().getTime() ) );
        ret.setCreateId( Long.valueOf( age.getCreateId() ) );
        ret.setDescription( new String( age.getDescription() ) );
        ret.setId( Long.valueOf( age.getId() ) );
        ret.setFightingTime( age.getFightingTime() );
        ret.setFightingRounds( age.getFightingRounds() );
        ret.setStartAge( age.getStartAge() );
        ret.setAgeLimit( age.getAgeLimit() );
        ret.setOvertime( age.getOvertime() );
        ret.setInjurytime( age.getInjurytime() );
        ret.setEstimatedTime( age.getEstimatedTime() );
        ret.setEstimatedTimeDuo( age.getEstimatedTimeDuo() );
        ret.setOrderNumber( age.getOrderNumber() );
        ret.setModificationDate( new Timestamp( age.getModificationDate().getTime() ) );
        ret.setModificationId( Long.valueOf( age.getModificationId() ) );

        ret.setEstimatedTimeNewa( age.getEstimatedTimeNewa() );
        ret.setFightingTimeNewa( age.getFightingTimeNewa() );
        return ret;
    }

    public static void mapAgeToDB( Age age, Age ageDB )
    {
        if ( age != null && ageDB == null )
        {
            ageDB = new Age();
        }

        if ( ageDB.getId() == null )
            ageDB.setId( age.getId() );
        ageDB.setAgeShort( new String( age.getAgeShort() ) );
        ageDB.setDescription( new String( age.getDescription() ) );
        ageDB.setFightingTime( age.getFightingTime() );
        ageDB.setFightingRounds( age.getFightingRounds() );
        ageDB.setStartAge( age.getStartAge() );
        ageDB.setAgeLimit( age.getAgeLimit() );
        ageDB.setOvertime( age.getOvertime() );
        ageDB.setInjurytime( age.getInjurytime() );
        ageDB.setEstimatedTime( age.getEstimatedTime() );
        ageDB.setEstimatedTimeDuo( age.getEstimatedTimeDuo() );
        //ageDB.setOrderNumber(age.getOrderNumber());
        ageDB.setModificationDate( new Timestamp( age.getModificationDate().getTime() ) );
        ageDB.setModificationId( Long.valueOf( age.getModificationId() ) );

        ageDB.setEstimatedTimeNewa( age.getEstimatedTimeNewa() );
        ageDB.setFightingTimeNewa( age.getFightingTimeNewa() );
    }

}
