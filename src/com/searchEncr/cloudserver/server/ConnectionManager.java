package com.searchEncr.cloudserver.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.searchEncr.cloudserver.server.ConnectionManager;
import com.searchEncr.exchange.MessageType;
import com.searchEncr.exchange.PacketType;

public class ConnectionManager implements Runnable
{
	private static final Logger logger = Logger.getLogger(ConnectionManager.class.getName());
	
	private Socket socket = null;
	private ObjectInputStream input = null;
    private Object obj;
    private MessageType msg;
    private ResponseProcessor rp;

    ConnectionManager(Socket socket)
    {
    	this.socket = socket;
    }
    
    public void run()
    {
    	try 
        {
    		int loop = 1;
    		input = new ObjectInputStream(socket.getInputStream());
    		rp = ResponseProcessor.getInstance();
    				
    		while (loop == 1)
    		{    			
    			obj = input.readObject();
    			if (obj instanceof MessageType)
    			{
    				msg = (MessageType) obj;
    			    			
    				switch (msg.getMessageType())
    				{
    					case PacketType.CLOUD_UPLOAD_DATA:
    						rp.readData(msg);
    						break;
    					case PacketType.CLOUD_QUERY_DATA:
    						rp.searchData(msg);
    						break;
    					default: 
    						logger.log(Level.WARNING, "Invalid message type [%d]", msg.getMessageType());
    						break;
    				}
    			}
    			else
    				logger.log(Level.WARNING, "Received unknown packet");
    		}
		} catch (IOException e) {
			logger.log(Level.WARNING, "Unable to receive object stream"); 
		} catch (ClassNotFoundException e) {
        	e.printStackTrace();
        } finally {
        	// close socket
        	try {
        		if (input != null)
        		{
        			input.close();
        		}
        		if (socket != null)
        		{
        			socket.close();
        		}
			} catch (IOException e) {
				logger.log(Level.WARNING, "Unable to flush input stream and close network socket");
			}
        }
    }
}