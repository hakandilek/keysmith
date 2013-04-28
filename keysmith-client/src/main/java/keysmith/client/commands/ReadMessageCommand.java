package keysmith.client.commands;

import java.security.PrivateKey;

import keysmith.client.CryptographyHelper;
import keysmith.client.KeysmithClientConfiguration;
import keysmith.client.client.MessengerServerClient;
import keysmith.common.core.Message;
import net.sourceforge.argparse4j.inf.Namespace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.cli.EnvironmentCommand;
import com.yammer.dropwizard.config.Environment;

public class ReadMessageCommand extends
		EnvironmentCommand<KeysmithClientConfiguration> {

	private static final Logger log = LoggerFactory
			.getLogger(ReadMessageCommand.class);

	private CryptographyHelper helper;

	public ReadMessageCommand(Service<KeysmithClientConfiguration> service,
			CryptographyHelper helper) {
		super(service, "read", "Reads a message from the server and decodes it with the previously stored private key");
		this.helper = helper;
	}

	@Override
	protected void run(Environment environment, Namespace namespace,
			KeysmithClientConfiguration configuration) throws Exception {
		String keyId = configuration.getKeyId();
		MessengerServerClient messenger = new MessengerServerClient(environment, configuration);
		
		log.info("getting encoded message from messenger server :" + keyId + " ...");
		Message encoded = messenger.getMessage(keyId);
		log.info("got encoded message:" + encoded);
		
		log.info("loading private key...");
		PrivateKey key = helper.loadPrivateKey(keyId);
		log.info("private key loaded");
		
		log.info("decoding message...");
		String message = helper.decrypt(encoded.getData(), key);
		log.info("message decoded:" + message);
	}

}
