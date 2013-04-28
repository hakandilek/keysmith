package keysmith.server.core;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MessageStore {

	private final Map<String, String> messages = new HashMap<String, String>();

	public String get(String keyId) {
		return messages.get(keyId);
	}

	public String put(String key) {
		String keyId = UUID.randomUUID().toString();
		messages.put(keyId, key);
		return keyId;
	}

	public String update(String keyId, String key) {
		messages.put(keyId, key);
		return keyId;
	}

	public String remove(String keyId) {
		return messages.remove(keyId);
	}

}
