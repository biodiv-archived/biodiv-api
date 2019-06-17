package biodiv.observation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.configuration2.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import biodiv.common.NakshaUrlService;
import biodiv.maps.MapAggregationResponse;
import biodiv.maps.MapAndBoolQuery;
import biodiv.maps.MapAndRangeQuery;
import biodiv.maps.MapBiodivResponse;
import biodiv.maps.MapDocument;
import biodiv.maps.MapHttpResponse;
import biodiv.maps.MapIntegrationService;
import biodiv.maps.MapNakshaAggregate;
import biodiv.maps.MapResponse;
import biodiv.maps.MapSearchParams;
import biodiv.maps.MapSearchQuery;
import biodiv.maps.MapService;

public class ObservationListService implements MapService {


	public static final String URL = "http://localhost:8081/";
	private final Logger log = LoggerFactory.getLogger(getClass());

    @Inject
	Configuration config;

    @Inject
    NakshaUrlService nakshaUrlService;

    @Inject
	MapIntegrationService mapIntegrationService;
    
    @Inject
	private ObservationListService observationListService;

	@Override
	public MapResponse create(String index, String type, String documentId, String document) {

		// TODO Auto-generated method stub

		String newurl = config.getString("nakshaUrl")+"/services/data/" + index + "/" + type + "/" + documentId;

		/**
		 * Map integration service have required method to make respective calls
		 */

		MapIntegrationService mapIntegrationService = new MapIntegrationService();
		/**
		 * Wrapper class for observation
		 */
		ObservationListObject object = new ObservationListObject();
		object.setDocument(document);

		/**
		 * MapResponse is generic class to handle all the responses
		 */

		MapHttpResponse res = mapIntegrationService.postRequest(newurl, object);
		/**
		 * ObjectMapper provided by the jackson for serializing and
		 * deserializing the java object to JSON.
		 */
		ObjectMapper mapper = new ObjectMapper();
		try {
//			return mapper.readValue(res.getDocument(), MapResponse.class);
			
			
		} catch (Exception e) {

		}
		return null;
	}

	@Override
	public MapHttpResponse fetch(String index, String type, String documentId) {
		// // TODO Auto-generated method stub
		MapIntegrationService mapIntegrationService = new MapIntegrationService();
		String newurl = config.getString("nakshaUrl")+"/services/data/" + index + "/" + type + "/" + documentId;
		MapHttpResponse content = mapIntegrationService.getRequest(newurl);
		return content;
	}

	@Override
	public MapResponse delete(String index, String type, String documentId) {
		String newurl = config.getString("nakshaUrl")+"/services/data/"+index + "/" + type+"/"+documentId;
		MapIntegrationService mapIntegrationService = new MapIntegrationService();
		MapResponse mapResponse=mapIntegrationService.deleteSingleDocument(newurl);
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MapResponse update(String index, String type, String documentId, String document) {
		String newurl = config.getString("nakshaUrl")+"/services/data/"+index + "/" + type+"/"+documentId;
		MapIntegrationService mapIntegrationService = new MapIntegrationService();
			MapResponse mapResponse=mapIntegrationService.updateSingleDocument(newurl,document);
			

		
		return null;
	}

	@Override
	public List<MapResponse> bulkUpload(String index, String type, String jsonArray) {
		// TODO Auto-generated method stub
		return null;
	}
/**
 * For single id observation object search
 */
	@Override
	public MapHttpResponse termSearch(String index, String type, String key, String value, Integer from, Integer limit) {
		// TODO Auto-generated method stub
		String newurl = config.getString("nakshaUrl")+"/services/term-search/" + index + "/" + type+"?"+"key="+key+"&value="+value;
		MapIntegrationService mapIntegrationService = new MapIntegrationService();
		MapHttpResponse mapHttpResponse= mapIntegrationService.getSingleSearch(newurl);	
		return mapHttpResponse;
	}

	@Override
	public List<String> searchBool(String index, String type, List<MapAndBoolQuery> queries, Integer from, Integer limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> searchRange(String index, String type, List<MapAndRangeQuery> queries, Integer from,
			Integer limit) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public MapNakshaAggregate getAggregate(String index, String type,String filter, MapSearchQuery searchQuery) {
		String newurl= config.getString("nakshaUrl")+"/services/aggregation/" + index + "/" + type+"/"+filter;
		log.debug("Searching at : {}", newurl);
		return mapIntegrationService.postAggregation(newurl, searchQuery);
	}
	
	@Override
	public MapBiodivResponse search(String index, String type, MapSearchQuery querys, String geoAggregationField, Integer geoAggegationPrecision, Boolean onlyFilteredAggregation, String termsAggregationField) {
		String newurl= config.getString("nakshaUrl")+"/services/search/" + index + "/" + type+"?geoAggregationField="+geoAggregationField +"&geoAggegationPrecision="+geoAggegationPrecision;
		log.debug("Searching at : {}", newurl);
		if(onlyFilteredAggregation != null && onlyFilteredAggregation == true) {
			newurl += "&onlyFilteredAggregation=true";
		}
		if(termsAggregationField != null) {
			newurl += "&termsAggregationField=" + termsAggregationField;
		}
		
		return mapIntegrationService.postSearch(newurl,querys);

	}

	@Override
	public MapDocument termsAggregation(String index, String type, String field, String locationField, Integer size, MapSearchQuery mapSearchQuery) {
		String url = nakshaUrlService.getTermsAggregationUrl(index, type, field, locationField, size);
		MapHttpResponse mapHttpResponse = mapIntegrationService.postRequest(url, mapSearchQuery);
		MapDocument mapDocument = new MapDocument();
		mapDocument.setDocument(mapHttpResponse.getDocument().toString());
		return mapDocument;
	}
	
	public void uploadSettingsAndMappings(String index, String settingsAndMappings) {
		// TODO Auto-generated method stub
		String newurl=config.getString("nakshaUrl")+"/services/mapping/"+index;
		MapIntegrationService mapIntegrationService = new MapIntegrationService();
		MapHttpResponse mapHttpResponse= mapIntegrationService.uploadSettingAndMappings(newurl,settingsAndMappings);
		
	}

	@Override
	public MapAggregationResponse mapAggregate(String index, String type, String sGroup, String taxon, String user,
			String userGroupList, String webaddress, String speciesName, String mediaFilter, String months,
			String isFlagged, String minDate, String maxDate, String validate, Map<String, List<String>> traitParams,
			Map<String, List<String>> customParams, String classificationid, MapSearchParams mapSearchParams,
			String maxvotedrecoid, String createdOnMaxDate, String createdOnMinDate, String status, String taxonId,
			String recoName) {
		
		MapSearchQuery mapSearchQuery = ObservationControllerHelper.getMapSearchQuery(sGroup, taxon, user,
				userGroupList, webaddress, speciesName, mediaFilter,months , isFlagged, minDate, maxDate, validate,
				traitParams, customParams, classificationid, mapSearchParams, maxvotedrecoid, createdOnMaxDate,
				createdOnMinDate, status, taxonId, recoName);
		
		MapSearchQuery mapSearchQueryFilter;
		
		
		String omiter=null;
		MapAggregationResponse aggregationResponse = new MapAggregationResponse();
		
		
		if(sGroup!=null && taxon!= null) {
			
			mapSearchQueryFilter = ObservationControllerHelper.getMapSearchQuery(omiter,omiter, user,
					userGroupList, webaddress, speciesName, mediaFilter,months , isFlagged, minDate, maxDate, validate,
					traitParams, customParams, classificationid, mapSearchParams, maxvotedrecoid, createdOnMaxDate,
					createdOnMinDate, status, taxonId, recoName);
			aggregationResponse.setGroupSpeciesName(observationListService.getAggregate(index, type,"speciesgroupname.keyword", mapSearchQueryFilter).getGroupAggregation());
		}
		else {
			aggregationResponse.setGroupSpeciesName(observationListService.getAggregate(index, type,"speciesgroupname.keyword", mapSearchQuery).getGroupAggregation());
		}
		if(status != null) {
			mapSearchQueryFilter = ObservationControllerHelper.getMapSearchQuery(sGroup,taxon, user,
					userGroupList, webaddress, speciesName, mediaFilter,months , isFlagged, minDate, maxDate, validate,
					traitParams, customParams, classificationid, mapSearchParams, maxvotedrecoid, createdOnMaxDate,
					createdOnMinDate, omiter, taxonId, recoName);
			aggregationResponse.setGroupStatus(observationListService.getAggregate(index, type, "status.keyword",mapSearchQueryFilter).getGroupAggregation());
		}
		else {
			aggregationResponse.setGroupStatus(observationListService.getAggregate(index, type, "status.keyword",mapSearchQuery).getGroupAggregation());
		}
		if(userGroupList != null)
		{
			mapSearchQueryFilter = ObservationControllerHelper.getMapSearchQuery(sGroup,taxon, user,
					omiter, webaddress, speciesName, mediaFilter,months , isFlagged, minDate, maxDate, validate,
					traitParams, customParams, classificationid, mapSearchParams, maxvotedrecoid, createdOnMaxDate,
					createdOnMinDate, status, taxonId, recoName);
			aggregationResponse.setGroupUserGroupName(observationListService.getAggregate(index, type, "usergroupname.keyword",mapSearchQueryFilter).getGroupAggregation());
		}else {
			aggregationResponse.setGroupUserGroupName(observationListService.getAggregate(index, type, "usergroupname.keyword",mapSearchQuery).getGroupAggregation());
		}
		if(isFlagged != null)
		{
			mapSearchQueryFilter = ObservationControllerHelper.getMapSearchQuery(sGroup,taxon, user,
					userGroupList, webaddress, speciesName, mediaFilter,months , omiter, minDate, maxDate, validate,
					traitParams, customParams, classificationid, mapSearchParams, maxvotedrecoid, createdOnMaxDate,
					createdOnMinDate, status, taxonId, recoName);
			aggregationResponse.setGroupFlag(observationListService.getAggregate(index, type, "flagcount",mapSearchQueryFilter).getGroupAggregation());
		}else {
			aggregationResponse.setGroupFlag(observationListService.getAggregate(index, type, "flagcount",mapSearchQuery).getGroupAggregation());
		}
		if(validate != null)
		{
			mapSearchQueryFilter = ObservationControllerHelper.getMapSearchQuery(sGroup,taxon, user,
					userGroupList, webaddress, speciesName, mediaFilter,months , isFlagged, minDate, maxDate, omiter,
					traitParams, customParams, classificationid, mapSearchParams, maxvotedrecoid, createdOnMaxDate,
					createdOnMinDate, status, taxonId, recoName);
			aggregationResponse.setGroupValidate(observationListService.getAggregate(index, type, "islocked",mapSearchQueryFilter).getGroupAggregation());
		}else {
			aggregationResponse.setGroupValidate(observationListService.getAggregate(index, type, "islocked",mapSearchQuery).getGroupAggregation());
		}
		if(months != null)
		{
			mapSearchQueryFilter = ObservationControllerHelper.getMapSearchQuery(sGroup,taxon, user,
					userGroupList, webaddress, speciesName, mediaFilter,omiter , isFlagged, minDate, maxDate, validate,
					traitParams, customParams, classificationid, mapSearchParams, maxvotedrecoid, createdOnMaxDate,
					createdOnMinDate, status, taxonId, recoName);
			aggregationResponse.setGroupMonth(observationListService.getAggregate(index, type, "frommonth",mapSearchQueryFilter).getGroupAggregation());
		}else {
			aggregationResponse.setGroupMonth(observationListService.getAggregate(index, type, "frommonth",mapSearchQuery).getGroupAggregation());
		}
		if(mediaFilter != null) {
			mapSearchQueryFilter = ObservationControllerHelper.getMapSearchQuery(sGroup,taxon, user,
					userGroupList, webaddress, speciesName, mediaFilter,months , isFlagged, minDate, maxDate, validate,
					traitParams, customParams, classificationid, mapSearchParams, maxvotedrecoid, createdOnMaxDate,
					createdOnMinDate, status, taxonId, recoName);
			
			aggregationResponse.setGroupAudio(getTotal(observationListService.getAggregate(index, type, "noofaudio", mapSearchQuery).getGroupAggregation()));
			aggregationResponse.setGroupVideo(getTotal(observationListService.getAggregate(index, type, "noofvideos", mapSearchQuery).getGroupAggregation()));
			aggregationResponse.setGroupImages(getTotal(observationListService.getAggregate(index, type, "noofimages", mapSearchQuery).getGroupAggregation()));
			aggregationResponse.setGroupNoMedia(getTotal(observationListService.getAggregate(index, type, "nomedia.keyword", mapSearchQuery).getGroupAggregation()));
			
		}
		else {
			aggregationResponse.setGroupAudio(getTotal(observationListService.getAggregate(index, type, "noofaudio", mapSearchQuery).getGroupAggregation()));
			aggregationResponse.setGroupVideo(getTotal(observationListService.getAggregate(index, type, "noofvideos", mapSearchQuery).getGroupAggregation()));
			aggregationResponse.setGroupImages(getTotal(observationListService.getAggregate(index, type, "noofimages", mapSearchQuery).getGroupAggregation()));
			aggregationResponse.setGroupNoMedia(getTotal(observationListService.getAggregate(index, type, "nomedia.keyword", mapSearchQuery).getGroupAggregation()));
			
		}	
		if(speciesName!=null) {
			mapSearchQueryFilter = ObservationControllerHelper.getMapSearchQuery(sGroup, taxon, user,
					userGroupList, webaddress, omiter, mediaFilter,months , isFlagged, minDate, maxDate, validate,
					traitParams, customParams, classificationid, mapSearchParams, maxvotedrecoid, createdOnMaxDate,
					createdOnMinDate, status, taxonId, recoName);
			aggregationResponse.setGroupIdentificationNameExists(observationListService.getAggregate(index, type, "name", mapSearchQueryFilter).getGroupAggregation());
			
		}
		else {
			aggregationResponse.setGroupIdentificationNameExists(observationListService.getAggregate(index, type, "name", mapSearchQuery).getGroupAggregation());
		}
		if(taxonId!=null) {
			mapSearchQueryFilter = ObservationControllerHelper.getMapSearchQuery(sGroup, taxon, user,
					userGroupList, webaddress, speciesName, mediaFilter,months , isFlagged, minDate, maxDate, validate,
					traitParams, customParams, classificationid, mapSearchParams, maxvotedrecoid, createdOnMaxDate,
					createdOnMinDate, status, omiter, recoName);
			aggregationResponse.setGroupTaxonIDExists(observationListService.getAggregate(index, type, "status", mapSearchQueryFilter).getGroupAggregation());
			
		}
		else {
			aggregationResponse.setGroupTaxonIDExists(observationListService.getAggregate(index, type, "status", mapSearchQuery).getGroupAggregation());
		}
		
		if(!traitParams.isEmpty()) {
			
			List<String> temp = new ArrayList<String>();
			if(traitParams.containsKey("trait_11.string")) {
				temp= traitParams.remove("trait_11.string");
				mapSearchQueryFilter = ObservationControllerHelper.getMapSearchQuery(sGroup, taxon, user,
						userGroupList, webaddress, speciesName, mediaFilter,months , isFlagged, minDate, maxDate, validate,
						traitParams, customParams, classificationid, mapSearchParams, maxvotedrecoid, createdOnMaxDate,
						createdOnMinDate, status, taxonId, recoName);
				aggregationResponse.setGroupPhenologyState(observationListService.getAggregate(index, type, "traits.trait_11.keyword", mapSearchQueryFilter).getGroupAggregation());
				traitParams.put("trait_11.string", temp);
			}
			else {
				aggregationResponse.setGroupPhenologyState(observationListService.getAggregate(index, type, "traits.trait_11.keyword", mapSearchQuery).getGroupAggregation());
			}
			if(traitParams.containsKey("trait_13.string")) {
				temp = traitParams.remove("trait_13.string");
				mapSearchQueryFilter = ObservationControllerHelper.getMapSearchQuery(sGroup, taxon, user,
						userGroupList, webaddress, speciesName, mediaFilter,months , isFlagged, minDate, maxDate, validate,
						traitParams, customParams, classificationid, mapSearchParams, maxvotedrecoid, createdOnMaxDate,
						createdOnMinDate, status, taxonId, recoName);
				aggregationResponse.setGroupAbundance(observationListService.getAggregate(index, type, "traits.trait_13.keyword", mapSearchQueryFilter).getGroupAggregation());
				traitParams.put("trait_13.string", temp);
			}
			else {
				aggregationResponse.setGroupAbundance(observationListService.getAggregate(index, type, "traits.trait_13.keyword", mapSearchQuery).getGroupAggregation());
			}
			if(traitParams.containsKey("trait_8.string")) {
				temp = traitParams.remove("trait_8.string");
				mapSearchQueryFilter = ObservationControllerHelper.getMapSearchQuery(sGroup, taxon, user,
						userGroupList, webaddress, speciesName, mediaFilter,months , isFlagged, minDate, maxDate, validate,
						traitParams, customParams, classificationid, mapSearchParams, maxvotedrecoid, createdOnMaxDate,
						createdOnMinDate, status, taxonId, recoName);
				aggregationResponse.setGroupSex(observationListService.getAggregate(index, type, "traits.trait_8.keyword", mapSearchQueryFilter).getGroupAggregation());
				traitParams.put("trait_8.string", temp);
			}
			else {
				aggregationResponse.setGroupSex(observationListService.getAggregate(index, type, "traits.trait_8.keyword", mapSearchQuery).getGroupAggregation());
			}
			if(traitParams.containsKey("trait_9.string")) {
				temp = traitParams.remove("trait_9.string");
				mapSearchQueryFilter = ObservationControllerHelper.getMapSearchQuery(sGroup, taxon, user,
						userGroupList, webaddress, speciesName, mediaFilter,months , isFlagged, minDate, maxDate, validate,
						traitParams, customParams, classificationid, mapSearchParams, maxvotedrecoid, createdOnMaxDate,
						createdOnMinDate, status, taxonId, recoName);
				aggregationResponse.setGroupLifeStage(observationListService.getAggregate(index, type, "traits.trait_9.keyword", mapSearchQueryFilter).getGroupAggregation());
				traitParams.put("trait_9.string", temp);
			}
			else {
				aggregationResponse.setGroupLifeStage(observationListService.getAggregate(index, type, "traits.trait_9.keyword", mapSearchQuery).getGroupAggregation());
			}
			if(traitParams.containsKey("trait_12.string")) {
				temp = traitParams.remove("trait_12.string");
				mapSearchQueryFilter = ObservationControllerHelper.getMapSearchQuery(sGroup, taxon, user,
						userGroupList, webaddress, speciesName, mediaFilter,months , isFlagged, minDate, maxDate, validate,
						traitParams, customParams, classificationid, mapSearchParams, maxvotedrecoid, createdOnMaxDate,
						createdOnMinDate, status, taxonId, recoName);
				aggregationResponse.setGroupHabitatType(observationListService.getAggregate(index, type, "traits.trait_12.keyword", mapSearchQueryFilter).getGroupAggregation());
				traitParams.put("trait_12.string", temp);
			}
			else {
				aggregationResponse.setGroupHabitatType(observationListService.getAggregate(index, type, "traits.trait_12.keyword", mapSearchQuery).getGroupAggregation());
			}
			if(traitParams.containsKey("trait_10.string")) {
				temp = traitParams.remove("trait_10.string");
				mapSearchQueryFilter = ObservationControllerHelper.getMapSearchQuery(sGroup, taxon, user,
						userGroupList, webaddress, speciesName, mediaFilter,months , isFlagged, minDate, maxDate, validate,
						traitParams, customParams, classificationid, mapSearchParams, maxvotedrecoid, createdOnMaxDate,
						createdOnMinDate, status, taxonId, recoName);
				aggregationResponse.setGroupLifeStageIncomplete(observationListService.getAggregate(index, type, "traits.trait_10.keyword", mapSearchQueryFilter).getGroupAggregation());
				traitParams.put("trait_10.string", temp);
			}
			else {
				aggregationResponse.setGroupLifeStageIncomplete(observationListService.getAggregate(index, type, "traits.trait_10.keyword", mapSearchQuery).getGroupAggregation());
			}
		}
		else {
			aggregationResponse.setGroupPhenologyState(observationListService.getAggregate(index, type, "traits.trait_11.keyword", mapSearchQuery).getGroupAggregation());
			aggregationResponse.setGroupAbundance(observationListService.getAggregate(index, type, "traits.trait_13.keyword", mapSearchQuery).getGroupAggregation());
			aggregationResponse.setGroupSex(observationListService.getAggregate(index, type,"traits.trait_8.keyword" , mapSearchQuery).getGroupAggregation());
			aggregationResponse.setGroupLifeStage(observationListService.getAggregate(index, type, "traits.trait_9.keyword", mapSearchQuery).getGroupAggregation());
			aggregationResponse.setGroupHabitatType(observationListService.getAggregate(index, type, "traits.trait_12.keyword", mapSearchQuery).getGroupAggregation());
			aggregationResponse.setGroupLifeStageIncomplete(observationListService.getAggregate(index, type, "traits.trait_10.keyword", mapSearchQuery).getGroupAggregation());
		}
		
		return aggregationResponse;
	}
	
	private Long getTotal(HashMap<Object, Long> media){
		Long sum = 0L;
		
		for(Map.Entry<Object, Long> entry: media.entrySet()) {
			if(!(entry.getKey().equals("0"))) {
				sum+=entry.getValue();
			}
		}
		return sum;
	}

}
