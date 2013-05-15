package keysmith.server;

import keysmith.messenger.MessengerModule;
import keysmith.service.KeysmithConfiguration;
import keysmith.service.KeysmithModule;
import dropwizard.module.ModularService;

public class KeysmithServer extends ModularService<KeysmithConfiguration> {

	public KeysmithServer() {
		super("keysmith");
		addModule(new KeysmithModule<KeysmithConfiguration>());
		addModule(new MessengerModule<KeysmithConfiguration>());
	}

}
