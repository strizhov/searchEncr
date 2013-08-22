package com.searchEncr.dataowner.termweight;

public class IndexCell 
{
	private String word;
	private Number weight;		
	
	public IndexCell(String word, Number weight)
	{
		this.word = word;
		this.weight = weight;
	}
	
	public String getWord()
	{
		return word;
	}
	
	public Number getWeight()
	{
		return weight;
	}
}
