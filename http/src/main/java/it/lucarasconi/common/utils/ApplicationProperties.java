package it.lucarasconi.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public final class ApplicationProperties {

	private static ApplicationProperties singleton;
	Properties p;
	Properties ptemp;
	private static String lock = "";
	
	public ApplicationProperties(Properties p) {
		this.p = p;
	}

	public static ApplicationProperties newInstance() {
		if (singleton == null) {
			synchronized (lock) {
				if (singleton == null) {
					singleton = new ApplicationProperties(new Properties());
				}
			}
		} 
		return singleton;
	}
	
	public void load(String file) {
		ptemp = new Properties();
		try {
			ptemp.load(new FileInputStream(new File(file)));
			p.putAll(ptemp);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ptemp = null;
		}
	} 
	
	public void load(InputStream is) {
		ptemp = new Properties();
		try {
			ptemp.load(is);
			p.putAll(ptemp);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ptemp = null;
		}
	} 
	
	public void clear() {
		p.clear();
	}
	
	public String get(String key) {
		return p.getProperty(key);
	}
	
	public String get(String key, String defaultValue) {
		return p.getProperty(key, defaultValue);
	}
	
	public void add(String key, String value) {
		p.put(key, value);
	}
}
