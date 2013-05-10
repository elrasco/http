package it.lucarasconi.budbiz.feed.frontend.google;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GooglePlacesApiTest {

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
	public void testSearchPlace() {
		Map<String, Object> res = GooglePlacesApi.searchPlace("roseto te");
		
		assertTrue(true);
	}
	
	@Test
	public void testPlaceDetail() {
		String res = GooglePlacesApi.placeDetails("roseto te, via garibaldi, 108");
		System.out.println(res);
		assertTrue(true);
	}

}
