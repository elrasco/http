package it.lucarasconi.common.db;

import it.lucarasconi.common.exception.DBInitException;
import it.lucarasconi.common.utils.ApplicationProperties;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.session.SqlSession;

public class DBManager {
	
	static ConcurrentHashMap<String, MyBatisConnectionFactory> factories = new ConcurrentHashMap<String, MyBatisConnectionFactory>();
	static Object lock = new Object();

	/**
	 * 
	 * @param name
	 * @return connectionFactory per name
	 * @throws DBInitException 
	 */
	public static void init(String name) throws DBInitException {
		init(name, ApplicationProperties.newInstance().get("db." + name + ".config"));
	}
	
	public static void init(String name, String config) throws DBInitException {
		MyBatisConnectionFactory result = factories.get(name);
		if (result == null) {
			synchronized (lock) {
				if (result == null) {
					result = new MyBatisConnectionFactory(name);
					factories.put(name, result);
					try {
						result.init( new InputStreamReader( new FileInputStream(config) ) );
					} catch (Exception e) {
						factories.remove(name);
						throw new DBInitException(e);
					}
				}
			}
		}
	}
	
	public static SqlSession getSession(String name) {
		if (factories.get(name) == null) {
			throw new RuntimeException(new DBInitException());
		}
		return factories.get(name).getSqlSessionFactory().openSession();
	}
	
}
