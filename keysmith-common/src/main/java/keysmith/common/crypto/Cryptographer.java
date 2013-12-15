package keysmith.common.crypto;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import keysmith.common.core.Message;

public class Cryptographer {

	protected KeyMaster keyMaster;

	public Cryptographer(KeyMaster keyMaster) {
		this.keyMaster = keyMaster;
	}

	public Message publicEncrypt(String message, PublicKey key) {
		return new Message(null, encrypt(message, key));
	}

	public String publicDecrypt(Message crypted, PrivateKey key) {
		return decrypt(crypted.getData(), key);
	}

	public Message symmetricEncrypt(String message, SecretKey key) {
		String dataString = encrypt(message, key);
		String keyString = keyMaster.encodeSecretKey(key);
		return new Message(keyString, dataString);
	}

	public String symmetricDecrypt(Message crypted) {
		SecretKey key = keyMaster.decodeSecretKey(crypted.getKey());
		return decrypt(crypted.getData(), key);
	}

	public Message hybridEncrypt(String message, PublicKey key) {
		SecretKey secretKey = keyMaster.generateSecretKey();
		String encryptedMsg = encrypt(message, secretKey);
		String encryptedKey = encrypt(secretKey, key);
		return new Message(encryptedKey, encryptedMsg);
	}

	public String hybridDecrypt(Message crypted, PrivateKey key) {
		String secretKeyData = decrypt(crypted.getKey(), key);
		SecretKey secretKey = keyMaster.decodeSecretKey(secretKeyData);
		String message = decrypt(crypted.getData(), secretKey);
		return message;
	}


	/**
	 * encrypts the given secret key with the given public key
	 */
	String encrypt(SecretKey secretKey, PublicKey key) {
		String data = keyMaster.encodeSecretKey(secretKey);
		return encrypt(data, key);
	}

	String encrypt(String data, PublicKey key) {
		try {
			Cipher cipher = keyMaster.getPublicCipher();
			cipher.init(Cipher.ENCRYPT_MODE, key);

			byte[] stringBytes = data.getBytes("UTF8");

			// encrypt using the cypher
			byte[] raw = cipher.doFinal(stringBytes);

			String result = Encoder.encode(raw);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	String decrypt(String encrypted, PrivateKey key) {
		try {
			Cipher cipher = keyMaster.getPublicCipher();
			cipher.init(Cipher.DECRYPT_MODE, key);

			byte[] raw = Encoder.decode(encrypted);

			// decode the message
			byte[] stringBytes = cipher.doFinal(raw);

			// converts the decoded message to a String
			String clear = new String(stringBytes, "UTF8");
			return clear;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	String encrypt(String data, SecretKey key) {
    	try {
			final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
			final Cipher cipher = keyMaster.getSecretCipher();
			cipher.init(Cipher.ENCRYPT_MODE, key, iv);

			byte[] stringBytes = data.getBytes("UTF8");

			byte[] raw = cipher.doFinal(stringBytes);
			
			String result = Encoder.encode(raw);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	String decrypt(String encrypted, SecretKey key) {
		try {
	    	final IvParameterSpec iv = new IvParameterSpec(new byte[8]);
			Cipher cipher = keyMaster.getSecretCipher();
			cipher.init(Cipher.DECRYPT_MODE, key, iv);

			byte[] raw = Encoder.decode(encrypted);

			// decode the message
			byte[] stringBytes = cipher.doFinal(raw);

			// converts the decoded message to a String
			String clear = new String(stringBytes, "UTF8");
			return clear;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
