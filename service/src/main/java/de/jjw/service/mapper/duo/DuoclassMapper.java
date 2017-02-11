/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoclassMapper.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:42
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

package de.jjw.service.mapper.duo;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.jjw.model.duo.Duoclass;
import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.service.mapper.admin.AgeMapper;
import de.jjw.service.modelWeb.DuoclassWeb;
import de.jjw.util.ICodestableTypeConstants;
import de.jjw.util.TypeUtil;

public class DuoclassMapper
    implements ICodestableTypeConstants
{

    public static List<DuoclassWeb> mapDuoclassListFromDB( List<Duoclass> duoclasses, Locale local )
    {
        List<DuoclassWeb> ret = new ArrayList<DuoclassWeb>( duoclasses.size() + 1 );
        DuoclassWeb duoclassWeb = null;

        for ( Duoclass duoclass : duoclasses )
        {
            duoclassWeb = mapDuoclassFromDB( duoclass );
            duoclassWeb.setSexWeb(
                CodestableMain.getInstance().getCodestableCodeValueByType( TYPE_SEX, duoclass.getSex(), local ) );
            if ( duoclass.getStartTime() != null )
            {
                duoclassWeb.setStartTimeWeb( new Date( duoclass.getStartTime().getTime() ) );
            }

            if ( duoclass.getAge() != null && !TypeUtil.isEmptyOrDefault( duoclass.getAge().getEstimatedTimeDuo() ) &&
                !TypeUtil.isEmptyOrDefault( duoclass.getNumberOfOpenFightsInClass() ) && !duoclass.isComplete() )
            {
                int estimatedTime =
                    ( (int) duoclass.getNumberOfOpenFightsInClass() ) * duoclass.getAge().getEstimatedTimeDuo();
                StringBuffer sb = new StringBuffer();

                sb.append( (int) estimatedTime / 3600 + ":" );
                estimatedTime = estimatedTime - ( (int) estimatedTime / 3600 ) * 3600;

                if ( ( (int) estimatedTime / 60 ) < 10 )
                {
                    sb.append( "0" );
                }
                sb.append( String.valueOf( (int) estimatedTime / 60 ) );
                duoclassWeb.setEstimateTimeWeb( sb.toString() );
            }

            ret.add( duoclassWeb );
        }

        return ret;
    }

    /**
     * maps a Weightclass from DB and get a new Weightclass object
     *
     * @param duoclass
     * @return
     */
    public static DuoclassWeb mapDuoclassFromDB( Duoclass duoclass )
    {
        try
        {
            if ( duoclass == null || null == duoclass.getId() )
            {
                return null;
            }
        }
        catch ( Exception e )
        {
            // ignore, not corresponding weightclass
            return null;
        }
        DuoclassWeb ret = new DuoclassWeb();

        ret.setAge( AgeMapper.mapAgeFromDB( duoclass.getAge() ) );
        ret.setCertificationPrint( duoclass.isCertificationPrint() );
        ret.setComplete( duoclass.isComplete() );
        ret.setDeleteStop( duoclass.isDeleteStop() );
        ret.setNumberOfTeams( duoclass.getNumberOfTeams() );
        ret.setSex( new String( duoclass.getSex() ) );

        // ret.setUser(new HashSet(weightclass.getUser()));

        ret.setDuoclassName( new String( duoclass.getDuoclassName() ) );
        if ( duoclass.getStartTime() == null )
        {
            ret.setStartTime( null );
        }
        else
        {
            ret.setStartTime( new Timestamp( duoclass.getStartTime().getTime() ) );
        }
        if ( duoclass.getEndTime() == null )
        {
            ret.setEndTime( null );
        }
        else
        {
            ret.setEndTime( new Timestamp( duoclass.getEndTime().getTime() ) );
        }
        ret.setInuse( duoclass.isInuse() );
        ret.setFightsystem( duoclass.getFightsystem() );

        ret.setNumberOfPotentialTeams( duoclass.getNumberOfPotentialTeams() );
        ret.setNumberOfFightsInClass( duoclass.getNumberOfFightsInClass() );
        ret.setNumberOfOpenFightsInClass( duoclass.getNumberOfOpenFightsInClass() );
        ret.setAverageFighttimeInClass( duoclass.getAverageFighttimeInClass() );

        ret.setParent( duoclass.getParent() );
        ret.setChilds( duoclass.getChilds() );
        ret.setParentId( duoclass.getParentId() );

        ret.setCreateDate( new Timestamp( duoclass.getCreateDate().getTime() ) );
        ret.setCreateId( Long.valueOf( duoclass.getCreateId() ) );
        ret.setId( Long.valueOf( duoclass.getId() ) );
        ret.setModificationDate( new Timestamp( duoclass.getModificationDate().getTime() ) );
        ret.setModificationId( Long.valueOf( duoclass.getModificationId() ) );
        return ret;
    }

    public static void mapDuoclassToDB( Duoclass duoclass, Duoclass duoclassDB )
    {

        if ( duoclass != null && duoclassDB != null )
        {
            if ( duoclassDB.getId() == null )
                duoclassDB.setId( duoclass.getId() );
            AgeMapper.mapAgeToDB( duoclass.getAge(), duoclassDB.getAge() );
            duoclassDB.setCertificationPrint( duoclass.isCertificationPrint() );
            duoclassDB.setComplete( duoclass.isComplete() );
            duoclassDB.setDeleteStop( duoclass.isDeleteStop() );
            duoclassDB.setNumberOfTeams( duoclass.getNumberOfTeams() );
            duoclassDB.setSex( new String( duoclass.getSex() ) );

            // weightclassDB.setUser(new HashSet(weightclass.getUser()));

            duoclassDB.setDuoclassName( new String( duoclass.getDuoclassName() ) );

            duoclassDB.setModificationDate( new Timestamp( duoclass.getModificationDate().getTime() ) );
            duoclassDB.setModificationId( Long.valueOf( duoclass.getModificationId() ) );
        }

    }

}
