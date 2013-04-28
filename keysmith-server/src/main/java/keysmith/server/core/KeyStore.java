package keysmith.server.core;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class KeyStore {

	private final Map<String, String> keys = new HashMap<String, String>();

	public String get(String keyId) {
		return keys.get(keyId);
	}

	public String put(String key) {
		String keyId = UUID.randomUUID().toString();
		keys.put(keyId, key);
		return keyId;
	}

	public String update(String keyId, String key) {
		keys.put(keyId, key);
		return keyId;
	}

	public String remove(String keyId) {
		return keys.remove(keyId);
	}

}
