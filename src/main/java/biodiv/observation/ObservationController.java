package biodiv.observation;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.pac4j.core.context.J2EContext;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.jax.rs.annotations.Pac4JProfileManager;
import org.pac4j.jax.rs.annotations.Pac4JSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import biodiv.auth.AuthUtils;
import biodiv.userGroup.UserGroup;

@Path("/observation")
public class ObservationController {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Inject
	ObservationService observationService;
 
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Observation show(@PathParam("id") long id) {

		try {
			Observation observation = observationService.show(id);
			return observation;
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}

	}

	@GET
	@Path("/{id}/userGroups")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserGroup> obvUserGroups(@PathParam("id") long id) {
		List<UserGroup> usrGrps = observationService.obvUserGroups(id);
		return usrGrps;
	}
	
	@GET
	@Path("/customFields")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Map<String, Object>> getCustomFields(@QueryParam("obvId") Long obvId) {

		List<Map<String, Object>> cf = observationService.getCustomFields(obvId);
		return cf;
	}

	@POST
	@Path("/updateCustomField")
	@Produces(MediaType.APPLICATION_JSON)
	@Pac4JSecurity(clients = "cookieClient,headerClient", authorizers = "isAuthenticated")
	public String updateCustomField(@QueryParam("fieldValue") String fieldValue, @QueryParam("cfId") Long cfId,
			@QueryParam("obvId") Long obvId, @Context HttpServletRequest request) throws NumberFormatException, Exception {

		CommonProfile profile = AuthUtils.currentUser(request);
		String msg = observationService.updateInlineCf(fieldValue, cfId, obvId, Long.parseLong(profile.getId()));
		return msg;
	}

	@GET
	@Path("/resource/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ObservationResource> getResource(@PathParam("id") long id) {
		List<ObservationResource> observationResources = observationService.getResouce(id);
		return observationResources;
	}

	@POST
	@Path("/updategroup")
	@Produces(MediaType.APPLICATION_JSON)
	@Pac4JSecurity(clients = "cookieClient,headerClient", authorizers = "isAuthenticated")
	public Object updateGroup(@QueryParam("objectid") Long objectid, @QueryParam("newGroupId") Long newGroupId,
			@QueryParam("oldGroupId") Long oldGroupId, @Context HttpServletRequest request) {
		CommonProfile profile = AuthUtils.currentUser(request);
		Object observation = observationService.updateGroup(objectid, newGroupId, oldGroupId,
				Long.parseLong(profile.getId()));
		return observation;
	}

	@GET
	@Path("/recommendationVotes")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> getRecommendationVotes(@QueryParam("obvIds") String obvs,
			@Pac4JProfileManager ProfileManager<CommonProfile> profileM, @Context SecurityContext securityContext,
			@Context HttpServletRequest request, @Context HttpServletResponse response) {
		log.debug("####### {}", profileM);
		log.debug("####### {}", profileM.getClass());
		log.debug("####### {}", profileM.get(true));
		log.debug("####### {}", profileM.get(false));
		// ProfileManager manager = new ProfileManager(new J2EContext(request,
		// null));
		// Optional<CommonProfile> profile = manager.get(false);
		//
		// log.debug("(((((((((({}", requestContext.getSecurityContext());
		// log.debug("(((((((((({}",
		// ((Pac4JSecurityContext)securityContext).getUserPrincipal());
		// SecurityContext securityContext =
		// profileM.getJaxRsContext().getRequestContext().getSecurityContext();

		// Principal profile = securityContext.getUserPrincipal();
		J2EContext context = new J2EContext(request, response);
		final ProfileManager<CommonProfile> manager = new ProfileManager(context);
		final Optional<CommonProfile> profile = manager.get(true);

		log.debug("^^^^^^^^^^^^^^^^^^^^^^ {} ", profile);
		// log.debug("^^^^^^^^^^^^^^^^^^^^^^ {} ", getProfile());
		Map<String, Object> recoVotes = observationService.getRecommendationVotes(obvs);
		return recoVotes;

	}

}
