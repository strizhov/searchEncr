package com.searchEncr.cloudserver;

import com.searchEncr.cloudserver.server.ServerListener;
import com.searchEncr.util.ConfigManager;
import com.searchEncr.util.ConfigOptions;

public class Main 
{
	/**
	 * @param args
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
	    	// start config manager
	    	ConfigManager cm = ConfigManager.getInstance();
	    	cm.readConfiguration(argv[0]);
	    	
	    	// start server
	    	String portstring = cm.getProperty(ConfigOptions.CLOUD_SERVER_PORT);
	    	int port = Integer.parseInt(portstring);
	    	
	    	// check port range
	    	if (port < 0 || port > 65535)
	    	{
	    		System.out.println("Incorrect port number");
	    		System.exit(1);
	    	}
	    	
	    	ServerListener serverlistener = new ServerListener(port);
	    	serverlistener.run();
	    }

	}
}
