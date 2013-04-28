package keysmith.client.client;

import keysmith.client.KeysmithClientConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yammer.dropwizard.config.Environment;

public class MessengerServerClient extends ApiClient {

	private static final Logger log = LoggerFactory
			.getLogger(MessengerServerClient.class);

	private static final String SERVER = "http://localhost:8080";

	private String postMessageURL;

	private String getMessageURL;

	public MessengerServerClient(Environment environment,
			KeysmithClientConfiguration configuration) {
		super(environment, configuration);
		postMessageURL = String.format("%s/messenger/message/%%s", SERVER);
		getMessageURL = String.format("%s/messenger/message/%%s", SERVER);
	}

	public void postMessage(String address, String encoded) {
		String url = String.format(postMessageURL, address);
		log.info("postMessage : " + url);
		utils.post(client, url, Void.class, encoded);
	}

	public String getMessage(String address) {
		String url = String.format(getMessageURL, address);
		log.info("getMessage : " + url);
		return utils.get(client, url, String.class);
	}

}
