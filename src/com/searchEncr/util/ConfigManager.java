package com.searchEncr.util;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigManager 
{
	private static final Logger logger = Logger.getLogger(ConfigManager.class.getName());
	private static ConfigManager instance = null;
	
	private Properties configFile;
	
	private ConfigManager()
	{}
	
	public static ConfigManager getInstance()
	{
		if (instance == null)
		{
			instance = new ConfigManager();
		}
		return instance;
	}
	
	public void readConfiguration(String fullfilepath)
	{
		configFile = new java.util.Properties();
		try 
		{
			FileInputStream is = new FileInputStream(fullfilepath);
			configFile.load(is);
		}catch(Exception e){
			logger.log(Level.SEVERE, "Unable to read "+fullfilepath+" configuration file. Exiting.");
			e.printStackTrace();
			System.exit(1);
		} 
	}
	
	public String getProperty(String key)
	{
		String value = this.configFile.getProperty(key);
		return value;
	}
	
}


