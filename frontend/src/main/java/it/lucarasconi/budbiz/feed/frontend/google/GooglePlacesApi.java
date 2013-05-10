package it.lucarasconi.budbiz.feed.frontend.google;

import it.lucarasconi.common.http.Request;
import it.lucarasconi.common.http.Response;
import it.lucarasconi.common.utils.JsonUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

public class GooglePlacesApi {
	
	private static String apikey = "AIzaSyDU2TXaEQfkPa33W5n71WfY-bV5AmfXMGY";
	
	/**
	 * 
	 * @param dove
	 * @return
	 */
	public static Map<String, Object> searchPlace(String dove) {
		
		Request req = new Request("https://maps.googleapis.com/maps/api/place/textsearch/json");
		req.addParam("key", apikey);
		req.addParam("query", dove);
		req.addParam("sensor", "false");
		req.addParam("language", "it");
		req.addParam("types", "administrative_area_level_1|administrative_area_level_2|administrative_area_level_3|colloquial_area|country|floor|geocode|intersection|locality|natural_feature|neighborhood|political|point_of_interest|post_box|postal_code|postal_code_prefix|postal_town|premise|room|route|street_address|street_number|sublocality|sublocality_level_4|sublocality_level_5|sublocality_level_3|sublocality_level_2|sublocality_level_1|subpremise|transit_station");
		
		Response res;
		try {
			res = req.get();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		try {
			return JsonUtils.read(res.getContent());
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static String placeDetails(String dove) {
		Map<String, Object> result = searchPlace(dove);
		
		if (result.get("status").equals("OK")) {

				Request req = new Request("https://maps.googleapis.com/maps/api/place/details/json");
				req.addParam("key", apikey);
				req.addParam("reference ", ( (Map<String, Object>)((ArrayList<Object>)result.get("results") ).get(0)).get("reference").toString());
				req.addParam("sensor", "false");
				req.addParam("language", "it");
				Response res;
				try {
					res = req.get();
				} catch (IOException e) {
					e.printStackTrace();
					return "";
				}
				return res.getContent();

		}
		return "";
	}
}
