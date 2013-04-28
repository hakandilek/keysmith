package keysmith.client.commands;

import java.security.PublicKey;

import keysmith.client.KeysmithClientConfiguration;
import keysmith.client.CryptographyHelper;
import keysmith.client.client.KeysmithServerClient;
import keysmith.client.client.MessengerServerClient;
import keysmith.common.core.Message;
import net.sourceforge.argparse4j.inf.Namespace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.cli.EnvironmentCommand;
import com.yammer.dropwizard.config.Environment;

public class SendMessageCommand extends
		EnvironmentCommand<KeysmithClientConfiguration> {

	private static final Logger log = LoggerFactory
			.getLogger(SendMessageCommand.class);

	private CryptographyHelper helper;

	public SendMessageCommand(Service<KeysmithClientConfiguration> service,
			CryptographyHelper helper) {
		super(service, "send", "Encodes the message with the public key "
				+ "from keysmith server and sends it to the messenger server");
		this.helper = helper;
	}

	@Override
	protected void run(Environment environment, Namespace namespace,
			KeysmithClientConfiguration configuration) throws Exception {
		String keyId = configuration.getKeyId();
		String message = configuration.getMessage();
		KeysmithServerClient keysmith = new KeysmithServerClient(environment,
				configuration, helper);
		MessengerServerClient messenger = new MessengerServerClient(
				environment, configuration);

		log.info("getting public key :" + keyId + " ...");
		PublicKey key = keysmith.getPublicKey(keyId);
		log.info("got public key:" + key);

		log.info("encoding message...");
		String encoded = helper.encrypt(message, key);
		log.info("message encoded : " + encoded);

		log.info("posting message to messenger server...");
		messenger.postMessage(keyId, new Message(null, encoded));
		log.info("message posted");
	}

}
