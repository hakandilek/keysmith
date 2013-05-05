package keysmith.service.core;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MemoryKeyStore implements Keystore {

	private final Map<String, SimpleKey> keys = new HashMap<String, SimpleKey>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see keysmith.server.core.Keystore#get(java.lang.String)
	 */
	public SimpleKey get(String keyId) {
		return keys.get(keyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see keysmith.server.core.Keystore#put(keysmith.server.core.SimpleKey)
	 */
	public String put(SimpleKey key) {
		String keyId = UUID.randomUUID().toString();
		keys.put(keyId, key);
		return keyId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see keysmith.server.core.Keystore#remove(java.lang.String)
	 */
	public SimpleKey removeKey(String keyId) {
		return keys.remove(keyId);
	}

}
