package com.searchEncr.cloudserver.server;

import com.searchEncr.cloudserver.similarity.SearchManager;
import com.searchEncr.cloudserver.storage.FileManager;
import com.searchEncr.exchange.CloudUploadData;
import com.searchEncr.exchange.MessageType;
import com.searchEncr.exchange.CloudTrapdoorQuery;

public class ResponseProcessor 
{
	private static ResponseProcessor instance = null;
	
	public static ResponseProcessor getInstance()
	{
		if (instance == null)
		{
			instance = new ResponseProcessor();
		}
		return instance;
	}
	
	public void readData(MessageType msg)
	{
		CloudUploadData clouddata = (CloudUploadData) msg;
		FileManager filemanager = FileManager.getInstance();
		filemanager.processEvent(clouddata);		
	}
	
	public void searchData(MessageType msg)
	{
		CloudTrapdoorQuery data = (CloudTrapdoorQuery) msg;
		SearchManager searchmanager = SearchManager.getInstance();
		searchmanager.processTrapdoorQuery(data);
	}
	
	
}
