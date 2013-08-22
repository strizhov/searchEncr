package com.searchEncr.cloudserver.storage;

import java.util.Collection;

public class FileNode {

	private Collection<Number> index;
	private String filename;
	
	public FileNode(Collection<Number> index, String filename)
	{
		this.index = index;
		this.filename = filename;		
	}
	
	public String getFilename()
	{
		return filename;
	}
	
	public Collection<Number> getIndex()
	{
		return index;
	}	
}
