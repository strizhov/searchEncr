package com.searchEncr.exchange;

import java.util.Collection;

public class CloudTrapdoorQuery extends MessageType
{
	private static final long serialVersionUID = -8928276701698436785L;
	private Collection<Number> index;	

	public CloudTrapdoorQuery(int messagetype, Collection<Number> index)
	{
		super(messagetype);
		this.index = index;
	}
	
	public Collection<Number> getIndex()
	{
		return index;
	}
	
}
