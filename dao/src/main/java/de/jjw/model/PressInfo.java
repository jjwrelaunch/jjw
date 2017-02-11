/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : PressInfo.java
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

package de.jjw.model;

import java.io.Serializable;
import java.util.List;

public class PressInfo
    extends BaseObject
    implements Serializable, Comparable<PressInfo>
{

    protected String eventName;

    protected String eventLocation;

    protected String eventDate;

    protected int eventParticipans;

    protected int eventParticipansFighter;

    protected int eventParticipansDuoTeams;

    protected int eventParticipansNewaFighter;

    protected int eventParticipansTeams;

    protected int eventParticipansRegions;

    protected int eventParticipansCountries;

    protected int eventFights;

    protected int eventFightsFighting;

    protected int eventFightsDuo;

    protected int eventFightsNewa;

    protected int eventFightsFightingOpen;

    protected int eventFightsDuoOpen;

    protected int eventFightsNewaOpen;

    List<LabelValueList> eventParticipansPerAgeFighter = null;

    List<LabelValueList> eventParticipansPerAgeDuoteams = null;

    List<LabelValueList> eventParticipansPerAgeNewaFighter = null;

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

    @Override
    public String toString()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int compareTo( PressInfo o )
    {
        // TODO Auto-generated method stub
        return 0;
    }

    public String getEventName()
    {
        return eventName;
    }

    public void setEventName( String eventName )
    {
        this.eventName = eventName;
    }

    public String getEventLocation()
    {
        return eventLocation;
    }

    public void setEventLocation( String eventLocation )
    {
        this.eventLocation = eventLocation;
    }

    public String getEventDate()
    {
        return eventDate;
    }

    public void setEventDate( String eventDate )
    {
        this.eventDate = eventDate;
    }

    public int getEventParticipans()
    {
        return eventParticipans;
    }

    public void setEventParticipans( int eventParticipans )
    {
        this.eventParticipans = eventParticipans;
    }

    public int getEventParticipansFighter()
    {
        return eventParticipansFighter;
    }

    public void setEventParticipansFighter( int eventParticipansFighter )
    {
        this.eventParticipansFighter = eventParticipansFighter;
    }

    public int getEventParticipansDuoTeams()
    {
        return eventParticipansDuoTeams;
    }

    public void setEventParticipansDuoTeams( int eventParticipansDuoTeams )
    {
        this.eventParticipansDuoTeams = eventParticipansDuoTeams;
    }

    public int getEventParticipansTeams()
    {
        return eventParticipansTeams;
    }

    public void setEventParticipansTeams( int eventParticipansTeams )
    {
        this.eventParticipansTeams = eventParticipansTeams;
    }

    public int getEventParticipansRegions()
    {
        return eventParticipansRegions;
    }

    public void setEventParticipansRegions( int eventParticipansRegions )
    {
        this.eventParticipansRegions = eventParticipansRegions;
    }

    public int getEventParticipansCountries()
    {
        return eventParticipansCountries;
    }

    public void setEventParticipansCountries( int eventParticipansCountries )
    {
        this.eventParticipansCountries = eventParticipansCountries;
    }

    public int getEventFights()
    {
        return eventFights;
    }

    public void setEventFights( int eventFights )
    {
        this.eventFights = eventFights;
    }

    public List<LabelValueList> getEventParticipansPerAgeFighter()
    {
        return eventParticipansPerAgeFighter;
    }

    public void setEventParticipansPerAgeFighter( List<LabelValueList> eventParticipansPerAgeFighter )
    {
        this.eventParticipansPerAgeFighter = eventParticipansPerAgeFighter;
    }

    public List<LabelValueList> getEventParticipansPerAgeDuoteams()
    {
        return eventParticipansPerAgeDuoteams;
    }

    public void setEventParticipansPerAgeDuoteams( List<LabelValueList> eventParticipansPerAgeDuoteams )
    {
        this.eventParticipansPerAgeDuoteams = eventParticipansPerAgeDuoteams;
    }

    /**
     * @return the eventParticipansNewaFighter
     */
    public int getEventParticipansNewaFighter()
    {
        return eventParticipansNewaFighter;
    }

    /**
     * @param eventParticipansNewaFighter the eventParticipansNewaFighter to set
     */
    public void setEventParticipansNewaFighter( int eventParticipansNewaFighter )
    {
        this.eventParticipansNewaFighter = eventParticipansNewaFighter;
    }

    /**
     * @return the eventFightsFighting
     */
    public int getEventFightsFighting()
    {
        return eventFightsFighting;
    }

    /**
     * @param eventFightsFighting the eventFightsFighting to set
     */
    public void setEventFightsFighting( int eventFightsFighting )
    {
        this.eventFightsFighting = eventFightsFighting;
    }

    /**
     * @return the eventFightsDuo
     */
    public int getEventFightsDuo()
    {
        return eventFightsDuo;
    }

    /**
     * @param eventFightsDuo the eventFightsDuo to set
     */
    public void setEventFightsDuo( int eventFightsDuo )
    {
        this.eventFightsDuo = eventFightsDuo;
    }

    /**
     * @return the eventFightsNewa
     */
    public int getEventFightsNewa()
    {
        return eventFightsNewa;
    }

    /**
     * @param eventFightsNewa the eventFightsNewa to set
     */
    public void setEventFightsNewa( int eventFightsNewa )
    {
        this.eventFightsNewa = eventFightsNewa;
    }

    /**
     * @return the eventFightsFightingOpen
     */
    public int getEventFightsFightingOpen()
    {
        return eventFightsFightingOpen;
    }

    /**
     * @param eventFightsFightingOpen the eventFightsFightingOpen to set
     */
    public void setEventFightsFightingOpen( int eventFightsFightingOpen )
    {
        this.eventFightsFightingOpen = eventFightsFightingOpen;
    }

    /**
     * @return the eventFightsDuoOpen
     */
    public int getEventFightsDuoOpen()
    {
        return eventFightsDuoOpen;
    }

    /**
     * @param eventFightsDuoOpen the eventFightsDuoOpen to set
     */
    public void setEventFightsDuoOpen( int eventFightsDuoOpen )
    {
        this.eventFightsDuoOpen = eventFightsDuoOpen;
    }

    /**
     * @return the eventFightsNewaOpen
     */
    public int getEventFightsNewaOpen()
    {
        return eventFightsNewaOpen;
    }

    /**
     * @param eventFightsNewaOpen the eventFightsNewaOpen to set
     */
    public void setEventFightsNewaOpen( int eventFightsNewaOpen )
    {
        this.eventFightsNewaOpen = eventFightsNewaOpen;
    }

    /**
     * @return the eventParticipansPerAgeNewaFighter
     */
    public List<LabelValueList> getEventParticipansPerAgeNewaFighter()
    {
        return eventParticipansPerAgeNewaFighter;
    }

    /**
     * @param eventParticipansPerAgeNewaFighter the eventParticipansPerAgeNewaFighter to set
     */
    public void setEventParticipansPerAgeNewaFighter( List<LabelValueList> eventParticipansPerAgeNewaFighter )
    {
        this.eventParticipansPerAgeNewaFighter = eventParticipansPerAgeNewaFighter;
    }

}
