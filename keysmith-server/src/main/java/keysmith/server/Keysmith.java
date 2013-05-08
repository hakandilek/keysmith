package keysmith.server;

import keysmith.service.core.Keystore;
import keysmith.service.db.KeysmithHibernateBundle;
import keysmith.service.db.SimpleKeyDAO;
import keysmith.service.resources.KeysmithResource;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.hibernate.HibernateBundle;

public class Keysmith<T extends Configuration> extends Service<T> {

	private final HibernateBundle<T> hibernate = new KeysmithHibernateBundle();

	@Override
	public void initialize(Bootstrap<T> bootstrap) {
		bootstrap.setName("keysmith");
		bootstrap.addBundle(hibernate);
	}

	@Override
	public void run(T configuration, Environment environment) throws Exception {
		// Keystore keyStore = new MemoryKeyStore();
		Keystore keyStore = new SimpleKeyDAO(hibernate.getSessionFactory());
		environment.addResource(new KeysmithResource(keyStore));
	}

}
