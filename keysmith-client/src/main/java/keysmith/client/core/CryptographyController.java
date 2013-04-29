package keysmith.client.core;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

import keysmith.common.core.Message;

public class CryptographyController {

	CryptographyHelper helper;

	public CryptographyController(CryptographyHelper helper) {
		this.helper = helper;
	}

	public Message publicEncrypt(String message, PublicKey key) {
		return new Message(null, encrypt(message, key));
	}

	public String publicDecrypt(Message crypted, PrivateKey key) {
		return decrypt(crypted.getData(), key);
	}

	
	String encrypt(String data, PublicKey key) {
		try {
			Cipher cipher = helper.getCipher();
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
			Cipher cipher = helper.getCipher();
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


}
