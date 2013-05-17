package it.lucarasconi.common.db;

import it.lucarasconi.common.utils.ApplicationProperties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

public class MyBatisConnectionFactory {

	SqlSessionFactory sqlSessionFactory;
	Logger logger = Logger.getLogger(this.getClass());
	private String environment = ApplicationProperties.newInstance().get("db." + getName() + ".env", "DEV");
	private String name;
	private String resource = MyBatisConnectionFactory.class.getPackage().getName().replaceAll("\\.", "/") + "/default-config.xml";
	private static final Object lock = new Object();
	
	static {
		org.apache.ibatis.logging.LogFactory.useLog4JLogging();
	}
	
	protected MyBatisConnectionFactory(String name) {
		this.name = name;
	}
	
	public void init(Reader reader, Properties myBatisProperties) {
		if (sqlSessionFactory == null) {
			synchronized (lock) {
				if (sqlSessionFactory == null) {
					if (logger.isInfoEnabled()) {
						logger.info(String.format("building session for environment %s (%s)", environment, myBatisProperties));
					}
					if (myBatisProperties == null) {
						sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, environment);
					} else {
						sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, environment, myBatisProperties);
					}
				}
			}	
		}
		try {
			reader.close();
		} catch (IOException e) {
			//log?
		}
	}
	
	public void init(Reader reader) {
		init(reader, null);
	}
	
	public void init() {
		init(resource, null);
	}
	
	private void init(String _resource, Properties myBatisProperties) {
		try {
			init(getReader(_resource), myBatisProperties);
		} catch (FileNotFoundException e) {
			logger.fatal(String.format("resource %s not found ", _resource), e);
		}
		catch (IOException e) {
			logger.fatal(String.format("resource %s not found ", _resource), e);
		} catch (Exception e) {
			logger.fatal(String.format("somthing went wrong in building db session"), e);
		}
	}
	
	private void init(File conf, Properties myBatisProperties) {
		resource = conf.getAbsolutePath();
		try {
			init(getReader(conf), myBatisProperties);
		} catch (FileNotFoundException e) {
			logger.fatal(String.format("resource %s not found ", conf), e);
		}
		catch (IOException e) {
			logger.fatal(String.format("resource %s not found ", conf), e);
		} catch (Exception e) {
			logger.fatal(String.format("somthing went wrong in building db session"), e);
		}
	}
	
	public SqlSessionFactory getSqlSessionFactory() {
		init(resource, null);
		return sqlSessionFactory;
	}
	
	public SqlSessionFactory getSqlSessionFactory(Properties myBatisProperties) {
		init(resource, myBatisProperties);
		return sqlSessionFactory;
	}
	
	public SqlSessionFactory getSqlSessionFactory(String ENV, Properties myBatisProperties) {
		environment = ENV;
		return getSqlSessionFactory(myBatisProperties);
	}
	
	public SqlSessionFactory getSqlSessionFactory(File conf) {
		init(conf, null);
		return sqlSessionFactory;
	}
	
	public SqlSessionFactory getSqlSessionFactory(File conf, Properties myBatisProperties) {
		init(conf, myBatisProperties);
		return sqlSessionFactory;
	}

	public SqlSessionFactory getSqlSessionFactory(File conf, String ENV, Properties myBatisProperties) {
		environment = ENV;
		return getSqlSessionFactory(conf, myBatisProperties);
	}
	
	private Reader getReader (String resource) throws FileNotFoundException, IOException {
		Reader reader = null;
		InputStream in = Resources.getResourceAsStream(resource);
		reader = new InputStreamReader(in);
		return reader;
	}
	
	private Reader getReader (File conf) throws FileNotFoundException, IOException {
		Reader reader = null;
		InputStream in = new FileInputStream(conf);
		reader = new InputStreamReader(in);
		return reader;
	}
	
	/**
	 * Closes all active and idle connections in the pool (if used POOLED)
	 */
	public void forceCloseAll() {
		if (sqlSessionFactory == null) {
			return;
		}
		if (logger.isInfoEnabled()) {
			logger.info("try to close active and idle connections...");
		}
		try {
			DataSource ds = sqlSessionFactory.getConfiguration().getEnvironment().getDataSource();
			((PooledDataSource) ds).forceCloseAll();
		} catch (Exception e) {
			logger.error("error closing connections", e);
		}
	}
	
	public String getName() {
		return name;
	}
}
