/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : DuoTeamAction.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:47
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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.springframework.dao.DataIntegrityViolationException;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.Team;
import de.jjw.model.admin.Age;
import de.jjw.model.admin.Country;
import de.jjw.model.admin.Region;
import de.jjw.model.duo.DuoTeam;
import de.jjw.model.fighting.Fighter;
import de.jjw.model.generalhelper.Codestable;
import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.service.TeamManager;
import de.jjw.service.admin.AgeManager;
import de.jjw.service.admin.CountryManager;
import de.jjw.service.admin.RegionManager;
import de.jjw.service.duo.DuoTeamManager;
import de.jjw.service.exception.DuoTeamIllegalReadyException;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.exception.JJWPoolBlockedException;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.action.validation.duo.DuoTeamValidator;

public class DuoTeamAction
    extends BasePage
    implements Serializable, IGlobalWebConstants
{
    
    private static String ALL_AGES = "allAges";

    private static String ALL_COUNTRY = "allCountry";

    private static String GOTO_ALL_DUOTEAM = "showDuoTeams";

    private static String GOTO_NOT_REG_DUOTEAM = "showNotRegDuoTeam";

    private static String GOTO_NEW_DUOTEAM = "editDuoTeam";

    DuoTeamManager duoTeamManager = null;

    AgeManager ageManager = null;

    CountryManager countryManager = null;

    RegionManager regionManager = null;

    TeamManager teamManager = null;

    private List<Age> ages = null;

    private List<Country> countries = null;

    private List<Region> regions = new ArrayList<Region>();

    private List<Team> teams = new ArrayList<Team>();

    private DuoTeam duoTeam = null;

    private Country country = new Country();

    private Region region = null;

    private Team team = null;

    public String addDuoTeam()
    {
        String ret = GOTO_NEW_DUOTEAM;
        Vector<ErrorElement> errors = new Vector<ErrorElement>();
        try
        {

            getDuoTeam().setTeam( getTeam() );
            // Code only for change Events (Post) of comboboxes
            if ( "change".equals( getRequest().getParameter( "main:adminDuoTeamAction:change" ) ) )
            {
                setRegions( regionManager.getRegionsByCountry( getCountry().getId() ) );

                boolean validRegion = false;
                for ( Region regionTmp : getRegions() )
                {
                    if ( getRegion() != null && getRegion().getId().longValue() == regionTmp.getId().longValue() )
                    {
                        validRegion = true;
                        break;
                    }
                }

                if ( validRegion )
                {
                    teams = teamManager.getTeamsByCountryAndRegion( getCountry().getId(), getRegion().getId() );
                    getFacesContext().getExternalContext().getRequestMap().put( "focus", "main:adminDuoTeamAction:team" );
                }
                else
                {
                    teams = new ArrayList<Team>();
                    this.region = null;
                    getFacesContext().getExternalContext().getRequestMap().put( "focus",
                                                                                "main:adminDuoTeamAction:region" );
                }

                return null;
            }
            // validate and save duoteam
            else
            {
                if ( !DuoTeamValidator.isRequiredFieldsValid( getDuoTeam(), errors )
                    && !DuoTeamValidator.isBusinessLogicValid( getDuoTeam(), errors ) )
                {
                    setErrorMessageVector( errors );
                    return null;
                }
            }
            if ( duoTeamManager.existDuoTeam( getDuoTeam() ) )
            {
                errors.add( new ErrorElement( ADMIN_DUOTEAM_ALREADY_EXIST ) );
                setErrorMessageVector( errors );
                return null;
            }

            setTechnicalDBFieldsForCreate( getDuoTeam() );

            duoTeamManager.saveDuoTeam( getDuoTeam() );

        }
        catch ( DataIntegrityViolationException e )
        {
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
            ret = null;
        }
        catch ( DuoTeamIllegalReadyException e )
        {
            errors.add( new ErrorElement( ADMIN_DUOTEAM_ILLEGAL_REGISTRATION ) );
            setErrorMessageVector( errors );
            ret = null;
        }
        catch ( JJWPoolBlockedException e )
        {
            errors.add( new ErrorElement( ADMIN_DUOTEAM_BLOCKED_POOL ) );
            setErrorMessageVector( errors );
            ret = null;
        }
        catch ( Exception e )
        {
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
            ret = null;
        }

        return ret;
    }

    public String addDuoTeamAndNextTeammember()
    {
        String ret = null;
        Vector<ErrorElement> errors = new Vector<ErrorElement>();
        try
        {

            getDuoTeam().setTeam( getTeam() );
            // Code only for change Events (Post) of comboboxes
            if ( "change".equals( getRequest().getParameter( "main:adminDuoTeamAction:change" ) ) )
            {
                setRegions( regionManager.getRegionsByCountry( getCountry().getId() ) );

                boolean validRegion = false;
                for ( Region regionTmp : getRegions() )
                {
                    if ( getRegion() != null && getRegion().getId().longValue() == regionTmp.getId().longValue() )
                    {
                        validRegion = true;
                        break;
                    }
                }

                if ( validRegion )
                {
                    teams = teamManager.getTeamsByCountryAndRegion( getCountry().getId(), getRegion().getId() );
                    getFacesContext().getExternalContext().getRequestMap().put( "focus", "main:adminDuoTeamAction:team" );
                }
                else
                {
                    teams = new ArrayList<Team>();
                    this.region = null;
                    getFacesContext().getExternalContext().getRequestMap().put( "focus",
                                                                                "main:adminDuoTeamAction:region" );
                }

                return null;
            }
            // validate and save fighter
            else
            {
                if ( !DuoTeamValidator.isRequiredFieldsValid( getDuoTeam(), errors )
                    && !DuoTeamValidator.isBusinessLogicValid( getDuoTeam(), errors ) )
                {
                    setErrorMessageVector( errors );
                    return null;
                }
            }
            if ( duoTeamManager.existDuoTeam( getDuoTeam() ) )
            {
                errors.add( new ErrorElement( ADMIN_DUOTEAM_ALREADY_EXIST ) );
                setErrorMessageVector( errors );
                return null;
            }

            setTechnicalDBFieldsForCreate( getDuoTeam() );

            duoTeamManager.saveDuoTeam( getDuoTeam() );
            duoTeam = new DuoTeam();
            duoTeam.setTeam( getTeam() );

        }
        catch ( DataIntegrityViolationException e )
        {
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
            ret = null;
        }
        catch ( DuoTeamIllegalReadyException e )
        {
            errors.add( new ErrorElement( ADMIN_DUOTEAM_ILLEGAL_REGISTRATION ) );
            setErrorMessageVector( errors );
            ret = null;
        }
        catch ( JJWPoolBlockedException e )
        {
            errors.add( new ErrorElement( ADMIN_DUOTEAM_BLOCKED_POOL ) );
            setErrorMessageVector( errors );
            ret = null;
        }
        catch ( Exception e )
        {
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
            ret = null;
        }

        return ret;
    }

    public String editDuoTeam()
    {
        String ret = GOTO_ALL_DUOTEAM;

        Vector<ErrorElement> errors = new Vector<ErrorElement>();
        // Code only for change Events (Post) of comboboxes
        if ( "change".equals( getRequest().getParameter( "main:adminDuoTeamAction:change" ) ) )
        {
            setRegions( regionManager.getRegionsByCountry( getCountry().getId() ) );

            boolean validRegion = false;
            for ( Region regionTmp : getRegions() )
            {
                if ( getRegion() != null && getRegion().getId().longValue() == regionTmp.getId().longValue() )
                {
                    validRegion = true;
                    break;
                }
            }

            if ( validRegion )
            {
                teams = teamManager.getTeamsByCountryAndRegion( getCountry().getId(), getRegion().getId() );
                getFacesContext().getExternalContext().getRequestMap().put( "focus", "main:adminDuoTeamAction:team" );
            }
            else
            {
                teams = new ArrayList<Team>();
                this.region = null;
                getFacesContext().getExternalContext().getRequestMap().put( "focus", "main:adminDuoTeamAction:region" );
            }
            getRequest().setAttribute( WEB_ADMIN_DUOTEAM, getDuoTeam() );
            return null;
        }
        else
        {

            if ( !DuoTeamValidator.isRequiredFieldsValid( getDuoTeam(), errors ) )
            {
                setErrorMessageVector( errors );
                return null;
            }
            
            try
            {
                if ( duoTeamManager.existDuoTeam( getDuoTeam() ) )
                {
                    errors.add( new ErrorElement( ADMIN_DUOTEAM_ALREADY_EXIST ) );
                    setErrorMessageVector( errors );
                    return null;
                }

                setTechnicalDBFieldsForUpdate( getDuoTeam() );

                duoTeamManager.saveDuoTeam( getDuoTeam() );

            }
            catch ( DataIntegrityViolationException e )
            {
                errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
                setErrorMessageVector( errors );
                ret = null;
            }
            catch ( JJWPoolBlockedException e )
            {
                errors.add( new ErrorElement( ADMIN_DUOTEAM_BLOCKED_POOL ) );
                setErrorMessageVector( errors );
                ret = null;
            }
            catch ( DuoTeamIllegalReadyException e )
            {
                errors.add( new ErrorElement( ADMIN_DUOTEAM_ILLEGAL_REGISTRATION ) );
                setErrorMessageVector( errors );
                ret = null;
            }
            catch ( Exception e )
            {
                errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
                setErrorMessageVector( errors );
                ret = null;
            }
        }
        return ret;
    }

    /**
     * function to edit from not registrated duoteams
     * 
     * @return
     */
    public String editNotRegDuoTeam()
    {
        String ret = GOTO_NOT_REG_DUOTEAM;

        Vector<ErrorElement> errors = new Vector<ErrorElement>();
        // Code only for change Events (Post) of comboboxes
        if ( "change".equals( getRequest().getParameter( "main:adminDuoTeamAction:change" ) ) )
        {
            setRegions( regionManager.getRegionsByCountry( getCountry().getId() ) );

            boolean validRegion = false;
            for ( Region regionTmp : getRegions() )
            {
                if ( getRegion() != null && getRegion().getId().longValue() == regionTmp.getId().longValue() )
                {
                    validRegion = true;
                    break;
                }
            }

            if ( validRegion )
            {
                teams = teamManager.getTeamsByCountryAndRegion( getCountry().getId(), getRegion().getId() );
                getFacesContext().getExternalContext().getRequestMap().put( "focus", "main:adminDuoTeamAction:team" );
            }
            else
            {
                teams = new ArrayList<Team>();
                this.region = null;
                getFacesContext().getExternalContext().getRequestMap().put( "focus", "main:adminDuoTeamAction:region" );
            }
            getRequest().setAttribute( WEB_ADMIN_DUOTEAM, getDuoTeam() );
            return null;
        }
        else
        {

            if ( !DuoTeamValidator.isRequiredFieldsValid( getDuoTeam(), errors ) )
            {
                setErrorMessageVector( errors );
                return null;
            }

            setTechnicalDBFieldsForUpdate( getDuoTeam() );
            try
            {
                duoTeamManager.saveDuoTeam( getDuoTeam() );

            }
            catch ( DataIntegrityViolationException e )
            {
                errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
                setErrorMessageVector( errors );
                ret = null;
            }
            catch ( JJWPoolBlockedException e )
            {
                errors.add( new ErrorElement( ADMIN_DUOTEAM_BLOCKED_POOL ) );
                setErrorMessageVector( errors );
                ret = null;
            }
            catch ( DuoTeamIllegalReadyException e )
            {
                errors.add( new ErrorElement( ADMIN_DUOTEAM_ILLEGAL_REGISTRATION ) );
                setErrorMessageVector( errors );
                ret = null;
            }
            catch ( Exception e )
            {
                errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
                setErrorMessageVector( errors );
                ret = null;
            }
        }
        return ret;
    }

    public String gotoNotRegDuoTeams()
    {
        return GOTO_NOT_REG_DUOTEAM;
    }

    public String gotoAllDuoTeams()
    {

        return "showDuoTeams";
    }

    public List<Age> getAges()
    {
        if ( ageManager == null )
        {
            log.warn( "keine ageManager Injection" );
        }
        if ( getRequest().getAttribute( ALL_AGES ) == null )
        {
            try
            {
                ages = ageManager.getAllAges();
            }
            catch ( JJWManagerException e )
            {
                addErrorElement( new ErrorElement( GEN_GERNERAL_ERROR ) );
            }
            getRequest().setAttribute( ALL_AGES, ALL_AGES );
        }
        return ages;
    }

    public List<Country> getCountries()
    {
        if ( countryManager == null )
        {
            log.warn( "keine fcountryManager Injection" );
        }
        if ( getRequest().getAttribute( ALL_COUNTRY ) == null )
        {
            countries = countryManager.getAllCountries();
            getRequest().setAttribute( ALL_COUNTRY, ALL_COUNTRY );
        }

        if ( WebExchangeContextHelper.getTemporaryAttribute( WEB_ADMIN_DUOTEAM_COUNTRY, getSession() ) == null
            && !countries.isEmpty() )
        {
            WebExchangeContextHelper.setTemporaryAttribute( WEB_ADMIN_DUOTEAM_COUNTRY, countries, getSession() );
        }
        return countries;
    }

    public List<Team> getTeams()
    {

        if ( teams == null || teams.size() == 0 )
        {

            if ( getRequest().getParameter( "main:adminDuoTeamAction:country" ) != null
                && getRequest().getParameter( "main:adminDuoTeamAction:region" ) != null )
            {
                teams =
                    teamManager.getTeamsByCountryAndRegion(
                                                            Long.valueOf(
                                                                          getRequest().getParameter(
                                                                                                     "main:adminDuoTeamAction:country" ) ).longValue(),
                                                            Long.valueOf(
                                                                          getRequest().getParameter(
                                                                                                     "main:adminDuoTeamAction:region" ) ).longValue() );
            }

        }

        return teams;
    }

    /**
     * @return Returns the duoTeam.
     */
    public DuoTeam getDuoTeam()
    {

        if ( duoTeam == null )
        {
            if ( getRequest().getAttribute( WEB_ADMIN_DUOTEAM ) != null )
            {
                // came from all fightersite
                duoTeam = (DuoTeam) getRequest().getAttribute( WEB_ADMIN_DUOTEAM );
                if ( duoTeam != null && duoTeam.getTeam() != null )
                {
                    team = duoTeam.getTeam();
                    if ( duoTeam.getTeam().getCountry() != null )
                    {
                        country = duoTeam.getTeam().getCountry();
                        setRegions( regionManager.getRegionsByCountry( duoTeam.getTeam().getCountry().getId() ) );
                    }
                    if ( duoTeam.getTeam().getRegion() != null )
                    {
                        region = duoTeam.getTeam().getRegion();
                        teams =
                            teamManager.getTeamsByCountryAndRegion( duoTeam.getTeam().getCountry().getId(),
                                                                    duoTeam.getTeam().getRegion().getId() );
                    }
                }

            }
            else
            {
                duoTeam = new DuoTeam();
            }
        }

        if ( duoTeam != null && duoTeam.getTeam() == null )
        {
            duoTeam.setTeam( team );
        }

        return duoTeam;
    }
    
  
    /**
     * @param duoTeam The duoTeam to set.
     */
    public void setDuoTeam( DuoTeam duoTeam )
    {
        this.duoTeam = duoTeam;
    }

    /**
     * @param duoTeamManager The duoTeamManager to set.
     */
    public void setDuoTeamManager( DuoTeamManager duoTeamManager )
    {
        this.duoTeamManager = duoTeamManager;
    }

    /**
     * @param ageManager The ageManager to set.
     */
    public void setAgeManager( AgeManager ageManager )
    {
        this.ageManager = ageManager;
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
     * @param teamManager The teamManager to set.
     */
    public void setTeamManager( TeamManager teamManager )
    {
        this.teamManager = teamManager;
    }

    public boolean getExistAllNessesaryData()
    {
        return getAges() != null && getAges().size() > 0 && getCountries() != null && getCountries().size() > 0
            && getTeams() != null && getTeams().size() > 0;

    }

    @Override
    public List getAgeListALL()
    {
        List ret = new ArrayList();

        for ( Age age : getAges() )
        {
            ret.add( new SelectItem( age, age.getDescription(), age.getId().toString() ) );
        }
        return ret;
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

    public List getRegionList()
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
        if ( regions == null || regions.size() == 0 )
        {

            if ( getRequest().getParameter( "main:adminDuoTeamAction:country" ) != null )
            {
                setRegions( regionManager.getRegionsByCountry( Long.valueOf( getRequest().getParameter(
                                                                                                        "main:adminDuoTeamAction:country" ) ) ) );
            }

        }

        return regions;
    }

    public void setRegions( List<Region> regions )
    {
        this.regions = regions;
    }

    @Override
    public List getTeamList()
    {
        List ret = new ArrayList();
        Team newTeam = new Team();
        newTeam.setId( TypeUtil.LONG_OBJECT_EMPTY );
        ret.add( new SelectItem( newTeam, EMPTY_ITEM_TEXT, TypeUtil.STRING_0 ) );

        for ( Team team : getTeams() )
        {
            ret.add( new SelectItem( team, TypeUtil.toString( team.getTeamName() ), TypeUtil.toString( team.getId() ) ) );
        }
        return ret;
    }

    public List getSexList()
    {
        List ret = new ArrayList();

        List<Codestable> list =
            CodestableMain.getInstance().getCodestableCodeByTypeAndLevel( TYPE_SEX, getRequest().getLocale(), 2 );
        for ( Codestable entry : list )
        {
            ret.add( new SelectItem( entry.getId(), entry.getValue() ) );
        }
        return ret;
    }

    public List getReadyList()
    {
        List ret = new ArrayList();

        List<Codestable> list =
            CodestableMain.getInstance().getCodestableCodeByType( TYPE_DUOTEAM_STATUS, getRequest().getLocale() );
        for ( Codestable entry : list )
        {
            ret.add( new SelectItem( entry.getId(), entry.getValue() ) );
        }
        return ret;
    }
    
    public List getYearOfBirthList()
    {
        List ret = new ArrayList();

        Calendar cal = new GregorianCalendar();
        int currentYear = cal.get( Calendar.YEAR );

        for ( int i = currentYear; i > currentYear - 100; i-- )
        {
            ret.add( new SelectItem( new Integer( i ), String.valueOf( i ) ) );
        }
        return ret;
    }

    public Country getCountry()
    {
        return country;
    }

    public void setCountry( Country country )
    {
        this.country = country;
    }

    public Region getRegion()
    {
        return region;
    }

    public void setRegion( Region region )
    {
        this.region = region;
    }

    public Team getTeam()
    {
        return team;
    }

    public void setTeam( Team team )
    {
        this.team = team;
    }

}
