/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : NewaFightMapper.java
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

package de.jjw.service.mapper.newa;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.model.newa.FriendlyNewaFight;
import de.jjw.model.newa.NewaFight;
import de.jjw.service.modelWeb.NewaFightWeb;
import de.jjw.service.util.IGlobalProjectConstants;
import de.jjw.util.ICodestableTypeConstants;
import de.jjw.util.TypeUtil;

public class NewaFightMapper
    implements ICodestableTypeConstants, IGlobalProjectConstants
{

    public static List<NewaFightWeb> mapFriendlyFightListFromDB( List<FriendlyNewaFight> fights )
    {
        List<NewaFightWeb> ret = new ArrayList<NewaFightWeb>( fights.size() + 1 );
        NewaFightWeb fightWeb = null;
        for ( FriendlyNewaFight item : fights )
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

    public static NewaFight mapFightFromDB( NewaFight fight )
    {
        if ( fight == null )
        {
            return null;
        }
        NewaFight ret = new NewaFight();

        ret.setChuiBlue( fight.getChuiBlue() );
        ret.setChuiRed( fight.getChuiRed() );
        ret.setFighterBlue( NewaFighterMapper.mapFighterFromDB( fight.getFighterBlue() ) );
        ret.setFighterRed( NewaFighterMapper.mapFighterFromDB( fight.getFighterRed() ) );
        ret.setFighterIdBlue( fight.getFighterIdBlue() );
        ret.setFighterIdRed( fight.getFighterIdRed() );
        //ret.setFightingclass(WeightclassMapper.mapWeightclassFromDB(fight.getFighterBlue().getFightingclass()));
        ret.setNewaclassId( fight.getNewaclassId() );
        ret.setFightTime( fight.getFightTime() );
        ret.setFightTimeWithBreaks( fight.getFightTimeWithBreaks() );
        if ( fight.getFlag() != null )
        {
            ret.setFlag( new String( fight.getFlag() ) );
        }
        ret.setFusengachi( fight.getFusengachi() );
        ret.setHansokumakeBlue( fight.getHansokumakeBlue() );
        ret.setHansokumakeRed( fight.getHansokumakeRed() );
        ret.setInjuryTimeBlue( fight.getInjuryTimeBlue() );
        ret.setInjuryTimeRed( fight.getInjuryTimeRed() );
        ret.setKikengachi( fight.getKikengachi() );
        ret.setOverallFightTime( fight.getOverallFightTime() );
        ret.setPointsBlue( fight.getPointsBlue() );
        ret.setPointsRed( fight.getPointsRed() );
        ret.setPointsBlueOnClock( fight.getPointsBlueOnClock() );
        ret.setPointsRedOnClock( fight.getPointsRedOnClock() );
        ret.setModifiedWith2Call( fight.isModifiedWith2Call() );
        ret.setSaveTime( new Timestamp( fight.getSaveTime().getTime() ) );
        if ( fight.getProtokoll() != null )
        {
            ret.setProtokoll( new String( fight.getProtokoll() ) );
        }
        ret.setShidoBlue( fight.getShidoBlue() );
        ret.setShidoRed( fight.getShidoRed() );
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

    public static NewaFightWeb mapFightFromDBForWeb( NewaFight fight )
    {
        if ( fight == null )
        {
            return null;
        }
        NewaFightWeb ret = new NewaFightWeb();

        ret.setChuiBlue( fight.getChuiBlue() );
        ret.setChuiRed( fight.getChuiRed() );
        ret.setFighterBlue( NewaFighterMapper.mapFighterFromDB( fight.getFighterBlue() ) );
        ret.setFighterRed( NewaFighterMapper.mapFighterFromDB( fight.getFighterRed() ) );
        ret.setFighterIdBlue( fight.getFighterIdBlue() );
        ret.setFighterIdRed( fight.getFighterIdRed() );
        // ret.setFightingclass(WeightclassMapper.mapWeightclassFromDB(fight.getFighterBlue().getFightingclass()));
        ret.setNewaclassId( fight.getNewaclassId() );
        ret.setFightTime( fight.getFightTime() );
        ret.setFightTimeWithBreaks( fight.getFightTimeWithBreaks() );
        if ( fight.getFlag() != null )
        {
            ret.setFlag( new String( fight.getFlag() ) );
        }
        ret.setFusengachi( fight.getFusengachi() );
        ret.setHansokumakeBlue( fight.getHansokumakeBlue() );
        ret.setHansokumakeRed( fight.getHansokumakeRed() );
        ret.setInjuryTimeBlue( fight.getInjuryTimeBlue() );
        ret.setInjuryTimeRed( fight.getInjuryTimeRed() );

        ret.setKikengachi( fight.getKikengachi() );
        ret.setOverallFightTime( fight.getOverallFightTime() );
        ret.setPointsBlue( fight.getPointsBlue() );
        ret.setPointsRed( fight.getPointsRed() );
        ret.setPointsBlueOnClock( fight.getPointsBlueOnClock() );
        ret.setPointsRedOnClock( fight.getPointsRedOnClock() );
        ret.setModifiedWith2Call( fight.isModifiedWith2Call() );
        ret.setSaveTime( new Timestamp( fight.getSaveTime().getTime() ) );
        if ( fight.getProtokoll() != null )
        {
            ret.setProtokoll( new String( fight.getProtokoll() ) );
        }
        ret.setShidoBlue( fight.getShidoBlue() );
        ret.setShidoRed( fight.getShidoRed() );
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

    public static void mapFightToDB( NewaFight fight, NewaFight fightDB, boolean deepMapping )
    {

        fightDB.setChuiBlue( fight.getChuiBlue() );
        fightDB.setChuiRed( fight.getChuiRed() );
        if ( deepMapping )
        {
            fightDB.setFighterBlue( NewaFighterMapper.mapFighterFromDB( fight.getFighterBlue() ) );
            fightDB.setFighterRed( NewaFighterMapper.mapFighterFromDB( fight.getFighterRed() ) );
        }
        fightDB.setFighterIdBlue( fight.getFighterIdBlue() );
        fightDB.setFighterIdRed( fight.getFighterIdRed() );
        // TODOfightDB.setFightingclass(fightingclass);
        fightDB.setFightTime( fight.getFightTime() );
        fightDB.setFightTimeWithBreaks( fight.getFightTimeWithBreaks() );
        //if (fight.getFlag() != null)fightDB.setFlag(new String (fight.getFlag()));
        fightDB.setFusengachi( fight.getFusengachi() );
        fightDB.setHansokumakeBlue( fight.getHansokumakeBlue() );
        fightDB.setHansokumakeRed( fight.getHansokumakeRed() );
        fightDB.setInjuryTimeBlue( fight.getInjuryTimeBlue() );
        fightDB.setInjuryTimeRed( fight.getInjuryTimeRed() );
        fightDB.setKikengachi( fight.getKikengachi() );
        fightDB.setOverallFightTime( fight.getOverallFightTime() );
        fightDB.setPointsBlue( fight.getPointsBlue() );
        fightDB.setPointsRed( fight.getPointsRed() );
        fightDB.setPointsBlueOnClock( fight.getPointsBlueOnClock() );
        fightDB.setPointsRedOnClock( fight.getPointsRedOnClock() );
        fightDB.setModifiedWith2Call( fight.isModifiedWith2Call() );
        fightDB.setSaveTime( new Timestamp( fight.getSaveTime().getTime() ) );

        if ( fight.getProtokoll() != null )
        {
            fightDB.setProtokoll( new String( fight.getProtokoll() ) );
        }
        fightDB.setShidoBlue( fight.getShidoBlue() );
        fightDB.setShidoRed( fight.getShidoRed() );
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

    public static NewaFightWeb mapFightForClockFromDB( NewaFight fight, Locale local )
    {
        if ( fight == null )
        {
            return null;
        }
        NewaFightWeb ret = new NewaFightWeb();

        ret.setChuiBlue( fight.getChuiBlue() );
        ret.setChuiRed( fight.getChuiRed() );
        ret.setFighterBlue( NewaFighterMapper.mapFighterFromDB( fight.getFighterBlue() ) );
        ret.setFighterRed( NewaFighterMapper.mapFighterFromDB( fight.getFighterRed() ) );
        ret.setFighterIdBlue( fight.getFighterIdBlue() );
        ret.setFighterIdRed( fight.getFighterIdRed() );
        ret.setNewaclass( NewaWeightclassMapper.mapWeightclassFromDB( fight.getFighterBlue().getNewaclass() ) );
        ret.getNewaclass().setSexWeb( CodestableMain.getInstance().getCodestableCodeValueByType( TYPE_SEX,
                                                                                                     ret.getNewaclass().getSex(),
                                                                                                     local ) );

        ret.setNewaclassId( fight.getNewaclassId() );
        ret.setFightTime( fight.getFightTime() );
        ret.setFightTimeWithBreaks( fight.getFightTimeWithBreaks() );
        if ( fight.getFlag() != null )
        {
            ret.setFlag( new String( fight.getFlag() ) );
        }
        ret.setFusengachi( fight.getFusengachi() );
        ret.setHansokumakeBlue( fight.getHansokumakeBlue() );
        ret.setHansokumakeRed( fight.getHansokumakeRed() );
        ret.setInjuryTimeBlue( fight.getInjuryTimeBlue() );
        ret.setInjuryTimeRed( fight.getInjuryTimeRed() );
        ret.setKikengachi( fight.getKikengachi() );
        ret.setOverallFightTime( fight.getOverallFightTime() );
        ret.setPointsBlue( fight.getPointsBlue() );
        ret.setPointsRed( fight.getPointsRed() );
        if ( fight.getProtokoll() != null )
        {
            ret.setProtokoll( new String( fight.getProtokoll() ) );
        }
        ret.setShidoBlue( fight.getShidoBlue() );
        ret.setShidoRed( fight.getShidoRed() );
        ret.setModifiedWith2Call( fight.isModifiedWith2Call() );
        ret.setSaveTime( new Timestamp( fight.getSaveTime().getTime() ) );

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

    public static void mapFriendlyFightToDB( FriendlyNewaFight fight, FriendlyNewaFight fightDB, boolean deepMapping )
    {

        fightDB.setChuiBlue( fight.getChuiBlue() );
        fightDB.setChuiRed( fight.getChuiRed() );
        if ( deepMapping )
        {
            fightDB.setFighterBlue( NewaFighterMapper.mapFighterFromDB( fight.getFighterBlue() ) );
            fightDB.setFighterRed( NewaFighterMapper.mapFighterFromDB( fight.getFighterRed() ) );
        }
        fightDB.setFighterIdBlue( fight.getFighterIdBlue() );
        fightDB.setFighterIdRed( fight.getFighterIdRed() );
        // TODOfightDB.setFightingclass(fightingclass);
        fightDB.setFightTime( fight.getFightTime() );
        fightDB.setFightTimeWithBreaks( fight.getFightTimeWithBreaks() );
        // if (fight.getFlag() != null)fightDB.setFlag(new String (fight.getFlag()));
        fightDB.setFusengachi( fight.getFusengachi() );
        fightDB.setHansokumakeBlue( fight.getHansokumakeBlue() );
        fightDB.setHansokumakeRed( fight.getHansokumakeRed() );
        fightDB.setInjuryTimeBlue( fight.getInjuryTimeBlue() );
        fightDB.setInjuryTimeRed( fight.getInjuryTimeRed() );
        fightDB.setKikengachi( fight.getKikengachi() );
        fightDB.setOverallFightTime( fight.getOverallFightTime() );
        fightDB.setPointsBlue( fight.getPointsBlue() );
        fightDB.setPointsRed( fight.getPointsRed() );
        fightDB.setPointsBlueOnClock( fight.getPointsBlueOnClock() );
        fightDB.setPointsRedOnClock( fight.getPointsRedOnClock() );
        fightDB.setModifiedWith2Call( fight.isModifiedWith2Call() );
        fightDB.setSaveTime( new Timestamp( fight.getSaveTime().getTime() ) );

        if ( fight.getProtokoll() != null )
        {
            fightDB.setProtokoll( new String( fight.getProtokoll() ) );
        }
        fightDB.setShidoBlue( fight.getShidoBlue() );
        fightDB.setShidoRed( fight.getShidoRed() );
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

    public static FriendlyNewaFight mapFriendlyFightFromDB( FriendlyNewaFight fight )
    {
        if ( fight == null )
        {
            return null;
        }
        FriendlyNewaFight ret = new FriendlyNewaFight();

        ret.setChuiBlue( fight.getChuiBlue() );
        ret.setChuiRed( fight.getChuiRed() );
        ret.setFighterBlue( NewaFighterMapper.mapFighterFromDB( fight.getFighterBlue() ) );
        ret.setFighterRed( NewaFighterMapper.mapFighterFromDB( fight.getFighterRed() ) );
        ret.setFighterIdBlue( fight.getFighterIdBlue() );
        ret.setFighterIdRed( fight.getFighterIdRed() );
        // ret.setFightingclass(WeightclassMapper.mapWeightclassFromDB(fight.getFighterBlue().getFightingclass()));
        ret.setNewaclassId( fight.getNewaclassId() );
        ret.setFightTime( fight.getFightTime() );
        ret.setFightTimeWithBreaks( fight.getFightTimeWithBreaks() );
        if ( fight.getFlag() != null )
        {
            ret.setFlag( new String( fight.getFlag() ) );
        }
        ret.setFusengachi( fight.getFusengachi() );
        ret.setHansokumakeBlue( fight.getHansokumakeBlue() );
        ret.setHansokumakeRed( fight.getHansokumakeRed() );
        ret.setInjuryTimeBlue( fight.getInjuryTimeBlue() );
        ret.setInjuryTimeRed( fight.getInjuryTimeRed() );
        ret.setKikengachi( fight.getKikengachi() );
        ret.setOverallFightTime( fight.getOverallFightTime() );
        ret.setPointsBlue( fight.getPointsBlue() );
        ret.setPointsRed( fight.getPointsRed() );
        ret.setPointsBlueOnClock( fight.getPointsBlueOnClock() );
        ret.setPointsRedOnClock( fight.getPointsRedOnClock() );
        ret.setModifiedWith2Call( fight.isModifiedWith2Call() );
        ret.setSaveTime( new Timestamp( fight.getSaveTime().getTime() ) );
        if ( fight.getProtokoll() != null )
        {
            ret.setProtokoll( new String( fight.getProtokoll() ) );
        }
        ret.setShidoBlue( fight.getShidoBlue() );
        ret.setShidoRed( fight.getShidoRed() );
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

    public static NewaFightWeb mapFriendlyFightForClockFromDB( FriendlyNewaFight fight, Locale local )
    {
        if ( fight == null )
        {
            return null;
        }
        NewaFightWeb ret = new NewaFightWeb();

        ret.setChuiBlue( fight.getChuiBlue() );
        ret.setChuiRed( fight.getChuiRed() );
        ret.setFighterBlue( NewaFighterMapper.mapFighterFromDB( fight.getFighterBlue() ) );
        ret.setFighterRed( NewaFighterMapper.mapFighterFromDB( fight.getFighterRed() ) );
        ret.setFighterIdBlue( fight.getFighterIdBlue() );
        ret.setFighterIdRed( fight.getFighterIdRed() );
        ret.setNewaclass( NewaWeightclassMapper.mapWeightclassFromDB( fight.getFighterBlue().getNewaclass() ) );
        ret.getNewaclass().setSexWeb( CodestableMain.getInstance().getCodestableCodeValueByType( TYPE_SEX,
                                                                                                     ret.getNewaclass().getSex(),
                                                                                                     local ) );

        ret.setNewaclassId( fight.getNewaclassId() );
        ret.setFightTime( fight.getFightTime() );
        ret.setFightTimeWithBreaks( fight.getFightTimeWithBreaks() );
        if ( fight.getFlag() != null )
        {
            ret.setFlag( new String( fight.getFlag() ) );
        }
        ret.setFusengachi( fight.getFusengachi() );
        ret.setHansokumakeBlue( fight.getHansokumakeBlue() );
        ret.setHansokumakeRed( fight.getHansokumakeRed() );
        ret.setInjuryTimeBlue( fight.getInjuryTimeBlue() );
        ret.setInjuryTimeRed( fight.getInjuryTimeRed() );
        ret.setKikengachi( fight.getKikengachi() );
        ret.setOverallFightTime( fight.getOverallFightTime() );
        ret.setPointsBlue( fight.getPointsBlue() );
        ret.setPointsRed( fight.getPointsRed() );
        if ( fight.getProtokoll() != null )
        {
            ret.setProtokoll( new String( fight.getProtokoll() ) );
        }
        ret.setShidoBlue( fight.getShidoBlue() );
        ret.setShidoRed( fight.getShidoRed() );
        ret.setModifiedWith2Call( fight.isModifiedWith2Call() );
        ret.setSaveTime( new Timestamp( fight.getSaveTime().getTime() ) );

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

    public static NewaFightWeb mapFightFromDBForWeb( FriendlyNewaFight fight )
    {
        if ( fight == null )
        {
            return null;
        }
        NewaFightWeb ret = new NewaFightWeb();

        ret.setChuiBlue( fight.getChuiBlue() );
        ret.setChuiRed( fight.getChuiRed() );
        ret.setFighterBlue( NewaFighterMapper.mapFighterFromDB( fight.getFighterBlue() ) );
        ret.setFighterRed( NewaFighterMapper.mapFighterFromDB( fight.getFighterRed() ) );
        ret.setFighterIdBlue( fight.getFighterIdBlue() );
        ret.setFighterIdRed( fight.getFighterIdRed() );
        // ret.setFightingclass(WeightclassMapper.mapWeightclassFromDB(fight.getFighterBlue().getFightingclass()));
        ret.setNewaclassId( fight.getNewaclassId() );
        ret.setFightTime( fight.getFightTime() );
        ret.setFightTimeWithBreaks( fight.getFightTimeWithBreaks() );
        if ( fight.getFlag() != null )
        {
            ret.setFlag( new String( fight.getFlag() ) );
        }
        ret.setFusengachi( fight.getFusengachi() );
        ret.setHansokumakeBlue( fight.getHansokumakeBlue() );
        ret.setHansokumakeRed( fight.getHansokumakeRed() );
        ret.setInjuryTimeBlue( fight.getInjuryTimeBlue() );
        ret.setInjuryTimeRed( fight.getInjuryTimeRed() );
        ret.setKikengachi( fight.getKikengachi() );
        ret.setOverallFightTime( fight.getOverallFightTime() );
        ret.setPointsBlue( fight.getPointsBlue() );
        ret.setPointsRed( fight.getPointsRed() );
        ret.setPointsBlueOnClock( fight.getPointsBlueOnClock() );
        ret.setPointsRedOnClock( fight.getPointsRedOnClock() );
        ret.setModifiedWith2Call( fight.isModifiedWith2Call() );
        ret.setSaveTime( new Timestamp( fight.getSaveTime().getTime() ) );
        if ( fight.getProtokoll() != null )
        {
            ret.setProtokoll( new String( fight.getProtokoll() ) );
        }
        ret.setShidoBlue( fight.getShidoBlue() );
        ret.setShidoRed( fight.getShidoRed() );
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
}