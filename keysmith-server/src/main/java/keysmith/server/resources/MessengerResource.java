package keysmith.server.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import keysmith.server.core.MessageStore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.core.InjectParam;
import com.yammer.metrics.annotation.Timed;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/messenger")
public class MessengerResource {

	private static final Logger log = LoggerFactory
			.getLogger(MessengerResource.class);

	private final MessageStore store;

	public MessengerResource(MessageStore store) {
		super();
		this.store = store;
	}

	@GET
	@Timed
	@Path("/message/{address}")
	public Response getMessage(@InjectParam String address) {
		log.info("getMessage : " + address);
		String msg = store.get(address);
		if (msg == null) {
			return Response.noContent().build();
		}
		return Response.ok(msg).build();
	}

	@POST
	@Timed
	@Path("/message")
	@Produces()
	public Response postMessage(String msg) {
		log.info("postMessage : " + msg);
		String address = store.put(msg);
		if (address == null) {
			return Response.noContent().build();
		}
		return Response.ok(address).build();
	}

	@POST
	@Timed
	@Path("/message/{address}")
	public Response updateMessage(@InjectParam String address, String msg) {
		log.info("updateMessage : " + address + ", " + msg);
		String oldAddress = store.update(address, msg);
		if (oldAddress == null) {
			return Response.noContent().build();
		}
		return Response.ok(oldAddress).build();
	}

	@DELETE
	@Timed
	@Path("/message/{address}")
	public Response removeMessage(@InjectParam String address) {
		log.info("removeMessage : " + address);
		String key = store.remove(address);
		if (key == null) {
			return Response.noContent().build();
		}
		return Response.ok(key).build();
	}

}
