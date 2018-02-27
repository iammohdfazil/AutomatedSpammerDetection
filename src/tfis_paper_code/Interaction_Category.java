package tfis_paper_code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import com.google.common.collect.ArrayListMultimap;

public class Interaction_Category 
{
	//Method to calculate the reputation and follower ratio of users
	public static void reputation_and_followerRatio() throws IOException,InterruptedException
    {
		Scanner inputfile=new Scanner(System.in);
		BufferedReader br, br1;
		BufferedWriter pw, pw1;
		while(true)
		{	
			try
			{
				//Provide the path of file that has the users' followers
				System.out.println("Enter the path of the file that has users' followers in csv format");
				String users_followers_file=inputfile.nextLine();
				br=new BufferedReader(new FileReader(users_followers_file));
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
				//Provide the path of file that has the users' followings
				System.out.println("Enter the path of the file that has users' followings in csv format");
				String users_followings_file=inputfile.nextLine();
				br1=new BufferedReader(new FileReader(users_followings_file));
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
				//Provide the path of file to save users' reputation
				System.out.println("Enter the path of the file to save the users' reputation in csv format");
				String reputation_file=inputfile.nextLine();
				pw=new BufferedWriter(new FileWriter(reputation_file, true));
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
				//Provide the path of file to save users' follower ratio
				System.out.println("Enter the path of the file to save the users' follower ratio in csv format");
				String follower_ratio_file=inputfile.nextLine();
				pw1=new BufferedWriter(new FileWriter(follower_ratio_file, true));
				break;
			}
			catch(FileNotFoundException fe)
			{
				System.out.println("Please enter a valid file path");
				continue;
			}
		}
		//Multimap object to map users' id with their followers and following id
		ArrayListMultimap<Integer,Integer> user_and_followers_id = ArrayListMultimap.create();
		ArrayListMultimap<Integer,Integer> user_and_followings_id = ArrayListMultimap.create();
		
		double reputation, follower_ratio;
		String follower, following;
		String[] followerstokens, followingtokens;
		
		//List to hold user id
		List<Integer> userid_list=new ArrayList<Integer>();
		
		while((follower=br.readLine())!=null&&(following=br1.readLine())!=null)
		{
			followerstokens=follower.split(",");
			followingtokens=following.split(",");
			userid_list.add(Integer.parseInt(followerstokens[0].trim()));
			
			for(int i=1;i<followerstokens.length;i++)
			{
				//True if user did not has follower
				if(followerstokens[i].trim().isEmpty())
					continue;
				else
					user_and_followers_id.put(Integer.parseInt(followerstokens[0].trim()), Integer.parseInt(followerstokens[i].trim()));
			}
			
			for(int i=1;i<followingtokens.length;i++)
			{
				//True if user did not has following
				if(followingtokens[i].trim().isEmpty())
					continue;
				else
					user_and_followings_id.put(Integer.parseInt(followingtokens[0].trim()), Integer.parseInt(followingtokens[i].trim()));
			}		
		}
		br.close();	br1.close();
		
		//List objects to hold friends and followers
		List<Integer> friend_list=new ArrayList<Integer>();
		List<Integer> follower_list=new ArrayList<Integer>();
		
		Set<Integer> userid_set=user_and_followers_id.keySet();
		
		//Loop to find followers and followings corresponding to every user their intersection and union
		for(Integer userid:userid_list)
		{
			follower_list=user_and_followers_id.get(userid);
			friend_list=user_and_followings_id.get(userid);
			
			if(userid_set.contains(userid))
			{
				List<Integer> intersection=new ArrayList<Integer>(friend_list);
				List<Integer> union=new ArrayList<Integer>(friend_list);	
				
				//Case when a user is not following to any one
				if(friend_list.size()==0)
				{
					pw.write(userid+","+"0.0"+"\n");
					pw1.write(userid+","+"0.0"+"\n");
				}
				else
				{
					//Finds intersection of followers and following of a user
					intersection.retainAll(follower_list);
					//Finds the union of followers and following of a user
					union.addAll(follower_list);
					//Account reputation calculation
					reputation=(double)intersection.size()/friend_list.size();
					//Follower ratio calculation
					follower_ratio=(double)follower_list.size()/union.size();
					
					pw.write(userid+","+reputation+"\n");
					pw1.write(userid+","+follower_ratio+"\n");
					
					System.out.println(userid+"=="+reputation+"===="+follower_ratio);
				}
			}
			else
			{
				pw.write(userid+","+"0.0"+"\n");
				pw1.write(userid+","+"0.0"+"\n");
			}		
		}	
		inputfile.close(); pw.close();pw1.close();	
    }
	
	//Method to calculate the clustering coefficient
	public static void clus_coefficient() throws IOException,FileNotFoundException,InterruptedException,NumberFormatException
	{
		Scanner inputfile=new Scanner(System.in);
		BufferedReader br, br1;
		BufferedWriter pw;
		
		while(true)
		{	
			try
			{
				//Provide the path of file that has the users' followers
				System.out.println("Enter the path of the file that has users' followers in csv format");
				String users_followers_file=inputfile.nextLine();
				br=new BufferedReader(new FileReader(users_followers_file));
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
				//Provide the path of file that has the users' followings
				System.out.println("Enter the path of the file that has users' followings in csv format");
				String users_followings_file=inputfile.nextLine();
				br1=new BufferedReader(new FileReader(users_followings_file));
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
				System.out.println("Enter the path of the file to save users' clustering coefficient in csv format");
				String clustering_coefficient_file=inputfile.nextLine();
				pw=new BufferedWriter(new FileWriter(clustering_coefficient_file));
				break;
			}
			catch(FileNotFoundException fe)
			{
				System.out.println("Please enter a valid file path");
				continue;
			}
		}
		ArrayListMultimap<Integer,Integer> userid_and_fols = ArrayListMultimap.create();
		ArrayListMultimap<Integer,Integer> userid_and_flng = ArrayListMultimap.create();
		ArrayListMultimap<Integer,Integer> friends_fol = ArrayListMultimap.create();
		ArrayListMultimap<Integer,Integer> neighbors_flng = ArrayListMultimap.create();
		
		List<Integer> neighbor_list=new ArrayList<Integer>();
		List<Integer> neighbor_list1=new ArrayList<Integer>();
		List<Integer> neighbors_fol_set=new ArrayList<Integer>();
		List<Integer> neighbors_flng_set=new ArrayList<Integer>();
		
		List<Integer> userid_list=new ArrayList<Integer>();
		
		String [] neighbor_tokens,friends_tokens;
		String neighbor,friend;
		
		//Loop to mapp the follower and friends of every user and finds friends and followers corresponding to every neighbor and friends  
		while((neighbor=br.readLine())!=null&&(friend=br1.readLine())!=null)
		{
			neighbor_tokens=neighbor.split(",");
			friends_tokens=friend.split(",");
			
			userid_list.add(Integer.parseInt(neighbor_tokens[0].trim()));
			
			if(neighbor_tokens.length>2)
			{
				 for(int i=1;i<neighbor_tokens.length;i++)
				 {
					 neighbors_flng.put(Integer.parseInt(neighbor_tokens[i].trim()),Integer.parseInt(neighbor_tokens[0].trim()));
					 userid_and_fols.put(Integer.parseInt(neighbor_tokens[0].trim()),Integer.parseInt(neighbor_tokens[i].trim()));		 
				 }					 
			}
			
			if(friends_tokens.length>2)
			{
				 for(int i=1;i<friends_tokens.length;i++)
				 {
					 friends_fol.put(Integer.parseInt(friends_tokens[i].trim()),Integer.parseInt(friends_tokens[0].trim()));
					 userid_and_flng.put(Integer.parseInt(friends_tokens[0].trim()),Integer.parseInt(friends_tokens[i].trim()));
				 }					 
			}
		}
		
		br.close();br1.close();
		
		//Sorting of neighbors ids in ascending order
		Set<Integer> neighbors_id_set = neighbors_flng.keySet();
		List<Integer> neighbors_id_list=new ArrayList<Integer>(neighbors_id_set);
		Collections.sort(neighbors_id_list);
		
		//Sorting of friends ids in ascending order
		Set<Integer> friends_id_set = friends_fol.keySet();
		List<Integer> friends_id_list=new ArrayList<Integer>(friends_id_set);
		Collections.sort(friends_id_list);
		
		Set<Integer> user_id_set = userid_and_fols.keySet();
		List<Integer> user_id_list=new ArrayList<Integer>(user_id_set);
		Collections.sort(user_id_list);
		
		//loop to add the followings of neighbors found in ben_mal_followings file
		for(Integer neighbor_id:neighbors_id_list)
		{
			if(Collections.binarySearch(user_id_list, neighbor_id)>=0)   
				neighbors_flng.get(neighbor_id).addAll(userid_and_flng.get(neighbor_id));
		}
		
		//Loop to add the followers of friends found in ben_mal_followers file
		for(Integer friend_id:friends_id_list)
		{
			if(Collections.binarySearch(user_id_list, friend_id)>=0)   
				friends_fol.get(friend_id).addAll(userid_and_fols.get(friend_id));
		}
		
		//Loop to calculate the clustering coefficient for every user
		for(Integer userid:userid_list)
		{
			
			int edges=0;
			
			//Map object to hold the already traversed edges among neighbors of a user
			TreeMap<Integer,Integer> added_edges=new TreeMap<Integer,Integer>();
			
			//Finding neighbor corresponding to user
			neighbor_list=userid_and_fols.get(userid);
			neighbor_list1=userid_and_fols.get(userid);
			
			//If a user has no follower or neighbor
			if(neighbor_list.size()<=1)
			{
				pw.write(userid+","+0.0+"\n");
			}
			else
			{
				//Loop to find the edges among the neighbors of a user
				for(Integer folid:neighbor_list )
				{		
					//Extraction of friends of neighbor of user
					neighbors_flng_set=neighbors_flng.get(folid);
					
						//Loop corresponding to every neighbor's friend
						for(Integer neighbor_flng_id:neighbors_flng_set)
						{	
							//Checks if neighbor's friend is also neighbor of the user and finds the edges among neighbors of the user
							for(Integer fol_id:neighbor_list1)
							{
								//Checks the neighbor's friends ids with other neighbors and whether edge has already been traversed or not?
								if(fol_id.equals(neighbor_flng_id)&&(!((added_edges.containsKey(folid)&&added_edges.containsValue(neighbor_flng_id))||(added_edges.containsKey(neighbor_flng_id)&&added_edges.containsKey(folid)))))
								{
									//Adds the edges into traversed list
									added_edges.put(folid,neighbor_flng_id);
									added_edges.put(neighbor_flng_id, folid);
									edges++;
								}
							}		
						}
						
						//Test whether neighbor's follower list is available or not
						if(Collections.binarySearch(friends_id_list, folid)>=0)
						{
							//Extracts the neighbor's followers
							neighbors_fol_set=friends_fol.get(folid);
							
							//Traverse every neighbor's follower
							for(Integer neighbor_fol_id:neighbors_fol_set)
							{
								for(Integer fol_id:neighbor_list)
								{
									if(fol_id==neighbor_fol_id&&!(added_edges.containsKey(fol_id)&&added_edges.containsValue(neighbor_fol_id)))
									{
										added_edges.put(neighbor_fol_id,folid);
										added_edges.put(folid, neighbor_fol_id);
										edges++;
									}	
								}	
							}
						}
				}
				
				//Compute the clustering coefficient
				double clus_coeff=(double)edges/(neighbor_list.size()*(neighbor_list.size()-1));
				System.out.println(userid+"==="+clus_coeff+"==="+edges);
				pw.write(userid+","+clus_coeff+"\n");
			}
		}
		inputfile.close(); pw.close();
	}
	
	//This method is a part to calculate the follower-based reputation and MFFFR
	//Method to find the followings' followers and followers'following of users
	public static void Friends_Fol_And_Neighbors_Flng_Extraction() throws IOException,InterruptedException
	{
		//Multimap objects to map various values
		ArrayListMultimap<Integer,Integer> friends_fol = ArrayListMultimap.create();
		ArrayListMultimap<Integer,Integer> user_fol_map = ArrayListMultimap.create();
		ArrayListMultimap<Integer,Integer> neighbors_flng = ArrayListMultimap.create();
		ArrayListMultimap<Integer,Integer> users_flng_map = ArrayListMultimap.create();
		
		Scanner inputfile=new Scanner(System.in);
		BufferedReader br, br1, br2, br3;
		BufferedWriter pw, pw1, pw2;
		
		while(true)
		{	
			try
			{
				//Provide the path of file that has the users' followers
				System.out.println("Enter the path of the file that has users' followers in csv format");
				String users_followers_file=inputfile.nextLine();
				br=new BufferedReader(new FileReader(users_followers_file));
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
				//Provide the path of file that has the users' followings
				System.out.println("Enter the path of the file that has users' followings in csv format");
				String users_followings_file=inputfile.nextLine();
				br1=new BufferedReader(new FileReader(users_followings_file));
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
				//Provide the path of file to save friends' followers
				System.out.println("Enter the path of the file to save the friends followers in csv format");
				String friends_followers_file=inputfile.nextLine();
				pw=new BufferedWriter(new FileWriter(friends_followers_file, true));
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
				//Provide the path of file to save followers' followings
				System.out.println("Enter the path of the file to save the followers' followings in csv format");
				String neighbors_following_file=inputfile.nextLine();
				pw1=new BufferedWriter(new FileWriter(neighbors_following_file, true));
				break;
			}
			catch(FileNotFoundException fe)
			{
				System.out.println("Please enter a valid file path");
				continue;
			}
		}
		String [] user_flng_tokens, user_fol_tokens;
		String user_flng, user_fol;

		
		while((user_flng=br.readLine())!=null&&(user_fol=br1.readLine())!=null)
		{			
			user_flng_tokens=user_flng.split(",");
			user_fol_tokens=user_fol.split(",");
			
			//It finds user's following and their followings' followers
			if(user_flng_tokens.length>2)
			{
				 for(int i=1;i<user_flng_tokens.length;i++)
					 friends_fol.put(Integer.parseInt(user_flng_tokens[i].trim()),Integer.parseInt(user_flng_tokens[0].trim()));
				 for(int j=1;j<user_flng_tokens.length;j++)
					 users_flng_map.put(Integer.parseInt(user_flng_tokens[0].trim()),Integer.parseInt(user_flng_tokens[j].trim()));
			}
			
			//It finds user's followers and their followers' followings
			if(user_fol_tokens.length>2)
			{			
				for(int i=1;i<user_fol_tokens.length;i++)
					neighbors_flng.put(Integer.parseInt(user_fol_tokens[i].trim()),Integer.parseInt(user_fol_tokens[0].trim()));
				 for(int j=1;j<user_fol_tokens.length;j++)
					 user_fol_map.put(Integer.parseInt(user_fol_tokens[0].trim()),Integer.parseInt(user_fol_tokens[j].trim()));
			}	
		}
		br.close();br1.close();
		
		//Sorts based on neighbor id or users following id
		Set<Integer> friends_id_set = friends_fol.keySet();
		List<Integer> friends_id_list=new ArrayList<Integer>(friends_id_set);
		Collections.sort(friends_id_list);
		
		Set<Integer> neighbors_id_set = neighbors_flng.keySet();
		List<Integer> neighbors_id_list=new ArrayList<Integer>(neighbors_id_set);
		Collections.sort(neighbors_id_list);
		
		Set<Integer> user_id_set = users_flng_map.keySet();
		List<Integer> user_id_list=new ArrayList<Integer>(user_id_set);
		Collections.sort(user_id_list);
		
		
		for(Integer friend_id:friends_id_list)
		{
			pw.write(friend_id+","+friends_fol.get(friend_id)+"\n");
			if(Collections.binarySearch(user_id_list, friend_id)>=0)   
				friends_fol.get(friend_id).addAll(user_fol_map.get(friend_id));
		}
		pw.close();
		
		for(Integer neighbor_id:neighbors_id_list)
		{
			pw1.write(neighbor_id+","+neighbors_flng.get(neighbor_id)+"\n");
			if(Collections.binarySearch(user_id_list, neighbor_id)>=0)   
				neighbors_flng.get(neighbor_id).addAll(users_flng_map.get(neighbor_id));
		}
		pw1.close();
		while(true)
		{	
			try
			{
				//Provide the path of file calculated above
				System.out.println("Enter the path of the file that has friends' followers in csv format");
				String friends_followers_file_in=inputfile.nextLine();
				br2=new BufferedReader(new FileReader(friends_followers_file_in));
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
				System.out.println("Enter the path of the file that has neighbors' followings in csv format");
				String neighbors_following_file_in=inputfile.nextLine();
				br3=new BufferedReader(new FileReader(neighbors_following_file_in));
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
				//Provide the path of file to save without bracket
				System.out.println("Enter the path of the file to save followers' reputation in csv format");
				String followers_reputation=inputfile.nextLine();
				pw2=new BufferedWriter(new FileWriter(followers_reputation, true));		
				break;
			}
			catch(FileNotFoundException fe)
			{
				System.out.println("Please enter a valid file path");
				continue;
			}
		}
		String friend_fols, neighbors_flng1;
		String [] friends_fol_tokens, neighbor_flng_tokens;
		ArrayListMultimap<Integer,Integer> friends_fol_map=ArrayListMultimap.create();
		
		while((friend_fols=br2.readLine())!=null)
		{
			friends_fol_tokens=friend_fols.replaceAll("\\[|\\]", "").split(",");
			for(int i=1;i<friends_fol_tokens.length;i++)
				friends_fol_map.put(Integer.parseInt(friends_fol_tokens[0].trim()), Integer.parseInt(friends_fol_tokens[i].trim()));
		}
		br2.close();
		
	
		while((neighbors_flng1=br3.readLine())!=null)
		{
			neighbor_flng_tokens=neighbors_flng1.replaceAll("\\[|\\]", "").split(",");
			int neighbor_id=Integer.parseInt(neighbor_flng_tokens[0].trim());
			
			List<Integer> neighbor_flng_set=neighbors_flng.get(neighbor_id);
		
			//Binary search on the followings' followers of users to find neighbor
			if(Collections.binarySearch(friends_id_list, neighbor_id)>=0)	
			{						
				List<Integer> neighbor_fol_set=friends_fol_map.get(neighbor_id);				
				List<Integer> intersection=new ArrayList<Integer>(neighbor_flng_set);
				intersection.retainAll(neighbor_fol_set);
				
				pw.write(neighbor_id+","+friends_fol_map.get(neighbor_id)+","+intersection+","+(double)(intersection.size())/(neighbor_flng_tokens.length-1)+"\n");	
				System.out.println("	"+neighbor_id+"	"+friends_fol_map.get(neighbor_id)+"	"+intersection+"	"+(double)(intersection.size())/(neighbor_flng_tokens.length-1));
			}
			//if neighbor has no followers
			else
			{
				pw.write(neighbor_id+","+0.0+"\n");
			}
		}
		inputfile.close(); pw2.close();	br3.close();
	}
	
	
	public static void followerBasedReputation() throws IOException,InterruptedException
	{
		Scanner inputfile=new Scanner(System.in);
		BufferedReader br, br1;
		BufferedWriter pw;
		
		while(true)
		{	
			try
			{
				//Provide the path of file that has the followers reputation
				System.out.println("Enter the path of the file that has followers' reputation in csv format");
				String followers_reputation_file=inputfile.nextLine();
				br=new BufferedReader(new FileReader(followers_reputation_file));
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
				//Provide the path of file that has the users' id
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
				//Provide the path of file to save the followers-based reputation of users
				System.out.println("Enter the path of the file to save the followers-based users reputation in csv format");
				String followers_based_reputation_file=inputfile.nextLine();
				pw=new BufferedWriter(new FileWriter(followers_based_reputation_file, true));
				break;
			}
			catch(FileNotFoundException fe)
			{
				System.out.println("Please enter a valid file path");
				continue;
			}
		}	
		ArrayListMultimap<Integer,Double> follower_id_and_reputation=ArrayListMultimap.create();
		String [] followers_token, follower_rep_tokens;
		String followers, follower_repu;
		int no_of_tokens, follower_id;
		
		//Loop to find the set of followers id and their reputation
		while((follower_repu=br.readLine())!=null)
		{
			follower_rep_tokens=follower_repu.split(",");
			no_of_tokens=follower_rep_tokens.length-1;
			follower_id_and_reputation.put(Integer.parseInt(follower_rep_tokens[0].trim()), Double.parseDouble(follower_rep_tokens[no_of_tokens].trim()));
		}
		br.close();
		
		//Set stores the neighbor id sorted in ascending order
		Set<Integer> follower_id_set=follower_id_and_reputation.keySet();  
		List<Integer> follower_id_list=new ArrayList<Integer>(follower_id_set);
		Collections.sort(follower_id_list);			
		
		//Loop to calculate the mean neighbor reputation
		while((followers=br1.readLine())!=null)
		{
			followers_token=followers.split(",");
			int followers_count=0;
			
			double follower_reputation=0.0;
			
			//condition for user having no neighbor or follower
			if(followers_token.length<2)
			{
				pw.write(followers_token[0]+","+0.0+"\n");
				continue;
			}
			else
			{				
				for(int i=1;i<followers_token.length;i++)
				{
					//condition to check if cell is empty or not
					if(!(followers_token[i].trim().isEmpty()))
					{
						followers_count++;
						follower_id=Integer.parseInt(followers_token[i].trim());
						if(Collections.binarySearch(follower_id_list, follower_id)>=0)	//Binary search operation on user neighbor list to find the neighbor				
							follower_reputation=follower_reputation+follower_id_and_reputation.get(follower_id).get(0);								
					}		
				}
				System.out.println(followers_token[0]+"	"+followers_count+"	"+follower_reputation+"	"+(follower_reputation/followers_count));
				pw.write(followers_token[0]+","+(follower_reputation/followers_count)+"\n");
			}
		}
		inputfile.close(); pw.close(); br1.close();
		System.out.println("completed");
	}
	
	public static void  meanFollowersFollowingToFollowerRatio()throws IOException,InterruptedException
	{
		Scanner inputfile=new Scanner(System.in);
		BufferedReader br, br1;
		BufferedWriter pw;
		
		while(true)
		{	
			try
			{
				//Provide the path of file that has followers and their followings
				System.out.println("Enter the path of the file that has neighbors' followings in csv format");
				String neighbors_following_file=inputfile.nextLine();
				br=new BufferedReader(new FileReader(neighbors_following_file));
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
				//Provide the path of file that has the users' id	
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
				System.out.println("Enter the path of the file to save the MFFFR value in csv format");
				String MFFFR=inputfile.nextLine();
				pw=new BufferedWriter(new FileWriter(MFFFR, true));
				break;
			}
			catch(FileNotFoundException fe)
			{
				System.out.println("Please enter a valid file path");
				continue;
			}
		}	
		String [] follower_tokens, followers_Following_Tokens;
		String follower, followers_Following;
		
		ArrayListMultimap<Integer,Integer> followerid_and_their_followings=ArrayListMultimap.create();
		
		//Loop to find neighbor id and their following sorted based on neighbor id
		while((followers_Following=br.readLine())!=null)
		{
			followers_Following_Tokens=followers_Following.replaceAll("\\[|\\]", "").split(",");
			for(int i=1; i<followers_Following_Tokens.length; i++)
				followerid_and_their_followings.put(Integer.parseInt(followers_Following_Tokens[0].trim()), Integer.parseInt(followers_Following_Tokens[i].trim()));
		}
		br.close();
		
		//Set stores the users' follower id to sort it in ascending order
		Set<Integer> followerid_set=followerid_and_their_followings.keySet();
		//conversion of neighbor_id_set into list as collection function works only on list
		List<Integer> followerid_list=new ArrayList<Integer>(followerid_set);
		Collections.sort(followerid_list);
		
		//Loop to find neighbor size, mean of neighbors following size and finding 
		int follower_size;
		while((follower=br1.readLine())!=null)
		{
			follower_tokens=follower.split(",");
			follower_size=follower_tokens.length-1;
			
			int follower_flng_size=0, mean_follower_flng_size=0;
			double follower_to_followers_flng_ratio=0.0;
			
			//test whether user has neighbor or not
			if(follower_size!=0)							
			{
				for(int i=1;i<follower_tokens.length;i++)
				{
					if(!(follower_tokens[i].trim().isEmpty()))
					{
						int follower_id=Integer.parseInt(follower_tokens[i].trim());
						//Binary search to find the neighbors from user's followings' followers
						//List to hold neighbors' followers
						if(Collections.binarySearch(followerid_list, follower_id)>=0)
							follower_flng_size=follower_flng_size+followerid_and_their_followings.get(follower_id).size();											
					}					
				}
				mean_follower_flng_size=(int)(follower_flng_size/follower_size);
				follower_to_followers_flng_ratio=(double)((double)mean_follower_flng_size/follower_size);
				
				//write to the output file
				pw.write(follower_tokens[0]+","+follower_to_followers_flng_ratio+"\n");
				System.out.println(follower_tokens[0]+"	"+follower_size+"	"+follower_flng_size+"	"+mean_follower_flng_size+"	"+follower_to_followers_flng_ratio);			
			}
			else
				pw.write(follower_tokens[0]+","+follower_to_followers_flng_ratio+"\n");					
		}
		inputfile.close(); pw.close(); br1.close();
		System.out.println("completed");
	}
}
