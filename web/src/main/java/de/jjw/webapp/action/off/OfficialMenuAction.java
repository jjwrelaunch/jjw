/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : OfficialMenuAction.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:46
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

package de.jjw.webapp.action.off;

import java.io.IOException;
import java.io.Serializable;

import de.jjw.service.duo.DuoTeamManager;
import de.jjw.service.fighting.FighterManager;
import de.jjw.service.newa.NewaFighterManager;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.admin.DuoTeamResultsAction;
import de.jjw.webapp.action.admin.FightingResultsAction;
import de.jjw.webapp.action.admin.newa.NewaResultsAction;
import de.jjw.webapp.constants.admin.IAgeConstants;

public class OfficialMenuAction
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
                                                                                                                         + "/off/start.html" ) );
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
                                                                                                                         + "/off/teamParticipants.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    // Fighting ------------------------------

    public String showFighter()
    {
        try
        {
            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/off/showFighter.html" ) );
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
                                                                                                                         + "/off/fightingclassOverview.html" ) );
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
                                                                                                                         + "/off/showFightingclassAsPdf.html" ) );
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
                                                                                                                         + "/off/showFightingResults.html" ) );
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
        }
        catch ( Exception e )
        {
            ;
        }
        return null;
    }

    // duo --------------------------------------------------------
    public String showDuoTeams()
    {
        try
        {
            getFacesContext().getExternalContext().redirect(
                                                             getFacesContext().getExternalContext().encodeActionURL(
                                                                                                                     getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/off/showDuoTeams.html" ) );
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
                                                                                                                         + "/off/duoclassOverview.html" ) );
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
                                                                                                                         + "/off/showDuoResults.html" ) );
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

    // Newa ------------------------------

    public String showNewaFighter()
    {
        try
        {
            getFacesContext().getExternalContext().redirect( getFacesContext().getExternalContext().encodeActionURL( getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/off/showNewaFighter.html" ) );
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
                                                                                                                         + "/off/newaclassOverview.html" ) );
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
                                                                                                                         + "/off/showNewaclassAsPdf.html" ) );
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
                                                                                                                         + "/off/showNewaResults.html" ) );
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
                                                                                                                         + "/off/pressInfos.html" ) );
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
                                                                                                                         + "/off/showMedalRanking.html" ) );
        }
        catch ( IOException e )
        {
            ;
        }
        return null;
    }

    public String showTatamiPreview()
    {
        try
        {
            getFacesContext().getExternalContext().redirect( getFacesContext().getExternalContext().encodeActionURL( getFacesContext().getExternalContext().getRequestContextPath()
                                                                                                                         + "/off/tatamiPreview.html" ) );
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
                                                                                                                         + "/off/allPreviews.html" ) );
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
