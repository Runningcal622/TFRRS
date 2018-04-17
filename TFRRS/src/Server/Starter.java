package Server;
import application.*;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Starter
{
	public static void main(String[] args) throws IOException
	{
		String url = "https://www.tfrrs.org/teams/xc/ky_college_m_centre.html";// enters data into the query
		Document doc = Jsoup.connect(url).get();
		String script = doc.select("script").get(1).toString();
		String[] schools = script.split("}");
		ArrayList<String> onlyschools = new ArrayList<String>();
		for (String school : schools)
		{
			if (school.contains("(M)") || school.contains("(F)"))
			{
				school = school.replace("{text:", "");
				school = school.replace(",url:", "");
				school = school.replace(",", "");
				school = school.replace("\"", "");
				onlyschools.add(school);
			}
		}
		String first = onlyschools.get(0);
		first = onlyschools.get(0).replace("<script>\n" + "        Breakpoints();\n" + "	list_teams = [];\n"
				+ "	list_athletes = [];\n" + "	\n" + "	 var autocomplete_teams = [\n" + "			    ", "");
		onlyschools.set(0, first);
		Map<String, String> maleTeams = new HashMap<String, String>();
		Map<String, String> femaleTeams = new HashMap<String, String>();
		ArrayList<String[]> temphold = new ArrayList<String[]>();

		for (String s : onlyschools)
		{
			temphold.add(s.split("/", 2));
		}
		// splitting the url from the school
		for (String[] split : temphold)
		{
			split[1] = "/" + split[1];
		}
		String torg = "https://www.tfrrs.org";
		/// populating the map of schools to urls
		for (String[] pairs : temphold)
		{
			if (pairs[0].contains("(F)"))
			{
				pairs[0] = pairs[0].replace("(F)", "").replace("\n", "").toLowerCase().trim();// take out the gender and
																								// any
				// newlines
				femaleTeams.put(pairs[0], torg + pairs[1].trim());
			} else if (pairs[0].contains("(M)"))
			{
				pairs[0] = pairs[0].replace("(M)", "").replace("\n", "").toLowerCase().trim();
				maleTeams.put(pairs[0], torg + pairs[1].trim());
			}

		}
		Tfrrs tfrrs = new Tfrrs(femaleTeams, maleTeams);
		// Tfrrs tfrrs = new Tfrrs(femaleTeams,maleTeams);
		Encoder e = new Encoder();
		e.serialize(tfrrs);
	}

}
