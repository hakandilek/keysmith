package keysmith.client;

import keysmith.client.commands.GenerateKeyCommand;
import keysmith.client.commands.ReadMessageCommand;
import keysmith.client.commands.SendMessageCommand;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class KeysmithClient extends Service<KeysmithClientConfiguration> {

	private CryptographyController controller;

	@Override
	public void initialize(Bootstrap<KeysmithClientConfiguration> bootstrap) {
		bootstrap.setName("keysmith-client");
		
		controller = new CryptographyController();
		bootstrap.addCommand(new GenerateKeyCommand(this, controller));
		bootstrap.addCommand(new SendMessageCommand(this, controller));
		bootstrap.addCommand(new ReadMessageCommand(this, controller));
	}

	@Override
	public void run(KeysmithClientConfiguration configuration,
			Environment environment) throws Exception {
		String algorithm = configuration.getAlgorithm();
		String cipherTransformation = configuration.getCipherTransformation();
		Integer keySize = configuration.getKeySize();
		controller.init(algorithm, cipherTransformation, keySize);
	}

}
