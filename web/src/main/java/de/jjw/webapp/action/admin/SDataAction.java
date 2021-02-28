package de.jjw.webapp.action.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import de.jjw.model.Team;
import de.jjw.model.admin.Age;
import de.jjw.model.admin.ConfigJJW;
import de.jjw.model.admin.SDataInputModel;
import de.jjw.model.fighting.Fighter;
import de.jjw.model.generalhelper.CodestableMain;
import de.jjw.service.TeamManager;
import de.jjw.service.admin.ConfigManager;
import de.jjw.service.admin.SDataInputManager;
import de.jjw.service.exception.IllegalTeamException;
import de.jjw.service.fighting.FighterManager;
import de.jjw.service.mapper.admin.SDataInputModelMapper;
import de.jjw.service.mapper.fighting.FighterMapper;
import de.jjw.service.modelWeb.DuoTeamWeb;
import de.jjw.service.modelWeb.FighterWeb;
import de.jjw.service.modelWeb.NewaFighterWeb;
import de.jjw.util.TypeUtil;
import de.jjw.webapp.IGlobalWebConstants;
import de.jjw.webapp.action.BasePage;
import de.jjw.webapp.action.validation.ErrorElement;
import de.jjw.webapp.constants.admin.IRegionConstants;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

public class SDataAction  extends BasePage
implements IGlobalWebConstants, IRegionConstants
{
	SDataInputManager sDataInputManager;
	private List<SDataInputModel> sDataInputItems;
	private TeamManager teamManager;
	private ConfigManager configManager;
	private ConfigJJW config;
	private FighterManager fighterManager;
	
	public String uploadSData() // uploadJSONFile()
	{
		Vector<ErrorElement> errors = new Vector<ErrorElement>();
		String line;
		this.sDataInputItems = new ArrayList<SDataInputModel>();
		

		try {
			config = configManager.getConfig();

			List<Team> teamList = teamManager.getAllTeams();
			JsonElement element = readJsonInput();

			JsonArray sDataElements = element.getAsJsonArray();
			for (JsonElement item : sDataElements) {
				//sDataInputItems.add(extractSDataInput(item.getAsJsonObject(), ageList, teamList));

				SDataInputModel fighterDB;
		            for ( SDataInputModel sItem : sDataInputItems )
		            {
		                fighterDB = new SDataInputModel();
		                setTechnicalDBFieldsForCreate( sItem ); // for mapping

		                SDataInputModelMapper.mapTeamToDB( sItem, fighterDB);
		                setTechnicalDBFieldsForCreate( fighterDB );
		                //fighterManager.saveFighter( sItem );
		                //sItem.setCheck( true );
		            }
				
			}
		} catch (Exception e) {
			log.error("uploadSData", e);
			errors.add(new ErrorElement("ADMIN_SDATA_UPLOAD_ERROR"));
			setErrorMessageVector(errors);
			return null;
		}
		return null;
	}
	
	
	private SDataInputModel extractSDataInput( JsonObject itemJson, List<Age> ageList, List<Team> teamList )throws IllegalTeamException
	{
		SDataInputModel sDataItem = new SDataInputModel();

		sDataItem.setId(Long.valueOf(itemJson.get("Athlete ID").getAsString()));
		sDataItem.setCountryCode(itemJson.get("Club County-Code").getAsString());
		sDataItem.setName(itemJson.get("Last Name").getAsString());
		sDataItem.setFirstname(itemJson.get("First Name").getAsString());
		
		String birthdateInverse = itemJson.get("Date Of Birth Complete").getAsString();
		String[] birthDateArrayInverse = birthdateInverse.trim().split("-");
		sDataItem.setDateOfBirth(
				birthDateArrayInverse[2] + ":" + birthDateArrayInverse[1] + ":" + birthDateArrayInverse[0]);
		sDataItem.setTeam(itemJson.get("Clubname").getAsString());
		sDataItem.setTeamId(Long.valueOf(itemJson.get("Club ID").getAsString()));
		sDataItem.setDuoTeamName(itemJson.get("Team Name").getAsString());

		String category= itemJson.get("Category").getAsString();
		if (category.lastIndexOf("Fighting System")>-1)
		{
			sDataItem.setDiscepline("F");
			category = category.substring(category.length() - 6, category.length() - 3);
			if (category.lastIndexOf("-") == 0)
				sDataItem.setWeight(Double.valueOf(category.substring(1, 2)));
			if (category.lastIndexOf("+") == 0)
				sDataItem.setWeight(1.0 + Double.valueOf(category.substring(1, 2)));
		}
		
		if (category.lastIndexOf("Duo Classic")>-1)
		{
			sDataItem.setDiscepline("D");
			category = category.substring(category.length() - 6, category.length() - 3);
			if (category.lastIndexOf("-") == 0)
				sDataItem.setWeight(Double.valueOf(category.substring(1, 2)));
			if (category.lastIndexOf("+") == 0)
				sDataItem.setWeight(1.0 + Double.valueOf(category.substring(1, 2)));
		}
		
		
		if (category.lastIndexOf("Ne-Waza")>-1)
		{
			sDataItem.setDiscepline("N");
			category = category.substring(category.length() - 6, category.length() - 3);
			if (category.lastIndexOf("-") == 0)
				sDataItem.setWeight(Double.valueOf(category.substring(1, 2)));
			if (category.lastIndexOf("+") == 0)
				sDataItem.setWeight(1.0 + Double.valueOf(category.substring(1, 2)));
		}
		

		String sex = itemJson.get("Genus").getAsString();
		if ("m".equals(sex) || "M".equals(sex))
			sDataItem.setSex("1");
		if ("f".equals(sex) || "F".equals(sex))
			sDataItem.setSex("2");

		return sDataItem;
	}
	
	/**
     * @return
     * @throws IOException
     */
    private JsonElement readJsonInput()
        throws IOException
    {
      /*  String line;
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
        }*/
    	return null;
    }

}
