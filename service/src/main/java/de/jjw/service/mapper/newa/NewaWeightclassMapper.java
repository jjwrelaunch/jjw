/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaWeightclassMapper.java
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

package de.jjw.service.mapper.newa;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.model.newa.Newaclass;
import de.jjw.service.mapper.admin.AgeMapper;
import de.jjw.service.modelWeb.NewaclassWeb;
import de.jjw.util.ICodestableTypeConstants;
import de.jjw.util.TypeUtil;

public class NewaWeightclassMapper
    implements ICodestableTypeConstants
{


    public static List<NewaclassWeb> mapWeightclassListFromDB( List<Newaclass> newaclasses, Locale local )
    {
        List<NewaclassWeb> ret = new ArrayList<NewaclassWeb>( newaclasses.size() + 1 );
        NewaclassWeb fightingclassWeb = null;

        for ( Newaclass newaclass : newaclasses )
        {
            fightingclassWeb = mapWeightclassFromDB( newaclass );
            fightingclassWeb.setSexWeb(
                CodestableMain.getInstance().getCodestableCodeValueByType( TYPE_SEX, newaclass.getSex(), local ) );
            if ( newaclass.getStartTime() != null )
            {
                fightingclassWeb.setStartTimeWeb( new Date( newaclass.getStartTime().getTime() ) );
            }
            if ( newaclass.getAge() != null && !TypeUtil.isEmptyOrDefault( newaclass.getAge().getEstimatedTimeNewa() )
                &&
                !TypeUtil.isEmptyOrDefault( newaclass.getNumberOfOpenFightsInClass() ) &&
                !newaclass.isComplete() )
            {
                int estimatedTime =
                    ( (int) newaclass.getNumberOfOpenFightsInClass() ) * newaclass.getAge().getEstimatedTimeNewa();
                StringBuffer sb = new StringBuffer();

                sb.append( (int) estimatedTime / 3600 + ":" );
                estimatedTime = estimatedTime - ( (int) estimatedTime / 3600 ) * 3600;

                if ( ( (int) estimatedTime / 60 ) < 10 )
                {
                    sb.append( "0" );
                }
                sb.append( String.valueOf( (int) estimatedTime / 60 ) );
                fightingclassWeb.setEstimateTimeWeb( sb.toString() );
            }
            ret.add( fightingclassWeb );
        }

        return ret;
    }

    /**
     * maps a Weightclass from DB and get a new Weightclass object
     *
     * @param fightingclass
     * @return
     */
    public static NewaclassWeb mapWeightclassFromDB( Newaclass fightingclass )
    {
        try
        {
            if ( fightingclass == null || null == fightingclass.getId() )
            {
                return null;
            }
        }
        catch ( Exception e )
        {
            // ignore, not corresponding weightclass
            return null;
        }
        NewaclassWeb ret = new NewaclassWeb();

        ret.setAge( AgeMapper.mapAgeFromDB( fightingclass.getAge() ) );
        ret.setCertificationPrint( fightingclass.isCertificationPrint() );
        ret.setComplete( fightingclass.isComplete() );
        ret.setDeleteStop( fightingclass.isDeleteStop() );
        ret.setNumberOfFighter( fightingclass.getNumberOfFighter() );
        ret.setSex( new String( fightingclass.getSex() ) );
        ret.setStartWeight( fightingclass.getStartWeight() );
        //ret.setUser(new HashSet(weightclass.getUser()));

        ret.setWeightclass( new String( fightingclass.getWeightclass() ) );
        ret.setWeightLimit( fightingclass.getWeightLimit() );
        if ( fightingclass.getStartTime() == null )
        {
            ret.setStartTime( null );
        }
        else
        {
            ret.setStartTime( new Timestamp( fightingclass.getStartTime().getTime() ) );
        }
        if ( fightingclass.getEndTime() == null )
        {
            ret.setEndTime( null );
        }
        else
        {
            ret.setEndTime( new Timestamp( fightingclass.getEndTime().getTime() ) );
        }
        ret.setInuse( fightingclass.isInuse() );
        ret.setFightsystem( fightingclass.getFightsystem() );

        ret.setNumberOfFighter( fightingclass.getNumberOfFighter() );
        ret.setNumberOfPotentialFighter( fightingclass.getNumberOfPotentialFighter() );
        ret.setNumberOfFightsInClass( fightingclass.getNumberOfFightsInClass() );
        ret.setNumberOfOpenFightsInClass( fightingclass.getNumberOfOpenFightsInClass() );
        ret.setAverageFighttimeInClass( fightingclass.getAverageFighttimeInClass() );
        
        ret.setParent(fightingclass.getParent());
        ret.setChilds(fightingclass.getChilds());
        ret.setParentId( fightingclass.getParentId() );

        ret.setCreateDate( new Timestamp( fightingclass.getCreateDate().getTime() ) );
        ret.setCreateId( Long.valueOf( fightingclass.getCreateId() ) );
        ret.setId( Long.valueOf( fightingclass.getId() ) );
        ret.setModificationDate( new Timestamp( fightingclass.getModificationDate().getTime() ) );
        ret.setModificationId( Long.valueOf( fightingclass.getModificationId() ) );
        return ret;
    }

    public static void mapWeightclassToDB( Newaclass fightingclass, Newaclass fightingclassDB )
    {

        if ( fightingclass != null && fightingclassDB != null )
        {
            if ( fightingclassDB.getId() == null )
                fightingclassDB.setId( fightingclass.getId() );
            AgeMapper.mapAgeToDB( fightingclass.getAge(), fightingclassDB.getAge() );
            fightingclassDB.setCertificationPrint( fightingclass.isCertificationPrint() );
            fightingclassDB.setComplete( fightingclass.isComplete() );
            fightingclassDB.setDeleteStop( fightingclass.isDeleteStop() );
            fightingclassDB.setNumberOfFighter( fightingclass.getNumberOfFighter() );
            fightingclassDB.setSex( new String( fightingclass.getSex() ) );
            fightingclassDB.setStartWeight( fightingclass.getStartWeight() );
            //weightclassDB.setUser(new HashSet(weightclass.getUser()));

            fightingclassDB.setWeightclass( new String( fightingclass.getWeightclass() ) );
            fightingclassDB.setWeightLimit( fightingclass.getWeightLimit() );

            fightingclassDB.setModificationDate( new Timestamp( fightingclass.getModificationDate().getTime() ) );
            fightingclassDB.setModificationId( Long.valueOf( fightingclass.getModificationId() ) );
        }

    }


}
