package keysmith.service.db;

import keysmith.service.KeysmithConfiguration;
import keysmith.service.core.SimpleKey;

import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.hibernate.HibernateBundle;

public class KeysmithHibernateBundle<T extends KeysmithConfiguration> extends
		HibernateBundle<T> {

	public KeysmithHibernateBundle() {
		super(SimpleKey.class);
	}

	public DatabaseConfiguration getDatabaseConfiguration(T conf) {
		DatabaseConfiguration config = conf.getDatabaseConfiguration();
		return config;
	}

}
