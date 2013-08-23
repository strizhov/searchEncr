package com.searchEncr.dataowner.termweight;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


public class FileNode 
{
	// absolute  file path: /home/bob/documents/bill.txt
	private String filename;
	
	// contain unique set of words from a document and their frequencies
	private HashMap<String, Integer> words;
	
	//contain index cell
	private ArrayList<IndexCell> cell;

	public FileNode(String filename)
	{
		this.filename = filename;
		words = new HashMap<String, Integer>();
	}
		
	public String getAbsoluteFilePath()
	{
		return filename;
	}
	
	/**
	 *  Return  the name of the file without absolute path
	 * @return String
	 */
	public String getFileName()
	{
		File file = new File(filename);
		return file.getName();
	}
	
	// Word collection and its frequency methods
	
	public void addWordToCollection(String word)
	{
		// check if the word exist
		if (words.containsKey(word) == true)
		{
			// word exist, find existing value and increment it
			Integer val = words.get(word);
			val++;
			words.put(word, val);
		}
		else
			words.put(word, new Integer(1));
	}
	
	public int getFreqFromCollection(String word)
	{
		if (words.containsKey(word) == true)
		{
			return words.get(word);
		}
		else
			return 0;		
	}
	
	public boolean checkWordInCollection(String word)
	{
		return words.containsKey(word);
	}
	
	public int getTotalNumOfWords()
	{
		return words.size();
	}
	
	public void clearWords()
	{
		words.clear();
	}
	
	// Index cell methods
	
	public void allocateIndexCell(int size)
	{
		this.cell = new ArrayList<IndexCell>(size);
	}
	
	public IndexCell getIndexCell(int index)
	{
		return cell.get(index);
	}
	
	public void addWordToIndexCell(int index, String word, Number weight)
	{
		IndexCell ic = new IndexCell(word, weight);
		cell.add(index, ic);
	}
	
	public void addWordToIndexCell(int index, String word)
	{
		IndexCell ic = new IndexCell(word, 0);
		cell.add(index, ic);
	}
	
	public void cleanIndexCell()
	{		
		for (int i = 0; i < cell.size(); i++)
		{
			IndexCell ic = new IndexCell(null, null);
			cell.add(i, ic);
		}
	}
	
	public int getIndexCellSize()
	{
		return cell.size();
	}
	
	public void printIndexCell()
	{
		System.out.println("Printing index cell from file "+filename);
		for (int i = 0; i< cell.size(); i++)
		{
			System.out.println("Index: "+i+", word: "+cell.get(i).getWord()+", weight: "+cell.get(i).getWeight());
		}
	}
	



}
