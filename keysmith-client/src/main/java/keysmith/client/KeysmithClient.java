package keysmith.client;

import keysmith.client.commands.GenerateKeyCommand;
import keysmith.client.commands.ReadMessageCommand;
import keysmith.client.commands.SendMessageCommand;
import keysmith.client.core.KeyMaster;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class KeysmithClient extends Service<KeysmithClientConfiguration> {

	private KeyMaster keyMaster;

	@Override
	public void initialize(Bootstrap<KeysmithClientConfiguration> bootstrap) {
		bootstrap.setName("keysmith-client");

		keyMaster = new KeyMaster();
		bootstrap.addCommand(new GenerateKeyCommand(this, keyMaster));
		bootstrap.addCommand(new SendMessageCommand(this, keyMaster));
		bootstrap.addCommand(new ReadMessageCommand(this, keyMaster));
	}

	@Override
	public void run(KeysmithClientConfiguration configuration,
			Environment environment) throws Exception {
		String publicKeyAlgorithm = configuration.getPublicKeyAlgorithm();
		String publicKeyTransformation = configuration
				.getPublicKeyTransformation();
		String secretKeyAlgorithm = configuration.getSecretKeyAlgorithm();
		String secretKeyTransformation = configuration
				.getSecretKeyTransformation();
		String secretSeed = configuration.getSecretSeed();
		Integer keySize = configuration.getKeySize();
		keyMaster.init(publicKeyAlgorithm, publicKeyTransformation,
				secretKeyAlgorithm, secretKeyTransformation, secretSeed,
				keySize);
	}

}
