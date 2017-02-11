/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FightTimes.java
 * Created : 23 Jul 2010
 * Last Modified: Fr, 23 Jul 2010 20:55:46
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

package de.jjw.model.admin;

import java.sql.Timestamp;

import de.jjw.model.BaseObject;

public class FightTimes
    extends BaseObject

{

    protected int fightCount;

    protected String tatami;

    protected Long tatamiAsId;

    protected String ageDescription;

    protected int totalFightTime;

    protected int pause;

    protected int fightTime;

    protected int diffTimeBreak;

    protected Timestamp saveTime;

    protected int fightTimeWithBreaks;

    public int getFightCount()
    {
        return fightCount;
    }

    public void setFightCount( int fightCount )
    {
        this.fightCount = fightCount;
    }

    public String getTatami()
    {
        return tatami;
    }

    public void setTatami( String tatami )
    {
        this.tatami = tatami;
    }

    public String getAgeDescription()
    {
        return ageDescription;
    }

    public void setAgeDescription( String ageDescription )
    {
        this.ageDescription = ageDescription;
    }

    public int getTotalFightTime()
    {
        return totalFightTime;
    }

    public String getTotalFightTimeAsSting()
    {
        return getTimeAsTimeString( totalFightTime );
    }

    public void setTotalFightTime( int totalFightTime )
    {
        this.totalFightTime = totalFightTime;
    }

    public int getPause()
    {
        return pause;
    }

    public String getPauseAsSting()
    {
        return getTimeAsTimeString( pause );
    }

    public void setPause( int pause )
    {
        this.pause = pause;
    }

    public int getFightTime()
    {
        return fightTime;
    }

    public String getFightTimeAsSting()
    {
        return getTimeAsTimeString( fightTime );
    }

    public void setFightTime( int fightTime )
    {
        this.fightTime = fightTime;
    }

    public int getDiffTimeBreak()
    {
        return diffTimeBreak;
    }

    public String getDiffTimeBreakAsSting()
    {
        return getTimeAsTimeString( diffTimeBreak );
    }

    public void setDiffTimeBreak( int diffTimeBreak )
    {
        this.diffTimeBreak = diffTimeBreak;
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
     */
    public void setSaveTime( Timestamp saveTime )
    {
        this.saveTime = saveTime;
    }

    /**
     * @return the fightTimeWithBreaks
     */
    public int getFightTimeWithBreaks()
    {
        return fightTimeWithBreaks;
    }

    /**
     * @param fightTimeWithBreaks the fightTimeWithBreaks to set
     */
    public void setFightTimeWithBreaks( int fightTimeWithBreaks )
    {
        this.fightTimeWithBreaks = fightTimeWithBreaks;
    }

    /**
     * @return the tatamiAsId
     */
    public Long getTatamiAsId()
    {
        return tatamiAsId;
    }

    /**
     * @param tatamiAsId the tatamiAsId to set
     */
    public void setTatamiAsId( Long tatamiAsId )
    {
        this.tatamiAsId = tatamiAsId;
    }

    /* (non-Javadoc)
     * @see de.jjw.model.BaseObject#equals(java.lang.Object)
     */
    @Override
    public boolean equals( Object o )
    {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see de.jjw.model.BaseObject#hashCode()
     */
    @Override
    public int hashCode()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see de.jjw.model.BaseObject#toString()
     */
    @Override
    public String toString()
    {
        // TODO Auto-generated method stub
        return null;
    }

    public FightTimes( int fightCount, String tatami, String ageDescription, int totalFightTime, int pause,
                       int fightTime, int diffTimeBreak, Timestamp saveTime, int fightTimeWithBreaks, long tatamiAsId )
    {
        super();
        this.fightCount = fightCount;
        this.tatami = tatami;
        this.ageDescription = ageDescription;
        this.totalFightTime = totalFightTime;
        this.pause = pause;
        this.fightTime = fightTime;
        this.diffTimeBreak = diffTimeBreak;
        this.saveTime = saveTime;
        this.fightTimeWithBreaks = fightTimeWithBreaks;
        this.tatamiAsId = tatamiAsId;
    }

    public FightTimes( int fightCount, int pause, Timestamp saveTime )
    {
        super();
        this.fightCount = fightCount;
        this.tatami = tatami;
        this.ageDescription = ageDescription;
        this.totalFightTime = totalFightTime;
        this.pause = pause;
        this.fightTime = fightTime;
        this.diffTimeBreak = diffTimeBreak;
        this.saveTime = saveTime;
        this.fightTimeWithBreaks = fightTimeWithBreaks;
        this.tatamiAsId = tatamiAsId;
    }

    public FightTimes( int fightCount, String tatami, String ageDescription, int totalFightTime, int pause,
                       int fightTime, int diffTimeBreak, int fightTimeWithBreaks, long tatamiAsId )
    {
        super();
        this.fightCount = fightCount;
        this.tatami = tatami;
        this.ageDescription = ageDescription;
        this.totalFightTime = totalFightTime;
        this.pause = pause;
        this.fightTime = fightTime;
        this.diffTimeBreak = diffTimeBreak;
        this.fightTimeWithBreaks = fightTimeWithBreaks;
        this.tatamiAsId = tatamiAsId;
    }

    private static String getTimeAsTimeString( int time )
    {
        StringBuffer sb = new StringBuffer();
        if ( time >= 3600 )
        {
            sb.append( String.valueOf( ( time / 3600 ) % 24 ) );
            sb.append( ":" );
            time = ( time % 3600 );
        }
        if ( time >= 60 )
        {
            if ( ( ( time / 60 ) % 60 ) < 10 )
                sb.append( '0' );
            sb.append( String.valueOf( ( time / 60 ) % 60 ) );
            sb.append( ":" );
            time = ( time % 60 );
        }
        else
            sb.append( "00:" );

        if ( ( ( time ) % 60 ) < 10 )
            sb.append( '0' );
        sb.append( String.valueOf( ( time ) % 60 ) );
        return sb.toString();
    }

}
