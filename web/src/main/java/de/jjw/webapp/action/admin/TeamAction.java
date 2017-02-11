/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : TeamAction.java
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


import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.faces.component.UIData;
import javax.faces.model.SelectItem;
import org.springframework.dao.DataIntegrityViolationException;
import org.apache.myfaces.custom.fileupload.UploadedFile;

import de.jjw.model.Team;
import de.jjw.model.admin.Age;
import de.jjw.model.admin.ConfigJJW;
import de.jjw.model.admin.Country;
import de.jjw.model.admin.Region;
import de.jjw.model.generalhelper.Codestable;
import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.model.newa.NewaFighter;
import de.jjw.service.TeamManager;
import de.jjw.service.admin.AgeManager;
import de.jjw.service.admin.CountryManager;
import de.jjw.service.admin.RegionManager;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.exception.TeamInUseException;
import de.jjw.service.util.IGlobalProjectConstants;
import de.jjw.util.ICodestableTypeConstants;
import de.jjw.util.IValueConstants;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.action.validation.TeamValidator;
import de.jjw.webapp.constants.ITeamConstants;

import de.jjw.service.util.IGlobalProjectConstants;

public class TeamAction
    extends BasePage
    implements Serializable, IGlobalWebConstants, ITeamConstants
{
    private static String NEW_TEAM = "newTeam";

    private static String EDIT_TEAM = "editTeam";

    private static String UPLOAD_TEAM = "uploadTeam";

    private static String UPLOAD_APPLICATIONS = "uploadApplications";

    private static String ALL_TEAMS = "allTeams";

    private TeamManager teamManager;

    private Team team = new Team();

    private List<Team> teams;

    private List<Region> regions = new ArrayList<Region>();

    private CountryManager countryManager;

    private RegionManager regionManager;
    
    private AgeManager ageManager;

    private UploadedFile logoFile;
    
    private boolean delete;
    

    public List<Team> getTeams()
    {
        if ( teamManager == null )
        {
            log.warn( "keine teamManager Injection" );
        }
        if ( getRequest().getAttribute( ALL_TEAMS ) == null )
        {
            teams = teamManager.getAllTeams();
            getRequest().setAttribute( ALL_TEAMS, ALL_TEAMS );
        }
        return teams;
    }

    public String addTeam()
    {
        String ret = ALL_TEAMS;
        Vector<ErrorElement> errors = new Vector<ErrorElement>();

        if ( "change".equals( getRequest().getParameter( "main:adminTeamAction:change" ) ) )
        {
            getRequest().setAttribute( WEB_ADMIN_TEAM_NEW, WEB_ADMIN_TEAM_NEW );
            setRegions( regionManager.getRegionsByCountry( team.getCountry().getId() ) );
            getFacesContext().getExternalContext().getRequestMap().put( "focus", "main:adminTeamAction:region" );
            return null;
        }

        if ( !TeamValidator.isRequiredFieldsValid( team, errors ) ||
            !TeamValidator.isBusinessLogicValid( team, errors, teamManager.getAllTeams(), false ) )
        {
            setErrorMessageVector( errors );
            return null;
        }

        setTechnicalDBFieldsForCreate( team );
        try
        {
            if ( logoFile != null && logoFile.getBytes() != null )
            {
                team.setLogo( logoFile.getBytes() );
            }
            teamManager.saveTeam( team, TeamManager.DELETE_LOGO );

        }
        catch ( DataIntegrityViolationException e )
        {
            errors.add( new ErrorElement( FIELD_TEAMNAME, ADMIN_TEAM_EXISTS ) );
            setErrorMessageVector( errors );
            return null;
        }
        catch ( Exception e )
        {
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
            return null;
        }
        setTeam( new Team() );
        return ret;
    }

    public String editTeam()
    {
        String ret = ALL_TEAMS;

        if ( "change".equals( getRequest().getParameter( "main:adminTeamAction:change" ) ) )
        {
            setRegions( regionManager.getRegionsByCountry( team.getCountry().getId() ) );
            getFacesContext().getExternalContext().getRequestMap().put( "focus", "main:adminTeamAction:region" );
            return null;
        }

        Vector<ErrorElement> errors = new Vector<ErrorElement>();
        if ( !TeamValidator.isRequiredFieldsValid( team, errors ) ||
            !TeamValidator.isBusinessLogicValid( team, errors, teamManager.getAllTeams(), true ) )
        {
            setErrorMessageVector( errors );
            return null;
        }
        setTechnicalDBFieldsForUpdate( team );
        try
        {
            // delete logo
            if ( isDelete() )
            {
                teamManager.saveTeam( team, TeamManager.DELETE_LOGO );
            }
            else if ( logoFile != null && logoFile.getBytes() != null )
            {
                team.setLogo( logoFile.getBytes() );
            }
            teamManager.saveTeam( team, TeamManager.CHANGE_LOGO );
        }
        catch ( DataIntegrityViolationException e )
        {
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR, GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
            ret = null;
        }

        catch ( Exception e )
        {
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
            ret = null;
        }
        setTeam( new Team() );
        return ret;
    }

    public String deleteTeam()
    {
        String ret = SUCCESS;
        Vector<ErrorElement> errors = new Vector<ErrorElement>();

        try
        {
            teamManager.removeTeam( (Team) dataTable.getRowData() );
        }
        catch ( TeamInUseException e )
        {
            errors.add( new ErrorElement( FIELD_TEAMNAME, ADMIN_TEAM_IN_USE ) );
            setErrorMessageVector( errors );
            return null;
        }
        catch ( Exception e )
        {
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
            return null;
        }
        return ret;
    }

    public String gotoNewTeam()
    {
        setTeam( new Team() );
        String ret = NEW_TEAM;
        getRequest().setAttribute( WEB_ADMIN_TEAM_NEW, WEB_ADMIN_TEAM_NEW );
        return ret;
    }

    public String gotoEditTeam()
    {
        setTeam( (Team) dataTable.getRowData() );
        return EDIT_TEAM;
    }

    public String gotoUploadTeam()
    {
        setTeam( (Team) dataTable.getRowData() );
        getRequest().setAttribute( WEB_ADMIN_TEAM_UPLOAD, team );
        return UPLOAD_TEAM;
    }

    public String gotoUploadApplications()
    {
        getRequest().setAttribute( WEB_ADMIN_APPLICATION_UPLOAD, team );
        return UPLOAD_APPLICATIONS;
    }

    public String gotoAllTeams()
    {
        setTeam( new Team() );
        return ALL_TEAMS;
    }

    @Override
    public List getCountryListALL()
    {
        List ret = new ArrayList();
        Country newCountry = new Country();
        newCountry.setId( TypeUtil.LONG_OBJECT_EMPTY );
        ret.add( new SelectItem( newCountry, EMPTY_ITEM_TEXT, TypeUtil.STRING_0 ) );
        for ( Country country : getCountries() )
        {
            ret.add( new SelectItem( country, country.getDescription(), country.getId().toString() ) );
        }
        return ret;
    }

    public List<Country> getCountries()
    {
        return countryManager.getAllCountries();
    }

    @Override
    public List getRegionListALL()
    {
        List ret = new ArrayList();
        Region newRegion = new Region();
        newRegion.setId( TypeUtil.LONG_OBJECT_EMPTY );
        ret.add( new SelectItem( newRegion, EMPTY_ITEM_TEXT, TypeUtil.STRING_0 ) );

        for ( Region region : getRegions() )
        {
            if ( region != null )
            {
                ret.add( new SelectItem( region, region.getDescription(), region.getId().toString() ) );
            }
        }

        return ret;
    }

    public List<Region> getRegions()
    {

        if ( team != null && team.getCountry() != null && team.getCountry().getId() != null )
        {
            setRegions( regionManager.getRegionsByCountry( team.getCountry().getId() ) );
        }
        if ( regions == null || regions.size() == 0 )
        {

            if ( getRequest().getParameter( "main:adminTeamAction:country" ) != null )
            {
                setRegions( regionManager.getRegionsByCountry(
                    Long.valueOf( getRequest().getParameter( "main:adminTeamAction:country" ) ) ) );
            }

        }

        return regions;
    }

    public void setRegions( List<Region> regions )
    {
        this.regions = regions;
    }

    /**
     * @return Returns the team.
     */
    public Team getTeam()
    {
        return team;
    }

    /**
     * @param team The team to set.
     */
    public void setTeam( Team team )
    {
        this.team = team;
    }



    public UploadedFile getLogoFile()
    {
        return logoFile;
    }

    public void setLogoFile( UploadedFile logoFile )
    {
        this.logoFile = logoFile;
    }

    public String getImageCommand()
    {
        StringBuffer sb = new StringBuffer( 50 );
        // sb.append(getFacesContext().getExternalContext().getRequestContextPath());
        sb.append( "/image?id=" );
        if ( team != null && team.getId() != null && team.getLogo() != null )
        {
            sb.append( team.getId() );
        }
        else
        {
            return null;
        }
        return sb.toString();
    }

    public boolean isDelete()
    {
        return delete;
    }

    public void setDelete( boolean delete )
    {
        this.delete = delete;
    }
    
    /**
     * @param teamManager The teamManager to set.
     */
    public void setTeamManager( TeamManager teamManager )
    {
        this.teamManager = teamManager;
    }

    /**
     * @param countryManager The countryManager to set.
     */
    public void setCountryManager( CountryManager countryManager )
    {
        this.countryManager = countryManager;
    }

    /**
     * @param regionManager The regionManager to set.
     */
    public void setRegionManager( RegionManager regionManager )
    {
        this.regionManager = regionManager;
    }

    /**
     * @return the ageManager
     */
    public AgeManager getAgeManager()
    {
        return ageManager;
    }

    /**
     * @param ageManager the ageManager to set
     */
    public void setAgeManager( AgeManager ageManager )
    {
        this.ageManager = ageManager;
    }
}