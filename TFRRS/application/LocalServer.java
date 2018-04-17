package application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import Server.Athlete;

public class LocalServer implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8507180160348007069L;
	Map<String, ArrayList<Athlete>> maleTeamList;
	Map<String, ArrayList<Athlete>> femaleTeamList;
	
	public LocalServer() {}

	/**
	 * @return the maleTeamList
	 */
	public Map<String, ArrayList<Athlete>> getMaleTeamList()
	{
		return maleTeamList;
	}

	/**
	 * @param maleTeamList the maleTeamList to set
	 */
	public void setMaleTeamList(Map<String, ArrayList<Athlete>> maleTeamList)
	{
		this.maleTeamList = maleTeamList;
	}
	
	// add all
	public void addToMaleTeamList(Map<String, ArrayList<Athlete>> maleTeamList)
	{
		this.maleTeamList.putAll(maleTeamList);;
	}

	/**
	 * @return the femaleTeamList
	 */
	public Map<String, ArrayList<Athlete>> getFemaleTeamList()
	{
		return femaleTeamList;
	}

	/**
	 * @param femaleTeamList the femaleTeamList to set
	 */
	public void setFemaleTeamList(Map<String, ArrayList<Athlete>> femaleTeamList)
	{
		this.femaleTeamList = femaleTeamList;
	}
	
	/// add all
	public void addToFemaleTeamList(Map<String, ArrayList<Athlete>> femaleTeamList)
	{
		this.femaleTeamList.putAll(femaleTeamList);;
	}
	
	

	
	

}
