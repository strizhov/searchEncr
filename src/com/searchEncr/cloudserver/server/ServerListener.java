package com.searchEncr.cloudserver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.searchEncr.cloudserver.server.ServerListener;


public class ServerListener implements Runnable
{
	private static final Logger logger = Logger.getLogger(ServerListener.class.getName());
	
	private int port;
	
	public ServerListener(int port)
	{
		this.port = port;
	}
	
	public void run()
	{	  
		ServerSocket sock = null;
		try 
		{
			sock = new ServerSocket(port);
			logger.log(Level.INFO, "Server is listening on port: " + port);
			
	        // for each incoming connection spawn a thread
	        while (!Thread.interrupted()) 
	        {
	        	//System.out.println("Spawn a thread!");
	            new Thread(new ConnectionManager(sock.accept())).start();
	            // or, single-threaded, or a thread pool
            }
	        
	        if (sock != null)
	        {	
	        	sock.close();
	        }
		} catch (IOException e) {
			logger.log(Level.WARNING, "Unable to start socket server on port: " + port);
			e.printStackTrace();
		}
	}
	
	
}