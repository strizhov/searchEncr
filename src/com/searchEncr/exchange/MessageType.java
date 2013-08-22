package com.searchEncr.exchange;

import java.io.Serializable;

public class MessageType implements Serializable
{
	private static final long serialVersionUID = -9021919257106977879L;
	private int messagetype;

	public MessageType()
	{}
	
	public MessageType(int id)
	{
		this.messagetype = id;
	}
	
	public int getMessageType()
	{
		return messagetype;
	}

}
