package de.jjw.model.admin;

import java.io.Serializable;

import de.jjw.model.BaseObject;
import de.jjw.model.Team;

public class SDataInputModel    extends BaseObject
implements Serializable {

	protected Long id = null;
	
    protected String name;

    protected String firstname;

    protected String duoTeamName;
    
    protected String team;

    protected String sex = "";

    protected double weight = 0;
    
    protected String dateOfBirth;
    
    protected String discepline;
    
    protected String countryCode;
    
    protected Long teamId = null;
    
    
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getDuoTeamName() {
		return duoTeamName;
	}

	public void setDuoTeamName(String duoTeamName) {
		this.duoTeamName = duoTeamName;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getDiscepline() {
		return discepline;
	}

	public void setDiscepline(String discepline) {
		this.discepline = discepline;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
