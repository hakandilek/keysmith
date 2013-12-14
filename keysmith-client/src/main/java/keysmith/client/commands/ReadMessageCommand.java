package keysmith.client.commands;

import java.io.File;
import java.nio.charset.Charset;
import java.security.PrivateKey;

import keysmith.client.KeysmithClientConfiguration;
import keysmith.client.client.MessengerServerClient;
import keysmith.common.core.Message;
import keysmith.common.crypto.Cryptographer;
import keysmith.common.crypto.KeyMaster;
import net.sourceforge.argparse4j.inf.Namespace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.Files;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.cli.EnvironmentCommand;
import com.yammer.dropwizard.config.Environment;

public class ReadMessageCommand extends
		EnvironmentCommand<KeysmithClientConfiguration> {

	private static final Logger log = LoggerFactory
			.getLogger(ReadMessageCommand.class);

	private KeyMaster keyMaster;

	private Cryptographer controller;

	public ReadMessageCommand(Service<KeysmithClientConfiguration> service,
			KeyMaster keyMaster) {
		super(service, "read", "Reads a message from the server and decodes it with the previously stored private key");
		this.keyMaster = keyMaster;
		this.controller = new Cryptographer(keyMaster);
	}

	@Override
	protected void run(Environment environment, Namespace namespace,
			KeysmithClientConfiguration configuration) throws Exception {
		log.info("reading keyId...");
		String keyId = Files.toString(new File(".keyId"), Charset.defaultCharset());
		log.info("keyId read.");
		
		MessengerServerClient messenger = new MessengerServerClient(environment, configuration);
		
		log.info("getting public key encoded message from messenger server :" + keyId + " ...");
		Message publicKeyEncoded = messenger.getMessage("public-" + keyId);
		log.info("got encoded message:" + publicKeyEncoded);
		log.info("getting secret key encoded message from messenger server :" + keyId + " ...");
		Message secretKeyEncoded = messenger.getMessage("secret-" + keyId);
		log.info("got encoded message:" + secretKeyEncoded);
		log.info("getting hybrid key encoded message from messenger server :" + keyId + " ...");
		Message hybridEncoded = messenger.getMessage("hybrid-" + keyId);
		log.info("got encoded message:" + hybridEncoded);
		
		log.info("loading private key...");
		PrivateKey priKey = keyMaster.loadPrivateKey(keyId);
		log.info("private key loaded");
		
		log.info("decoding public key message...");
		String message = controller.publicDecrypt(publicKeyEncoded, priKey);
		log.info("message decoded:" + message);
		log.info("decoding secret key message...");
		message = controller.symmetricDecrypt(secretKeyEncoded);
		log.info("message decoded:" + message);
		log.info("decoding hybrid message...");
		message = controller.hybridDecrypt(hybridEncoded, priKey);
		log.info("message decoded:" + message);
	}

}
