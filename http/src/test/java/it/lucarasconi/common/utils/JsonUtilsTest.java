package it.lucarasconi.common.utils;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class JsonUtilsTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWriteValueAsString() {
		fail("Not yet implemented");
	}

	@Test
	public void testRead() {
		String json = 
				"{                                                                                                                                                                                                                                                                                                                                       " +
						"   \"html_attributions\" : [],                                                                                                                                                                                                                                                                                                          " +
						"   \"results\" : [                                                                                                                                                                                                                                                                                                                      " +
						"      {                                                                                                                                                                                                                                                                                                                                 " +
						"         \"formatted_address\" : \"Via Giuseppe Garibaldi, 64026 Roseto degli Abruzzi TE, Italia\",                                                                                                                                                                                                                                     " +
						"         \"geometry\" : {                                                                                                                                                                                                                                                                                                               " +
						"            \"location\" : {                                                                                                                                                                                                                                                                                                            " +
						"               \"lat\" : 42.67724890,                                                                                                                                                                                                                                                                                                   " +
						"               \"lng\" : 14.01375080                                                                                                                                                                                                                                                                                                    " +
						"            }                                                                                                                                                                                                                                                                                                                           " +
						"         },                                                                                                                                                                                                                                                                                                                             " +
						"         \"icon\" : \"http://maps.gstatic.com/mapfiles/place_api/icons/geocode-71.png\",                                                                                                                                                                                                                                                " +
						"         \"id\" : \"2075b7aee7141856c6e4c25f64a23314802fa705\",                                                                                                                                                                                                                                                                         " +
						"         \"name\" : \"Via Giuseppe Garibaldi\",                                                                                                                                                                                                                                                                                         " +
						"         \"reference\" : \"CrQBpQAAAA4EgAFPvvcZTaxJuQa58DtFnDLnu1NHys_Si_HlCdhBaxeRAu51PLfgwzlmz60InYgftN8NJmJ-WbxAx-OW9b2K3N36exsqord5rGDR_j4mJKufUZJslTRuESHvPRSb4U9CFozhvTzSQQGBSkT79hJURQtRSVuICAbOeJvTRJ7DEYA6m-g21P1X9TeOw_uYGoLYnU0hO0cpIKvAeUdGZzr5bEY2U38lJQTtrlBVngBPEhCl8Ukj2IkqIZSR6AgmJEAsGhQxEeWqplTANWG284ufizD9ZMJmjA\"," +
						"         \"types\" : [ \"route\" ]                                                                                                                                                                                                                                                                                                      " +
						"      }                                                                                                                                                                                                                                                                                                                                 " +
						"   ],                                                                                                                                                                                                                                                                                                                                   " +
						"   \"status\" : \"OK\"                                                                                                                                                                                                                                                                                                                  " +
						"}                                                                                                                                                                                                                                                                                                                                       " ;                                                                                                                                                                                                                                                                                                                                
		
		LinkedHashMap<String, Object> o = null;
		try {
			o = JsonUtils.read(json);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(o);
		
		assertTrue(true);
	}

}
