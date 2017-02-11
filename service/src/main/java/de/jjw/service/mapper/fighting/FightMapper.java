/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightMapper.java
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

package de.jjw.service.mapper.fighting;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.jjw.model.fighting.Fight;
import de.jjw.model.fighting.FriendlyFight;
import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.service.modelWeb.FightWeb;
import de.jjw.service.util.IGlobalProjectConstants;
import de.jjw.util.ICodestableTypeConstants;
import de.jjw.util.TypeUtil;

public class FightMapper
    implements ICodestableTypeConstants, IGlobalProjectConstants
{

    public static List<FightWeb> mapFriendlyFightListFromDB( List<FriendlyFight> fights )
    {
        List<FightWeb> ret = new ArrayList<FightWeb>( fights.size() + 1 );
        FightWeb fightWeb = null;
        for ( FriendlyFight item : fights )
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

    public static Fight mapFightFromDB( Fight fight )
    {
        if ( fight == null )
        {
            return null;
        }
        Fight ret = new Fight();

        ret.setChuiBlue( fight.getChuiBlue() );
        ret.setChuiRed( fight.getChuiRed() );
        ret.setFighterBlue( FighterMapper.mapFighterFromDB( fight.getFighterBlue() ) );
        ret.setFighterRed( FighterMapper.mapFighterFromDB( fight.getFighterRed() ) );
        ret.setFighterIdBlue( fight.getFighterIdBlue() );
        ret.setFighterIdRed( fight.getFighterIdRed() );
        //ret.setFightingclass(WeightclassMapper.mapWeightclassFromDB(fight.getFighterBlue().getFightingclass()));
        ret.setFightingclassId( fight.getFightingclassId() );
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
        ret.setIpponBlueI( fight.getIpponBlueI() );
        ret.setIpponBlueII( fight.getIpponBlueII() );
        ret.setIpponBlueIII( fight.getIpponBlueIII() );
        ret.setIpponRedI( fight.getIpponRedI() );
        ret.setIpponRedII( fight.getIpponRedII() );
        ret.setIpponRedIII( fight.getIpponRedIII() );
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

    public static FightWeb mapFightFromDBForWeb( Fight fight )
    {
        if ( fight == null )
        {
            return null;
        }
        FightWeb ret = new FightWeb();

        ret.setChuiBlue( fight.getChuiBlue() );
        ret.setChuiRed( fight.getChuiRed() );
        ret.setFighterBlue( FighterMapper.mapFighterFromDB( fight.getFighterBlue() ) );
        ret.setFighterRed( FighterMapper.mapFighterFromDB( fight.getFighterRed() ) );
        ret.setFighterIdBlue( fight.getFighterIdBlue() );
        ret.setFighterIdRed( fight.getFighterIdRed() );
        // ret.setFightingclass(WeightclassMapper.mapWeightclassFromDB(fight.getFighterBlue().getFightingclass()));
        ret.setFightingclassId( fight.getFightingclassId() );
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
        ret.setIpponBlueI( fight.getIpponBlueI() );
        ret.setIpponBlueII( fight.getIpponBlueII() );
        ret.setIpponBlueIII( fight.getIpponBlueIII() );
        ret.setIpponRedI( fight.getIpponRedI() );
        ret.setIpponRedII( fight.getIpponRedII() );
        ret.setIpponRedIII( fight.getIpponRedIII() );
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

    public static void mapFightToDB( Fight fight, Fight fightDB, boolean deepMapping )
    {

        fightDB.setChuiBlue( fight.getChuiBlue() );
        fightDB.setChuiRed( fight.getChuiRed() );
        if ( deepMapping )
        {
            fightDB.setFighterBlue( FighterMapper.mapFighterFromDB( fight.getFighterBlue() ) );
            fightDB.setFighterRed( FighterMapper.mapFighterFromDB( fight.getFighterRed() ) );
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
        fightDB.setIpponBlueI( fight.getIpponBlueI() );
        fightDB.setIpponBlueII( fight.getIpponBlueII() );
        fightDB.setIpponBlueIII( fight.getIpponBlueIII() );
        fightDB.setIpponRedI( fight.getIpponRedI() );
        fightDB.setIpponRedII( fight.getIpponRedII() );
        fightDB.setIpponRedIII( fight.getIpponRedIII() );
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

    public static FightWeb mapFightForClockFromDB( Fight fight, Locale local )
    {
        if ( fight == null )
        {
            return null;
        }
        FightWeb ret = new FightWeb();

        ret.setChuiBlue( fight.getChuiBlue() );
        ret.setChuiRed( fight.getChuiRed() );
        ret.setFighterBlue( FighterMapper.mapFighterFromDB( fight.getFighterBlue() ) );
        ret.setFighterRed( FighterMapper.mapFighterFromDB( fight.getFighterRed() ) );
        ret.setFighterIdBlue( fight.getFighterIdBlue() );
        ret.setFighterIdRed( fight.getFighterIdRed() );
        ret.setFightingclass( WeightclassMapper.mapWeightclassFromDB( fight.getFighterBlue().getFightingclass() ) );
        ret.getFightingclass().setSexWeb( CodestableMain.getInstance().getCodestableCodeValueByType( TYPE_SEX,
                                                                                                     ret.getFightingclass().getSex(),
                                                                                                     local ) );

        ret.setFightingclassId( fight.getFightingclassId() );
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
        ret.setIpponBlueI( fight.getIpponBlueI() );
        ret.setIpponBlueII( fight.getIpponBlueII() );
        ret.setIpponBlueIII( fight.getIpponBlueIII() );
        ret.setIpponRedI( fight.getIpponRedI() );
        ret.setIpponRedII( fight.getIpponRedII() );
        ret.setIpponRedIII( fight.getIpponRedIII() );
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

    public static void mapFriendlyFightToDB( FriendlyFight fight, FriendlyFight fightDB, boolean deepMapping )
    {

        fightDB.setChuiBlue( fight.getChuiBlue() );
        fightDB.setChuiRed( fight.getChuiRed() );
        if ( deepMapping )
        {
            fightDB.setFighterBlue( FighterMapper.mapFighterFromDB( fight.getFighterBlue() ) );
            fightDB.setFighterRed( FighterMapper.mapFighterFromDB( fight.getFighterRed() ) );
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
        fightDB.setIpponBlueI( fight.getIpponBlueI() );
        fightDB.setIpponBlueII( fight.getIpponBlueII() );
        fightDB.setIpponBlueIII( fight.getIpponBlueIII() );
        fightDB.setIpponRedI( fight.getIpponRedI() );
        fightDB.setIpponRedII( fight.getIpponRedII() );
        fightDB.setIpponRedIII( fight.getIpponRedIII() );
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

    public static FriendlyFight mapFriendlyFightFromDB( FriendlyFight fight )
    {
        if ( fight == null )
        {
            return null;
        }
        FriendlyFight ret = new FriendlyFight();

        ret.setChuiBlue( fight.getChuiBlue() );
        ret.setChuiRed( fight.getChuiRed() );
        ret.setFighterBlue( FighterMapper.mapFighterFromDB( fight.getFighterBlue() ) );
        ret.setFighterRed( FighterMapper.mapFighterFromDB( fight.getFighterRed() ) );
        ret.setFighterIdBlue( fight.getFighterIdBlue() );
        ret.setFighterIdRed( fight.getFighterIdRed() );
        // ret.setFightingclass(WeightclassMapper.mapWeightclassFromDB(fight.getFighterBlue().getFightingclass()));
        ret.setFightingclassId( fight.getFightingclassId() );
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
        ret.setIpponBlueI( fight.getIpponBlueI() );
        ret.setIpponBlueII( fight.getIpponBlueII() );
        ret.setIpponBlueIII( fight.getIpponBlueIII() );
        ret.setIpponRedI( fight.getIpponRedI() );
        ret.setIpponRedII( fight.getIpponRedII() );
        ret.setIpponRedIII( fight.getIpponRedIII() );
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

    public static FightWeb mapFriendlyFightForClockFromDB( FriendlyFight fight, Locale local )
    {
        if ( fight == null )
        {
            return null;
        }
        FightWeb ret = new FightWeb();

        ret.setChuiBlue( fight.getChuiBlue() );
        ret.setChuiRed( fight.getChuiRed() );
        ret.setFighterBlue( FighterMapper.mapFighterFromDB( fight.getFighterBlue() ) );
        ret.setFighterRed( FighterMapper.mapFighterFromDB( fight.getFighterRed() ) );
        ret.setFighterIdBlue( fight.getFighterIdBlue() );
        ret.setFighterIdRed( fight.getFighterIdRed() );
        ret.setFightingclass( WeightclassMapper.mapWeightclassFromDB( fight.getFighterBlue().getFightingclass() ) );
        ret.getFightingclass().setSexWeb( CodestableMain.getInstance().getCodestableCodeValueByType( TYPE_SEX,
                                                                                                     ret.getFightingclass().getSex(),
                                                                                                     local ) );

        ret.setFightingclassId( fight.getFightingclassId() );
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
        ret.setIpponBlueI( fight.getIpponBlueI() );
        ret.setIpponBlueII( fight.getIpponBlueII() );
        ret.setIpponBlueIII( fight.getIpponBlueIII() );
        ret.setIpponRedI( fight.getIpponRedI() );
        ret.setIpponRedII( fight.getIpponRedII() );
        ret.setIpponRedIII( fight.getIpponRedIII() );
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

    public static FightWeb mapFightFromDBForWeb( FriendlyFight fight )
    {
        if ( fight == null )
        {
            return null;
        }
        FightWeb ret = new FightWeb();

        ret.setChuiBlue( fight.getChuiBlue() );
        ret.setChuiRed( fight.getChuiRed() );
        ret.setFighterBlue( FighterMapper.mapFighterFromDB( fight.getFighterBlue() ) );
        ret.setFighterRed( FighterMapper.mapFighterFromDB( fight.getFighterRed() ) );
        ret.setFighterIdBlue( fight.getFighterIdBlue() );
        ret.setFighterIdRed( fight.getFighterIdRed() );
        // ret.setFightingclass(WeightclassMapper.mapWeightclassFromDB(fight.getFighterBlue().getFightingclass()));
        ret.setFightingclassId( fight.getFightingclassId() );
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
        ret.setIpponBlueI( fight.getIpponBlueI() );
        ret.setIpponBlueII( fight.getIpponBlueII() );
        ret.setIpponBlueIII( fight.getIpponBlueIII() );
        ret.setIpponRedI( fight.getIpponRedI() );
        ret.setIpponRedII( fight.getIpponRedII() );
        ret.setIpponRedIII( fight.getIpponRedIII() );
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