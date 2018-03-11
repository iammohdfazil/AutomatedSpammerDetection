package tfis_paper_code;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

public class Content_Category {
	//Method to compute the url ratio, unique url ratio, mention ratio, unique url ratio, and hash-tag ratio
	public static void UR_MR_UUR_UMR_HTR() throws IOException,InterruptedException
    	{
		Scanner inputfile = new Scanner(System.in);
		
		//Multimap objects to map tweets, urls, mentions and hashtags for every user id 
		ArrayListMultimap<Integer,String> userid_and_tweets = ArrayListMultimap.create();
		ArrayListMultimap<Integer,String> userid_and_url = ArrayListMultimap.create();
		ArrayListMultimap<Integer,String> userid_and_mention = ArrayListMultimap.create();
		ArrayListMultimap<Integer,String> userid_and_hashtag = ArrayListMultimap.create();
		
		//Multimap set object map users and their unique urls, mentions and hashtags
		SetMultimap<Integer,String> userid_and_uniqueurl = HashMultimap.create();
		SetMultimap<Integer,String> userid_and_uniquemention = HashMultimap.create();
		SetMultimap<Integer,String> userid_and_uniquehashtag = HashMultimap.create();
		BufferedReader br, br1;
		PrintWriter pw;
		while(true)
		{	
			try
			{
				//Provide the path of file that has users' tweets
				System.out.println("Enter the path of the file that has users' tweets in csv format");
				String tweets_file=inputfile.nextLine();
				br=new BufferedReader(new FileReader(tweets_file));
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
				//Provide the path of file that has users' id
				System.out.println("Enter the path of the file that has users ids in csv format");
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
				//Provide the path of file to save the features value
				System.out.println("Enter the path of the file to save the UR, MR, UUR, UMR, HTR fratures in csv format");
				String users_UR_MR_UUR_UMR_HTR_file=inputfile.nextLine();
				pw=new PrintWriter(new FileWriter(users_UR_MR_UUR_UMR_HTR_file));
				break;
		    }
			catch(FileNotFoundException fe)
			{
				System.out.println("Please enter a valid file path");
				continue;
			}
		}
	 	String [] userid_tweet_tokens, useridtokens;
		String user_tweet, userid;
		
		//Loop to map tweets of every user corresponding to their id
	 	while((user_tweet=br.readLine())!=null)
	 	{
			userid_tweet_tokens=user_tweet.split(",");
			userid_and_tweets.put(Integer.parseInt(userid_tweet_tokens[0].trim()), userid_tweet_tokens[1]);
	 	}
		br.close();
		
		//Loop for the calculation of features values for every users
		while((userid=br1.readLine())!=null)
	 	{
	 		useridtokens=userid.split(",");
	 		int uid=Integer.parseInt(useridtokens[0].trim());
			
	 		//Extraction of tweet list for given user id
			List<String> tweetlist=new ArrayList<String>();
			tweetlist.addAll(userid_and_tweets.get(uid));
			
			//Loop for every tweet of tweet set corresponding to a user  
			for(String tweet:tweetlist)
			{
				//regular expression to match the URL
				Pattern p = Pattern.compile(										
			            "\\b(((ht|f)tp(s?)\\:\\/\\/|~\\/|\\/)|www.)" + 
			            "(\\w+:\\w+@)?(([-\\w]+\\.)+(com|org|net|gov" + 
			            "|mil|biz|info|mobi|name|aero|jobs|museum" + 
			            "|travel|[a-z]{2}))(:[\\d]{1,5})?" + 
			            "(((\\/([-\\w~!$+|.,=]|%[a-f\\d]{2})+)+|\\/)+|\\?|#)?" + 
			            "((\\?([-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" + 
			            "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)" + 
			            "(&(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" + 
			            "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)*)*" + 
			            "(#([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)?\\b");
				
				//Matcher class object to match the given regular expression in tweet
				Matcher url_reg=p.matcher(tweet);								
				
				//regular expression to find the mention
				Pattern p1=Pattern.compile("\\@([^\\s]+)");
				Matcher mention_reg=p1.matcher(tweet);
				//regular expression to find the hashtag
				Pattern p2=Pattern.compile("\\#([^\\s]+)");
				Matcher hashtag_reg=p2.matcher(tweet);
				
				//Loop executes equal to the number of URL identified in users' tweets
				while(url_reg.find())
				{
					//Multimap storing url and unique url list and set corresponding to every user
					userid_and_url.put(uid, tweet.substring(url_reg.start(),url_reg.end()));
					userid_and_uniqueurl.put(uid, tweet.substring(url_reg.start(),url_reg.end()));
				}
				
				//Loop executes equal to the number mention identified in users' tweets
				while(mention_reg.find())
				{
					//Multimap storing mention list and unique mention set corresponding to every user
					userid_and_mention.put(uid, tweet.substring(mention_reg.start(),mention_reg.end()));
					userid_and_uniquemention.put(uid, tweet.substring(mention_reg.start(),mention_reg.end()));
				}
				
				//Loop executes equal to the number hash-tag identified in users' tweets
				while(hashtag_reg.find())
				{
					//Multimap storing hashtag list and unique hashtag set corresponding to every user
					userid_and_hashtag.put(uid, tweet.substring(hashtag_reg.start(),hashtag_reg.end()));
					userid_and_uniquehashtag.put(uid, tweet.substring(hashtag_reg.start(),hashtag_reg.end()));
				}	   		
			}
			//Write the results to the file
			pw.write(uid+","+((double)userid_and_url.get(uid).size()/userid_and_tweets.get(uid).size())+","+((double)userid_and_uniqueurl.get(uid).size()/userid_and_url.get(uid).size())+","+((double)userid_and_mention.get(uid).size()/userid_and_tweets.get(uid).size())+","+((double)userid_and_uniquemention.get(uid).size()/userid_and_mention.get(uid).size())+","+((double)userid_and_hashtag.get(uid).size()/userid_and_tweets.get(uid).size())+"\n");
			System.out.println(uid+","+((double)userid_and_url.get(uid).size()/userid_and_tweets.get(uid).size())+","+((double)userid_and_uniqueurl.get(uid).size()/userid_and_url.get(uid).size())+","+((double)userid_and_mention.get(uid).size()/userid_and_tweets.get(uid).size())+","+((double)userid_and_uniquemention.get(uid).size()/userid_and_mention.get(uid).size())+","+((double)userid_and_hashtag.get(uid).size()/userid_and_tweets.get(uid).size())+"\n");
		}
		inputfile.close(); br1.close(); pw.close();
    	}
	
	//Method to compute the tweet and hash-tag similarity
	public static void tweetHashtagSimilarityRatio() throws IOException,InterruptedException
    	{
		Scanner inputfile = new Scanner(System.in);
		BufferedReader br, br1, br2;
		PrintWriter pw, pw1;
		while(true)
		{	
			try
			{
				//Provide the path of file that has users' tweets
				System.out.println("Enter the path of the file that has users' tweets in csv format");
				String tweets_file=inputfile.nextLine();
				br=new BufferedReader(new FileReader(tweets_file));
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
				//Provide the path of file to save the intermediate results
				System.out.println("Enter the path of the file to save hash-tag count for each tweet in csv format");
				String users_hashtag_count_file=inputfile.nextLine();
				pw=new PrintWriter(new FileWriter(users_hashtag_count_file));
				break;
			}
			catch(FileNotFoundException fe)
			{
				System.out.println("Please enter a valid file path");
				continue;
			}
		}
		ArrayListMultimap<Integer, Integer> total_hashtag_mapping=ArrayListMultimap.create();
		ArrayListMultimap<Integer, Integer> matched_hashtag_mapping=ArrayListMultimap.create();
		
		LinkedHashSet<Integer> userid=new LinkedHashSet<Integer>();
		
		String hashtag, tweet, hline, id;
		String [] user_id;
		
		//Loop to find the hash-tags and matching words used in tweets 
		while((tweet=br.readLine())!=null)
		{
			 int total_hashtag=0, matched_hashtag=0;
			 String[] tweet_tokens=tweet.split(",");
			 	
		 	//Regular expression to match the hash-tags
			Pattern p=Pattern.compile("\\#([^\\s]+)");
			Matcher hashtag_reg=p.matcher(tweet_tokens[1]);
			
			//Loop equal to the number of hash-tags matched in tweets
			while(hashtag_reg.find())
    			{
				total_hashtag++;
				String[] tweet_words=tweet_tokens[1].split(" ");
				
				//Regular expression to remove special character 
				hashtag=tweet_tokens[1].substring(hashtag_reg.start(),hashtag_reg.end()).replaceAll("[^\\w\\s]","");		
				
				for(int i=0;i<tweet_words.length;i++)
				{
					//Checks if words contain the hash-tag or not
					if(StringUtils.containsIgnoreCase(tweet_words[i].trim().replaceAll("[^\\w\\s]",""), hashtag))
						matched_hashtag++;
				}
    			}
			// matched_hashtag is minus by 1 because hash-tag will always match itself
			if(total_hashtag!=0)
				pw.write(tweet_tokens[0]+","+tweet_tokens[1]+","+total_hashtag+","+(matched_hashtag-1)+"\n");
			else
				pw.write(tweet_tokens[0]+","+tweet_tokens[1]+","+total_hashtag+","+matched_hashtag+"\n");
		}
		pw.close(); br.close();
		
		while(true)
		{	
			try
			{
				//Provide the path of the file that has the intermediate results computed above
				System.out.println("Enter the path of the file that has users' hashtag count in csv format");
				String hashtag_count_file=inputfile.nextLine();
				br1=new BufferedReader(new FileReader(hashtag_count_file));
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
				System.out.println("Enter the path of the file that has users ids in csv format");
				String uid_file=inputfile.nextLine();
				br2=new BufferedReader(new FileReader(uid_file));
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
				//Provide the path of the file to save the hash-tags and tweets similarity
				System.out.println("Enter the path of the file to save the hashtag and tweets similarity ratio in csv format");
				String htag_tweet_sim_ratio=inputfile.nextLine();
				pw1=new PrintWriter(new FileWriter(htag_tweet_sim_ratio));
				break;
				}
				catch(FileNotFoundException fe)
				{
					System.out.println("Please enter a valid file path");
					continue;
				}
			}
		while((hline=br1.readLine())!=null)
		{	 
			 String[] hashcount_tokens=hline.split(",");
			 
			 userid.add(Integer.parseInt(hashcount_tokens[0].trim()));
			//Multimap for hash-tag count corresponding to every user-id
			 total_hashtag_mapping.put(Integer.parseInt(hashcount_tokens[0].trim()),Integer.parseInt(hashcount_tokens[2].trim()));
			//Multimap for matched hash-tag count corresponding to every user-id
			 matched_hashtag_mapping.put(Integer.parseInt(hashcount_tokens[0].trim()),Integer.parseInt(hashcount_tokens[3].trim()));
		}
		br1.close();
		
		while((id=br2.readLine())!=null)
		{
			user_id=id.split(",");
			userid.add(Integer.parseInt(user_id[0].trim()));
		}
		br2.close();
		
		//loop to calculate the hash-tag and tweet similarity ratio
		for(Integer uid:userid)
		{
			int total_hashtag=0,tm_hashtag=0;
			double matched_htag_ratio=0;
			//list of hash-tag count in every tweets of users
			List<Integer> th_no_list=total_hashtag_mapping.get(uid);		
			//list of matched hash-tag count in every tweets of users
			List<Integer> mh_no_list=matched_hashtag_mapping.get(uid);			
			
			//Loop to calculate the sum of the total and matched hash-tags count of the user 
			for(int l=0;l<th_no_list.size();l++)
			{
				total_hashtag=total_hashtag+th_no_list.get(l).intValue();		
				tm_hashtag=tm_hashtag+mh_no_list.get(l).intValue();
			}
			//Hashtag ratio calculation for user uid
			matched_htag_ratio=((double)tm_hashtag)/total_hashtag;
			System.out.println(uid+"		"+total_hashtag+"	"+tm_hashtag+"	"+matched_htag_ratio);
			pw1.write(uid+","+matched_htag_ratio+"\n");
		}
		inputfile.close(); pw1.close();
    	}
	
	//Function to compute the automated tweets URL ratio and Automated tweets similarity
	public static void AUR_ATS() throws IOException
	{
		Scanner inputfile=new Scanner(System.in);
		BufferedReader br, br1;
		PrintWriter pw, pw1;
		
		//Multimap object creation for mapping tweet, url, mention and hashtag list corresponding to every user id 
		ArrayListMultimap<Integer,String> userid_and_apitweets = ArrayListMultimap.create();
				
		while(true)
		{	
			try
			{
				//Provide the path of the file that has users' id
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
			 	//Provide the path of the file to save the AUR
				System.out.println("Enter the path of the file to save AUR in csv format");
				String AUR_file=inputfile.nextLine();
			 	pw=new PrintWriter(new FileWriter(AUR_file));
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
			 	//Provide the path of the file to save the AUR
			 	System.out.println("Enter the path of the file to save AUR in csv format");
				String ATS_file=inputfile.nextLine();
			 	pw1=new PrintWriter(new FileWriter(ATS_file));
			 	break;
			}
			catch(FileNotFoundException fe)
			{
				System.out.println("Please enter a valid file path");
				continue;
			}
		}
	 	String [] userid_tokens;
		String tweet, user_id;
		
		Set<Integer> uid_set=new LinkedHashSet<Integer>();
		
		//Loop for user id list creation
	 	while((user_id=br.readLine())!=null)
	 	{
	 		userid_tokens=user_id.split(",");
	 		uid_set.add(Integer.parseInt(userid_tokens[0].trim()));	
	 	} 		
	 	br.close();
	 	
	 	pw.write("userid, API url ratio, API tweet similarity"+"\n");
	 	
	 	for(Integer uid:uid_set)
	 	{
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
			
			int noof_apitweets=0, webtweetcount=0, noof_tweets=0, apitweets_withurl=0;
			
			while((tweet=br1.readLine())!=null)
			{		
				String	[]tweet_tokens=tweet.split(",");
	 		
				if(tweet_tokens[1].trim().isEmpty()==false&&(Integer.parseInt(tweet_tokens[0].trim())==uid))
				{
					noof_tweets++;
					if(tweet_tokens[3].trim().equals("web")==true)
						webtweetcount++;
					else
					{
						noof_apitweets++;
						userid_and_apitweets.put(uid, tweet_tokens[1]);
						//regular expression to find the URL
						Pattern p = Pattern.compile(							
					            "\\b(((ht|f)tp(s?)\\:\\/\\/|~\\/|\\/)|www.)" + 
					            "(\\w+:\\w+@)?(([-\\w]+\\.)+(com|org|net|gov" + 
					            "|mil|biz|info|mobi|name|aero|jobs|museum" + 
					            "|travel|[a-z]{2}))(:[\\d]{1,5})?" + 
					            "(((\\/([-\\w~!$+|.,=]|%[a-f\\d]{2})+)+|\\/)+|\\?|#)?" + 
					            "((\\?([-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" + 
					            "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)" + 
					            "(&(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" + 
					            "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)*)*" + 
					            "(#([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)?\\b");
						
						//Matcher object to match the given regular expression in tweet
						Matcher m=p.matcher(tweet_tokens[1]);								
						if(m.find())
							apitweets_withurl++;
					}
				}
			}
			double apiurlratio=((double)apitweets_withurl/noof_apitweets);
			pw.write(uid+","+apiurlratio+"\n");
			System.out.println(uid+"==="+noof_tweets+"===="+noof_apitweets+"====="+apiurlratio+"==="+webtweetcount);
		br1.close();
	 	}
	 	pw.close();
		
	 	System.out.println("userid, automated tweetcount, API tweetsimilarity"+"\n");
	 	
	 	pw1.write("userid, API tweetsimilarity"+"\n");
	 	for(Integer uid: uid_set)
		{
			List<String> apitweetlist=userid_and_apitweets.get(uid);
			Content_Category cobject=new Content_Category();
			
			double sum_tweetsim=0.0;
			int tweetpair=0;
			for(int i=0; i<apitweetlist.size();i++)
			{
				String tweet1=apitweetlist.get(i).replaceAll("u'", "").replaceAll("\\'\\)", "");
				String tweet1token[]=tweet1.split(" ");
				
				for(int k=i+1;k<apitweetlist.size();k++)
				{
					tweetpair++;
					String tweet2=apitweetlist.get(k).replaceAll("u'", "").replaceAll("\\'\\)", "");
					String tweet2token[]=tweet2.split(" ");
					
					HashMap<String,Integer> T1word_and_count=new HashMap<String, Integer>();
					HashMap<String,Integer> T2word_and_count=new HashMap<String, Integer>();
					
					
					for(int l=0; l<tweet1token.length;l++)
					{
						if (!T1word_and_count.containsKey(tweet1token[l].trim())) 
							T1word_and_count.put(tweet1token[l].trim(), 1);
			            else 
			            	T1word_and_count.put(tweet1token[l].trim(), T1word_and_count.get(tweet1token[l].trim())+1);
					}
					for(int l=0; l<tweet2token.length;l++)
					{
						
						if (!T2word_and_count.containsKey(tweet2token[l].trim())) 
							T2word_and_count.put(tweet2token[l].trim(), 1);
			            else 
			            	T2word_and_count.put(tweet2token[l].trim(), T2word_and_count.get(tweet2token[l].trim())+1);
					}
					
					double tweetsim=cobject.cosineSimilarity(T1word_and_count, T2word_and_count);
					//System.out.println(tweet1+"==="+tweet2+"==="+tweetsim);
					sum_tweetsim=sum_tweetsim+tweetsim;
				}
			}
			pw1.write(uid+","+(sum_tweetsim/tweetpair)+"\n");
			System.out.println(uid+"==="+apitweetlist.size()+"==="+tweetpair+"==="+(sum_tweetsim/tweetpair)+"==="+sum_tweetsim);
		}
	 	inputfile.close(); pw1.close();
	}
	
	public Double cosineSimilarity(HashMap<String, Integer> leftVector, HashMap<String, Integer> rightVector) 
	{
         if (leftVector == null || rightVector == null) 
           throw new IllegalArgumentException("Vectors must not be null");
         
        Set<String> T1wordset=leftVector.keySet();
		Set<String> T2wordset=rightVector.keySet();
	
		Set<String> common_words=new TreeSet<String>(T1wordset);
		common_words.retainAll(T2wordset);
	//	System.out.println(T1wordset+"==="+T2wordset+"====="+common_words);
         double dotProduct = dot(leftVector, rightVector, common_words);
         double d1 = 0.0d, d2 = 0.0d;
         
         for (Integer value : leftVector.values())
        	 d1 += Math.pow(value, 2);
         
         for (Integer value : rightVector.values())
        	 d2 += Math.pow(value, 2);
         
         double cosineSimilarity;
         
         if (d1 <= 0.0 || d2 <= 0.0)
        	 cosineSimilarity = 0.0;
         else
        	 cosineSimilarity = (double) (dotProduct / (double) (Math.sqrt(d1) * Math.sqrt(d2)));
         
         return cosineSimilarity;	                  
	}
	
	private double dot(HashMap<String, Integer> leftVector, HashMap<String, Integer> rightVector, Set<String> intersection) 
	{
        long dotProduct=0;
        for (String key : intersection) 
            dotProduct += (leftVector.get(key) * rightVector.get(key));
  
       return dotProduct;
    }
}
