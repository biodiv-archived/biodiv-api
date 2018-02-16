package biodiv.observation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.jvnet.hk2.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import biodiv.Transactional;
import biodiv.activityFeed.ActivityFeedService;
import biodiv.auth.AuthUtils;
import biodiv.comment.CommentService;
import biodiv.common.AbstractService;
import biodiv.common.CommonMethod;
import biodiv.common.LanguageService;
import biodiv.common.SpeciesGroup;
import biodiv.common.SpeciesGroupService;
import biodiv.taxon.datamodel.dao.Taxon;
import biodiv.user.User;
import biodiv.user.UserService;
import biodiv.customField.CustomFieldService;

import biodiv.userGroup.UserGroup;

@Service
public class ObservationService extends AbstractService<Observation> {

	private ObservationDao observationDao;

	@Inject
	private CustomFieldService customFieldService;

	@Inject
	private SpeciesGroupService speciesGroupService;

	@Inject
	private ActivityFeedService activityFeedService;

	@Inject
	private UserService userService;

	@Inject
	private RecommendationService recommendationService;

	@Inject
	private CommentService commentService;
	
	@Inject
	private LanguageService languageService;

	@Inject
	ObservationService(ObservationDao observationDao) {
		super(observationDao);
		this.observationDao = observationDao;
		System.out.println("ObservationService constructor");
	}

	public List<Observation> findAllByGroup(int max, int offset) {
		System.out.println("alter");
		return null;
	}

	@Transactional
	public List<UserGroup> obvUserGroups(long id) {
		try {
			List<UserGroup> usrGrps = observationDao.obvUserGroups(id);
			return usrGrps;
		} catch (Exception e) {
			throw e;
		} finally {
		}

	}

	@Transactional
	public String updateInlineCf(String fieldValue, Long cfId, Long obvId, long userId) {

		String msg;
		try {
			Observation obv = findById(obvId);
			Set<UserGroup> obvUserGrps = obv.getUserGroups();
			msg = customFieldService.updateInlineCf(fieldValue, cfId, obvId, userId, obvUserGrps);
			return msg;
		} catch (Exception e) {
			throw e;
		} finally {

		}
	}

	@Transactional
	public List<Map<String, Object>> getCustomFields(Long obvId) {

		try {
			Observation obv = findById(obvId);
			Set<UserGroup> obvUserGrps = obv.getUserGroups();
			List<Map<String, Object>> cf = customFieldService.getAllCustomfiedls(obvId, obvUserGrps);
			return cf;
		} catch (Exception e) {
			throw e;
		} finally {

		}
	}

	@Transactional
	public List<ObservationResource> getResouce(long id) {
		// TODO Auto-generated method stub
		List<ObservationResource> observationResources = observationDao.getResource(id);
		return observationResources;

	}

	@Transactional
	public Object updateGroup(Long objectid, Long newgroupid, Long oldGroupId, Long userId) {
		// TODO Auto-generated method stub
		User user = userService.findById(userId);
		Object obj;
		SpeciesGroup speciesGroup = speciesGroupService.findById(newgroupid);
		String newSpeciesGroupName = speciesGroup.getName();
		SpeciesGroup oldSpeciesGroup = speciesGroupService.findById(oldGroupId);
		String oldSpeciesGroupName = oldSpeciesGroup.getName();
		Observation obseravtion=show(objectid);
		obj =  observationDao.updateGroup(obseravtion,speciesGroup);
		
		//activityFeed
		Date dateCreated = new java.util.Date();
		Date lastUpdated = dateCreated;
		String activityDescription = oldSpeciesGroupName+" to "+newSpeciesGroupName;
		System.out.println(activityDescription);
		Map<String, Object> afNew = activityFeedService.createMapforAf("Object",objectid,obseravtion,
				"species.participation.Observation","species.participation.Observation",objectid,"Observation species group updated",
				"Species group updated",activityDescription,activityDescription,null,null,true,null,dateCreated,lastUpdated);
		activityFeedService.addActivityFeed(user,afNew,obseravtion,(String)afNew.get("rootHolderType"));
		//activityFeed
		return obj;
	}

	@Transactional
	public Observation show(long id) {
		Observation obseravtion = observationDao.findById(id);
		return obseravtion;
	}

	@Transactional
	public Map<String, Object> getRecommendationVotes(String obvs) {

		try {
			long[] obvIds = Arrays.asList(obvs.split(",")).stream().map(String::trim).mapToLong(Long::parseLong)
					.toArray();
			Integer noOfObvs = obvIds.length;
			Boolean singleObv = false;
			String query = "";
			if (noOfObvs == 1) {
				query = "select * from reco_vote_details rv where rv.observation_id =:obvId";
				singleObv = true;
			} else {
				query = "select * from reco_vote_details rv where rv.observation_id in (" + obvs
						+ ")  order by rv.observation_id";
			}

			List<Object[]> rv = observationDao.getRecommendationVotes(query, singleObv, obvIds[0], obvs);

			// return rv;
			List<Map<String, Object>> recoVotes = new ArrayList<Map<String, Object>>();

			Map<String, Object> obvListRecoVotesResult = new HashMap<String, Object>();

			for (Object[] obj : rv) {
				Map<String, Object> reco = new HashMap<String, Object>();
				reco.put("recoVoteId", ((java.math.BigInteger) obj[0]).longValue());
				reco.put("commonNameRecoId", obj[1]);
				reco.put("authorId", ((java.math.BigInteger) obj[2]).longValue());
				reco.put("votedOn", (Date) obj[3]);
				reco.put("comment", obj[4]);
				reco.put("originalAuthor", (String) obj[5]);
				reco.put("givenSciName", obj[6]);
				reco.put("givenCommonName", obj[7]);
				reco.put("recoId", ((java.math.BigInteger) obj[8]).longValue());
				reco.put("name", obj[9]);
				reco.put("isScientificName", obj[10]);
				reco.put("languageId",  obj[11]);
				reco.put("taxonConceptId",  obj[12]);
				reco.put("normalizedForm", obj[13]);
				reco.put("status", obj[14]);
				reco.put("speciesId", obj[15]);
				reco.put("observationId", ((java.math.BigInteger) obj[16]).longValue());
				reco.put("isLocked", obj[17]);
				reco.put("maxVotedRecoId", ((java.math.BigInteger) obj[18]).longValue());
				recoVotes.add(reco);
			}

			// trying

			Map<String, Object> recoMaps = new HashMap<String, Object>();
			for (Map<String, Object> recoVote : recoVotes) {
				// System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
				// System.out.println(obvListRecoVotesResult.get("'"+recoVote.get("observationId")+"'"));
				Map<String, Object> obvRecoVotesResult = (Map<String, Object>) obvListRecoVotesResult
						.get(((Long) recoVote.get("observationId")).toString());
				if (obvRecoVotesResult == null) {
					obvRecoVotesResult = new HashMap<String, Object>();
					obvRecoVotesResult.put("recoVotes", new ArrayList<Map<String, Object>>()); // checkin
																								// g%%%%%%%%%%%%%%%%%%%%%%%%%
					obvRecoVotesResult.put("totalVotes", 0);
					obvRecoVotesResult.put("uniqueVotes", 0);
					obvListRecoVotesResult.put(((Long) recoVote.get("observationId")).toString(), obvRecoVotesResult);
					recoMaps = recoMaps;
				}

				Map<String, Object> map = new HashMap<String, Object>();
				if (recoMaps.containsKey(recoVote.get("recoId").toString())) {
					map = (Map<String, Object>) recoMaps.get(recoVote.get("recoId").toString());
				} else {
					map.put("recoId", recoVote.get("recoId"));
					map.put("isScientificName", recoVote.get("isScientificName"));

					if (recoVote.get("taxonConceptId") != null) {
						map.put("speciesId", recoVote.get("speciesId"));
						map.put("normalizedForm", recoVote.get("normalizedForm"));
						if (recoVote.get("status").equals("SYNONYM") && recoVote.get("isScientificName").equals(true)) {
							map.put("synonymOf", recoVote.get("normalizedForm"));
						}
					}
					map.put("name", recoVote.get("name"));

					recoMaps.put(recoVote.get("recoId").toString(), map);
					List<Map<String, Object>> array = (List<Map<String, Object>>) obvRecoVotesResult.get("recoVotes");
					array.add(map); // testing ^^^^^^^^^^^^^^^^6
					obvRecoVotesResult.put("recoVotes", array);
				}

				if (recoVote.get("commonNameRecoId") != null) {
					if (!map.containsKey("commonNames")) {
						map.put("commonNames", new HashMap<String, Object>());
					}
					Recommendation cnReco = recommendationService
							.findById(((java.math.BigInteger) recoVote.get("commonNameRecoId")).longValue());
					Long cnLangId = (cnReco.getLanguageId() != null) ? (cnReco.getLanguageId()) : (Long) 205L; /// what
																												/// is
																												/// englishId
																												/// hack
					if (!((Map<String, Object>) map.get("commonNames")).containsKey(cnLangId.toString())) {
						((Map<String, Object>) map.get("commonNames")).put(cnLangId.toString(),
								new HashSet<Recommendation>());
					}
					Set<Recommendation> abc = (Set<Recommendation>) ((Map<String, Object>) map.get("commonNames"))
							.get(cnLangId.toString());
					abc.add(cnReco);
					((Map<String, Object>) map.get("commonNames")).put(cnLangId.toString(), abc);
					// dont know

				}

				if (!map.containsKey("authors")) {
					map.put("authors", new ArrayList<List<Object>>());
				}
				Map<String, Object> author = userService
						.findAuthorSignature(userService.findById((Long) recoVote.get("authorId")));
				// User originalAuthor = userService.findById((Long)
				// recoVote.get("originalAuthor"));
				List<List<Object>> userList = (List<List<Object>>) map.get("authors");
				List<Object> author_1 = new ArrayList<Object>();
				author_1.add(author);
				author_1.add(recoVote.get("originalAuthor"));
				userList.add(author_1);
				map.put("authors", userList);

				if (!map.containsKey("votedOn")) {
					map.put("votedOn", new ArrayList<String>());
				}
				List<String> voteDates = (List<String>) map.get("votedOn");
				voteDates.add(recoVote.get("votedOn").toString());
				map.put("votedOn", voteDates);

				if (!map.containsKey("noOfVotes")) {
					map.put("noOfVotes", 0);
				}
				int noOfVotes = ((int) map.get("noOfVotes")) + 1;
				map.put("noOfVotes", noOfVotes);

				if (recoVote.get("comment") != null) {
					if (!map.containsKey("recoComments")) {
						map.put("recoComments", "");
					}
					Map<String, Object> rc = new HashMap<String, Object>();
					rc.put("recoVoteId", recoVote.get("recoVoteId"));
					rc.put("comment", recoVote.get("comment"));
					rc.put("author", userService.findById((Long) recoVote.get("authorId")));
					rc.put("votedOn", recoVote.get("votedOn"));
					map.put("recoComments", rc);
				}

				map.put("obvId", recoVote.get("observationId"));
				map.put("isLocked", recoVote.get("isLocked"));

				if ((Boolean) recoVote.get("isLocked") == false) {
					map.put("showLock", true);
				} else {
					if (recoVote.get("recoId").equals(recoVote.get("maxVotedRecoId"))) {
						map.put("showLock", false);
					} else {
						map.put("showLock", true);
					}
				}

				map.put("hasObvLockPerm",
						hasObvLockPerm((Long) recoVote.get("observationId"), (Long) recoVote.get("recoId")));
				Long totalCommentCount = commentService.getTotalRecoCommentCount((Long) map.get("recoId"),
						(Long) recoVote.get("observationId"));
				System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");
				System.out.println(totalCommentCount);
				map.put("totalCommentCount", totalCommentCount);
			}

			for (Map.Entry<String, Object> entry : obvListRecoVotesResult.entrySet()) {
				System.out.println("*****************************************8");
				int totalVotes = 0;
				Map<String, Object> obvRecoVotesResult = (Map<String, Object>) entry.getValue();
				System.out.println("testing recoVotes");
				System.out.println(obvRecoVotesResult.get("recoVotes"));
				List<Map<String, Object>> a = (List<Map<String, Object>>) obvRecoVotesResult.get("recoVotes");
				for (Map<String, Object> entry2 : a) {
					Map<String, Object> map = (Map<String, Object>) entry2; // checking..............................
					totalVotes += (int) map.get("noOfVotes");
					if (map.get("commonNames") != null) {

						// to be written
						String cNames = Observation
								.getFormattedCommonNames((Map<String, Object>) map.get("commonNames"), true, languageService);
						map.put("commonNames", (cNames.equals("")) ? "" : "(" + cNames + ")");
					}

					for (List<Object> authors_1 : (List<List<Object>>) map.get("authors")) {
						// if(currentUser){ // what is currentUser
						// if(((User)authors_1.get(0)).getId() ==
						// currentUser.id){
						// map.put("disAgree", true);
						// }
						// }

					}
				}
				obvRecoVotesResult.put("totalVotes", totalVotes);
				obvRecoVotesResult.put("uniqueVotes",
						((List<Map<String, Object>>) obvRecoVotesResult.get("recoVotes")).size());
				List<Map<String, Object>> finalRecos = (List<Map<String, Object>>) obvRecoVotesResult.get("recoVotes");
				Collections.sort(finalRecos, new Comparator<Map<String, Object>>() {
					@Override
					public int compare(Map<String, Object> map1, Map<String, Object> map2) {
						if ((Integer) map1.get("noOfVotes") > (Integer) map2.get("noOfVotes")) {
							return +1;
						} else if ((Integer) map1.get("noOfVotes") < (Integer) map2.get("noOfVotes")) {
							return -1;
						} else {
							return 0;
						}
					}
				});
				obvRecoVotesResult.put("recoVotes", finalRecos); // have to sort
																	// later
			}
			return obvListRecoVotesResult;
		} catch (Exception e) {
			throw e;
		} finally {
			//
		}
	}

	private Boolean hasObvLockPerm(Long obvId, Long recoId) {
		Taxon taxon = (recommendationService.findById(recoId) != null)
				? recommendationService.findById(recoId).getTaxonConcept() : null;
		return AuthUtils.isLoggedIn();
	}

}