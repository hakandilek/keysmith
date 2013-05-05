package keysmith.service;

import keysmith.service.core.Keystore;
import keysmith.service.db.KeysmithHibernateBundle;
import keysmith.service.db.SimpleKeyDAO;
import keysmith.service.resources.KeysmithResource;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

public class KeysmithService<T extends KeysmithConfiguration> extends Service<T> {

	private final KeysmithHibernateBundle<T> hibernate = new KeysmithHibernateBundle<T>();

	@Override
	public void initialize(Bootstrap<T> bootstrap) {
		bootstrap.setName("keysmith");
		bootstrap.addBundle(hibernate);
	}

	@Override
	public void run(T configuration, Environment environment) throws Exception {
		Keystore keyStore = new SimpleKeyDAO(hibernate.getSessionFactory());
		environment.addResource(new KeysmithResource(keyStore));
	}

}
