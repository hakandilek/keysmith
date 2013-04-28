package keysmith.server;

import keysmith.server.core.KeyStore;
import keysmith.server.resources.KeysmithResource;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class KeysmithService extends Service<KeysmithServiceConfiguration> {

	@Override
	public void initialize(Bootstrap<KeysmithServiceConfiguration> bootstrap) {
		bootstrap.setName("keysmith-server");
	}

	@Override
	public void run(KeysmithServiceConfiguration configuration,
			Environment environment) throws Exception {
		KeyStore keyStore = new KeyStore();
		environment.addResource(new KeysmithResource(keyStore));
	}

}
