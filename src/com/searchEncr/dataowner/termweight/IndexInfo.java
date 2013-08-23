package com.searchEncr.dataowner.termweight;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

public class IndexInfo 
{
	private Collection<String> uniqueWords = new TreeSet<String>();
	
	private static IndexInfo instance = null;
	
	private IndexInfo()
	{}
	
	public static IndexInfo getInstance()
	{
		if (instance == null)
		{
			instance = new IndexInfo();
		}
		return instance;
	}
	
	public void addWordToIndexInfo(String word)
	{
		uniqueWords.add(word);
	}
	
	public int getIndexInfoSize()
	{
		return uniqueWords.size();
	}
	
	public Collection<String> getIndexInfo()
	{
		return uniqueWords;
	}
	
	public void printValues()
	{
		System.out.println("Printing values in collection: ");
		
		for (String c: uniqueWords)
		{
			System.out.println(String.format("%s", c));			
		}
	}
	
	public void shuffleIndex()
	{
		List<String> list = new ArrayList<String>(uniqueWords);
		Collections.shuffle(list);
		for(String s: list)
		{
			System.out.println(s); 
		}
	}
}
