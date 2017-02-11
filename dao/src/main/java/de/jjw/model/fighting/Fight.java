/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : Fight.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 21:58:17
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

package de.jjw.model.fighting;

import java.sql.Timestamp;

import de.jjw.model.BaseObject;
import de.jjw.util.TypeUtil;

/**
 * @hibernate.class table="fight_list"
 */
public class Fight 
    extends BaseObject implements Cloneable
{

    protected Long id;

    protected long fighterIdRed = TypeUtil.LONG_MIN;

    protected long fighterIdBlue = TypeUtil.LONG_MIN;

    protected Fighter fighterRed;

    protected Fighter fighterBlue;

    protected Fightingclass fightingclass;

    protected Long fightingclassId = TypeUtil.LONG_MIN;

    protected int pointsRed = TypeUtil.INT_MIN;

    protected int pointsBlue = TypeUtil.INT_MIN;

    protected String flag;

    protected Long winnerId = TypeUtil.LONG_MIN;

    protected int ipponRedI;

    protected int ipponRedII;

    protected int ipponRedIII;

    protected int ipponBlueI;

    protected int ipponBlueII;

    protected int ipponBlueIII;

    protected int shidoRed;

    protected int shidoBlue;

    protected int chuiRed;

    protected int chuiBlue;

    protected int hansokumakeRed;

    protected int hansokumakeBlue;

    protected String protokoll;

    protected int kikengachi;

    protected int fusengachi;

    protected int fightTime;

    protected int injuryTimeRed;

    protected int injuryTimeBlue;

    protected int fightTimeWithBreaks;

    protected int overallFightTime;

    protected String poolPart;

    protected boolean mainRound = TypeUtil.BOOLEAN_TRUE;

    protected int fightNumberInPart = TypeUtil.INT_MIN;

    protected int pointsRedOnClock = TypeUtil.INT_MIN;

    protected int pointsBlueOnClock = TypeUtil.INT_MIN;

    protected boolean modifiedWith2Call = TypeUtil.BOOLEAN_FALSE;

    protected Timestamp saveTime = new Timestamp( TypeUtil.YEAR_2000_IN_MS );

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
     * @return the fighterRed
     */
    public Fighter getFighterRed()
    {
        return fighterRed;
    }

    /**
     * @return the fighterBlue
     * @hibernate.property column="fighterIdRed"
     */
    public long getFighterIdRed()
    {
        // if(fighterRed != null) return fighterRed.getId();
        // else return TypeUtil.LONG_OBJECT_EMPTY;
        return fighterIdRed;
    }

    /**
     * @param fighterRed the fighterRed to set
     */
    public void setFighterRed( Fighter fighterRed )
    {
        this.fighterRed = fighterRed;
    }

    /**
     * @return the fighterBlue
     */
    public Fighter getFighterBlue()
    {
        return fighterBlue;
    }

    /**
     * @return the fighterBlue
     * @hibernate.property column="fighterIdBlue"
     */
    public long getFighterIdBlue()
    {
        // if(fighterBlue != null) return fighterBlue.getId();
        // else return TypeUtil.LONG_OBJECT_EMPTY;
        return fighterIdBlue;
    }

    /**
     * @param fighterBlue the fighterBlue to set
     */
    public void setFighterBlue( Fighter fighterBlue )
    {
        this.fighterBlue = fighterBlue;
    }

    /**
     * @return Returns the age.
     * @hibernate.many-to-one not-null="true" class="de.jjw.model.fighting.Fightingclass" column="fightingclass"
     * insert="false" update="false"
     */
    public Fightingclass getFightingclass()
    {
        return fightingclass;
    }

    public void setFightingclass( Fightingclass fightingclass )
    {
        this.fightingclass = fightingclass;
    }

    /**
     * @return Returns the age.
     * @hibernate.property column="fightingclass"
     */
    public Long getFightingclassId()
    {
        return fightingclassId;
    }

    public void setFightingclassId( Long fightingclassId )
    {
        this.fightingclassId = fightingclassId;
    }

    /**
     * @return the pointsRed
     * @hibernate.property column="pointsRed"
     */
    public int getPointsRed()
    {
        return pointsRed;
    }

    /**
     * @param pointsRed the pointsRed to set
     */
    public void setPointsRed( int pointsRed )
    {
        this.pointsRed = pointsRed;
    }

    /**
     * @return the pointedBlue
     * @hibernate.property column="pointsBlue"
     */
    public int getPointsBlue()
    {
        return pointsBlue;
    }

    /**
     * @param pointedBlue the pointedBlue to set
     */
    public void setPointsBlue( int pointedBlue )
    {
        this.pointsBlue = pointedBlue;
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
     * @return the ipponRedI
     * @hibernate.property column="ipponRedI"
     */
    public int getIpponRedI()
    {
        return ipponRedI;
    }

    /**
     * @param ipponRedI the ipponRedI to set
     */
    public void setIpponRedI( int ipponRedI )
    {
        this.ipponRedI = ipponRedI;
    }

    /**
     * @return the ipponRedII
     * @hibernate.property column="ipponRedII"
     */
    public int getIpponRedII()
    {
        return ipponRedII;
    }

    /**
     * @param ipponRedII the ipponRedII to set
     */
    public void setIpponRedII( int ipponRedII )
    {
        this.ipponRedII = ipponRedII;
    }

    /**
     * @return the ipponRedIII
     * @hibernate.property column="ipponRedIII"
     */
    public int getIpponRedIII()
    {
        return ipponRedIII;
    }

    /**
     * @param ipponRedIII the ipponRedIII to set
     */
    public void setIpponRedIII( int ipponRedIII )
    {
        this.ipponRedIII = ipponRedIII;
    }

    /**
     * @return the ipponBlueI
     * @hibernate.property column="ipponBlueI"
     */
    public int getIpponBlueI()
    {
        return ipponBlueI;
    }

    /**
     * @param ipponBlueI the ipponBlueI to set
     */
    public void setIpponBlueI( int ipponBlueI )
    {
        this.ipponBlueI = ipponBlueI;
    }

    /**
     * @return the ipponBlueII
     * @hibernate.property column="ipponBlueII"
     */
    public int getIpponBlueII()
    {
        return ipponBlueII;
    }

    /**
     * @param ipponBlueII the ipponBlueII to set
     */
    public void setIpponBlueII( int ipponBlueII )
    {
        this.ipponBlueII = ipponBlueII;
    }

    /**
     * @return the ipponBlueIII
     * @hibernate.property column="ipponBlueIII"
     */
    public int getIpponBlueIII()
    {
        return ipponBlueIII;
    }

    /**
     * @param ipponBlueIII the ipponBlueIII to set
     */
    public void setIpponBlueIII( int ipponBlueIII )
    {
        this.ipponBlueIII = ipponBlueIII;
    }

    /**
     * @return the shidoRed
     * @hibernate.property column="shidoRed"
     */
    public int getShidoRed()
    {
        return shidoRed;
    }

    /**
     * @param shidoRed the shidoRed to set
     */
    public void setShidoRed( int shidoRed )
    {
        this.shidoRed = shidoRed;
    }

    /**
     * @return the shidoBlue
     * @hibernate.property column="shidoBlue"
     */
    public int getShidoBlue()
    {
        return shidoBlue;
    }

    /**
     * @param shidoBlue the shidoBlue to set
     */
    public void setShidoBlue( int shidoBlue )
    {
        this.shidoBlue = shidoBlue;
    }

    /**
     * @return the chuiRed
     * @hibernate.property column="chuiRed"
     */
    public int getChuiRed()
    {
        return chuiRed;
    }

    /**
     * @param chuiRed the chuiRed to set
     */
    public void setChuiRed( int chuiRed )
    {
        this.chuiRed = chuiRed;
    }

    /**
     * @return the chuiBlue
     * @hibernate.property column="chuiBlue"
     */
    public int getChuiBlue()
    {
        return chuiBlue;
    }

    /**
     * @param chuiBlue the chuiBlue to set
     */
    public void setChuiBlue( int chuiBlue )
    {
        this.chuiBlue = chuiBlue;
    }

    /**
     * @return the hansokumakeRed
     * @hibernate.property column="hansokumakeRed"
     */
    public int getHansokumakeRed()
    {
        return hansokumakeRed;
    }

    /**
     * @param hansokumakeRed the hansokumakeRed to set
     */
    public void setHansokumakeRed( int hansokumakeRed )
    {
        this.hansokumakeRed = hansokumakeRed;
    }

    /**
     * @return the hansokumakeBlue
     * @hibernate.property column="hansokumakeBlue"
     */
    public int getHansokumakeBlue()
    {
        return hansokumakeBlue;
    }

    /**
     * @param hansokumakeBlue the hansokumakeBlue to set
     */
    public void setHansokumakeBlue( int hansokumakeBlue )
    {
        this.hansokumakeBlue = hansokumakeBlue;
    }

    /**
     * @return the protokoll
     * @hibernate.property column="protokoll"
     */
    public String getProtokoll()
    {
        return protokoll;
    }

    /**
     * @param protokoll the protokoll to set
     */
    public void setProtokoll( String protokoll )
    {
        this.protokoll = protokoll;
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
     * @return the fightTime
     * @hibernate.property column="fightTime"
     */
    public int getFightTime()
    {
        return fightTime;
    }

    /**
     * @param fightTime the fightTime to set
     */
    public void setFightTime( int fightTime )
    {
        this.fightTime = fightTime;
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
     * @return the fightTimeWithBreaks
     * @hibernate.property column="fightTimeWithBreaks"
     */
    public int getFightTimeWithBreaks()
    {
        return fightTimeWithBreaks;
    }

    /**
     * @param fightTimeWithBreaks the fightTimeWhitBreaks to set
     */
    public void setFightTimeWithBreaks( int fightTimeWithBreaks )
    {
        this.fightTimeWithBreaks = fightTimeWithBreaks;
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

    public void setFighterIdRed( long fighterIdRed )
    {
        this.fighterIdRed = fighterIdRed;
    }

    public void setFighterIdBlue( long fighterIdBlue )
    {
        this.fighterIdBlue = fighterIdBlue;
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

    /**
     * @return Returns the pointsRedOnClock.
     * @hibernate.property column="pointsRedOnClock"
     */
    public int getPointsRedOnClock()
    {
        return pointsRedOnClock;
    }

    public void setPointsRedOnClock( int pointsRedOnClock )
    {
        this.pointsRedOnClock = pointsRedOnClock;
    }

    /**
     * @return Returns the pointsBlueOnClock.
     * @hibernate.property column="pointsBlueOnClock"
     */
    public int getPointsBlueOnClock()
    {
        return pointsBlueOnClock;
    }

    public void setPointsBlueOnClock( int pointsBlueOnClock )
    {
        this.pointsBlueOnClock = pointsBlueOnClock;
    }

    public void reset()
    {

        id = TypeUtil.LONG_MIN;
        fighterIdRed = TypeUtil.LONG_MIN;
        ;
        fighterIdBlue = TypeUtil.LONG_MIN;
        ;
        fighterRed = null;
        fighterBlue = null;
        fightingclass = null;
        fightingclassId = TypeUtil.LONG_MIN;
        pointsRed = TypeUtil.INT_MIN;
        pointsBlue = TypeUtil.INT_MIN;
        flag = null;
        winnerId = TypeUtil.LONG_MIN;
        ipponRedI = TypeUtil.INT_DEFAULT;
        ipponRedII = TypeUtil.INT_DEFAULT;
        ipponRedIII = TypeUtil.INT_DEFAULT;
        ipponBlueI = TypeUtil.INT_DEFAULT;
        ipponBlueII = TypeUtil.INT_DEFAULT;
        ipponBlueIII = TypeUtil.INT_DEFAULT;
        shidoRed = TypeUtil.INT_DEFAULT;
        shidoBlue = TypeUtil.INT_DEFAULT;
        chuiRed = TypeUtil.INT_DEFAULT;
        chuiBlue = TypeUtil.INT_DEFAULT;
        hansokumakeRed = TypeUtil.INT_DEFAULT;
        hansokumakeBlue = TypeUtil.INT_DEFAULT;
        protokoll = null;
        kikengachi = TypeUtil.INT_DEFAULT;
        fusengachi = TypeUtil.INT_DEFAULT;
        pointsRedOnClock = TypeUtil.INT_DEFAULT;
        pointsBlueOnClock = TypeUtil.INT_DEFAULT;

        fightTime = TypeUtil.INT_DEFAULT;
        injuryTimeRed = TypeUtil.INT_DEFAULT;
        injuryTimeBlue = TypeUtil.INT_DEFAULT;
        fightTimeWithBreaks = TypeUtil.INT_DEFAULT;
        overallFightTime = TypeUtil.INT_DEFAULT;
        poolPart = null;
        mainRound = TypeUtil.BOOLEAN_TRUE;
        fightNumberInPart = TypeUtil.INT_MIN;
        modifiedWith2Call = TypeUtil.BOOLEAN_FALSE;
        saveTime = new Timestamp( TypeUtil.YEAR_2000_IN_MS );
        ;

    }

    public void resetForKoList()
    {

        fighterIdRed = TypeUtil.LONG_MIN;
        ;
        fighterIdBlue = TypeUtil.LONG_MIN;
        ;
        fighterRed = null;
        fighterBlue = null;

        pointsRed = TypeUtil.INT_MIN;
        pointsBlue = TypeUtil.INT_MIN;

        winnerId = TypeUtil.LONG_MIN;
        ipponRedI = TypeUtil.INT_DEFAULT;
        ipponRedII = TypeUtil.INT_DEFAULT;
        ipponRedIII = TypeUtil.INT_DEFAULT;
        ipponBlueI = TypeUtil.INT_DEFAULT;
        ipponBlueII = TypeUtil.INT_DEFAULT;
        ipponBlueIII = TypeUtil.INT_DEFAULT;
        shidoRed = TypeUtil.INT_DEFAULT;
        shidoBlue = TypeUtil.INT_DEFAULT;
        chuiRed = TypeUtil.INT_DEFAULT;
        chuiBlue = TypeUtil.INT_DEFAULT;
        hansokumakeRed = TypeUtil.INT_DEFAULT;
        hansokumakeBlue = TypeUtil.INT_DEFAULT;
        protokoll = null;
        kikengachi = TypeUtil.INT_DEFAULT;
        fusengachi = TypeUtil.INT_DEFAULT;
        pointsRedOnClock = TypeUtil.INT_DEFAULT;
        pointsBlueOnClock = TypeUtil.INT_DEFAULT;

        fightTime = TypeUtil.INT_DEFAULT;
        injuryTimeRed = TypeUtil.INT_DEFAULT;
        injuryTimeBlue = TypeUtil.INT_DEFAULT;
        fightTimeWithBreaks = TypeUtil.INT_DEFAULT;
        overallFightTime = TypeUtil.INT_DEFAULT;
        modifiedWith2Call = TypeUtil.BOOLEAN_FALSE;
        saveTime = new Timestamp( TypeUtil.YEAR_2000_IN_MS );
        dirty = TypeUtil.BOOLEAN_TRUE;
    }

    /**
     * @return the modifiedWith2Call
     */
    public boolean isModifiedWith2Call()
    {
        return modifiedWith2Call;
    }

    /**
     * @param modifiedWith2Call the modifiedWith2Call to set
     * @hibernate.property column="modifiedWithSecondCall" type="yes_no"
     */
    public void setModifiedWith2Call( boolean modifiedWith2Call )
    {
        this.modifiedWith2Call = modifiedWith2Call;
    }

    /**
     * @return the saveTime
     */
    public Timestamp getSaveTime()
    {
        return saveTime;
    }

    /**
     * @param saveTime the saveTime to set
     * @hibernate.property column="saveTime"
     */
    public void setSaveTime( Timestamp saveTime )
    {
        this.saveTime = saveTime;
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
