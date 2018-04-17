
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements; 


public class StraightToTfrrs
{
   public static void main(String[] args)throws Exception{
   {
      StraightToTfrrs f = new StraightToTfrrs();
      DataPrint p = new DataPrint();
      ArrayList<Team> meet = new ArrayList<Team>();
      File file = new File("Team_stats.txt");
      FileWriter fw = new FileWriter(file.getAbsoluteFile());
      BufferedWriter bw = new BufferedWriter(fw);
      
      //PrintWriter file = new PrintWriter("Team_Stats", "UTF-8");
      String gen;
      String state;
      String method = " ";
      Scanner sc = new Scanner(System.in);   
         
      System.out.println("What team would you like to search for?(Capitalize) :");//getting school name
      String col = sc.nextLine();
      while (col.equals(""))
      {
         System.out.println("What team would you like to search for?(Capitalize) :");
         col = sc.nextLine();

      }
      while (!col.equals("done"))
      {
         System.out.println("What gender would you like to search for?(put m/f) :");///  m/f used in html query
         gen = sc.nextLine();
         System.out.println("What state is this team from?(2 letter code, Capitalize):"); /// tells what state
         state = sc.nextLine();   
         meet.add(f.dogibb(col, gen, state, f));  //////// where it goes to look up school
         //System.out.println(meet.get(0).getTeam().get(0).get8kxc());
         System.out.println("What team would you like to search for?(Capitalize) :");
         col = sc.nextLine();
         

      }
      System.out.println("What data do you want to look for? \n"
      +"Possible codes for all times: by8k, by5k, by6k, by2mile, by5ktrack, by3k, bysteeple, by10k, byall\n"
      +"Possible codes for prs: pr8k, pr5k, pr6k, pr2mile, pr5ktrack, pr3k, prsteeple, pr10k, prall(puts all prs into document), recent"); /// tells what state
      method = sc.nextLine();
      p.data(method, meet,bw);
      bw.close();
      sc.close();
      }
   }
                  /////
                     //////
   
   public Team dogibb(String col, String gen, String state, StraightToTfrrs f) throws Exception{ //////// make write to a file
   {
      Team nteam;
      col = col.replace(" ","_");
      String url = "https://www.tfrrs.org/teams/xc/"+state+"_college_"+gen+"_"+col+".html";// enters data into the query
      Document doc = Jsoup.connect(url).get();
      List<String> namelink = doc.getElementsByClass("name").eachText();// all the names on the page
      ArrayList<String> names = new ArrayList<String>();
      String[] n;
      for (int i=1; i<namelink.size();i++)
      {
        n = namelink.get(i).replace(",","").split(" ");
        if (n.length<3)
        {
           names.add(n[1]+" "+n[0]);
           Arrays.fill(n, null);                                          // need to empty array     
        }
        else
        {
            names.add(n[2]+" "+n[0]+" "+n[1]);
            Arrays.fill(n, null); 
        }
      }
      List<String> years =doc.getElementsByClass("year").eachText();// need to get each name
      //////////System.out.println(years);
      Elements links = doc.getElementsByClass("name").select("a[href*=www.tfrrs]"); // the refs assosiated with each name
      int i = 0;
      ArrayList<Athlete> team = new ArrayList<Athlete>();
      for (Element link : links)
      {
         
         String relink = link.attr("abs:href"); 
         String[] chch = relink.split("");
         ArrayList<String> pieces = new ArrayList<String>();
         for (String ch : chch)
         {
            if (!ch.equals(" "))
            {
               pieces.add(ch);
            }
         
         }
         String myname = (String) names.get(i);
         String myyear = (String) years.get(i);
         myname = myname.replace(" ","_");
         //System.out.println(myname);
         String newurl = "https://www.tfrrs.org/athletes/"+pieces.get(32)+pieces.get(33)+pieces.get(34)+pieces.get(35)+pieces.get(36)+pieces.get(37)+pieces.get(38)+"/"+col+"/"+myname+".html";
         i++;
         doc = Jsoup.connect(newurl).get();
         f.getinfo(doc , myname.replace("_"," "), myyear, gen, col,team);//// going to next function
                   
      
      
      }
      return f.makeTeam(team , col , gen); 
            //System.out.println(team);
      //System.out.println(team.get(1).getName()+team.get(1).get5ktrack());
   }   
 }
                  ///// /
                  ////  ///
 
 
   public void getinfo(Document doc, String name, String year, String gen, String col, ArrayList<Athlete> team) throws Exception{
   {
      //System.out.println(name);
           /////// how do i make this an Athlete type
      ArrayList<String> acttimes= new ArrayList<String>();
      //ArrayList<String> reevents= new ArrayList<String>();
      //ArrayList<String> acsport= new ArrayList<String>();
      List<String> times = doc.getElementsByClass("mark").eachText();
      List<String> events = doc.getElementsByClass("event").eachText();
      List<String> sport = doc.getElementsByClass("io").eachText();
      List<String> dates = doc.getElementsByClass("date").eachText();
      ArrayList<DateTime> k5xc = new ArrayList<DateTime>();
      ArrayList<DateTime> k8xc = new ArrayList<DateTime>();
      ArrayList<DateTime> twomile = new ArrayList<DateTime>();
      ArrayList<DateTime> k5track = new ArrayList<DateTime>();
      ArrayList<DateTime> k3track = new ArrayList<DateTime>();
      ArrayList<DateTime> steeple = new ArrayList<DateTime>();
      ArrayList<DateTime> k6xc = new ArrayList<DateTime>();
      ArrayList<DateTime> k10 = new ArrayList<DateTime>();
      ArrayList<DateTime> alldates = new ArrayList<DateTime>();
      //System.out.println(reevents);
      //System.out.println(acsport);
      //ArrayList<SimpleDateFormat> alltime = new ArrayList<SimpleDateFormat>(); 
      String[]  simpledate;
      String temp;
      for (int i=0; i<dates.size();i++)
      {
         if (dates.get(i).contains("/"))
         {
            temp = dates.get(i).replace("-"," ");
            //System.out.println(temp.split(" "));
            simpledate = temp.split(" ");
            //System.out.println(simpledate[0]);
            dates.remove(i);
            if (simpledate[1].contains("/"))
            {
               dates.add(i, simpledate[1].replace("/","-"));
            }
            if (simpledate.length==6)
            {
               dates.add(i,simpledate[3]+"-"+simpledate[4]+"-"+simpledate[5]);
            }
           //  else
//             {
//                dates.add(i, simpledate[1]);
//             }
         } 
      }
      //System.out.println(dates);
      for (int i=2; i<times.size();i++)
      {
         if (times.get(i).equals("NT"))
         {
            //events.remove(i-4);
            i++;
         }
         else
         {
            acttimes.add(times.get(i));
            i++;
         }
      }  
      sport.remove(0);
      events.remove(0);
      dates.remove(0);
      //
      //System.out.println(acttimes);
     
      SimpleDateFormat timeval = new SimpleDateFormat("MM.dd.yy 'at' mm:ss");
      DateTimeComparator comp = DateTimeComparator.getTimeOnlyInstance();
      DateTime realistic8k = new DateTime(2017,10,5,0,21,30);
      for (int i=0; i<acttimes.size();i++)
      {
         if (!acttimes.get(i).equals("NT") && !acttimes.get(i).equals("DNF") && acttimes.get(i).contains(":"))
         {
            Date newdate =  timeval.parse(dates.get(i).replace("-",".")+" at "+acttimes.get(i));
            //System.out.println(newdate);
            //System.out.println(newdate.getDay());
            DateTime d = new DateTime(1900+newdate.getYear(),newdate.getMonth()+1,newdate.getDate(), 0 ,
            newdate.getMinutes(),newdate.getSeconds());
            alldates.add(d);
            
            //System.out.println(d.toString("MM-dd-yy"));
            if (sport.get(i).equals("XC"))
            {
               if (events.get(i).equals("5K") || events.get(i).equals("3.1M"))
               {
                  //System.out.println(dates.get(i).replace("-","."));
                  k5xc.add(d);
                  //System.out.println(k5xc);
               }
               if ((events.get(i).equals("8K") || events.get(i).equals("5M") || events.get(i).equals("8.04672K"))&& comp.compare(d,realistic8k)>0 )
               {
                  k8xc.add(d);
               }
               if (events.get(i).equals("2M") || events.get(i).equals("3.2K"))
               {
                  twomile.add(d);
               }
               if (events.get(i).equals("6K"))
               {
                  k6xc.add(d);
               }

           }
           else
           {
               if (events.get(i).equals("5000"))
               {
                  k5track.add(d);
               
               }
               if (events.get(i).equals("3000S"))
               {
                  steeple.add(d);
   
               }
               if (events.get(i).equals("3000"))
               {
                  k3track.add(d);
               
               }
               if (events.get(i).equals("10,000"))
               {
                  k10.add(d);
               
               }
           }
           }
        if (acttimes.get(i).equals("NT") || acttimes.get(i).equals("DNF"))
         {
            //events.remove(i);
            //acttimes.remove(i);
         } 

      
      }
      team.add(new Athlete(name,gen,year,col,k5xc,k8xc,twomile,k5track,k3track,steeple,k10,k6xc,alldates,events));  ///make sure order is right
      
      }
      //System.out.println("");

   
   }
   
                     ///////
                     ////     
                              //
   public Team makeTeam(ArrayList<Athlete> team, String col, String gen)
   {
      if (gen.equals("m") || gen.equals("M") || gen.equals("Men") || gen.equals("men") || gen.equals("male") || gen.equals("Male"))
      {
        String gender = "Men";
        Team nteam = new Team(team, col, gender);
        return nteam;

      }
      else      
      {
         String gender = "Women"; 
         Team nteam = new Team(team, col, gender);
         return nteam;
      }

   }
   
}