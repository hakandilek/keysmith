package keysmith.service;

import keysmith.service.db.KeysmithHibernateBundle;
import keysmith.service.db.SimpleKeyDAO;
import keysmith.service.health.KeystoreMetricCheck;
import keysmith.service.health.KeystoreSizeCheck;
import keysmith.service.health.MetricKeyStore;
import keysmith.service.resources.KeysmithResource;

import com.codahale.metrics.MetricRegistry;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

import dropwizard.module.Module;

public class KeysmithModule<T extends KeysmithConfiguration> implements Module<T> {

	private final KeysmithHibernateBundle<T> hibernate = new KeysmithHibernateBundle<T>();
	
	public void initialize(Bootstrap<T> bootstrap) {
		bootstrap.setName("keysmith");
		bootstrap.addBundle(hibernate);
		bootstrap.addBundle(new AssetsBundle("/favicon.ico")); 
	}

	public void register(T configuration, Environment environment) throws Exception {
		MetricRegistry metrics = new MetricRegistry();
		MetricKeyStore keyStore = new MetricKeyStore(new SimpleKeyDAO(hibernate.getSessionFactory()), metrics);
		
		environment.addResource(new KeysmithResource(keyStore));
		environment.addHealthCheck(new KeystoreSizeCheck(keyStore));
		environment.addHealthCheck(new KeystoreMetricCheck(keyStore));
	}

}
