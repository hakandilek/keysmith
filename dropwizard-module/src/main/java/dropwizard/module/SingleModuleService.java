package dropwizard.module;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.config.Environment;

public class SingleModuleService<C extends Configuration> extends Service<C> {

	private final Module<C> module;
	private final String name;

	public SingleModuleService(String name, Module<C> module) {
		this.name = name;
		this.module = module;
	}

	@Override
	public void initialize(Bootstrap<C> bootstrap) {
		module.initialize(bootstrap);
		bootstrap.setName(name);
	}

	@Override
	public void run(C configuration, Environment environment) throws Exception {
		module.register(configuration, environment);
	}

}
