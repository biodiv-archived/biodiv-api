package biodiv.admin;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.pac4j.jax.rs.annotations.Pac4JSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import biodiv.observation.ObservationList;
import biodiv.user.UserController;

@Path("/admin")
public class AdminController {
	
	private final Logger log = LoggerFactory.getLogger(UserController.class);
	
	/**
	 * 
	 * @param index
	 * The this going to allow admin to upload setting and mapping 
	 */
	@POST
	@Path("/{index}/uploadmapping")
	@Produces(MediaType.APPLICATION_JSON)
	@Pac4JSecurity(clients = "headerClient", authorizers = "isAuthenticated")
	public void uploadMapping(@PathParam("index") String index,String settingsAndMappings){
		
		ObservationList observationList=new ObservationList();
		observationList.uploadSettingsAndMappings(index,settingsAndMappings);
		
		
	}

}