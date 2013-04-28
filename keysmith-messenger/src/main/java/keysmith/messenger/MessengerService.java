package keysmith.messenger;

import keysmith.messenger.core.MessageStore;
import keysmith.messenger.resources.MessengerResource;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class MessengerService extends Service<MessengerConfiguration> {

	@Override
	public void initialize(Bootstrap<MessengerConfiguration> bootstrap) {
		bootstrap.setName("messenger-server");
	}

	@Override
	public void run(MessengerConfiguration configuration,
			Environment environment) throws Exception {
		MessageStore messageStore = new MessageStore();
		environment.addResource(new MessengerResource(messageStore));
	}

}
