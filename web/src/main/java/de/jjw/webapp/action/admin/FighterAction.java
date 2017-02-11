/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : FighterAction.java
 * Created : 05 Jun 2010
 * Last Modified: Sat, 05 Jun 2010 20:55:45
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

import javax.faces.model.SelectItem;

import org.springframework.dao.DataIntegrityViolationException;

import de.jjw.model.Team;
import de.jjw.model.admin.Age;
import de.jjw.model.admin.Country;
import de.jjw.model.admin.Region;
import de.jjw.model.fighting.Fighter;
import de.jjw.model.generalhelper.Codestable;
import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.service.TeamManager;
import de.jjw.service.admin.AgeManager;
import de.jjw.service.admin.CountryManager;
import de.jjw.service.admin.RegionManager;
import de.jjw.service.exception.FighterIllegalReadyException;
import de.jjw.service.exception.JJWManagerException;
import de.jjw.service.exception.JJWPoolBlockedException;
import de.jjw.service.fighting.FighterManager;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.WebExchangeContextHelper;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.action.validation.fighting.FighterValidator;
import de.jjw.webapp.constants.admin.IFighterConstants;

public class FighterAction
    extends BasePage
    implements Serializable, IGlobalWebConstants, IFighterConstants
{

    private static String ALL_AGES = "allAges";

    private static String ALL_COUNTRY = "allCountry";

    private static String GOTO_ALL_FIGHTER = "showFighter";

    private static String GOTO_NOT_REG_FIGHTER = "showNotRegFighter";

    private static String GOTO_NEW_FIGHTER = "editFighter";

    FighterManager fighterManager = null;

    AgeManager ageManager = null;

    CountryManager countryManager = null;

    RegionManager regionManager = null;

    TeamManager teamManager = null;

    private List<Age> ages = null;

    private List<Country> countries = null;

    private List<Region> regions = new ArrayList<Region>();

    private List<Team> teams = new ArrayList<Team>();

    private Fighter fighter = null;

    private Country country = new Country();

    private Region region = null;

    private Team team = null;

    public String addFighter()
    {
        String ret = GOTO_NEW_FIGHTER;
        Vector<ErrorElement> errors = new Vector<ErrorElement>();
        getFighter().setTeam( getTeam() );
        // Code only for change Events (Post) of comboboxes
        if ( "change".equals( getRequest().getParameter( "main:adminFighterAction:change" ) ) )
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
                getFacesContext().getExternalContext().getRequestMap().put( "focus", "main:adminFighterAction:team" );
            }
            else
            {
                teams = new ArrayList<Team>();
                this.region = null;
                getFacesContext().getExternalContext().getRequestMap().put( "focus", "main:adminFighterAction:region" );
            }

            return null;
        }
        // validate and save fighter
        else
        {
            if ( !FighterValidator.isRequiredFieldsValid( getFighter(), errors )
                && !FighterValidator.isBusinessLogicValid( getFighter(), errors ) )
            {
                setErrorMessageVector( errors );
                return null;
            }
        }
        if ( fighterManager.existFighter( getFighter() ) )
        {
            errors.add( new ErrorElement( ADMIN_FIGHTER_ALREADY_EXIST ) );
            setErrorMessageVector( errors );
            return null;
        }
        //        
        // //check for dublicates
        // List<Fighter> dublicates = fighterManager.getPossibleDublicateFighters(getFighter());
        // if ( dublicates != null && dublicates.size() > 0) {
        // //TODO: standardrueckfrage
        // //return null;
        // }

        setTechnicalDBFieldsForCreate( getFighter() );
        try
        {
            fighterManager.saveFighter( getFighter() );

        }
        catch ( DataIntegrityViolationException e )
        {
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
            ret = null;
        }
        catch ( FighterIllegalReadyException e )
        {
            errors.add( new ErrorElement( ADMIN_FIGHTER_ILLEGAL_REGISTRATION ) );
            setErrorMessageVector( errors );
            ret = null;
        }
        catch ( JJWPoolBlockedException e )
        {
            errors.add( new ErrorElement( ADMIN_FIGHTER_BLOCKED_POOL ) );
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

    public String addFighterAndNextTeammember()
    {
        String ret = null;
        Vector<ErrorElement> errors = new Vector<ErrorElement>();
        getFighter().setTeam( getTeam() );
        // Code only for change Events (Post) of comboboxes
        if ( "change".equals( getRequest().getParameter( "main:adminFighterAction:change" ) ) )
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
                getFacesContext().getExternalContext().getRequestMap().put( "focus", "main:adminFighterAction:team" );
            }
            else
            {
                teams = new ArrayList<Team>();
                this.region = null;
                getFacesContext().getExternalContext().getRequestMap().put( "focus", "main:adminFighterAction:region" );
            }

            return null;
        }
        // validate and save fighter
        else
        {
            if ( !FighterValidator.isRequiredFieldsValid( getFighter(), errors )
                && !FighterValidator.isBusinessLogicValid( getFighter(), errors ) )
            {
                setErrorMessageVector( errors );
                return null;
            }
        }
        if ( fighterManager.existFighter( getFighter() ) )
        {
            errors.add( new ErrorElement( ADMIN_FIGHTER_ALREADY_EXIST ) );
            setErrorMessageVector( errors );
            return null;
        }
        //        
        // //check for dublicates
        // List<Fighter> dublicates = fighterManager.getPossibleDublicateFighters(getFighter());
        // if ( dublicates != null && dublicates.size() > 0) {
        // //TODO: standardrueckfrage
        // //return null;
        // }

        setTechnicalDBFieldsForCreate( getFighter() );
        try
        {
            fighterManager.saveFighter( getFighter() );

            fighter = new Fighter();
            fighter.setTeam( getTeam() );

        }
        catch ( DataIntegrityViolationException e )
        {
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
            ret = null;
        }
        catch ( FighterIllegalReadyException e )
        {
            errors.add( new ErrorElement( ADMIN_FIGHTER_ILLEGAL_REGISTRATION ) );
            setErrorMessageVector( errors );
            ret = null;
        }
        catch ( JJWPoolBlockedException e )
        {
            errors.add( new ErrorElement( ADMIN_FIGHTER_BLOCKED_POOL ) );
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

    public String editFighter()
    {
        String ret = GOTO_ALL_FIGHTER;

        Vector<ErrorElement> errors = new Vector<ErrorElement>();
        // Code only for change Events (Post) of comboboxes
        if ( "change".equals( getRequest().getParameter( "main:adminFighterAction:change" ) ) )
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
                getFacesContext().getExternalContext().getRequestMap().put( "focus", "main:adminFighterAction:team" );
            }
            else
            {
                teams = new ArrayList<Team>();
                this.region = null;
                getFacesContext().getExternalContext().getRequestMap().put( "focus", "main:adminFighterAction:region" );
            }
            getRequest().setAttribute( WEB_ADMIN_FIGHTER, getFighter() );
            return null;
        }
        else
        {

            if ( !FighterValidator.isRequiredFieldsValid( getFighter(), errors ) 
                  && !FighterValidator.isBusinessLogicValid( getFighter(), errors ))
            {
                setErrorMessageVector( errors );
                return null;
            }
            
            if ( fighterManager.existFighter( getFighter() ) )
            {
                errors.add( new ErrorElement( ADMIN_FIGHTER_ALREADY_EXIST ) );
                setErrorMessageVector( errors );
                return null;
            }

            setTechnicalDBFieldsForUpdate( getFighter() );
            try
            {
                fighterManager.saveFighter( getFighter() );

            }
            catch ( DataIntegrityViolationException e )
            {
                errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
                setErrorMessageVector( errors );
                log.error( e );
                ret = null;
            }
            catch ( JJWPoolBlockedException e )
            {
                errors.add( new ErrorElement( ADMIN_FIGHTER_BLOCKED_POOL ) );
                setErrorMessageVector( errors );
                ret = null;
            }
            catch ( FighterIllegalReadyException e )
            {
                errors.add( new ErrorElement( ADMIN_FIGHTER_ILLEGAL_REGISTRATION ) );
                setErrorMessageVector( errors );
                ret = null;
            }            
            catch ( Exception e )
            {
                errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
                setErrorMessageVector( errors );
                log.error( e );
                ret = null;
            }
        }
        return ret;
    }

    /**
     * function is called, when edit fighter from notReg fighter page
     * 
     * @return
     */
    public String editNotRegFighter()
    {
        String ret = GOTO_NOT_REG_FIGHTER;

        Vector<ErrorElement> errors = new Vector<ErrorElement>();
        // Code only for change Events (Post) of comboboxes
        if ( "change".equals( getRequest().getParameter( "main:adminFighterAction:change" ) ) )
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
                getFacesContext().getExternalContext().getRequestMap().put( "focus", "main:adminFighterAction:team" );
            }
            else
            {
                teams = new ArrayList<Team>();
                this.region = null;
                getFacesContext().getExternalContext().getRequestMap().put( "focus", "main:adminFighterAction:region" );
            }
            getRequest().setAttribute( WEB_ADMIN_FIGHTER, getFighter() );
            return null;
        }
        else
        {

            if ( !FighterValidator.isRequiredFieldsValid( getFighter(), errors ) )
            {
                setErrorMessageVector( errors );
                return null;
            }

            setTechnicalDBFieldsForUpdate( getFighter() );
            try
            {
                fighterManager.saveFighter( getFighter() );

            }
            catch ( DataIntegrityViolationException e )
            {
                errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
                setErrorMessageVector( errors );
                ret = null;
            }
            catch ( JJWPoolBlockedException e )
            {
                errors.add( new ErrorElement( ADMIN_FIGHTER_BLOCKED_POOL ) );
                setErrorMessageVector( errors );
                ret = null;
            }
            catch ( FighterIllegalReadyException e )
            {
                errors.add( new ErrorElement( ADMIN_FIGHTER_ILLEGAL_REGISTRATION ) );
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

    public String gotoNotRegFighter()
    {
        return GOTO_NOT_REG_FIGHTER;
    }

    public String gotoAllFighter()
    {

        return "showFighter";
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

        if ( WebExchangeContextHelper.getTemporaryAttribute( WEB_ADMIN_FIGHTER_COUNTRY, getSession() ) == null
            && !countries.isEmpty() )
        {
            WebExchangeContextHelper.setTemporaryAttribute( WEB_ADMIN_FIGHTER_COUNTRY, countries, getSession() );
        }
        return countries;
    }

    public List<Team> getTeams()
    {

        if ( teams == null || teams.size() == 0 )
        {

            if ( getRequest().getParameter( "main:adminFighterAction:country" ) != null
                && getRequest().getParameter( "main:adminFighterAction:region" ) != null )
            {
                teams =
                    teamManager.getTeamsByCountryAndRegion(
                                                            Long.valueOf(
                                                                          getRequest().getParameter(
                                                                                                     "main:adminFighterAction:country" ) ).longValue(),
                                                            Long.valueOf(
                                                                          getRequest().getParameter(
                                                                                                     "main:adminFighterAction:region" ) ).longValue() );
            }

        }

        return teams;
    }

    /**
     * @return Returns the fighter.
     */
    public Fighter getFighter()
    {

        if ( fighter == null )
        {
            if ( getRequest().getAttribute( WEB_ADMIN_FIGHTER ) != null )
            {
                // came from all fightersite
                fighter = (Fighter) getRequest().getAttribute( WEB_ADMIN_FIGHTER );
                if ( fighter != null && fighter.getTeam() != null )
                {
                    team = fighter.getTeam();
                    if ( fighter.getTeam().getCountry() != null )
                    {
                        country = fighter.getTeam().getCountry();
                        setRegions( regionManager.getRegionsByCountry( fighter.getTeam().getCountry().getId() ) );
                    }
                    if ( fighter.getTeam().getRegion() != null )
                    {
                        region = fighter.getTeam().getRegion();
                        teams =
                            teamManager.getTeamsByCountryAndRegion( fighter.getTeam().getCountry().getId(),
                                                                    fighter.getTeam().getRegion().getId() );
                    }
                }

            }
            else
            {
                fighter = new Fighter();
            }
        }

        if ( fighter != null && fighter.getTeam() == null )
        {
            fighter.setTeam( team );
        }

        return fighter;
    }

    /**
     * @param fighter The fighter to set.
     */
    public void setFighter( Fighter fighter )
    {
        this.fighter = fighter;
    }

    /**
     * @param fighterManager The fighterManager to set.
     */
    public void setFighterManager( FighterManager fighterManager )
    {
        this.fighterManager = fighterManager;
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

        // List<Age> ages = ageManager.getAllAges();
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

        // List<Country> countries = countryManager.getAllCountries();

        for ( Country country : getCountries() )
        {
            ret.add( new SelectItem( country, country.getDescription(), country.getId().toString() ) );
        }
        return ret;
    }

    /*
     * public List getTeamListALL() { List ret = new ArrayList(); Team newTeam = new Team();
     * newTeam.setId(TypeUtil.LONG_OBJECT_EMPTY); ret.add(new SelectItem(newTeam,EMPTY_ITEM_TEXT,TypeUtil.STRING_0));
     * List<Team> teams = teamManager.getAllTeams(); for(Team team: teams) { ret.add(new
     * SelectItem(team,TypeUtil.toString(team.getTeamName()),TypeUtil.toString(team.getId()))); } return ret; }
     */

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

            if ( getRequest().getParameter( "main:adminFighterAction:country" ) != null )
            {
                setRegions( regionManager.getRegionsByCountry( Long.valueOf( getRequest().getParameter(
                                                                                                        "main:adminFighterAction:country" ) ) ) );
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
            CodestableMain.getInstance().getCodestableCodeByTypeAndLevel( TYPE_SEX, getRequest().getLocale(), 1 );
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

    public List getReadyList()
    {
        List ret = new ArrayList();

        List<Codestable> list =
            CodestableMain.getInstance().getCodestableCodeByType( TYPE_FIGHTER_STATUS, getRequest().getLocale() );
        for ( Codestable entry : list )
        {
            ret.add( new SelectItem( entry.getId(), entry.getValue() ) );
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
