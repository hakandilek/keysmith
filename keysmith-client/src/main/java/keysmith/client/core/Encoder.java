package keysmith.client.core;

import com.google.common.io.BaseEncoding;

public class Encoder {

	protected static final BaseEncoding ENCODING = BaseEncoding.base64Url();

	public static String encode(byte[] data) {
		return ENCODING.encode(data);
	}

	public static byte[] decode(String string) {
		return ENCODING.decode(string);
	}
	
}