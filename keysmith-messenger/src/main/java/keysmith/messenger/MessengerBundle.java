package keysmith.messenger;

import keysmith.messenger.core.MessageStore;
import keysmith.messenger.resources.MessengerResource;

import com.yammer.dropwizard.ConfiguredBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.config.Environment;

public class MessengerBundle<T extends Configuration> implements ConfiguredBundle<T> {

	public void initialize(Bootstrap<?> bootstrap) {
		bootstrap.setName("keysmith");
	}

	@Override
	public void run(T configuration, Environment environment) throws Exception {
		MessageStore messageStore = new MessageStore();
		environment.addResource(new MessengerResource(messageStore));
	}

}
