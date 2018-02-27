package tfis_paper_code;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import com.google.common.collect.ArrayListMultimap;

public class Community_Category {
	//Method to find the followers and followings of users' followers
	public static void followersFolllowersAndFollowings() throws IOException,InterruptedException
	{
		//Multimap to map neighbor-id and their corresponding followings
		ArrayListMultimap<Integer,Integer> followerid_and_flng=ArrayListMultimap.create();
		//Multimap to store users and their corresponding followers
		ArrayListMultimap<Integer,Integer> userid_and_fols=ArrayListMultimap.create();
		//Multimap to store followings-id and their corresponding followers 
		ArrayListMultimap<Integer,Integer> friendsid_and_fols=ArrayListMultimap.create();
		//Multimap to store users and their corresponding followings 
		ArrayListMultimap<Integer,Integer> userid_and_flngs=ArrayListMultimap.create();
		
		Scanner inputfile=new Scanner(System.in);
		BufferedReader br, br1, br2;
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
				//Provide the path of file that has the users' followers
				System.out.println("Enter the path of the file that has users' followers in csv format");
				String users_followers_file1=inputfile.nextLine();
				br2=new BufferedReader(new FileReader(users_followers_file1));
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
		String [] followers_tokens, followers_tokens1, users_flng_tokens;
		String followers, followers1, users_flng;
		
		while((followers=br.readLine())!=null)
		{
			
			followers_tokens=followers.split(",");
			if(followers_tokens.length==2)
				continue;
			 for(int i=1;i<followers_tokens.length;i++)
			 {
				 followerid_and_flng.put(Integer.parseInt(followers_tokens[i].trim()),Integer.parseInt(followers_tokens[0].trim()));
				 userid_and_fols.put(Integer.parseInt(followers_tokens[0].trim()),Integer.parseInt(followers_tokens[i].trim()));
			 }
				
		}
		br.close();
		
		while((users_flng=br1.readLine())!=null)
		{
			users_flng_tokens=users_flng.split(",");
			
			if(users_flng_tokens.length==2)
				continue;
			 for(int i=1;i<users_flng_tokens.length;i++)
			 {
				 friendsid_and_fols.put(Integer.parseInt(users_flng_tokens[i].trim()),Integer.parseInt(users_flng_tokens[0].trim()));
				 userid_and_flngs.put(Integer.parseInt(users_flng_tokens[0].trim()),Integer.parseInt(users_flng_tokens[i].trim()));
			 }
		}
		br1.close();
		
		//Set of users ids sorted in ascending order
		Set<Integer> userid_set=userid_and_fols.keySet(); 
		List<Integer> userid_list=new ArrayList<Integer>(userid_set); 
		Collections.sort(userid_list);
		
		//Set of users' followers ids sorted in ascending order 
		Set<Integer> followerid_set=followerid_and_flng.keySet();
		List<Integer> followerid_list=new ArrayList<Integer>(followerid_set);
		Collections.sort(followerid_list);
		
		//Set of users' followings ids sorted in ascending order 
		Set<Integer> friendid_set=friendsid_and_fols.keySet();
		List<Integer> friendsid_list=new ArrayList<Integer>(friendid_set);
		Collections.sort(friendsid_list);
		
		for(Integer friendid:friendsid_list)
		{
			if(Collections.binarySearch(userid_list, friendid)>=0)                  
				friendsid_and_fols.get(friendid).addAll(userid_and_fols.get(friendid));
		}
		
		for(Integer followerid:followerid_list)
		{
			if(Collections.binarySearch(userid_list, followerid)>=0)                  
				followerid_and_flng.get(followerid).addAll(userid_and_flngs.get(followerid));
		}
		
		int u=1, followers_size;
		while((followers1=br2.readLine())!=null)
		{			
			followers_tokens1=followers1.split(",");
			followers_size=followers_tokens1.length-1;
			
			//provide the path of the file to save the connections among the followers and following of every user
			//After execution of this method and find the connection among the followers and followings of every users saved in user file pass it to the conclude algorithm to find the communities for every user
			while(true)
			{	
				try
				{
					System.out.println("Enter the path of the directory to save the connections amonf the connecting nodes of users in csv format");
					String connections_file=inputfile.nextLine();
					pw2=new BufferedWriter(new FileWriter(connections_file+u+++".txt",true));
					break;
				}
				catch(FileNotFoundException fe)
				{
					System.out.println("Please enter a valid file path");
					continue;
				}
			}	
			
			if(followers_size!=0)							
			{
				for(int j=1;j<followers_size;j++)
				{
					pw2.write(Long.parseLong(followers_tokens1[j].trim())+"	"+Long.parseLong(followers_tokens1[0].trim())+"\n");
				}
				for(int i=1;i<followers_size;i++)
				{
					if(!(followers_tokens1[i].trim().isEmpty()))		//checks if neighbor id is empty or not
					{
						int follower_id=Integer.parseInt(followers_tokens1[i].trim());
						
						//Condition for finding followers' following
						//Binary search operation on users' neighbor list to find the neighbor
						if(Collections.binarySearch(followerid_list, follower_id)>=0)	
						{
							for(int k=0;k<followerid_and_flng.get(follower_id).size();k++)
								pw2.write(followerid_and_flng.get(follower_id).get(k)+"	"+follower_id+"\n");												
						}
						
						//Condition to find followers of the followers
						if(Collections.binarySearch(friendsid_list, follower_id)>=0)	//Binary search operation on user's following list to find the neighbors and correspondingly neighbor followers
						{
							for(int k=0;k<friendsid_and_fols.get(follower_id).size();k++)
								pw2.write(friendsid_and_fols.get(follower_id).get(k)+"	"+follower_id+"\n");												
						}
					}
				}
			}pw2.close();
		}
		br2.close();
		
		for(Integer friendsid : friendsid_list)
			pw.write(friendsid+","+friendsid_and_fols.get(friendsid)+"\n");
		pw.close();
		
		for(Integer neighborid : followerid_list)
			pw1.write(neighborid+","+followerid_and_flng.get(neighborid)+"\n");
		inputfile.close(); pw1.close();
	}
	
	//Method to find the reputation of every community users which is later used to find the community reputation
	public static void communityUsersReputation() throws IOException,InterruptedException
	{
		Scanner inputfile=new Scanner(System.in);
		
		//Multimap with neighbor id and corresponding followings and followers 
		ArrayListMultimap<Integer,Integer> followerid_and_flngs=ArrayListMultimap.create();
		ArrayListMultimap<Integer,Integer> friendid_and_fol=ArrayListMultimap.create();
		BufferedReader br, br1;
		BufferedWriter pw;
		while(true)
		{	
			try
			{
				System.out.println("Enter the path of the file that has neighbors' follwoings in csv format");
				String neighbors_followings_file=inputfile.nextLine();
				br=new BufferedReader(new FileReader(neighbors_followings_file));
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
				//Provide the path of the files calculated in the previous method
				System.out.println("Enter the path of the file that has friends followers in csv format");
				String friends_followers_file=inputfile.nextLine();
				br1=new BufferedReader(new FileReader(friends_followers_file));
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
				//provide the path of the file that save the reputation of the users of every communities 
				System.out.println("Enter the path of the file to save the community users reputation in csv format");
				String community_users_rep_file=inputfile.nextLine();
				pw=new BufferedWriter(new FileWriter(community_users_rep_file, true));
				break;
			}
			catch(FileNotFoundException fe)
			{
				System.out.println("Please enter a valid file path");
				continue;
			}
		}	
		String [] friend_fol_tokens, follower_flngs_tokens;
		String friend_fols, follower_flngs;
		
		//Loop to map followings for every users 
		while((follower_flngs=br.readLine())!=null)
		{
			follower_flngs_tokens=follower_flngs.replaceAll("\\[|\\]","").split(",");
			for(int i=1;i<follower_flngs_tokens.length;i++)
				followerid_and_flngs.put(Integer.parseInt(follower_flngs_tokens[0].trim()), Integer.parseInt(follower_flngs_tokens[i].trim()));
			
		}
		br.close();
		
		//Loop to map followers for every users 
		while((friend_fols=br1.readLine())!=null)
		{
			friend_fol_tokens=friend_fols.replaceAll("\\[|\\]","").split(",");
			for(int i=1;i<friend_fol_tokens.length;i++)
				friendid_and_fol.put(Integer.parseInt(friend_fol_tokens[0].trim()), Integer.parseInt(friend_fol_tokens[i].trim()));
		}
		br1.close();
		
		//List has the followers in descending order
		Set<Integer> follower_id_set=followerid_and_flngs.keySet();
		List<Integer> follower_id_list=new ArrayList<Integer>(follower_id_set);
		Collections.sort(follower_id_list);
		
		//List has the followings in descending order
		Set<Integer> friend_id_set=friendid_and_fol.keySet();
		List<Integer> friend_id_list=new ArrayList<Integer>(friend_id_set);
		Collections.sort(friend_id_list);
		
		int follower_flng_size;
		
		//Loop to calculate the account reputation
		for(Integer followerid:follower_id_set)
		{
			double acccount_rep = 0;
			follower_flng_size=followerid_and_flngs.get(followerid).size();
			
			//Searches the community users among users' following and followers list where the key set is the users id in which community users will be searched
			if(Collections.binarySearch(follower_id_list, followerid)>=0&&Collections.binarySearch(friend_id_list, followerid)>=0)
			{
				Set<Integer> intersection=new HashSet<Integer>(friendid_and_fol.get(followerid));
				intersection.retainAll(followerid_and_flngs.get(followerid));
				acccount_rep=(double)(intersection.size())/follower_flng_size;
			}
			pw.write(followerid+","+acccount_rep+"\n");
			System.out.println("user	"+followerid +" reputation is	"+acccount_rep+"	");					
		}
		inputfile.close(); pw.close();
	}
	
	//Method to compute the users' reputation based on their associated communities
	public static void communityBasedUsersReputation() throws IOException, InterruptedException
	{
		//Map to save reputation of users with their ids
		TreeMap<Integer, Double> followerid_and_rep=new TreeMap<Integer,Double>();
		
		Scanner inputfile=new Scanner(System.in);
		BufferedReader br, br1, br2;
		BufferedWriter pw;
		while(true)
		{	
			try
			{
				//provide the path of the file that has the reputation of the users of every communities computed in the previous method 
				System.out.println("Enter the path of the file that has the community users reputation in csv format");
				String community_users_rep_file=inputfile.nextLine();
				br=new BufferedReader(new FileReader(community_users_rep_file));
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
				//provide the path of the file to save the community-based users' reputation
				System.out.println("Enter the path of the file to save community-based users' reputation in csv format");
				String CBR_file=inputfile.nextLine();
				pw=new BufferedWriter(new FileWriter(CBR_file, true));
				break;
			}
			catch(FileNotFoundException fe)
			{
				System.out.println("Please enter a valid file path");
				continue;
			}
		}	
		String [] userid_token, com_users_tokens, rep_tokens;
		String userid, userids, rep;
		
		//Loop to map user's reputation with their id
		while((rep=br.readLine())!=null)
		{
			rep_tokens=rep.split(",");
			followerid_and_rep.put(Integer.parseInt(rep_tokens[0].trim()), Double.parseDouble(rep_tokens[1].trim()));
		}
		br.close();
		
		//It sorts users ids so that binary search can be performed on it.
		Set<Integer> follower_id_set=followerid_and_rep.keySet();
		List<Integer> followerid_list=new ArrayList<Integer>(follower_id_set);
		Collections.sort(followerid_list);
		
		int i=1;
		while((userid=br1.readLine())!=null)	
		{	
			userid_token=userid.split(",");
			LineNumberReader lr;
			while(true)
			{
				try
				{
					//LineNumberReader object finds the line number in a file that corresponds to users who form no community
					System.out.println("Enter the path of the directory that has the text file of the communities of users");
					String communities_files=inputfile.nextLine();
					lr=new LineNumberReader(new FileReader(communities_files+i+".txt"));
					break;
				}
				catch(FileNotFoundException fe)
				{
					System.out.println("Please enter a valid file path");
					continue;
				}
			}	
			lr.skip(Long.MAX_VALUE);
			
			//Condition for the users forming no community
			if(lr.getLineNumber()==1||lr.getLineNumber()==0)
			{	
				System.out.println("file has only one line \n File Name:	"+"outuser"+i+".txt"+"		"+lr.getLineNumber());
				pw.write(userid_token[0]+","+0.0+"\n");
				i++;
				continue;
			}
			else
			{
				while(true)
				{
					try
					{
						System.out.println("Enter the path of the directory that has the text file of the communities of users");
						String communities_files1=inputfile.nextLine();
						br2=new BufferedReader(new FileReader(communities_files1+i+".txt"));
						break;
					}
					catch(FileNotFoundException fe)
					{
						System.out.println("Please enter a valid file path");
						continue;
					}
				}	
				double avg_com_rep=0.0;
				while((userids=br2.readLine())!=null)
				{
					//It skips the last line as it does not contain community
					if(userids.contains("Q"))
						continue;
					else
					{
						double acc_rep=0.0;
						com_users_tokens=userids.split("[\\t,\\,]");
						
						Set<Integer> com_users=new HashSet<Integer>();
						for(int k=0;k<com_users_tokens.length;k++)
							com_users.add(Integer.parseInt(com_users_tokens[k]));
						
						for(Integer com_user:com_users)
						{
							if(Collections.binarySearch(followerid_list, com_user)>=0)
								acc_rep=acc_rep+followerid_and_rep.get(com_user);
						}	
						//Reputation of community avg reputation is added up for a single user
						avg_com_rep=avg_com_rep+((double)acc_rep/(com_users_tokens.length));
						System.out.println("Community avg. reputation is:	"+(double)acc_rep/(com_users_tokens.length));
					}					
				}
				System.out.println("Community based user reputation is:	"+(double)avg_com_rep/(lr.getLineNumber()-1));
				pw.write(userid_token[0]+","+(double)avg_com_rep/(lr.getLineNumber()-1)+"\n");
				br2.close();
			}
			lr.close();
			i++;
		}
		inputfile.close(); br1.close(); pw.close();	
	}
	
	//Method to calculate the clustering coefficient of users averaged over the CC of users of the all the communities
	public static void communityBasedClusteringCoefficient() throws IOException,InterruptedException
	{
		//Multimap for mapping followings and followers corresponding to every user id
		ArrayListMultimap<Integer,Integer> friend_and_fols=ArrayListMultimap.create();
		ArrayListMultimap<Integer,Integer> follower_and_their_followings=ArrayListMultimap.create();
		
		Scanner inputfile=new Scanner(System.in);
		BufferedReader br, br1, br2, br3;
		BufferedWriter pw;
		
		while(true)
		{
			try
			{
		
				//provide the path of the files that have neighbor_flngs and friend_fols without brackets
				System.out.println("Enter the path of the file that has neighbors' follwoings in csv format");
				String neighbors_followings_file=inputfile.nextLine();
				br=new BufferedReader(new FileReader(neighbors_followings_file));
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
				//Provide the path of the files calculated in the previous method
				System.out.println("Enter the path of the file that has friends followers in csv format");
				String friends_followers_file=inputfile.nextLine();
				br1=new BufferedReader(new FileReader(friends_followers_file));
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
				//provide the path of the file that has user-id
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
				//provide the path of the file to save the community-based clustering coefficient
				System.out.println("Enter the path of the file to save the CBCC of users in csv format");
				String CBCC_file=inputfile.nextLine();
				pw=new BufferedWriter(new FileWriter(CBCC_file, true));
				break;
			}
			catch(FileNotFoundException fe)
			{
				System.out.println("Please enter a valid file path");
				continue;
			}
		}	
		String [] userid_token, friend_fol_tokens, follower_flng_tokens, comm_user_tokens;
		String userid, friend_fols, follower_flngs, comm_users;
		
		//Loop to map followings for every follower 
		while((follower_flngs=br.readLine())!=null)
		{
			follower_flng_tokens=follower_flngs.replaceAll("\\[|\\]","").split(",");
			for(int i=1;i<follower_flng_tokens.length;i++)
				follower_and_their_followings.put(Integer.parseInt(follower_flng_tokens[0].trim()), Integer.parseInt(follower_flng_tokens[i].trim()));	
		}
		br.close();
				
		//loop to map followers of every followings 
		while((friend_fols=br1.readLine())!=null)
		{
			friend_fol_tokens=friend_fols.replaceAll("\\[|\\]","").split(",");
			for(int i=1;i<friend_fol_tokens.length;i++)
				friend_and_fols.put(Integer.parseInt(friend_fol_tokens[0].trim()), Integer.parseInt(friend_fol_tokens[i].trim()));
		}
		br1.close();
		
		//Sorts the followers id in ascending order
		Set<Integer> follower_id_set=follower_and_their_followings.keySet();
		List<Integer> follower_id_list=new ArrayList<Integer>(follower_id_set);
		Collections.sort(follower_id_list);
		
		//Sorts the followings id in ascending order
		Set<Integer> friend_id_set=friend_and_fols.keySet();
		List<Integer> friend_id_list=new ArrayList<Integer>(friend_id_set);
		Collections.sort(friend_id_list);
		
		int i=1;
		while((userid=br2.readLine())!=null)
		{
			userid_token=userid.split(",");
			LineNumberReader ln;
			while(true)
			{
				try
				{
					//Path to the directory containing users that has communities 
					System.out.println("Enter the path of the directory that has the text file of the communities of users");
					String communities_files=inputfile.nextLine();
					ln=new LineNumberReader(new FileReader(communities_files+i+".txt"));
					break;
				}
				catch(FileNotFoundException fe)
				{
					System.out.println("Please enter a valid file path");
					continue;
				}
			}	
			
			ln.skip(Long.MAX_VALUE);
			
			//Condition to skip the file belonging to the user whom neighbor do not form any community
			if(ln.getLineNumber()==1||ln.getLineNumber()==0)
			{
				System.out.println("file has only one line \n+ File Name:	"+"outuser"+i+".txt"+"		"+ln.getLineNumber());
				System.out.println(userid_token[0]+"	"+0.0);
				pw.write(userid_token[0]+","+0.0+"\n");
				i++;
				continue;
			}
			else
			{
				while(true)
				{
					try
					{
						System.out.println("Enter the path of the directory that has the text file of the communities of users");
						String communities_files1=inputfile.nextLine();
						br3=new BufferedReader(new FileReader(communities_files1+i+".txt"));
						break;
					}
					catch(FileNotFoundException fe)
					{
						System.out.println("Please enter a valid file path");
						continue;
					}
				}	
				double u_ccoef=0.0;
				
				//Reads the communities line by line as every community is represented in one line
				while((comm_users=br3.readLine())!=null)
				{
					if(comm_users.contains("Q"))
						continue;
					else
					{
						int t_edges=0;
						int edge=0,edge1=0;
						double c_coef=0.0;
						
						//Breaks the communities into their users separated by comma or tab
						comm_user_tokens=comm_users.split("[\\t,\\,]");
						
						Set<Integer> users=new HashSet<Integer>();
						for(int k=0;k<comm_user_tokens.length;k++)
							users.add(Integer.parseInt(comm_user_tokens[k]));
						int c_len=comm_user_tokens.length;
						
						if(users.contains(Integer.parseInt(userid_token[0].trim())))
						{
							for(int j=0;j<comm_user_tokens.length;j++)
							{	
								Integer id=Integer.parseInt(comm_user_tokens[j].trim());
				
								//Searching of the community nodes in  user id list so that if found, can search their edges going from or coming to that node that their followers or followings
								if(Collections.binarySearch(friend_id_list, id)>=0)
								{
									for(int k=j+1;k<comm_user_tokens.length;k++)
										if(friend_and_fols.get(id).contains(Integer.parseInt(comm_user_tokens[k].trim())))
										{
											System.out.println(comm_user_tokens[j]+"	"+Integer.parseInt(comm_user_tokens[k].trim()));
											edge++;
										}
								}
								
								if(Collections.binarySearch(follower_id_list, id)>=0)
								{
									for(int k=j+1;k<comm_user_tokens.length;k++)
										if(follower_and_their_followings.get(id).contains(Integer.parseInt(comm_user_tokens[k].trim())))
										{
											System.out.println(comm_user_tokens[j]+"	"+Integer.parseInt(comm_user_tokens[k].trim()));
											edge1++;
										}
								}
							}
							t_edges=edge+edge1;
							
							//This is for the case one there is no link among the community members of the node if not checked it gives NaN
							if(t_edges==0)
									c_coef=0.0;
							else
								 	c_coef=(double)t_edges/(c_len*(c_len-1)); //Clustering coefficient of a single community of a user
							u_ccoef=u_ccoef+c_coef;
							System.out.println(u_ccoef);
						}	
					}					
				}
				System.out.println(userid_token[0]+"	user coefficient is:	"+(double)u_ccoef/(ln.getLineNumber()-1)+"\n============");
				pw.write(userid_token[0]+","+(double)u_ccoef/(ln.getLineNumber()-1)+"\n");		//Clustering coefficient of a user averaged over all the communities formed by its neighbors
				br3.close();
			}
			ln.close();
			i++;
		}
		inputfile.close(); pw.close(); br2.close();
	}
}
