package com.searchEncr.dataowner.adminserver;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;

import com.searchEncr.dataowner.termweight.IndexInfo;
import com.searchEncr.exchange.DataOwnerTrapdoorQueryReply;
import com.searchEncr.exchange.MessageType;
import com.searchEncr.exchange.PacketType;

public class ResponseProcessor 
{
	private MessageType msg;
	private Socket sock;
	
	public ResponseProcessor(MessageType msg, Socket sock)
	{
		this.msg = msg;
		this.sock = sock;
	}
	
	public void start()
	{
		switch (msg.getMessageType())
		{
			case PacketType.TRAPDOOR_QUERY:
				sendTrapdoorQueryReply();
				break;
			default: 
				System.out.println(System.currentTimeMillis()+" Invalid Message Type");
				break;
		}
	}
	
	/**
	 * Constructs reply back to the data user and sends it via socket
	 * 
	 */
	private void sendTrapdoorQueryReply()
	{
        try 
        {
        	IndexInfo indexinfo = IndexInfo.getInstance();
        	
        	Collection<String> wordindex = indexinfo.getIndexInfo();
        	
        	for (String a: wordindex)
        	{
        		System.out.println("Words:" + a);
        	}
        	
        	DataOwnerTrapdoorQueryReply reply = new DataOwnerTrapdoorQueryReply(PacketType.TRAPDOOR_QUERY_REPLY, wordindex);

        	ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());
        	
        	out.writeObject(reply);
			out.flush();
			
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
