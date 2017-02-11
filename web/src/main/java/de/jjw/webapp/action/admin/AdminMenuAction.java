/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : AdminMenuAction.java
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

package de.jjw.webapp.action.admin;

import java.io.IOException;
import java.io.Serializable;

import de.jjw.service.duo.DuoTeamManager;
import de.jjw.service.fighting.FighterManager;
import de.jjw.service.newa.NewaFighterManager;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.admin.newa.NewaResultsAction;
import de.jjw.webapp.constants.admin.IAgeConstants;

public class AdminMenuAction
    extends BasePage
    implements Serializable, IGlobalWebConstants, IAgeConstants
{

    FighterManager fighterManager = null;

    DuoTeamManager duoTeamManager = null;

    NewaFighterManager newaFighterManager = null;

    public String start()
    {
        try
        {
            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/start.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String logout()
    {
        try
        {
            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/logout" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String teamParticipants()
    {
        try
        {
            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/teamParticipants.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    // Fighting ------------------------------
    public String editFighter()
    {
        try
        {
            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/editFighter.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String showFighter()
    {
        try
        {
            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/showFighter.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String showNotRegistratedFighter()
    {
        try
        {
            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/showNotRegistratedFighter.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String createFightingclass()
    {
        try
        {
            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/createFightingclass.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String fightingclassOverview()
    {
        try
        {
            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/fightingclassOverview.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String showFightingclassAsPdf()
    {
        try
        {
            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/showFightingclassAsPdf.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String userFightingclassOverview()
    {
        try
        {
            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/userFightingclassOverview.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String showWeightclasses()
    {
        try
        {
            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/showWeightclasses.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String showFightingResults()
    {
        try
        {
            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/showFightingResults.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String showFightingResultsAsPdf()
    {
        try
        {
            FightingResultsAction fra = new FightingResultsAction();
            fra.setFighterManager( fighterManager );
            return fra.showFightingclassAsPdf();
            // getFacesContext().getExternalContext().redirect( getFacesContext().getExternalContext().encodeActionURL(
            // getFacesContext().getExternalContext().getRequestContextPath() +
            // "/admin/showFightingResultsAsPdf.html" ) );
        }
        catch ( Exception e )
        {
            ;
        }
        return null;
    }

    // duo --------------------------------------------------------
    public String editDuoTeam()
    {
        try
        {
            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/editDuoTeam.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String showDuoTeams()
    {
        try
        {
            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/showDuoTeams.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String showNotRegistratedDuoTeams()
    {
        try
        {
            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/showNotRegistratedDuoTeams.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String createDuoclass()
    {
        try
        {
            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/createDuoclass.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String duoclassOverview()
    {
        try
        {
            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/duoclassOverview.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String userDuoclassOverview()
    {
        try
        {
            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/userDuoclassOverview.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String showDuoclasses()
    {
        try
        {
            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/showDuoclasses.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String showDuoResults()
    {
        try
        {
            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/showDuoResults.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String showDuoResultsAsPdf()
    {
        try
        {
            DuoTeamResultsAction fra = new DuoTeamResultsAction();
            fra.setDuoTeamManager( duoTeamManager );
            return fra.showDuoResultAsPdf();
            // getFacesContext().getExternalContext().redirect( getFacesContext().getExternalContext().encodeActionURL(
            // getFacesContext().getExternalContext().getRequestContextPath() +
            // "/admin/showFightingResultsAsPdf.html" ) );
        }
        catch ( Exception e )
        {
            ;
        }
        return null;
    }

    // admin --------------------------------------------
    public String showUsers()
    {
        try
        {
            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/showUsers.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String showRegions()
    {
        try
        {
            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/showRegions.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String showCountries()
    {
        try
        {
            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/showCountries.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String showAges()
    {
        try
        {
            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/showAges.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String showTeams()
    {
        try
        {
            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/showTeams.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String showConfig()
    {
        try
        {
            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/showConfig.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String showFightsystem()
    {
        try
        {
            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/showFightsystem.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String showStatistics()
    {
        try
        {
            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/showStatistics.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String showFriendlies()
    {
        try
        {
            getFacesContext().getExternalContext().redirect( getFacesContext().getExternalContext().encodeActionURL( getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/friendlies.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    // newa ------------------------------
    public String editNewaFighter()
    {
        try
        {
            getFacesContext().getExternalContext().redirect( getFacesContext().getExternalContext().encodeActionURL( getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/editNewaFighter.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String showNewaFighter()
    {
        try
        {
            getFacesContext().getExternalContext().redirect( getFacesContext().getExternalContext().encodeActionURL( getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/showNewaFighter.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String showNotRegistratedNewaFighter()
    {
        try
        {
            getFacesContext().getExternalContext().redirect( getFacesContext().getExternalContext().encodeActionURL( getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/showNotRegistratedNewaFighter.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String createNewaclass()
    {
        try
        {
            getFacesContext().getExternalContext().redirect( getFacesContext().getExternalContext().encodeActionURL( getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/createNewaclass.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String newaclassOverview()
    {
        try
        {
            getFacesContext().getExternalContext().redirect( getFacesContext().getExternalContext().encodeActionURL( getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/newaclassOverview.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String showNewaclassAsPdf()
    {
        try
        {
            getFacesContext().getExternalContext().redirect( getFacesContext().getExternalContext().encodeActionURL( getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/showNewaclassAsPdf.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String userNewaclassOverview()
    {
        try
        {
            getFacesContext().getExternalContext().redirect( getFacesContext().getExternalContext().encodeActionURL( getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/userNewaclassOverview.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String showNewaWeightclasses()
    {
        try
        {
            getFacesContext().getExternalContext().redirect( getFacesContext().getExternalContext().encodeActionURL( getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/showNewaWeightclasses.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String showNewaResults()
    {
        try
        {
            getFacesContext().getExternalContext().redirect( getFacesContext().getExternalContext().encodeActionURL( getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/showNewaResults.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String showNewaResultsAsPdf()
    {
        try
        {
            NewaResultsAction fra = new NewaResultsAction();
            fra.setNewaFighterManager( newaFighterManager );
            return fra.showFightingclassAsPdf();
        }
        catch ( Exception e )
        {
            ;
        }
        return null;
    }

    // press ---------------------------
    public String pressInfos()
    {
        try
        {
            getFacesContext().getExternalContext().redirect( getFacesContext().getExternalContext().encodeActionURL( getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/pressInfos.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String showMedalRanking()
    {
        try
        {
            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/showMedalRanking.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String allPreviews()
    {
        try
        {
            getFacesContext().getExternalContext().redirect( getFacesContext().getExternalContext().encodeActionURL( getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/admin/allPreviews.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public FighterManager getFighterManager()
    {
        return fighterManager;
    }

    public void setFighterManager( FighterManager fighterManager )
    {
        this.fighterManager = fighterManager;
    }

    public DuoTeamManager getDuoTeamManager()
    {
        return duoTeamManager;
    }

    public void setDuoTeamManager( DuoTeamManager duoTeamManager )
    {
        this.duoTeamManager = duoTeamManager;
    }

    /**
     * @return the newaFighterManager
     */
    public NewaFighterManager getNewaFighterManager()
    {
        return newaFighterManager;
    }

    /**
     * @param newaFighterManager the newaFighterManager to set
     */
    public void setNewaFighterManager( NewaFighterManager newaFighterManager )
    {
        this.newaFighterManager = newaFighterManager;
    }

}
