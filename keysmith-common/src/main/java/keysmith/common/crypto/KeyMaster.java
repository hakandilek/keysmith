package keysmith.common.crypto;

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
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class KeyMaster {

	private int keySize;

	private String publicKeyAlgorithm;

	private String publicKeyTransformation;

	private String secretKeyAlgorithm;

	private String secretKeyTransformation;

	private KeyFactory publicKeyFactory;

	private String secretSeed;

	public KeyMaster() {
	}

	public void init(String publicKeyAlgorithm, String publicKeyTransformation,
			String secretKeyAlgorithm, String secretKeyTransformation,
			String secretSeed, Integer keySize) throws NoSuchAlgorithmException {
		this.publicKeyAlgorithm = publicKeyAlgorithm;
		this.publicKeyTransformation = publicKeyTransformation;
		this.secretKeyAlgorithm = secretKeyAlgorithm;
		this.secretKeyTransformation = secretKeyTransformation;
		this.secretSeed = secretSeed;
		this.keySize = keySize;
		this.publicKeyFactory = KeyFactory.getInstance(publicKeyAlgorithm);
	}

	public KeyPair generateKeyPair() {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator
					.getInstance(publicKeyAlgorithm);
			keyGen.initialize(keySize);
			KeyPair kp = keyGen.genKeyPair();
			return kp;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public SecretKey generateSecretKey() {
		byte[] seedBytes = secretSeed.getBytes();
		byte[] bytes = new byte[24];
		new Random().nextBytes(bytes);

		// feed first 8 bytes from the seed
		for (int i = 0; i < seedBytes.length && i < 8; i++) {
			byte b = seedBytes[i];
			bytes[i] = b;
		}

		SecretKeySpec ks = new SecretKeySpec(bytes, secretKeyAlgorithm);
		return ks;
	}

	public String encodePublicKey(PublicKey key) {
		return Encoder.encode(key.getEncoded());
	}

	public PublicKey decodePublicKey(String keyString) {
		try {
			byte[] data = Encoder.decode(keyString);
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(data);
			PublicKey key = publicKeyFactory.generatePublic(keySpec);
			return key;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String encodeSecretKey(SecretKey key) {
		return Encoder.encode(key.getEncoded());
	}

	public SecretKey decodeSecretKey(String keyString) {
		byte[] data = Encoder.decode(keyString);
		return new SecretKeySpec(data, secretKeyAlgorithm);
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
			PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
					encodedPrivateKey);
			PrivateKey key = publicKeyFactory.generatePrivate(privateKeySpec);
			return key;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Cipher getPublicCipher() throws NoSuchAlgorithmException,
			NoSuchPaddingException {
		return Cipher.getInstance(publicKeyTransformation);
	}

	public Cipher getSecretCipher() throws NoSuchAlgorithmException,
			NoSuchPaddingException {
		return Cipher.getInstance(secretKeyTransformation);
	}

}
