package keysmith.client.commands;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import keysmith.client.KeysmithClientConfiguration;
import keysmith.client.client.KeysmithServerClient;
import keysmith.client.core.KeyMaster;
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

	private KeyMaster keyMaster;

	public GenerateKeyCommand(Service<KeysmithClientConfiguration> service,
			KeyMaster keyMaster) {
		super(service, "generate", "Generates a key pair, posts the public "
				+ "key to the server and stores the private key");
		this.keyMaster = keyMaster;
	}

	@Override
	protected void run(Environment env, Namespace namespace,
			KeysmithClientConfiguration conf) throws Exception {
		String keyId = conf.getKeyId();

		log.info("generating keys...");
		KeyPair kp = keyMaster.generateKeyPair();
		PublicKey pubKey = kp.getPublic();
		PrivateKey priKey = kp.getPrivate();
		log.info("keys generated.");
		log.info("public key : " + pubKey);

		log.info("posting public key...");
		KeysmithServerClient client = new KeysmithServerClient(env, conf, keyMaster);
		keyId = client.updatePublicKey(keyId, pubKey);
		log.info("public key posted : " + keyId);

		log.info("saving private key...");
		keyId = keyMaster.savePrivateKey(keyId, priKey);
		log.info("private key saved. keyId:" + keyId);

	}

}
