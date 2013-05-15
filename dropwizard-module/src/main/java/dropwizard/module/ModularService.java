package dropwizard.module;

import java.util.ArrayList;
import java.util.List;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.config.Environment;

public class ModularService<C extends Configuration> extends Service<C> {

	private final List<Module<C>> modules;
	private final String name;

	protected ModularService(String name) {
		super();
		this.name = name;
		this.modules = new ArrayList<Module<C>>();
	}
	
	protected void addModule(Module<C> module) {
		modules.add(module);
	}

	@Override
	public void initialize(Bootstrap<C> bootstrap) {
		for (Module<C> m : modules) {
			m.initialize(bootstrap);
		}
		bootstrap.setName(name);
	}

	@Override
	public void run(C configuration, Environment environment) throws Exception {
		
		for (Module<C> m : modules) {
			m.register(configuration, environment);
		}
	}

}
