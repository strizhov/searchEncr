package com.searchEncr.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class Crypto
{	
	public static final int  		CRYPTO_AES_KEY_SIZE	= 256;
	public static final String 		CRYPTO_AES_ALGORITHM = "AES";
	 
	public static byte[] generateKey() throws NoSuchAlgorithmException 
	{
		SecureRandom sr = new SecureRandom();
		KeyGenerator keyGen = KeyGenerator.getInstance(CRYPTO_AES_ALGORITHM);
		keyGen.init(CRYPTO_AES_KEY_SIZE, sr);
		SecretKey secretKey = keyGen.generateKey();
		byte[] ret = secretKey.getEncoded();
		return ret;
	}

	public static byte[] encryptBytes(byte[] msg, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException 
	{ 		
		SecretKeySpec skeySpec = new SecretKeySpec(key, CRYPTO_AES_ALGORITHM);
	    Cipher cipher = Cipher.getInstance(CRYPTO_AES_ALGORITHM);
	    cipher.init(Cipher.ENCRYPT_MODE, skeySpec);	        	
	    byte[] cipherBytes = cipher.doFinal(msg);	        		        	
	    return cipherBytes;		     
	}
	
	/**
	 * This method converts a set of bytes into a Hexadecimal representation.
	 */
	 public static String convertBytesToHex(byte[] buf) {
		StringBuffer strBuf = new StringBuffer();

		for (int i = 0; i < buf.length; i++) {
			int byteValue = (int) buf[i] & 0xff;
			if (byteValue <= 15) {
				strBuf.append("0");
			}
			strBuf.append(Integer.toString(byteValue, 16));
		}
		return strBuf.toString();
	}

	/**
	 * This method converts a specified hexadecimal String into a set of bytes.
	 */
	public static byte[] convertHexToBytes(String hexString) {
		int size = hexString.length();
		byte[] buf = new byte[size / 2];

		int j = 0;
		for (int i = 0; i < size; i++) {
			String a = hexString.substring(i, i + 2);
			int valA = Integer.parseInt(a, 16);

			i++;

			buf[j] = (byte) valA;
			j++;
		}
		return buf;
	}
	
}
