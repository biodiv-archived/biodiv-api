package biodiv.common;

import org.apache.commons.configuration2.Configuration;

import com.google.inject.Inject;

public class ESmoduleUrlService {

	@Inject
	Configuration config;
	
	public String getDownloadUrl(String index, String type, String geoField) {
		return config.getString("esmodule.url") + "/v1/services/download/" + index + "/" + type + "?filePath=" + config.getString("download.filePath") + "&geoField=" + geoField;
	}
	
	public String getTermsAggregationUrl(String index, String type, String field, String geoField, Integer size) {
		StringBuilder builder = new StringBuilder();
		builder.append(config.getString("esmodule.url"));
		builder.append("/v1/services/terms-aggregation/");
		builder.append(index);
		builder.append("/");
		builder.append(type);
		builder.append("?field=");
		builder.append(field);
		builder.append("&size=");
		builder.append(size);
		builder.append("&locationField=");
		builder.append(geoField);
		return builder.toString();
	}
}
