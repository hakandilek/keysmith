package keysmith.client.core;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

import keysmith.common.core.Message;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CryptographyControllerTest {

	@Mock
	CryptographyHelper mockHelper;

	CryptographyController controller;

	KeyPair keyPair;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		when(mockHelper.getCipher()).thenReturn(cipher);
		controller = new CryptographyController(mockHelper);

		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(512);
		keyPair = keyGen.genKeyPair();
	}

	@After
	public void tearDown() throws Exception {
		mockHelper = null;
		controller = null;
		keyPair = null;
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

}
