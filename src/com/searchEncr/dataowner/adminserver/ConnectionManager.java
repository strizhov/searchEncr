package com.searchEncr.dataowner.adminserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.searchEncr.exchange.MessageType;

public class ConnectionManager implements Runnable
{
	private Socket socket;
    private ObjectInputStream  globalin;
    private MessageType msg;

    ConnectionManager(Socket s)
    {
    	this.socket = s;
    }
    
    public void run()
    {
    	try 
        {
    		this.globalin = new ObjectInputStream(socket.getInputStream());
    		this.msg = (MessageType) globalin.readObject();

    		ResponseProcessor rp = new ResponseProcessor(this.msg, this.socket);
    		rp.start();
        } catch (ClassNotFoundException ex) {
        	Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
        	Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}