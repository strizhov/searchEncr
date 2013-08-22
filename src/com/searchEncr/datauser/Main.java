package com.searchEncr.datauser;

import com.searchEncr.util.ConfigManager;
import com.searchEncr.util.ConfigOptions;

public class Main {
	
	public static void main(String[] argv)
	{
		if (argv.length == 0)
	    {
			System.out.println("No configuration file provided. Exiting application.");
			System.exit(1);
	    }
	    else
	    {
	    	// start config manager
	    	ConfigManager cm = ConfigManager.getInstance();
	    	cm.readConfiguration(argv[0]);
	    	
	    	String cloudhost = cm.getProperty(ConfigOptions.CLOUD_SERVER_HOSTNAME);
	    	String cloudportstring = cm.getProperty(ConfigOptions.CLOUD_SERVER_PORT);
	    	
	    	String dataownerhost = cm.getProperty(ConfigOptions.DATA_OWNER_HOSTNAME);
	    	String dataownerportstring = cm.getProperty(ConfigOptions.DATA_OWNER_PORT);
	    	
	    	int cloudport = Integer.parseInt(cloudportstring);
	    	int dataownerport = Integer.parseInt(dataownerportstring);
	    	
	    	if (cloudport < 0 || cloudport > 65535)
	    	{
	    		System.out.println("Incorrect cloud server port number");
	    		System.exit(1);
	    	}
	    	
	    	if (dataownerport < 0 || dataownerport > 65535)
	    	{
	    		System.out.println("Incorrect admin port number");
	    		System.exit(1);
	    	}

	    	// start trapdoor manager
	    	TrapdoorManager trapdoormanager = new TrapdoorManager();
	    	trapdoormanager.setCloudServerHostname(cloudhost);
	    	trapdoormanager.setCloudServerPort(cloudport);  	
	    	trapdoormanager.setDataOwnerHostname(dataownerhost);
	    	trapdoormanager.setDataOwnerPort(dataownerport);
	    	trapdoormanager.run();
	    }
	}

}
