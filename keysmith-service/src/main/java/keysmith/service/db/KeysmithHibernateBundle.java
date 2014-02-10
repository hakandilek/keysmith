package keysmith.service.db;

import keysmith.service.core.SimpleKey;

import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.hibernate.HibernateBundle;

public abstract class KeysmithHibernateBundle<T extends Configuration> extends
		HibernateBundle<T>  {

	public KeysmithHibernateBundle() {
		super(SimpleKey.class);
	}

}
