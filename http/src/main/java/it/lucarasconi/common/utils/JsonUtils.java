package it.lucarasconi.common.utils;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtils {

	public static String write(Object results) throws JsonGenerationException, JsonMappingException, IOException {
		ObjectMapper om = new ObjectMapper();
		return om.writeValueAsString(results);
	}
	
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String,Object> read(String json)  throws JsonGenerationException, JsonMappingException, IOException{
		ObjectMapper om = new ObjectMapper();
		return (LinkedHashMap<String, Object>) om.readValue(json, Object.class);
	}
}
