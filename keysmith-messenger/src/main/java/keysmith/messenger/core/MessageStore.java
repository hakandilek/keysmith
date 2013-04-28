package keysmith.messenger.core;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import keysmith.common.core.Message;

public class MessageStore {

	private final Map<String, Message> messages = new HashMap<String, Message>();

	public Message get(String keyId) {
		return messages.get(keyId);
	}

	public String put(Message message) {
		String keyId = UUID.randomUUID().toString();
		messages.put(keyId, message);
		return keyId;
	}

	public String update(String keyId, Message message) {
		messages.put(keyId, message);
		return keyId;
	}

	public Message remove(String keyId) {
		return messages.remove(keyId);
	}

}
