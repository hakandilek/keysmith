package keysmith.client.commands;

import java.security.PublicKey;

import keysmith.client.KeysmithClientConfiguration;
import keysmith.client.CryptographyController;
import keysmith.client.client.KeysmithServerClient;
import keysmith.client.client.MessengerServerClient;
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

	private CryptographyController controller;

	public SendMessageCommand(Service<KeysmithClientConfiguration> service,
			CryptographyController controller) {
		super(service, "send", "Encodes the message with the public key from keysmith server and sends it to the messenger server");
		this.controller = controller;
	}

	@Override
	protected void run(Environment environment, Namespace namespace,
			KeysmithClientConfiguration configuration) throws Exception {
		String keyId = configuration.getKeyId();
		KeysmithServerClient keysmith = new KeysmithServerClient(environment, configuration);
		MessengerServerClient messenger = new MessengerServerClient(environment, configuration);
		
		log.info("getting public key :" + keyId + " ...");
		PublicKey key = keysmith.getPublicKey(keyId);
		log.info("got public key:" + key);
		
		log.info("encoding message...");
		String encoded = controller.encrypt(configuration.getMessage(), key);
		log.info("message encoded : " + encoded);
		
		log.info("posting message to messenger server...");
		messenger.postMessage(keyId, encoded);
		log.info("message posted");
	}

}
