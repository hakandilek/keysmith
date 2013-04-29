package keysmith.client.core;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

public class HttpUtils {

	public HttpUtils() {
	}

	public <T> T get(Client client, String url, Class<T> resultType) {
		WebResource wr = client.resource(url);

		Builder b = wr.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
		ClientResponse response = b.get(ClientResponse.class);

		if (response.getStatus() != 200) {
			exception(response);
		}

		T result = response.getEntity(resultType);
		return result;
	}

	public <T> T delete(Client client, String url, Class<T> resultType) {
		WebResource wr = client.resource(url);

		Builder b = wr.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
		ClientResponse response = b.delete(ClientResponse.class);

		if (response.getStatus() != 200) {
			exception(response);
		}

		T result = response.getEntity(resultType);
		return result;
	}

	public <T, P> T post(Client client, String url, Class<T> resultType,
			P param) {
		WebResource wr = client.resource(url);

		Builder b = wr.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON);
		ClientResponse response = b.post(ClientResponse.class, param);

		if (response.getStatus() != 200) {
			exception(response);
		}

		T result = response.getEntity(resultType);
		return result;
	}

	private void exception(ClientResponse response) {
		throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
	}

}
