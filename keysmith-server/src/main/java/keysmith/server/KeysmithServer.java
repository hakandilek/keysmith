package keysmith.server;


import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.DatabaseConfiguration;

import keysmith.messenger.MessengerBundle;
import keysmith.service.KeysmithConfiguration;
import keysmith.service.KeysmithBundle;

public class KeysmithServer extends Service<KeysmithConfiguration> {

	public KeysmithServer() {
	}

	@Override
	public void initialize(Bootstrap<KeysmithConfiguration> bootstrap) {
		bootstrap.setName("keysmith");
		//keysmith bundle
		bootstrap.addBundle(new KeysmithBundle<KeysmithConfiguration>() {
			@Override
			public DatabaseConfiguration getDatabaseConfiguration(KeysmithConfiguration configuration) {
				return configuration.getDatabaseConfiguration();
			}
		});
		
		//messenger bundle
		bootstrap.addBundle(new MessengerBundle<KeysmithConfiguration>());
	}

	@Override
	public void run(KeysmithConfiguration configuration, Environment environment) throws Exception {
	}

}
