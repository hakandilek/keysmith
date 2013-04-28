package keysmith.client.commands;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import keysmith.client.KeysmithClientConfiguration;
import keysmith.client.CryptographyController;
import keysmith.client.client.KeysmithServerClient;
import net.sourceforge.argparse4j.inf.Namespace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.cli.EnvironmentCommand;
import com.yammer.dropwizard.config.Environment;

public class GenerateKeyCommand extends
		EnvironmentCommand<KeysmithClientConfiguration> {

	private static final Logger log = LoggerFactory
			.getLogger(GenerateKeyCommand.class);

	private CryptographyController controller;

	public GenerateKeyCommand(Service<KeysmithClientConfiguration> service,
			CryptographyController controller) {
		super(
				service,
				"generate",
				"Generates a key pair, posts the public key to the server and stores the private key");
		this.controller = controller;
	}

	@Override
	protected void run(Environment env, Namespace namespace,
			KeysmithClientConfiguration conf) throws Exception {

		log.info("generating keys...");
		KeyPair kp = controller.generateKeyPair();
		PublicKey pubKey = kp.getPublic();
		PrivateKey priKey = kp.getPrivate();
		log.info("keys generated.");
		log.info("public key : " + pubKey);

		log.info("posting public key...");
		KeysmithServerClient client = new KeysmithServerClient(env, conf);
		String keyId = client.updatePublicKey(conf.getKeyId(), pubKey);
		log.info("public key posted : " + keyId);

		log.info("saving private key...");
		keyId = controller.savePrivateKey(keyId, priKey);
		log.info("private key saved. keyId:" + keyId);

	}

}
