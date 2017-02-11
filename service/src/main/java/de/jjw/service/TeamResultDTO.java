package de.jjw.service;

import java.io.Serializable;
import java.util.List;

import de.jjw.service.modelWeb.DuoTeamWeb;
import de.jjw.service.modelWeb.FighterWeb;

public class TeamResultDTO
    implements Serializable
{

    private List<FighterWeb> fighterList = null;

    private List<DuoTeamWeb> duoteamList = null;

    public boolean isResultsForTeam()
    {
        if ( ( fighterList == null || fighterList.isEmpty() ) && ( duoteamList == null || duoteamList.isEmpty() ) )
            return false;
        return true;
    }

    /**
     * @return the fighterList
     */
    public List<FighterWeb> getFighterList()
    {
        return fighterList;
    }

    /**
     * @param fighterList the fighterList to set
     */
    public void setFighterList( List<FighterWeb> fighterList )
    {
        this.fighterList = fighterList;
    }

    /**
     * @return the duoteamList
     */
    public List<DuoTeamWeb> getDuoteamList()
    {
        return duoteamList;
    }

    /**
     * @param duoteamList the duoteamList to set
     */
    public void setDuoteamList( List<DuoTeamWeb> duoteamList )
    {
        this.duoteamList = duoteamList;
    }

}
