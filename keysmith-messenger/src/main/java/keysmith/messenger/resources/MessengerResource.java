package keysmith.messenger.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import keysmith.common.core.Message;
import keysmith.messenger.core.MessageStore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	public Response getMessage(@PathParam("address") String address) {
		log.info("getMessage : " + address);
		Message msg = store.get(address);
		if (msg == null) {
			return Response.noContent().build();
		}
		return Response.ok(msg).build();
	}

	@POST
	@Timed
	@Path("/message")
	@Produces()
	public Response postMessage(Message msg) {
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
	public Response updateMessage(@PathParam("address") String address,
			Message msg) {
		log.info("updateMessage.address : " + address);
		log.info("updateMessage.message : " + msg);
		String oldAddress = store.update(address, msg);
		if (oldAddress == null) {
			return Response.noContent().build();
		}
		return Response.ok(oldAddress).build();
	}

	@DELETE
	@Timed
	@Path("/message/{address}")
	public Response removeMessage(@PathParam("address") String address) {
		log.info("removeMessage : " + address);
		Message msg = store.remove(address);
		if (msg == null) {
			return Response.noContent().build();
		}
		return Response.ok(msg).build();
	}

}
