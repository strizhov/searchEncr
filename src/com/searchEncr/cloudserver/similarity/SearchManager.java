package com.searchEncr.cloudserver.similarity;

import static com.searchEncr.cloudserver.similarity.VectorMath.dotp;
import static com.searchEncr.cloudserver.similarity.VectorMath.magnitude;

import java.util.Collection;

import com.searchEncr.cloudserver.storage.FileManager;
import com.searchEncr.cloudserver.storage.FileNode;
import com.searchEncr.exchange.CloudTrapdoorQuery;


public class SearchManager {
	
	private static SearchManager instance = null;
	
	public static SearchManager getInstance()
	{
		if (instance == null)
		{
			instance = new SearchManager();
		}
		return instance;	
	}
	
	public void processTrapdoorQuery(CloudTrapdoorQuery data)	
	{
		// get query index from message packet
		Collection<Number> queryindex = data.getIndex();
		
		// for each filenode, get the stored index and run similarity
		FileManager filemanager = FileManager.getInstance();
		int size = filemanager.getFileNodeSize();
		for(int i = 0; i < size; i++)
		{
			FileNode fn = filemanager.getFileNode(i);
			Collection<Number> storedindex = fn.getIndex();
			
			System.out.println("File name: "+fn.getFilename());
			
			System.out.println("Stored index: ");
			for (Number a: storedindex)
			{
				System.out.print(" "+a.doubleValue());
			}
			System.out.println(";");
			
			
			System.out.println("Query index: ");
			for (Number b: queryindex)
			{
				System.out.print(" "+b.doubleValue());
			}
			System.out.println(";");
			
			
			
			double value = calculateCosineSimilarity(storedindex, queryindex);

			System.out.println("Similarity measure: "+value);
		}
		
	}

	private double calculateCosineSimilarity(Collection<Number> storedindex, Collection<Number> queryindex)
	{
		double dotProduct = dotp(storedindex, queryindex);
		
		double vectorOneMagnitude = magnitude(storedindex);
		double vectorTwoMagnitude = magnitude(queryindex);
			
		return dotProduct / (vectorOneMagnitude * vectorTwoMagnitude);
	}
	
}
