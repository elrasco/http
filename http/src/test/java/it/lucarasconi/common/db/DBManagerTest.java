package it.lucarasconi.common.db;

import static org.junit.Assert.*;

import it.lucarasconi.common.exception.DBInitException;

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
	public void testGet() throws DBInitException {
		//inizializzo la sessione
		DBManager.init("EXPLORER", "src/test/java/it/lucarasconi/common/db/danieli-config.xml");
		DBManager.init("EXPLORER", "src/test/java/it/lucarasconi/common/db/danieli-config.xml");
		DBManager.init("TACS", "src/test/java/it/lucarasconi/common/db/danieli-config.xml");
		
		SqlSession s1 = DBManager.getSession("EXPLORER");
		SqlSession s2 = DBManager.getSession("EXPLORER");
		SqlSession s3 = DBManager.getSession("EXPLORER");
		
		
		
		s1.close();
		s2.close();
		s3.close();
		
		MyBatisConnectionFactory nn = new MyBatisConnectionFactory("");
	}

}
