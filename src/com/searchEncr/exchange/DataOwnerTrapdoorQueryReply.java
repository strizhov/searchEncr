package com.searchEncr.exchange;

import java.util.Collection;

public class DataOwnerTrapdoorQueryReply extends MessageType
{
	private static final long serialVersionUID = -5057204452627792965L;
	
	private Collection<String> trapdoorindex;

	public DataOwnerTrapdoorQueryReply(int type, Collection<String> trapdoorindex)
	{
		super(type);
		this.trapdoorindex = trapdoorindex;
	}
	
	public Collection<String> getTrapdoorIndex()
	{
		return trapdoorindex;
	}
}
