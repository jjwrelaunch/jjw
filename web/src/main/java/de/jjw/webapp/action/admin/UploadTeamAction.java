/*
 * This file is part of Ju Jutsu Web (JJW) - a J2EE Ju-Jutsu competition management software.
 * Copyright (c) 2010.
 * Joerg Boehme / Tino Schlegel
 *
 * File    : UploadTeamAction.java
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import javax.faces.component.UIData;

import org.apache.myfaces.custom.fileupload.UploadedFile;

import de.jjw.dao.generalhelper.OptimisticLockingException;
import de.jjw.model.Preview;
import de.jjw.model.Team;
import de.jjw.model.admin.Age;
import de.jjw.model.admin.ConfigJJW;
import de.jjw.model.duo.DuoTeam;
import de.jjw.model.fighting.Fighter;
import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.model.newa.NewaFighter;
import de.jjw.service.TeamManager;
import de.jjw.service.admin.AgeManager;
import de.jjw.service.admin.ConfigManager;
import de.jjw.service.duo.DuoTeamManager;
import de.jjw.service.exception.DuoTeamIllegalReadyException;
import de.jjw.service.exception.FighterIllegalReadyException;
import de.jjw.service.exception.IllegalTeamException;
import de.jjw.service.exception.JJWPoolBlockedException;
import de.jjw.service.exception.NewaFighterIllegalReadyException;
import de.jjw.service.exception.TeamInUseException;
import de.jjw.service.fighting.FighterManager;
import de.jjw.service.mapper.duo.DuoTeamMapper;
import de.jjw.service.mapper.fighting.FighterMapper;
import de.jjw.service.mapper.newa.NewaFighterMapper;
import de.jjw.service.modelWeb.DuoTeamWeb;
import de.jjw.service.modelWeb.FighterWeb;
import de.jjw.service.modelWeb.NewaFighterWeb;
import de.jjw.service.newa.NewaFighterManager;
import de.jjw.service.util.IGlobalProjectConstants;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.action.validation.duo.DuoTeamValidator;
import de.jjw.webapp.action.validation.fighting.FighterValidator;
import de.jjw.webapp.action.validation.newa.NewaFighterValidator;
import de.jjw.webapp.constants.ITeamConstants;

import org.springframework.dao.DataIntegrityViolationException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

public class UploadTeamAction
    extends BasePage
    implements Serializable, IGlobalWebConstants, ITeamConstants
{

    private static String ALL_TEAMS = "showAllTeams";

    private TeamManager teamManager;

    private Team team;

    private long teamId;

    private AgeManager ageManager;

    private FighterManager fighterManager;

    private DuoTeamManager duoTeamManager;

    private NewaFighterManager newaFighterManager;
    
    private UploadedFile registerFile;

    private boolean delete;
    
    private List<FighterWeb> fighterList;

    private List<DuoTeamWeb> duoteamList;

    private List<NewaFighterWeb> newaFighterList;

    protected UIData dataTable2; // duo

    protected UIData dataTable3; // newa
    
    ConfigManager configManager = null;
    
    private ConfigJJW config;


    public String saveTeam()
    {
        String ret = ALL_TEAMS;
        Vector<ErrorElement> errors = new Vector<ErrorElement>();
       
        try
        {
            // save participan and check
            Fighter fighterDB;
            for ( FighterWeb item : fighterList )
            {
                fighterDB = new Fighter();
                setTechnicalDBFieldsForCreate( item ); // for mapping

                FighterMapper.mapFighterToDB( item, fighterDB, true );
                setTechnicalDBFieldsForCreate( fighterDB );
                fighterManager.saveFighter( fighterDB );
                item.setCheck( true );
            }

            DuoTeam teamDB;
            for ( DuoTeamWeb item : duoteamList )
            {
                teamDB = new DuoTeam();
                setTechnicalDBFieldsForCreate( item );// for mapping

                DuoTeamMapper.mapDuoTeamToDB( item, teamDB, true );
                setTechnicalDBFieldsForCreate( teamDB );
                duoTeamManager.saveDuoTeam( teamDB );
                item.setCheck( true );
            }

            NewaFighter newaFighterDB;
            for ( NewaFighterWeb item : newaFighterList )
            {
                newaFighterDB = new NewaFighter();
                setTechnicalDBFieldsForCreate( item );// for mapping

                NewaFighterMapper.mapFighterToDB( item, newaFighterDB, true );
                setTechnicalDBFieldsForCreate( newaFighterDB );
                newaFighterManager.saveFighter( newaFighterDB );
                item.setCheck( true );
            }

        }
        catch ( DataIntegrityViolationException e )
        {
            log.error( "saveTeam", e );
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR, GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
            ret = null;
        }
        catch ( OptimisticLockingException e )
        {
            log.error( "saveTeam", e );
            errors.add( new ErrorElement( GLO_OPTIMISTIC_LOCKING ) );
            setErrorMessageVector( errors );
            ret = null;
        }
        catch ( JJWPoolBlockedException e )
        {
            log.error( "saveTeam", e );
            errors.add( new ErrorElement( ADMIN_FIGHTER_BLOCKED_POOL ) );
            setErrorMessageVector( errors );
            ret = null;
        }
        catch ( FighterIllegalReadyException e )
        {
            log.error( "saveTeam", e );
            errors.add( new ErrorElement( ADMIN_FIGHTER_ILLEGAL_REGISTRATION ) );
            setErrorMessageVector( errors );
            ret = null;
        }
        catch ( DuoTeamIllegalReadyException e )
        {
            log.error( "saveTeam", e );
            errors.add( new ErrorElement( ADMIN_DUOTEAM_ILLEGAL_REGISTRATION ) );
            setErrorMessageVector( errors );
            ret = null;
        }
        catch ( NewaFighterIllegalReadyException e )
        {
            log.error( "saveTeam", e );
            errors.add( new ErrorElement( ADMIN_NEWA_FIGHTER_ILLEGAL_REGISTRATION ) );
            setErrorMessageVector( errors );
            ret = null;
        }
        catch ( Exception e )
        {
            log.error( "saveTeam", e );
            errors.add( new ErrorElement( GEN_GERNERAL_ERROR ) );
            setErrorMessageVector( errors );
            ret = null;
        }
        // setTeam( new Team() );
        dataTable = null;
        dataTable2 = null;
        dataTable3 = null;
        return ret;
    }


    public String gotoAllTeams()
    {
        setTeam( new Team() );
        return ALL_TEAMS;
    }


    /**
     * @return Returns the team.
     */
    public Team getTeam()
    {
        if ( team == null )
        {
            team = (Team) getRequest().getAttribute( WEB_ADMIN_TEAM_UPLOAD );
            if ( team == null && TypeUtil.LONG_DEFAULT < teamId )
                try
                {
                    team = teamManager.getTeam( teamId );
                }
                catch ( Exception e )
                {
                    ;
                }
        }

        return team;
    }

    /**
     * @param team The team to set.
     */
    public void setTeam( Team team )
    {
        this.team = team;
    }

    /**
     * @return the teamId
     */
    public long getTeamId()
    {
        if ( team != null )
            teamId = team.getId();
        return teamId;
    }

    /**
     * @param teamId the teamId to set
     */
    public void setTeamId( long teamId )
    {
        this.teamId = teamId;
    }

    public boolean isDelete()
    {
        return delete;
    }

    public void setDelete( boolean delete )
    {
        this.delete = delete;
    }
    
    public UploadedFile getRegisterFile()
    {
        return registerFile;
    }

    public void setRegisterFile( UploadedFile registerFile )
    {
        this.registerFile = registerFile;
    }
    
    public String uploadTeam() // uploadJSONFile()
    {
        Vector<ErrorElement> errors = new Vector<ErrorElement>();
        String line;
        this.fighterList = new ArrayList<FighterWeb>();
        this.duoteamList = new ArrayList<DuoTeamWeb>();
        this.newaFighterList = new ArrayList<NewaFighterWeb>();
        
        try
        {
        	config = configManager.getConfig();
        	
            List<Age> ageList = ageManager.getAllAges();
            List<Team> teamList = teamManager.getAllTeams();
            JsonElement element = readJsonInput();

            if ( element.getAsJsonObject().get( "fighter" ) != null )
            {
                JsonArray fighterElements = element.getAsJsonObject().get( "fighter" ).getAsJsonArray();
                for ( JsonElement item : fighterElements )
                {
                    fighterList.add( extractFighter( item.getAsJsonObject(), ageList, teamList ) );
                }
            }
            if ( element.getAsJsonObject().get( "duo" ) != null )
            {
                JsonArray duoElements = element.getAsJsonObject().get( "duo" ).getAsJsonArray();
                for ( JsonElement item : duoElements )
                {
                    duoteamList.add( extractDuoteam( item.getAsJsonObject(), ageList, teamList ) );
                }
            }

            if ( element.getAsJsonObject().get( "newaza" ) != null )
            {
                JsonArray newazaElements = element.getAsJsonObject().get( "newaza" ).getAsJsonArray();
                for ( JsonElement item : newazaElements )
                {
                    newaFighterList.add( extractNewaFighter( item.getAsJsonObject(), ageList, teamList ) );
                }
            }

            if ( FighterValidator.isDoubleNameFighter( fighterList )
                || DuoTeamValidator.isDoubleNameDuoTeam( duoteamList )
                || NewaFighterValidator.isDoubleNameNewaFighter( newaFighterList ) )
            {
                errors.add( new ErrorElement( ADMIN_TEAM_UPLOAD_DOUBLE_PARTICIPANT ) );
                setErrorMessageVector( errors );
                return null;
            }

            if ( !FighterValidator.isRequiredFieldsValid( fighterList, errors )
                && !DuoTeamValidator.isRequiredFieldsValid( duoteamList, errors )
                && !NewaFighterValidator.isRequiredFieldsValid( newaFighterList, errors ) )
            {
                setErrorMessageVector( errors );
                return null;
            }
            
            for ( Fighter item : fighterList )
            {
                if ( fighterManager.existFighter( item ) )
                {
                    errors.add( new ErrorElement( ADMIN_FIGHTER_ALREADY_EXIST ) );
                    setErrorMessageVector( errors );
                    return null;
                }
            }
            
            for ( DuoTeam item : duoteamList )
            {
                if ( duoTeamManager.existDuoTeam( item ) )
                {
                    errors.add( new ErrorElement( ADMIN_DUOTEAM_ALREADY_EXIST ) );
                    setErrorMessageVector( errors );
                    return null;
                }
            }
            
            for ( NewaFighter item : newaFighterList )
            {
                if ( newaFighterManager.existFighter( item ) )
                {
                    errors.add( new ErrorElement( ADMIN_NEWA_FIGHTER_ALREADY_EXIST) );
                    setErrorMessageVector( errors );
                    return null;
                }
            }
            saveTeam();
            return null;
        }
        catch ( IllegalTeamException e )
        {
            log.error( "uploadTeam", e );
            errors.add( new ErrorElement( ADMIN_TEAM_UPLOAD_ERROR ) );
            setErrorMessageVector( errors );
            return null;
        } 
        catch ( Exception e )
        {
            log.error( "uploadTeam", e );
            errors.add( new ErrorElement( ADMIN_TEAM_UPLOAD_ERROR ) );
            setErrorMessageVector( errors );
            return null;
        }

    }

    /**
     * @return
     * @throws IOException
     */
    private JsonElement readJsonInput()
        throws IOException
    {
        String line;
        BufferedReader registerInput = new BufferedReader( new InputStreamReader( registerFile.getInputStream(), "UTF-8") );
        try
        {
        StringBuffer sb = new StringBuffer( (int) registerFile.getSize() );
        line = registerInput.readLine();
        sb.append( line );
        while ( line != null )
        {
            line = registerInput.readLine();
            if ( line != null )
                sb.append( line.trim() );
        }
        registerInput.close();

        String json = sb.toString();
        JsonParser parser = new JsonParser();

        JsonElement element = parser.parse( json );
        return element;
        }
        catch ( IOException e )
        {
            log.error( "\n\nJSON file has a defect \n\n" );
            throw e;
        }
    }

    private FighterWeb extractFighter( JsonObject item, List<Age> ageList, List<Team> teamList )throws IllegalTeamException
    {
        Team teamForFighter;
        if ( TypeUtil.isEmptyOrDefault( teamId ) )
            teamForFighter = getTeamForUpload( item.get( "teamId" ).getAsInt(), teamList,item.get( "team" ).getAsString(),false );
        else
            teamForFighter = getTeamForUpload( (int) teamId, teamList,item.get( "team" ).getAsString(),true  );

        FighterWeb newFighter = new FighterWeb();
        
        String birthdate= item.get( "yearOfBirth" ).getAsString();
        String[]  birthDateArry = birthdate.trim().replace(".", ":").split(":");

        newFighter.setName( item.get( "name" ).getAsString() );
        newFighter.setFirstname( item.get( "firstname" ).getAsString() );
        newFighter.setAge( getAge( birthDateArry, ageList ) );
        newFighter.setAgeId( newFighter.getAge().getId() );
        newFighter.setTeamId( teamForFighter.getId() );
        newFighter.setTeam( teamForFighter );
        newFighter.setSex( item.get( "gender" ).getAsString() );
        newFighter.setSexWeb( CodestableMain.getInstance().getCodestableCodeValueByType( TYPE_SEX,
                                                                                         item.get( "gender" ).getAsString(),
                                                                                         Locale.GERMAN ) );
        newFighter.setWeight( item.get( "weight" ).getAsDouble() );
                
        newFighter.setYearOfBirth( Integer.valueOf(birthDateArry[2]) );
        newFighter.setMonthOfBirth( Integer.valueOf(birthDateArry[1]) );
        newFighter.setDayOfBirth( Integer.valueOf(birthDateArry[0]) );            

        return newFighter;
    }

    private DuoTeamWeb extractDuoteam( JsonObject item, List<Age> ageList, List<Team> teamList )throws IllegalTeamException
    {
        Team teamForDuo;
        if ( TypeUtil.isEmptyOrDefault( teamId ) )
            teamForDuo = getTeamForUpload( item.get( "teamId" ).getAsInt(), teamList,item.get( "team" ).getAsString() ,false );
        else
            teamForDuo = getTeamForUpload( (int) teamId, teamList,item.get( "team" ).getAsString(),true  );

        DuoTeamWeb newDuoteam = new DuoTeamWeb();
        
        String birthdate= item.get( "yearOfBirth" ).getAsString();
        String[]  birthDateArry = birthdate.trim().replace(".", ":").split(":");
        String birthdate2= item.get( "yearOfBirth2" ).getAsString();
        String[]  birthDateArry2 = birthdate2.trim().replace(".", ":").split(":");

        newDuoteam.setName( item.get( "name" ).getAsString() );
        newDuoteam.setFirstname( item.get( "firstname" ).getAsString() );
        newDuoteam.setName2( item.get( "name2" ).getAsString() );
        newDuoteam.setFirstname2( item.get( "firstname2" ).getAsString() );
        newDuoteam.setAge( getAge( birthDateArry, birthDateArry2,ageList ) );
        newDuoteam.setAgeId( newDuoteam.getAge().getId() );
        newDuoteam.setTeamId( teamForDuo.getId() );
        newDuoteam.setTeam( teamForDuo );
        newDuoteam.setSex( item.get( "gender" ).getAsString() );
        newDuoteam.setSexWeb( CodestableMain.getInstance().getCodestableCodeValueByType( TYPE_SEX,
                                                                                         item.get( "gender" ).getAsString(),
                                                                                         Locale.GERMAN ) );                
        newDuoteam.setYearOfBirth( Integer.valueOf(birthDateArry[2]) );
        newDuoteam.setMonthOfBirth( Integer.valueOf(birthDateArry[1]) );
        newDuoteam.setDayOfBirth( Integer.valueOf(birthDateArry[0]) );            
        
        newDuoteam.setYearOfBirth2( Integer.valueOf(birthDateArry2[2]) );
        newDuoteam.setMonthOfBirth2( Integer.valueOf(birthDateArry2[1]) );
        newDuoteam.setDayOfBirth2( Integer.valueOf(birthDateArry2[0]) );
        
        return newDuoteam;
    }

    private NewaFighterWeb extractNewaFighter( JsonObject item, List<Age> ageList, List<Team> teamList ) throws IllegalTeamException
    {
        Team teamForFighter;
        if ( TypeUtil.isEmptyOrDefault( teamId ) )
            teamForFighter = getTeamForUpload( item.get( "teamId" ).getAsInt(), teamList,item.get( "team" ).getAsString(),false  );
        else
            teamForFighter = getTeamForUpload( (int) teamId, teamList,item.get( "team" ).getAsString(),true  );

        NewaFighterWeb newFighter = new NewaFighterWeb();
        
        String birthdate= item.get( "yearOfBirth" ).getAsString();
        String[]  birthDateArry = birthdate.trim().replace(".", ":").split(":");
        
        newFighter.setName( item.get( "name" ).getAsString() );
        newFighter.setFirstname( item.get( "firstname" ).getAsString() );
        newFighter.setAge( getAge(birthDateArry, ageList ) );
        newFighter.setAgeId( newFighter.getAge().getId() );
        newFighter.setTeamId( teamForFighter.getId() );
        newFighter.setTeam( teamForFighter );
        newFighter.setSex( item.get( "gender" ).getAsString() );
        newFighter.setSexWeb( CodestableMain.getInstance().getCodestableCodeValueByType( TYPE_SEX,
                                                                                         item.get( "gender" ).getAsString(),
                                                                                         Locale.GERMAN ) );
        
        newFighter.setWeight( item.get( "weight" ).getAsDouble() );
        newFighter.setYearOfBirth( Integer.valueOf(birthDateArry[2]) );
        newFighter.setMonthOfBirth( Integer.valueOf(birthDateArry[1]) );
        newFighter.setDayOfBirth( Integer.valueOf(birthDateArry[0]) );            
        return newFighter;
    }


        
    private boolean isCorrectVersion( String version )
    {
        return IGlobalProjectConstants.UPLOAD_VERSION.equals( version );
    }

    private Age getAge( String[]  birthDateArry, List<Age> ageList)
    {
    	int dayBirth =Integer.valueOf(birthDateArry[0]);
    	int monthBirth =Integer.valueOf(birthDateArry[1]);
    	
    	Calendar rightNow = Calendar.getInstance();
        int actualAge = rightNow.get( Calendar.YEAR ) - Integer.valueOf( birthDateArry[2].trim() );
        

        //test if we are before or after deadline
        String [] deadline = config.getDeadline().split("/");
        int dayDeadline =Integer.valueOf(deadline[0]);
    	int monthDeadline =Integer.valueOf(deadline[1]);            	
        
        if(monthBirth < monthDeadline)actualAge++;
        else {
        	if(monthBirth == monthDeadline && dayBirth<dayDeadline)actualAge++;
        }
        
        for(Age item: ageList){

            if ( item.getAgeLimit() >= actualAge && item.getStartAge() <= actualAge )
                return item;
        }
        return null;
    }

    private Age getAge( String[]  birthDateArry , String[]  birthDateArry2, List<Age> ageList )
    {
 	
    	int dayBirth =Integer.valueOf(birthDateArry[0]);
    	int monthBirth =Integer.valueOf(birthDateArry[1]);
    	int yearBirth =Integer.valueOf(birthDateArry[2]);    	
    	
    	int dayBirth2 =Integer.valueOf(birthDateArry2[0]);
    	int monthBirth2 =Integer.valueOf(birthDateArry2[1]);
    	int yearBirth2 =Integer.valueOf(birthDateArry2[2]);
    	
    	//test of year
    	if ( yearBirth > yearBirth2 ) {
    		yearBirth = yearBirth2;
    		monthBirth=monthBirth2;
    		dayBirth=dayBirth2;
    	}
    	else {//test of month
    		if ( yearBirth == yearBirth2 && monthBirth> monthBirth2) {
    			yearBirth = yearBirth2;
        		monthBirth=monthBirth2;
        		dayBirth=dayBirth2;
        	}
    		else {//test of day
        		if (yearBirth == yearBirth2 && monthBirth == monthBirth2 && dayBirth> dayBirth2) {
        			yearBirth = yearBirth2;
            		monthBirth=monthBirth2;
            		dayBirth=dayBirth2;
            	}    			
    		}    		
    	}
    	
        Calendar rightNow = Calendar.getInstance();
        int actualAge = rightNow.get( Calendar.YEAR ) - yearBirth;
        

        //test if we are before or after deadline
        String [] deadline = config.getDeadline().split("/");
        int dayDeadline =Integer.valueOf(deadline[0]);
    	int monthDeadline =Integer.valueOf(deadline[1]);            	
        
        if(monthBirth < monthDeadline)actualAge++;
        else {
        	if(monthBirth == monthDeadline && dayBirth<dayDeadline)actualAge++;
        }

        for ( Age item : ageList )
        {

            if ( item.getAgeLimit() >= actualAge && item.getStartAge() <= actualAge )
                return item;
        }
        return null;
    }

    private Team getTeamForUpload( int teamId, List<Team> teamList, String teamName, boolean teamFromDB  ) throws IllegalTeamException
    {
        for ( Team item : teamList )
        {
            if ( item.getId() == teamId ){
                if(teamFromDB) return item;
                
                if(!item.getTeamName().equals( teamName ))
                    throw new IllegalTeamException("wrong team: "+ teamName + " to teamId: "+teamId);
                return item;
            }
        }
        return null;
    }

    public List<FighterWeb> getFighterList()
    {
        return fighterList;
    }

    public List<DuoTeamWeb> getDuoteamList()
    {
        return duoteamList;
    }

    public List<NewaFighterWeb> getNewaFighterList()
    {
        return newaFighterList;
    }


    /**
     * @param fighterList the fighterList to set
     */
    public void setFighterList( List<FighterWeb> fighterList )
    {
        this.fighterList = fighterList;
    }

    /**
     * @param duoteamList the duoteamList to set
     */
    public void setDuoteamList( List<DuoTeamWeb> duoteamList )
    {
        this.duoteamList = duoteamList;
    }

    /**
     * @param newaFighterList the newaFighterList to set
     */
    public void setNewaFighterList( List<NewaFighterWeb> newaFighterList )
    {
        this.newaFighterList = newaFighterList;
    }

    /**
     * @return the dataTable2
     */
    public UIData getDataTable2()
    {
        return dataTable2;
    }

    /**
     * @param dataTable2 the dataTable2 to set
     */
    public void setDataTable2( UIData dataTable2 )
    {
        this.dataTable2 = dataTable2;
    }

    /**
     * @return the dataTable3
     */
    public UIData getDataTable3()
    {
        return dataTable3;
    }

    /**
     * @param dataTable3 the dataTable3 to set
     */
    public void setDataTable3( UIData dataTable3 )
    {
        this.dataTable3 = dataTable3;
    }

    /**
     * @param teamManager The teamManager to set.
     */
    public void setTeamManager( TeamManager teamManager )
    {
        this.teamManager = teamManager;
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

    /**
     * @param fighterManager the fighterManager to set
     */
    public void setFighterManager( FighterManager fighterManager )
    {
        this.fighterManager = fighterManager;
    }

    /**
     * @param duoTeamManager the duoTeamManager to set
     */
    public void setDuoTeamManager( DuoTeamManager duoTeamManager )
    {
        this.duoTeamManager = duoTeamManager;
    }

    /**
     * @param newaFighterManager the newaFighterManager to set
     */
    public void setNewaFighterManager( NewaFighterManager newaFighterManager )
    {
        this.newaFighterManager = newaFighterManager;
    }
    
    public void setConfigManager( ConfigManager configManager )
    {
        this.configManager = configManager;
    }
}