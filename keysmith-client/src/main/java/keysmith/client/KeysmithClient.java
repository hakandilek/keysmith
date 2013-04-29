package keysmith.client;

import keysmith.client.commands.GenerateKeyCommand;
import keysmith.client.commands.ReadMessageCommand;
import keysmith.client.commands.SendMessageCommand;
import keysmith.client.core.CryptographyHelper;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class KeysmithClient extends Service<KeysmithClientConfiguration> {

	private CryptographyHelper helper;

	@Override
	public void initialize(Bootstrap<KeysmithClientConfiguration> bootstrap) {
		bootstrap.setName("keysmith-client");
		
		helper = new CryptographyHelper();
		bootstrap.addCommand(new GenerateKeyCommand(this, helper));
		bootstrap.addCommand(new SendMessageCommand(this, helper));
		bootstrap.addCommand(new ReadMessageCommand(this, helper));
	}

	@Override
	public void run(KeysmithClientConfiguration configuration,
			Environment environment) throws Exception {
		String algorithm = configuration.getAlgorithm();
		String cipherTransformation = configuration.getCipherTransformation();
		Integer keySize = configuration.getKeySize();
		helper.init(algorithm, cipherTransformation, keySize);
	}

}
