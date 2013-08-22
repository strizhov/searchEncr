package com.searchEncr.datauser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.searchEncr.exchange.CloudTrapdoorQuery;
import com.searchEncr.exchange.PacketType;
import com.searchEncr.util.UploadProcessor;

public class UserInputManager implements Runnable
{
	private static final Logger logger = Logger.getLogger(UserInputManager.class.getName());
			
	private Scanner scanner;
	private String  inputline;
	private ArrayList<String> wordlist; 
	
	private String cloudserver_hostname;
	private int cloudserver_port;
	
	public UserInputManager()
	{
		this.scanner = new Scanner(System.in);
		this.wordlist = new ArrayList<String>();
	}

	public void setCloudServerHostname(String cloudserver_hostname)
	{
		this.cloudserver_hostname = cloudserver_hostname;
	}
	
	public void setCloudServerPort(int cloudserver_port)
	{
		this.cloudserver_port = cloudserver_port;
	}
	
	@Override
	public void run() {
		
		System.out.println("Please enter words to search using whitespace: ");
		
		while(true)
		{
			// clear arraylist
			wordlist.clear();
			
			// read line from input
			inputline = scanner.nextLine();
			
			StringTokenizer st = new StringTokenizer(inputline);
			
			while (st.hasMoreElements()) 
			{
				String word = st.nextToken();
				wordlist.add(word.toLowerCase());
			}			
				
			if (wordlist.isEmpty() == false)
			{
				sendTrapdoorQueryToCloudServer();
			}

			
		} // while loop
		
	}
	
	private void sendTrapdoorQueryToCloudServer()
	{
		TrapdoorIndex index = TrapdoorIndex.getInstance();
		
		Collection<Number> coll = index.getTrapdoorQuery(wordlist);
		
		CloudTrapdoorQuery querymsg = new CloudTrapdoorQuery(PacketType.CLOUD_QUERY_DATA, coll);

		UploadProcessor networkprocessor = new UploadProcessor(cloudserver_hostname, cloudserver_port);
		networkprocessor.establishConnection();
		networkprocessor.writeData(querymsg);
		networkprocessor.closeConnection();
	}
	

}
