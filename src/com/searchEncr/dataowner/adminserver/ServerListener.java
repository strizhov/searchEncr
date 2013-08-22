package com.searchEncr.dataowner.adminserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerListener implements Runnable
{

	private int port;
	
	public ServerListener(int port)
	{
		this.port = port;
	}
	
	public void run()
	{	            
		try 
		{
			ServerSocket sock = new ServerSocket(port);
			System.out.println("Server is listening port "+this.port);
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
		} catch (IOException ex) {
			Logger.getLogger(ServerListener.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	
}
