package keysmith.server;

import keysmith.server.core.Keystore;
import keysmith.server.db.KeysmithHibernateBundle;
import keysmith.server.db.SimpleKeyDAO;
import keysmith.server.resources.KeysmithResource;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.hibernate.HibernateBundle;

public class KeysmithService extends Service<KeysmithServiceConfiguration> {

	private final HibernateBundle<KeysmithServiceConfiguration> hibernate = new KeysmithHibernateBundle();
	
	@Override
	public void initialize(Bootstrap<KeysmithServiceConfiguration> bootstrap) {
		bootstrap.setName("keysmith-server");
		bootstrap.addBundle(hibernate);
	}

	@Override
	public void run(KeysmithServiceConfiguration configuration,
			Environment environment) throws Exception {
		System.out.println(hibernate.getDatabaseConfiguration(configuration));
		//Keystore keyStore = new MemoryKeyStore();
		Keystore keyStore = new SimpleKeyDAO(hibernate.getSessionFactory());
		environment.addResource(new KeysmithResource(keyStore));
	}

}
