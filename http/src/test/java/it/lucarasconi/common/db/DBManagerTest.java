package it.lucarasconi.common.db;

import static org.junit.Assert.*;

import java.io.File;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DBManagerTest {

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
	public void testGet() {
		//inizializzo la sessione
		SqlSessionFactory f1 = DBManager.get("EXPLORER").getSqlSessionFactory(new File("src/test/java/it/lucarasconi/common/db/danieli-config.xml"));
		SqlSessionFactory f2 = DBManager.get("EXPLORER").getSqlSessionFactory(new File("src/test/java/it/lucarasconi/common/db/danieli-config.xml"));
		SqlSessionFactory f3 = DBManager.get("TACS").getSqlSessionFactory(new File("src/test/java/it/lucarasconi/common/db/danieli-config.xml"));
		
		SqlSession s1 = DBManager.getSession("EXPLORER");
		SqlSession s2 = f1.openSession();
		SqlSession s3 = f1.openSession();
		
		assertTrue(f1 == f2);
		assertTrue(f1 != f3);
		assertTrue(s1 != s2);
		assertTrue(s2 != s3);
		assertTrue(s1 != s3);
		
		s1.close();
		s2.close();
		s3.close();
		
		MyBatisConnectionFactory nn = new MyBatisConnectionFactory("");
	}

}
