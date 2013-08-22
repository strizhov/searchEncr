package com.searchEncr.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileIO {
	
	public static void saveByteArrayToFile(String filename, byte[] array) throws Exception
	{
		File file = new File(filename);
		FileOutputStream keyfos = new FileOutputStream(file);
		DataOutputStream dos = new DataOutputStream(keyfos);
		dos.write(array);
		dos.close();
		keyfos.close();
	}

	public static byte[] readByteArrayFromFile(String filename) throws Exception 
	{
		File file = new File(filename);
		FileInputStream fis = new FileInputStream(file);
	    DataInputStream dis = new DataInputStream(fis);
	    
	    // Get the size of the file
	    long length = file.length();

	    if (length > Integer.MAX_VALUE) {
	    	throw new IOException("File length is bigger than "+Integer.MAX_VALUE+" MAX value");
	    }

	    // Create the byte array to hold the data
	    byte[] bytes = new byte[(int)length];

	    // Read in the bytes
	    int offset = 0;
	    int numRead = 0;
	    while (offset < bytes.length
	           && (numRead=dis.read(bytes, offset, bytes.length-offset)) >= 0) {
	        offset += numRead;
	    }

	    // Ensure all the bytes have been read in
	    if (offset < bytes.length) {
	        throw new IOException("Could not completely read file "+file.getName());
	    }
	    
	    dis.close();
	    fis.close();
	    return bytes; 
	}
}
