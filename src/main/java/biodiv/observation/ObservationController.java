package biodiv.observation;

import biodiv.Transactional;
import biodiv.auth.AuthUtils;
import biodiv.customField.CustomField;
import biodiv.user.User;
import biodiv.userGroup.UserGroup;
import biodiv.util.Utils;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.jax.rs.annotations.Pac4JSecurity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

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
        }
        return null;
    }

    @GET
    @Path("/{id}/userGroups")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserGroup> obvUserGroups(@PathParam("id") long id) {
        return observationService.obvUserGroups(id);
    }

    @GET
    @Path("/customFields")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public List<Map<String, Object>> getCustomFields(@QueryParam("obvId") Long obvId) {
        return observationService.getCustomFields(obvId);
    }

    @GET
    @Path("/customFields/list")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public List<CustomField> getAllCustomFields(@QueryParam("uid") Long uid) {
        if (uid != null) {
            return observationService.getAllCustomFieldsByUserGroup(uid);
        }
        return observationService.getAllCustomFields();
    }

    @POST
    @Path("/updateCustomField")
    @Produces(MediaType.APPLICATION_JSON)
    @Pac4JSecurity(clients = "cookieClient,headerClient", authorizers = "isAuthenticated")
    public String updateCustomField(@QueryParam("fieldValue") String fieldValue, @QueryParam("cfId") Long cfId,
                                    @QueryParam("obvId") Long obvId,
                                    @QueryParam("loggedInUserId") Long loggedInUserId,
                                    @QueryParam("isAdmin") Boolean isAdmin, @Context HttpServletRequest request) throws NumberFormatException, Exception {
        CommonProfile profile = AuthUtils.currentUser(request);
        String msg = observationService.updateInlineCf(fieldValue, cfId, obvId, Long.parseLong(profile.getId()),
                loggedInUserId, isAdmin);
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
    public String updateGroup(@QueryParam("objectIds") String objectIds, @QueryParam("newGroupId") Long newGroupId,
                              @Context HttpServletRequest request) {
        CommonProfile profile = AuthUtils.currentUser(request);
        String observation = observationService.updateGroup(objectIds, newGroupId, Long.parseLong(profile.getId()));
        return observation;
    }

    @GET
    @Path("/recommendationVotes")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getRecommendationVotes(@QueryParam("obvIds") String obvs,
                                                      @QueryParam("loggedInUserId") Long loggedInUserId, @QueryParam(
                                                              "isAdmin") Boolean isAdmin, @QueryParam("isSpeciesAdmin"
    ) Boolean isSpeciesAdmin) {
        //CommonProfile profile = AuthUtils.currentUser(request);
        //System.out.println("testing lock permission");
        //System.out.println("testing lock permission");
        //System.out.println("testing lock permission"+profile.isPresent());
        //System.out.println("testing lock permission"+profileM.get(true));
        //final CommonProfile profile = profileM.get(true).get();
        //System.out.println("testing lock permission "+profile.get().getId());
        //System.out.println("testing lock permission "+profileM);
        Map<String, Object> recoVotes = observationService.getRecommendationVotes(obvs, loggedInUserId, isAdmin,
                isSpeciesAdmin);
        return recoVotes;

    }

    @POST
    @Path("/addRecommendationVote")
    @Produces(MediaType.APPLICATION_JSON)
    @Pac4JSecurity(clients = "cookieClient,headerClient", authorizers = "isAuthenticated")
    public Response addRecommendationVote(@QueryParam("obvIds") String obvIds,
                                          @QueryParam("commonName") String commonName,
                                          @QueryParam("languageName") String languageName,
                                          @QueryParam("recoName") String recoName, @QueryParam("recoId") Long recoId,
                                          @QueryParam("recoComment") String recoComment,
                                          @Context HttpServletRequest request) throws NumberFormatException, Exception {
        CommonProfile profile = AuthUtils.currentUser(request);
        String r = observationService.addRecommendationVote(obvIds, commonName, languageName, recoName, recoId,
                recoComment, Long.parseLong(profile.getId()));
        return Utils.toJSONResponse(r);
    }

    @GET
    @Path("/findWhoLiked")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> findWhoLiked(@QueryParam("obvId") long obvId) {
        List<User> usrGrps = observationService.findWhoLiked(obvId);
        return usrGrps;
    }

    //	@GET
    //	@Path("/olderFlags")
    //	@Produces(MediaType.APPLICATION_JSON)
    //	public List<User> fetchOlderFlags (@QueryParam("obvId") long obvId) {
    //		List<User> usrGrps = observationService.findWhoLiked(obvId);
    //		return usrGrps;
    //	}

}
