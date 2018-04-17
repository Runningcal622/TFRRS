import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import Server.*;

@SuppressWarnings("unused")
public class DataPrint
{
   DateTimeComparator date = DateTimeComparator.getTimeOnlyInstance();
   DateTimeComparator day = DateTimeComparator.getDateOnlyInstance();
   
   
   public void data(String met, ArrayList<Team> meet, BufferedWriter bw) throws Exception{
   {
   
    if (met.equals("by8k"))
      {
    	System.out.println("HI"+meet.size());
    	
         for (int i = 0; i<meet.size();i++)
         {
            Team theteam = meet.get(i);
            System.out.println(theteam.getName());
            for (int m=0; m<theteam.getTeam().size();m++)
            {
               Athlete player = theteam.getTeam().get(m);
               if (!player.get8kxc().isEmpty())
               {
                  System.out.println(player.getName());
               }
               for (int j=0; j<player.get8kxc().size();j++)
               {
                  //System.out.println(player.getName()+player.get8kxc());
                  
                  System.out.println(player.get8kxc().get(j).toString("mm:ss"));//getMinuteOfHour()+":"+ player.get8kxc().get(j).getSecondOfMinute());
                 
               }
               System.out.println(" ");
            }
         }
         }
         //////// 2
     if (met.equals("by5k"))
      {
         for (int i = 0; i<meet.size();i++)
         {
            Team theteam = meet.get(i);
            System.out.println(theteam.getName());
            for (int m=0; m<theteam.getTeam().size();m++)
            {
               Athlete player = theteam.getTeam().get(m);
               if (!player.get5kxc().isEmpty())
               {
                  System.out.println(player.getName());
               }
               for (int j=0; j<player.get5kxc().size();j++)
               {
                  //System.out.println(player.getName()+player.get8kxc());
                  System.out.println(player.get5kxc().get(j).toString("mm:ss"));//getMinuteOfHour()+":"+ player.get8kxc().get(j).getSecondOfMinute());
                 
               }
               System.out.println(" ");
            }
         }
         }
         //////// 3
      if (met.equals("by6k"))
      {
         for (int i = 0; i<meet.size();i++)
         {
            Team theteam = meet.get(i);
            System.out.println(theteam.getName());
            for (int m=0; m<theteam.getTeam().size();m++)
            {
               Athlete player = theteam.getTeam().get(m);
               if (!player.get6k().isEmpty())
               {
                  System.out.println(player.getName());
               }
               for (int j=0; j<player.get6k().size();j++)
               {
                  //System.out.println(player.getName()+player.get8kxc());
                  System.out.println(player.get6k().get(j).toString("mm:ss"));//getMinuteOfHour()+":"+ player.get8kxc().get(j).getSecondOfMinute());
                 
               }
               System.out.println(" ");
            }
         }
   
   }
   
         //////4
   if (met.equals("by2mile"))
      {
         for (int i = 0; i<meet.size();i++)
         {
            Team theteam = meet.get(i);
            System.out.println(theteam.getName());
            for (int m=0; m<theteam.getTeam().size();m++)
            {
               Athlete player = theteam.getTeam().get(m);
               if (!player.getTwoMile().isEmpty())
               {
                  System.out.println(player.getName());
               }

               for (int j=0; j<player.getTwoMile().size();j++)
               {
                  //System.out.println(player.getName()+player.get8kxc());
                  System.out.println(player.getTwoMile().get(j).toString("mm:ss"));//getMinuteOfHour()+":"+ player.get8kxc().get(j).getSecondOfMinute());
                 
               }
               System.out.println(" ");
            }
         }
         }
         ///// 5
      if (met.equals("by5ktrack"))
      {
         for (int i = 0; i<meet.size();i++)
         {
            Team theteam = meet.get(i);
            System.out.println(theteam.getName());
            for (int m=0; m<theteam.getTeam().size();m++)
            {
               Athlete player = theteam.getTeam().get(m);
               if (!player.get5ktrack().isEmpty())
               {
                  System.out.println(player.getName());
               }

               for (int j=0; j<player.get5ktrack().size();j++)
               {
                  //System.out.println(player.getName()+player.get8kxc());
                  System.out.println(player.get5ktrack().get(j).toString("mm:ss"));//getMinuteOfHour()+":"+ player.get8kxc().get(j).getSecondOfMinute());
                 
               }
               System.out.println(" ");
            }
         }
         }
           // // // 6
      if (met.equals("by3k"))
      {
         for (int i = 0; i<meet.size();i++)
         {
            Team theteam = meet.get(i);
            System.out.println(theteam.getName());
            for (int m=0; m<theteam.getTeam().size();m++)
            {
               Athlete player = theteam.getTeam().get(m);
               if (!player.get3k().isEmpty())
               {
                  System.out.println(player.getName());
               }
               for (int j=0; j<player.get3k().size();j++)
               {
                  //System.out.println(player.getName()+player.get8kxc());
                  System.out.println(player.get3k().get(j).toString("mm:ss"));//getMinuteOfHour()+":"+ player.get8kxc().get(j).getSecondOfMinute());
                 
               }
               System.out.println(" ");
            }
         }
         }
            ///// 7
      if (met.equals("bysteeple"))
      {
         for (int i = 0; i<meet.size();i++)
         {
            Team theteam = meet.get(i);
            System.out.println(theteam.getName());
            for (int m=0; m<theteam.getTeam().size();m++)
            {
               Athlete player = theteam.getTeam().get(m);
               if (!player.getsteeple().isEmpty())
               {
                  System.out.println(player.getName());
               }
               for (int j=0; j<player.getsteeple().size();j++)
               {
                  //System.out.println(player.getName()+player.get8kxc());
                  System.out.println(player.getsteeple().get(j).toString("mm:ss"));//getMinuteOfHour()+":"+ player.get8kxc().get(j).getSecondOfMinute());
                 
               }
               System.out.println(" ");
            }
         }
         }
         ///// 8
      if (met.equals("by10k"))
      {
         for (int i = 0; i<meet.size();i++)
         {
            Team theteam = meet.get(i);
            System.out.println(theteam.getName());
            for (int m=0; m<theteam.getTeam().size();m++)
            {
               Athlete player = theteam.getTeam().get(m);
               if (!player.get10k().isEmpty())
               {
                  System.out.println(player.getName());
               }
               for (int j=0; j<player.get10k().size();j++)
               {
                  //System.out.println(player.getName()+player.get8kxc());
                  System.out.println(player.get10k().get(j).toString("mm:ss"));//getMinuteOfHour()+":"+ player.get8kxc().get(j).getSecondOfMinute());
                 
               }
               System.out.println(" ");
            }
         }
         }
         
         //////9
      if (met.equals("byall"))
      {
         for (int i = 0; i<meet.size();i++)
         {
            Team theteam = meet.get(i);
            System.out.println(theteam.getName());
            for (int m=0; m<theteam.getTeam().size();m++)
            {
               Athlete player = theteam.getTeam().get(m);
               if (!player.getalldates().isEmpty())
               {
                  System.out.println(player.getName());
               }
               for (int j=0; j<player.getalldates().size();j++)
               {
                  //System.out.println(player.getName()+player.get8kxc());
                  //System.out.println(player.getallevents().get(j).charAt(0));
                  System.out.println(player.getallevents().get(j)+"   "+player.getalldates().get(j).toString("mm:ss"));//getMinuteOfHour()+":"+ player.get8kxc().get(j).getSecondOfMinute());
                 
               }
               System.out.println(" ");
            }
         }
         }
         
         
               
               ////// pr 1
   if (met.equals("pr8k"))
      {
         for (int i = 0; i<meet.size();i++)
         {
            Team theteam = meet.get(i);
            System.out.println(theteam.getName());
            for (int m=0; m<theteam.getTeam().size();m++)
            {
               Athlete player = theteam.getTeam().get(m);
               ArrayList<DateTime> pt = player.get8kxc();
               pt.sort(date);
               if (!player.get8kxc().isEmpty())
               {
                  System.out.println(player.getName());
                  System.out.println(pt.get(0).toString("mm:ss"));
                  System.out.println(" ");
               }             

            }
         }
         }
         /////// pr 2
    if (met.equals("pr5k"))
      {
         for (int i = 0; i<meet.size();i++)
         {
            Team theteam = meet.get(i);
            System.out.println(theteam.getName());
            for (int m=0; m<theteam.getTeam().size();m++)
            {
               Athlete player = theteam.getTeam().get(m);
               ArrayList<DateTime> pt = player.get5kxc();
               pt.sort(date);
               if (!player.get5kxc().isEmpty())
               {
                  System.out.println(player.getName());
                  System.out.println(pt.get(0).toString("mm:ss"));
                  System.out.println(" ");
               } 
            }
         }
         }
         //////// pr 3
      if (met.equals("pr6k"))
      {
         for (int i = 0; i<meet.size();i++)
         {
            Team theteam = meet.get(i);
            System.out.println(theteam.getName());
            for (int m=0; m<theteam.getTeam().size();m++)
            {
               Athlete player = theteam.getTeam().get(m);
               ArrayList<DateTime> pt = player.get6k();
               pt.sort(date);
               if (!player.get6k().isEmpty())
               {
                  System.out.println(player.getName());
                  System.out.println(pt.get(0).toString("mm:ss"));
                  System.out.println(" ");
               }              
            }
         }
   
   }
   
         ////// pr 4
   if (met.equals("pr2mile"))
      {
         for (int i = 0; i<meet.size();i++)
         {
            Team theteam = meet.get(i);
            System.out.println(theteam.getName());
            for (int m=0; m<theteam.getTeam().size();m++)
            {
               Athlete player = theteam.getTeam().get(m);
               ArrayList<DateTime> pt = player.getTwoMile();
               pt.sort(date);
               if (!player.getTwoMile().isEmpty())
               {
                  System.out.println(player.getName());
                  System.out.println(pt.get(0).toString("mm:ss"));
                  System.out.println(" ");
               }
            }
         }
         }
         ///// pr 5
      if (met.equals("pr5ktrack"))
      {
         for (int i = 0; i<meet.size();i++)
         {
            Team theteam = meet.get(i);
            System.out.println(theteam.getName());
            for (int m=0; m<theteam.getTeam().size();m++)
            {
               Athlete player = theteam.getTeam().get(m);
               ArrayList<DateTime> pt = player.get5ktrack();
               pt.sort(date);              
               if (!player.get5ktrack().isEmpty())
               {
                  System.out.println(player.getName());
                  System.out.println(pt.get(0).toString("mm:ss"));
                  System.out.println(" ");
               }
            }
         }
         }
           // // // pr 6
      if (met.equals("pr3k"))
      {
         for (int i = 0; i<meet.size();i++)
         {
            Team theteam = meet.get(i);
            System.out.println(theteam.getName());
            for (int m=0; m<theteam.getTeam().size();m++)
            {
               Athlete player = theteam.getTeam().get(m);
               ArrayList<DateTime> pt = player.get3k();
               pt.sort(date);
               if (!player.get3k().isEmpty())
               {
                  System.out.println(player.getName());
                  System.out.println(pt.get(0).toString("mm:ss"));
                  System.out.println(" ");
               }
            }
         }
         }
            ///// pr 7
      if (met.equals("prsteeple"))
      {
         for (int i = 0; i<meet.size();i++)
         {
            Team theteam = meet.get(i);
            System.out.println(theteam.getName());
            for (int m=0; m<theteam.getTeam().size();m++)
            {
               Athlete player = theteam.getTeam().get(m);
               ArrayList<DateTime> pt = player.getsteeple();
               pt.sort(date);
               if (!player.getsteeple().isEmpty())
               {
                  System.out.println(player.getName());
                  System.out.println(pt.get(0).toString("mm:ss"));
                  System.out.println(" ");
               }
            }
         }
         }
         ///// pr 8
      if (met.equals("pr10k"))
      {
         for (int i = 0; i<meet.size();i++)
         {
            Team theteam = meet.get(i);
            System.out.println(theteam.getName());
            for (int m=0; m<theteam.getTeam().size();m++)
            {
               Athlete player = theteam.getTeam().get(m);
               ArrayList<DateTime> pt = player.get10k();
               pt.sort(date);
               if (!player.get10k().isEmpty())
               {
                  System.out.println(player.getName());
                  System.out.println(pt.get(0).toString("mm:ss"));
                  System.out.println(" ");
               }           
            }
         }
         }
      if (met.equals("prall"))
      {
         for (int i = 0; i<meet.size();i++)
         {
            Team theteam = meet.get(i);
            bw.write(theteam.getName());
            bw.newLine();
            bw.newLine();
            for (int m=0; m<theteam.getTeam().size();m++)
            {
               Athlete player = theteam.getTeam().get(m);
               ArrayList<DateTime> pt8 = player.get8kxc();
               pt8.sort(date);
               ArrayList<DateTime> pt6 = player.get6k();
               pt6.sort(date);
               ArrayList<DateTime> pt5 = player.get5kxc();
               pt5.sort(date);
               ArrayList<DateTime> pt2m = player.getTwoMile();
               pt2m.sort(date);
               ArrayList<DateTime> pt10 = player.get10k();
               pt10.sort(date);
               ArrayList<DateTime> pt5track = player.get5ktrack();
               pt5track.sort(date);
               ArrayList<DateTime> pt3 = player.get3k();
               pt3.sort(date);
               ArrayList<DateTime> ptsteeple = player.getsteeple();
               ptsteeple.sort(date);
               System.out.println(player.getName());
               bw.write(player.getName());
               bw.newLine();  
               if (!player.get8kxc().isEmpty())
               {
                  System.out.println("8k: "+pt8.get(0).toString("mm:ss"));
                  bw.write("8k: "+pt8.get(0).toString("mm:ss"));
                  bw.newLine();
               }  
               if (!player.get6k().isEmpty())
               {
                  System.out.println("6k: "+pt6.get(0).toString("mm:ss"));
                  bw.write("6k: "+pt6.get(0).toString("mm:ss"));
                bw.newLine();
               }   
               if (!player.get5kxc().isEmpty())
               {
                  System.out.println("5k xc: "+pt5.get(0).toString("mm:ss"));
                  bw.write("5k: "+pt5.get(0).toString("mm:ss"));
                  bw.newLine();
               }   
               if (!player.getTwoMile().isEmpty())
               {
                  System.out.println("2 mile: "+pt2m.get(0).toString("mm:ss"));
                  bw.write("2 mile: "+pt2m.get(0).toString("mm:ss"));
                  bw.newLine();
               }   
               if (!player.get10k().isEmpty())
               {
                  System.out.println("10k: "+pt10.get(0).toString("mm:ss"));
                  bw.write("10k: "+pt10.get(0).toString("mm:ss"));
                  bw.newLine();
 
               }   
               if (!player.get5ktrack().isEmpty())
               {
                  System.out.println("5k track: "+pt5track.get(0).toString("mm:ss"));
                  bw.write("5k track: "+pt5track.get(0).toString("mm:ss"));
                  bw.newLine();

               }   
               if (!player.get3k().isEmpty())
               {
                  System.out.println("3k: "+pt3.get(0).toString("mm:ss"));
                  bw.write("3k: "+pt3.get(0).toString("mm:ss"));
                  bw.newLine();
               }   
               if (!player.getsteeple().isEmpty())
               {
                  System.out.println("steeple: "+ptsteeple.get(0).toString("mm:ss"));
                  bw.write("steeple: "+ptsteeple.get(0).toString("mm:ss"));
                  bw.newLine();
               }
               bw.newLine();  
               System.out.println("");         
            }
         }
         //System.out.println("Written to file"+bw.toString());
     
        
      }
      
      
      if (met.equals("recent")) 
      {
         Scanner sc = new Scanner(System.in);
         System.out.println("What year would your date be?(use numbers)");//getting year
         int year = sc.nextInt();
         System.out.println("What month would your date be?(use numbers)");//getting month
         int month = sc.nextInt();
         System.out.println("What day would your date be?(use numbers)");//getting day
         int da = sc.nextInt();


         //String[] sep = oldday.split(" ");
         DateTime back = new DateTime(year ,month, da,0,0,0);
         for (int i = 0; i<meet.size();i++)
         {
            Team theteam = meet.get(i);
            System.out.println(theteam.getName());
            for (int m=0; m<theteam.getTeam().size();m++)
            {
               Athlete player = theteam.getTeam().get(m);
               if (!player.getalldates().isEmpty())
               {
                  System.out.println(player.getName());
               }
               for (int j=0; j<player.getalldates().size();j++)
               {
                  if (day.compare(player.getalldates().get(j),back)>0)
                  {
                     System.out.println(player.getallevents().get(j)+"   "+player.getalldates().get(j).toString("mm:ss"));//getMinuteOfHour()+":"+ player.get8kxc().get(j).getSecondOfMinute());
                  }
               }
               System.out.println(" ");
            }
         }
   
      sc.close();
      }

}
}
}