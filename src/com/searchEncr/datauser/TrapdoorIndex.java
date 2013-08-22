package com.searchEncr.datauser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrapdoorIndex {
	
	private static final Logger logger = Logger.getLogger(TrapdoorIndex.class.getName());
	
	private Collection<String> trapdoor;
	private static TrapdoorIndex instance = null;
	
	public static TrapdoorIndex getInstance()
	{
		if (instance == null)
		{
			instance = new TrapdoorIndex();
		}
		return instance;
	}
	
	public void setTrapdoorIndex(Collection<String> trapdoor)
	{
		this.trapdoor = trapdoor;
	}
	
	protected Collection<String> getTrapdoorIndex()
	{
		return trapdoor;
	}
	
	/**
	 * Fill up the trapdoor index with TF values of input words from terminal
	 * @param inputwords
	 * @return 
	 */
	public Collection<Number> getTrapdoorQuery(ArrayList<String> inputwords)
	{
		Vector<Number> queryindex = new Vector<Number>(trapdoor.size());
		
		// zero all values in query index
		for (int i = 0; i< queryindex.capacity(); i++)
		{
			queryindex.add(new Double(0));
		}
		
		// insert weight in proper positions
		int n = 0;
		for (String trapword: trapdoor)
		{
			for (String inputword: inputwords)
			{
				if (trapword.equals(inputword) == true)
				{
					// get word frequency in whole collection
					int freq = getWordFrequency(inputword, inputwords);
					
					// get tf value
					double tfvalue = getTfValue(freq, trapdoor.size());
					
					// include tf value in a query vector 
					queryindex.set(n, new Double(tfvalue));
				}
			}
			n++;
		}
		
		System.out.println("Query vector: ");
		for(int i = 0; i< trapdoor.size(); i++)
		{
			System.out.print (" "+queryindex.get(i).doubleValue());
		}
		System.out.println(" end");
		
		return queryindex;
	}
	/**
	 * For performance measurement purpose: insert weights in random positions in query index
	 * This method takes a number of words in a query
	 * @param numberofwords
	 * @return
	 */
	protected Collection<Number> getTrapdoorQuery(int numberofwords)
	{
		Vector<Number> queryindex = new Vector<Number>(trapdoor.size());
		
		// zero all values in query index
		for (int i = 0; i< queryindex.capacity(); i++)
		{
			queryindex.add(new Double(0));
		}
		
		Random randomGenerator = new Random();
		
		for (int idx = 0; idx < numberofwords; idx++)
		{
			// generate random position in the query
			int randomposition = randomGenerator.nextInt(trapdoor.size());
			
			double tfvalue = getTfValue(1, trapdoor.size());
			
			// include tf value in a query vector 
			queryindex.set(randomposition, new Double(tfvalue));
		}
		
		return queryindex;
	}
	
	/**
	 * Generage Term Frequency value for a term in a document
	 * @param numOfOccurrences
	 * @param totalTermsInDocument
	 * @return
	 */
	private double getTfValue(double numOfOccurrences, double totalTermsInDocument)
	{
        double tf =  Math.log10( 1 + (numOfOccurrences / totalTermsInDocument) );
        return tf;
	}
	
	/**
	 * Get frequency of the word in a arraylist
	 * @param testword
	 * @param inputwords
	 * @return
	 */
	private int getWordFrequency(String testword, ArrayList<String> inputwords)
	{
		int n = 0;
		for (String word: inputwords)
		{
			if (word.equals(testword) == true)
			{
				n++;
			}
		}
		return n;
			
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (String s: trapdoor)
		{
			sb.append(s);
			sb.append(" ");
		}
		
		return sb.toString();
			
	}
}
