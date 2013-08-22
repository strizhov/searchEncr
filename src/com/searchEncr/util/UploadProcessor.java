package com.searchEncr.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class UploadProcessor 
{
	private String cloudhostname;
	private int    cloudport;
	private Socket cloudsocket;
	private ObjectOutputStream output = null;
	private ObjectInputStream input = null;
	
	public UploadProcessor(String cloudhostname, int cloudport)
	{
		this.cloudhostname = cloudhostname;
		this.cloudport = cloudport;
	}
	
	public void establishConnection()
	{		
		try 
		{
			cloudsocket = Networking.openSocket(cloudhostname, cloudport);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeData(Object obj)
	{
		try {
			if (output == null)
			{
				output = new ObjectOutputStream(cloudsocket.getOutputStream());
			}
			
			output.writeObject(obj);
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Object readData()
	{
		Object obj = null;

		try {
			if (input == null)
			{
				input = new ObjectInputStream(cloudsocket.getInputStream());
			}

			obj = input.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return obj;
	}
	
	public void closeConnection()
	{	
		try {
			if (output != null)
			{
				output.close();
			}
			
			if (input != null)
			{
				input.close();
			}
			
			if (cloudsocket != null)
			{
				cloudsocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}





















