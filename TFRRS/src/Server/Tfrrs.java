package Server;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

public class Tfrrs implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5470207468423370401L;
	
	/// map from school name to school url: females
	public Map<String, String> femaleTeams;
	/// map from school name to school url : males
	public Map<String, String> maleTeams;

	public Tfrrs()
	{
	}

	public Tfrrs(Map<String, String> femaleTeams, Map<String, String> maleTeams) throws IOException
	{
		this.femaleTeams = femaleTeams;
		this.maleTeams = maleTeams;
	}

	public Map<String, String> getFemaleTeams()
	{
		return femaleTeams;
	}

	public void setFemaleTeams(Map<String, String> femaleTeams)
	{
		this.femaleTeams = femaleTeams;
	}

	public Map<String, String> getMaleTeams()
	{
		return maleTeams;
	}

	public void setMaleTeams(Map<String, String> maleTeams)
	{
		this.maleTeams = maleTeams;
	}

}
