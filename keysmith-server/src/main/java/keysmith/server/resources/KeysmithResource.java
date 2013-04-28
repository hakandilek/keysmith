package keysmith.server.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import keysmith.server.core.KeyStore;

import com.sun.jersey.api.core.InjectParam;
import com.yammer.metrics.annotation.Timed;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/keysmith")
public class KeysmithResource {
	
	private final KeyStore keyStore;

	public KeysmithResource(KeyStore keyStore) {
		super();
		this.keyStore = keyStore;
	}

	@GET
	@Timed
	@Path("/publicKey/{keyId}")
	public Response getPublicKey(@InjectParam String keyId) {
		String key = keyStore.get(keyId);
		if (key == null) {
			return Response.noContent().build();
		}
		return Response.ok(key).build();
	}

	@POST
	@Timed
	@Path("/publicKey")
	@Produces()
	public Response postPublicKey(String key) {
		String keyId = keyStore.put(key);
		if (keyId == null) {
			return Response.noContent().build();
		}
		return Response.ok(keyId).build();
	}

	@POST
	@Timed
	@Path("/publicKey/{keyId}")
	public Response updatePublicKey(@InjectParam String keyId, String key) {
		String oldKeyId = keyStore.update(keyId, key);
		if (oldKeyId == null) {
			return Response.noContent().build();
		}
		return Response.ok(oldKeyId).build();
	}

	@DELETE
	@Timed
	@Path("/publicKey/{keyId}")
	public Response removePublicKey(@InjectParam String keyId) {
		String key = keyStore.remove(keyId);
		if (key == null) {
			return Response.noContent().build();
		}
		return Response.ok(key).build();
	}


}
