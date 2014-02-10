package keysmith.service;

import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.db.DatabaseConfiguration;

public interface KeysmithConfigurationStrategy<T extends Configuration> {
	DatabaseConfiguration getDatabaseConfiguration(T configuration);
}
