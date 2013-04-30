package keysmith.server.db;

import keysmith.server.KeysmithServiceConfiguration;
import keysmith.server.core.SimpleKey;

import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.hibernate.HibernateBundle;

public class KeysmithHibernateBundle extends
		HibernateBundle<KeysmithServiceConfiguration> {

	public KeysmithHibernateBundle() {
		super(SimpleKey.class);
	}

	public DatabaseConfiguration getDatabaseConfiguration(
			KeysmithServiceConfiguration conf) {
		DatabaseConfiguration config = conf.getDatabaseConfiguration();
		return config;
	}

}
