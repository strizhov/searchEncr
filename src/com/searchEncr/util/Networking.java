package com.searchEncr.util;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class Networking {
	
	private static int socketTimeout = 5; // socket connection timeout
	
    /*
     * Opens a socket connection to a host
     * Input: server name, port number
     * Output: socket
     * Author: Mikhail Strizhov	
     */
    public static Socket openSocket(String server, int port) throws Exception 
    {
    	InetAddress inteAddress = InetAddress.getByName(server);
    	SocketAddress socketAddress = new InetSocketAddress(inteAddress, port);

        // create a socket
        Socket socket = new Socket();

        int timeoutInMs = socketTimeout * 1000;
        socket.connect(socketAddress, timeoutInMs);
            
        return socket;
    }
    
    /*
     * Return machine hostname
     */
    public static  String getHostName() throws Exception
    {
    	InetAddress addr = InetAddress.getLocalHost();
    	return addr.getHostName();
    }
}
