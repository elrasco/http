package it.lucarasconi.common.db;

import it.lucarasconi.common.utils.ApplicationProperties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.ibatis.session.SqlSession;

public class DBManager {
	
	static ConcurrentHashMap<String, MyBatisConnectionFactory> factories = new ConcurrentHashMap<>();
	static ConcurrentHashMap<String, Boolean> initializations = new ConcurrentHashMap<>();
	static Object lock = new Object();

	/**
	 * 
	 * @param name
	 * @return connectionFactory per name
	 */
	public static MyBatisConnectionFactory get(String name) {
		MyBatisConnectionFactory result = factories.get(name);
		if (result == null) {
			synchronized (lock) {
				if (result == null) {
					result = new MyBatisConnectionFactory(name);
					factories.put(name, result);
					initializations.put(name, false);
					try {
						result.init(new InputStreamReader(new FileInputStream(ApplicationProperties.newInstance().get("db." + name + ".config"))));
						initializations.put(name, true);
					} catch (FileNotFoundException e) {
						//TODO: log
					} catch (NullPointerException e) {
						//TODO: log 
					}
				}
			}
		}
		return result;
	}
	
	public static SqlSession getSession(String name) {
		if (!initializations.get(name)) {
			throw new RuntimeException(String.format("missing configuration for %s", name));
		}
		return factories.get(name).getSqlSessionFactory().openSession();
	}
	
}
