package keysmith.messenger;

import keysmith.messenger.core.MessageStore;
import keysmith.messenger.resources.MessengerResource;

import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.config.Environment;

import dropwizard.module.Module;

public class MessengerModule<C extends Configuration> implements Module<C> {

	public void initialize(Bootstrap<C> bootstrap) {
	}

	public void register(C configuration, Environment environment)
			throws Exception {
		MessageStore messageStore = new MessageStore();
		environment.addResource(new MessengerResource(messageStore));
	}

}
