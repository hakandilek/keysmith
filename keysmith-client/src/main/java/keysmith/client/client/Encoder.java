package keysmith.client.client;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import com.google.common.io.BaseEncoding;

public class Encoder {

	protected static final BaseEncoding ENCODING = BaseEncoding.base64Url();

	public static String encode(PublicKey key) {
		return ENCODING.encode(key.getEncoded());
	}

	public static String encode(byte[] data) {
		return ENCODING.encode(data);
	}

	public static byte[] decode(String string) {
		return ENCODING.decode(string);
	}
	
	public static PublicKey decodePublicKey(String keyString) {
		try {
			byte[] data = ENCODING.decode(keyString);
			KeyFactory kf = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(data);
			PublicKey key = kf.generatePublic(keySpec);
			return key;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
