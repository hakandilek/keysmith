package keysmith.server;

import keysmith.service.KeysmithConfiguration;
import keysmith.service.KeysmithService;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class KeysmithServer extends Service<KeysmithConfiguration> {

	private final KeysmithService<KeysmithConfiguration> keysmith = new KeysmithService<KeysmithConfiguration>();
	
	@Override
	public void initialize(Bootstrap<KeysmithConfiguration> bootstrap) {
		keysmith.initialize(bootstrap);
		bootstrap.setName("keysmith-server");
	}

	@Override
	public void run(KeysmithConfiguration configuration,
			Environment environment) throws Exception {
		keysmith.run(configuration, environment);
	}

}
