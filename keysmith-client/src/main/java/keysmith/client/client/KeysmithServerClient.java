package keysmith.client.client;

import java.security.PublicKey;

import keysmith.client.KeysmithClientConfiguration;
import keysmith.client.core.KeyMaster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.yammer.dropwizard.config.Environment;

public class KeysmithServerClient extends ApiClient {

	private static final Logger log = LoggerFactory
			.getLogger(KeysmithServerClient.class);

	private final String postKeyURL;

	private final String updateKeyURL;

	private final String getKeyURL;

	private final String removeKeyURL;

	private KeyMaster keyMaster;

	public KeysmithServerClient(Environment environment,
			KeysmithClientConfiguration configuration, KeyMaster keyMaster) {
		super(environment, configuration);
		this.keyMaster = keyMaster;
		String server = configuration.getKeysmithServer();
		postKeyURL = String.format("%s/keysmith/publicKey", server);
		updateKeyURL = String.format("%s/keysmith/publicKey/%%s", server);
		getKeyURL = String.format("%s/keysmith/publicKey/%%s", server);
		removeKeyURL = String.format("%s/keysmith/publicKey/%%s", server);
	}

	public String updatePublicKey(String keyId, PublicKey key) {
		String url = String.format(updateKeyURL, keyId);
		log.info("updatePublicKey : " + url);
		String keyString = keyMaster.encodePublicKey(key);
		log.info("key encoded : " + keyString);
		utils.post(client, url, String.class, keyString);
		return keyId;
	}

	public String postPublicKey(PublicKey key) {
		String url = postKeyURL;
		log.info("postPublicKey : " + url);
		String keyString = keyMaster.encodePublicKey(key);
		log.info("key encoded : " + keyString);
		return utils.post(client, url, String.class, keyString);
	}

	public PublicKey getPublicKey(String keyId) {
		String url = String.format(getKeyURL, keyId);
		log.info("getPublicKey : " + url);
		String keyString = utils.get(client, url, String.class);
		PublicKey key = keyMaster.decodePublicKey(keyString);
		return key;
	}

	public PublicKey removePublicKey(String keyId, Client client) {
		String url = String.format(removeKeyURL, keyId);
		log.info("removePublicKey : " + url);
		String keyString = utils.delete(client, url, String.class);
		PublicKey key = keyMaster.decodePublicKey(keyString);
		return key;
	}

}
