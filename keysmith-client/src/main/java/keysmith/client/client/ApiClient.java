package keysmith.client.client;

import keysmith.client.core.HttpUtils;

import com.sun.jersey.api.client.Client;
import com.yammer.dropwizard.client.JerseyClientBuilder;
import com.yammer.dropwizard.client.JerseyClientConfiguration;
import com.yammer.dropwizard.config.Environment;

public class ApiClient {

	protected Client client;

	protected HttpUtils utils;

	public ApiClient(Environment environment,
			JerseyClientConfiguration configuration) {
		client = new JerseyClientBuilder().using(configuration)
				.using(environment).build();
		utils = new HttpUtils();
	}

}
