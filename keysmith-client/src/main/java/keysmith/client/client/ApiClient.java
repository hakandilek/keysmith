package keysmith.client.client;

import keysmith.client.KeysmithClientConfiguration;

import com.sun.jersey.api.client.Client;
import com.yammer.dropwizard.client.JerseyClientBuilder;
import com.yammer.dropwizard.config.Environment;

public class ApiClient {

	protected Client client;

	protected HttpUtils utils;

	public ApiClient(Environment environment,
			KeysmithClientConfiguration configuration) {
		client = new JerseyClientBuilder()
				.using(configuration.getJerseyClientConfiguration())
				.using(environment).build();
		utils = new HttpUtils();
	}

}
