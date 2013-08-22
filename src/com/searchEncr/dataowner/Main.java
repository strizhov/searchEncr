package com.searchEncr.dataowner;

import com.searchEncr.dataowner.adminserver.ServerListener;
import com.searchEncr.dataowner.termweight.CryptoManager;
import com.searchEncr.dataowner.termweight.ProcessingUnit;
import com.searchEncr.util.ConfigManager;
import com.searchEncr.util.ConfigOptions;


public class Main {

	/**
	 * @param argv
	 */
	public static void main(String[] argv)
	{
		if (argv.length == 0)
	    {
			System.out.println("No configuration file provided. Exiting application.");
			System.exit(1);
	    }
	    else
	    {
	    	ConfigManager cm = ConfigManager.getInstance();
	    	cm.readConfiguration(argv[0]);
	    	
	    	String filedirectory = cm.getProperty(ConfigOptions.DATA_OWNER_FILE_DIR);
	    	String cloudhost = cm.getProperty(ConfigOptions.CLOUD_SERVER_HOSTNAME);
	    	String cloudportstring = cm.getProperty(ConfigOptions.CLOUD_SERVER_PORT);
	    	String adminportstring = cm.getProperty(ConfigOptions.DATA_OWNER_PORT);
	    	String keyfilename = cm.getProperty(ConfigOptions.DATA_OWNER_SECRET_KEY);
	    	
	    	int cloudport = Integer.parseInt(cloudportstring);
	    	int adminport = Integer.parseInt(adminportstring);
	    	
	    	if (cloudport < 0 || cloudport > 65535)
	    	{
	    		System.out.println("Incorrect cloud server port number");
	    		System.exit(1);
	    	}
	    	
	    	if (adminport < 0 || adminport > 65535)
	    	{
	    		System.out.println("Incorrect admin port number");
	    		System.exit(1);
	    	}

	    	// launch processing unit
	    	ProcessingUnit pu = new ProcessingUnit();
	    	pu.setDirectory(filedirectory);
	    	pu.setCloudHostname(cloudhost);
	    	pu.setCloudPort(cloudport);
	    	pu.setKeyFileName(keyfilename);
	    	pu.run();
	    	
	    	// launch admin server
	    	ServerListener tm = new ServerListener(adminport);
	    	tm.run();
	    }	
		
	}

}
