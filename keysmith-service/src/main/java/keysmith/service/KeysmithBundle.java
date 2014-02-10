package keysmith.service;

import keysmith.service.db.KeysmithHibernateBundle;
import keysmith.service.db.SimpleKeyDAO;
import keysmith.service.health.KeystoreMetricCheck;
import keysmith.service.health.KeystoreSizeCheck;
import keysmith.service.health.MetricKeyStore;
import keysmith.service.resources.KeysmithResource;

import com.codahale.metrics.MetricRegistry;
import com.yammer.dropwizard.ConfiguredBundle;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.hibernate.HibernateBundle;

public abstract class KeysmithBundle<T extends Configuration> implements ConfiguredBundle<T>,
		KeysmithConfigurationStrategy<T> {

	private final HibernateBundle<Configuration> hibernate = new KeysmithHibernateBundle<Configuration>() {
		@SuppressWarnings("unchecked")
		public DatabaseConfiguration getDatabaseConfiguration(Configuration configuration) {
			return KeysmithBundle.this.getDatabaseConfiguration((T) configuration);
		}
	};

	public void initialize(Bootstrap<?> bootstrap) {
		bootstrap.setName("keysmith");
		bootstrap.addBundle(hibernate);
		bootstrap.addBundle(new AssetsBundle("/favicon.ico"));
	}

	@Override
	public void run(T configuration, Environment environment) throws Exception {
		MetricRegistry metrics = new MetricRegistry();
		MetricKeyStore keyStore = new MetricKeyStore(new SimpleKeyDAO(hibernate.getSessionFactory()), metrics);

		environment.addResource(new KeysmithResource(keyStore));
		environment.addHealthCheck(new KeystoreSizeCheck(keyStore));
		environment.addHealthCheck(new KeystoreMetricCheck(keyStore));
	}

}
