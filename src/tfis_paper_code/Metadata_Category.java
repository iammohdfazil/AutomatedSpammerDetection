package tfis_paper_code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Months;
import org.joda.time.Seconds;
import org.joda.time.Years;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.math.LongMath;

public class Metadata_Category {
	
	public static void retweetRatio() throws IOException,InterruptedException
    	{
		//Provide the path of the file having the user-id
		Scanner inputfile = new Scanner(System.in);
		BufferedReader br, br1;
		PrintWriter pw;
		while(true)
		{	
			try
			{
				//Provide the path of file that has users' id
				System.out.println("Enter the path of the file that has users ids in csv format");
				String uid_file=inputfile.nextLine();
				br=new BufferedReader(new FileReader(uid_file));
				break;
			}
			catch(FileNotFoundException fe)
			{
				System.out.println("Please enter a valid file path");
				continue;
			}
		}
		while(true)
		{	
			try
			{
				//Provide the path of the file to save the retweet ratio of users
				System.out.println("Enter the path of the file to save the retweet ratio in csv format");
				String retweet_outfile=inputfile.nextLine();
				pw=new PrintWriter(new FileWriter(retweet_outfile, true));
				break;
			}
			catch(FileNotFoundException fe)
			{
				System.out.println("Please enter a valid file path");
				continue;
			}
		}
		String tweet, userid;
		String tweettokens[], useridtokens[];
		
		//Loop to calculate retweet count for every user using their user-id
		while((userid=br.readLine())!=null)
		{
			int retweetcount=0, tweetcount=0;
			useridtokens=userid.split(",");
			
			while(true)
			{	
				try
				{
					//Provide the path of file that has users' tweets
					System.out.println("Enter the path of the file that has users' tweets in csv format");
					String tweets_file=inputfile.nextLine();
					br1=new BufferedReader(new FileReader(tweets_file));
					break;
				}
				catch(FileNotFoundException fe)
				{
					System.out.println("Please enter a valid file path");
					continue;
				}
			}
			
			//Loop to check the retweet status of tweets
			int i=1;
			while((tweet=br1.readLine())!=null)
			{
				tweettokens=tweet.split(",");
				System.out.println(i++);
				//Condition to equality of userid
				if(useridtokens[0].trim().equalsIgnoreCase(tweettokens[0].trim()))
				{
					tweetcount++;
					if(tweettokens[1].trim().startsWith("RT@")||tweettokens[1].trim().startsWith("RT")||tweettokens[1].trim().startsWith("RT @"))				
						retweetcount++;
				}
			}
			br1.close();
			//For the users who donot have tweets
			if(tweetcount==0)
				pw.println(useridtokens[0]+","+0);
			else
				pw.println(useridtokens[0]+","+((float)retweetcount/tweetcount));
			System.out.println("Retweet count for user  "+useridtokens[0]+"	is:	"+retweetcount+"	Tweet count is:	"+tweetcount+"	and Retweet ratio is:	"+((float)retweetcount/tweetcount));
		}
		inputfile.close(); pw.close();br.close();
    	}

	public static void automatedTweetRatio() throws IOException,InterruptedException
    	{
		Scanner inputfile = new Scanner(System.in);
		BufferedReader br, br1;
		PrintWriter pw;
		
		while(true)
		{	
			try
			{
				System.out.println("Enter the path of the file that has users ids in csv format");
				String uid_file=inputfile.nextLine();
				br=new BufferedReader(new FileReader(uid_file));
				break;
			}
			catch(FileNotFoundException fe)
			{
				System.out.println("Please enter a valid file path");
				continue;
			}
		}
	 	
		while(true)
		{	
			try
			{
				 	
				//provide the path of the output file to save the automation tweet ratio
				System.out.println("Enter the path of the file to save the automated tweet ratio in csv format");
				String users_ATR_file=inputfile.nextLine();
			 	pw=new PrintWriter(new FileWriter(users_ATR_file));
			 	break;
			}
			catch(FileNotFoundException fe)
			{
				System.out.println("Please enter a valid file path");
				continue;
			}
		}

	 	String [] useridtokens;
		String tweet, userid;
		
	 	while((userid=br.readLine())!=null)
	 	{
	 		useridtokens=userid.split(",");
	 		
	 		while(true)
			{	
				try
				{
					//Provide the path of file that has users' tweets
					System.out.println("Enter the path of the file that has users' tweets in csv format");
					String tweets_file=inputfile.nextLine();
					br1=new BufferedReader(new FileReader(tweets_file));
					break;
				}
				catch(FileNotFoundException fe)
				{
					System.out.println("Please enter a valid file path");
					continue;
				}
			}
	
			int noof_apitweets=0, noof_tweets=0;
			
			while((tweet=br1.readLine())!=null)
			{
				String	[]tweet_tokens=tweet.split(",");
		 		
				if(tweet_tokens[1].trim().isEmpty()==false&&(Integer.parseInt(tweet_tokens[1].trim())==Integer.parseInt(useridtokens[0].trim())))
				{
					noof_tweets++;
					if(tweet_tokens[3].trim().equals("web"))
						continue;
					else
						noof_apitweets++;
				}	
			}
			double apiratio=((double)noof_apitweets/noof_tweets);
			pw.write(useridtokens[0].trim()+","+apiratio+"\n");
			br1.close();
	 	}
	 	inputfile.close(); br.close();	pw.close();	
   	}
	
	//Method to compute the tweets time standard deviation(TSD)
	public static void tweetsTimeStandardDeviation() throws IOException,InterruptedException
    	{
		Scanner inputfile = new Scanner(System.in);
		BufferedReader br, br1;
		BufferedWriter pw;
		
		while(true)
		{	
			try
			{
				//Provide the path of the file having the users' tweets-time
				System.out.println("Enter the path of the file that has users' tweets-timing in csv format");
				String tweets_timefile=inputfile.nextLine();
				br=new BufferedReader(new FileReader(tweets_timefile));
				break;
			}
			catch(FileNotFoundException fe)
			{
				System.out.println("Please enter a valid file path");
				continue;
			}
		}
		while(true)
		{	
			try
			{
				//Provide the path of the file having the user-id
				System.out.println("Enter the path of the file that has users' id in csv format");
				String uid_file=inputfile.nextLine();
				br1=new BufferedReader(new FileReader(uid_file));
				break;
			}
			catch(FileNotFoundException fe)
			{
				System.out.println("Please enter a valid file path");
				continue;
			}
		}
		while(true)
		{	
			try
			{
				//Provide the path of the file to save the users and their tweet-time standard deviation
				System.out.println("Enter the path of the file to save the tweets time standard deviation in csv format");
				String tweet_time_sd=inputfile.nextLine();
				pw=new BufferedWriter(new FileWriter(tweet_time_sd,true));
				break;
			}
			catch(FileNotFoundException fe)
			{
				System.out.println("Please enter a valid file path");
				continue;
			}
		}
		//Multimap to store the users ids and their tweets times
		ArrayListMultimap<Integer,Long> userid_and_tweetstime=ArrayListMultimap.create();
		String [] tweet_token, useridtokens;
		String tweet, userid;
		
		//Loop to map users' id with their tweets time
		while((tweet=br.readLine())!=null)
		{
			tweet_token=tweet.split(",");
			userid_and_tweetstime.put(Integer.parseInt(tweet_token[0].trim()), Long.parseLong(tweet_token[2].replace("L", "").trim()));			
		}
		br.close();
		
		//List creation for sorting of user id list in ascending order
		Set<Integer> user_id_set=userid_and_tweetstime.keySet();
		List<Integer> user_id_list=new ArrayList<Integer>(user_id_set);
		Collections.sort(user_id_list);
		
		while((userid=br1.readLine())!=null)
	 	{
	 		useridtokens=userid.split(",");
	 		int uid=Integer.parseInt(useridtokens[0].trim());
			int h=0,m=0,s=0;
			
			TreeSet<Long> sorted_time=new TreeSet<Long>();
			if(Collections.binarySearch(user_id_list, uid)>=0)
				sorted_time.addAll(userid_and_tweetstime.get(uid));
		
			int len=sorted_time.size();
			Object[] time=sorted_time.toArray();
			String[] tweet_time=new String[time.length];
			
			for(int j=0;j<tweet_time.length;j++)
				tweet_time[j]=time[j].toString();
			
			if(tweet_time.length==0||tweet_time.length==1)
			{
				System.out.println("No tweet or only one tweet");
				pw.write(uid+","+"0"+"\n");
			}	
			else
			{
				System.out.println(uid+"=="+(tweet_time.length)+"len"+len);
				long sum_time_square=0;
				pw.write(uid+",");
				for(int l=0;l<tweet_time.length;l++)
				{							
					long t1=Long.parseLong(tweet_time[l].replace("L", "").trim());
					DateTime dt1=new DateTime(t1);
				
					h=h+dt1.getHourOfDay();
					m=m+dt1.getMinuteOfHour();
					s=s+dt1.getSecondOfMinute();
					long time_in_sec=dt1.getHourOfDay()*3600+dt1.getMinuteOfHour()*60+dt1.getSecondOfMinute();
					sum_time_square=sum_time_square+(time_in_sec*time_in_sec);
				}
	
				sum_time_square=sum_time_square/(tweet_time.length);
				
				//Converting the sun time into a unified unit of second
				long tweet_time_sum=h*3600+m*60+s;
				
				//Dividing total second by the number of time interval
				tweet_time_sum=tweet_time_sum/len;	
				
				long mean_sec_square_long=tweet_time_sum*tweet_time_sum;
				
				long var_in_sec=sum_time_square-mean_sec_square_long;
				
				long sd_in_sec=LongMath.sqrt(var_in_sec, RoundingMode.FLOOR );
				System.out.println(var_in_sec+"		sd_in_sec   "+sd_in_sec);
				pw.write(sd_in_sec+"\n");
			}
		}
		inputfile.close(); br1.close(); pw.close();
		System.out.println("Completed");
	}
	
	//Method to compute the intermediate result to compute the tweets time interval standard deviation(TISD)
	public static void tweetsTimeIntervalAndTheirAverage() throws IOException,InterruptedException
    	{
		Scanner inputfile = new Scanner(System.in);
		BufferedReader br, br1;
		BufferedWriter pw;
		
		while(true)
		{	
			try
			{
				//Provide the path of the file that has users' tweets-time
				System.out.println("Enter the path of the file that has users' tweets times in csv format");
				String tweets_timefile=inputfile.nextLine();
				br=new BufferedReader(new FileReader(tweets_timefile));
				break;
			}
			catch(FileNotFoundException fe)
			{
				System.out.println("Please enter a valid file path");
				continue;
			}
		}
		while(true)
		{	
			try
			{
				//Provide the path of the file that has users' id
				System.out.println("Enter the path of the file that has users' id in csv format");
				String uid_file=inputfile.nextLine();
				br1=new BufferedReader(new FileReader(uid_file));
				break;
			}
			catch(FileNotFoundException fe)
			{
				System.out.println("Please enter a valid file path");
				continue;
			}
		}
		while(true)
		{	
			try
			{
				//Provide the path of the file to output an intermediatry result
				System.out.println("Enter the path of the output file to save the tweets-time interval and their mean in csv format");
				String tweet_time_interval_and_mean_file=inputfile.nextLine();
				pw=new BufferedWriter(new FileWriter(tweet_time_interval_and_mean_file,true));
				break;
			}
			catch(FileNotFoundException fe)
			{
				System.out.println("Please enter a valid file path");
				continue;
			}
		}
		ArrayListMultimap<Integer,Long> userid_and_tweetstime=ArrayListMultimap.create();
		String [] tweet_token, useridtokens;
		String tweets, userid;
	
		//Loop to carrying out to map account creation time for every user with user id using the user.csv file
		while((tweets=br.readLine())!=null)
		{
			tweet_token=tweets.split(",");
			userid_and_tweetstime.put(Integer.parseInt(tweet_token[0].trim()), Long.parseLong(tweet_token[2].replace("L", "").trim()));			
		}
		br.close();
		
		//List creation for sorting of user id list in ascending order
		Set<Integer> user_id_set=userid_and_tweetstime.keySet();
		List<Integer> user_id_list=new ArrayList<Integer>(user_id_set);
		Collections.sort(user_id_list);
		
		while((userid=br1.readLine())!=null)
	 	{
	 		useridtokens=userid.split(",");
	 		int uid=Integer.parseInt(useridtokens[0].trim());
		
			int y=0,mo=0,d=0,h=0,m=0,s=0;
			TreeSet<Long> sorted_time=new TreeSet<Long>();
			
			if(Collections.binarySearch(user_id_list, uid)>=0)
				sorted_time.addAll(userid_and_tweetstime.get(uid));
		
			int len=sorted_time.size()-1;
			Object[] time=sorted_time.toArray();
			String[] tweet_time=new String[time.length];
			
			for(int j=0;j<tweet_time.length;j++)
				tweet_time[j]=time[j].toString();
			
			if(tweet_time.length==0||tweet_time.length==1)
				pw.write(uid+","+"0"+"\n");
			else
			{
				System.out.println(uid+",");
				pw.write(uid+",");
				for(int l=0;l<tweet_time.length-1;l++)
				{							
					long t1=Long.parseLong(tweet_time[l].replace("L", "").trim());
					long t2=Long.parseLong(tweet_time[l+1].replace("L", "").trim());
					DateTime dt1=new DateTime(t1);
					DateTime dt2=new DateTime(t2);
				
					String td=Years.yearsBetween(dt1, dt2).getYears()+":"+Months.monthsBetween(dt1, dt2).getMonths()%12+":"+Days.daysBetween(dt1, dt2).getDays()%30+":"+Hours.hoursBetween(dt1, dt2).getHours()%24+":"+Minutes.minutesBetween(dt1, dt2).getMinutes()%60+":"+Seconds.secondsBetween(dt1, dt2).getSeconds()%60;
	
					pw.write(td+",");
					System.out.println(t1+"===="+td+",");
					y=y+(Years.yearsBetween(dt1, dt2).getYears());
					mo=mo+(Months.monthsBetween(dt1, dt2).getMonths()%12);
					d=d+(Days.daysBetween(dt1, dt2).getDays()%30);
					h=h+(Hours.hoursBetween(dt1, dt2).getHours()%24);
					m=m+(Minutes.minutesBetween(dt1, dt2).getMinutes()%60);
					s=s+(Seconds.secondsBetween(dt1, dt2).getSeconds()%60);
				}
				
				if(y>0)
				{
					pw.write(","+len+","+"0"+"\n");
					System.out.println("0"+"\n");
					System.out.println(uid);
					y=0;	mo=0; d=0;	h=0;	m=0;	s=0;
					continue;
				}
				
				//Converting the time interval into a unified unit of second
				long tweet_time_diff_sum=y*12*30*24*3600+mo*30*24*3600+d*24*3600+h*3600+m*60+s;
				System.out.println(mo+"=="+d+"=="+h+"=="+m+"=="+s+"========"+tweet_time_diff_sum);
				
				//Dividing total second by the number of time interval
				tweet_time_diff_sum=tweet_time_diff_sum/len;					
				
				long amo=tweet_time_diff_sum/(30*24*3600);
				tweet_time_diff_sum=tweet_time_diff_sum%(30*24*3600);
				
				long ad=tweet_time_diff_sum/(24*3600);
				tweet_time_diff_sum=tweet_time_diff_sum%(24*3600);
				
				long ah=tweet_time_diff_sum/3600;
				tweet_time_diff_sum=tweet_time_diff_sum%3600;
				
				long am=tweet_time_diff_sum/60;
				
				long as=tweet_time_diff_sum%60;
				String avgt=amo+":"+ad+":"+ah+":"+am+":"+as;
				pw.write(","+len+","+avgt+"\n");
				System.out.println("average is==="+avgt);
			}
		}
		inputfile.close(); br1.close(); pw.close();
    	}
	
	//Method to compute the time interval standard deviation(TISD) of tweets
	public static void tweetsTimeIntervalStandardDeviation() throws IOException,InterruptedException
    	{
		Scanner inputfile=new Scanner(System.in);
		BufferedReader br;
		BufferedWriter pw;
		
		while(true)
		{
			try
			{
				//Provide the path of the file that has tweets time interval and their mean as  calculated in the previous method
				System.out.println("Enter the path of the file that has users' tweets-time interval and its mean in csv format");
				String tweets_timefile=inputfile.nextLine();
				br=new BufferedReader(new FileReader(tweets_timefile));
				break;
			}
			catch(FileNotFoundException fe)
			{
				System.out.println("Please enter a valid file path");
				continue;
			}
		}
		while(true)
		{
			try
			{
				//Provide the path of the file to save the tweets-time interval standard deviation
				System.out.println("Enter the path of the output file to save the tweets-time interval SD in csv format");
				String tweet_time_interval_sd=inputfile.nextLine();
				pw=new BufferedWriter(new FileWriter(tweet_time_interval_sd));
				break;
			}
			catch(FileNotFoundException fe)
			{
				System.out.println("Please enter a valid file path");
				continue;
			}
		}
		String time_interval, mean_interval_sec;
		String [] time_interval_tokens, intra_time_interval_tokens, mean_tokens;
		long mean_sec_in_long;
		
		//Loop to calculate the Variance of each user
		while((time_interval=br.readLine())!=null)
		{
			time_interval_tokens=time_interval.split(",");
			int no_of_tokens=time_interval_tokens.length;
			
			//Code to convert the mean time interval into seconds
			mean_tokens=time_interval_tokens[time_interval_tokens.length-1].split(":");
			if(time_interval_tokens.length<=2||time_interval_tokens[time_interval_tokens.length-1].equalsIgnoreCase("0"))
			{
				pw.write(time_interval_tokens[0]+","+"0"+"\n");
				//System.out.println(time_interval_tokens[0]);
				continue;
			}
			mean_interval_sec=Integer.toString(Integer.parseInt(mean_tokens[0])*30*24*60*60+Integer.parseInt(mean_tokens[1])*24*60*60+Integer.parseInt(mean_tokens[2])*60*60+Integer.parseInt(mean_tokens[3])*60+Integer.parseInt(mean_tokens[4]));
			mean_sec_in_long=Long.parseLong(mean_interval_sec)*Long.parseLong(mean_interval_sec);
			
			long sum_inter_sec=0L,dif_time;
			String time_interval_sec;
			
			for(int i=1;i<(time_interval_tokens.length-3);i++)
			{
				intra_time_interval_tokens=time_interval_tokens[i].split(":");
				time_interval_sec=Integer.toString(Integer.parseInt(intra_time_interval_tokens[1])*30*24*60*60+Integer.parseInt(intra_time_interval_tokens[2])*24*60*60+Integer.parseInt(intra_time_interval_tokens[3])*60*60+Integer.parseInt(intra_time_interval_tokens[4])*60+Integer.parseInt(intra_time_interval_tokens[5]));
				sum_inter_sec=sum_inter_sec+(Long.parseLong(time_interval_sec)*Long.parseLong(time_interval_sec));
			}
			
			sum_inter_sec=sum_inter_sec/(no_of_tokens-4);
			
			//calculate the variance in seconds
			dif_time=sum_inter_sec-mean_sec_in_long;		
			//calculate the standard deviation by finding the square root of variance
			long sd_in_sec=LongMath.sqrt(dif_time,RoundingMode.FLOOR );
			pw.write(time_interval_tokens[0]+","+sd_in_sec+"\n");
			System.out.println(dif_time+"	"+sd_in_sec+"===Variance of===="+time_interval_tokens[0]+"	is: "+sd_in_sec);		
		}
		inputfile.close(); br.close(); pw.close();
    }
}
