package com.searchEncr.dataowner.termweight;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.searchEncr.exchange.CloudUploadData;
import com.searchEncr.exchange.PacketType;
import com.searchEncr.util.FileIO;
import com.searchEncr.util.UploadProcessor;


public class ProcessingUnit implements Runnable
{
	private static final Logger logger = Logger.getLogger(ProcessingUnit.class.getName());
	
	// Pattern regular expression, ideally should be read from config file
	private static final String regexpression = "[\\w']+";
	
	// ArrayList of all filenode objects
	private ArrayList<FileNode> filenode = new ArrayList<FileNode>();
	
	// directory that include document collection
	private String directory;
	private String cloudserver;
	private int    cloudport;
	private String keyfilename;
	
	public ProcessingUnit()
	{}
	
	public void setDirectory(String directory)
	{
		this.directory = directory;
	}
	
	public void setCloudHostname(String cloudserver)
	{
		this.cloudserver = cloudserver;
	}
	
	public void setCloudPort(int port)
	{
		this.cloudport = port;
	}
	
	public void setKeyFileName(String keyfilename)
	{
		this.keyfilename = keyfilename;
	}
	
	@Override
	public void run() 
	{
		// get indexinfo singleton
		IndexInfo indexinfo = IndexInfo.getInstance();
		
		// read files from directory
		readFiles(directory, indexinfo);
		
		long starttime = System.currentTimeMillis();
		// construct proper index information
		constructIndexForEachFile(indexinfo);
		long stoptime = System.currentTimeMillis();
		
		logger.log(Level.INFO, "Time to construct index: "+(stoptime-starttime));
		
		// for each of document 
/*		for (int i = 0; i < filenode.size(); i++)
		{
			// get filenode object
			FileNode fn = filenode.get(i);
			
			fn.printIndexCell();
		}
*/		
    	// generate secret key
    	CryptoManager cryptomanager = CryptoManager.getInstance();
    	cryptomanager.generateNewKey();
    	cryptomanager.saveSecretKey(keyfilename);
    	
		// upload data to cloud server
		uploadDataToCloudServer();
		
		cleanUpData(indexinfo);

	}//launchProcessingUnit
	
	
	/**
	 * Read files from specified directory
	 * 
	 */
	private void readFiles(String filedir, IndexInfo indexinfo)
	{
		// Start listing directory
		File dir = new File(filedir);

		FileReader fr = null;
		BufferedReader br = null;
		String line = null;

		for (File f : dir.listFiles()) 
		{
			try 
			{
				// create  new file node object
				FileNode fn = new FileNode(f.getAbsolutePath());

				// 	create new FileReader and BufferReader
				fr = new FileReader(f);
				br = new BufferedReader(fr);
			
				// read line by line until the end of the file
				while ((line = br.readLine()) != null) 
				{
					// compose indexinfo object with words	
					extractWordsFromTextLine(line, fn, indexinfo);
				}
			
				br.close();
				fr.close();
			
				// add filenode to the array of all files
				filenode.add(fn);
			
			} catch (FileNotFoundException e) {
				logger.log(Level.WARNING, "Unable to read file: "+f.getName());
				e.printStackTrace();
			} catch (IOException e) {
				logger.log(Level.WARNING, "Unable to read line file: "+f.getName());
				e.printStackTrace();
			}
						
		}//for loop
	}
	
	/**
	 * Extract word from a text line and construct index frame.
	 * @param line text line
	 * @param fn
	 * @param indexinfo IndexInfo object
	 * @return void
	 */
	private void extractWordsFromTextLine(String line, FileNode fn, IndexInfo indexinfo)
	{
		Pattern p = Pattern.compile(regexpression);
	    Matcher m = p.matcher(line);
	    	    
	    // find matching words
	    while ( m.find() == true) 
	    {
	        //System.out.println(input.substring(m.start(), m.end()));
	    	
	    	// add a word to Filenode object
	    	fn.addWordToCollection(m.group().toLowerCase());
	    	
	    	// convert each founded word to lower case and add it to indexinfo
	    	indexinfo.addWordToIndexInfo(m.group().toLowerCase());
	    } 	
	}
	
	/**
	 * For each document put the right word in each index in index cell 
	 * @param indexinfo IndexInfo object
	 * @return null
	 */
	private void constructIndexForEachFile(IndexInfo indexinfo)
	{
		int i = 0;
		int n = 0;
		
		int numOfOccurrences, totaltermsindocument, totalnumberofdocuments, numOfDocumentsWithTerm;
		Number weight;
		
		// get the size of index frame
		int sizeIndexInfo = indexinfo.getIndexInfoSize();
		
		System.out.println("Index size: "+sizeIndexInfo);	
		
		// for each of document, allocate the proper index cell size
		for (i = 0; i < filenode.size(); i++)
		{
			FileNode fn = filenode.get(i);
			fn.allocateIndexCell(sizeIndexInfo);	
		}
		
		// for each of document 
		for (i = 0; i < filenode.size(); i++)
		{
			// 
			n = 0;
			
			// get filenode object
			FileNode fn = filenode.get(i);
			
			// for each word in Indexinfo
			for (String word: indexinfo.getIndexInfo())
			{
/*				logger.log(Level.INFO, "Word: "+c);
				logger.log(Level.INFO, "Term frequency: "+getTermFrequency(fn, c));
				logger.log(Level.INFO, "Total Terms in document: "+getTotalTermsInDocument(fn));
				logger.log(Level.INFO, "Total Documents: "+getTotalDocuments());
				logger.log(Level.INFO, "Number of documents with term: "+getNumOfDocumentsWithTerm(c));
*/				
				numOfOccurrences = getTermFrequency(fn, word);
				if (numOfOccurrences != 0)
				{
				  totaltermsindocument = getTotalTermsInDocument(fn);
				  totalnumberofdocuments = getTotalDocuments();
				  numOfDocumentsWithTerm = getNumOfDocumentsWithTerm(word);

				  // calculate the frequency here
				  TFIDF tfidf = new TFIDF(numOfOccurrences, totaltermsindocument, totalnumberofdocuments, numOfDocumentsWithTerm);
				  weight = tfidf.getValue();
				}
				else // in this case TFIDF value is 0
				{
				  weight = 0;
				}
					
				// add this word with proper index value
				fn.addWordToIndexCell(n, word, weight);
		
				// move index forward
				n++;
			}

		}
	}
	
	/**
	 * Returns the frequency of a given word in a word collection in filenode
	 * @param fn
	 * @param word
	 * @return Number
	 */
	private int getTermFrequency(FileNode fn, String word)
	{
		return fn.getFreqFromCollection(word);
	}
	
	/**
	 * Calculates the total number of words in a given document
	 * @param fn
	 * @return 
	 */
	private int getTotalTermsInDocument(FileNode fn)
	{
		return fn.getTotalNumOfWords();
	}
	
	/**
	 * Returns the total number of documents in a whole collection
	 * @return
	 */
	private int getTotalDocuments()
	{
		return filenode.size();
	}
	
	/**
	 * Calculates how many times a given word appears in ALL documents
	 * @return
	 */
	private int getNumOfDocumentsWithTerm(String word)
	{
		int counter = 0;
		
		// for each of document in a collection 
		for (int i = 0; i < filenode.size(); i++)			
		{
			FileNode fn = filenode.get(i);
			
			if (fn.checkWordInCollection(word) == true)
			{
				counter++;
			}
		}
		return counter;
	}
	
	/**
	 * Establish connection to remote cloud server and send data
	 * 
	 */
	private void uploadDataToCloudServer()
	{
		UploadProcessor uploadprocessor = new UploadProcessor(cloudserver, cloudport);
		uploadprocessor.establishConnection();
		for (int i = 0; i < filenode.size(); i++)
		{
			CloudUploadData obj = getCloudUploadData(filenode.get(i));
			if (obj != null)
			{
				logger.log(Level.WARNING, "File: "+filenode.get(i).getFileName());
				uploadprocessor.writeData(obj);
			}
			else
			{
				logger.log(Level.WARNING, "Unable to create data packet for the cloud");
			}
		}
		uploadprocessor.closeConnection();
	}
	
	/**
	 * Prepare a packet structure for each file and index
	 * @param filenode
	 * @return
	 */
	private CloudUploadData getCloudUploadData(FileNode filenode)
	{		
		try 
		{
			Collection<Number> index = new Vector<Number>();	
			for (int i = 0; i < filenode.getIndexCellSize(); i++)
			{
				IndexCell indexcell = filenode.getIndexCell(i);
				Number weight = indexcell.getWeight();
				index.add(weight);
			}
			
			byte[] filedata = FileIO.readByteArrayFromFile(filenode.getAbsoluteFilePath());
			
			CryptoManager cryptomanager = CryptoManager.getInstance();
			byte[] encrfiledata = cryptomanager.encryptBytes(filedata);
			if (encrfiledata == null)
			{
				logger.log(Level.WARNING, "Unable to encrypt file: "+filenode.getFileName());
				return null;
			}
		
			CloudUploadData msg = new CloudUploadData(PacketType.CLOUD_UPLOAD_DATA, index, filenode.getFileName(), encrfiledata);
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/*
	 * Clear unnesessary data
	 * @param indexinfo
	 */
	private void cleanUpData(IndexInfo indexinfo)
	{
		for (int i = 0; i < filenode.size(); i++)
		{
			// get filenode object
			FileNode fn = filenode.get(i);
			
			fn.clearWords();
		}
		
	}
	
}

