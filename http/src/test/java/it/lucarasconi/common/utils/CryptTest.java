package it.lucarasconi.common.utils;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CryptTest {

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
	public void testEncrypt() {
		try {
			String cleartext = "benvenuti in questo fantastico mondo";
			String encrypted = Crypt.encrypt(cleartext);
			System.out.println(encrypted);
			assertTrue( cleartext.equals(Crypt.decrypt(encrypted)) );
		} catch (Exception e) {
			fail(e.getMessage());
		}
		
	}

	@Test
	public void testDecrypt() {
		try {
			Crypt.decrypt(Crypt.encrypt("benvenuti èèè @@@@ in questo fantastico mondo"));
			assertTrue(true);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
