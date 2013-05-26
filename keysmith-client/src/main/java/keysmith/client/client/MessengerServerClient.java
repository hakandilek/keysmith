package keysmith.client.client;

import keysmith.client.KeysmithClientConfiguration;
import keysmith.client.core.HttpResponseException;
import keysmith.common.core.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yammer.dropwizard.config.Environment;

public class MessengerServerClient extends ApiClient {

	private static final Logger log = LoggerFactory
			.getLogger(MessengerServerClient.class);

	private String postMessageURL;

	private String getMessageURL;

	public MessengerServerClient(Environment environment,
			KeysmithClientConfiguration configuration) {
		super(environment, configuration.getJerseyClientConfiguration());
		String server = configuration.getMessengerServer();
		postMessageURL = String.format("%s/messenger/message/%%s", server);
		getMessageURL = String.format("%s/messenger/message/%%s", server);
	}

	public void postMessage(String address, Message message) {
		String url = String.format(postMessageURL, address);
		log.info("postMessage : " + url);
		try {
			utils.post(client, url, String.class, message);
		} catch (HttpResponseException e) {
			e.printStackTrace();
		}
	}

	public Message getMessage(String address) {
		String url = String.format(getMessageURL, address);
		log.info("getMessage : " + url);
		try {
			return utils.get(client, url, Message.class);
		} catch (HttpResponseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
