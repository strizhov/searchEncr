package com.searchEncr.dataowner.termweight;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.searchEncr.util.Crypto;
import com.searchEncr.util.FileIO;

public class CryptoManager
{
	private static final Logger logger = Logger.getLogger(CryptoManager.class.getName());
	
	private byte[] secretkey = null;
	private static CryptoManager instance = null;
	
	private CryptoManager()
	{}
	
	protected static CryptoManager getInstance()
	{
		if (instance == null)
		{
			instance = new CryptoManager();
		}
		return instance;
	}
	
	protected void generateNewKey()
	{
		try {
			this.secretkey = Crypto.generateKey();
		} catch (NoSuchAlgorithmException e) {
			logger.log(Level.WARNING, "Unable to generate new secret key");
			e.printStackTrace();
		}
	}

	protected void loadSecretKeyFromFile(String keyfilename)
	{
		try {
			this.secretkey = FileIO.readByteArrayFromFile(keyfilename);
		} catch (Exception e) {
			logger.log(Level.WARNING, "Unable to read secret key from file: "+keyfilename);
			e.printStackTrace();
		}
	}
	
	protected void saveSecretKey(String keyfilename)
	{
		try {
			FileIO.saveByteArrayToFile(keyfilename, secretkey);
		} catch (Exception e) {
			logger.log(Level.WARNING, "Unable to save secret key to file: "+keyfilename);
			e.printStackTrace();
		}
	}
	
	protected byte[] encryptBytes(byte[] filebytes)
	{
		byte[] out = null;
		if (this.secretkey == null)
		{
			logger.log(Level.WARNING, "Unable to encrypt file, secret key is not generated");
			return null;
		}
		else
		{
			try {
				out = Crypto.encryptBytes(filebytes, secretkey);				
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			}
		}
		return out;
	}

}
