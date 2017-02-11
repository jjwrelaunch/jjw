/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoFightMapper.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 21:54:41
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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import de.jjw.model.duo.DuoFight;
import de.jjw.model.duo.FriendlyDuoFight;
import de.jjw.service.modelWeb.DuoFightWeb;
import de.jjw.service.util.IGlobalProjectConstants;
import de.jjw.util.TypeUtil;

public class DuoFightMapper
    implements IGlobalProjectConstants
{
    public static List<DuoFightWeb> mapFriendlyFightListFromDB( List<FriendlyDuoFight> fights )
    {
        List<DuoFightWeb> ret = new ArrayList<DuoFightWeb>( fights.size() + 1 );
        DuoFightWeb fightWeb = null;
        for ( FriendlyDuoFight item : fights )
        {
            fightWeb = mapFightFromDBForWeb( item );
            if ( item.getWinnerId() >= TypeUtil.LONG_DEFAULT )
            {
                fightWeb.setFightReady( IMAGE_JJW_DIR + OK_GIF );
            }
            else
            {
                fightWeb.setFightReady( IMAGE_JJW_DIR + X_GIF );
            }
            ret.add( fightWeb );
        }

        return ret;
    }

    public static DuoFight mapFightFromDB( DuoFight fight )
    {
        if ( fight == null )
        {
            return null;
        }
        DuoFight ret = new DuoFight();

        ret.setDuoTeamBlue( DuoTeamMapper.mapDuoTeamFromDB( fight.getDuoTeamBlue() ) );
        ret.setDuoTeamRed( DuoTeamMapper.mapDuoTeamFromDB( fight.getDuoTeamRed() ) );
        ret.setTeamIdBlue( fight.getTeamIdBlue() );
        ret.setTeamIdRed( fight.getTeamIdRed() );
        ret.setDuoclassId( fight.getDuoclassId() );
        if ( fight.getFlag() != null )
        {
            ret.setFlag( fight.getFlag() );
        }
        ret.setFusengachi( fight.getFusengachi() );
        ret.setInjuryTimeBlue( fight.getInjuryTimeBlue() );
        ret.setInjuryTimeRed( fight.getInjuryTimeRed() );
        ret.setKikengachi( fight.getKikengachi() );
        ret.setOverallFightTime( fight.getOverallFightTime() );
        ret.setPointsBlue( fight.getPointsBlue() );
        ret.setPointsRed( fight.getPointsRed() );
        ret.setPointsBlueMax( fight.getPointsBlueMax() );
        ret.setPointsRedMax( fight.getPointsRedMax() );

        ret.setBlueSeriesArefI( fight.getBlueSeriesArefI() );
        ret.setBlueSeriesArefII( fight.getBlueSeriesArefII() );
        ret.setBlueSeriesArefIII( fight.getBlueSeriesArefIII() );
        ret.setBlueSeriesArefIV( fight.getBlueSeriesArefIV() );
        ret.setBlueSeriesArefV( fight.getBlueSeriesArefV() );
        ret.setBlueSeriesBrefI( fight.getBlueSeriesBrefI() );
        ret.setBlueSeriesBrefII( fight.getBlueSeriesBrefII() );
        ret.setBlueSeriesBrefIII( fight.getBlueSeriesBrefIII() );
        ret.setBlueSeriesBrefIV( fight.getBlueSeriesBrefIV() );
        ret.setBlueSeriesBrefV( fight.getBlueSeriesBrefV() );
        ret.setBlueSeriesCrefI( fight.getBlueSeriesCrefI() );
        ret.setBlueSeriesCrefII( fight.getBlueSeriesCrefII() );
        ret.setBlueSeriesCrefIII( fight.getBlueSeriesCrefIII() );
        ret.setBlueSeriesCrefIV( fight.getBlueSeriesCrefIV() );
        ret.setBlueSeriesCrefV( fight.getBlueSeriesCrefV() );
        ret.setBlueSeriesDrefI( fight.getBlueSeriesDrefI() );
        ret.setBlueSeriesDrefII( fight.getBlueSeriesDrefII() );
        ret.setBlueSeriesDrefIII( fight.getBlueSeriesDrefIII() );
        ret.setBlueSeriesDrefIV( fight.getBlueSeriesDrefIV() );
        ret.setBlueSeriesDrefV( fight.getBlueSeriesDrefV() );

        ret.setRedSeriesArefI( fight.getRedSeriesArefI() );
        ret.setRedSeriesArefII( fight.getRedSeriesArefII() );
        ret.setRedSeriesArefIII( fight.getRedSeriesArefIII() );
        ret.setRedSeriesArefIV( fight.getRedSeriesArefIV() );
        ret.setRedSeriesArefV( fight.getRedSeriesArefV() );
        ret.setRedSeriesBrefI( fight.getRedSeriesBrefI() );
        ret.setRedSeriesBrefII( fight.getRedSeriesBrefII() );
        ret.setRedSeriesBrefIII( fight.getRedSeriesBrefIII() );
        ret.setRedSeriesBrefIV( fight.getRedSeriesBrefIV() );
        ret.setRedSeriesBrefV( fight.getRedSeriesBrefV() );
        ret.setRedSeriesCrefI( fight.getRedSeriesCrefI() );
        ret.setRedSeriesCrefII( fight.getRedSeriesCrefII() );
        ret.setRedSeriesCrefIII( fight.getRedSeriesCrefIII() );
        ret.setRedSeriesCrefIV( fight.getRedSeriesCrefIV() );
        ret.setRedSeriesCrefV( fight.getRedSeriesCrefV() );
        ret.setRedSeriesDrefI( fight.getRedSeriesDrefI() );
        ret.setRedSeriesDrefII( fight.getRedSeriesDrefII() );
        ret.setRedSeriesDrefIII( fight.getRedSeriesDrefIII() );
        ret.setRedSeriesDrefIV( fight.getRedSeriesDrefIV() );
        ret.setRedSeriesDrefV( fight.getRedSeriesDrefV() );

        if ( fight.getWinnerId() != null )
        {
            ret.setWinnerId( Long.valueOf( fight.getWinnerId() ) );
        }

        if ( fight.getCreateDate() != null )
        {
            ret.setCreateDate( new Timestamp( fight.getCreateDate().getTime() ) );
        }
        if ( fight.getCreateId() != null )
        {
            ret.setCreateId( Long.valueOf( fight.getCreateId() ) );
        }
        if ( fight.getId() != null )
        {
            ret.setId( Long.valueOf( fight.getId() ) );
        }
        if ( fight.getModificationDate() != null )
        {
            ret.setModificationDate( new Timestamp( fight.getModificationDate().getTime() ) );
        }
        if ( fight.getModificationId() != null )
        {
            ret.setModificationId( Long.valueOf( fight.getModificationId() ) );
        }
        return ret;
    }

    public static void mapFightToDB( DuoFight fight, DuoFight fightDB, boolean deepMapping )
    {

        if ( deepMapping )
        {
            fightDB.setDuoTeamBlue( DuoTeamMapper.mapDuoTeamFromDB( fight.getDuoTeamBlue() ) );
            fightDB.setDuoTeamRed( DuoTeamMapper.mapDuoTeamFromDB( fight.getDuoTeamRed() ) );
        }
        fightDB.setTeamIdBlue( fight.getTeamIdBlue() );
        fightDB.setTeamIdRed( fight.getTeamIdRed() );
        fightDB.setDuoclass( fight.getDuoclass() );
        // if (fight.getFlag() != null)fightDB.setFlag(new String (fight.getFlag()));
        fightDB.setFusengachi( fight.getFusengachi() );
        fightDB.setInjuryTimeBlue( fight.getInjuryTimeBlue() );
        fightDB.setInjuryTimeRed( fight.getInjuryTimeRed() );
        fightDB.setKikengachi( fight.getKikengachi() );
        fightDB.setOverallFightTime( fight.getOverallFightTime() );
        fightDB.setPointsBlue( fight.getPointsBlue() );
        fightDB.setPointsRed( fight.getPointsRed() );
        fightDB.setPointsBlueMax( fight.getPointsBlueMax() );
        fightDB.setPointsRedMax( fight.getPointsRedMax() );

        fightDB.setBlueSeriesArefI( fight.getBlueSeriesArefI() );
        fightDB.setBlueSeriesArefII( fight.getBlueSeriesArefII() );
        fightDB.setBlueSeriesArefIII( fight.getBlueSeriesArefIII() );
        fightDB.setBlueSeriesArefIV( fight.getBlueSeriesArefIV() );
        fightDB.setBlueSeriesArefV( fight.getBlueSeriesArefV() );
        fightDB.setBlueSeriesBrefI( fight.getBlueSeriesBrefI() );
        fightDB.setBlueSeriesBrefII( fight.getBlueSeriesBrefII() );
        fightDB.setBlueSeriesBrefIII( fight.getBlueSeriesBrefIII() );
        fightDB.setBlueSeriesBrefIV( fight.getBlueSeriesBrefIV() );
        fightDB.setBlueSeriesBrefV( fight.getBlueSeriesBrefV() );
        fightDB.setBlueSeriesCrefI( fight.getBlueSeriesCrefI() );
        fightDB.setBlueSeriesCrefII( fight.getBlueSeriesCrefII() );
        fightDB.setBlueSeriesCrefIII( fight.getBlueSeriesCrefIII() );
        fightDB.setBlueSeriesCrefIV( fight.getBlueSeriesCrefIV() );
        fightDB.setBlueSeriesCrefV( fight.getBlueSeriesCrefV() );
        fightDB.setBlueSeriesDrefI( fight.getBlueSeriesDrefI() );
        fightDB.setBlueSeriesDrefII( fight.getBlueSeriesDrefII() );
        fightDB.setBlueSeriesDrefIII( fight.getBlueSeriesDrefIII() );
        fightDB.setBlueSeriesDrefIV( fight.getBlueSeriesDrefIV() );
        fightDB.setBlueSeriesDrefV( fight.getBlueSeriesDrefV() );

        fightDB.setRedSeriesArefI( fight.getRedSeriesArefI() );
        fightDB.setRedSeriesArefII( fight.getRedSeriesArefII() );
        fightDB.setRedSeriesArefIII( fight.getRedSeriesArefIII() );
        fightDB.setRedSeriesArefIV( fight.getRedSeriesArefIV() );
        fightDB.setRedSeriesArefV( fight.getRedSeriesArefV() );
        fightDB.setRedSeriesBrefI( fight.getRedSeriesBrefI() );
        fightDB.setRedSeriesBrefII( fight.getRedSeriesBrefII() );
        fightDB.setRedSeriesBrefIII( fight.getRedSeriesBrefIII() );
        fightDB.setRedSeriesBrefIV( fight.getRedSeriesBrefIV() );
        fightDB.setRedSeriesBrefV( fight.getRedSeriesBrefV() );
        fightDB.setRedSeriesCrefI( fight.getRedSeriesCrefI() );
        fightDB.setRedSeriesCrefII( fight.getRedSeriesCrefII() );
        fightDB.setRedSeriesCrefIII( fight.getRedSeriesCrefIII() );
        fightDB.setRedSeriesCrefIV( fight.getRedSeriesCrefIV() );
        fightDB.setRedSeriesCrefV( fight.getRedSeriesCrefV() );
        fightDB.setRedSeriesDrefI( fight.getRedSeriesDrefI() );
        fightDB.setRedSeriesDrefII( fight.getRedSeriesDrefII() );
        fightDB.setRedSeriesDrefIII( fight.getRedSeriesDrefIII() );
        fightDB.setRedSeriesDrefIV( fight.getRedSeriesDrefIV() );
        fightDB.setRedSeriesDrefV( fight.getRedSeriesDrefV() );

        if ( fight.getWinnerId() != null )
        {
            fightDB.setWinnerId( Long.valueOf( fight.getWinnerId() ) );
        }

        fightDB.setCreateDate( new Timestamp( fight.getCreateDate().getTime() ) );
        fightDB.setCreateId( Long.valueOf( fight.getCreateId() ) );
        fightDB.setId( Long.valueOf( fight.getId() ) );
        fightDB.setModificationDate( new Timestamp( fight.getModificationDate().getTime() ) );
        fightDB.setModificationId( Long.valueOf( fight.getModificationId() ) );
    }

    public static DuoFightWeb mapFightForClockFromDB( DuoFight fight )
    {
        if ( fight == null )
        {
            return null;
        }
        DuoFightWeb ret = new DuoFightWeb();

        ret.setDuoTeamBlue( DuoTeamMapper.mapDuoTeamFromDB( fight.getDuoTeamBlue() ) );
        ret.setDuoTeamRed( DuoTeamMapper.mapDuoTeamFromDB( fight.getDuoTeamRed() ) );
        ret.setTeamIdBlue( fight.getTeamIdBlue() );
        ret.setTeamIdRed( fight.getTeamIdRed() );
        ret.setDuoclass( DuoclassMapper.mapDuoclassFromDB( fight.getDuoclass() ) );
        ret.setDuoclassId( fight.getDuoclassId() );
        if ( fight.getFlag() != null )
        {
            ret.setFlag( fight.getFlag() );
        }
        ret.setFusengachi( fight.getFusengachi() );
        ret.setInjuryTimeBlue( fight.getInjuryTimeBlue() );
        ret.setInjuryTimeRed( fight.getInjuryTimeRed() );
        ret.setKikengachi( fight.getKikengachi() );
        ret.setOverallFightTime( fight.getOverallFightTime() );
        ret.setPointsBlue( fight.getPointsBlue() );
        ret.setPointsRed( fight.getPointsRed() );
        ret.setPointsBlueMax( fight.getPointsBlueMax() );
        ret.setPointsRedMax( fight.getPointsRedMax() );

        ret.setBlueSeriesArefI( fight.getBlueSeriesArefI() );
        ret.setBlueSeriesArefII( fight.getBlueSeriesArefII() );
        ret.setBlueSeriesArefIII( fight.getBlueSeriesArefIII() );
        ret.setBlueSeriesArefIV( fight.getBlueSeriesArefIV() );
        ret.setBlueSeriesArefV( fight.getBlueSeriesArefV() );
        ret.setBlueSeriesBrefI( fight.getBlueSeriesBrefI() );
        ret.setBlueSeriesBrefII( fight.getBlueSeriesBrefII() );
        ret.setBlueSeriesBrefIII( fight.getBlueSeriesBrefIII() );
        ret.setBlueSeriesBrefIV( fight.getBlueSeriesBrefIV() );
        ret.setBlueSeriesBrefV( fight.getBlueSeriesBrefV() );
        ret.setBlueSeriesCrefI( fight.getBlueSeriesCrefI() );
        ret.setBlueSeriesCrefII( fight.getBlueSeriesCrefII() );
        ret.setBlueSeriesCrefIII( fight.getBlueSeriesCrefIII() );
        ret.setBlueSeriesCrefIV( fight.getBlueSeriesCrefIV() );
        ret.setBlueSeriesCrefV( fight.getBlueSeriesCrefV() );
        ret.setBlueSeriesDrefI( fight.getBlueSeriesDrefI() );
        ret.setBlueSeriesDrefII( fight.getBlueSeriesDrefII() );
        ret.setBlueSeriesDrefIII( fight.getBlueSeriesDrefIII() );
        ret.setBlueSeriesDrefIV( fight.getBlueSeriesDrefIV() );
        ret.setBlueSeriesDrefV( fight.getBlueSeriesDrefV() );

        ret.setRedSeriesArefI( fight.getRedSeriesArefI() );
        ret.setRedSeriesArefII( fight.getRedSeriesArefII() );
        ret.setRedSeriesArefIII( fight.getRedSeriesArefIII() );
        ret.setRedSeriesArefIV( fight.getRedSeriesArefIV() );
        ret.setRedSeriesArefV( fight.getRedSeriesArefV() );
        ret.setRedSeriesBrefI( fight.getRedSeriesBrefI() );
        ret.setRedSeriesBrefII( fight.getRedSeriesBrefII() );
        ret.setRedSeriesBrefIII( fight.getRedSeriesBrefIII() );
        ret.setRedSeriesBrefIV( fight.getRedSeriesBrefIV() );
        ret.setRedSeriesBrefV( fight.getRedSeriesBrefV() );
        ret.setRedSeriesCrefI( fight.getRedSeriesCrefI() );
        ret.setRedSeriesCrefII( fight.getRedSeriesCrefII() );
        ret.setRedSeriesCrefIII( fight.getRedSeriesCrefIII() );
        ret.setRedSeriesCrefIV( fight.getRedSeriesCrefIV() );
        ret.setRedSeriesCrefV( fight.getRedSeriesCrefV() );
        ret.setRedSeriesDrefI( fight.getRedSeriesDrefI() );
        ret.setRedSeriesDrefII( fight.getRedSeriesDrefII() );
        ret.setRedSeriesDrefIII( fight.getRedSeriesDrefIII() );
        ret.setRedSeriesDrefIV( fight.getRedSeriesDrefIV() );
        ret.setRedSeriesDrefV( fight.getRedSeriesDrefV() );

        if ( fight.getWinnerId() != null )
        {
            ret.setWinnerId( Long.valueOf( fight.getWinnerId() ) );
        }

        if ( fight.getCreateDate() != null )
        {
            ret.setCreateDate( new Timestamp( fight.getCreateDate().getTime() ) );
        }
        if ( fight.getCreateId() != null )
        {
            ret.setCreateId( Long.valueOf( fight.getCreateId() ) );
        }
        if ( fight.getId() != null )
        {
            ret.setId( Long.valueOf( fight.getId() ) );
        }
        if ( fight.getModificationDate() != null )
        {
            ret.setModificationDate( new Timestamp( fight.getModificationDate().getTime() ) );
        }
        if ( fight.getModificationId() != null )
        {
            ret.setModificationId( Long.valueOf( fight.getModificationId() ) );
        }
        return ret;
    }

    public static DuoFightWeb mapFightFromDBForWeb( FriendlyDuoFight fight )
    {
        if ( fight == null )
        {
            return null;
        }
        DuoFightWeb ret = new DuoFightWeb();

        ret.setDuoTeamBlue( DuoTeamMapper.mapDuoTeamFromDB( fight.getDuoTeamBlue() ) );
        ret.setDuoTeamRed( DuoTeamMapper.mapDuoTeamFromDB( fight.getDuoTeamRed() ) );
        ret.setTeamIdBlue( fight.getTeamIdBlue() );
        ret.setTeamIdRed( fight.getTeamIdRed() );
        ret.setDuoclassId( fight.getDuoclassId() );
        if ( fight.getFlag() != null )
        {
            ret.setFlag( fight.getFlag() );
        }
        ret.setFusengachi( fight.getFusengachi() );
        ret.setInjuryTimeBlue( fight.getInjuryTimeBlue() );
        ret.setInjuryTimeRed( fight.getInjuryTimeRed() );
        ret.setKikengachi( fight.getKikengachi() );
        ret.setOverallFightTime( fight.getOverallFightTime() );
        ret.setPointsBlue( fight.getPointsBlue() );
        ret.setPointsRed( fight.getPointsRed() );
        ret.setPointsBlueMax( fight.getPointsBlueMax() );
        ret.setPointsRedMax( fight.getPointsRedMax() );

        ret.setBlueSeriesArefI( fight.getBlueSeriesArefI() );
        ret.setBlueSeriesArefII( fight.getBlueSeriesArefII() );
        ret.setBlueSeriesArefIII( fight.getBlueSeriesArefIII() );
        ret.setBlueSeriesArefIV( fight.getBlueSeriesArefIV() );
        ret.setBlueSeriesArefV( fight.getBlueSeriesArefV() );
        ret.setBlueSeriesBrefI( fight.getBlueSeriesBrefI() );
        ret.setBlueSeriesBrefII( fight.getBlueSeriesBrefII() );
        ret.setBlueSeriesBrefIII( fight.getBlueSeriesBrefIII() );
        ret.setBlueSeriesBrefIV( fight.getBlueSeriesBrefIV() );
        ret.setBlueSeriesBrefV( fight.getBlueSeriesBrefV() );
        ret.setBlueSeriesCrefI( fight.getBlueSeriesCrefI() );
        ret.setBlueSeriesCrefII( fight.getBlueSeriesCrefII() );
        ret.setBlueSeriesCrefIII( fight.getBlueSeriesCrefIII() );
        ret.setBlueSeriesCrefIV( fight.getBlueSeriesCrefIV() );
        ret.setBlueSeriesCrefV( fight.getBlueSeriesCrefV() );
        ret.setBlueSeriesDrefI( fight.getBlueSeriesDrefI() );
        ret.setBlueSeriesDrefII( fight.getBlueSeriesDrefII() );
        ret.setBlueSeriesDrefIII( fight.getBlueSeriesDrefIII() );
        ret.setBlueSeriesDrefIV( fight.getBlueSeriesDrefIV() );
        ret.setBlueSeriesDrefV( fight.getBlueSeriesDrefV() );

        ret.setRedSeriesArefI( fight.getRedSeriesArefI() );
        ret.setRedSeriesArefII( fight.getRedSeriesArefII() );
        ret.setRedSeriesArefIII( fight.getRedSeriesArefIII() );
        ret.setRedSeriesArefIV( fight.getRedSeriesArefIV() );
        ret.setRedSeriesArefV( fight.getRedSeriesArefV() );
        ret.setRedSeriesBrefI( fight.getRedSeriesBrefI() );
        ret.setRedSeriesBrefII( fight.getRedSeriesBrefII() );
        ret.setRedSeriesBrefIII( fight.getRedSeriesBrefIII() );
        ret.setRedSeriesBrefIV( fight.getRedSeriesBrefIV() );
        ret.setRedSeriesBrefV( fight.getRedSeriesBrefV() );
        ret.setRedSeriesCrefI( fight.getRedSeriesCrefI() );
        ret.setRedSeriesCrefII( fight.getRedSeriesCrefII() );
        ret.setRedSeriesCrefIII( fight.getRedSeriesCrefIII() );
        ret.setRedSeriesCrefIV( fight.getRedSeriesCrefIV() );
        ret.setRedSeriesCrefV( fight.getRedSeriesCrefV() );
        ret.setRedSeriesDrefI( fight.getRedSeriesDrefI() );
        ret.setRedSeriesDrefII( fight.getRedSeriesDrefII() );
        ret.setRedSeriesDrefIII( fight.getRedSeriesDrefIII() );
        ret.setRedSeriesDrefIV( fight.getRedSeriesDrefIV() );
        ret.setRedSeriesDrefV( fight.getRedSeriesDrefV() );

        if ( fight.getWinnerId() != null )
        {
            ret.setWinnerId( Long.valueOf( fight.getWinnerId() ) );
        }

        if ( fight.getCreateDate() != null )
        {
            ret.setCreateDate( new Timestamp( fight.getCreateDate().getTime() ) );
        }
        if ( fight.getCreateId() != null )
        {
            ret.setCreateId( Long.valueOf( fight.getCreateId() ) );
        }
        if ( fight.getId() != null )
        {
            ret.setId( Long.valueOf( fight.getId() ) );
        }
        if ( fight.getModificationDate() != null )
        {
            ret.setModificationDate( new Timestamp( fight.getModificationDate().getTime() ) );
        }
        if ( fight.getModificationId() != null )
        {
            ret.setModificationId( Long.valueOf( fight.getModificationId() ) );
        }
        return ret;
    }

    public static FriendlyDuoFight mapFriendlyFightFromDB( FriendlyDuoFight fight )
    {
        if ( fight == null )
        {
            return null;
        }
        FriendlyDuoFight ret = new FriendlyDuoFight();

        ret.setDuoTeamBlue( DuoTeamMapper.mapDuoTeamFromDB( fight.getDuoTeamBlue() ) );
        ret.setDuoTeamRed( DuoTeamMapper.mapDuoTeamFromDB( fight.getDuoTeamRed() ) );
        ret.setTeamIdBlue( fight.getTeamIdBlue() );
        ret.setTeamIdRed( fight.getTeamIdRed() );
        ret.setDuoclassId( fight.getDuoclassId() );
        if ( fight.getFlag() != null )
        {
            ret.setFlag( fight.getFlag() );
        }
        ret.setFusengachi( fight.getFusengachi() );
        ret.setInjuryTimeBlue( fight.getInjuryTimeBlue() );
        ret.setInjuryTimeRed( fight.getInjuryTimeRed() );
        ret.setKikengachi( fight.getKikengachi() );
        ret.setOverallFightTime( fight.getOverallFightTime() );
        ret.setPointsBlue( fight.getPointsBlue() );
        ret.setPointsRed( fight.getPointsRed() );
        ret.setPointsBlueMax( fight.getPointsBlueMax() );
        ret.setPointsRedMax( fight.getPointsRedMax() );

        ret.setBlueSeriesArefI( fight.getBlueSeriesArefI() );
        ret.setBlueSeriesArefII( fight.getBlueSeriesArefII() );
        ret.setBlueSeriesArefIII( fight.getBlueSeriesArefIII() );
        ret.setBlueSeriesArefIV( fight.getBlueSeriesArefIV() );
        ret.setBlueSeriesArefV( fight.getBlueSeriesArefV() );
        ret.setBlueSeriesBrefI( fight.getBlueSeriesBrefI() );
        ret.setBlueSeriesBrefII( fight.getBlueSeriesBrefII() );
        ret.setBlueSeriesBrefIII( fight.getBlueSeriesBrefIII() );
        ret.setBlueSeriesBrefIV( fight.getBlueSeriesBrefIV() );
        ret.setBlueSeriesBrefV( fight.getBlueSeriesBrefV() );
        ret.setBlueSeriesCrefI( fight.getBlueSeriesCrefI() );
        ret.setBlueSeriesCrefII( fight.getBlueSeriesCrefII() );
        ret.setBlueSeriesCrefIII( fight.getBlueSeriesCrefIII() );
        ret.setBlueSeriesCrefIV( fight.getBlueSeriesCrefIV() );
        ret.setBlueSeriesCrefV( fight.getBlueSeriesCrefV() );
        ret.setBlueSeriesDrefI( fight.getBlueSeriesDrefI() );
        ret.setBlueSeriesDrefII( fight.getBlueSeriesDrefII() );
        ret.setBlueSeriesDrefIII( fight.getBlueSeriesDrefIII() );
        ret.setBlueSeriesDrefIV( fight.getBlueSeriesDrefIV() );
        ret.setBlueSeriesDrefV( fight.getBlueSeriesDrefV() );

        ret.setRedSeriesArefI( fight.getRedSeriesArefI() );
        ret.setRedSeriesArefII( fight.getRedSeriesArefII() );
        ret.setRedSeriesArefIII( fight.getRedSeriesArefIII() );
        ret.setRedSeriesArefIV( fight.getRedSeriesArefIV() );
        ret.setRedSeriesArefV( fight.getRedSeriesArefV() );
        ret.setRedSeriesBrefI( fight.getRedSeriesBrefI() );
        ret.setRedSeriesBrefII( fight.getRedSeriesBrefII() );
        ret.setRedSeriesBrefIII( fight.getRedSeriesBrefIII() );
        ret.setRedSeriesBrefIV( fight.getRedSeriesBrefIV() );
        ret.setRedSeriesBrefV( fight.getRedSeriesBrefV() );
        ret.setRedSeriesCrefI( fight.getRedSeriesCrefI() );
        ret.setRedSeriesCrefII( fight.getRedSeriesCrefII() );
        ret.setRedSeriesCrefIII( fight.getRedSeriesCrefIII() );
        ret.setRedSeriesCrefIV( fight.getRedSeriesCrefIV() );
        ret.setRedSeriesCrefV( fight.getRedSeriesCrefV() );
        ret.setRedSeriesDrefI( fight.getRedSeriesDrefI() );
        ret.setRedSeriesDrefII( fight.getRedSeriesDrefII() );
        ret.setRedSeriesDrefIII( fight.getRedSeriesDrefIII() );
        ret.setRedSeriesDrefIV( fight.getRedSeriesDrefIV() );
        ret.setRedSeriesDrefV( fight.getRedSeriesDrefV() );

        if ( fight.getWinnerId() != null )
        {
            ret.setWinnerId( Long.valueOf( fight.getWinnerId() ) );
        }

        if ( fight.getCreateDate() != null )
        {
            ret.setCreateDate( new Timestamp( fight.getCreateDate().getTime() ) );
        }
        if ( fight.getCreateId() != null )
        {
            ret.setCreateId( Long.valueOf( fight.getCreateId() ) );
        }
        if ( fight.getId() != null )
        {
            ret.setId( Long.valueOf( fight.getId() ) );
        }
        if ( fight.getModificationDate() != null )
        {
            ret.setModificationDate( new Timestamp( fight.getModificationDate().getTime() ) );
        }
        if ( fight.getModificationId() != null )
        {
            ret.setModificationId( Long.valueOf( fight.getModificationId() ) );
        }
        return ret;
    }

    public static DuoFightWeb mapFriendlyFightForClockFromDB( FriendlyDuoFight fight )
    {
        if ( fight == null )
        {
            return null;
        }
        DuoFightWeb ret = new DuoFightWeb();

        ret.setDuoTeamBlue( DuoTeamMapper.mapDuoTeamFromDB( fight.getDuoTeamBlue() ) );
        ret.setDuoTeamRed( DuoTeamMapper.mapDuoTeamFromDB( fight.getDuoTeamRed() ) );
        ret.setTeamIdBlue( fight.getTeamIdBlue() );
        ret.setTeamIdRed( fight.getTeamIdRed() );
        //
        // if ( fight.getDuoclass() != null )
        // ret.setDuoclass( DuoclassMapper.mapDuoclassFromDB( fight.getDuoclass() ) );

        // ret.setDuoclassId( fight.getDuoclassId() );
        if ( fight.getFlag() != null )
        {
            ret.setFlag( fight.getFlag() );
        }
        ret.setFusengachi( fight.getFusengachi() );
        ret.setInjuryTimeBlue( fight.getInjuryTimeBlue() );
        ret.setInjuryTimeRed( fight.getInjuryTimeRed() );
        ret.setKikengachi( fight.getKikengachi() );
        ret.setOverallFightTime( fight.getOverallFightTime() );
        ret.setPointsBlue( fight.getPointsBlue() );
        ret.setPointsRed( fight.getPointsRed() );
        ret.setPointsBlueMax( fight.getPointsBlueMax() );
        ret.setPointsRedMax( fight.getPointsRedMax() );

        ret.setBlueSeriesArefI( fight.getBlueSeriesArefI() );
        ret.setBlueSeriesArefII( fight.getBlueSeriesArefII() );
        ret.setBlueSeriesArefIII( fight.getBlueSeriesArefIII() );
        ret.setBlueSeriesArefIV( fight.getBlueSeriesArefIV() );
        ret.setBlueSeriesArefV( fight.getBlueSeriesArefV() );
        ret.setBlueSeriesBrefI( fight.getBlueSeriesBrefI() );
        ret.setBlueSeriesBrefII( fight.getBlueSeriesBrefII() );
        ret.setBlueSeriesBrefIII( fight.getBlueSeriesBrefIII() );
        ret.setBlueSeriesBrefIV( fight.getBlueSeriesBrefIV() );
        ret.setBlueSeriesBrefV( fight.getBlueSeriesBrefV() );
        ret.setBlueSeriesCrefI( fight.getBlueSeriesCrefI() );
        ret.setBlueSeriesCrefII( fight.getBlueSeriesCrefII() );
        ret.setBlueSeriesCrefIII( fight.getBlueSeriesCrefIII() );
        ret.setBlueSeriesCrefIV( fight.getBlueSeriesCrefIV() );
        ret.setBlueSeriesCrefV( fight.getBlueSeriesCrefV() );
        ret.setBlueSeriesDrefI( fight.getBlueSeriesDrefI() );
        ret.setBlueSeriesDrefII( fight.getBlueSeriesDrefII() );
        ret.setBlueSeriesDrefIII( fight.getBlueSeriesDrefIII() );
        ret.setBlueSeriesDrefIV( fight.getBlueSeriesDrefIV() );
        ret.setBlueSeriesDrefV( fight.getBlueSeriesDrefV() );

        ret.setRedSeriesArefI( fight.getRedSeriesArefI() );
        ret.setRedSeriesArefII( fight.getRedSeriesArefII() );
        ret.setRedSeriesArefIII( fight.getRedSeriesArefIII() );
        ret.setRedSeriesArefIV( fight.getRedSeriesArefIV() );
        ret.setRedSeriesArefV( fight.getRedSeriesArefV() );
        ret.setRedSeriesBrefI( fight.getRedSeriesBrefI() );
        ret.setRedSeriesBrefII( fight.getRedSeriesBrefII() );
        ret.setRedSeriesBrefIII( fight.getRedSeriesBrefIII() );
        ret.setRedSeriesBrefIV( fight.getRedSeriesBrefIV() );
        ret.setRedSeriesBrefV( fight.getRedSeriesBrefV() );
        ret.setRedSeriesCrefI( fight.getRedSeriesCrefI() );
        ret.setRedSeriesCrefII( fight.getRedSeriesCrefII() );
        ret.setRedSeriesCrefIII( fight.getRedSeriesCrefIII() );
        ret.setRedSeriesCrefIV( fight.getRedSeriesCrefIV() );
        ret.setRedSeriesCrefV( fight.getRedSeriesCrefV() );
        ret.setRedSeriesDrefI( fight.getRedSeriesDrefI() );
        ret.setRedSeriesDrefII( fight.getRedSeriesDrefII() );
        ret.setRedSeriesDrefIII( fight.getRedSeriesDrefIII() );
        ret.setRedSeriesDrefIV( fight.getRedSeriesDrefIV() );
        ret.setRedSeriesDrefV( fight.getRedSeriesDrefV() );

        if ( fight.getWinnerId() != null )
        {
            ret.setWinnerId( Long.valueOf( fight.getWinnerId() ) );
        }

        if ( fight.getCreateDate() != null )
        {
            ret.setCreateDate( new Timestamp( fight.getCreateDate().getTime() ) );
        }
        if ( fight.getCreateId() != null )
        {
            ret.setCreateId( Long.valueOf( fight.getCreateId() ) );
        }
        if ( fight.getId() != null )
        {
            ret.setId( Long.valueOf( fight.getId() ) );
        }
        if ( fight.getModificationDate() != null )
        {
            ret.setModificationDate( new Timestamp( fight.getModificationDate().getTime() ) );
        }
        if ( fight.getModificationId() != null )
        {
            ret.setModificationId( Long.valueOf( fight.getModificationId() ) );
        }
        return ret;
    }

    public static void mapFriendlyFightToDB( FriendlyDuoFight fight, FriendlyDuoFight fightDB, boolean deepMapping )
    {

        if ( deepMapping )
        {
            fightDB.setDuoTeamBlue( DuoTeamMapper.mapDuoTeamFromDB( fight.getDuoTeamBlue() ) );
            fightDB.setDuoTeamRed( DuoTeamMapper.mapDuoTeamFromDB( fight.getDuoTeamRed() ) );
        }
        fightDB.setTeamIdBlue( fight.getTeamIdBlue() );
        fightDB.setTeamIdRed( fight.getTeamIdRed() );
        fightDB.setDuoclass( fight.getDuoclass() );
        // if (fight.getFlag() != null)fightDB.setFlag(new String (fight.getFlag()));
        fightDB.setFusengachi( fight.getFusengachi() );
        fightDB.setInjuryTimeBlue( fight.getInjuryTimeBlue() );
        fightDB.setInjuryTimeRed( fight.getInjuryTimeRed() );
        fightDB.setKikengachi( fight.getKikengachi() );
        fightDB.setOverallFightTime( fight.getOverallFightTime() );
        fightDB.setPointsBlue( fight.getPointsBlue() );
        fightDB.setPointsRed( fight.getPointsRed() );
        fightDB.setPointsBlueMax( fight.getPointsBlueMax() );
        fightDB.setPointsRedMax( fight.getPointsRedMax() );

        fightDB.setBlueSeriesArefI( fight.getBlueSeriesArefI() );
        fightDB.setBlueSeriesArefII( fight.getBlueSeriesArefII() );
        fightDB.setBlueSeriesArefIII( fight.getBlueSeriesArefIII() );
        fightDB.setBlueSeriesArefIV( fight.getBlueSeriesArefIV() );
        fightDB.setBlueSeriesArefV( fight.getBlueSeriesArefV() );
        fightDB.setBlueSeriesBrefI( fight.getBlueSeriesBrefI() );
        fightDB.setBlueSeriesBrefII( fight.getBlueSeriesBrefII() );
        fightDB.setBlueSeriesBrefIII( fight.getBlueSeriesBrefIII() );
        fightDB.setBlueSeriesBrefIV( fight.getBlueSeriesBrefIV() );
        fightDB.setBlueSeriesBrefV( fight.getBlueSeriesBrefV() );
        fightDB.setBlueSeriesCrefI( fight.getBlueSeriesCrefI() );
        fightDB.setBlueSeriesCrefII( fight.getBlueSeriesCrefII() );
        fightDB.setBlueSeriesCrefIII( fight.getBlueSeriesCrefIII() );
        fightDB.setBlueSeriesCrefIV( fight.getBlueSeriesCrefIV() );
        fightDB.setBlueSeriesCrefV( fight.getBlueSeriesCrefV() );
        fightDB.setBlueSeriesDrefI( fight.getBlueSeriesDrefI() );
        fightDB.setBlueSeriesDrefII( fight.getBlueSeriesDrefII() );
        fightDB.setBlueSeriesDrefIII( fight.getBlueSeriesDrefIII() );
        fightDB.setBlueSeriesDrefIV( fight.getBlueSeriesDrefIV() );
        fightDB.setBlueSeriesDrefV( fight.getBlueSeriesDrefV() );

        fightDB.setRedSeriesArefI( fight.getRedSeriesArefI() );
        fightDB.setRedSeriesArefII( fight.getRedSeriesArefII() );
        fightDB.setRedSeriesArefIII( fight.getRedSeriesArefIII() );
        fightDB.setRedSeriesArefIV( fight.getRedSeriesArefIV() );
        fightDB.setRedSeriesArefV( fight.getRedSeriesArefV() );
        fightDB.setRedSeriesBrefI( fight.getRedSeriesBrefI() );
        fightDB.setRedSeriesBrefII( fight.getRedSeriesBrefII() );
        fightDB.setRedSeriesBrefIII( fight.getRedSeriesBrefIII() );
        fightDB.setRedSeriesBrefIV( fight.getRedSeriesBrefIV() );
        fightDB.setRedSeriesBrefV( fight.getRedSeriesBrefV() );
        fightDB.setRedSeriesCrefI( fight.getRedSeriesCrefI() );
        fightDB.setRedSeriesCrefII( fight.getRedSeriesCrefII() );
        fightDB.setRedSeriesCrefIII( fight.getRedSeriesCrefIII() );
        fightDB.setRedSeriesCrefIV( fight.getRedSeriesCrefIV() );
        fightDB.setRedSeriesCrefV( fight.getRedSeriesCrefV() );
        fightDB.setRedSeriesDrefI( fight.getRedSeriesDrefI() );
        fightDB.setRedSeriesDrefII( fight.getRedSeriesDrefII() );
        fightDB.setRedSeriesDrefIII( fight.getRedSeriesDrefIII() );
        fightDB.setRedSeriesDrefIV( fight.getRedSeriesDrefIV() );
        fightDB.setRedSeriesDrefV( fight.getRedSeriesDrefV() );

        if ( fight.getWinnerId() != null )
        {
            fightDB.setWinnerId( Long.valueOf( fight.getWinnerId() ) );
        }

        fightDB.setCreateDate( new Timestamp( fight.getCreateDate().getTime() ) );
        fightDB.setCreateId( Long.valueOf( fight.getCreateId() ) );
        fightDB.setId( Long.valueOf( fight.getId() ) );
        fightDB.setModificationDate( new Timestamp( fight.getModificationDate().getTime() ) );
        fightDB.setModificationId( Long.valueOf( fight.getModificationId() ) );
    }
}
