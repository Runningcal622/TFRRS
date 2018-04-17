package application;

import Server.*;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import org.joda.time.DateTimeComparator;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ToWeb
{
	private static final String SERIALIZED_FILE_NAME = "tfrrs.xml";
	static Document doc;
	static ArrayList<String> field = new ArrayList<String>(Arrays.asList("Long Jump", "Triple Jump", "Javelin",
			"High Jump", "Pole Vault", "Shot Put", "Discus", "Hammer", "Weight Throw"));
	

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException
	{
		doc = Jsoup.connect("https://www.google.com").get();

		// file to write to
		File file = new File("allteams.txt");
		System.out.println(file.getAbsolutePath());
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);

		// new tfrrs object of teams map
		Tfrrs tfrrs = ToWeb.deceral();

		// team urls
		Map<String, String> webAddress = ToWeb.read_In(tfrrs);
		ArrayList<String> Athlete_names = new ArrayList<String>();

		// the name of an athlete to the url for his webpage
		Map<String, ArrayList<String>> namelinks = new HashMap<String, ArrayList<String>>();

		namelinks = ToWeb.getAthletes(webAddress);

		/// map of teams to list of their athletes
		Map<String, ArrayList<Athlete>> teamsToAthletes = ToWeb.getAthInfo(namelinks);

		/// gets stats for each athlete
		for (Entry<String, ArrayList<Athlete>> ent : teamsToAthletes.entrySet())
		{
			for (Athlete ath : ent.getValue())
			{
				System.out.println(ath.getName());
				ath = ToWeb.getStats(ath);
			}

		}


		bw.close();

	}

	/*
	 * this takes a map of names to urls and returns a map of teams to list of
	 * athletes
	 */
	public static Map<String, ArrayList<Athlete>> getAthInfo(Map<String, ArrayList<String>> namelinks)
	{
		String key;
		ArrayList<String> value;
		Map<String, ArrayList<Athlete>> teamsToAthletes = new HashMap<String, ArrayList<Athlete>>();
		for (Entry<String, ArrayList<String>> entry : namelinks.entrySet())
		{

			// key is name last,first
			key = entry.getKey();
			/// value is arraylist if url, gender, team name, and year in a string
			value = entry.getValue();
			String[] nameS = key.split(",");
			String athname = nameS[1] + " " + nameS[0];
			String gen = value.get(1);
			String teamname = value.get(2);
			String url = value.get(0);
			String year = value.get(3);
			// System.out.println(athname + " " + value.get(1) + " " + value.get(2)+"
			// "+value.get(0)+" "+value.get(3));

			teamsToAthletes = ToWeb.teamCreation(athname, url, gen, teamname, year, teamsToAthletes);

		}
		return teamsToAthletes;
	}

	public static Athlete getStats(Athlete ath) throws IOException
	{
		doc = Jsoup.connect(ath.getUrl()).get();
		/// gets the data from only meet results table
		/// data is seperated by each meet
		List<String> e = doc.getElementById("event-history").getElementsByClass("table table-hover"/* col-lg-12" */)
				.eachText();

		for (String s : e)
		{

			String[] sub = s.split("\\(", 2);
			// first piece is the event name
			String event = sub[0].trim();
			String[] othersub = sub[1].split("\\)", 2);

			// second piece is the indoor or outdoor
			String inorout = othersub[0];
			String nonDividedSub = othersub[1];
			if (field.contains(event))
			{
				ath = doFieldResult(s, ath, event, inorout, nonDividedSub);

			}

			else
			{
				ath = doNonFieldResult(s, ath, event, inorout, nonDividedSub);
			}

			// sub[1] = sub[1].split(")");
		}

		return ath;
	}

	/*
	 * this adds non field event efforts to an athlete and returns them
	 */

	private static Athlete doNonFieldResult(String s, Athlete ath, String event, String inorout, String nonDividedSub)
	{
		ArrayList<String> substringsNon = new ArrayList<String>();
		ArrayList<Integer> commaIndexNon = new ArrayList<Integer>();
		/// do field making result
		/// get the comma indecies
		commaIndexNon = getCommas(nonDividedSub);
		substringsNon = makeSubs(commaIndexNon, nonDividedSub);

		ArrayList<ArrayList<String>> both = seperate(substringsNon);
		ArrayList<String> substringsWithFoulNon = both.get(1);
		ArrayList<String> substringsNoFoulNon = both.get(0);

		ArrayList<Result> noFoulResArrNon = interpretNoFoulTrack(substringsNoFoulNon, event, inorout);
		ArrayList<Result> FoulResArrNon = interpretFoulTrack(substringsWithFoulNon, event, inorout);
		noFoulResArrNon.addAll(FoulResArrNon);
		ath.addEvents(noFoulResArrNon);
		return ath;
	}

	private static ArrayList<Result> interpretFoulTrack(ArrayList<String> substringsWithFoulNon, String event,
			String inorout)
	{
		ArrayList<Result> resArr = new ArrayList<Result>();
		for (String effort : substringsWithFoulNon)
		{
			effort.trim();
			ArrayList<String> foulDis = new ArrayList<String>();
			Collections.addAll(foulDis, effort.split(" "));
			/// remove empty
			foulDis.remove("");
			// first one is just a comma
			foulDis.remove(0);

			/// meet info
			getMeetAndDate(foulDis);
			String meetPlace = makeMeetPlace(foulDis);

			List<String> meetDateArr = foulDis.subList(foulDis.size() - 3, foulDis.size());
			// for Localdate object
			int day = Integer.parseInt(meetDateArr.get(1));
			int month = getMonthInt(meetDateArr.get(0));
			int year = Integer.parseInt(meetDateArr.get(2));

			LocalDate date = new LocalDate(year, month, day);
			RunningTime time = new RunningTime("0:0");
			// System.out.println(meetPlace + " " + date.toString() + " " + inMeters);
			Result oneRes = new Result(event, meetPlace, inorout, time, date);
			resArr.add(oneRes);
		}
		return resArr;
	}

	private static ArrayList<Result> interpretNoFoulTrack(ArrayList<String> substringsNoFoulNon, String event,
			String inorout)
	{
		ArrayList<Result> resArr = new ArrayList<Result>();
		for (String effort : substringsNoFoulNon)
		{
			ArrayList<String> foulDis = new ArrayList<String>();
			Collections.addAll(foulDis, effort.split(" "));
			foulDis.remove(0);
			String timeInString = foulDis.get(0);
			timeInString = timeInString.replace(".", ":").trim();
			timeInString = timeInString.replace("w", "");
			timeInString = timeInString.replace("W", "");
			timeInString = timeInString.replace("H", "");
			timeInString = timeInString.replace("h", "");
			RunningTime time = new RunningTime(timeInString);
			foulDis.remove(0);
			foulDis.set(foulDis.size() - 2, foulDis.get(foulDis.size() - 2).replace(",", ""));
			List<String> dateArr = foulDis.subList(foulDis.size() - 3, foulDis.size());
			List<String> meetArr = foulDis.subList(0, foulDis.size() - 3);
			if (dateArr.get(1).length() > 2)
			{
				String day = dateArr.get(1);
				int dash = day.indexOf("-");
				String subday = day.substring(dash + 1, day.length());
				// sets the piece equal to itself minus the extra day and dash
				dateArr.set(1, subday);
			}
			int day = Integer.parseInt(dateArr.get(1));
			int month = getMonthInt(dateArr.get(0));
			int year = Integer.parseInt(dateArr.get(2));

			LocalDate date = new LocalDate(year, month, day);
			String meetPlace = "";
			for (String m : meetArr)
			{
				meetPlace += m+" ";
			}
			Result oneRes = new Result(event, meetPlace, inorout, time, date);
			resArr.add(oneRes);
		}
		return resArr;
	}

	/*
	 * this takes info for a field event and adds it to the athlete returns athlete
	 * with added data
	 */
	private static Athlete doFieldResult(String s, Athlete ath, String event, String inorout, String nonDividedSub)
	{
		ArrayList<String> substrings = new ArrayList<String>();
		ArrayList<Integer> commaIndex = new ArrayList<Integer>();
		/// do field making result
		/// get the comma indecies
		commaIndex = getCommas(nonDividedSub);
		substrings = makeSubs(commaIndex, nonDividedSub);

		ArrayList<ArrayList<String>> both = seperate(substrings);
		ArrayList<String> substringsWithFoul = both.get(1);
		ArrayList<String> substringsNoFoul = both.get(0);

		ArrayList<Result> noFoulResArr = interpretNoFoul(substringsNoFoul, event, inorout);
		ArrayList<Result> FoulResArr = interpretFoul(substringsWithFoul, event, inorout);
		noFoulResArr.addAll(FoulResArr);
		ath.addEvents(noFoulResArr);
		return ath;
	}

	private static ArrayList<Result> interpretFoul(ArrayList<String> substringsWithFoul, String event, String inorout)
	{
		// array of results
		ArrayList<Result> resArr = new ArrayList<Result>();
		for (String effort : substringsWithFoul)
		{
			effort.trim();
			ArrayList<String> foulDis = new ArrayList<String>();
			Collections.addAll(foulDis, effort.split(" "));
			/// remove empty
			foulDis.remove("");
			// first one is just a comma
			foulDis.remove(0);

			Float inMeters = (float) 0;
			/// meet info
			getMeetAndDate(foulDis);
			String meetPlace = makeMeetPlace(foulDis);

			List<String> meetDateArr = foulDis.subList(foulDis.size() - 3, foulDis.size());
			// for Localdate object
			int day = Integer.parseInt(meetDateArr.get(1));
			int month = getMonthInt(meetDateArr.get(0));
			int year = Integer.parseInt(meetDateArr.get(2));

			LocalDate date = new LocalDate(year, month, day);
			// System.out.println(meetPlace + " " + date.toString() + " " + inMeters);
			Result oneRes = new Result(event, meetPlace, inorout, inMeters, date);
			resArr.add(oneRes);

		}
		return resArr;
	}

	/*
	 * this takes a list of events that have a distance and returns a list of
	 * results from those events
	 * 
	 */

	private static ArrayList<Result> interpretNoFoul(ArrayList<String> substringsNoFoul, String event, String inorout)
	{
		ArrayList<Result> resArr = new ArrayList<Result>();

		for (String effort : substringsNoFoul)
		{
			ArrayList<String> dis = new ArrayList<String>();
			Collections.addAll(dis, effort.split("\""));
			String twoDistances = dis.get(0).trim();
			String meetAndDate = dis.get(1).trim();
			ArrayList<String> theDistance = new ArrayList<String>();
			ArrayList<String> meetData = new ArrayList<String>();

			/// making arrayslist from string[]
			Collections.addAll(theDistance, twoDistances.split(" "));
			Collections.addAll(meetData, meetAndDate.split(" "));

			/// getting the distance from substring
			getTheDistance(theDistance);
			// get meet name and the date
			getMeetAndDate(meetData);

			String meetPlace = makeMeetPlace(meetData);

			List<String> meetDateArr = meetData.subList(meetData.size() - 3, meetData.size());

			int day = Integer.parseInt(meetDateArr.get(1));
			int month = getMonthInt(meetDateArr.get(0));
			int year = Integer.parseInt(meetDateArr.get(2));

			LocalDate date = new LocalDate(year, month, day);
			//// meters
			theDistance.set(0, theDistance.get(0).replace("W", ""));
			Float inMeters = Float.parseFloat(theDistance.get(0));
			// System.out.println(meetPlace + " " + date.toString() + " " + inMeters);
			Result oneRes = new Result(event, meetPlace, inorout, inMeters, date);
			resArr.add(oneRes);
		}
		return resArr;
	}

	/*
	 * returns the string of where the meet takes place
	 */
	private static String makeMeetPlace(ArrayList<String> meetData)
	{
		List<String> meetPlaceArr = meetData.subList(0, meetData.size() - 3);
		String meetPlace = "";
		for (String meetPiece : meetPlaceArr)
		{
			meetPlace += meetPiece + " ";
		}
		meetPlace = meetPlace.trim();
		return meetPlace;
	}

	private static int getMonthInt(String monthS)
	{
		switch (monthS.toLowerCase()) {
		case "jan":
			return 1;
		case "feb":
			return 2;
		case "mar":
			return 3;
		case "apr":
			return 4;
		case "may":
			return 5;
		case "jun":
			return 6;
		case "jul":
			return 7;
		case "aug":
			return 8;
		case "sep":
			return 9;
		case "oct":
			return 10;
		case "nov":
			return 11;
		case "dec":
			return 12;
		}
		return 0;
	}

	/*
	 * given a array of meet and date info it gets seperates it properly
	 * 
	 */
	private static void getMeetAndDate(ArrayList<String> meetData)
	{
		int meetDSize = meetData.size();
		meetData.set(meetDSize - 2, meetData.get(meetDSize - 2).replace(",", ""));
		/// if date piece is longer than 2 it must be a 2 day meet
		if (meetData.get(meetDSize - 2).length() > 2)
		{
			String day = meetData.get(meetDSize - 2);
			int dash = day.indexOf("-");
			String subday = day.substring(dash + 1, day.length());
			// sets the piece equal to itself minus the extra day and dash
			meetData.set(meetDSize - 2, subday);
		}
		// take out empty strings, winds
		for (int iter = 0; iter < meetData.size(); iter++)
		{
			String element = meetData.get(iter);
			if (element.length() == 0 || element.contains("(") || element.equals("  "))
			{
				meetData.remove(element);
				iter--;
			}
		}

	}

	private static void getTheDistance(ArrayList<String> theDistance)
	{
		for (int iter = 0; iter < theDistance.size(); iter++)
		{
			String element = theDistance.get(iter);
			if (element.length() == 0 || element.contains("(") || element.equals("  "))
			{
				theDistance.remove(element);
				iter--;
			}
		}
		theDistance.remove(1);
		/// get the meter
		String meter = theDistance.get(0).replace("mw", "");
		meter = meter.replace("m", "");
		meter = meter.replace("w","");
		theDistance.set(0, meter);
		/// get feet
		theDistance.set(1, theDistance.get(1).replace("\'", ""));

	}

	private static ArrayList<ArrayList<String>> seperate(ArrayList<String> substrings)
	{
		ArrayList<String> substringsWithFoul = new ArrayList<String>();
		ArrayList<String> substringsNoFoul = new ArrayList<String>();
		for (String sub : substrings)
		{
			if (sub.contains("FOUL") || sub.contains("ND") || sub.contains("DNF") 
					|| sub.contains("NH") || sub.trim().equals(""))
			{
				substringsWithFoul.add(sub);
			} else
			{
				substringsNoFoul.add(sub);
			}
		}
		ArrayList<ArrayList<String>> both = new ArrayList<ArrayList<String>>();
		both.add(substringsNoFoul);
		both.add(substringsWithFoul);
		return both;
	}

	/*
	 * This takes a arrraylist of comma positions and a undivided string and returns
	 * all the substrings
	 * 
	 */
	private static ArrayList<String> makeSubs(ArrayList<Integer> commaIndex, String nonDividedSub)
	{
		ArrayList<String> substrings = new ArrayList<String>();
		int startOfSubstring = 0;
		int endOfSubstring = commaIndex.get(0);
		for (int m = 0; m < commaIndex.size(); m++)
		{

			substrings.add(nonDividedSub.substring(startOfSubstring, endOfSubstring));
			startOfSubstring = endOfSubstring;

			/// for getting the next substring
			if (m == commaIndex.size() - 1)
			{
				endOfSubstring = nonDividedSub.length() - 1;

			} else
			{
				endOfSubstring = commaIndex.get(m + 1);
			}

		}
		return substrings;
	}

	/*
	 * get the indecies of the commas in an undivided string
	 * 
	 * 
	 */
	private static ArrayList<Integer> getCommas(String nonDividedSub)
	{
		ArrayList<Integer> commaIndex = new ArrayList<Integer>();
		for (int i = 0; i < nonDividedSub.length(); i++)
		{
			if (nonDividedSub.charAt(i) == ',')
			{
				commaIndex.add(i + 6);
			}
		}
		return commaIndex;
	}

	public static Map<String, ArrayList<Athlete>> teamCreation(String athName, String url, String gender,
			String teamname, String year, Map<String, ArrayList<Athlete>> map)
	{
		Athlete ath = new Athlete(athName, gender, year, teamname, url, new ArrayList<Result>());
		ArrayList<Athlete> current = new ArrayList<Athlete>();
		if (map.containsKey(teamname + gender))
		{
			current = map.get(teamname + gender);
			current.add(ath);

		} else
		{
			ArrayList<Athlete> createArray = new ArrayList<Athlete>();
			createArray.add(ath);
			map.put(teamname + gender, createArray);
		}
		return map;
	}

	/*
	 * Input is a map of team names to team urls Output is a map of a name to
	 * arraylist of url, gender, and team name
	 */

	public static Map<String, ArrayList<String>> getAthletes(Map<String, String> webAddress) throws IOException
	{
		Document doc;
		String both;
		String[] split;
		String name;
		String link;
		String gen = null;
		Map<String, ArrayList<String>> nlink = new HashMap<String, ArrayList<String>>();
		for (Entry<String, String> s : webAddress.entrySet())
		{
			String key = s.getKey();
			String value = s.getValue();
			doc = Jsoup.connect(value).get();
			Elements e = doc.select("div.col-lg-4").select("a[href*=www.tfrrs.org/athletes]");

			/// gets the year of each person
			Elements year = doc.select("div.col-lg-4").select("table").select("td");
			ArrayList<String> eachyear = new ArrayList<String>();
			for (int i = 0; i < year.size(); i++)
			{
				if (year.get(i).toString().contains("href"))
				{
					year.remove(i);
					i -= 1;
				} else
				{
					String curyear = year.get(i).toString().replaceAll("<td>", "");
					curyear = curyear.replaceAll("</td>", "");
					curyear = curyear.trim();
					eachyear.add(curyear);
				}
			}

			// System.out.println(eachyear);
			if (value.contains("_f_"))
			{
				gen = "f";
			} else
			{
				gen = "m";
			}
			int numyear = 0;
			for (Element ele : e)
			{
				//// formatting the url and splitting it into the name and the url

				both = ele.toString();
				both = both.replace("<a href=\"", "");
				both = both.replace("</a>", "");
				both = both.replace("\"", "");
				both = both.replaceFirst("/", "");
				both = both.replaceFirst("/", "");
				both = both.replaceAll("\\s", "");
				split = both.split(">");
				name = split[1];
				link = "https://" + split[0].trim();
				// System.out.println(split[1] + " " + split[0] + " " + gen);
				ArrayList<String> linkgen = new ArrayList<String>();
				linkgen.add(link);
				linkgen.add(gen);
				linkgen.add(key.substring(0, key.length() - 1));
				linkgen.add(eachyear.get(numyear));
				nlink.put(name, linkgen);
				numyear++;
			}

		}
		return nlink;

	}

	/*
	 * Input: This takes a college name, a map of all colleges to urls; one for male
	 * teams
	 * 
	 * Output: returns the url of the team they wish to find
	 * 
	 */
	private static String find_Team_Male(String college, Map<String, String> male)
	{
		// System.out.println((college));
		if (male.containsKey(college))
		{
			return male.get(college);
		}
		return "not in database";
	}

	/*
	 * Input: This takes a college name, a map of all colleges to urls; one for
	 * female teams Output: returns the url of the team they wish to find
	 * 
	 */
	private static String find_Team_Female(String college, Map<String, String> female)
	{
		if (female.containsKey(college))
		{
			return female.get(college);
		}
		return null;
	}

	/*
	 * Input: This is given a tffrs object output: The ArrayList of urls for the
	 * colleges requested this adds the urls for the toweb object to use later when
	 * visiting these teams for the data they hold
	 */
	private static Map<String, String> read_In(Tfrrs tfrrs)
	{
		Map<String, String> web_Address = new HashMap<String, String>();
		String gen;
		Scanner sc = new Scanner(System.in);
		System.out.println("What team would you like to search for? :");// getting school name
		String col = sc.nextLine().toLowerCase();
		while (!col.equals("done"))
		{
			System.out.println("What gender?(m:male  f:female   b:both)");
			gen = sc.nextLine();
			// System.out.println(tfrrs.maleTeams.size());
			if (gen.equals("m") || gen.equals("M"))
			{
				/// add m or f to college so if you add the female and male teams of a college
				/// both
				/// hashes are saved since they would be the same otherwise
				web_Address.put(col + "m", ToWeb.find_Team_Male(col, tfrrs.maleTeams));
			}
			if (gen.equals("F") || gen.equals("f"))
			{
				web_Address.put(col + "f", ToWeb.find_Team_Female(col, tfrrs.femaleTeams));
			}
			if (gen.equals("B") || gen.equals("b"))
			{
				web_Address.put(col + "m", ToWeb.find_Team_Male(col, tfrrs.maleTeams));
				web_Address.put(col + "f", ToWeb.find_Team_Female(col, tfrrs.femaleTeams));
			}
			System.out.println("What team would you like to search for?(Capitalize) :");// getting school name
			col = sc.nextLine().toLowerCase();
		}
		sc.close();
		return web_Address;
	}

	/*
	 * Input: given input from the GUI returns the map of team names to urls
	 */
	public static Map<String, String> GuiInput(ArrayList<String> femaleT, ArrayList<String> maleT, Tfrrs tfrrs)
	{
		Map<String, String> web_Address = new HashMap<String, String>();
		String url;

		for (String female : femaleT)
		{
			url = tfrrs.femaleTeams.get(female);
			web_Address.put(female + "f", url);
			// System.out.println("added "+female+"f");
		}

		for (String male : maleT)
		{
			url = tfrrs.maleTeams.get(male);
			web_Address.put(male + "m", url);
			// System.out.println("added "+male+"m");
		}
		return web_Address;
	}

	/*
	 * This decerializes the Tfrrs xml file that is saved
	 * 
	 */
	public static Tfrrs deceral()
	{
		XMLDecoder decoder = null;
		try
		{
			decoder = new XMLDecoder(new BufferedInputStream(new FileInputStream(SERIALIZED_FILE_NAME)));
		} catch (FileNotFoundException e)
		{
			System.out.println("ERROR: File dvd.xml not found");
		}
		Tfrrs tfrrs = (Tfrrs) decoder.readObject();
		// System.out.println(bourneSeries);
		return tfrrs;
	}
}
