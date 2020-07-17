package com.autozi.passcar.cache.memcached;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtils {
	public static Properties prop=null;
	public static Properties domainProp=null;
	static {
		prop = new Properties();
		InputStream in = PropertyUtils.class.getResourceAsStream("/autozi_common_mem.properties");
		try {
			prop.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		domainProp = new Properties();
		in = PropertyUtils.class.getResourceAsStream("/autozi_common_uc.properties");
		try {
			domainProp.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从application.properties取出key对应的字符串
	 * @param key
	 * @return 
	 */
	public static String getValue(String key) {
		return prop.getProperty(key);
	}
	
	/**
	 * 从application.properties取出key对应的字符串
	 * @param key
	 * @return 
	 */
	public static String getDomainValue(String key) {
		return domainProp.getProperty(key);
	}
	
}
