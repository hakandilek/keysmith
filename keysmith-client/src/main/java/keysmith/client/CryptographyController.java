package keysmith.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;

import keysmith.client.client.Encoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CryptographyController {
	private static final Logger log = LoggerFactory
			.getLogger(CryptographyController.class);

	private String algorithm;
	private String cipherTransformation;

	public CryptographyController() {
	}

	public void init(String algorithm, String cipherTransformation) {
		this.algorithm = algorithm;
		this.cipherTransformation = cipherTransformation;
	}

	public KeyPair generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algorithm);
			keyGen.initialize(512);
			KeyPair kp = keyGen.genKeyPair();
			return kp;
		} catch (NoSuchAlgorithmException e) {
			log.error("Algorithm not recognized" + e, e);
		}
		return null;
	}

	public String savePrivateKey(String keyId, PrivateKey key) {
		try {
			// Store Private Key.
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
					key.getEncoded());
			File file = new File("." + keyId + ".privateKey");
			OutputStream os = new FileOutputStream(file);
			os.write(pkcs8EncodedKeySpec.getEncoded());
			os.close();
			return keyId;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public PrivateKey loadPrivateKey(String keyId) {
		try {
			// Read file.
			File file = new File("." + keyId + ".privateKey");
			InputStream is = new FileInputStream(file);
			byte[] encodedPrivateKey = new byte[(int) file.length()];
			is.read(encodedPrivateKey);
			is.close();

			// Convert to key.
			KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
			PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
					encodedPrivateKey);
			PrivateKey key = keyFactory.generatePrivate(privateKeySpec);
			return key;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String encrypt(String message, PublicKey key) {
		try {
			Cipher cipher = Cipher.getInstance(cipherTransformation);
			cipher.init(Cipher.ENCRYPT_MODE, key);

			byte[] stringBytes = message.getBytes("UTF8");

			// encrypt using the cypher
			byte[] raw = cipher.doFinal(stringBytes);

			String result = Encoder.encode(raw);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String decrypt(String encrypted, PrivateKey key) {
		try {
			Cipher cipher = Cipher.getInstance(cipherTransformation);
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
