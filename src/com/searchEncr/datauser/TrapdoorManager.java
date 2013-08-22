package com.searchEncr.datauser;

import java.util.Collection;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.searchEncr.exchange.DataOwnerTrapdoorQuery;
import com.searchEncr.exchange.DataOwnerTrapdoorQueryReply;
import com.searchEncr.exchange.PacketType;
import com.searchEncr.util.UploadProcessor;

public class TrapdoorManager implements Runnable{
	
	private static final Logger logger = Logger.getLogger(TrapdoorManager.class.getName());
	
	private String dataowner_hostname;
	private int dataowner_port;
	private String cloudserver_hostname;
	private int cloudserver_port;
	
	public TrapdoorManager()
	{}
	
	public void setDataOwnerHostname(String dataowner_hostname)
	{
		this.dataowner_hostname = dataowner_hostname;
	}
	
	public void setDataOwnerPort(int dataowner_port)
	{
		this.dataowner_port = dataowner_port;
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
		receiveTrapdoorFromDataOwner();		

		UserInputManager uimanager = new UserInputManager();
		uimanager.setCloudServerHostname(cloudserver_hostname);
		uimanager.setCloudServerPort(cloudserver_port);
		uimanager.run();
		
	}
	
	
	private void receiveTrapdoorFromDataOwner()
	{
		try {			
			DataOwnerTrapdoorQuery trapdoorquerymsg = new DataOwnerTrapdoorQuery(PacketType.TRAPDOOR_QUERY);
			
			UploadProcessor uploader = new UploadProcessor(dataowner_hostname, dataowner_port);
			uploader.establishConnection();
			uploader.writeData(trapdoorquerymsg);
			
			DataOwnerTrapdoorQueryReply trapdoorreply = (DataOwnerTrapdoorQueryReply) uploader.readData();
			uploader.closeConnection();
			
			TrapdoorIndex trapdoorindex = TrapdoorIndex.getInstance();
			trapdoorindex.setTrapdoorIndex(trapdoorreply.getTrapdoorIndex());
			
			logger.log(Level.INFO, "Index: "+trapdoorindex.toString());
		} catch (Exception e) {
			logger.log(Level.WARNING, "Unable to connect to dataowner, hostname: "+dataowner_hostname+" port " + dataowner_port);
			e.printStackTrace();
		}
	}
	

}
