package Server;

import java.util.ArrayList;

public class Team
{
	String teamname;
	ArrayList<Athlete> athletes;
	String gender;

	Team(ArrayList<Athlete> ath, String name, String gen)
	{
		teamname = name;
		athletes = ath;
		gender = gen;
	}

	public ArrayList<Athlete> getTeam()
	{
		return this.athletes;

	}

	public void addAthlete(Athlete a)
	{
		this.athletes.add(a);
	}

	public Athlete getAthlete(Athlete a)
	{
		if (this.athletes.contains(a))
		{
			return this.athletes.get(athletes.indexOf(a));
		} else
		{
			return null;
		}

	}

	public String getName()
	{
		return this.teamname;

	}

	public String getGender()
	{
		return this.gender;

	}

}