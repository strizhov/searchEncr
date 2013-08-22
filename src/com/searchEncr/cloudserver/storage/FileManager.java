package com.searchEncr.cloudserver.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.searchEncr.exchange.CloudUploadData;
import com.searchEncr.util.FileIO;

public class FileManager {
	
	private static final Logger logger = Logger.getLogger(FileManager.class.getName());
	
	private static FileManager instance = null;
	
	private ArrayList<FileNode> fileinfo = new ArrayList<FileNode>();
	
	public static FileManager getInstance()
	{
		if (instance == null)
		{
			instance = new FileManager();
		}
		return instance;		
	}

	public void processEvent(CloudUploadData data)
	{
		logger.log(Level.INFO, "Cloud server received file: "+data.getFilename());
		String filename = data.getFilename();
		Collection<Number> index = data.getIndex();
		
		// create new filenode object and add it to filemanager
		FileNode fn = new FileNode(index, filename);
		fileinfo.add(fn);		
		
		// save data to disk
		try {
			FileIO.saveByteArrayToFile(filename, data.getData());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FileNode getFileNode(int index)
	{
		return fileinfo.get(index);
	}
	
	public int getFileNodeSize()
	{
		return fileinfo.size();
	}
}
