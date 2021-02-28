package de.jjw.service.mapper.admin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import de.jjw.model.admin.SDataInputModel;

public class SDataInputModelMapper {

	public static List<SDataInputModel> mapTeamListFromDB(List<SDataInputModel> input) {
		if (input == null) {
			return null;
		}

		List<SDataInputModel> ret = new ArrayList<SDataInputModel>(input.size() + 1);

		for (SDataInputModel team : input) {
			ret.add(mapTeamFromDB(team));
		}

		return ret;
	}

	/**
	 * maps a Team from DB and get a new Team object
	 *
	 * @param input
	 * @return
	 */
	public static SDataInputModel mapTeamFromDB(SDataInputModel input) {
		SDataInputModel ret = new SDataInputModel();

		ret.setCountryCode(new String(input.getCountryCode()));
		ret.setDateOfBirth(new String(input.getDateOfBirth()));
		ret.setDiscepline(new String(input.getDiscepline()));
		ret.setDuoTeamName(new String(input.getDuoTeamName()));
		ret.setFirstname(new String(input.getFirstname()));
		ret.setId(Long.valueOf(input.getId()));
		ret.setName(new String(input.getName()));
		ret.setSex(new String(input.getSex()));
		ret.setTeam(new String(input.getTeam()));
		ret.setTeamId(input.getTeamId());
		ret.setWeight(input.getWeight());

		ret.setCreateDate(new Timestamp(input.getCreateDate().getTime()));
		ret.setCreateId(Long.valueOf(input.getCreateId()));
		ret.setId(Long.valueOf(input.getId()));
		ret.setModificationDate(new Timestamp(input.getModificationDate().getTime()));
		ret.setModificationId(Long.valueOf(input.getModificationId()));

		return ret;
	}

	/*
	 * Function maps and set a new ModificationDate
	 *
	 */

	public static void mapTeamToDB(SDataInputModel input, SDataInputModel inputDB) {

		
		if (input != null && inputDB == null) {
			inputDB = new SDataInputModel();
		}
		
		if (inputDB.getId() == null)
			inputDB.setId(input.getId());

		inputDB.setCountryCode(new String(input.getCountryCode()));
		inputDB.setDateOfBirth(new String(input.getDateOfBirth()));
		inputDB.setDiscepline(new String(input.getDiscepline()));
		inputDB.setDuoTeamName(new String(input.getDuoTeamName()));
		inputDB.setFirstname(new String(input.getFirstname()));
		inputDB.setId(Long.valueOf(input.getId()));
		inputDB.setName(new String(input.getName()));
		inputDB.setSex(new String(input.getSex()));
		inputDB.setTeam(new String(input.getTeam()));
		inputDB.setTeamId(Long.valueOf(input.getTeamId()));
		inputDB.setWeight(input.getWeight());

		inputDB.setCreateDate(new Timestamp(input.getCreateDate().getTime()));
		inputDB.setCreateId(Long.valueOf(input.getCreateId()));
		inputDB.setModificationDate(new Timestamp(System.currentTimeMillis()));
		inputDB.setModificationId(Long.valueOf(input.getModificationId()));

	}

}
