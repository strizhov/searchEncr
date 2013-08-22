package com.searchEncr.exchange;

import java.util.Collection;

public class CloudUploadData extends MessageType 
{
	private static final long serialVersionUID = 1632908686887915647L;
	private Collection<Number> index;
	private String filename;
	private byte[] data;
	
	public CloudUploadData()
	{}
	
	public CloudUploadData(int messagetype, Collection<Number> index, String filename, byte[] data)
	{
		super(messagetype);
		this.index = index;
		this.filename = filename;
		this.data = data;
	}
	
	public Collection<Number> getIndex()
	{
		return index;
	}
	
	public String getFilename()
	{
		return filename;
	}
	
	public byte[] getData()
	{
		return data;
	}

}
