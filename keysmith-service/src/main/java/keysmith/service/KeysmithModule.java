package keysmith.service;

import keysmith.service.core.Keystore;
import keysmith.service.db.KeysmithHibernateBundle;
import keysmith.service.db.SimpleKeyDAO;
import keysmith.service.resources.KeysmithResource;

import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

import dropwizard.module.Module;

public class KeysmithModule<T extends KeysmithConfiguration> implements Module<T> {

	private final KeysmithHibernateBundle<T> hibernate = new KeysmithHibernateBundle<T>();

	public void initialize(Bootstrap<T> bootstrap) {
		bootstrap.setName("keysmith");
		bootstrap.addBundle(hibernate);
	}

	public void register(T configuration, Environment environment) throws Exception {
		Keystore keyStore = new SimpleKeyDAO(hibernate.getSessionFactory());
		environment.addResource(new KeysmithResource(keyStore));
	}

}
