package dropwizard.module;

import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.config.Environment;

public interface Module<C extends Configuration> {

	void initialize(Bootstrap<C> bootstrap);
	
	void register(C configuration, Environment environment) throws Exception;
}
