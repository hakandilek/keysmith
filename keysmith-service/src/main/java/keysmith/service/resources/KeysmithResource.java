package keysmith.service.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import keysmith.service.core.Keystore;
import keysmith.service.core.SimpleKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yammer.dropwizard.hibernate.UnitOfWork;
import com.yammer.metrics.annotation.Timed;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/keysmith")
public class KeysmithResource {

	private static final Logger log = LoggerFactory
			.getLogger(KeysmithResource.class);

	private final Keystore keyStore;

	public KeysmithResource(Keystore keyStore) {
		super();
		this.keyStore = keyStore;
	}

	@GET
	@Timed
	@Path("/publicKey/{ukey}")
	@UnitOfWork
	public Response getPublicKey(@PathParam("ukey") String ukey) {
		SimpleKey key = keyStore.get(ukey);
		if (key == null) {
			return Response.noContent().build();
		}
		return Response.ok(key.getData()).build();
	}

	@POST
	@Timed
	@Path("/publicKey")
	@UnitOfWork
	public Response postPublicKey(String keyData) {
		String ukey = keyStore.put(new SimpleKey(null, keyData));
		if (ukey == null) {
			return Response.noContent().build();
		}
		return Response.ok(ukey).build();
	}

	@POST
	@Timed
	@Path("/publicKey/{ukey}")
	@UnitOfWork
	public Response updatePublicKey(@PathParam("ukey") String ukey, String keyData) {
		SimpleKey key = new SimpleKey(null, keyData);
		key.setUkey(ukey);
		log.info("updatePublicKey.address : " + ukey);
		log.info("updatePublicKey.message : " + key);
		SimpleKey oldKey = keyStore.update(ukey, key);
		if (oldKey == null) {
			return Response.noContent().build();
		}
		return Response.ok(oldKey.getUkey()).build();
	}

	@DELETE
	@Timed
	@Path("/publicKey/{ukey}")
	@UnitOfWork
	public Response removePublicKey(@PathParam("ukey") String ukey) {
		SimpleKey key = keyStore.removeKey(ukey);
		if (key == null) {
			return Response.noContent().build();
		}
		return Response.ok(key.getData()).build();
	}

}
