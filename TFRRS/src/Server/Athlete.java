package Server;
import java.util.ArrayList;

public class Athlete
{
    String name;
    String sex;
    String year;
    String college;
    String url;
    ArrayList<Result> events; 
  

    public Athlete(String n, String gender, String grade, String uni, String address,  ArrayList<Result> e)
    {
	name = n;
	sex = gender;
	year = grade;
	college = uni;
	url = address;
	events=e;
	
    }


	/**
	 * @return the url
	 */
	public String getUrl()
	{
		return url;
	}


	/**
	 * @param url the url to set
	 */
	public void setUrl(String url)
	{
		this.url = url;
	}


	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}


	/**
	 * @return the sex
	 */
	public String getSex()
	{
		return sex;
	}


	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex)
	{
		this.sex = sex;
	}


	/**
	 * @return the year
	 */
	public String getYear()
	{
		return year;
	}


	/**
	 * @param year the year to set
	 */
	public void setYear(String year)
	{
		this.year = year;
	}


	/**
	 * @return the college
	 */
	public String getCollege()
	{
		return college;
	}


	/**
	 * @param college the college to set
	 */
	public void setCollege(String college)
	{
		this.college = college;
	}


	/**
	 * @return the events
	 */
	public ArrayList<Result> getEvents()
	{
		return events;
	}


	/**
	 * @param events the events to set
	 */
	public void setEvents(ArrayList<Result> events)
	{
		this.events = events;
	}
	
	public void addEvents(ArrayList<Result> events)
	{
	    this.events.addAll(events);
	}
	
	public void addEvent(Result event)
	{
	    this.events.add(event);
	}

}
