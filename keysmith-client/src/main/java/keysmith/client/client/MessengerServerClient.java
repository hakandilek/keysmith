package keysmith.client.client;

import keysmith.client.KeysmithClientConfiguration;
import keysmith.common.core.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yammer.dropwizard.config.Environment;

public class MessengerServerClient extends ApiClient<KeysmithClientConfiguration> {

	private static final Logger log = LoggerFactory
			.getLogger(MessengerServerClient.class);

	private String postMessageURL;

	private String getMessageURL;

	public MessengerServerClient(Environment environment,
			KeysmithClientConfiguration configuration) {
		super(environment, configuration);
		String server = configuration.getMessengerServer();
		postMessageURL = String.format("%s/messenger/message/%%s", server);
		getMessageURL = String.format("%s/messenger/message/%%s", server);
	}

	public void postMessage(String address, Message message) {
		String url = String.format(postMessageURL, address);
		log.info("postMessage : " + url);
		utils.post(client, url, String.class, message);
	}

	public Message getMessage(String address) {
		String url = String.format(getMessageURL, address);
		log.info("getMessage : " + url);
		return utils.get(client, url, Message.class);
	}

}
