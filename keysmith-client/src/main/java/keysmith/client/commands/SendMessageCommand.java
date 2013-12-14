package keysmith.client.commands;

import java.io.File;
import java.nio.charset.Charset;
import java.security.PublicKey;

import javax.crypto.SecretKey;

import keysmith.client.KeysmithClientConfiguration;
import keysmith.client.client.KeysmithServerClient;
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

public class SendMessageCommand extends
		EnvironmentCommand<KeysmithClientConfiguration> {

	private static final Logger log = LoggerFactory
			.getLogger(SendMessageCommand.class);

	private KeyMaster keyMaster;

	private Cryptographer controller;

	public SendMessageCommand(Service<KeysmithClientConfiguration> service,
			KeyMaster keyMaster) {
		super(service, "send", "Encodes the message with the public key "
				+ "from keysmith server and sends it to the messenger server");
		this.keyMaster = keyMaster;
		this.controller = new Cryptographer(keyMaster);
	}

	@Override
	protected void run(Environment environment, Namespace namespace,
			KeysmithClientConfiguration configuration) throws Exception {
		log.info("reading keyId...");
		String keyId = Files.toString(new File(".keyId"),
				Charset.defaultCharset());
		log.info("keyId read.");

		String message = configuration.getMessage();
		KeysmithServerClient keysmith = new KeysmithServerClient(environment,
				configuration, keyMaster);
		MessengerServerClient messenger = new MessengerServerClient(
				environment, configuration);

		log.info("getting public key :" + keyId + " ...");
		PublicKey pubKey = keysmith.getPublicKey(keyId);
		log.info("got public key:" + pubKey);
		log.info("generating secret key :" + keyId + " ...");
		SecretKey secKey = keyMaster.generateSecretKey();
		log.info("generated secret key:" + secKey);

		log.info("encoding message with public key ...");
		Message publicKeyEncoded = controller.publicEncrypt(message, pubKey);
		log.info("message encoded : " + publicKeyEncoded);
		log.info("encoding message with secret key ...");
		Message secretKeyEncoded = controller.symmetricEncrypt(message, secKey);
		log.info("message encoded : " + secretKeyEncoded);
		log.info("encoding message hybrid ...");
		Message hybridEncoded = controller.hybridEncrypt(message, pubKey);
		log.info("message encoded : " + hybridEncoded);

		log.info("posting public key encoded message to messenger server...");
		messenger.postMessage("public-" + keyId, publicKeyEncoded);
		log.info("message posted");
		log.info("posting secret key encoded message to messenger server...");
		messenger.postMessage("secret-" + keyId, secretKeyEncoded);
		log.info("message posted");
		log.info("posting hybrid encoded message to messenger server...");
		messenger.postMessage("hybrid-" + keyId, hybridEncoded);
		log.info("message posted");

	}

}
