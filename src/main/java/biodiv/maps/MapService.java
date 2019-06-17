package biodiv.maps;

import java.util.List;
import java.util.Map;

/**
 * 
 * The services available from map module
 */
public interface MapService {

	public MapResponse create(String index, String type, String documentId, String document);

	public MapHttpResponse fetch(String index, String type, String documentId);

	public MapResponse delete(String index, String type, String documentId);

	public MapResponse update(String index, String type, String documentId, String document);

	public List<MapResponse> bulkUpload(String index, String type, String jsonArray);

	public MapHttpResponse termSearch(String index, String type, String key, String value, Integer from, Integer limit);

	public MapBiodivResponse search(String index, String type, MapSearchQuery query, String geoAggregationField,
			Integer geoAggegationPrecision, Boolean onlyFilteredAggregation, String termsAggregationField);

	public List<String> searchBool(String index, String type, List<MapAndBoolQuery> queries, Integer from,
			Integer limit);

	public List<String> searchRange(String index, String type, List<MapAndRangeQuery> queries, Integer from,
			Integer limit);

	MapDocument termsAggregation(String index, String type, String field, String locationField, Integer size,
			MapSearchQuery mapSearchQuery);
	
	MapNakshaAggregate getAggregate(String index, String type,String filter, MapSearchQuery searchQuery);
	
	MapAggregationResponse mapAggregate(String index,String type,String sGroup, String taxon, String user, String userGroupList,
			String webaddress, String speciesName, String mediaFilter, String months, String isFlagged, String minDate,
			String maxDate, String validate, Map<String, List<String>> traitParams,
			Map<String, List<String>> customParams, String classificationid, MapSearchParams mapSearchParams,
			String maxvotedrecoid, String createdOnMaxDate, String createdOnMinDate, String status, String taxonId,
			String recoName);
}
