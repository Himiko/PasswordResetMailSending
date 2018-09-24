package com.pilot.pswrest.config;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.xml.bind.DatatypeConverter;
public class PBKDF2 {
	/** create hash exception */
	static public class InvalidHashException extends Exception {
		public InvalidHashException(String message) {
			super(message);
		}
		
		public InvalidHashException(String message, Throwable source) {
			super(message, source);
		}
	}
	
    private static final String ALGORITHM = "PBKDF2WithHmacSHA1"; 
    private static final int ITERATIONS = 64000; 
    private static final int SALT_BYTE_SIZE = 24; 
    private static final int HASH_BYTE_SIZE = 18;
    
    /** calculate hash for inputed password */
    public static String hashPassword(String input_password) {
    	byte[] hash = null;
    	char[] password = input_password.toCharArray();
    	byte[] salt = generateRandomSalt();
    	
		hash = calcuateHash(password, salt,ITERATIONS, HASH_BYTE_SIZE);
		
		// format: hash+salt (to save in DB)
		String hashPassword = toBase64(hash) + toBase64(salt);
		
		setRandomSaltValue(toBase64(salt));
    	return hashPassword;
    }
    
    /** calculating hash value for password */
    private static byte[] calcuateHash(char[] password, byte[] salt, int iterations, int bytes) {
    	byte[] hash = null;
		try {
			PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
			SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
			hash = skf.generateSecret(spec).getEncoded();
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		
		return hash;
	}

    /** get random salt value in bytes */
	private static byte[] generateRandomSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[SALT_BYTE_SIZE];
		random.nextBytes(salt);
		return salt;
	}
	
	/** verify whether two passwords are same or not */
	public static boolean verifyPassword(String dbPassword, String dbSalt, char[] loginPassword) throws InvalidHashException {
		byte[] salt = null;
		try {
			salt = fromBase64(dbSalt);
		} catch (IllegalArgumentException e) {
			throw new InvalidHashException("Base64 decoding of salt failed.", e);
		}
		
		byte[] hash = null;
		try {
			// get index for salt from dbPassword
			final Integer index = dbPassword.indexOf(dbSalt.toString());
			// remove salt to get only password (format: hash+salt)
			hash = fromBase64(dbPassword.substring(0, index));
		} catch (IllegalArgumentException e) {
			throw new InvalidHashException(
					"Base64 decoding of pbkdf2 output failed.", e);
		}
		
		byte[] loginHash = calcuateHash(loginPassword, salt, ITERATIONS, hash.length);
		
		return comparePasswords(hash, loginHash);
	}
		
	/**
	* Compares the two byte arrays in length-constant time using XOR. 
	* 
	* @param DBHash   The registered password hash 
	* @param loginHash The login password hash 
	* @return True if both match, false otherwise 
	*/ 
	private static boolean comparePasswords(byte[] DBHash, byte[] loginHash) { 
		int diff = DBHash.length ^ loginHash.length; 
		for (int i = 0; i < DBHash.length && i < loginHash.length; i++) { 
		    diff |= DBHash[i] ^ loginHash[i]; 
		} 
		
		return diff == 0;
	} 
	
	private static byte[] fromBase64(String hex) {
		return DatatypeConverter.parseBase64Binary(hex);
	}
	
	private static String toBase64(byte[] array) {
		return DatatypeConverter.printBase64Binary(array);
	}
	
	private static String randomSaltValue;
	private int iterationIndex;
	
	public static String getRandomSaltValue(){
		return randomSaltValue;
	}
	public static void setRandomSaltValue(String value){
		randomSaltValue = value;
	}
	
	public int getIterationIndex(){
		return iterationIndex;
	}
	public void setIterationIndex(int iterations){
		iterationIndex = iterations;
	}
}
