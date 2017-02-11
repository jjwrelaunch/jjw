/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoFight.java
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

package de.jjw.model.duo;

import de.jjw.model.BaseObject;
import de.jjw.util.TypeUtil;

/**
 * @hibernate.class table="duo_fight_list"
 */
public class DuoFight
    extends BaseObject implements Cloneable
{

    protected Long id;

    protected long teamIdRed = TypeUtil.LONG_MIN;

    protected long teamIdBlue = TypeUtil.LONG_MIN;

    protected DuoTeam duoTeamRed;

    protected DuoTeam duoTeamBlue;

    protected Duoclass duoclass;

    protected Long duoclassId = TypeUtil.LONG_MIN;

    protected double pointsRed = TypeUtil.DOUBLE_MIN;

    protected double pointsBlue = TypeUtil.DOUBLE_MIN;

    protected double pointsRedMax = TypeUtil.DOUBLE_MIN;

    protected double pointsBlueMax = TypeUtil.DOUBLE_MIN;

    protected String flag;

    protected Long winnerId = TypeUtil.LONG_MIN;

    // series A-D

    protected int kikengachi;

    protected int fusengachi;

    protected int injuryTimeRed;

    protected int injuryTimeBlue;

    protected int overallFightTime;

    protected String poolPart;

    protected boolean mainRound = TypeUtil.BOOLEAN_TRUE;

    protected int fightNumberInPart = TypeUtil.INT_MIN;

    protected double redSeriesArefI;

    protected double redSeriesArefII;

    protected double redSeriesArefIII;

    protected double redSeriesArefIV;

    protected double redSeriesArefV;

    protected double blueSeriesArefI;

    protected double blueSeriesArefII;

    protected double blueSeriesArefIII;

    protected double blueSeriesArefIV;

    protected double blueSeriesArefV;

    protected double redSeriesBrefI;

    protected double redSeriesBrefII;

    protected double redSeriesBrefIII;

    protected double redSeriesBrefIV;

    protected double redSeriesBrefV;

    protected double blueSeriesBrefI;

    protected double blueSeriesBrefII;

    protected double blueSeriesBrefIII;

    protected double blueSeriesBrefIV;

    protected double blueSeriesBrefV;

    protected double redSeriesCrefI;

    protected double redSeriesCrefII;

    protected double redSeriesCrefIII;

    protected double redSeriesCrefIV;

    protected double redSeriesCrefV;

    protected double blueSeriesCrefI;

    protected double blueSeriesCrefII;

    protected double blueSeriesCrefIII;

    protected double blueSeriesCrefIV;

    protected double blueSeriesCrefV;

    protected double redSeriesDrefI;

    protected double redSeriesDrefII;

    protected double redSeriesDrefIII;

    protected double redSeriesDrefIV;

    protected double redSeriesDrefV;

    protected double blueSeriesDrefI;

    protected double blueSeriesDrefII;

    protected double blueSeriesDrefIII;

    protected double blueSeriesDrefIV;

    protected double blueSeriesDrefV;

    private boolean dirty = TypeUtil.BOOLEAN_FALSE;

    // using for class creation, only fights with true will create in db

    private boolean writeFlag = TypeUtil.BOOLEAN_FALSE;

    /**
     * @return the id
     * @hibernate.id column="id" generator-class="increment" unsaved-value="null"
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId( Long id )
    {
        this.id = id;
    }

    /**
     * @return the duoteamRed
     */
    public DuoTeam getDuoTeamRed()
    {
        return duoTeamRed;
    }

    /**
     * @return the duoteamred
     * @hibernate.property column="teamIdRed"
     */
    public long getTeamIdRed()
    {
        return teamIdRed;
    }

    /**
     * @param duoTeamRed the fighterRed to set
     */
    public void setDuoTeamRed( DuoTeam duoTeamRed )
    {
        this.duoTeamRed = duoTeamRed;
    }

    /**
     * @return the duoteamBlue
     */
    public DuoTeam getDuoTeamBlue()
    {
        return duoTeamBlue;
    }

    /**
     * @return the fighterBlue
     * @hibernate.property column="teamIdBlue"
     */
    public long getTeamIdBlue()
    {
        return teamIdBlue;
    }

    /**
     * @param duoTeamBlue the fighterBlue to set
     */
    public void setDuoTeamBlue( DuoTeam duoTeamBlue )
    {
        this.duoTeamBlue = duoTeamBlue;
    }

    /**
     * @return Returns the age.
     * @hibernate.many-to-one not-null="true" class="de.jjw.model.duo.Duoclass" column="duoclass" insert="false"
     *                        update="false"
     */
    public Duoclass getDuoclass()
    {
        return duoclass;
    }

    public void setDuoclass( Duoclass duoclass )
    {
        this.duoclass = duoclass;
    }

    /**
     * @return Returns the age.
     * @hibernate.property column="duoclass"
     */
    public Long getDuoclassId()
    {
        return duoclassId;
    }

    public void setDuoclassId( Long duoclassId )
    {
        this.duoclassId = duoclassId;
    }

    /**
     * @return the pointsRed
     * @hibernate.property column="pointsRed"
     */
    public double getPointsRed()
    {
        return pointsRed;
    }

    /**
     * @param pointsRed the pointsRed to set
     */
    public void setPointsRed( double pointsRed )
    {
        this.pointsRed = pointsRed;
    }

    /**
     * @return the pointedBlue
     * @hibernate.property column="pointsBlue"
     */
    public double getPointsBlue()
    {
        return pointsBlue;
    }

    /**
     * @param pointsBlue the pointedBlue to set
     */
    public void setPointsBlue( double pointsBlue )
    {
        this.pointsBlue = pointsBlue;
    }

    /**
     * @return the pointsRedMax
     * @hibernate.property column="pointsRedMax"
     */
    public double getPointsRedMax()
    {
        return pointsRedMax;
    }

    /**
     * @param pointsRedMax the pointsRedMax to set
     */
    public void setPointsRedMax( double pointsRedMax )
    {
        this.pointsRedMax = pointsRedMax;
    }

    /**
     * @return the pointedBlueMax
     * @hibernate.property column="pointsBlueMax"
     */
    public double getPointsBlueMax()
    {
        return pointsBlueMax;
    }

    /**
     * @param pointedBlueMax the pointedBlueMax to set
     */
    public void setPointsBlueMax( double pointedBlueMax )
    {
        this.pointsBlueMax = pointedBlueMax;
    }

    /**
     * @return the flag
     * @hibernate.property column="flag"
     */
    public String getFlag()
    {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag( String flag )
    {
        this.flag = flag;
    }

    /**
     * @return the winnerId
     * @hibernate.property column="winnerId"
     */
    public Long getWinnerId()
    {
        return winnerId;
    }

    /**
     * @param winnerId the winnerId to set
     */
    public void setWinnerId( Long winnerId )
    {
        this.winnerId = winnerId;
    }

    /**
     * @return the kikengachi
     * @hibernate.property column="kikengachi"
     */
    public int getKikengachi()
    {
        return kikengachi;
    }

    /**
     * @param kikengachi the kikengachi to set
     */
    public void setKikengachi( int kikengachi )
    {
        this.kikengachi = kikengachi;
    }

    /**
     * @return the fusengachi
     * @hibernate.property column="fusengachi"
     */
    public int getFusengachi()
    {
        return fusengachi;
    }

    /**
     * @param fusengachi the fusengachi to set
     */
    public void setFusengachi( int fusengachi )
    {
        this.fusengachi = fusengachi;
    }

    /**
     * @return the injuryTimeRed
     * @hibernate.property column="injuryTimeRed"
     */
    public int getInjuryTimeRed()
    {
        return injuryTimeRed;
    }

    /**
     * @param injuryTimeRed the injuryTimeRed to set
     */
    public void setInjuryTimeRed( int injuryTimeRed )
    {
        this.injuryTimeRed = injuryTimeRed;
    }

    /**
     * @return the injuryTimeBlue
     * @hibernate.property column="injuryTimeBlue"
     */
    public int getInjuryTimeBlue()
    {
        return injuryTimeBlue;
    }

    /**
     * @param injuryTimeBlue the injuryTimeBlue to set
     */
    public void setInjuryTimeBlue( int injuryTimeBlue )
    {
        this.injuryTimeBlue = injuryTimeBlue;
    }

    /**
     * @return the overallFightTime
     * @hibernate.property column="overallFightTime"
     */
    public int getOverallFightTime()
    {
        return overallFightTime;
    }

    /**
     * @param overallFightTime the overallFightTime to set
     */
    public void setOverallFightTime( int overallFightTime )
    {
        this.overallFightTime = overallFightTime;
    }

    @Override
    public String toString()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean equals( Object o )
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int hashCode()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    public void setTeamIdRed( long teamIdRed )
    {
        this.teamIdRed = teamIdRed;
    }

    public void setTeamIdBlue( long teamIdBlue )
    {
        this.teamIdBlue = teamIdBlue;
    }

    /**
     * @return Returns the poolPart.
     * @hibernate.property column="poolPart"
     */
    public String getPoolPart()
    {
        return poolPart;
    }

    public void setPoolPart( String poolPart )
    {
        this.poolPart = poolPart;
    }

    /**
     * @return Returns the mainRound.
     * @hibernate.property column="mainRound" type="yes_no"
     */
    public boolean isMainRound()
    {
        return mainRound;
    }

    public void setMainRound( boolean mainRound )
    {
        this.mainRound = mainRound;
    }

    /**
     * @return Returns the fightNumberInPart.
     * @hibernate.property column="fightNumberInPart"
     */
    public int getFightNumberInPart()
    {
        return fightNumberInPart;
    }

    public void setFightNumberInPart( int fightNumberInPart )
    {
        this.fightNumberInPart = fightNumberInPart;
    }

    public void reset()
    {

        id = TypeUtil.LONG_MIN;
        teamIdRed = TypeUtil.LONG_MIN;
        ;
        teamIdBlue = TypeUtil.LONG_MIN;
        ;
        duoTeamRed = null;
        duoTeamBlue = null;
        duoclass = null;
        duoclassId = TypeUtil.LONG_MIN;
        pointsRed = TypeUtil.DOUBLE_MIN;
        pointsBlue = TypeUtil.DOUBLE_MIN;
        pointsRedMax = TypeUtil.DOUBLE_MIN;
        pointsBlueMax = TypeUtil.DOUBLE_MIN;
        flag = null;
        winnerId = TypeUtil.LONG_MIN;

        redSeriesArefI = TypeUtil.DOUBLE_DEFAULT;
        redSeriesArefII = TypeUtil.DOUBLE_DEFAULT;
        redSeriesArefIII = TypeUtil.DOUBLE_DEFAULT;
        redSeriesArefIV = TypeUtil.DOUBLE_DEFAULT;
        redSeriesArefV = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesArefI = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesArefII = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesArefIII = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesArefIV = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesArefV = TypeUtil.DOUBLE_DEFAULT;

        redSeriesBrefI = TypeUtil.DOUBLE_DEFAULT;
        redSeriesBrefII = TypeUtil.DOUBLE_DEFAULT;
        redSeriesBrefIII = TypeUtil.DOUBLE_DEFAULT;
        redSeriesBrefIV = TypeUtil.DOUBLE_DEFAULT;
        redSeriesBrefV = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesBrefI = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesBrefII = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesBrefIII = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesBrefIV = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesBrefV = TypeUtil.DOUBLE_DEFAULT;

        redSeriesCrefI = TypeUtil.DOUBLE_DEFAULT;
        redSeriesCrefII = TypeUtil.DOUBLE_DEFAULT;
        redSeriesCrefIII = TypeUtil.DOUBLE_DEFAULT;
        redSeriesCrefIV = TypeUtil.DOUBLE_DEFAULT;
        redSeriesCrefV = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesCrefI = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesCrefII = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesCrefIII = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesCrefIV = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesCrefV = TypeUtil.DOUBLE_DEFAULT;

        redSeriesDrefI = TypeUtil.DOUBLE_DEFAULT;
        redSeriesDrefII = TypeUtil.DOUBLE_DEFAULT;
        redSeriesDrefIII = TypeUtil.DOUBLE_DEFAULT;
        redSeriesDrefIV = TypeUtil.DOUBLE_DEFAULT;
        redSeriesDrefV = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesDrefI = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesDrefII = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesDrefIII = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesDrefIV = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesDrefV = TypeUtil.DOUBLE_DEFAULT;

        kikengachi = TypeUtil.INT_DEFAULT;
        fusengachi = TypeUtil.INT_DEFAULT;

        injuryTimeRed = TypeUtil.INT_DEFAULT;
        injuryTimeBlue = TypeUtil.INT_DEFAULT;
        overallFightTime = TypeUtil.INT_DEFAULT;
        poolPart = null;
        mainRound = TypeUtil.BOOLEAN_TRUE;
        fightNumberInPart = TypeUtil.INT_MIN;

    }

    public void resetForKoList()
    {

        teamIdRed = TypeUtil.LONG_MIN;
        ;
        teamIdBlue = TypeUtil.LONG_MIN;
        ;
        duoTeamRed = null;
        duoTeamBlue = null;

        pointsRed = TypeUtil.DOUBLE_MIN;
        pointsBlue = TypeUtil.DOUBLE_MIN;
        pointsRedMax = TypeUtil.DOUBLE_MIN;
        pointsBlueMax = TypeUtil.DOUBLE_MIN;

        winnerId = TypeUtil.LONG_MIN;

        redSeriesArefI = TypeUtil.DOUBLE_DEFAULT;
        redSeriesArefII = TypeUtil.DOUBLE_DEFAULT;
        redSeriesArefIII = TypeUtil.DOUBLE_DEFAULT;
        redSeriesArefIV = TypeUtil.DOUBLE_DEFAULT;
        redSeriesArefV = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesArefI = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesArefII = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesArefIII = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesArefIV = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesArefV = TypeUtil.DOUBLE_DEFAULT;

        redSeriesBrefI = TypeUtil.DOUBLE_DEFAULT;
        redSeriesBrefII = TypeUtil.DOUBLE_DEFAULT;
        redSeriesBrefIII = TypeUtil.DOUBLE_DEFAULT;
        redSeriesBrefIV = TypeUtil.DOUBLE_DEFAULT;
        redSeriesBrefV = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesBrefI = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesBrefII = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesBrefIII = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesBrefIV = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesBrefV = TypeUtil.DOUBLE_DEFAULT;

        redSeriesCrefI = TypeUtil.DOUBLE_DEFAULT;
        redSeriesCrefII = TypeUtil.DOUBLE_DEFAULT;
        redSeriesCrefIII = TypeUtil.DOUBLE_DEFAULT;
        redSeriesCrefIV = TypeUtil.DOUBLE_DEFAULT;
        redSeriesCrefV = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesCrefI = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesCrefII = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesCrefIII = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesCrefIV = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesCrefV = TypeUtil.DOUBLE_DEFAULT;

        redSeriesDrefI = TypeUtil.DOUBLE_DEFAULT;
        redSeriesDrefII = TypeUtil.DOUBLE_DEFAULT;
        redSeriesDrefIII = TypeUtil.DOUBLE_DEFAULT;
        redSeriesDrefIV = TypeUtil.DOUBLE_DEFAULT;
        redSeriesDrefV = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesDrefI = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesDrefII = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesDrefIII = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesDrefIV = TypeUtil.DOUBLE_DEFAULT;
        blueSeriesDrefV = TypeUtil.DOUBLE_DEFAULT;
        kikengachi = TypeUtil.INT_DEFAULT;
        fusengachi = TypeUtil.INT_DEFAULT;

        injuryTimeRed = TypeUtil.INT_DEFAULT;
        injuryTimeBlue = TypeUtil.INT_DEFAULT;
        overallFightTime = TypeUtil.INT_DEFAULT;
        dirty = TypeUtil.BOOLEAN_TRUE;
    }

    public boolean isDirty()
    {
        return dirty;
    }

    public void setDirty( boolean dirty )
    {
        this.dirty = dirty;
    }

    public boolean isWriteFlag()
    {
        return writeFlag;
    }

    public void setWriteFlag( boolean writeFlag )
    {
        this.writeFlag = writeFlag;
    }

    /**
     * @return the redSeriesArefI
     * @hibernate.property column="redSeriesArefI"
     */
    public double getRedSeriesArefI()
    {
        return redSeriesArefI;
    }

    public void setRedSeriesArefI( double redSeriesArefI )
    {
        this.redSeriesArefI = redSeriesArefI;
    }

    /**
     * @return the redSeriesArefII
     * @hibernate.property column="redSeriesArefII"
     */
    public double getRedSeriesArefII()
    {
        return redSeriesArefII;
    }

    public void setRedSeriesArefII( double redSeriesArefII )
    {
        this.redSeriesArefII = redSeriesArefII;
    }

    /**
     * @return the redSeriesArefIII
     * @hibernate.property column="redSeriesArefIII"
     */
    public double getRedSeriesArefIII()
    {
        return redSeriesArefIII;
    }

    public void setRedSeriesArefIII( double redSeriesArefIII )
    {
        this.redSeriesArefIII = redSeriesArefIII;
    }

    /**
     * @return the redSeriesArefIV
     * @hibernate.property column="redSeriesArefIV"
     */
    public double getRedSeriesArefIV()
    {
        return redSeriesArefIV;
    }

    public void setRedSeriesArefIV( double redSeriesArefIV )
    {
        this.redSeriesArefIV = redSeriesArefIV;
    }

    /**
     * @return the redSeriesArefV
     * @hibernate.property column="redSeriesArefV"
     */
    public double getRedSeriesArefV()
    {
        return redSeriesArefV;
    }

    public void setRedSeriesArefV( double redSeriesArefV )
    {
        this.redSeriesArefV = redSeriesArefV;
    }

    /**
     * @return the blueSeriesArefI
     * @hibernate.property column="blueSeriesArefI"
     */
    public double getBlueSeriesArefI()
    {
        return blueSeriesArefI;
    }

    public void setBlueSeriesArefI( double blueSeriesArefI )
    {
        this.blueSeriesArefI = blueSeriesArefI;
    }

    /**
     * @return the blueSeriesArefII
     * @hibernate.property column="blueSeriesArefII"
     */
    public double getBlueSeriesArefII()
    {
        return blueSeriesArefII;
    }

    public void setBlueSeriesArefII( double blueSeriesArefII )
    {
        this.blueSeriesArefII = blueSeriesArefII;
    }

    /**
     * @return the blueSeriesArefIII
     * @hibernate.property column="blueSeriesArefIII"
     */
    public double getBlueSeriesArefIII()
    {
        return blueSeriesArefIII;
    }

    public void setBlueSeriesArefIII( double blueSeriesArefIII )
    {
        this.blueSeriesArefIII = blueSeriesArefIII;
    }

    /**
     * @return the blueSeriesArefIV
     * @hibernate.property column="blueSeriesArefIV"
     */
    public double getBlueSeriesArefIV()
    {
        return blueSeriesArefIV;
    }

    public void setBlueSeriesArefIV( double blueSeriesArefIV )
    {
        this.blueSeriesArefIV = blueSeriesArefIV;
    }

    /**
     * @return the blueSeriesArefV
     * @hibernate.property column="blueSeriesArefV"
     */
    public double getBlueSeriesArefV()
    {
        return blueSeriesArefV;
    }

    public void setBlueSeriesArefV( double blueSeriesArefV )
    {
        this.blueSeriesArefV = blueSeriesArefV;
    }

    /**
     * @return the redSeriesBrefI
     * @hibernate.property column="redSeriesBrefI"
     */
    public double getRedSeriesBrefI()
    {
        return redSeriesBrefI;
    }

    public void setRedSeriesBrefI( double redSeriesBrefI )
    {
        this.redSeriesBrefI = redSeriesBrefI;
    }

    /**
     * @return the redSeriesBrefII
     * @hibernate.property column="redSeriesBrefII"
     */
    public double getRedSeriesBrefII()
    {
        return redSeriesBrefII;
    }

    public void setRedSeriesBrefII( double redSeriesBrefII )
    {
        this.redSeriesBrefII = redSeriesBrefII;
    }

    /**
     * @return the redSeriesBrefIII
     * @hibernate.property column="redSeriesBrefIII"
     */
    public double getRedSeriesBrefIII()
    {
        return redSeriesBrefIII;
    }

    public void setRedSeriesBrefIII( double redSeriesBrefIII )
    {
        this.redSeriesBrefIII = redSeriesBrefIII;
    }

    /**
     * @return the redSeriesBrefIV
     * @hibernate.property column="redSeriesBrefIV"
     */
    public double getRedSeriesBrefIV()
    {
        return redSeriesBrefIV;
    }

    public void setRedSeriesBrefIV( double redSeriesBrefIV )
    {
        this.redSeriesBrefIV = redSeriesBrefIV;
    }

    /**
     * @return the redSeriesBrefV
     * @hibernate.property column="redSeriesBrefV"
     */
    public double getRedSeriesBrefV()
    {
        return redSeriesBrefV;
    }

    public void setRedSeriesBrefV( double redSeriesBrefV )
    {
        this.redSeriesBrefV = redSeriesBrefV;
    }

    /**
     * @return the blueSeriesBrefI
     * @hibernate.property column="blueSeriesBrefI"
     */
    public double getBlueSeriesBrefI()
    {
        return blueSeriesBrefI;
    }

    public void setBlueSeriesBrefI( double blueSeriesBrefI )
    {
        this.blueSeriesBrefI = blueSeriesBrefI;
    }

    /**
     * @return the blueSeriesBrefII
     * @hibernate.property column="blueSeriesBrefII"
     */
    public double getBlueSeriesBrefII()
    {
        return blueSeriesBrefII;
    }

    public void setBlueSeriesBrefII( double blueSeriesBrefII )
    {
        this.blueSeriesBrefII = blueSeriesBrefII;
    }

    /**
     * @return the blueSeriesBrefIII
     * @hibernate.property column="blueSeriesBrefIII"
     */
    public double getBlueSeriesBrefIII()
    {
        return blueSeriesBrefIII;
    }

    public void setBlueSeriesBrefIII( double blueSeriesBrefIII )
    {
        this.blueSeriesBrefIII = blueSeriesBrefIII;
    }

    /**
     * @return the blueSeriesBrefIV
     * @hibernate.property column="blueSeriesBrefIV"
     */
    public double getBlueSeriesBrefIV()
    {
        return blueSeriesBrefIV;
    }

    public void setBlueSeriesBrefIV( double blueSeriesBrefIV )
    {
        this.blueSeriesBrefIV = blueSeriesBrefIV;
    }

    /**
     * @return the blueSeriesBrefV
     * @hibernate.property column="blueSeriesBrefV"
     */
    public double getBlueSeriesBrefV()
    {
        return blueSeriesBrefV;
    }

    public void setBlueSeriesBrefV( double blueSeriesBrefV )
    {
        this.blueSeriesBrefV = blueSeriesBrefV;
    }

    /**
     * @return the redSeriesCrefI
     * @hibernate.property column="redSeriesCrefI"
     */
    public double getRedSeriesCrefI()
    {
        return redSeriesCrefI;
    }

    public void setRedSeriesCrefI( double redSeriesCrefI )
    {
        this.redSeriesCrefI = redSeriesCrefI;
    }

    /**
     * @return the redSeriesCrefII
     * @hibernate.property column="redSeriesCrefII"
     */
    public double getRedSeriesCrefII()
    {
        return redSeriesCrefII;
    }

    public void setRedSeriesCrefII( double redSeriesCrefII )
    {
        this.redSeriesCrefII = redSeriesCrefII;
    }

    /**
     * @return the redSeriesCrefIII
     * @hibernate.property column="redSeriesCrefIII"
     */
    public double getRedSeriesCrefIII()
    {
        return redSeriesCrefIII;
    }

    public void setRedSeriesCrefIII( double redSeriesCrefIII )
    {
        this.redSeriesCrefIII = redSeriesCrefIII;
    }

    /**
     * @return the redSeriesCrefIV
     * @hibernate.property column="redSeriesCrefIV"
     */
    public double getRedSeriesCrefIV()
    {
        return redSeriesCrefIV;
    }

    public void setRedSeriesCrefIV( double redSeriesCrefIV )
    {
        this.redSeriesCrefIV = redSeriesCrefIV;
    }

    /**
     * @return the redSeriesCrefV
     * @hibernate.property column="redSeriesCrefV"
     */
    public double getRedSeriesCrefV()
    {
        return redSeriesCrefV;
    }

    public void setRedSeriesCrefV( double redSeriesCrefV )
    {
        this.redSeriesCrefV = redSeriesCrefV;
    }

    /**
     * @return the blueSeriesCrefI
     * @hibernate.property column="blueSeriesCrefI"
     */
    public double getBlueSeriesCrefI()
    {
        return blueSeriesCrefI;
    }

    public void setBlueSeriesCrefI( double blueSeriesCrefI )
    {
        this.blueSeriesCrefI = blueSeriesCrefI;
    }

    /**
     * @return the blueSeriesCrefII
     * @hibernate.property column="blueSeriesCrefII"
     */
    public double getBlueSeriesCrefII()
    {
        return blueSeriesCrefII;
    }

    public void setBlueSeriesCrefII( double blueSeriesCrefII )
    {
        this.blueSeriesCrefII = blueSeriesCrefII;
    }

    /**
     * @return the blueSeriesCrefIII
     * @hibernate.property column="blueSeriesCrefIII"
     */
    public double getBlueSeriesCrefIII()
    {
        return blueSeriesCrefIII;
    }

    public void setBlueSeriesCrefIII( double blueSeriesCrefIII )
    {
        this.blueSeriesCrefIII = blueSeriesCrefIII;
    }

    /**
     * @return the blueSeriesCrefIV
     * @hibernate.property column="blueSeriesCrefIV"
     */
    public double getBlueSeriesCrefIV()
    {
        return blueSeriesCrefIV;
    }

    public void setBlueSeriesCrefIV( double blueSeriesCrefIV )
    {
        this.blueSeriesCrefIV = blueSeriesCrefIV;
    }

    /**
     * @return the blueSeriesCrefV
     * @hibernate.property column="blueSeriesCrefV"
     */
    public double getBlueSeriesCrefV()
    {
        return blueSeriesCrefV;
    }

    public void setBlueSeriesCrefV( double blueSeriesCrefV )
    {
        this.blueSeriesCrefV = blueSeriesCrefV;
    }

    /**
     * @return the redSeriesDrefI
     * @hibernate.property column="redSeriesDrefI"
     */
    public double getRedSeriesDrefI()
    {
        return redSeriesDrefI;
    }

    public void setRedSeriesDrefI( double redSeriesDrefI )
    {
        this.redSeriesDrefI = redSeriesDrefI;
    }

    /**
     * @return the redSeriesDrefII
     * @hibernate.property column="redSeriesDrefII"
     */
    public double getRedSeriesDrefII()
    {
        return redSeriesDrefII;
    }

    public void setRedSeriesDrefII( double redSeriesDrefII )
    {
        this.redSeriesDrefII = redSeriesDrefII;
    }

    /**
     * @return the redSeriesDrefIII
     * @hibernate.property column="redSeriesDrefIII"
     */
    public double getRedSeriesDrefIII()
    {
        return redSeriesDrefIII;
    }

    public void setRedSeriesDrefIII( double redSeriesDrefIII )
    {
        this.redSeriesDrefIII = redSeriesDrefIII;
    }

    /**
     * @return the redSeriesDrefIV
     * @hibernate.property column="redSeriesDrefIV"
     */
    public double getRedSeriesDrefIV()
    {
        return redSeriesDrefIV;
    }

    public void setRedSeriesDrefIV( double redSeriesDrefIV )
    {
        this.redSeriesDrefIV = redSeriesDrefIV;
    }

    /**
     * @return the redSeriesDrefV
     * @hibernate.property column="redSeriesDrefV"
     */
    public double getRedSeriesDrefV()
    {
        return redSeriesDrefV;
    }

    public void setRedSeriesDrefV( double redSeriesDrefV )
    {
        this.redSeriesDrefV = redSeriesDrefV;
    }

    /**
     * @return the blueSeriesDrefI
     * @hibernate.property column="blueSeriesDrefI"
     */
    public double getBlueSeriesDrefI()
    {
        return blueSeriesDrefI;
    }

    public void setBlueSeriesDrefI( double blueSeriesDrefI )
    {
        this.blueSeriesDrefI = blueSeriesDrefI;
    }

    /**
     * @return the blueSeriesDrefII
     * @hibernate.property column="blueSeriesDrefII"
     */
    public double getBlueSeriesDrefII()
    {
        return blueSeriesDrefII;
    }

    public void setBlueSeriesDrefII( double blueSeriesDrefII )
    {
        this.blueSeriesDrefII = blueSeriesDrefII;
    }

    /**
     * @return the blueSeriesDrefIII
     * @hibernate.property column="blueSeriesDrefIII"
     */
    public double getBlueSeriesDrefIII()
    {
        return blueSeriesDrefIII;
    }

    public void setBlueSeriesDrefIII( double blueSeriesDrefIII )
    {
        this.blueSeriesDrefIII = blueSeriesDrefIII;
    }

    /**
     * @return the blueSeriesDrefIV
     * @hibernate.property column="blueSeriesDrefIV"
     */
    public double getBlueSeriesDrefIV()
    {
        return blueSeriesDrefIV;
    }

    public void setBlueSeriesDrefIV( double blueSeriesDrefIV )
    {
        this.blueSeriesDrefIV = blueSeriesDrefIV;
    }

    /**
     * @return the blueSeriesDrefV
     * @hibernate.property column="blueSeriesDrefV"
     */
    public double getBlueSeriesDrefV()
    {
        return blueSeriesDrefV;
    }

    public void setBlueSeriesDrefV( double blueSeriesDrefV )
    {
        this.blueSeriesDrefV = blueSeriesDrefV;
    }
    
    public Object clone() {
        try
        {
            return super.clone();
        }
        catch ( CloneNotSupportedException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
      } 


}
