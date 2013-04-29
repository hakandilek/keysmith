package keysmith.client.core;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import keysmith.common.core.Message;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class CryptographyControllerTest {

	@Mock
	KeyMaster keyMaster;

	CryptographyController controller;

	KeyPair keyPair;

	SecretKey secretKey;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Cipher pubCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		Cipher secCipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		when(keyMaster.getPublicCipher()).thenReturn(pubCipher);
		when(keyMaster.getSecretCipher()).thenReturn(secCipher);
		controller = new CryptographyController(keyMaster);

		Mockito.doCallRealMethod()
				.when(keyMaster)
				.init(anyString(), anyString(), anyString(), anyString(),
						anyString(), anyInt());
		when(keyMaster.generateKeyPair()).thenCallRealMethod();
		when(keyMaster.generateSecretKey()).thenCallRealMethod();
		when(keyMaster.encodeSecretKey(any(SecretKey.class))).thenCallRealMethod();
		when(keyMaster.decodeSecretKey(any(String.class))).thenCallRealMethod();

		keyMaster.init("RSA", "RSA/ECB/PKCS1Padding", "DESede",
				"DESede/CBC/PKCS5Padding", "test secret", 512);
		keyPair = keyMaster.generateKeyPair();
		secretKey = keyMaster.generateSecretKey();
	}

	@After
	public void tearDown() throws Exception {
		keyMaster = null;
		controller = null;
		keyPair = null;
		secretKey = null;
	}

	@Test
	public void testPublicEncryptDecrypt() {
		PublicKey pubKey = keyPair.getPublic();
		PrivateKey priKey = keyPair.getPrivate();

		Message encrypted = controller.publicEncrypt("test", pubKey);
		assertNotNull(encrypted);
		assertNotNull(encrypted.getData());
		assertNull(encrypted.getKey());

		String message = controller.publicDecrypt(encrypted, priKey);
		assertEquals("test", message);
	}

	@Test
	public void testSymmetricEncryptDecrypt() {

		Message encrypted = controller.symmetricEncrypt("test", secretKey);
		assertNotNull(encrypted);
		assertNotNull(encrypted.getData());
		assertNotNull(encrypted.getKey());

		String message = controller.symmetricDecrypt(encrypted);
		assertEquals("test", message);
	}

	@Test
	public void testHybridEncryptDecrypt() {
		PublicKey pubKey = keyPair.getPublic();
		PrivateKey priKey = keyPair.getPrivate();

		Message encrypted = controller.hybridEncrypt("test", pubKey);
		assertNotNull(encrypted);
		assertNotNull(encrypted.getData());
		assertNotNull(encrypted.getKey());

		String message = controller.hybridDecrypt(encrypted, priKey);
		assertEquals("test", message);
	}

}
